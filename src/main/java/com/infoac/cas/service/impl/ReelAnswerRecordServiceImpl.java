package com.infoac.cas.service.impl;

import com.alibaba.fastjson.JSON;
import com.infoac.cas.dao.AnswerDao;
import com.infoac.cas.dao.ReelAnswerRecordDao;
import com.infoac.cas.dao.ReelDao;
import com.infoac.cas.dto.*;
import com.infoac.cas.service.IReelAnswerRecordService;
import com.infoac.cas.util.Const;
import com.infoac.cas.util.DateUtil;
import com.infoac.cas.util.StringUtil;
import com.infoac.cas.util.SummaryUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;

@Service
public class ReelAnswerRecordServiceImpl implements IReelAnswerRecordService {

    @Resource
    private ReelAnswerRecordDao reelAnswerRecordDao;
    @Resource
    private ReelDao reelDao;
    @Autowired
    private IReelAnswerRecordService reelAnswerRecordService;
    @Autowired
    private AnswerDao answerDao;

    @Override
    public Integer saveReelAnswerRecord(ReelAnswerRecordDTO answer) {
        return reelAnswerRecordDao.saveReelAnswerRecord(answer);
    }

    @Override
    public List<ReelAnswerRecordDTO> listReelAnswerRecord(String reelId) {
        return reelAnswerRecordDao.listReelAnswerRecord(reelId , null);
    }

    @Override
    public String getReelAnswerAverageTimeConsuming(String reelId) {
        return reelAnswerRecordDao.getReelAnswerAverageTimeConsuming(reelId);
    }

    @Override
    public int getReceivedCount(String reelId) {
        return reelAnswerRecordDao.getReceivedCount(reelId);
    }

    @Override
    public List<ReceiveLineChartDTO> receiveLineChart(String reelId) {
        return reelAnswerRecordDao.receiveLineChart(reelId);
    }

    @Override
    public int updateAnwserRecord(ReelAnswerRecordDTO record) {
        return reelAnswerRecordDao.updateAnwserRecord(record);
    }

