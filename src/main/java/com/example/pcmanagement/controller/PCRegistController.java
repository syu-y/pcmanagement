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
import com.example.pcmanagement.domain.model.PC;
import com.example.pcmanagement.domain.model.PcRegisterForm;
import com.example.pcmanagement.domain.model.PcRegisterForm;
import com.example.pcmanagement.domain.model.SignupForm;
import com.example.pcmanagement.domain.model.User;
import com.example.pcmanagement.service.PCService;
import com.example.pcmanagement.service.UserService;

import java.sql.Date;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.Map;

@Controller
public class PCRegistController {

    @Autowired
    private PCService pcService;

    @GetMapping("/pcRegister")
    public String getPcRegist(@ModelAttribute PcRegisterForm form, Model model){
        model. addAttribute("contents", "login/pcRegister::pcRegister_contents");
        return "login/homeLayout";
    }

    @PostMapping("/pcRegister")
    public String postPcRegist(@ModelAttribute @Validated(GroupOrder.class) PcRegisterForm form, BindingResult bindingResult, Model model){
        model. addAttribute("contents", "login/pcRegister::pcRegister_contents");
        System.out.println(form);

        if(bindingResult.hasErrors()){
            System.out.println(form);
            return getPcRegist(form, model);
        }

        if(!pcService.checkPC(form.getPcId())){
            System.out.println(form);

            PC pc = new PC();
            pc.setPcId(form.getPcId());
            pc.setPurpose(form.getPurpose());
            // 日付関連
            pc.setBuyDate(form.getBuyDate());
            Calendar cal = Calendar.getInstance();
            cal.setTime(form.getBuyDate());
            cal.add(Calendar.YEAR, 4);
            pc.setRecycleDate(new Date(cal.getTime().getTime()));

            pc.setPurpose(form.getPurpose());
            //書かなくてもデフォルトでnull
            //pc.setUserName(null);
            //pc.setUserId(null);
            pc.setState(form.getState());
            pc.setMaker(form.getMaker());
            pc.setSerial(form.getSerial());
            pc.setMacAddress(form.getMacAddress());
            pc.setCpu(form.getCpu());
            pc.setScore(form.getScore());
            pc.setMemory(form.getMemory());
            pc.setResolution(form.getResolution());
            pc.setGraphics(form.getGraphics());
            pc.setOs(form.getOs());

            pcService.addPC(pc);
        }
        else{
            model.addAttribute("pcIdError", "管理番号が重複しています！");
            return getPcRegist(form, model);
        }
        return "redirect:/pcList";
    }
}
