package sys.helper;

import domain.pcs.PcsPollInspector;
import persistence.pcs.PcsPollInspectorMapper;
import sys.tags.CmTag;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class PcsHelper {

    //党代会投票
    public final static String PCSPOLL_INSPECTOR_LOGIN_SESSION_NAME = "_pcsInspector";

    public static void setSession(HttpServletRequest request, int inspectorId){

        HttpSession session = request.getSession(true);
        session.setAttribute(PCSPOLL_INSPECTOR_LOGIN_SESSION_NAME, inspectorId);

        // 8小时超时
        session.setMaxInactiveInterval(8 * 60 * 60);
    }

    public static Integer getSession(HttpServletRequest request){

        HttpSession session = request.getSession(true);
        return (Integer) session.getAttribute(PCSPOLL_INSPECTOR_LOGIN_SESSION_NAME);
    }

    public static PcsPollInspector getSessionInspector(HttpServletRequest request){

        Integer inspectorId = getSession(request);

        if(inspectorId!=null){
            PcsPollInspectorMapper pcsPollInspectorMapper = CmTag.getBean(PcsPollInspectorMapper.class);
            return pcsPollInspectorMapper.selectByPrimaryKey(inspectorId);
        }

        return null;
    }

    public static PcsPollInspector doLogout(HttpServletRequest request){

        PcsPollInspector inspector = getSessionInspector(request);

        HttpSession session = request.getSession(true);
        session.removeAttribute(PCSPOLL_INSPECTOR_LOGIN_SESSION_NAME);

        return inspector;
    }


}
