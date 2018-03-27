package controller.sc.scMatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/sc")
public class ScMatterSettingController extends ScMatterBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    /*private static Set<String> codeSet = new LinkedHashSet<>(Arrays.asList("mc_sc_matter_check_ow_handle_type"
    , "mc_sc_matter_check_handle_type", "mc_sc_matter_check_result_type"));

    @RequiresPermissions("scMatterSetting:list")
    @RequestMapping("/scMatterSetting")
    public String scMatterSetting(ModelMap modelMap) {

        Map<String, MetaClass> metaClassMap = metaClassService.codeKeyMap();
        List<MetaClass> metaClassList = new ArrayList<>();
        for (String code : codeSet) {
            metaClassList.add(metaClassMap.get(code));
        }

        modelMap.put("metaClassList", metaClassList);

        return "sc/scMatter/scMatterSetting";
    }


    @RequiresPermissions("scMatterSetting:edit")
    @RequestMapping("/scMatterSetting_au")
    public String scMatterSetting_au(Integer id, ModelMap modelMap) {

        if(id!=null){
            MetaType metaType = metaTypeMapper.selectByPrimaryKey(id);
            modelMap.put("metaType", metaType);
        }

        return "sc/scMatter/scMatterSetting_au";
    }

    @RequiresPermissions("scMatterSetting:edit")
    @RequestMapping(value = "/scMatterSetting_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_scMatterSetting_au(MetaType record) {

        if(record.getId()==null) {
            record.setCode(metaTypeService.genCode());
            metaTypeService.insertSelective(record);
        }else{
            metaTypeService.updateByPrimaryKeySelective(record);
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("scMatterSetting:del")
    @RequestMapping(value = "/scMatterSetting_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_scMatterSetting_del(int id) {

        MetaType metaType = metaTypeMapper.selectByPrimaryKey(id);
        MetaClass metaClass = metaClassService.findAll().get(metaType.getClassId());
        if(!codeSet.contains(metaClass.getCode())){
            throw new UnauthorizedException();
        }

        metaTypeService.del(id);

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("scMatterSetting:changeOrder")
    @RequestMapping(value = "/scMatterSetting_changeOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map do_scMatterSetting_changeOrder(Integer id,Integer addNum, HttpServletRequest request) {

        metaTypeService.changeOrder(id, addNum);
        logger.info(addLog(SystemConstants.LOG_SC_MATTER, "个人有关事项参数设置-调序：%s, %s", id, addNum));
        return success(FormUtils.SUCCESS);
    }*/
}
