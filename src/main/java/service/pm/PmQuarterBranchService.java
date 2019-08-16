package service.pm;

import domain.member.Member;
import domain.party.Branch;
import domain.party.BranchExample;
import domain.pm.PmExcludeBranch;
import domain.pm.PmExcludeBranchExample;
import domain.pm.PmQuarterBranch;
import domain.pm.PmQuarterBranchExample;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import persistence.party.BranchMapper;
import service.BaseMapper;
import shiro.ShiroHelper;
import sys.constants.PmConstants;

import java.util.*;

@Service
public class PmQuarterBranchService extends PmBaseMapper {

    public boolean idDuplicate(Integer id, String code){

        Assert.isTrue(StringUtils.isNotBlank(code), "null");

        PmQuarterBranchExample example = new PmQuarterBranchExample();
        PmQuarterBranchExample.Criteria criteria = example.createCriteria();
        if(id!=null) criteria.andIdNotEqualTo(id);

        return pmQuarterBranchMapper.countByExample(example) > 0;
    }

    @Transactional
    public void insertSelective(Integer quarterId,Integer partyId){

        PmQuarterBranch record=new PmQuarterBranch();

        BranchExample example = new BranchExample();
        example.createCriteria().andPartyIdEqualTo(partyId).andIsDeletedEqualTo(false);
        List<Branch> branchs= branchMapper.selectByExample(example);

        PmExcludeBranchExample pmExcludeBranchexample = new PmExcludeBranchExample();
        example.createCriteria();
        List<PmExcludeBranch> pmExcludeBranchs=pmExcludeBranchMapper.selectByExample(pmExcludeBranchexample);

        for(Branch branch :branchs){

            record.setQuarterId(quarterId);
            record.setPartyId(partyId);
            record.setBranchId(branch.getId());
            record.setBranchName(branch.getName());

            for(PmExcludeBranch pmExcludeBranch :pmExcludeBranchs){
               if(pmExcludeBranch.getPartyId()==branch.getPartyId()&&pmExcludeBranch.getBranchId()==branch.getId()){
                   record.setIsExclude(true);
               }else{
                   record.setIsExclude(false);
               }
            }

            pmQuarterBranchMapper.insertSelective(record);
        }

        //record.setNum();
        //record.setDueNum();
        //record.setFinishNum();

    }

    @Transactional
    @CacheEvict(value="PmQuarterBranch:ALL", allEntries = true)
    public void del(Integer id){

        pmQuarterBranchMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    @CacheEvict(value="PmQuarterBranch:ALL", allEntries = true)
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        PmQuarterBranchExample example = new PmQuarterBranchExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        pmQuarterBranchMapper.deleteByExample(example);
    }

    @Transactional
    public void updateByPrimaryKeySelective(PmQuarterBranch record){

        pmQuarterBranchMapper.updateByPrimaryKeySelective(record);
    }

    @Cacheable(value="PmQuarterBranch:ALL")
    public Map<Integer, PmQuarterBranch> findAll() {

        PmQuarterBranchExample example = new PmQuarterBranchExample();
        example.createCriteria();
        example.setOrderByClause("sort_order desc");
        List<PmQuarterBranch> records = pmQuarterBranchMapper.selectByExample(example);
        Map<Integer, PmQuarterBranch> map = new LinkedHashMap<>();
        for (PmQuarterBranch record : records) {
            map.put(record.getId(), record);
        }

        return map;
    }

    /**
     * 排序 ，要求 1、sort_order>0且不可重复  2、sort_order 降序排序
     * @param id
     * @param addNum
     */
    @Transactional
    @CacheEvict(value = "PmQuarterBranch:ALL", allEntries = true)
    public void changeOrder(int id, int addNum) {

        changeOrder("pm_quarter_branch", null, ORDER_BY_DESC, id, addNum);
    }
}
