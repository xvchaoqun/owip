package job.unit;

import domain.unit.UnitPostView;
import domain.unit.UnitPostViewExample;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import persistence.unit.UnitPostViewMapper;
import service.unit.UnitPostService;
import sys.constants.SystemConstants;
import java.util.List;

public class UpdateUnitAdmin implements Job {

    @Autowired
    private UnitPostViewMapper unitPostViewMapper;
    @Autowired
    private UnitPostService unitPostService;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        UnitPostViewExample example = new UnitPostViewExample();
        UnitPostViewExample.Criteria criteria = example.createCriteria();
        criteria.andStatusEqualTo(SystemConstants.UNIT_POST_STATUS_NORMAL);
        criteria.andLeaderTypeGreaterThan(SystemConstants.UNIT_POST_LEADER_TYPE_NOT);
        List<UnitPostView> records = unitPostViewMapper.selectByExample(example);
        unitPostService.updateUnitPostRole(records);
    }
}
