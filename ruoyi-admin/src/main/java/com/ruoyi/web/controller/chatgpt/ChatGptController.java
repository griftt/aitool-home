package com.ruoyi.web.controller.chatgpt;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONUtil;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.gptUtil.ChatGptConfigConstant;
import com.ruoyi.common.param.TalkParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/chatgpt")
@RestController
public class ChatGptController {
    /**
     *
     *
     * @param
     * @return 结果
     */
    @PostMapping("/talk")
    public AjaxResult talk(@RequestBody TalkParam talkParam)
    {

        AjaxResult ajax = AjaxResult.success();
        ajax.put("aaaa",123);

        HttpRequest post = HttpRequest.post("https://api.openai.com/v1/chat/completions");
        post.header("Authorization","Bearer "+ ChatGptConfigConstant.API_KEY);
        post.header("Content-Type","application/json");

        Map<String, Object> data = new HashMap<>();
        data.put("model", "gpt-3.5-turbo");
        data.put("temperature", 0.7);

        List<Map<String, String>> messages = new ArrayList<>();
        Map<String, String> message = new HashMap<>();
        message.put("role", "user");
        message.put("content", talkParam.question);
        messages.add(message);

        data.put("messages", messages);
        String s = JSONUtil.toJsonStr(data);
        System.out.println(s);

        post.body(s);
        HttpResponse execute = post.execute();
        String body = execute.body();
        System.out.println(body);
        ajax.put("result",body);
        return ajax;
    }


    public static void main(String[] args) {
        HttpRequest post = HttpRequest.post("https://api.openai.com/v1/chat/completions");
        post.header("Authorization","Bearer "+ ChatGptConfigConstant.API_KEY);
        post.header("Content-Type","application/json");

        Map<String, Object> data = new HashMap<>();
        data.put("model", "gpt-3.5-turbo");
        data.put("temperature", 0.7);

        List<Map<String, String>> messages = new ArrayList<>();
        Map<String, String> message = new HashMap<>();
        message.put("role", "user");
        message.put("content", "你知道中国的4大名著吗");
        messages.add(message);

        data.put("messages", messages);
        String s = JSONUtil.toJsonStr(data);

        System.out.println(s);
        post.body(s);
        HttpResponse execute = post.execute();
        String body = execute.body();
        System.out.println(body);
    }
}
