package persistence.common;

/**
 * Created by fafa on 2016/11/8.
 */
public class PassportSearchBean {

    private Integer unitId;
    private Integer cadreId;
    private Integer classId;
    private String code;
    private Byte type;
    private Integer safeBoxId;
    private Byte cancelConfirm;
    private Boolean isLent;

    public PassportSearchBean() {
    }

    public PassportSearchBean(Integer unitId, Integer cadreId, Integer classId, String code,
                              Byte type, Integer safeBoxId, Byte cancelConfirm, Boolean isLent) {
        this.unitId = unitId;
        this.cadreId = cadreId;
        this.classId = classId;
        this.code = code;
        this.type = type;
        this.safeBoxId = safeBoxId;
        this.cancelConfirm = cancelConfirm;
        this.isLent = isLent;
    }

    public Integer getUnitId() {
        return unitId;
    }

    public void setUnitId(Integer unitId) {
        this.unitId = unitId;
    }

    public Integer getCadreId() {
        return cadreId;
    }

    public void setCadreId(Integer cadreId) {
        this.cadreId = cadreId;
    }

    public Integer getClassId() {
        return classId;
    }

    public void setClassId(Integer classId) {
        this.classId = classId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public Integer getSafeBoxId() {
        return safeBoxId;
    }

    public void setSafeBoxId(Integer safeBoxId) {
        this.safeBoxId = safeBoxId;
    }

    public Byte getCancelConfirm() {
        return cancelConfirm;
    }

    public void setCancelConfirm(Byte cancelConfirm) {
        this.cancelConfirm = cancelConfirm;
    }

    public Boolean getIsLent() {
        return isLent;
    }

    public void setIsLent(Boolean isLent) {
        this.isLent = isLent;
    }
}
