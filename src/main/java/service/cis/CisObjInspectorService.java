package service.cis;

import domain.cis.CisInspector;
import domain.cis.CisObjInspector;
import domain.cis.CisObjInspectorExample;
import domain.sys.SysUserView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sys.constants.CisConstants;
import sys.tool.tree.TreeNode;

import java.util.*;

@Service
public class CisObjInspectorService extends CisBaseMapper {

    @Autowired
    private CisInspectorService cisInspectorService;

    public TreeNode getTree(Set<Integer> selectIdSet) {

        if (null == selectIdSet) selectIdSet = new HashSet<>();

        TreeNode root = new TreeNode();
        root.title = "请选择";
        root.expand = true;
        root.isFolder = true;
        root.hideCheckbox = true;
        List<TreeNode> rootChildren = new ArrayList<TreeNode>();
        root.children = rootChildren;
        for (Map.Entry<Byte, String> entry : CisConstants.CIS_INSPECTOR_STATUS_MAP.entrySet()) {
            TreeNode groupNode = new TreeNode();
            groupNode.title = entry.getValue();
            groupNode.expand = true;
            groupNode.isFolder = true;
            groupNode.hideCheckbox = true;
            List<TreeNode> children = new ArrayList<TreeNode>();
            groupNode.children = children;

            List<CisInspector> inspectors = cisInspectorService.getInspectors(entry.getKey());

            for (CisInspector inspector : inspectors) {

                SysUserView uv = inspector.getUser();
                TreeNode node = new TreeNode();
                node.title = uv.getRealname() + "-" + uv.getCode();
                node.key = inspector.getId() + "";

                if (selectIdSet.contains(inspector.getId())) {
                    node.select = true;
                }
                children.add(node);
            }

            rootChildren.add(groupNode);
        }

        return root;
    }

    @Transactional
    public void updateInspectIds(int objId, Integer[] inspectorIds){

        CisObjInspectorExample example = new CisObjInspectorExample();
        example.createCriteria().andObjIdEqualTo(objId);
        cisObjInspectorMapper.deleteByExample(example);

        if(inspectorIds==null || inspectorIds.length==0) return ;

        for (Integer inspectorId : inspectorIds) {

            CisObjInspector record = new CisObjInspector();
            record.setObjId(objId);
            record.setInspectorId(inspectorId);
            cisObjInspectorMapper.insert(record);
        }
    }

    @Transactional
    public void insertSelective(CisObjInspector record) {

        cisObjInspectorMapper.insertSelective(record);
    }

    @Transactional
    public void del(Integer id) {

        cisObjInspectorMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids) {

        if (ids == null || ids.length == 0) return;

        CisObjInspectorExample example = new CisObjInspectorExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        cisObjInspectorMapper.deleteByExample(example);
    }

    @Transactional
    public int updateByPrimaryKeySelective(CisObjInspector record) {

        return cisObjInspectorMapper.updateByPrimaryKeySelective(record);
    }
}
