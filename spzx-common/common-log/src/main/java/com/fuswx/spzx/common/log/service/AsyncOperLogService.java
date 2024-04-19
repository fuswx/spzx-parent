package com.fuswx.spzx.common.log.service;

import com.fuswx.spzx.model.entity.system.SysOperLog;

public interface AsyncOperLogService {

    //保存日志数据
    void saveSysOperLog(SysOperLog sysOperLog);

}
