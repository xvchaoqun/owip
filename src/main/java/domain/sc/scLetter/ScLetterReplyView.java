package domain.sc.scLetter;

import sys.jackson.SignRes;

import java.io.Serializable;
import java.util.Date;

public class ScLetterReplyView implements Serializable {
    private Integer id;

    private Integer letterId;

    private Integer type;

    private Integer num;

    @SignRes
    private String filePath;

    private String fileName;

    private Date replyDate;

    private String remark;

    private Boolean isDeleted;

    private Integer letterYear;

    private Integer letterNum;

    @SignRes
    private String letterFilePath;

    private String letterFileName;

    private Date letterQueryDate;

    private Integer letterType;

    private Long replyItemCount;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getLetterId() {
        return letterId;
    }

    public void setLetterId(Integer letterId) {
        this.letterId = letterId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath == null ? null : filePath.trim();
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName == null ? null : fileName.trim();
    }

    public Date getReplyDate() {
        return replyDate;
    }

    public void setReplyDate(Date replyDate) {
        this.replyDate = replyDate;
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

    public Integer getLetterYear() {
        return letterYear;
    }

    public void setLetterYear(Integer letterYear) {
        this.letterYear = letterYear;
    }

    public Integer getLetterNum() {
        return letterNum;
    }

    public void setLetterNum(Integer letterNum) {
        this.letterNum = letterNum;
    }

    public String getLetterFilePath() {
        return letterFilePath;
    }

    public void setLetterFilePath(String letterFilePath) {
        this.letterFilePath = letterFilePath == null ? null : letterFilePath.trim();
    }

    public String getLetterFileName() {
        return letterFileName;
    }

    public void setLetterFileName(String letterFileName) {
        this.letterFileName = letterFileName == null ? null : letterFileName.trim();
    }

    public Date getLetterQueryDate() {
        return letterQueryDate;
    }

    public void setLetterQueryDate(Date letterQueryDate) {
        this.letterQueryDate = letterQueryDate;
    }

    public Integer getLetterType() {
        return letterType;
    }

    public void setLetterType(Integer letterType) {
        this.letterType = letterType;
    }

    public Long getReplyItemCount() {
        return replyItemCount;
    }

    public void setReplyItemCount(Long replyItemCount) {
        this.replyItemCount = replyItemCount;
    }
}