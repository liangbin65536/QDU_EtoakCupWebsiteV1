package com.qdujava.etoakcup.dao;

import com.qdujava.etoakcup.entity.DetailEntity;
import com.qdujava.etoakcup.entity.UserEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserEntityMapper {
    int deleteByPrimaryKey(String id);

    int insert(UserEntity record);

    int insertSelective(UserEntity record);

    UserEntity selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(UserEntity record);

    int updateByPrimaryKey(UserEntity record);

    boolean mobileCheck(String mobile);

    DetailEntity getDetailsByMobile(String mobile);

    List<UserEntity> getMemberByMobile(String mobile);

    Integer getAuthorityById(String id);

    UserEntity login(@Param("mobile") String mobile, @Param("password") String password);

    String getItemById(String id);
}