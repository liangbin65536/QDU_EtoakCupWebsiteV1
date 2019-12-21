package com.qdujava.etoakcup.controller;

import com.qdujava.etoakcup.entity.SubjectEntity;
import com.qdujava.etoakcup.entity.UserEntity;
import com.qdujava.etoakcup.service.MatchService;
import com.qdujava.etoakcup.util.Result;
import com.qdujava.etoakcup.util.SubjectsGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;

/**
 * @Author: liangbin
 * @Date: 2018/4/27 13:51
 */
@RestController
@RequestMapping("/contest")
public class MatchController {
    @Autowired
    SubjectsGenerator generator;

    @Autowired
    MatchService matchService;

    HttpSession session;

    @RequestMapping("/login")
    public Result<UserEntity> login(@RequestParam String username, @RequestParam String password, HttpServletRequest request) {
        Result<UserEntity> result = matchService.login(username, password);
        if (result.getCode().equals("1")) {
            generator.createTest(result.getData().getId());
            session = request.getSession();
            session.setAttribute("user",result.getData());
            session.setMaxInactiveInterval(100 * 60);
        }
        return result;
    }

    //根据请求的id获取对应的subject
    @RequestMapping("/ques/{requestid}")
    public SubjectEntity getSubject(@PathVariable("requestid") int requestid, HttpServletRequest request) {
        session = request.getSession();
        UserEntity user = (UserEntity) session.getAttribute("user");
        return matchService.getSubjectById(user.getId(),requestid);
    }

    //获取所有的subjectid
    @RequestMapping("/allsubjects")
    public List<SubjectEntity> getAllSubjects(HttpServletRequest request) {
        session = request.getSession();
        return matchService.getAllSubjects(((UserEntity) session.getAttribute("user")).getId());
    }

    //接收试卷答案
    @PostMapping("/submit")
    public Result submit(HttpServletRequest request,@RequestBody String answer) {
        return matchService.markPaper((UserEntity)request.getSession().getAttribute("user"),answer);
    }

    @RequestMapping("/currentrank")
    public List<HashMap<String,String>> getRank(@RequestParam String language) {
        return generator.getRank(language);
    }

    @RequestMapping("/keepsession")
    public String keepSession() {
        System.out.println("Keep Alive");
        return "OK";
    }
}
