package com.qdujava.etoakcup.util;

import com.qdujava.etoakcup.dao.SubjectEntityMapper;
import com.qdujava.etoakcup.dao.UserEntityMapper;
import com.qdujava.etoakcup.entity.SubjectEntity;
import com.qdujava.etoakcup.entity.UserEntity;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Tuple;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 题目生成工具类
 * @Author: liangbin
 * @Date: 2018/4/27 16:19
 */
@Component
public class SubjectsGenerator implements InitializingBean {

    @Autowired
    SubjectEntityMapper subjectDao;

    @Autowired
    UserEntityMapper userDao;

    private JedisPool pool;

    private List<String> allJavaSubjectsId;
    private List<String> allPHPSubjectsId;
    private List<String> allCSubjectsId;

    //生成题目
    public void createTest(String userid) {
        Jedis jedis = pool.getResource();
        jedis.ltrim(userid,1,0);
        String language = userDao.getItemById(userid);
        List<Integer> randomNums;
        List<String> currentUserSubjects = new ArrayList<>();
        if (language.equals("Java")) {
            randomNums = RandomNum.getNum(30,0,allJavaSubjectsId.size());
            randomNums.forEach(num-> currentUserSubjects.add(allJavaSubjectsId.get(num)));
        } else if (language.equals("PHP")) {
            randomNums = RandomNum.getNum(30,0,allPHPSubjectsId.size());
            randomNums.forEach(num-> currentUserSubjects.add(allPHPSubjectsId.get(num)));
        } else if (language.equals("C")){
            randomNums = RandomNum.getNum(30,0,allCSubjectsId.size());
            randomNums.forEach(num-> currentUserSubjects.add(allCSubjectsId.get(num)));
        }
        currentUserSubjects.forEach(subject-> jedis.lpush(userid,subject));
        jedis.close();
    }


    public String getSubjectId(String userid, int requestid) {
        Jedis jedis = pool.getResource();
        String subjectid = jedis.lindex(userid,requestid);
        jedis.close();
        return subjectid;
    }

    //获取某个用户所有的Subject
    public List<SubjectEntity> getAllSubjects(String userid) {
        Jedis jedis = pool.getResource();
        List<String> allSubjectsId = jedis.lrange(userid,0,-1);
        List<SubjectEntity> userSubjects = new ArrayList<>();
        allSubjectsId.forEach(id-> {
            SubjectEntity subject = subjectDao.selectByPrimaryKey(id);
            userSubjects.add(subject);
            jedis.set(subject.getId(),subject.getAnswer());
        });

        jedis.close();
        return userSubjects;
    }

    //判卷
    public int countAnswer(String[] answer,UserEntity currentUser) {
        AtomicInteger score = new AtomicInteger(0);
        try (Jedis jedis = pool.getResource()) {
            List<String> allSubjectsId = jedis.lrange(currentUser.getId(), 0, -1);
            for (int i = 0; i < allSubjectsId.size(); i++) {
                String rightAnswer = jedis.get(allSubjectsId.get(i));
                if (answer[i].equals(rightAnswer)) {
                    score.getAndIncrement();
                }
            }
            String language = userDao.getItemById(currentUser.getId());
            String rankName;
            if (language.equals("Java")) {
                rankName = "JavaRank";
            } else if (language.equals("PHP")) {
                rankName = "PHPRank";
            } else {
                rankName = "CRank";
            }
            jedis.zadd(rankName, score.get(), currentUser.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return score.get();
    }

    //获取排名
    public List<HashMap<String,String>> getRank(String language) {
        List<HashMap<String, String>> rankList = new ArrayList<>();
        try (Jedis jedis = pool.getResource()) {
            String rankName = language + "Rank";
            for (Tuple tuple : jedis.zrevrangeWithScores(rankName, 0, 100)) {
                HashMap<String, String> singleScore = new HashMap<>();
                String name = subjectDao.selectNameByPrimaryKey(tuple.getElement());
                String mobile = subjectDao.selectMobileByPrimaryKey(tuple.getElement());
                singleScore.put("name", name + "," + mobile);
                singleScore.put("score", String.valueOf(tuple.getScore()));
                rankList.add(singleScore);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rankList;
    }

    //确认是否已经提交过
    public boolean checkIfSubmited(String userid) {
        Jedis jedis = pool.getResource();
        boolean flag =  jedis.zscore("JavaRank", userid) == null && jedis.zscore("CRank", userid) == null && jedis.zscore("PHPRank", userid) == null;
        jedis.close();
        return flag;
    }

    @Override
    public void afterPropertiesSet() {
        pool = new JedisPool();
        allJavaSubjectsId = subjectDao.getAllJavaSubjectsId();
        allPHPSubjectsId = subjectDao.getAllPHPSubjectsId();
        allCSubjectsId = subjectDao.getAllCSubjectsId();
    }
}
