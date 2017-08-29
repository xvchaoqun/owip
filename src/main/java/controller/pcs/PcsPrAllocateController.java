package controller.pcs;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import controller.BaseController;
import domain.pcs.PcsConfig;
import domain.pcs.PcsPrAllocate;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.BooleanUtils;
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
import sys.gson.NullStringToEmptyAdapterFactory;
import sys.utils.FormUtils;
import sys.utils.JSONUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class PcsPrAllocateController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("pcsPrAllocate:list")
    @RequestMapping("/pcsPrAllocate")
    public String pcsPrAllocate(Boolean toUpdate, ModelMap modelMap) {

        PcsConfig currentPcsConfig = pcsConfigService.getCurrentPcsConfig();
        int configId = currentPcsConfig.getId();
        List<PcsPrAllocateBean> records = iPcsMapper.selectPcsPrAllocateBeans(configId, null, new RowBounds());
        modelMap.put("records", records);

        if (BooleanUtils.isTrue(toUpdate))
            return "pcs/pcsPrAllocate/pcsPrAllocate_au";

        return "pcs/pcsPrAllocate/pcsPrAllocate_page";
    }

    @RequiresPermissions("pcsPrAllocate:edit")
    @RequestMapping(value = "/pcsPrAllocate_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_pcsPrAllocate_au( String items, HttpServletRequest request) throws UnsupportedEncodingException {

        String itemsJson = new String(Base64.decodeBase64(items), "UTF-8");
        Gson gson = new GsonBuilder().registerTypeAdapterFactory(new NullStringToEmptyAdapterFactory()).create();
        JsonParser parser = new JsonParser();
        JsonArray ja = parser.parse(itemsJson).getAsJsonArray();
        List<PcsPrAllocate> records = new ArrayList<>();
        for (JsonElement obj : ja) {
            PcsPrAllocate record = gson.fromJson(obj, PcsPrAllocate.class);
            records.add(record);
        }
        PcsConfig currentPcsConfig = pcsConfigService.getCurrentPcsConfig();
        int configId = currentPcsConfig.getId();

        pcsPrAlocateService.batchAdd(configId, records);

        logger.info(addLog(SystemConstants.LOG_ADMIN, "更新党代表分配方案：%s", JSONUtils.toString(records)));

        return success(FormUtils.SUCCESS);
    }
}
