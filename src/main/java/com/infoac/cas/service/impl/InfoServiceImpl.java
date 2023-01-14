package com.infoac.cas.service.impl;

import com.infoac.cas.dao.InfoDao;
import com.infoac.cas.entity.Info;
import com.infoac.cas.service.InfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * @Author: SenjiePeng
 * @Description:
 * @Date: Created in  2019/7/9 13:39
 */
@Service
public class InfoServiceImpl implements InfoService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private InfoDao infoDao;

    @Override
    @Transactional
    public void insertInfo(Info info) {
        //爬取数据
        //Spider.create(myCnblogsSpider).addUrl("https://voice.hupu.com/nba/1").addPipeline(mysqlPipeline)
        /*Spider.create(myCnblogsSpider).addUrl("https://voice.hupu.com/nba/1")
                .thread(3).run();*/
        //压缩
        /*logger.info("爬取完成，压缩文件夹");
        try {
            FileToZip.zipFile("e:/spider","e:/spider.zip");
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        //todo:发送给摆渡机
        //先发送到服务器

        infoDao.insertInfo(info);
    }
}
