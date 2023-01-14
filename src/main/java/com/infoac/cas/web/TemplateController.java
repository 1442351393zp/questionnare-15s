package com.infoac.cas.web;

import com.infoac.cas.dto.*;
import com.infoac.cas.entity.Client;
import com.infoac.cas.entity.ReelVO;
import com.infoac.cas.service.ReelService;
import com.infoac.cas.service.TemplateService;
import com.infoac.cas.util.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.List;

//import com.infoac.cas.dto.TemplateDTO;

/**
 * @author zp
 * 创建模板的问卷调查
 */
@CrossOrigin
@RestController
@RequestMapping("/template")
public class TemplateController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private TemplateService templateService;
    @Autowired
    private ReelService reelService;

    /*
     *模板页面
     */
    @RequestMapping(value = "/template", produces = {"application/json;charset=UTF-8"})
    public ModelAndView template(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("reel/template");
        return mv;
    }

    //  /*
//   * 模板列表的展示
//   */
//  @RequestMapping(value = "/templatelist",produces= {"application/josn;charset=UTF-8"})
//  @ResponseBody
//   public String savetemplate() throws Exception{
//	  JSONObject  json  = new JSONObject();
//	  try {
//			 List <TemplateDTO> templates =  templateService.templatelist();
//			 JSONArray jsonArray = JSONArray.fromObject(templates);
//			 json.put("code", Globals.SUCCESSE);
//		     json.put("msg",  Globals.MSG_CZ_SUCCESSE);
//		     json.put("data", jsonArray);
//		  }catch (Exception e) {
//			  json.put("code", Globals.FIAL);
//			  json.put("msg",  Globals.MSG_CZ_FAIL);
//		 }
//		  return json.toString();
//       }
//	/*
//	 * 模板列表的添加
//	 */
//  @RequestMapping(value = "/addtemplate",produces= {"application/json;charset=UTF-8"})
//  @ResponseBody
//  public String addtemplate(TemplateDTO template,int type) throws Exception{
//	  JSONObject json = new JSONObject();
//		 try {
//			 if(template!=null) {
//			    if(type==0) {
//				    //编辑模板节点
//			    	templateService.updateTemplate(template);
//			    }else if(type ==1) {
//				    //增加模板节点
//			    	templateService.addperTemplate(template);
//			   }
//			 }
//			json.put("code", "0");
//			json.put("msg", "设置模板成功");
//		} catch (Exception e) {
//			e.printStackTrace();
//			json.put("code", "1");
//			json.put("msg", "设置模板失败");
//		}
//
//
//		 return json.toString();
//
//   }
//    /*
//	  * 获取列表数据
//	  */
//	 @RequestMapping(value = "/findTemplate",produces= {"application/json;charset=UTF-8"})
//	 @ResponseBody
//		public String findPermission(HttpServletRequest request, String typeid ) throws Exception{
//			JSONObject json = new JSONObject();
//			TemplateDTO template= templateService.findTemplate(typeid);
//	    	if(template != null) {
//	    		json.put("code", 0);
//	    		json.put("permission", template);
//	    	}else {
//	    		json.put("code", 1);
//	    	}
//	    	return json.toString();
//	    }
//	 /*
//	  *
//	  * 删除模板列列表
//	  *
//	  */
//	 @RequestMapping(value = "/deleteTemplate",produces= {"application/json;charset=UTF-8"})
//	 @ResponseBody
//	 public String deleteTemplate(String typeid) {
//		 JSONObject json = new JSONObject();
//		 boolean flag = templateService.deleteTemplate(typeid);
//		 if(flag) {
//			  json.put("code", "0");
//			  json.put("msg", "删除成功");
//		 }else {
//			 json.put("code", "1");
//			 json.put("msg", "删除失败 查看是否有子节点");
//		 }
//		 return json.toString();
//	 }
    /*
     * 选择模板类型
     * 展示效果是树形结构的数据 ,展示查询到后台的所有模板问卷的数据
     */
    @RequestMapping(value = "/choiceTemplate", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String choiceTemplate() throws Exception {
        JSONObject json = new JSONObject();
        try {
            //查询到所有的数据,没有装换成树结构
            List<TemplateVO> templates = templateService.templatelists();
            //数机构数据的展示
            List<TemplateVO> trperVOS = TreeBuliderTemplate.buildByRecursive(templates);
            JSONArray jsonArray = JSONArray.fromObject(trperVOS);
            json.put("code", Globals.SUCCESSE);
            json.put("msg", Globals.MSG_CZ_SUCCESSE);
            json.put("data", jsonArray);
        } catch (Exception e) {
            json.put("code", Globals.FIAL);
            json.put("msg", Globals.MSG_CZ_FAIL);
        }

        return json.toString();
    }
    /* 保存问卷为模板问卷
     * rid:卷id
     * typeid:模板问卷对应的类型id
     * istype:模板问卷的标志（"1"）
     */
    @RequestMapping(value = "/svaeTemple", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String svaeTemple(HttpServletRequest request,String rid,String typeid,String istype){
        String Rid =request.getParameter("rid");
        String Typeid = request.getParameter("typeid");
        String isType = request.getParameter("istype");
        ReelDTO reelDTO =new ReelDTO();
        reelDTO.setrId(Rid);
        reelDTO.setIstype(isType);
        reelDTO.setTypeid(Typeid);
        JSONObject json = new JSONObject();
        if(Rid!=null&&Typeid!=null){
            templateService.svaeTemple(reelDTO);
            json.put("code", Globals.SUCCESSE);
            json.put("msg", Globals.MSG_CZ_SUCCESSE);
        }else {
            json.put("code", Globals.FIAL);
            json.put("msg", Globals.MSG_CZ_FAIL);
        }
        return json.toString();

    }


    /*
     * 模板列表的展示
     * 展示模板问卷所属分类就是最左边
     * 根据模板分类展示模板问卷也就是 中间和右边的类容
     */
    @RequestMapping(value = "/templatelists", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String templateLists(ReelVO reelVo) {
        JSONObject json = new JSONObject();
        if (reelVo.getTypeid()!= null) {
            //获取对应模板类型的数据
            List<ReelVO> reelList = templateService.templateLists(reelVo);
            Long  count =  templateService.findTemplateCount(reelVo);
            JSONArray jsonArray = JSONArray.fromObject(reelList);
            json.put("code", Globals.SUCCESSE);
            json.put("msg", Globals.MSG_CZ_SUCCESSE);
            json.put("data", jsonArray);
            json.put("count", count);
        } else {
            json.put("code", Globals.FIAL);
            json.put("msg", Globals.MSG_CZ_FAIL);
        }

        return json.toString();
    }
	 /*
	  模板问卷题目的展示
	  */
    @RequestMapping(value = "/get_template", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String get_template(HttpServletRequest request,HttpServletResponse response,String reelId){
        String userId =request.getParameter("userId");
        String startTime =request.getParameter("startTime");
        String endTime =request.getParameter("endTime");
        JSONObject json = new JSONObject();
        if(reelId!=null){
//            List<SubjectDTO> subjectlist = templateService.subjectlist(reelId);
            AllSubjectDTO allSubjectDTO =new AllSubjectDTO();
            allSubjectDTO = templateService.queryReelLists(allSubjectDTO,reelId,userId,startTime,endTime);
            JSONArray jsonArray = JSONArray.fromObject(allSubjectDTO);
            json.put("code", Globals.SUCCESSE);
            json.put("msg", Globals.MSG_CZ_SUCCESSE);
            json.put("data", jsonArray);

        }else{
            json.put("code", Globals.FIAL);
            json.put("msg", Globals.MSG_CZ_FAIL);
        }
        return json.toString();
    }
    /*
     模板搜索
     */
    @RequestMapping(value = "/template_name", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
     public String template_name(ReelVO reelVo){
        JSONObject json = new JSONObject();
        List<ReelVO> reelList = templateService.templateName(reelVo);
        JSONArray jsonArray = JSONArray.fromObject(reelList);
        json.put("data", jsonArray);
        json.put("code", Globals.SUCCESSE);
        json.put("msg", Globals.MSG_CZ_SUCCESSE);
        return json.toString();
    }
    /*
      使用模板创建问卷（卷(rid)，页(rid)，题(reelid)，选项（reelid））
     */
    @RequestMapping(value = "/editTemplate", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String editTemplate(String reelid){
        //从session中获取用户
        HttpSession session = ContextHolderUtils.getSession();
        Client client = ClientManager.getInstance().getClient(session.getId());
        UserDTO user = client.getUser();
        JSONObject json = new JSONObject();

        if(reelid!=null){
             //将模板卷的信息查出来
            ReelDTO reelTemplste =  templateService.addTempldateReel(reelid);
            //将模板卷的信息以新的记录保存进去
            ReelDTO reel = new ReelDTO();
            String rid = DateUtil.getCommentId();
            reel.setrId(rid);
            reel.setTitle(reelTemplste.getTitle());
            reel.setStartLanguage(reelTemplste.getStartLanguage());
            reel.setEndLanguage(reelTemplste.getEndLanguage());
            reel.setCreateTime(DateUtil.getTime());
            reel.setReelStatus("1");//0开始回收 1暂停回收
            reel.setDelFlag("0");//0.正常显1.废纸篓2.删除
            reel.setShowNo("0");
            reel.setAnonymous("0");
            reel.setOnceChance("0");
            reel.setInitiate("1");
            reel.setRetrieve("1");
            reel.setIsLimit("1");
            reel.setEndTime(DateUtil.getTime());
            reel.setSetup("1");
            reel.setFolderId("");
            reel.setUserId(user.getId());
            String year = String.valueOf(LocalDate.now().getYear());
            reel.setMyyear(year);
            reel.setBksetting("0");//默认皮肤为0
            reel.setCanal("0");
            reel.setCanalOnline("0");
            reel.setCanalText("在线");
            templateService.addReel(reel);
            //查询模板页的信息
            List<PageDTO> pageDTO = templateService.selectPageTempleate(reelid);
            //将模板页的信息插入表中
            for (PageDTO pageDT:pageDTO) {
                String pages = DateUtil.getCommentId();
                PageDTO page = new PageDTO();
                page.setPageId(pages);
                page.setCreateTime(DateUtil.getTime());
                page.setrId(rid);
                page.setPagenum(pageDT.getPagenum());
                templateService.addPageTempleate(page);
                //插入页下所有的题
                List<SubjectDTO> subjectDTO = templateService.selectSubjectTemplate(reelid,pageDT.getPageId());
                //将模板类容以新的数据插入表中
                for (SubjectDTO subjectDT:subjectDTO) {
                    String subjectID = StringUtil.get32UUID();
                    SubjectDTO subject = new SubjectDTO();
                    subject.setSubjectId(subjectID);
                    subject.setTopic(subjectDT.getTopic());
                    subject.setRemark(subjectDT.getRemark());
                    subject.setSubjectType(subjectDT.getSubjectType());
                    subject.setMust(subjectDT.getMust());
                    subject.setCreatTime(DateUtil.getMilliSecondTime());
                    subject.setPageId(pages);
                    subject.setReelId(rid);
                    subject.setSubjectnum(subjectDT.getSubjectnum());
                    templateService.addSubjectTemplate(subject);
                    //将模板选项查出来
                    List<OptionsDTO> optionsDTO = templateService.selectOptionsTemplate(reelid,subjectDT.getSubjectId());
                    //将模板数据插入表中生成新的数据
                    for (OptionsDTO optionsDT:optionsDTO) {
                        OptionsDTO options = new OptionsDTO();
                        options.setOptionsId(StringUtil.get32UUID());
                        options.setOrderId(optionsDT.getOrderId());
                        options.setOptions(optionsDT.getOptions());
                        options.setSubjectId(subjectID);
                        options.setOptionImgUrl(optionsDT.getOptionImgUrl());
                        options.setOptionVideoUrl(optionsDT.getOptionVideoUrl());
                        options.setReelId(rid);
                        options.setOptionnum(optionsDT.getOptionnum());
                        options.setCreateTime(DateUtil.getMilliSecondTime());
                        templateService.addOptionsTemplate(options);
                    }

                }
            }
            json.put("code", Globals.SUCCESSE);
            json.put("data",rid);
            json.put("msg", Globals.MSG_CZ_SUCCESSE);
        }else{
                json.put("code", Globals.FIAL);
                json.put("msg", Globals.MSG_CZ_FAIL);
        }
        return json.toString();
    }
}




