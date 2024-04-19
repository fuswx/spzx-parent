package com.fuswx.spzx.common.exception;

import com.fuswx.spzx.model.vo.common.ResultCodeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class FuswxException extends RuntimeException{

    private Integer code;
    private String message;

    private ResultCodeEnum resultCodeEnum;

    public FuswxException(ResultCodeEnum resultCodeEnum) {
        this.resultCodeEnum = resultCodeEnum;
    }
}
