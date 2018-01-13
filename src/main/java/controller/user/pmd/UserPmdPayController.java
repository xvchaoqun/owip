package controller.user.pmd;

import controller.PmdBaseController;
import controller.global.OpException;
import domain.member.Member;
import domain.pmd.PmdMember;
import domain.pmd.PmdMonth;
import domain.pmd.PmdOrderCampuscard;
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
import sys.constants.SystemConstants;
import sys.utils.FormUtils;
import sys.utils.JSONUtils;
import sys.utils.PropertiesUtils;

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

        PayFormWszfBean payFormBean = pmdPayWszfService.createPayFormBean(pmdMember.getId());
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
        String sign = pmdPayWszfService.verifySign(bean);
        String ret = "orderDate=" + bean.getOrderDate() +
                "&orderNo=" + bean.getOrderNo() +
                "&amount=" + bean.getAmount() +
                "&jylsh=" + bean.getJylsh() +
                "&tranStat=" + bean.getTranStat() +
                "&return_type=" + bean.getReturn_type() +
                "&sign=" + sign;
        modelMap.put("ret", ret);*/
        // test

        return "user/pmd/payConfirm_wszf";
    }
    @RequiresPermissions("userPmdMember:payConfirm")
    @RequestMapping(value = "/payConfirm_wszf", method = RequestMethod.POST)
    @ResponseBody
    public Map do_payConfirm_wszf(int monthId) {

        PayFormWszfBean payFormBean = pmdPayWszfService.payConfirm(monthId);
        logger.info(addLog(SystemConstants.LOG_PMD, "支付已确认，跳转至支付页面...%s",
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

        return pmdMember;
    }

    // 校园卡
    //@RequiresPermissions("userPmdMember:payConfirm")
    @RequestMapping("/payConfirm_campuscard")
    public String payConfirm_campuscard(int id,
                                        @RequestParam(required = false, defaultValue = "1")Boolean isSelfPay,
                                        ModelMap modelMap) {

        PmdMember pmdMember = checkPayAuth(id, isSelfPay);
        modelMap.put("pmdMember", pmdMember);
        modelMap.put("pay_url", PropertiesUtils.getString("pay.campuscard.url"));

        return "user/pmd/payConfirm_campuscard";
    }

    //@RequiresPermissions("userPmdMember:payConfirm")
    @RequestMapping(value = "/payConfirm_campuscard", method = RequestMethod.POST)
    @ResponseBody
    public Map do_payConfirm_campuscard(int id,
                                        @RequestParam(required = false, defaultValue = "1")Boolean isSelfPay) {

        checkPayAuth(id, isSelfPay);

        PmdOrderCampuscard order = pmdPayCampusCardService.payConfirm(id, isSelfPay);
        logger.info(addLog(SystemConstants.LOG_PMD, "支付已确认，跳转至支付页面...%s",
                JSONUtils.toString(order, false)));

        Map<String, Object> resultMap = success(FormUtils.SUCCESS);
        resultMap.put("order", order);

        // test
        /*PayNotifyCampusCardBean bean = new PayNotifyCampusCardBean();
        bean.setPaycode(order.getPaycode());
        bean.setPayitem("ZZBGZ001");
        bean.setPayer(order.getPayer());
        bean.setPayertype(order.getPayertype());
        bean.setSn(order.getSn());
        bean.setAmt(order.getAmt());
        bean.setPaid("true");
        bean.setPaidtime(DateUtils.getCurrentDateTime("yyyy-MM-dd HH:mm:ss"));

        String sign =  MD5Util.md5Hex(pmdPayCampusCardService.signMd5Str(bean), "utf-8");
        String ret = "paycode=" + bean.getPaycode() +
                "&payitem=" + bean.getPayitem() +
                "&payer=" + bean.getPayer() +
                "&payertype=" + bean.getPayertype() +
                "&sn=" + bean.getSn() +
                "&amt=" + bean.getAmt() +
                "&paid=" + bean.getPaid() +
                "&paidtime=" + bean.getPaidtime() +
                "&sign=" + sign;
        resultMap.put("ret", ret);*/
        // test

        return resultMap;
    }
}
