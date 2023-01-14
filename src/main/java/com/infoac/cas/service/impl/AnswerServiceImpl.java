package com.infoac.cas.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.infoac.cas.dao.AnswerDao;
import com.infoac.cas.dao.ReelAnswerRecordDao;
import com.infoac.cas.dao.ReelDao;
import com.infoac.cas.dao.UserDao;
import com.infoac.cas.dto.*;
import com.infoac.cas.entity.AnswerVo;
import com.infoac.cas.exception.BusinessException;
import com.infoac.cas.service.AnswerService;
import com.infoac.cas.service.ReelService;
import com.infoac.cas.service.UserService;
import com.infoac.cas.util.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;

@Service
@Transactional
public class AnswerServiceImpl implements AnswerService{
	
	@Autowired
    private AnswerDao answerDao;

	@Autowired
	private ReelAnswerRecordDao reelAnswerRecordDao;

	@Autowired
	private ReelDao reelDao;
    @Autowired
    private UserDao userDao;

    @Autowired
	private UserService userService;
	@Autowired
	private ReelService reelService;
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private final static String ANONYMOUS_STR = "***";

	@Override
	public int findAnswerCount(String reelId, String userId) {
		Map<String,Object> map = new HashMap<>();
		
		List<Integer> years = DateUtil.getYearList();
		map.put("reelId", reelId);
		map.put("userId", userId);
		map.put("years", years);
		
		return answerDao.findAnswerCount(map);
	}

	@Override
	@Transactional
	public void saveAnswer(AnswerDTO answerVo) {
       logger.info("===========保存答案开始======"+answerVo.toString());
		Map<String,Object> map = new HashMap<>();
//		String year = String.valueOf(DateUtil.getCurrentYear());
		int year = DateUtil.getCurrentYear();
		List<AnswerDTO> answerList = new ArrayList<>();
		AnswerDTO answer = null;
		String[] optionIds = null;
		String reelId = answerVo.getReelId();
		String userId = answerVo.getUserId();
		logger.info("=========获取的用户id==========="+userId);
		String receiverId = answerVo.getReceiverId();
		String date = DateUtil.dateFormat(new Date());

        UserDTO loginUser = null;
		try {
            loginUser = ContextHolderUtils.getLoginUser();
        }catch (Exception e){
		    logger.warn("saveAnswer,is menhu....");
            loginUser = userDao.findUserByuid(userId);
        }
        //如设置匿名，进行处理
		String receiverName = getAnonymousReceiverId(reelId,receiverId);
		//添加答题记录
		logger.info("======添加答题记录开始");
		ReelAnswerRecordDTO record = new ReelAnswerRecordDTO();
        String recordid = StringUtil.get32UUID();
        record.setId(recordid);
//		record.setUserId(StringUtils.isBlank(loginUser.getId()) ? "" : loginUser.getId());
		record.setUserId(receiverId);
		record.setUserName(receiverName);
		record.setReelId(reelId);
		record.setTimeConsuming(answerVo.getTimeConsuming());
		record.setStartTime(answerVo.getStartTime());
		record.setEndTime(answerVo.getEndTime());
		record.setCreateOpId(StringUtils.isBlank(loginUser.getId()) ? "" : loginUser.getId());
		record.setCreateOpName(StringUtils.isBlank(loginUser.getUsername()) ? "" : loginUser.getUsername());
		record.setCreateOrgId(StringUtils.isBlank(loginUser.getOrganid()) ? "" : loginUser.getOrganid());
		reelAnswerRecordDao.saveReelAnswerRecord(record);
		logger.info("======添加答题记录结束");

		for (int i = 0; i < answerVo.getSubjectIds().length; i++) {
			String subjectId = answerVo.getSubjectIds()[i];
			String optionsId = answerVo.getOptionsIds()[i];
			String pageId = answerVo.getPageIds()[i];
			if(StringUtils.isBlank(optionsId)){
				continue;
			}
			optionsId  = optionsId.substring(0, optionsId.length()-1);
			optionIds = optionsId.split(";");

			for (int j = 0; j < optionIds.length; j++) {
				   answer = new AnswerDTO();
					//单多选
					answer.setId(StringUtil.get32UUID());
					answer.setSubjectId(subjectId);
					answer.setOptionsId(optionIds[j]);
					answer.setPageId(pageId);
					answer.setUserId(userId);
					answer.setReelId(reelId);
					answer.setCreateDate(date);
					answer.setReceiverId(receiverId);
				   if(StringUtils.isNotBlank(answerVo.getTextAnswerList())){
					List<TextAnswerDTO> textAnswer = JSONObject.parseArray(answerVo.getTextAnswerList(),TextAnswerDTO.class );
					for (TextAnswerDTO textAnswerDTO :textAnswer) {
						if(answer.getOptionsId().equals(textAnswerDTO.getOptionsId())){
							answer.setTextAnswer(textAnswerDTO.getTextAnswer());
						}
					}
				  }
					//保存答题记录编号
					answer.setAnswerRecordId(record.getId());
					answer.setReceiverName(receiverName);
					answerList.add(answer);


			}
		}

		//含有题目的填空,插入填空表,oid为空
        //含有选项的填空,插入填空表
        List<OobjectDTO> oobjects1 = answerVo.getOobjects();
        for (OobjectDTO oobjectDTO : oobjects1) {
            String sid = oobjectDTO.getSid();
            String oid = oobjectDTO.getOid();
            List<String> ssite = oobjectDTO.getSsite();
            GapFillingDTO gapFillingDTO = new GapFillingDTO();
			gapFillingDTO.setContent("");
			String s = JSON.toJSONString(ssite);
			if(!"null".equals(s) && StringUtils.isNotBlank(s)){
				gapFillingDTO.setContent(s);
			}
            gapFillingDTO.setFillinId(DateUtil.getCommentId());
            gapFillingDTO.setrId(reelId);
            gapFillingDTO.setUserId(receiverId);
            gapFillingDTO.setSubjectId(sid);
            gapFillingDTO.setOptionsId(oid);
            gapFillingDTO.setRecordId(recordid);
            gapFillingDTO.setCreateTime(DateUtil.getTime());
            String type ="1";
            if(StringUtils.isNotBlank(oid)){
                type ="2";//选项
            }
            gapFillingDTO.setType(type);
            answerDao.insertGap(gapFillingDTO);
        }

        map.put("year", year);
		//map.put("answerList", answerList);
		for (AnswerDTO answerDTO : answerList) {
			map.put("answer",answerDTO);
			answerDao.saveAnswer(map);
		}

		logger.info("******AnswerServiceImpl.saveAnswer锛氫繚瀛樻垚鍔�******");

	}

