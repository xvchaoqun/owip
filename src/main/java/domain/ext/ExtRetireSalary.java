package domain.ext;

import java.io.Serializable;
import java.math.BigDecimal;

public class ExtRetireSalary implements Serializable {
    private Integer id;

    private String zgh;

    private BigDecimal ltxf;

    private String rq;

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

    public BigDecimal getLtxf() {
        return ltxf;
    }

    public void setLtxf(BigDecimal ltxf) {
        this.ltxf = ltxf;
    }

    public String getRq() {
        return rq;
    }

    public void setRq(String rq) {
        this.rq = rq == null ? null : rq.trim();
    }
}