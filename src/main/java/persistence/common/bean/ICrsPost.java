package persistence.common.bean;

import domain.crs.CrsPost;

public class ICrsPost extends CrsPost {

    private Integer applicantId;
    private Boolean applicantIsQuit;

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
}
