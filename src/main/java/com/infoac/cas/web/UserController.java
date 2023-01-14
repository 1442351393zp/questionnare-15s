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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author: ZP
 * @Description: 用户查询
 * @Date: Created in  2019/7/4 9:28
 */
@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController {

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
     * 用户信息的同步
     *
     */
    @RequestMapping(value = "/tongbuUser", produces = {"application/josn;charset=UTF-8"})
    @ResponseBody
    @Log(operateType = "同步组织及用户信息")
    public String tongbuUser(HttpServletRequest request) throws Exception {
        JSONObject json = new JSONObject();//15840402430
        logger.info("--------------获取开始--------------");
        json = syndeptuser("now");
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
        json = syndeptuser("all");
        return json.toString();
    }

    private JSONObject syndeptuser(String flag) {
        JSONObject json = new JSONObject();
        json.put("msg", "同步成功");
        json.put("code", 0);
        logger.info("--------------获取开始--------------");
        String url = ProjectConfUtils.getProjectConf("ssourl");
        String clientId = ProjectConfUtils.getProjectConf("client_id");
        String clientSecret = ProjectConfUtils.getProjectConf("client_secret");
        String grantType = ProjectConfUtils.getProjectConf("grant_type");
        String valtokenUrl = url + "/api/uaa/oauth/token?client_id=" + clientId + "&client_secret=" + clientSecret + "&grant_type=" + grantType;
        //获取微服务 access_token
        String token = HttpClientUtil.getInstance().doPostValtoken(valtokenUrl);
        logger.info("--------------返回结果--------------" + token);
        TokenDTO result = JSON.parseObject(token, TokenDTO.class);
        String accessToken = result.getAccess_token();
        logger.info("-----------------获取结束-----------------");
        String desktop = ProjectConfUtils.getProjectConf("desktop");
        long startime =0;
        if("all".equals(flag)){
            //删除目前数据库组织用户
            //删除之前的用户
            userService.userdeleteall();
            //删除之前用户和角色的关系
            userService.deleteUserRoleall();
            //删除之前用户和组织机构的关系
            organizaService.deleteRolePermissionall();
            //删除组织表
            organizaService.organizadeleteall();
        }else {
            startime=syssynService.getStartTime(desktop);

        }
        //用户信息实体类
        UserDTO user = new UserDTO();
        //用户和角色的实体类
        UserRoleKey urk = new UserRoleKey();
        //组织机构实体类
        OrganizaDTO organ = new OrganizaDTO();
        //用户和组织机的构实体类
        UserOrganKey uok = new UserOrganKey();
        String httpUrl = url + "api/org/syncdepartments?starttime=" + startime + "&department=root&access_token=" + accessToken;
        String userResult = HttpClientUtil.getInstance().sendHttpGet(httpUrl);
        MemberResult result1 = JSON.parseObject(userResult, MemberResult.class);
        if (result1.getRsltcode() == 200) {
            List<MemberBean> memberList = result1.getUser();
            List<ReDepartment> redepartmentList = result1.getOrg();
            logger.info("--------------同步开始--------------");
            for (MemberBean bean : memberList) {
                int type = bean.getType();
                String id = bean.getUserid();
                if (type == 0) {        //删除
                    userService.userdelete(id);
                    //删除之前用户和角色的关系
                    userService.deleteUserRole(id);
                    //删除之前用户和组织机构的关系
                    organizaService.deleteRolePermission(id);
                } else if (type == 1) {   //修改
                    //删除之前的用户
                    userService.userdelete(id);
                    //删除之前用户和角色的关系
                    userService.deleteUserRole(id);
                    //删除之前用户和组织机构的关系
                    organizaService.deleteRolePermission(id);
                    user = new UserDTO();
                    user.setId(id);//用户id
                    user.setEmail(bean.getUserEmail());//用户邮箱
                    user.setUsername(bean.getAccount()); //用户账号也就是用户名
                    user.setNickname(bean.getFullname());//用户昵称
                    user.setBan(bean.getIsDelete());//用户是否禁用（0：启用；1：禁用)
                    user.setPassword("123456");//用户密码
                    user.setSex(bean.getSex());//用户性别
                    user.setPhone(bean.getTel());//用户电话
                    user.setIsManager(String.valueOf(bean.getIsManager()));
                    user.setOrganid(bean.getOrganid());
                    urk.setRoleid("4");//槐树林需要用户默认是管理员
                    urk.setUserid(user.getId());
                    //将组织机构和用户的关系同步进来
                    uok.setOrgid(bean.getOrganid());
                    uok.setUserid(id);
                    //保存修改之后的用户
                    userService.addUser(user);
                    //保存修改之后的用户和组织机构的关系
                    organizaService.saveroleUser(uok);
                    //保存修改之后的用户和角色的关系
                    userService.saveroleUser(urk);

                } else { //新增
                    //用户是否存在
                    user = new UserDTO();
                    user.setId(bean.getUserid());//用户id
                    user.setEmail(bean.getUserEmail());//用户邮箱
                    user.setUsername(bean.getAccount()); //用户账号也就是用户名
                    user.setNickname(bean.getFullname());//用户昵称
                    user.setBan(bean.getIsDelete());
                    ;//用户是否禁用（0：启用；1：禁用)
                    //if(StringUtils.isBlank(bean.getPassword())){
                    user.setPassword("123456");//用户密码
                    user.setSex(bean.getSex());//用户性别
                    user.setPhone(bean.getTel());//用户电话
                    user.setIsManager(String.valueOf(bean.getIsManager()));//用户说明
                    user.setOrganid(bean.getOrganid());
                    urk.setRoleid("4");//槐树林需要用户默认是管理员
                    urk.setUserid(user.getId());
                    //将用户和组织机构的关系同步进来
                    uok.setOrgid(bean.getOrganid());
                    uok.setUserid(id);
                    //保存同步的用户
                    userService.addUser(user);
                    //保存用户和组织机构的关系
                    organizaService.saveroleUser(uok);
                    //保存用户和角色的关系
                    userService.saveroleUser(urk);
                }
            }
            for (ReDepartment bean : redepartmentList) {
                int type = bean.getType();
                String id = bean.getOrganId();  //organId
                if (type == 0) {         //删除
                    organizaService.organizadelete(id);
                } else if (type == 1) {   //修改
                    //删除数据重新添加
                    organizaService.organizadelete(id);
                    organ = new OrganizaDTO();
                    organ.setId(bean.getOrganId()); //机构id
                    organ.setPid(bean.getFatherId()); //父id
                    organ.setOrname(bean.getOrganName());//机构名称
                    organ.setOrcode(bean.getCode());//机构代码
                    organ.setOrrank(String.valueOf(bean.getOrderId()));//优先级
                    organizaService.organizaAdd(organ);
                } else {                 //新增
                    organ = new OrganizaDTO();
                    organ.setId(bean.getOrganId()); //机构id
                    organ.setPid(bean.getFatherId()); //父id
                    organ.setOrname(bean.getOrganName());//机构名称
                    organ.setOrcode(bean.getCode());//机构代码
                    organ.setOrrank(String.valueOf(bean.getOrderId()));//优先级
                    organizaService.organizaAdd(organ);
                }

            }
            Syssyn syn = new Syssyn();
            syn.setName(desktop);
            syn.setStarttime(result1.getTimestamp());
            syn.setUpdateTime(DateUtil.simplifyTimestampFormat(new Date()));
            syssynService.updateStartTime(syn);
        } else {
            logger.info("--------------增量同步失败--------------");
            json.put("msg", "同步失败");
            json.put("code", 1);
        }
        return json;
    }

}
