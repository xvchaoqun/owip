package service.modify;

import domain.base.MetaType;
import domain.cadre.Cadre;
import domain.cadre.CadreExample;
import domain.cadreInspect.CadreInspect;
import domain.cadreInspect.CadreInspectExample;
import domain.cadreReserve.CadreReserve;
import domain.cadreReserve.CadreReserveExample;
import domain.modify.ModifyCadreAuth;
import domain.modify.ModifyCadreAuthExample;
import domain.sys.SysUserView;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;
import service.base.MetaTypeService;
import service.sys.SysUserService;
import shiro.ShiroHelper;
import sys.constants.SystemConstants;
import sys.tool.tree.TreeNode;
import sys.utils.ContextHelper;
import sys.utils.IpUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class ModifyCadreAuthService extends BaseMapper {

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private MetaTypeService metaTypeService;

    // 干部库类别-职务属性-干部
    public TreeNode getTree(){

        // 已经设置为永久生效的干部，不可选
        Set<Integer> disabledCadreIdSet = new HashSet();
        {
            List<Integer> validCadreIds = iModifyMapper.selectValidModifyCadreAuth();
            disabledCadreIdSet.addAll(validCadreIds);
        }

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
        List<Cadre> inspectCadres = new ArrayList<>();
        // 类别-后备干部库
        Map<Byte, List<Cadre>> typeReserveCadresMap = new LinkedHashMap<>();
        for (Map.Entry<Byte, String> entry : SystemConstants.CADRE_RESERVE_TYPE_MAP.entrySet()) {
            typeReserveCadresMap.put(entry.getKey(), new ArrayList<Cadre>());
        }

        {
            CadreExample example = new CadreExample();
            example.createCriteria().andStatusIn(Arrays.asList(SystemConstants.CADRE_STATUS_MIDDLE,
                    SystemConstants.CADRE_STATUS_LEADER));
            List<Cadre> cadres = cadreMapper.selectByExample(example);
            for (Cadre cadre : cadres) {
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

        {
            CadreInspectExample example = new CadreInspectExample();
            example.createCriteria().andStatusEqualTo(SystemConstants.CADRE_INSPECT_STATUS_NORMAL);
            example.setOrderByClause("sort_order asc");
            List<CadreInspect> cadreInspects = cadreInspectMapper.selectByExample(example);
            for (CadreInspect cadreInspect : cadreInspects) {
                Cadre cadre = cadreMapper.selectByPrimaryKey(cadreInspect.getCadreId());
                inspectCadres.add(cadre);
            }
        }

        {
            CadreReserveExample example = new CadreReserveExample();
            example.createCriteria().andStatusIn(Arrays.asList(SystemConstants.CADRE_RESERVE_STATUS_NORMAL,
                    SystemConstants.CADRE_RESERVE_STATUS_TO_INSPECT));
            example.setOrderByClause("sort_order asc");
            List<CadreReserve> cadreReserves = cadreReserveMapper.selectByExample(example);
            for (CadreReserve cadreReserve : cadreReserves) {
                Cadre cadre = cadreMapper.selectByPrimaryKey(cadreReserve.getCadreId());
                List<Cadre> cadres = null;
                Byte type = cadreReserve.getType();
                if(typeReserveCadresMap.containsKey(type)) {
                    cadres = typeReserveCadresMap.get(type);
                }
                if(null == cadres) cadres = new ArrayList<>();
                cadres.add(cadre);
                typeReserveCadresMap.put(type, cadres);
            }
        }


        // 按职务属性排序
        Map<String, List<Cadre>> postCadresMap = new LinkedHashMap<>();
        for (MetaType metaType : postMap.values()) {
            if(postIdCadresMap.containsKey(metaType.getId()))
                postCadresMap.put(metaType.getName(), postIdCadresMap.get(metaType.getId()));
        }

        TreeNode cadreRoot = new TreeNode();
        cadreRoot.title = "现任干部";
        cadreRoot.expand = false;
        cadreRoot.isFolder = true;
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
                if(disabledCadreIdSet.contains(cadre.getId())){
                    node.hideCheckbox = true;
                }
                titleChildren.add(node);
            }
            cadreRootChildren.add(titleNode);
        }

        if(inspectCadres.size()>0) {
            TreeNode inspectCadreRoot = new TreeNode();
            inspectCadreRoot.title = SystemConstants.CADRE_STATUS_MAP.get(SystemConstants.CADRE_STATUS_INSPECT);
            inspectCadreRoot.expand = true;
            inspectCadreRoot.isFolder = true;
            List<TreeNode> children = new ArrayList<TreeNode>();
            inspectCadreRoot.children = children;
            rootChildren.add(inspectCadreRoot);
            for (Cadre cadre : inspectCadres) {

                String title = cadre.getTitle();
                TreeNode node = new TreeNode();
                node.title = cadre.getUser().getRealname() + (title != null ? ("-" + title) : "");
                node.key = cadre.getId() + "";
                if(disabledCadreIdSet.contains(cadre.getId())){
                    node.hideCheckbox = true;
                }
                children.add(node);
            }
        }


        int count = 0;
        TreeNode reserveCadreRoot = new TreeNode();
        reserveCadreRoot.title = SystemConstants.CADRE_STATUS_MAP.get(SystemConstants.CADRE_STATUS_RESERVE);
        reserveCadreRoot.expand = true;
        reserveCadreRoot.isFolder = true;
        List<TreeNode> reserveCadreRootChildren = new ArrayList<TreeNode>();
        reserveCadreRoot.children = reserveCadreRootChildren;
        for(Map.Entry<Byte, List<Cadre>> entry : typeReserveCadresMap.entrySet()) {
            List<Cadre> entryValue = entry.getValue();
            if(entryValue.size()>0) {
                count += entryValue.size();
                TreeNode titleNode = new TreeNode();
                titleNode.title = SystemConstants.CADRE_RESERVE_TYPE_MAP.get(entry.getKey());
                titleNode.expand = false;
                titleNode.isFolder = true;
                List<TreeNode> titleChildren = new ArrayList<TreeNode>();
                titleNode.children = titleChildren;
                for (Cadre cadre : entryValue) {

                    String title = cadre.getTitle();
                    TreeNode node = new TreeNode();
                    node.title = cadre.getUser().getRealname() + (title != null ? ("-" + title) : "");
                    node.key = cadre.getId() + "";
                    if (disabledCadreIdSet.contains(cadre.getId())) {
                        node.hideCheckbox = true;
                    }
                    titleChildren.add(node);
                }
                reserveCadreRootChildren.add(titleNode);
            }
        }
        if(count>0) rootChildren.add(reserveCadreRoot);


        return root;
    }

    @Transactional
    @CacheEvict(value="ModifyCadreAuths", key = "#record.cadreId")
    public void insertSelective(ModifyCadreAuth record){
        if(BooleanUtils.isTrue(record.getIsUnlimited())){
            record.setStartTime(null);
            record.setEndTime(null);
        }
        modifyCadreAuthMapper.insertSelective(record);

        SysUserView uv = record.getCadre().getUser();
        sysUserService.clearUserCache(uv.getUserId(), uv.getUsername(), uv.getCode());
    }

    @Transactional
    @CacheEvict(value="ModifyCadreAuths", allEntries = true)
    public void batchAdd(Integer[] cadreIds, Date start, Date end, boolean isUnlimited){

        ModifyCadreAuth record = new ModifyCadreAuth();
        record.setIsUnlimited(isUnlimited);
        if(BooleanUtils.isNotTrue(record.getIsUnlimited())) {
            record.setStartTime(start);
            record.setEndTime(end);
        }
        record.setAddTime(new Date());
        record.setAddUserId(ShiroHelper.getCurrentUserId());
        record.setAddIp(IpUtils.getRealIp(ContextHelper.getRequest()));

        for (Integer cadreId : cadreIds) {
            record.setCadreId(cadreId);
            modifyCadreAuthMapper.insertSelective(record);

            SysUserView uv = record.getCadre().getUser();
            sysUserService.clearUserCache(uv.getUserId(), uv.getUsername(), uv.getCode());
        }
    }

    @Transactional
    @CacheEvict(value="ModifyCadreAuths", key = "#result.cadreId")
    public ModifyCadreAuth del(Integer id){

        ModifyCadreAuth result = modifyCadreAuthMapper.selectByPrimaryKey(id);
        modifyCadreAuthMapper.deleteByPrimaryKey(id);

        SysUserView uv = result.getCadre().getUser();
        sysUserService.clearUserCache(uv.getUserId(), uv.getUsername(), uv.getCode());
        return result;
    }

    @Transactional
    @CacheEvict(value="ModifyCadreAuths", allEntries = true)
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        for (Integer id : ids) {
            del(id);
        }
    }

    @Transactional
    @CacheEvict(value="ModifyCadreAuths", key = "#record.cadreId")
    public void updateByPrimaryKeySelective(ModifyCadreAuth record){

        modifyCadreAuthMapper.updateByPrimaryKeySelective(record);

        if(BooleanUtils.isTrue(record.getIsUnlimited())){
            iModifyMapper.del_ModifyCadreAuth_time(record.getId());
        }

        SysUserView uv = record.getCadre().getUser();
        sysUserService.clearUserCache(uv.getUserId(), uv.getUsername(), uv.getCode());
    }

    // 读取干部的所有权限设置
    @Cacheable(value="ModifyCadreAuths", key = "#cadreId")
    public List<ModifyCadreAuth> findAll(int cadreId) {

        ModifyCadreAuthExample example = new ModifyCadreAuthExample();
        example.createCriteria().andCadreIdEqualTo(cadreId);

        return modifyCadreAuthMapper.selectByExample(example);
    }
}
