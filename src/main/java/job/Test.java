package job;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Test implements Job{

	@Override
	public void execute(JobExecutionContext context)
			throws JobExecutionException {

		JobDataMap dataMap = context.getJobDetail().getJobDataMap();
		if(dataMap.containsKey("userId")) {
			int userId = dataMap.getInt("userId");
			System.out.println("userId=" + userId);
		}

		System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())  + "Hello World....");
	}

}