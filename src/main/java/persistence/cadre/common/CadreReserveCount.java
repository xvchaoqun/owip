package persistence.cadre.common;

/**
 * Created by fafa on 2016/12/29.
 */
public class CadreReserveCount {

    private Byte type;
    private Byte status;
    private Integer  num;

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }
}
