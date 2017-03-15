package com.example.controller;

import com.example.domain.ClientMessage;
import com.example.domain.ServerMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by xuyufengyy on 2017/3/13.
 */
@Controller
public class WebSocketController {

    private static Logger logger = LoggerFactory.getLogger(WebSocketController.class);

    @RequestMapping("/")
    public String index(){
        return "index";
    }

    @MessageMapping("/socket")
    //SendTo 发送至 Broker 下的指定订阅路径
    @SendTo("/topic/greetings")
    public ServerMessage say(ClientMessage clientMessage){
        //方法用于广播测试
        logger.info("clientMessage.getName() = " + clientMessage.getName());
        return new ServerMessage("Welcome , "+clientMessage.getName()+" !");
    }

    @SubscribeMapping("/macro")
    public ServerMessage handleSubscription() {
        logger.info("this is the @SubscribeMapping('/marco')");
        return new ServerMessage("i am a msg from SubscribeMapping('/macro').");
    }

}
