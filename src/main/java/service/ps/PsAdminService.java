package service.ps;

import domain.ps.PsAdmin;
import domain.ps.PsAdminExample;
import domain.ps.PsMember;
import domain.ps.PsMemberExample;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.sys.SysUserService;
import sys.constants.PsInfoConstants;
import sys.utils.DateUtils;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class PsAdminService extends PsBaseMapper {

    @Autowired
    private SysUserService sysUserService;
    public boolean idDuplicate(Integer id, int userId){


        PsAdminExample example = new PsAdminExample();
        PsAdminExample.Criteria criteria = example.createCriteria()
                .andUserIdEqualTo(userId);
        if(id!=null) criteria.andIdNotEqualTo(id);

        return psAdminMapper.countByExample(example) > 0;
    }

    @Transactional
    public void insertSelective(PsAdmin record){
        record.setSortOrder(getNextSortOrder("ps_admin", "ps_id=" + record.getPsId()));
        sysUserService.addRole(record.getUserId(), PsInfoConstants.ROLE_PS_ADMIN);
        psAdminMapper.insertSelective(record);
    }

    @Transactional
    public void del(Integer id){

        psAdminMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        PsAdminExample example = new PsAdminExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        psAdminMapper.deleteByExample(example);
    }

    @Transactional
    public void updateByPrimaryKeySelective(PsAdmin record){
        psAdminMapper.updateByPrimaryKeySelective(record);
    }

    public void changeOrder(Integer id,Integer addNum){
        if (addNum == 0) return;

        byte orderBy = ORDER_BY_ASC;

        PsAdmin psAdmin = psAdminMapper.selectByPrimaryKey(id);
        Integer baseSortOrder = psAdmin.getSortOrder();

        PsAdminExample example = new PsAdminExample();
        if (addNum * orderBy > 0) {

            example.createCriteria().andPsIdEqualTo(psAdmin.getPsId()).andIsHistoryEqualTo(psAdmin.getIsHistory())
                    .andSortOrderGreaterThan(baseSortOrder);
            example.setOrderByClause("sort_order asc");
        } else {

            example.createCriteria().andPsIdEqualTo(psAdmin.getPsId()).andIsHistoryEqualTo(psAdmin.getIsHistory())
                    .andSortOrderLessThan(baseSortOrder);
            example.setOrderByClause("sort_order desc");
        }

        List<PsAdmin> overEntities = psAdminMapper.selectByExampleWithRowbounds(example, new RowBounds(0, Math.abs(addNum)));
        if (overEntities.size() > 0) {

            PsAdmin targetEntity = overEntities.get(overEntities.size() - 1);

            if (addNum * orderBy > 0)
                commonMapper.downOrder("ps_admin", String.format("ps_id=%s and is_history=%s", psAdmin.getPsId(), psAdmin.getIsHistory()),
                        baseSortOrder, targetEntity.getSortOrder());
            else
                commonMapper.upOrder("ps_admin", String.format("ps_id=%s and is_history=%s", psAdmin.getPsId(), psAdmin.getIsHistory()),
                        baseSortOrder, targetEntity.getSortOrder());

            PsAdmin record = new PsAdmin();
            record.setId(id);
            record.setSortOrder(targetEntity.getSortOrder());
            psAdminMapper.updateByPrimaryKeySelective(record);
    }

}

    public void updateAdminStatus(Integer[] ids, String _endDate, Boolean isHistory){
        if(ids==null || ids.length==0) return;
        Date endDate = null;
        if(StringUtils.isNotBlank(_endDate)){
            endDate = DateUtils.parseDate(_endDate,DateUtils.YYYYMMDD_DOT);
        }
        for (Integer id : ids){
            PsAdmin psAdmin = new PsAdmin();
            psAdmin.setId(id);
            psAdmin.setIsHistory(isHistory);
            psAdmin.setEndDate(endDate);
            psAdminMapper.updateByPrimaryKeySelective(psAdmin);
        }
    }
}
