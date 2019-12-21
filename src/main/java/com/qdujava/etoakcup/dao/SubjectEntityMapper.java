package com.qdujava.etoakcup.dao;

import com.qdujava.etoakcup.entity.SubjectEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubjectEntityMapper {
    int deleteByPrimaryKey(String id);

    int insert(SubjectEntity record);

    int insertSelective(SubjectEntity record);

    SubjectEntity selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SubjectEntity record);

    int updateByPrimaryKeyWithBLOBs(SubjectEntity record);

    int updateByPrimaryKey(SubjectEntity record);

    List<String> getAllJavaSubjectsId();

    List<String> getAllPHPSubjectsId();

    List<String> getAllCSubjectsId();

    String selectNameByPrimaryKey(String id);

    String selectMobileByPrimaryKey(String mobile);
}