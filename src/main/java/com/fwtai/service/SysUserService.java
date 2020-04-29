package com.fwtai.service;

import com.fwtai.bean.SysUserEntity;
import com.fwtai.components.BCryptPasswordEncoderUtil;
import com.fwtai.dao.DaoHandle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @作者 田应平
 * @版本 v1.0
 * @创建时间 2020-04-29 18:20
 * @QQ号码 444141300
 * @Email service@dwlai.com
 * @官网 http://www.fwtai.com
*/
@Service
public class SysUserService{

    @Autowired
    private DaoHandle daoHandle;

    @Autowired
    private BCryptPasswordEncoderUtil bCryptPasswordEncoderUtil;

    public boolean checkLogin(final String username,final String rawPassword) throws Exception {
        final SysUserEntity userEntity = daoHandle.queryForEntity("sys_user.getUserByUserName",username);
        System.out.println("userEntity = " + userEntity);
        if (userEntity == null) {
            //System.out.println("checkLogin--------->账号不存在，请重新尝试！");
            //设置友好提示
            throw  new Exception("账号不存在，请重新尝试！");
        }else {
            //加密的密码
            final String encodedPassword = userEntity.getPassWord();
            //和加密后的密码进行比配
            if(!bCryptPasswordEncoderUtil.matches(rawPassword,encodedPassword)) {
                //System.out.println("checkLogin--------->密码不正确！");
                //设置友好提示
                throw new Exception("密码不正确！");
            }else{
                return true;
            }
        }
    }

    public SysUserEntity getUserByUserName(final String username){
        return daoHandle.queryForEntity("sys_user.getUserByUserName",username);
    }
}