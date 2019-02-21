package domain.sys;

import persistence.sys.SchedulerJobMapper;
import sys.tags.CmTag;

import java.io.Serializable;
import java.util.Date;

public class SchedulerLog implements Serializable {

    public SchedulerJob getJob(){
        if(jobId!=null){

            SchedulerJobMapper schedulerJobMapper = CmTag.getBean(SchedulerJobMapper.class);
            return schedulerJobMapper.selectByPrimaryKey(jobId);
        }

        return null;
    }

    private Integer id;

    private Integer jobId;

    private String jobName;

    private String jobGroup;

    private Boolean isManualTrigger;

    private Byte status;

    private Date triggerTime;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getJobId() {
        return jobId;
    }

    public void setJobId(Integer jobId) {
        this.jobId = jobId;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName == null ? null : jobName.trim();
    }

    public String getJobGroup() {
        return jobGroup;
    }

    public void setJobGroup(String jobGroup) {
        this.jobGroup = jobGroup == null ? null : jobGroup.trim();
    }

    public Boolean getIsManualTrigger() {
        return isManualTrigger;
    }

    public void setIsManualTrigger(Boolean isManualTrigger) {
        this.isManualTrigger = isManualTrigger;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Date getTriggerTime() {
        return triggerTime;
    }

    public void setTriggerTime(Date triggerTime) {
        this.triggerTime = triggerTime;
    }
}