package service.base;

import domain.base.MetaClass;
import domain.base.MetaClassExample;
import domain.base.MetaType;
import domain.base.MetaTypeExample;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import service.BaseMapper;
import sys.tool.tree.TreeNode;

import java.util.*;

@Service
public class MetaTypeService extends BaseMapper {

    public String getName(Integer id){

        if(id==null)return "";

        MetaType metaType = findAll().get(id);
        if(metaType==null) return "";

        return metaType.getName();
    }

    // Set<MetaTypeId>
    public TreeNode getTree( String classCode,  Set<Integer> selectIdSet, Set<Integer> disabledIdSet){

        if(null == selectIdSet) selectIdSet = new HashSet<>();

        TreeNode root = new TreeNode();
        root.title = "元数据属性";
        root.expand = true;
        root.isFolder = true;
        root.hideCheckbox = true;
        List<TreeNode> rootChildren = new ArrayList<TreeNode>();
        root.children = rootChildren;

        Map<Integer, MetaType> metaTypeMap = metaTypes(classCode);

        for (MetaType metaType : metaTypeMap.values()) {

            TreeNode node = new TreeNode();
            node.title = metaType.getName();
            node.key = metaType.getId() + "";
            if (selectIdSet.contains(metaType.getId().intValue())) {
                node.select = true;
            }
            if(disabledIdSet.contains(metaType.getId().intValue()))
                node.hideCheckbox = true;

            rootChildren.add(node);
        }

        return root;
    }

    // 代码中获取属性列表
    @Cacheable(value="MetaTyes", key = "#classCode")
    public Map<Integer, MetaType> metaTypes(String classCode) {

        Map<Integer, MetaType> map = new LinkedHashMap<>();

        Integer classId = null;
        {
            MetaClassExample example = new MetaClassExample();
            example.createCriteria().andCodeEqualTo(classCode).andAvailableEqualTo(true);
            List<MetaClass> metaClasses = metaClassMapper.selectByExample(example);
            if (metaClasses.size() == 0) return map;
            MetaClass metaClass = metaClasses.get(0);
            classId = metaClass.getId();
        }

        MetaTypeExample example = new MetaTypeExample();
        example.createCriteria().andClassIdEqualTo(classId).andAvailableEqualTo(true);
        example.setOrderByClause("sort_order desc");
        List<MetaType> metaTypes = metaTypeMapper.selectByExample(example);
        for (MetaType metaType : metaTypes) {
            map.put(metaType.getId(), metaType);
        }

        return map;
    }

    public String genCode(){

        String prefix = "mt";
        String code = "";
        int count = 0;
        do {
            code = prefix + "_" + RandomStringUtils.randomAlphanumeric(6).toLowerCase();
            MetaTypeExample example = new MetaTypeExample();
            example.createCriteria().andCodeEqualTo(code).andAvailableEqualTo(true);
            count = metaTypeMapper.countByExample(example);
        } while(count>0);
        return code;
    }

    public boolean codeAvailable(Integer id, String code){

        Assert.isTrue(StringUtils.isNotBlank(code), "code is blank");

        MetaTypeExample example = new MetaTypeExample();
        MetaTypeExample.Criteria criteria = example.createCriteria().andCodeEqualTo(code).andAvailableEqualTo(true);
        if(id!=null) criteria.andIdNotEqualTo(id);

        return metaTypeMapper.countByExample(example) == 0;
    }

    // 根据名称查找（唯一），用于数据导入时
    public MetaType findByName(String classCode, String name){
        Integer classId = null;
        {
            MetaClassExample example = new MetaClassExample();
            example.createCriteria().andCodeEqualTo(classCode).andAvailableEqualTo(true);
            List<MetaClass> metaClasses = metaClassMapper.selectByExample(example);
            if (metaClasses.size() == 0) return null;
            MetaClass metaClass = metaClasses.get(0);
            classId = metaClass.getId();
        }

        MetaTypeExample example = new MetaTypeExample();
        example.createCriteria().andClassIdEqualTo(classId).andAvailableEqualTo(true).andNameEqualTo(name);
        List<MetaType> metaTypes = metaTypeMapper.selectByExample(example);
        if(metaTypes.size()>0) return metaTypes.get(0);
        return null;
    }

