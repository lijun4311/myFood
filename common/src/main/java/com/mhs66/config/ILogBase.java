package com.mhs66.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 *description:
 *日志工具类接口
 *@author 76442
 *@date 2020-07-15 20:10
 */
public interface ILogBase {
    Logger log = LoggerFactory.getLogger("AsyncLogger");
}
