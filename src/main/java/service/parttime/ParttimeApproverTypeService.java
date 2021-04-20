package service.parttime;

import domain.base.MetaType;
import domain.parttime.*;
import domain.unit.Unit;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import service.base.MetaTypeService;
import service.cadre.CadreService;
import service.sys.SysUserService;
import service.unit.UnitService;
import sys.constants.ParttimeConstants;
import sys.tool.tree.TreeNode;

import java.util.*;

@Service
public class ParttimeApproverTypeService extends ParttimeBaseMapper {

    @Autowired
    private MetaTypeService metaTypeService;
    @Autowired
    private CadreService cadreService;
    @Autowired
    private UnitService unitService;
    @Autowired
    private SysUserService sysUserService;

    @Transactional
    @Caching(evict= {
            @CacheEvict(value = "UserPermissions", allEntries = true),
            @CacheEvict(value = "parttimeApproverType:ALL", allEntries = true)
    })
    public void del(Integer id){
        parttimeApproverTypeMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    @Caching(evict= {
            @CacheEvict(value = "UserPermissions", allEntries = true),
            @CacheEvict(value = "parttimeApproverType:ALL", allEntries = true)
    })
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        ParttimeApproverTypeExample example = new ParttimeApproverTypeExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        parttimeApproverTypeMapper.deleteByExample(example);
    }

    /**
     * 排序 ，要求 1、sort_order>0且不可重复  2、sort_order 降序排序
     * @param id
     * @param addNum
     */
    @Transactional
    @CacheEvict(value = "parttimeApproverType:ALL", allEntries = true)
    public void changeOrder(int id, int addNum) {

        if(addNum == 0) return ;

        ParttimeApproverType entity = parttimeApproverTypeMapper.selectByPrimaryKey(id);
        Integer baseSortOrder = entity.getSortOrder();
        ParttimeApproverTypeExample example = new ParttimeApproverTypeExample();
        if (addNum > 0) {
            example.createCriteria().andSortOrderGreaterThan(baseSortOrder);
            example.setOrderByClause("sort_order asc");
        }else {
            example.createCriteria().andSortOrderLessThan(baseSortOrder);
            example.setOrderByClause("sort_order desc");
        }

        List<ParttimeApproverType> overEntities = parttimeApproverTypeMapper.selectByExampleWithRowbounds(example, new RowBounds(0, Math.abs(addNum)));
        if(overEntities.size()>0) {

            ParttimeApproverType targetEntity = overEntities.get(overEntities.size()-1);

            if (addNum > 0)
                commonMapper.downOrder("parttime_approver_type", null, baseSortOrder, targetEntity.getSortOrder());
            else
                commonMapper.upOrder("parttime_approver_type", null, baseSortOrder, targetEntity.getSortOrder());

            ParttimeApproverType record = new ParttimeApproverType();
            record.setId(id);
            record.setSortOrder(targetEntity.getSortOrder());
            parttimeApproverTypeMapper.updateByPrimaryKeySelective(record);
        }
    }

    public boolean idDuplicate(Integer id, String name, byte type){

        Assert.isTrue(StringUtils.isNotBlank(name), "name is blank");

        ParttimeApproverTypeExample example = new ParttimeApproverTypeExample();
        ParttimeApproverTypeExample.Criteria criteria = example.createCriteria().andNameEqualTo(name);
        if(type== ParttimeConstants.PARTTIME_APPROVER_TYPE_UNIT
                ||type==ParttimeConstants.PARTTIME_APPROVER_TYPE_LEADER) {
            criteria.andTypeEqualTo(type);
        }
        if(id!=null) criteria.andIdNotEqualTo(id);

        return parttimeApproverTypeMapper.countByExample(example) > 0;
    }

    @Transactional
    @CacheEvict(value="parttimeApproverType:ALL", allEntries = true)
    public int insertSelective(ParttimeApproverType record){

        Assert.isTrue(!idDuplicate(null, record.getName(), record.getType()), "duplicate name and type");
        record.setSortOrder(getNextSortOrder("parttime_approver_type", null));
        return parttimeApproverTypeMapper.insertSelective(record);
    }

    @Transactional
    @Caching(evict= {
            @CacheEvict(value = "UserPermissions", allEntries = true),// 干部请假部分，有校领导和本单位正职的权限控制。
            @CacheEvict(value = "parttimeApproverType:ALL", allEntries = true)
    })
    public int updateByPrimaryKeySelective(ParttimeApproverType record){
        if(StringUtils.isNotBlank(record.getName()))
            Assert.isTrue(!idDuplicate(record.getId(), record.getName(), record.getType()), "duplicate name and type");
        return parttimeApproverTypeMapper.updateByPrimaryKeySelective(record);
    }

    @Cacheable(value="parttimeApproverType:ALL")
    public Map<Integer, ParttimeApproverType> findAll() {

        ParttimeApproverTypeExample example = new ParttimeApproverTypeExample();
        example.setOrderByClause("sort_order desc");
        List<ParttimeApproverType> parttimeApproverTypees = parttimeApproverTypeMapper.selectByExample(example);
        Map<Integer, ParttimeApproverType> map = new LinkedHashMap<>();
        for (ParttimeApproverType parttimeApproverType : parttimeApproverTypees) {
            map.put(parttimeApproverType.getId(), parttimeApproverType);
        }

        return map;
    }

