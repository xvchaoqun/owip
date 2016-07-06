package domain.cadre;

import java.io.Serializable;

public class CadreResearch implements Serializable {
    private Integer id;

    private Integer cadreId;

    private String chairFile;

    private String chairFileName;

    private String joinFile;

    private String joinFileName;

    private String publishFile;

    private String publishFileName;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCadreId() {
        return cadreId;
    }

    public void setCadreId(Integer cadreId) {
        this.cadreId = cadreId;
    }

    public String getChairFile() {
        return chairFile;
    }

    public void setChairFile(String chairFile) {
        this.chairFile = chairFile == null ? null : chairFile.trim();
    }

    public String getChairFileName() {
        return chairFileName;
    }

    public void setChairFileName(String chairFileName) {
        this.chairFileName = chairFileName == null ? null : chairFileName.trim();
    }

    public String getJoinFile() {
        return joinFile;
    }

    public void setJoinFile(String joinFile) {
        this.joinFile = joinFile == null ? null : joinFile.trim();
    }

    public String getJoinFileName() {
        return joinFileName;
    }

    public void setJoinFileName(String joinFileName) {
        this.joinFileName = joinFileName == null ? null : joinFileName.trim();
    }

    public String getPublishFile() {
        return publishFile;
    }

    public void setPublishFile(String publishFile) {
        this.publishFile = publishFile == null ? null : publishFile.trim();
    }

    public String getPublishFileName() {
        return publishFileName;
    }

    public void setPublishFileName(String publishFileName) {
        this.publishFileName = publishFileName == null ? null : publishFileName.trim();
    }
}