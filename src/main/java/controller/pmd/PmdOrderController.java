package controller.pmd;

import domain.pmd.PmdOrder;
import domain.sys.SysUserView;
import ext.common.pay.OrderQueryResult;
import mixin.MixinUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import persistence.pmd.common.IPmdOrder;
import service.pmd.PmdOrderService;
import sys.constants.LogConstants;
import sys.constants.PmdConstants;
import sys.tool.paging.CommonList;
import sys.utils.FormUtils;
import sys.utils.JSONUtils;

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
    @RequestMapping("/pmdOrder_orders")
    public String pmdOrder_orders(int memberId, ModelMap modelMap) {

        List<PmdOrder> pmdOrders = iPmdMapper.findRelateOrders(PmdConstants.PMD_ORDER_TYPE_MONTH, memberId);
        modelMap.put("pmdOrders", pmdOrders);

        return "/pmd/pmdOrder/pmdOrder_orders";
    }

    @RequiresPermissions("pmdOw:admin")
    @RequestMapping("/pmdOrderItemList")
    public String pmdOrderItemList(String sn, ModelMap modelMap) {

        List<Map> orderItems = iPmdMapper.listOrderItems(sn);
        modelMap.put("orderItems", orderItems);

        return "pmd/pmdOrder/pmdOrderItemList";
    }

    @RequiresPermissions("pmdOw:admin")
    @RequestMapping("/pmdOrder")
    public String pmdOrder(int userId, ModelMap modelMap) {

        SysUserView uv = sysUserService.findById(userId);
        modelMap.put("uv", uv);

        return "pmd/pmdOrder/pmdOrder_page";
    }

    @RequiresPermissions("pmdOw:admin")
    @RequestMapping("/pmdOrder_data")
    public void pmdOrder_data(HttpServletResponse response,
                              int userId,
                             Integer pageSize, Integer pageNo) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        // 查询支付人或缴费人
        int count = iPmdMapper.countPayList(userId);
        if ((pageNo - 1) * pageSize >= count) {
            pageNo = Math.max(1, pageNo - 1);
        }
        List<IPmdOrder> records = iPmdMapper.selectPayList(userId, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(pmdOrder.class, pmdOrderMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }
    
    @RequiresPermissions("pmdOw:closeTrade")
    @RequestMapping(value = "/pmdOrder_closeTrade", method = RequestMethod.POST)
    @ResponseBody
    public Map pmdOrder_closeTrade(String sn, HttpServletRequest request) throws IOException {

        PmdOrderService.CloseTradeRet ret = pmdOrderService.closeTrade(sn);
        logger.info(addLog(LogConstants.LOG_PMD, "后台关闭订单：%s，结果: %s", sn, JSONUtils.toString(ret, false)));

        return ret.success?success(FormUtils.SUCCESS):failed("关闭失败：" + ret.ret);
    }

    @RequiresPermissions("pmdOw:admin")
    @RequestMapping("/pmdOrder_query")
    public String pmdOrder_query(String sn, ModelMap modelMap) throws IOException {

        OrderQueryResult queryResult = pmdOrderService.query(sn);
        modelMap.put("hasPay", queryResult.isHasPay());

        modelMap.put("ret", queryResult.getRet());

        PmdOrder order = pmdOrderMapper.selectByPrimaryKey(sn);
        modelMap.put("order", order);

        return "pmd/pmdOrder/pmdOrder_query";
    }

    // 查询支付成功后，主动更新订单状态
    @RequiresPermissions("pmdOw:admin")
    @RequestMapping(value = "/pmdOrder_query_callback", method = RequestMethod.POST)
    @ResponseBody
    public Map pmdOrder_query_callback(String sn, ModelMap modelMap) throws IOException {

        pmdOrderService.processQuery(sn);

        return success();
    }
}
