package sys.helper;

import domain.dr.DrOnlineInspector;
import persistence.dr.DrOnlineInspectorMapper;
import sys.tags.CmTag;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class DrHelper {

    //线上民主推荐
    public final static String DRONLINE_INSPECTOR_LOGIN_SESSION_NAME = "_drInspector";

    public static void setSession(HttpServletRequest request, int inspectorId){

        HttpSession session = request.getSession(true);
        session.setAttribute(DRONLINE_INSPECTOR_LOGIN_SESSION_NAME, inspectorId);

        // 8小时超时
        session.setMaxInactiveInterval(8 * 60 * 60);
    }

    public static Integer getSession(HttpServletRequest request){

        HttpSession session = request.getSession(true);
        return (Integer) session.getAttribute(DRONLINE_INSPECTOR_LOGIN_SESSION_NAME);
    }

    public static DrOnlineInspector getSessionInspector(HttpServletRequest request){

        Integer inspectorId = getSession(request);

        if(inspectorId!=null){
            DrOnlineInspectorMapper drOnlineInspectorMapper = CmTag.getBean(DrOnlineInspectorMapper.class);
            return drOnlineInspectorMapper.selectByPrimaryKey(inspectorId);
        }

        return null;
    }

    public static DrOnlineInspector doLogout(HttpServletRequest request){

        DrOnlineInspector inspector = getSessionInspector(request);

        HttpSession session = request.getSession(true);
        session.removeAttribute(DRONLINE_INSPECTOR_LOGIN_SESSION_NAME);

        return inspector;
    }


}
