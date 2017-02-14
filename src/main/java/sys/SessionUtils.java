package sys;

import domain.train.TrainInspector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by lm on 2017/2/9.
 */
public class SessionUtils {

    public final static String TRAIN_INSPECTOR_LOGIN_SESSION_NAME = "_trainInspector";

    public static void setTrainInspector(HttpServletRequest request, TrainInspector trainInspector){

        HttpSession session = request.getSession(true);
        session.setAttribute(TRAIN_INSPECTOR_LOGIN_SESSION_NAME, trainInspector);
    }

    public static TrainInspector getTrainInspector(HttpServletRequest request){

        HttpSession session = request.getSession(true);
        return (TrainInspector) session.getAttribute(TRAIN_INSPECTOR_LOGIN_SESSION_NAME);
    }

    public static TrainInspector trainInspector_logout(HttpServletRequest request){

        TrainInspector trainInspector = getTrainInspector(request);
        HttpSession session = request.getSession(true);
        session.removeAttribute(TRAIN_INSPECTOR_LOGIN_SESSION_NAME);
        return trainInspector;
    }
}
