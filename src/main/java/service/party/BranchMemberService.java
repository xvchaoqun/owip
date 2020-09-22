package service.party;

import domain.base.MetaType;
import domain.party.*;
import domain.sys.SysUserView;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import persistence.party.common.OwAdmin;
import service.BaseMapper;
import service.base.MetaTypeService;
import service.cadre.CadreService;
import sys.helper.PartyHelper;
import sys.tags.CmTag;
import sys.tool.tree.TreeNode;

import java.util.*;

@Service
public class BranchMemberService extends BaseMapper {
    @Autowired
    private OrgAdminService orgAdminService;
    @Autowired
    private BranchAdminService branchAdminService;
    @Autowired
    private BranchMemberGroupService branchMemberGroupService;
    @Autowired
    private PartyMemberService partyMemberService;
    @Autowired
    private PartyService partyService;
    @Autowired
    private MetaTypeService metaTypeService;
    @Autowired
    private CadreService cadreService;

    public TreeNode getTree(Set<Integer> selectIdSet) {

        Map<Integer, List<SysUserView>> groupMap = new LinkedHashMap<>();
        Map<Integer, MetaType> metaTypeMap = metaTypeService.metaTypes("mc_branch_member_type");
        for (Map.Entry<Integer, MetaType> entry : metaTypeMap.entrySet()) {
            groupMap.put(entry.getKey(), new ArrayList<SysUserView>());
        }

        {
            BranchMemberViewExample example = new BranchMemberViewExample();
            example.createCriteria()
                    .andIsDeletedEqualTo(false)
                    .andIsHistoryEqualTo(false);
            example.setOrderByClause("party_sort_order desc, branch_sort_order desc,sort_order desc");

            List<BranchMemberView> branchMemberViews = branchMemberViewMapper.selectByExample(example);

            for (BranchMemberView pmv : branchMemberViews) {

                int typeId = pmv.getTypeId();
                SysUserView uv = pmv.getUser();

                List<SysUserView> uvs = groupMap.get(typeId);
                if (uvs == null) {
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

        for (Map.Entry<Integer, List<SysUserView>> entry : groupMap.entrySet()) {
            List<SysUserView> entryValue = entry.getValue();
            if (entryValue.size() > 0) {

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

    // 查询用户是否是支部管理员或直属党支部管理员
    public boolean isPresentAdmin(Integer userId, Integer partyId, Integer branchId) {
        if (userId == null) return false;
        if (partyId == null && branchId == null) return false;

        if (branchId == null) { // 直属党支部管理员
            boolean directBranch = partyService.isDirectBranch(partyId);
            boolean isAdmin = partyMemberService.isPresentAdmin(userId, partyId);
            return directBranch && isAdmin;
        } else { // 支部管理员
            return branchAdminService.adminBranch(userId, branchId);
        }
    }

    // 删除支部管理员
    @Transactional
    public void delAdmin(int userId, int branchId, Boolean normal) {

        Branch branch = branchMapper.selectByPrimaryKey(branchId);
        PartyHelper.checkAuth(branch.getPartyId(), branch.getId());

        if(normal==null || !normal){  // normal=true时只删除其他管理员， normal=false或空时，删除班子成员和其他管理员

            OwAdmin owAdmin = new OwAdmin();
            owAdmin.setUserId(userId);
            owAdmin.setBranchId(branchId);
            owAdmin.setNormal(false); // 班子成员管理员
            List<OwAdmin> owAdmins = iPartyMapper.selectBranchAdminList(owAdmin, new RowBounds());

            for (OwAdmin record : owAdmins) { // 一般只有一个

                branchAdminService.setBranchAdmin(record.getId(), false);
            }
        }

        if(normal==null){
            OwAdmin owAdmin = new OwAdmin();
            owAdmin.setUserId(userId);
            owAdmin.setBranchId(branchId);
            owAdmin.setNormal(true); // 其他管理员
            List<OwAdmin> owAdmins = iPartyMapper.selectBranchAdminList(owAdmin, new RowBounds());

            for (OwAdmin record : owAdmins) { // 一般只有一个

                orgAdminService.del(record.getId(), record.getUserId());
            }
        }
    }

    // 获取现任支部书记
    public BranchMemberView getBranchSecretary(int branchId) {

        BranchMemberGroup presentGroup = branchMemberGroupService.getPresentGroup(branchId);
        if (presentGroup == null) return null;

        MetaType secretaryType = CmTag.getMetaTypeByCode("mt_branch_secretary");

        BranchMemberViewExample example = new BranchMemberViewExample();
        example.createCriteria().andGroupIdEqualTo(presentGroup.getId())
                .andTypeIdEqualTo(secretaryType.getId())
                    .andIsHistoryEqualTo(false);

        List<BranchMemberView> records = branchMemberViewMapper.selectByExample(example);
        return records.size() == 0 ? null : records.get(0);
    }

    public boolean idDuplicate(Integer id, int groupId, int userId, int typeId) {

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
        if (StringUtils.equalsIgnoreCase(metaType.getCode(), "mt_branch_secretary")) {

            // 每个委员会只有一个书记
            BranchMemberExample example = new BranchMemberExample();
            BranchMemberExample.Criteria criteria = example.createCriteria()
                    .andGroupIdEqualTo(groupId).andTypeIdEqualTo(typeId).andIsHistoryEqualTo(false);
            if (id != null) criteria.andIdNotEqualTo(id);

            if (branchMemberMapper.countByExample(example) > 0) return true;
        }

        return false;
    }

    @Transactional
    public int insertSelective(BranchMember record, boolean autoAdmin) {

        BranchMemberGroup branchMemberGroup = branchMemberGroupMapper.selectByPrimaryKey(record.getGroupId());
        Branch branch = branchMapper.selectByPrimaryKey(branchMemberGroup.getBranchId());
        PartyHelper.checkAuth(branch.getPartyId(), branch.getId());

        record.setIsAdmin(false);
        record.setIsHistory(false);
        record.setSortOrder(getNextSortOrder("ow_branch_member",
                "group_id=" + record.getGroupId()+ " and is_history=0"));
        branchMemberMapper.insertSelective(record);

        if (autoAdmin) {
            branchAdminService.setBranchAdmin(record.getId(), true);
        }
        if(CmTag.getCadre(record.getUserId())==null) {
            cadreService.addTempCadre(record.getUserId());
        }
        return 1;
    }

    @Transactional
    public void del(Integer id) {

        BranchMember branchMember = branchMemberMapper.selectByPrimaryKey(id);

        BranchMemberGroup branchMemberGroup = branchMemberGroupMapper.selectByPrimaryKey(branchMember.getGroupId());
        Branch branch = branchMapper.selectByPrimaryKey(branchMemberGroup.getBranchId());
        PartyHelper.checkAuth(branch.getPartyId(), branch.getId());

        branchAdminService.setBranchAdmin(id, false);

        branchMemberMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids) {

        if (ids == null || ids.length == 0) return;
        for (Integer id : ids) {
            BranchMember branchMember = branchMemberMapper.selectByPrimaryKey(id);

            BranchMemberGroup branchMemberGroup = branchMemberGroupMapper.selectByPrimaryKey(branchMember.getGroupId());
            Branch branch = branchMapper.selectByPrimaryKey(branchMemberGroup.getBranchId());
            PartyHelper.checkAuth(branch.getPartyId(), branch.getId());

            branchAdminService.setBranchAdmin(id, false);
        }
        BranchMemberExample example = new BranchMemberExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        branchMemberMapper.deleteByExample(example);
    }

    @Transactional
    public int updateByPrimaryKeySelective(BranchMember record, boolean autoAdmin) {

        BranchMember old = branchMemberMapper.selectByPrimaryKey(record.getId());

        BranchMemberGroup branchMemberGroup = branchMemberGroupMapper.selectByPrimaryKey(old.getGroupId());
        Branch branch = branchMapper.selectByPrimaryKey(branchMemberGroup.getBranchId());
        PartyHelper.checkAuth(branch.getPartyId(), branch.getId());

        record.setIsAdmin(old.getIsAdmin());
        record.setIsHistory(null);
        branchMemberMapper.updateByPrimaryKeySelective(record);

        // 选择的类别是自动设定为管理员
        if (autoAdmin) {
            branchAdminService.setBranchAdmin(record.getId(), true);
        }

        return 1;
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

        BranchMember entity = branchMemberMapper.selectByPrimaryKey(id);
        Integer groupId = entity.getGroupId();
        boolean isHistory = entity.getIsHistory();
        changeOrder("ow_branch_member", "group_id=" + groupId
                + " and is_history=" + isHistory, ORDER_BY_DESC, id, addNum);
    }

    // 离任/重新任命
    @Transactional
    public void dismiss(Integer id, boolean dismiss, Date dismissDate, Date assignDate) {

        BranchMember branchMember = branchMemberMapper.selectByPrimaryKey(id);

        BranchMember record = new BranchMember();
        record.setId(id);
        record.setIsHistory(dismiss);
        record.setDismissDate(dismissDate);
        record.setAssignDate(assignDate);
        record.setSortOrder(getNextSortOrder("ow_branch_member",
                "group_id=" + branchMember.getGroupId() + " and is_history=" + dismiss));

        branchMemberMapper.updateByPrimaryKeySelective(record);

        if(dismiss) {
            branchAdminService.setBranchAdmin(id, false);
        }else{
            commonMapper.excuteSql("update ow_branch_member set dismiss_date=null where id="+id);
            if (branchMember.getIsAdmin()) {
                branchAdminService.setBranchAdmin(id, true);
            }
        }
    }
}
