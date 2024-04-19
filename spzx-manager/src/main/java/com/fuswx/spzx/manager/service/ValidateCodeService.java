package com.fuswx.spzx.manager.service;

import com.fuswx.spzx.model.vo.system.ValidateCodeVo;

public interface ValidateCodeService {
    //生成图片验证码
    ValidateCodeVo generateValidateCode();
}
