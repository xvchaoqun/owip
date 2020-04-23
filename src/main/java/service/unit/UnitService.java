package service.unit;

import controller.global.OpException;
import domain.base.MetaType;
import domain.unit.Unit;
import domain.unit.UnitExample;
import org.apache.commons.lang.StringUtils;
import org.olap4j.impl.ArrayMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import service.BaseMapper;
import service.base.MetaTypeService;
import service.global.CacheHelper;
import sys.constants.SystemConstants;
import sys.tool.tree.TreeNode;
import sys.utils.DateUtils;

import java.util.*;

@Service
public class UnitService extends BaseMapper {

    public static final String TABLE_NAME = "unit";

    @Autowired
    protected MetaTypeService metaTypeService;
    @Autowired
    protected CacheHelper cacheHelper;

    public List<Unit> findUnitByTypeAndStatus(Integer type, byte status) {

        UnitExample example = new UnitExample();
        UnitExample.Criteria criteria = example.createCriteria().andStatusEqualTo(status);
        if (type != null)
            criteria.andTypeIdEqualTo(type);
        example.setOrderByClause(" sort_order asc");
        return unitMapper.selectByExample(example);
    }

    // 查找正在运行单位
    public Unit findRunUnitByCode(String code) {

        UnitExample example = new UnitExample();
        example.createCriteria().andCodeEqualTo(code).andStatusEqualTo(SystemConstants.UNIT_STATUS_RUN);
        List<Unit> units = unitMapper.selectByExample(example);
        if (units.size() > 0) return units.get(0);
        return null;
    }
    // 查找第一个历史的运行单位，用于导入
    public Unit findHistoryUnitByCode(String code, String name) {

        UnitExample example = new UnitExample();
        example.createCriteria().andCodeEqualTo(code)
                .andStatusEqualTo(SystemConstants.UNIT_STATUS_HISTORY)
                .andNameEqualTo(name);
        List<Unit> units = unitMapper.selectByExample(example);
        if (units.size() > 0) return units.get(0);
        return null;
    }

    // 查找正在运转单位
    public List<Unit> findRunUnits(int unitId) {

        return iUnitMapper.findRunUnits(unitId);

    }

    // 查找历史单位
    public List<Unit> findHistoryUnits(int unitId) {

        return iUnitMapper.findHistoryUnits(unitId);
    }

    // unSelectIdSet:不可选的
    public TreeNode getTree(Byte status, Set<Integer> unSelectIdSet) {

        if (null == unSelectIdSet) unSelectIdSet = new HashSet<>();

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
        if (status != null) {
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
                if (unit.getStatus() == SystemConstants.UNIT_STATUS_HISTORY)
                    node.addClass = "delete";

                if (unSelectIdSet.contains(unit.getId().intValue())) {
                    node.hideCheckbox = true;
                    node.unselectable = true;
                } else {
                    selectableCount++;
                }
                titleChildren.add(node);
            }
            if (selectableCount == 0) {
                titleNode.hideCheckbox = true;
                titleNode.unselectable = true;
            }
            rootChildren.add(titleNode);
        }

