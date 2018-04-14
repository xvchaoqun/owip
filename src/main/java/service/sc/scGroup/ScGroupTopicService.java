package service.sc.scGroup;

import domain.sc.scGroup.ScGroupTopic;
import domain.sc.scGroup.ScGroupTopicExample;
import domain.sc.scGroup.ScGroupTopicUnit;
import domain.sc.scGroup.ScGroupTopicUnitExample;
import domain.unit.Unit;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import service.BaseMapper;
import service.unit.UnitService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class ScGroupTopicService extends BaseMapper {

    @Autowired
    private UnitService unitService;

    public boolean idDuplicate(Integer id, String code) {

        Assert.isTrue(StringUtils.isNotBlank(code), "null");

        ScGroupTopicExample example = new ScGroupTopicExample();
        ScGroupTopicExample.Criteria criteria = example.createCriteria();
        if (id != null) criteria.andIdNotEqualTo(id);

        return scGroupTopicMapper.countByExample(example) > 0;
    }

    @Transactional
    public void insertSelective(ScGroupTopic record, Integer[] unitIds) {

        record.setIsDeleted(false);
        scGroupTopicMapper.insertSelective(record);

        updateUnitIds(record.getId(), unitIds);
    }

    @Transactional
    public void del(Integer id) {

        scGroupTopicMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids) {

        if (ids == null || ids.length == 0) return;

        ScGroupTopicExample example = new ScGroupTopicExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        ScGroupTopic record = new ScGroupTopic();
        record.setIsDeleted(true);
        scGroupTopicMapper.updateByExampleSelective(record, example);
    }

    @Transactional
    public void updateByPrimaryKeySelective(ScGroupTopic record, Integer[] unitIds) {

        scGroupTopicMapper.updateByPrimaryKeySelective(record);
        updateUnitIds(record.getId(), unitIds);
    }

    public List<Unit> getUnits(Integer topicId) {

        Map<Integer, Unit> unitMap = unitService.findAll();

        List<Unit> units = new ArrayList<>();
        ScGroupTopicUnitExample example = new ScGroupTopicUnitExample();
        example.createCriteria().andTopicIdEqualTo(topicId);
        example.setOrderByClause("id asc");
        List<ScGroupTopicUnit> scGroupTopicUnits = scGroupTopicUnitMapper.selectByExample(example);
        for (ScGroupTopicUnit scGroupTopicUnit : scGroupTopicUnits) {
            Integer unitId = scGroupTopicUnit.getUnitId();
            Unit unit = unitMap.get(unitId);
            units.add(unit);
        }

        return units;
    }

    public void updateUnitIds(int topicId, Integer[] unitIds) {

        ScGroupTopicUnitExample example = new ScGroupTopicUnitExample();
        example.createCriteria().andTopicIdEqualTo(topicId);
        scGroupTopicUnitMapper.deleteByExample(example);

        if (unitIds != null && unitIds.length > 0) {
            for (Integer unitId : unitIds) {

                ScGroupTopicUnit _record = new ScGroupTopicUnit();
                _record.setTopicId(topicId);
                _record.setUnitId(unitId);
                scGroupTopicUnitMapper.insertSelective(_record);
            }
        }
    }
}
