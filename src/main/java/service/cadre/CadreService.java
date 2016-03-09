package service.cadre;

import bean.XlsCadre;
import domain.*;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;
import service.sys.MetaTypeService;
import service.sys.SysUserService;
import service.unit.UnitService;
import sys.constants.SystemConstants;
import sys.tags.CmTag;
import sys.tool.tree.TreeNode;

import java.util.*;

@Service
public class CadreService extends BaseMapper {

    @Autowired
    private MetaTypeService metaTypeService;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private UnitService unitService;

    @Transactional
    public int importCadres(final List<XlsCadre> cadres, byte status) {
        //int duplicate = 0;
        int success = 0;
        for(XlsCadre uRow: cadres){

            Cadre record = new Cadre();
            String userCode = uRow.getUserCode();
            SysUser sysUser = sysUserService.findByUsername(userCode);
            if(sysUser== null) throw  new RuntimeException("工作证号："+userCode+"不存在");
            record.setUserId(sysUser.getId());
            record.setStatus(status);
            record.setTypeId(uRow.getAdminLevel());
            record.setPostId(uRow.getPostId());
            Unit unit = unitService.findUnitByCode(uRow.getUnitCode());
            if(unit==null){
                throw  new RuntimeException("单位编号："+uRow.getUnitCode()+"不存在");
            }
            record.setUnitId(unit.getId());
            record.setTitle(uRow.getTitle());
            record.setRemark(uRow.getRemark());

            if (idDuplicate(null, sysUser.getId())) {
                throw  new RuntimeException("导入失败，工作证号："+uRow.getUserCode()+"重复");
            }

            insertSelective(record);
            success++;
        }
        return success;
    }
    // 职务属性-干部 Set<cadreId>
    public TreeNode getTree( Set<Integer> selectIdSet){

        if(null == selectIdSet) selectIdSet = new HashSet<>();

        TreeNode root = new TreeNode();
        root.title = "现任干部库";
        root.expand = true;
        root.isFolder = true;
        root.hideCheckbox = true;
        List<TreeNode> rootChildren = new ArrayList<TreeNode>();
        root.children = rootChildren;

        Map<Integer, MetaType> postMap = metaTypeService.metaTypes("mc_post");
        // 职务属性-干部
        Map<String, List<Cadre>> cadreMap = new LinkedHashMap<>();

        CadreExample example = new CadreExample();
        example.createCriteria().andStatusEqualTo(SystemConstants.CADRE_STATUS_NOW);
        example.setOrderByClause(" sort_order desc");
        List<Cadre> cadres = cadreMapper.selectByExample(example);
        for (Cadre cadre : cadres) {
            List<Cadre> list = null;
            MetaType postType = postMap.get(cadre.getPostId());
            String post = postType.getName();
            if (cadreMap.containsKey(post)) {
                list = cadreMap.get(post);
            }
            if (null == list) list = new ArrayList<>();
            list.add(cadre);

            cadreMap.put(post, list);
        }

        int i = 0;
        for (Map.Entry<String, List<Cadre>> entry : cadreMap.entrySet()) {

            TreeNode titleNode = new TreeNode();
            titleNode.title = entry.getKey();
            titleNode.expand = (i++<1);
            titleNode.isFolder = true;
            List<TreeNode> titleChildren = new ArrayList<TreeNode>();
            titleNode.children = titleChildren;

            for (Cadre cadre : entry.getValue()) {

                TreeNode node = new TreeNode();
                SysUser sysUser = sysUserService.findById(cadre.getUserId());
                node.title = sysUser.getRealname();
                node.key = cadre.getId() + "";
                if (selectIdSet.contains(cadre.getId().intValue())) {
                    node.select = true;
                }
                titleChildren.add(node);
            }

            rootChildren.add(titleNode);
        }

        return root;
    }
    @Transactional
    @CacheEvict(value="Cadre:ALL", allEntries = true)
    public void leave(int id, byte status){

        if(status == SystemConstants.CADRE_STATUS_LEAVE){

            // 处级干部离任时，所有的证件都移动到 取消集中管理证件库
            Passport record = new Passport();
            record.setType(SystemConstants.PASSPORT_TYPE_CANCEL);
            record.setCancelType(SystemConstants.PASSPORT_CANCEL_TYPE_DISMISS);

            PassportExample example = new PassportExample();
            example.createCriteria().andCadreIdEqualTo(id).
                    andTypeEqualTo(SystemConstants.PASSPORT_TYPE_KEEP).andAbolishEqualTo(false);
            passportMapper.updateByExampleSelective(record, example);
        }

        Cadre record = new Cadre();
        record.setStatus(status);
        CadreExample example = new CadreExample();
        example.createCriteria().andIdEqualTo(id).andStatusEqualTo(SystemConstants.CADRE_STATUS_NOW);

        cadreMapper.updateByExampleSelective(record, example);
    }

