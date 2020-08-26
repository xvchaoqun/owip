package controller.sys;

import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import shiro.ShiroHelper;

import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;
import javax.websocket.server.ServerEndpointConfig.Configurator;

public class HttpSessionConfigurator extends Configurator {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void modifyHandshake(ServerEndpointConfig sec, HandshakeRequest request, HandshakeResponse response) {

        //logger.info("openSession..." + ShiroHelper.getCurrentUsername());
        sec.getUserProperties().put(Subject.class.getName(), ShiroHelper.getSubject());
    }
}