    @Transactional
    @Caching(evict= {
            @CacheEvict(value = "MetaType:ALL", allEntries = true),
            @CacheEvict(value = "MetaType:Code:ALL", allEntries = true),
            @CacheEvict(value = "MetaTyes", allEntries = true)
    })
    public int insertSelective(MetaType record){

        Assert.isTrue(codeAvailable(null, record.getCode()), "wrong code");
        record.setSortOrder(getNextSortOrder("base_meta_type", "class_id=" + record.getClassId()));
        return metaTypeMapper.insertSelective(record);
    }

    @Transactional
    @Caching(evict= {
            @CacheEvict(value = "MetaType:ALL", allEntries = true),
            @CacheEvict(value = "MetaType:Code:ALL", allEntries = true),
            @CacheEvict(value = "MetaTyes", allEntries = true)
    })
    public void del(Integer id){

        metaTypeMapper.deleteByPrimaryKey(id);
    }
    @Transactional
    @CacheEvict(value="MetaType:ALL", allEntries = true)
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        MetaTypeExample example = new MetaTypeExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        metaTypeMapper.deleteByExample(example);
    }


    @Transactional
    @Caching(evict= {
            @CacheEvict(value = "MetaType:ALL", allEntries = true),
            @CacheEvict(value = "MetaType:Code:ALL", allEntries = true),
            @CacheEvict(value = "MetaTyes", allEntries = true)
    })
    public int updateByPrimaryKeySelective(MetaType record){
        if(StringUtils.isNotBlank(record.getCode()))
            Assert.isTrue(codeAvailable(record.getId(), record.getCode()), "wrong code");
        return metaTypeMapper.updateByPrimaryKeySelective(record);
    }


    @Cacheable(value="MetaType:ALL")
    public Map<Integer, MetaType> findAll() {

        MetaTypeExample example = new MetaTypeExample();
        example.createCriteria().andAvailableEqualTo(true);
        example.setOrderByClause("sort_order desc");
        List<MetaType> metaTypees = metaTypeMapper.selectByExample(example);
        Map<Integer, MetaType> map = new LinkedHashMap<>();
        for (MetaType metaType : metaTypees) {
            map.put(metaType.getId(), metaType);
        }

        return map;
    }

    @Cacheable(value="MetaType:Code:ALL")
    public Map<String, MetaType> codeKeyMap() {

        MetaTypeExample example = new MetaTypeExample();
        example.createCriteria().andAvailableEqualTo(true);
        example.setOrderByClause("sort_order desc");
        List<MetaType> metaTypees = metaTypeMapper.selectByExample(example);
        Map<String, MetaType> map = new LinkedHashMap<>();
        for (MetaType metaType : metaTypees) {
            map.put(metaType.getCode(), metaType);
        }

        return map;
    }

    @Transactional
    @Caching(evict= {
            @CacheEvict(value = "MetaType:ALL", allEntries = true),
            @CacheEvict(value = "MetaType:Code:ALL", allEntries = true),
            @CacheEvict(value = "MetaTyes", allEntries = true)
    })
    public void changeOrder(int id, int addNum, int classId) { // 分类内部排序

        if(addNum == 0) return ;

        MetaType entity = metaTypeMapper.selectByPrimaryKey(id);
        Integer baseSortOrder = entity.getSortOrder();

        MetaTypeExample example = new MetaTypeExample();
        if (addNum > 0) {

            example.createCriteria().andClassIdEqualTo(classId).andSortOrderGreaterThan(baseSortOrder);
            example.setOrderByClause("sort_order asc");
        }else {

            example.createCriteria().andClassIdEqualTo(classId).andSortOrderLessThan(baseSortOrder);
            example.setOrderByClause("sort_order desc");
        }

        List<MetaType> overEntities = metaTypeMapper.selectByExampleWithRowbounds(example, new RowBounds(0, Math.abs(addNum)));
        if(overEntities.size()>0) {

            MetaType targetEntity = overEntities.get(overEntities.size()-1);

            if (addNum > 0)
                commonMapper.downOrder("base_meta_type", "class_id=" + classId, baseSortOrder, targetEntity.getSortOrder());
            else
                commonMapper.upOrder("base_meta_type", "class_id=" + classId, baseSortOrder, targetEntity.getSortOrder());

            MetaType record = new MetaType();
            record.setId(id);
            record.setSortOrder(targetEntity.getSortOrder());
            metaTypeMapper.updateByPrimaryKeySelective(record);
        }
    }
}
