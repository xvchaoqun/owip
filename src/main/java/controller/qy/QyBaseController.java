package controller.qy;

import org.springframework.beans.factory.annotation.Autowired;
import service.qy.*;
import sys.HttpResponseMethod;

public class QyBaseController extends QyBaseMapper implements HttpResponseMethod {

    @Autowired
    protected QyRewardObjService qyRewardObjService;
    @Autowired
    protected QyRewardService qyRewardService;
    @Autowired
    protected QyYearRewardService qyYearRewardService;
    @Autowired
    protected QyYearService qyYearService;
    @Autowired
    protected QyRewardRecordService qyRewardRecordService;
}
