package service.sys;

import controller.global.OpException;
import domain.sys.SchedulerJob;
import domain.sys.SchedulerJobExample;
import org.apache.ibatis.session.RowBounds;
import org.quartz.Scheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import persistence.sys.SchedulerJobMapper;
import service.BaseMapper;
import sys.quartz.QuartzManager;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by lm on 2017/9/17.
 */
@Service
public class SchedulerJobService  extends BaseMapper {

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
    public int insert(SchedulerJob record) {

        Assert.isTrue(!idDuplicate(record.getId(), record.getName()), "定时任务名称重复");

        record.setCreateTime(new Date());
        record.setIsStarted(false);
        record.setSortOrder(getNextSortOrder("sys_scheduler_job", null));
        return schedulerJobMapper.insert(record);
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

        String clazz = schedulerJob.getClazz();

        Class cls = null;
        try {
            cls = Class.forName(clazz);
        } catch (ClassNotFoundException e) {
            logger.error("类不存在", e);
            throw new OpException("类{0}不存在", schedulerJob.getClazz());
        }

        QuartzManager.addJob(scheduler, clazz.replaceAll("\\.", "_"), cls, schedulerJob.getCron());

        SchedulerJob record = new SchedulerJob();
        record.setId(id);
        record.setIsStarted(true);
        schedulerJobMapper.updateByPrimaryKeySelective(record);

        logger.info("启动定时任务[{}]成功", schedulerJob.getName());
    }

    // 启动所有已开启的任务（系统启动时执行）
    public void runAllJobs() {

        Scheduler scheduler = schedulerFactoryBean.getScheduler();

        SchedulerJobExample example = new SchedulerJobExample();
        example.createCriteria().andIsStartedEqualTo(true);
        List<SchedulerJob> schedulerJobs = schedulerJobMapper.selectByExample(example);

        int total = schedulerJobs.size();
        int success = 0;
        logger.info("启动所有已开启的定时任务...");
        for (SchedulerJob schedulerJob : schedulerJobs) {

            try {
                String clazz =  schedulerJob.getClazz();
                QuartzManager.addJob(scheduler, clazz.replaceAll("\\.", "_"),
                        Class.forName(clazz), schedulerJob.getCron());
                success++;
                logger.info("启动定时任务[{}]", schedulerJob.getName());
            } catch (ClassNotFoundException e) {

                logger.error("类不存在", e);
            }
        }

        logger.info("启动所有已开启的定时任务(总共{}个，成功{}个)...完成", total, success);
    }

    @Transactional
    public void stopJob(Integer id) {

        SchedulerJob schedulerJob = schedulerJobMapper.selectByPrimaryKey(id);
        Scheduler scheduler = schedulerFactoryBean.getScheduler();

        String clazz =  schedulerJob.getClazz();
        QuartzManager.removeJob(scheduler, clazz.replaceAll("\\.", "_"));

        SchedulerJob record = new SchedulerJob();
        record.setId(id);
        record.setIsStarted(false);
        schedulerJobMapper.updateByPrimaryKeySelective(record);

        logger.info("关闭定时任务[{}]成功", schedulerJob.getName());
    }

    @Transactional
    public void updateByPrimaryKeySelective(SchedulerJob record) {

        int id = record.getId();
        Assert.isTrue(!idDuplicate(id, record.getName()), "定时任务名称重复");

        SchedulerJob schedulerJob = schedulerJobMapper.selectByPrimaryKey(id);

        stopJob(id);

        schedulerJobMapper.updateByPrimaryKeySelective(record);

        if (schedulerJob.getIsStarted())
            startJob(id);
    }

    @Transactional
    public void batchDel(Integer[] ids) {

        if(ids==null || ids.length==0) return ;

        for (Integer id : ids) {
            stopJob(id);
        }

        SchedulerJobExample example = new SchedulerJobExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        schedulerJobMapper.deleteByExample(example);
    }

    public void changeOrder(int id, int addNum) {

        if(addNum == 0) return ;

        SchedulerJob entity = schedulerJobMapper.selectByPrimaryKey(id);
        Integer baseSortOrder = entity.getSortOrder();

        SchedulerJobExample example = new SchedulerJobExample();
        if (addNum > 0) {

            example.createCriteria().andSortOrderGreaterThan(baseSortOrder);
            example.setOrderByClause("sort_order asc");
        }else {

            example.createCriteria().andSortOrderLessThan(baseSortOrder);
            example.setOrderByClause("sort_order desc");
        }

        List<SchedulerJob> overEntities = schedulerJobMapper.selectByExampleWithRowbounds(example, new RowBounds(0, Math.abs(addNum)));
        if(overEntities.size()>0) {

            SchedulerJob targetEntity = overEntities.get(overEntities.size()-1);

            if (addNum > 0)
                commonMapper.downOrder("sys_scheduler_job", null, baseSortOrder, targetEntity.getSortOrder());
            else
                commonMapper.upOrder("sys_scheduler_job", null, baseSortOrder, targetEntity.getSortOrder());

            SchedulerJob record = new SchedulerJob();
            record.setId(id);
            record.setSortOrder(targetEntity.getSortOrder());
            schedulerJobMapper.updateByPrimaryKeySelective(record);
        }
    }
}
