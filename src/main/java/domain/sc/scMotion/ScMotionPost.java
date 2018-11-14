package domain.sc.scMotion;

import domain.unit.UnitPostView;
import persistence.sc.scMotion.ScMotionMapper;
import service.unit.UnitPostService;
import sys.tags.CmTag;

import java.io.Serializable;
import java.util.Map;

public class ScMotionPost implements Serializable {

    private ScMotion motion;
    public ScMotion getMotion(){

        if(motion==null){
            ScMotionMapper scMotionMapper = CmTag.getBean(ScMotionMapper.class);
            motion = scMotionMapper.selectByPrimaryKey(motionId);
        }
        return motion;
    }

    public UnitPostView getUnitPost(){

        ScMotion motion = getMotion();
        UnitPostService unitPostService = CmTag.getBean(UnitPostService.class);
        Map<Integer, UnitPostView> unitPostMap = unitPostService.findAll(motion.getUnitId());
        return unitPostMap.get(unitPostId);
    }

    private Integer id;

    private Integer motionId;

    private Integer unitPostId;

    private String remark;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMotionId() {
        return motionId;
    }

    public void setMotionId(Integer motionId) {
        this.motionId = motionId;
    }

    public Integer getUnitPostId() {
        return unitPostId;
    }

    public void setUnitPostId(Integer unitPostId) {
        this.unitPostId = unitPostId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}