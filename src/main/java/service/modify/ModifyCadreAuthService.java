package service.modify;

import domain.cadre.Cadre;
import domain.modify.ModifyCadreAuth;
import domain.modify.ModifyCadreAuthExample;
import domain.sys.MetaType;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;
import service.cadre.CadreService;
import service.helper.ContextHelper;
import service.helper.ShiroSecurityHelper;
import service.sys.MetaTypeService;
import sys.constants.SystemConstants;
import sys.tool.tree.TreeNode;
import sys.utils.IpUtils;

import java.util.*;

@Service
public class ModifyCadreAuthService extends BaseMapper {

    @Autowired
    private CadreService cadreService;
    @Autowired
    private MetaTypeService metaTypeService;

    // 干部库类别-职务属性-干部
    public TreeNode getTree(){

        Map<Integer, MetaType> postMap = metaTypeService.metaTypes("mc_post");

        TreeNode root = new TreeNode();
        root.title = "干部库";
        root.expand = true;
        root.isFolder = true;
        root.hideCheckbox = true;
        List<TreeNode> rootChildren = new ArrayList<TreeNode>();
        root.children = rootChildren;

        // 职务属性-现任干部
        Map<Integer, List<Cadre>> postIdCadresMap = new LinkedHashMap<>();
        // 考察对象
        List<Cadre> tempCadres = new ArrayList<>();
        // 后备干部库
        List<Cadre> reserveCadres = new ArrayList<>();

        Map<Integer, Cadre> cadreMap = cadreService.findAll();
        for (Cadre cadre : cadreMap.values()) {
            if(cadre.getStatus()== SystemConstants.CADRE_STATUS_NOW) {
                List<Cadre> list = null;
                MetaType postType = postMap.get(cadre.getPostId());
                int postId = postType.getId();
                if (postIdCadresMap.containsKey(postId)) {
                    list = postIdCadresMap.get(postId);
                }
                if (null == list) list = new ArrayList<>();
                list.add(cadre);
                postIdCadresMap.put(postId, list);
            }else if(cadre.getStatus()== SystemConstants.CADRE_STATUS_TEMP) {

                tempCadres.add(cadre);
            }else if(cadre.getStatus()== SystemConstants.CADRE_STATUS_RESERVE) {

                reserveCadres.add(cadre);
            }
        }
        // 按职务属性排序
        Map<String, List<Cadre>> postCadresMap = new LinkedHashMap<>();
        for (MetaType metaType : postMap.values()) {
            if(postIdCadresMap.containsKey(metaType.getId()))
                postCadresMap.put(metaType.getName(), postIdCadresMap.get(metaType.getId()));
        }

        TreeNode cadreRoot = new TreeNode();
        cadreRoot.title = SystemConstants.CADRE_STATUS_MAP.get(SystemConstants.CADRE_STATUS_NOW);
        cadreRoot.expand = true;
        cadreRoot.isFolder = true;
        cadreRoot.hideCheckbox = true;
        List<TreeNode> cadreRootChildren = new ArrayList<TreeNode>();
        cadreRoot.children = cadreRootChildren;
        rootChildren.add(cadreRoot);
        for (Map.Entry<String, List<Cadre>> entry : postCadresMap.entrySet()) {

            List<Cadre> entryValue = entry.getValue();
            TreeNode titleNode = new TreeNode();
            titleNode.title = entry.getKey();
            titleNode.expand = false;
            titleNode.isFolder = true;
            List<TreeNode> titleChildren = new ArrayList<TreeNode>();
            titleNode.children = titleChildren;

            for (Cadre cadre : entryValue) {

                String title = cadre.getTitle();
                TreeNode node = new TreeNode();
                node.title = cadre.getUser().getRealname() + (title!=null?("-" + title):"");
                node.key =  cadre.getId() + "";
                titleChildren.add(node);
            }
            cadreRootChildren.add(titleNode);
        }

        if(tempCadres.size()>0) {
            TreeNode tempCadreRoot = new TreeNode();
            tempCadreRoot.title = SystemConstants.CADRE_STATUS_MAP.get(SystemConstants.CADRE_STATUS_TEMP);
            tempCadreRoot.expand = false;
            tempCadreRoot.isFolder = true;
            tempCadreRoot.hideCheckbox = true;
            List<TreeNode> tempCadreRootChildren = new ArrayList<TreeNode>();
            tempCadreRoot.children = tempCadreRootChildren;
            rootChildren.add(tempCadreRoot);
            for (Cadre cadre : tempCadres) {

                String title = cadre.getTitle();
                TreeNode node = new TreeNode();
                node.title = cadre.getUser().getRealname() + (title != null ? ("-" + title) : "");
                node.key = cadre.getId() + "";
                tempCadreRootChildren.add(node);
            }
        }

        if(reserveCadres.size()>0) {
            TreeNode reserveCadreRoot = new TreeNode();
            reserveCadreRoot.title = SystemConstants.CADRE_STATUS_MAP.get(SystemConstants.CADRE_STATUS_RESERVE);
            reserveCadreRoot.expand = false;
            reserveCadreRoot.isFolder = true;
            reserveCadreRoot.hideCheckbox = true;
            List<TreeNode> reserveCadreRootChildren = new ArrayList<TreeNode>();
            reserveCadreRoot.children = reserveCadreRootChildren;
            rootChildren.add(reserveCadreRoot);
            for (Cadre cadre : reserveCadres) {

                String title = cadre.getTitle();
                TreeNode node = new TreeNode();
                node.title = cadre.getUser().getRealname() + (title != null ? ("-" + title) : "");
                node.key = cadre.getId() + "";
                reserveCadreRootChildren.add(node);
            }
        }

        return root;
    }

