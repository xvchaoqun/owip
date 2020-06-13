package service.cet;

import domain.cet.CetParty;
import domain.cet.CetPartyAdmin;
import domain.cet.CetPartyAdminExample;
import domain.cet.CetPartyExample;
import domain.party.Party;
import domain.party.PartyExample;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import persistence.party.common.OwAdmin;
import sys.constants.CetConstants;
import sys.tags.CmTag;
import sys.tool.tree.TreeNode;

import java.util.*;

@Service
public class CetPartyService extends CetBaseMapper {

    @Autowired
    private CetPartyAdminService cetPartyAdminService;

    public boolean idDuplicate(Integer id, Integer partyId) {

        if(partyId==null) return false;

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

    //同步基层党组织管理员
    @Transactional
    public void batchSync(Integer[] cetPartyIds) {

        CetPartyExample example = new CetPartyExample();
        CetPartyExample.Criteria criteria =
                example.createCriteria().andPartyIdIsNotNull() // 仅同步关联了基层党组织的党委管理员
                        .andIsDeletedEqualTo(false); // 仅同步现任党委

        if(cetPartyIds!=null && cetPartyIds.length>0){
            criteria.andIdIn(Arrays.asList(cetPartyIds)); // 限定某些党委同步
        }

        List<CetParty> cetParties = cetPartyMapper.selectByExample(example);

        for (CetParty cetParty : cetParties) {

            int id = cetParty.getId();

            // 先删除“非普通管理员”
            CetPartyAdminExample example1 = new CetPartyAdminExample();
            example1.createCriteria().andCetPartyIdEqualTo(id)
                    .andTypeNotEqualTo(CetConstants.CET_PARTY_ADMIN_NORMAL);
            List<CetPartyAdmin> cetPartyAdmins = cetPartyAdminMapper.selectByExample(example1);
            for (CetPartyAdmin cetPartyAdmin : cetPartyAdmins) {
                cetPartyAdminMapper.deleteByPrimaryKey(cetPartyAdmin.getId());
                cetPartyAdminService.updateRoleCetAdminParty(cetPartyAdmin.getUserId());
            }

            // 再同步基层党组织管理员（含普通管理员）
            int partyId = cetParty.getPartyId();
            OwAdmin search = new OwAdmin();
            search.setPartyId(partyId);
            List<OwAdmin> owAdmins = iPartyMapper.selectPartyAdminList(search, new RowBounds());
            for (OwAdmin owAdmin : owAdmins) {

                int userId = owAdmin.getUserId();
                cetPartyAdminService.insertOrUpdate(cetParty.getId(), userId);
            }
        }
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