	@Override
	public List<SubjectDTO> queryAnswerRId(String rId,String userId) {
        Map<String,Object> map = new HashMap<>();
		
		List<Integer> years = DateUtil.getYearList();
		map.put("reelId", rId);
		map.put("userId", userId);
		map.put("years", years);
		return answerDao.queryAnswerRId(map);
	}
	@Override
	public Long findAnswerCountByUser(AnswerVo answer) {
		return answerDao.findAnswerCountByUser(answer);
	}
    @Override
    public Long findunAnswerCountByUser(AnswerVo answer) {
        return answerDao.findunAnswerCountByUser(answer);
    }

	@Override
	public List<AnswerUserDTO> findAnswerListByUser(AnswerVo answer) {
		return answerDao.findAnswerListByUser(answer);
	}

    @Override
    public List<AllSubjectDTO> findunAnswerListByUser(AnswerVo answer) {
        return answerDao.findunAnswerListByUser(answer);
    }

    @Override
    public void msgExpire(List<Integer> yearsList) {
	    //获取今年前年的开始调查,设置了答题结束时间,设置了推动提醒的,推送状态是1的.三个小时,
        String time = DateUtil.getTime();
        String time2 = DateUtil.getBef3Time(3);
        List<AllSubjectDTO> allSubjectDTOS = answerDao.msgExpire(time,time2);
        //循环查找选择用户表,
        String url = ProjectConfUtils.getProjectConf("ssourl");
        String token = getAccessToken(url);
        for (AllSubjectDTO allSubjectDTO : allSubjectDTOS) {
            String rid = allSubjectDTO.getrId();
            String userId = allSubjectDTO.getUserId();
            String title = allSubjectDTO.getTitle();
            String endTime = allSubjectDTO.getEndTime();
            List<SelectUserDTO> selectList = userDao.getSelectUser(rid);
            //给参与人员发消息
            for(int i = 0; i < selectList.size(); i++) {
                sendMsg(url,token,selectList.get(i).getUserId(),userId,rid,title,endTime);
            }
            //设置过期推送状态0
            answerDao.updateExpire(rid);
        }


    }

