package service.pmd;

import domain.pmd.PmdFee;
import domain.pmd.PmdFeeExample;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shiro.ShiroHelper;
import sys.constants.SystemConstants;

import java.util.*;

@Service
public class PmdFeeService extends PmdBaseMapper {
    @Autowired
    private PmdPartyAdminService pmdPartyAdminService;
    @Autowired
    private PmdBranchAdminService pmdBranchAdminService;

    public boolean idDuplicate(Integer id, Integer userId, Date payMonth){

        PmdFeeExample example = new PmdFeeExample();
        PmdFeeExample.Criteria criteria = example.createCriteria();
        if(id!=null) criteria.andIdNotEqualTo(id);
        criteria.andUserIdEqualTo(userId).andPayMonthEqualTo(payMonth);

        return pmdFeeMapper.countByExample(example) > 0;
    }

    @Transactional
    public void insertSelective(PmdFee record){

        Integer loginUserId = ShiroHelper.getCurrentUserId();
        if(!ShiroHelper.isPermitted(SystemConstants.PERMISSION_PARTYVIEWALL)&&
                !pmdPartyAdminService.isPartyAdmin(loginUserId,record.getPartyId())&&
                !pmdBranchAdminService.isBranchAdmin(loginUserId,record.getBranchId())){
            throw new UnauthorizedException();
        }

        pmdFeeMapper.insertSelective(record);
    }

    @Transactional
    public void del(Integer id){

        pmdFeeMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        PmdFeeExample example = new PmdFeeExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        pmdFeeMapper.deleteByExample(example);
    }

    @Transactional
    public void updateByPrimaryKeySelective(PmdFee record){

        Integer loginUserId = ShiroHelper.getCurrentUserId();
        if(!ShiroHelper.isPermitted(SystemConstants.PERMISSION_PARTYVIEWALL)&&
                !pmdPartyAdminService.isPartyAdmin(loginUserId,record.getPartyId())&&
                !pmdBranchAdminService.isBranchAdmin(loginUserId,record.getBranchId())){
            throw new UnauthorizedException();
        }

        pmdFeeMapper.updateByPrimaryKeySelective(record);
    }

    public Map<Integer, PmdFee> findAll() {

        PmdFeeExample example = new PmdFeeExample();
        example.createCriteria().andStatusEqualTo((byte)1);
        example.setOrderByClause("sort_order desc");
        List<PmdFee> records = pmdFeeMapper.selectByExample(example);
        Map<Integer, PmdFee> map = new LinkedHashMap<>();
        for (PmdFee record : records) {
            map.put(record.getId(), record);
        }

        return map;
    }
}
