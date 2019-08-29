package com.socket.demo.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/server")
public class ServerController {
    @RequestMapping("/start")
    public String start() {

        return "server";
    }

    @ResponseBody
    @RequestMapping("/serverrec")
    public String rec(String hua, ModelMap modelMap) {
        System.out.println(hua);
        return hua;
    }


}
