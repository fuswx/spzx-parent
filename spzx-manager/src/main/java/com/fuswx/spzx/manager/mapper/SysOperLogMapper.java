package com.fuswx.spzx.manager.mapper;

import com.fuswx.spzx.model.entity.system.SysOperLog;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SysOperLogMapper {
    void insert(SysOperLog sysOperLog);
}
