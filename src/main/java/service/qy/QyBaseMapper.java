package service.qy;

import org.springframework.beans.factory.annotation.Autowired;
import persistence.qy.*;
import service.CoreBaseMapper;

public class QyBaseMapper extends CoreBaseMapper {

    @Autowired
    protected QyRewardObjMapper qyRewardObjMapper;
    @Autowired
    protected QyRewardMapper qyRewardMapper;
    @Autowired
    protected QyYearRewardMapper qyYearRewardMapper;
    @Autowired
    protected persistence.qy.QyYearMapper qyYearMapper;
    @Autowired
    protected QyRewardRecordMapper qyRewardRecordMapper;
    @Autowired
    protected QyRewardObjViewMapper qyRewardObjViewMapper;
    @Autowired
    protected QyRewardRecordViewMapper qyRewardRecordViewMapper;
}
