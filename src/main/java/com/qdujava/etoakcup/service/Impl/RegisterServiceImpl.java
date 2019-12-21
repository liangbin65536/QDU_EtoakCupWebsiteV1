package com.qdujava.etoakcup.service.Impl;

import com.qdujava.etoakcup.dao.TeamEntityMapper;
import com.qdujava.etoakcup.dao.TeamSubjectEntityMapper;
import com.qdujava.etoakcup.dao.UserEntityMapper;
import com.qdujava.etoakcup.entity.*;
import com.qdujava.etoakcup.service.RegisterService;
import com.qdujava.etoakcup.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * @Author: liangbin
 * @Date: 2018/4/13 16:36
 */
@Service
public class RegisterServiceImpl implements RegisterService {

    @Autowired
    UserEntityMapper userDao;
    @Autowired
    TeamEntityMapper teamDao;
    @Autowired
    TeamSubjectEntityMapper tSubjectDao;
    @Autowired
    Result result;


    @Override
    public Result mobileCheck(String mobile) {
        if (userDao.mobileCheck(mobile)){
            result.setCode("0").setMessage("该手机号已被注册");
        }
        else {
            result.setCode("1");
        }
        return result;
    }

    @Override
    public Result tnameCheck(String tname) {
        if (teamDao.tnameCheck(tname)){
            result.setCode("0").setMessage("这个名字已经有人取啦");
        }
        else {
            result.setCode("1");
        }
        return result;
    }

    @Override
    public List<TeamEntity> getAllteam() {
        return teamDao.getAllTeam();
    }

    @Override
    public List<TeamSubjectEntity> getAllTeamSubject() {
        return tSubjectDao.getAllTeamSubject();
    }

    @Override
    public void register(RegisterEntity registerEntity) {
        UserEntity user = new UserEntity();
        TeamEntity team = new TeamEntity();
        //再次确认手机号是否已被注册
        if (!userDao.mobileCheck(registerEntity.getMobile())) {
            //是否参加个人赛
            if (registerEntity.isPersonal()) {
                //参加个人赛，且参加团队赛
                if (registerEntity.getTid() != null && registerEntity.getTid().equals("1111")) {
                    user.setId(UUID.randomUUID().toString().replace("-", ""));
                    user.setAuthority(0);
                    user.setItem(registerEntity.getItem());
                    user.setMajor(registerEntity.getMajor());
                    user.setMobile(registerEntity.getMobile());
                    user.setName(registerEntity.getName());
                    user.setPassword(registerEntity.getPassword());
                    user.setRemark(registerEntity.getRemark());
                    //user.setTid("1111");  tid默认值为1111 仅参加个人赛的同学都在这个团队里面
                    userDao.insertSelective(user);
                }
                //参加团队赛，不为队长
                else if (registerEntity.getAuthority() == 0) {
                    user.setId(UUID.randomUUID().toString().replace("-", ""));
                    user.setAuthority(0);
                    user.setItem(registerEntity.getItem());
                    user.setMajor(registerEntity.getMajor());
                    user.setMobile(registerEntity.getMobile());
                    user.setName(registerEntity.getName());
                    user.setPassword(registerEntity.getPassword());
                    user.setRemark(registerEntity.getRemark());
                    user.setTid(registerEntity.getTid());
                    userDao.insertSelective(user);
                }
                //是队长，同时注册个人和团队信息
                else {
                    if (!teamDao.tnameCheck(registerEntity.getTname())) {
                        String teamid = UUID.randomUUID().toString().replace("-", "");
                        String userid = UUID.randomUUID().toString().replace("-", "");
                        team.setId(teamid);
                        user.setId(userid);
                        team.setLeaderid(userid);
                        team.setSlogan(registerEntity.getSlogan());
                        team.setTitem(registerEntity.getTitem());
                        team.setTname(registerEntity.getTname());
                        user.setAuthority(1);
                        user.setItem(registerEntity.getItem());
                        user.setMajor(registerEntity.getMajor());
                        user.setMobile(registerEntity.getMobile());
                        user.setName(registerEntity.getName());
                        user.setPassword(registerEntity.getPassword());
                        user.setRemark(registerEntity.getRemark());
                        user.setTid(teamid);
                        userDao.insertSelective(user);
                        teamDao.insertSelective(team);
                    }
                }
            }
            //不参加个人赛 是否为队长
            else if (registerEntity.getAuthority() == 0) {
                user.setId(UUID.randomUUID().toString().replace("-", ""));
                user.setAuthority(0);
                //user.setItem(registerEntity.getItem());  不参加个人赛 使用默认的Item“团队赛”
                user.setMajor(registerEntity.getMajor());
                user.setMobile(registerEntity.getMobile());
                user.setName(registerEntity.getName());
                user.setPassword(registerEntity.getPassword());
                user.setRemark(registerEntity.getRemark());
                user.setTid(registerEntity.getTid());
                userDao.insertSelective(user);
            }
            //是队长，同时注册个人和团队信息
            else {
                String teamid = UUID.randomUUID().toString().replace("-", "");
                String userid = UUID.randomUUID().toString().replace("-", "");
                team.setId(teamid);
                user.setId(userid);
                team.setLeaderid(userid);
                team.setSlogan(registerEntity.getSlogan());
                team.setTitem(registerEntity.getTitem());
                team.setTname(registerEntity.getTname());
                user.setAuthority(1);
                //user.setItem(registerEntity.getItem());  不参加个人赛 使用默认的Item“团队赛”
                user.setMajor(registerEntity.getMajor());
                user.setMobile(registerEntity.getMobile());
                user.setName(registerEntity.getName());
                user.setPassword(registerEntity.getPassword());
                user.setRemark(registerEntity.getRemark());
                user.setTid(teamid);
                userDao.insertSelective(user);
                teamDao.insertSelective(team);
            }
        }
    }

    @Override
    public DetailEntity getDetailsByMobile(String mobile) {
        return userDao.getDetailsByMobile(mobile);
    }

    @Override
    public List<UserEntity> getMemberByMobile(String mobile) {
        return userDao.getMemberByMobile(mobile);
    }

    @Override
    public Result deleteUserById(String memberid) {
        if (userDao.getAuthorityById(memberid)==1){
            result.setCode("0").setMessage("队长不可删除");
        }
        else if (userDao.deleteByPrimaryKey(memberid)!=0){
            result.setCode("1").setMessage("删除成功");
        }
        else {
            result.setCode("0").setMessage("删除失败");
        }
        return result;
    }

    @Override
    public Result updateTeamItem(String mobile, String itemid) {
        teamDao.updateTeamItem(itemid, mobile);
        result.setMessage("选择项目成功");
        return result;
    }


}
