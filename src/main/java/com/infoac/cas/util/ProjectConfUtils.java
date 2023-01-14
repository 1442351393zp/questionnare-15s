package com.infoac.cas.util;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.IOException;
import java.util.Properties;

/**
 * @Author: PengSenjie
 * @description: 获取项目配置参数
 * @Date: Created in 19-9-2 下午2:11
 */
public class ProjectConfUtils {


    public static String getProjectConf(String key){
        try {
            Properties properties = PropertiesLoaderUtils.loadProperties(new ClassPathResource("project.properties"));
            return properties.getProperty(key);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "error";
    }

}
