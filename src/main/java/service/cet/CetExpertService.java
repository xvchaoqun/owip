package service.cet;

import domain.cet.CetExpert;
import domain.cet.CetExpertExample;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import sys.constants.CetConstants;

import java.util.Arrays;
import java.util.List;

@Service
public class CetExpertService extends CetBaseMapper {

    public boolean idDuplicate(Integer id, int userId){


        CetExpertExample example = new CetExpertExample();
        CetExpertExample.Criteria criteria = example.createCriteria().andUserIdEqualTo(userId);
        if(id!=null) criteria.andIdNotEqualTo(id);

        return cetExpertMapper.countByExample(example) > 0;
    }

    public boolean idDuplicate(Integer id, String code){

        Assert.isTrue(StringUtils.isNotBlank(code), "null");

        CetExpertExample example = new CetExpertExample();
        CetExpertExample.Criteria criteria = example.createCriteria().andCodeEqualTo(code);
        if(id!=null) criteria.andIdNotEqualTo(id);

        return cetExpertMapper.countByExample(example) > 0;
    }

    // 获取校内专家
    public CetExpert getByUserId(int userId){

        CetExpertExample example = new CetExpertExample();
        CetExpertExample.Criteria criteria =
                example.createCriteria().andUserIdEqualTo(userId)
                        .andTypeEqualTo(CetConstants.CET_EXPERT_TYPE_IN);

        List<CetExpert> cetExperts = cetExpertMapper.selectByExampleWithRowbounds(example, new RowBounds(0, 1));
        return cetExperts.size()==1?cetExperts.get(0):null;
    }

    // 获取校外专家
    public CetExpert getByCode(String code){

        CetExpertExample example = new CetExpertExample();
        CetExpertExample.Criteria criteria =
                example.createCriteria().andCodeEqualTo(code)
                        .andTypeEqualTo(CetConstants.CET_EXPERT_TYPE_OUT);

        List<CetExpert> cetExperts = cetExpertMapper.selectByExampleWithRowbounds(example, new RowBounds(0, 1));
        return cetExperts.size()==1?cetExperts.get(0):null;
    }

    @Transactional
    public void insertSelective(CetExpert record){

        record.setSortOrder(getNextSortOrder("cet_expert", null));
        cetExpertMapper.insertSelective(record);
    }

    @Transactional
    public int bacthImport(List<CetExpert> records) {

        int addCount = 0;
        for (CetExpert record : records) {

            CetExpert cetExpert = null;
            if(record.getType()==CetConstants.CET_EXPERT_TYPE_IN){
                cetExpert = getByUserId(record.getUserId());
            }else{
                cetExpert = getByCode(record.getCode());
            }

            if(cetExpert==null){
                insertSelective(record);
                addCount++;
            }else{
                record.setId(cetExpert.getId());
                updateByPrimaryKeySelective(record);
            }
        }

        return addCount;
    }

    @Transactional
    public void del(Integer id){

        cetExpertMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        CetExpertExample example = new CetExpertExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        cetExpertMapper.deleteByExample(example);
    }

    @Transactional
    public void updateByPrimaryKeySelective(CetExpert record){

        cetExpertMapper.updateByPrimaryKeySelective(record);

        if(record.getType()== CetConstants.CET_EXPERT_TYPE_IN){
            commonMapper.excuteSql("update cet_expert set code = null where id="+ record.getId());
        }else{
            commonMapper.excuteSql("update cet_expert set user_id = null where id="+ record.getId());
        }
    }


    /**
     * 排序 ，要求 1、sort_order>0且不可重复  2、sort_order 降序排序
     * @param id
     * @param addNum
     */
    @Transactional
    public void changeOrder(int id, int addNum) {

        if(addNum == 0) return ;

        CetExpert entity = cetExpertMapper.selectByPrimaryKey(id);
        Integer baseSortOrder = entity.getSortOrder();

        CetExpertExample example = new CetExpertExample();
        if (addNum > 0) {

            example.createCriteria().andSortOrderGreaterThan(baseSortOrder);
            example.setOrderByClause("sort_order asc");
        }else {

            example.createCriteria().andSortOrderLessThan(baseSortOrder);
            example.setOrderByClause("sort_order desc");
        }

        List<CetExpert> overEntities = cetExpertMapper.selectByExampleWithRowbounds(example, new RowBounds(0, Math.abs(addNum)));
        if(overEntities.size()>0) {

            CetExpert targetEntity = overEntities.get(overEntities.size()-1);

            if (addNum > 0)
                commonMapper.downOrder("cet_expert", null, baseSortOrder, targetEntity.getSortOrder());
            else
                commonMapper.upOrder("cet_expert", null, baseSortOrder, targetEntity.getSortOrder());

            CetExpert record = new CetExpert();
            record.setId(id);
            record.setSortOrder(targetEntity.getSortOrder());
            cetExpertMapper.updateByPrimaryKeySelective(record);
        }
    }
}
