package controller.pmd.user;

import com.google.gson.Gson;
import controller.global.OpException;
import controller.pmd.PmdBaseController;
import domain.member.Member;
import domain.pmd.PmdMember;
import domain.pmd.PmdMonth;
import domain.pmd.PmdOrder;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import service.pmd.PayFormWszfBean;
import shiro.ShiroHelper;
import sys.constants.LogConstants;
import sys.utils.FormUtils;
import sys.utils.JSONUtils;
import sys.utils.PropertiesUtils;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Controller
@RequestMapping("/user/pmd")
public class UserPmdPayController extends PmdBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    // 校园统一支付平台
    @RequiresPermissions("userPmdMember:payConfirm")
    @RequestMapping("/payConfirm_wszf")
    public String payConfirm_wszf(int monthId, ModelMap modelMap) {

        int userId = ShiroHelper.getCurrentUserId();

        PmdMember pmdMember = pmdMemberService.get(monthId, userId);
        modelMap.put("pmdMember", pmdMember);

        PayFormWszfBean payFormBean = pmdOrderWszfService.createPayFormBean(pmdMember.getId());
        modelMap.put("payFormBean", payFormBean);

        modelMap.put("pay_url", PropertiesUtils.getString("pay.wszf.url"));

        // test
        /*PayNotifyWszfBean bean = new PayNotifyWszfBean();
        bean.setOrderDate(payFormBean.getOrderDate());
        bean.setOrderNo(payFormBean.getOrderNo());
        bean.setAmount(payFormBean.getAmount());
        bean.setJylsh(String.valueOf(1303190000001L + pmdMember.getId()));
        bean.setTranStat("1");
        bean.setReturn_type("1");
        String sign = pmdOrderWszfService.verifySign(bean);
        String ret = "orderDate=" + bean.getOrderDate() +
                "&orderNo=" + bean.getOrderNo() +
                "&amount=" + bean.getAmount() +
                "&jylsh=" + bean.getJylsh() +
                "&tranStat=" + bean.getTranStat() +
                "&return_type=" + bean.getReturn_type() +
                "&sign=" + sign;
        modelMap.put("ret", ret);*/
        // test

        return "pmd/user/payConfirm_wszf";
    }
    @RequiresPermissions("userPmdMember:payConfirm")
    @RequestMapping(value = "/payConfirm_wszf", method = RequestMethod.POST)
    @ResponseBody
    public Map do_payConfirm_wszf(int monthId) {

        PayFormWszfBean payFormBean = pmdOrderWszfService.payConfirm(monthId);
        logger.info(addLog(LogConstants.LOG_PMD, "支付已确认，跳转至支付页面...%s",
                JSONUtils.toString(payFormBean, false)));

        return success(FormUtils.SUCCESS);
    }

    // 缴费原则：本人或代缴（支部成员或支部管理员）
    // （应该判断当月缴费所在的支部，因为用户可能延迟缴费之后进行了组织关系转接）
    private PmdMember checkPayAuth(int pmdMemberId, boolean isSelfPay){

        PmdMember _pmdMember = pmdMemberMapper.selectByPrimaryKey(pmdMemberId);
        PmdMonth currentPmdMonth = pmdMonthService.getCurrentPmdMonth();
        PmdMember pmdMember = pmdMemberService.get(currentPmdMonth.getId(), _pmdMember.getUserId());

        int userId = ShiroHelper.getCurrentUserId();
        if(isSelfPay){ // 本人线上缴费

            SecurityUtils.getSubject().checkPermission("userPmdMember:payConfirm");

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

    // 支付订单确认
    //@RequiresPermissions("userPmdMember:payConfirm")
    @RequestMapping("/payConfirm")
    public String payConfirm(int id, @RequestParam(required = false, defaultValue = "1")Boolean isSelfPay,
                                        ModelMap modelMap) {

        PmdMember pmdMember = checkPayAuth(id, isSelfPay);
        modelMap.put("pmdMember", pmdMember);
        modelMap.put("pay_url", PropertiesUtils.getString("pay.campuscard.url"));

        return "pmd/user/payConfirm";
    }

    //@RequiresPermissions("userPmdMember:payConfirm")
    @RequestMapping(value = "/payConfirm", method = RequestMethod.POST)
    @ResponseBody
    public Map do_payConfirm(int id, @RequestParam(required = false, defaultValue = "1")Boolean isSelfPay) {

        checkPayAuth(id, isSelfPay);

        PmdOrder order = pmdOrderCampusCardService.payConfirm(id, isSelfPay);
        logger.info(addLog(LogConstants.LOG_PMD, "支付已确认，跳转至支付页面...%s",
                JSONUtils.toString(order, false)));

        Gson gson = new Gson();
        Map<String, Object> params =  gson.fromJson(order.getParams(), Map.class);
        params.put("sn", order.getSn());
        params.put("sign", order.getSign());
        
        Map<String, Object> resultMap = success(FormUtils.SUCCESS);
        resultMap.put("order", params);

        // test
        /*String paycode = (String) params.get("paycode");
        String payitem = "ZZBGZ001";
        String payer = order.getPayer();
        String payertype = (String) params.get("payertype");
        String sn = order.getSn();
        String amt = order.getAmt();
        String paid = "true";
        String paidtime = DateUtils.getCurrentDateTime("yyyy-MM-dd HH:mm:ss");

        
        String sign =  MD5Util.md5Hex(PmdOrderCampusCardService.notifySignStr(paycode, sn, amt, payer, paid, paidtime), "utf-8");
        String ret = "paycode=" + paycode +
                "&payitem=" + payitem +
                "&payer=" + payer +
                "&payertype=" + payertype +
                "&sn=" + sn +
                "&amt=" + amt +
                "&paid=" + paid +
                "&paidtime=" + paidtime +
                "&sign=" + sign;
        resultMap.put("ret", ret);*/
        // test

        return resultMap;
    }

    // 批量缴费订单确认
    //@RequiresPermissions("userPmdMember:payConfirm")
    @RequestMapping("/payConfirm_batch")
    public String payConfirm_batch(@RequestParam(name = "ids[]")Integer[] ids, boolean isDelay, ModelMap modelMap) {

        BigDecimal duePay = BigDecimal.ZERO;
        for (Integer id : ids) {
            PmdMember pmdMember = checkPayAuth(id, false);
            duePay = duePay.add(pmdMember.getDuePay());
        }
        modelMap.put("ids", ids);
        modelMap.put("duePay", duePay);
        modelMap.put("pay_url", PropertiesUtils.getString("pay.campuscard.url"));

        return "pmd/user/payConfirm_batch";
    }

    //@RequiresPermissions("userPmdMember:payConfirm")
    @RequestMapping(value = "/payConfirm_batch", method = RequestMethod.POST)
    @ResponseBody
    public Map do_payConfirm_batch(@RequestParam(name = "ids[]")Integer[] ids, boolean isDelay) {

        for (Integer id : ids) {
            checkPayAuth(id, false);
        }

        PmdOrder order = pmdOrderCampusCardService.batchPayConfirm(isDelay, ids);
        logger.info(addLog(LogConstants.LOG_PMD, "批量缴费支付已确认，跳转至支付页面...%s",
                JSONUtils.toString(order, false)));

        Gson gson = new Gson();
        Map<String, Object> params =  gson.fromJson(order.getParams(), Map.class);
        params.put("sn", order.getSn());
        params.put("sign", order.getSign());
        
        Map<String, Object> resultMap = success(FormUtils.SUCCESS);
        resultMap.put("order", params);

        // test
        /*String paycode = (String) params.get("paycode");
        String payitem = "ZZBGZ001";
        String payer = order.getPayer();
        String payertype = (String) params.get("payertype");
        String sn = order.getSn();
        String amt = order.getAmt();
        String paid = "true";
        String paidtime = DateUtils.getCurrentDateTime("yyyy-MM-dd HH:mm:ss");

        
        String sign =  MD5Util.md5Hex(PmdOrderCampusCardService.notifySignStr(paycode, sn, amt, payer, paid, paidtime), "utf-8");
        String ret = "paycode=" + paycode +
                "&payitem=" + payitem +
                "&payer=" + payer +
                "&payertype=" + payertype +
                "&sn=" + sn +
                "&amt=" + amt +
                "&paid=" + paid +
                "&paidtime=" + paidtime +
                "&sign=" + sign;
        resultMap.put("ret", ret);*/
        // test

        return resultMap;
    }
}
