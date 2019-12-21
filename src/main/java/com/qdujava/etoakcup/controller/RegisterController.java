package com.qdujava.etoakcup.controller;

import com.qdujava.etoakcup.entity.DetailEntity;
import com.qdujava.etoakcup.service.RegisterService;
import com.qdujava.etoakcup.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;


/**
 * 比赛报名controller
 * @Author: liangbin
 * @Date: 2018/4/13 16:28
 */

@RestController
@RequestMapping("/user")
public class RegisterController {

    @Autowired
    RegisterService registerService;


    //检查手机号是否已注册
    @GetMapping("/mobilecheck")
    public Result mobileCheck(@RequestParam String mobile) {
        return registerService.mobileCheck(mobile);
    }

    //检查队伍名是否已注册
    @GetMapping("/tnamecheck")
    public Result tnameCheck(@RequestParam String tname) {
        return registerService.tnameCheck(tname);
    }

    @GetMapping()
    public DetailEntity getDetails(@RequestParam String mobile, HttpSession session) {
        session.setAttribute("members",registerService.getMemberByMobile(mobile));
        return registerService.getDetailsByMobile(mobile);
    }

    @GetMapping("/delete")
    public Result deleteMember(@RequestParam String memberid, @RequestParam String mobile, HttpSession session) {
        Result result = registerService.deleteUserById(memberid);
        session.setAttribute("members",registerService.getMemberByMobile(mobile));
        return result;
    }

    @GetMapping("/updateitem")
    public Result updateItem(@RequestParam String mobile, @RequestParam String itemid) {
        Result result = registerService.updateTeamItem(mobile, itemid);
        return result;
    }

}
