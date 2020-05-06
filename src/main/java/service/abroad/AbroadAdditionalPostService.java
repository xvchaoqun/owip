package service.abroad;

import domain.abroad.AbroadAdditionalPost;
import domain.abroad.AbroadAdditionalPostExample;
import domain.abroad.ApproverBlackList;
import domain.base.MetaType;
import domain.cadre.CadreView;
import domain.sys.SysUserView;
import domain.unit.Unit;
import org.apache.commons.lang3.BooleanUtils;
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
import sys.constants.CadreConstants;
import sys.tags.CmTag;
import sys.tool.tree.TreeNode;

import java.util.*;

@Service
public class AbroadAdditionalPostService extends AbroadBaseMapper {

    @Autowired
    private MetaTypeService metaTypeService;
    @Autowired
    private UnitService unitService;
    @Autowired
    private CadreService cadreService;
    @Autowired
    private ApproverBlackListService approverBlackListService;
    @Autowired
    private SysUserService sysUserService;

    // 获取某个单位下的兼审正职
    public List<CadreView> findAdditionalPrincipals(Integer unitId) {

        List<CadreView> cadreList = new ArrayList<>();
        if(unitId==null) return cadreList;
        AbroadAdditionalPostExample example = new AbroadAdditionalPostExample();
        example.createCriteria().andUnitIdEqualTo(unitId);
        List<AbroadAdditionalPost> cPosts = abroadAdditionalPostMapper.selectByExample(example);
        for (AbroadAdditionalPost cPost : cPosts) {
            cadreList.add(CmTag.getCadreById(cPost.getCadreId()));
        }
        return cadreList;
    }

    class CadrePostBean {
        private int cadreId;
        private int unitId;
        private int postId;
        private boolean additional;

        public CadrePostBean(int cadreId, int unitId, int postId, boolean additional) {
            this.cadreId = cadreId;
            this.unitId = unitId;
            this.postId = postId;
            this.additional = additional;
        }

        public int getCadreId() {
            return cadreId;
        }

        public void setCadreId(int cadreId) {
            this.cadreId = cadreId;
        }

        public int getUnitId() {
            return unitId;
        }

