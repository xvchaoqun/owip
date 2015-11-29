package persistence.common;

import java.io.Serializable;

/**
 * Created by fafa on 2015/11/29.
 */
public class UnitAdminCadre implements Serializable{
    private int unitId;
    private  int cadreId;
    private int postId;
    private  boolean isPositive;

    public int getUnitId() {
        return unitId;
    }

    public void setUnitId(int unitId) {
        this.unitId = unitId;
    }

    public int getCadreId() {
        return cadreId;
    }

    public void setCadreId(int cadreId) {
        this.cadreId = cadreId;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public boolean isPositive() {
        return isPositive;
    }

    public void setPositive(boolean isPositive) {
        this.isPositive = isPositive;
    }
}
