package com.wzb.visual.controller;

import com.wzb.visual.BaiduAI.FaceContrast;
import com.wzb.visual.tools.Base64Image;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Satsuki
 * @time 2019/4/10 16:55
 * @description:
 */
@RestController
@RequestMapping("/api")
public class APIController {
    //结果老老实实返回了String
    //todo:这边的返回不应该是map可以是json数据 也可以把json数据作为map中的一部分进行返回比如data字段对应的值
    @RequestMapping(value = "/face-recognition", method = RequestMethod.POST)
    public Map<String, Object> faceRecognition(HttpServletRequest request, @RequestParam("username") String username, @RequestParam("file") MultipartFile[] file) {
//    public Map<String, Object> faceRecognition(HttpServletRequest request, @RequestBody JSONObject params, @RequestParam("files") MultipartFile[] file) {
//    public org.json.JSONObject faceRecognition(HttpServletRequest request, @RequestParam("username")String username, @RequestParam("file") MultipartFile[] file) {
//    public String faceRecognition(HttpServletRequest request, @RequestParam("username")String username, @RequestParam("file") MultipartFile[] file) {
        //使用linkedHashMap是为了保证这个map中存储时按照存入的顺序存储
        //顾名思义，HashMap根据键计算和管理哈希值，因此顺序未定义。
        // TreeMap按键的自然顺序排序。
        // LinkedHashMap保存插入的顺序，以便使用HashMap和LinkedList进行管理。

//        System.out.println(params.toString());
        System.out.println(username);
        System.out.println(file.equals(null));
        System.out.println(file.length);
        System.out.println(request.getParameter("file"));
//        System.out.println(request.getParameter("file").getBytes());
        String picData = request.getParameter("file");
//        System.out.println(file[0].isEmpty());
//        System.out.println(file.toString());

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
//        String username = params.getString("username");
//        try {
//            recvFilePath = FileUtil.uploadImage(request, username, file[0], false);
//            System.out.println("recvFilePath:" + recvFilePath);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }


        String path = "G:\\java_project_idea\\visual\\Img";

        recvFilePath = path + "\\test3.jpg";
//        File recvFile = new File(recvFilePath);
//        if(recvFile.exists() && recvFile.isFile()){
//            recvFile.delete();
//        }
        try {
            Base64Image.base64ToImage(picData, recvFilePath);
//            if(recvFile.createNewFile()){
//                System.out.println("Create success");
//                //通过二进制流写入
////                BufferedWriter bw = new BufferedWriter(new FileWriter(recvFilePath,true));
//
////                FileWriter fileWriter = new FileWriter(recvFilePath,true);
//
////                DataOutputStream out = new DataOutputStream(new FileOutputStream(recvFilePath,true));
////
//////                bw.write(request.getParameter("file").getBytes());
//////                bw.write();
////                out.write(request.getParameter("file").getBytes());
////                out.close();
////                Base64Image.base64ToImage(picData,recvFilePath);
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        //用户图片文件名
//        String origName = file[0].getOriginalFilename();// 文件原名称
//        String userFilePath = path + username + "." + origName.split("\\.")[1];
        //todo:这里可以改成StringBuilder(非线程安全
        String userFilePath = path + "\\" + username + ".jpg";
//        String userFileTruePath = "";

//        //判断图片文件后缀形成真实文件名
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
//        todo:再试试这些resData.toJSONArray()

        resultMap.put("data", resData);
        //todo:可以在这里保存开锁记录到数据库中

        //返回了树莓派后续要在树莓派上进行判断
//        return resultMap;
//        return resData.toString();

        return resData.toMap();
    }
}