    private String getAccessToken(String url) {
        logger.info("-----------------获取开始-----------------");
        String clientId = ProjectConfUtils.getProjectConf("client_id");
        String clientSecret = ProjectConfUtils.getProjectConf("client_secret");
        String grantType = ProjectConfUtils.getProjectConf("grant_type");

        //获取微服务 access_token
        String valtokenUrl = url + "/api/uaa/oauth/token?client_id="+clientId+"&client_secret="+clientSecret+"&grant_type="+grantType;
        String token = HttpClientUtil.getInstance().doPostValtoken(valtokenUrl);
        logger.info("--------------返回结果--------------"+token);
        TokenDTO result = JSON.parseObject(token, TokenDTO.class);
        String accessToken = result.getAccess_token();
        logger.info("-----------------获取结束-----------------");
        return accessToken;
    }
//url,token,selectList.get(i).getUserId(),userId,rid,title,endTime
    private boolean sendMsg(String url, String token, String receiverId, String senderId, String reelId,String title,String endTime) {
        if(!"".equals(token) && token != null) {
            String appId = ProjectConfUtils.getProjectConf("client_id");
            String tbzxUrl = ProjectConfUtils.getProjectConf("tbzx")+"?reelId="+reelId+"&userId="+senderId+"receiverId="+receiverId;
            System.out.println("answer,tbzxUrl:"+tbzxUrl);
            JSONObject json = new JSONObject();
            //String date = DateUtil.getCurrentDateStr();
            String content = "您的问卷调查即将过期:"+title+",过期时间:"+endTime;
            json.put("type", "LINK");
            json.put("title", "您有新的通知");
            json.put("content", content);
            json.put("appid", appId);
            json.put("receiver", receiverId);
            json.put("sendfrom", senderId);
            json.put("redirect", tbzxUrl);
            json.put("operations", "[]");
            byte[] bytes = json.toJSONString().getBytes();
            String encodeds = Base64.getEncoder().encodeToString(bytes);
            String encoded = encodeds.replaceAll("/", "_");
            String msgUrl = url + "api/msg/messageCenter/send/messageBox/WJDC/"+receiverId+"/"+encoded+"?access_token="+token;

            String result = HttpClientUtil.getInstance().sendHttpPost(msgUrl);
            if("TOPIC_AND_TAG_NOT_REGIST".equals(result)) {
                logger.info("(当前主题和标签未注册2)------------需要新增订阅信息2");
                return false;
            }else if("SEND_FAILED".equals(result)){
                logger.info("-----------2"+receiverId+"-----------发送失败2");
                return false;
            }else {
                logger.info("-----------2"+receiverId+"-----------发送成功2");
                return true;
            }
        }else {
            return false;
        }
    }

    @Override
	public List<AnswerDTO> findAnswersByUser(String reelId, String userId, String year, String recordId) {
		return answerDao.findAnswersByUser(reelId,userId,year,recordId);
	}

    @Override
    public String saveCollect(CollectDTO collectDTO) {
        //收藏题目唯一id
        String id = StringUtil.get32UUID();
        collectDTO.setId(id);
        List<String> optionList = collectDTO.getOptionList();
        collectDTO.setOptions(JSON.toJSONString(optionList));
        answerDao.saveCollect(collectDTO);
        return id;
    }
    @Override
    public void saveCollectsys(CollectDTO collectDTO) {
        List<String> optionList = collectDTO.getOptionList();
        collectDTO.setOptions(JSON.toJSONString(optionList));
        answerDao.saveCollectsys(collectDTO);
    }

    @Override
    public List<CollectDTO> collectList(String userId) {
        List<CollectDTO> collectDTOS = answerDao.collectList(userId);
        for (CollectDTO collectDTO : collectDTOS) {
            String options = collectDTO.getOptions();
            List<String> strings = JSON.parseArray(options, String.class);
            collectDTO.setOptions("");
            collectDTO.setOptionList(strings);
        }
        return collectDTOS;
    }

    @Override
    public List<CollectDTO> contentList() {
        List<CollectDTO> collectDTOS = answerDao.contentList();
        for (CollectDTO collectDTO : collectDTOS) {
            String options = collectDTO.getOptions();
            List<String> strings = JSON.parseArray(options, String.class);
            collectDTO.setOptions("");
            collectDTO.setOptionList(strings);
        }
        return collectDTOS;
    }


