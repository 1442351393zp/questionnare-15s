package com.infoac.cas.web;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.infoac.cas.dto.*;
import com.infoac.cas.entity.AnswerVo;
import com.infoac.cas.entity.Client;
import com.infoac.cas.enums.IsBooleanEnum;
import com.infoac.cas.exception.BusinessException;
import com.infoac.cas.service.*;
import com.infoac.cas.util.*;
import net.sf.json.JSONArray;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;

/**
 * @Author: cc
 * @Description: 我的填报
 * @Date: Created in  2019/11/24 9:28
 */
@CrossOrigin
@RestController
@RequestMapping("/answer")
public class AnswerController {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
    @Autowired
    private AnswerService answerService;
    
    @Autowired
    private ReelService reelService;
    @Autowired
    private UserService userService;
    @Autowired
	private IReelBrowseRecordService reelBrowseRecordService;
    @Autowired
    private IReelAnswerRecordService iReelAnswerRecordService;

    /**
	 * 打开问卷页面
	 * 
	 * @param request
	 * @return
     * @throws Exception 
	 */
	@RequestMapping(value = "/center",method = RequestMethod.GET)
	public ModelAndView AnswerCenter(HttpServletRequest request,HttpServletResponse response) throws Exception {
		int days = 0;
		int count = 0;
		int answerflag = 1;//0shi答过题,1未答过题
		String stop ="1";//0是暂停,1否
		ModelAndView mv = new ModelAndView();
		String reelId = request.getParameter("reelId");
		String userId = request.getParameter("userId");
		String receiverId = request.getParameter("receiverId");
		//查询问卷的设置
		AllSubjectDTO reelVo = reelService.queryByRIdReel(reelId);
		if(reelVo != null) {
            String reelStatus = reelVo.getReelStatus();
            if("1".equals(reelStatus)){
                //暂停
                stop="0";
            }
            reelVo.setUserId(userId);
			reelVo.setReceiverId(receiverId);
			if(IsBooleanEnum.YES_ZREO.getKey().equals(reelVo.getSetup())) {   //设置
				days = DateUtil.compDate(reelVo.getEndTime());  //1进行中        -1结束
                if(days == -1){
                    //暂停
                    stop="0";
                }
			}
			if(reelVo.getOnceChance().equals(IsBooleanEnum.YES_ZREO.getKey())) {   //设置一次答题机会
				if(days == 1 || days == 0) {    //已设置   
					count = answerService.findAnswerCount(reelId,receiverId);   //去查答题
                    if(count > 0) {
                        //da guo ti
                        answerflag=0;
                    }
                    reelVo = reelService.queryReelList(reelVo,reelId);
                }else {
					reelVo = reelService.queryReelList(reelVo,reelId);
				}
			}else {    //没设置
				//if(days != -1) {
					reelVo = reelService.queryReelList(reelVo,reelId);
				//}
			}
		}else {
			logger.info("*************问卷null*************");
		}

		//插入浏览记录
        UserDTO loginUser =null;
        try {
		    loginUser = ContextHolderUtils.getLoginUser();
        }catch (Exception e){
            logger.warn("center,is menhu....");
            loginUser = userService.findUserByuid(userId);
        }
		ReelBrowseRecordDTO browse = new ReelBrowseRecordDTO();
		try {
			browse.setId(StringUtil.get32UUID());
			browse.setUserId(loginUser != null ? loginUser.getId() : "");
			browse.setCreateOpId(loginUser != null ? loginUser.getId() : "");
			browse.setCreateOpName(loginUser != null ? loginUser.getUsername() : "");
			browse.setCreateOrgId(loginUser != null ? loginUser.getOrganid() : "");
			browse.setReelId(reelId);
			logger.info("========插入浏览记录：userId:{},reelId:{}",browse.getUserId(),reelId);
			reelBrowseRecordService.save(browse);
		} catch (Exception e) {
			logger.warn("========插入浏览记录异常：",e);
		}

		//删除新问卷,桌面消息数字
        answerService.delDeskOne(userId,reelId);

		//判断问卷是否暂停,暂停不可提交了:1字段,2过期
        mv.addObject("stop", stop);
		mv.addObject("reelVo", reelVo);
        mv.addObject("answerflag", answerflag);
        mv.setViewName("reel/fill");
		return mv;
	}
	/**
	 * 我的填报
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/mine")
	public ModelAndView AnswerMine(HttpServletRequest request,HttpServletResponse response,AnswerVo answer) throws Exception {
		ModelAndView mv = new ModelAndView();
		HttpSession session = ContextHolderUtils.getSession();
		Client client = ClientManager.getInstance().getClient(session.getId());
		UserDTO user = client.getUser();
		if(user != null) {
			mv.addObject("userid", user.getId());
			mv.addObject("isManager", user.getIsManager());
			mv.setViewName("reel/answerList");
		}else {
			logger.info("----------用户信息为空----------");
		}
//		String userId = request.getParameter("userId");
		return mv;
	}
	/**
	 * 我的填报    加载数据
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/minelist",method = RequestMethod.POST,produces= {"application/json;charset=UTF-8"})
	public String AnswerMineList(HttpServletRequest request,HttpServletResponse response,AnswerVo answer) throws Exception {
		JSONObject json = new JSONObject();
		HttpSession session = ContextHolderUtils.getSession();
		Client client = ClientManager.getInstance().getClient(session.getId());
		UserDTO user = client.getUser();
		if(user != null) {
			//查询问卷的设置
			answer.setUserId(user.getId());
			//answer.setYear("2019");
			Long count = answerService.findAnswerCountByUser(answer);   //去查答题
			List<AnswerUserDTO> list = answerService.findAnswerListByUser(answer);
            //int count = list.size();
			JSONArray jsonArray = JSONArray.fromObject(list);
			json.put("code", Globals.SUCCESSE);
			json.put("message",  Globals.MSG_CZ_SUCCESSE);
			json.put("data", jsonArray);
			json.put("count", count);
			return json.toString();
		}else {
			logger.info("----------用户信息为空----------");
			json.put("code", Globals.FIAL);
			json.put("message",  Globals.MSG_CZ_FAIL);
			return json.toString();
		}
	}


    /**
     * 未答问卷刷新
     */
    @RequestMapping(value = "/unmine")
    public ModelAndView unmine(HttpServletRequest request,HttpServletResponse response,AnswerVo answer) throws Exception {
        ModelAndView mv = new ModelAndView();
        HttpSession session = ContextHolderUtils.getSession();
        Client client = ClientManager.getInstance().getClient(session.getId());
        UserDTO user = client.getUser();
        if(user != null) {
            mv.addObject("isManager", user.getIsManager());
            mv.addObject("userid", user.getId());
            mv.setViewName("reel/unanswerList");
        }else {
            logger.info("----------unmine用户信息为空----------");
        }
//		String userId = request.getParameter("userId");
        return mv;
    }

