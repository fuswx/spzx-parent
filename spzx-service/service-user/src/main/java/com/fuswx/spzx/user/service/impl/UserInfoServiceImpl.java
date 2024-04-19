package com.fuswx.spzx.user.service.impl;

import com.alibaba.fastjson2.JSON;
import com.fuswx.spzx.common.exception.FuswxException;
import com.fuswx.spzx.model.dto.h5.UserLoginDto;
import com.fuswx.spzx.model.dto.h5.UserRegisterDto;
import com.fuswx.spzx.model.entity.user.UserInfo;
import com.fuswx.spzx.model.vo.common.ResultCodeEnum;
import com.fuswx.spzx.model.vo.h5.UserInfoVo;
import com.fuswx.spzx.user.mapper.UserInfoMapper;
import com.fuswx.spzx.user.service.UserInfoService;
import com.fuswx.spzx.utils.AuthContextUtil;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Resource
    private UserInfoMapper userInfoMapper;

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    //会员注册
    @Override
    public void register(UserRegisterDto userRegisterDto) {
        //1、userRegisterDto获取到相关数据
        String username = userRegisterDto.getUsername();
        String password = userRegisterDto.getPassword();
        String nickName = userRegisterDto.getNickName();
        String code = userRegisterDto.getCode();


        //2、验证码校验
        //2.1 从redis获取发送验证码
        String redisCode = redisTemplate.opsForValue().get(username);
        //2.2 获取输入验证码，进行对比
        if (!redisCode.equals(code)){
            throw new FuswxException(ResultCodeEnum.VALIDATECODE_ERROR);
        }

        //3、校验用户名不能重复
        UserInfo userInfo = userInfoMapper.selectByUserName(username);
        if (userInfo != null){ //存在相同用户名
            throw new FuswxException(ResultCodeEnum.USER_NAME_IS_EXISTS);
        }

        //4、封装添加数据，调用方法添加数据库
        userInfo = new UserInfo();
        userInfo.setUsername(username);
        userInfo.setPassword(DigestUtils.md5DigestAsHex(password.getBytes()));
        userInfo.setNickName(nickName);
        userInfo.setPhone(username);
        userInfo.setStatus(1);
        userInfo.setSex(0);
        userInfo.setAvatar("http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoj0hHXhgJNOTSOFsS4uZs8x1ConecaVOB8eIl115xmJZcT4oCicvia7wMEufibKtTLqiaJeanU2Lpg3w/132");

        userInfoMapper.save(userInfo);

        //5、从redis删除发送的验证码
        redisTemplate.delete(username);
    }

    //登录
    @Override
    public String login(UserLoginDto userLoginDto) {
        //1、dto获取用户名和密码
        String username = userLoginDto.getUsername();
        String password = userLoginDto.getPassword();

        //2、根据用户名查询数据库，得到用户信息
        UserInfo userInfo = userInfoMapper.selectByUserName(username);

        //3、比较密码是否一致
        String databasePassword = userInfo.getPassword();
        String md5Password = DigestUtils.md5DigestAsHex(password.getBytes());
        if (!databasePassword.equals(md5Password)){
            throw new FuswxException(ResultCodeEnum.LOGIN_ERROR);
        }

        //4、校验用户是否禁用
        if (userInfo.getStatus() == 0){
            throw new FuswxException(ResultCodeEnum.ACCOUNT_STOP);
        }

        //5、生成token
        String token = UUID.randomUUID().toString().replaceAll("-", "");

        //6、把用户信息放到redis里面
        redisTemplate.opsForValue().set("user:spzx:" + token, JSON.toJSONString(userInfo), 30, TimeUnit.DAYS);

        //7、返回token
        return token;
    }

    //获取当前登录用户信息
    @Override
    public UserInfoVo getCurrentUserInfo(String token) {
        //从redis根据token获取用户信息
//        String userJson = redisTemplate.opsForValue().get("user:spzx:" + token);
//        if (!StringUtils.hasText(userJson)){
//            throw new FuswxException(ResultCodeEnum.LOGIN_AUTH);
//        }
//        UserInfo userInfo = JSON.parseObject(userJson, UserInfo.class);
        //从ThreadLocal获取用户信息
        UserInfo userInfo = AuthContextUtil.getUserInfo();
        UserInfoVo userInfoVo = new UserInfoVo();
        BeanUtils.copyProperties(userInfo, userInfoVo);
        return userInfoVo;
    }
}