        return root;
    }

    public boolean idDuplicate(Integer id, String code, byte status) {

        Assert.isTrue(StringUtils.isNotBlank(code), "code is blank");

        if(status==SystemConstants.UNIT_STATUS_HISTORY){
            // 历史单位的单位编码允许重复
            return false;
        }

        UnitExample example = new UnitExample();
        UnitExample.Criteria criteria = example.createCriteria().andCodeEqualTo(code);


        if (id != null){
            criteria.andIdNotEqualTo(id);
        }

        return unitMapper.countByExample(example) > 0;
    }

    @Transactional
    @CacheEvict(value = "Unit:ALL", allEntries = true)
    public int insertSelective(Unit record) {

        record.setSortOrder(getNextSortOrder("unit", "status=" + record.getStatus()));
        return unitMapper.insertSelective(record);
    }

    @Transactional
    @CacheEvict(value = "Unit:ALL", allEntries = true)
    public void del(Integer id) {

        unitMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    @CacheEvict(value = "Unit:ALL", allEntries = true)
    public void batchDel(Integer[] ids) {

        if (ids == null || ids.length == 0) return;

        UnitExample example = new UnitExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        unitMapper.deleteByExample(example);
    }

    @Transactional
    @CacheEvict(value = "Unit:ALL", allEntries = true)
    public void abolish(Integer[] ids, boolean isAbolish) {
        if (ids == null || ids.length == 0) return;

        Map<Integer,Unit> unitMap=findAll();
        for (Integer id : ids) {
            Unit unit = unitMap.get(id);
            if (idDuplicate(id, unit.getCode(), isAbolish?SystemConstants.UNIT_STATUS_HISTORY:SystemConstants.UNIT_STATUS_RUN)) {
                throw new OpException("单位编码重复(" + unitMap.get(id).getCode() + ")");
            }

            Unit record = new Unit();
            record.setId(id);
            if (isAbolish) {
                record.setSortOrder(getNextSortOrder("unit", "status=" + SystemConstants.UNIT_STATUS_HISTORY));
                record.setStatus(SystemConstants.UNIT_STATUS_HISTORY);
            } else {
                record.setSortOrder(getNextSortOrder("unit", "status=" + SystemConstants.UNIT_STATUS_RUN));
                record.setStatus(SystemConstants.UNIT_STATUS_RUN);
            }
            unitMapper.updateByPrimaryKeySelective(record);
        }
    }

    @Transactional
    @CacheEvict(value = "Unit:ALL", allEntries = true)
    public int updateByPrimaryKeySelective(Unit record) {

        return unitMapper.updateByPrimaryKeySelective(record);
    }

    //正在运转和历史单位
    @Cacheable(value = "Unit:ALL")
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
     *
     * @param id
     * @param addNum
     */
    @Transactional
    @CacheEvict(value = "Unit:ALL", allEntries = true)
    public void changeOrder(int id, int addNum) {

        Unit unit = unitMapper.selectByPrimaryKey(id);
        changeOrder("unit", "status=" + unit.getStatus(), ORDER_BY_ASC, id, addNum);
    }

    // 导入单位
    @Transactional
    @CacheEvict(value = "Unit:ALL", allEntries = true)
    public Map<String, Object> batchImport(List<Map<Integer, String>> xlsRows, byte status) {

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
                    || StringUtils.isBlank(type)) {
                failedXlsRows.add(xlsRow);
                throw new OpException("第{0}行数据有误：单位编码或名称或类型为空", row);
            }

            MetaType unitType = metaTypeService.findByName("mc_unit_type", type);
            if (unitType == null) {
                failedXlsRows.add(xlsRow);
                throw new OpException("第{0}行单位类型[{1}]不存在", row, type);
            }

            Unit _unit = null;

            if(status==SystemConstants.UNIT_STATUS_RUN) {
                // 如果是导入现运行单位，检查是否是更新操作。
                _unit = findRunUnitByCode(code);
            }else{
                // 如果是导入历史单位，则只根据单位名称和编码查找第一个匹配的
                _unit = findHistoryUnitByCode(code, name);
            }

            Unit record = new Unit();
            record.setCode(code);
            record.setName(name);
            record.setTypeId(unitType.getId());
            String workTime = xlsRow.get(3);
            record.setWorkTime(DateUtils.parseStringToDate(workTime));

            record.setUrl(xlsRow.get(4));
            record.setRemark(xlsRow.get(5));
            int ret = 0;
            if (_unit == null) {
                record.setCreateTime(new Date());
                record.setStatus(status);
                record.setSortOrder(getNextSortOrder("unit", "status=" + status));
                ret = insertSelective(record);
            } else {
                record.setId(_unit.getId());
                ret = updateByPrimaryKeySelective(record);
            }

            if (ret == 1) {
                success++;
            } else {
                failedXlsRows.add(xlsRow);
            }
        }

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("success", success);
        resultMap.put("failedXlsRows", failedXlsRows);
        return resultMap;
    }

    // 批量导入更新单位编码
    @Transactional
    @CacheEvict(value = "Unit:ALL", allEntries = true)
    public Map<String, Object> batchImportCodes(List<Map<Integer, String>> xlsRows) {

        int success = 0;
        int row = 1;
        for (Map<Integer, String> xlsRow : xlsRows) {

            row++;
            String code = StringUtils.trim(xlsRow.get(0));
            String newCode = StringUtils.trim(xlsRow.get(2));
            if (StringUtils.isBlank(code) || StringUtils.isBlank(newCode)) {

                throw new OpException("第{0}行数据有误[编码为空]", row);
            }

            Unit unit = findRunUnitByCode(code);
            if (unit != null) {

                Unit _unit = findRunUnitByCode(newCode);
                if (_unit != null) {
                    throw new OpException("第{0}行数据有误，编码重复[{1}]。", row, newCode);
                }

                Unit record = new Unit();
                record.setId(unit.getId());
                record.setCode(newCode);

                unitMapper.updateByPrimaryKeySelective(record);

                success++;
            }
        }

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("success", success);
        return resultMap;
    }

    @Transactional
    @CacheEvict(value = "Unit:ALL", allEntries = true)
    public void unit_batchSort(byte status, List<Integer> unitIdList){

        commonMapper.excuteSql("update unit set sort_order=null where status=" + status
                + " and id in(" + StringUtils.join(unitIdList, ",") + ")");
        for (Integer unitId : unitIdList){
            Unit record= new Unit();
            record.setId(unitId);
            record.setSortOrder(getNextSortOrder(TABLE_NAME,"status=" + status));

            unitMapper.updateByPrimaryKeySelective(record);
        }
    }

    //线上民主推荐 可选单位
    public TreeNode getTree(Set<Integer> selectIdSet){

        if (null == selectIdSet) selectIdSet = new HashSet<>();

        TreeNode root = new TreeNode();
        root.title = "单位";
        root.expand = true;
        root.isFolder = true;
        root.hideCheckbox = true;
        List<TreeNode> rootChildren = new ArrayList<>();
        root.children = rootChildren;

        //Map<Integer, String> unitTypes = getUnitType();
        //Map<类型名称, 单位>
        Map<String, List<Unit>> unitMap = new LinkedHashMap<>();

        UnitExample example = new UnitExample();
        example.createCriteria().andStatusEqualTo(SystemConstants.UNIT_STATUS_RUN);
        example.setOrderByClause(" sort_order asc");
        List<Unit> units = unitMapper.selectByExample(example);
        for (Unit unit : units){

            List<Unit> list = null;
            String unitType = metaTypeService.getName(unit.getTypeId());
            if (unitMap.containsKey(unitType)){
                list = unitMap.get(unitType);
            }
            if (null == list) list = new ArrayList<>();
            list.add(unit);

            unitMap.put(unitType, list);
        }

        for (Map.Entry<String, List<Unit>> entry : unitMap.entrySet()){
            TreeNode titleNode = new TreeNode();
            titleNode.title = entry.getKey();
            titleNode.expand = false;
            titleNode.isFolder = true;
            List<TreeNode> titleChildren = new ArrayList<>();
            titleNode.children = titleChildren;

            for (Unit unit : entry.getValue()){
                TreeNode node = new TreeNode();
                node.title = unit.getName();
                node.key = unit.getId() + "";
                if (selectIdSet.contains(unit.getId().intValue())){
                    node.select = true;
                }
                titleChildren.add(node);
            }
            rootChildren.add(titleNode);
        }
        return root;
    }

    public Map<Integer, String> getUnitType(){

        Map<Integer, MetaType> metaTypeMap =  metaTypeService.metaTypes("mc_unit_type");
        Map<Integer, String> unitTypes = new ArrayMap<>();
        for (Map.Entry<Integer, MetaType> entry : metaTypeMap.entrySet()){
            unitTypes.put(entry.getValue().getId(), entry.getValue().getName());
        }

        return unitTypes;
    }

    //获得正在运转的单位
    public Map<Integer, Unit> getRunAll() {

        UnitExample example = new UnitExample();
        example.createCriteria().andStatusEqualTo(SystemConstants.UNIT_STATUS_RUN);
        example.setOrderByClause("sort_order asc");
        List<Unit> unites = unitMapper.selectByExample(example);
        Map<Integer, Unit> map = new LinkedHashMap<>();
        for (Unit unit : unites) {
            map.put(unit.getId(), unit);
        }

        return map;
    }

    public List<Integer> getUnitIdsLikeUnitName(List<Unit> units){

        List<Integer> unitIds = new ArrayList<>();
        for (Unit unit : units){
            unitIds.add(unit.getId());
        }

        return unitIds;
    }

    @Transactional
    public void changeNotStatPost(Integer[] ids, Boolean notStatPost){

        UnitExample example = new UnitExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));

        Unit unit = new Unit();
        unit.setNotStatPost(notStatPost);
        unitMapper.updateByExampleSelective(unit,example);

        cacheHelper.clearCache("Unit:ALL",null);
    }
}
