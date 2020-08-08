package domain.sc.scMatter;

import sys.jackson.SignRes;

import java.io.Serializable;
import java.util.Date;

public class ScMatterCheckView implements Serializable {
    private Integer id;

    private Integer year;

    private Date checkDate;

    @SignRes
    private String checkFile;

    private String checkFileName;

    private String files;

    private Integer num;

    private Boolean isRandom;

    private String remark;

    private Boolean isDeleted;

    private Integer itemCount;

    private Integer itemCheckCount;

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

    public Date getCheckDate() {
        return checkDate;
    }

    public void setCheckDate(Date checkDate) {
        this.checkDate = checkDate;
    }

    public String getCheckFile() {
        return checkFile;
    }

    public void setCheckFile(String checkFile) {
        this.checkFile = checkFile == null ? null : checkFile.trim();
    }

    public String getCheckFileName() {
        return checkFileName;
    }

    public void setCheckFileName(String checkFileName) {
        this.checkFileName = checkFileName == null ? null : checkFileName.trim();
    }

    public String getFiles() {
        return files;
    }

    public void setFiles(String files) {
        this.files = files == null ? null : files.trim();
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Boolean getIsRandom() {
        return isRandom;
    }

    public void setIsRandom(Boolean isRandom) {
        this.isRandom = isRandom;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Integer getItemCount() {
        return itemCount;
    }

    public void setItemCount(Integer itemCount) {
        this.itemCount = itemCount;
    }

    public Integer getItemCheckCount() {
        return itemCheckCount;
    }

    public void setItemCheckCount(Integer itemCheckCount) {
        this.itemCheckCount = itemCheckCount;
    }
}