package service.sys;

import controller.global.OpException;
import domain.sys.SchedulerJob;
import domain.sys.SchedulerJobExample;
import org.quartz.Scheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import persistence.SchedulerJobMapper;
import sys.quartz.QuartzManager;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by lm on 2017/9/17.
 */
@Service
public class SchedulerJobService {

    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private SchedulerJobMapper schedulerJobMapper;
    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;

    public boolean idDuplicate(Integer id, String name) {

        SchedulerJobExample example = new SchedulerJobExample();
        SchedulerJobExample.Criteria criteria = example.createCriteria().andNameEqualTo(name.toLowerCase().trim());
        if (id != null) criteria.andIdNotEqualTo(id);

        return schedulerJobMapper.countByExample(example) > 0;
    }

    @Transactional
    public int insert(SchedulerJob schedulerJob) {

        Assert.isTrue(!idDuplicate(schedulerJob.getId(), schedulerJob.getName()), "定时任务名称重复");

        schedulerJob.setCreateTime(new Date());
        schedulerJob.setIsStarted(false);
        return schedulerJobMapper.insert(schedulerJob);
    }

    public Map<String, Map<String, Object>> allJobsMap() {

        Scheduler scheduler = schedulerFactoryBean.getScheduler();

        return QuartzManager.queryAllJobs(scheduler);
    }

    public Map<String, Map<String, Object>> runJobsMap() {

        Scheduler scheduler = schedulerFactoryBean.getScheduler();

        return QuartzManager.queryRunJobs(scheduler);
    }

    @Transactional
    public void startJob(Integer id) {

        SchedulerJob schedulerJob = schedulerJobMapper.selectByPrimaryKey(id);
        Scheduler scheduler = schedulerFactoryBean.getScheduler();

        Class cls = null;
        try {
            cls = Class.forName(schedulerJob.getClazz());
        } catch (ClassNotFoundException e) {
            logger.error("类不存在", e);
            throw new OpException("类{0}不存在", schedulerJob.getClazz());
        }

        String jobName = schedulerJob.getName();
        QuartzManager.addJob(scheduler, jobName, cls, schedulerJob.getCron());

        SchedulerJob record = new SchedulerJob();
        record.setId(id);
        record.setIsStarted(true);
        schedulerJobMapper.updateByPrimaryKeySelective(record);

        logger.info("启动定时任务[{}]成功", jobName);
    }

    // 启动所有已开启的任务（系统启动时执行）
    public void runAllJobs() {

        Scheduler scheduler = schedulerFactoryBean.getScheduler();

        SchedulerJobExample example = new SchedulerJobExample();
        example.createCriteria().andIsStartedEqualTo(true);
        List<SchedulerJob> schedulerJobs = schedulerJobMapper.selectByExample(example);


        logger.info("启动所有已开启的定时任务...");
        for (SchedulerJob schedulerJob : schedulerJobs) {

            try {

                String jobName =  schedulerJob.getName();
                QuartzManager.addJob(scheduler, jobName,
                        Class.forName(schedulerJob.getClazz()), schedulerJob.getCron());

                logger.info("启动定时任务[{}]", jobName);
            } catch (ClassNotFoundException e) {

                logger.error("类不存在", e);
            }
        }

        logger.info("启动所有已开启的定时任务...完成");
    }

    @Transactional
    public void stopJob(Integer id) {

        SchedulerJob schedulerJob = schedulerJobMapper.selectByPrimaryKey(id);
        Scheduler scheduler = schedulerFactoryBean.getScheduler();

        String jobName =  schedulerJob.getName();
        QuartzManager.removeJob(scheduler, jobName);

        SchedulerJob record = new SchedulerJob();
        record.setId(id);
        record.setIsStarted(false);
        schedulerJobMapper.updateByPrimaryKeySelective(record);

        logger.info("关闭定时任务[{}]成功", jobName);
    }

    @Transactional
    public void updateByPrimaryKeySelective(SchedulerJob record) {

        int id = record.getId();
        Assert.isTrue(!idDuplicate(id, record.getName()), "定时任务名称重复");

        stopJob(id);

        schedulerJobMapper.updateByPrimaryKeySelective(record);

        SchedulerJob schedulerJob = schedulerJobMapper.selectByPrimaryKey(id);
        if (schedulerJob.getIsStarted())
            startJob(id);
    }

    @Transactional
    public void del(Integer id) {

        stopJob(id);
        schedulerJobMapper.deleteByPrimaryKey(id);
    }
}
