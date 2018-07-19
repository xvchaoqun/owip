package service.cadre;

import domain.base.MetaType;
import domain.cadre.Cadre;
import domain.cadre.CadreExample;
import domain.cadre.CadreView;
import domain.dispatch.Dispatch;
import domain.dispatch.DispatchCadre;
import domain.sys.SysUserView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.BaseMapper;
import service.base.MetaTypeService;
import service.dispatch.DispatchService;
import service.sys.SysUserService;
import sys.constants.CadreConstants;
import sys.tags.CmTag;
import sys.tool.tree.TreeNode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
    private DispatchService dispatchService;

    // 查找某个单位的正职（现任）
    public List<Cadre> findMainPost(int unitId) {

        List<Cadre> cadreList = new ArrayList<>();
        Map<Integer, MetaType> metaTypeMap = metaTypeService.findAll();
        {
            CadreExample example = new CadreExample();
            example.createCriteria().andUnitIdEqualTo(unitId)
                    .andStatusIn(Arrays.asList(CadreConstants.CADRE_STATUS_MIDDLE,
                            CadreConstants.CADRE_STATUS_LEADER));
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
        Map<Integer, Dispatch> dispatchMap = dispatchService.findAll();
        Map<Integer, MetaType> postMap = metaTypeService.metaTypes("mc_post");

        for (DispatchCadre dispatchCadre : dispatchCadres) {

            Dispatch dispatch = dispatchMap.get(dispatchCadre.getDispatchId());
            TreeNode node = new TreeNode();
            MetaType postType = postMap.get(dispatchCadre.getPostId());
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
}
