package domain.sc;

import java.io.Serializable;

public class ScMatterTransferItem implements Serializable {
    private Integer id;

    private Integer transferId;

    private Integer matterItemId;

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

    public Integer getMatterItemId() {
        return matterItemId;
    }

    public void setMatterItemId(Integer matterItemId) {
        this.matterItemId = matterItemId;
    }
}