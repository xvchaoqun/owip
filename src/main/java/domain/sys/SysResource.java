package domain.sys;

import java.io.Serializable;

public class SysResource implements Serializable {

    public Integer getLevel(){

        return parentIds.split("/").length-1;
    }

    private Integer id;

    private Boolean isMobile;

    private String name;

    private String remark;

    private String type;

    private String menuCss;

    private String url;

    private Integer parentId;

    private String parentIds;

    private Boolean isLeaf;

    private String permission;

    private String countCacheKeys;

    private String countCacheRoles;

    private Byte available;

    private Integer sortOrder;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getIsMobile() {
        return isMobile;
    }

    public void setIsMobile(Boolean isMobile) {
        this.isMobile = isMobile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getMenuCss() {
        return menuCss;
    }

    public void setMenuCss(String menuCss) {
        this.menuCss = menuCss == null ? null : menuCss.trim();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getParentIds() {
        return parentIds;
    }

    public void setParentIds(String parentIds) {
        this.parentIds = parentIds == null ? null : parentIds.trim();
    }

    public Boolean getIsLeaf() {
        return isLeaf;
    }

    public void setIsLeaf(Boolean isLeaf) {
        this.isLeaf = isLeaf;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission == null ? null : permission.trim();
    }

    public String getCountCacheKeys() {
        return countCacheKeys;
    }

    public void setCountCacheKeys(String countCacheKeys) {
        this.countCacheKeys = countCacheKeys == null ? null : countCacheKeys.trim();
    }

    public String getCountCacheRoles() {
        return countCacheRoles;
    }

    public void setCountCacheRoles(String countCacheRoles) {
        this.countCacheRoles = countCacheRoles == null ? null : countCacheRoles.trim();
    }

    public Byte getAvailable() {
        return available;
    }

    public void setAvailable(Byte available) {
        this.available = available;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }
}