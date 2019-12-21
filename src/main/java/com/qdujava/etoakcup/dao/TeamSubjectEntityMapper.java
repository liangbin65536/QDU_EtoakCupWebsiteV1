package com.qdujava.etoakcup.dao;

import com.qdujava.etoakcup.entity.TeamSubjectEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeamSubjectEntityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TeamSubjectEntity record);

    int insertSelective(TeamSubjectEntity record);

    TeamSubjectEntity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TeamSubjectEntity record);

    int updateByPrimaryKeyWithBLOBs(TeamSubjectEntity record);

    int updateByPrimaryKey(TeamSubjectEntity record);

    List<TeamSubjectEntity> getAllTeamSubject();
}