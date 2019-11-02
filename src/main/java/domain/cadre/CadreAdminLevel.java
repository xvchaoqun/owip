package domain.cadre;

import domain.dispatch.Dispatch;
import sys.tags.CmTag;

import java.io.Serializable;
import java.util.Date;

public class CadreAdminLevel implements Serializable {

    public Dispatch getsDispatch(){
        return CmTag.getDispatch(sDispatchId);
    }

    public Dispatch geteDispatch(){
        return CmTag.getDispatch(eDispatchId);
    }

    private Integer id;

    private Integer cadreId;

    private Integer adminLevel;

    private Integer sDispatchId;

    private Date sWorkTime;

    private Integer eDispatchId;

    private Date eWorkTime;

    private String sPost;

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

    public Integer getAdminLevel() {
        return adminLevel;
    }

    public void setAdminLevel(Integer adminLevel) {
        this.adminLevel = adminLevel;
    }

    public Integer getsDispatchId() {
        return sDispatchId;
    }

    public void setsDispatchId(Integer sDispatchId) {
        this.sDispatchId = sDispatchId;
    }

    public Date getsWorkTime() {
        return sWorkTime;
    }

    public void setsWorkTime(Date sWorkTime) {
        this.sWorkTime = sWorkTime;
    }

    public Integer geteDispatchId() {
        return eDispatchId;
    }

    public void seteDispatchId(Integer eDispatchId) {
        this.eDispatchId = eDispatchId;
    }

    public Date geteWorkTime() {
        return eWorkTime;
    }

    public void seteWorkTime(Date eWorkTime) {
        this.eWorkTime = eWorkTime;
    }

    public String getsPost() {
        return sPost;
    }

    public void setsPost(String sPost) {
        this.sPost = sPost == null ? null : sPost.trim();
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