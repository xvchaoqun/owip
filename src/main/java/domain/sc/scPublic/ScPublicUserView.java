package domain.sc.scPublic;

import domain.cadre.CadreView;
import domain.sc.scCommittee.ScCommitteeView;
import domain.sc.scRecord.ScRecordView;
import domain.sys.SysUserView;
import persistence.sc.IScMapper;
import sys.tags.CmTag;
import sys.utils.DateUtils;

import java.io.Serializable;
import java.util.Date;

public class ScPublicUserView implements Serializable {

    public CadreView getCadre(){ return CmTag.getCadreById(cadreId);}
    public SysUserView getRecordUser(){ return CmTag.getUserById(recordUserId);}

    public ScRecordView getScRecord(){

        if(recordId==null) return null;
        IScMapper iScMapper = CmTag.getBean(IScMapper.class);
        if(iScMapper==null) return null;
        return iScMapper.getScRecordView(recordId);
    }

    public ScCommitteeView getScCommittee(){

        if(committeeId==null) return null;
        IScMapper iScMapper = CmTag.getBean(IScMapper.class);
        if(iScMapper==null) return null;
        return iScMapper.getScCommitteeView(committeeId);
    }

    public String getCode(){
        return String.format("公示〔%s〕号", DateUtils.formatDate(publishDate, "yyyyMMdd"));
    }

    private Integer id;

    private Integer publicId;

    private Integer voteId;

    private Integer sortOrder;

    private Integer recordId;

    private Integer year;

    private Integer committeeId;

    private Date publishDate;

    private Date publicStartDate;

    private Date publicEndDate;

    private String pdfFilePath;

    private String wordFilePath;

    private Integer recordUserId;

    private String originalPost;

    private Integer cadreId;

    private String post;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPublicId() {
        return publicId;
    }

    public void setPublicId(Integer publicId) {
        this.publicId = publicId;
    }

    public Integer getVoteId() {
        return voteId;
    }

    public void setVoteId(Integer voteId) {
        this.voteId = voteId;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public Integer getRecordId() {
        return recordId;
    }

    public void setRecordId(Integer recordId) {
        this.recordId = recordId;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getCommitteeId() {
        return committeeId;
    }

    public void setCommitteeId(Integer committeeId) {
        this.committeeId = committeeId;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
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

    public String getPdfFilePath() {
        return pdfFilePath;
    }

    public void setPdfFilePath(String pdfFilePath) {
        this.pdfFilePath = pdfFilePath == null ? null : pdfFilePath.trim();
    }

    public String getWordFilePath() {
        return wordFilePath;
    }

    public void setWordFilePath(String wordFilePath) {
        this.wordFilePath = wordFilePath == null ? null : wordFilePath.trim();
    }

    public Integer getRecordUserId() {
        return recordUserId;
    }

    public void setRecordUserId(Integer recordUserId) {
        this.recordUserId = recordUserId;
    }

    public String getOriginalPost() {
        return originalPost;
    }

    public void setOriginalPost(String originalPost) {
        this.originalPost = originalPost == null ? null : originalPost.trim();
    }

    public Integer getCadreId() {
        return cadreId;
    }

    public void setCadreId(Integer cadreId) {
        this.cadreId = cadreId;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post == null ? null : post.trim();
    }
}