package service.cpc;

import domain.cadre.CadrePost;
import domain.unit.Unit;

import java.util.List;

/**
 * Created by fafa on 2017/5/24.
 */
public class CpcAllocationBean {

    private Unit unit;

    // 正处级干部
    private Integer mainNum;
    private List<CadrePost> mains;
    private Integer mainCount; // 实际人数
    private Integer mainLack; // 空缺数

    // 副处级干部
    private Integer viceNum;
    private List<CadrePost> vices;
    private Integer viceCount;
    private Integer viceLack; // 空缺数

    // 无行政级别干部
    private Integer noneNum;
    private List<CadrePost> nones;
    private Integer noneCount;
    private Integer noneLack; // 空缺数

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public Integer getMainNum() {
        return mainNum;
    }

    public void setMainNum(Integer mainNum) {
        this.mainNum = mainNum;
    }

    public List<CadrePost> getMains() {
        return mains;
    }

    public void setMains(List<CadrePost> mains) {
        this.mains = mains;
    }

    public Integer getMainCount() {
        return mainCount;
    }

    public void setMainCount(Integer mainCount) {
        this.mainCount = mainCount;
    }

    public Integer getMainLack() {
        return mainLack;
    }

    public void setMainLack(Integer mainLack) {
        this.mainLack = mainLack;
    }

    public Integer getViceNum() {
        return viceNum;
    }

    public void setViceNum(Integer viceNum) {
        this.viceNum = viceNum;
    }

    public List<CadrePost> getVices() {
        return vices;
    }

    public void setVices(List<CadrePost> vices) {
        this.vices = vices;
    }

    public Integer getViceCount() {
        return viceCount;
    }

    public void setViceCount(Integer viceCount) {
        this.viceCount = viceCount;
    }

    public Integer getViceLack() {
        return viceLack;
    }

    public void setViceLack(Integer viceLack) {
        this.viceLack = viceLack;
    }

    public Integer getNoneNum() {
        return noneNum;
    }

    public void setNoneNum(Integer noneNum) {
        this.noneNum = noneNum;
    }

    public List<CadrePost> getNones() {
        return nones;
    }

    public void setNones(List<CadrePost> nones) {
        this.nones = nones;
    }

    public Integer getNoneCount() {
        return noneCount;
    }

    public void setNoneCount(Integer noneCount) {
        this.noneCount = noneCount;
    }

    public Integer getNoneLack() {
        return noneLack;
    }

    public void setNoneLack(Integer noneLack) {
        this.noneLack = noneLack;
    }
}
