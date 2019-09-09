package service.cadre;

import domain.base.MetaType;
import domain.cadre.Cadre;
import domain.cadre.CadrePost;
import domain.cadre.CadreView;
import domain.cadre.CadreViewExample;
import domain.dispatch.Dispatch;
import domain.dispatch.DispatchCadre;
import domain.party.Branch;
import domain.party.Party;
import domain.sys.SysUserView;
import domain.sys.TeacherInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import service.BaseMapper;
import service.base.MetaTypeService;
import service.cadreReserve.CadreReserveService;
import service.party.BranchService;
import service.party.MemberService;
import service.party.PartyService;
import service.sys.SysUserService;
import service.sys.TeacherInfoService;
import sys.constants.CadreConstants;
import sys.tags.CmTag;
import sys.tool.tree.TreeNode;

import java.util.*;

/**
 * Created by fafa on 2016/12/24.
 */
@Service
public class CadreCommonService extends BaseMapper {

    @Autowired
    private MetaTypeService metaTypeService;
    @Autowired
    private SysUserService sysUserService;
    @Autowired(required = false)
    private CadreEduService cadreEduService;
    @Autowired(required = false)
    private MemberService memberService;
    @Autowired
    protected TeacherInfoService teacherInfoService;
    @Autowired
    protected CadrePostService cadrePostService;
    @Autowired
    protected CadreReserveService cadreReserveService;
    @Autowired
    protected CadreAdminLevelService cadreAdminLevelService;
    @Autowired
    protected PartyService partyService;
    @Autowired
    protected BranchService branchService;

    // 查找某个单位的正职（现任）
    public List<CadreView> findMainPost(Integer unitId) {

        List<CadreView> cadreList = new ArrayList<>();
        if(unitId==null) return cadreList;

        CadreViewExample example = new CadreViewExample();
        example.createCriteria().andUnitIdEqualTo(unitId)
                .andIsPrincipalEqualTo(true)
                .andStatusIn(Arrays.asList(CadreConstants.CADRE_STATUS_MIDDLE,
                        CadreConstants.CADRE_STATUS_LEADER));

        example.setOrderByClause("status desc, sort_order desc");

        return cadreViewMapper.selectByExample(example);
    }

    // 获取干部发文
    public TreeNode getDispatchCadreTree(int cadreId, Byte type) {

        Cadre cadre = cadreMapper.selectByPrimaryKey(cadreId);

        TreeNode root = new TreeNode();
        root.title = "选择离任文件";
        root.expand = true;
        root.isFolder = true;
        root.hideCheckbox = true;
        root.children = new ArrayList<TreeNode>();

        if(iDispatchMapper==null) return root;
        List<DispatchCadre> dispatchCadres = iDispatchMapper.selectDispatchCadreList(cadreId, type);
        Map<Integer, MetaType> postMap = metaTypeService.metaTypes("mc_post");

        for (DispatchCadre dispatchCadre : dispatchCadres) {

            Dispatch dispatch = dispatchCadre.getDispatch();
            TreeNode node = new TreeNode();
            MetaType postType = postMap.get(dispatchCadre.getPostType());
            node.title = CmTag.getDispatchCode(dispatch.getCode(), dispatch.getDispatchTypeId(), dispatch.getYear())
                    + "-" + dispatchCadre.getPost() + (postType != null ? ("-" + postType.getName()) : "");
            node.key = dispatchCadre.getId() + "";
            node.expand = false;
            node.isFolder = false;
            node.noLink = true;
            node.icon = false;
            node.hideCheckbox = false;
            node.tooltip = dispatch.getFile();
            node.select = (cadre.getDispatchCadreId() != null && cadre.getDispatchCadreId().intValue() == dispatchCadre.getId());

            root.children.add(node);
        }
        return root;
    }

    // 默认使用cadreId作为key
    public TreeNode getTree(Set<CadreView> cadreList, Set<Byte> cadreStatusList,
                            Set<Integer> selectIdSet, Set<Integer> disabledIdSet) {

        return getTree(cadreList, cadreStatusList, selectIdSet, disabledIdSet, true, true, false);
    }

