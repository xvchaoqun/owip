package controller.pmd;

import domain.party.Branch;
import domain.party.BranchExample;
import domain.party.Party;
import domain.pmd.PmdBranch;
import domain.pmd.PmdMonth;
import domain.pmd.PmdPayBranch;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import service.pmd.PayNotifyWszfBean;
import service.pmd.PmdPayBranchService;
import shiro.ShiroHelper;
import sys.constants.LogConstants;
import sys.utils.DateUtils;
import sys.utils.JSONUtils;
import sys.utils.MD5Util;
import sys.utils.PropertiesUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created by lm on 2017/11/7.
 */
@Controller
@RequestMapping("/pmd/pay")
public class PmdPayTestController extends PmdBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private PmdPayBranchService pmdPayBranchService;
    //@Autowired
    //private PmdTestService pmdTestService;

    /**
     *
     delete from pmd_member_pay where member_id in(select id from pmd_member where branch_id  not in(1152,1157,1164,1168) and has_pay=0);

     delete from pmd_member where branch_id  not in(1152,1157,1164,1168) and has_pay=0;

     delete from pmd_branch where branch_id  not in(1152,1157,1164,1168);
     */
    //@RequestMapping("/ab")
    public void ab( HttpServletResponse response) throws IOException {

        PmdMonth currentMonth = pmdMonthService.getMonth(new Date());

        // 机关-人事处党支部
        String brachCode = "063333455";
        BranchExample example = new BranchExample();
        example.createCriteria().andCodeEqualTo(brachCode).andIsDeletedEqualTo(false);
        List<Branch> branchList = branchMapper.selectByExample(example);
        Branch branch = branchList.get(0);

        int monthId = currentMonth.getId();
        Party party = partyService.findAll().get(branch.getPartyId());
        int partyId = party.getId();
        int branchId = branch.getId();
        String partyName = party.getName();

        Set<Integer> allPayBranchIdSet = pmdPayBranchService.getAllPayBranchIdSet(null).keySet();
        PmdBranch record = new PmdBranch();
        record.setMonthId(monthId);
        record.setPartyId(partyId);
        record.setBranchId(branchId);
        record.setPartyName(partyName);
        record.setBranchName(branch.getName());
        record.setSortOrder(party.getSortOrder());
        record.setHasReport(false);

        pmdBranchMapper.insertSelective(record);

        if (!allPayBranchIdSet.contains(branchId)) {
            PmdPayBranch _record = new PmdPayBranch();
            _record.setBranchId(branchId);
            _record.setPartyId(partyId);
            _record.setMonthId(monthId);
            pmdPayBranchMapper.insertSelective(_record);
        }


        pmdMonthService.addBranch(branch.getPartyId(), branch.getId(), currentMonth);
        response.getWriter().write("success");
    }

    //@RequestMapping("/step")
   /* @ResponseBody
    public Map test(int step){

        switch (step){
            case 0:
                pmdTestService.step0();
                break;
            case 1:
                pmdTestService.step1();
                break;
            case 2:
                pmdTestService.step2();
                break;
            case 3:
                pmdTestService.step3();
                break;
        }

        return success("操作成功");
    }*/

    //@RequestMapping("/test")
    public String test(@RequestParam(required = false, defaultValue = "0.01") String amount,
                       @RequestParam(required = false, defaultValue = "0") int type,
                            HttpServletRequest request, ModelMap modelMap) {

        String orderDate = DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
        String orderNo = DateUtils.formatDate(new Date(), "yyyyMMdd") + DateUtils.formatDate(new Date(), "HHmmss");

        String return_url = "http://zzbgz.bnu.edu.cn/pmd/pay/test/returnPage";
        String notify_url = "http://zzbgz.bnu.edu.cn/pmd/pay/test/notifyPage";

        String xmpch = "004-2014050001";
        String key = "umz4aea6g97skeect0jtxigvjkrimd0o";

        if(type==1){
            xmpch = PropertiesUtils.getString("pay.id");
            key= PropertiesUtils.getString("pay.key");
        }


        amount = StringUtils.trim(amount);
        String md5Str = "orderDate=" + orderDate +
                "&orderNo=" + orderNo +
                "&amount=" + amount +
                "&xmpch=" + xmpch +
                "&return_url=" + return_url +
                "&notify_url=" + notify_url + key;
        String sign = MD5Util.md5Hex(md5Str, "utf-8");

        modelMap.put("orderDate", orderDate);
        modelMap.put("orderNo", orderNo);
        modelMap.put("xmpch", xmpch);
        modelMap.put("amount", amount);
        modelMap.put("return_url", return_url);
        modelMap.put("notify_url", notify_url);
        modelMap.put("key", key);
        modelMap.put("sign", sign);

        logger.info(addLog(ShiroHelper.getCurrentUserId(), ShiroHelper.getCurrentUsername(),
                LogConstants.LOG_PMD, "test跳转支付页面：%s", md5Str));

        return "pmd/pay/test";
    }

   // @RequestMapping("/test/returnPage")
    public String returnPage(PayNotifyWszfBean bean, HttpServletRequest request, ModelMap modelMap) {

        String ret =  JSONUtils.toString(request.getParameterMap(), false);
        logger.info("pmd returnPage request.getParameterMap()=" +ret);

        //pmdPayService.returnPage(bean);

        modelMap.put("ret", ret);

        return "pmd/pay/return_test_page";
    }

    //@RequestMapping("/test/notifyPage")
    public void notifyPage(PayNotifyWszfBean bean, HttpServletRequest request,
                           HttpServletResponse response, ModelMap modelMap) throws IOException {

        logger.info("pmd notifyPage request.getParameterMap()=" + JSONUtils.toString(request.getParameterMap(), false));
        response.getWriter().write("success");
    }
}
