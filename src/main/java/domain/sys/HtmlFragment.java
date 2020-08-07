package domain.sys;

import persistence.sys.HtmlFragmentMapper;
import sys.tags.CmTag;

import java.io.Serializable;
import java.util.List;

public class HtmlFragment implements Serializable {

    private List<HtmlFragment> childs;

    public List<HtmlFragment> getChilds() {
        return childs;
    }

    public void setChilds(List<HtmlFragment> childs) {
        this.childs = childs;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }
    public HtmlFragment getParent(){

        if(fid!=null && fid>0){

            HtmlFragmentMapper htmlFragmentMapper = CmTag.getBean(HtmlFragmentMapper.class);
            return htmlFragmentMapper.selectByPrimaryKey(fid);
        }
        return null;
    }


    private Integer id;

    private Integer fid;

    private String code;

    private Byte category;

    private Byte type;

    private Integer roleId;

    private String title;

    private String content;

    private String attr;

    private Boolean isDeleted;

    private String remark;

    private Integer sortOrder;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFid() {
        return fid;
    }

    public void setFid(Integer fid) {
        this.fid = fid;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public Byte getCategory() {
        return category;
    }

    public void setCategory(Byte category) {
        this.category = category;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getAttr() {
        return attr;
    }

    public void setAttr(String attr) {
        this.attr = attr == null ? null : attr.trim();
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }
}