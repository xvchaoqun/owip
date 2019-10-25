package bean;
public class PartyMemberInfoForm extends CadreInfoForm {

    private String branchName;
    private Boolean isPrefessionalSecretary;
    private String assignDate;
    private String resume;
    private String reward;
    private String ces;

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public Boolean getIsPrefessionalSecretary() {
        return isPrefessionalSecretary;
    }

    public void setIsPrefessionalSecretary(Boolean prefessionalSecretary) {
        isPrefessionalSecretary = prefessionalSecretary;
    }

    public String getAssignDate() {
        return assignDate;
    }

    public void setAssignDate(String assignDate) {
        this.assignDate = assignDate;
    }

    public String getResume() {
        return resume;
    }

    public void setResume(String resume) {
        this.resume = resume;
    }

    @Override
    public String getReward() {
        return reward;
    }

    @Override
    public void setReward(String reward) {
        this.reward = reward;
    }

    @Override
    public String getCes() {
        return ces;
    }

    @Override
    public void setCes(String ces) {
        this.ces = ces;
    }
}
