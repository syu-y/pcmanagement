package com.example.pcmanagement.controller;

import java.util.Date;
import java.util.List;

import com.example.pcmanagement.domain.model.PC;
import com.example.pcmanagement.domain.model.PcEditForm;
import com.example.pcmanagement.domain.model.SignupForm;
import com.example.pcmanagement.domain.model.User;
import com.example.pcmanagement.service.PCService;
import com.example.pcmanagement.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;



@Controller
public class HomeController{
    @Autowired
    UserService userService;
    @Autowired
    PCService pcService;
    @Autowired
	PasswordEncoder passwdEncoder;

    @GetMapping("/home")
    public String getHome(Model model){
        Date today = new Date();
        model.addAttribute("contents", "login/home::home_contents");
        List<PC> pcList = pcService.getPCs();
        for(PC pc : pcList){
        }
        model.addAttribute("pcList", pcList);
        return "login/homeLayout";
    }
    @PostMapping("/logout")
    public String postLogout(Model model){
        return "redirect:/login";
    }
    @GetMapping("/logout")
    public String getLogout(Model model){
        return postLogout(model);
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
        //user.setPassword(form.getPassword());
        user.setPassword(passwdEncoder.encode(form.getPassword()));
        user.setPermission(form.getPermission());

        userService.addUser(user);

        return getUserList(model);
    }

    @PostMapping(value="/userDetailEdit", params="delete")
    public String postUserDetailDelete(@ModelAttribute SignupForm form, Model model){
        userService.removeUserByUserId(form.getUserId());
        return getUserList(model);
    }

    // PC情報表示/編集機能
    @GetMapping("/pcDetailEdit/{pcId:.+}")
    public String getPCDetailEdit(@ModelAttribute PcEditForm form,
        Model model,@PathVariable("pcId")String pcId){
        System.out.println("pcId = " + pcId);
        //ここでセッションユーザの権限で詳細・編集画面に分岐させたい
        model.addAttribute("contents", "login/pcDetailEdit::pcEdit_contents");

        if(pcId != null && (pcId.length() > 0) ){
            PC pc = pcService.getPC(pcId);
            form.setPcId(pc.getPcId());
            form.setType(pc.getType());
            form.setBuyDate(pc.getBuyDate());
            form.setRecycleDate(pc.getRecycleDate());
            form.setUserId(pc.getUserId());
            form.setUserName(pc.getUserName());
            form.setState(pc.getState());
            form.setPurpose(pc.getPurpose());
            form.setMaker(pc.getMaker());
            form.setSerial(pc.getSerial());
            form.setMacAddress(pc.getMacAddress());
            form.setCpu(pc.getCpu());
            form.setScore(pc.getScore());
            form.setMemory(pc.getMemory());
            form.setResolution(pc.getResolution());
            form.setGraphics(pc.getGraphics());
            form.setOs(pc.getOs());
            model.addAttribute("pcEditForm", form);
        }
        List<User> userList = userService.getUsers();
        model.addAttribute("userList", userList);
        return "login/homeLayout";
    }

    // PC情報更新
    @PostMapping(value="/pcDetailEdit", params="update")
    public String postPCDetailUpdate(@ModelAttribute PcEditForm form, Model model){

        PC pc = pcService.getPC(form.getPcId());
        System.out.println(form);

        //if(form.getUserId() == null && (form.getUserId().length() == 0) ){
        if(form.getUserId() == null || form.getUserId().length() == 0){
            pc.setUserId(null);
            pc.setUserName(null);
        }
        else{
            User user = userService.getUser(form.getUserId());
            pc.setUserId(user.getUserId());
            pc.setUserName(user.getUserName());
        }
        pc.setState(form.getState());
        pc.setPurpose(form.getPurpose());
        pcService.addPC(pc);
        return getPCList(model);
    }
}
