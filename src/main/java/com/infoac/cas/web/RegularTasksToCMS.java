package com.infoac.cas.web;

import com.alibaba.fastjson.JSON;
import com.infoac.cas.dto.*;
import com.infoac.cas.entity.*;
import com.infoac.cas.service.*;
import com.infoac.cas.util.DateUtil;
import com.infoac.cas.util.HttpClientUtil;
import com.infoac.cas.util.ProjectConfUtils;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * @author 同步用户和组织机构
 */
@Component
public class RegularTasksToCMS {
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
    private SystokenService systokenService;
    @Autowired
    private SyssynService syssynService;
    @Autowired
    private AnswerService answerService;

    /**
     * 每天1点执行一次
     * 获取微服务 access_token
     **/
    @Scheduled(cron = "0 0 0/1 * * ?")
    public void getAccessToken() {
        logger.info("--------------获取开始--------------");
        String url = ProjectConfUtils.getProjectConf("ssourl");
        String clientId = ProjectConfUtils.getProjectConf("client_id");
        String clientSecret = ProjectConfUtils.getProjectConf("client_secret");
        String grantType = ProjectConfUtils.getProjectConf("grant_type");
        String valtokenUrl2 = url + "/api/uaa/oauth/token?client_id=" + clientId + "&client_secret=" + clientSecret + "&grant_type=" + grantType;
        //获取微服务 access_token
        String token = HttpClientUtil.getInstance().doPostValtoken(valtokenUrl2);
        logger.info("token==========:" + token);
        TokenDTO result = JSON.parseObject(token, TokenDTO.class);
        String accessToken = result.getAccess_token();
	     /*Long time = result.getExpires_in();    //时间
	     redisOperator.set("access_token", accessToken);
	     //redisOperator.set("startime", time.toString());
	     redisOperator.expire("access_token", time.intValue());*/
        String desktop = ProjectConfUtils.getProjectConf("desktop");
        Systoken systoken = new Systoken();
        systoken.setName(desktop);
        systoken.setAccessToken(accessToken);
        systoken.setUpdateTime(DateUtil.simplifyTimestampFormat(new Date()));
        systokenService.updateAccessToken(systoken);
    }

    /**
     * 每天2点执行一次
     * 同步用户
     **/
   // @Scheduled(cron = "0 0/5 * * * ?")
    public void synchroMember() {
 		 /*String token = redisOperator.get("access_token");
 		 String startime = redisOperator.get("startime");
 		 if(StringUtils.isBlank(startime)) {
 			 startime="0";
 		 }*/
        String desktop = ProjectConfUtils.getProjectConf("desktop");
        String token = systokenService.getAccessToken(desktop);
        long startime = syssynService.getStartTime(desktop);
        if ("test".equals(token) || StringUtils.isBlank(token)) {
            getAccessToken();
        }
        logger.info("--------------增量同步接口--------------");
        saveMember(startime, token, desktop);
        //token 不为空执行
 		 /*if(!"".equals(token) && token != null){
 			 logger.info("--------------增量同步接口--------------");
 			 saveMember(startime,token);
 			 //saveDepartment(startime,token);
 		 }else{
 			 //token 为空调用验证
 			 getAccessToken();
 			 token = redisOperator.get("access_token");
 			 startime = redisOperator.get("startime");
 			 saveMember(startime,token);
 			 //saveDepartment(startime,token);
 		 }*/
    }

