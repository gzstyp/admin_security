package com.fwtai.bean;

public class SysUserEntity{

    private String userId;

    private String userName;

    private String passWord;

    private Integer state;

    private String description;

    public SysUserEntity() {
    }

    public SysUserEntity(final String userId,final String userName,final String passWord,final Integer state,final String description) {
        this.userId = userId;
        this.userName = userName;
        this.passWord = passWord;
        this.state = state;
        this.description = description;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    @Override
    public String toString(){
        return "SysUserEntity{" + "userId='" + userId + '\'' + ", userName='" + userName + '\'' + ", passWord='" + passWord + '\'' + ", state=" + state + ", description='" + description + '\'' + '}';
    }
}
