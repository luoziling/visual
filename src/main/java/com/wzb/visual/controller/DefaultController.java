package com.wzb.visual.controller;


import com.wzb.visual.BaiduAI.FaceContrast;
import com.wzb.visual.constant.Constant;
import com.wzb.visual.model.User;
import com.wzb.visual.tools.Base64Image;

import com.wzb.visual.tools.FileUtil;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 *
 */
@Controller
@RequestMapping(value = "/")
public class DefaultController {

    /**
     * 首页
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home(HttpServletRequest request, Model model) {
        User currentUser = (User) request.getSession().getAttribute(Constant.CURRENT_USER);
        model.addAttribute(Constant.CURRENT_USER, currentUser);
        return "/login";
    }

    /**
     * 个人中心
     */
    @RequestMapping(value = "/PersonalCenter", method = RequestMethod.GET)
    public String PersonalCenter(HttpServletRequest request, Model model) {
        User currentUser = (User) request.getSession().getAttribute(Constant.CURRENT_USER);
        if (currentUser == null) {
            //返回首页
            return "redirect:/";
        }
        model.addAttribute(Constant.CURRENT_USER, currentUser);
        return "/PersonalCenter";
    }




}
