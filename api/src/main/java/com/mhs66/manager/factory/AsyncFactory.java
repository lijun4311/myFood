package com.mhs66.manager.factory;

import com.mhs66.config.ILogBase;
import com.mhs66.pojo.OperLog;
import com.mhs66.service.OperLogService;
import com.mhs66.utils.SpringUtils;

import java.util.TimerTask;

/**
 * 异步工厂（产生任务用）
 *
 * @author liuhulu
 */
public class AsyncFactory implements ILogBase {


    /**
     * 操作日志记录
     *
     * @param operLog 操作日志信息
     * @return 任务task
     */
    public static TimerTask recordOper(final OperLog operLog) {
        return new TimerTask() {
            @Override
            public void run() {
                // 远程查询操作地点
                SpringUtils.getBean(OperLogService.class).save(operLog);
            }
        };
    }
}
