package persistence.cet.common;

import domain.cet.CetProject;
import domain.cet.CetTrain;
import sys.tags.CmTag;

/**
 * Created by lm on 2018/3/14.
 */
public class ICetTrain extends CetTrain {

    public CetProject getCetProject(){
        ICetMapper iCetMapper = CmTag.getBean(ICetMapper.class);
        return iCetMapper.getCetProject(getId());
    }

    private Integer userId;
    // 已选课数量
    private Integer courseCount;

    public Integer getUserId() {
        return userId;
    }

    public ICetTrain setUserId(Integer userId) {
        this.userId = userId;
        return this;
    }

    public Integer getCourseCount() {
        return courseCount;
    }

    public ICetTrain setCourseCount(Integer courseCount) {
        this.courseCount = courseCount;
        return this;
    }
}
