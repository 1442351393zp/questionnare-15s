package com.infoac.cas.web;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.infoac.cas.dto.*;
import com.infoac.cas.entity.Client;
import com.infoac.cas.service.AnswerService;
import com.infoac.cas.service.IReelAnswerRecordService;
import com.infoac.cas.service.ReelService;
import com.infoac.cas.util.Log;

import net.sf.json.JSONArray;

import com.infoac.cas.util.*;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

/**
 * @Author: cc
 * @Description: 问卷调查
 * @Date: Created in  2019/10/29
 */
@CrossOrigin
@RestController
@RequestMapping("/reel")
public class ReelController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ReelService reelService;
    @Autowired
    private AnswerService answerService;
    @Autowired
    private IReelAnswerRecordService recordService;


    /**
     * 默认首页
     *
     * @param user
     * @param request
     * @param session
     * @return
     */
    @RequestMapping(value = "/firstindex")
    public ModelAndView firstIndex(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView();
        HttpSession session = ContextHolderUtils.getSession();
        Client client = ClientManager.getInstance().getClient(session.getId());
        UserDTO user = client.getUser();
        if (user != null) {
            mv.addObject("userId", user.getId());
        } else {
            logger.info("----------用户信息为空----------");
        }
        mv.setViewName("reel/index");
        return mv;
    }

    /**
     * 创建问卷页面
     *
     * @param user
     * @param request
     * @param session
     * @return
     */
    @RequestMapping(value = "/main")
    public ModelAndView createQuestion(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView();
        HttpSession session = ContextHolderUtils.getSession();
        Client client = ClientManager.getInstance().getClient(session.getId());
        UserDTO user = client.getUser();
        if (user != null) {
            mv.addObject("userId", user.getId());
        } else {
            logger.info("----------用户信息为空----------");
        }
        String rId = request.getParameter("rId");
        String subjectId = StringUtil.get32UUID();
        String folderId = request.getParameter("folderId");//文件夹id
        mv.addObject("rId", rId);
        mv.addObject("subjectId", subjectId);
        mv.addObject("folderId", folderId);
        mv.addObject("reelStatus", Globals.STOP_ONE);
        mv.setViewName("reel/main");
        return mv;
    }

    /**
     * 编辑问卷页面
     *
     * @param user
     * @param request
     * @param session
     * @return
     */
    @RequestMapping(value = "/editpage")
    public ModelAndView editQuestion(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView();
        HttpSession session = ContextHolderUtils.getSession();
        Client client = ClientManager.getInstance().getClient(session.getId());
        UserDTO user = client.getUser();
        if (user != null) {
            mv.addObject("userId", user.getId());
            String rId = request.getParameter("rId");
            List<AllSubjectDTO> allSubjectDTO = new ArrayList<AllSubjectDTO>();
            String pageIdArr = "";
            if (StringUtils.isBlank(rId)) {
                //logger.error("ReelController类下的queryAllSubject方法:rId为空，不能查询到数据！");
            } else {
                allSubjectDTO = reelService.queryReel(rId);
                List<PageDTO> pageList = new ArrayList<PageDTO>();
                pageList = reelService.queryPage(rId);
                allSubjectDTO.get(0).setPageList(pageList);
                for (int i = 0; i < pageList.size(); i++) {
                    pageIdArr += (pageList.get(i).getPageId()) + ",";
                }
                pageIdArr = pageIdArr.substring(0, pageIdArr.length() - 1);
                mv.addObject("type", allSubjectDTO.get(0).getReelStatus());
            }
            mv.addObject("pageIdArr", pageIdArr);
            mv.addObject("rId", rId);
            mv.setViewName("reel/edit");
        } else {
            logger.info("----------用户信息为空----------");
            mv.setViewName(null);
        }
        return mv;
    }

    /**
     * 我的问卷列表页和文件夹下的列表页
     *
     * @param user
     * @param request
     * @param session
     * @return
     */
    @RequestMapping(value = "/list")
    public ModelAndView list(HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setCharacterEncoding("utf-8");
        ModelAndView mv = new ModelAndView();
        String folderId = request.getParameter("folderId");
        List<FolderDTO> FolderDTO = new ArrayList<FolderDTO>();
        List<AllSubjectDTO> allSubjectDTO = new ArrayList<AllSubjectDTO>();
        HttpSession session = ContextHolderUtils.getSession();
        Client client = ClientManager.getInstance().getClient(session.getId());
        UserDTO user = client.getUser();
        if (user != null) {
            if (StringUtils.isBlank(folderId)) {//为空时是我的问卷列表
                allSubjectDTO = reelService.queryListRecycle(user.getId());
                String delFlag = "0";//正常数据
                FolderDTO = reelService.queryBasketList(delFlag, user.getId());
                if (!allSubjectDTO.isEmpty()) {
                    for (int j = 0; j < allSubjectDTO.size(); j++) {
                        String rId = allSubjectDTO.get(j).getrId();
                        List<PageDTO> pageDTO = new ArrayList<PageDTO>();
                        pageDTO = reelService.queryPage(rId);
                        allSubjectDTO.get(j).setPageList(pageDTO);
                        allSubjectDTO.get(j).setTitle(SummaryUtil.spiltByLabel(allSubjectDTO.get(j).getTitle()));
                    }

                    String viewStatus = reelService.queryViewStatus(user.getId(), user.getUsername());
                    mv.addObject("userId", user.getId());
                    mv.addObject("allSubjectDTO", allSubjectDTO);
                    mv.addObject("FolderDTO", FolderDTO);
                    mv.addObject("viewStatus", viewStatus);//列表视图状态
                    mv.addObject("folderId", folderId);
                    mv.setViewName("reel/list");

                } else if (!FolderDTO.isEmpty()) {
                    String viewStatus = reelService.queryViewStatus(user.getId(), user.getUsername());
                    mv.addObject("userId", user.getId());
                    mv.addObject("allSubjectDTO", allSubjectDTO);
                    mv.addObject("FolderDTO", FolderDTO);
                    mv.addObject("viewStatus", viewStatus);//列表视图状态
                    mv.addObject("folderId", folderId);
                    mv.setViewName("reel/list");
                } else {//列表空页面
                    mv.addObject("userId", user.getId());
                    mv.addObject("allSubjectDTO", allSubjectDTO);
                    mv.addObject("FolderDTO", FolderDTO);
                    mv.addObject("folderId", folderId);
                    mv.setViewName("reel/emptyList");
                }


            } else {//其他是点击进入文件夹列表
                String delFlag = "0";//正常数据
                allSubjectDTO = reelService.queryByfolderIdReel(folderId, delFlag);
                if (!allSubjectDTO.isEmpty()) {
                    for (int j = 0; j < allSubjectDTO.size(); j++) {
                        String rId = allSubjectDTO.get(j).getrId();
                        List<PageDTO> pageDTO = new ArrayList<PageDTO>();
                        pageDTO = reelService.queryPage(rId);
                        allSubjectDTO.get(j).setPageList(pageDTO);
                        allSubjectDTO.get(j).setTitle(SummaryUtil.spiltByLabel(allSubjectDTO.get(j).getTitle()));
                    }
                }
                String viewStatus = reelService.queryViewStatus(user.getId(), user.getUsername());
                mv.addObject("userId", user.getId());
                mv.addObject("allSubjectDTO", allSubjectDTO);
                mv.addObject("viewStatus", viewStatus);//列表视图状态
                mv.addObject("folderId", folderId);
                mv.setViewName("reel/list");
            }
        } else {
            logger.info("----------用户信息为空----------");
            response.sendRedirect("/login/login");
        }
        return mv;
    }

    /**
     * 创建问卷页面
     *
     * @param user
     * @param request
     * @param session
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/main1")
    public ModelAndView createQuestion1(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView mv = new ModelAndView();
        String rId = request.getParameter("rId");
        //String userId = request.getParameter("userId");
        HttpSession session = ContextHolderUtils.getSession();
        Client client = ClientManager.getInstance().getClient(session.getId());
        UserDTO user = client.getUser();
        List<AllSubjectDTO> allSubjectDTO = new ArrayList<AllSubjectDTO>();
        String pageIdArr = "";
        if (StringUtils.isBlank(rId)) {
            logger.error("ReelController类下的queryAllSubject方法:rId为空，不能查询到数据！");
        } else {
            allSubjectDTO = reelService.queryReel(rId);
            List<PageDTO> pageList = new ArrayList<PageDTO>();
            pageList = reelService.queryPage(rId);
            allSubjectDTO.get(0).setPageList(pageList);
            for (int i = 0; i < pageList.size(); i++) {
                pageIdArr += (pageList.get(i).getPageId()) + ",";
            }
            mv.addObject("reelStatus", allSubjectDTO.get(0).getReelStatus());
        }
        pageIdArr = pageIdArr.substring(0, pageIdArr.length() - 1);

        mv.addObject("pageIdArr", pageIdArr);
        mv.addObject("rId", rId);
        mv.addObject("userId", user.getId());
        mv.setViewName("reel/main");
        return mv;
    }


    /**
     * 进入统计页面
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/statistics")
    public ModelAndView statistics(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView();
        String rId = request.getParameter("rId");
        mv.addObject("rId", rId);
        mv.setViewName("reel/statistics");
        return mv;
    }

    /**
     * 进入统计报表右侧数据
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/countPic")
    @ResponseBody
    public ModelAndView countPic(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView();
        String rId = request.getParameter("rId");
        String startTime = request.getParameter("startTime") == null ? "" : request.getParameter("startTime");
        String endTime = request.getParameter("endTime") == null ? "" : request.getParameter("endTime");
        String subjectArr = request.getParameter("subjectArr") == null ? "" : request.getParameter("subjectArr");

        AllSubjectDTO allSubjectDTO = queryCount(rId, startTime, endTime, subjectArr);
        if (StringUtils.isNotBlank(startTime) && StringUtils.isNotBlank(startTime)) {
            allSubjectDTO.setStartTime(startTime);
            allSubjectDTO.setEndsTime(endTime);
        }
        if (StringUtils.isNotBlank(subjectArr)) {
            mv.addObject("subjectArr", subjectArr);
        }
        JSONArray array = JSONArray.fromObject(allSubjectDTO);
        mv.addObject("allSubjectArray", array.toString());
        mv.addObject("allSubjectDTO", allSubjectDTO);

        mv.setViewName("reel/count-pic");
        return mv;
    }

    /**
     * 统计报表查询和导出共用
     *
     * @param rId
     * @param startTime
     * @param endTime
     * @param subjectArr
     * @return
     */
    private AllSubjectDTO queryCount(String rId, String startTime, String endTime, String subjectArr) {
        AllSubjectDTO allSubjectDTO = reelService.queryByRIdReel(rId);
        List<PageDTO> pageDTO = reelService.queryPage(rId);
        for (int j = 0; j < pageDTO.size(); j++) {
            String pageId = pageDTO.get(j).getPageId();
            List<SubjectDTO> subjectDTO = reelService.querySubject(pageId);
            for (int i = 0; i < subjectDTO.size(); i++) {
                List<CountPicDTO> countPicDTO = new ArrayList<CountPicDTO>();
                String subjectId = subjectDTO.get(i).getSubjectId();
                //下面的正则还是有问题  为了避免乱码直接使用工具类处理全部样式
//                String topic = subjectDTO.get(i).getTopic().replaceAll("<input type=\"text\" value=\"\" class=\"completions\" maxlength=\"20\">", "_");
//                topic.replaceAll("<input type=\"text\" value=\"\" class=\"completions\" maxlength=\"20\" style=\"position: relative;\">", "_");
                subjectDTO.get(i).setTopic(SummaryUtil.spiltByLabel(subjectDTO.get(i).getTopic()));
                subjectDTO.get(i).setRemark(SummaryUtil.spiltByLabel(subjectDTO.get(i).getRemark()));
                NumberFormat numberFormat = NumberFormat.getInstance();
                numberFormat.setMaximumFractionDigits(2);
                //是否带有编辑筛选条件
                if (StringUtils.isNotBlank(subjectArr)) {
                    com.alibaba.fastjson.JSONArray jsonArray = com.alibaba.fastjson.JSONArray.parseArray(subjectArr);
                    JSONObject jsonObj = new JSONObject();
                    List<String> answerRecordIdList = new ArrayList<String>();
                    List<String> moreAnswerRecordIdList = new ArrayList<String>();
                    for (int f = 0; f < jsonArray.size(); f++) {
                        jsonObj = (JSONObject) jsonArray.get(f);
                        String selectedSubjectId = jsonObj.getString("subjectId");
                        String selectedInclude = jsonObj.getString("include");
                        String selectedOptionsId = jsonObj.getString("optionsId");
                        if (selectedInclude.equals("1")) {//包含
                            answerRecordIdList = answerService.queryAnswerRecordId(selectedOptionsId, selectedSubjectId, startTime, endTime);
                        } else {//2不包含
                            answerRecordIdList = answerService.queryNotIncludeAnswerRecordId(selectedOptionsId, selectedSubjectId, startTime, endTime);
                        }
                        for (int t = 0; t < answerRecordIdList.size(); t++) {
                            moreAnswerRecordIdList.add(answerRecordIdList.get(t));
                        }
                    }
                    countPicDTO = reelService.queryCompareCountPic(rId, pageId, subjectId, "0");
                    Map<String, Integer> repetitionMap = new HashMap<String, Integer>();
                    for (String obj : moreAnswerRecordIdList) {
                        if (repetitionMap.containsKey(obj)) {
                            repetitionMap.put(obj, repetitionMap.get(obj).intValue() + 1);
                        } else {
                            repetitionMap.put(obj, new Integer(1));
                        }
                    }
                    Iterator<String> keys = repetitionMap.keySet().iterator();
                    if (keys.hasNext() == false) {
                        for (int b = 0; b < countPicDTO.size(); b++) {
                            countPicDTO.get(b).setPercentage("0");
                        }
                    }
                    while (keys.hasNext()) {
                        String key = keys.next();
                        if (repetitionMap.get(key).intValue() == jsonArray.size()) {//符合条件的去查询该卷都选择了哪些题的选项
                            List<ReceivedDataInfo> receivedDataInfo = answerService.queryoptionsId(key, subjectId);
                            //循环多个答题id，计算每个答题id选择的哪些选项，将所有答题选项重新统计个数

                            for (int d = 0; d < receivedDataInfo.size(); d++) {
                                String optionsId = receivedDataInfo.get(d).getOptionsId();
                                for (int p = 0; p < countPicDTO.size(); p++) {
                                    logger.info("解析当前选项的类容并重新组装数据");
                                    if("0".equals(countPicDTO.get(p).getIsMultipleChoice())){
                                        logger.info("当前选项是多选里面的其他选项");
                                        String options = "其他选项"+Integer.valueOf(p+1);
                                        countPicDTO.get(p).setOptions(options);
                                    }else {
                                        countPicDTO.get(p).setOptions(SummaryUtil.spiltByLabel(countPicDTO.get(p).getOptions()));
                                    }
                                    if (countPicDTO.get(p).getOptionsId().equals(optionsId)) {
                                        String selectedOtherNum = countPicDTO.get(p).getNum();
                                        int num = Integer.parseInt(selectedOtherNum);
                                        num = num + 1;
                                        countPicDTO.get(p).setNum(String.valueOf(num));
                                    }
                                }
                            }
                            String optionsNum = "";//取全部个数
                            int optionsNumes = 0;
                            for (int w = 0; w < countPicDTO.size(); w++) {
                                optionsNum = countPicDTO.get(w).getNum();
                                int optionsNums = Integer.parseInt(optionsNum);
                                optionsNumes += optionsNums;
                            }
                            for (int v = 0; v < countPicDTO.size(); v++) {//计算重新筛选后的占比
                                int num = Integer.parseInt(countPicDTO.get(v).getNum());
                                String percentage = "";
                                if (optionsNumes != 0) {
                                    percentage = numberFormat.format((num / (float) optionsNumes) * 100);
                                } else {
                                    percentage = "0";
                                }
                                countPicDTO.get(v).setPercentage(percentage);
                            }
                        } else {//都不符合
                            for (int b = 0; b < countPicDTO.size(); b++) {
                                countPicDTO.get(b).setPercentage("0");
                            }
                        }
                    }
                    subjectDTO.get(i).setCountPicDTO(countPicDTO);
                } else {
                    countPicDTO = reelService.countPic(rId, pageId, subjectId, startTime, endTime);
                    logger.info("获取到当前题目下的选项:"+countPicDTO.toString());
                    for (int b = 0; b < countPicDTO.size(); b++) {
                        logger.info("解析当前选项的类容并重新组装数据");
                        if( "0".equals(countPicDTO.get(b).getIsMultipleChoice())){
                            logger.info("当前选项是多选里面的其他选项");
                            String options = "其他选项"+Integer.valueOf(b+1);
                            countPicDTO.get(b).setOptions(options);
                        }else{
                            logger.info("当前选项是正常选项");
                            countPicDTO.get(b).setOptions(SummaryUtil.spiltByLabel(countPicDTO.get(b).getOptions()));

                        }
                        String countNum = countPicDTO.get(b).getCountNum();
                        String num = countPicDTO.get(b).getNum();
                        float countNums = Float.parseFloat(countNum);
                        float nums = Float.parseFloat(num);
                        String percentage = "";
                        if (countNums != 0.0) {
                            percentage = numberFormat.format((nums / countNums) * 100);
                        } else {
                            percentage = "0";
                        }
                        countPicDTO.get(b).setPercentage(percentage);
                    }
                    subjectDTO.get(i).setCountPicDTO(countPicDTO);

                }
                if("4".equals(subjectDTO.get(i).getSubjectType())){
                      //筛选条件为空
                    if(StringUtils.isBlank(subjectArr)){
                        //搜索条件,开始时间，结束时间，赛选条件
                        Map<String, Object> map = new HashMap<String, Object>();
                        List<Integer> years = DateUtil.getYearList();
                        map.put("years", years);
                        map.put("rId",rId);
                        map.put("subjectId",subjectId);
                        map.put("startTime",startTime);
                        map.put("endTime",endTime);
                        List<TextAnswerDTO> textanswer = reelService.getTextAnswerList(map);
                        subjectDTO.get(i).setTextanswer(textanswer);
                    }else{
                        //筛选条件不为空时
                        com.alibaba.fastjson.JSONArray jsonArray = com.alibaba.fastjson.JSONArray.parseArray(subjectArr);
                        JSONObject jsonObj = new JSONObject();
                        List<TextAnswerDTO> textanswer =null;
                        for (int f = 0; f < jsonArray.size(); f++) {
                            jsonObj = (JSONObject) jsonArray.get(f);
                            String selectedSubjectId = jsonObj.getString("subjectId");
                            String selectedInclude = jsonObj.getString("include");
                            //包含此选项是
                            if(selectedSubjectId.equals(subjectDTO.get(i))&&selectedInclude.equals("1")){
                                textanswer  = reelService.getIncludeAnswerList(rId, selectedSubjectId, startTime, endTime);
                            }else if(selectedSubjectId.equals(subjectDTO.get(i))&&selectedInclude.equals("2")){ //不包含
                                textanswer  = reelService.getNoIncludeAnswerList(rId, selectedSubjectId, startTime, endTime);

                            }

                         }
                        subjectDTO.get(i).setTextanswer(textanswer);

                    }

                }

            }
            pageDTO.get(j).setSubjectList(subjectDTO);
        }
        //获取文本内容
        allSubjectDTO.setPageList(pageDTO);
        String createTime = allSubjectDTO.getCreateTime().substring(0, allSubjectDTO.getCreateTime().indexOf(" "));
        allSubjectDTO.setStartTime(createTime);
        if (StringUtils.isBlank(startTime) && StringUtils.isBlank(endTime)) {
            startTime = createTime;
            endTime = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        }
        allSubjectDTO.setEndsTime(endTime);
        return allSubjectDTO;
    }


    /**
     * 跳转上传页面
     *
     * @param user
     * @param request
     * @param session
     * @return
     */
    @RequestMapping(value = "/toUpload")
    public ModelAndView toUpload(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("reel/upload");
        return mv;
    }

    /**
     * 图片上传
     *
     * @param user
     * @param request
     * @param session
     * @return
     */
    @RequestMapping(value = "/uploadImg")
    public Map<String, String> uploadImg(HttpServletRequest request, HttpServletResponse response, @RequestParam(required = true) MultipartFile icons) {
        Map<String, String> mp = new HashMap<String, String>();
        if (!icons.isEmpty() && null != icons) {
            //上传图标
            String filePath = PathUtil.getClasspath() + Const.FILEPATHMODULEIMG + DateUtil.getDays(); // 文件上传路径
            String fileName = FileUpload.fileUp(icons, filePath, DateUtil.get32UUID()); // 执行上传
            //路径保存的是uploadFiles/uploadImgs/+当前日期+文件名称
            String path = request.getContextPath();
            String basePath = request.getScheme() + "://"
                    + request.getServerName() + ":" + request.getServerPort()
                    + path;
            String imgAddress = basePath + "/" + Const.FILEPATHMODULEIMG + DateUtil.getDays() + "/" + fileName;
            String img = "<img src='" + imgAddress + "' width='60%'>";
            mp.put("img", img);
        }
        return mp;
    }


    /**
     * @Author: cc
     * @Description: 创建题卷
     * @Date: Created in 2019/8/27
     */
    @RequestMapping(value = "/reelsave", produces = {"application/josn;charset=UTF-8"})
    @ResponseBody
    public String taskSave(HttpServletRequest request, ReelDTO reel) {
        JSONObject json = new JSONObject();
        try {
            reel.setMyyear(String.valueOf(LocalDate.now().getYear()));
            reel.setCanal("0");
            reel.setCanalOnline("0");
            reel.setBksetting("0");//默认皮肤为0
            reel.setCanalText("在线");
            reelService.reelSave(reel);
            json.put("code", Globals.SUCCESSE);
            json.put("msg", Globals.MSG_XG_SUCCESSE);
        } catch (Exception e) {
            json.put("code", Globals.FIAL);
            json.put("code", Globals.MSG_XG_FAIL);
        }
        return json.toString();
    }

    /**
     * 查询卷下的题和选项
     *
     * @param request
     * @param rId
     * @param pageId
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/queryAllSubject")
    @ResponseBody
    public List<AllSubjectDTO> queryAllSubject(HttpServletRequest request, String rId, String pageId) throws Exception {
        request.setCharacterEncoding("utf-8");
        List<AllSubjectDTO> allSubjectDTO = new ArrayList<AllSubjectDTO>();

        if (StringUtils.isBlank(rId)) {
            logger.error("ReelController类下的queryAllSubject方法:rId为空，不能查询到数据！");
        } else {
            allSubjectDTO = reelService.queryReel(rId);
            PageDTO pageDTO = new PageDTO();
            pageDTO = reelService.queryPageByIdAndrId(pageId);
            allSubjectDTO.get(0).setPageDTO(pageDTO);

            List<SubjectDTO> subjectList = new ArrayList<SubjectDTO>();
            subjectList = reelService.querySubject(pageId);
            allSubjectDTO.get(0).setSubjectList(subjectList);
            for (int p = 0; p < subjectList.size(); p++) {
                String subjectId = subjectList.get(p).getSubjectId();
                List<OptionsDTO> optionsList = new ArrayList<OptionsDTO>();
                optionsList = reelService.queryOptions(subjectId);
                subjectList.get(p).setOptionsList(optionsList);
            }
        }

        return allSubjectDTO;
    }


    /**
     * 问卷大纲tab页显示题
     *
     * @param request
     * @param rId
     * @param pageId
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/queryOutlineSubject")
    @ResponseBody
    public AllSubjectDTO queryOutlineSubject(HttpServletRequest request) throws Exception {
        request.setCharacterEncoding("utf-8");
        AllSubjectDTO allSubjectDTO = new AllSubjectDTO();
        String rId = request.getParameter("rId");
        if (StringUtils.isBlank(rId)) {
            logger.error("ReelController类下的queryOutlineSubject方法--问卷大纲:rId为空，不能查询到数据！");
        } else {
            allSubjectDTO = reelService.queryByRIdReel(rId);
            List<PageDTO> pageList = new ArrayList<PageDTO>();
            pageList = reelService.queryPageRId(rId);
            List<SubjectDTO> subjectList = new ArrayList<SubjectDTO>();
            for (int i = 0; i < pageList.size(); i++) {
                String pageId = pageList.get(i).getPageId();
                subjectList = reelService.querySubject(pageId);
                pageList.get(i).setSubjectList(subjectList);
            }
            allSubjectDTO.setPageList(pageList);
        }
        return allSubjectDTO;
    }


    @RequestMapping(value = "/queryAllByPageIdSubject")
    @ResponseBody
    public List<AllSubjectDTO> queryAllByPageIdSubject(HttpServletRequest request) throws Exception {
        request.setCharacterEncoding("utf-8");
        List<AllSubjectDTO> allSubjectDTO = new ArrayList<AllSubjectDTO>();
        String rId = request.getParameter("rId");
        String pageId = request.getParameter("pageId");
        if (StringUtils.isBlank(rId)) {
            logger.error("ReelController类下的queryAllSubject方法:rId为空，不能查询到数据！");
        } else {
            allSubjectDTO = reelService.queryReel(rId);
            PageDTO pageDTO = new PageDTO();
            pageDTO = reelService.queryPageByIdAndrId(pageId);
            allSubjectDTO.get(0).setPageDTO(pageDTO);

            List<SubjectDTO> subjectList = new ArrayList<SubjectDTO>();
            subjectList = reelService.querySubject(pageId);
            allSubjectDTO.get(0).setSubjectList(subjectList);
            for (int p = 0; p < subjectList.size(); p++) {
                String subjectId = subjectList.get(p).getSubjectId();
                List<OptionsDTO> optionsList = new ArrayList<OptionsDTO>();
                optionsList = reelService.queryOptions(subjectId);
                subjectList.get(p).setOptionsList(optionsList);
            }
        }
        return allSubjectDTO;
    }

    @RequestMapping(value = "/queryList")
    @ResponseBody
    public ModelAndView queryList(HttpServletRequest request, String rId) throws Exception {
        request.setCharacterEncoding("utf-8");
        ModelAndView mv = new ModelAndView();
        List<AllSubjectDTO> allSubjectDTO = new ArrayList<AllSubjectDTO>();
        if (StringUtils.isBlank(rId)) {
            allSubjectDTO = reelService.queryList();
        }
        mv.setViewName("reel/list");
        mv.addObject("reelDTO", allSubjectDTO);
        return mv;
    }


    @RequestMapping(value = "/queryByRid")
    @ResponseBody
    public AllSubjectDTO queryByRid(HttpServletRequest request, String rId) throws Exception {
        request.setCharacterEncoding("utf-8");
        AllSubjectDTO allSubject = new AllSubjectDTO();
        if (StringUtils.isNotBlank(rId)) {
            allSubject = reelService.queryByRIdReelRecycle(rId);
        }
        if (allSubject == null) {//查询后为给前端取值自定义赋值为0
            allSubject = new AllSubjectDTO();
            allSubject.setrId(rId);
            allSubject.setPageNum("0");
            allSubject.setSubjectNum("0");
            allSubject.setRecycleNum("0");
            allSubject.setReelStatus("2");//2查不到
        }
        return allSubject;
    }

    /**
     * 保存题
     *
     * @param
     * @throws Exception
     */
    @RequestMapping(value = "/subjectSave")
    @ResponseBody
    @Log(operateType = "问卷创建")
    public SubjectDTO subjectSave(HttpServletRequest request, String[] optionsArr) throws Exception {
        request.setCharacterEncoding("utf-8");
        HttpSession session = ContextHolderUtils.getSession();
        Client client = ClientManager.getInstance().getClient(session.getId());
        UserDTO user = client.getUser();

        String rId = request.getParameter("rId");
        String title = request.getParameter("title");
        String pageId = "";
        pageId = request.getParameter("pageId");
        String folderId = request.getParameter("folderId");
        String startLanguage = request.getParameter("startLanguage");
        String endLanguage = request.getParameter("endLanguage");
        String must = request.getParameter("must");
        if (StringUtils.isBlank(rId)) {
            //卷
            rId = DateUtil.getCommentId();
            ReelDTO reelDTO = new ReelDTO();
            reelDTO.setrId(rId);
            if (StringUtils.isBlank(title)) {
                reelDTO.setTitle("问卷标题");//如果直接保存题，不写标题就直接默认标题
                reelDTO.setStartLanguage("为了给您提供更好的服务，希望您能抽出几分钟时间，将您的感受和建议告诉我们,我们非常重视每位用户的宝贵意见，期待您的参与！现在我们就马上开始吧！");//如果直接保存题，不写标起始语就直接默认起始语
            } else {
                reelDTO.setTitle(title);
                reelDTO.setStartLanguage(startLanguage);
            }
            reelDTO.setCreateTime(DateUtil.getTime());
            reelDTO.setReelStatus("1");//0开始回收 1暂停回收
            reelDTO.setDelFlag("0");//0.正常显1.废纸篓2.删除
            reelDTO.setShowNo("0");
            reelDTO.setAnonymous("1");
            reelDTO.setOnceChance("0");
            reelDTO.setInitiate("1");
            reelDTO.setRetrieve("1");
            reelDTO.setIsLimit("1");
            reelDTO.setEndTime(DateUtil.getTime());
            reelDTO.setSetup("1");
            reelDTO.setUserId(user.getId());
            reelDTO.setFolderId(folderId);
            reelDTO.setEndLanguage(endLanguage);//结束语
            String year = String.valueOf(LocalDate.now().getYear());
            reelDTO.setMyyear(year);
            reelDTO.setCanal("0");
            reelDTO.setCanalOnline("0");
            reelDTO.setBksetting("0");//默认皮肤为0
            reelDTO.setCanalText("在线");
            reelService.reelSave(reelDTO);
        }
        if (StringUtils.isBlank(pageId)) {
            int pagenum = getNowPagenum(rId, 1);
            //页
            PageDTO pageDTO = new PageDTO();
            pageId = DateUtil.getCommentId();
            pageDTO.setPageId(pageId);
            pageDTO.setCreateTime(DateUtil.getTime());
            pageDTO.setrId(rId);
            pageDTO.setPagenum(pagenum);
            reelService.pageSave(pageDTO);
        }

        //题
        SubjectDTO subjectDTO = new SubjectDTO();
        String subjectId = StringUtil.get32UUID();
        String topic = request.getParameter("topic");
        String remark = request.getParameter("remark");
        String subjectType = request.getParameter("subjectType");
        subjectDTO.setSubjectId(subjectId);
        subjectDTO.setTopic(topic);
        subjectDTO.setRemark(remark);
        subjectDTO.setSubjectType(subjectType);
        subjectDTO.setMust(must);
        subjectDTO.setCreatTime(DateUtil.getTime());
        subjectDTO.setPageId(pageId);
        subjectDTO.setReelId(rId);
        subjectDTO.setCreatTime(DateUtil.getMilliSecondTime());
        //添加题的序号
        //查询页下多少题
        int subjectnum = getNowSubjectnum(pageId);
        subjectDTO.setSubjectnum(subjectnum);
        //选项
        String[] options = request.getParameterValues("optionsArr[]");
        OptionsDTO optionsDTO = new OptionsDTO();
        if (options != null && "2".equals(subjectType)) {
            for (int h = 0; h < options.length; h++) {
                String optionName[] = options[h].split("&&&&");
                subjectDTO.setSubjectType("2");
                optionsDTO.setOptionsId(StringUtil.get32UUID());
                optionsDTO.setOptions(optionName[0]);
                optionsDTO.setSubjectId(subjectId);
                optionsDTO.setReelId(rId);
                optionsDTO.setCreateTime(DateUtil.getMilliSecondTime());
                optionsDTO.setOptionnum(h + 1);
                optionsDTO.setIsMultipleChoice(optionName[1]);
                reelService.opntionsSave(optionsDTO);
            }
        } else if (options != null && "1".equals(subjectType)) {
            for (int h = 0; h < options.length; h++) {
                String optionName[] = options[h].split("&&&&");
                subjectDTO.setSubjectType("1");
                optionsDTO.setOptionsId(StringUtil.get32UUID());
                optionsDTO.setOptions(optionName[0]);
                optionsDTO.setSubjectId(subjectId);
                optionsDTO.setReelId(rId);
                optionsDTO.setCreateTime(DateUtil.getMilliSecondTime());
                optionsDTO.setOptionnum(0);
                optionsDTO.setIsMultipleChoice("3");
                reelService.opntionsSave(optionsDTO);
            }
        } else if ("4".equals(subjectType)) {
                subjectDTO.setSubjectType("4");
                optionsDTO.setOptionsId(StringUtil.get32UUID());
                optionsDTO.setOptions("");
                optionsDTO.setSubjectId(subjectId);
                optionsDTO.setReelId(rId);
                optionsDTO.setCreateTime(DateUtil.getMilliSecondTime());
                optionsDTO.setOptionnum(0);
                optionsDTO.setIsMultipleChoice("2");
                reelService.opntionsSave(optionsDTO);


        }

        reelService.subjectSave(subjectDTO);
        subjectDTO.setPageId(pageId);
        subjectDTO.setReelId(rId);
        return subjectDTO;
    }

    /**
     * @Author: PengSenjie
     * @description: 总题号
     * @Date: Created in 20-3-9 下午4:36
     */
    private int getNowSubjectnum(String pageId) {
        //查题所在的页pagenum
        int mypagenum = 1000;
        /*PageDTO pageDTO = reelService.queryPageByIdAndrId(pageId);
        if(pageDTO != null){
            mypagenum = pageDTO.getPagenum();
        }*/
        //查询新增的题属于这页的序号  addToPageSubject /subjectSave /copySubject /addToPageSubject
        SubjectDTO subjectDTO = reelService.queryMaxSubject(pageId);
        int subjectnum = 1;
        if (subjectDTO != null) {
            subjectnum = subjectnum + subjectDTO.getSubjectnum();
        } else {
            //查当前页
            PageDTO pageDTO = reelService.queryPageByIdAndrId(pageId);
            //不可能未空
            if (pageDTO == null) {
                logger.error("getNowSubjectnum,bu keneng wei kong a.....");
            }
            return pageDTO.getPagenum() + subjectnum;
        }
        return subjectnum;
    }


    /**
     * @Author: PengSenjie
     * @description: 获取当前插入页的pagenum
     * @Date: Created in 20-3-9 下午3:55
     */
    private int getNowPagenum(String rId, int mul) {
        //查询新增的页属于第几页  /addPage /subjectSave /titleSave /endLanguageSave /remarkSave
        PageDTO pageDTO = reelService.queryMaxPage(rId);
        int pagenum = 1000;//初始化,2000,3000...
        if (pageDTO != null) {
            pagenum = pagenum + pageDTO.getPagenum();
        } else {
            pagenum = pagenum * mul;
        }
        return pagenum;
    }

    /**
     * 新增页
     *
     * @param
     * @throws Exception
     */
    @RequestMapping(value = "/addPage")
    @ResponseBody
    public String addPage(HttpServletRequest request) throws Exception {
        request.setCharacterEncoding("utf-8");
        String rId = request.getParameter("rId");
        int pagenum = getNowPagenum(rId, 1);

        //页
        PageDTO pageDTO = new PageDTO();
        String pageId = DateUtil.getCommentId();
        pageDTO.setPageId(pageId);
        pageDTO.setCreateTime(DateUtil.getTime());
        pageDTO.setrId(rId);
        pageDTO.setPagenum(pagenum);
        reelService.pageSave(pageDTO);
        return pageId;
    }

    /**
     * 保存题
     *
     * @param
     * @throws Exception
     */
    @RequestMapping(value = "/addToPageSubject")
    @ResponseBody
    public String addToPageSubject(HttpServletRequest request) throws Exception {
        request.setCharacterEncoding("utf-8");
        String flag = "0";
        String rId = request.getParameter("rId");
        String pageId = request.getParameter("pageId");
        //题
        SubjectDTO subjectDTO = new SubjectDTO();
        String subjectId = StringUtil.get32UUID();
        String topic = request.getParameter("topic");
        String remark = request.getParameter("remark");
        String subjectType = request.getParameter("subjectType");
        String must = request.getParameter("must");
        subjectDTO.setSubjectId(subjectId);
        subjectDTO.setTopic(topic);
        subjectDTO.setRemark(remark);
        subjectDTO.setSubjectType(subjectType);
        subjectDTO.setMust(must);
        subjectDTO.setCreatTime(DateUtil.getTime());
        subjectDTO.setPageId(pageId);
        subjectDTO.setReelId(rId);
        subjectDTO.setCreatTime(DateUtil.getMilliSecondTime());
        //查询页下多少题
        int subjectnum = getNowSubjectnum(pageId);
        subjectDTO.setSubjectnum(subjectnum);
        //选项
        String[] options = request.getParameterValues("optionsArr[]");
        OptionsDTO optionsDTO = new OptionsDTO();
        if (options != null && "2".equals(subjectType)) {
            for (int h = 0; h < options.length; h++) {
                String optionName[] = options[h].split("&&&&");
                subjectDTO.setSubjectType("2");
                optionsDTO.setOptionsId(StringUtil.get32UUID());
                optionsDTO.setOptions(optionName[0]);
                optionsDTO.setSubjectId(subjectId);
                optionsDTO.setReelId(rId);
                optionsDTO.setCreateTime(DateUtil.getMilliSecondTime());
                optionsDTO.setOptionnum(h + 1);
                optionsDTO.setIsMultipleChoice(optionName[1]);
                reelService.opntionsSave(optionsDTO);
            }
        } else if (options != null && "1".equals(subjectType)) {
            for (int h = 0; h < options.length; h++) {
                String optionName[] = options[h].split("&&&&");
                subjectDTO.setSubjectType("1");
                optionsDTO.setOptionsId(StringUtil.get32UUID());
                optionsDTO.setOptions(optionName[0]);
                optionsDTO.setSubjectId(subjectId);
                optionsDTO.setReelId(rId);
                optionsDTO.setCreateTime(DateUtil.getMilliSecondTime());
                optionsDTO.setOptionnum(0);
                optionsDTO.setIsMultipleChoice("3");
                reelService.opntionsSave(optionsDTO);
            }
        } else if ("4".equals(subjectType)) {
                subjectDTO.setSubjectType("4");
                optionsDTO.setOptionsId(StringUtil.get32UUID());
                optionsDTO.setOptions("");
                optionsDTO.setSubjectId(subjectId);
                optionsDTO.setReelId(rId);
                optionsDTO.setCreateTime(DateUtil.getMilliSecondTime());
                optionsDTO.setOptionnum(0);
                optionsDTO.setIsMultipleChoice("2");
                reelService.opntionsSave(optionsDTO);

        }
        ReelDTO reelDTO = new ReelDTO();
        reelDTO.setrId(rId);
        reelDTO.setUpdateTime(DateUtil.getTime());
        reelService.updateReel(reelDTO);//已经有rid了表示现在是修改了。
        reelService.subjectSave(subjectDTO);
        flag = "1";
        return flag;
    }

    /**
     * 删除页
     *
     * @param
     * @throws Exception
     */
    @RequestMapping(value = "/deletePageById")
    @ResponseBody
    public BaseResult deletePageById(HttpServletRequest request) throws Exception {
        BaseResult baseResult = new BaseResult();
        request.setCharacterEncoding("utf-8");
        //String flag="0";

        String pageId = request.getParameter("pageId");
        PageDTO pageDTO = new PageDTO();
        pageDTO.setPageId(pageId);
        try {
            List<SubjectDTO> listSubject = reelService.querySubject(pageId);
            for (int i = 0; i < listSubject.size(); i++) {
                String subjectId = listSubject.get(i).getSubjectId();
                reelService.deleteOptionsById(subjectId);//删除选项数据
            }
            reelService.deleteSubjectById(pageId);//删除题
            reelService.deletePageById(pageId);//删除页
            //flag="1";
            baseResult.setData(listSubject);
            baseResult.setCode(Globals.SUCCESSE);
            baseResult.setMsg(Globals.MSG_XG_SUCCESSE);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return baseResult;
    }

    /**
     * 移除到垃圾篓(移动list页面问卷、移动文件夹包括文件夹下的问卷、移动文件夹下的问卷共用一个ctrl)
     *
     * @param
     * @throws Exception
     */
    @RequestMapping(value = "/removeBasket")
    @ResponseBody
    public String removeBasket(HttpServletRequest request) throws Exception {
        request.setCharacterEncoding("utf-8");
        String flag = "0";

        String rId = request.getParameter("rId");
        String folderId = request.getParameter("folderId");
        if (StringUtils.isNotBlank(rId) && StringUtils.isNotBlank(folderId)) {//都不为空时是文件夹下的问卷移动到垃圾篓
            Map<String, String> mp = new HashMap<String, String>();
            mp.put("rId", rId);
            mp.put("folderId", folderId);
            reelService.removeFolderToBasket(mp);
            flag = "1";
        } else {
            if (StringUtils.isNotBlank(rId)) {//不为空是移动list页面的问卷
                ReelDTO reelDTO = new ReelDTO();
                reelDTO.setrId(rId);
                reelService.removeBasket(reelDTO);
                flag = "1";
            }
            if (StringUtils.isNotBlank(folderId)) {//不为空时移动的是文件夹和文件夹下的问卷
                FolderDTO folderDTO = new FolderDTO();
                folderDTO.setFolderId(folderId);
                reelService.removeFolderBasket(folderDTO);

                //文件夹移动到废纸篓，文件夹下的问卷修改到废纸篓
                List<AllSubjectDTO> allSubjectDTO = new ArrayList<AllSubjectDTO>();
                String delFlag = "0";
                allSubjectDTO = reelService.queryByfolderIdReel(folderId, delFlag);
                if (!allSubjectDTO.isEmpty()) {
                    for (int j = 0; j < allSubjectDTO.size(); j++) {
                        ReelDTO reelDTO = new ReelDTO();
                        rId = allSubjectDTO.get(j).getrId();
                        reelDTO.setrId(rId);
                        reelService.removeBasket(reelDTO);
                    }
                }
                flag = "1";
            }
        }
        return flag;
    }


    /**
     * 从垃圾篓恢复到正常
     *
     * @param
     * @throws Exception
     */
    @RequestMapping(value = "/recoverBasket")
    @ResponseBody
    public String recoverBasket(HttpServletRequest request) throws Exception {
        request.setCharacterEncoding("utf-8");
        String flag = "0";
        String rId = request.getParameter("rId");
        if (StringUtils.isNotBlank(rId)) {
            ReelDTO reelDTO = new ReelDTO();
            reelDTO.setrId(rId);
            reelService.recoverBasket(reelDTO);
            flag = "1";
        } else {
            String folderId = request.getParameter("folderId");
            FolderDTO folderDTO = new FolderDTO();
            folderDTO.setFolderId(folderId);
            reelService.recoverFolderBasket(folderDTO);
            //文件夹恢复正常，文件夹下的问卷修改正常状态
            List<AllSubjectDTO> allSubjectDTO = new ArrayList<AllSubjectDTO>();
            String delFlag = "1";
            allSubjectDTO = reelService.queryByfolderIdReel(folderId, delFlag);
            if (!allSubjectDTO.isEmpty()) {
                for (int j = 0; j < allSubjectDTO.size(); j++) {
                    ReelDTO reelDTO = new ReelDTO();
                    rId = allSubjectDTO.get(j).getrId();
                    reelDTO.setrId(rId);
                    reelService.recoverBasket(reelDTO);
                }
            }
            flag = "1";
        }
        return flag;
    }

    /**
     * 查询废纸篓数据
     *
     * @param request
     * @param rId
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/queryBasketList")
    @ResponseBody
    public ModelAndView queryBasketList(HttpServletRequest request, String rId) throws Exception {
        request.setCharacterEncoding("utf-8");
        ModelAndView mv = new ModelAndView();
        List<FolderDTO> folderDTO = new ArrayList<FolderDTO>();
        if (StringUtils.isBlank(rId)) {
            String delFlag = "";
            HttpSession session = ContextHolderUtils.getSession();
            Client client = ClientManager.getInstance().getClient(session.getId());
            UserDTO user = client.getUser();
            folderDTO = reelService.queryBasketList(delFlag, user.getId());
        }
        mv.setViewName("reel/basket");
        mv.addObject("reelDTO", folderDTO);
        return mv;
    }


    /**
     * 新建文件夹
     *
     * @param
     * @throws Exception
     */
    @RequestMapping(value = "/folderSave")
    @ResponseBody
    public String folderSave(HttpServletRequest request) throws Exception {
        request.setCharacterEncoding("utf-8");
        HttpSession session = ContextHolderUtils.getSession();
        Client client = ClientManager.getInstance().getClient(session.getId());
        UserDTO user = client.getUser();
        String userid = user.getId();
        String flag = "0";
        FolderDTO folderDTO = new FolderDTO();
        String folderId = "folder" + DateUtil.getCommentId();//区分是文件夹id
        String folderName = request.getParameter("folderName");
        folderDTO.setFolderId(folderId);
        folderDTO.setCreateTime(DateUtil.getTime());
        folderDTO.setFolderName(folderName);
        folderDTO.setDelFlag("0");
        folderDTO.setUserId(userid);
        reelService.folderSave(folderDTO);
        flag = "1";
        return flag;
    }


    /**
     * 去垃圾篓列表页面
     *
     * @param user
     * @param request
     * @param session
     * @return
     */
    @RequestMapping(value = "/trashBasket")
    public ModelAndView trashBasket(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView();
        String folderId = request.getParameter("folderId");
        String folderName = request.getParameter("folderName");
        List<AllSubjectDTO> allSubjectDTO = new ArrayList<AllSubjectDTO>();
        HttpSession session = ContextHolderUtils.getSession();
        Client client = ClientManager.getInstance().getClient(session.getId());
        UserDTO user = client.getUser();
        String viewStatus = "";//垃圾篓视图状态
        if (StringUtils.isBlank(folderId)) {//为空时是我的垃圾篓列表
            allSubjectDTO = reelService.queryTrashBasketList(user.getId());
            if (!allSubjectDTO.isEmpty()) {
                for (int j = 0; j < allSubjectDTO.size(); j++) {
                    String rId = allSubjectDTO.get(j).getrId();
                    List<PageDTO> pageDTO = new ArrayList<PageDTO>();
                    pageDTO = reelService.queryPage(rId);
                    allSubjectDTO.get(j).setPageList(pageDTO);
                }
            }
            viewStatus = reelService.queryViewStatus(user.getId(), user.getUsername());
            List<FolderDTO> folderDTO = reelService.queryRemoveFolderList(user.getId());
            mv.addObject("viewStatus", viewStatus);
            mv.addObject("folderDTO", folderDTO);
        } else {//垃圾篓里的文件夹下的列表
            String delFlag = "1";
            allSubjectDTO = reelService.queryByfolderIdReel(folderId, delFlag);
            if (!allSubjectDTO.isEmpty()) {
                for (int j = 0; j < allSubjectDTO.size(); j++) {
                    String rId = allSubjectDTO.get(j).getrId();
                    List<PageDTO> pageDTO = new ArrayList<PageDTO>();
                    pageDTO = reelService.queryPage(rId);
                    allSubjectDTO.get(j).setPageList(pageDTO);
                }
            }
            viewStatus = reelService.queryViewStatus(user.getId(), user.getUsername());
            mv.addObject("viewStatus", viewStatus);
        }

        mv.setViewName("reel/trashBasket");
        mv.addObject("allSubjectDTO", allSubjectDTO);
        mv.addObject("folderId", folderId);
        mv.addObject("folderName", folderName);
        mv.addObject("userId", user.getId());
        return mv;
    }

    private void delReelAanswerRecored(String rid) {
        recordService.deleteRecord(rid);
    }

    private void delGap(String rid) {
        answerService.delGap(rid);
    }


    /**
     * 彻底删除包括卷、页、题、选项
     *
     * @param
     * @throws Exception
     */
    @RequestMapping(value = "/deleteReelById")
    @ResponseBody
    @Log(operateType = "彻底删除问卷")
    public String deleteReelById(HttpServletRequest request) throws Exception {
        request.setCharacterEncoding("utf-8");
        String flag = "0";
        try {
            String rId = request.getParameter("rId");
            ReelDTO reelDTO = new ReelDTO();
            reelDTO.setrId(rId);
            List<PageDTO> pageList = reelService.queryPageRId(rId);
            String pageId = "";
            for (int i = 0; i < pageList.size(); i++) {
                pageId = pageList.get(i).getPageId();
                List<SubjectDTO> listSubject = reelService.querySubject(pageId);
                for (int k = 0; k < listSubject.size(); k++) {
                    String subjectId = listSubject.get(k).getSubjectId();
                    reelService.deleteOptionsById(subjectId);//删除选项数据
                }
                reelService.deleteSubjectById(pageId);//删除题
            }
            reelService.deletePageRId(rId);//删除页
            reelService.deleteReelById(rId);//删除卷
            delReelAanswerRecored(rId);
            delGap(rId);
            flag = "1";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 点击文件夹删除---彻底删除包括卷、页、题、选项
     *
     * @param
     * @throws Exception
     */
    @RequestMapping(value = "/deleteFolder")
    @ResponseBody
    public String deleteFolder(HttpServletRequest request) throws Exception {
        request.setCharacterEncoding("utf-8");
        String flag = "0";
        try {
            String folderId = request.getParameter("folderId");

            ReelDTO reelDTO = new ReelDTO();
            reelDTO.setFolderId(folderId);
            List<ReelDTO> ReelList = reelService.queryReelfolderId(reelDTO);
            String rId = "";
            for (int p = 0; p < ReelList.size(); p++) {
                rId = ReelList.get(p).getrId();
                List<PageDTO> pageList = reelService.queryPageRId(rId);
                String pageId = "";
                for (int i = 0; i < pageList.size(); i++) {
                    pageId = pageList.get(i).getPageId();
                    List<SubjectDTO> listSubject = reelService.querySubject(pageId);
                    for (int k = 0; k < listSubject.size(); k++) {
                        String subjectId = listSubject.get(k).getSubjectId();
                        reelService.deleteOptionsById(subjectId);//删除选项数据
                    }
                    reelService.deleteSubjectById(pageId);//删除题
                }
            }
            reelService.deletePageRId(rId);//删除页
            reelService.deleteReelById(rId);//删除卷
            reelService.deleteFolder(folderId);//删除文件夹
            //todo: 删除答题记录表,现在reel是物理删除
            delReelAanswerRecored(rId);
            delGap(rId);
            flag = "1";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 单独保存题目
     *
     * @param
     * @throws Exception
     */
    @RequestMapping(value = "/titleSave")
    @ResponseBody
    public ReelDTO titleSave(HttpServletRequest request) throws Exception {
        request.setCharacterEncoding("utf-8");
        HttpSession session = ContextHolderUtils.getSession();
        Client client = ClientManager.getInstance().getClient(session.getId());
        UserDTO user = client.getUser();

        String rId = request.getParameter("rId");
        String title = request.getParameter("title");
        String pageId = "";
        pageId = request.getParameter("pageId");
        String folderId = request.getParameter("folderId");
        String startLanguage = request.getParameter("startLanguage");
        String endLanguage = request.getParameter("endLanguage");
        ReelDTO reelDTO = new ReelDTO();

        if (StringUtils.isNotBlank(rId)) {
            reelDTO.setrId(rId);

            if (startLanguage == null) {//新增标题没有新增开始语，给一个默认
                reelDTO.setStartLanguage("为了给您提供更好的服务，希望您能抽出几分钟时间，将您的感受和建议告诉我们,我们非常重视每位用户的宝贵意见，期待您的参与！现在我们就马上开始吧！");
            }
            if (startLanguage != null) {
                reelDTO.setStartLanguage(startLanguage);
            }
            if (title != "") {
                reelDTO.setTitle(title);
            }
            reelService.updateTitleReel(reelDTO);//修改标题
        }
        if (StringUtils.isBlank(rId)) {
            //卷
            rId = DateUtil.getCommentId();
            reelDTO.setrId(rId);
            reelDTO.setCreateTime(DateUtil.getTime());
            reelDTO.setReelStatus("1");//0开始回收 1暂停回收
            reelDTO.setDelFlag("0");//0.正常显1.废纸篓2.删除
            reelDTO.setShowNo("0");
            reelDTO.setAnonymous("1");
            reelDTO.setOnceChance("0");
            reelDTO.setInitiate("1");
            reelDTO.setRetrieve("1");
            reelDTO.setIsLimit("1");
            reelDTO.setEndTime(DateUtil.getTime());
            reelDTO.setSetup("1");
            reelDTO.setUserId(user.getId());
            reelDTO.setFolderId(folderId);
            reelDTO.setEndLanguage(endLanguage);
            if (title != "") {
                reelDTO.setTitle(title);
            }
            if (title == "") {
                reelDTO.setTitle("问卷标题");//新增时如果没新增标题，给一个默认标题

            }
            if (startLanguage == "") {//新增时如果没有新增开始语默认开始语
                reelDTO.setStartLanguage("为了给您提供更好的服务，希望您能抽出几分钟时间，将您的感受和建议告诉我们,我们非常重视每位用户的宝贵意见，期待您的参与！现在我们就马上开始吧！");

            } else {
                reelDTO.setStartLanguage(startLanguage);
            }
            reelDTO.setMyyear(String.valueOf(LocalDate.now().getYear()));
            reelDTO.setBksetting("0");//默认皮肤为0
            reelDTO.setCanal("0");
            reelDTO.setCanalOnline("0");
            reelDTO.setCanalText("在线");
            reelService.reelSave(reelDTO);//新增标题
        }
        if (StringUtils.isBlank(pageId)) {
            int pagenum = getNowPagenum(rId, 1);
            //页
            PageDTO pageDTO = new PageDTO();
            pageId = DateUtil.getCommentId();
            pageDTO.setPageId(pageId);
            pageDTO.setCreateTime(DateUtil.getTime());
            pageDTO.setrId(rId);
            pageDTO.setPagenum(pagenum);
            reelService.pageSave(pageDTO);
        }
        reelDTO.setPageId(pageId);
        ReelDTO reel = reelService.findReelByRid(rId);
        reelDTO.setStartLanguage(reel.getStartLanguage());
        return reelDTO;
    }


    /**
     * 单独保存开始语
     *
     * @param
     * @throws Exception
     */
    @RequestMapping(value = "/remarkSave")
    @ResponseBody
    public ReelDTO remarkSave(HttpServletRequest request) throws Exception {
        request.setCharacterEncoding("utf-8");
        HttpSession session = ContextHolderUtils.getSession();
        Client client = ClientManager.getInstance().getClient(session.getId());
        UserDTO user = client.getUser();
        String title = request.getParameter("title");
        String rId = request.getParameter("rId");
        String pageId = "";
        pageId = request.getParameter("pageId");
        String folderId = request.getParameter("folderId");
        String startLanguage = request.getParameter("startLanguage");
        String endLanguage = request.getParameter("endLanguage");
        ReelDTO reelDTO = new ReelDTO();

        if (StringUtils.isNotBlank(rId)) {
            reelDTO.setrId(rId);
            if (startLanguage != "") {
                reelDTO.setStartLanguage(startLanguage);
                reelService.updateRemarkReel(reelDTO);//修改开始语
            }
        }
        if (StringUtils.isBlank(rId)) {
            //卷
            rId = DateUtil.getCommentId();
            reelDTO.setrId(rId);
            reelDTO.setCreateTime(DateUtil.getTime());
            reelDTO.setReelStatus("1");//0开始回收 1暂停回收
            reelDTO.setDelFlag("0");//0.正常显1.废纸篓2.删除
            reelDTO.setShowNo("0");
            reelDTO.setAnonymous("0");
            reelDTO.setOnceChance("0");
            reelDTO.setInitiate("1");
            reelDTO.setRetrieve("1");
            reelDTO.setIsLimit("1");
            reelDTO.setEndTime(DateUtil.getTime());
            reelDTO.setSetup("1");
            reelDTO.setUserId(user.getId());
            reelDTO.setFolderId(folderId);
            reelDTO.setEndLanguage(endLanguage);
            if (startLanguage != "") {
                reelDTO.setStartLanguage(startLanguage);
            }
            if (title == null) {
                reelDTO.setTitle("问卷标题");//新增时如果没新增标题，给一个默认标题

            }
            if (startLanguage == "") {//新增时如果没有新增开始语默认开始语
                reelDTO.setStartLanguage("为了给您提供更好的服务，希望您能抽出几分钟时间，将您的感受和建议告诉我们,我们非常重视每位用户的宝贵意见，期待您的参与！现在我们就马上开始吧！");

            }
            reelDTO.setMyyear(String.valueOf(LocalDate.now().getYear()));
            reelDTO.setBksetting("0");//默认皮肤为0
            reelDTO.setCanal("0");
            reelDTO.setCanalOnline("0");
            reelDTO.setCanalText("在线");
            reelService.reelSave(reelDTO);//新增标题
        }
        if (StringUtils.isBlank(pageId)) {
            int pagenum = getNowPagenum(rId, 1);
            //页
            PageDTO pageDTO = new PageDTO();
            pageId = DateUtil.getCommentId();
            pageDTO.setPageId(pageId);
            pageDTO.setCreateTime(DateUtil.getTime());
            pageDTO.setrId(rId);
            pageDTO.setPagenum(pagenum);
            reelService.pageSave(pageDTO);
        }
        reelDTO.setPageId(pageId);
        ReelDTO reel = reelService.findReelByRid(rId);
        reelDTO.setTitle(reel.getTitle());
        return reelDTO;
    }

    /**
     * 单独保存结束语
     *
     * @param
     * @throws Exception
     */
    @RequestMapping(value = "/endLanguageSave")
    @ResponseBody
    public ReelDTO endLanguageSave(HttpServletRequest request) throws Exception {
        request.setCharacterEncoding("utf-8");
        HttpSession session = ContextHolderUtils.getSession();
        Client client = ClientManager.getInstance().getClient(session.getId());
        UserDTO user = client.getUser();
        String title = request.getParameter("title");
        String rId = request.getParameter("rId");
        String pageId = "";
        pageId = request.getParameter("pageId");
        String folderId = request.getParameter("folderId");
        String startLanguage = request.getParameter("startLanguage");
        String endLanguage = request.getParameter("endLanguage");
        ReelDTO reelDTO = new ReelDTO();

        if (StringUtils.isNotBlank(rId)) {
            reelDTO.setrId(rId);
            if (startLanguage != "") {
                reelDTO.setStartLanguage(startLanguage);
                reelDTO.setEndLanguage(endLanguage);
                reelService.updateEndLanguageReel(reelDTO);//修改结束语
            }
        }
        if (StringUtils.isBlank(rId)) {
            //卷
            rId = DateUtil.getCommentId();
            reelDTO.setrId(rId);
            reelDTO.setCreateTime(DateUtil.getTime());
            reelDTO.setReelStatus("1");//0开始回收 1暂停回收
            reelDTO.setDelFlag("0");//0.正常显1.废纸篓2.删除
            reelDTO.setShowNo("0");
            reelDTO.setAnonymous("0");
            reelDTO.setOnceChance("0");
            reelDTO.setInitiate("1");
            reelDTO.setRetrieve("1");
            reelDTO.setIsLimit("1");
            reelDTO.setEndTime(DateUtil.getTime());
            reelDTO.setSetup("1");
            reelDTO.setUserId(user.getId());
            reelDTO.setFolderId(folderId);
            reelDTO.setEndLanguage(endLanguage);
            if (startLanguage != "") {
                reelDTO.setStartLanguage(startLanguage);
            }
            if (title == null) {
                reelDTO.setTitle("问卷标题");//新增时如果没新增标题，给一个默认标题

            }
            if (startLanguage == "") {//新增时如果没有新增开始语默认开始语
                reelDTO.setStartLanguage("为了给您提供更好的服务，希望您能抽出几分钟时间，将您的感受和建议告诉我们,我们非常重视每位用户的宝贵意见，期待您的参与！现在我们就马上开始吧！");
            }
            reelDTO.setMyyear(String.valueOf(LocalDate.now().getYear()));
            reelDTO.setBksetting("0");//默认皮肤为0
            reelDTO.setCanal("0");
            reelDTO.setCanalOnline("0");
            reelDTO.setCanalText("在线");
            reelService.reelSave(reelDTO);//新增标题
        }
        if (StringUtils.isBlank(pageId)) {
            int pagenum = getNowPagenum(rId, 1);
            //页
            PageDTO pageDTO = new PageDTO();
            pageId = DateUtil.getCommentId();
            pageDTO.setPageId(pageId);
            pageDTO.setCreateTime(DateUtil.getTime());
            pageDTO.setrId(rId);
            pageDTO.setPagenum(pagenum);
            reelService.pageSave(pageDTO);
        }
        reelDTO.setPageId(pageId);
        ReelDTO reel = reelService.findReelByRid(rId);
        reelDTO.setTitle(reel.getTitle());
        return reelDTO;
    }


    /**
     * 删除一条数据
     *
     * @param
     * @throws Exception
     */
    @RequestMapping(value = "/delSubject")
    @ResponseBody
    public String delSubject(HttpServletRequest request) throws Exception {
        request.setCharacterEncoding("utf-8");
        String flag = "";
        String subjectId = request.getParameter("subjectId");
        reelService.deleteOptionsById(subjectId);
        reelService.deleteSubjectId(subjectId);
        flag = "1";
        return flag;
    }

    /**
     * 根据subjectId查询一条题的数据
     *
     * @param
     * @throws Exception
     */
    @RequestMapping(value = "/querySubjectId")
    @ResponseBody
    public SubjectDTO querySubjectId(HttpServletRequest request) throws Exception {
        request.setCharacterEncoding("utf-8");
        String subjectId = request.getParameter("subjectId");
        SubjectDTO subjectDTO = reelService.querySubjectId(subjectId);
        List<OptionsDTO> optionsDTO = reelService.queryOptions(subjectId);
        subjectDTO.setOptionsList(optionsDTO);
        return subjectDTO;
    }
    /**根据subjectId修改题
     * @param
     * @throws Exception
     */
	/*@RequestMapping(value="/updateSubject")
	@ResponseBody
	public SubjectDTO updateSubject(HttpServletRequest request) throws Exception{
		request.setCharacterEncoding("utf-8");
		String subjectId = request.getParameter("subjectId");
		String topic = request.getParameter("topic");
		String remark = request.getParameter("remark");
		String subjectType = request.getParameter("subjectType");
		String must = request.getParameter("must");
		String rId = request.getParameter("rId");//卷id
		String jsonOptions = request.getParameter("jsonOptions");//
		SubjectDTO subjectDTO =new SubjectDTO();
		try {
			subjectDTO.setSubjectId(subjectId);
			subjectDTO.setTopic(topic);
			subjectDTO.setRemark(remark);
			subjectDTO.setSubjectType(subjectType);
			subjectDTO.setMust(must);
			reelService.updateSubject(subjectDTO);//修改题
			JSONObject jsonObj =new JSONObject();
			com.alibaba.fastjson.JSONArray obj = JSONObject.parseArray(jsonOptions);
			List<OptionsDTO> optionsList =new ArrayList<OptionsDTO>();
			for(int k=0;k<obj.size();k++) {
				jsonObj = (JSONObject) obj.get(k);
				String options = jsonObj.get("options").toString()==null?"":jsonObj.get("options").toString();
				String optionsId = jsonObj.get("optionsId").toString()==null?"":jsonObj.get("optionsId").toString();
				OptionsDTO optionsDTO = new OptionsDTO();
				optionsDTO.setOptions(options);
				optionsDTO.setOptionsId(optionsId);
				if(StringUtils.isNotBlank(optionsId)) {
						reelService.opntionsUpdate(optionsDTO);//修改选项
						optionsList.add(optionsDTO);
				}else {
					if(StringUtils.isNotBlank(options)){
						optionsId = StringUtil.get32UUID();
						optionsDTO.setOptionsId(optionsId);
						optionsDTO.setCreateTime(DateUtil.getMilliSecondTime());
						optionsDTO.setSubjectId(subjectId);
						optionsDTO.setReelId(rId);
						reelService.opntionsSave(optionsDTO);//新增 选项
						optionsList.add(optionsDTO);
					}
				}
				
			}
			subjectDTO.setOptionsList(optionsList);
			String[] deleteOptionsIdArr = request.getParameterValues("deleteOptionsIdArr[]");
			if(deleteOptionsIdArr != null) {
				for(int j=0;j<deleteOptionsIdArr.length;j++) {
					String optionsId = deleteOptionsIdArr[j];
					reelService.deleteOptionsId(optionsId);//删除optios数据
					answerService.deleteAnswerOptionsId(optionsId);//选项已经被删除的，同时删除调查表的数据
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("updateSubject方法修改失败");
		}
		return subjectDTO;
	}*/

    /**
     * 根据subjectId修改题
     *
     * @param
     * @throws Exception
     */
    @RequestMapping(value = "/updateSubject")
    @ResponseBody
    public SubjectDTO updateSubject(HttpServletRequest request, String[] optionsArr) throws Exception {
        request.setCharacterEncoding("utf-8");
        String subjectId = request.getParameter("subjectId");
        String topic = request.getParameter("topic");
        String remark = request.getParameter("remark");
        String subjectType = request.getParameter("subjectType");
        String must = request.getParameter("must");
        String rId = request.getParameter("rId");//卷id
        String jsonOptions = request.getParameter("jsonOptions");//
        SubjectDTO subjectDTO = new SubjectDTO();
        try {
            subjectDTO.setSubjectId(subjectId);
            subjectDTO.setTopic(topic);
            subjectDTO.setRemark(remark);
            subjectDTO.setSubjectType(subjectType);
            subjectDTO.setMust(must);
            reelService.updateSubject(subjectDTO);//修改题

            // 删除这题目下所有选项，rId，subjectId
            reelService.deleteOptionsById(subjectId);//删除选项数据

            //选项
            JSONObject jsonObj =new JSONObject();
            com.alibaba.fastjson.JSONArray obj = JSONObject.parseArray(jsonOptions);
            List<OptionsDTO> optionsList =new ArrayList<OptionsDTO>();
            if (obj != null && "2".equals(subjectType)) {
                for (int h = 0; h < obj.size(); h++) {
                    jsonObj = (JSONObject) obj.get(h);
                    String options = jsonObj.get("options").toString()==null?"":jsonObj.get("options").toString();
                    String optionsId = jsonObj.get("optionsId").toString()==null?"":jsonObj.get("optionsId").toString();
                    String isMultipleChoice = jsonObj.get("isMultipleChoice").toString()==null?"":jsonObj.get("isMultipleChoice").toString();
                    OptionsDTO optionsDTO = new OptionsDTO();
                    subjectDTO.setSubjectType("2");
                    if(StringUtils.isBlank(optionsId)){
                        optionsDTO.setOptionsId(StringUtil.get32UUID());
                    }else {
                        optionsDTO.setOptionsId(optionsId);
                    }
                    optionsDTO.setOptions(options);
                    optionsDTO.setSubjectId(subjectId);
                    optionsDTO.setReelId(rId);
                    optionsDTO.setCreateTime(DateUtil.getMilliSecondTime());
                    optionsDTO.setOptionnum(h + 1);
                    optionsDTO.setIsMultipleChoice(isMultipleChoice);
                    reelService.opntionsSave(optionsDTO);
                    optionsList.add(optionsDTO);
                }
            } else if (obj != null && "1".equals(subjectType)) {
                for (int h = 0; h < obj.size(); h++) {
                    jsonObj = (JSONObject) obj.get(h);
                    String options = jsonObj.get("options").toString()==null?"":jsonObj.get("options").toString();
                    String optionsId = jsonObj.get("optionsId").toString()==null?"":jsonObj.get("optionsId").toString();
                    OptionsDTO optionsDTO = new OptionsDTO();
                    subjectDTO.setSubjectType("1");
                    if(StringUtils.isBlank(optionsId)){
                        optionsDTO.setOptionsId(StringUtil.get32UUID());
                    }else {
                        optionsDTO.setOptionsId(optionsId);
                    }
                    optionsDTO.setOptions(options);
                    optionsDTO.setSubjectId(subjectId);
                    optionsDTO.setReelId(rId);
                    optionsDTO.setCreateTime(DateUtil.getMilliSecondTime());
                    optionsDTO.setOptionnum(0);
                    optionsDTO.setIsMultipleChoice("3");
                    reelService.opntionsSave(optionsDTO);
                    optionsList.add(optionsDTO);
                }
            } else if ("4".equals(subjectType)) {
                    OptionsDTO optionsDTO = new OptionsDTO();
                    subjectDTO.setSubjectType("4");
                    optionsDTO.setOptionsId(StringUtil.get32UUID());
                    optionsDTO.setOptions("");
                    optionsDTO.setSubjectId(subjectId);
                    optionsDTO.setReelId(rId);
                    optionsDTO.setCreateTime(DateUtil.getMilliSecondTime());
                    optionsDTO.setOptionnum(0);
                    optionsDTO.setIsMultipleChoice("2");
                    reelService.opntionsSave(optionsDTO);
                    optionsList.add(optionsDTO);
            }
            subjectDTO.setOptionsList(optionsList);
            String[] deleteOptionsIdArr = request.getParameterValues("deleteOptionsIdArr[]");
            if(deleteOptionsIdArr != null) {
                for(int j=0;j<deleteOptionsIdArr.length;j++) {
                    String optionsId = deleteOptionsIdArr[j];
                    reelService.deleteOptionsId(optionsId);//删除optios数据
                    answerService.deleteAnswerOptionsId(optionsId);//选项已经被删除的，同时删除调查表的数据
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("updateSubject方法修改失败");
        }
        return subjectDTO;
    }

    /**
     * 点击多选框彻底删除包括文件夹、卷、页、题、选项
     *
     * @param
     * @throws Exception
     */
    @RequestMapping(value = "/deleteReel")
    @ResponseBody
    @Log(operateType = "删除问卷")
    public String deleteReel(HttpServletRequest request) throws Exception {
        request.setCharacterEncoding("utf-8");
        String flag = "0";
        try {
            String[] rIdArr = request.getParameterValues("rIdArr[]");
            if (rIdArr != null) {
                String rId = "";
                for (int j = 0; j < rIdArr.length; j++) {
                    rId = rIdArr[j];
                    if (rId.contains("folder")) {//区分是文件夹的id（包括folder是文件夹的id）
                        List<String> rIdList = reelService.queryReelId(rId);
                        if (!rIdList.isEmpty()) {
                            for (int k = 0; k < rIdList.size(); k++) {
                                String reelId = rIdList.get(k);
                                reelService.deleteOptionsReelId(reelId);//删除选项
                                reelService.deleteSubjectReelId(reelId);//删除题
                                reelService.deletePageReelId(reelId);//删除页
                                reelService.deleteReelById(reelId);//删除卷
                                delReelAanswerRecored(rId);
                                delGap(rId);
                            }
                        }
                        reelService.deleteFolder(rId);//删除文件夹 (此时rId的值是folderId)
                    } else {
                        reelService.deleteOptionsReelId(rId);//删除选项
                        reelService.deleteSubjectReelId(rId);//删除题
                        reelService.deletePageReelId(rId);//删除页
                        reelService.deleteReelById(rId);//删除卷
                        delReelAanswerRecored(rId);
                        delGap(rId);
                    }
                }
                flag = "1";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 修改视图状态
     *
     * @param
     * @throws Exception
     */
    @RequestMapping(value = "/updateViewStatus")
    @ResponseBody
    public String updateViewStatus(HttpServletRequest request) throws Exception {
        request.setCharacterEncoding("utf-8");
        String flag = "0";
        try {
            String status = request.getParameter("status");
            String userId = request.getParameter("userId");
            ViewStatusDTO viewStatusDTO = new ViewStatusDTO();
            viewStatusDTO.setStatus(status);
            viewStatusDTO.setUserId(userId);
            reelService.updateViewStatus(viewStatusDTO);
            flag = "1";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 拷贝题
     *
     * @param
     * @throws Exception
     */
    @RequestMapping(value = "/copySubject")
    @ResponseBody
    public SubjectDTO copySubject(HttpServletRequest request) throws Exception {
        request.setCharacterEncoding("utf-8");
        SubjectDTO subjectDTO = new SubjectDTO();
        try {
            String subjectId = request.getParameter("subjectId");//生成之前的题id
            subjectDTO = reelService.querySubjectId(subjectId);
            String newSubjectId = StringUtil.get32UUID();//新生成的题id
            subjectDTO.setSubjectId(newSubjectId);
            subjectDTO.setCreatTime(DateUtil.getTime());
            reelService.subjectSave(subjectDTO);//新增

            List<OptionsDTO> optionsList = reelService.queryOptions(subjectId);
            for (int i = 0; i < optionsList.size(); i++) {
                optionsList.get(i).setOptionsId(StringUtil.get32UUID());
                optionsList.get(i).setSubjectId(newSubjectId);
                reelService.opntionsSave(optionsList.get(i));
            }
            subjectDTO.setOptionsList(optionsList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return subjectDTO;
    }


    /**
     * 根据页查询是否有题
     *
     * @param
     * @throws Exception
     */
    @RequestMapping(value = "/querySubject")
    @ResponseBody
    public String querySubject(HttpServletRequest request) throws Exception {
        request.setCharacterEncoding("utf-8");
        String flag = "0";//没有题
        try {
            String pageId = request.getParameter("pageId");//页id
            List<SubjectDTO> subjectList = reelService.querySubject(pageId);
            if (subjectList.size() > 0) {
                flag = "1";//有题
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }


    /**
     * @detail：统计报表导出Excel表单
     */
    @RequestMapping(value = "poiExport")
    @ResponseBody
    public void retentateTypeExportExcel(HttpServletResponse res, HttpServletRequest req) {
        try {
            String rId = req.getParameter("rId");
            String startTime = req.getParameter("startTime") == null ? "" : req.getParameter("startTime");
            String endTime = req.getParameter("endTime") == null ? "" : req.getParameter("endTime");
            String subjectArr = req.getParameter("subjectArr") == null ? "" : req.getParameter("subjectArr");
            AllSubjectDTO allSubjectDTO = queryCount(rId, startTime, endTime, subjectArr);
            String sheetName = URLEncoder.encode(allSubjectDTO.getTitle() + "_" + allSubjectDTO.getrId() + "", "utf-8");
            ExportExcel e = new ExportExcel();
            e.createFile(allSubjectDTO, sheetName, req, res);
            logger.info("导出Excel表单信息：" + "Excel 导出成功！");
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("导出Excel表单信息：" + "Excel 导出失败！");
        }
    }


    /**
     * @param collectDTO
     * @return
     */
    @PostMapping("/checkUrl")
    public BaseResult checkUrl(@RequestBody BKsettingDTO bKsettingDTO) {
        long timeStart = System.currentTimeMillis();
        String json = JSON.toJSONString(bKsettingDTO);
        logger.info("collect:{}", json);
        String rid = bKsettingDTO.getRid();
        BaseResult baseResult = new BaseResult();
        baseResult.setCode(Globals.SUCCESSE);
        baseResult.setMsg(Globals.MSG_XG_SUCCESSE);
        try {
            List<PageDTO> pageList = reelService.queryPage(rid);
            for (PageDTO pageDTO : pageList) {
                String pageId = pageDTO.getPageId();
                //根据页查找对应的题目
                List<SubjectDTO> subjectList = reelService.querySubject(pageId);
                for (SubjectDTO subjectDTO : subjectList) {
                    String topic = subjectDTO.getTopic();
                    String subjectId = subjectDTO.getSubjectId();
                    if (StringUtils.isNotBlank(topic) && (topic.contains("<img") || topic.contains("<video") || topic.contains("<input") || topic.contains("<a") || topic.contains("<strong"))) {
                        baseResult.setCode(Globals.FIAL);
                        return baseResult;
                    }
                    //根据题目找对应选项
                    List<OptionsDTO> optionsList = reelService.queryOptions(subjectId);
                    for (OptionsDTO optionsDTO : optionsList) {
                        String options = optionsDTO.getOptions();
                        if (StringUtils.isNotBlank(options) && (options.contains("<img") || options.contains("<video") || options.contains("<input") || options.contains("<a") || topic.contains("<strong"))) {
                            baseResult.setCode(Globals.FIAL);
                            return baseResult;
                        }
                    }
                }
            }
        } catch (Exception e) {
            baseResult.setCode(Globals.FIAL);
            baseResult.setMsg(Globals.MSG_XG_FAIL);
        }
        long timeEnd = System.currentTimeMillis();
        logger.info("collect耗时{}毫秒", timeEnd - timeStart);
        return baseResult;
    }

    /**
     * @Author: PengSenjie
     * @description: 文本编辑问卷:自由编辑文本自动生成问卷,点击开始按钮
     * @Date: Created in 20-3-3 上午9:55
     */
    @RequestMapping(value = "/reeltext")
    public ModelAndView reeltext(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView();
        HttpSession session = ContextHolderUtils.getSession();
        Client client = ClientManager.getInstance().getClient(session.getId());
        UserDTO user = client.getUser();
        if (user != null) {
            mv.addObject("userId", user.getId());
        } else {
            logger.info("----------reeltext用户信息为空----------");
        }
        String rId = request.getParameter("rId");
        if (rId == null || "null".equals(rId)) {
            rId = "";
        }
        String reelStatus = Globals.STOP_ONE;
        if (StringUtils.isNotBlank(rId)) {
            AllSubjectDTO allSubjectDTO = reelService.queryReel(rId).get(0);
            reelStatus = allSubjectDTO.getReelStatus();
        }
        //初始化默认值
        String text = "问卷标题(示例)<br>欢迎语(可删除)<br><br>这是单选题的标题[单选题]<br>(这道题的备注说明,可以删除不填写备注)<br>单选题选项1<br>单选题选项2<br><br>这是多选题的标题[多选题]<br>多选题选项1<br>多选题选项2<br>多选题选项3<br><br>" +
                "===分页===<br><br>这是第二页多选题的标题[多选题]<br>多选题选项4<br>多选题选项5<br>多选题选项6<br><br>这是第二页单选题标题[单选题]<br>(这道题的备注说明,可以删除不填写备注)<br>单选题选项3<br>单选题选项4<br><br>===分页===<br>";
        mv.addObject("reelStatus", reelStatus);
        mv.addObject("rId", rId);
        mv.addObject("text", text);
        mv.setViewName("reel/reeltext");
        return mv;
    }


    /**
     * @Author: PengSenjie
     * @description: 文本编辑问卷:自由编辑文本自动生成问卷,点击文本编辑
     * @Date: Created in 20-3-3 上午9:55
     */
    @RequestMapping(value = "/editreeltext")
    public ModelAndView ridreeltext(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView();
        HttpSession session = ContextHolderUtils.getSession();
        Client client = ClientManager.getInstance().getClient(session.getId());
        UserDTO user = client.getUser();
        if (user != null) {
            mv.addObject("userId", user.getId());
        } else {
            logger.info("----------reeltext用户信息为空----------");
        }
        String reelStatus = Globals.STOP_ONE;
        StringBuffer text = new StringBuffer();//文本编辑左侧换行文字数据 /n
        String str = "";
        String rId = request.getParameter("rId");
        if (StringUtils.isNotBlank(rId)) {
            //问卷新增保存后或者问卷编辑跳到文本编辑页
            //反向生成文字版
            //1先查reel
            AllSubjectDTO allSubjectDTO = reelService.queryReel(rId).get(0);
            reelStatus = allSubjectDTO.getReelStatus();
            String title = allSubjectDTO.getTitle();
            String startLanguage = allSubjectDTO.getStartLanguage();
            //String endLanguage = allSubjectDTO.getEndLanguage();
            text.append(title).append("<br>").append(startLanguage).append("<br>");
            List<PageDTO> pageList = reelService.queryPage(rId);
            for (PageDTO pageDTO : pageList) {
                String pageId = pageDTO.getPageId();
                //根据页查找对应的题目
                List<SubjectDTO> subjectList = reelService.querySubject(pageId);
                for (SubjectDTO subjectDTO : subjectList) {
                    String topic = subjectDTO.getTopic();
                    String subjectId = subjectDTO.getSubjectId();
                    String subjectType = subjectDTO.getSubjectType();//题类型  1单选,2多选
                    String remark = subjectDTO.getRemark();
                    //String topicImgUrl = subjectDTO.getTopicImgUrl();//标题中的图片地址
                    //String topicVideoUrl = subjectDTO.getTopicVideoUrl();//标题中的视频路径
                    text.append("<br>").append(topic);
                    if ("2".equals(subjectType)) {
                        text.append("[多选题]").append("<br>");
                    } else {
                        text.append("[单选题]").append("<br>");
                    }
                    //备注
                    if (StringUtils.isNotBlank(remark)) {
                        text.append("(").append(remark).append(")<br>");
                    }
                    //根据题目找对应选项
                    List<OptionsDTO> optionsList = reelService.queryOptions(subjectId);
                    for (OptionsDTO optionsDTO : optionsList) {
                        String options = optionsDTO.getOptions();
                        //String optionImgUrl = optionsDTO.getOptionImgUrl();
                        //String optionVideoUrl = optionsDTO.getOptionVideoUrl();
                        text.append(options).append("<br>");
                    }
                }
                //拼页的标识
                text.append("<br>").append("===分页===").append("<br>");
            }
            str = text.toString();
        } else {
            str = "问卷标题(示例)<br>欢迎语(可删除)<br><br>这是单选题的标题[单选题]<br>(这道题的备注说明,可以删除不填写备注)<br>单选题选项1<br>单选题选项2<br><br>这是多选题的标题[多选题]<br>多选题选项1<br>多选题选项2<br>多选题选项3<br><br>" +
                    "===分页===<br><br>这是第二页多选题的标题[多选题]<br>多选题选项4<br>多选题选项5<br>多选题选项6<br><br>这是第二页单选题标题[单选题]<br>(这道题的备注说明,可以删除不填写备注)<br>单选题选项3<br>单选题选项4<br><br>===分页===<br>";
        }
        logger.info("ridreeltext,查表将数据转换为字符串文本展示,text:{}", text);
        if (rId == null || "null".equals(rId)) {
            rId = "";
        }
        mv.addObject("reelStatus", reelStatus);
        mv.addObject("rId", rId);
        mv.addObject("text", str);
        mv.setViewName("reel/editreeltext");
        return mv;
    }


    /**
     * @Author: PengSenjie
     * @description: 问卷文本自动生成
     * @Date: Created in 20-3-2 下午1:37
     */
    @RequestMapping(value = "/reeltextSave")
    @ResponseBody
    public BaseResult reeltextSave(@RequestBody ReelTextListDTO reelTextListDTO) {
        logger.info("reeltextSave,reelTextListDTO:{}", JSON.toJSONString(reelTextListDTO));
        BaseResult baseResult = new BaseResult();
        HttpSession session = ContextHolderUtils.getSession();
        Client client = ClientManager.getInstance().getClient(session.getId());
        UserDTO user = client.getUser();
        //todo :1.rid为空问卷所有相关的,不为空则修改
        //oldrId后台返回给前台的,前台继续传过来,用来删除
        String oldrId = reelTextListDTO.getRid();
        if (!"".equals(oldrId)) {
            reelService.deleteOptionsReelId(oldrId);//删除选项
            reelService.deleteSubjectReelId(oldrId);//删除题
            reelService.deletePageReelId(oldrId);//删除页
            //reelService.deleteReelById(oldrId);//删除卷
        }

        //2.插入新的数据
        //2.1卷基本参数
        ReelDTO reelDTO = new ReelDTO();
        String rId = DateUtil.getCommentId();
        reelDTO.setrId(rId);
        String reeltitle = "";
        String startLanguage = "";
        String endLanguage = "";
        List<ReelText> list = reelTextListDTO.getList();
        //int uppageIndex =0;//上一个页id
        //int pageIndex =0;//当前页id
        int pageIndex = 1;//当前页数
        String pageId = DateUtil.getCommentId();//页id
        //第一页
        PageDTO pageDTOfirst = new PageDTO();
        pageDTOfirst.setPageId(pageId);
        pageDTOfirst.setCreateTime(DateUtil.getTime());
        int nowPagenum = getNowPagenum(pageId, 1);
        pageDTOfirst.setPagenum(nowPagenum);
        if ("".equals(oldrId)) {
            pageDTOfirst.setrId(rId);
        } else {
            pageDTOfirst.setrId(oldrId);
        }
        reelService.pageSave(pageDTOfirst);
        for (int i = 0; i < list.size(); i++) {
            ReelText reelText = list.get(i);
            String type = reelText.getType();//标识,0问卷标题header,1欢迎语welcome,2题目radio/checkbox,3分页page
            String title = reelText.getTitle();
            //pageIndex = reelText.getPageIndex();
            /*if(pageIndex > uppageIndex){
                pageId = DateUtil.getCommentId();
            }*/
            switch (type) {
                case "header":
                    reeltitle = title;
                    break;
                case "welcome":
                    startLanguage = startLanguage + title;
                    break;
                case "radio":
                case "checkbox":
                    if ("".equals(oldrId)) {
                        addTextSubject(rId, reelText, pageId, type);
                    } else {
                        addTextSubject(oldrId, reelText, pageId, type);
                    }
                    break;
                case "page":
                    //最后一个为分页,那么跳过
                    if (i < list.size() - 1) {
                        pageIndex = pageIndex + 1;
                        PageDTO pageDTO = new PageDTO();
                        pageId = DateUtil.getCommentId();
                        pageDTO.setPageId(pageId);
                        pageDTO.setCreateTime(DateUtil.getTime());
                        int pagenum = getNowPagenum(pageId, pageIndex);
                        pageDTO.setPagenum(pagenum);
                        if ("".equals(oldrId)) {
                            pageDTO.setrId(rId);
                        } else {
                            pageDTO.setrId(oldrId);
                        }
                        reelService.pageSave(pageDTO);
                    }
                    break;
                default:
                    break;
            }
            //uppageIndex = pageIndex;
        }

        reelDTO.setTitle(reeltitle);
        if ("".equals(reeltitle)) {
            //新增时如果没新增标题，给一个默认标题
            reelDTO.setTitle("问卷标题");
        }
        //新增标题没有新增开始语，给一个默认
        reelDTO.setStartLanguage(startLanguage);
        if ("".equals(startLanguage)) {
            reelDTO.setStartLanguage("为了给您提供更好的服务，希望您能抽出几分钟时间，将您的感受和建议告诉我们,我们非常重视每位用户的宝贵意见，期待您的参与！现在我们就马上开始吧！");
        }
        reelDTO.setUpdateTime(DateUtil.getTime());
        if ("".equals(oldrId)) {
            reelDTO.setCreateTime(DateUtil.getTime());
            reelDTO.setReelStatus("1");//0开始回收 1暂停回收
            reelDTO.setDelFlag("0");//0.正常显1.废纸篓2.删除
            reelDTO.setShowNo("0");
            reelDTO.setAnonymous("0");
            reelDTO.setOnceChance("0");
            reelDTO.setInitiate("1");
            reelDTO.setRetrieve("1");
            reelDTO.setIsLimit("1");
            reelDTO.setEndTime(DateUtil.getTime());
            reelDTO.setSetup("1");
            reelDTO.setUserId(user.getId());
            reelDTO.setEndLanguage(endLanguage);
            reelDTO.setFolderId("");
            if ("".equals(endLanguage)) {
                reelDTO.setEndLanguage(" <img src=\"../template/image/end.png\"><span>问卷到此结束，感谢您的参与</span> ");
            }
            reelDTO.setMyyear(String.valueOf(LocalDate.now().getYear()));
            reelDTO.setBksetting("0");//默认皮肤为0
            reelDTO.setCanal("0");
            reelDTO.setCanalOnline("0");
            reelDTO.setCanalText("在线");
            reelService.reelSave(reelDTO);//新增标题
        } else {
            //修改reel表
            reelDTO.setrId(oldrId);
            reelService.updateTitleReel(reelDTO);//修改开始语,标题,时间
        }

        //3.返回问卷id,操作状态
        baseResult.setCode(Globals.SUCCESSE);
        baseResult.setMsg(Globals.MSG_XG_SUCCESSE);
        if ("".equals(oldrId)) {
            baseResult.setData(rId);
        } else {
            baseResult.setData(oldrId);
        }
        logger.info("oldrId:{},rid:{}", oldrId, rId);
        return baseResult;
    }

    private void addTextSubject(String rId, ReelText reelText, String pageId, String type) {
        //2.2题存储
        SubjectDTO subjectDTO = new SubjectDTO();
        String topic = reelText.getTitle();
        String subjectId = StringUtil.get32UUID();
        String subjectType = "";//1单选,2多选
        System.out.println("topic:" + topic);
        if ("radio".equals(type)) {
            //topic = topic.replace("[单选题]","").replace("【单选题】","");
            subjectType = "1";
        } else if ("checkbox".equals(type)) {
            //topic = topic.replace("[多选题]","").replace("【多选题】","");
            subjectType = "2";
        } else {
            //没有题型有错误
            logger.error("addTextSubject no subjectType,topic:{}", topic);
            subjectType = "1";
        }
        subjectDTO.setSubjectId(subjectId);
        subjectDTO.setTopic(topic);
        subjectDTO.setSubjectType(subjectType);
        subjectDTO.setMust("0");//0是 1否
        subjectDTO.setCreatTime(DateUtil.getTime());
        subjectDTO.setPageId(pageId);
        subjectDTO.setReelId(rId);
        subjectDTO.setRemark(reelText.getRemark());
        subjectDTO.setCreatTime(DateUtil.getMilliSecondTime());
        //查询页下多少题
        int subjectnum = getNowSubjectnum(pageId);
        subjectDTO.setSubjectnum(subjectnum);
        //选项
        String[] options = reelText.getOptions();
        if (options != null) {
            for (int h = 0; h < options.length; h++) {
                OptionsDTO optionsDTO = new OptionsDTO();
                optionsDTO.setOptionsId(StringUtil.get32UUID());
                optionsDTO.setOptions(options[h]);
                optionsDTO.setSubjectId(subjectId);
                optionsDTO.setReelId(rId);
                optionsDTO.setCreateTime(DateUtil.getMilliSecondTime());
                optionsDTO.setOptionnum(h + 1);
                reelService.opntionsSave(optionsDTO);
            }
        }
        reelService.subjectSave(subjectDTO);
    }


    /**
     * @Author: PengSenjie
     * @description: 皮肤页面查询状态
     * @Date: Created in 20-3-6 下午3:38
     */
    @PostMapping(value = "/getBksetting")
    public BaseResult getSkin(@RequestBody ReelTextListDTO reelTextListDTO) {
        long timeStart = System.currentTimeMillis();
        String json = JSON.toJSONString(reelTextListDTO);
        logger.info("getBksetting:{}", json);
        BaseResult baseResult = new BaseResult();
        try {
            String rId = reelTextListDTO.getRid();
            AllSubjectDTO allSubjectDTO = reelService.queryReel(rId).get(0);
            logger.info("getBksetting,return:{}", JSON.toJSONString(allSubjectDTO));
            baseResult.setData(allSubjectDTO.getBksetting());
            baseResult.setCode(Globals.SUCCESSE);
            baseResult.setMsg(Globals.MSG_CX_SUCCESSE);
        } catch (Exception e) {
            baseResult.setCode(Globals.FIAL);
            baseResult.setMsg(Globals.MSG_CX_FAIL);
        }
        long timeEnd = System.currentTimeMillis();
        logger.info("getBksetting耗时{}毫秒", timeEnd - timeStart);
        return baseResult;
    }

    /**
     * @Author: PengSenjie
     * @description: 皮肤页面
     * @Date: Created in 20-3-6 下午3:38
     */
    @RequestMapping(value = "/skin")
    public ModelAndView toSetup(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView();
        HttpSession session = ContextHolderUtils.getSession();
        Client client = ClientManager.getInstance().getClient(session.getId());
        UserDTO user = client.getUser();
        if (user != null) {
            mv.addObject("userId", user.getId());
        } else {
            logger.info("----------skin用户信息为空----------");
        }
        String rId = request.getParameter("rId");
        if (rId == null || "null".equals(rId)) {
            rId = "";
        }
        String reelStatus = Globals.STOP_ONE;
        if (StringUtils.isNotBlank(rId)) {
            AllSubjectDTO allSubjectDTO = reelService.queryReel(rId).get(0);
            reelStatus = allSubjectDTO.getReelStatus();
        }
        AllSubjectDTO reelVo = reelService.queryByRIdReel(rId);
        reelVo = reelService.queryReelList(reelVo, rId);
        mv.addObject("reelVo", reelVo);
        mv.addObject("reelStatus", reelStatus);
        mv.addObject("rId", rId);
        mv.setViewName("reel/skin");
        return mv;
    }

    /**
     * @Author: PengSenjie
     * @description: 皮肤设置
     * @Date: Created in 20-3-4 上午11:05
     */
    @PostMapping("/bksetting")
    public BaseResult bksetting(@RequestBody BKsettingDTO bKsettingDTO) {
        long timeStart = System.currentTimeMillis();
        String json = JSON.toJSONString(bKsettingDTO);
        logger.info("bksetting:{}", json);
        BaseResult baseResult = new BaseResult();
        try {
            bKsettingDTO.setUpdateTime(DateUtil.getTime());
            reelService.updateBKsetting(bKsettingDTO);
            baseResult.setCode(Globals.SUCCESSE);
            baseResult.setMsg(Globals.MSG_XG_SUCCESSE);
        } catch (Exception e) {
            baseResult.setCode(Globals.FIAL);
            baseResult.setMsg(Globals.MSG_XG_FAIL);
        }
        long timeEnd = System.currentTimeMillis();
        logger.info("bksetting耗时{}毫秒", timeEnd - timeStart);
        return baseResult;
    }


    /**
     * @Author: PengSenjie
     * @description: 拖拽左侧列表查询
     * @Date: Created in 20-3-10 上午9:18
     */
    @PostMapping("/getDragList")
    public BaseResult getDragList(@RequestBody DragDTO dragDTO) {
        long timeStart = System.currentTimeMillis();
        String json = JSON.toJSONString(dragDTO);
        logger.info("getDragList:{}", json);
        BaseResult baseResult = new BaseResult();
        try {
            String rid = dragDTO.getRid();
            //查询页题
            List<PageDTO> pageList = reelService.queryPage(rid);
            for (PageDTO pageDTO : pageList) {
                String pageId = pageDTO.getPageId();
                //根据页查找对应的题目
                List<SubjectDTO> subjectList = reelService.querySubject(pageId);
                pageDTO.setSubjectList(subjectList);
            }
            baseResult.setData(pageList);
            baseResult.setCode(Globals.SUCCESSE);
            baseResult.setMsg(Globals.MSG_CX_SUCCESSE);
        } catch (Exception e) {
            baseResult.setCode(Globals.FIAL);
            baseResult.setMsg(Globals.MSG_CX_FAIL);
        }
        long timeEnd = System.currentTimeMillis();
        logger.info("getDragList耗时{}毫秒", timeEnd - timeStart);
        return baseResult;
    }


    /**
     * @Author: PengSenjie
     * @description: 拖拽关系修改
     * @Date: Created in 20-3-10 上午10:12
     */
    @PostMapping("/updateDrag")
    public BaseResult updateDrag(@RequestBody DragDTO dragDTO) {
        long timeStart = System.currentTimeMillis();
        String json = JSON.toJSONString(dragDTO);
        logger.info("updateDrag:{}", json);
        BaseResult baseResult = new BaseResult();
        try {
            String rid = dragDTO.getRid();
            List<PageDTO> pageDTOList = dragDTO.getPageDTOList();
            int pagenum = 1000;//初始化,2000,3000...
            for (int i = 0; i < pageDTOList.size(); i++) {
                PageDTO pageDTO = pageDTOList.get(i);
                String pageId = pageDTO.getPageId();
                //int pagenum = pageDTO.getPagenum();
                //重新生成页序号
                pagenum = (i + 1) * pagenum;
                PageDTO newpageDTO = new PageDTO();
                newpageDTO.setPagenum(pagenum);
                newpageDTO.setrId(rid);
                newpageDTO.setPageId(pageId);
                reelService.updatePageNum(newpageDTO);
                List<SubjectDTO> subjectList = pageDTO.getSubjectList();
                for (int j = 0; j < subjectList.size(); j++) {
                    //String subjectId = subjectDTO.getSubjectId();
                    SubjectDTO subjectDTO = subjectList.get(j);
                    //循环重新修改subjectnum
                    subjectDTO.setUpdatetime(DateUtil.getMilliSecondTime());
                    subjectDTO.setSubjectnum(pagenum + j + 10);
                    subjectDTO.setPageId(pageId);
                    subjectDTO.setReelId(rid);
                    reelService.updateSubjectNum(subjectDTO);
                }
            }


            baseResult.setCode(Globals.SUCCESSE);
            baseResult.setMsg(Globals.MSG_XG_SUCCESSE);
        } catch (Exception e) {
            baseResult.setCode(Globals.FIAL);
            baseResult.setMsg(Globals.MSG_XG_FAIL);
        }
        long timeEnd = System.currentTimeMillis();
        logger.info("updateDrag耗时{}毫秒", timeEnd - timeStart);
        return baseResult;
    }


    /**
     * 生成填空主键id
     *
     * @param
     * @throws Exception
     */
    @RequestMapping(value = "/createFillinId")
    @ResponseBody
    public String createFillinId(HttpServletRequest request) throws Exception {
        String fillinId = DateUtil.getCommentId();
        return fillinId;
    }


    /**
     * 插入填空设置
     *
     * @param
     * @throws Exception
     */
    @RequestMapping(value = "/addGapFilling")
    @ResponseBody
    public String addGapFilling(HttpServletRequest request) throws Exception {

        GapFillingDTO gapFillingDTO = new GapFillingDTO();
        String fillinId = request.getParameter("fillinId");
        String textType = request.getParameter("textType");
        String atMostChar = request.getParameter("atMostChar");
        String mustFillIn = request.getParameter("mustFillIn");
        DateUtil.getTime();
        return fillinId;
    }

    /**
     * 上传视频
     *
     * @param user
     * @param request
     * @param session
     * @return
     */
    @RequestMapping(value = "/uploadVdieo")
    @ResponseBody
    public JSONObject uploadVdieo(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "videoFile", required = true) MultipartFile videoFiles) {
        JSONObject json = new JSONObject();
        if (!videoFiles.isEmpty() && null != videoFiles) {
            //上传图标
            String filePath = PathUtil.getClasspath() + Const.FILEPATHMODULEVIDEO + DateUtil.getDays(); // 文件上传路径
            String fileName = FileUpload.fileUp(videoFiles, filePath, DateUtil.get32UUID()); // 执行上传
            //路径保存的是uploadFiles/uploadImgs/+当前日期+文件名称
            String path = request.getContextPath();
            String basePath = request.getScheme() + "://"
                    + request.getServerName() + ":" + request.getServerPort()
                    + path;
            String videoAddress = basePath + "/" + Const.FILEPATHMODULEVIDEO + DateUtil.getDays() + "/" + fileName;
            //String video ="<video src='"+videoAddress+"' width='60%' controls='controls'></video>";
            String video = "<video src='" + videoAddress + "' controls='controls' width='30%'></video>";
            json.put("video", video);
        }
        return json;
    }

    /**
     * 查询未删除所有文件夹的名字
     *
     * @param
     * @throws Exception
     */
    @RequestMapping(value = "/queryAllFolder")
    @ResponseBody
    public List<FolderDTO> queryAllFolder(HttpServletRequest request) throws Exception {
        String delFlag = request.getParameter("delFlag");
        HttpSession session = ContextHolderUtils.getSession();
        Client client = ClientManager.getInstance().getClient(session.getId());
        UserDTO user = client.getUser();
        List<FolderDTO> folerNameList = reelService.queryAllFolder(delFlag, user.getId());
        return folerNameList;
    }

    /**
     * 将选中的卷移动到文件夹下
     *
     * @param
     * @throws Exception
     */
    @RequestMapping(value = "/moveToFolder")
    @ResponseBody
    public String moveToFolder(HttpServletRequest request) throws Exception {
        String folderId = request.getParameter("folderId");
        String rId = request.getParameter("rId");
        Map<String, String> mp = new HashMap<String, String>();
        mp.put("folderId", folderId);
        mp.put("rId", rId);
        String flag = "0";
        try {
            reelService.moveToFolder(mp);
            flag = "1";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 将文件夹下的问卷移出
     *
     * @param
     * @throws Exception
     */
    @RequestMapping(value = "/shiftOutFolder")
    @ResponseBody
    public String shiftOutFolder(HttpServletRequest request) throws Exception {
        String rId = request.getParameter("rId");
        String flag = "0";
        try {
            reelService.shiftOutFolder(rId);
            flag = "1";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 将文件夹下的问卷移出
     *
     * @param
     * @throws Exception
     */
    @RequestMapping(value = "/shiftOutTrashBasketFolder")
    @ResponseBody
    public String shiftOutTrashBasketFolder(HttpServletRequest request) throws Exception {
        String rId = request.getParameter("rId");
        String flag = "0";
        try {
            Map<String, String> mp = new HashMap<String, String>();
            String delFlag = request.getParameter("delFlag");
            mp.put("rId", rId);
            mp.put("delFlag", delFlag);
            reelService.shiftOutTrashBasketFolder(mp);
            flag = "1";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 查询题和选项
     *
     * @param
     * @throws Exception
     */
    @RequestMapping(value = "/querySubjectAndOptions")
    @ResponseBody
    public List<SubjectDTO> querySubjectAndOptions(HttpServletRequest request) throws Exception {
        String rId = request.getParameter("rId");
        List<SubjectDTO> subjectList = new ArrayList<SubjectDTO>();
        try {
            subjectList = reelService.queryPageAndSubect(rId);//题的数据
            for (int i = 0; i < subjectList.size(); i++) {
                String subjectId = subjectList.get(i).getSubjectId();
                List<OptionsDTO> optionsList = reelService.queryOptions(subjectId);
                subjectList.get(i).setOptionsList(optionsList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return subjectList;
    }


}
