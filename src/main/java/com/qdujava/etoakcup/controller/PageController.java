package com.qdujava.etoakcup.controller;

import com.qdujava.etoakcup.entity.DetailEntity;
import com.qdujava.etoakcup.entity.RegisterEntity;
import com.qdujava.etoakcup.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageController {
    @Autowired
    RegisterService registerService;

    @RequestMapping("/")
    public String index(Model model) {
        model.addAttribute("teamlist",registerService.getAllteam());
        model.addAttribute("tsubjectlist",registerService.getAllTeamSubject());
        model.addAttribute("newuser",new RegisterEntity());
        return "index";
    }

    //注册
    @PostMapping("/user")
    public String register(@ModelAttribute RegisterEntity newuser) {
        registerService.register(newuser);
        return "notice";
    }

    @RequestMapping("/notice")
    public String notice() {
        return "notice";
    }

    @RequestMapping("/details")
    public String showDetails(Model model) {
        model.addAttribute("tsubjectlist",registerService.getAllTeamSubject());
        return "showdetails";
    }

    @RequestMapping("/contest/question")
    public String question() {
        return "question";
    }

    @RequestMapping("/solo")
    public String compete() {
        return "login";
    }

    @RequestMapping("/rank")
    public String rank() {
        return "rank";
    }

}
