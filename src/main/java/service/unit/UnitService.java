package service.unit;

import controller.global.OpException;
import domain.base.MetaType;
import domain.unit.Unit;
import domain.unit.UnitExample;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import service.BaseMapper;
import service.base.MetaTypeService;
import sys.constants.SystemConstants;
import sys.tool.tree.TreeNode;
import sys.utils.DateUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class UnitService extends BaseMapper {

    @Autowired
    protected MetaTypeService metaTypeService;

    public List<Unit> findUnitByTypeAndStatus(Integer type, byte status){

        UnitExample example = new UnitExample();
        UnitExample.Criteria criteria = example.createCriteria().andStatusEqualTo(status);
        if(type!=null)
            criteria.andTypeIdEqualTo(type);
        example.setOrderByClause(" sort_order asc");
        return unitMapper.selectByExample(example);
    }

    public Unit findUnitByCode(String code){

        UnitExample example = new UnitExample();
        example.createCriteria().andCodeEqualTo(code);
        List<Unit> units = unitMapper.selectByExample(example);
        if(units.size()>0) return units.get(0);
        return null;
    }

    // 查找正在运转单位
    public List<Unit> findRunUnits(int unitId){

        return iUnitMapper.findRunUnits(unitId);

    }
    // 查找历史单位
    public List<Unit> findHistoryUnits(int unitId){

        return iUnitMapper.findHistoryUnits(unitId);
    }

    // unSelectIdSet:不可选的
    public TreeNode getTree(Byte status, Set<Integer> unSelectIdSet){

        if(null == unSelectIdSet) unSelectIdSet = new HashSet<>();

        TreeNode root = new TreeNode();
        root.title = "单位";
        root.expand = true;
        root.isFolder = true;
        root.hideCheckbox = true;
        List<TreeNode> rootChildren = new ArrayList<TreeNode>();
        root.children = rootChildren;

        Map<Integer, MetaType> typeMap = metaTypeService.metaTypes("mc_unit_type");
        // 类型名称-单位
        Map<String, List<Unit>> unitMap = new LinkedHashMap<>();

        UnitExample example = new UnitExample();
        UnitExample.Criteria criteria = example.createCriteria();
        if(status!=null){
            criteria.andStatusEqualTo(status);
        }
        example.setOrderByClause(" sort_order asc");
        List<Unit> units = unitMapper.selectByExample(example);
        for (Unit unit : units) {
            List<Unit> list = null;
            MetaType metaType = typeMap.get(unit.getTypeId());
            String type = metaType.getName();
            if (unitMap.containsKey(type)) {
                list = unitMap.get(type);
            }
            if (null == list) list = new ArrayList<>();
            list.add(unit);

            unitMap.put(type, list);
        }

        for (Map.Entry<String, List<Unit>> entry : unitMap.entrySet()) {

            TreeNode titleNode = new TreeNode();
            titleNode.title = entry.getKey();
            titleNode.expand = false;
            titleNode.isFolder = true;
            List<TreeNode> titleChildren = new ArrayList<TreeNode>();
            titleNode.children = titleChildren;

            int selectableCount = 0;
            for (Unit unit : entry.getValue()) {

                TreeNode node = new TreeNode();
                node.title = unit.getName();
                node.key = unit.getId() + "";
                if(unit.getStatus()==SystemConstants.UNIT_STATUS_HISTORY)
                    node.addClass = "delete";

                if (unSelectIdSet.contains(unit.getId().intValue())) {
                    node.hideCheckbox = true;
                    node.unselectable = true;
                }else{
                    selectableCount++;
                }
                titleChildren.add(node);
            }
            if(selectableCount==0){
                titleNode.hideCheckbox = true;
                titleNode.unselectable = true;
            }
            rootChildren.add(titleNode);
        }

        return root;
    }

    public boolean idDuplicate(Integer id, String code){

        Assert.isTrue(StringUtils.isNotBlank(code), "code is blank");

        UnitExample example = new UnitExample();
        UnitExample.Criteria criteria = example.createCriteria().andCodeEqualTo(code)/*.andStatusEqualTo(SystemConstants.UNIT_STATUS_RUN)*/;
        if(id!=null) criteria.andIdNotEqualTo(id);

        return unitMapper.countByExample(example) > 0;
    }

    @Transactional
    @CacheEvict(value="Unit:ALL", allEntries = true)
    public int insertSelective(Unit record){

        record.setSortOrder(getNextSortOrder("unit", "status=" + record.getStatus()));
        return unitMapper.insertSelective(record);
    }
    @Transactional
    @CacheEvict(value="Unit:ALL", allEntries = true)
    public void del(Integer id){

        unitMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    @CacheEvict(value="Unit:ALL", allEntries = true)
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        UnitExample example = new UnitExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        unitMapper.deleteByExample(example);
    }

    @Transactional
    @CacheEvict(value="Unit:ALL", allEntries = true)
    public void abolish(Integer[] ids){
        if(ids==null || ids.length==0) return;

        for (Integer id : ids) {
            Unit record = new Unit();
            record.setId(id);
            record.setSortOrder(getNextSortOrder("unit", "status=" + SystemConstants.UNIT_STATUS_HISTORY));
            record.setStatus(SystemConstants.UNIT_STATUS_HISTORY);
            unitMapper.updateByPrimaryKeySelective(record);
        }
        /*UnitExample example = new UnitExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));

        Unit record = new Unit();
        record.setStatus(SystemConstants.UNIT_STATUS_HISTORY);
        unitMapper.updateByExampleSelective(record, example);*/
    }

    @Transactional
    @CacheEvict(value="Unit:ALL", allEntries = true)
    public int updateByPrimaryKeySelective(Unit record){

        return unitMapper.updateByPrimaryKeySelective(record);
    }

    @Cacheable(value="Unit:ALL")
    public Map<Integer, Unit> findAll() {

        UnitExample example = new UnitExample();
        example.createCriteria()/*.andStatusEqualTo(SystemConstants.UNIT_STATUS_RUN)*/;
        example.setOrderByClause("sort_order asc");
        List<Unit> unites = unitMapper.selectByExample(example);
        Map<Integer, Unit> map = new LinkedHashMap<>();
        for (Unit unit : unites) {
            map.put(unit.getId(), unit);
        }

        return map;
    }

    /**
     * 排序 ，要求 1、sort_order>0且不可重复  2、sort_order 降序排序
     * 3.sort_order = LAST_INSERT_ID()+1,
     * @param id
     * @param addNum
     */
    @Transactional
    @CacheEvict(value = "Unit:ALL", allEntries = true)
    public void changeOrder(int id, byte status, int addNum) {

        if(addNum == 0) return ;

        Unit entity = unitMapper.selectByPrimaryKey(id);
        Integer baseSortOrder = entity.getSortOrder();

        UnitExample example = new UnitExample();
        if (addNum < 0) {

            example.createCriteria().andStatusEqualTo(status).andSortOrderGreaterThan(baseSortOrder);
            example.setOrderByClause("sort_order asc");
        }else {

            example.createCriteria().andStatusEqualTo(status).andSortOrderLessThan(baseSortOrder);
            example.setOrderByClause("sort_order desc");
        }

        List<Unit> overEntities = unitMapper.selectByExampleWithRowbounds(example, new RowBounds(0, Math.abs(addNum)));
        if(overEntities.size()>0) {

            Unit targetEntity = overEntities.get(overEntities.size()-1);

            if (addNum < 0)
                commonMapper.downOrder("unit", "status=" + status, baseSortOrder, targetEntity.getSortOrder());
            else
                commonMapper.upOrder("unit", "status=" + status, baseSortOrder, targetEntity.getSortOrder());

            Unit record = new Unit();
            record.setId(id);
            record.setSortOrder(targetEntity.getSortOrder());
            unitMapper.updateByPrimaryKeySelective(record);
        }
    }

    // 导入单位
    @Transactional
    @CacheEvict(value="Unit:ALL", allEntries = true)
    public Map<String, Object> batchImport(List<Map<Integer, String>> xlsRows) {

            int success = 0;
            List<Map<Integer, String>> failedXlsRows = new ArrayList<>();

            int row = 1;
            for (Map<Integer, String> xlsRow : xlsRows) {

                row++;
                String code = StringUtils.trim(xlsRow.get(0));
                String name = StringUtils.trim(xlsRow.get(1));
                String type = StringUtils.trim(xlsRow.get(2));
                if (StringUtils.isBlank(code)
                        || StringUtils.isBlank(name)
                        || StringUtils.isBlank(type)){
                    failedXlsRows.add(xlsRow);
                    throw new OpException("第{0}行数据有误[空字段]", row);
                }

                MetaType unitType = metaTypeService.findByName("mc_unit_type", type);
                if(unitType==null){
                    failedXlsRows.add(xlsRow);
                    throw new OpException("第{0}行单位类型[{1}]不存在", row, type);
                }

                Unit _unit = findUnitByCode(code);

                Unit record = new Unit();
                record.setCode(code);
                record.setName(name);
                record.setTypeId(unitType.getId());
                String workTime = xlsRow.get(3);
                //System.out.println(workTime);
                if(StringUtils.isNotBlank(workTime)) {
                    record.setWorkTime(DateUtils.parseDate(workTime, DateUtils.YYYY_MM_DD));
                }
                record.setUrl(xlsRow.get(4));
                record.setRemark(xlsRow.get(5));
                int ret = 0;
                if(_unit==null) {
                    record.setCreateTime(new Date());
                    record.setStatus(SystemConstants.UNIT_STATUS_RUN);
                    record.setSortOrder(getNextSortOrder("unit", "status=" + SystemConstants.UNIT_STATUS_RUN));
                    ret = insertSelective(record);
                }else{
                    record.setId(_unit.getId());
                    ret = updateByPrimaryKeySelective(record);
                }

                if(ret==1) {
                    success++;
                }else{
                    failedXlsRows.add(xlsRow);
                }
            }

            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("success", success);
            resultMap.put("failedXlsRows", failedXlsRows);
            return resultMap;
    }
}
