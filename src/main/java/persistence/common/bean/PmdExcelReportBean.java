package persistence.common.bean;

/**
 * Created by lm on 2017/12/16.
 */
public class PmdExcelReportBean {

    private Integer total;
    private Integer zj;
    private Integer gl;
    private Integer gq;
    private Integer xp;
    private Integer xszl;
    private Integer lt;
    private Integer bdx;
    private Integer dx;

    @Override
    public String toString() {
        return "PmdExcelReportBean{" +
                "total=" + total +
                ", zj=" + zj +
                ", gl=" + gl +
                ", gq=" + gq +
                ", xp=" + xp +
                ", xszl=" + xszl +
                ", lt=" + lt +
                ", bdx=" + bdx +
                ", dx=" + dx +
                '}';
    }

    // 其他
    public int getOther(){

        return getTotal() - (getZj()+getGl() + getGq() + getXp() + getXszl() + getLt() + getBdx() + getDx());
    }

    private int returnInt(Integer val) {
        return val == null ? 0 : val;
    }

    public Integer getTotal() {
        return returnInt(total);
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getZj() {
        return returnInt(zj);
    }

    public void setZj(Integer zj) {
        this.zj = zj;
    }

    public Integer getGl() {
        return returnInt(gl);
    }

    public void setGl(Integer gl) {
        this.gl = gl;
    }

    public Integer getGq() {
        return returnInt(gq);
    }

    public void setGq(Integer gq) {
        this.gq = gq;
    }

    public Integer getXp() {
        return returnInt(xp);
    }

    public void setXp(Integer xp) {
        this.xp = xp;
    }

    public Integer getXszl() {
        return returnInt(xszl);
    }

    public void setXszl(Integer xszl) {
        this.xszl = xszl;
    }

    public Integer getLt() {
        return returnInt(lt);
    }

    public void setLt(Integer lt) {
        this.lt = lt;
    }

    public Integer getBdx() {
        return returnInt(bdx);
    }

    public void setBdx(Integer bdx) {
        this.bdx = bdx;
    }

    public Integer getDx() {
        return returnInt(dx);
    }

    public void setDx(Integer dx) {
        this.dx = dx;
    }
}
