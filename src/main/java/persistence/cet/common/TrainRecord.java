package persistence.cet.common;

import java.math.BigDecimal;
import java.util.Date;

public class TrainRecord {
    
    private Date startDate;
    private Date endDate;
    private String name;
    private Byte type;
    private String organizer;
    private BigDecimal period;
    private Boolean isGraduate;
    
    public Date getStartDate() {
        return startDate;
    }
    
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
    
    public Date getEndDate() {
        return endDate;
    }
    
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public Byte getType() {
        return type;
    }
    
    public void setType(Byte type) {
        this.type = type;
    }
    
    public String getOrganizer() {
        return organizer;
    }
    
    public void setOrganizer(String organizer) {
        this.organizer = organizer;
    }
    
    public BigDecimal getPeriod() {
        return period;
    }
    
    public void setPeriod(BigDecimal period) {
        this.period = period;
    }
    
    public Boolean getIsGraduate() {
        return isGraduate;
    }
    
    public void setIsGraduate(Boolean isGraduate) {
        this.isGraduate = isGraduate;
    }
}
