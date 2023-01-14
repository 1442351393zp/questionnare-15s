package com.infoac.cas.service;

import com.infoac.cas.dto.ReceiveLineChartDTO;
import com.infoac.cas.dto.ReceivedDataInfo;
import com.infoac.cas.dto.ReelAnswerRecordDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface IReelAnswerRecordService {

    Integer saveReelAnswerRecord(ReelAnswerRecordDTO answer);

    List<ReelAnswerRecordDTO> listReelAnswerRecord(String reelId);


    /**
     * @Description: 获取问卷平均答题花费时长
     * @Param:
     * @Return:
     * @Exception:
     */
    String getReelAnswerAverageTimeConsuming(String reelId);

    /**
    * @Description: 获取问卷回收数
    * @Param:
    * @Return:
    * @Exception:
    */
     int getReceivedCount(String reelId);

    /**
    * @Description: 获取问卷日回收量
    * @Param:
    * @Return:
    * @Exception:
    */
    List<ReceiveLineChartDTO> receiveLineChart(String reelId);

    int updateAnwserRecord(ReelAnswerRecordDTO record);

    /**
     * @Description: 获取"回收数据"表格数据
     * @Param:
     * @Return:
     * @Exception:
     */
    Map<String,Object> listReceivedTableData(String reelId,String startTime ,String endTime , int pageSize);

    /**
    * @author: Mr.Xu
    * @Date: 2020/1/2 10:56
    * @Description 查询问卷答题记录详情
    */
    List<ReceivedDataInfo> listReceivedTableData( List<String> year , String reelId,String startTime ,String endTime , int pageSize);

    /**
    * @author: Mr.Xu
    * @Date: 2020/1/7 9:26
    * @Description 逻辑删除问卷答题记录
    */
    int delReelStatistics(List<String> reelAnswerRecordId);

    List<String> getAllReceriveidByReelId(@Param("reelId") String reelId);

    int countReelAnswerRecord(String reelId,String userId);
    void deleteRecord(String reelId);

}
