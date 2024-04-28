package com.fuswx.spzx.pay.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.fuswx.spzx.model.vo.common.Result;
import com.fuswx.spzx.model.vo.common.ResultCodeEnum;
import com.fuswx.spzx.pay.service.AliPayService;
import com.fuswx.spzx.pay.service.PaymentInfoService;
import com.fuswx.spzx.pay.utils.AliPayProperties;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/order/alipay")
public class AlipayController {

    @Resource
    private AliPayService aliPayService;

    @Resource
    private AliPayProperties aliPayProperties;

    @Resource
    private PaymentInfoService paymentInfoService;

    @Operation(summary = "支付宝下单", description = "支付宝下单接口")
    @GetMapping("/submitAlipay/{orderNo}")
    public Result submitAliPay(@PathVariable String orderNo) {
        String form = aliPayService.submitAliPay(orderNo);
        return Result.build(form, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "支付宝异步回调", description = "支付宝异步回调接口")
    @RequestMapping("/callback/notify")
    public String alipayNotify(@RequestParam Map<String, String> paramMap, HttpServletRequest request) {
        //签名校验
        boolean signVerified = false; //调用SDK验证签名
        try {
            signVerified = AlipaySignature.rsaCheckV1(paramMap, aliPayProperties.getAlipayPublicKey(), AliPayProperties.charset, AliPayProperties.signType);
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        System.out.println("******************" + paramMap);
        //交易状态
        String tradeStatus = paramMap.get("trade_status");
        if (signVerified){
            // TODO 验签成功后，按照支付结果异步通知中的描述，对支付结果中的业务内容进行二次校验，校验成功后在response中返回success并继续商户自身业务处理，校验失败返回failure
            if ("TRADE_SUCCESS".equals(tradeStatus) || "TRADE_FINISHED".equals(tradeStatus)){
                //正常的支付成功，我们应该更新交易记录状态
                paymentInfoService.updatePaymentStatus(paramMap);
                return "success";
            }
        } else {
            //TODO 验签失败则记录异常日志，并在response中返回failure
            return "failure";
        }
        return "failure";
    }

}
