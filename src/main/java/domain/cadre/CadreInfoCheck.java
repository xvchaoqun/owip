package domain.cadre;

import java.io.Serializable;

public class CadreInfoCheck implements Serializable {
    private Integer cadreId;

    private Boolean postPro;

    private Boolean postAdmin;

    private Boolean postWork;

    private Boolean parttime;

    private Boolean train;

    private Boolean course;

    private Boolean courseReward;

    private Boolean researchDirect;

    private Boolean researchIn;

    private Boolean book;

    private Boolean paper;

    private Boolean researchReward;

    private Boolean reward;

    private Boolean famliyAbroad;

    private Boolean company;

    private static final long serialVersionUID = 1L;

    public Integer getCadreId() {
        return cadreId;
    }

    public void setCadreId(Integer cadreId) {
        this.cadreId = cadreId;
    }

    public Boolean getPostPro() {
        return postPro;
    }

    public void setPostPro(Boolean postPro) {
        this.postPro = postPro;
    }

    public Boolean getPostAdmin() {
        return postAdmin;
    }

    public void setPostAdmin(Boolean postAdmin) {
        this.postAdmin = postAdmin;
    }

    public Boolean getPostWork() {
        return postWork;
    }

    public void setPostWork(Boolean postWork) {
        this.postWork = postWork;
    }

    public Boolean getParttime() {
        return parttime;
    }

    public void setParttime(Boolean parttime) {
        this.parttime = parttime;
    }

    public Boolean getTrain() {
        return train;
    }

    public void setTrain(Boolean train) {
        this.train = train;
    }

    public Boolean getCourse() {
        return course;
    }

    public void setCourse(Boolean course) {
        this.course = course;
    }

    public Boolean getCourseReward() {
        return courseReward;
    }

    public void setCourseReward(Boolean courseReward) {
        this.courseReward = courseReward;
    }

    public Boolean getResearchDirect() {
        return researchDirect;
    }

    public void setResearchDirect(Boolean researchDirect) {
        this.researchDirect = researchDirect;
    }

    public Boolean getResearchIn() {
        return researchIn;
    }

    public void setResearchIn(Boolean researchIn) {
        this.researchIn = researchIn;
    }

    public Boolean getBook() {
        return book;
    }

    public void setBook(Boolean book) {
        this.book = book;
    }

    public Boolean getPaper() {
        return paper;
    }

    public void setPaper(Boolean paper) {
        this.paper = paper;
    }

    public Boolean getResearchReward() {
        return researchReward;
    }

    public void setResearchReward(Boolean researchReward) {
        this.researchReward = researchReward;
    }

    public Boolean getReward() {
        return reward;
    }

    public void setReward(Boolean reward) {
        this.reward = reward;
    }

    public Boolean getFamliyAbroad() {
        return famliyAbroad;
    }

    public void setFamliyAbroad(Boolean famliyAbroad) {
        this.famliyAbroad = famliyAbroad;
    }

    public Boolean getCompany() {
        return company;
    }

    public void setCompany(Boolean company) {
        this.company = company;
    }
}