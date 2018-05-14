package controller.pmd;

import domain.pmd.PmdOrderCampuscard;
import domain.pmd.PmdOrderCampuscardExample;
import domain.pmd.PmdOrderCampuscardView;
import domain.pmd.PmdOrderCampuscardViewExample;
import domain.sys.SysUserView;
import mixin.MixinUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import service.pmd.PmdPayCampusCardService;
import sys.constants.LogConstants;
import sys.tool.paging.CommonList;
import sys.utils.FormUtils;
import sys.utils.JSONUtils;
import sys.utils.PropertiesUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/pmd")
public class PmdOrderController extends PmdBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("pmdOw:admin")
    @RequestMapping("/pmdOrderCampuscard_orders")
    public String pmdOrderCampuscard_orders(int memberId, ModelMap modelMap) {

        PmdOrderCampuscardExample example = new PmdOrderCampuscardExample();
        example.createCriteria().andMemberIdEqualTo(memberId);
        example.setOrderByClause("create_time desc");
        List<PmdOrderCampuscard> pmdOrderCampuscards =
                pmdOrderCampuscardMapper.selectByExample(example);

        modelMap.put("pmdOrderCampuscards", pmdOrderCampuscards);

        return "/pmd/pmdOrder/pmdOrderCampuscard_orders";
    }

    @RequiresPermissions("pmdOw:admin")
    @RequestMapping("/pmdOrderCampuscard")
    public String pmdOrderCampuscard(int userId, ModelMap modelMap) {

        SysUserView uv = sysUserService.findById(userId);
        modelMap.put("uv", uv);

        return "pmd/pmdOrder/pmdOrderCampuscard_page";
    }

    @RequiresPermissions("pmdOw:admin")
    @RequestMapping("/pmdOrderCampuscard_data")
    public void pmdOrderCampuscard_data(HttpServletResponse response,
                              int userId,
                             Integer pageSize, Integer pageNo) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        PmdOrderCampuscardViewExample example = new PmdOrderCampuscardViewExample();

        // 查询支付人或缴费人
        SysUserView uv = sysUserService.findById(userId);
        String code = uv.getCode();
        example.or().andMemberUserIdEqualTo(userId);
        example.or().andPayerEqualTo(code);

        example.setOrderByClause("create_time desc");

        long count = pmdOrderCampuscardViewMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {
            pageNo = Math.max(1, pageNo - 1);
        }
        List<PmdOrderCampuscardView> records = pmdOrderCampuscardViewMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(pmdOrderCampuscard.class, pmdOrderCampuscardMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }
    
    @RequiresPermissions("pmdOw:admin")
    @RequestMapping(value = "/pmdOrderCampuscard_closeTrade", method = RequestMethod.POST)
    @ResponseBody
    public Map pmdOrderCampuscard_closeTrade(String sn, HttpServletRequest request) throws IOException {

        PmdPayCampusCardService.CloseTradeRet ret = pmdPayCampusCardService.closeTrade(sn);
        logger.info(addLog(LogConstants.LOG_PMD, "后台关闭订单：%s，结果: %s", sn, JSONUtils.toString(ret, false)));

        return ret.success?success(FormUtils.SUCCESS):failed("关闭失败：" + ret.desc + "（" + ret.code+"）");
    }

    @RequiresPermissions("pmdOw:admin")
    @RequestMapping("/pmdOrderCampuscard_query")
    public String pmdOrderCampuscard_query(String sn, String code, ModelMap modelMap) throws IOException {

        String ret = pmdPayCampusCardService.query(sn, code);
        modelMap.put("ret", ret);

        PmdOrderCampuscard order = pmdOrderCampuscardMapper.selectByPrimaryKey(sn);
        modelMap.put("order", order);

        String keys = PropertiesUtils.getString("pay.campuscard.keys");
        modelMap.put("keys",keys);
        modelMap.put("yek", StringUtils.reverse(keys));


        return "pmd/pmdOrder/pmdOrderCampuscard_query";
    }
}
