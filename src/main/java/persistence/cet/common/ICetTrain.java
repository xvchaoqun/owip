package persistence.cet.common;

import domain.cet.CetTrain;
import domain.cet.CetTrainObjViewExample;
import persistence.cet.CetTrainObjViewMapper;
import sys.tags.CmTag;

/**
 * Created by lm on 2018/3/14.
 */
public class ICetTrain extends CetTrain {

    // 已选课数
    public long getCourseCount(){

        if(userId==null) return 0;

        CetTrainObjViewMapper cetTrainObjViewMapper = CmTag.getBean(CetTrainObjViewMapper.class);

        CetTrainObjViewExample example = new CetTrainObjViewExample();
        example.createCriteria().andTrainIdEqualTo(getId()).andUserIdEqualTo(userId);

        return cetTrainObjViewMapper.countByExample(example);
    }

    private Integer userId;
    private Integer projectId;
    private String projectName;

    public Integer getUserId() {
        return userId;
    }

    public ICetTrain setUserId(Integer userId) {
        this.userId = userId;
        return this;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public ICetTrain setProjectId(Integer projectId) {
        this.projectId = projectId;
        return this;
    }

    public String getProjectName() {
        return projectName;
    }

    public ICetTrain setProjectName(String projectName) {
        this.projectName = projectName;
        return this;
    }
}
