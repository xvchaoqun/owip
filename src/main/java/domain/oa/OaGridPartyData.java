package domain.oa;

import java.io.Serializable;

public class OaGridPartyData implements Serializable {
    private Integer id;

    private Integer gridPartyId;

    private String cellLabel;

    private Integer num;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getGridPartyId() {
        return gridPartyId;
    }

    public void setGridPartyId(Integer gridPartyId) {
        this.gridPartyId = gridPartyId;
    }

    public String getCellLabel() {
        return cellLabel;
    }

    public void setCellLabel(String cellLabel) {
        this.cellLabel = cellLabel == null ? null : cellLabel.trim();
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }
}