    private void saveMember(long startime, String token, String desktop) {
        JSONObject json = new JSONObject();
        logger.info("--------------获取开始--------------");
        String url = ProjectConfUtils.getProjectConf("ssourl");
        //用户信息实体类
        UserDTO user = new UserDTO();
        //用户和角色的实体类
        UserRoleKey urk = new UserRoleKey();
        //组织机构实体类
        OrganizaDTO organ = new OrganizaDTO();
        //用户和组织机的构实体类
        UserOrganKey uok = new UserOrganKey();
        String httpUrl = url + "api/org/syncdepartments?starttime=" + startime + "&department=root&access_token=" + token;
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
                    user.setBan(bean.getIsDelete());;//用户是否禁用（0：启用；1：禁用)
                    //user.setPassword("123456");//用户密码
                    if(StringUtils.isBlank(bean.getPassword())){
                        user.setPassword("123456");//用户密码
                    }else {
                        user.setPassword(bean.getPassword());
                    }
                    user.setSex(bean.getSex());//用户性别
                    user.setPhone(bean.getTel());//用户电话
                    user.setIsManager(String.valueOf(bean.getIsManager()));
                    user.setOrganid(bean.getOrganid());
                    urk.setRoleid("4");//槐树林需要用户默认是管理员
                    urk.setUserid(user.getId());
                    /*if (user.getIsManager().equals("0")) {
                        urk.setRoleid("0");
                        urk.setUserid(user.getId());

                    } else if (user.getIsManager().equals("1")) {
                        urk.setRoleid("1");
                        urk.setUserid(user.getId());
                    } else if (user.getIsManager().equals("2")) {
                        urk.setRoleid("2");
                        urk.setUserid(user.getId());
                    } else {
                        urk.setRoleid("4");
                        urk.setUserid(user.getId());
                    }*/
                    //将组织机构和用户的关系同步进来
                    uok.setOrgid(bean.getOrganid());
                    uok.setUserid(id);
                    //保存修改之后的用户
                    userService.addUser(user);
                    //保存修改之后的用户和组织机构的关系
                    organizaService.saveroleUser(uok);
                    //保存修改之后的用户和角色的关系
                    userService.saveroleUser(urk);

                } else {                 //新增
                    UserDTO us = userService.userFind(bean.getUserid());
                    if (us == null) {

                        user = new UserDTO();
                        user.setId(bean.getUserid());//用户id
                        user.setEmail(bean.getUserEmail());//用户邮箱
                        user.setUsername(bean.getAccount()); //用户账号也就是用户名
                        user.setNickname(bean.getFullname());//用户昵称
                        user.setBan(bean.getIsDelete());;//用户是否禁用（0：启用；1：禁用)
                        //user.setPassword("123456");//用户密码
                        //if(StringUtils.isBlank(bean.getPassword())){
                            user.setPassword("123456");//用户密码
                        /*}else {
                            user.setPassword(bean.getPassword());
                        }*/
                        user.setSex(bean.getSex());//用户性别
                        user.setPhone(bean.getTel());//用户电话
                        user.setIsManager(String.valueOf(bean.getIsManager()));//用户说明
                        user.setOrganid(bean.getOrganid());
                        urk.setRoleid("4");//槐树林需要用户默认是管理员
                        urk.setUserid(user.getId());
                        /*if (user.getIsManager().equals("0")) {
                            urk.setRoleid("0");
                            urk.setUserid(user.getId());

                        } else if (user.getIsManager().equals("1")) {
                            urk.setRoleid("1");
                            urk.setUserid(user.getId());
                        } else if (user.getIsManager().equals("2")) {
                            urk.setRoleid("2");
                            urk.setUserid(user.getId());
                        } else {
                            urk.setRoleid("4");
                            urk.setUserid(user.getId());
                        }*/
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
                    OrganizaDTO or = organizaService.organFind(bean.getOrganId());
                    if (or == null) {
                        organ = new OrganizaDTO();
                        organ.setId(bean.getOrganId()); //机构id
                        organ.setPid(bean.getFatherId()); //父id
                        organ.setOrname(bean.getOrganName());//机构名称
                        organ.setOrcode(bean.getCode());//机构代码
                        organ.setOrrank(String.valueOf(bean.getOrderId()));//优先级
                        organizaService.organizaAdd(organ);
                    }
                }

            }
            /*redisOperator.set("startime",result1.getTimestamp().toString());*/
            Syssyn syn = new Syssyn();
            syn.setName(desktop);
            syn.setStarttime(result1.getTimestamp());
            syn.setUpdateTime(DateUtil.simplifyTimestampFormat(new Date()));
            syssynService.updateStartTime(syn);
            //事务回滚
            //TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        } else {
            logger.info("--------------增量同步失败--------------");
        }
        logger.info("--------------同步结束--------------");
    }

    /**
     * @Author: PengSenjie
     * @description: 问卷即将过期消息提醒
     * @Date: Created in 20-1-7 上午9:03
     */
    @Scheduled(cron = "0 0/5 * * * ?")
    public void msgExpire() {
        logger.info("--------------问卷即将过期消息提醒start--------------");
        List<Integer> yearsList = DateUtil.getYearList();
        answerService.msgExpire(yearsList);
        logger.info("--------------问卷即将过期消息提醒end--------------");
    }


}
