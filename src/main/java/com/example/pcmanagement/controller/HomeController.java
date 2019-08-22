package com.example.pcmanagement.controller;

import java.util.List;

import com.example.pcmanagement.domain.model.PC;
import com.example.pcmanagement.domain.model.PcRegisterForm;
import com.example.pcmanagement.domain.model.SignupForm;
import com.example.pcmanagement.domain.model.User;
import com.example.pcmanagement.service.PCService;
import com.example.pcmanagement.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class HomeController{
    @Autowired
    UserService userService;
    @Autowired
    PCService pcService;

    @GetMapping("/home")
    public String getHome(Model model){
        model.addAttribute("contents", "login/home::home_contents");
        return "login/homeLayout";
    }
    @PostMapping("/logout")
    public String postLogout(Model model){
        return "redirect:/login";
    }

    @GetMapping("/userList") public String getUserList(Model model) {
        model. addAttribute("contents", "login/userList::userList_contents");
        List<User> userList = userService.getUsers();
        model.addAttribute("userList", userList);
        return "login/homeLayout";
    }

    @GetMapping("/pcList") public String getPCList(Model model) {
        model. addAttribute("contents", "login/pcList::pcList_contents");
        List<PC> pcList = pcService.getPCs();
        model.addAttribute("pcList", pcList);
        return "login/homeLayout";
    }
    @GetMapping("/userDEtail/{userId:.+}")
    public String getUserDetail(@ModelAttribute SignupForm form, Model model, @PathVariable("userId")String userId){
        System. out. println(" userId = " + userId);
        model.addAttribute("contents", "login/userDetail::userDetail_contents");
        if(userId != null && userId.equals("")){
            User user = userService.getUser(userId);
            form.setUserId(userId);
            form.setUserName(user.getUserName());
            form.setPassword(user.getPassword());
            form.setPermission(user.getPermission());
            model.addAttribute("signupForm", form);
        }
        return "login/homeLayout";
    }
}
