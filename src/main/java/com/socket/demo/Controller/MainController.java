package com.socket.demo.Controller;

import com.socket.demo.po.Constant;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Map;

@Controller
public class MainController {
    //返回服务器IP地址给前端页面
    @RequestMapping("/chat")
    @ResponseBody
    public String chat() {
        return Constant.getUrl();
    }

    //刷新在线用户
    @RequestMapping("/refonline")
    @ResponseBody
    public String refonline() throws JSONException {
        String ref = new String();
        JSONObject jsonObject = new JSONObject();
        for (Map.Entry<String, ArrayList> entry : MyWebSocket.getMap().entrySet()) {
//            ref += "用户:" + entry.getValue().get(0) + "  ID号:" + entry.getKey() + "<br/>";
            jsonObject.put(entry.getKey(), entry.getValue().get(0));
        }
        return jsonObject.toString();
    }

    //刷新离线用户
    @RequestMapping("/refoffline")
    @ResponseBody
    public String refoffline() throws JSONException {
        String ref = new String();
        JSONObject jsonObject = new JSONObject();
        for (Map.Entry<String, ArrayList> entry : MyWebSocket.getOffmap().entrySet()) {
//            ref += "用户:" + entry.getValue().get(0) + "  ID号:" + entry.getKey() + "<br/>";
            jsonObject.put(entry.getKey(), entry.getValue().get(0));
        }
        return jsonObject.toString();
    }

    //获取用户ID以及用户名并返回test页面
    @RequestMapping(value = "/main", method = RequestMethod.POST)
    public String ma(String id, String name, Model model) {
        model.addAttribute("id", id);
        model.addAttribute("name", name);
        return "test";
    }

}