    @Override
    public Map<String,Object> listReceivedTableData(String reelId , String startTime ,String endTime , int pageSize) {
        Map<String,Object> table = new HashMap<>();
        //表头
        List<ColumnsDTO> head = new ArrayList<>();
        //行数据  key:value = anwserRecordId:所有topic
        //必须按查询结果顺序存放
        Map<String,List<ColumnsDTO>> body = new LinkedHashMap<>();
        List<List<ColumnsDTO>> bodyList = new ArrayList<>();
        List<String> toNowYearList = DateUtil.get2019ToNowYearList(new Date());
        //查询问卷回答记录
        List<ReceivedDataInfo> receivedDataInfos = listReceivedTableData(toNowYearList, reelId , startTime , endTime , pageSize);
        //查询结果必须是一个问卷的所有答题信息，不可以多个
        if(!CollectionUtils.isEmpty(receivedDataInfos)){
            for (ReceivedDataInfo receivedDataInfo : receivedDataInfos) {
                String answerRecordId = receivedDataInfo.getAnswerRecordId();
                List<ColumnsDTO> rowDatas = body.get(answerRecordId);
                if(CollectionUtils.isEmpty(rowDatas)){
                    rowDatas = new ArrayList<>();
                    addFixedTitleColumns(rowDatas,receivedDataInfo);
                    body.put(answerRecordId,rowDatas);
                }
                //添加题目列
                ColumnsDTO topicColumn = new ColumnsDTO();
                topicColumn.setColumnId(receivedDataInfo.getSubjectId());
                topicColumn.setColumnName(SummaryUtil.spiltByLabel(receivedDataInfo.getTopic()));
                topicColumn.setColumnValueId(receivedDataInfo.getOptionsId());
                String optionsIds = receivedDataInfo.getOptionsId();
                String options =null;
                if(StringUtils.isNotBlank(receivedDataInfo.getOptions())){
                     options = SummaryUtil.spiltByLabel(receivedDataInfo.getOptions());
                }else{
                     options = receivedDataInfo.getOptions();
                }

                topicColumn.setColumnValueName(options);
                if(StringUtils.isNotBlank(optionsIds)){
                    String[] split = optionsIds.split(",");
                    //topicColumn.setColumnValueName(options);
                    List<String> strings2 = new ArrayList<>();
                    for (String s : split) {
                        GapFillingDTO gap = new GapFillingDTO();
                        gap.setUserId(receivedDataInfo.getUserId());
                        gap.setrId(reelId);
                        gap.setRecordId(receivedDataInfo.getAnswerRecordId());
                        gap.setSubjectId(receivedDataInfo.getSubjectId());
                        gap.setType("2");
                        gap.setOptionsId(s);
                        GapFillingDTO gapFillingDTO = answerDao.listGap(gap);
                        if(gapFillingDTO!=null){
                            String content1 = gapFillingDTO.getContent();
                            if(StringUtils.isNotBlank(content1)){
                                List<String> strings3 = JSON.parseArray(content1, String.class);
                                strings2.addAll(strings3);
                            }
                        }
                    }
                    String newtopic = StringUtil.gapText(options,strings2);
                    if("2".equals(receivedDataInfo.getSubjecttype())){
                        if("0".equals(receivedDataInfo.getIsMultipleChoice())){
                            newtopic =  receivedDataInfo.getTextAnswer();
                        }
                    }else if ("4".equals(receivedDataInfo.getSubjecttype())){
                        //文本
                        newtopic = receivedDataInfo.getTextAnswer();
                    }
                    topicColumn.setColumnValueName(newtopic);

                }
                topicColumn.setDisplay(Const.COLUMN_DISPLAY_SHOW);
                topicColumn.setFixed(Const.COLUMN_DYNAMIC);
                rowDatas.add(topicColumn);
            }
        }

        for (String s : body.keySet()) {
            bodyList.add(body.get(s));
        }
        //处理标题列
        if(bodyList.size() > 0) {
            head.addAll(bodyList.get(0));
        }else{
            //添加固定列
            addFixedTitleColumns(head,null);
            //没有答题记录的情况下单独重查标题列
            List<SubjectDTO> subjectDTOS = reelDao.querySubjectListByReelId(reelId);
            if(!CollectionUtils.isEmpty(subjectDTOS)){
                //添加题目列
                for (SubjectDTO subjectDTO : subjectDTOS) {
                    ColumnsDTO topicColumn = new ColumnsDTO();
                    topicColumn.setColumnId(subjectDTO.getSubjectId());
                    topicColumn.setColumnName(subjectDTO.getTopic());
//                    topicColumn.setColumnValueId(subjectDTO.getOptionsId());
//                    topicColumn.setColumnValueName(subjectDTO.getOptions());
                    topicColumn.setDisplay(Const.COLUMN_DISPLAY_SHOW);
                    topicColumn.setFixed(Const.COLUMN_DYNAMIC);
                    head.add(topicColumn);
                }
            }
        }

        AllSubjectDTO allSubjectDTO = reelDao.queryByRIdReel(reelId);
        int receivedCount = reelAnswerRecordService.getReceivedCount(reelId);
        table.put("head",head);
        table.put("body",bodyList);
        table.put("title",allSubjectDTO.getTitle());
        table.put("receivedCount",receivedCount);
        return table;
    }

