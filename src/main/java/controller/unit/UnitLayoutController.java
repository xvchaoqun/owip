package controller.unit;

import controller.BaseController;
import domain.cadre.CadreLeader;
import domain.cadre.CadreLeaderUnit;
import domain.base.MetaType;
import domain.unit.Unit;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import persistence.common.UnitAdminCadre;
import sys.constants.SystemConstants;

import java.util.*;

@Controller
public class UnitLayoutController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    // 单位综合管理 总体布局
    @RequiresPermissions("unitLayout:view")
    @RequestMapping("/unit_layout")
    public String unit_layout(@RequestParam(defaultValue = "0")int type, ModelMap modelMap) {

        modelMap.put("type", type);

        return "unit/unitLayout/unit_layout";
    }

    @RequiresPermissions("unitLayout:view")
    @RequestMapping("/unit_layout_byType")
    public String unit_layout_byType(int type, ModelMap modelMap) {

        if(type==0)
            return "forward:/unit_layout_leader";

        return "forward:/unit_layout_other";
    }
    // 校领导页面
    @RequiresPermissions("unitLayout:view")
    @RequestMapping("/unit_layout_leader")
    public String unit_layout_leader(ModelMap modelMap) {

        Map<Integer, MetaType> leaderTypeMap = metaTypeService.metaTypes("mc_leader_type");

        // key: leader.id
        Map<Integer, List<CadreLeaderUnit>> contactLeaderUnitMap = new LinkedHashMap<>();
        Map<Integer, List<CadreLeaderUnit>> managerLeaderUnitMap = new LinkedHashMap<>();
        Map<String, MetaType> codeKeyMap = metaTypeService.codeKeyMap();

        Map<MetaType, List<CadreLeader>> resultMap = new LinkedHashMap<>();
        for (MetaType leaderType : leaderTypeMap.values()) {

            List<CadreLeader> leaders = cadreLeaderService.findLeaderByType(leaderType.getId());
            resultMap.put(leaderType, leaders);

            for (CadreLeader leader : leaders) {
                // 根据校领导id，类别获取关联单位
                contactLeaderUnitMap.put(leader.getId(), cadreLeaderService.findLeaderUnitByType(leader.getId(),
                        codeKeyMap.get("mt_leader_contact").getId()));
                managerLeaderUnitMap.put(leader.getId(), cadreLeaderService.findLeaderUnitByType(leader.getId(),
                        codeKeyMap.get("mt_leader_manager").getId()));
            }
        }
        modelMap.put("resultMap", resultMap);
        modelMap.put("cLeaderUnitMap", contactLeaderUnitMap);
        modelMap.put("mLeaderUnitMap", managerLeaderUnitMap);

        return "unit/unitLayout/unit_layout_leader";
    }
    // 其他类型的单位页面
    @RequiresPermissions("unitLayout:view")
    @RequestMapping("/unit_layout_other")
    public String unit_layout_other(int type, ModelMap modelMap) {

        // 按类别查找正在运转单位
        List<Unit> runUnits = unitService.findUnitByTypeAndStatus(type, SystemConstants.UNIT_STATUS_RUN);
        List<Unit> historyUnits = unitService.findUnitByTypeAndStatus(type, SystemConstants.UNIT_STATUS_HISTORY);
        modelMap.put("runUnits", runUnits);
        modelMap.put("historyUnits", historyUnits);

        // 根据类型查找所有正在运转的单位的 现任 行政班子成员
        List<UnitAdminCadre> unitAdminCadres = iUnitMapper.findUnitAdminCadreByType(type);
        // 单位所有正职 key: unit.id
        Map<Integer, List<UnitAdminCadre>> pUnitAdminCadreMap = new HashMap<>();
        Map<Integer, List<UnitAdminCadre>> npUnitAdminCadreMap = new HashMap<>();
        for (UnitAdminCadre unitAdminCadre : unitAdminCadres) {
            int unitId = unitAdminCadre.getUnitId();
            List<UnitAdminCadre> pUnitAdminCadreList = pUnitAdminCadreMap.get(unitId);
            List<UnitAdminCadre> npUnitAdminCadreList = npUnitAdminCadreMap.get(unitId);
            if(unitAdminCadre.isPositive()){
                if(pUnitAdminCadreList==null){
                    pUnitAdminCadreList = new ArrayList<>();
                    pUnitAdminCadreMap.put(unitId, pUnitAdminCadreList);
                }
                pUnitAdminCadreList.add(unitAdminCadre);
            }else{
                if(npUnitAdminCadreList==null){
                    npUnitAdminCadreList = new ArrayList<>();
                    npUnitAdminCadreMap.put(unitId, npUnitAdminCadreList);
                }
                npUnitAdminCadreList.add(unitAdminCadre);
            }
        }
        modelMap.put("pUnitAdminCadreMap", pUnitAdminCadreMap);
        modelMap.put("npUnitAdminCadreMap", npUnitAdminCadreMap);
        modelMap.put("cadreMap", cadreService.findAll());

        return "unit/unitLayout/unit_layout_other";
    }
}
