package com.wzb.visual.controller;


import com.wzb.visual.BaiduAI.FaceContrast;
import com.wzb.visual.constant.Constant;
import com.wzb.visual.model.User;
import com.wzb.visual.tools.Base64Image;

import com.wzb.visual.tools.FileUtil;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

import java.io.File;
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

    //todo:这边的返回不应该是map可以是json数据 也可以把json数据作为map中的一部分进行返回比如data字段对应的值
    @RequestMapping(value = "/face-recognition", method = RequestMethod.POST)
    public Map<String, Object> faceRecognition(HttpServletRequest request, @RequestBody JSONObject params, @RequestParam("file") MultipartFile[] file) {
        //使用linkedHashMap是为了保证这个map中存储时按照存入的顺序存储
        //顾名思义，HashMap根据键计算和管理哈希值，因此顺序未定义。
        // TreeMap按键的自然顺序排序。
        // LinkedHashMap保存插入的顺序，以便使用HashMap和LinkedList进行管理。

        Map<String, Object> resultMap = new LinkedHashMap<>();
        //在这边要接收从python端传来的数据并且转成jpg格式
        //android那边是通过上面通信的（通过蓝牙通信
        //也有输入输出流
        //服务器这边采用post进行通信
        //todo:要从手机端开始多加一个username的信息传到树莓派，树莓派再通过post请求发照片和username传给服务器
        // 使用什么进行数据传递  数据流还是直接可以把图片传过来接收

        //todo:接收数据
        String recvFilePath = "";
//        List<String> fileName = new ArrayList<>();
        String username = params.getString("username");
        try {
            recvFilePath = FileUtil.uploadImage(request, username, file[0], false);
            System.out.println("recvFilePath:" + recvFilePath);

        } catch (Exception e) {
            e.printStackTrace();
        }


        String path = "G:\\java_project_idea\\visual\\Img";


        //用户图片文件名
        String origName = file[0].getOriginalFilename();// 文件原名称
        String userFilePath = path + username + "." + origName.split("\\.")[1];
//        String userFileTruePath = "";

        //判断图片文件后缀形成真实文件名
//        if (new File(userFilePath + ".jpg").exists()) {
//            userFileTruePath = userFilePath + ".jpg";
//        } else if (new File(userFilePath + ".png").exists()) {
//            userFileTruePath = userFilePath + ".png";
//        } else if (new File(userFilePath + ".gif").exists()) {
//            userFileTruePath = userFilePath + ".gif";
//        }


//        String nowName = path_deposit + "." + origName.split("\\.")[1];

        System.out.println(userFilePath);

        String file1 = Base64Image.imageToBase64(recvFilePath);
        String file2 = Base64Image.imageToBase64(userFilePath);

        //baiduAI使用的是org.json.JSONObject
        org.json.JSONObject resData = FaceContrast.contrast(file1, file2);

        resultMap.put("data", resData);
        //todo:可以在这里保存开锁记录到数据库中

        //返回了树莓派后续要在树莓派上进行判断
        return resultMap;
    }


}
