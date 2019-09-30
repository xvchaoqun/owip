package controller.cet;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import sys.constants.CadreConstants;
import sys.tool.tree.TreeNode;

import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

@Controller
@RequestMapping("/cet")
public class CetController extends CetBaseController{

    @RequiresPermissions("cetUnitProject:list")
    @RequestMapping("/selectCadres_tree")
    @ResponseBody
    public Map selectCadres_tree(Integer projectId, Integer traineeTypeId) throws IOException {

        Set<Integer> selectIdSet = new HashSet<>();
        if(projectId!=null && traineeTypeId!=null){
            selectIdSet = cetProjectObjService.getSelectedProjectObjUserIdSet(projectId, traineeTypeId);
        }

        Set<Byte> cadreStatusList = new HashSet<>();
        cadreStatusList.add(CadreConstants.CADRE_STATUS_MIDDLE);
        TreeNode tree = cadreCommonService.getTree(new LinkedHashSet<>(cadreService.findAll().values()),
                cadreStatusList, selectIdSet, null, false, true, false);

        Map<String, Object> resultMap = success();
        resultMap.put("tree", tree);
        return resultMap;
    }

    @RequiresPermissions("cetUnitProject:list")
    @RequestMapping("/selectCadreReserves_tree")
    @ResponseBody
    public Map selectCadreReserves_tree(Integer projectId, Integer traineeTypeId) throws IOException {

        Set<Integer> selectIdSet = new HashSet<>();
        if(projectId!=null && traineeTypeId!=null){
            selectIdSet = cetProjectObjService.getSelectedProjectObjUserIdSet(projectId, traineeTypeId);
        }

        TreeNode tree = cadreReserveService.getTree(selectIdSet, false);

        Map<String, Object> resultMap = success();
        resultMap.put("tree", tree);
        return resultMap;
    }

    @RequiresPermissions("cetUnitProject:list")
    @RequestMapping("/selectPartyMembers_tree")
    @ResponseBody
    public Map selectPartyMembers_tree(Integer projectId, Integer traineeTypeId) throws IOException {

        Set<Integer> selectIdSet = new HashSet<>();
        if(projectId!=null && traineeTypeId!=null){
            selectIdSet = cetProjectObjService.getSelectedProjectObjUserIdSet(projectId, traineeTypeId);
        }

        TreeNode tree = partyMemberService.getTree(selectIdSet);

        Map<String, Object> resultMap = success();
        resultMap.put("tree", tree);
        return resultMap;
    }

    @RequiresPermissions("cetUnitProject:list")
    @RequestMapping("/selectBranchMembers_tree")
    @ResponseBody
    public Map selectBranchMembers_tree(Integer projectId, Integer traineeTypeId) throws IOException {

        Set<Integer> selectIdSet = new HashSet<>();
        if(projectId!=null && traineeTypeId!=null){
            selectIdSet = cetProjectObjService.getSelectedProjectObjUserIdSet(projectId, traineeTypeId);
        }

        TreeNode tree = branchMemberService.getTree(selectIdSet);

        Map<String, Object> resultMap = success();
        resultMap.put("tree", tree);
        return resultMap;
    }

    @RequiresPermissions("cetUnitProject:list")
    @RequestMapping("/selectOrganizers_tree")
    @ResponseBody
    public Map selectOrganizers_tree(Integer projectId, Integer traineeTypeId) throws IOException {

        Set<Integer> selectIdSet = new HashSet<>();
        if(projectId!=null && traineeTypeId!=null){
            selectIdSet = cetProjectObjService.getSelectedProjectObjUserIdSet(projectId, traineeTypeId);
        }

        TreeNode tree = organizerService.getTree(selectIdSet);

        Map<String, Object> resultMap = success();
        resultMap.put("tree", tree);
        return resultMap;
    }

    @RequiresPermissions("cetUnitProject:list")
    @RequestMapping("/selectMemberApply_tree")
    @ResponseBody
    public Map selectMemberApply_tree(Integer projectId, Integer traineeTypeId, byte stage) throws IOException {

        Set<Integer> selectIdSet = new HashSet<>();
        if(projectId!=null && traineeTypeId!=null){
            selectIdSet = cetProjectObjService.getSelectedProjectObjUserIdSet(projectId, traineeTypeId);
        }
        TreeNode tree = memberApplyService.getTree(selectIdSet, stage);
        Map<String, Object> resultMap = success();
        resultMap.put("tree", tree);
        return resultMap;
    }
}
