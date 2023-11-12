package com.chapts.wechat.controller;

import org.apache.commons.codec.binary.Hex;
import org.springframework.web.bind.annotation.*;

import java.security.MessageDigest;
import java.util.Arrays;

/**
 * @author Floweryu
 * @date 2023/11/11 22:18
 */
@RestController
@RequestMapping("/wechat")
public class CallBackController {

    private final String token = "zhangjunfeng";

    @GetMapping("/callback")
    public String weChatCallBack(@RequestParam("timestamp") String timestamp,
                                 @RequestParam("nonce") String nonce,
                                 @RequestParam("signature") String signature,
                                 @RequestParam("echostr") String echostr) {
        // 创建string数组接收,排序的方法很多
        String[] strings = {timestamp, nonce, token};
        // java自己提供的排序
        Arrays.sort(strings);
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA");
            String message = strings[0] + strings[1] + strings[2];
            byte[] cipherBytes = messageDigest.digest(message.getBytes());
            String cipherStr = Hex.encodeHexString(cipherBytes);
            return signature.equals(cipherStr) ? echostr : "验证失败";
        } catch (Exception e) {
            return "error";
        }
    }

}