    /**
     * 普通用户展示未答问卷
     *
     */
    @RequestMapping(value = "/unminelist",method = RequestMethod.POST,produces= {"application/json;charset=UTF-8"})
    public String unminelist(HttpServletRequest request,HttpServletResponse response,AnswerVo answer) throws Exception {
        JSONObject json = new JSONObject();
        HttpSession session = ContextHolderUtils.getSession();
        Client client = ClientManager.getInstance().getClient(session.getId());
        UserDTO user = client.getUser();
        if(user != null) {
            //查询问卷是否是只能答一次
            //Long aLong = answerService.findunAnswerCountByUser(answer);
            //List<AllSubjectDTO> allSubjectDTO = answerService.findunAnswerListByUser(answer);
            //全部问卷
            String status = "0";
            String year = answer.getYear();
            List<AllSubjectDTO> allSubjectDTO = reelService.queryAllReel(status,year);
            List<AllSubjectDTO> allSubjectDTO2 = new ArrayList<AllSubjectDTO>();
            if(!allSubjectDTO.isEmpty()) {
                for(int j=0;j<allSubjectDTO.size();j++) {
                    //排除限制用户答题的数据
                    String rId = allSubjectDTO.get(j).getrId();
                    String onceChance = allSubjectDTO.get(j).getOnceChance();
                    String isLimit = allSubjectDTO.get(j).getIsLimit();
                    if("0".equals(isLimit)){
                        List<SelectUserDTO> selectUser = userService.getSelectUser(rId);
                        for (SelectUserDTO selectUserDTO : selectUser) {
                            String userId = selectUserDTO.getUserId();
                            if(userId.equals(user.getId())){
                                //是否是只能答一次
                                if("0".equals(onceChance)){
                                    //查答题记录表
                                    int count = iReelAnswerRecordService.countReelAnswerRecord(rId,userId);
                                    if(count>0){
                                        break;
                                    }
                                }
                                List<PageDTO> pageDTO = reelService.queryPage(rId);
                                allSubjectDTO.get(j).setPageList(pageDTO);
                                allSubjectDTO.get(j).setReceiverId(user.getId());
                                allSubjectDTO2.add(allSubjectDTO.get(j));
                                break;
                            }
                        }
                    }else {
                        //是否是只能答一次
                        if("0".equals(onceChance)){
                            //查答题记录表
                            int count = iReelAnswerRecordService.countReelAnswerRecord(rId,user.getId());
                            if(count>0){
                                continue;
                            }
                        }
                        List<PageDTO> pageDTO = reelService.queryPage(rId);
                        allSubjectDTO.get(j).setPageList(pageDTO);
                        allSubjectDTO.get(j).setReceiverId(user.getId());
                        allSubjectDTO2.add(allSubjectDTO.get(j));
                    }
                }
            }
            List<AllSubjectDTO> newallSubjectDTOS = listPage(allSubjectDTO2,answer.getPage(),answer.getRecPerPage());
            for (AllSubjectDTO newdeskpic : newallSubjectDTOS) {
                String nuid = user.getId();
                String nrid = newdeskpic.getrId();
                int i = answerService.countUnminelist(nuid, nrid);
                if(i>0){
                    newdeskpic.setNewRellStatus("0");
                }else {
                    newdeskpic.setNewRellStatus("1");
                }
            }
            JSONArray jsonArray = JSONArray.fromObject(newallSubjectDTOS);
            json.put("code", Globals.SUCCESSE);
            json.put("message",  Globals.MSG_CZ_SUCCESSE);
            json.put("data", jsonArray);
            json.put("count", allSubjectDTO2.size());
            //刪除未查看問卷數字
            answerService.delDeskCount(user.getId());
            //桌面数字角标刷新
//            boolean b = false;
//            try {
//                b = sendMsgDesk(user.getId(),user.getId());
//            } catch (Exception e) {
//                logger.error("桌面数字角标刷新,错误");
//            }
//            logger.info("桌面数字角标刷新:{}",b);
            return json.toString();
        }else {
            logger.info("----------用户信息为空----------");
            json.put("code", Globals.FIAL);
            json.put("message",  Globals.MSG_CZ_FAIL);
            return json.toString();
        }
    }



