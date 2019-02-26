package domain.sc.scBorder;

import sys.utils.DateUtils;

import java.io.Serializable;
import java.util.Date;

public class ScBorderView implements Serializable {

    public String getCode(){
        return String.format("出入境报备〔%s〕号", DateUtils.formatDate(recordDate, "yyyyMMdd"));
    }

    private Integer id;

    private Integer year;

    private Date recordDate;

    private String addFile;

    private String changeFile;

    private String deleteFile;

    private String recordFile;

    private String remark;

    private Integer itemCount;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Date getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(Date recordDate) {
        this.recordDate = recordDate;
    }

    public String getAddFile() {
        return addFile;
    }

    public void setAddFile(String addFile) {
        this.addFile = addFile == null ? null : addFile.trim();
    }

    public String getChangeFile() {
        return changeFile;
    }

    public void setChangeFile(String changeFile) {
        this.changeFile = changeFile == null ? null : changeFile.trim();
    }

    public String getDeleteFile() {
        return deleteFile;
    }

    public void setDeleteFile(String deleteFile) {
        this.deleteFile = deleteFile == null ? null : deleteFile.trim();
    }

    public String getRecordFile() {
        return recordFile;
    }

    public void setRecordFile(String recordFile) {
        this.recordFile = recordFile == null ? null : recordFile.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Integer getItemCount() {
        return itemCount;
    }

    public void setItemCount(Integer itemCount) {
        this.itemCount = itemCount;
    }
}