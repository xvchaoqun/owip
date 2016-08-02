package domain.dispatch;

import java.io.Serializable;

public class DispatchCadreRelate implements Serializable {
    private Integer id;

    private Integer dispatchCadreId;

    private Integer relateId;

    private Byte relateType;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDispatchCadreId() {
        return dispatchCadreId;
    }

    public void setDispatchCadreId(Integer dispatchCadreId) {
        this.dispatchCadreId = dispatchCadreId;
    }

    public Integer getRelateId() {
        return relateId;
    }

    public void setRelateId(Integer relateId) {
        this.relateId = relateId;
    }

    public Byte getRelateType() {
        return relateType;
    }

    public void setRelateType(Byte relateType) {
        this.relateType = relateType;
    }
}