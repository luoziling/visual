package com.wzb.visual.controller;


import com.wzb.visual.constant.Constant;
import com.wzb.visual.model.User;
import com.wzb.visual.service.impl.UserServiceImpl;
import com.wzb.visual.dto.AjaxResult;
import com.wzb.visual.dto.ExReturn;
import com.wzb.visual.tools.FileUtil;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.*;

//@RestController
@Controller
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserServiceImpl userService;

    /**
     * 验证登陆
     */

//    @RequestMapping(value = "/api/login",method = RequestMethod.POST)
//    @ResponseBody
//    public AjaxResult login(HttpServletRequest request, HttpServletResponse response){
//        System.out.println("验证登陆");
//        AjaxResult ajaxResult = new AjaxResult();
//        try {
//            String username = request.getParameter("username");
//            System.out.println("username" + username);
//            String password = request.getParameter("password");
//            System.out.println("password" + password);
//            User user = userService.getByUsername(username);
//            System.out.println("user:" + user.toString());
//            if(user!=null){
//                if(password.equals(user.getPassword())){
//                    System.out.println("验证成功");
//                    request.getSession().setAttribute(Constant.CURRENT_USER,user);
//                    ajaxResult.setData(user);
//                    ajaxResult.setSuccess(true);
////                    System.out.println("" + ajaxResult.);
//                }else {
//                    return AjaxResult.fixedError(SatsukiWebError.WRONG_PASSWORD);
//                }
//            }else {
//                return AjaxResult.fixedError(SatsukiWebError.WRONG_USERNAME);
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//            return AjaxResult.fixedError(SatsukiWebError.COMMON);
//        }
//
//        System.out.println("最后成功返回");
//        return ajaxResult;
//    }

    //ResponseBody---加上该注解表明该方法返回值均自动转为json格式传给前端
    @RequestMapping(value = "/api/login", method = RequestMethod.POST)
    @ResponseBody
    public ExReturn login(@RequestBody JSONObject params, HttpServletRequest request, Model model) {
        ExReturn myReturn = new ExReturn();
        System.out.println("验证登陆");
        AjaxResult ajaxResult = new AjaxResult();

        HashMap<String, Object> data = new HashMap<>();

        System.out.println("params:" + params.getString("username") + " " + params.getString("password"));
        try {
//            System.out.println("name+word" + name + " " + word);
//            String username = request.getParameter("username");
            String username = params.getString("username");
            System.out.println("username" + username);
//            String password = request.getParameter("password");
            String password = params.getString("password");
            System.out.println("password" + password);
            User user = userService.getByUsername(username);
            System.out.println("user:" + user.toString());
            if (user != null) {
                if (password.equals(user.getPassword())) {
                    System.out.println("验证成功");
                    request.getSession().setAttribute(Constant.CURRENT_USER, user);
//                    HttpSession session = request.getSession();
//                    session.getAttribute("current_user");
                    User user1 = (User) request.getSession().getAttribute("current_user");
//                    ajaxResult.setData(user);
                    data.put("user", user);

                    model.addAttribute("current_user", user);

                    //在这里可以通过hashmap的形式把data放入其中
                    // 但是跳转了网页之后可能无法获取
                    // 所以最好还是先通过session 这个data可以在同一页面下获取（有待再考虑
                    //user就直接通过session获取把
                    myReturn.setData(data);
                    ajaxResult.setSuccess(true);
//                    System.out.println("" + ajaxResult.);
                    myReturn.setSuccess(true);
                    myReturn.setMsg("success");
//                    myReturn.getData();
                } else {
//                    return AjaxResult.fixedError(SatsukiWebError.WRONG_PASSWORD);
                }
            } else {
//                return AjaxResult.fixedError(SatsukiWebError.WRONG_USERNAME);
            }
        } catch (Exception e) {
            e.printStackTrace();
//            return AjaxResult.fixedError(SatsukiWebError.COMMON);
        }

//        Gson gson = new Gson();
//        String json = gson.toJson(myReturn);


//        System.out.println(json);
        System.out.println("最后成功返回");
//        return json;
//        return ajaxResult;
        return myReturn;
    }

    //    @ResponseBody