    // 干部选择，（职务属性-干部 Set<cadreId>） , 用于审批人身份时disabledIdSet=null
    public TreeNode getTree(Set<CadreView> cadreList, // 干部列表
                            Set<Byte> cadreStatusList, // 干部库类别过滤
                            Set<Integer> selectIdSet, // 已选干部
                            Set<Integer> disabledIdSet,// 不可选干部
                            boolean useCadreId, // key使用 干部ID or 用户ID
                            boolean enableSelect, // 是否有选择框
                            boolean defExpand // 一级属性（职务属性）默认是否展开
    ) {

        if (null == selectIdSet) selectIdSet = new HashSet<>();
        if (null == disabledIdSet) disabledIdSet = new HashSet<>();

        TreeNode root = new TreeNode();
        root.title = "现任干部库";
        root.expand = true;
        root.isFolder = true;
        root.hideCheckbox = true;
        List<TreeNode> rootChildren = new ArrayList<TreeNode>();
        root.children = rootChildren;

        Map<Integer, MetaType> postMap = metaTypeService.metaTypes("mc_post");
        // 职务属性-干部
        Map<Integer, List<CadreView>> postIdCadresMap = new LinkedHashMap<>();

        /*CadreExample example = new CadreExample();
        example.createCriteria().andStatusEqualTo(CadreConstants.CADRE_STATUS_NOW);
        example.setOrderByClause(" sort_order desc");
        List<Cadre> cadres = cadreMapper.selectByExample(example);*/
        for (CadreView cadre : cadreList) {
            if (cadreStatusList.contains(cadre.getStatus())) {
                List<CadreView> list = null;
                MetaType postType = postMap.get(cadre.getPostType());

                int postId = -1;
                if(postType!=null) postId = postType.getId();

                if (postIdCadresMap.containsKey(postId)) {
                    list = postIdCadresMap.get(postId);
                }
                if (null == list) list = new ArrayList<>();
                list.add(cadre);

                postIdCadresMap.put(postId, list);
            }
        }

        // 排序
        Map<String, List<CadreView>> postCadresMap = new LinkedHashMap<>();
        for (MetaType metaType : postMap.values()) {
            if (postIdCadresMap.containsKey(metaType.getId()))
                postCadresMap.put(metaType.getName(), postIdCadresMap.get(metaType.getId()));
        }

        int i = 0;
        for (Map.Entry<String, List<CadreView>> entry : postCadresMap.entrySet()) {

            List<CadreView> entryValue = entry.getValue();
            TreeNode titleNode = new TreeNode();

            titleNode.expand = defExpand;
            titleNode.isFolder = true;
            List<TreeNode> titleChildren = new ArrayList<TreeNode>();
            titleNode.children = titleChildren;
            if (!enableSelect)
                titleNode.hideCheckbox = true;

            int selectCount = 0;
            for (CadreView cadre : entryValue) {

                int cadreId = cadre.getId();
                int userId = cadre.getUserId();
                String title = cadre.getTitle();
                TreeNode node = new TreeNode();
                SysUserView uv = sysUserService.findById(userId);
                node.title = uv.getRealname() + (title != null ? ("-" + title) : "");
                int key = useCadreId?cadreId:userId;
                node.key =  key + "";

                if (enableSelect && selectIdSet.contains(key)) {
                    selectCount++;
                    node.select = true;
                }
                if (!enableSelect || disabledIdSet.contains(key))
                    node.hideCheckbox = true;
                titleChildren.add(node);
            }
            titleNode.title = entry.getKey() + String.format("(%s", selectCount > 0 ? selectCount + "/" : "") + entryValue.size() + "人)";
            rootChildren.add(titleNode);
        }
        return root;
    }

