package controller.ftl;

/**
 * Created by liaomin on 16/10/7.
 */
public class ColBean {

    public String val;
    public int type; // 0:没有缩进, 1: 期间, 2:期间缩进

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
