package com.qdujava.etoakcup.entity;

public class TeamEntity {
    private String id;

    private String tname;

    private String leaderid;

    private String slogan;

    private Integer titem;

    private Float tscore;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getTname() {
        return tname;
    }

    public void setTname(String tname) {
        this.tname = tname == null ? null : tname.trim();
    }

    public String getLeaderid() {
        return leaderid;
    }

    public void setLeaderid(String leaderid) {
        this.leaderid = leaderid == null ? null : leaderid.trim();
    }

    public String getSlogan() {
        return slogan;
    }

    public void setSlogan(String slogan) {
        this.slogan = slogan == null ? null : slogan.trim();
    }

    public Integer getTitem() {
        return titem;
    }

    public void setTitem(Integer titem) {
        this.titem = titem;
    }

    public Float getTscore() {
        return tscore;
    }

    public void setTscore(Float tscore) {
        this.tscore = tscore;
    }
}