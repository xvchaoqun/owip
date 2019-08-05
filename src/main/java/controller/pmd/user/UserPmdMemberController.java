package controller.pmd.user;

import controller.pmd.PmdBaseController;
import ext.domain.ExtRetireSalary;
import ext.domain.ExtRetireSalaryExample;
import domain.pmd.*;
import domain.sys.SysUserView;
import ext.service.ExtRetireSalaryImport;
import mixin.MixinUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import shiro.ShiroHelper;
import sys.constants.PmdConstants;
import sys.constants.RoleConstants;
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

    @Autowired
    private ExtRetireSalaryImport extRetireSalaryImport;

    private Logger logger = LoggerFactory.getLogger(getClass());

    private Integer checkPayAuth(Integer pmdMemberId, boolean isSelfPay){

        if(isSelfPay){
            throw new UnauthorizedException();
            //SecurityUtils.getSubject().checkPermission("userPmdMember:setSalary");
            //return ShiroHelper.getCurrentUserId();
        }else{
            // 组织部管理员或支部管理员可代替设置工资
            SecurityUtils.getSubject().checkPermission("userPmdMember:helpSetSalary");

            PmdMember _pmdMember = pmdMemberMapper.selectByPrimaryKey(pmdMemberId);
            PmdMonth currentPmdMonth = pmdMonthService.getCurrentPmdMonth();
            PmdMember pmdMember = pmdMemberService.get(currentPmdMonth.getId(), _pmdMember.getUserId());

            if(ShiroHelper.lackRole(RoleConstants.ROLE_PMD_OW)) {
                // （只允许支部管理员或直属支部管理员进行代缴）
                Integer partyId = pmdMember.getPartyId();
                Integer branchId = pmdMember.getBranchId();

                int userId = ShiroHelper.getCurrentUserId();
                if (!pmdBranchAdminService.isBranchAdmin(userId, partyId, branchId)) {
                    throw new UnauthorizedException();
                }
            }

            return pmdMember.getUserId();
        }
    }

    // 支部管理员代替设置工资时，需要传入pmdMemberId和isSelf=0
    //@RequiresPermissions("userPmdMember:setSalary")
    @RequestMapping("/pmdMember_setSalary")
    public String pmdMember_setSalary(Integer pmdMemberId,
                                      @RequestParam(required = false, defaultValue = "1")Boolean isSelf,
                                      ModelMap modelMap) {

        int userId;
        if(isSelf) {
            SecurityUtils.getSubject().checkPermission("userPmdMember:setSalary");
            userId = ShiroHelper.getCurrentUserId();
        }else {
            userId = checkPayAuth(pmdMemberId, isSelf);
        }

        PmdConfigMember pmdConfigMember = pmdConfigMemberService.getPmdConfigMember(userId);
        modelMap.put("pmdConfigMember", pmdConfigMember);

        if(pmdConfigMember.getConfigMemberType()== PmdConstants.PMD_MEMBER_TYPE_RETIRE){

            return "pmd/user/pmdMember_setRetireBase";
        }

        PmdConfigMemberType pmdConfigMemberType = pmdConfigMemberTypeService.get(pmdConfigMember.getConfigMemberTypeId());
        PmdNorm pmdNorm = pmdConfigMemberType.getPmdNorm();

        modelMap.put("pmdNorm", pmdNorm);

        if(BooleanUtils.isTrue(pmdConfigMember.getHasSetSalary())) {
            modelMap.put("duePay", pmdExtService.calDuePay(pmdConfigMember));
        }

        return "pmd/user/pmdMember_setSalary";
    }

    // 计算应缴党费额度
    //@RequiresPermissions("userPmdMember:calDuePay")
    @RequestMapping(value = "/pmdMember_calDuePay", method = RequestMethod.POST)
    @ResponseBody
    public Map do_pmdMember_calDuePay(PmdConfigMember record, HttpServletRequest request) {

        BigDecimal duePay = pmdExtService.calDuePay(record);
        Map<String, Object> resultMap = success(FormUtils.SUCCESS);
        resultMap.put("duePay", duePay);

        return resultMap;
    }

    // 保存应缴党费额度
    //@RequiresPermissions("userPmdMember:setSalary")
    @RequestMapping(value = "/pmdMember_setSalary", method = RequestMethod.POST)
    @ResponseBody
    public Map do_pmdMember_setSalary(Integer pmdMemberId, PmdConfigMember record,
                                      @RequestParam(required = false, defaultValue = "1")Boolean isSelf,
                                      HttpServletRequest request) {

        int userId = checkPayAuth(pmdMemberId, isSelf);
        record.setUserId(userId);
        pmdConfigMemberService.setSalary(record, isSelf);
        return success(FormUtils.SUCCESS);
    }

    // 同步最新月份的离退休人员党费计算基数
    @RequestMapping("/pmdMember_syncRetireSalary")
    @ResponseBody
    public Map pmdMember_syncRetireSalary(int pmdMemberId) {

        int userId = checkPayAuth(pmdMemberId, false);
        SysUserView uv = sysUserService.findById(userId);

        extRetireSalaryImport.byCode(uv.getCode());

        ExtRetireSalaryExample example = new ExtRetireSalaryExample();
        example.createCriteria().andZghEqualTo(uv.getCode());
        example.setOrderByClause("rq desc");
        List<ExtRetireSalary> extRetireSalaries = extRetireSalaryMapper.selectByExampleWithRowbounds(example, new RowBounds(0, 1));

        Map resultMap = new HashMap<>();
        resultMap.put("exist", false);
        if(extRetireSalaries!=null && extRetireSalaries.size()==1){

            ExtRetireSalary extRetireSalary = extRetireSalaries.get(0);
            resultMap.put("base", extRetireSalary.getBase());
            resultMap.put("rq", extRetireSalary.getRq());

            resultMap.put("exist", true);
        }

        return resultMap;
    }

    @RequestMapping(value = "/pmdMember_setRetireBase", method = RequestMethod.POST)
    @ResponseBody
    public Map do_pmdMember_setRetireBase(int pmdMemberId, BigDecimal retireSalary, HttpServletRequest request) {

        int userId = checkPayAuth(pmdMemberId, false);

        pmdConfigMemberService.setRetireBase(userId, retireSalary);
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("userPmdMember:list")
    @RequestMapping("/pmdMember")
    public String pmdMember(ModelMap modelmap) {

        int userId = ShiroHelper.getCurrentUserId();
        modelmap.put("canSetSalary", pmdConfigMemberService.canSetSalary(userId));

        return "pmd/user/pmdMember_page";
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
