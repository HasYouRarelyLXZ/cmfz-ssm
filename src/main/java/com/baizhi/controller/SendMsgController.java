package com.baizhi.controller;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.baizhi.entity.User;
import com.baizhi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * 借助第三方接口获取发送短信验证的接口
 */
@Controller
@RequestMapping("/identify")
public class SendMsgController {
    @Autowired
    private UserService userService;

    @ResponseBody
    @RequestMapping(value = "/obtain", method = RequestMethod.GET)
    public void obtain(String phone) throws ClientException {
        //设置超时时间-可自行调整
        System.setProperty("sun.net.client.defaultConnectTimeout", "9000");
        System.setProperty("sun.net.client.defaultReadTimeout", "9000");
        //初始化ascClient需要的几个参数
        final String product = "Dysmsapi";//短信API产品名称（短信产品名固定，无需修改）
        final String domain = "dysmsapi.aliyuncs.com";//短信API产品域名（接口地址固定，无需修改）
        //替换成你的AK
        final String accessKeyId = "LTAIWJ7nUhCUwnZl";//你的accessKeyId,参考本文档步骤2
        final String accessKeySecret = "s5OSXqX2AXo4PHPeKWcZdD7T2IGqvP";//你的accessKeySecret，参考本文档步骤2
        //初始化ascClient,暂时不支持多region（请勿修改）
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId,
                accessKeySecret);
        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
        IAcsClient acsClient = new DefaultAcsClient(profile);
        //组装请求对象
        SendSmsRequest request = new SendSmsRequest();
        //使用post提交
        request.setMethod(MethodType.POST);
        //必填:待发送手机号。支持以逗号分隔的形式进行批量调用，批量上限为1000个手机号码,批量调用相对于单条调用及时性稍有延迟,验证码类型的短信推荐使用单条调用的方式；发送国际/港澳台消息时，接收号码格式为00+国际区号+号码，如“0085200000000”
        request.setPhoneNumbers(phone);
        //必填:短信签名-可在短信控制台中找到
        request.setSignName("我是htf");
        //必填:短信模板-可在短信控制台中找到
        request.setTemplateCode("SMS_109355426");
        //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
        //友情提示:如果JSON中需要带换行符,请参照标准的JSON协议对换行符的要求,比如短信内容中包含\r\n的情况在JSON中需要表示成\\r\\n,否则会导致JSON在服务端解析失败
        //手动生成一个短信验证码
        Random random = new Random();
        String codeValue = "";
        for (int i = 0; i < 6; i++) {
            int a = random.nextInt(10);  //随即生成0-9的数
            codeValue += String.valueOf(a);
        }
        request.setTemplateParam("{\"code\":\"" + codeValue + "\"}");   //转义\"为'
        //可选-上行短信扩展码(扩展码字段控制在7位或以下，无特殊需求用户请忽略此字段)
        //request.setSmsUpExtendCode("90997");
        //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
        request.setOutId("yourOutId");
//请求失败这里会抛ClientException异常
        SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
        if (sendSmsResponse.getCode() != null && sendSmsResponse.getCode().equals("OK")) {
            //请求成功
            Jedis jedis = new Jedis("192.168.218.129", 6379);
            jedis.set("code",codeValue);
            jedis.expire("code",60);
        }
    }

    /*短信验证码校验接口*/
    @ResponseBody
    @RequestMapping(value = "/check", method = RequestMethod.GET)
    public Map<String, Object> check(String phone, String code) {

        Map<String, Object> map = new HashMap<>();
        //验证短信验证码
        Jedis jedis = new Jedis("192.168.218.129", 6379);
        Boolean flag = jedis.exists("code");  //判断存储的标志是否过期
        if(flag){ //如果没有过期则比较密码
            String codeValue = jedis.get("code");
            if(code.equalsIgnoreCase(code)){
                //移除验证码
                jedis.del("code");
                //比较手机号
                map = userService.getUserByPhoneUnix(phone);
            }else {
                //验证码不一致
                map.put("errMsg","验证码错误");
            }
        }else {
            map.put("errMsg","验证码已过期,请重新获取验证码");
        }
        return map;
    }
}
