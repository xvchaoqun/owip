package controller.pmd;

import controller.PmdBaseController;
import domain.pmd.PmdOrderCampuscard;
import domain.pmd.PmdOrderCampuscardExample;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import service.pmd.PmdPayCampusCardService;
import sys.constants.SystemConstants;
import sys.utils.FormUtils;
import sys.utils.JSONUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/pmd")
public class PmdOrderController extends PmdBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("pmdOw:admin")
    @RequestMapping("/pmdOrderCampusCard")
    public String pmdOrderCampusCard(int memberId, ModelMap modelMap) {

        PmdOrderCampuscardExample example = new PmdOrderCampuscardExample();
        example.createCriteria().andMemberIdEqualTo(memberId);
        example.setOrderByClause("create_time desc");
        List<PmdOrderCampuscard> pmdOrderCampuscards =
                pmdOrderCampuscardMapper.selectByExample(example);

        modelMap.put("pmdOrderCampuscards", pmdOrderCampuscards);

        return "/pmd/pmdOrder/pmdOrderCampuscard";
    }

    @RequiresPermissions("pmdOw:admin")
    @RequestMapping(value = "/pmdOrderCampusCard_closeTrade", method = RequestMethod.POST)
    @ResponseBody
    public Map pmdOrderCampusCard_closeTrade(String sn, HttpServletRequest request) throws IOException {

        PmdPayCampusCardService.CloseTradeRet ret = pmdPayCampusCardService.closeTrade(sn);
        logger.info(addLog(SystemConstants.LOG_OW, "后台关闭订单：%s，结果: %s", sn, JSONUtils.toString(ret, false)));

        return ret.success?success(FormUtils.SUCCESS):failed("关闭失败：" + ret.desc + "（" + ret.code+"）");
    }
}
