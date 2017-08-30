package controller.pcs;

import controller.BaseController;
import domain.pcs.PcsConfig;
import domain.pcs.PcsPrAllocate;
import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import persistence.common.bean.PcsPrAllocateBean;
import sys.constants.SystemConstants;
import sys.gson.GsonUtils;
import sys.utils.FormUtils;
import sys.utils.JSONUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

@Controller
public class PcsPrAllocateController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("pcsPrAllocate:list")
    @RequestMapping("/pcsPrAllocate")
    public String pcsPrAllocate(ModelMap modelMap) {

        PcsConfig currentPcsConfig = pcsConfigService.getCurrentPcsConfig();
        int configId = currentPcsConfig.getId();
        List<PcsPrAllocateBean> records = iPcsMapper.selectPcsPrAllocateBeans(configId, null, new RowBounds());
        modelMap.put("records", records);

        return "pcs/pcsPrAllocate/pcsPrAllocate_au";
    }

    @RequiresPermissions("pcsPrAllocate:edit")
    @RequestMapping(value = "/pcsPrAllocate_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_pcsPrAllocate_au( String items, HttpServletRequest request) throws UnsupportedEncodingException {

        List<PcsPrAllocate> records = GsonUtils.toBeans(items, PcsPrAllocate.class);
        PcsConfig currentPcsConfig = pcsConfigService.getCurrentPcsConfig();
        int configId = currentPcsConfig.getId();

        pcsPrAlocateService.batchUpdate(configId, records);

        logger.info(addLog(SystemConstants.LOG_ADMIN, "更新党代表分配方案：%s", JSONUtils.toString(records)));

        return success(FormUtils.SUCCESS);
    }


}
