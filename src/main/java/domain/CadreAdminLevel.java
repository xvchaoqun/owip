package domain;

import sys.tags.CmTag;

import java.io.Serializable;

public class CadreAdminLevel implements Serializable {

    public DispatchCadre getStartDispatchCadre(){
        if(startDispatchCadreId!=null){
            return CmTag.getDispatchCadre(startDispatchCadreId);
        }
        return null;
    }

    public Dispatch getStartDispatch(){

        DispatchCadre startDispatchCadre = getStartDispatchCadre();
        if(startDispatchCadre!=null){
            return startDispatchCadre.getDispatch();
        }
        return null;
    }
    public Dispatch getEndDispatch(){
        if(endDispatchCadreId!=null){
            DispatchCadre dispatchCadre = CmTag.getDispatchCadre(endDispatchCadreId);
            return dispatchCadre.getDispatch();
        }
        return null;
    }

    private Integer id;

    private Integer cadreId;

    private Integer adminLevelId;

    private Integer startDispatchCadreId;

    private Integer endDispatchCadreId;

    private String remark;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCadreId() {
        return cadreId;
    }

    public void setCadreId(Integer cadreId) {
        this.cadreId = cadreId;
    }

    public Integer getAdminLevelId() {
        return adminLevelId;
    }

    public void setAdminLevelId(Integer adminLevelId) {
        this.adminLevelId = adminLevelId;
    }

    public Integer getStartDispatchCadreId() {
        return startDispatchCadreId;
    }

    public void setStartDispatchCadreId(Integer startDispatchCadreId) {
        this.startDispatchCadreId = startDispatchCadreId;
    }

    public Integer getEndDispatchCadreId() {
        return endDispatchCadreId;
    }

    public void setEndDispatchCadreId(Integer endDispatchCadreId) {
        this.endDispatchCadreId = endDispatchCadreId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}