    @Transactional
    @CacheEvict(value="ModifyCadreAuths", key = "#record.cadreId")
    public int insertSelective(ModifyCadreAuth record){
        if(BooleanUtils.isTrue(record.getIsUnlimited())){
            record.setStartTime(null);
            record.setEndTime(null);
        }
        return modifyCadreAuthMapper.insertSelective(record);
    }

    @Transactional
    @CacheEvict(value="ModifyCadreAuths", allEntries = true)
    public void batchAdd(Integer[] cadreIds, Date start, Date end, boolean isUnlimited){

        ModifyCadreAuth record = new ModifyCadreAuth();
        record.setIsUnlimited(isUnlimited);
        if(BooleanUtils.isFalse(record.getIsUnlimited())) {
            record.setStartTime(start);
            record.setEndTime(end);
        }
        record.setAddTime(new Date());
        record.setAddUserId(ShiroSecurityHelper.getCurrentUserId());
        record.setAddIp(IpUtils.getRealIp(ContextHelper.getRequest()));

        for (Integer cadreId : cadreIds) {
            record.setCadreId(cadreId);
            modifyCadreAuthMapper.insertSelective(record);
        }
    }

    @Transactional
    @CacheEvict(value="ModifyCadreAuths", key = "#result.cadreId")
    public ModifyCadreAuth del(Integer id){

        ModifyCadreAuth result = modifyCadreAuthMapper.selectByPrimaryKey(id);
        modifyCadreAuthMapper.deleteByPrimaryKey(id);
        return result;
    }

    @Transactional
    @CacheEvict(value="ModifyCadreAuths", allEntries = true)
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        ModifyCadreAuthExample example = new ModifyCadreAuthExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        modifyCadreAuthMapper.deleteByExample(example);
    }

    @Transactional
    @CacheEvict(value="ModifyCadreAuths", key = "#record.cadreId")
    public void updateByPrimaryKeySelective(ModifyCadreAuth record){

        modifyCadreAuthMapper.updateByPrimaryKeySelective(record);

        if(BooleanUtils.isTrue(record.getIsUnlimited())){
            updateMapper.del_ModifyCadreAuth_time(record.getId());
        }
    }

    // 读取干部的所有权限设置
    @Cacheable(value="ModifyCadreAuths", key = "#cadreId")
    public List<ModifyCadreAuth> findAll(int cadreId) {

        ModifyCadreAuthExample example = new ModifyCadreAuthExample();
        example.createCriteria().andCadreIdEqualTo(cadreId);

        return modifyCadreAuthMapper.selectByExample(example);
    }
}
