package sys.helper;

import domain.cet.CetTrainEvaNorm;
import domain.cet.CetTrainEvaRank;
import domain.cet.CetTrainEvaTable;
import domain.cet.CetTrainInspector;
import service.cet.CetTrainCourseService;
import service.cet.CetTrainEvaNormService;
import service.cet.CetTrainEvaRankService;
import service.cet.CetTrainEvaTableService;
import sys.tags.CmTag;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * Created by lm on 2018/6/8.
 */
public class CetHelper {

    // 匿名评课登录
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

    public static CetTrainEvaTable getCetTrainEvaTable(Integer evaTableId) {

        CetTrainEvaTableService CetTrainEvaTableService = CmTag.getBean(CetTrainEvaTableService.class);

        return CetTrainEvaTableService.findAll().get(evaTableId);
    }

    public static Map<Integer, CetTrainEvaNorm> getCetTrainEvaNorms(Integer evaTableId) {

        CetTrainEvaNormService cetTrainEvaNormService = CmTag.getBean(CetTrainEvaNormService.class);

        return cetTrainEvaNormService.findAll(evaTableId);
    }

    public static Map<Integer, CetTrainEvaRank> getCetTrainEvaRanks(Integer evaTableId) {

        CetTrainEvaRankService CetTrainEvaRankService = CmTag.getBean(CetTrainEvaRankService.class);

        return CetTrainEvaRankService.findAll(evaTableId);
    }

    public static Integer evaIsClosed(Integer courseId) {

        CetTrainCourseService cetTrainCourseService = CmTag.getBean(CetTrainCourseService.class);

        return cetTrainCourseService.evaIsClosed(courseId);
    }
}
