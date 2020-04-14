package service.dr;

import domain.dr.DrOnlineInspectorType;
import domain.dr.DrOnlineInspectorTypeExample;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import sys.tool.tree.TreeNode;

import java.util.*;

@Service
public class DrOnlineInspectorTypeService extends DrBaseMapper {

    public boolean idDuplicate(Integer id, String code){

        Assert.isTrue(StringUtils.isNotBlank(code), "null");

        DrOnlineInspectorTypeExample example = new DrOnlineInspectorTypeExample();
        DrOnlineInspectorTypeExample.Criteria criteria = example.createCriteria();
        if(id!=null) criteria.andIdNotEqualTo(id);

        return drOnlineInspectorTypeMapper.countByExample(example) > 0;
    }

    @Transactional
    public void insertSelective(DrOnlineInspectorType record){

        record.setSortOrder(getNextSortOrder("dr_online_inspector_type", null));
        drOnlineInspectorTypeMapper.insertSelective(record);
    }

    @Transactional
    public void changeStatus(Byte status, Integer[] ids){

        for (Integer id : ids) {
            DrOnlineInspectorType record = findAll().get(id);
            record.setStatus(status);
            drOnlineInspectorTypeMapper.updateByPrimaryKey(record);
        }
    }

    @Transactional
    public void del(Integer id){

        drOnlineInspectorTypeMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        DrOnlineInspectorTypeExample example = new DrOnlineInspectorTypeExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        drOnlineInspectorTypeMapper.deleteByExample(example);
    }

    @Transactional
    public void updateByPrimaryKeySelective(DrOnlineInspectorType record){

        drOnlineInspectorTypeMapper.updateByPrimaryKeySelective(record);
    }

    public Map<Integer, DrOnlineInspectorType> findAll() {

        DrOnlineInspectorTypeExample example = new DrOnlineInspectorTypeExample();
        example.createCriteria();
        example.setOrderByClause("sort_order asc");
        List<DrOnlineInspectorType> records = drOnlineInspectorTypeMapper.selectByExample(example);
        Map<Integer, DrOnlineInspectorType> map = new LinkedHashMap<>();
        for (DrOnlineInspectorType record : records) {
            map.put(record.getId(), record);
        }

        return map;
    }
    /**
     * 排序 ，要求 1、sort_order>0且不可重复  2、sort_order 降序排序
     * @param id
     * @param addNum
     */
    @Transactional
    public void changeOrder(int id, int addNum) {

        changeOrder("dr_online_inspector_type", null, ORDER_BY_DESC, id, addNum);
    }

    //参评人类型树
    public TreeNode getTree(Collection<DrOnlineInspectorType> drOnlineInspectorTypes,  Set<Integer> selectIdSet){

        if (null == selectIdSet) selectIdSet = new HashSet<Integer>();

        TreeNode root = new TreeNode();
        root.title = "参评人身份";
        root.expand = true;
        root.isFolder = true;
        root.hideCheckbox = true;
        List<TreeNode> rootChildren = new ArrayList<TreeNode>();

        root.children = rootChildren;

        if (drOnlineInspectorTypes == null || drOnlineInspectorTypes.size() <= 0) return root;

        for (DrOnlineInspectorType drOnlineInspectorType : drOnlineInspectorTypes){
            TreeNode node = new TreeNode();
            node.title = drOnlineInspectorType.getType();
            node.key = drOnlineInspectorType.getId() + "";
            if (selectIdSet.contains(drOnlineInspectorType.getId().intValue())){
                node.select = true;
            }
            rootChildren.add(node);
        }
        return root;
    }
}
