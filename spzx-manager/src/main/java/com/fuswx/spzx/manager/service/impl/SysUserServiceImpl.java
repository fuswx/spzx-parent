package com.fuswx.spzx.manager.service.impl;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.fuswx.spzx.common.exception.FuswxException;
import com.fuswx.spzx.manager.mapper.SysRoleUserMapper;
import com.fuswx.spzx.manager.mapper.SysUserMapper;
import com.fuswx.spzx.manager.service.SysUserService;
import com.fuswx.spzx.model.dto.system.AssginRoleDto;
import com.fuswx.spzx.model.dto.system.LoginDto;
import com.fuswx.spzx.model.dto.system.SysUserDto;
import com.fuswx.spzx.model.entity.system.SysUser;
import com.fuswx.spzx.model.vo.common.ResultCodeEnum;
import com.fuswx.spzx.model.vo.system.LoginVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class SysUserServiceImpl implements SysUserService {

    @Resource
    private SysUserMapper sysUserMapper;

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @Resource
    private SysRoleUserMapper sysRoleUserMapper;

    //用户登录
    @Override
    public LoginVo login(LoginDto loginDto) {

        //1、获取输入验证码和存储到redis的key名称 LoginDto获取到
        String captcha = loginDto.getCaptcha();
        String key = loginDto.getCodeKey();

        //2、根据获取到的redis里面的key，查询redis里面存储的验证码
        String redisCode = redisTemplate.opsForValue().get("user:validate" + key);

        //3、比较输入验证码和redis存储验证码是否一致
        //4、如果不一样，提示用户校验失败
        if (StrUtil.isEmpty(redisCode) || !StrUtil.equalsIgnoreCase(redisCode, captcha)){
            throw new FuswxException(ResultCodeEnum.VALIDATECODE_ERROR);
        }

        //5、如果一致，删除redis里面验证码
        redisTemplate.delete("user:validate" + key);

        //1、获取提交用户名，loginDto获取到
        String userName = loginDto.getUserName();

        //2、根据用户名查询数据库表sys_user表
        SysUser user = sysUserMapper.selectUserInfoByUserName(userName);

        //3、如果根据用户名查不到对应信息，用户不存在，返回错误信息
        if (user == null){
//            throw new RuntimeException("用户名不存在");
            throw new FuswxException(ResultCodeEnum.LOGIN_ERROR);
        }

        //4、如果根据用户名查询到用户信息，用户存在
        //5、获取输入的密码，比较输入的密码和数据库密码是否一致
        String database_password = user.getPassword();
        //把输入的密码进行加密，再比较数据库密码，md5
        String input_password = DigestUtils.md5DigestAsHex(loginDto.getPassword().getBytes());
        //比较
        if (!input_password.equals(database_password)){
//            throw new RuntimeException("密码不正确");
            throw new FuswxException(ResultCodeEnum.LOGIN_ERROR);
        }

        //6、如果密码一致，登陆成功，如果密码不一致则登陆失败
        //7、登录成功，生成用户唯一标识token
        String token = UUID.randomUUID().toString().replace("-", "");

        //8、把登录成功的用户信息放到redis里面
        //key:token value:用户信息
        redisTemplate.opsForValue()
                .set("user:login" + token, JSON.toJSONString(user), 7, TimeUnit.DAYS);

        //9、返回loginVo对象
        LoginVo loginVo = new LoginVo();
        loginVo.setToken(token);
        return loginVo;
    }

    @Override
    public SysUser getUserInfo(String token) {
        String userJson = redisTemplate.opsForValue().get("user:login" + token);
        return JSON.parseObject(userJson, SysUser.class);
    }

    @Override
    public void logout(String token) {
        redisTemplate.delete("user:login" + token);
    }

    //1、用户条件分页查询接口
    @Override
    public PageInfo<SysUser> findByPage(Integer pageNum, Integer pageSize, SysUserDto sysUserDto) {
        PageHelper.startPage(pageNum, pageSize);
        List<SysUser> list = sysUserMapper.findByPage(sysUserDto);
        return new PageInfo<>(list);
    }

    //2、用户添加
    @Override
    public void saveSysUser(SysUser sysUser) {
        //1、判断用户名不能重复
        String userName = sysUser.getUserName();
        SysUser dbSysUser = sysUserMapper.selectUserInfoByUserName(userName);
        if (dbSysUser != null){
            throw new FuswxException(ResultCodeEnum.USER_NAME_IS_EXISTS);
        }

        //2、输入密码进行加密
        String md5Password = DigestUtils.md5DigestAsHex(sysUser.getPassword().getBytes());
        sysUser.setPassword(md5Password);

        //设置status值
        sysUser.setStatus(1);

        sysUserMapper.save(sysUser);
    }

    //3、用户修改
    @Override
    public void updateSysUser(SysUser sysUser) {
        sysUserMapper.update(sysUser);
    }

    //4、用户删除
    @Override
    public void deleteById(Integer userId) {
        sysUserMapper.delete(userId);
    }

    //用户分配角色
    @Override
    public void doAssign(AssginRoleDto assginRoleDto) {
        //1、根据userId删除用户之前分配的角色数据
        sysRoleUserMapper.deleteByUserId(assginRoleDto.getUserId());

        //2、重新分配新数据
        List<Long> roleIdList = assginRoleDto.getRoleIdList();
        //遍历得到每个角色id
        for (Long roleId : roleIdList) {
            sysRoleUserMapper.doAssign(assginRoleDto.getUserId(), roleId);
        }
    }
}
