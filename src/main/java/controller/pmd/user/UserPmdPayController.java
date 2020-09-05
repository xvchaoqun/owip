package controller.pmd.user;

import com.google.gson.Gson;
import controller.global.OpException;
import controller.pmd.PmdBaseController;
import domain.member.Member;
import domain.pmd.PmdMember;
import domain.pmd.PmdMonth;
import domain.pmd.PmdOrder;
import ext.pay.PayUtils;
import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import shiro.ShiroHelper;
import sys.constants.LogConstants;
import sys.utils.FormUtils;
import sys.utils.JSONUtils;
import sys.utils.RequestUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.*;

@Controller
@RequestMapping("/user/pmd")
public class UserPmdPayController extends PmdBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    // 缴费原则：本人或代缴（支部成员或支部管理员）
    // （应该判断当月缴费所在的支部，因为用户可能延迟缴费之后进行了组织关系转接）
    private PmdMember checkPayAuth(int pmdMemberId, boolean isSelfPay){

        PmdMember _pmdMember = pmdMemberMapper.selectByPrimaryKey(pmdMemberId);
        PmdMonth currentPmdMonth = pmdMonthService.getCurrentPmdMonth();
        PmdMember pmdMember = pmdMemberService.get(currentPmdMonth.getId(), _pmdMember.getUserId());

        int userId = ShiroHelper.getCurrentUserId();
        if(isSelfPay){ // 本人线上缴费

            ShiroHelper.checkPermission("userPmdMember:payConfirm");

            if(pmdMember.getUserId()!=userId){
                throw new UnauthorizedException();
            }
        }else{
            if(userId==pmdMember.getUserId()){
                throw new OpException("不允许给本人代缴");
            }
            // 代缴（只允许支部管理员或直属支部管理员进行代缴）
            Integer partyId = pmdMember.getPartyId();
            Integer branchId = pmdMember.getBranchId();

            Member member = memberService.get(userId);

            if(partyService.isDirectBranch(partyId)){

                if(member==null || member.getPartyId().intValue()!=partyId) {

                    List<Integer> adminPartyIds = pmdPartyAdminService.getAdminPartyIds(userId);
                    Set<Integer> adminPartyIdSet = new HashSet<>();
                    adminPartyIdSet.addAll(adminPartyIds);

                    if (!adminPartyIdSet.contains(partyId)) {
                        throw new UnauthorizedException();
                    }
                }
            }else{

                if(member==null || member.getPartyId().intValue()!=partyId
                        || member.getBranchId().intValue()!=branchId) {

                    List<Integer> adminBranchIds = pmdBranchAdminService.getAdminBranchIds(userId);
                    Set<Integer> adminBranchIdSet = new HashSet<>();
                    adminBranchIdSet.addAll(adminBranchIds);
                    if (!adminBranchIdSet.contains(branchId)) {
                        throw new UnauthorizedException();
                    }
                }
            }
        }

        return _pmdMember;
    }

    // 页面支付通知
    @RequestMapping("/callback")
    public String callback(HttpServletRequest request, ModelMap modelMap) throws UnsupportedEncodingException {

        Map<String, String[]> parameterMap = request.getParameterMap();
        logger.info("pmd page callback request.getParameterMap()=" + JSONUtils.toString(request.getParameterMap(), false));

        modelMap.put("verifySign", pmdOrderService.verifyNotifySign(request));

        if(parameterMap.size()>0) {

            String sn = request.getParameter("thirdorderid");
            PmdOrder pmdOrder = pmdOrderMapper.selectByPrimaryKey(sn);
            if(pmdOrder!=null && pmdOrder.getUserId().intValue()==ShiroHelper.getCurrentUserId()) {
                pmdOrderService.notify(request);
            }
        }

        return "pmd/user/callback";
    }

    // 支付订单确认
    //@RequiresPermissions("userPmdMember:payConfirm")
    @RequestMapping("/payConfirm")
    public String payConfirm(int id, @RequestParam(required = false, defaultValue = "1")Boolean isSelfPay,
                                        ModelMap modelMap) {

        PmdMember pmdMember = checkPayAuth(id, isSelfPay);
        modelMap.put("pmdMember", pmdMember);
        //modelMap.put("pay_url", PropertiesUtils.getString("pay.campuscard.url"));

        return "pmd/user/payConfirm";
    }

    //@RequiresPermissions("userPmdMember:payConfirm")
    @RequestMapping(value = "/payConfirm", method = RequestMethod.POST)
    @ResponseBody
    public Map do_payConfirm(int id, @RequestParam(required = false, defaultValue = "1")Boolean isSelfPay,
                             @RequestParam(required = false, defaultValue = "0")Boolean isMobile,
                             HttpServletRequest request) throws UnsupportedEncodingException {

        checkPayAuth(id, isSelfPay);

        PmdOrder order = pmdOrderService.payConfirm(id, isSelfPay,
                isMobile? PayUtils.orderType_PHONE: PayUtils.orderType_PC);
        logger.info(addLog(LogConstants.LOG_PMD, "支付已确认，跳转至支付页面...%s",
                JSONUtils.toString(order, false)));

        Gson gson = new Gson();
        Map<String, Object> params =  gson.fromJson(order.getParams(), Map.class);
        //params.put("sn", order.getSn());
        params.put("sign", order.getSign());
        
        Map<String, Object> resultMap = success(FormUtils.SUCCESS);
        resultMap.put("order", params);

        String homeURL = RequestUtils.getHomeURL(request);
        if(isMobile) {
            resultMap.put("thirdurl", homeURL + "/m/pmd/callback");
        }else{
            resultMap.put("thirdurl", homeURL + "/user/pmd/callback");
        }

        if(springProps.devMode) {
            // test
            Map<String, Object> callbackMap = new LinkedHashMap<>(params);
            callbackMap.remove("ordertype");
            callbackMap.remove("sign");
            callbackMap.put("orderid", order.getSn() + "back");
            callbackMap.put("state", "1");
            callbackMap.put("sign", URLEncoder.encode(PayUtils.sign(callbackMap), "UTF-8"));

            callbackMap.put("actulamt", params.get("tranamt")); // 实际交易金额
            resultMap.put("ret", FormUtils.requestParams(callbackMap));
        }

        return resultMap;
    }

    // 批量缴费订单确认
    //@RequiresPermissions("userPmdMember:payConfirm")
    @RequestMapping("/payConfirm_batch")
    public String payConfirm_batch(@RequestParam(name = "ids")Integer[] ids, boolean isDelay, ModelMap modelMap) {

        BigDecimal duePay = BigDecimal.ZERO;
        for (Integer id : ids) {
            PmdMember pmdMember = checkPayAuth(id, false);
            BigDecimal _duePay = pmdMember.getDuePay();
            if(_duePay==null){
                throw new OpException("还没有为{0}确定缴费额度，请先确定缴费额度后再操作。",
                        pmdMember.getUser().getRealname());
            }
            duePay = duePay.add(_duePay);
        }
        modelMap.put("ids", ids);
        modelMap.put("duePay", duePay);

        return "pmd/user/payConfirm_batch";
    }

    //@RequiresPermissions("userPmdMember:payConfirm")
    @RequestMapping(value = "/payConfirm_batch", method = RequestMethod.POST)
    @ResponseBody
    public Map do_payConfirm_batch(@RequestParam(name = "ids")Integer[] ids, boolean isDelay,
                                   HttpServletRequest request) throws UnsupportedEncodingException {

        for (Integer id : ids) {
            checkPayAuth(id, false);
        }

        PmdOrder order = pmdOrderService.batchPayConfirm(isDelay, ids);
        logger.info(addLog(LogConstants.LOG_PMD, "批量缴费支付已确认，跳转至支付页面...%s",
                JSONUtils.toString(order, false)));

        Gson gson = new Gson();
        Map<String, Object> params =  gson.fromJson(order.getParams(), Map.class);
        //params.put("sn", order.getSn());
        params.put("sign", order.getSign());
        
        Map<String, Object> resultMap = success(FormUtils.SUCCESS);
        resultMap.put("order", params);

        String homeURL = RequestUtils.getHomeURL(request);
        resultMap.put("thirdurl", homeURL + "/user/pmd/callback");

        // test
        /*Map<String, Object> callbackMap = new LinkedHashMap<>(params);
        callbackMap.remove("ordertype");
        callbackMap.remove("sign");
        callbackMap.put("orderid", order.getSn()+"back");
        callbackMap.put("state", "1");
        callbackMap.put("sign", URLEncoder.encode(BnuPayUtils.sign(callbackMap), "UTF-8"));

        callbackMap.put("actulamt", params.get("tranamt")); // 实际交易金额
        resultMap.put("ret", FormUtils.requestParams(callbackMap));*/

        return resultMap;
    }
}
