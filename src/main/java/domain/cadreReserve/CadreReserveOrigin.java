package domain.cadreReserve;

import domain.cadre.CadreView;
import domain.cis.CisInspectObj;
import org.springframework.format.annotation.DateTimeFormat;
import persistence.cis.CisInspectObjMapper;
import sys.tags.CmTag;
import sys.utils.DateUtils;

import java.io.Serializable;
import java.util.Date;

public class CadreReserveOrigin implements Serializable {

    // 干部考察材料编号
    public String getSn(){

        CisInspectObjMapper cisInspectObjMapper = CmTag.getBean(CisInspectObjMapper.class);
        if(objId!=null && cisInspectObjMapper!=null){
            CisInspectObj cisInspectObj = cisInspectObjMapper.selectByPrimaryKey(objId);
            if(cisInspectObj!=null) return cisInspectObj.getSn();
        }
        return null;
    }

    public CadreView getCadre(){

        return CmTag.getCadreByUserId(userId);
    }

    private Integer id;

    private Byte way;

    private Integer userId;

    private Byte reserveType;

    private String recommendUnit;

    @DateTimeFormat(pattern = DateUtils.YYYY_MM_DD)
    private Date recommendDate;

    private String wordFilePath;

    private String pdfFilePath;

    private Integer objId;

    private String remark;

    private Date addTime;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Byte getWay() {
        return way;
    }

    public void setWay(Byte way) {
        this.way = way;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Byte getReserveType() {
        return reserveType;
    }

    public void setReserveType(Byte reserveType) {
        this.reserveType = reserveType;
    }

    public String getRecommendUnit() {
        return recommendUnit;
    }

    public void setRecommendUnit(String recommendUnit) {
        this.recommendUnit = recommendUnit == null ? null : recommendUnit.trim();
    }

    public Date getRecommendDate() {
        return recommendDate;
    }

    public void setRecommendDate(Date recommendDate) {
        this.recommendDate = recommendDate;
    }

    public String getWordFilePath() {
        return wordFilePath;
    }

    public void setWordFilePath(String wordFilePath) {
        this.wordFilePath = wordFilePath == null ? null : wordFilePath.trim();
    }

    public String getPdfFilePath() {
        return pdfFilePath;
    }

    public void setPdfFilePath(String pdfFilePath) {
        this.pdfFilePath = pdfFilePath == null ? null : pdfFilePath.trim();
    }

    public Integer getObjId() {
        return objId;
    }

    public void setObjId(Integer objId) {
        this.objId = objId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }
}