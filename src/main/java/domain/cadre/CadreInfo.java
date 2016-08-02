package domain.cadre;

import java.io.Serializable;
import java.util.Date;

public class CadreInfo implements Serializable {
    private Integer id;

    private Byte type;

    private Integer cadreId;

    private String content;

    private Date lastSaveDate;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public Integer getCadreId() {
        return cadreId;
    }

    public void setCadreId(Integer cadreId) {
        this.cadreId = cadreId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public Date getLastSaveDate() {
        return lastSaveDate;
    }

    public void setLastSaveDate(Date lastSaveDate) {
        this.lastSaveDate = lastSaveDate;
    }
}