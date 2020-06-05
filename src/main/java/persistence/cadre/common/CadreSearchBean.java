package persistence.cadre.common;

import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * Created by fafa on 2017/1/18.
 */
public class CadreSearchBean {

    public static CadreSearchBean getInstance(String unitTypeGroup, byte cadreType){

        CadreSearchBean searchBean = new CadreSearchBean();
        searchBean.setUnitTypeGroup(unitTypeGroup);
        searchBean.setCadreType(cadreType);

        return searchBean;
    }

    public String unitTypeGroup; // 单位大类
    public byte cadreType; // 干部类型， 1：处级  2：科级
    public Boolean isPrincipal; // 是否正职
    public List<Integer> labels; // 干部标签
    public List<Integer> adminLevels; // 行政级别
    public Boolean isKeepSalary; // 是否保留待遇
    public Integer minNowPostAge; // 现职务始任年限（最小值）
    public Integer maxNowPostAge; // 现职务始任年限（最大值）

    public boolean birthToDay; // 年龄计算是否精确到日（系统属性中设定，用来计算精确的年龄）

    public String getUnitTypeGroup() {
        return unitTypeGroup;
    }

    public CadreSearchBean setUnitTypeGroup(String unitTypeGroup) {

        this.unitTypeGroup = StringUtils.trimToNull(unitTypeGroup);
        return this;
    }

    public byte getCadreType() {
        return cadreType;
    }

    public CadreSearchBean setCadreType(byte cadreType) {
        this.cadreType = cadreType;
        return this;
    }

    public Boolean getPrincipal() {
        return isPrincipal;
    }

    public CadreSearchBean setPrincipal(Boolean principal) {
        isPrincipal = principal;
        return this;
    }

    public List<Integer> getLabels() {
        return labels;
    }

    public CadreSearchBean setLabels(List<Integer> labels) {
        this.labels = labels;
        return this;
    }

    public List<Integer> getAdminLevels() {
        return adminLevels;
    }

    public CadreSearchBean setAdminLevels(List<Integer> adminLevels) {
        this.adminLevels = adminLevels;
        return this;
    }

    public Boolean getKeepSalary() {
        return isKeepSalary;
    }

    public CadreSearchBean setKeepSalary(Boolean keepSalary) {
        isKeepSalary = keepSalary;
        return this;
    }

    public Integer getMinNowPostAge() {
        return minNowPostAge;
    }

    public CadreSearchBean setMinNowPostAge(Integer minNowPostAge) {
        this.minNowPostAge = minNowPostAge;
        return this;
    }

    public Integer getMaxNowPostAge() {
        return maxNowPostAge;
    }

    public CadreSearchBean setMaxNowPostAge(Integer maxNowPostAge) {
        this.maxNowPostAge = maxNowPostAge;
        return this;
    }

    public boolean isBirthToDay() {
        return birthToDay;
    }

    public CadreSearchBean setBirthToDay(boolean birthToDay) {
        this.birthToDay = birthToDay;
        return this;
    }
}
