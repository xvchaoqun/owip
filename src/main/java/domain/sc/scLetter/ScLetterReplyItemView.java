package domain.sc.scLetter;

import java.io.Serializable;
import java.util.Date;

public class ScLetterReplyItemView implements Serializable {
    private Integer id;

    private Integer replyId;

    private Integer userId;

    private String content;

    private Integer letterId;

    private Integer replyType;

    private Date replyDate;

    private Integer replyNum;

    private Integer letterYear;

    private Integer letterNum;

    private Date letterQueryDate;

    private Integer letterType;

    private String realname;

    private String code;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getReplyId() {
        return replyId;
    }

    public void setReplyId(Integer replyId) {
        this.replyId = replyId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public Integer getLetterId() {
        return letterId;
    }

    public void setLetterId(Integer letterId) {
        this.letterId = letterId;
    }

    public Integer getReplyType() {
        return replyType;
    }

    public void setReplyType(Integer replyType) {
        this.replyType = replyType;
    }

    public Date getReplyDate() {
        return replyDate;
    }

    public void setReplyDate(Date replyDate) {
        this.replyDate = replyDate;
    }

    public Integer getReplyNum() {
        return replyNum;
    }

    public void setReplyNum(Integer replyNum) {
        this.replyNum = replyNum;
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

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname == null ? null : realname.trim();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }
}