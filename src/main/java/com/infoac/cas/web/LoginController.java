package com.infoac.cas.web;

import com.alibaba.fastjson.JSON;
import com.infoac.cas.dto.*;
import com.infoac.cas.entity.Client;
import com.infoac.cas.entity.UserVO;
import com.infoac.cas.service.*;
import com.infoac.cas.util.Log;
import com.infoac.cas.util.*;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: ZP
 * @Description: 登录
 * @Date: Created in  2019/7/4 9:28
 */
@CrossOrigin
@RestController
@RequestMapping("/login")
public class LoginController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private LoginService loginService;
	@Autowired
	private UserService userService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private PermissionService permissionService;
    @Autowired
    private AnswerService answerService;

	/**
	 * @Author: PengSenjie
	 * @Description: 登录
	 * @Date: Created in 2019/7/4 9:30
	 */
	@GetMapping(value = "/login1")
	public ModelAndView login() {
		return new ModelAndView("login").addObject("username", "暂时");
	}

	/**
	 * 用户通过登陆页进行登录
	 *
	 * @param
	 * @param request
	 * @param
	 * @return
	 */
	@RequestMapping(value = "/login")
	public ModelAndView loginInfoac(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		String license = ProjectConfUtils.getProjectConf("license");
		boolean flog = true;
		if ("0".equals(license)) {
			flog = ContentLoader.load();
		}
		//boolean load = false;
		if (flog) {
			mv.setViewName("reel/login");
		} else {
			logger.warn("login,licence过期");
			mv.setViewName("reel/license");
		}
		return mv;
	}

	/**
	 * 访问license授权页面
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/license")
	@ResponseBody
	public Object license(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		String license = request.getParameter("license");

		logger.info("license:{}", license);
		String path = request.getRealPath("") + "/_config/licence.key";
		System.out.println(path);

		FileReader read = new FileReader(path);
		BufferedReader br = new BufferedReader(read);
		StringBuilder sb = new StringBuilder();
		while (br.ready() != false) {
			sb.append(br.readLine());
			sb.append("\r\n");
		}
		System.out.println(sb.toString());
		sb.delete(0, sb.length());
		sb.append(license);
		read.close();
		br.close();
		FileOutputStream fs = new FileOutputStream(path);
		fs.write(sb.toString().getBytes());
		fs.close();

		ContentLoader con = new ContentLoader();
		boolean flog = con.loadLicence(license);
		if (flog) {
			map.put("status", "1");//1：激活成功
		} else {
			map.put("status", "0");//0：激活失败
		}

		//mv.addObject("pd", pd);
		return map;
	}


	/*
	 * 登陆验证
	 *
	 */
	@RequestMapping(value = "/checkuser", produces = {"application/josn;charset=UTF-8"})
	@ResponseBody
	public String checkuser(HttpServletRequest req, HttpServletResponse resp, UserVO user) throws Exception {
		UserDTO dto = null;
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		List<UserDTO> userList = userService.findUser(username, password);
		if (userList.size() != 0) {
			dto = userList.get(0);
		}
		HttpSession session = ContextHolderUtils.getSession();
		String randCode = req.getParameter("randCode");
		JSONObject j = new JSONObject();
		if (StringUtils.isEmpty(randCode)) {
			j.accumulate("msg", "请输入验证码");
			j.accumulate("isSuccess", false);
			return j.toString();
		} else if (!randCode.equalsIgnoreCase(String.valueOf(session.getAttribute("randCode")))) {
			j.accumulate("msg", "验证码错误");
			j.accumulate("isSuccess", false);
			return j.toString();
		} else if (userList.size() == 0) {
			j.accumulate("msg", "用户名或密码错误!");
			j.accumulate("isSuccess", false);
		} else {
			if (dto != null) {
				Client client = new Client();
				client.setUser(dto);
				ClientManager.getInstance().addClinet(session.getId(), client);
				j.accumulate("msg", "登录成功");
				j.accumulate("isSuccess", true);
				//resp.sendRedirect("/questionnaire/login/indexs?userid="+dto.getId());
			}

		}
		String str = j.toString();
		return str;
	}

	/**
	 * 登录成功的页面
	 *
	 * @param
	 * @param request
	 * @param
	 * @return
	 */
	@RequestMapping(value = "/index")
	@Log(operateType = "登录")
	public ModelAndView news_list(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		HttpSession session = ContextHolderUtils.getSession();
		Client clinet = ClientManager.getInstance().getClient(session.getId());
		if (clinet == null) {
			mv.setViewName("reel/login");
		} else {
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
				mv.addObject("username", user.getNickname());
				mv.setViewName("reel/head");
			}
		}
			return mv;

		}

	/**
	 * 创建问卷页面
	 * 
	 * @param
	 * @param request
	 * @param
	 * @return
	 */
	@RequestMapping(value = "/createQuestion")
	public ModelAndView createQuestion(HttpServletRequest request,HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		try {
			response.sendRedirect("/subject/myQuestionaire");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//mv.setViewName("subject/myQuestionaire");
		return mv;
	}
	/*
	 * 单点登陆通过桌面进行登陆
	 */
	
    @RequestMapping(value = "/loginsso")
    public ModelAndView loginSso(HttpServletRequest request,HttpServletResponse resp) throws Exception {
        //localhost:9081/questionnaire/login/loginsso?access_token=17254239-2bf7-494b-b498-02afe3a9bcd2
        String license = ProjectConfUtils.getProjectConf("license");
        boolean flog =true;
        if("0".equals(license)){
            flog = ContentLoader.load();
        }
        if(!flog){
            logger.warn("login,licence过期");
            return new ModelAndView("reel/license");
        }
    	HttpSession session = ContextHolderUtils.getSession();
    	String accessToken = request.getParameter("access_token");
    	logger.debug("request:"+accessToken);
        //验证token有效性
    	SsoUserDTO ssoUser = checkAccessTokenReturnUid(accessToken);
        if(ssoUser.getRsltcode() != 20009){
        	UserDTO user = userService.userFind(ssoUser.getUserid());
        	if(user != null) {
        		logger.info("单点登录成功");
        		Client client = new Client();
				client.setUser(user);
				ClientManager.getInstance().addClinet(session.getId(),client);
    			resp.sendRedirect("/questionnaire/login/index");
    			return null;
        	}else {
        		logger.info("单点登录失败");
        		return new ModelAndView("reel/login");
        	}
        }else{
        	logger.info("单点登录失败");
        	return new ModelAndView("reel/login");
        }
    }
    //验证token 的有效性 和用户信息是否存在
    private SsoUserDTO checkAccessTokenReturnUid(String access_token) throws Exception{
    	String url = ProjectConfUtils.getProjectConf("ssourl");
        logger.debug("access_token:"+access_token);
        SsoUserDTO ssoUser = null;
        String valtokenUrl2 = url + "api/sso/valtoken/" + access_token;
        String tokenflag = HttpClientUtil.getInstance().doPostValtoken(valtokenUrl2);
        logger.debug("tokenflag:" + tokenflag);
        SsoResult result = JSON.parseObject(tokenflag, SsoResult.class);
        if (result !=null && "success".equals(result.getResult())) {
            //token 有效时间
            //获取userid
            String userUrl = url + "api/sso/user";
            String param = "access_token=" + access_token;
            String userinfo = HttpClientUtil.getInstance().sendHttpPost(userUrl, param);
            ssoUser = JSON.parseObject(userinfo, SsoUserDTO.class);
            if ("success".equals(ssoUser.getResult())) {
                //user需要去自己库里校验有效性吗？
                String userid = ssoUser.getUserid();
            } else {
                logger.error("获取user信息失败：" + ssoUser.getRsltmsg());
            }
        } else {
            logger.error("token无效：" + result.getRsltmsg());
        }
        return ssoUser;
    }


    /**
     * @Author: PengSenjie
     * @description: 辦公桌面顯示紅點數量
     * @Date: Created in 20-3-20 下午3:09
     */
    @GetMapping("/countUserReel")
    public DeskReturnDTO countUserReel(HttpServletRequest request) {
        long timeStart = System.currentTimeMillis();
        String access_token = request.getParameter("access_token");
        logger.info("countUserReel:{}", access_token);
        String userId ="";
        DeskReturnDTO returnDTO = new DeskReturnDTO();
        returnDTO.setResult("success");
        returnDTO.setCount("0");
        try {
            //查用戶id
            //获取userid
            String url = ProjectConfUtils.getProjectConf("ssourl");
            String userUrl = url + "api/sso/user";
            String param = "access_token=" + access_token;
            String userinfo = HttpClientUtil.getInstance().sendHttpPost(userUrl, param);
            SsoUserDTO ssoUser = JSON.parseObject(userinfo, SsoUserDTO.class);
            if ("success".equals(ssoUser.getResult())) {
                userId = ssoUser.getUserid();
            } else {
                logger.error("获取user信息失败：" + ssoUser.getRsltmsg());
            }

            if(StringUtils.isNotBlank(userId)){
                //查詢未查看未卷數量
                int count = answerService.countDeskCount(userId);
                returnDTO.setCount(String.valueOf(count));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        long timeEnd = System.currentTimeMillis();
        logger.info("countUserReel耗时{}毫秒", timeEnd - timeStart);
        return returnDTO;
    }

	/*
      获取新的统计页面
     */
	@RequestMapping(value = "/newStatis")
	public ModelAndView firstIndex(String rid,String username) {
		ModelAndView mv = new ModelAndView();
		UserDTO user = userService.userMessage(username);
        HttpSession session = ContextHolderUtils.getSession();
		if(user!=null){
            Client client = new Client();
            client.setUser(user);
            ClientManager.getInstance().addClinet(session.getId(),client);
            String kid = "zp";
            if(rid != null) {
                mv.addObject("rId", rid);
                mv.addObject("kId",kid);
            }else {
                logger.info("----------卷id为空----------");
            }
        }else{
		    logger.info("--------用户信息不存在-----------");
        }
		mv.setViewName("reel/see-head");
		return mv;
	}

}
