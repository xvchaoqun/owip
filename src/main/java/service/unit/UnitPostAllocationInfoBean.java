package service.unit;

import domain.cadre.CadrePost;
import domain.unit.Unit;
import domain.unit.UnitPostView;

import java.util.List;

/**
 * Created by fafa on 2017/5/24.
 */
public class UnitPostAllocationInfoBean {

    private Unit unit;

    // 正处级干部
    private Integer mainNum;
    private List<CadrePost> mains;
    private List<CadrePost> mainKeep ;// 保留待遇
    private List<UnitPostView> mainLackPost;// 空缺岗位
    private Integer mainCount; // 实际人数
    private Integer mainLack; // 空缺数

    // 副处级干部
    private Integer viceNum;
    private List<CadrePost> vices;
    private List<CadrePost> viceKeep ;// 保留待遇
    private List<UnitPostView> viceLackPost;
    private Integer viceCount;
    private Integer viceLack; // 空缺数

    // 无行政级别干部
    private Integer noneNum;
    private List<CadrePost> nones;
    private Integer noneCount;
    private Integer noneLack; // 空缺数

    private Integer noCpcMainCount;  //正处级不占职数
    private List<CadrePost> noCpcMains;
    private Integer noCpcViceCount;  //副处级不占职数
    private List<CadrePost> noCpcVices;

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
    public List<UnitPostView> getMainLackPost() {
        return mainLackPost;
    }

    public void setMainLackPost(List<UnitPostView> mainLackPost) {
        this.mainLackPost = mainLackPost;
    }

    public List<UnitPostView> getViceLackPost() {
        return viceLackPost;
    }

    public void setViceLackPost(List<UnitPostView> viceLackPost) {
        this.viceLackPost = viceLackPost;
    }

    public List<CadrePost> getMainKeep() {
        return mainKeep;
    }

    public void setMainKeep(List<CadrePost> mainKeep) {
        this.mainKeep = mainKeep;
    }

    public List<CadrePost> getViceKeep() {
        return viceKeep;
    }

    public void setViceKeep(List<CadrePost> viceKeep) {
        this.viceKeep = viceKeep;
    }

    public Integer getNoCpcMainCount() {
        return noCpcMainCount;
    }

    public void setNoCpcMainCount(Integer noCpcMainCount) {
        this.noCpcMainCount = noCpcMainCount;
    }

    public List<CadrePost> getNoCpcMains() {
        return noCpcMains;
    }

    public void setNoCpcMains(List<CadrePost> noCpcMains) {
        this.noCpcMains = noCpcMains;
    }

    public Integer getNoCpcViceCount() {
        return noCpcViceCount;
    }

    public void setNoCpcViceCount(Integer noCpcViceCount) {
        this.noCpcViceCount = noCpcViceCount;
    }

    public List<CadrePost> getNoCpcVices() {
        return noCpcVices;
    }

    public void setNoCpcVices(List<CadrePost> noCpcVices) {
        this.noCpcVices = noCpcVices;
    }
}
