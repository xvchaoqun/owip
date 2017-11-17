package service.pmd;

import controller.global.OpException;
import domain.pmd.PmdBranch;
import domain.pmd.PmdBranchExample;
import domain.pmd.PmdMemberExample;
import domain.pmd.PmdMonth;
import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import persistence.common.bean.PmdReportBean;
import service.BaseMapper;
import shiro.ShiroHelper;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class PmdBranchService extends BaseMapper {

    @Autowired
    private PmdMonthService pmdMonthService;
    @Autowired
    private PmdBranchAdminService pmdBranchAdminService;

    // 党支部报送
    @Transactional
    public void report(int id) {

        PmdBranch pmdBranch = pmdBranchMapper.selectByPrimaryKey(id);
        if(pmdBranch==null) return;

        int monthId = pmdBranch.getMonthId();
        int partyId = pmdBranch.getPartyId();
        int branchId = pmdBranch.getBranchId();

        /*List<Integer> adminBranchIds = pmdBranchAdminService.getAdminBranchIds(ShiroHelper.getCurrentUserId());
        Set<Integer> adminBranchIdSet = new HashSet<>();
        adminBranchIdSet.addAll(adminBranchIds);
        if(!adminBranchIdSet.contains(branchId)){
            throw new UnauthorizedException();
        }*/

        if(!canReport(monthId, partyId, branchId)){
            throw new OpException("当前不允许报送。");
        }
        PmdBranch record = new PmdBranch();
        record.setHasReport(true);
        record.setReportUserId(ShiroHelper.getCurrentUserId());
        record.setReportTime(new Date());
        //record.setReportIp(ContextHelper.getRealIp());

        // 保存数据汇总
        PmdReportBean r = iPmdMapper.getBranchPmdReportBean(monthId, partyId, branchId);
        try {
            PropertyUtils.copyProperties(record, r);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        PmdBranchExample example = new PmdBranchExample();
        example.createCriteria().andIdEqualTo(id)
                .andHasReportEqualTo(false);
        pmdBranchMapper.updateByExampleSelective(record, example);
    }

    // 判断支部是否可以报送
    public boolean canReport(int monthId, int parytId, int branchId){

        PmdMonth currentPmdMonth = pmdMonthService.getCurrentPmdMonth();
        if(currentPmdMonth==null || currentPmdMonth.getId()!=monthId) return false;

        PmdBranch pmdBranch = get(monthId, parytId, branchId);
        if(pmdBranch==null) return false;

        // 如果存在 没有支付且没有设置为延迟缴费， 则不可报送
        PmdMemberExample example = new PmdMemberExample();
        example.createCriteria().andMonthIdEqualTo(monthId)
                .andPartyIdEqualTo(parytId)
                .andBranchIdEqualTo(branchId)
                .andHasPayEqualTo(false)
                .andIsDelayEqualTo(false);

        return pmdMemberMapper.countByExample(example)==0;
    }

    public PmdBranch get(int monthId, int parytId, int branchId){

        PmdBranchExample example = new PmdBranchExample();
        example.createCriteria().andMonthIdEqualTo(monthId)
                .andPartyIdEqualTo(parytId)
                .andBranchIdEqualTo(branchId);
        List<PmdBranch> pmdBranches = pmdBranchMapper.selectByExample(example);
        return pmdBranches.size()==0?null:pmdBranches.get(0);
    }

    @Transactional
    public void insertSelective(PmdBranch record) {

        pmdBranchMapper.insertSelective(record);
    }

    @Transactional
    public void del(Integer id) {

        pmdBranchMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids) {

        if (ids == null || ids.length == 0) return;

        PmdBranchExample example = new PmdBranchExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        pmdBranchMapper.deleteByExample(example);
    }

    @Transactional
    public int updateByPrimaryKeySelective(PmdBranch record) {
        return pmdBranchMapper.updateByPrimaryKeySelective(record);
    }
}
