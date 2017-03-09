package service.cis;

import domain.cis.CisInspectorView;
import domain.cis.CisObjInspector;
import domain.cis.CisObjInspectorExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;
import sys.constants.SystemConstants;
import sys.tool.tree.TreeNode;

import java.util.*;

@Service
public class CisObjInspectorService extends BaseMapper {

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
        for (Map.Entry<Byte, String> entry : SystemConstants.CIS_INSPECTOR_STATUS_MAP.entrySet()) {
            TreeNode groupNode = new TreeNode();
            groupNode.title = entry.getValue();
            groupNode.expand = true;
            groupNode.isFolder = true;
            groupNode.hideCheckbox = true;
            List<TreeNode> children = new ArrayList<TreeNode>();
            groupNode.children = children;

            List<CisInspectorView> nowInspectors = cisInspectorService.getNowInspectors(entry.getKey());

            for (CisInspectorView inspector : nowInspectors) {

                TreeNode node = new TreeNode();
                node.title = inspector.getRealname() + "-" + inspector.getCode();
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
