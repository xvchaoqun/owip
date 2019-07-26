package ext.domain;

import java.io.Serializable;
import java.math.BigDecimal;

public class ExtRetireSalary implements Serializable {
    private Integer id;

    private String zgh;

    private String rq;

    private BigDecimal base;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getZgh() {
        return zgh;
    }

    public void setZgh(String zgh) {
        this.zgh = zgh == null ? null : zgh.trim();
    }

    public String getRq() {
        return rq;
    }

    public void setRq(String rq) {
        this.rq = rq == null ? null : rq.trim();
    }

    public BigDecimal getBase() {
        return base;
    }

    public void setBase(BigDecimal base) {
        this.base = base;
    }
}