package com.qdujava.etoakcup.entity;

/**
 * 查询战队信息用POJO类
 * @Author: liangbin
 * @Date: 2018/4/17 21:21
 */
public class DetailEntity {
    private String mobile;
    private String uname;
    private String tname;
    private String titem;
    private float tscore;
    private Integer sscore;
    private Integer authority;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getTname() {
        return tname;
    }

    public void setTname(String tname) {
        this.tname = tname;
    }

    public String getTitem() {
        return titem;
    }

    public void setTitem(String titem) {
        this.titem = titem;
    }

    public float getTscore() {
        return tscore;
    }

    public void setTscore(float tscore) {
        this.tscore = tscore;
    }

    public Integer getSscore() {
        return sscore;
    }

    public void setSscore(Integer sscore) {
        this.sscore = sscore;
    }

    public Integer getAuthority() {
        return authority;
    }

    public void setAuthority(Integer authority) {
        this.authority = authority;
    }
}
