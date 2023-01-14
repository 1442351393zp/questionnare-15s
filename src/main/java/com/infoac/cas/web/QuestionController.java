package com.infoac.cas.web;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.infoac.cas.dto.*;
import com.infoac.cas.entity.Client;
import com.infoac.cas.entity.MemberResult;
import com.infoac.cas.exception.BusinessException;
import com.infoac.cas.service.IReelAnswerRecordService;
import com.infoac.cas.service.QuestionService;
import com.infoac.cas.service.ReelService;
import com.infoac.cas.util.*;
import com.infoac.cas.util.Log;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: cc
 * @Description: 我的问卷
 * @Date: Created in  2019/7/4 9:28
 */
@CrossOrigin
@RestController
@RequestMapping("/question")
public class QuestionController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private QuestionService questionService;
	@Autowired
	private ReelService reelService;
	@Autowired
	private IReelAnswerRecordService reelAnswerRecordService;

	/**
	 * @Author: cc
	 * @Description: 开始调查/手动暂停
	 * @Date: Created in 2019/8/27
	 */
	@RequestMapping(value = "/startresearch", produces = {"application/josn;charset=UTF-8"})
	@ResponseBody
	public String startResearch(HttpServletRequest request, ReelDTO reel) {
		JSONObject json = new JSONObject();
		try {
			logger.info("---------开始调查/手动暂停---------");
			String type = reel.getReelStatus();
			if (StringUtils.isNotBlank(type)) {
				questionService.startResearch(reel);
			}
			json.put("code", Globals.SUCCESSE);
			json.put("msg", Globals.MSG_XG_SUCCESSE);
			json.put("reelStatus", type);
    		/*if(type.equals(Globals.START_ONE)) {    //给相反的值
    			logger.info("---------手动暂停---------" + Globals.STOP_ONE);
    			json.put("reelStatus", Globals.STOP_ONE);
    		}else {
    			logger.info("---------开始调查---------" + Globals.START_ONE);
    			json.put("reelStatus", Globals.START_ONE);
    		}*/
		} catch (Exception e) {
			json.put("code", Globals.FIAL_NO_SUBJECT);
			json.put("msg", "此问卷未创建题目，不可调查操作");
		}
		return json.toString();
	}

	/**
	 * @Author: cc
	 * @Description: 手动暂停
	 * @Date: Created in 2019/8/27
	 */
	@RequestMapping(value = "/stopresearch", produces = {"application/josn;charset=UTF-8"})
	@ResponseBody
	public String stopResearch(HttpServletRequest request, ReelDTO reel) {
		JSONObject json = new JSONObject();
		try {
			questionService.stopResearch(reel);
			json.put("code", Globals.SUCCESSE);
			json.put("msg", Globals.MSG_XG_SUCCESSE);
		} catch (Exception e) {
			json.put("code", Globals.FIAL);
			json.put("msg", Globals.MSG_XG_FAIL);
		}
		return json.toString();
	}

	/**
	 * @Author: cc
	 * @Description: 设置页面
	 * @Date: Created in 2019/8/27
	 */
	@RequestMapping(value = "/tosetup")
	public ModelAndView toSetup(HttpServletRequest request, ReelDTO reel) {
		ModelAndView mv = new ModelAndView();
		//回显
		ReelDTO reelSetup = questionService.findReelSetup(reel.getrId());
		String setup = reelSetup.getSetup();
		if ("1".equals(setup)) {
			reelSetup.setEndTime("");
		}
		mv.addObject("reel", reelSetup);
		mv.setViewName("reel/setup");
		return mv;
	}

	/**
	 * @Author: cc
	 * @Description: 显示问题编号
	 * @Date: Created in 2019/8/27
	 */
	@RequestMapping(value = "/showno", produces = {"application/josn;charset=UTF-8"})
	@ResponseBody
	public String showAnswerNo(HttpServletRequest request, String showNo, String rId) {
		JSONObject json = new JSONObject();
		try {
			ReelDTO reel = new ReelDTO();
			reel.setrId(rId);
			reel.setShowNo(showNo);
			questionService.updateReel(reel);
			json.put("code", Globals.SUCCESSE);
			json.put("msg", Globals.MSG_XG_SUCCESSE);
		} catch (Exception e) {
			json.put("code", Globals.FIAL);
			json.put("msg", Globals.MSG_XG_FAIL);
		}
		return json.toString();
	}

	/**
	 * @Author: cc
	 * @Description: 设置勾选渠道
	 * @Date: Created in 2020/2/25
	 */
	@RequestMapping(value = "/updatecanaltext", produces = {"application/josn;charset=UTF-8"})
	@ResponseBody
	public String updateCanalText(String rId, String flag) {
		JSONObject json = new JSONObject();
		try {

			if ("d".equals(flag)) {
				//清空渠道描述
				questionService.updateReelCanalText(rId);
			} else {
				try {
					Thread.sleep(200);
				} catch (Exception e) {
					e.printStackTrace();
				}
				//重新修改渠道描述
				//查询渠道所有勾选信息,组装成中文描述
				StringBuffer canalTextb = new StringBuffer();
				ReelDTO reelSetup = questionService.findReelSetup(rId);
				String canalOnline = reelSetup.getCanalOnline();
				String canalEmail = reelSetup.getCanalEmail();
				String canalMsn = reelSetup.getCanalMsn();
				if ("0".equals(canalOnline)) {
					canalTextb.append("在线,");
				}
				if ("0".equals(canalEmail)) {
					canalTextb.append("邮件,");
				}
				if ("0".equals(canalMsn)) {
					canalTextb.append("即时通讯,");
				}
				if (canalTextb.length() > 0) {
					String s = canalTextb.toString();
					String canalText = s.substring(0, s.length() - 1);
					ReelDTO reel = new ReelDTO();
					reel.setrId(rId);
					reel.setCanalText(canalText);
					questionService.updateReel(reel);
				} else {
					//清空渠道描述
					questionService.updateReelCanalText(rId);
				}
			}
			json.put("code", Globals.SUCCESSE);
			json.put("msg", Globals.MSG_XG_SUCCESSE);
		} catch (Exception e) {
			json.put("code", Globals.FIAL);
			json.put("msg", Globals.MSG_XG_FAIL);
		}
		return json.toString();
	}

	/**
	 * @Author: cc
	 * @Description: 设置勾选渠道
	 * @Date: Created in 2020/2/25
	 */
	@RequestMapping(value = "/setcanal", produces = {"application/josn;charset=UTF-8"})
	@ResponseBody
	public String setCanal(String canal, String rId, String flag) {
		JSONObject json = new JSONObject();
		try {
			ReelDTO reel = new ReelDTO();
			reel.setrId(rId);
			switch (flag) {
				case "0":
					reel.setCanal(canal);
					break;
				case "1":
					reel.setCanalOnline(canal);
					break;
				case "2":
					reel.setCanalEmail(canal);
					break;
				case "3":
					reel.setCanalMsn(canal);
					break;
				default:
					logger.warn("flag is no use...");

			}
			questionService.updateReel(reel);
			json.put("code", Globals.SUCCESSE);
			json.put("msg", Globals.MSG_XG_SUCCESSE);
		} catch (Exception e) {
			json.put("code", Globals.FIAL);
			json.put("msg", Globals.MSG_XG_FAIL);
		}
		return json.toString();
	}

	/**
	 * @Author: cc
	 * @Description: 匿名
	 * @Date: Created in 2019/8/27
	 */
	@RequestMapping(value = "/anonymous", produces = {"application/josn;charset=UTF-8"})
	@ResponseBody
	public String isAnonymous(HttpServletRequest request, String anonymous, String rId) {
		JSONObject json = new JSONObject();
		try {
			ReelDTO reel = new ReelDTO();
			reel.setrId(rId);
			reel.setAnonymous(anonymous);
			questionService.updateReel(reel);
			json.put("code", Globals.SUCCESSE);
			json.put("msg", Globals.MSG_XG_SUCCESSE);
		} catch (Exception e) {
			json.put("code", Globals.FIAL);
			json.put("msg", Globals.MSG_XG_FAIL);
		}
		return json.toString();
	}

	/**
	 * @Author: cc
	 * @Description: 发起
	 * @Date: Created in 2019/8/27
	 */
	@RequestMapping(value = "/isinitiate", produces = {"application/josn;charset=UTF-8"})
	@ResponseBody
	public String isInitiate(HttpServletRequest request, String initiate, String rId) {
		JSONObject json = new JSONObject();
		try {
			ReelDTO reel = new ReelDTO();
			reel.setrId(rId);
			reel.setInitiate(initiate);
			questionService.updateReel(reel);
			json.put("code", Globals.SUCCESSE);
			json.put("msg", Globals.MSG_XG_SUCCESSE);
		} catch (Exception e) {
			json.put("code", Globals.FIAL);
			json.put("msg", Globals.MSG_XG_FAIL);
		}
		return json.toString();
	}

	/**
	 * @Author: cc
	 * @Description: 收回
	 * @Date: Created in 2019/8/27
	 */
	@RequestMapping(value = "/isretrieve", produces = {"application/josn;charset=UTF-8"})
	@ResponseBody
	public String isRetrieve(HttpServletRequest request, String retrieve, String rId) {
		JSONObject json = new JSONObject();
		try {
			ReelDTO reel = new ReelDTO();
			reel.setrId(rId);
			reel.setRetrieve(retrieve);
			questionService.updateReel(reel);
			json.put("code", Globals.SUCCESSE);
			json.put("msg", Globals.MSG_XG_SUCCESSE);
		} catch (Exception e) {
			json.put("code", Globals.FIAL);
			json.put("msg", Globals.MSG_XG_FAIL);
		}
		return json.toString();
	}

	/**
	 * @Author: cc
	 * @Description: 参与人员, 取消按钮调用
	 * @Date: Created in 2019/8/27
	 */
	@RequestMapping(value = "/setupuser", produces = {"application/josn;charset=UTF-8"})
	@ResponseBody
	public String setUpUser(HttpServletRequest request, String limit, String rId) {
		JSONObject json = new JSONObject();
		try {
			ReelDTO reel = new ReelDTO();
			reel.setrId(rId);
			reel.setIsLimit("1");
			questionService.updateReel(reel);
			json.put("code", Globals.SUCCESSE);
			json.put("msg", Globals.MSG_XG_SUCCESSE);
		} catch (Exception e) {
			json.put("code", Globals.FIAL);
			json.put("msg", Globals.MSG_XG_FAIL);
		}
		return json.toString();
	}

	/**
	 * @Author: cc
	 * @Description: 一次机会
	 * @Date: Created in 2019/8/27
	 */
	@RequestMapping(value = "/oncechance", produces = {"application/josn;charset=UTF-8"})
	@ResponseBody
	public String onceChance(HttpServletRequest request, String onceChance, String rId) {
		JSONObject json = new JSONObject();
		try {
			ReelDTO reel = new ReelDTO();
			reel.setrId(rId);
			reel.setOnceChance(onceChance);
			questionService.updateReel(reel);
			json.put("code", Globals.SUCCESSE);
			json.put("msg", Globals.MSG_XG_SUCCESSE);
		} catch (Exception e) {
			json.put("code", Globals.FIAL);
			json.put("msg", Globals.MSG_XG_FAIL);
		}
		return json.toString();
	}

	/**
	 * @Author: cc
	 * @Description: 设置结束时间
	 * @Date: Created in 2019/8/27
	 */
	@RequestMapping(value = "/setendtime", produces = {"application/josn;charset=UTF-8"})
	@ResponseBody
	public String setEndTime(HttpServletRequest request, ReelDTO reel) {
		JSONObject json = new JSONObject();
		try {
//    		ReelDTO reel = new ReelDTO();
//    		reel.setrId(rId);
//    		reel.setSetup(setup);
//    		reel.setEndTime(endTime);
			questionService.updateReel(reel);
			json.put("code", Globals.SUCCESSE);
			json.put("msg", Globals.MSG_XG_SUCCESSE);
		} catch (Exception e) {
			json.put("code", Globals.FIAL);
			json.put("msg", Globals.MSG_XG_FAIL);
		}
		return json.toString();
	}

	/**
	 * @Author: cc
	 * @Description: 预览
	 * @Date: Created in 2019/12/4
	 */
	@RequestMapping(value = "/preview")
	public ModelAndView toPreview(HttpServletRequest request, String reelId) {
		ModelAndView mv = new ModelAndView();
		//查询问卷的设置
		AllSubjectDTO reelVo = reelService.queryByRIdReel(reelId);
		reelVo = reelService.queryReelList(reelVo, reelId);
		mv.addObject("reelVo", reelVo);
		mv.setViewName("reel/preview");
		return mv;
	}

	/**
	 * @Description: “回收概况”展示信息
	 * @Param:
	 * @Return:
	 * @Exception:
	 */
	@RequestMapping("/receiveDetail")
	public ModelAndView receiveDetail(String reelId) {
		ModelAndView mv = new ModelAndView();
		ReceiveDetailResultDto receiveDetailResultDto = null;
		try {
			if (StringUtils.isBlank(reelId)) {
				throw new BusinessException("reelId不允许为空");
			}
			receiveDetailResultDto = questionService.receiveDetail(reelId);
		} catch (Exception e) {
			logger.error("=========receiveDetail()异常：", e);
		}

		String result = JSON.toJSONString(receiveDetailResultDto);
		ReceiveDetailResultDto receiveDetailResultDto1 = JSONObject.parseObject(result, ReceiveDetailResultDto.class);
		mv.addObject("result", receiveDetailResultDto1);
		mv.setViewName("/reel/fill-situation");

		return mv;
	}

	/**
	 * @Description: “回收概况”折线图
	 * @Param:
	 * @Return:
	 * @Exception:
	 */
	@RequestMapping("/receiveLineChart")
	public BaseResult receiveLineChart(String reelId) {
		BaseResult json = new BaseResult();
		List<ReceiveLineChartDTO> stringStringMap = null;
		try {
			if (StringUtils.isBlank(reelId)) {
				throw new BusinessException("reelId不允许为空");
			}
			stringStringMap = reelAnswerRecordService.receiveLineChart(reelId);
			json.setCode(Globals.SUCCESSE);
			json.setMsg(Globals.MSG_CZ_SUCCESSE);
			json.setData(stringStringMap);
		} catch (Exception e) {
			json.setCode(Globals.FIAL);
			json.setMsg(Globals.MSG_CZ_FAIL);
			logger.error("=========receiveLineChart()异常：", e);
		}

		return json;
	}

	/**
	 * @Description: 查询“回收数据”页面
	 * @Param:
	 * @Return:
	 * @Exception:
	 */
	@PostMapping("/listReceivedTablePage")
	public ModelAndView listReceivedTablePage() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/reel/reclaim");

		return mv;
	}

	/**
	 * @Description: 查询“回收数据”表格数据
	 * @Param:
	 * @Return:
	 * @Exception:
	 */
	@RequestMapping(value = "/listReceivedTableData")
	public BaseResult listReceivedTableData(String reelId, String startTime, String endTime, int pageSize) {
		Map<String, Object> tableDatas = null;
		BaseResult jsonObject = new BaseResult();
		try {
			if (StringUtils.isBlank(reelId)) {
				throw new BusinessException("reelId不允许为空");
			}
			if (pageSize == 0) {
				pageSize = 20;
			}
			tableDatas = reelAnswerRecordService.listReceivedTableData(reelId, startTime, endTime, pageSize);
//			String s = JSON.toJSONString(tableDatas);
//			jsonObject = JSONObject.parseObject(s);
			jsonObject.setData(tableDatas);
			jsonObject.setCode(Globals.SUCCESSE);
			jsonObject.setMsg(Globals.MSG_CZ_SUCCESSE);
		} catch (Exception e) {
			jsonObject.setCode(Globals.FIAL);
			jsonObject.setMsg(Globals.MSG_CZ_FAIL);
			logger.error("=========receiveLineChart()异常：", e);
		}

		return jsonObject;
	}

	/**
	 * @author: Mr.Xu
	 * @Date: 2020/1/7 9:22
	 * @Description 删除问卷统计
	 */
	@RequestMapping("/delReelStatistics")
	public BaseResult delReelStatistics(@RequestParam(value = "reelAnswerRecordIds[]") List<String> reelAnswerRecordIds) {
		BaseResult json = new BaseResult();
		if (CollectionUtils.isEmpty(reelAnswerRecordIds)) {
			throw new BusinessException("reelAnswerRecordId不允许为空");
		}
		try {
			reelAnswerRecordService.delReelStatistics(reelAnswerRecordIds);
			json.setCode(Globals.SUCCESSE);
			json.setMsg(Globals.MSG_CZ_SUCCESSE);
		} catch (Exception e) {
			json.setCode(Globals.FIAL);
			json.setMsg(Globals.MSG_CZ_FAIL);
			logger.error("===========delReelStatistics异常：", e);
		}

		return json;
	}

