package domain.cet;

import domain.sys.SysUserView;
import persistence.cet.CetDiscussGroupMapper;
import persistence.cet.CetTrainCourseMapper;
import service.cet.CetDiscussGroupObjService;
import service.cet.CetPlanCourseObjService;
import service.cet.CetTraineeCourseService;
import service.cet.CetTraineeService;
import sys.tags.CmTag;
import sys.utils.ContextHelper;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CetProjectObjView implements Serializable {

    public Map getObjInfo() {

        HttpServletRequest request = ContextHelper.getRequest();
        if (request == null) return null;

        Map<String, Object> resultMap = new HashMap<>();

        Integer trainCourseId = (Integer) request.getAttribute("trainCourseId");
        Integer planCourseId = (Integer) request.getAttribute("planCourseId");
        Integer discussGroupId = (Integer) request.getAttribute("discussGroupId");
        if (trainCourseId == null && planCourseId == null && discussGroupId==null) return null;

        if (trainCourseId != null) {
            // 培训班选课页面
            CetTraineeCourseService cetTraineeCourseService = CmTag.getBean(CetTraineeCourseService.class);
            CetTraineeCourseView ctc = cetTraineeCourseService.getCetTraineeCourseView(userId, trainCourseId);

            if (ctc != null) {
                resultMap.put("canQuit", ctc.getCanQuit());
                resultMap.put("traineeId", ctc.getTraineeId());
                resultMap.put("isFinished", ctc.getIsFinished());
                resultMap.put("chooseTime", ctc.getChooseTime());
                resultMap.put("chooseUserId", ctc.getChooseUserId());
                resultMap.put("chooseUserName", ctc.getChooseUserName());
            } else {
                resultMap.put("canQuit", true);
                CetTrainCourseMapper cetTrainCourseMapper = CmTag.getBean(CetTrainCourseMapper.class);
                CetTrainCourse cetTrainCourse = cetTrainCourseMapper.selectByPrimaryKey(trainCourseId);
                CetTraineeService cetTraineeService = CmTag.getBean(CetTraineeService.class);
                CetTraineeView cetTraineeView = cetTraineeService.createIfNotExist(userId, cetTrainCourse.getTrainId());
                resultMap.put("traineeId", cetTraineeView.getId());
            }
        } else if (planCourseId != null) {
            // 培训方案选课页面(自主学习和上级专题班)
            CetPlanCourseObjService cetPlanCourseObjService = CmTag.getBean(CetPlanCourseObjService.class);
            CetPlanCourseObj cpo = cetPlanCourseObjService.getByUserId(userId, planCourseId);
            if (cpo != null) {
                resultMap.put("planCourseObjId", cpo.getId());
                BigDecimal period = BigDecimal.ZERO;
                int planCourseObjId = cpo.getId();
                List<CetPlanCourseObjResult> results = cetPlanCourseObjService.getResults(planCourseObjId);
                for (CetPlanCourseObjResult result : results) {
                    if (result.getPeriod() == null) continue;
                    period = period.add(result.getPeriod());
                }

                resultMap.put("period", period);

                resultMap.put("chooseTime", cpo.getChooseTime());
                if (cpo.getChooseUserId() != null) {

                    resultMap.put("note", cpo.getNote());

                    resultMap.put("isFinished", cpo.getIsFinished());
                    resultMap.put("chooseUserId", cpo.getChooseUserId());
                    SysUserView uv = CmTag.getUserById(cpo.getChooseUserId());
                    resultMap.put("chooseUserName", uv == null ? null : uv.getRealname());
                }
            }
        }else if(discussGroupId!=null){

            CetDiscussGroupMapper cetDiscussGroupMapper = CmTag.getBean(CetDiscussGroupMapper.class);
            CetDiscussGroup cetDiscussGroup = cetDiscussGroupMapper.selectByPrimaryKey(discussGroupId);
            Integer discussId = cetDiscussGroup.getDiscussId();

            CetDiscussGroupObjService cetDiscussGroupObjService = CmTag.getBean(CetDiscussGroupObjService.class);
            CetDiscussGroupObj cetDiscussGroupObj = cetDiscussGroupObjService.getByDiscussId(id, discussId);

            if(cetDiscussGroupObj!=null){
                // 当前所在分组
                resultMap.put("discussGroupId",  cetDiscussGroupObj.getDiscussGroupId());
                resultMap.put("isFinished",  cetDiscussGroupObj.getIsFinished());
            }
        }

        return resultMap;
    }

    private Integer id;

    private Integer projectId;

    private Integer userId;

    private Integer traineeTypeId;

    private Boolean isQuit;

    private BigDecimal shouldFinishPeriod;

    private BigDecimal finishPeriod;

    private Boolean isGraduate;

    private String wordWrite;

    private String writeFilePath;

    private String remark;

    private Integer cadreId;

    private String title;

    private Integer adminLevel;

    private Integer postType;

    private Boolean isOw;

    private Date owGrowTime;

    private Date dpGrowTime;

    private Integer dpTypeId;

    private String proPost;

    private Date lpWorkTime;

    private String mobile;

    private String email;

    private Byte cadreStatus;

    private Integer cadreSortOrder;

    private String username;

    private String code;

    private String realname;

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

    public BigDecimal getShouldFinishPeriod() {
        return shouldFinishPeriod;
    }

    public void setShouldFinishPeriod(BigDecimal shouldFinishPeriod) {
        this.shouldFinishPeriod = shouldFinishPeriod;
    }

    public BigDecimal getFinishPeriod() {
        return finishPeriod;
    }

    public void setFinishPeriod(BigDecimal finishPeriod) {
        this.finishPeriod = finishPeriod;
    }

    public Boolean getIsGraduate() {
        return isGraduate;
    }

    public void setIsGraduate(Boolean isGraduate) {
        this.isGraduate = isGraduate;
    }

    public String getWordWrite() {
        return wordWrite;
    }

    public void setWordWrite(String wordWrite) {
        this.wordWrite = wordWrite == null ? null : wordWrite.trim();
    }

    public String getWriteFilePath() {
        return writeFilePath;
    }

    public void setWriteFilePath(String writeFilePath) {
        this.writeFilePath = writeFilePath == null ? null : writeFilePath.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Integer getCadreId() {
        return cadreId;
    }

    public void setCadreId(Integer cadreId) {
        this.cadreId = cadreId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public Integer getAdminLevel() {
        return adminLevel;
    }

    public void setAdminLevel(Integer adminLevel) {
        this.adminLevel = adminLevel;
    }

    public Integer getPostType() {
        return postType;
    }

    public void setPostType(Integer postType) {
        this.postType = postType;
    }

    public Boolean getIsOw() {
        return isOw;
    }

    public void setIsOw(Boolean isOw) {
        this.isOw = isOw;
    }

    public Date getOwGrowTime() {
        return owGrowTime;
    }

    public void setOwGrowTime(Date owGrowTime) {
        this.owGrowTime = owGrowTime;
    }

    public Date getDpGrowTime() {
        return dpGrowTime;
    }

    public void setDpGrowTime(Date dpGrowTime) {
        this.dpGrowTime = dpGrowTime;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
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
}