package service.sc.scShift;

import domain.sc.scShift.ScShift;
import domain.sc.scShift.ScShiftExample;
import domain.unit.UnitPost;
import domain.unit.UnitPostView;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import service.BaseMapper;
import service.sc.ScBaseMapper;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class ScShiftService extends ScBaseMapper {

    public boolean idDuplicate(Integer id, String code){

        Assert.isTrue(StringUtils.isNotBlank(code), "null");

        ScShiftExample example = new ScShiftExample();
        ScShiftExample.Criteria criteria = example.createCriteria();
        if(id!=null) criteria.andIdNotEqualTo(id);

        return scShiftMapper.countByExample(example) > 0;
    }

    @Transactional
    public void insertSelective(ScShift record){

        UnitPostView unitPostView = iUnitMapper.getUnitPost(record.getAssignPostId());

        record.setUnitName(unitPostView.getUnitName());
        record.setIsPrincipal(unitPostView.getIsPrincipal());
        record.setLeaderType(unitPostView.getLeaderType());
        record.setAdminLevel(unitPostView.getAdminLevel());
        record.setPostType(unitPostView.getPostType());

        scShiftMapper.insertSelective(record);
    }

    @Transactional
    public void del(Integer id){

        scShiftMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        ScShiftExample example = new ScShiftExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        scShiftMapper.deleteByExample(example);
    }

    @Transactional
    public void updateByPrimaryKeySelective(ScShift record){

        scShiftMapper.updateByPrimaryKeySelective(record);
    }

    public Map<Integer, ScShift> findAll() {

        ScShiftExample example = new ScShiftExample();
        example.createCriteria();
        example.setOrderByClause("sort_order desc");
        List<ScShift> records = scShiftMapper.selectByExample(example);
        Map<Integer, ScShift> map = new LinkedHashMap<>();
        for (ScShift record : records) {
            map.put(record.getId(), record);
        }

        return map;
    }
}
