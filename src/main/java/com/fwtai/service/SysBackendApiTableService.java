package com.fwtai.service;

import com.fwtai.bean.SysBackendApiTable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @作者 田应平
 * @版本 v1.0
 * @创建时间 2020-04-29 18:21
 * @QQ号码 444141300
 * @Email service@dwlai.com
 * @官网 http://www.fwtai.com
 */
@Service
public class SysBackendApiTableService{

    public List<SysBackendApiTable> getApiUrlByUserName(final String username) {
        System.out.println("getApiUrlByUserName.................");
        final SysBackendApiTable sysBackendApiTable1 = new SysBackendApiTable();
        sysBackendApiTable1.setBackendApiUrl("/api/list");
        sysBackendApiTable1.setBackendApiMethod("GET");

        final SysBackendApiTable sysBackendApiTable2 = new SysBackendApiTable();
        sysBackendApiTable2.setBackendApiUrl("/api/create");
        sysBackendApiTable2.setBackendApiMethod("GET");

        final SysBackendApiTable sysBackendApiTable3 = new SysBackendApiTable();
        sysBackendApiTable3.setBackendApiUrl("/api/edit");
        sysBackendApiTable3.setBackendApiMethod("GET");

        final SysBackendApiTable sysBackendApiTable4 = new SysBackendApiTable();
        sysBackendApiTable4.setBackendApiUrl("/api/register");
        sysBackendApiTable4.setBackendApiMethod("POST");

        final ArrayList<SysBackendApiTable> apis = new ArrayList<>();
        apis.add(sysBackendApiTable1);
        apis.add(sysBackendApiTable2);
        apis.add(sysBackendApiTable3);
        //apis.add(sysBackendApiTable4);
        return apis;
    }
}