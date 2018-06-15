package domain.crs;

import java.io.Serializable;

public class CrsPostWithBLOBs extends CrsPost implements Serializable {
    private String postDuty;

    private String requirement;

    private String qualification;

    private String meetingNotice;

    private String meetingSummary;

    private static final long serialVersionUID = 1L;

    public String getPostDuty() {
        return postDuty;
    }

    public void setPostDuty(String postDuty) {
        this.postDuty = postDuty == null ? null : postDuty.trim();
    }

    public String getRequirement() {
        return requirement;
    }

    public void setRequirement(String requirement) {
        this.requirement = requirement == null ? null : requirement.trim();
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification == null ? null : qualification.trim();
    }

    public String getMeetingNotice() {
        return meetingNotice;
    }

    public void setMeetingNotice(String meetingNotice) {
        this.meetingNotice = meetingNotice == null ? null : meetingNotice.trim();
    }

    public String getMeetingSummary() {
        return meetingSummary;
    }

    public void setMeetingSummary(String meetingSummary) {
        this.meetingSummary = meetingSummary == null ? null : meetingSummary.trim();
    }
}