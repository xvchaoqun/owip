package domain.oa;

import org.springframework.format.annotation.DateTimeFormat;
import sys.jackson.SignRes;
import sys.utils.DateUtils;

import java.io.Serializable;
import java.util.Date;

public class OaGrid implements Serializable {
    private Integer id;

    private String name;

    private Integer year;

    private Byte type;

    @SignRes
    private String templateFilePath;

    private Integer row;

    private String col;

    private String startPos;

    private String endPos;

    private String readonlyPos;

    private String content;

    @DateTimeFormat(pattern = DateUtils.YYYY_MM_DD_HH_MM)
    private Date deadline;

    private String contact;

    private String remark;

    private Byte status;

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

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public String getTemplateFilePath() {
        return templateFilePath;
    }

    public void setTemplateFilePath(String templateFilePath) {
        this.templateFilePath = templateFilePath == null ? null : templateFilePath.trim();
    }

    public Integer getRow() {
        return row;
    }

    public void setRow(Integer row) {
        this.row = row;
    }

    public String getCol() {
        return col;
    }

    public void setCol(String col) {
        this.col = col == null ? null : col.trim();
    }

    public String getStartPos() {
        return startPos;
    }

    public void setStartPos(String startPos) {
        this.startPos = startPos == null ? null : startPos.trim();
    }

    public String getEndPos() {
        return endPos;
    }

    public void setEndPos(String endPos) {
        this.endPos = endPos == null ? null : endPos.trim();
    }

    public String getReadonlyPos() {
        return readonlyPos;
    }

    public void setReadonlyPos(String readonlyPos) {
        this.readonlyPos = readonlyPos == null ? null : readonlyPos.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact == null ? null : contact.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }
}