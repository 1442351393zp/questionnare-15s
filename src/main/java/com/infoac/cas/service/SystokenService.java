package com.infoac.cas.service;

import com.infoac.cas.entity.Systoken;

/**
 * 所,中软,access_token
 */
public interface SystokenService {

    String getAccessToken(String name);

    void updateAccessToken(Systoken systoken);
}

