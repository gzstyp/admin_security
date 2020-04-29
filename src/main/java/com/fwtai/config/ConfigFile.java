package com.fwtai.config;

/**
 * @作者 田应平
 * @版本 v1.0
 * @创建时间 2020-04-26 12:25
 * @QQ号码 444141300
 * @Email service@dwlai.com
 * @官网 http://www.fwtai.com
 */

public final class ConfigFile{

    public final static String roles = "roles";

    public static final String TOKEN_HEADER = "Authorization";

    public static final String TOKEN_PREFIX = "Bearer ";

    /**统一全局的code的状态码json关键字key关键字响应给客户端*/
    public final static String code = "code";
    /**统一全局的msg提示消息json关键字key响应给客户端*/
    public final static String msg = "msg";
    /**操作成功*/
    public final static int code200 = 200;
    /**操作成功*/
    public final static String msg200 = "操作成功";
    /**暂无数据*/
    public final static int code201 = 201;
    /**暂无数据*/
    public final static String msg201 = "暂无数据";
    /**系统出现错误*/
    public final static int code204 = 204;
    /**系统出现错误*/
    public final static String msg204 = "系统出现错误";
    /**统一全局的total总条数|总记录数json关键字key响应给客户端*/
    public final static String total = "total";
    /**统一全局的result返回数据(含分页的数据)json关键字key响应给客户端*/
    public final static String data = "data";

    private ConfigFile() {
        throw new IllegalStateException("不能创建静态的类示例");
    }
}