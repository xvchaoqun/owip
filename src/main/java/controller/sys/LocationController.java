package controller.sys;

import controller.BaseController;
import domain.Location;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import sys.constants.SystemConstants;
import sys.utils.FormUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@Controller
public class LocationController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequestMapping("/location_JSON")
    public String location_JSON(ModelMap modelMap) {

        modelMap.put("JSON", locationService.toJSON());
        return "common/location_json";
    }

    @RequiresPermissions("location:list")
    @RequestMapping("/location")
    public String location() {

        return "index";
    }
    @RequiresPermissions("location:list")
    @RequestMapping("/location_page")
    public String location_page() {

        return "sys/location/location_page";
    }

    @RequestMapping("/location_node")
    @ResponseBody
    public Map getChildNodes(@RequestParam(defaultValue = "0")int parentCode){

        Map<String, Object> map = success(FormUtils.SUCCESS);
        map.put("result", locationService.findChildNodes(parentCode));

        return map;
    }

    @RequiresPermissions("location:edit")
    @RequestMapping(value = "/location_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_location_au(Location record, HttpServletRequest request) {

        Integer id = record.getId();

        if(locationService.idDuplicate(id, record.getCode())){
            throw new RuntimeException("编码重复");
        }

        if (id == null) {
            locationService.insertSelective(record);
            logger.info(addLog(request, SystemConstants.LOG_ADMIN, "添加省、市、地区：%s", record.getId()));
        } else {

            locationService.updateByPrimaryKeySelective(record);
            logger.info(addLog(request, SystemConstants.LOG_ADMIN, "更新省、市、地区：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("location:edit")
    @RequestMapping("/location_au")
    public String location_au(Integer id, Integer parentCode, ModelMap modelMap) {
        Location location = null;
        if (id != null) {
            location = locationMapper.selectByPrimaryKey(id);
        }else{
            if(parentCode!=null){
                location = new Location();
                location.setParentCode(parentCode);
            }
        }

        modelMap.put("location", location);
        return "sys/location/location_au";
    }

    @RequiresPermissions("location:del")
    @RequestMapping(value = "/location_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_location_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            locationService.del(id);
            logger.info(addLog(request, SystemConstants.LOG_ADMIN, "删除省、市、地区：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("location:del")
    @RequestMapping(value = "/location_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            locationService.batchDel(ids);
            logger.info(addLog(request, SystemConstants.LOG_ADMIN, "批量删除省、市、地区：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }
}
