package service.abroad;

import bean.ApproverTypeBean;
import domain.abroad.Approver;
import domain.abroad.ApproverExample;
import domain.cadre.CadreView;
import org.apache.ibatis.session.RowBounds;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import service.BaseMapper;
import sys.constants.CadreConstants;
import sys.tags.CmTag;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class ApproverService extends BaseMapper {

    // 判断一个用户（非干部管理员）是否有因私出国境审批权限
    public boolean hasApproveAuth(int userId){

        ApproverTypeBean approverTypeBean = CmTag.getApproverTypeBean(userId);
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
    public List<Approver> findByType(int typeId){

        ApproverExample example = new ApproverExample();
        example.createCriteria().andTypeIdEqualTo(typeId);

        return approverMapper.selectByExample(example);
    }

    public boolean idDuplicate(Integer id, int cadreId){

        ApproverExample example = new ApproverExample();
        ApproverExample.Criteria criteria = example.createCriteria().andCadreIdEqualTo(cadreId);
        if(id!=null) criteria.andIdNotEqualTo(id);

        return approverMapper.countByExample(example) > 0;
    }

    @Transactional
    @CacheEvict(value="Approver:ALL", allEntries = true)
    public int insertSelective(Approver record){

        Assert.isTrue(!idDuplicate(null, record.getCadreId()), "duplicate cadreId");
        record.setSortOrder(getNextSortOrder("abroad_approver", "1=1"));
        return approverMapper.insertSelective(record);
    }
    @Transactional
    @CacheEvict(value="Approver:ALL", allEntries = true)
    public void del(Integer id){

        approverMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    @CacheEvict(value="Approver:ALL", allEntries = true)
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        ApproverExample example = new ApproverExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        approverMapper.deleteByExample(example);
    }

    @Transactional
    @CacheEvict(value="Approver:ALL", allEntries = true)
    public int updateByPrimaryKeySelective(Approver record){
        if(record.getCadreId()!=null)
            Assert.isTrue(!idDuplicate(record.getId(), record.getCadreId()), "duplicate cadreId");
        return approverMapper.updateByPrimaryKeySelective(record);
    }

    @Cacheable(value="Approver:ALL")
    public Map<Integer, Approver> findAll() {

        ApproverExample example = new ApproverExample();
        example.setOrderByClause("sort_order desc");
        List<Approver> approveres = approverMapper.selectByExample(example);
        Map<Integer, Approver> map = new LinkedHashMap<>();
        for (Approver approver : approveres) {
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
    @CacheEvict(value = "Approver:ALL", allEntries = true)
    public void changeOrder(int id, int addNum) {

        if(addNum == 0) return ;

        Approver entity = approverMapper.selectByPrimaryKey(id);
        Integer baseSortOrder = entity.getSortOrder();

        ApproverExample example = new ApproverExample();
        if (addNum > 0) {

            example.createCriteria().andSortOrderGreaterThan(baseSortOrder);
            example.setOrderByClause("sort_order asc");
        }else {

            example.createCriteria().andSortOrderLessThan(baseSortOrder);
            example.setOrderByClause("sort_order desc");
        }

        List<Approver> overEntities = approverMapper.selectByExampleWithRowbounds(example, new RowBounds(0, Math.abs(addNum)));
        if(overEntities.size()>0) {

            Approver targetEntity = overEntities.get(overEntities.size()-1);

            if (addNum > 0)
                commonMapper.downOrder("abroad_approver", null, baseSortOrder, targetEntity.getSortOrder());
            else
                commonMapper.upOrder("abroad_approver", null, baseSortOrder, targetEntity.getSortOrder());

            Approver record = new Approver();
            record.setId(id);
            record.setSortOrder(targetEntity.getSortOrder());
            approverMapper.updateByPrimaryKeySelective(record);
        }
    }
}
