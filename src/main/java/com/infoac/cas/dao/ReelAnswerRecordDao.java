package com.infoac.cas.dao;

import com.infoac.cas.dto.ReceiveLineChartDTO;
import com.infoac.cas.dto.ReceivedDataInfo;
import com.infoac.cas.dto.ReelAnswerRecordDTO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReelAnswerRecordDao {

    Integer saveReelAnswerRecord(@Param("answer") ReelAnswerRecordDTO answer);

    List<ReelAnswerRecordDTO> listReelAnswerRecord(@Param("reelId") String reelId , @Param("userId")String userId);

    /**
    * @Description: 获取问卷平均答题花费时长
    * @Param:
    * @Return:
    * @Exception:
    */
    String getReelAnswerAverageTimeConsuming(@Param("reelId") String reelId);

    int getReceivedCount(@Param("reelId") String reelId);

    List<ReceiveLineChartDTO> receiveLineChart(@Param("reelId") String reelId);

    int updateAnwserRecord(ReelAnswerRecordDTO record);

    List<ReceivedDataInfo> listReceivedTableData(@Param("yearsList") List<String> year , @Param("reelId") String reelId,@Param("startTime")String startTime ,@Param("endTime")String endTime , @Param("pageSize")int pageSize);

    Integer updateOptions(@Param("id") String id , @Param("year") String year , @Param("optionsId") String optionsId);

    List<String> getAllReceriveidByReelId(@Param("reelId") String reelId);

    ReelAnswerRecordDTO getRecordById(@Param("id") String id);

    int countReelAnswerRecord(@Param("reelId") String reelId , @Param("userId")String userId);
    void deleteRecord(@Param("reelId") String reelId);

    List<ReceivedDataInfo> OtherlistReceivedTableData(@Param("yearsList") List<String> year , @Param("reelId") String reelId,@Param("startTime")String startTime ,@Param("endTime")String endTime , @Param("pageSize")int pageSize);
}
