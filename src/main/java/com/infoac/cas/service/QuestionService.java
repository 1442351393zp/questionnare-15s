package com.infoac.cas.service;

import com.alibaba.fastjson.JSONObject;
import com.infoac.cas.dto.*;

import java.util.List;
import java.util.Map;

public interface QuestionService {

	void stopResearch(ReelDTO reel);

	void startResearch(ReelDTO reel);

	void updateReel(ReelDTO reel);

    ReelDTO findReelSetup(String rId);

	ReceiveDetailResultDto receiveDetail(String reelId);

    void updateReelCanalText(String rId);

    String selectuserids(String rid);

    List<String> selectUserList(String rid);

    List<String> selectUsers();

    /**
     * 答题的人员的展示
     * @param rId
     * @return
     */
    SelectUsersDTO getQuestionState(String rId);

    /**
     * 发送消息给未答人员
     * @param jsonList
     */
    void sendMessage(JSONObject jsonList)throws Exception;
}
