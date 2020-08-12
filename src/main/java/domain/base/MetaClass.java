package domain.base;

import bean.MetaClassOption;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

public class MetaClass implements Serializable {

    public Map<String, MetaClassOption> getOptions(){

        if(StringUtils.isNotBlank(extraOptions)){

            Map<String, MetaClassOption> options = new LinkedHashMap<>();
            String[] _options = extraOptions.split(",");
            for (String _option : _options) {
                String[] tmp = _option.split("\\|");

                MetaClassOption option = new MetaClassOption();
                option.setKey(tmp[0]);
                option.setName((tmp.length>1)?tmp[1]:tmp[0]);
                option.setDetail((tmp.length>2)?tmp[2]:option.getName());
                option.setRemark((tmp.length>3)?tmp[3]:option.getName());

                options.put(tmp[0], option);
            }

            return options;
        }

        return null;
    }

    private Integer id;

    private String name;

    private String firstLevel;

    private String secondLevel;

    private String code;

    private String boolAttr;

    private String extraAttr;

    private String extraOptions;

    private Integer sortOrder;

    private Boolean isDeleted;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getFirstLevel() {
        return firstLevel;
    }

    public void setFirstLevel(String firstLevel) {
        this.firstLevel = firstLevel == null ? null : firstLevel.trim();
    }

    public String getSecondLevel() {
        return secondLevel;
    }

    public void setSecondLevel(String secondLevel) {
        this.secondLevel = secondLevel == null ? null : secondLevel.trim();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public String getBoolAttr() {
        return boolAttr;
    }

    public void setBoolAttr(String boolAttr) {
        this.boolAttr = boolAttr == null ? null : boolAttr.trim();
    }

    public String getExtraAttr() {
        return extraAttr;
    }

    public void setExtraAttr(String extraAttr) {
        this.extraAttr = extraAttr == null ? null : extraAttr.trim();
    }

    public String getExtraOptions() {
        return extraOptions;
    }

    public void setExtraOptions(String extraOptions) {
        this.extraOptions = extraOptions == null ? null : extraOptions.trim();
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }
}