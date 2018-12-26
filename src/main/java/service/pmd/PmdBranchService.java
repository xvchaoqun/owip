package service.pmd;

import controller.global.OpException;
import domain.pmd.*;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import persistence.pmd.common.PmdReportBean;
import shiro.ShiroHelper;
import sys.constants.RoleConstants;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.List;

@Service
public class PmdBranchService extends PmdBaseMapper {

    @Autowired
    private PmdMonthService pmdMonthService;
    @Autowired
    private PmdPartyService pmdPartyService;
    @Autowired
    private PmdBranchAdminService pmdBranchAdminService;
    @Autowired
    private PmdPartyAdminService pmdPartyAdminService;

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
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }

        PmdBranchExample example = new PmdBranchExample();
        example.createCriteria().andIdEqualTo(id)
                .andHasReportEqualTo(false);
        pmdBranchMapper.updateByExampleSelective(record, example);
    }
    // 更新党支部报送
    @Transactional
    public void updateReport(int id) {

        PmdBranch pmdBranch = pmdBranchMapper.selectByPrimaryKey(id);
        if(pmdBranch==null) return;

        int monthId = pmdBranch.getMonthId();
        int partyId = pmdBranch.getPartyId();
        int branchId = pmdBranch.getBranchId();

        PmdBranch record = new PmdBranch();
        record.setId(id);
        
        // 保存数据汇总
        PmdReportBean r = iPmdMapper.getBranchPmdReportBean(monthId, partyId, branchId);
        try {
            PropertyUtils.copyProperties(record, r);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }

        pmdBranchMapper.updateByPrimaryKeySelective(record);
    }

    // 撤销党支部报送
    @Transactional
    public void unreport(int pmdBranchId) {

        PmdBranch pmdBranch = pmdBranchMapper.selectByPrimaryKey(pmdBranchId);
        int partyId = pmdBranch.getPartyId();
        int branchId = pmdBranch.getBranchId();
        // 组织部管理员、分党委管理员、党支部管理员允许报送
        if(ShiroHelper.lackRole(RoleConstants.ROLE_PMD_OW)) {
            if (!pmdPartyAdminService.isPartyAdmin(ShiroHelper.getCurrentUserId(), partyId)) {
                if (!pmdBranchAdminService.isBranchAdmin(ShiroHelper.getCurrentUserId(), partyId, branchId)) {
                    throw new UnauthorizedException();
                }
            }
        }
        int monthId = pmdBranch.getMonthId();
        PmdMonth currentPmdMonth = pmdMonthService.getCurrentPmdMonth();
        if(currentPmdMonth==null || currentPmdMonth.getId()!=monthId){
            throw new OpException("不允许撤销报送。");
        }
        PmdParty pmdParty = pmdPartyService.get(monthId, partyId);
        if(BooleanUtils.isTrue(pmdParty.getHasReport())){
            throw new OpException("党委已报送，不允许撤销报送。");
        }

        PmdBranch record = new PmdBranch();
        record.setId(pmdBranchId);
        record.setHasReport(false);

        pmdBranchMapper.updateByPrimaryKeySelective(record);
    }

    // 判断报送权限
    public boolean canReport(int monthId, int partyId, int branchId){

        PmdMonth currentPmdMonth = pmdMonthService.getCurrentPmdMonth();
        if(currentPmdMonth==null || currentPmdMonth.getId()!=monthId) return false;

        PmdBranch pmdBranch = get(monthId, partyId, branchId);
        if(pmdBranch==null) return false;

        // 组织部管理员、分党委管理员、党支部管理员允许报送
        if(ShiroHelper.lackRole(RoleConstants.ROLE_PMD_OW)) {
            if (!pmdPartyAdminService.isPartyAdmin(ShiroHelper.getCurrentUserId(), partyId)) {
                if (!pmdBranchAdminService.isBranchAdmin(ShiroHelper.getCurrentUserId(), partyId, branchId)) {
                    return false;
                }
            }
        }

        // 如果存在 没有支付且没有设置为延迟缴费， 则不可报送
        PmdMemberExample example = new PmdMemberExample();
        example.createCriteria().andMonthIdEqualTo(monthId)
                .andPartyIdEqualTo(partyId)
                .andBranchIdEqualTo(branchId)
                .andHasPayEqualTo(false)
                .andIsDelayEqualTo(false);

        return pmdMemberMapper.countByExample(example)==0;
    }

    public PmdBranch get(int monthId, int partyId, int branchId){

        PmdBranchExample example = new PmdBranchExample();
        example.createCriteria().andMonthIdEqualTo(monthId)
                .andPartyIdEqualTo(partyId)
                .andBranchIdEqualTo(branchId);
        List<PmdBranch> pmdBranches = pmdBranchMapper.selectByExample(example);
        return pmdBranches.size()==0?null:pmdBranches.get(0);
    }

    @Transactional
    public void insertSelective(PmdBranch record) {

        pmdBranchMapper.insertSelective(record);
    }

    @Transactional
    public void del(int pmdBranchId){

        // 只能删除当月的缴费党支部
        PmdMonth currentPmdMonth = pmdMonthService.getCurrentPmdMonth();
        if(currentPmdMonth==null){
            throw new OpException("缴费未开启");
        }
        int monthId = currentPmdMonth.getId();
        PmdBranch pmdBranch = pmdBranchMapper.selectByPrimaryKey(pmdBranchId);
        if(pmdBranch.getMonthId() != monthId){
            throw new OpException("仅允许删除当前缴费月份的党支部");
        }

        if(pmdBranch.getHasReport()){
            throw new OpException("不能删除已报送支部");
        }

        int partyId = pmdBranch.getPartyId();
        int branchId = pmdBranch.getBranchId();
        iPmdMapper.delNotPayMembers(monthId, partyId, branchId);

        PmdMemberExample example = new PmdMemberExample();
        example.createCriteria().andMonthIdEqualTo(monthId).andPartyIdEqualTo(partyId)
                .andBranchIdEqualTo(branchId);
        if(pmdMemberMapper.countByExample(example)==0){

            pmdBranchMapper.deleteByPrimaryKey(pmdBranchId);
        }
    }

    @Transactional
    public void batchDel(Integer[] ids) {

        for (Integer id : ids) {
            del(id);
        }
    }

    @Transactional
    public int updateByPrimaryKeySelective(PmdBranch record) {
        return pmdBranchMapper.updateByPrimaryKeySelective(record);
    }

    //获取还未设置应缴额度的党员
    public List<PmdMember> listUnsetDuepayMembers(int pmdBranchId) {

        PmdBranch pmdBranch = pmdBranchMapper.selectByPrimaryKey(pmdBranchId);

        PmdMemberExample example = new PmdMemberExample();
        example.createCriteria().andMonthIdEqualTo(pmdBranch.getMonthId())
                .andPartyIdEqualTo(pmdBranch.getPartyId())
                .andBranchIdEqualTo(pmdBranch.getBranchId()).andDuePayIsNull();

        return pmdMemberMapper.selectByExample(example);
    }
}
