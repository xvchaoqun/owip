package domain.base;

import java.io.Serializable;

public class SitemapRole implements Serializable {
    private Integer id;

    private Integer sitemapId;

    private Integer roleId;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSitemapId() {
        return sitemapId;
    }

    public void setSitemapId(Integer sitemapId) {
        this.sitemapId = sitemapId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }
}