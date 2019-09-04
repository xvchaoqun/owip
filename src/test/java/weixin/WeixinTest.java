package weixin;

import org.junit.Test;
import sys.weixin.WeixinClient;

import java.io.IOException;

public class WeixinTest extends WeixinClient {

    @Test
    public void getUserId(){

        String code = "YCjVu5_t5vqzcNBJKq91wCLBBfiRDvAwF7x7DyrwWZI";
        String userId = getUserId(code);

        System.out.println("userId = " + userId);
    }

    @Test
    public void testSendTextMessage() throws IOException {

        String redirectUrl = "http://192.168.1.2:8080/cas?url=/m/pmd/pmdMember";
        String content = "组工系统消息测试。<a href='{wxRedirectUrl}'>点击查看</a>";

        sendTextMessage(content, "jxliaom", redirectUrl);
    }
    @Test
    public void testSendNewsMessage() throws IOException {

        String title = "组工消息提醒";
        String content = "组工系统消息测试。";
        String redirectUrl = "http://192.168.1.2:8080/cas?url=/m/pmd/pmdMember";

        sendNewsMessage(title, content, "jxliaom", null, redirectUrl);
    }

    public final static String corpID = "wx1528d303f9296427";
    public final static String corpSecret = "cmffbV-CKzm791JoMZexv8u4dqBD80kgskha8svIj1qL0e7QZR--_hVwGyzLKy0d";
    public int agentId = 100;

    @Override
    public String getAccessToken() throws IOException {
        return getAccessToken(getCorpID(), getCorpSecret()).getToken();
    }

    @Override
    public String getCorpID() {
        return corpID;
    }

    @Override
    public String getCorpSecret() {
        return corpSecret;
    }

    @Override
    public int getAgentId() {
        return agentId;
    }
}