//    @RequestMapping(value = "/api/login",method = {RequestMethod.POST,RequestMethod.GET})
//    public String login(HttpServletRequest request, HttpServletResponse response){
//        Boolean flag = false;
//        System.out.println("验证登陆");
////        AjaxResult ajaxResult = new AjaxResult();
//        try {
//            String username = request.getParameter("username");
//            System.out.println("username: " + username);
//            String password = request.getParameter("password");
//            System.out.println("password: " + password);
//            User user = userService.getByUsername(username);
//            System.out.println("user:" + user.toString());
//            if(user!=null){
//                if(password.equals(user.getPassword())){
//                    System.out.println("验证成功");
//                    request.getSession().setAttribute(Constant.CURRENT_USER,user);
//                    flag=true;
////                    ajaxResult.setData(user);
//                }else {
////                    return AjaxResult.fixedError(SatsukiWebError.WRONG_PASSWORD);
//                }
//            }else {
////                return AjaxResult.fixedError(SatsukiWebError.WRONG_USERNAME);
//            }
//        }catch (Exception e){
//            e.printStackTrace();
////            return AjaxResult.fixedError(SatsukiWebError.COMMON);
//        }
//        System.out.println("最后成功返回");
//        if(flag){
//            System.out.println("最后成功返回1");
//            return "cover.html";
////            try {
////                response.sendRedirect("cover");
////            }catch (Exception e){
////                e.printStackTrace();
////            }
//
//        }
//        System.out.println("login");
//        return "login";
////        return ajaxResult;
//    }
//    @RequestMapping(value = "/api/login",method = {RequestMethod.POST,RequestMethod.GET})
//    public String login(HttpServletRequest request, HttpServletResponse response){
//        return "cover.html";
//    }

    /**
     * 修改信息
     */

    @RequestMapping(value = "/api/saveInfo", method = RequestMethod.POST)
    @ResponseBody
    public ExReturn saveInfo(@RequestBody JSONObject params, HttpServletRequest request) {
        ExReturn exReturn = new ExReturn();
        Boolean gender = false;
        System.out.println("进入/api/saveInfo");
        System.out.println("gender:" + params.getBoolean("gender"));
        System.out.println("birthday:" + params.getString("birthday"));
        gender = params.getBoolean("gender");
//        if(params.getBoolean("gender")){
//            gender = false;
//        }else if (params.getBoolean("gender")){
//            gender = true;
//        }
        Date birthday = new Date(0);
        //关于日期的获取以及转换
        String dateString = params.getString("birthday");
        //先看看是什么样的然后再尝试转换成Date
        System.out.println(dateString);
        birthday = Date.valueOf(dateString);


        //可以从session中获取user
        // 然后对数据库中的user进行修改
        // 再获取修改后的user
        // 再加入session再
        // 刷新页面
        User user = (User) request.getSession().getAttribute(Constant.CURRENT_USER);


        try {
            System.out.println("gender1:" + gender);
            user.setGender(gender);
            user.setBirthday(birthday);
            userService.update(user);

            request.getSession().setAttribute(Constant.CURRENT_USER, user);

            exReturn.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
        }


        return exReturn;
    }

    /**
     * 修改密码
     *
     * @param params  通过ajax传递过来的参数
     * @param request 为了获取session
     * @return ExReturn
     */
    @RequestMapping(value = "/api/savePassword", method = RequestMethod.POST)
    @ResponseBody
    public ExReturn savePassword(@RequestBody JSONObject params, HttpServletRequest request) {
        ExReturn exReturn = new ExReturn();
        String password = params.getString("newPassword");
        System.out.println(password);


        User user = (User) request.getSession().getAttribute(Constant.CURRENT_USER);

        try {
            user.setPassword(password);
            userService.update(user);
//            request.getSession().setAttribute(Constant.CURRENT_USER,user);
            request.getSession().setAttribute(Constant.CURRENT_USER, null);

            exReturn.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return exReturn;
    }

    @RequestMapping(value = "/api/uploadImg")
    @ResponseBody
    public Map<String, Object> uploadImg(HttpServletRequest request, HttpServletResponse response, @RequestParam("file") MultipartFile[] file) throws Exception {
        Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
        if (file != null && file.length > 0) {
            //可以支持上传多张图片 在一张的时候用下表为0的参数
            List<String> fileName = new ArrayList<>();
            PrintWriter out = null;
            User user = (User) request.getSession().getAttribute(Constant.CURRENT_USER);
            try {
                for (int i = 0; i < file.length; i++) {
                    fileName.add(FileUtil.uploadImage(request, user.getUsername(), file[i], false));
                }
                //上传成功
                if (fileName != null && fileName.size() > 0) {
                    System.out.println("上传成功！");
                    resultMap.put("status", 200);
                    resultMap.put("message", "上传成功！");
                } else {
                    resultMap.put("status", 500);
                    resultMap.put("message", "上传失败！文件格式错误！");
                }
            } catch (Exception e) {
                e.printStackTrace();
                resultMap.put("status", 500);
                resultMap.put("message", "上传异常！");
            }
        } else {
            resultMap.put("status", 500);
            resultMap.put("message", "没有检测到有效文件！");
        }
        return resultMap;
    }

    /**
     * 用户退出
     */
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(HttpServletRequest request) {
        request.getSession().setAttribute(Constant.CURRENT_USER, null);
        //request.getHeader("Referer")用于获取来源页地址
        String url = request.getHeader("Referer");
        return "redirect:" + url;
    }

    /**
     * 测试
     */
    @RequestMapping(value = "test")
    public String Test() {
        return "cover.html";
    }


}
