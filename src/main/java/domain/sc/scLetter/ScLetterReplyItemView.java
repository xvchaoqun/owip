package domain.sc.scLetter;

import domain.sc.scRecord.ScRecordView;
import domain.sc.scRecord.ScRecordViewExample;
import domain.sys.SysUserView;
import org.apache.commons.lang3.StringUtils;
import persistence.sc.IScMapper;
import persistence.sc.scRecord.ScRecordViewMapper;
import sys.tags.CmTag;
import sys.utils.NumberUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ScLetterReplyItemView implements Serializable {

    public SysUserView getRecordUser(){ return CmTag.getUserById(recordUserId);}

    public ScRecordView getScRecord(){

        if(recordId==null) return null;
        IScMapper iScMapper = CmTag.getBean(IScMapper.class);
        if(iScMapper==null) return null;
        return iScMapper.getScRecordView(recordId);
    }

    public List<ScRecordView> getScRecords(){

        if(StringUtils.isBlank(recordIds)) return null;
        ScRecordViewMapper scRecordViewMapper = CmTag.getBean(ScRecordViewMapper.class);
        if(scRecordViewMapper==null) return null;

        ScRecordViewExample example = new ScRecordViewExample();
        example.createCriteria().andIdIn(new ArrayList<>(NumberUtils.toIntSet(recordIds, ",")));
        return scRecordViewMapper.selectByExample(example);
    }

    private Integer id;

    private Integer replyId;

    private Integer userId;

    private String content;

    private Integer itemId;

    private Integer recordId;

    private String recordIds;

    private Integer recordUserId;

    private Integer letterId;

    private Integer replyType;

    private Date replyDate;

    private Integer replyNum;

    private String replyFilePath;

    private String replyFileName;

    private Integer letterYear;

    private Integer letterNum;

    private String letterFilePath;

    private String letterFileName;

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

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public Integer getRecordId() {
        return recordId;
    }

    public void setRecordId(Integer recordId) {
        this.recordId = recordId;
    }

    public String getRecordIds() {
        return recordIds;
    }

    public void setRecordIds(String recordIds) {
        this.recordIds = recordIds == null ? null : recordIds.trim();
    }

    public Integer getRecordUserId() {
        return recordUserId;
    }

    public void setRecordUserId(Integer recordUserId) {
        this.recordUserId = recordUserId;
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

    public String getReplyFilePath() {
        return replyFilePath;
    }

    public void setReplyFilePath(String replyFilePath) {
        this.replyFilePath = replyFilePath == null ? null : replyFilePath.trim();
    }

    public String getReplyFileName() {
        return replyFileName;
    }

    public void setReplyFileName(String replyFileName) {
        this.replyFileName = replyFileName == null ? null : replyFileName.trim();
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