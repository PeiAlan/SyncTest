package com.example;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lushuaixin
 * @description: TODO
 * @date 2019/11/19 15:05
 */
public class WatsonsFileUtil {

    private static final Log logger = LogFactory.getLog(WatsonsFileUtil.class);

    /**
     * 读取文件内容到list
     *
     * @param filePath
     * @param encoding
     * @return
     */
    public synchronized static List<String> getListOfFile(String filePath, String encoding) {
        List<String> list = new ArrayList<String>();
        BufferedReader bReader_conf = null;
        String temp = null;
        String temp1 = null;
        try {
            bReader_conf = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), encoding));
            while ((temp = bReader_conf.readLine()) != null) {
                temp1 = temp;
                if (!StringUtil.isNumeric(temp1.substring(0, 1))) {
                    temp1 = temp1.substring(1);
                }
                list.add(temp1);
            }
            bReader_conf.close();
            logger.info("写入fileutil list：" + list.size());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e.getMessage());
        } finally {
            if (bReader_conf != null) {
                try {
                    bReader_conf.close();
                } catch (IOException e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
        return list;
    }
}
