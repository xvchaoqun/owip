package domain.cet;

import persistence.cet.CetTrainCourseMapper;
import service.cet.CetTraineeCourseService;
import service.cet.CetTraineeService;
import sys.tags.CmTag;
import sys.utils.ContextHelper;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class CetProjectObjCadreView implements Serializable {

    public Map getCetTraineeCourseView(){

        HttpServletRequest request = ContextHelper.getRequest();
        if(request==null) return null;
        Integer trainCourseId = (Integer) request.getAttribute("trainCourseId");
        if(trainCourseId==null) return null;

        CetTraineeCourseService cetTraineeCourseService = CmTag.getBean(CetTraineeCourseService.class);
        CetTraineeCourseView ctc = cetTraineeCourseService.getCetTraineeCourseView(userId, trainCourseId);
        Map<String, Object> resultMap = new HashMap<>();
        if(ctc!=null){
            resultMap.put("canQuit", ctc.getCanQuit());
            resultMap.put("traineeId", ctc.getTraineeId());
            resultMap.put("isFinished", ctc.getIsFinished());
            resultMap.put("chooseTime", ctc.getChooseTime());
            resultMap.put("chooseUserId", ctc.getChooseUserId());
            resultMap.put("chooseUserName", ctc.getChooseUserName());
        }else{
            resultMap.put("canQuit", true);
            CetTrainCourseMapper cetTrainCourseMapper = CmTag.getBean(CetTrainCourseMapper.class);
            CetTrainCourse cetTrainCourse = cetTrainCourseMapper.selectByPrimaryKey(trainCourseId);
            CetTraineeService cetTraineeService = CmTag.getBean(CetTraineeService.class);
            CetTraineeView cetTraineeView = cetTraineeService.createIfNotExist(userId, cetTrainCourse.getTrainId());
            resultMap.put("traineeId", cetTraineeView.getId());
        }

        return resultMap;
    }

    private Integer id;

    private Integer projectId;

    private Integer userId;

    private Integer traineeTypeId;

    private Boolean isQuit;

    private String remark;

    private BigDecimal finishPeriod;

    private Integer cadreId;

    private String code;

    private String realname;

    private String title;

    private Integer typeId;

    private Integer postId;

    private Long cadreDpType;

    private Integer dpTypeId;

    private String proPost;

    private Date lpWorkTime;

    private String mobile;

    private String email;

    private Byte cadreStatus;

    private Integer cadreSortOrder;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getTraineeTypeId() {
        return traineeTypeId;
    }

    public void setTraineeTypeId(Integer traineeTypeId) {
        this.traineeTypeId = traineeTypeId;
    }

    public Boolean getIsQuit() {
        return isQuit;
    }

    public void setIsQuit(Boolean isQuit) {
        this.isQuit = isQuit;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public BigDecimal getFinishPeriod() {
        return finishPeriod;
    }

    public void setFinishPeriod(BigDecimal finishPeriod) {
        this.finishPeriod = finishPeriod;
    }

    public Integer getCadreId() {
        return cadreId;
    }

    public void setCadreId(Integer cadreId) {
        this.cadreId = cadreId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname == null ? null : realname.trim();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    public Long getCadreDpType() {
        return cadreDpType;
    }

    public void setCadreDpType(Long cadreDpType) {
        this.cadreDpType = cadreDpType;
    }

    public Integer getDpTypeId() {
        return dpTypeId;
    }

    public void setDpTypeId(Integer dpTypeId) {
        this.dpTypeId = dpTypeId;
    }

    public String getProPost() {
        return proPost;
    }

    public void setProPost(String proPost) {
        this.proPost = proPost == null ? null : proPost.trim();
    }

    public Date getLpWorkTime() {
        return lpWorkTime;
    }

    public void setLpWorkTime(Date lpWorkTime) {
        this.lpWorkTime = lpWorkTime;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public Byte getCadreStatus() {
        return cadreStatus;
    }

    public void setCadreStatus(Byte cadreStatus) {
        this.cadreStatus = cadreStatus;
    }

    public Integer getCadreSortOrder() {
        return cadreSortOrder;
    }

    public void setCadreSortOrder(Integer cadreSortOrder) {
        this.cadreSortOrder = cadreSortOrder;
    }
}