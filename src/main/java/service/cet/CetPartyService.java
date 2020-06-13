package service.cet;

import domain.cet.CetParty;
import domain.cet.CetPartyAdmin;
import domain.cet.CetPartyExample;
import domain.party.Party;
import domain.party.PartyExample;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import persistence.party.common.OwAdmin;
import service.party.PartyMemberService;
import sys.tags.CmTag;
import sys.tool.tree.TreeNode;

import java.util.*;

@Service
public class CetPartyService extends CetBaseMapper {

    @Autowired
    private CetPartyAdminService cetPartyAdminService;
    @Autowired
    private PartyMemberService partyMemberService;

    public boolean idDuplicate(Integer id, int partyId) {

        CetPartyExample example = new CetPartyExample();
        CetPartyExample.Criteria criteria = example.createCriteria().andPartyIdEqualTo(partyId);
        if (id != null) criteria.andIdNotEqualTo(id);

        return cetPartyMapper.countByExample(example) > 0;
    }

    @Transactional
    public void insertSelective(CetParty record) {

        record.setIsDeleted(false);
        record.setSortOrder(getNextSortOrder("cet_party", "is_deleted=0"));
        cetPartyMapper.insertSelective(record);
    }

    @Transactional
    public void batchDel(Integer[] ids, boolean isDeleted) {

        if (ids == null || ids.length == 0) return;

        for (Integer id : ids) {

            // 先删除或恢复二级党委
            CetParty record = new CetParty();
            record.setId(id);
            record.setIsDeleted(isDeleted);
            record.setSortOrder(getNextSortOrder("cet_party", "is_deleted="+ isDeleted));
            cetPartyMapper.updateByPrimaryKeySelective(record);

            // 后更新二级党委管理员权限
            List<CetPartyAdmin> cetPartyAdmins = cetPartyAdminService.findByPartyId(id);
            for (CetPartyAdmin cetPartyAdmin : cetPartyAdmins) {

                cetPartyAdminService.updateRoleCetAdminParty(cetPartyAdmin.getUserId());
            }
        }
    }

    @Transactional
    public int updateByPrimaryKeySelective(CetParty record) {

        return cetPartyMapper.updateByPrimaryKeySelective(record);
    }

    //同步管理员
    @Transactional
    public void batchSync() {

        List<Party> parties = partyMapper.selectByExample(new PartyExample());
        List<Integer> partyIds = new ArrayList<>();
        for (Party party : parties) {
            if (!party.getIsDeleted())
                partyIds.add(party.getId());
        }
        Map<Integer, CetParty> cetPartyMap = findAll();
        List<OwAdmin> owAdmins = iPartyMapper.selectPartyAdminList( new OwAdmin(), new RowBounds());
        for (Map.Entry<Integer, CetParty> entry : cetPartyMap.entrySet()) {
            CetParty cetParty = entry.getValue();
            Integer partyId = cetParty.getPartyId();
            Integer cetPartyId = cetParty.getId();
            if (null != partyId && partyIds.contains(partyId)) {

                Set<Integer> userIds = new HashSet<>();
                for (OwAdmin admin : owAdmins) {
                    if (admin.getPartyId().equals(partyId))
                        userIds.add(admin.getUserId());
                }
                //先删除管理员,仅删除委员中包含的,不删除自己设置的
                List<CetPartyAdmin> cetPartyAdmins = cetPartyAdminService.findByPartyId(cetPartyId);
                for (CetPartyAdmin cetPartyAdmin : cetPartyAdmins) {
                    Integer userId = cetPartyAdmin.getUserId();
                    if (userIds.contains(cetPartyAdmin.getUserId())) {
                        cetPartyAdminMapper.deleteByPrimaryKey(cetPartyAdmin.getId());
                        cetPartyAdminService.updateRoleCetAdminParty(userId);
                    }
                }
                //同步管理员
                for (Integer userId : userIds) {
                    cetPartyAdminService.insert(cetPartyId, userId);
                }
            }
        }
    }

    private Map<Integer, CetParty> findAll() {

        CetPartyExample example = new CetPartyExample();
        example.createCriteria().andIsDeletedEqualTo(false);
        List<CetParty> cetParties = cetPartyMapper.selectByExample(example);
        Map<Integer, CetParty> map = new HashMap<>();
        for (CetParty cetParty : cetParties) {
            map.put(cetParty.getId(), cetParty);
        }
        return map;
    }

    @Transactional
    public void changeOrder(int id, int addNum) {

        CetParty cetParty = cetPartyMapper.selectByPrimaryKey(id);
        changeOrder("cet_party", "is_deleted=" + cetParty.getIsDeleted(), ORDER_BY_DESC, id, addNum);
    }

    public TreeNode getTree(Set<Integer> selectIdSet) {

        if (null == selectIdSet) selectIdSet = new HashSet<>();

        TreeNode root = new TreeNode();
        root.title = "二级党委列表";
        root.expand = true;
        root.isFolder = true;
        root.hideCheckbox = true;
        List<TreeNode> rootChildren = new ArrayList<TreeNode>();
        root.children = rootChildren;

        PartyExample example = new PartyExample();
        example.createCriteria().andIsDeletedEqualTo(false);
        example.setOrderByClause(" sort_order desc");
        List<Party> partyList = partyMapper.selectByExample(example);
        for (Party party : partyList) {

            TreeNode node = new TreeNode();
            node.title = party.getName();
            node.key = party.getId() + "";
            if (selectIdSet.contains(party.getId().intValue())) {
                node.key = null;
                node.select = true;
                node.hideCheckbox = false;
                node.unselectable = true;
            }

            rootChildren.add(node);
        }

        return root;
    }

    @Transactional
    public void batchInsert(Integer[] partyIds) {

        if (partyIds.length == 0) return;

        for (Integer partyId : partyIds) {
            if (partyId == 0) continue;
            CetPartyExample example = new CetPartyExample();
            example.createCriteria().andPartyIdEqualTo(partyId).andIsDeletedEqualTo(true);
            List<CetParty> cetParties = cetPartyMapper.selectByExample(example);
            CetParty record = new CetParty();
            if (cetParties.size() == 1){
                record.setId(cetParties.get(0).getId());
                record.setIsDeleted(false);
                cetPartyMapper.updateByPrimaryKeySelective(record);
            }else {
                record.setPartyId(partyId);
                record.setName(CmTag.getParty(partyId).getName());
                insertSelective(record);
            }
        }
    }
}
