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
import persistence.party.common.OwAdmin;
import service.BaseMapper;
import service.base.MetaTypeService;
import service.cadre.CadreService;
import service.sys.SysConfigService;
import shiro.ShiroHelper;
import sys.constants.SystemConstants;
import sys.tags.CmTag;
import sys.tool.tree.TreeNode;

import java.util.*;

@Service
public class PartyMemberService extends BaseMapper {

    @Autowired
    private OrgAdminService orgAdminService;
    @Autowired
    private PartyAdminService partyAdminService;
    @Autowired
    private MetaTypeService metaTypeService;
    @Autowired
    protected PartyMemberGroupService partyMemberGroupService;
    @Autowired
    protected SysConfigService sysConfigService;
    @Autowired
    protected CadreService cadreService;

    public TreeNode getTree(Set<Integer> selectIdSet) {

        Map<Integer, List<SysUserView>> groupMap = new LinkedHashMap<>();
        Map<Integer, MetaType> metaTypeMap = metaTypeService.metaTypes("mc_party_member_post");
        for (Map.Entry<Integer, MetaType> entry : metaTypeMap.entrySet()) {
            groupMap.put(entry.getKey(), new ArrayList<SysUserView>());
        }

        {
            PartyMemberViewExample example = new PartyMemberViewExample();
            example.createCriteria()
                    .andIsDeletedEqualTo(false)
                    .andIsPresentEqualTo(true)
                    .andIsHistoryEqualTo(false);
            example.setOrderByClause("party_sort_order desc, sort_order desc");

            List<PartyMemberView> partyMemberViews = partyMemberViewMapper.selectByExample(example);

            for (PartyMemberView pmv : partyMemberViews) {

                int postId = pmv.getPostId();
                SysUserView uv = pmv.getUser();

                List<SysUserView> uvs = groupMap.get(postId);
                if (uvs == null) {
                    uvs = new ArrayList<>();
                    groupMap.put(postId, uvs);
                }
                uvs.add(uv);
            }
        }

        TreeNode root = new TreeNode();
        root.title = "分党委班子成员";
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

    // 获取现任分党委委员（拥有某种分工的）
    public List<PartyMemberView> getPartyMemberViews(int partyId, int typeId) {

        PartyMemberGroup presentGroup = partyMemberGroupService.getPresentGroup(partyId);
        if (presentGroup == null) return new ArrayList<>();

        PartyMemberViewExample example = new PartyMemberViewExample();
        example.createCriteria().andGroupIdEqualTo(presentGroup.getId())
                .andTypeIdsIn(Arrays.asList(typeId))
                .andIsHistoryEqualTo(false);

        return partyMemberViewMapper.selectByExample(example);
    }

    // 获取现任分党委委员（拥有某种分工的）
    public PartyMemberView getPartyMemberView(int partyId, int userId, int typeId) {

        PartyMemberGroup presentGroup = partyMemberGroupService.getPresentGroup(partyId);
        if (presentGroup == null) return null;

        PartyMemberViewExample example = new PartyMemberViewExample();
        example.createCriteria().andGroupIdEqualTo(presentGroup.getId()).andUserIdEqualTo(userId)
                .andTypeIdsIn(Arrays.asList(typeId))
                .andIsHistoryEqualTo(false);
        List<PartyMemberView> records = partyMemberViewMapper.selectByExample(example);
        return records.size() == 0 ? null : records.get(0);
    }

    // 获取现任分党委委员
    public PartyMemberView getPartyMemberView(int partyId, int userId) {

        PartyMemberGroup presentGroup = partyMemberGroupService.getPresentGroup(partyId);
        if (presentGroup == null) return null;

        PartyMemberViewExample example = new PartyMemberViewExample();
        example.createCriteria().andGroupIdEqualTo(presentGroup.getId())
                .andUserIdEqualTo(userId)
                .andIsHistoryEqualTo(false);

        List<PartyMemberView> records = partyMemberViewMapper.selectByExample(example);
        return records.size() == 0 ? null : records.get(0);
    }

    // 获取现任分党委书记
    public PartyMemberView getPartySecretary(int partyId) {

        PartyMemberGroup presentGroup = partyMemberGroupService.getPresentGroup(partyId);
        if (presentGroup == null) return null;

        MetaType partySecretaryType = CmTag.getMetaTypeByCode("mt_party_secretary");

        PartyMemberViewExample example = new PartyMemberViewExample();
        example.createCriteria().andGroupIdEqualTo(presentGroup.getId())
                .andPostIdEqualTo(partySecretaryType.getId())
                .andIsHistoryEqualTo(false);

        List<PartyMemberView> records = partyMemberViewMapper.selectByExample(example);
        return records.size() == 0 ? null : records.get(0);
    }

    // 是否现任分党委书记
    public boolean isPartySecretary(int userId, int partyId) {

        PartyMemberGroup presentGroup = partyMemberGroupService.getPresentGroup(partyId);
        if (presentGroup == null) return false;

        MetaType partySecretaryType = CmTag.getMetaTypeByCode("mt_party_secretary");

        PartyMemberViewExample example = new PartyMemberViewExample();
        example.createCriteria().andGroupIdEqualTo(presentGroup.getId())
                .andPostIdEqualTo(partySecretaryType.getId())
                .andUserIdEqualTo(userId)
                .andIsHistoryEqualTo(false);

        return partyMemberViewMapper.countByExample(example)>0;
    }

    // 查询用户是否是现任分党委、党总支、直属党支部班子的管理员
    public boolean isPresentAdmin(Integer userId, Integer partyId) {

        if (userId == null || partyId == null) return false;

        return partyAdminService.adminParty(userId, partyId);
    }

    public boolean hasAdminAuth(Integer userId, Integer partyId) {

        if (ShiroHelper.isPermitted(SystemConstants.PERMISSION_PARTYVIEWALL))
            return true;

        return isPresentAdmin(userId, partyId);
    }

    // 删除分党委管理员
    @Transactional
    public void delAdmin(int userId, int partyId, Boolean normal) {

       if(normal==null || !normal){  // normal=true时只删除普通管理员， normal=false或空时，删除班子成员和普通管理员

            OwAdmin owAdmin = new OwAdmin();
            owAdmin.setUserId(userId);
            owAdmin.setPartyId(partyId);
            owAdmin.setNormal(false); // 班子成员管理员
            List<OwAdmin> owAdmins = iPartyMapper.selectPartyAdminList(owAdmin, new RowBounds());

            for (OwAdmin record : owAdmins) { // 一般只有一个

                PartyMember partyMember = new PartyMember();
                partyMember.setId(record.getId());
                partyMember.setUserId(userId);
                partyMember.setGroupId(record.getGroupId());
                partyMember.setIsAdmin(true);

                partyAdminService.toggleAdmin(partyMember);
            }
        }

        if(normal==null){

            OwAdmin owAdmin = new OwAdmin();
            owAdmin.setUserId(userId);
            owAdmin.setPartyId(partyId);
            owAdmin.setNormal(true); // 普通管理员
            List<OwAdmin> owAdmins = iPartyMapper.selectPartyAdminList(owAdmin, new RowBounds());

            for (OwAdmin record : owAdmins) {
                orgAdminService.del(record.getId(), record.getUserId());
            }
        }
    }

    public boolean idDuplicate(Integer id, int groupId, int userId, int postId) {

        // 20190405注释 同一个人可能存在兼职情况
        {
            // 但同一个人不可以在同一个委员会任同一个职务
            PartyMemberExample example = new PartyMemberExample();
            PartyMemberExample.Criteria criteria = example.createCriteria()
                    .andGroupIdEqualTo(groupId).andPostIdEqualTo(postId)
                    .andUserIdEqualTo(userId).andIsHistoryEqualTo(false);
            if (id != null) criteria.andIdNotEqualTo(id);

            if (partyMemberMapper.countByExample(example) > 0) return true;
        }

        MetaType metaType = metaTypeService.findAll().get(postId);
        if (StringUtils.equalsIgnoreCase(metaType.getCode(), "mt_party_secretary")) {

            // 每个委员会只有一个书记
            PartyMemberExample example = new PartyMemberExample();
            PartyMemberExample.Criteria criteria = example.createCriteria()
                    .andGroupIdEqualTo(groupId).andPostIdEqualTo(postId)
                    .andIsHistoryEqualTo(false);
            if (id != null) criteria.andIdNotEqualTo(id);

            if (partyMemberMapper.countByExample(example) > 0) return true;
        }

        return false;
    }

    // 根据班子、用户ID、职务获取 班子成员
    public PartyMember get(int groupId, int userId, int postId) {

        PartyMemberExample example = new PartyMemberExample();
        PartyMemberExample.Criteria criteria = example.createCriteria()
                .andGroupIdEqualTo(groupId).andPostIdEqualTo(postId)
                .andUserIdEqualTo(userId);

        List<PartyMember> partyMembers = partyMemberMapper.selectByExampleWithRowbounds(example, new RowBounds(0, 1));
        return partyMembers.size() == 1 ? partyMembers.get(0) : null;
    }

    @Transactional
    public int insertSelective(PartyMember record, boolean autoAdmin) {

        record.setIsAdmin(false);
        record.setIsHistory(false);
        record.setSortOrder(getNextSortOrder("ow_party_member",
                "group_id=" + record.getGroupId() + " and is_history=0"));
        partyMemberMapper.insertSelective(record);

        if (autoAdmin) {
            partyAdminService.toggleAdmin(record);
        }
        if(CmTag.getCadre(record.getUserId())==null) {
            cadreService.addTempCadre(record.getUserId());
        }
        return 1;
    }

    @Transactional
    public void del(Integer id) {

        PartyMember partyMember = partyMemberMapper.selectByPrimaryKey(id);

        if (partyMember.getIsAdmin()) {
            partyAdminService.toggleAdmin(partyMember);
        }
        partyMemberMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids) {

        if (ids == null || ids.length == 0) return;
        for (Integer id : ids) {
            PartyMember partyMember = partyMemberMapper.selectByPrimaryKey(id);
            // 权限控制
            if (!ShiroHelper.isPermitted(SystemConstants.PERMISSION_PARTYVIEWALL)) {

                Integer groupId = partyMember.getGroupId();
                PartyMemberGroup partyMemberGroup = partyMemberGroupMapper.selectByPrimaryKey(groupId);
                Integer partyId = partyMemberGroup.getPartyId();

                // 要求是分党委管理员
                if (!isPresentAdmin(ShiroHelper.getCurrentUserId(), partyId)) {
                    throw new UnauthorizedException();
                }
            }

            if (partyMember.getIsAdmin()) {
                partyAdminService.toggleAdmin(partyMember);
            }
        }
        PartyMemberExample example = new PartyMemberExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        partyMemberMapper.deleteByExample(example);
    }

    @Transactional
    public int updateByPrimaryKey(PartyMember record, boolean autoAdmin) {

        PartyMember old = partyMemberMapper.selectByPrimaryKey(record.getId());
        record.setIsAdmin(old.getIsAdmin());
        record.setSortOrder(old.getSortOrder());
        record.setGroupId(old.getGroupId());
        record.setIsHistory(null);
        partyMemberMapper.updateByPrimaryKeySelective(record);

        // 如果以前不是管理员，但是选择的类别是自动设定为管理员
        if (!old.getIsHistory() && !record.getIsAdmin() && autoAdmin) {
            record.setUserId(old.getUserId());
            record.setGroupId(old.getGroupId());
            partyAdminService.toggleAdmin(record);
        }

        if (record.getTypeIds() == null) {
            commonMapper.excuteSql("update ow_party_member set type_ids=null where id=" + record.getId());
        }

        return 1;
    }

    @Transactional
    public int bacthImport(List<PartyMember> records) {

        int addCount = 0;
        for (PartyMember record : records) {

            PartyMember _record = get(record.getGroupId(), record.getUserId(), record.getPostId());

            Integer postId = _record.getPostId();
            MetaType metaType = CmTag.getMetaType(postId);
            boolean isAdmin = ((StringUtils.equals(metaType.getCode(), "mt_party_secretary")
                    || StringUtils.equals(metaType.getCode(), "mt_party_vice_secretary")));

            if (_record == null) {

                insertSelective(record, isAdmin);
                addCount++;
            } else {

                if (_record.getIsAdmin()) {
                    // 先清除管理员
                    partyAdminService.toggleAdmin(_record);
                }

                record.setId(_record.getId());
                updateByPrimaryKey(record, isAdmin);
            }
        }

        return addCount;
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

        PartyMember entity = partyMemberMapper.selectByPrimaryKey(id);
        Integer groupId = entity.getGroupId();
        boolean isHistory = entity.getIsHistory();
        changeOrder("ow_party_member", "group_id=" + groupId
                + " and is_history=" + isHistory, ORDER_BY_DESC, id, addNum);
    }

    // 离任/重新任命
    @Transactional
    public void dissmiss(Integer id, boolean dismiss, Date dismissDate, Date assignDate) {

        PartyMember partyMember = partyMemberMapper.selectByPrimaryKey(id);

        PartyMember record = new PartyMember();
        record.setId(id);
        record.setIsHistory(dismiss);
        record.setDismissDate(dismissDate);
        record.setAssignDate(assignDate);
        record.setSortOrder(getNextSortOrder("ow_party_member",
                "group_id=" + partyMember.getGroupId() + " and is_history=" + dismiss));

        partyMemberMapper.updateByPrimaryKeySelective(record);

        if (dismiss) {
            if (partyMember.getIsAdmin()) {
                partyAdminService.toggleAdmin(partyMember);
            }
        } else {
            commonMapper.excuteSql("update ow_party_member set dismiss_date=null where id=" + id);
        }
    }

    public List<PartyMemberView> getByPartyId(Integer partyId) {

        PartyMemberViewExample example = new PartyMemberViewExample();
        example.createCriteria().andGroupPartyIdEqualTo(partyId).andIsAdminEqualTo(true);
        return partyMemberViewMapper.selectByExample(example);
    }
}
