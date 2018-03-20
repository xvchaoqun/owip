package sys;

import domain.cet.CetTrainInspector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by lm on 2017/2/9.
 */
public class SessionUtils {

    public final static String TRAIN_INSPECTOR_LOGIN_SESSION_NAME = "_trainInspector";

    public static void setTrainInspector(HttpServletRequest request, CetTrainInspector trainInspector){

        HttpSession session = request.getSession(true);
        session.setAttribute(TRAIN_INSPECTOR_LOGIN_SESSION_NAME, trainInspector);
    }

    public static CetTrainInspector getTrainInspector(HttpServletRequest request){

        HttpSession session = request.getSession(true);
        return (CetTrainInspector) session.getAttribute(TRAIN_INSPECTOR_LOGIN_SESSION_NAME);
    }

    public static CetTrainInspector trainInspector_logout(HttpServletRequest request){

        CetTrainInspector trainInspector = getTrainInspector(request);
        HttpSession session = request.getSession(true);
        session.removeAttribute(TRAIN_INSPECTOR_LOGIN_SESSION_NAME);
        return trainInspector;
    }
}
