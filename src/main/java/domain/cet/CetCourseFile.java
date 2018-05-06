package domain.cet;

import java.io.Serializable;

public class CetCourseFile implements Serializable {
    private Integer id;

    private Integer courseId;

    private String fileName;

    private String filePath;

    private Boolean hasPaper;

    private String paperNote;

    private Integer sortOrder;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName == null ? null : fileName.trim();
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath == null ? null : filePath.trim();
    }

    public Boolean getHasPaper() {
        return hasPaper;
    }

    public void setHasPaper(Boolean hasPaper) {
        this.hasPaper = hasPaper;
    }

    public String getPaperNote() {
        return paperNote;
    }

    public void setPaperNote(String paperNote) {
        this.paperNote = paperNote == null ? null : paperNote.trim();
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }
}