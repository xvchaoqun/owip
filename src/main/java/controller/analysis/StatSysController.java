package controller.analysis;

import controller.BaseController;
import domain.sys.SysOnlineStatic;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.joda.time.DateTime;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;
import java.util.List;

/**
 * Created by fafa on 2016/8/22.
 */
@Controller
public class StatSysController extends BaseController {


    @RequiresPermissions("stat:list")
    @RequestMapping("/stat_sys_page")
    public String stat_sys_page() {

        return "analysis/sys/stat_sys_page";
    }

    @RequiresPermissions("stat:list")
    @RequestMapping("/stat_online_day")
    public String stat_online_day(ModelMap modelMap) {

        Date start = new DateTime().minusMonths(3).toDate();
        Date end = new Date();

        List<SysOnlineStatic> beans = statMapper.online_static_day(start, end);
        modelMap.put("beans", beans);
        return "analysis/sys/stat_online_day";
    }

}
