package com.infoac.cas.dao;

import com.infoac.cas.dto.*;
import com.infoac.cas.entity.AnswerVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface AnswerDao {

	int saveAnswer(Map<String, Object> map);

	int findAnswerCount(Map<String, Object> map);
	/**
	 * 题集合
	 * @param rId
	 * @return
	 */
	List<SubjectDTO> queryAnswerRId(Map<String, Object> map);

	List<AnswerUserDTO> findAnswerListByUser(@Param("answer")AnswerVo answer);
	List<AllSubjectDTO> findunAnswerListByUser(@Param("answer")AnswerVo answer);
	List<AllSubjectDTO> msgExpire(@Param("time") String time,@Param("time2") String time2);
	void updateExpire(@Param("rId") String rId);
    Long findunAnswerCountByUser(@Param("answer")AnswerVo answer);
	Long findAnswerCountByUser(@Param("answer")AnswerVo answer);
	/**
	 * 统计选项百分比
	 * @param rId
	 * @return
	 */
	OptionsDTO queryAnswerOptionsId(Map<String, Object> map);
	/**
	 * 选项集合
	 * @param rId
	 * @return
	 */
	List<AnswerDTO> findAnswersByUser(@Param("reelId")String reelId, @Param("userId")String userId, @Param("year")String year, @Param("recordId")String recordId);

    void saveCollect(@Param("collectDTO") CollectDTO collectDTO);
    void saveCollectsys(@Param("collectDTO") CollectDTO collectDTO);
    List<CollectDTO> collectList(@Param("userId") String userId);
    List<CollectDTO> contentList();
    CollectDTO findCollection(@Param("id") String id);
    CollectDTO findCollectsys(@Param("id") String id);
    void delCollection(@Param("collectDTO") CollectDTO collectDTO);

    /**
    * @Description:查询每个年份分表中的结果
    * @Param:
    * @Return:
    * @Exception:
    */
    List<YearsTableAnswerDTO> getYearsTableResult(@Param("id") String id, @Param("reelId") String reelId, @Param("subjectId") String subjectId,@Param("receiverId") String receiverId ,@Param("yearsList") List<String> yearsList);

    /**
    * @Description:修改某个答题记录年表中某一项的答案
    * @Param:
    * @Return:
    * @Exception:
    */
	Integer updateOptions(@Param("id") String id ,@Param("year") String year , @Param("optionsId") String optionsId);

	/**
	* @Description:删除某人某份问卷某个题目的答案
	* @Param:
	* @Return:
	* @Exception:
	*/
	Integer deleteSubjectAnswer(@Param("receiverId") String receiverId,@Param("subjectId")String subjectId,@Param("reelId")String reelId,@Param("year") String year);

	/**
	* @Description:查询某用户某道题的答案
	* @Param:
	* @Return:
	* @Exception:
	*/
	List<SubjectAnswerDTO> getSubjectAnswer(@Param("reelid")String reelId ,@Param("yearsList")List<String> yearsList, @Param("subjectid") String subjectId, @Param("receiverid") String receiverid,@Param("answerRecordId")String answerRecordId);
    /**
     * 修改题时，若删除选项，根据optionsId删除填报数据
     * @param map
     */
	void deleteAnswerOptionsId(Map<String, Object> map);
    int countDeskCount(@Param("userId") String userId);
    int countUnminelist(@Param("userId") String userId,@Param("rId") String rId);
    void delDeskCount(@Param("userId") String userId);
    void delDeskCountByrid(@Param("rId") String rId);
    void delDeskOne(@Param("userId") String userId,@Param("rId") String rId);
    void insertDeskCount(@Param("rId") String rId,@Param("userId") String userId,@Param("id") String id);
    void insertGap(@Param("gap") GapFillingDTO gap);
    void delGap(@Param("rId") String rId);
    GapFillingDTO listGap(@Param("gap") GapFillingDTO gap);

    
    /**
	 * 包含
	 * @return
	 */
	List<String> queryAnswerRecordId(Map<String, Object> map);
	 /**
	 * 不包含 
	 * @return
	 */
	List<String> queryNotIncludeAnswerRecordId(Map<String, Object> map);
	
	 /**
	 * 
	 * @return
	 */
	List<ReceivedDataInfo> queryoptionsId(Map<String, Object> map);

    void saveListenerUnmine(@Param("listen")ListenerUnmineDTO listen);
    int getListercount(@Param("uid") String uid);
    void delListenerUnmine(@Param("uid") String uid);

	/**
	 * 包含
	 * @param map
	 * @return
	 */
	List<TextAnswerDTO> getNoIncludeAnswerList(Map<String, Object> map);
}
