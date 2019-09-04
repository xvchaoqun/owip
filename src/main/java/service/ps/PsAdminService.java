package service.ps;

import domain.ps.PsAdmin;
import domain.ps.PsAdminExample;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.sys.SysUserService;
import sys.constants.PsConstants;
import sys.utils.DateUtils;

import java.util.Date;

@Service
public class PsAdminService extends PsBaseMapper {

    @Autowired
    private SysUserService sysUserService;
    public boolean idDuplicate(Integer id, int psId, Byte type){

        // 只判断二级党校管理员是否重复
        if (type == PsConstants.PS_ADMIN_TYPE_UNIT) return false;

        PsAdminExample example = new PsAdminExample();
        PsAdminExample.Criteria criteria = example.createCriteria()
                .andPsIdEqualTo(psId)
                .andTypeEqualTo(PsConstants.PS_ADMIN_TYPE_PARTY)
                .andIsHistoryEqualTo(false);

        if(id!=null) criteria.andIdNotEqualTo(id);

        return psAdminMapper.countByExample(example) > 0;
    }

    // 更新角色
    public void updateRole(int userId, byte type){

        if(iPsMapper.getAdminPsIds(userId, type).size()>0){

            if(type == PsConstants.PS_ADMIN_TYPE_PARTY) {
                sysUserService.addRole(userId, PsConstants.ROLE_PS_PARTY);
            }else if(type == PsConstants.PS_ADMIN_TYPE_UNIT) {
                sysUserService.addRole(userId, PsConstants.ROLE_PS_UNIT);
            }
        }else{
           if(type == PsConstants.PS_ADMIN_TYPE_PARTY) {
                sysUserService.delRole(userId, PsConstants.ROLE_PS_PARTY);
            }else if(type == PsConstants.PS_ADMIN_TYPE_UNIT) {
                sysUserService.delRole(userId, PsConstants.ROLE_PS_UNIT);
            }
        }
    }

    @Transactional
    public void insertSelective(PsAdmin record){

        record.setSortOrder(getNextSortOrder("ps_admin",
                "ps_id=" + record.getPsId() + " and is_history="+ record.getIsHistory()));
        psAdminMapper.insertSelective(record);

        updateRole(record.getUserId(), record.getType());
    }

    @Transactional
    public void del(Integer id){

        PsAdmin psAdmin = psAdminMapper.selectByPrimaryKey(id);
        psAdminMapper.deleteByPrimaryKey(id);

        updateRole(psAdmin.getUserId(), psAdmin.getType());
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        for (Integer id : ids) {
            del(id);
        }
    }

    @Transactional
    public void updateByPrimaryKeySelective(PsAdmin record){

        PsAdmin psAdmin = psAdminMapper.selectByPrimaryKey(record.getId());
        psAdminMapper.updateByPrimaryKeySelective(record);
        if(record.getUserId()!=null && record.getUserId().intValue() != psAdmin.getUserId()){
            // 如果更换了人员，需要更新权限
            updateRole(record.getUserId(), record.getType()==null?psAdmin.getType():record.getType());
            updateRole(psAdmin.getUserId(), psAdmin.getType());
        }
    }

    @Transactional
    public void changeOrder(Integer id,Integer addNum){

        PsAdmin psAdmin = psAdminMapper.selectByPrimaryKey(id);
        changeOrder("ps_admin", String.format("ps_id=%s and is_history=%s",
                psAdmin.getPsId(), psAdmin.getIsHistory()), ORDER_BY_ASC, id, addNum);
    }

    @Transactional
    public void updateAdminStatus(Integer[] ids, String _endDate, Boolean isHistory){

        if(ids==null || ids.length==0) return;
        Date endDate = null;
        if(StringUtils.isNotBlank(_endDate)){
            endDate = DateUtils.parseDate(_endDate,DateUtils.YYYYMMDD_DOT);
        }
        for (Integer id : ids){
            PsAdmin record = new PsAdmin();
            record.setId(id);
            record.setIsHistory(isHistory);
            record.setEndDate(endDate);
            psAdminMapper.updateByPrimaryKeySelective(record);

            PsAdmin psAdmin = psAdminMapper.selectByPrimaryKey(id);
            updateRole(psAdmin.getUserId(), psAdmin.getType());
        }
    }
}
