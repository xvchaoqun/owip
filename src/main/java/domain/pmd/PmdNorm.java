package domain.pmd;

import service.pmd.PmdNormValueService;
import sys.tags.CmTag;

import java.io.Serializable;
import java.util.Date;

public class PmdNorm implements Serializable {

    public PmdNormValue getPmdNormValue(){

        PmdNormValueService pmdNormValueService = CmTag.getBean(PmdNormValueService.class);
        return pmdNormValueService.getCurrentPmdNormValue(id);
    }

    private Integer id;

    private Byte type;

    private String name;

    private Byte setType;

    private Date startTime;

    private Date endTime;

    private Integer startUserId;

    private Integer endUserId;

    private Integer sortOrder;

    private Byte status;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Byte getSetType() {
        return setType;
    }

    public void setSetType(Byte setType) {
        this.setType = setType;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getStartUserId() {
        return startUserId;
    }

    public void setStartUserId(Integer startUserId) {
        this.startUserId = startUserId;
    }

    public Integer getEndUserId() {
        return endUserId;
    }

    public void setEndUserId(Integer endUserId) {
        this.endUserId = endUserId;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }
}