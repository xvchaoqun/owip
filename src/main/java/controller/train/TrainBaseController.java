package controller.train;

import controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import service.train.StatTrainService;
import service.train.TrainCourseService;
import service.train.TrainEvaNormService;
import service.train.TrainEvaRankService;
import service.train.TrainEvaTableService;
import service.train.TrainInspectorCourseService;
import service.train.TrainInspectorService;
import service.train.TrainService;

/**
 * Created by lm on 2017/9/20.
 */
public class TrainBaseController extends BaseController {

    @Autowired
    protected StatTrainService statTrainService;

    @Autowired
    protected TrainService trainService;
    @Autowired
    protected TrainCourseService trainCourseService;
    @Autowired
    protected TrainEvaNormService trainEvaNormService;
    @Autowired
    protected TrainEvaRankService trainEvaRankService;
    @Autowired
    protected TrainEvaTableService trainEvaTableService;
    @Autowired
    protected TrainInspectorService trainInspectorService;
    @Autowired
    protected TrainInspectorCourseService trainInspectorCourseService;
}