    // 干部选择，（职务属性-干部 Set<cadreId>） , 用于审批人身份时disabledIdSet=null
    // 使用cadreId_unitId作为key
    public TreeNode getTree2(Set<CadreView> cadreList, // 干部列表
                            Set<Byte> cadreStatusList, // 干部库类别过滤
                            Set<String> selectIdSet, // 已选干部
                            Set<String> disabledIdSet,// 不可选干部
                            boolean enableSelect, // 是否有选择框
                            boolean defExpand // 一级属性（职务属性）默认是否展开
    ) {

        if (null == selectIdSet) selectIdSet = new HashSet<>();
        if (null == disabledIdSet) disabledIdSet = new HashSet<>();

        TreeNode root = new TreeNode();
        root.title = "现任干部库";
        root.expand = true;
        root.isFolder = true;
        root.hideCheckbox = true;
        List<TreeNode> rootChildren = new ArrayList<TreeNode>();
        root.children = rootChildren;

        Map<Integer, MetaType> postMap = metaTypeService.metaTypes("mc_post");
        // 职务属性-干部
        Map<Integer, List<CadreView>> postIdCadresMap = new LinkedHashMap<>();

        /*CadreExample example = new CadreExample();
        example.createCriteria().andStatusEqualTo(CadreConstants.CADRE_STATUS_NOW);
        example.setOrderByClause(" sort_order desc");
        List<Cadre> cadres = cadreMapper.selectByExample(example);*/
        for (CadreView cadre : cadreList) {
            if (cadreStatusList.contains(cadre.getStatus())) {
                List<CadreView> list = null;
                MetaType postType = postMap.get(cadre.getPostType());

                int postId = -1;
                if(postType!=null) postId = postType.getId();
                if (postIdCadresMap.containsKey(postId)) {
                    list = postIdCadresMap.get(postId);
                }
                if (null == list) list = new ArrayList<>();
                list.add(cadre);

                postIdCadresMap.put(postId, list);
            }
        }

        // 排序
        Map<String, List<CadreView>> postCadresMap = new LinkedHashMap<>();
        for (MetaType metaType : postMap.values()) {
            if (postIdCadresMap.containsKey(metaType.getId()))
                postCadresMap.put(metaType.getName(), postIdCadresMap.get(metaType.getId()));
        }

        int i = 0;
        for (Map.Entry<String, List<CadreView>> entry : postCadresMap.entrySet()) {

            List<CadreView> entryValue = entry.getValue();
            TreeNode titleNode = new TreeNode();

            titleNode.expand = defExpand;
            titleNode.isFolder = true;
            List<TreeNode> titleChildren = new ArrayList<TreeNode>();
            titleNode.children = titleChildren;
            if (!enableSelect)
                titleNode.hideCheckbox = true;

            int selectCount = 0;
            for (CadreView cadre : entryValue) {

                int cadreId = cadre.getId();
                int userId = cadre.getUserId();
                String title = cadre.getTitle();
                TreeNode node = new TreeNode();
                SysUserView uv = sysUserService.findById(userId);
                node.title = uv.getRealname() + (title != null ? ("-" + title) : "");
                String key = cadreId + "_" + cadre.getUnitId();
                node.key =  key + "";

                if (enableSelect && selectIdSet.contains(key)) {
                    selectCount++;
                    node.select = true;
                }
                if (!enableSelect || disabledIdSet.contains(key))
                    node.hideCheckbox = true;
                titleChildren.add(node);
            }
            titleNode.title = entry.getKey() + String.format("(%s", selectCount > 0 ? selectCount + "/" : "") + entryValue.size() + "人)";
            rootChildren.add(titleNode);
        }
        return root;
    }

    public void cadreBase(Integer cadreId, ModelMap modelMap){

        if(cadreId==null) return;

        CadreView cadre = iCadreMapper.getCadre(cadreId);
        modelMap.put("cadre", cadre);

        if(cadre == null) return;
        SysUserView uv = sysUserService.findById(cadre.getUserId());
        modelMap.put("uv", uv);

        Map<Integer, Branch> branchMap = branchService.findAll();
        Map<Integer, Party> partyMap = partyService.findAll();
        modelMap.put("branchMap", branchMap);
        modelMap.put("partyMap", partyMap);
        modelMap.put("member", memberService.get(uv.getId()));

        TeacherInfo teacherInfo = teacherInfoService.get(uv.getId());
        modelMap.put("teacherInfo", teacherInfo);

        // 兼职单位
        List<CadrePost> subCadrePosts = cadrePostService.getSubCadrePosts(cadreId);
        if (subCadrePosts.size() >= 1) {
            modelMap.put("subCadrePost1", subCadrePosts.get(0));
        }
        if (subCadrePosts.size() >= 2) {
            modelMap.put("subCadrePost2", subCadrePosts.get(1));
        }

        // 最高学历
        modelMap.put("highEdu", cadreEduService.getHighEdu(cadreId));
        //最高学位
        modelMap.put("highDegrees", cadreEduService.getHighDegrees(cadreId, null));

        // 是否已认定了参加工作时间，没认定前可修改
        modelMap.put("hasVerifyWorkTime", cadre.getVerifyWorkTime()!=null);

        // 如果是后备干部
        modelMap.put("cadreReserve", cadreReserveService.getNormalRecord(cadreId));
    }
}