    @Override
    public CollectDTO findCollection(String id) {
        CollectDTO collectDTO = answerDao.findCollection(id);
        if(collectDTO != null){
            String options = collectDTO.getOptions();
            List<String> strings = JSON.parseArray(options, String.class);
            collectDTO.setOptionList(strings);
            collectDTO.setOptions("");
        }
        return collectDTO;
    }

    @Override
    public CollectDTO findCollectsys(String id) {
        CollectDTO collectDTO = answerDao.findCollectsys(id);
        if(collectDTO != null){
            String options = collectDTO.getOptions();
            List<String> strings = JSON.parseArray(options, String.class);
            collectDTO.setOptionList(strings);
            collectDTO.setOptions("");
        }
        return collectDTO;
    }

    @Override
    public void delCollection(CollectDTO collectDTO) {
        answerDao.delCollection(collectDTO);
    }

	@Override
	public List<YearsTableAnswerDTO> getYearsTableResult(String id, String reelId, String subjectId ,String receiverId) {
		List<String> toNowYearList = DateUtil.get2019ToNowYearList(new Date());

		return answerDao.getYearsTableResult(id,reelId,subjectId ,receiverId ,toNowYearList);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public Integer updateOptions(String reelId , String subjectId , List<String> optionsId,String userId,String pageId, String receiverId,Set<String> years) {
		logger.info("=========updateOptions()参数：reelId:{},subjectId:{},optionsId:{},userId:{},pageId:{},receiverId:{},years:{}",
				reelId,subjectId,optionsId,userId,pageId,receiverId,years);
		//先删除该题目已选答案
		for (String year : years) {
			deleteSubjectAnswer(receiverId, subjectId, reelId, year);
		}

		if(!CollectionUtils.isEmpty(optionsId)){
			//再插入新的答案
			List<AnswerDTO> answerList = new ArrayList<>();
			String date = DateUtil.dateFormat(new Date());
			for (int j = 0; j < optionsId.size(); j++) {
				AnswerDTO answer = new AnswerDTO();
				answer.setId(StringUtil.get32UUID());
				answer.setSubjectId(subjectId);
				answer.setOptionsId(optionsId.get(j));
				answer.setPageId(pageId);
				answer.setUserId(userId);
				answer.setReelId(reelId);
				answer.setCreateDate(date);
				answer.setReceiverId(receiverId);
				answerList.add(answer);
			}
			Map<String,Object> map = new HashMap<>();
			int year = DateUtil.getCurrentYear();
			map.put("year", year);
			map.put("answerList", answerList);

			return answerDao.saveAnswer(map);
		}

		return 0;
	}

	@Override
	public Integer updateOptionsByReveiverId(String reelId, String subjectId, String reveiverId , List<String> optionsId) {
		//查询指定问卷某个人回答的某个题目记录，单选题一条记录，多选题可能多条记录
		List<YearsTableAnswerDTO> yearsTableResult = getYearsTableResult(null, reelId, subjectId, reveiverId);
		logger.info("========updateOptionsByReveiverId 查询问卷答案结果数目：{}",yearsTableResult.size());
		Set<String> years = new HashSet<>();
		if(!CollectionUtils.isEmpty(yearsTableResult)){
			for (int i = 0; i < yearsTableResult.size(); i++) {
				years.add(yearsTableResult.get(i).getYearStr());
			}
		}else{
			//空的话默认为当前年份
			years.add(DateUtil.timestampYearFormat(new Date()));
		}
		//从reel表取userId
		String userId = null;
		//从subject表取pageId
		String pageId = null;
		List<AllSubjectDTO> allSubjectDTOS = reelDao.queryReel(reelId);
		if(!CollectionUtils.isEmpty(allSubjectDTOS)){
			userId = allSubjectDTOS.get(0).getUserId();
		}
		SubjectDTO subjectDTO = reelDao.querySubjectId(subjectId);
		if(subjectDTO != null) pageId = subjectDTO.getPageId();

		return updateOptions(reelId,subjectId,optionsId,userId,pageId,reveiverId,years);
	}

	@Override
	public Integer deleteSubjectAnswer(String receiverId, String subjectId, String reelId, String year) {
		return answerDao.deleteSubjectAnswer(receiverId,subjectId,reelId,year);
	}

	@Override
	public List<Map<String,Object>> getQuestions(String reelId , String subjectId, String receiverid , String answerRecordId) {
		//只能查一个问卷的信息
		if(StringUtils.isBlank(reelId)) throw new BusinessException("reelAnswerRecordId不允许为空");
		////Version 1 获取指定问卷的所有受访者，无法区分同一个人答同一份问卷多次
//		List<String> receivers = new ArrayList<>();
//		if(StringUtils.isNotBlank(receiverid)){
//			receivers.add(receiverid);
//		}else{
//			//此处要求答题顺序，与/listReceivedTableData顺序一样
//			receivers = reelAnswerRecordDao.getAllReceriveidByReelId(reelId);
//		}
		////Version 2 答题记录，可以区分同一个人答同一份问卷多次
		List<ReelAnswerRecordDTO> reelAnswerRecordDTOS;
		if(StringUtils.isNotBlank(receiverid)){
			reelAnswerRecordDTOS = reelAnswerRecordDao.listReelAnswerRecord(reelId, receiverid);
		}else{
			//此处要求答题顺序，与/listReceivedTableData顺序一样
			reelAnswerRecordDTOS = reelAnswerRecordDao.listReelAnswerRecord(reelId, null);
		}
		////Version 1 存放同一问卷所有受访者的答案集合
//		List<Map<String,Object>> everyoneQuestions = new ArrayList<>(receivers.size());
		//Version 2
		List<Map<String,Object>> everyoneQuestions = new ArrayList<>(reelAnswerRecordDTOS.size());
		//Version 1
//		if(!CollectionUtils.isEmpty(receivers)){
//			for (String receiver : receivers) {
		//Version 2
		if(!CollectionUtils.isEmpty(reelAnswerRecordDTOS)){
			for (ReelAnswerRecordDTO reelAnswerRecordDTO : reelAnswerRecordDTOS) {
				List<String> yearsList = DateUtil.get2019ToNowYearList(new Date());
				//TODO 暂不支持统计所有问卷的所有答题情况，只针对单份问卷，reelId必需
				//Version 1
//				List<SubjectAnswerDTO> subjectAnswers = answerDao.getSubjectAnswer(reelId, yearsList, subjectId, receiver,answerRecordId);
				//Version 2
				List<SubjectAnswerDTO> subjectAnswers = answerDao.getSubjectAnswer(reelId, yearsList, subjectId, reelAnswerRecordDTO.getUserId(),reelAnswerRecordDTO.getId());
				//临时存储按题目分组数据,控制题目存入顺序按照查询好的顺序存放
				Map<String,List<SubjectAnswerDTO>> tempMap = new LinkedHashMap<>();
				subjectAnswers.stream().forEach(subjectAnswerDTO -> {
					String subject = subjectAnswerDTO.getSubjectid();
					if(StringUtils.isNotBlank(subject)){
						List<SubjectAnswerDTO> subjectAnswerDTOS = tempMap.get(subject);
						if(CollectionUtils.isEmpty(subjectAnswerDTOS)) subjectAnswerDTOS = new ArrayList<>();
						subjectAnswerDTOS.add(subjectAnswerDTO);
						tempMap.put(subject,subjectAnswerDTOS);
					}
				});
				List<QuestionDTO> someoneQuestions = new ArrayList<>();
				Map<String,Object> someoneAllInfo = new HashMap<>();
				tempMap.entrySet().stream().forEach(stringListEntry -> {
					QuestionDTO question = new QuestionDTO();
					for (int i = 0 ; i<stringListEntry.getValue().size() ; i++) {
						SubjectAnswerDTO subjectAnswerDTO = stringListEntry.getValue().get(i);
                        GapFillingDTO gap = new GapFillingDTO();
                        gap.setUserId(subjectAnswerDTO.getReceiverid());
                        gap.setrId(subjectAnswerDTO.getReelid());
                        gap.setRecordId(subjectAnswerDTO.getAnswerRecordId());
                        gap.setSubjectId(subjectAnswerDTO.getSubjectid());
						if(i == 0){
							question.setSubjectid(subjectAnswerDTO.getSubjectid());
                            //题填空
                            gap.setType("1");
                            //GapFillingDTO gapFillingDTO = listMyGap(gap);
                            GapFillingDTO gapFillingDTO = answerDao.listGap(gap);
                            String topic = subjectAnswerDTO.getTopic();
                            question.setTopic(topic);
                            if(gapFillingDTO!=null){
                                String content = gapFillingDTO.getContent();
                                if(StringUtils.isNotBlank(content)){
                                    List<String> strings = JSON.parseArray(content, String.class);
                                    String newtopic = StringUtil.gapText(topic,strings);
                                    question.setTopic(newtopic);
                                }
                            }
							question.setSubjectType(subjectAnswerDTO.getSubjectType());
							question.setReelid(subjectAnswerDTO.getReelid());
							question.setMustItem(subjectAnswerDTO.getMustItem());
							question.setReceiverid(subjectAnswerDTO.getReceiverid());
							question.setRemark(subjectAnswerDTO.getRemark());
						}
						if(StringUtils.isNotBlank(subjectAnswerDTO.getAnswerRecordId())) question.setAnswerRecordId(subjectAnswerDTO.getAnswerRecordId());
						List<QuestionDTO.Option> options = question.getOptions();
						if(options == null) {
							question.setOptions(new ArrayList<>());
							options = question.getOptions();
						}
						QuestionDTO.Option option = question.new Option();
                        //选项填空
                        gap.setType("2");
                        gap.setOptionsId(subjectAnswerDTO.getOptionsid());
                        GapFillingDTO gapFillingDTO2 = answerDao.listGap(gap);
                        String topic1 = subjectAnswerDTO.getOptions();
						String textAnswer = subjectAnswerDTO.getTextAnswer();
						if(StringUtils.isNotBlank(textAnswer)){
							topic1 = topic1 + textAnswer;
						}
						if("0".equals(subjectAnswerDTO.getIsMultipleChoice())){
							topic1 = "其他："+topic1;
						}
						option.setOptionsValue(topic1);
                        if(gapFillingDTO2!=null){
                            String content1 = gapFillingDTO2.getContent();
                            if(StringUtils.isNotBlank(content1)){
                                List<String> strings2 = JSON.parseArray(content1, String.class);
                                String newtopic = StringUtil.gapText(topic1,strings2);
                                option.setOptionsValue(newtopic);
                            }
                        }
						option.setOptionsid(subjectAnswerDTO.getOptionsid());
						option.setChecked(subjectAnswerDTO.getChecked());
						options.add(option);
					}
					someoneQuestions.add(question);
				});
				someoneAllInfo.put("questions",someoneQuestions);

				String startTime = "";
				String endTime = "";
				String recordId = "";
				//Version 1
//				List<ReelAnswerRecordDTO> reelAnswerRecordDTOS = null;
//				if(!CollectionUtils.isEmpty(someoneQuestions)){
//					reelAnswerRecordDTOS = reelAnswerRecordDao.listReelAnswerRecord(reelId, someoneQuestions.get(0).getReceiverid());
//				}
//				if(!CollectionUtils.isEmpty(reelAnswerRecordDTOS)){
//					startTime = reelAnswerRecordDTOS.get(0).getStartTime();
//					endTime = reelAnswerRecordDTOS.get(0).getEndTime();
//					recordId = reelAnswerRecordDTOS.get(0).getId();
//				}
				//Version 2
				if(!CollectionUtils.isEmpty(reelAnswerRecordDTOS)){
					startTime = reelAnswerRecordDTO.getStartTime();
					endTime = reelAnswerRecordDTO.getEndTime();
					recordId = reelAnswerRecordDTO.getId();
				}

				someoneAllInfo.put("answerRecordId",recordId);
				someoneAllInfo.put("startTime",startTime);
				someoneAllInfo.put("endTime",endTime);
//				String receiverName = getAnonymousReceiverId(reelId,receiver);
//				String receiverName = getAnonymousReceiverId(reelId,reelAnswerRecordDTO.getUserId());
				ReelAnswerRecordDTO record = reelAnswerRecordDao.getRecordById(reelAnswerRecordDTO.getId());
				someoneAllInfo.put("receiverName",record.getUserName() == null ? "" : record.getUserName() );
                everyoneQuestions.add(someoneAllInfo);
			}
		}

		return everyoneQuestions;
	}

	/**
	* @Description:根据问卷匿名配置和答题人id获取答题人名字
	* @Param:
	* @Return:
	* @Exception:
	*/
	public String getAnonymousReceiverId(String reelId, String receiver){
		String receiverName = "";
		AllSubjectDTO reelVo = reelService.queryByRIdReel(reelId);
		if(reelVo != null){
			if("0".equals(reelVo.getAnonymous())){
				receiverName = ANONYMOUS_STR;
			}else {
				UserDTO userDTO = userService.userFind(receiver);
				receiverName = userDTO.getNickname();
			}
		}

		return receiverName;
	}

	@Override
	public List<Map<String, Object>> getAllQuestions(String reelId, String receiverid) {
//		Map<String,Object> result = new HashMap<>();
//		List<List<QuestionDTO>> questions = getQuestions(reelId, null, receiverid ,null);
//		List<ReelAnswerRecordDTO> reelAnswerRecordDTOS = reelAnswerRecordDao.listReelAnswerRecord(reelId, receiverid);
//		String startTime = "";
//		String endTime = "";
//		if(!CollectionUtils.isEmpty(reelAnswerRecordDTOS)){
//			startTime = reelAnswerRecordDTOS.get(0).getStartTime();
//			endTime = reelAnswerRecordDTOS.get(0).getEndTime();
//		}
//		result.put("data",questions);
//		result.put("startTime",startTime);
//		result.put("endTime",endTime);

		List<Map<String, Object>> result = getQuestions(reelId, null, receiverid, null);

		return result;
	}

	
	@Override
	public void deleteAnswerOptionsId(String optionsId) {
		Map<String,Object> map = new HashMap<>();
 		List<Integer> years = DateUtil.getYearList();
 		map.put("years", years);
 		map.put("optionsId", optionsId);
 		answerDao.deleteAnswerOptionsId(map);
	}

    @Override
    public int countDeskCount(String userId) {
        return answerDao.countDeskCount(userId);
    }

    @Override
    public int countUnminelist(String userId, String rId) {
        return answerDao.countUnminelist(userId,rId);
    }

    @Override
    public void delDeskCount(String userId) {
        answerDao.delDeskCount(userId);
    }

    @Override
    public void delDeskOne(String userId, String rId) {
        answerDao.delDeskOne(userId,rId);
    }

    @Override
    public void insertDeskCount(String rId,String userId,String id) {
        answerDao.insertDeskCount(rId,userId,id);
    }

    @Override
    public void insertGap(GapFillingDTO gap) {
        answerDao.insertGap(gap);
    }

    @Override
    public void delGap(String rId) {
        answerDao.delGap(rId);
    }

	
	@Override
	public List<String> queryAnswerRecordId(String optionsId,String subjectId,String startTime,String endTime) {
		Map<String,Object> map = new HashMap<>();
		List<Integer> years = DateUtil.getYearList();
 		map.put("years", years);
 		map.put("optionsId", optionsId);
 		map.put("subjectId", subjectId);
 		map.put("subjectId", startTime);
 		map.put("subjectId", endTime);
		return answerDao.queryAnswerRecordId(map);
	}

	@Override
	public List<ReceivedDataInfo> queryoptionsId(String answerRecordId,String subjectId) {
		Map<String,Object> map = new HashMap<>();
		List<Integer> years = DateUtil.getYearList();
 		map.put("years", years);
 		map.put("answerRecordId", answerRecordId);
 		map.put("subjectId", subjectId);
		return answerDao.queryoptionsId(map);
	}

	
	@Override
	public List<String> queryNotIncludeAnswerRecordId(String optionsId, String subjectId,String startTime,String endTime) {
		Map<String,Object> map = new HashMap<>();
		List<Integer> years = DateUtil.getYearList();
 		map.put("years", years);
 		map.put("optionsId", optionsId);
 		map.put("subjectId", subjectId);
 		map.put("subjectId", startTime);
 		map.put("subjectId", endTime);
		return answerDao.queryNotIncludeAnswerRecordId(map);
	}

    /*@Override
    public List<OobjectDTO> listGap(GapFillingDTO gap) {
        return listMyGap(gap);
    }*/
    /*public GapFillingDTO listMyGap(GapFillingDTO gap){
        GapFillingDTO gapFillingDTO = answerDao.listGap(gap);
        gapFillingDTO.setContentList(JSON.parseArray(gapFillingDTO.getContent(), String.class));
        return gapFillingDTO;
    }*/


    @Override
    public void saveListenerUnmine(ListenerUnmineDTO listen) {
        answerDao.saveListenerUnmine(listen);
    }

    @Override
    public int getListercount(String uid) {
        return answerDao.getListercount(uid);
    }


    @Override
    public void delListenerUnmine(String uid) {
        answerDao.delListenerUnmine(uid);
    }
}
