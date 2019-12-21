package com.qdujava.etoakcup.service;

import com.qdujava.etoakcup.entity.*;
import com.qdujava.etoakcup.util.Result;

import java.util.List;

/**
 * 比赛报名service
 * @Author: liangbin
 * @Date: 2018/4/13 16:35
 */

public interface RegisterService {
    Result mobileCheck(String mobile);
    Result tnameCheck(String tname);
    List<TeamEntity> getAllteam();
    List<TeamSubjectEntity> getAllTeamSubject();
    void register(RegisterEntity registerEntity);
    DetailEntity getDetailsByMobile(String mobile);
    List<UserEntity> getMemberByMobile(String mobile);
    Result deleteUserById(String memberid);
    Result updateTeamItem(String mobile, String itemid);
}
