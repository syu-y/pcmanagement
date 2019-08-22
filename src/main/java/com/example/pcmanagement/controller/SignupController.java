package com.example.pcmanagement.controller;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.pcmanagement.domain.model.GroupOrder;
import com.example.pcmanagement.domain.model.SignupForm;
import com.example.pcmanagement.domain.model.User;
import com.example.pcmanagement.service.UserService;

import java.util.LinkedHashMap;
import java.util.Map;

@Controller
public class SignupController {

    @Autowired
    private UserService userService;

    // Make radioButton
    private Map<String, String> radioMarriage;
    private Map<String, String> initRadioMarriage(){
        Map<String, String> radio = new LinkedHashMap<>();
        radio.put("Married", "true");
        radio.put("Unmarried", "false");
        return radio;
    }
    // Set radioButton to Model object
    @GetMapping("/signup")
    public String getSignUp(@ModelAttribute SignupForm form, Model model){
        radioMarriage = initRadioMarriage();
        model.addAttribute("radioMarriage", radioMarriage);
        model. addAttribute("contents", "login/signup::signup_contents");
        return "login/homeLayout";
    }
    // Redirect to login.html
    @PostMapping("/signup")
    public String postSignUp(@ModelAttribute @Validated(GroupOrder.class) SignupForm form, BindingResult bindingResult, Model model){
        model. addAttribute("contents", "login/signup::signup_contents");
        if(bindingResult.hasErrors()){
            return getSignUp(form, model);
        }

        if(!userService.checkUser(form.getUserId())){
            System.out.println(form);
            User user = new User();
            user.setUserId(form.getUserId());
            user.setUserName(form.getUserName());
            user.setPassword(form.getPassword());
            user.setPermission(form.getPermission());
            userService.addUser(user);
        }
        else{
            model.addAttribute("userIdError", "ユーザーIDが重複しています！");
            return getSignUp(form, model);
        }
        return "redirect:/usrList";
    }
}