    public boolean idDuplicate(Integer id, int userId){

        CadreExample example = new CadreExample();
        CadreExample.Criteria criteria = example.createCriteria().andUserIdEqualTo(userId);
        if(id!=null) criteria.andIdNotEqualTo(id);

        return cadreMapper.countByExample(example) > 0;
    }

    public Cadre findByUserId(int userId){

        CadreExample example = new CadreExample();
        example.createCriteria().andUserIdEqualTo(userId);
        List<Cadre> cadres = cadreMapper.selectByExampleWithRowbounds(example, new RowBounds(0, 1));
        if(cadres.size()>0) return cadres.get(0);

        return null;
    }
    @Transactional
    @CacheEvict(value="Cadre:ALL", allEntries = true)
    public int insertSelective(Cadre record){

        SysUser sysUser = sysUserService.findById(record.getUserId());
        // 添加干部身份
        sysUserService.addRole(sysUser.getId(), SystemConstants.ROLE_CADRE, sysUser.getUsername());

        cadreMapper.insertSelective(record);
        Integer id = record.getId();
        Cadre _record = new Cadre();
        _record.setId(id);
        _record.setSortOrder(id);
        return cadreMapper.updateByPrimaryKeySelective(_record);
    }
    @Transactional
    @CacheEvict(value="Cadre:ALL", allEntries = true)
    public void del(Integer id){

        cadreMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    @CacheEvict(value="Leader:ALL", allEntries = true)
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        CadreExample example = new CadreExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        cadreMapper.deleteByExample(example);
    }
    
    @Transactional
    @CacheEvict(value="Cadre:ALL", allEntries = true)
    public int updateByPrimaryKeySelective(Cadre record){
        return cadreMapper.updateByPrimaryKeySelective(record);
    }

    @Transactional
    @CacheEvict(value="Cadre:ALL", allEntries = true)
    public int updateByExampleSelective(Cadre record, CadreExample example){

        return cadreMapper.updateByExampleSelective(record, example);
    }

    @Cacheable(value="Cadre:ALL")
    public Map<Integer, Cadre> findAll() {

        CadreExample example = new CadreExample();
        example.setOrderByClause("sort_order desc");
        List<Cadre> cadrees = cadreMapper.selectByExample(example);
        Map<Integer, Cadre> map = new LinkedHashMap<>();
        for (Cadre cadre : cadrees) {
            map.put(cadre.getId(), cadre);
        }

        return map;
    }

    /**
     * 排序 ，要求 1、sort_order>0且不可重复  2、sort_order 降序排序
     * 3.sort_order = id,
     * @param id
     * @param addNum
     */
    @Transactional
    @CacheEvict(value = "Cadre:ALL", allEntries = true)
    public void changeOrder(int id, byte status, int addNum) {

        if(addNum == 0) return ;

        Cadre entity = cadreMapper.selectByPrimaryKey(id);
        Integer baseSortOrder = entity.getSortOrder();

        CadreExample example = new CadreExample();
        if (addNum > 0) {

            example.createCriteria().andStatusEqualTo(status).andSortOrderGreaterThan(baseSortOrder);
            example.setOrderByClause("sort_order asc");
        }else {

            example.createCriteria().andStatusEqualTo(status).andSortOrderLessThan(baseSortOrder);
            example.setOrderByClause("sort_order desc");
        }

        List<Cadre> overEntities = cadreMapper.selectByExampleWithRowbounds(example, new RowBounds(0, Math.abs(addNum)));
        if(overEntities.size()>0) {

            Cadre targetEntity = overEntities.get(overEntities.size()-1);

            if (addNum > 0)
                commonMapper.downOrder_cadre(status, baseSortOrder, targetEntity.getSortOrder());
            else
                commonMapper.upOrder_cadre(status, baseSortOrder, targetEntity.getSortOrder());

            Cadre record = new Cadre();
            record.setId(id);
            record.setSortOrder(targetEntity.getSortOrder());
            cadreMapper.updateByPrimaryKeySelective(record);
        }
    }
}
