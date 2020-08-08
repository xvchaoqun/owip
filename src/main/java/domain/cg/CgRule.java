package domain.cg;

import org.springframework.format.annotation.DateTimeFormat;
import service.cg.CgTeamService;
import sys.jackson.SignRes;
import sys.tags.CmTag;
import sys.utils.DateUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class CgRule implements Serializable {
    private Integer id;

    private Integer teamId;

    private Byte type;

    private Boolean isCurrent;

    @DateTimeFormat(pattern = DateUtils.YYYYMMDD_DOT)
    private Date confirmDate;

    private String content;

    public List<String> getContentList(){return CmTag.getBean(CgTeamService.class).formatContent(content);}

    @SignRes
    private String filePath;

    private Integer sortOrder;

    private String remark;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTeamId() {
        return teamId;
    }

    public void setTeamId(Integer teamId) {
        this.teamId = teamId;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public Boolean getIsCurrent() {
        return isCurrent;
    }

    public void setIsCurrent(Boolean isCurrent) {
        this.isCurrent = isCurrent;
    }

    public Date getConfirmDate() {
        return confirmDate;
    }

    public void setConfirmDate(Date confirmDate) {
        this.confirmDate = confirmDate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath == null ? null : filePath.trim();
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}