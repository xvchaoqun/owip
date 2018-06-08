package service.cla;

import domain.cadre.CadreView;
import domain.cla.ClaApprover;
import domain.cla.ClaApproverExample;
import org.apache.ibatis.session.RowBounds;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import persistence.cla.common.ClaApproverTypeBean;
import service.BaseMapper;
import sys.constants.CadreConstants;
import sys.tags.CmTag;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class ClaApproverService extends BaseMapper {

    // 判断一个用户（非干部管理员）是否有干部请假审批权限
    public boolean hasApproveAuth(int userId){

        ClaApproverTypeBean approverTypeBean = CmTag.getClaApproverTypeBean(userId);
        if(approverTypeBean==null) return false;
        CadreView cadre = approverTypeBean.getCadre();
        if ((cadre.getStatus() != CadreConstants.CADRE_STATUS_MIDDLE
                && cadre.getStatus() != CadreConstants.CADRE_STATUS_LEADER) ||
                !(approverTypeBean.getMainPostUnitIds().size()>0
                        || approverTypeBean.isManagerLeader()
                        || approverTypeBean.isApprover())) {
            return false;
        }

        return true;
    }

    // 根据审批人身份类型查找审批人
    public List<ClaApprover> findByType(int typeId){

        ClaApproverExample example = new ClaApproverExample();
        example.createCriteria().andTypeIdEqualTo(typeId);

        return claApproverMapper.selectByExample(example);
    }

    public boolean idDuplicate(Integer id, int cadreId){

        ClaApproverExample example = new ClaApproverExample();
        ClaApproverExample.Criteria criteria = example.createCriteria().andCadreIdEqualTo(cadreId);
        if(id!=null) criteria.andIdNotEqualTo(id);

        return claApproverMapper.countByExample(example) > 0;
    }

    @Transactional
    @CacheEvict(value="ClaApprover:ALL", allEntries = true)
    public int insertSelective(ClaApprover record){

        Assert.isTrue(!idDuplicate(null, record.getCadreId()), "duplicate cadreId");
        record.setSortOrder(getNextSortOrder("cla_approver", null));
        return claApproverMapper.insertSelective(record);
    }
    @Transactional
    @CacheEvict(value="ClaApprover:ALL", allEntries = true)
    public void del(Integer id){

        claApproverMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    @CacheEvict(value="ClaApprover:ALL", allEntries = true)
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        ClaApproverExample example = new ClaApproverExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        claApproverMapper.deleteByExample(example);
    }

    @Transactional
    @CacheEvict(value="ClaApprover:ALL", allEntries = true)
    public int updateByPrimaryKeySelective(ClaApprover record){
        if(record.getCadreId()!=null)
            Assert.isTrue(!idDuplicate(record.getId(), record.getCadreId()), "duplicate cadreId");
        return claApproverMapper.updateByPrimaryKeySelective(record);
    }

    @Cacheable(value="ClaApprover:ALL")
    public Map<Integer, ClaApprover> findAll() {

        ClaApproverExample example = new ClaApproverExample();
        example.setOrderByClause("sort_order desc");
        List<ClaApprover> approveres = claApproverMapper.selectByExample(example);
        Map<Integer, ClaApprover> map = new LinkedHashMap<>();
        for (ClaApprover approver : approveres) {
            map.put(approver.getId(), approver);
        }

        return map;
    }

    /**
     * 排序 ，要求 1、sort_order>0且不可重复  2、sort_order 降序排序
     * @param id
     * @param addNum
     */
    @Transactional
    @CacheEvict(value = "ClaApprover:ALL", allEntries = true)
    public void changeOrder(int id, int addNum) {

        if(addNum == 0) return ;

        ClaApprover entity = claApproverMapper.selectByPrimaryKey(id);
        Integer baseSortOrder = entity.getSortOrder();

        ClaApproverExample example = new ClaApproverExample();
        if (addNum > 0) {

            example.createCriteria().andSortOrderGreaterThan(baseSortOrder);
            example.setOrderByClause("sort_order asc");
        }else {

            example.createCriteria().andSortOrderLessThan(baseSortOrder);
            example.setOrderByClause("sort_order desc");
        }

        List<ClaApprover> overEntities = claApproverMapper.selectByExampleWithRowbounds(example, new RowBounds(0, Math.abs(addNum)));
        if(overEntities.size()>0) {

            ClaApprover targetEntity = overEntities.get(overEntities.size()-1);

            if (addNum > 0)
                commonMapper.downOrder("cla_approver", null, baseSortOrder, targetEntity.getSortOrder());
            else
                commonMapper.upOrder("cla_approver", null, baseSortOrder, targetEntity.getSortOrder());

            ClaApprover record = new ClaApprover();
            record.setId(id);
            record.setSortOrder(targetEntity.getSortOrder());
            claApproverMapper.updateByPrimaryKeySelective(record);
        }
    }
}
