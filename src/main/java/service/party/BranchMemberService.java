package service.party;

import domain.party.*;
import domain.sys.MetaType;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;
import service.OrgAdminService;
import service.sys.MetaTypeService;
import shiro.ShiroUser;
import sys.constants.SystemConstants;

import java.util.Arrays;
import java.util.List;

@Service
public class BranchMemberService extends BaseMapper {
    @Autowired
    private OrgAdminService orgAdminService;
    @Autowired
    private BranchMemberAdminService branchMemberAdminService;
    @Autowired
    private  PartyMemberService partyMemberService;
    @Autowired
    private  PartyService partyService;
    @Autowired
    private MetaTypeService metaTypeService;

    public void checkAuth(int partyId){

        //===========权限
        Subject subject = SecurityUtils.getSubject();
        ShiroUser shiroUser = (ShiroUser) subject.getPrincipal();
        Integer loginUserId = shiroUser.getId();
        if (!subject.hasRole(SystemConstants.ROLE_ADMIN)
                && !subject.hasRole(SystemConstants.ROLE_ODADMIN)) {

            boolean isAdmin = partyMemberService.isPresentAdmin(loginUserId, partyId);
            if(!isAdmin) throw new UnauthorizedException();
        }
    }

    // 查询用户是否是支部管理员或直属党支部管理员
    public boolean isPresentAdmin(Integer userId,Integer partyId, Integer branchId){
        if(userId==null) return false;
        if(partyId==null && branchId==null) return false;

        if(branchId==null) { // 直属党支部管理员
            boolean directBranch = partyService.isDirectBranch(partyId);
            boolean isAdmin = partyMemberService.isPresentAdmin(userId, partyId);
            return directBranch && isAdmin;
        }else { // 支部管理员
            return commonMapper.isBranchAdmin(userId, branchId) > 0;
        }
    }

    // 删除支部管理员
    @Transactional
    public void delAdmin(int userId, int branchId){

        Branch branch = branchMapper.selectByPrimaryKey(branchId);
        checkAuth(branch.getPartyId());

        List<BranchMember> branchMembers = commonMapper.findBranchAdminOfBranchMember(userId, branchId);
        for (BranchMember branchMember : branchMembers) { // 理论上只有一个
            branchMemberAdminService.toggleAdmin(branchMember);
        }
        List<OrgAdmin> orgAdmins = commonMapper.findBranchAdminOfOrgAdmin(userId, branchId);
        for (OrgAdmin orgAdmin : orgAdmins) { // 理论上只有一个
            orgAdminService.del(orgAdmin.getId(), orgAdmin.getUserId());
        }
    }

    public boolean idDuplicate(Integer id, int groupId, int userId, int typeId){

        {
            // 同一个人不可以在同一个委员会
            BranchMemberExample example = new BranchMemberExample();
            BranchMemberExample.Criteria criteria = example.createCriteria()
                    .andGroupIdEqualTo(groupId).andUserIdEqualTo(userId);
            if (id != null) criteria.andIdNotEqualTo(id);

            if(branchMemberMapper.countByExample(example) > 0) return true;
        }

        MetaType metaType = metaTypeService.findAll().get(typeId);
        if(StringUtils.equalsIgnoreCase(metaType.getCode(), "mt_branch_secretary")){

            // 每个委员会只有一个书记
            BranchMemberExample example = new BranchMemberExample();
            BranchMemberExample.Criteria criteria = example.createCriteria()
                    .andGroupIdEqualTo(groupId).andTypeIdEqualTo(typeId);
            if (id != null) criteria.andIdNotEqualTo(id);

            if(branchMemberMapper.countByExample(example) > 0) return true;
        }

        return false;
    }

