package service.dr;

import domain.dr.*;
import domain.sys.SysUserView;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sys.constants.DrConstants;
import sys.tool.tree.TreeNode;

import java.util.*;

@Service
public class DrOfflineService extends DrBaseMapper {

    @Autowired
    private DrMemberService drMemberService;

    public TreeNode getSelectMemberTree(Set<Integer> selectIdSet) {

        if (null == selectIdSet) selectIdSet = new HashSet<>();

        TreeNode root = new TreeNode();
        root.title = "请选择";
        root.expand = true;
        root.isFolder = true;
        root.hideCheckbox = true;
        List<TreeNode> rootChildren = new ArrayList<TreeNode>();
        root.children = rootChildren;
        for (Map.Entry<Byte, String> entry : DrConstants.DR_MEMBER_STATUS_MAP.entrySet()) {
            TreeNode groupNode = new TreeNode();
            groupNode.title = entry.getValue();
            groupNode.expand = true;
            groupNode.isFolder = true;
            groupNode.hideCheckbox = true;
            List<TreeNode> children = new ArrayList<TreeNode>();
            groupNode.children = children;

            List<DrMember> drMembers = drMemberService.getMembers(entry.getKey());

            for (DrMember drMember : drMembers) {

                SysUserView uv = drMember.getUser();
                TreeNode node = new TreeNode();
                node.title = uv.getRealname() + "-" + uv.getCode();
                node.key = drMember.getId() + "";

                if (selectIdSet.contains(drMember.getId())) {
                    node.select = true;
                }
                children.add(node);
            }

            rootChildren.add(groupNode);
        }

        return root;
    }

    /*public boolean idDuplicate(Integer id, String code){

        Assert.isTrue(StringUtils.isNotBlank(code), "null");

        DrOfflineExample example = new DrOfflineExample();
        DrOfflineExample.Criteria criteria = example.createCriteria().andCodeEqualTo(code).andStatusEqualTo(true);
        if(id!=null) criteria.andIdNotEqualTo(id);

        return drOfflineMapper.countByExample(example) > 0;
    }*/

    public int getNextSeq(Date recommendDate) {

        DrOfflineExample example = new DrOfflineExample();
        DrOfflineExample.Criteria criteria = example.createCriteria()
                .andRecommendDateEqualTo(recommendDate);
        example.setOrderByClause("seq desc");

        List<DrOffline> records = drOfflineMapper.selectByExampleWithRowbounds(example, new RowBounds(0, 1));

        int seq = 1;
        if(records.size()>0) {
            DrOffline record = records.get(0);
            seq = record.getSeq() + 1;
        }

        return seq;
    }

    @Transactional
    public void insertSelective(DrOffline record){

        record.setSeq(getNextSeq(record.getRecommendDate()));
        drOfflineMapper.insertSelective(record);
    }

    @Transactional
    public void del(Integer id){

        drOfflineMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        DrOfflineExample example = new DrOfflineExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        drOfflineMapper.deleteByExample(example);
    }

    @Transactional
    public void updateByPrimaryKeySelective(DrOffline record){

        drOfflineMapper.updateByPrimaryKeySelective(record);
        if(BooleanUtils.isFalse(record.getNeedVoterType())){
            int offlineId = record.getId();
            commonMapper.excuteSql("update dr_offline set voter_type_tpl_id=null, voters=null where id=" + offlineId);
            commonMapper.excuteSql("update dr_offline_candidate set weight=vote, voters=null where offline_id="+ offlineId);
        }
    }
}
