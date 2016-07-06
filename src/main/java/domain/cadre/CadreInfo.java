package domain.cadre;

import java.io.Serializable;
import java.util.Date;

public class CadreInfo implements Serializable {
    private Integer cadreId;

    private String mobile;

    private String officePhone;

    private String homePhone;

    private String email;

    private String work;

    private Date workSaveDate;

    private static final long serialVersionUID = 1L;

    public Integer getCadreId() {
        return cadreId;
    }

    public void setCadreId(Integer cadreId) {
        this.cadreId = cadreId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public String getOfficePhone() {
        return officePhone;
    }

    public void setOfficePhone(String officePhone) {
        this.officePhone = officePhone == null ? null : officePhone.trim();
    }

    public String getHomePhone() {
        return homePhone;
    }

    public void setHomePhone(String homePhone) {
        this.homePhone = homePhone == null ? null : homePhone.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getWork() {
        return work;
    }

    public void setWork(String work) {
        this.work = work == null ? null : work.trim();
    }

    public Date getWorkSaveDate() {
        return workSaveDate;
    }

    public void setWorkSaveDate(Date workSaveDate) {
        this.workSaveDate = workSaveDate;
    }
}