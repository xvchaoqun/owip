package service.cla;

import domain.base.MetaType;
import domain.cadre.Cadre;
import domain.cadre.CadreView;
import domain.cla.ClaAdditionalPost;
import domain.cla.ClaAdditionalPostExample;
import domain.cla.ClaApproverBlackList;
import domain.cla.ClaApproverType;
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
public class ClaAdditionalPostService extends ClaBaseMapper {

    @Autowired
    private MetaTypeService metaTypeService;
    @Autowired
    private UnitService unitService;
    @Autowired
    private CadreService cadreService;
    @Autowired
    private ClaApproverBlackListService claApproverBlackListService;
    @Autowired
    private ClaApproverTypeService claApproverTypeService;
    @Autowired
    private SysUserService sysUserService;

    // 获取某个单位下的兼审正职
    public List<CadreView> findAdditionalPost(int unitId) {

        List<CadreView> cadreList = new ArrayList<>();
        ClaAdditionalPostExample example = new ClaAdditionalPostExample();
        example.createCriteria().andUnitIdEqualTo(unitId);
        List<ClaAdditionalPost> cPosts = claAdditionalPostMapper.selectByExample(example);
        for (ClaAdditionalPost cPost : cPosts) {
            cadreList.add(CmTag.getCadreById(cPost.getCadreId()));
        }
        return cadreList;
    }

    class CadrePostBean {
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
    public TreeNode getMainPostCadreTree() {

        TreeNode root = new TreeNode();
        root.title = "现任干部库";
        root.expand = true;
        root.isFolder = true;
        root.hideCheckbox = true;
        List<TreeNode> rootChildren = new ArrayList<TreeNode>();
        root.children = rootChildren;

        Map<Integer, MetaType> postMap = metaTypeService.metaTypes("mc_post");
        // 职务属性-干部
        Map<Integer, List<CadrePostBean>> unitIdCadresMap = new LinkedHashMap<>();

        for (Cadre cadre : cadreService.getCadres()) {

            CadreView cv = CmTag.getCadreById(cadre.getId());
            if ((cadre.getStatus() == CadreConstants.CADRE_STATUS_CJ
                    || cadre.getStatus() == CadreConstants.CADRE_STATUS_LEADER)
                    && BooleanUtils.isTrue(cv.getIsPrincipal())) {
                List<CadrePostBean> list = null;
                Integer unitId = cv.getUnitId();
                if (unitIdCadresMap.containsKey(unitId)) {
                    list = unitIdCadresMap.get(unitId);
                }
                if (null == list) list = new ArrayList<>();
                CadrePostBean bean = new CadrePostBean(cadre.getId(), cv.getPostType(), false);
                list.add(bean);

                unitIdCadresMap.put(unitId, list);
            }
        }

        Map<String, ClaAdditionalPost> claAdditionalPostMap = findAll();
        for (ClaAdditionalPost cPost : claAdditionalPostMap.values()) {
            CadreView cadre = CmTag.getCadreById(cPost.getCadreId());
            if (cadre.getStatus() == CadreConstants.CADRE_STATUS_CJ
                    || cadre.getStatus() == CadreConstants.CADRE_STATUS_LEADER) {
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
            if (unitIdCadresMap.containsKey(unit.getId()))
                unitCadresMap.put(unit.getName(), unitIdCadresMap.get(unit.getId()));
        }

        // 本单位正职身份
        ClaApproverType mainPostApproverType = claApproverTypeService.getMainPostApproverType();
        Map<Integer, ClaApproverBlackList> blackListMap = claApproverBlackListService.findAll(mainPostApproverType.getId());
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
                CadreView cadre = CmTag.getCadreById(cadreId);
                SysUserView uv = sysUserService.findById(cadre.getUserId());
                node.title = uv.getRealname() + "-" + postMap.get(bean.getPostId()).getName() +
                        (bean.additional ? "(兼审单位)" : "");

                if (bean.additional) {
                    node.unselectable = true;
                } else {
                    node.key = cadreId + "";
                }
                node.select = true;

                // 本单位正职黑名单
                if (!bean.additional && blackListMap.get(cadreId) != null) {
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
            ClaAdditionalPostExample example = new ClaAdditionalPostExample();
            ClaAdditionalPostExample.Criteria criteria = example.createCriteria()
                    .andCadreIdEqualTo(cadreId).andUnitIdEqualTo(unitId);
            if (id != null) criteria.andIdNotEqualTo(id);
            if (claAdditionalPostMapper.countByExample(example) > 0) return true;
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
            @CacheEvict(value="ClaAdditionalPost:ALL", allEntries = true),
            @CacheEvict(value="ClaAdditionalPost", key="#record.cadreId")
    })
    public void insertSelective(ClaAdditionalPost record){

        Assert.isTrue(!idDuplicate(null, record.getCadreId(), record.getUnitId()), "duplicate");
        claAdditionalPostMapper.insertSelective(record);
    }

    @Transactional
    @Caching(evict={
            @CacheEvict(value="ClaAdditionalPost:ALL", allEntries = true),
            @CacheEvict(value="ClaAdditionalPost", allEntries = true)
    })
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        ClaAdditionalPostExample example = new ClaAdditionalPostExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        claAdditionalPostMapper.deleteByExample(example);
    }

    @Transactional
    @Caching(evict={
            @CacheEvict(value="ClaAdditionalPost:ALL", allEntries = true),
            @CacheEvict(value="ClaAdditionalPost", key="#record.cadreId")
    })
    public int updateByPrimaryKeySelective(ClaAdditionalPost record){

        Assert.isTrue(!idDuplicate(record.getId(), record.getCadreId(), record.getUnitId()), "duplicate");

        return claAdditionalPostMapper.updateByPrimaryKeySelective(record);
    }

    @Cacheable(value="ClaAdditionalPost", key="#cadreId")
    public List<ClaAdditionalPost> findCadrePosts(int cadreId) {

        ClaAdditionalPostExample example = new ClaAdditionalPostExample();
        example.createCriteria().andCadreIdEqualTo(cadreId);
        List<ClaAdditionalPost> claAdditionalPosts = claAdditionalPostMapper.selectByExample(example);

        return claAdditionalPosts;
    }

    // key: cadreId+"_"+unitId
    @Cacheable(value="ClaAdditionalPost:ALL")
    public Map<String, ClaAdditionalPost> findAll() {

        ClaAdditionalPostExample example = new ClaAdditionalPostExample();
        List<ClaAdditionalPost> claAdditionalPostes = claAdditionalPostMapper.selectByExample(example);
        Map<String, ClaAdditionalPost> map = new LinkedHashMap<>();
        for (ClaAdditionalPost cPost : claAdditionalPostes) {
            map.put(cPost.getCadreId() + "_" + cPost.getUnitId(), cPost);
        }
        return map;
    }
}
