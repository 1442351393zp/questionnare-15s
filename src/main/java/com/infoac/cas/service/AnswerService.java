package com.infoac.cas.service;

import com.infoac.cas.dto.*;
import com.infoac.cas.entity.AnswerVo;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface AnswerService {

	int findAnswerCount(String reelId, String userId);

	void saveAnswer(AnswerDTO answerVo);
	/**
	 * 查询答题
	 * @param rId
	 * @return
	 */
	List<SubjectDTO> queryAnswerRId(String rId,String userId);
	/**
	 * 受访者列表条数
	 * @param rId
	 * @return
	 */
	Long findAnswerCountByUser(AnswerVo answer);
	/**
	 * 受访者列表
	 * @param rId
	 * @return
	 */
	List<AnswerUserDTO> findAnswerListByUser(AnswerVo answer);
	List<AllSubjectDTO> findunAnswerListByUser(AnswerVo answer);
	void msgExpire(List<Integer> yearsList);
    Long findunAnswerCountByUser(AnswerVo answer);
	/**
	 * 受访者选项集合
	 * @param rId
	 * @return
	 */
	List<AnswerDTO> findAnswersByUser(String reelId, String userId, String year, String recordId);

    String saveCollect(CollectDTO collectDTO);
    void saveCollectsys(CollectDTO collectDTO);
    List<CollectDTO> collectList(String userId);
    List<CollectDTO> contentList();
    CollectDTO findCollection(String id);
    CollectDTO findCollectsys(String id);
    void delCollection(CollectDTO collectDTO);

	/**
	 * @Description:查询每个年份分表中的结果
	 * @Param:
	 * @Return:
	 * @Exception:
	 */
	List<YearsTableAnswerDTO> getYearsTableResult(String id, String reelId, String subjectId, String receiverId);

	/**
	* @Description: 修改某个人所答某道题的答案
	* @Param:
	* @Return:
	* @Exception:
	*/
	Integer updateOptions(String reelId , String subjectId , List<String> optionsId, String userId, String pageId, String receiverId, Set<String> years);

	Integer updateOptionsByReveiverId(String reelId,String subjectId, String reveiverId, List<String> optionsId);

	/**
	 * @Description: 删除某人个某份问卷中某个题目的答案
	 * @Param:
	 * @Return:
	 * @Exception:
	 */
	Integer deleteSubjectAnswer( String receiverId,String subjectId,String reelId,String year);

	/**
	 * @Description:查询某个问卷某(些)用户某道题的答案
	 * @Param:
	 * @Return:
	 * @Exception:
	 */
	List<Map<String,Object>> getQuestions( String reelId ,String subjectId, String receiverid,String answerRecordId);

	/**
	 * @Description:查询某个问卷某用户所有题的答案及页面其他信息
	 * @Param:
	 * @Return:
	 * @Exception:
	 */
	List<Map<String, Object>> getAllQuestions(String reelId , String receiverid);
	/**
	 * 修改题时，若删除选项，根据optionsId删除填报数据
	 * @param optionsId
	 */
	void deleteAnswerOptionsId(String optionsId);
	int countDeskCount(String userId);
	int countUnminelist(String userId,String rId);
    void delDeskCount(String userId);
    void delDeskOne(String userId,String rId);
    void insertDeskCount(String rId,String userId,String id);

    void insertGap(GapFillingDTO gap);
    void delGap(String rId);
    //GapFillingDTO listMyGap(GapFillingDTO gap);

    /**
  	 * 包含
  	 * @return
  	 */
  	List<String> queryAnswerRecordId(String optionsId,String subjectId,String startTime,String endTime);
  	/**
	 * 不包含 
	 * @return
	 */
	List<String> queryNotIncludeAnswerRecordId(String optionsId,String subjectId,String startTime,String endTime);
  	 /**
	 * 
	 * @return
	 */
	List<ReceivedDataInfo> queryoptionsId(String answerRecordId,String subjectId);
	
    void saveListenerUnmine(ListenerUnmineDTO listenerUnmineDTO);

    int getListercount(String uid);
    void delListenerUnmine(String uid);

}
