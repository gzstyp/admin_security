package com.fwtai.service;

import com.fwtai.bean.SysFrontendMenuTable;
import com.fwtai.dao.DaoHandle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @作者 田应平
 * @版本 v1.0
 * @创建时间 2020-04-29 18:20
 * @QQ号码 444141300
 * @Email service@dwlai.com
 * @官网 http://www.fwtai.com
*/
@Service
public class SysFrontendMenuTableService{

    @Autowired
    private DaoHandle daoHandle;

    public List<SysFrontendMenuTable> getMenusByUserName(final String username){
        return daoHandle.queryListEntity("sys_menu.getMenusByUserName",username);
    }
}