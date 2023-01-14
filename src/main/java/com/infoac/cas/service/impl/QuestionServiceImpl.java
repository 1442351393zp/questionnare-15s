package com.infoac.cas.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.infoac.cas.dao.*;
import com.infoac.cas.dto.*;
import com.infoac.cas.entity.Client;
import com.infoac.cas.enums.IsBooleanEnum;
import com.infoac.cas.exception.BusinessException;
import com.infoac.cas.service.IReelAnswerRecordService;
import com.infoac.cas.service.QuestionService;
import com.infoac.cas.util.*;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class QuestionServiceImpl implements QuestionService{
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private static final String TIME_FORMAT = "%s时%s分%s秒";
	
	@Autowired
    private QuestionDao questionDao;
	@Autowired
    private ReelDao reelDao;
	@Autowired
	private UserDao userDao;

	@Autowired
	private ReelBrowseRecordDao browseRecordDao;

    @Autowired
    private IReelAnswerRecordService reelAnswerRecordService;
    @Autowired
    private AnswerDao answerDao;
	
	@Override
	@Transactional
	public void stopResearch(ReelDTO reel) {
		String url = ProjectConfUtils.getProjectConf("ssourl");
		List<SelectUserDTO> selectList = new ArrayList<>();
		List<UserDTO> allList = new ArrayList<>();
		String reelId = reel.getrId();
		String userId = reel.getUserId();
		AllSubjectDTO reelVo = reelDao.queryByRIdReel(reelId);
		if(reelVo != null) {
			if(IsBooleanEnum.YES_ZREO.getKey().equals(reelVo.getRetrieve())) {    //是否发收回消息
				String token = getAccessToken(url);
				logger.info("token===================:" + token);
				if(IsBooleanEnum.YES_ZREO.getKey().equals(reelVo.getIsLimit())) {  //是否限定人员
					selectList = userDao.getSelectUser(reel.getrId());
					//给参与人员发消息
					for(int i = 0; i < selectList.size(); i++) {
						sendMsg(url,token,selectList.get(i).getUserId(),userId,reelId,reelVo.getTitle());
					}
				}else {
					allList = userDao.getAllUser();
					//给参与人员发消息
	                for(int i = 0; i < allList.size(); i++) {
	                	sendMsg(url,token,allList.get(i).getId(),userId,reelId,reelVo.getTitle());
					}
				}
			}
			questionDao.updateReel(reel);
		}
		
	}

	private String getAccessToken(String url) {
		logger.info("-----------------获取开始-----------------");
    	String clientId = ProjectConfUtils.getProjectConf("client_id");
    	String clientSecret = ProjectConfUtils.getProjectConf("client_secret");
    	String grantType = ProjectConfUtils.getProjectConf("grant_type");
    	
    	//获取微服务 access_token
 	    String valtokenUrl = url + "/api/uaa/oauth/token?client_id="+clientId+"&client_secret="+clientSecret+"&grant_type="+grantType;
 	    logger.info("获取消息的token的地址");
 	    String token = HttpClientUtil.getInstance().doPostValtoken(valtokenUrl);
 	    logger.info("--------------返回结果token:--------------"+token);
 	    TokenDTO result = JSON.parseObject(token, TokenDTO.class);
 	    String accessToken = result.getAccess_token();
 	    logger.info("-----------------获取结束-----------------");
 	    return accessToken;
	}

	@Transactional
	@Override
	public void startResearch(ReelDTO reel) {
		String url = ProjectConfUtils.getProjectConf("ssourl");
		List<SelectUserDTO> selectList = new ArrayList<>();
		List<UserDTO> allList = new ArrayList<>();
		String reelId = reel.getrId();
		AllSubjectDTO reelVo = reelDao.queryByRIdReel(reelId);
        String userId = reelVo.getUserId();
        //查看题目是否未空,空则前端提示:题目为空,不可开始调查
        List<SubjectDTO> subjectDTOS = reelDao.querySubjectListByReelId(reelId);
        if(CollectionUtils.isEmpty(subjectDTOS)){
            throw new BusinessException("题目为空,不可操作");
        }
        if(reelVo != null) {
            String reelStatus = reelVo.getReelStatus();//卷的状态,0开始,1暂停
			String token = "123";
            if(IsBooleanEnum.NO_ONE.getKey().equals(reelStatus)){
                //页面点击暂停调查,那么数据库里是0(开始),推送
                if(IsBooleanEnum.YES_ZREO.getKey().equals(reelVo.getInitiate())) {
//                    String token = getAccessToken(url);
                    if(IsBooleanEnum.YES_ZREO.getKey().equals(reelVo.getIsLimit())) {  //是否限定人员
                        selectList = userDao.getSelectUser(reel.getrId());
                        //给参与人员发消息
                        for(int i = 0; i < selectList.size(); i++) {
                            //單開始調查時,插入DESK_COUNT記錄
                            String id = DateUtil.getCommentId();
                            answerDao.insertDeskCount(reelId,selectList.get(i).getUserId(),id);
                            sendMsg(url,token,selectList.get(i).getUserId(),userId,reelId,reelVo.getTitle());
                        }
                    }else {
                        allList = userDao.getAllUser();
                        //给全部人员发消息
                        for(int i = 0; i < allList.size(); i++) {
                            //單開始調查時,插入DESK_COUNT記錄
                            String id = DateUtil.getCommentId();
                            answerDao.insertDeskCount(reelId,allList.get(i).getId(),id);
                            sendMsg(url,token,allList.get(i).getId(),userId,reelId,reelVo.getTitle());
                        }
                    }
                }else {
                    if(IsBooleanEnum.YES_ZREO.getKey().equals(reelVo.getIsLimit())) {  //是否限定人员
                        selectList = userDao.getSelectUser(reel.getrId());
                        //给参与人员发消息
                        for(int i = 0; i < selectList.size(); i++) {
                            //單開始調查時,插入DESK_COUNT記錄
                            String id = DateUtil.getCommentId();
                            answerDao.insertDeskCount(reelId,selectList.get(i).getUserId(),id);
                        }
                    }else {
                        allList = userDao.getAllUser();
                        //给全部人员发消息,太多sql循環有問題
                        for(int i = 0; i < allList.size(); i++) {
                            //單開始調查時,插入DESK_COUNT記錄
                            String id = DateUtil.getCommentId();
                            answerDao.insertDeskCount(reelId,allList.get(i).getId(),id);
                        }
                    }
                }
            }else {
                //暂停问卷删除desk表
                answerDao.delDeskCountByrid(reelId);
            }
			questionDao.updateReel(reel);
		}else {
			logger.info("-----------------题卷为空-----------------");
		}
	}
	
	
	
    private boolean sendMsg(String url, String token, String receiverId, String senderId, String reelId,String title) {
 	    if(!"".equals(token) && token != null) {
 	    	String appId = ProjectConfUtils.getProjectConf("client_id");
 	    	String tbzxUrl = ProjectConfUtils.getProjectConf("tbzx")+"?reelId="+reelId+"&userId="+senderId+"&receiverId="+receiverId;
            System.out.println("quest,tbzxUrl:"+tbzxUrl);
 	    	JSONObject json = new JSONObject();
 	    	String date = DateUtil.getCurrentDateStr();
 	    	String content = "您在"+date+"收到一条问卷调查:"+title;
 	    	json.put("type", "LINK");
 	    	json.put("title", "您有新的通知");
 	    	json.put("content", content);
 	    	json.put("appid", appId);
 	    	json.put("receiver", receiverId);
 	    	json.put("sendfrom", senderId);
 	    	json.put("redirect", tbzxUrl);
 	    	json.put("operations", "[]");
			byte[] bytes = new byte[0];
			try {
				bytes = json.toJSONString().getBytes("UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			String encodeds = Base64.getEncoder().encodeToString(bytes);
 	    	String encoded = encodeds.replaceAll("/", "_");
 	    	String msgUrl = url + "messageCenter/send/messageBox/WJDC/"+receiverId+"/"+encoded;
            
 	    	String result = HttpClientUtil.getInstance().sendHttpPost(msgUrl);
            if("TOPIC_AND_TAG_NOT_REGIST".equals(result)) {
            	logger.info("(当前主题和标签未注册)------------需要新增消息订阅信息");
            	return false;
            }else if("SEND_FAILED".equals(result)){
            	logger.info("-----------"+receiverId+"-----------发送失败");
            	return false;
            }else {
            	logger.info("-----------"+receiverId+"-----------发送成功");
            	return true;
            }
 	    }else {
 	    	return false;
 	    }
	}
    /*
     * 设置
     */
	@Override
	public void updateReel(ReelDTO reel) {
		questionDao.updateReel(reel);
	}

    @Override
    public void updateReelCanalText(String rid) {
        questionDao.updateReelCanalText(rid);
    }



	@Override
	public  List<String>  selectUsers() {
		return questionDao.selectUsers();
	}

	/**
	 * 查询答题的已答和未答
	 * @param rId
	 * @return
	 */
	@Override
	public SelectUsersDTO  getQuestionState(String rId) {
		String pid = ProjectConfUtils.getProjectConf("PID");
		SelectUsersDTO returnDTO = new SelectUsersDTO();
		Map<String, Object> map = new HashMap<>();
		logger.info("查询开始，查询当前问卷是否有限定用户");
		String limit = selectuserids(rId);
		if(limit.equals("0")){
		logger.info("当前问卷有限定用户,查询限定用户的人员");
        List<QuestionStateDTO> questionAllList= questionDao.getQuestionAllperope(rId);
        logger.info("查询已经答过的人员");
        List<QuestionStateDTO> answeredPeoperList = questionDao.getAnsweredPeoper(rId);
        logger.info("组装未答人员");
		List<QuestionStateDTO> noAnsweredPeoperList =questionAllList.stream().filter(item->!answeredPeoperList.contains(item)).collect(Collectors.toList());
        map.put("0",answeredPeoperList);
        map.put("1",noAnsweredPeoperList);
		}else {
		logger.info("当前问卷没有限定用户,默认是全部人员");
		List<QuestionStateDTO> questionAllList = questionDao.getNoQuestionAllperope();
		logger.info("查询已经答过的人员");
		List<QuestionStateDTO> answeredPeoperList = questionDao.getAnsweredPeoper(rId);
//		logger.info("组装未答人员");
//		List<QuestionStateDTO> noAnsweredNoPeoperList = questionAllList.stream().filter(item->!answeredPeoperList.contains(item)).collect(Collectors.toList());
//		map.put("0",answeredPeoperList);
//		map.put("1",noAnsweredNoPeoperList);
		map.put("list",this.getOrganAnswer(pid,answeredPeoperList));
		net.sf.json.JSONArray arr = net.sf.json.JSONArray.fromObject(this.getOrganAnswer(pid,answeredPeoperList));
		String json = arr.toString();
		returnDTO.setZtreeuser(json);
		}
	    return returnDTO;
	}

	/**
	 * 发送消息给未答的人员
	 * @param jsonList
	 */
	@Override
	public void sendMessage(JSONObject jsonList) throws Exception{
		logger.info("组装消息发送给未答人员通知");
		JSONArray userIdList = jsonList.getJSONArray("userIdLIst");
		String rId  = jsonList.get("rId").toString();
		String receiverId = questionDao.getReelUserId(rId);
		String url = ProjectConfUtils.getProjectConf("ssourl");
		String appId = ProjectConfUtils.getProjectConf("15client_id");
		for (int i = 0; i <userIdList.size() ; i++) {
			    String tbzxUrl = ProjectConfUtils.getProjectConf("tbzx")+"?reelId="+rId+"&userId="+userIdList.get(i)+"&receiverId="+receiverId;
			    logger.info("当前问卷地址:"+tbzxUrl);
				JSONObject json = new JSONObject();
				String content = "您有一份未答问卷,请登录问卷调查及时作答";
				json.put("type", "LINK");
				json.put("title", "您有一份未答问卷,请登录问卷调查及时作答");
				json.put("content", content);
				json.put("appid", appId);
				json.put("receiver", userIdList.get(i));
				json.put("sendfrom",receiverId );
				json.put("redirect", tbzxUrl);
				json.put("operations", "[]");
				byte[]	bytes = json.toJSONString().getBytes("UTF-8");
			    String encodeds = Base64.getEncoder().encodeToString(bytes);
				String encoded = encodeds.replaceAll("/", "_");
				String msgUrl = url + "messageCenter/send/messageBox/WJDC/"+userIdList.get(i)+"/"+encoded;
				logger.info("=============消息的发送地址:"+msgUrl);
				String result = HttpClientUtil.getInstance().sendHttpPost(msgUrl);
				logger.info("=========消息发送返回=========:"+result);
				if("TOPIC_AND_TAG_NOT_REGIST".equals(result)) {
					logger.info("(桌面角标当前主题和标签未注册)------------需要新增消息订阅信息");
				}else if("SEND_FAILED".equals(result)){
					logger.info("-----------"+receiverId+"-----------消息发送失败");
				}else {
					logger.info("-----------"+receiverId+"-----------消息发送成功");
				}
		}

	}

	@Override
    public String selectuserids(String rid) {
        return questionDao.selectuserids(rid);
    }

    @Override
    public List<String> selectUserList(String rid) {
        return questionDao.selectUserList(rid);
    }


    /**
     *  根据rid查找设置,回显
     * @param iId
     */
    @Override
    public ReelDTO findReelSetup(String iId) {
        return questionDao.findReelSetup(iId);
    }


	@Override
	public ReceiveDetailResultDto receiveDetail(String reelId) {
		ReceiveDetailResultDto result = new ReceiveDetailResultDto();
		int receivedCount = reelAnswerRecordService.getReceivedCount(reelId);
		int browseCount = browseRecordDao.getBrowseCount(reelId);
        String reelAnswerAverageTimeConsuming = reelAnswerRecordService.getReelAnswerAverageTimeConsuming(reelId);
		reelAnswerAverageTimeConsuming = reelAnswerAverageTimeConsuming == null ? "0" : reelAnswerAverageTimeConsuming;
		List<AllSubjectDTO> allSubjectDTOS = reelDao.queryReel(reelId);
		long hours = Long.parseLong(reelAnswerAverageTimeConsuming)/1000/60/60;
        long minutes = Long.parseLong(reelAnswerAverageTimeConsuming)/1000/60%60;
        long seconds = Long.parseLong(reelAnswerAverageTimeConsuming)/1000%60;
        String time = String.format(TIME_FORMAT, hours,minutes,seconds);
        result.setBrowseCount(browseCount);
		result.setReceivedCount(receivedCount);
		result.setReceivedRate();
		result.setReceivedAvgTime(time);
		result.setReelId(reelId);
		String reelName = allSubjectDTOS.get(0).getTitle();
		result.setReelName(reelName);

		return result;
	}

    public List<OrganVO> getOrganAnswer(String pid,List<QuestionStateDTO> answeredPeoperList){
		List<OrganVO> departmentList = this.listSubDepartmentByParentId(pid);
		for (OrganVO depar : departmentList) {
			List<OrganVO> all=null;
			if(StringUtils.isBlank(depar.getOrganid())) {
				//部门
				depar.setIconSkin("fu");
				List<OrganVO> users = getfindBydeptid(depar.getId());
				if(answeredPeoperList.size()>0){
					for (OrganVO user : users) {
						//选中状态
						for (QuestionStateDTO selectUserDTO : answeredPeoperList) {
							String userId = selectUserDTO.getUserid();
							if(userId.equals(user.getId())){
								user.setChecked("true");
							}
						}
					}
				}
				all= this.getOrganAnswer(depar.getId(),answeredPeoperList);
				all.addAll(users);
			}
			depar.setChildren(all);
		}
		return departmentList;
	}


	public List<OrganVO> listSubDepartmentByParentId(String pid){
    	List<OrganVO> organVOS =   userDao.findForList(pid);
		for (OrganVO organ:organVOS ) {
			organ.setType("dept");
		}
		return  organVOS;
	}
	public List<OrganVO> getfindBydeptid(String pid){
		List<OrganVO> organVOS = userDao.findBydeptid(pid);
		for (OrganVO organ:organVOS ) {
			organ.setType("user");
		}
		return  organVOS;
	}

}