    private List<ColumnsDTO> addFixedTitleColumns(List<ColumnsDTO> rowDatas,ReceivedDataInfo receivedDataInfo){
        ColumnsDTO answerRecordColumn = new ColumnsDTO();
        answerRecordColumn.setColumnId("answerRecordId");
        answerRecordColumn.setColumnName("答题记录编号");
        if(receivedDataInfo != null){
            answerRecordColumn.setColumnValueId(receivedDataInfo.getAnswerRecordId());
            answerRecordColumn.setColumnValueName(receivedDataInfo.getAnswerRecordId());
        }
        answerRecordColumn.setDisplay(Const.COLUMN_DISPLAY_HIDDEN);
        answerRecordColumn.setFixed(Const.COLUMN_FIXED);
        rowDatas.add(answerRecordColumn);
        ColumnsDTO reelIdColumn = new ColumnsDTO();
        reelIdColumn.setColumnId("reelId");
        reelIdColumn.setColumnName("问卷编号");
        if(receivedDataInfo != null){
            reelIdColumn.setColumnValueId(receivedDataInfo.getReelId());
            reelIdColumn.setColumnValueName(receivedDataInfo.getReelId());
        }
        reelIdColumn.setDisplay(Const.COLUMN_DISPLAY_HIDDEN);
        reelIdColumn.setFixed(Const.COLUMN_FIXED);
        rowDatas.add(reelIdColumn);
        ColumnsDTO startTimeColumn = new ColumnsDTO();
        startTimeColumn.setColumnId("startTime");
        startTimeColumn.setColumnName("开始答题时间");
        if(receivedDataInfo != null){
            startTimeColumn.setColumnValueId(receivedDataInfo.getStartTime());
            startTimeColumn.setColumnValueName(receivedDataInfo.getStartTime());
        }
        startTimeColumn.setDisplay(Const.COLUMN_DISPLAY_SHOW);
        startTimeColumn.setFixed(Const.COLUMN_FIXED);
        rowDatas.add(startTimeColumn);
        ColumnsDTO endTimeColumn = new ColumnsDTO();
        endTimeColumn.setColumnId("endTime");
        endTimeColumn.setColumnName("结束答题时间");
        if(receivedDataInfo != null){
            endTimeColumn.setColumnValueId(receivedDataInfo.getEndTime());
            endTimeColumn.setColumnValueName(receivedDataInfo.getEndTime());
        }
        endTimeColumn.setDisplay(Const.COLUMN_DISPLAY_SHOW);
        endTimeColumn.setFixed(Const.COLUMN_FIXED);
        rowDatas.add(endTimeColumn);
        ColumnsDTO userIdColumn = new ColumnsDTO();
        userIdColumn.setColumnId("userId");
        userIdColumn.setColumnName("答题人编号");
        if(receivedDataInfo != null){
            userIdColumn.setColumnValueId(receivedDataInfo.getUserId());
            userIdColumn.setColumnValueName(receivedDataInfo.getUserId());
        }
        userIdColumn.setDisplay(Const.COLUMN_DISPLAY_HIDDEN);
        userIdColumn.setFixed(Const.COLUMN_FIXED);
        rowDatas.add(userIdColumn);

        return rowDatas;
    }

    /**
     * 处理查询出来的数据
     * @param year
     * @param reelId
     * @param startTime
     * @param endTime
     * @param pageSize
     * @return
     */
    @Override
    public List<ReceivedDataInfo> listReceivedTableData(List<String> year, String reelId ,String startTime ,String endTime , int pageSize) {
        return reelAnswerRecordDao.listReceivedTableData(year, reelId, startTime, endTime, pageSize);
    }

    @Override
    public int delReelStatistics(List<String> reelAnswerRecordIds) {
        ReelAnswerRecordDTO param = new ReelAnswerRecordDTO();
//        param.setId(reelAnswerRecordId);
        param.setReelAnswerRecordIds(reelAnswerRecordIds);
        param.setStatus(Const.DATA_INVALID);

        return reelAnswerRecordDao.updateAnwserRecord(param);
    }

    @Override
    public List<String> getAllReceriveidByReelId(String reelId) {

        return reelAnswerRecordDao.getAllReceriveidByReelId(reelId);
    }

    @Override
    public int countReelAnswerRecord(String reelId, String userId) {
        return reelAnswerRecordDao.countReelAnswerRecord(reelId,userId);
    }

    @Override
    public void deleteRecord(String reelId) {
        reelAnswerRecordDao.deleteRecord(reelId);
    }

    /**
     * 处理带有其他选线的回答记录
     * @param year
     * @param reelId
     * @param startTime
     * @param endTime
     * @param pageSize
     * @return
     */
    public List<ReceivedDataInfo> OtherlistReceivedTableData(List<String> year, String reelId ,String startTime ,String endTime , int pageSize) {
        return reelAnswerRecordDao.OtherlistReceivedTableData(year, reelId, startTime, endTime, pageSize);
    }
}
