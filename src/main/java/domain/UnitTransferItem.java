package domain;

import java.io.Serializable;

public class UnitTransferItem implements Serializable {
    private Integer id;

    private Integer transferId;

    private Integer dispatchUnitId;

    private Integer sortOrder;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTransferId() {
        return transferId;
    }

    public void setTransferId(Integer transferId) {
        this.transferId = transferId;
    }

    public Integer getDispatchUnitId() {
        return dispatchUnitId;
    }

    public void setDispatchUnitId(Integer dispatchUnitId) {
        this.dispatchUnitId = dispatchUnitId;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }
}