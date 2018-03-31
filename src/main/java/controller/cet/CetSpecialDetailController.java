package controller.cet;

import domain.cet.CetSpecial;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import sys.constants.SystemConstants;
import sys.utils.FormUtils;

import java.math.BigDecimal;
import java.util.Map;

@Controller
@RequestMapping("/cet")
public class CetSpecialDetailController extends CetBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("cetSpecial:edit")
    @RequestMapping("/cetSpecial_detail")
    public String cetSpecial_detail(Integer specialId, ModelMap modelMap) {

        if (specialId != null) {
            CetSpecial cetSpecial = cetSpecialMapper.selectByPrimaryKey(specialId);
            modelMap.put("cetSpecial", cetSpecial);
        }
        return "cet/cetSpecial/cetSpecial_detail/menu";
    }

    @RequiresPermissions("cetSpecial:edit")
    @RequestMapping("/cetSpecial_detail/obj")
    public String specialee(int specialId, ModelMap modelMap) {

        CetSpecial cetSpecial = cetSpecialMapper.selectByPrimaryKey(specialId);
        modelMap.put("cetSpecial", cetSpecial);

        return "cet/cetSpecial/cetSpecial_detail/obj";
    }

    @RequiresPermissions("cetSpecial:edit")
    @RequestMapping("/cetSpecial_detail/setting")
    public String time(int specialId, ModelMap modelMap) {

        CetSpecial cetSpecial = cetSpecialMapper.selectByPrimaryKey(specialId);
        modelMap.put("cetSpecial", cetSpecial);

        return "cet/cetSpecial/cetSpecial_detail/setting";
    }

    @RequiresPermissions("cetSpecial:edit")
    @RequestMapping(value = "/cetSpecial_detail/setting", method = RequestMethod.POST)
    @ResponseBody
    public Map do_time(int specialId, BigDecimal requirePeriod) {

        CetSpecial cetSpecial = cetSpecialMapper.selectByPrimaryKey(specialId);

        CetSpecial record = new CetSpecial();
        record.setId(specialId);
        record.setRequirePeriod(requirePeriod);

        cetSpecialMapper.updateByPrimaryKeySelective(record);

        logger.info(addLog(SystemConstants.LOG_CET, "更新专题培训参数设置：%s~%s",
                cetSpecial.getName(), requirePeriod));

        return success(FormUtils.SUCCESS);
    }
}