    // 获得分管校领导身份
    public ParttimeApproverType getLeaderApproverType(){
        ParttimeApproverTypeExample example = new ParttimeApproverTypeExample();
        example.createCriteria().andTypeEqualTo(ParttimeConstants.PARTTIME_APPROVER_TYPE_LEADER);
        List<ParttimeApproverType> approverTypes = parttimeApproverTypeMapper.selectByExample(example);
        if(approverTypes.size()>0) return approverTypes.get(0);
        return null;
    }

    @Cacheable(value="ParttimeApproverBlackList", key = "#approverTypeId")
    public Map<Integer, ParttimeApproverBlackList> findAllBlackList(int approverTypeId) {

        ParttimeApproverBlackListExample example = new ParttimeApproverBlackListExample();
        example.createCriteria().andApproverTypeIdEqualTo(approverTypeId);
        List<ParttimeApproverBlackList> records = parttimeApproverBlackListMapper.selectByExample(example);
        Map<Integer, ParttimeApproverBlackList> map = new LinkedHashMap<>();
        for (ParttimeApproverBlackList record : records) {
            map.put(record.getCadreId(), record);
        }
        return map;
    }

    // 查询某类审批人身份 已设定的审批人 Set<干部ID>
    public Set<Integer> findApproverCadreIds(Integer typeId){

        Set<Integer> selectIdSet = new HashSet<Integer>();
        ParttimeApproverExample example = new ParttimeApproverExample();
        if(typeId!=null)
            example.createCriteria().andTypeIdEqualTo(typeId);
        List<ParttimeApprover> approvers = parttimeApproverMapper.selectByExample(example);
        for (ParttimeApprover approver : approvers) {
            selectIdSet.add(approver.getCadreId());
        }

        return selectIdSet;
    }

    public ParttimeApproverType getMainPostApproverType(){

        ParttimeApproverTypeExample example = new ParttimeApproverTypeExample();
        example.createCriteria().andTypeEqualTo(ParttimeConstants.PARTTIME_APPROVER_TYPE_UNIT);
        List<ParttimeApproverType> approverTypes = parttimeApproverTypeMapper.selectByExample(example);
        if(approverTypes.size()>0) return approverTypes.get(0);
        return null;
    }

    @Transactional
    @CacheEvict(value="ParttimeApproverBlackList", key = "#approverTypeId")
    public void updateCadreIds(int approverTypeId, Integer[] cadreIds){

        ParttimeApproverBlackListExample example = new ParttimeApproverBlackListExample();
        example.createCriteria().andApproverTypeIdEqualTo(approverTypeId);
        parttimeApproverBlackListMapper.deleteByExample(example);

        if(cadreIds==null || cadreIds.length==0) return;

        for (Integer cadreId : cadreIds) {
            ParttimeApproverBlackList record = new ParttimeApproverBlackList();
            record.setApproverTypeId(approverTypeId);
            record.setCadreId(cadreId);
            parttimeApproverBlackListMapper.insert(record);
        }
    }

    // 为某类审批人身份 设定审批人<干部ID>
    @Transactional
    @CacheEvict(value="UserPermissions", allEntries=true, beforeInvocation=true)
    public void updateApproverCadreIds(int typeId, Integer[] cadreIds){

        ParttimeApproverExample example = new ParttimeApproverExample();
        example.createCriteria().andTypeIdEqualTo(typeId);
        parttimeApproverMapper.deleteByExample(example);

        if(cadreIds==null || cadreIds.length==0) return ;

        for (Integer cadreId : cadreIds) {
            ParttimeApprover record = new ParttimeApprover();
            record.setTypeId(typeId);
            record.setCadreId(cadreId);
            record.setSortOrder(getNextSortOrder("parttime_approver", null));
            parttimeApproverMapper.insertSelective(record);
        }
    }

    public TreeNode getMainPostCadreTree() {
        TreeNode root = new TreeNode();
        root.title = "现任干部库";
        root.expand = true;
        root.isFolder = true;
        root.hideCheckbox = true;
        List<TreeNode> rootChildren = new ArrayList<TreeNode>();
        root.children = rootChildren;

        Map<Integer, MetaType> postMap = metaTypeService.metaTypes("mc_post");


        // 排序
        Map<Integer, Unit> unitMap = unitService.findAll();

        ParttimeApproverType mainPostApproverType = getMainPostApproverType();
        Map<Integer, ParttimeApproverBlackList> blackListMap = findAllBlackList(mainPostApproverType.getId());

        return root;
    }

    @Cacheable(value = "ParttimeApprover:ALL")
    public Map<Integer, ParttimeApprover> findAllCadreApprover() {
        ParttimeApproverExample example = new ParttimeApproverExample();
        example.createCriteria().andTypeIdEqualTo((int) ParttimeConstants.PARTTIME_APPROVER_TYPE_UNIT);
        example.setOrderByClause("sort_order asc");
        List<ParttimeApprover> approvers = parttimeApproverMapper.selectByExample(example);
        Map<Integer, ParttimeApprover> map = new LinkedHashMap<>();
        for (ParttimeApprover approver : approvers) {
            map.put(approver.getCadreId(), approver);
        }
        return map;
    }
}
