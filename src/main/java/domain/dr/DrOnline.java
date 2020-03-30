package domain.dr;

import org.apache.commons.lang3.StringUtils;
import org.springframework.format.annotation.DateTimeFormat;
import persistence.dr.DrMemberMapper;
import sys.tags.CmTag;
import sys.utils.DateUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DrOnline implements Serializable {

    public DrMember getChiefMember(){

        DrMemberMapper drMemberMapper = CmTag.getBean(DrMemberMapper.class);
        return drMemberMapper.selectByPrimaryKey(chiefMemberId);
    }

    public String getCode(){
        return String.format("民主推荐〔%s%s〕号",
                DateUtils.formatDate(recommendDate, "yyyyMMdd"),
                String.format("%02d", seq));
    }

    public List<DrMember> getDrMembers(){
        List<DrMember> memberList = new ArrayList<>();
        if(StringUtils.isNotBlank(members)) {
            DrMemberMapper drMemberMapper = CmTag.getBean(DrMemberMapper.class);
            for (String memberIdStr : members.split(",")) {
                memberList.add(drMemberMapper.selectByPrimaryKey(Integer.valueOf(memberIdStr)));
            }
        }
        return memberList;
    }

    private Integer id;

    private Integer recordId;

    private Short year;

    @DateTimeFormat(pattern = DateUtils.YYYY_MM_DD)
    private Date recommendDate;

    private Integer seq;

    private Byte status;

    private Integer type;

    private Integer chiefMemberId;

    private String members;

    @DateTimeFormat(pattern = DateUtils.YYYY_MM_DD)
    private Date startTime;

    @DateTimeFormat(pattern = DateUtils.YYYY_MM_DD)
    private Date endTime;

    private String remark;

    private String notice;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRecordId() {
        return recordId;
    }

    public void setRecordId(Integer recordId) {
        this.recordId = recordId;
    }

    public Short getYear() {
        return year;
    }

    public void setYear(Short year) {
        this.year = year;
    }

    public Date getRecommendDate() {
        return recommendDate;
    }

    public void setRecommendDate(Date recommendDate) {
        this.recommendDate = recommendDate;
    }

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getChiefMemberId() {
        return chiefMemberId;
    }

    public void setChiefMemberId(Integer chiefMemberId) {
        this.chiefMemberId = chiefMemberId;
    }

    public String getMembers() {
        return members;
    }

    public void setMembers(String members) {
        this.members = members == null ? null : members.trim();
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice == null ? null : notice.trim();
    }
}