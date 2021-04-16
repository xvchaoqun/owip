package service.party;

import controller.global.OpException;
import domain.party.*;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import persistence.party.common.OwAdmin;
import service.BaseMapper;
import service.LoginUserService;
import service.sys.SysUserService;
import shiro.ShiroHelper;
import sys.constants.RoleConstants;
import sys.helper.PartyHelper;
import sys.utils.DateUtils;
import sys.utils.NumberUtils;

import java.net.UnknownHostException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class BranchMemberGroupService extends BaseMapper {

    @Autowired
    private LoginUserService loginUserService;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private BranchAdminService branchAdminService;

    // 查找现任委员会
    public BranchMemberGroup getPresentGroup(int branchId) {

        BranchMemberGroupExample _example = new BranchMemberGroupExample();
        _example.createCriteria().andBranchIdEqualTo(branchId).andIsDeletedEqualTo(false);
        List<BranchMemberGroup> branchMemberGroups = branchMemberGroupMapper.selectByExample(_example);
        int size = branchMemberGroups.size();
        if (size > 1) {
            throw new OpException("数据请求错误：现任班子不唯一。");
        }
        if (size == 1) return branchMemberGroups.get(0);

        return null;
    }

    // 查找班子的所有管理员
    public List<BranchMember> getGroupAdmins(int groupId) {

        BranchMemberExample _example = new BranchMemberExample();
        _example.createCriteria().andGroupIdEqualTo(groupId).andIsAdminEqualTo(true);
        return branchMemberMapper.selectByExample(_example);
    }
    
    /*private void clearPresentGroup(int branchId) {
        
        BranchMemberGroup presentGroup = getPresentGroup(branchId);
        if (presentGroup == null) return;
        
        // 去掉以前设置的现任班子状态
        Integer groupId = presentGroup.getId();
        BranchMemberGroup _record = new BranchMemberGroup();
        _record.setId(groupId);
        branchMemberGroupMapper.updateByPrimaryKeySelective(_record);
        
        for (BranchMember branchMember : getGroupAdmins(groupId)) {
            int userId = branchMember.getUserId();
            // 删除账号的"党支部管理员"角色
            // 如果他只是该党支部的管理员，则删除账号所属的"党支部管理员"角色； 否则不处理
            if (branchAdminService.adminBranchIdCount(userId) == 0) {
                sysUserService.delRole(userId, RoleConstants.ROLE_BRANCHADMIN);
            }
        }
    }*/

    // 更新班子为现任班子时，需要把该班子的所有管理员添加“党支部管理员”角色
    /*private void rebuildPresentGroupAdmin(int groupId) {
        
        for (BranchMember branchMember : getGroupAdmins(groupId)) {
            int userId = branchMember.getUserId();
            SysUserView sysUser = sysUserService.findById(userId);
            // 添加账号的"党支部管理员"角色
            // 如果账号是现任班子的管理员， 且没有"党支部管理员"角色，则添加
            if (!CmTag.hasRole(sysUser.getUsername(), RoleConstants.ROLE_BRANCHADMIN)) {
                sysUserService.addRole(userId, RoleConstants.ROLE_BRANCHADMIN);
            }
        }
    }*/

    // 获取支部委员列表 <branchMemberType, List<BranchMember>>
    public Map<Integer,  List<BranchMember>> getBranchMemberListMap(int groupId) {

        BranchMemberExample example = new BranchMemberExample();
        example.createCriteria().andGroupIdEqualTo(groupId);
        example.setOrderByClause("sort_order desc");
        List<BranchMember> branchMembers = branchMemberMapper.selectByExample(example);

        Map<Integer, List<BranchMember>> branchMemberMap = new LinkedHashMap<>();
        for (BranchMember branchMember : branchMembers) {

            Set<Integer> typeIds = NumberUtils.toIntSet(branchMember.getTypes(), ",");
            for (Integer typeId : typeIds) {
                if(branchMemberMap.get(typeId)==null){
                    List<BranchMember> branchMemberList = new ArrayList<>();
                    branchMemberMap.put(typeId, branchMemberList);
                }
                List<BranchMember> branchMemberList = branchMemberMap.get(typeId);
                branchMemberList.add(branchMember);
            }
        }

        return branchMemberMap;
    }

    @Transactional
    public int insertSelective(BranchMemberGroup record) {

        Branch branch = branchMapper.selectByPrimaryKey(record.getBranchId());
        PartyHelper.checkAuth(branch.getPartyId(), branch.getId());

        record.setIsDeleted(false);
        record.setSortOrder(getNextSortOrder("ow_branch_member_group", null));
        return branchMemberGroupMapper.insertSelective(record);
    }

    // 查找历任委员会（根据任命时间查找，用于导入数据）
    public BranchMemberGroup getHistoryGroup(int branchId, Date appointTime){

        BranchMemberGroupExample _example = new BranchMemberGroupExample();
        _example.createCriteria().andBranchIdEqualTo(branchId)
                .andIsDeletedEqualTo(true)
                .andAppointTimeEqualTo(appointTime);
        List<BranchMemberGroup> branchMemberGroups =
                branchMemberGroupMapper.selectByExampleWithRowbounds(_example, new RowBounds(0,1));

        return branchMemberGroups.size()==1?branchMemberGroups.get(0):null;
    }

    @Transactional
    public int bacthImport(List<BranchMemberGroup> records) {

        int addCount = 0;
        for (BranchMemberGroup record : records) {

            BranchMemberGroup _record = getPresentGroup(record.getBranchId());
            if(_record == null && record.getAppointTime()!=null){

                _record = getHistoryGroup(record.getBranchId(), record.getAppointTime());
            }

            if(_record==null){

                insertSelective(record);
                addCount++;
            }else{
                record.setId(_record.getId());
                updateByPrimaryKeySelective(record);
            }

            if(BooleanUtils.isNotTrue(record.getIsDeleted())){
                commonMapper.excuteSql("update ow_branch_member_group " +
                        "set actual_tran_time=null where id="+_record.getId());
            }
        }

        return addCount;
    }

    @Transactional
    public void batchDel(Integer[] ids, boolean isDeleted, String _actualTranTime) {

        if (ids == null || ids.length == 0) return;

        for (Integer id : ids) {
            BranchMemberGroup branchMemberGroup = branchMemberGroupMapper.selectByPrimaryKey(id);
            int branchId = branchMemberGroup.getBranchId();
            Branch branch = branchMapper.selectByPrimaryKey(branchId);
            PartyHelper.checkAuth(branch.getPartyId(), branchId);

            if (!isDeleted) { // 恢复支部委员会
                if (branch.getIsDeleted())
                    throw new OpException(String.format("恢复支部委员会失败，支部委员会所属的支部【%s】已删除", branch.getName()));
                else {
                    Party party = partyMapper.selectByPrimaryKey(branch.getPartyId());
                    if (party.getIsDeleted())
                        throw new OpException(String.format("恢复支部委员会失败，支部委员会所在党组织【%s】已删除", party.getName()));
                }

                BranchMemberGroup presentGroup = getPresentGroup(branchId);
                if(presentGroup!=null){
                    throw new OpException(String.format("恢复支部委员会失败，支部委员会已存在【%s】", branch.getName()));
                }
            }
        }
        BranchMemberGroupExample example = new BranchMemberGroupExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        BranchMemberGroup record = new BranchMemberGroup();
        record.setIsDeleted(isDeleted);
        if (isDeleted && StringUtils.isNotBlank(_actualTranTime)) {
            record.setActualTranTime(DateUtils.parseDate(_actualTranTime, DateUtils.YYYY_MM_DD));
        }
        branchMemberGroupMapper.updateByExampleSelective(record, example);
    }

    // 删除已撤销的班子
    @Transactional
    public void realDel(Integer[] ids) {

        if (ids == null || ids.length == 0) return;

        BranchMemberGroupExample example = new BranchMemberGroupExample();
        example.createCriteria()
                .andIdIn(Arrays.asList(ids))
                .andIsDeletedEqualTo(true);
        branchMemberGroupMapper.deleteByExample(example);
    }

    @Transactional
    public int updateByPrimaryKeySelective(BranchMemberGroup record) {

        BranchMemberGroup branchMemberGroup = branchMemberGroupMapper.selectByPrimaryKey(record.getId());
        Branch branch = branchMapper.selectByPrimaryKey(branchMemberGroup.getBranchId());
        PartyHelper.checkAuth(branch.getPartyId(), branch.getId());

        return branchMemberGroupMapper.updateByPrimaryKeySelective(record);
    }


    /**
     * 排序 ，要求 1、sort_order>0且不可重复  2、sort_order 降序排序
     * 3.sort_order = LAST_INSERT_ID()+1,
     *
     * @param id
     * @param addNum
     */
    @Transactional
    public void changeOrder(int id, int addNum) {

        if (addNum == 0) return;

        BranchMemberGroup entity = branchMemberGroupMapper.selectByPrimaryKey(id);

        Branch branch = branchMapper.selectByPrimaryKey(entity.getBranchId());
        PartyHelper.checkAuth(branch.getPartyId());

        Integer baseSortOrder = entity.getSortOrder();

        BranchMemberGroupExample example = new BranchMemberGroupExample();
        if (addNum > 0) {

            example.createCriteria().andSortOrderGreaterThan(baseSortOrder);
            example.setOrderByClause("sort_order asc");
        } else {

            example.createCriteria().andSortOrderLessThan(baseSortOrder);
            example.setOrderByClause("sort_order desc");
        }

        List<BranchMemberGroup> overEntities = branchMemberGroupMapper.selectByExampleWithRowbounds(example, new RowBounds(0, Math.abs(addNum)));
        if (overEntities.size() > 0) {

            BranchMemberGroup targetEntity = overEntities.get(overEntities.size() - 1);

            if (addNum > 0)
                commonMapper.downOrder("ow_branch_member_group", null, baseSortOrder, targetEntity.getSortOrder());
            else
                commonMapper.upOrder("ow_branch_member_group", null, baseSortOrder, targetEntity.getSortOrder());

            BranchMemberGroup record = new BranchMemberGroup();
            record.setId(id);
            record.setSortOrder(targetEntity.getSortOrder());
            branchMemberGroupMapper.updateByPrimaryKeySelective(record);
        }
    }

    //按照partyId统计应换届的支部委员会的数量
    public int count(Integer partyId) {

        BranchMemberGroupViewExample example = new BranchMemberGroupViewExample();
        BranchMemberGroupViewExample.Criteria criteria = example.createCriteria()
                .andIsDeletedEqualTo(false).andTranTimeLessThanOrEqualTo(new Date());
        if (!ShiroHelper.isPermitted(RoleConstants.PERMISSION_PARTYVIEWALL)){
            List<Integer> partyIdList = loginUserService.adminPartyIdList();
            if (partyIdList.contains(partyId)) {
                criteria.andPartyIdEqualTo(partyId);
            }else {
                criteria.andPartyIdIsNull();
            }
        }else {
            if (partyId != null) {
                criteria.andPartyIdEqualTo(partyId);
            }
        }

        return (int) branchMemberGroupViewMapper.countByExample(example);
    }

    //按照partyId统计应换届的支部委员会的数量
    public int countBranch(int branchId) {

        BranchMemberGroupViewExample example = new BranchMemberGroupViewExample();
        BranchMemberGroupViewExample.Criteria criteria = example.createCriteria()
                .andIsDeletedEqualTo(false).andTranTimeLessThanOrEqualTo(new Date());
        List<Integer> branchIdList = loginUserService.adminBranchIdList();
        if (branchIdList.contains(branchId)) {
            criteria.andBranchIdEqualTo(branchId);
        }else {
            criteria.andBranchIdIsNull();
        }

        return (int) branchMemberGroupViewMapper.countByExample(example);
    }

    // 得到afterMonths月后需要换届的党支部id
    public List<Integer> getTranBranchIds(int afterMonths){

        Date afterMonthDate = DateUtils.getDateBeforeOrAfterMonthes(new Date(), afterMonths);
        BranchMemberGroupExample example = new BranchMemberGroupExample();
        example.createCriteria().andIsDeletedEqualTo(false).andTranTimeEqualTo(afterMonthDate);
        List<BranchMemberGroup> groupList = branchMemberGroupMapper.selectByExample(example);

        return groupList==null?null:groupList.stream().map(BranchMemberGroup::getBranchId).collect(Collectors.toList());
    }

    // 支部委员会换届提醒
    @Transactional
    public void tranRemind() throws UnknownHostException {

        // 到期前6个月提醒一次
        List<Integer> afterSixMonth = getTranBranchIds(6);
        if (afterSixMonth != null && afterSixMonth.size() > 0) {
            for (Integer branchId : afterSixMonth) {
                sendMsg(branchId, "您所在支部委员会6个月后将换届");
            }
        }

        // 到期前3个月提醒一次
        List<Integer> afterThreeMonth = getTranBranchIds(3);
        if (afterThreeMonth!=null&& afterThreeMonth.size()>0) {
            for (Integer branchId : afterThreeMonth) {
                sendMsg(branchId, "3个月后将换届");
            }
        }

        // 到期后，每个月提醒一次
        BranchMemberGroupExample example1 = new BranchMemberGroupExample();
        example1.createCriteria().andIsDeletedEqualTo(false).andTranTimeLessThan(new Date());
        List<BranchMemberGroup> groupList1 = branchMemberGroupMapper.selectByExample(example1);
        if (groupList1 != null && groupList1.size() > 0) {
            for (BranchMemberGroup group : groupList1) {
                Date tranTime = group.getTranTime();
                int diffMonth = DateUtils.monthDiff(tranTime, new Date());
                if (DateUtils.compareDate(tranTime, DateUtils.getDateBeforeOrAfterMonthes(tranTime, diffMonth)))
                    System.out.println(tranTime);
                    sendMsg(group.getBranchId(), "已过换届时间");
            }
        }

        // 没填应换届时间，每个月提醒一次
        List<Integer> hasNoTranTime = new ArrayList<>();
        BranchMemberGroupExample example = new BranchMemberGroupExample();
        example.createCriteria().andIsDeletedEqualTo(false).andTranTimeIsNull();
        List<BranchMemberGroup> groupList = branchMemberGroupMapper.selectByExample(example);
        if (DateUtils.compareDate(DateUtils.getFirstDateOfMonth(new Date()), new Date())) {
            if (groupList != null && groupList.size() > 0) {
                hasNoTranTime.addAll(groupList.stream().map(BranchMemberGroup::getBranchId).collect(Collectors.toList()));
                for (Integer branchId : hasNoTranTime) {
                    sendMsg(branchId, "未填写换届时间");
                }
            }
        }
    }

    // 微信提醒
    @Transactional
    public void sendMsg(Integer branchId, String msg) throws UnknownHostException {
        Branch branch = branchMapper.selectByPrimaryKey(branchId);
        OwAdmin owAdmin = new OwAdmin();
        owAdmin.setBranchId(branchId);
        List<OwAdmin> records = iPartyMapper.selectBranchAdminList(owAdmin, new RowBounds());
        if (records != null && records.size() > 0) {
            for (OwAdmin record : records) {
                /*SysMsg sysMsg = new SysMsg();
                sysMsg.setUserId(record.getUserId());
                sysMsg.setTitle("支部委员会换届提醒");
                sysMsg.setContent("您管理的支部委员会"+branch.getName()+msg+"，请及时在系统的组织机构管理中操作。");
                sysMsg.setSendTime(new Date());
                sysMsg.setSendUserId(ShiroHelper.getCurrentUserId());
                sysMsg.setStatus(SystemConstants.SYS_MSG_STATUS_UNCONFIRM);
                sysMsg.setIp(InetAddress.getLocalHost().getHostAddress());
                sysMsgMapper.insertSelective(sysMsg);*/
            }
        }
    }
}
