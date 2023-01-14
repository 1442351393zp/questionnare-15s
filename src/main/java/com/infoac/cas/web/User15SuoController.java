package com.infoac.cas.web;

import com.alibaba.fastjson.JSON;
import com.infoac.cas.dto.*;
import com.infoac.cas.entity.*;
import com.infoac.cas.service.*;
import com.infoac.cas.util.Log;
import com.infoac.cas.util.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

/**
 * @Author: ZP
 * @Description: 适配15所用户中心接口
 * @Date: Created in  2019/7/4 9:28
 */
@CrossOrigin
@RestController
@RequestMapping("/userFifteenSuo")
public class User15SuoController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserService userService;
    /*@Autowired
   	private RedisOperator  redisOperator;*/
    @Autowired
    private RoleService roleService;
    @Autowired
    private OrganizaService organizaService;
    @Autowired
    private PermissionService permissionService;

    @Autowired
    private SystokenService systokenService;
    @Autowired
    private SyssynService syssynService;
    @Autowired
    private QuestionService questionService;
    @Autowired
    private ReelService reelService;

    /**
     * @Author: cc
     * @Description: 查出用户
     * @Date: Created in 2019/8/27
     */
    @RequestMapping(value = "/getuser", produces = {"application/josn;charset=UTF-8"})
    @ResponseBody
    public String getUser(HttpServletRequest req, HttpServletResponse Resp) {
        SelectUsersDTO returnDTO = new SelectUsersDTO();
        String reelId = req.getParameter("reelId");
        //查是否勾上了用户按钮
        AllSubjectDTO allSubjectDTO = reelService.queryByRIdReel(reelId);
        returnDTO.setUserlist(new ArrayList<>());
        String isLimit = allSubjectDTO.getIsLimit();
        JSONArray arr = JSONArray.fromObject(userService.listAllDepartment("0", reelId, isLimit));
        String json = arr.toString();
        returnDTO.setZtreeuser(json);
        if ("0".equals(isLimit)) {
            //勾上返回用户列表
            List<SelectDTO> selectUserName = userService.getSelectUserName(reelId);
            returnDTO.setUserlist(selectUserName);
        }
        return JSON.toJSONString(returnDTO);
    }


    /*@RequestMapping(value = "/getuser",produces= {"application/josn;charset=UTF-8"})
    @ResponseBody
    public String getUser(HttpServletRequest req,HttpServletResponse Resp){
        JSONObject json = new JSONObject();
        String reelId = req.getParameter("reelId");
        List<UserDTO> allList = userService.getAllUser();
        if(StringUtil.isNotEmpty(reelId)) {
            List<SelectUserDTO> selectList = userService.getSelectUser(reelId);
            json.put("selectList", selectList);
        }else {
            json.put("selectList", null);
        }
        json.put("allList", allList);
        return json.toString();
    }*/

    /**
     * @Author: cc
     * @Description: 保存用户
     * @Date: Created in 2019/8/27
     */
    @RequestMapping(value = "/saveselectuser", produces = {"application/josn;charset=UTF-8"})
    @ResponseBody
    public String saveSelectUser(HttpServletRequest req, HttpServletResponse Resp) {
        JSONObject json = new JSONObject();
        String reelId = req.getParameter("reelId");
        String userId = req.getParameter("userIds");
        //String userName = req.getParameter("userNames");
        String limit = "0";
        if (userId != null && !"".equals(userId)) {
            userService.saveSelectUser(reelId, userId);
        }
        ReelDTO reel = new ReelDTO();
        reel.setrId(reelId);
        reel.setIsLimit(limit);
        questionService.updateReel(reel);
        json.put("code", Globals.SUCCESSE);
        json.put("msg", Globals.MSG_XG_SUCCESSE);
        return json.toString();
    }

    /*
     * 用户页
     */
    @RequestMapping(value = "/user", produces = {"application/json;charset=UTF-8"})
    public ModelAndView loginInfoac(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("reel/user");
        return mv;
    }

    /*
     * 系统管理页面
     */
    @RequestMapping(value = "/system")
    public ModelAndView System(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView();
        HttpSession session = ContextHolderUtils.getSession();
        Client clinet = ClientManager.getInstance().getClient(session.getId());
        UserDTO user = clinet.getUser();
        if (user.getId() != null) {
            //根据用户信息获取权限
            List<RoleDTO> roles = roleService.selectuserRole(user.getId());
            String ro = null;
            for (RoleDTO roless : roles) {
                ro = roless.getRoleid();
            }
            List<PermissionVO> perlist = permissionService.selectper(ro);
            List<PermissionVO> trperVos = TreeBulider.buildByRecursive(perlist);
            mv.addObject("trperVos", trperVos);
            mv.setViewName("reel/system");
        }
        return mv;

    }

    /*
     * 用户列表的展示
     */
    @RequestMapping(value = "/userlist", method = RequestMethod.POST, produces = {"application/josn;charset=UTF-8"})
    @ResponseBody
    public String userlist(UserVO uservo, HttpServletResponse resp) throws Exception {
        JSONObject json = new JSONObject();
        //所有的数据
        List<UserDTO> userlist = userService.finduserlist(uservo);
        //分页展示
        Long count = userService.findUserCount(uservo);
        BasePage page = new BasePage();
        if (uservo.getPage() == 0) {
            page.setPage(1);
        }
        page.setPage(uservo.getPage());
        page.setRecPerPage(uservo.getRecPerPage());
        page.setRecPerPage(userlist.size());
        page.setRecTotal(count);
        JSONArray jsonArray = JSONArray.fromObject(userlist);
        json.put("code", 0);
        json.put("message", Globals.MSG_CZ_SUCCESSE);
        json.put("data", jsonArray);
        json.put("pager", page);
        json.put("count", count);
        return json.toString();
    }

    /*
     * 用户添加
     */
    @RequestMapping(value = "/userAdd", produces = {"application/josn;charset=UTF-8"})
    @ResponseBody
    @Log(operateType = "用户添加")
    public String userAdd(HttpServletRequest request, UserDTO user) throws Exception {
        JSONObject json = new JSONObject();
        try {
            userService.userAdd(user);
            json.put("code", Globals.SUCCESSE);
            json.put("msg", Globals.MSG_TJ_SUCCESSE);

        } catch (Exception e) {
            // TODO: handle exception
            json.put("msg", Globals.MSG_TJ_FAIL);
            json.put("code", Globals.FIAL);
        }
        return json.toString();
    }

    /*
     * 用户修改和保存
     */
    @RequestMapping(value = "/userupdate", produces = {"application/josn;charset=UTF-8"})
    @ResponseBody
    @Log(operateType = "用户修改")
    public String userupdate(HttpServletRequest request, UserDTO user) throws Exception {
        JSONObject json = new JSONObject();
        try {
            userService.userupdate(user);
            json.put("code", Globals.SUCCESSE);
            json.put("msg", Globals.MSG_XG_SUCCESSE);

        } catch (Exception e) {
            // TODO: handle exception
            json.put("code", Globals.FIAL);
            json.put("msg", Globals.MSG_XG_FAIL);
        }
        return json.toString();
    }

    /*
     * 用户删除
     */
    @RequestMapping(value = "/userupdelete", produces = {"application/josn;charset=UTF-8"})
    @ResponseBody
    @Log(operateType = "用户删除")
    public String userupdelete(HttpServletRequest request, String[] ids) throws Exception {
        JSONObject json = new JSONObject();
        try {
            if (ids != null && ids.length > 0) {
                for (String id : ids) {
                    UserDTO user = userService.userFind(id);
                    if (user.getNickname().equals("管理员")) {
                        json.put("msg", "管理员无法删除");
                        json.put("code", Globals.FIAL);
                        break;
                    } else {
                        userService.userdelete(id);
                        json.put("code", Globals.SUCCESSE);
                        json.put("msg", Globals.MSG_SC_SUCCESSE);
                    }
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
            json.put("code", Globals.FIAL);
            json.put("msg", Globals.MSG_SC_FAIL);
        }
        return json.toString();
    }

    /*
     * 查询用户对应的角色列表
     *
     */
    @RequestMapping(value = "/findroleUser", produces = {"application/josn;charset=UTF-8"})
    @ResponseBody
    public String findroleUser(HttpServletRequest request, HttpServletResponse resp, RoleDTO roleDTO, String userid) {
        JSONObject json = new JSONObject();
        //获取所有的角色列表
        List<RoleDTO> rolelist = roleService.findrolelist(roleDTO);
        //查询用户对应的角色信息
        List<RoleDTO> role = roleService.selectuserRole(userid);
        JSONArray jsonArray = JSONArray.fromObject(rolelist);
        JSONArray jsonArraylist = JSONArray.fromObject(role);
        json.put("data", jsonArray);
        json.put("data1", jsonArraylist);
        json.put("code", 0);
        return json.toString();
    }
    /*
     *给用户赋予角色或者更改用户对应的角色
     */

    @RequestMapping(value = "/saveroleUser", produces = {"application/josn;charset=UTF-8"})
    @ResponseBody
    @Log(operateType = "用户角色修改")
    public String saveroleUser(String roleids, String userid) {
        JSONObject json = new JSONObject();
        //先清除数据库
        userService.deleteUserRole(userid);
        //保存用户的角色
        String[] roleridss = roleids.split(",");
        for (String roleid : roleridss) {

            UserRoleKey urk = new UserRoleKey();
            urk.setRoleid(roleid);
            urk.setUserid(userid);
            //保存新的角色或者保存修改之后的角色
            userService.saveroleUser(urk);
        }
        UserDTO userDTO = new UserDTO();
        userDTO.setIsManager(roleids);
        userDTO.setId(userid);
        userService.updateuserismanage(userDTO);
        json.put("code", 0);
        json.put("msg", "修改成功");
        return json.toString();
    }

    /*
     * 查询用户对应的机构列表
     */
    @RequestMapping(value = "/findPerms", produces = {"application/josn;charset=UTF-8"})
    @ResponseBody
    public String findPerms(OrganVO organVO, String userid) {
        JSONObject json = new JSONObject();
        OrganVO vo = null;
        OrganVO ts = null;
        try {
            //查询所有的
            List<OrganVO> perVO = organizaService.findOrganlist(organVO);//获取所有数据，未转化成数结构
            //根据id查询的
            List<OrganVO> perlist = organizaService.selectUserOrgan(userid);//获取所有数据，未转化成数结构
            //展示选中的数据
            for (int i = 0; i < perVO.size(); i++) {
                vo = perVO.get(i);
                for (int j = 0; j < perlist.size(); j++) {
                    ts = perlist.get(j);
                    if (vo.getId().equals(ts.getId())) {
                        vo.setChecked("true");
                        break;
                    } else {
                        vo.setChecked("false");
                    }

                }
            }
            //数机构数据的展示
            List<OrganVO> trperVOS = TreeBuliderOrgan.buildByRecursive(perVO);
            JSONArray jsonArray = JSONArray.fromObject(trperVOS);
            json.put("code", "0");
            json.put("msg", "查询成功");
            json.put("data", jsonArray);
        } catch (Exception e) {
            e.printStackTrace();
            json.put("code", "1");
            json.put("msg", "查询失败");
        }
        return json.toString();
    }

    /*
     * 根据用户来修改机构和保存
     */
    @RequestMapping(value = "/updateroleOrgan", produces = {"application/josn;charset=UTF-8"})
    @ResponseBody
    public String updaterolePerms(String userid, String id) {
        JSONObject json = new JSONObject();
        //保存用户对应的组织机构先清库
        organizaService.deleteRolePermission(userid);
        //保存用户对印的组织机构
        String[] ids = id.split(",");
        UserOrganKey uok = new UserOrganKey();
        for (String idss : ids) {
            uok.setOrgid(idss);
            uok.setUserid(userid);
            organizaService.saveroleUser(uok);
        }
        json.put("code", 0);
        json.put("msg", "修改成功");
        return json.toString();
    }

    /*
     * 全部重新同步组织用户
     *
     */
    @RequestMapping(value = "/tongbuUserall", produces = {"application/josn;charset=UTF-8"})
    @ResponseBody
    @Log(operateType = "全部重新同步组织及用户信息")
    public String tongbuUserall(HttpServletRequest request) throws Exception {
        JSONObject json = new JSONObject();
        logger.info("--------------全部重新同步组织及用户信息获取开始--------------");
        try {
            logger.info("--------------获取开始--------------");
            String code  = syndeptuser();
            if(!"0".equals(code)){
                json.put("code",code);
                json.put("msg","同步失败");
            }else {
                json.put("code",code);
                json.put("msg","同步成功");
            }
        } catch (Exception e) {
            json.put("msg", "同步失败");
            json.put("code", 1);
            logger.info("同步失败:"+e.toString());
        }
        return json.toString();
    }

    private String  syndeptuser() {
         TokenDTO result = getToken();
         if("1".equals(result.getCode())){
            return result.getCode();
         }
        String accessToken = result.getVisit_token();
        logger.info("-----------------获取结束token结束 -----------------");
        logger.info("====全量同步====开始删除之前的数据");
        //删除之前的用户
        userService.userdeleteall();
        //删除之前用户和角色的关系
        userService.deleteUserRoleall();
        //删除之前用户和组织机构的关系
        organizaService.deleteRolePermissionall();
        //删除组织表
        organizaService.organizadeleteall();
        logger.info("====全量同步====数据删除成功");
        logger.info("==========开始获取用户组织机构数据从第三方接口获取=========");
        try {
        String allOrgUserUrl = ProjectConfUtils.getProjectConf("sync.ip");
        String systemid = ProjectConfUtils.getProjectConf("sso.system.id");
        String  orgId = ProjectConfUtils.getProjectConf("org.id");
        String allOrgUserResult = HttpClientUtil.getInstance().doGetHeader(allOrgUserUrl+"?orgId="+orgId +"&systemid="+systemid,accessToken);
        logger.info("同步的数据信息是:"+allOrgUserResult.toString());
        JSONObject allResult= JSONObject.fromObject(allOrgUserResult);
        JSONObject data = allResult.getJSONObject("data");
            if(!"0".equals(allResult.getString("code"))){
                return "1";
            }else{
                logger.info("==========开始写入组织结构和用户数据=========");
                JSONArray organs = data.getJSONArray("orgs");
                for (Object o : organs) {
                    //组织机构实体类
                    OrganizaDTO organ = new OrganizaDTO();
                    JSONObject dept = JSONObject.fromObject(o);
                    organ.setPid("-1".equals(dept.getString("orgParentId")) ? "0" : dept.getString("orgParentId"));
                    organ.setId(dept.getString("orgId"));
                    organ.setOrname(dept.getString("orgFullName"));
                    organ.setOrcode(dept.getString("orgcode"));
                    organizaService.organizaListAdd(organ);
                }
                logger.info("==========写入数据库组织信息完成.开始写入用户信息=========");
                JSONArray userList = data.getJSONArray("users");
                for (Object o : userList) {
                    //用户和组织机的构实体类
                    UserOrganKey uok = new UserOrganKey();
                    UserDTO user = new UserDTO();
                    //用户和角色的实体类
                    UserRoleKey urk = new UserRoleKey();
                    JSONObject userDto = JSONObject.fromObject(o);
                    user.setId(userDto.getString("mail"));//用户id
                    user.setUsername(userDto.getString("cn"));//用户账号也就是用户名
                    user.setNickname(userDto.getString("personTrueName"));//用户昵称
                    user.setPassword("123456");//密码
                    user.setOrganid(userDto.getString("personOrgId"));
                    user.setBan(0);
                    userService.addUserList(user);
                    logger.info("==========解析数据完成开始保存用户数据=================");
                    //设置默认的用户和角色的关系
                    urk.setUserid(user.getId());
                    urk.setRoleid("4");
                    userService.saveroleUserList(urk);
                    logger.info("===============保存修改之后的用户和角色的关系数据结束======================");
                    //将组织机构和用户的关系同步进来
                    uok.setOrgid(userDto.getString("personOrgId"));
                    uok.setUserid(user.getId());
                    organizaService.saveOrganUserList(uok);
                    logger.info("===============保存用户和组织机构的关系数据结束======================");
                }
                logger.info("===============保存用户关系数据结束======================");
            }
            logger.info("============同步数据成功===============");
            return "0";
        } catch (Exception e) {
            logger.info("============同步数据失败==============="+e.toString());
            return "1";
        }
    }
    /*
     * 15所通过桌面单点登录
     */
    @RequestMapping(value = "/loginsso")
    public ModelAndView loginSso(HttpServletRequest request,HttpServletResponse resp) throws Exception {
        logger.info("==========开始单点登录===========");
        String license = ProjectConfUtils.getProjectConf("license");
        boolean flog =true;
        if("0".equals(license)){
            flog = ContentLoader.load();
        }
        if(!flog){
            logger.warn("====login,licence过期======");
            return new ModelAndView("reel/license");
        }
        HttpSession session = ContextHolderUtils.getSession();
        String accessToken = request.getParameter("access_token");
        logger.info("======获取拼写的token是=======:"+accessToken);
        try {
            SsoUser15DTO ssoUser = checkAccessTokenReturnUid15(accessToken);
            if("0".equals(ssoUser.getCode())){
                JSONObject data = JSONObject.fromObject(ssoUser.getData());
                String userId =  data.getString("emailId");
                UserDTO user = userService.userFind(userId);
                if(user != null) {
                    logger.info("单点登录成功");
                    Client client = new Client();
                    client.setUser(user);
                    ClientManager.getInstance().addClinet(session.getId(),client);
                    resp.sendRedirect("/questionnaire/login/index");
                    return null;
                }else {
                    logger.info("单点登录失败原因可能是用户信息不存在");
                    return new ModelAndView("reel/login");
                }
            }else{
                logger.info("单点登录失败原因可能是验证token有效性失败:");
                return new ModelAndView("reel/login");
            }
        } catch (Exception e) {
            logger.info("单点登录失败:"+e.toString());
            return new ModelAndView("reel/login");
        }
    }

    /**
     * 验证token的有效性
     * @return
     */
  private SsoUser15DTO  checkAccessTokenReturnUid15(String token){
     logger.info("===验证token的有效性===");
      SsoUser15DTO ssoUser15DTO = new SsoUser15DTO();
      try {
          TokenDTO result = getToken();
          if("0".equals(result.getCode())){
           logger.info("成功获取到请求头中的token:"+result.getVisit_token());
           Map<String, String> map = new HashMap<>();
           map.put("token",token);
           String url = ProjectConfUtils.getProjectConf("15tokenUrl");
           String results = HttpClientUtil.getInstance().sendHttp15Post(url,map,token);
           logger.info("打印返回结果信息:"+results.toString());
           ssoUser15DTO = JSON.parseObject(results, SsoUser15DTO.class);
           logger.info("--------------获取token有效性结束--------------");
           }else {
              ssoUser15DTO.setCode("1");
          }
       } catch (Exception e) {
          logger.info("=======验证token的有效性异常信息为:"+e.toString());
          ssoUser15DTO.setCode("1");
      }
      return ssoUser15DTO;
  }

    /**
     * 获取15所的token
     * @return String
     */
  private TokenDTO  getToken(){
      TokenDTO result  = new TokenDTO();
      try {
          Map<String, String> map = new HashMap<>();
          logger.info("--------------获取token开始--------------");
          map.put("client_id",ProjectConfUtils.getProjectConf("15client_id"));
          map.put("client_secret",ProjectConfUtils.getProjectConf("15client_secret"));
          String url = ProjectConfUtils.getProjectConf("15ssourl");
          //获取微服务 access_token
          String token = HttpClientUtil.getInstance().sendHttpPost(url,map);
          logger.info("请求返回参数结果是:"+token.toString());
          result = JSON.parseObject(token, TokenDTO.class);
          logger.info("获取的token是:"+result.getVisit_token());
       } catch (Exception e) {
          result.setCode("1");
          logger.info("获取token失败:"+e.toString());
      }
      return result;
  }


}
