package sys.helper;

import domain.dr.DrOnlineInspector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class DrHelper {

    //线上民主推荐
    public final static String DRONLINE_INSPECTOR_LOGIN_SESSION_NAME = "_drInspector";

    public static void setDrInspector(HttpServletRequest request, DrOnlineInspector inspector){

        HttpSession session = request.getSession(true);
        session.setAttribute(DRONLINE_INSPECTOR_LOGIN_SESSION_NAME, inspector);
    }

    public static DrOnlineInspector getDrInspector(HttpServletRequest request){

        HttpSession session = request.getSession(true);
        return (DrOnlineInspector) session.getAttribute(DRONLINE_INSPECTOR_LOGIN_SESSION_NAME);
    }

    public static DrOnlineInspector drInspector_logout(HttpServletRequest request){

        DrOnlineInspector inspector = getDrInspector(request);
        HttpSession session = request.getSession(true);
        session.removeAttribute(DRONLINE_INSPECTOR_LOGIN_SESSION_NAME);
        return inspector;
    }


}
