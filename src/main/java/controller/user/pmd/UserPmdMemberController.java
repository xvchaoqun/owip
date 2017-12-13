package controller.user.pmd;

import controller.PmdBaseController;
import domain.pmd.PmdConfigMember;
import domain.pmd.PmdConfigMemberType;
import domain.pmd.PmdMemberPayView;
import domain.pmd.PmdMemberPayViewExample;
import domain.pmd.PmdNorm;
import mixin.MixinUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import shiro.ShiroHelper;
import sys.tool.paging.CommonList;
import sys.utils.DateUtils;
import sys.utils.FormUtils;
import sys.utils.JSONUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/user/pmd")
public class UserPmdMemberController extends PmdBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("userPmdMember:setSalary")
    @RequestMapping("/pmdMember_setSalary")
    public String pmdMember_setSalary(ModelMap modelMap) {

        int userId = ShiroHelper.getCurrentUserId();
        PmdConfigMember pmdConfigMember = pmdConfigMemberService.getPmdConfigMember(userId);
        modelMap.put("pmdConfigMember", pmdConfigMember);

        PmdConfigMemberType pmdConfigMemberType = pmdConfigMemberTypeService.get(pmdConfigMember.getConfigMemberTypeId());
        PmdNorm pmdNorm = pmdConfigMemberType.getPmdNorm();

        modelMap.put("pmdNorm", pmdNorm);

        if(BooleanUtils.isTrue(pmdConfigMember.getHasSetSalary())) {
            modelMap.put("duePay", pmdConfigMemberService.calDuePay(pmdConfigMember));
        }

        return "user/pmd/pmdMember_setSalary";
    }

    // 计算应缴党费额度
    @RequiresPermissions("userPmdMember:calDuePay")
    @RequestMapping(value = "/pmdMember_calDuePay", method = RequestMethod.POST)
    @ResponseBody
    public Map do_pmdMember_calDuePay(PmdConfigMember record, HttpServletRequest request) {

        BigDecimal duePay = pmdConfigMemberService.calDuePay(record);
        Map<String, Object> resultMap = success(FormUtils.SUCCESS);
        resultMap.put("duePay", duePay);

        return resultMap;
    }

    // 保存应缴党费额度
    @RequiresPermissions("userPmdMember:setSalary")
    @RequestMapping(value = "/pmdMember_setSalary", method = RequestMethod.POST)
    @ResponseBody
    public Map do_pmdMember_setSalary(PmdConfigMember record, HttpServletRequest request) {

        pmdConfigMemberService.setSalary(record);
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("userPmdMember:list")
    @RequestMapping("/pmdMember")
    public String pmdMember(ModelMap modelmap) {

        int userId = ShiroHelper.getCurrentUserId();
        modelmap.put("canSetSalary", pmdConfigMemberService.canSetSalary(userId));

        return "user/pmd/pmdMember_page";
    }

    @RequiresPermissions("userPmdMember:list")
    @RequestMapping("/pmdMember_data")
    public void pmdMember_data(HttpServletResponse response,
                               @DateTimeFormat(pattern = "yyyy-MM") Date payMonth,
                               Boolean hasPay,
                               Boolean isDelay,
                               Boolean isSelfPay,
                            Integer pageSize, Integer pageNo) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        int userId = ShiroHelper.getCurrentUserId();
        PmdMemberPayViewExample example = new PmdMemberPayViewExample();
        PmdMemberPayViewExample.Criteria criteria = example.createCriteria()
                .andUserIdEqualTo(userId);
        example.setOrderByClause("month_id desc");

        if(payMonth!=null){
            criteria.andPayMonthEqualTo(DateUtils.getFirstDateOfMonth(payMonth));
        }
        if (hasPay != null) {
            criteria.andHasPayEqualTo(hasPay);
        }
        if (isDelay != null) {
            if(BooleanUtils.isFalse(isDelay)){
                // 按时缴费，肯定已缴费
                criteria.andHasPayEqualTo(true);
            }
            criteria.andIsDelayEqualTo(isDelay);
        }
        if (isSelfPay != null) {
            criteria.andIsSelfPayEqualTo(isSelfPay);
        }

        long count = pmdMemberPayViewMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<PmdMemberPayView> records = pmdMemberPayViewMapper.selectByExampleWithRowbounds(example,
                new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(pmdMember.class, pmdMemberMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }
}
