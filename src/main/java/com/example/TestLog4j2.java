package com.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 测试 {@link lombok.extern.log4j.Log4j2} 日志框架
 * -Dlog4j2.contextSelector=org.apache.logging.log4j.core.async.AsyncLoggerContextSelector
 *
 * @author Ellison_Pei
 * @date 2021/10/19 09:56
 * @since 1.0
 **/
public class TestLog4j2 {
    private static Logger logger = LogManager.getLogger(TestLog4j2.class);

    public static void main(String[] args) {
        for (int i = 0; i < 500; i++) {
            logger.trace("trace level");
            logger.debug("debug level");
            logger.info("info level");
            logger.warn("warn level");
            logger.error("error level");
            logger.fatal("fatal level");
        }

        try {
            Thread.sleep(1000 * 61);
        } catch (InterruptedException e) {
        }

        logger.trace("trace level");
        logger.debug("debug level");
        logger.info("info level");
        logger.warn("warn level");
        logger.error("error level");
        logger.fatal("fatal level");
    }

}
