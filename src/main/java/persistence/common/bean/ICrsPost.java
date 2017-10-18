package persistence.common.bean;

import domain.crs.CrsPost;

public class ICrsPost extends CrsPost {

    private Integer applicantId;
    private Boolean applicantIsQuit;
    private Byte infoCheckStatus;
    private Byte requireCheckStatus;
    private Boolean isRequireCheckPass;

    public Integer getApplicantId() {
        return applicantId;
    }

    public void setApplicantId(Integer applicantId) {
        this.applicantId = applicantId;
    }

    public Boolean getApplicantIsQuit() {
        return applicantIsQuit;
    }

    public void setApplicantIsQuit(Boolean applicantIsQuit) {
        this.applicantIsQuit = applicantIsQuit;
    }

    public Byte getInfoCheckStatus() {
        return infoCheckStatus;
    }

    public void setInfoCheckStatus(Byte infoCheckStatus) {
        this.infoCheckStatus = infoCheckStatus;
    }

    public Byte getRequireCheckStatus() {
        return requireCheckStatus;
    }

    public void setRequireCheckStatus(Byte requireCheckStatus) {
        this.requireCheckStatus = requireCheckStatus;
    }

    public Boolean getIsRequireCheckPass() {
        return isRequireCheckPass;
    }

    public void setIsRequireCheckPass(Boolean isRequireCheckPass) {
        this.isRequireCheckPass = isRequireCheckPass;
    }
}