//	/**
//	 * @Description: 查询“回收数据”表格数据
//	 * @Param:
//	 * @Return:
//	 * @Exception:
//	 */
//	@PostMapping("/listReceivedTableData")
//	public ModelAndView listReceivedTableData(String reelId){
//		ModelAndView mv = new ModelAndView();
//		Map<String, Object> tableDatas = null;
//		try {
//			if(StringUtils.isBlank(reelId)){
//				throw new BusinessException("reelId不允许为空");
//			}
//			tableDatas = reelAnswerRecordService.listReceivedTableData(reelId);
//		} catch (Exception e) {
//			logger.error("=========receiveLineChart()异常：",e);
//		}
//
//		String result = JSON.toJSONString(tableDatas);
//		mv.addObject("result", result);
//		mv.setViewName("/reel/reclaim");
//
//		return mv;
//	}

	@RequestMapping("/test")
	public void test() {
//		ReelAnswerRecordDTO record = new ReelAnswerRecordDTO();
//		record.setId(StringUtil.get32UUID());
//		record.setUserId("dsfdsfdsfds");
//		record.setReelId("testesttsttstdtstetsttet");
//		record.setTimeConsuming("");
//		record.setCreateOpId("sdaasfewfwe");
//		record.setCreateOpName("dsfasdfsaqw");
//		record.setCreateOrgId("safdasfewwc");
//		record.setStartTime("2019-12-31 17:33:05");
//		record.setEndTime("2019-12-31 17:35:08");
//		reelAnswerRecordService.saveReelAnswerRecord(record);

//		ReelAnswerRecordDTO record = new ReelAnswerRecordDTO();
//		record.setId("8c7fb7922a40436fb460adc7ec2bf3f3");
//		record.setStatus(0);
//		record.setStartTime("2019-12-31 17:30:00");
//		reelAnswerRecordService.updateAnwserRecord(record);
	}
    /*
      消息发布（选中默认用户）
     */
	@RequestMapping(value = "/publishMessage", produces = {"application/josn;charset=UTF-8"})
	@ResponseBody
	public String publishMessage(@RequestBody PushMsgDTO pushmsgDTO,HttpServletResponse resp)throws Exception{
		  JSONObject json = new JSONObject();
		try {
			//此卷有限定用户
			String lislimt = questionService.selectuserids(pushmsgDTO.getRid());
			if(lislimt.equals("0")){
				List<String> account = questionService.selectUserList(pushmsgDTO.getRid());
				pushmsgDTO.setUsername(account);
				putMessage(pushmsgDTO);
			   //此卷没有限定用户时默认全部用户
			}else {
				List<String> accounts = questionService.selectUsers();
				pushmsgDTO.setUsername(accounts);
				putMessage(pushmsgDTO);
			}
			json.put("code",0);
			json.put("message","发布成功");
		} catch (Exception e) {
			logger.info("发布出错:"+e.toString());
			json.put("code",1);
			json.put("message","发布失败");
		}
		return json.toString();
	 }
	/*
    消息转发（可以选择添加用户）
    */
	@RequestMapping(value = "/pushMessage", produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	//@Log(operateType = "推送消息")
	public String pushMessage(@RequestBody PushMsgDTO pushMsgDTO) throws Exception {
		JSONObject jsons = putMessage(pushMsgDTO);
		return jsons.toString();
	}
	/*
	  推送消息接口
	 */
	private JSONObject putMessage(PushMsgDTO pushMsgDTO){
		HttpSession session = ContextHolderUtils.getSession();
		Client clinet = ClientManager.getInstance().getClient(session.getId());
		JSONObject jsons = new JSONObject();
		try {
			if (clinet != null) {
				UserDTO user = clinet.getUser();
				logger.info("--------------获取开始--------------");
				String url = ProjectConfUtils.getProjectConf("pushurl");
				String newStatic = ProjectConfUtils.getProjectConf("newStatic");
				String token = getToken(url);
				for (String account:pushMsgDTO.getUsername() ) {
					JSONObject json = new JSONObject();
					json.put("content", "问卷调查");
					json.put("creator", user.getId());
					json.put("detailUrl", newStatic+account+"&rid="+pushMsgDTO.getRid());  //+account+"&rid="+pushMsgDTO.getRid()
					json.put("title", "问卷统计结果");
					json.put("titleBgColor", "#AE0000");
					json.put("titleColor", "#000079");
					json.put("type", "1");
					json.put("toUsers", account);
					String messages = json.toJSONString();
					//消息推送 post请求
					String httpUrl = url + "platform/api2/message/sendMessageTemplate";
					String userResult = HttpClientUtil.getInstance().doPostPushokenHttps(httpUrl, token, messages);
					logger.info("--------------获取返回参数--------------" + userResult);
					//获取返回参数
					PushResult result = JSON.parseObject(userResult, PushResult.class);
					if ("0".equals(result.getCode())) {
						logger.info("消息发送成功");
						jsons.put("code", Globals.SUCCESSE);
						jsons.put("msg", Globals.MSG_CZ_SUCCESSE);
					} else {
						logger.info("消息发送失败");
						jsons.put("code", Globals.FIAL);
						jsons.put("msg", Globals.MSG_CZ_FAIL);
					}
				}
			}
		}catch (Exception e){
			jsons.put("code", Globals.FIAL);
			jsons.put("msg", Globals.MSG_CZ_FAIL);
		}
		return jsons;
	}
	//获取token
	private String getToken(String url) {
		String accessType = ProjectConfUtils.getProjectConf("accessType");
		String appID = ProjectConfUtils.getProjectConf("appID");
		String appSecret = ProjectConfUtils.getProjectConf("appSecret");
		String valtokenUrl = url + "platform/api2/token?accessType=" + accessType + "&appID=" + appID + "&appSecret=" + appSecret;
		//获取微服务token get请求
		String a = HttpClientUtil.getInstance().sendHttpsGet(valtokenUrl);
		logger.info("--------------返回结果--------------" + a);
		PushResult result = JSON.parseObject(a, PushResult.class);
		String accessToken = result.getResult().getAccess_token();
		logger.info("-----------------获取结束-----------------");
		if("0".equals(result.getCode())){
			logger.info("获取token成功");
		}else{
			logger.info("获取token失败");
		}
		return  accessToken;
	}

	/**
	 * 查询当前的问卷答题统计
	 * 展示已答和未答得人员
	 */
	@RequestMapping(value = "/questionState", produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public JSONObject   questionState(String rId ){
		JSONObject json = new JSONObject();
		try {
			SelectUsersDTO  selectUsersDTO  =  questionService.getQuestionState(rId);
			json.put("data",selectUsersDTO);
			json.put("code", Globals.SUCCESSE);
			json.put("msg", Globals.MSG_CZ_SUCCESSE);
		} catch (Exception e) {
			json.put("code", Globals.FIAL);
			json.put("msg", Globals.MSG_CZ_FAIL);
			logger.info("查询失败:"+e.getMessage());
		}
		return json;
	}
	/**
	 * 发送消息给未答的人员
	 */
	@RequestMapping(value = "/sendMessage", produces = {"application/json;charset=UTF-8"})
	public BaseResult sendMessage(@RequestBody JSONObject jsonList){
		BaseResult json = new BaseResult();
		try {
			questionService.sendMessage(jsonList);
			json.setCode(Globals.SUCCESSE);
			json.setMsg(Globals.MSG_CZ_SUCCESSE);
		} catch (Exception e) {
			json.setCode(Globals.FIAL);
			json.setMsg(Globals.MSG_CZ_FAIL);
			logger.info("发送消息失败:"+e.getMessage());
		}
		return json;
	}
}

