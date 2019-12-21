package com.qdujava.etoakcup.dao;

import com.qdujava.etoakcup.entity.TeamEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeamEntityMapper {
    int deleteByPrimaryKey(String id);

    int insert(TeamEntity record);

    int insertSelective(TeamEntity record);

    TeamEntity selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(TeamEntity record);

    int updateByPrimaryKey(TeamEntity record);

    boolean tnameCheck(String tname);

    List<TeamEntity> getAllTeam();

    int updateTeamItem(@Param("itemid") String itemid, @Param("mobile")String mobile);
}