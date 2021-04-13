package job.unit;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import service.unit.UnitPostService;

public class UpdateUnitAdmin implements Job {

    @Autowired
    private UnitPostService unitPostService;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

        unitPostService.updateUnitAdminRoles();
    }
}
