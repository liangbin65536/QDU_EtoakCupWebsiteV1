package com.qdujava.etoakcup.service.Impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qdujava.etoakcup.dao.SubjectEntityMapper;
import com.qdujava.etoakcup.dao.UserEntityMapper;
import com.qdujava.etoakcup.entity.SubjectEntity;
import com.qdujava.etoakcup.entity.UserAnswer;
import com.qdujava.etoakcup.entity.UserEntity;
import com.qdujava.etoakcup.service.MatchService;
import com.qdujava.etoakcup.util.Result;
import com.qdujava.etoakcup.util.SubjectsGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * @Author: liangbin
 * @Date: 2018/4/28 14:09
 */
@Service
public class MatchServiceImpl implements MatchService {

    @Autowired
    UserEntityMapper userDao;
    @Autowired
    SubjectEntityMapper subjectDao;
    @Autowired
    SubjectsGenerator generator;

    @Override
    public Result<UserEntity> login(String mobile, String password) {
        UserEntity user = userDao.login(mobile, password);
        Result<UserEntity> result = new Result<>();
        if (user==null) {
            result.setMessage("登录失败，请用手机号+密码登录").setCode("2");
            return result;
        }
        if (generator.checkIfSubmited(user.getId())) {
            result.setMessage("登录成功").setCode("1").setData(user);
        } else {
            result.setMessage("你已经参加过比赛了兄弟").setCode("0").setData(user);
        }
        return result;
    }

    @Override
    public SubjectEntity getSubjectById(String userid, int requestid) {
        return subjectDao.selectByPrimaryKey(generator.getSubjectId(userid, requestid));
    }

    @Override
    public List<SubjectEntity> getAllSubjects(String userid) {
        List<SubjectEntity> subjects = generator.getAllSubjects(userid);
        subjects.forEach(subject -> subject.setAnswer("0"));
        return subjects;
    }

    @Override
    public Result markPaper(UserEntity user, String answer) {
        ObjectMapper mapper = new ObjectMapper();
        int score = 0;
        try {
            UserAnswer userAnswer = mapper.readValue(answer, UserAnswer.class);
            String[] arrayAnswer = userAnswer.getAnswer();
            String[] convertAnswer = new String[arrayAnswer.length];
            for (int i=0; i<arrayAnswer.length; i++) {
                switch (arrayAnswer[i]) {
                    case "0":
                        convertAnswer[i] = "A";
                        break;
                    case "1":
                        convertAnswer[i] = "B";
                        break;
                    case "2":
                        convertAnswer[i] = "C";
                        break;
                    case "3":
                        convertAnswer[i] = "D";
                        break;
                    case "-1":
                        convertAnswer[i] = "null";
                        break;
                }
            }
            System.out.println(Arrays.toString(convertAnswer));
            System.out.println(generator.countAnswer(convertAnswer,user));
            score =  generator.countAnswer(convertAnswer,user);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Result<Integer> result = new Result<>();
        result.setData(score).setCode("1").setMessage("批卷完成");
        return result;
    }

}
