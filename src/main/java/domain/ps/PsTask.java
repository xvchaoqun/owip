package domain.ps;

import persistence.ps.common.IPsMapper;
import sys.tags.CmTag;

import java.io.Serializable;
import java.util.Date;

public class PsTask implements Serializable {

    public Integer getCountFile(){
        IPsMapper iPsMapper = CmTag.getBean(IPsMapper.class);
        return iPsMapper.getCountFile(id);
    }

    private Integer id;

    private String name;

    private Integer year;

    private String psIds;

    private String files;

    private Date releaseDate;

    private Boolean isPublish;

    private String remark;

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

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getPsIds() {
        return psIds;
    }

    public void setPsIds(String psIds) {
        this.psIds = psIds == null ? null : psIds.trim();
    }

    public String getFiles() {
        return files;
    }

    public void setFiles(String files) {
        this.files = files == null ? null : files.trim();
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Boolean getIsPublish() {
        return isPublish;
    }

    public void setIsPublish(Boolean isPublish) {
        this.isPublish = isPublish;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}