        public void setUnitId(int unitId) {
            this.unitId = unitId;
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
    public TreeNode getUnitPrincipalCadreTree(int approverTypeId) {

        TreeNode root = new TreeNode();
        root.title = "现任干部库";
        root.expand = true;
        root.isFolder = true;
        root.hideCheckbox = true;
        List<TreeNode> rootChildren = new ArrayList<TreeNode>();
        root.children = rootChildren;

        Map<Integer, CadreView> cadreMap = cadreService.findAll();
        Map<Integer, MetaType> postMap = metaTypeService.metaTypes("mc_post");
        // 单位ID-干部
        Map<Integer, List<CadrePostBean>> unitIdCadresMap = new LinkedHashMap<>();

        for (CadreView cadre : cadreMap.values()) {
            if ((cadre.getStatus() == CadreConstants.CADRE_STATUS_CJ
                    || cadre.getStatus() == CadreConstants.CADRE_STATUS_LEADER)
                    && BooleanUtils.isTrue(cadre.getIsPrincipal())) {
                List<CadrePostBean> list = null;
                Integer unitId = cadre.getUnitId();
                if (unitIdCadresMap.containsKey(unitId)) {
                    list = unitIdCadresMap.get(unitId);
                }
                if (null == list) list = new ArrayList<>();
                if(cadre.getPostType()!=null && cadre.getUnitId()!=null) {
                    CadrePostBean bean = new CadrePostBean(cadre.getId(), cadre.getUnitId(), cadre.getPostType(), false);
                    list.add(bean);
                    unitIdCadresMap.put(unitId, list); // 获取所有单位的正职
                }
            }
        }

        // key: cadreId+"_"+unitId
        Map<String, AbroadAdditionalPost> abroadAdditionalPostMap = findAll();
        for (AbroadAdditionalPost cPost : abroadAdditionalPostMap.values()) {
            CadreView cadre = cadreMap.get(cPost.getCadreId());
            if ((cadre.getStatus() == CadreConstants.CADRE_STATUS_CJ
                    || cadre.getStatus() == CadreConstants.CADRE_STATUS_LEADER)) {
                List<CadrePostBean> list = null;
                Integer unitId = cPost.getUnitId();
                if (unitIdCadresMap.containsKey(unitId)) {
                    list = unitIdCadresMap.get(unitId);
                }
                if (null == list) list = new ArrayList<>();
                CadrePostBean bean = new CadrePostBean(cPost.getCadreId(), cPost.getUnitId(),
                        cPost.getPostId(), true);
                list.add(bean);

                unitIdCadresMap.put(unitId, list); // 加入 兼审单位 的正职
            }
        }

        // 排序
        Map<Integer, Unit> unitMap = unitService.findAll();
        Map<String, List<CadrePostBean>> unitCadresMap = new LinkedHashMap<>();
        for (Unit unit : unitMap.values()) {
            if (unitIdCadresMap.containsKey(unit.getId()))
                unitCadresMap.put(unit.getName(), unitIdCadresMap.get(unit.getId()));
        }

        // 本单位正职身份
        Map<String, ApproverBlackList> blackListMap = approverBlackListService.findAll(approverTypeId);
        for (Map.Entry<String, List<CadrePostBean>> entry : unitCadresMap.entrySet()) {

            List<CadrePostBean> entryValue = entry.getValue();
            TreeNode titleNode = new TreeNode();


            titleNode.isFolder = true;
            titleNode.hideCheckbox = true;
            titleNode.unselectable = true;
            List<TreeNode> titleChildren = new ArrayList<TreeNode>();
            titleNode.children = titleChildren;

            int blackCount = 0;
            for (CadrePostBean bean : entryValue) {

                int cadreId = bean.getCadreId();
                TreeNode node = new TreeNode();
                CadreView cadre = cadreMap.get(cadreId);
                SysUserView uv = sysUserService.findById(cadre.getUserId());
                node.title = uv.getRealname() + "-" + postMap.get(bean.getPostId()).getName() +
                        (bean.additional ? "(兼审单位)" : "");

                node.key = cadreId + "_" + bean.getUnitId();
                node.select = true;

                // 本单位正职黑名单
                if (blackListMap.get(node.key) != null) {
                    node.select = false;
                    blackCount++;
                }

                titleChildren.add(node);
            }

            int selectCount = entryValue.size() - blackCount;
            titleNode.title = entry.getKey() + String.format("(%s", selectCount > 0 ? selectCount + "/" : "") + entryValue.size() + ")";
            rootChildren.add(titleNode);
        }
        return root;
    }

    public boolean idDuplicate(Integer id, int cadreId, int unitId){

        {
            AbroadAdditionalPostExample example = new AbroadAdditionalPostExample();
            AbroadAdditionalPostExample.Criteria criteria = example.createCriteria()
                    .andCadreIdEqualTo(cadreId).andUnitIdEqualTo(unitId);
            if (id != null) criteria.andIdNotEqualTo(id);
            if (abroadAdditionalPostMapper.countByExample(example) > 0) return true;
        }
        {
            CadreView cadre = iCadreMapper.getCadre(cadreId);
            // 这个单位是该干部的主职单位
            if(cadre!=null && cadre.getUnitId()!=null
                    && cadre.getUnitId().intValue()==unitId) return true;
        }

        return false;
    }

    @Transactional
    @Caching(evict={
            @CacheEvict(value="AbroadAdditionalPost:ALL", allEntries = true),
            @CacheEvict(value="AbroadAdditionalPost", key="#record.cadreId")
    })
    public void insertSelective(AbroadAdditionalPost record){

        Assert.isTrue(!idDuplicate(null, record.getCadreId(), record.getUnitId()), "duplicate");
        abroadAdditionalPostMapper.insertSelective(record);
    }

    @Transactional
    @Caching(evict={
            @CacheEvict(value="AbroadAdditionalPost:ALL", allEntries = true),
            @CacheEvict(value="AbroadAdditionalPost", allEntries = true)
    })
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        AbroadAdditionalPostExample example = new AbroadAdditionalPostExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        abroadAdditionalPostMapper.deleteByExample(example);
    }

    @Transactional
    @Caching(evict={
            @CacheEvict(value="AbroadAdditionalPost:ALL", allEntries = true),
            @CacheEvict(value="AbroadAdditionalPost", key="#record.cadreId")
    })
    public int updateByPrimaryKeySelective(AbroadAdditionalPost record){

        Assert.isTrue(!idDuplicate(record.getId(), record.getCadreId(), record.getUnitId()),"duplicate");

        return abroadAdditionalPostMapper.updateByPrimaryKeySelective(record);
    }

    @Cacheable(value="AbroadAdditionalPost", key="#cadreId")
    public List<AbroadAdditionalPost> findCadrePosts(int cadreId) {

        AbroadAdditionalPostExample example = new AbroadAdditionalPostExample();
        example.createCriteria().andCadreIdEqualTo(cadreId);
        List<AbroadAdditionalPost> abroadAdditionalPosts = abroadAdditionalPostMapper.selectByExample(example);

        return abroadAdditionalPosts;
    }

    // key: cadreId+"_"+unitId
    @Cacheable(value="AbroadAdditionalPost:ALL")
    public Map<String, AbroadAdditionalPost> findAll() {

        AbroadAdditionalPostExample example = new AbroadAdditionalPostExample();
        List<AbroadAdditionalPost> abroadAdditionalPostes = abroadAdditionalPostMapper.selectByExample(example);
        Map<String, AbroadAdditionalPost> map = new LinkedHashMap<>();
        for (AbroadAdditionalPost cPost : abroadAdditionalPostes) {
            map.put(cPost.getCadreId() + "_" + cPost.getUnitId(), cPost);
        }
        return map;
    }
}
