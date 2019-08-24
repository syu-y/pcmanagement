package com.example.pcmanagement.controller;

import java.util.List;

import com.example.pcmanagement.domain.model.PC;
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
import org.springframework.web.bind.annotation.RequestBody;



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
    @GetMapping("/userDetailEdit/{userId:.+}")
    public String getUserDetailEdit(@ModelAttribute SignupForm form, Model model,
                                 @PathVariable("userId")String userId){
        System.out.println("userId = " + userId);
        //ここでセッションユーザの権限で詳細・編集画面に分岐させたい
        model.addAttribute("contents", "login/userDetailEdit::userEdit_contents");

        if(userId != null && (userId.length() > 0) ){
            User user = userService.getUser(userId);
            form.setUserId(user.getUserId());
            form.setUserName(user.getUserName());
            form.setPassword(user.getPassword());
            form.setPermission(user.getPermission());
            model.addAttribute("signupForm", form);
        }
        return "login/homeLayout";
    }

    @PostMapping(value="/userDetailEdit", params="update")
    public String postUserDetailUpdate(@ModelAttribute SignupForm form, Model model){

        User user = new User();
        user.setUserId(form.getUserId());
        user.setUserName(form.getUserName());
        user.setPassword(form.getPassword());
        user.setPermission(form.getPermission());

        try {
            userService.addUser(user);
        }
        return getUserList(model);
    }

    @PostMapping(value="/userDetailEdit", params="delete")
    public String postUserDetailDelete(@ModelAttribute SignupForm form, Model model){
        userService.removeUserByUserId(form.getUserId());
        return getUserList(model);
    }
}
