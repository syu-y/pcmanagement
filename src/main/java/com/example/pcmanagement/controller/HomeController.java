package com.example.pcmanagement.controller;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.example.pcmanagement.domain.model.PC;
import com.example.pcmanagement.domain.model.PcEditForm;
import com.example.pcmanagement.domain.model.RentalLog;
import com.example.pcmanagement.domain.model.SignupForm;
import com.example.pcmanagement.domain.model.User;
import com.example.pcmanagement.service.PCService;
import com.example.pcmanagement.service.RentalLogService;
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
    RentalLogService rentalLogService;
    @Autowired
	PasswordEncoder passwdEncoder;

    @GetMapping("/home")
    public String getHome(Model model){
        Calendar today = Calendar.getInstance();
        model.addAttribute("contents", "login/home::home_contents");
        List<PC> pcList = new ArrayList<>();
        List<PC> recycleList = new ArrayList<>();
        pcList = pcService.getPCs();
        for(PC pc : pcList){
            Calendar recycleDay = Calendar.getInstance();
            recycleDay.setTime(pc.getRecycleDate());
            if(today.after(recycleDay)) recycleList.add(pc);
        }
        model.addAttribute("recycleList", recycleList);
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
        model.addAttribute("contents", "login/userList::userList_contents");
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

            List<RentalLog> alllogList = rentalLogService.getRentalLogs();
            List<RentalLog> logList = new ArrayList<>();
            for(RentalLog log: alllogList){
                if(log.getUserId().equals(form.getUserId())){
                    logList.add(log);
                }
            }
            model.addAttribute("myLogList", logList);
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

            List<RentalLog> alllogList = rentalLogService.getRentalLogs();
            List<RentalLog> logList = new ArrayList<>();
            for(RentalLog log: alllogList){
                if(log.getPcId().equals(form.getPcId())){
                    logList.add(log);
                }
            }
            model.addAttribute("myLogList", logList);
        }
        List<User> userList = userService.getUsers();
        model.addAttribute("userList", userList);
        return "login/homeLayout";
    }

    // PC情報更新
    @PostMapping(value="/pcDetailEdit", params="update")
    public String postPCDetailUpdate(@ModelAttribute PcEditForm form, Model model){

        Calendar cal = Calendar.getInstance();
        Date today = new Date(cal.getTime().getTime());

        PC pc = pcService.getPC(form.getPcId());
        System.out.println(form);

        //if(form.getUserId() == null && (form.getUserId().length() == 0) ){
        if(form.getUserId() == null || form.getUserId().length() == 0 || !form.getState().equals("使用中")){
            pc.setUserId(null);
            pc.setUserName(null);
        }
        else{
            User user = userService.getUser(form.getUserId());
            pc.setUserId(user.getUserId());
            pc.setUserName(user.getUserName());
        }

        //   ログデータの書き込み
        //　借りるとき
        if(form.getState().equals("使用中")){
            //　パターン1
            //　未使用・廃棄・登録中　->　使用中（使用者いなかった前提）
            if(!form.getState().equals(pc.getState())){
                if(pc.getUserId() != null){
                    RentalLog log = new RentalLog();
                    log.setPcId(pc.getPcId());
                    log.setUserId(pc.getUserId());
                    log.setStartDate(today);
                    log.setEndDate(null);
                    rentalLogService.addRentalLog(log);
                }
            }
            //　パターン2
            //　使用中　-> 使用中　（返却を挟まない）
            else{
                List<RentalLog> notReturnLogs = rentalLogService.findNotReturnLog();
                for(RentalLog log: notReturnLogs){
                    //　未返却のPCのうち対象のPCを検索
                    if(log.getPcId().equals(form.getPcId())){
                        //　前の使用者は返却処理
                        log.setEndDate(today);
                        rentalLogService.addRentalLog(log);
                        //　新しい使用者のログ作成
                        RentalLog newLog = new RentalLog();
                        newLog.setPcId(pc.getPcId());
                        newLog.setUserId(pc.getUserId());
                        newLog.setStartDate(today);
                        newLog.setEndDate(null);
                        rentalLogService.addRentalLog(newLog);
                        break;
                    }
                }
            }
        }
        //　返すとき
        //　使用中 -> 未使用・登録中・廃棄
        else{
            if(pc.getState().equals("使用中")){
                List<RentalLog> notReturnLogs = rentalLogService.findNotReturnLog();
                for(RentalLog log: notReturnLogs){
                    //　未返却のPCのうち対象のPCを検索
                    if(log.getPcId().equals(form.getPcId())){
                        log.setEndDate(today);
                        rentalLogService.addRentalLog(log);
                        break;
                    }
                }
            }
        }

        pc.setState(form.getState());
        pc.setPurpose(form.getPurpose());
        pcService.addPC(pc);
        return getPCList(model);
    }

    @GetMapping("/logList") public String getRentalLogList(Model model) {
        model. addAttribute("contents", "login/logList::logList_contents");
        List<RentalLog> logList = rentalLogService.getRentalLogs();
        model.addAttribute("logList", logList);
        return "login/homeLayout";
    }
}
