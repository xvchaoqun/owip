package controller.sys;

import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import shiro.ShiroUser;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.io.InputStream;

@ServerEndpoint(value = "/log", configurator = HttpSessionConfigurator.class)
public class LogWebSocketHandle {

    private Logger logger = LoggerFactory.getLogger(getClass());
    private Process process;
    private InputStream inputStream;

    @OnOpen
    public void onOpen(Session session) {
        try {

            Subject subject = (Subject) session.getUserProperties().get(Subject.class.getName());
            ShiroUser shiroUser = (ShiroUser) subject.getPrincipal();
            if(!shiroUser.getPermissions().contains("system:cmd")){
                session.close();
                return;
            }

            // 执行tail -f命令
            String cmd = "tail -f ~/tomcat_logs/info.$(date \\+%Y-%m-%d).log";
            process = Runtime.getRuntime().exec(
                    new String[]{"/bin/sh", "-c", cmd.trim()});
            //String cmd = "ping -t localhost";
            //process = Runtime.getRuntime().exec(cmd);

            inputStream = process.getInputStream();

            String username = shiroUser.getUsername();
            logger.info("Open log WebSocket session:" + session.getId() + " " + username);

            // 一定要启动新的线程，防止InputStream阻塞处理WebSocket的线程
            TailLogThread thread = new TailLogThread(inputStream, session);
            thread.start();

            InputStream errorStream = process.getErrorStream();
            TailLogThread thread2 = new TailLogThread(errorStream, session);
            thread2.start();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    @OnClose
    public void onClose(Session session, CloseReason closeReason) {
        try {
            if (inputStream != null)
                inputStream.close();
            if (process != null)
                process.destroy();
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        logger.info("Close log WebSocket session:" + session.getId() + " " + closeReason.toString());
    }

    @OnError
    public void onError(Throwable thr) {

        logger.error(thr.getMessage());
    }
}