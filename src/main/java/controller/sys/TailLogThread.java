package controller.sys;

import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import shiro.ShiroUser;

import javax.websocket.Session;
import java.io.*;

public class TailLogThread extends Thread {

    private Logger logger = LoggerFactory.getLogger(getClass());
    private BufferedReader reader;
    private Session session;

    public TailLogThread(InputStream in, Session session) throws UnsupportedEncodingException {
        //this.reader = new BufferedReader(new InputStreamReader(in, "gbk"));
        this.reader = new BufferedReader(new InputStreamReader(in));
        this.session = session;

    }

    @Override
    public void run() {

        String line;
        try {
            while ((line = reader.readLine()) != null) {

                Subject subject = (Subject) session.getUserProperties().get(Subject.class.getName());
                try {
                    ShiroUser shiroUser = (ShiroUser) subject.getPrincipal();

                    // 将实时日志通过WebSocket发送给客户端，给每一行添加一个HTML换行
                    session.getBasicRemote().sendText(line + "<br>");
                } catch (UnknownSessionException ex) { // 用户登出
                    session.close();
                    break;
                }
            }
        } catch (IOException e) {
            logger.warn(e.getMessage());
        }
    }
}