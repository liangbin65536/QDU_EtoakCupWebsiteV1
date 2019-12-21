package com.qdujava.etoakcup.service;

import com.qdujava.etoakcup.entity.SubjectEntity;
import com.qdujava.etoakcup.entity.UserEntity;
import com.qdujava.etoakcup.util.Result;

import java.util.List;

/**
 * 个人赛service
 * @Author: liangbin
 * @Date: 2018/4/28 14:09
 */
public interface MatchService {
    Result<UserEntity> login(String mobile, String password);
    SubjectEntity getSubjectById(String userid, int requestid);
    List<SubjectEntity> getAllSubjects(String userid);
    Result markPaper(UserEntity user, String answer);
}
