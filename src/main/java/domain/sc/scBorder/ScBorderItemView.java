package domain.sc.scBorder;

import domain.cadre.CadreView;
import sys.tags.CmTag;
import sys.utils.DateUtils;

import java.io.Serializable;
import java.util.Date;

public class ScBorderItemView implements Serializable {

    public CadreView getCadre(){
        if(cadreId==null) return null;

        return CmTag.getCadreById(cadreId);
    }
    public String getCode(){
        return String.format("出入境报备[%s]号", DateUtils.formatDate(recordDate, "yyyyMMdd"));
    }
    
    private Integer id;

    private Integer borderId;

    private Byte type;

    private Integer cadreId;

    private String title;

    private Integer adminLevel;

    private String dispatchCadreIds;

    private String remark;

    private Integer year;

    private Date recordDate;

    private String addFile;

    private String changeFile;

    private String deleteFile;

    private String recordFile;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBorderId() {
        return borderId;
    }

    public void setBorderId(Integer borderId) {
        this.borderId = borderId;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public Integer getCadreId() {
        return cadreId;
    }

    public void setCadreId(Integer cadreId) {
        this.cadreId = cadreId;
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

    public String getDispatchCadreIds() {
        return dispatchCadreIds;
    }

    public void setDispatchCadreIds(String dispatchCadreIds) {
        this.dispatchCadreIds = dispatchCadreIds == null ? null : dispatchCadreIds.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Date getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(Date recordDate) {
        this.recordDate = recordDate;
    }

    public String getAddFile() {
        return addFile;
    }

    public void setAddFile(String addFile) {
        this.addFile = addFile == null ? null : addFile.trim();
    }

    public String getChangeFile() {
        return changeFile;
    }

    public void setChangeFile(String changeFile) {
        this.changeFile = changeFile == null ? null : changeFile.trim();
    }

    public String getDeleteFile() {
        return deleteFile;
    }

    public void setDeleteFile(String deleteFile) {
        this.deleteFile = deleteFile == null ? null : deleteFile.trim();
    }

    public String getRecordFile() {
        return recordFile;
    }

    public void setRecordFile(String recordFile) {
        this.recordFile = recordFile == null ? null : recordFile.trim();
    }
}