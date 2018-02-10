package domain.sc.scPublic;

import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

public class ScPublic implements Serializable {
    private Integer id;

    private Integer committeeId;

    private Integer year;

    private Integer num;

    private String wordFilePath;

    private String pdfFilePath;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date publicStartDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date publicEndDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date publishDate;

    private Boolean isFinished;

    private Boolean isConfirmed;

    private String remark;

    private Boolean isDeleted;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCommitteeId() {
        return committeeId;
    }

    public void setCommitteeId(Integer committeeId) {
        this.committeeId = committeeId;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getWordFilePath() {
        return wordFilePath;
    }

    public void setWordFilePath(String wordFilePath) {
        this.wordFilePath = wordFilePath == null ? null : wordFilePath.trim();
    }

    public String getPdfFilePath() {
        return pdfFilePath;
    }

    public void setPdfFilePath(String pdfFilePath) {
        this.pdfFilePath = pdfFilePath == null ? null : pdfFilePath.trim();
    }

    public Date getPublicStartDate() {
        return publicStartDate;
    }

    public void setPublicStartDate(Date publicStartDate) {
        this.publicStartDate = publicStartDate;
    }

    public Date getPublicEndDate() {
        return publicEndDate;
    }

    public void setPublicEndDate(Date publicEndDate) {
        this.publicEndDate = publicEndDate;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public Boolean getIsFinished() {
        return isFinished;
    }

    public void setIsFinished(Boolean isFinished) {
        this.isFinished = isFinished;
    }

    public Boolean getIsConfirmed() {
        return isConfirmed;
    }

    public void setIsConfirmed(Boolean isConfirmed) {
        this.isConfirmed = isConfirmed;
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
}