package service.cadre;

import domain.abroad.ApproverBlackList;
import domain.abroad.ApproverType;
import domain.cadre.Cadre;
import domain.cadre.CadreAdditionalPost;
import domain.cadre.CadreAdditionalPostExample;
import domain.cadre.CadreExample;
import domain.dispatch.Dispatch;
import domain.dispatch.DispatchCadre;
import domain.base.MetaType;
import domain.sys.SysUserView;
import domain.unit.Unit;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.BaseMapper;
import service.abroad.ApproverBlackListService;
import service.abroad.ApproverTypeService;
import service.cadreReserve.CadreReserveService;
import service.dispatch.DispatchService;
import service.base.MetaTypeService;
import service.sys.SysUserService;
import service.unit.UnitService;
import sys.constants.SystemConstants;
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
    @Autowired
    private CadreService cadreService;
    @Autowired
    private CadreReserveService cadreReserveService;

    // 获取某个单位下的兼审正职
    public List<Cadre> findAdditionalPost(int unitId){

        List<Cadre> cadreList = new ArrayList<>();
        Map<Integer, Cadre> cadreMap = cadreService.findAll();
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
            example.createCriteria().andUnitIdEqualTo(unitId)
                    .andStatusNotIn(Arrays.asList(SystemConstants.CADRE_STATUS_RESERVE,
                            SystemConstants.CADRE_STATUS_TEMP));
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

    // 获取干部发文
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
            if(cadre.getStatus()== SystemConstants.CADRE_STATUS_MIDDLE
                    || cadre.getStatus()== SystemConstants.CADRE_STATUS_LEADER) {
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

        Map<Integer, Cadre> cadreMap = cadreService.findAll();
        Map<Integer, MetaType> postMap = metaTypeService.metaTypes("mc_post");
        // 职务属性-干部
        Map<Integer, List<CadrePostBean>> unitIdCadresMap = new LinkedHashMap<>();

        for (Cadre cadre : cadreMap.values()) {
            if((cadre.getStatus()== SystemConstants.CADRE_STATUS_MIDDLE
                    || cadre.getStatus()== SystemConstants.CADRE_STATUS_LEADER)
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
            if((cadre.getStatus()== SystemConstants.CADRE_STATUS_MIDDLE
                    || cadre.getStatus()== SystemConstants.CADRE_STATUS_LEADER)
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
}
