package domain.base;

import domain.sys.SysRole;

import java.io.Serializable;
import java.util.List;

public class Sitemap implements Serializable {

    private List<Sitemap> subSitemaps;

    public List<Sitemap> getSubSitemaps() {
        return subSitemaps;
    }

    public void setSubSitemaps(List<Sitemap> subSitemaps) {
        this.subSitemaps = subSitemaps;
    }

    /*public List<SysRole> sysRoles;

    public List<SysRole> getSysRoles() {
        return sysRoles;
    }

    public void setSysRoles(List<SysRole> sysRoles) {
        this.sysRoles = sysRoles;
    }*/

    private Integer id;

    private Integer fid;

    private String title;

    private String url;

    private String remark;

    private Integer resourceId;

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Integer getResourceId() {
        return resourceId;
    }

    public void setResourceId(Integer resourceId) {
        this.resourceId = resourceId;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }
}