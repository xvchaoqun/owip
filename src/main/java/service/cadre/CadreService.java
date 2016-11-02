package service.cadre;

import bean.XlsCadre;
import domain.abroad.ApproverBlackList;
import domain.abroad.ApproverType;
import domain.abroad.Passport;
import domain.abroad.PassportExample;
import domain.cadre.Cadre;
import domain.cadre.CadreAdditionalPost;
import domain.cadre.CadreAdditionalPostExample;
import domain.cadre.CadreExample;
import domain.dispatch.Dispatch;
import domain.dispatch.DispatchCadre;
import domain.sys.MetaType;
import domain.sys.SysUser;
import domain.sys.SysUserView;
import domain.unit.Unit;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;
import service.abroad.ApproverBlackListService;
import service.abroad.ApproverTypeService;
import service.dispatch.DispatchService;
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
    @Autowired
    private DispatchService dispatchService;
    @Autowired
    private CadreAdditionalPostService cadreAdditionalPostService;
    @Autowired
    private ApproverBlackListService approverBlackListService;
    @Autowired
    private ApproverTypeService approverTypeService;

    // 获取干部范文
    public TreeNode getDispatchCadreTree(int cadreId, Byte type){

        Cadre cadre = cadreMapper.selectByPrimaryKey(cadreId);

        List<DispatchCadre> dispatchCadres =  commonMapper.selectDispatchCadreList(cadreId, type);
        Map<Integer, Dispatch> dispatchMap = dispatchService.findAll();
        Map<Integer, MetaType> postMap = metaTypeService.metaTypes("mc_post");
        TreeNode root = new TreeNode();
        root.title = "选择离任文件";
        root.expand = true;
        root.isFolder = true;
        root.hideCheckbox = true;
        root.children =  new ArrayList<TreeNode>();
        for (DispatchCadre dispatchCadre : dispatchCadres) {

            Dispatch dispatch = dispatchMap.get(dispatchCadre.getDispatchId());
            TreeNode node = new TreeNode();
            MetaType postType = postMap.get(dispatchCadre.getPostId());
            node.title = CmTag.getDispatchCode(dispatch.getCode(), dispatch.getDispatchTypeId(), dispatch.getYear())
                    +"-" + dispatchCadre.getPost() + (postType!=null?("-" + postType.getName()):"");
            node.key = dispatchCadre.getId()+"";
            node.expand = false;
            node.isFolder = false;
            node.noLink = true;
            node.icon = false;
            node.hideCheckbox = false;
            node.tooltip = dispatch.getFile();
            node.select = (cadre.getDispatchCadreId()!=null && cadre.getDispatchCadreId().intValue() == dispatchCadre.getId());

            root.children.add(node);
        }
        return root;
    }

    @Transactional
    @Caching(evict= {
            @CacheEvict(value = "UserPermissions", allEntries = true),// 因私出国部分，有校领导和本单位正职的权限控制。
            @CacheEvict(value = "Cadre:ALL", allEntries = true)
    })
    public int importCadres(final List<XlsCadre> cadres, byte status) {
        //int duplicate = 0;
        int success = 0;
        for(XlsCadre uRow: cadres){

            Cadre record = new Cadre();
            String userCode = uRow.getUserCode();
            SysUserView uv = sysUserService.findByCode(userCode);
            if(uv== null) throw  new RuntimeException("工作证号："+userCode+"不存在");
            record.setUserId(uv.getId());
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

            if (idDuplicate(null, uv.getId())) {
                throw  new RuntimeException("导入失败，工作证号："+uRow.getUserCode()+"重复");
            }

            insertSelective(record);
            success++;
        }
        return success;
    }

    public TreeNode getTree( Set<Cadre> cadreList, Set<Integer> selectIdSet, Set<Integer> disabledIdSet){

        return getTree(cadreList, selectIdSet, disabledIdSet, true, false);
    }

    // 职务属性-干部 Set<cadreId> , 用于审批人身份时disabledIdSet=null
    public TreeNode getTree( Set<Cadre> cadreList, Set<Integer> selectIdSet,
                             Set<Integer> disabledIdSet, boolean enableSelect, boolean defExpand){

        if(null == selectIdSet) selectIdSet = new HashSet<>();
        if(null == disabledIdSet) disabledIdSet = new HashSet<>();

        TreeNode root = new TreeNode();
        root.title = "现任干部库";
        root.expand = true;
        root.isFolder = true;
        root.hideCheckbox = true;
        List<TreeNode> rootChildren = new ArrayList<TreeNode>();
        root.children = rootChildren;

        Map<Integer, MetaType> postMap = metaTypeService.metaTypes("mc_post");
        // 职务属性-干部
        Map<Integer, List<Cadre>> postIdCadresMap = new LinkedHashMap<>();

        /*CadreExample example = new CadreExample();
        example.createCriteria().andStatusEqualTo(SystemConstants.CADRE_STATUS_NOW);
        example.setOrderByClause(" sort_order desc");
        List<Cadre> cadres = cadreMapper.selectByExample(example);*/
        for (Cadre cadre : cadreList) {
            if(cadre.getStatus()==SystemConstants.CADRE_STATUS_NOW) {
                List<Cadre> list = null;
                MetaType postType = postMap.get(cadre.getPostId());
                int postId = postType.getId();
                if (postIdCadresMap.containsKey(postId)) {
                    list = postIdCadresMap.get(postId);
                }
                if (null == list) list = new ArrayList<>();
                list.add(cadre);

                postIdCadresMap.put(postId, list);
            }
        }

        // 排序
        Map<String, List<Cadre>> postCadresMap = new LinkedHashMap<>();
        for (MetaType metaType : postMap.values()) {
            if(postIdCadresMap.containsKey(metaType.getId()))
                postCadresMap.put(metaType.getName(), postIdCadresMap.get(metaType.getId()));
        }

        int i = 0;
        for (Map.Entry<String, List<Cadre>> entry : postCadresMap.entrySet()) {

            List<Cadre> entryValue = entry.getValue();
            TreeNode titleNode = new TreeNode();

            titleNode.expand = defExpand;
            titleNode.isFolder = true;
            List<TreeNode> titleChildren = new ArrayList<TreeNode>();
            titleNode.children = titleChildren;
            if(!enableSelect)
                titleNode.hideCheckbox = true;

            int selectCount = 0;
            for (Cadre cadre : entryValue) {

                int cadreId = cadre.getId();
                String title = cadre.getTitle();
                TreeNode node = new TreeNode();
                SysUserView uv = sysUserService.findById(cadre.getUserId());
                node.title = uv.getRealname() + (title!=null?("-" + title):"");
                node.key =  cadreId + "";

                if (enableSelect && selectIdSet.contains(cadreId)) {
                    selectCount++;
                    node.select = true;
                }
                if(!enableSelect || disabledIdSet.contains(cadreId))
                    node.hideCheckbox = true;
                titleChildren.add(node);
            }
            titleNode.title = entry.getKey() + String.format("(%s", selectCount>0?selectCount+"/":"")+ entryValue.size() + "人)";
            rootChildren.add(titleNode);
        }
        return root;
    }

    class CadrePostBean{
        private int cadreId;
        private int postId;
        private boolean additional;

        public CadrePostBean(int cadreId, int postId, boolean additional) {
            this.cadreId = cadreId;
            this.postId = postId;
            this.additional = additional;
        }

        public int getCadreId() {
            return cadreId;
        }

        public void setCadreId(int cadreId) {
            this.cadreId = cadreId;
        }

        public int getPostId() {
            return postId;
        }

        public void setPostId(int postId) {
            this.postId = postId;
        }

        public boolean isAdditional() {
            return additional;
        }

        public void setAdditional(boolean additional) {
            this.additional = additional;
        }
    }

    // 本单位正职列表（审批人，包括兼任职务）
    public TreeNode getMainPostCadreTree(){

        TreeNode root = new TreeNode();
        root.title = "现任干部库";
        root.expand = true;
        root.isFolder = true;
        root.hideCheckbox = true;
        List<TreeNode> rootChildren = new ArrayList<TreeNode>();
        root.children = rootChildren;

        Map<Integer, Cadre> cadreMap = findAll();
        Map<Integer, MetaType> postMap = metaTypeService.metaTypes("mc_post");
        // 职务属性-干部
        Map<Integer, List<CadrePostBean>> unitIdCadresMap = new LinkedHashMap<>();

        for (Cadre cadre : cadreMap.values()) {
            if(cadre.getStatus()==SystemConstants.CADRE_STATUS_NOW
                    && BooleanUtils.isTrue(postMap.get(cadre.getPostId()).getBoolAttr())) {
                List<CadrePostBean> list = null;
                Integer unitId = cadre.getUnitId();
                if (unitIdCadresMap.containsKey(unitId)) {
                    list = unitIdCadresMap.get(unitId);
                }
                if (null == list) list = new ArrayList<>();
                CadrePostBean bean = new CadrePostBean(cadre.getId(), cadre.getPostId(), false);
                list.add(bean);

                unitIdCadresMap.put(unitId, list);
            }
        }
        Map<String, CadreAdditionalPost> cadreAdditionalPostMap = cadreAdditionalPostService.findAll();
        for (CadreAdditionalPost cPost : cadreAdditionalPostMap.values()) {
            Cadre cadre = cadreMap.get(cPost.getCadreId());
            if(cadre.getStatus()==SystemConstants.CADRE_STATUS_NOW
                    && BooleanUtils.isTrue(postMap.get(cPost.getPostId()).getBoolAttr())) {
                List<CadrePostBean> list = null;
                Integer unitId = cPost.getUnitId();
                if (unitIdCadresMap.containsKey(unitId)) {
                    list = unitIdCadresMap.get(unitId);
                }
                if (null == list) list = new ArrayList<>();
                CadrePostBean bean = new CadrePostBean(cPost.getCadreId(),
                        cPost.getPostId(), true);
                list.add(bean);

                unitIdCadresMap.put(unitId, list);
            }
        }

        // 排序
        Map<Integer, Unit> unitMap = unitService.findAll();
        Map<String, List<CadrePostBean>> unitCadresMap = new LinkedHashMap<>();
        for (Unit unit : unitMap.values()) {
            if(unitIdCadresMap.containsKey(unit.getId()))
                unitCadresMap.put(unit.getName(), unitIdCadresMap.get(unit.getId()));
        }

        // 本单位正职身份
        ApproverType mainPostApproverType = approverTypeService.getMainPostApproverType();
        Integer mainPostTypeId = mainPostApproverType.getId();
        Map<Integer, ApproverBlackList> blackListMap = approverBlackListService.findAll(mainPostApproverType.getId());
        for (Map.Entry<String, List<CadrePostBean>> entry : unitCadresMap.entrySet()) {

            List<CadrePostBean> entryValue = entry.getValue();
            TreeNode titleNode = new TreeNode();


            titleNode.isFolder = true;
            titleNode.hideCheckbox=true;
            titleNode.unselectable=true;
            List<TreeNode> titleChildren = new ArrayList<TreeNode>();
            titleNode.children = titleChildren;

            int blackCount = 0;
            for (CadrePostBean bean : entryValue) {

                int cadreId = bean.getCadreId();
                TreeNode node = new TreeNode();
                Cadre cadre = cadreMap.get(cadreId);
                SysUserView uv = sysUserService.findById(cadre.getUserId());
                node.title = uv.getRealname() + "-" + postMap.get(bean.getPostId()).getName() +
                (bean.additional?"(兼审单位)":"");

                if(bean.additional) {
                    node.unselectable = true;
                }else{
                    node.key =  cadreId + "";
                }
                node.select=true;

                // 本单位正职黑名单
                if(!bean.additional && blackListMap.get(cadreId)!=null) {
                    node.select = false;
                    blackCount++;
                }

                titleChildren.add(node);
            }

            int selectCount = entryValue.size() - blackCount;
            titleNode.title = entry.getKey() + String.format("(%s", selectCount>0?selectCount+"/":"") + entryValue.size() + ")";
            rootChildren.add(titleNode);
        }
        return root;
    }

    @Transactional
    @Caching(evict= {
            @CacheEvict(value = "UserPermissions", allEntries = true),
            @CacheEvict(value = "Cadre:ALL", allEntries = true)
    })
    public void leave(int id, byte status, String title, Integer dispatchCadreId){

        if(status == SystemConstants.CADRE_STATUS_LEAVE){

            // 中层干部离任时，所有的证件都移动到 取消集中管理证件库
            Passport record = new Passport();
            record.setType(SystemConstants.PASSPORT_TYPE_CANCEL);
            record.setCancelType(SystemConstants.PASSPORT_CANCEL_TYPE_DISMISS);

            PassportExample example = new PassportExample();
            example.createCriteria().andCadreIdEqualTo(id).
                    andTypeEqualTo(SystemConstants.PASSPORT_TYPE_KEEP);
            passportMapper.updateByExampleSelective(record, example);
        }

        Cadre record = new Cadre();
        record.setStatus(status);
        if(StringUtils.isNotBlank(title))
            record.setTitle(title);
        record.setDispatchCadreId(dispatchCadreId);
        CadreExample example = new CadreExample();
        example.createCriteria().andIdEqualTo(id).andStatusEqualTo(SystemConstants.CADRE_STATUS_NOW);

        cadreMapper.updateByExampleSelective(record, example);
    }

    // 重新任用， 离任->考察对象
    @Transactional
    @Caching(evict= {
            @CacheEvict(value = "UserPermissions", allEntries = true),
            @CacheEvict(value = "Cadre:ALL", allEntries = true)
    })
    public void assign(Integer[] ids){

        Cadre record = new Cadre();
        record.setStatus(SystemConstants.CADRE_STATUS_TEMP);
        CadreExample example = new CadreExample();
        example.createCriteria().andIdIn(Arrays.asList(ids))
                .andStatusIn(Arrays.asList(SystemConstants.CADRE_STATUS_LEAVE,
                        SystemConstants.CADRE_STATUS_LEADER_LEAVE));

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

    // 获取某个单位下的兼审正职
    public List<Cadre> findAdditionalPost(int unitId){

        List<Cadre> cadreList = new ArrayList<>();
        Map<Integer, Cadre> cadreMap = findAll();
        Map<Integer, MetaType> metaTypeMap = metaTypeService.findAll();
        CadreAdditionalPostExample example = new CadreAdditionalPostExample();
        example.createCriteria().andUnitIdEqualTo(unitId);
        List<CadreAdditionalPost> cPosts = cadreAdditionalPostMapper.selectByExample(example);
        for (CadreAdditionalPost cPost : cPosts) {
            MetaType postType = metaTypeMap.get(cPost.getPostId());
            if (postType.getBoolAttr()) {
                cadreList.add(cadreMap.get(cPost.getCadreId()));
            }
        }
        return cadreList;
    }

    // 查找某个单位的正职(不包括兼任职务的干部)
    public List<Cadre> findMainPost(int unitId){

        List<Cadre> cadreList = new ArrayList<>();
        Map<Integer, MetaType> metaTypeMap = metaTypeService.findAll();
        {
            CadreExample example = new CadreExample();
            example.createCriteria().andUnitIdEqualTo(unitId);
            List<Cadre> cadres = cadreMapper.selectByExample(example);
            for (Cadre cadre : cadres) {
                MetaType postType = metaTypeMap.get(cadre.getPostId());
                if (postType.getBoolAttr()) {
                    cadreList.add(cadre);
                }
            }
        }

        return cadreList;
    }

    @Transactional
    @Caching(evict= {
            @CacheEvict(value = "UserPermissions", allEntries = true),
            @CacheEvict(value = "Cadre:ALL", allEntries = true)
    })
    public int insertSelective(Cadre record){

        SysUserView uv = sysUserService.findById(record.getUserId());
        // 添加干部身份
        sysUserService.addRole(uv.getId(), SystemConstants.ROLE_CADRE, uv.getUsername(), uv.getCode());

        record.setIsDp(false);// 初次添加标记为非民主党派
        cadreMapper.insertSelective(record);
        Integer id = record.getId();
        Cadre _record = new Cadre();
        _record.setId(id);
        _record.setSortOrder(id);
        return cadreMapper.updateByPrimaryKeySelective(_record);
    }
    @Transactional
    @Caching(evict= {
            @CacheEvict(value = "UserPermissions", allEntries = true),
            @CacheEvict(value = "Cadre:ALL", allEntries = true)
    })
    public void del(Integer id){

        cadreMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    @Caching(evict= {
            @CacheEvict(value = "UserPermissions", allEntries = true),
            @CacheEvict(value = "Cadre:ALL", allEntries = true)
    })
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        CadreExample example = new CadreExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        cadreMapper.deleteByExample(example);
    }

    @Transactional
    @Caching(evict= {
            @CacheEvict(value = "UserPermissions", allEntries = true),
            @CacheEvict(value = "Cadre:ALL", allEntries = true)
    })
    public void democraticParty_batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        CadreExample example = new CadreExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        Cadre record = new Cadre();
        record.setIsDp(false);
        cadreMapper.updateByExampleSelective(record, example);
    }

    
    @Transactional
    @Caching(evict= {
            @CacheEvict(value = "UserPermissions", allEntries = true),
            @CacheEvict(value = "Cadre:ALL", allEntries = true)
    })
    public int updateByPrimaryKeySelective(Cadre record){
        return cadreMapper.updateByPrimaryKeySelective(record);
    }

    @Transactional
    @Caching(evict= {
            @CacheEvict(value = "UserPermissions", allEntries = true),
            @CacheEvict(value = "Cadre:ALL", allEntries = true)
    })
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
