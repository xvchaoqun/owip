package controller.analysis;

import controller.BaseController;
import domain.sys.SysOnlineStatic;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.joda.time.DateTime;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.List;

/**
 * Created by fafa on 2016/8/22.
 */
@Controller
public class StatSysController extends BaseController {


    @RequiresPermissions("stat:list")
    @RequestMapping("/stat_sys_page")
    public String stat_sys_page(@RequestParam(defaultValue = "1")byte type, ModelMap modelMap) {

        modelMap.put("type", type);

        return "analysis/sys/stat_sys_page";
    }

    // type: 1 最近三个月 2 最近半年 3 最近一年 4 全部
    @RequiresPermissions("stat:list")
    @RequestMapping("/stat_online_day")
    public String stat_online_day(@RequestParam(defaultValue = "1")byte type, ModelMap modelMap) {


        int minusMonths = 3;
        switch (type){
            case 1: minusMonths=3;break;
            case 2: minusMonths=6;break;
            case 3:minusMonths=12;break;
            case 4:minusMonths=9999;break;
        }
        Date start = new DateTime().minusMonths(minusMonths).toDate();
        Date end = new Date();

        List<SysOnlineStatic> beans = iSysMapper.online_static_day(start, end);
        modelMap.put("beans", beans);
        return "analysis/sys/stat_online_day";
    }

}
