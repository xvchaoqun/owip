package service.cis;

import domain.cis.CisObjUnit;
import domain.cis.CisObjUnitExample;
import domain.unit.Unit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;
import service.unit.UnitService;
import sys.constants.SystemConstants;
import sys.tool.tree.TreeNode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class CisObjUnitService extends BaseMapper {

    @Autowired
    private UnitService unitService;

    public TreeNode getTree(Set<Integer> selectIdSet) {

        if (null == selectIdSet) selectIdSet = new HashSet<>();

        TreeNode root = new TreeNode();
        root.title = "请选择单位";
        root.expand = true;
        root.isFolder = true;
        root.hideCheckbox = true;
        List<TreeNode> rootChildren = new ArrayList<TreeNode>();
        root.children = rootChildren;

        List<Unit> units = unitService.findUnitByTypeAndStatus(null, SystemConstants.UNIT_STATUS_RUN);

        for (Unit unit : units) {

            TreeNode node = new TreeNode();
            node.title = unit.getName() + "-" + unit.getCode();
            node.key = unit.getId() + "";

            if (selectIdSet.contains(unit.getId())) {
                node.select = true;
            }
            rootChildren.add(node);
        }

        return root;
    }

    @Transactional
    public void insertSelective(CisObjUnit record) {

        cisObjUnitMapper.insertSelective(record);
    }

    @Transactional
    public void del(Integer id) {

        cisObjUnitMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids) {

        if (ids == null || ids.length == 0) return;

        CisObjUnitExample example = new CisObjUnitExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        cisObjUnitMapper.deleteByExample(example);
    }

    @Transactional
    public int updateByPrimaryKeySelective(CisObjUnit record) {
        return cisObjUnitMapper.updateByPrimaryKeySelective(record);
    }
}
