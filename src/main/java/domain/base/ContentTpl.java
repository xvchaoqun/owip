package domain.base;

import domain.sys.SysUserView;
import service.base.ContentTplService;
import sys.tags.CmTag;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class ContentTpl implements Serializable {

    public List<SysUserView> getReceivers(){

        ContentTplService contentTplService = CmTag.getBean(ContentTplService.class);
        return contentTplService.getShorMsgReceivers(id);
    }

    private Integer id;

    private String name;

    private Integer roleId;

    private Byte type;

    private String code;

    private Byte wxMsgType;

    private String wxTitle;

    private String wxUrl;

    private String wxPic;

    private String content;

    private Byte contentType;

    private Byte engine;

    private Integer paramCount;

    private String paramNames;

    private String paramDefValues;

    private Integer sortOrder;

    private Integer userId;

    private Date createTime;

    private String remark;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public Byte getWxMsgType() {
        return wxMsgType;
    }

    public void setWxMsgType(Byte wxMsgType) {
        this.wxMsgType = wxMsgType;
    }

    public String getWxTitle() {
        return wxTitle;
    }

    public void setWxTitle(String wxTitle) {
        this.wxTitle = wxTitle == null ? null : wxTitle.trim();
    }

    public String getWxUrl() {
        return wxUrl;
    }

    public void setWxUrl(String wxUrl) {
        this.wxUrl = wxUrl == null ? null : wxUrl.trim();
    }

    public String getWxPic() {
        return wxPic;
    }

    public void setWxPic(String wxPic) {
        this.wxPic = wxPic == null ? null : wxPic.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public Byte getContentType() {
        return contentType;
    }

    public void setContentType(Byte contentType) {
        this.contentType = contentType;
    }

    public Byte getEngine() {
        return engine;
    }

    public void setEngine(Byte engine) {
        this.engine = engine;
    }

    public Integer getParamCount() {
        return paramCount;
    }

    public void setParamCount(Integer paramCount) {
        this.paramCount = paramCount;
    }

    public String getParamNames() {
        return paramNames;
    }

    public void setParamNames(String paramNames) {
        this.paramNames = paramNames == null ? null : paramNames.trim();
    }

    public String getParamDefValues() {
        return paramDefValues;
    }

    public void setParamDefValues(String paramDefValues) {
        this.paramDefValues = paramDefValues == null ? null : paramDefValues.trim();
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}