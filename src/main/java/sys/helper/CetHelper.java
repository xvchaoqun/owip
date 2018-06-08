package sys.helper;

import domain.cet.CetTrainEvaNorm;
import domain.cet.CetTrainEvaRank;
import domain.cet.CetTrainEvaTable;
import service.cet.CetTrainCourseService;
import service.cet.CetTrainEvaNormService;
import service.cet.CetTrainEvaRankService;
import service.cet.CetTrainEvaTableService;
import sys.tags.CmTag;

import java.util.Map;

/**
 * Created by lm on 2018/6/8.
 */
public class CetHelper {

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