    private boolean sendMsgDesk(String receiverId, String senderId) {
        String url = ProjectConfUtils.getProjectConf("ssourl");
        String token = getAccessToken(url);
        if(!"".equals(token) && token != null) {
            String appId = ProjectConfUtils.getProjectConf("client_id");
            JSONObject json = new JSONObject();
            json.put("type", "LINK");
            json.put("title", "消息更新");
            json.put("content", "消息更新");
            json.put("appid", appId);
            json.put("receiver", receiverId);
            json.put("sendfrom", senderId);
            json.put("redirect", "");
            json.put("operations", "[]");
            json.put("visible", "false");
            byte[] bytes = json.toJSONString().getBytes();
            String encodeds = Base64.getEncoder().encodeToString(bytes);
            String encoded = encodeds.replaceAll("/", "_");
            String msgUrl = url + "api/msg/messageCenter/send/messageBox/WJDC/"+receiverId+"/"+encoded+"?access_token="+token;

            String result = HttpClientUtil.getInstance().sendHttpPost(msgUrl);
            if("TOPIC_AND_TAG_NOT_REGIST".equals(result)) {
                logger.info("(桌面角标当前主题和标签未注册)------------需要新增消息订阅信息");
                return false;
            }else if("SEND_FAILED".equals(result)){
                logger.info("-----------"+receiverId+"-----------桌面角标发送失败");
                return false;
            }else {
                logger.info("-----------"+receiverId+"-----------桌面角标发送成功");
                return true;
            }
        }else {
            return false;
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

    public static List<AllSubjectDTO> listPage(List<AllSubjectDTO> pageList, int page, int count){
        int size = pageList.size();
        int pageCount = size/count;
        int fromIndex = count * (page - 1);
        int toIndex = fromIndex + count;
        if (toIndex>=size){
            toIndex=size;
        }
        if (page>pageCount+1){
            fromIndex=0;
            toIndex=0;
        }
        return pageList.subList(fromIndex,toIndex);
    }


    /**
     * @Author: cc
     * @Description: 保存答案
     * @Date: Created in 2019/8/27
     */
    @RequestMapping(value = "/saveanswer",produces= {"application/josn;charset=UTF-8"})
    @ResponseBody
    public String saveAnswer(HttpServletRequest request,AnswerDTO answerVo){
    	JSONObject json = new JSONObject();
    	try {
			if(StringUtils.isBlank(answerVo.getTimeConsuming())){
				throw new BusinessException("timeConsuming不允许为空");
			}
			if(StringUtils.isBlank(answerVo.getStartTime())){
				throw new BusinessException("startTime不允许为空");
			}
			if(StringUtils.isBlank(answerVo.getEndTime())){
				throw new BusinessException("endTime不允许为空");
			}
			logger.info("saveanswer,answ:{}",JSON.toJSONString(answerVo));
            //判断是否只能填写一次,是否填写过,前端置灰,后端再判断
            //查询问卷的设置
            boolean flag = true;
            String reelId = answerVo.getReelId();
            String receiverId = answerVo.getReceiverId();
            AllSubjectDTO reelVo = reelService.queryByRIdReel(reelId);
            if(IsBooleanEnum.YES_ZREO.getKey().equals(reelVo.getOnceChance())) {   //设置一次答题机会
                    int count = answerService.findAnswerCount(reelId,receiverId);   //去查答题
                    if(count > 0) {
                        flag=false;
                        logger.info("一次答题机会,填写过...");
                    }
            }
            if(flag){
                String oobjectstr = answerVo.getOobjectstr();
                answerVo.setOobjects(JSON.parseArray(oobjectstr, OobjectDTO.class));
                answerService.saveAnswer(answerVo);
            }
            //存入监听数据
            //ListenerUnmineDTO listenerUnmineDTO = new ListenerUnmineDTO();
            //listenerUnmineDTO.setRid(reelId);
            //listenerUnmineDTO.setUserid(receiverId);
            //answerService.saveListenerUnmine(listenerUnmineDTO);
            json.put("code", Globals.SUCCESSE);
    		json.put("msg", Globals.MSG_XG_SUCCESSE);
		} catch (Exception e) {
    		logger.error("=========保存答案异常：",e);
			json.put("code", Globals.FIAL);
			json.put("code", Globals.MSG_XG_FAIL);
		}
    	return json.toString();
    }
    /**
     * @Author: cc
     * @Description: 我的某一问卷
     * @Date: Created in 2019/12/4
     */
    @RequestMapping(value = "/someone")
    public ModelAndView toSomeone(HttpServletRequest request,String reelId,String userId,String year,String recordId){
    	ModelAndView mv = new ModelAndView();
    	//查询问卷的设置
    	//year = "2019";
    	AllSubjectDTO reelVo = reelService.queryByRIdReel(reelId);
    	reelVo = reelService.queryReelListRecord(reelVo,reelId,recordId,userId);
    	List<AnswerDTO> answerList = answerService.findAnswersByUser(reelId,userId,year,recordId);
     	//填空题
        GapFillingDTO gap = new GapFillingDTO();
        gap.setrId(reelId);
        gap.setUserId(userId);
        //List<OobjectDTO> oobjects = answerService.listGap(gap);
        //mv.addObject("oobjects", oobjects);
		mv.addObject("reelVo", reelVo);
		mv.addObject("answerList", answerList);
		mv.setViewName("reel/someone");
		return mv;
    }

    /**
     * @Author: PengSenjie
     * @description: 收藏题目
     * @Date: Created in 19-12-20 下午2:22
     */
    @PostMapping("/collect")
    public BaseResult collect(@RequestBody CollectDTO collectDTO) {
        long timeStart = System.currentTimeMillis();
        String json = JSON.toJSONString(collectDTO);
        logger.info("collect:{}", json);
        BaseResult baseResult = new BaseResult();
        try {
            collectDTO.setMust("0");//是否必填 0是  1否
            String id =answerService.saveCollect(collectDTO);
            baseResult.setCode(Globals.SUCCESSE);
            baseResult.setMsg(Globals.MSG_XG_SUCCESSE);
            baseResult.setData(id);
        } catch (Exception e) {
            logger.error("==========collect 异常：",e);
            baseResult.setCode(Globals.FIAL);
            baseResult.setMsg(Globals.MSG_XG_FAIL);
        }
        long timeEnd = System.currentTimeMillis();
        logger.info("collect耗时{}毫秒", timeEnd - timeStart);
        return baseResult;
    }

    /**
     * @Author: PengSenjie
     * @description: 查询收藏题目列表
     * @Date: Created in 19-12-20 下午2:22
     */
    @PostMapping("/collectionList")
    public CollectAllDTO collectionList(@RequestBody CollectDTO collectDTO) {
        long timeStart = System.currentTimeMillis();
        String json = JSON.toJSONString(collectDTO);
        logger.info("collectionList:{}", json);
        CollectAllDTO collectAllDTO = new CollectAllDTO();
        try {
            String userId = collectDTO.getUserId();
            List<CollectDTO> collectDTOS = answerService.collectList(userId);
            logger.info("collectionList,return:{}", JSON.toJSONString(collectDTOS));
            collectAllDTO.setCollectList(collectDTOS);
            //默认值
            List<CollectDTO> contentList = answerService.contentList();
            collectAllDTO.setContentList(contentList);
            collectAllDTO.setCode(Globals.SUCCESSE);
            collectAllDTO.setMsg(Globals.MSG_XG_SUCCESSE);
        } catch (Exception e) {
            collectAllDTO.setCode(Globals.FIAL);
            collectAllDTO.setMsg(Globals.MSG_XG_FAIL);
        }
        long timeEnd = System.currentTimeMillis();
        logger.info("collectionList耗时{}毫秒", timeEnd - timeStart);
        return collectAllDTO;
    }

    /**
     * @Author: PengSenjie
     * @description: 查找题目
     * @Date: Created in 19-12-23 上午9:59
     */
    @PostMapping("/findCollection")
    public BaseResult findCollection(@RequestBody CollectDTO collectDTO) {
        long timeStart = System.currentTimeMillis();
        String id = collectDTO.getId();
        String type = collectDTO.getType();
        logger.info("findCollection:{}", id);
        BaseResult baseResult = new BaseResult();
        CollectDTO collectDTOS = new CollectDTO();
        try {
            if("0".equals(type) || "1".equals(type)){
                collectDTOS = answerService.findCollectsys(id);
            }else {
                collectDTOS = answerService.findCollection(id);
            }
            logger.info("findCollection,return:{}", JSON.toJSONString(collectDTOS));
            baseResult.setData(collectDTOS);
            baseResult.setCode(Globals.SUCCESSE);
            baseResult.setMsg(Globals.MSG_XG_SUCCESSE);
        } catch (Exception e) {
            baseResult.setCode(Globals.FIAL);
            baseResult.setMsg(Globals.MSG_XG_FAIL);
        }
        long timeEnd = System.currentTimeMillis();
        logger.info("findCollection耗时{}毫秒", timeEnd - timeStart);
        return baseResult;
    }


    /**
     * @Author: PengSenjie
     * @description: 删除收藏题目
     * @Date: Created in 20-3-2 上午10:47
     */
    @PostMapping("/delCollection")
    public BaseResult delCollection(@RequestBody CollectDTO collectDTO) {
        long timeStart = System.currentTimeMillis();
        logger.info("delCollection:{}", JSON.toJSONString(collectDTO));
        BaseResult baseResult = new BaseResult();
        try {
            answerService.delCollection(collectDTO);
            baseResult.setCode(Globals.SUCCESSE);
            baseResult.setMsg(Globals.MSG_XG_SUCCESSE);
        } catch (Exception e) {
            baseResult.setCode(Globals.FIAL);
            baseResult.setMsg(Globals.MSG_XG_FAIL);
        }
        long timeEnd = System.currentTimeMillis();
        logger.info("delCollection耗时{}毫秒", timeEnd - timeStart);
        return baseResult;
    }

	/**
	 * @Description:“回收数据”页面修改答案回显题目数据
	 * @Param: [reelId 问卷id, subjectId 题目id, reveiverId 受访者id , optionsId  修改后的选项, answerRecordId,答题记录编号（允许同一个人回答多次时用来区分具体某一次，修改时获取数据使用）]
	 * @Return: org.springframework.web.servlet.ModelAndView
	 * @Exception:
	 */
	@RequestMapping("/getSubjectAnswer")
	public BaseResult getSubjectAnswer(String reelId , String subjectId, String receiverid ,String answerRecordId){
		BaseResult json = new BaseResult();
		List<Map<String,Object>> questions = null;
		try {
			if(StringUtils.isBlank(reelId)){
				throw new BusinessException("reelId不允许为空");
			}
//			if(StringUtils.isBlank(subjectId)){
//				throw new BusinessException("subjectId不允许为空");
//			}
//			if(StringUtils.isBlank(receiverid)){
//				throw new BusinessException("receiverId不允许为空");
//			}
			json.setCode(Globals.SUCCESSE);
			json.setMsg(Globals.MSG_CZ_SUCCESSE);
			questions = answerService.getQuestions(reelId, subjectId, receiverid,answerRecordId);
			json.setData(questions);
		} catch (Exception e) {
			json.setCode( Globals.FIAL);
			json.setMsg(Globals.MSG_CZ_FAIL);
			logger.error("=========getSubjectAnswer()异常：",e);
		}

		return json;
	}


	/**
	 * @Description:“回收数据”页面修改某个人某个问卷某个题目的选项答案
	 * @Param: [reelId 问卷id, subjectId 题目id, reveiverId 受访者id , optionsId  修改后的选项]
	 * @Return: org.springframework.web.servlet.ModelAndView
	 * @Exception:
	 */
	@RequestMapping("/updateOptions")
	public BaseResult updateOptions(@RequestBody UpdateOptionsParams params){
		BaseResult json = new BaseResult();
		Integer result = 0;
		try {
			if(StringUtils.isBlank(params.getReelId())){
				throw new BusinessException("reelId不允许为空");
			}
			if(StringUtils.isBlank(params.getSubjectId())){
				throw new BusinessException("subjectId不允许为空");
			}
			if(StringUtils.isBlank(params.getReceiverId())){
				throw new BusinessException("receiverId不允许为空");
			}
			//可以修改为什么都不选
//			if(CollectionUtils.isEmpty(params.getOptionsId())){
//				throw new BusinessException("optionsId不允许为空");
//			}
			json.setCode(Globals.SUCCESSE);
			json.setMsg( Globals.MSG_XG_SUCCESSE);
			result = answerService.updateOptionsByReveiverId(params.getReelId(), params.getSubjectId(), params.getReceiverId(),params.getOptionsId());
			json.setData(result);
		} catch (Exception e) {
			json.setCode( Globals.FIAL);
			json.setMsg( Globals.MSG_XG_FAIL);
			logger.error("=========receiveLineChart()异常：",e);
		}

		return json;
	}

	/**
	 * @Description:“回收数据”页面查看问卷题目和其他信息数据
	 * @Param: [reelId 问卷id, subjectId 题目id, reveiverId 受访者id , optionsId  修改后的选项]
	 * @Return: org.springframework.web.servlet.ModelAndView
	 * @Exception:
	 */
	@RequestMapping("/getAllQuestions")
	public BaseResult getAllQuestions(String reelId, String receiverid){
		List<Map<String, Object>> allQuestions = null;
		BaseResult json = new BaseResult();
		try {
			if(StringUtils.isBlank(reelId)){
				throw new BusinessException("reelId不允许为空");
			}
			//不传查所有人的答题情况
//			if(StringUtils.isBlank(receiverid)){
//				throw new BusinessException("receiverId不允许为空");
//			}
			json.setCode( Globals.SUCCESSE);
			json.setMsg( Globals.MSG_CZ_SUCCESSE);
			allQuestions = answerService.getAllQuestions(reelId, receiverid);
			json.setData(allQuestions);
		} catch (Exception e) {
			json.setCode( Globals.FIAL);
			json.setMsg( Globals.MSG_CZ_FAIL);
			logger.error("=========getAllQuestions()异常：",e);
		}

		return json;
	}

	/**
	 * @Description:“回收数据”页面
	 * @Param:
	 * @Return: org.springframework.web.servlet.ModelAndView
	 * @Exception:
	 */
	@RequestMapping("/getQuestionPage")
	public ModelAndView getQuestionPage(HttpServletRequest request){
		ModelAndView mv = new ModelAndView();
		mv.addObject("requset",request);
		mv.setViewName("reel/count-answer");

		return mv;
	}



    /**
     * @Author: PengSenjie
     * @description: 监听用户未答列表
     * @Date: Created in 20-4-2 下午1:49
     */
    /*@GetMapping(value = "/htmltime")
    @ResponseBody
    public String htmltime (HttpServletRequest request){
        String flag = "false";
        String uid =request.getParameter("uid");
        try {
            int count = answerService.getListercount(uid);
            if(count>0){
                logger.info("刷新未答列表页面....");
                flag="true";
                answerService.delListenerUnmine(uid);
                logger.info("htmltime:"+flag+"-----------------------------");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return flag;
    }*/



    /**
     * 提供给首页的问卷待办事项
     *
     */
    @RequestMapping(value = "/unminelistPage",method = RequestMethod.GET)
    @ResponseBody
    public JSONArray unminelistPage(HttpServletRequest request,HttpServletResponse response) throws Exception {
        JSONArray jsonArray = new JSONArray();
        String userid = request.getParameter("userid");
        if(StringUtils.isNotBlank(userid)) {
            //查询问卷是否是只能答一次
            //Long aLong = answerService.findunAnswerCountByUser(answer);
            //List<AllSubjectDTO> allSubjectDTO = answerService.findunAnswerListByUser(answer);
            //全部问卷
            String status = "0";
            String year = String.valueOf(LocalDate.now().getYear());
            List<AllSubjectDTO> allSubjectDTO = reelService.queryAllReel(status,year);
            List<AllSubjectDTO> allSubjectDTO2 = new ArrayList<AllSubjectDTO>();
            if(!allSubjectDTO.isEmpty()) {
                for(int j=0;j<allSubjectDTO.size();j++) {
                    //排除限制用户答题的数据
                    String rId = allSubjectDTO.get(j).getrId();
                    String onceChance = allSubjectDTO.get(j).getOnceChance();
                    String isLimit = allSubjectDTO.get(j).getIsLimit();
                    if("0".equals(isLimit)){
                        List<SelectUserDTO> selectUser = userService.getSelectUser(rId);
                        for (SelectUserDTO selectUserDTO : selectUser) {
                            String userId = selectUserDTO.getUserId();
                            if(userId.equals(userid)){
                                //是否是只能答一次
                                if("0".equals(onceChance)){
                                    //查答题记录表
                                    int count = iReelAnswerRecordService.countReelAnswerRecord(rId,userId);
                                    if(count>0){
                                        break;
                                    }
                                }
                                List<PageDTO> pageDTO = reelService.queryPage(rId);
                                allSubjectDTO.get(j).setPageList(pageDTO);
                                allSubjectDTO.get(j).setReceiverId(userid);
                                allSubjectDTO2.add(allSubjectDTO.get(j));
                                break;
                            }
                        }
                    }else {
                        //是否是只能答一次
                        if("0".equals(onceChance)){
                            //查答题记录表
                            int count = iReelAnswerRecordService.countReelAnswerRecord(rId,userid);
                            if(count>0){
                                continue;
                            }
                        }
                        List<PageDTO> pageDTO = reelService.queryPage(rId);
                        allSubjectDTO.get(j).setPageList(pageDTO);
                        allSubjectDTO.get(j).setReceiverId(userid);
                        allSubjectDTO2.add(allSubjectDTO.get(j));
                    }
                }
            }
            List<AllSubjectDTO> newallSubjectDTOS = listPage(allSubjectDTO2,1,4);
            for (AllSubjectDTO newdeskpic : newallSubjectDTOS) {
                //组装新数据结构
                JSONObject jsonObject = new JSONObject();
                //标题
                String title = newdeskpic.getTitle();
                jsonObject.put("title","【问卷】"+title);
                //单点地址
                String url = ProjectConfUtils.getProjectConf("tbzx")+"?reelId="+newdeskpic.getrId()+"&userId="+newdeskpic.getUserId()+"&receiverId="+userid;
                jsonObject.put("url",url);
                //时间
                String createTime = newdeskpic.getCreateTime();
                logger.info("createTime:"+createTime);
                logger.info("createTime substring(0,10):"+createTime.substring(0,10));
                jsonObject.put("sqrq",createTime.substring(0,10));
                //发起人
                String createPeo = newdeskpic.getCreatePeo();
                jsonObject.put("sqr",createPeo);
                jsonArray.add(jsonObject);
            }
            return jsonArray;
        }else {
            logger.error("----------unminelistPage is null----------");
            return jsonArray;
        }
    }

}
