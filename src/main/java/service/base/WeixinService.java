package service.base;

import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import sys.entity.SendMsgResult;
import sys.tags.CmTag;
import sys.weixin.WeixinClient;

import java.io.IOException;

@Service
public class WeixinService extends WeixinClient {

    // 发送消息（无跳转地址）
    public SendMsgResult sendText(String content, String toUser) throws IOException {

        return sendText(content, toUser, null, null);
    }

    public SendMsgResult sendText(String content, String toUser, String redirectUrl) throws IOException {
        return sendText(content, toUser, redirectUrl, null);
    }

    /**
     * 发送微信消息
     *
     * @param content     发送内容
     * @param toUser      发送对象
     * @param redirectUrl 跳转地址
     * @param label       跳转地址标签文字
     * @return
     * @throws IOException
     */
    public SendMsgResult sendText(String content, String toUser, String redirectUrl, String label) throws IOException {

        if (StringUtils.isNotBlank(redirectUrl)) {
            content += "<a href='{wxRedirectUrl}'>" + StringUtils.defaultIfBlank(label, "（点此进入）") + "</a>";
        }

        return sendTextMessage(content, toUser, redirectUrl);
    }

    // 发送图文消息（无图片、无地址）
    public SendMsgResult sendNews(String title, String description, String toUser) throws IOException {

        return sendNews(title, description, toUser, null, null);
    }

    // 发送图文消息（无图片）
    public SendMsgResult sendNews(String title, String description, String toUser, String redirectUrl) throws IOException {

        return sendNews(title, description, toUser, null, redirectUrl);
    }

    /**
     * 发送图文消息
     *
     * @param title
     * @param description
     * @param toUser
     * @param picUrl
     * @param redirectUrl
     * @return
     * @throws IOException
     */
    public SendMsgResult sendNews(String title, String description,
                                  String toUser, String picUrl, String redirectUrl) throws IOException {

        return sendNewsMessage(title, description, toUser, picUrl, redirectUrl);
    }

    @Cacheable(value = "wxAccessToken", key = "corp")
    @Override
    public String getCorpAccessToken() throws IOException {
        return getCorpAccessToken(getCorpID(), getCorpSecret()).getToken();
    }

    @CachePut(value = "wxAccessToken", key = "corp")
    public String refreshCorpAccessToken() throws IOException {
        return getCorpAccessToken();
    }

    @Cacheable(value = "wxAccessToken", key = "app")
    @Override
    public String getAppAccessToken() throws IOException {
        return getCorpAccessToken(getCorpID(), getAppSecret()).getToken();
    }

    @CachePut(value = "wxAccessToken", key = "app")
    public String refreshAppAccessToken() throws IOException {
        return getAppAccessToken();
    }

    @Override
    public String getCorpID() {
        return CmTag.getStringProperty("wx.corpID");
    }

    @Override
    public String getCorpSecret() {
        return CmTag.getStringProperty("wx.corpSecret");
    }

    @Override
    public String getAgentId() {
        return CmTag.getStringProperty("wx.agentId");
    }

    @Override
    public String getAppSecret() {
        return CmTag.getStringProperty("wx.appSecret");
    }
}
