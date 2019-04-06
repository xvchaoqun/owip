package service.party;

import domain.base.MetaType;
import domain.party.*;
import domain.sys.SysUserView;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;
import service.base.MetaTypeService;
import shiro.ShiroHelper;
import sys.constants.SystemConstants;
import sys.tool.tree.TreeNode;

import java.util.*;

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

    public TreeNode getTree(Set<Integer> selectIdSet){

        Map<Integer, List<SysUserView>> groupMap = new LinkedHashMap<>();
        Map<Integer, MetaType> metaTypeMap = metaTypeService.metaTypes("mc_branch_member_type");
        for (Map.Entry<Integer, MetaType> entry : metaTypeMap.entrySet()) {
            groupMap.put(entry.getKey(), new ArrayList<SysUserView>());
        }

        {
            BranchMemberViewExample example = new BranchMemberViewExample();
            example.createCriteria()
                    .andIsDeletedEqualTo(false)
                    .andIsPresentEqualTo(true);
            example.setOrderByClause("party_sort_order desc, branch_sort_order desc,sort_order desc");

            List<BranchMemberView> branchMemberViews = branchMemberViewMapper.selectByExample(example);

            for (BranchMemberView pmv : branchMemberViews) {

                int typeId = pmv.getTypeId();
                SysUserView uv = pmv.getUser();

                List<SysUserView> uvs = groupMap.get(typeId);
                if(uvs==null){
                    uvs = new ArrayList<>();
                    groupMap.put(typeId, uvs);
                }
                uvs.add(uv);
            }
        }

        TreeNode root = new TreeNode();
        root.title = "党支部班子成员";
        root.expand = true;
        root.isFolder = true;
        List<TreeNode> rootChildren = new ArrayList<TreeNode>();
        root.children = rootChildren;

        for(Map.Entry<Integer, List<SysUserView>> entry : groupMap.entrySet()) {
            List<SysUserView> entryValue = entry.getValue();
            if(entryValue.size()>0) {

                TreeNode titleNode = new TreeNode();
                titleNode.expand = false;
                titleNode.isFolder = true;
                List<TreeNode> titleChildren = new ArrayList<TreeNode>();
                titleNode.children = titleChildren;

                int selectCount = 0;
                for (SysUserView uv : entryValue) {

                    int userId = uv.getUserId();
                    String unit = uv.getUnit();
                    TreeNode node = new TreeNode();
                    node.title = uv.getRealname() + (unit != null ? ("-" + unit) : "");

                    int key = userId;
                    node.key = key + "";

                    if (selectIdSet.contains(key)) {
                        selectCount++;
                        node.select = true;
                    }

                    titleChildren.add(node);
                }

                titleNode.title = metaTypeMap.get(entry.getKey()).getName() + String.format("(%s", selectCount > 0 ? selectCount + "/" : "") + entryValue.size() + "人)";
                rootChildren.add(titleNode);
            }
        }
        return root;
    }

    public void checkAuth(int partyId){

        //===========权限
        Integer loginUserId = ShiroHelper.getCurrentUserId();
        if (!ShiroHelper.isPermitted(SystemConstants.PERMISSION_PARTYVIEWALL)) {

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
            return iPartyMapper.isBranchAdmin(userId, branchId) > 0;
        }
    }

    // 删除支部管理员
    @Transactional
    public void delAdmin(int userId, int branchId){

        Branch branch = branchMapper.selectByPrimaryKey(branchId);
        checkAuth(branch.getPartyId());

        List<BranchMember> branchMembers = iPartyMapper.findBranchAdminOfBranchMember(userId, branchId);
        for (BranchMember branchMember : branchMembers) { // 理论上只有一个
            branchMemberAdminService.toggleAdmin(branchMember);
        }
        List<OrgAdmin> orgAdmins = iPartyMapper.findBranchAdminOfOrgAdmin(userId, branchId);
        for (OrgAdmin orgAdmin : orgAdmins) { // 理论上只有一个
            orgAdminService.del(orgAdmin.getId(), orgAdmin.getUserId());
        }
    }

    public boolean idDuplicate(Integer id, int groupId, int userId, int typeId){

        // 20190405注释 可能存在兼职情况
        /*{
            // 同一个人不可以在同一个委员会
            BranchMemberExample example = new BranchMemberExample();
            BranchMemberExample.Criteria criteria = example.createCriteria()
                    .andGroupIdEqualTo(groupId).andUserIdEqualTo(userId);
            if (id != null) criteria.andIdNotEqualTo(id);

            if(branchMemberMapper.countByExample(example) > 0) return true;
        }*/

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
        record.setSortOrder(getNextSortOrder("ow_branch_member", "group_id=" + record.getGroupId()));
        branchMemberMapper.insertSelective(record);

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
                commonMapper.downOrder("ow_branch_member", "group_id=" + groupId, baseSortOrder, targetEntity.getSortOrder());
            else
                commonMapper.upOrder("ow_branch_member", "group_id=" + groupId, baseSortOrder, targetEntity.getSortOrder());

            BranchMember record = new BranchMember();
            record.setId(id);
            record.setSortOrder(targetEntity.getSortOrder());
            branchMemberMapper.updateByPrimaryKeySelective(record);
        }
    }
}
