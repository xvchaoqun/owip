package domain.pcs;

import domain.sys.SysUserView;
import persistence.pcs.PcsProposalFileMapper;
import sys.tags.CmTag;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class PcsProposalView implements Serializable {

    public List<PcsProposalFile> getFiles(){

        PcsProposalFileExample example = new PcsProposalFileExample();
        example.createCriteria().andProposalIdEqualTo(id);
        return CmTag.getBean(PcsProposalFileMapper.class).selectByExample(example);
    }
    public SysUserView getUser(){
        return CmTag.getUserById(userId);
    }

    private Integer id;

    private Integer configId;

    private String code;

    private Integer userId;

    private String name;

    private String keywords;

    private Integer type;

    private String content;

    private Date createTime;

    private Date checkTime;

    private Byte status;

    private String inviteUserIds;

    private Integer inviteCount;

    private String seconderIds;

    private Integer seconderCount;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getConfigId() {
        return configId;
    }

    public void setConfigId(Integer configId) {
        this.configId = configId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords == null ? null : keywords.trim();
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(Date checkTime) {
        this.checkTime = checkTime;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public String getInviteUserIds() {
        return inviteUserIds;
    }

    public void setInviteUserIds(String inviteUserIds) {
        this.inviteUserIds = inviteUserIds == null ? null : inviteUserIds.trim();
    }

    public Integer getInviteCount() {
        return inviteCount;
    }

    public void setInviteCount(Integer inviteCount) {
        this.inviteCount = inviteCount;
    }

    public String getSeconderIds() {
        return seconderIds;
    }

    public void setSeconderIds(String seconderIds) {
        this.seconderIds = seconderIds == null ? null : seconderIds.trim();
    }

    public Integer getSeconderCount() {
        return seconderCount;
    }

    public void setSeconderCount(Integer seconderCount) {
        this.seconderCount = seconderCount;
    }
}