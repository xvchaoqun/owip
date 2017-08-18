package service.crs;

import domain.base.MetaType;
import domain.cadre.CadreView;
import domain.crs.CrsExpert;
import domain.crs.CrsExpertExample;
import domain.crs.CrsExpertView;
import domain.crs.CrsExpertViewExample;
import domain.sys.SysUserView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;
import service.base.MetaTypeService;
import service.sys.SysUserService;
import sys.constants.SystemConstants;
import sys.tool.tree.TreeNode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class CrsExpertService extends BaseMapper {

    @Autowired
    private MetaTypeService metaTypeService;
    @Autowired
    private SysUserService sysUserService;

    public List<CrsExpertView> getExperts(byte status){

        CrsExpertViewExample example = new CrsExpertViewExample();
        example.createCriteria().andStatusEqualTo(status);
        example.setOrderByClause("sort_order desc");
        return crsExpertViewMapper.selectByExample(example);
    }

    @Transactional
    public void batchAdd(Integer[] userIds) {

        if (userIds == null || userIds.length == 0) return;

        for (Integer userId : userIds) {

            CrsExpert record = new CrsExpert();
            record.setUserId(userId);
            record.setStatus(SystemConstants.CRS_EXPERT_STATUS_NOW);
            insertSelective(record);
        }
    }

    // 拱批量选择专家
    public TreeNode getTree(Set<Integer> selectIdSet) {

        if (null == selectIdSet) selectIdSet = new HashSet<>();

        TreeNode root = new TreeNode();
        root.title = "请选择";
        root.expand = true;
        root.isFolder = true;
        root.hideCheckbox = true;
        List<TreeNode> rootChildren = new ArrayList<TreeNode>();
        root.children = rootChildren;
        for (Map.Entry<Byte, String> entry : SystemConstants.CRS_EXPERT_STATUS_MAP.entrySet()) {
            TreeNode groupNode = new TreeNode();
            groupNode.title = entry.getValue();
            groupNode.expand = true;
            groupNode.isFolder = true;
            groupNode.hideCheckbox = true;
            List<TreeNode> children = new ArrayList<TreeNode>();
            groupNode.children = children;

            List<CrsExpertView> inspectors = getExperts(entry.getKey());

            for (CrsExpertView expert : inspectors) {

                TreeNode node = new TreeNode();
                node.title = expert.getRealname() + "-" + expert.getCode();
                node.key = expert.getUserId() + "";

                if (selectIdSet.contains(expert.getUserId())) {
                    node.select = true;
                }
                children.add(node);
            }

            rootChildren.add(groupNode);
        }

        return root;
    }

    // 批量添加专家 key是userId
    public TreeNode getTree(Set<CadreView> cadreList, // 干部列表
                            Set<Integer> disabledIdSet,// 不可选干部
                            boolean enableSelect, // 是否有选择框
                            boolean defExpand // 一级属性（职务属性）默认是否展开
    ) {

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
        example.createCriteria().andStatusEqualTo(SystemConstants.CADRE_STATUS_NOW);
        example.setOrderByClause(" sort_order desc");
        List<Cadre> cadres = cadreMapper.selectByExample(example);*/
        for (CadreView cadre : cadreList) {
            if (SystemConstants.CRS_EXPERT_CADRE_STATUS_SET.contains(cadre.getStatus())) {
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

                int userId = cadre.getUserId();
                String title = cadre.getTitle();
                TreeNode node = new TreeNode();
                SysUserView uv = sysUserService.findById(cadre.getUserId());
                node.title = uv.getRealname() + (title != null ? ("-" + title) : "");
                node.key = userId + ""; //////////////////////////// key = userId

                if (!enableSelect || disabledIdSet.contains(userId)) {
                    selectCount++;
                    node.hideCheckbox = true;
                }
                titleChildren.add(node);
            }
            titleNode.title = entry.getKey() + String.format("(%s", selectCount > 0 ? selectCount + "/" : "") + entryValue.size() + "人)";
            rootChildren.add(titleNode);
        }
        return root;
    }

    public CrsExpertView getCrsExpert(int id){

        return crsExpertViewMapper.selectByPrimaryKey(id);
    }

    public List<CrsExpertView> getCrsExperts(byte status){

        CrsExpertViewExample example = new CrsExpertViewExample();
        example.createCriteria().andStatusEqualTo(status);
        example.setOrderByClause("cadre_status asc, cadre_sort_order desc");
        return crsExpertViewMapper.selectByExample(example);
    }

    @Transactional
    public void insertSelective(CrsExpert record) {

        crsExpertMapper.insertSelective(record);
    }

    public void abolish(Integer[] ids) {

        if (ids == null || ids.length == 0) return;

        CrsExpert record = new CrsExpert();
        record.setStatus(SystemConstants.CIS_INSPECTOR_STATUS_HISTORY);

        CrsExpertExample example = new CrsExpertExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));

        crsExpertMapper.updateByExampleSelective(record, example);
    }

    public void reuse(Integer[] ids) {

        if (ids == null || ids.length == 0) return;

        CrsExpert record = new CrsExpert();
        record.setStatus(SystemConstants.CIS_INSPECTOR_STATUS_NOW);

        CrsExpertExample example = new CrsExpertExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));

        crsExpertMapper.updateByExampleSelective(record, example);
    }

    @Transactional
    public void batchDel(Integer[] ids) {

        if (ids == null || ids.length == 0) return;

        CrsExpert record = new CrsExpert();
        record.setStatus(SystemConstants.CIS_INSPECTOR_STATUS_DELETE);

        CrsExpertExample example = new CrsExpertExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));

        crsExpertMapper.updateByExampleSelective(record, example);
    }

    @Transactional
    public int updateByPrimaryKeySelective(CrsExpert record) {
        return crsExpertMapper.updateByPrimaryKeySelective(record);
    }
}
