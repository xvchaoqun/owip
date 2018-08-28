package domain.sc.scSubsidy;

import domain.cadre.CadreView;
import persistence.sc.scSubsidy.ScSubsidyMapper;
import sys.tags.CmTag;

import java.io.Serializable;

public class ScSubsidyCadre implements Serializable {

    public CadreView getCadre(){
        return CmTag.getCadreById(cadreId);
    }

    public ScSubsidy getSubsidy(){

        return CmTag.getBean(ScSubsidyMapper.class).selectByPrimaryKey(subsidyId);
    }

    private Integer id;

    private Integer subsidyId;

    private Integer cadreId;

    private Integer unitId;

    private String post;

    private String title;

    private Integer adminLevel;

    private String remark;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSubsidyId() {
        return subsidyId;
    }

    public void setSubsidyId(Integer subsidyId) {
        this.subsidyId = subsidyId;
    }

    public Integer getCadreId() {
        return cadreId;
    }

    public void setCadreId(Integer cadreId) {
        this.cadreId = cadreId;
    }

    public Integer getUnitId() {
        return unitId;
    }

    public void setUnitId(Integer unitId) {
        this.unitId = unitId;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post == null ? null : post.trim();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public Integer getAdminLevel() {
        return adminLevel;
    }

    public void setAdminLevel(Integer adminLevel) {
        this.adminLevel = adminLevel;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}