package persistence.cet.common;

import domain.cet.CetProject;
import domain.cet.CetTrainObjViewExample;
import persistence.cet.CetTrainObjViewMapper;
import sys.tags.CmTag;

public class ICetProject extends CetProject {

    // 已选课数
    public long getCourseCount(){

        if(userId==null) return 0;

        CetTrainObjViewMapper cetTrainObjViewMapper = CmTag.getBean(CetTrainObjViewMapper.class);

        CetTrainObjViewExample example = new CetTrainObjViewExample();
        example.createCriteria().andProjectIdEqualTo(getId()).andUserIdEqualTo(userId);

        return cetTrainObjViewMapper.countByExample(example);
    }

    private Integer userId;

    public Integer getUserId() {
        return userId;
    }

    public ICetProject setUserId(Integer userId) {
        this.userId = userId;
        return this;
    }
}
