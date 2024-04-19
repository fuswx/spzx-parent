package com.fuswx.spzx.manager.service.impl;

import com.fuswx.spzx.common.log.service.AsyncOperLogService;
import com.fuswx.spzx.manager.mapper.SysOperLogMapper;
import com.fuswx.spzx.model.entity.system.SysOperLog;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class AsyncOperLogServiceImpl implements AsyncOperLogService {

    @Resource
    private SysOperLogMapper sysOperLogMapper;

    //保存日志数据
    @Override
    public void saveSysOperLog(SysOperLog sysOperLog) {
        sysOperLogMapper.insert(sysOperLog);
    }
}
