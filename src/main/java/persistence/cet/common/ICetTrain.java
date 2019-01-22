package persistence.cet.common;

import domain.cet.*;
import persistence.cet.CetTraineeViewMapper;
import sys.tags.CmTag;

import java.util.List;

/**
 * Created by lm on 2018/3/14.
 */
public class ICetTrain extends CetTrainView {

    public CetProject getCetProject(){
        ICetMapper iCetMapper = CmTag.getBean(ICetMapper.class);
        return iCetMapper.getCetProject(getId());
    }

    public Integer getCourseCount() {

        CetTraineeViewMapper cetTraineeViewMapper = CmTag.getBean(CetTraineeViewMapper.class);

        CetTraineeViewExample example = new CetTraineeViewExample();
        example.createCriteria().andObjIdEqualTo(objId).andTrainIdEqualTo(getId());
        List<CetTraineeView> cetTraineeViews = cetTraineeViewMapper.selectByExample(example);

        return cetTraineeViews.size()==0?null:cetTraineeViews.get(0).getCourseCount();
    }

    private Integer objId;

    public Integer getObjId() {
        return objId;
    }

    public void setObjId(Integer objId) {
        this.objId = objId;
    }
}