    @Transactional
    public int insertSelective(BranchMember record, boolean autoAdmin){

        BranchMemberGroup branchMemberGroup = branchMemberGroupMapper.selectByPrimaryKey(record.getGroupId());
        Branch branch = branchMapper.selectByPrimaryKey(branchMemberGroup.getBranchId());
        checkAuth(branch.getPartyId());

        record.setIsAdmin(false);
        branchMemberMapper.insertSelective(record);

        Integer id = record.getId();
        BranchMember _record = new BranchMember();
        _record.setId(id);
        _record.setSortOrder(id);
        branchMemberMapper.updateByPrimaryKeySelective(_record);

        if(autoAdmin){
            branchMemberAdminService.toggleAdmin(record);
        }
        return 1;
    }
    @Transactional
    public void del(Integer id){

        BranchMember branchMember = branchMemberMapper.selectByPrimaryKey(id);

        BranchMemberGroup branchMemberGroup = branchMemberGroupMapper.selectByPrimaryKey(branchMember.getGroupId());
        Branch branch = branchMapper.selectByPrimaryKey(branchMemberGroup.getBranchId());
        checkAuth(branch.getPartyId());

        if(branchMember.getIsAdmin()){
            branchMemberAdminService.toggleAdmin(branchMember);
        }
        branchMemberMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;
        for (Integer id : ids) {
            BranchMember branchMember = branchMemberMapper.selectByPrimaryKey(id);

            BranchMemberGroup branchMemberGroup = branchMemberGroupMapper.selectByPrimaryKey(branchMember.getGroupId());
            Branch branch = branchMapper.selectByPrimaryKey(branchMemberGroup.getBranchId());
            checkAuth(branch.getPartyId());

            if(branchMember.getIsAdmin()){
                branchMemberAdminService.toggleAdmin(branchMember);
            }
        }
        BranchMemberExample example = new BranchMemberExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        branchMemberMapper.deleteByExample(example);
    }

    @Transactional
    public int updateByPrimaryKeySelective(BranchMember record, boolean autoAdmin){
        BranchMember old = branchMemberMapper.selectByPrimaryKey(record.getId());

        BranchMemberGroup branchMemberGroup = branchMemberGroupMapper.selectByPrimaryKey(old.getGroupId());
        Branch branch = branchMapper.selectByPrimaryKey(branchMemberGroup.getBranchId());
        checkAuth(branch.getPartyId());

        record.setIsAdmin(old.getIsAdmin());
        branchMemberMapper.updateByPrimaryKeySelective(record);

        // 如果以前不是管理员，但是选择的类别是自动设定为管理员
        if(!record.getIsAdmin() && autoAdmin){
            record.setUserId(old.getUserId());
            record.setGroupId(old.getGroupId());
            branchMemberAdminService.toggleAdmin(record);
        }
        return 1;
    }

    /**
     * 排序 ，要求 1、sort_order>0且不可重复  2、sort_order 降序排序
     * 3.sort_order = LAST_INSERT_ID()+1,
     * @param id
     * @param addNum
     */
    @Transactional
    public void changeOrder(int id, int addNum) {

        if(addNum == 0) return ;

        BranchMember entity = branchMemberMapper.selectByPrimaryKey(id);

        BranchMemberGroup branchMemberGroup = branchMemberGroupMapper.selectByPrimaryKey(entity.getGroupId());
        Branch branch = branchMapper.selectByPrimaryKey(branchMemberGroup.getBranchId());
        checkAuth(branch.getPartyId());

        Integer baseSortOrder = entity.getSortOrder();
        Integer groupId = entity.getGroupId();

        BranchMemberExample example = new BranchMemberExample();
        if (addNum > 0) {

            example.createCriteria().andGroupIdEqualTo(groupId).andSortOrderGreaterThan(baseSortOrder);
            example.setOrderByClause("sort_order asc");
        }else {

            example.createCriteria().andGroupIdEqualTo(groupId).andSortOrderLessThan(baseSortOrder);
            example.setOrderByClause("sort_order desc");
        }

        List<BranchMember> overEntities = branchMemberMapper.selectByExampleWithRowbounds(example, new RowBounds(0, Math.abs(addNum)));
        if(overEntities.size()>0) {

            BranchMember targetEntity = overEntities.get(overEntities.size()-1);

            if (addNum > 0)
                commonMapper.downOrder_branchMember(groupId, baseSortOrder, targetEntity.getSortOrder());
            else
                commonMapper.upOrder_branchMember(groupId, baseSortOrder, targetEntity.getSortOrder());

            BranchMember record = new BranchMember();
            record.setId(id);
            record.setSortOrder(targetEntity.getSortOrder());
            branchMemberMapper.updateByPrimaryKeySelective(record);
        }
    }
}
