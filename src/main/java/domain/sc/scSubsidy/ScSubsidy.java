package domain.sc.scSubsidy;

import domain.base.AnnualType;
import org.springframework.format.annotation.DateTimeFormat;
import service.base.AnnualTypeService;
import sys.constants.SystemConstants;
import sys.tags.CmTag;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

public class ScSubsidy implements Serializable {

    public static String getHrCode(Integer hrType, short year, int hrNum){

        AnnualTypeService annualTypeService = CmTag.getBean(AnnualTypeService.class);
        Map<Integer, AnnualType> annualTypeMap = annualTypeService.findAll(SystemConstants.ANNUAL_TYPE_MODULE_SUBSIDY);
        if(hrType!=null){
            AnnualType annualType = annualTypeMap.get(hrType);
            String name = annualType.getName();

            return String.format("%s[%s]%s号", name, year, hrNum);
        }

        return null;
    }

    public String getHrCode(){

        return getHrCode(hrType, year, hrNum);
    }

    public static String getFeCode(Integer feType, short year, int feNum){

        AnnualTypeService annualTypeService = CmTag.getBean(AnnualTypeService.class);
        Map<Integer, AnnualType> annualTypeMap = annualTypeService.findAll(SystemConstants.ANNUAL_TYPE_MODULE_SUBSIDY);

        if(feType!=null){
            AnnualType annualType = annualTypeMap.get(feType);
            String name = annualType.getName();

            return String.format("%s[%s]%s号", name, year, feNum);
        }

        return null;
    }

    public String getFeCode(){

        return getFeCode(feType, year, feNum);
    }

    private Integer id;

    private Short year;

    private Integer hrType;

    private Integer feType;

    private Integer hrNum;

    private Integer feNum;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date infoDate;

    private String hrFilePath;

    private String feFilePath;

    private String remark;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Short getYear() {
        return year;
    }

    public void setYear(Short year) {
        this.year = year;
    }

    public Integer getHrType() {
        return hrType;
    }

    public void setHrType(Integer hrType) {
        this.hrType = hrType;
    }

    public Integer getFeType() {
        return feType;
    }

    public void setFeType(Integer feType) {
        this.feType = feType;
    }

    public Integer getHrNum() {
        return hrNum;
    }

    public void setHrNum(Integer hrNum) {
        this.hrNum = hrNum;
    }

    public Integer getFeNum() {
        return feNum;
    }

    public void setFeNum(Integer feNum) {
        this.feNum = feNum;
    }

    public Date getInfoDate() {
        return infoDate;
    }

    public void setInfoDate(Date infoDate) {
        this.infoDate = infoDate;
    }

    public String getHrFilePath() {
        return hrFilePath;
    }

    public void setHrFilePath(String hrFilePath) {
        this.hrFilePath = hrFilePath == null ? null : hrFilePath.trim();
    }

    public String getFeFilePath() {
        return feFilePath;
    }

    public void setFeFilePath(String feFilePath) {
        this.feFilePath = feFilePath == null ? null : feFilePath.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}