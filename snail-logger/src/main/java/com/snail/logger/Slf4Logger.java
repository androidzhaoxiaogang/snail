package com.snail.logger;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class Slf4Logger implements ILogger {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired(required = false)
    private FastJsonHttpMsgConverter fastJsonHttpMessageConverter;

    @Override
    public void save(LoggerInfo loggerInfo) {
        if (logger.isInfoEnabled())
            if (fastJsonHttpMessageConverter == null)
                logger.info(JSON.toJSONString(loggerInfo));
            else
                logger.info(fastJsonHttpMessageConverter.converter(loggerInfo));
    }
}
