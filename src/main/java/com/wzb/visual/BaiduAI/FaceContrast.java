package com.wzb.visual.BaiduAI;

import com.baidu.aip.face.AipFace;
import com.baidu.aip.face.MatchRequest;
import com.wzb.visual.tools.Base64Image;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * @author Satsuki
 * @time 2019/4/3 18:10
 * @description:
 */
public class FaceContrast {
    //设置APPID/AK/SK
    public static final String APP_ID = "15930954";
    public static final String API_KEY = "IHn8o97rThV9pyGEZ8ptHL4i";
    public static final String SECRET_KEY = "7K8B3n6fWQnXg5oZACIMSyMULYsqv6bx";

    public static void main(String[] args) {
        String file11 = "G:\\java_project_idea\\visual\\Img\\test1.png";
        String file22 = "G:\\java_project_idea\\visual\\Img\\test2.png";
        String file1 = Base64Image.imageToBase64(file11);
        String file2 = Base64Image.imageToBase64(file22);
        JSONObject res = contrast(file1, file2);
        System.out.println("res:" + res.toString());
    }

    public static JSONObject contrast(String file1, String file2) {
        JSONObject result = new JSONObject();

        //初始化一个AipFace
        AipFace client = new AipFace(APP_ID, API_KEY, SECRET_KEY);

        // 可选：设置网络连接参数
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);

        // 可选：设置代理服务器地址, http和socket二选一，或者均不设置
//        client.setHttpProxy("proxy_host", proxy_port);  // 设置http代理
//        client.setSocketProxy("proxy_host", proxy_port);  // 设置socket代理


        // 可选：设置log4j日志输出格式，若不设置，则使用默认配置
        // 也可以直接通过jvm启动参数设置此环境变量
        System.setProperty("aip.log4j.conf", "path/to/your/log4j.properties");

        MatchRequest req1 = new MatchRequest(file1, "BASE64");
        MatchRequest req2 = new MatchRequest(file2, "BASE64");

        ArrayList<MatchRequest> requests = new ArrayList<>();
        requests.add(req1);
        requests.add(req2);

        //todo:一定要注意是import org.json.JSONObject;
        result = client.match(requests);

        System.out.println("result:" + result.toString(2));
//        System.out.println("score:" + result.getString("score"));
        JSONObject result1 = result.getJSONObject("result");
        System.out.println("score:" + result1.getDouble("score"));

//        return result;
        //result1是result下面的接下去就可以直接获取score
        return result1;
    }
}
