package controller.pmd;

import com.google.gson.Gson;
import controller.global.OpException;
import domain.base.MetaType;
import domain.member.Member;
import domain.pmd.*;
import domain.pmd.PmdFeeExample.Criteria;
import domain.sys.SysUserView;
import ext.utils.Pay;
import mixin.MixinUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import shiro.ShiroHelper;
import sys.constants.LogConstants;
import sys.constants.PmdConstants;
import sys.constants.RoleConstants;
import sys.tags.CmTag;
import sys.tool.paging.CommonList;
import sys.utils.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

@Controller
@RequestMapping("/pmd")
public class PmdFeeController extends PmdBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("pmdFee:list")
    @RequestMapping("/pmdFee")
    public String pmdFee(Integer partyId,Integer branchId,ModelMap modelMap) {

        if (partyId != null) {
            modelMap.put("party",partyMapper.selectByPrimaryKey(partyId));
        }
        if (branchId != null) {
            modelMap.put("branch",branchMapper.selectByPrimaryKey(branchId));
        }

        return "pmd/pmdFee/pmdFee_page";
    }

    @RequiresPermissions("pmdFee:list")
    @RequestMapping("/pmdFee_data")
    @ResponseBody
    public void pmdFee_data(HttpServletResponse response,
                                    Integer userId,
                                    Integer partyId,
                                    Integer branchId,
                                    Boolean isOnlinePay,
                                    Boolean hasPay,
                                    Date payTime,
                                Byte status,
                                 @RequestParam(required = false, defaultValue = "0") int export,
                                 Integer[] ids, // 导出的记录
                                 Integer pageSize, Integer pageNo)  throws IOException{

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        PmdFeeExample example = new PmdFeeExample();
        Criteria criteria = example.createCriteria().andStatusEqualTo(PmdConstants.PMD_FEE_STATUS_NORMAL);
        example.setOrderByClause("id desc");

        List<Integer> adminPartyIds = pmdPartyAdminService.getAdminPartyIds(ShiroHelper.getCurrentUserId());
        List<Integer> adminBranchIds = pmdBranchAdminService.getAdminBranchIds(ShiroHelper.getCurrentUserId());

        criteria.addPermits(adminPartyIds, adminBranchIds);

        if (userId!=null) {
            criteria.andUserIdEqualTo(userId);
        }
        if (partyId!=null) {
            criteria.andPartyIdEqualTo(partyId);
        }
        if (branchId!=null) {
            criteria.andBranchIdEqualTo(branchId);
        }
        if (isOnlinePay!=null) {
            criteria.andIsOnlinePayEqualTo(isOnlinePay);
        }
        if (hasPay!=null) {
            criteria.andHasPayEqualTo(hasPay);
        }
        if (payTime!=null) {
        criteria.andPayTimeGreaterThan(payTime);
        }

        if (export == 1) {
            if(ids!=null && ids.length>0)
                criteria.andIdIn(Arrays.asList(ids));
            pmdFee_export(example, response);
            return;
        }

        long count = pmdFeeMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<PmdFee> records= pmdFeeMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(pmdFee.class, pmdFeeMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("pmdFee:edit")
    @RequestMapping(value = "/pmdFee_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_pmdFee_au(PmdFee record, HttpServletRequest request) {

        Integer id = record.getId();
        Member member = memberService.get(record.getUserId());

        record.setPartyId(member.getPartyId());
        record.setBranchId(member.getBranchId());

        if(record.getEndMonth()==null){
            record.setEndMonth(record.getStartMonth());
        }

        if(record.getStartMonth().after(record.getEndMonth())){
            return failed("缴费月份有误");
        }

        SysUserView uv = CmTag.getUserById(record.getUserId());
        record.setUserType(uv.getType());

        checkDuplicate(id, record.getUserId(), record.getStartMonth(), record.getEndMonth());

        if (id == null) {

            record.setIsOnlinePay(true);
            record.setHasPay(false);
            record.setStatus(PmdConstants.PMD_FEE_STATUS_NORMAL);
            pmdFeeService.insertSelective(record);
            logger.info(log( LogConstants.LOG_PMD, "添加党员缴纳党费：{0}", record.getId()));
        } else {

            record.setIsOnlinePay(null);
            record.setStatus(null);
            pmdFeeService.updateByPrimaryKeySelective(record);
            logger.info(log( LogConstants.LOG_PMD, "更新党员缴纳党费：{0}", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("pmdFee:edit")
    @RequestMapping("/pmdFee_import")
    public String pmdFee_import(ModelMap modelMap) {

        return "pmd/pmdFee/pmdFee_import";
    }

    @RequiresPermissions("pmdFee:edit")
    @RequestMapping(value = "/pmdFee_import", method = RequestMethod.POST)
    @ResponseBody
    public Map do_pmdFee_import(HttpServletRequest request) throws InvalidFormatException, IOException {

        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile xlsx = multipartRequest.getFile("xlsx");

        OPCPackage pkg = OPCPackage.open(xlsx.getInputStream());
        XSSFWorkbook workbook = new XSSFWorkbook(pkg);
        XSSFSheet sheet = workbook.getSheetAt(0);
        List<Map<Integer, String>> xlsRows = ExcelUtils.getRowData(sheet);

        List<PmdFee> records = new ArrayList<>();
        int row = 1;
        for (Map<Integer, String> xlsRow : xlsRows) {

            PmdFee record = new PmdFee();
            row++;

            String userCode = StringUtils.trim(xlsRow.get(0));
            if (StringUtils.isBlank(userCode)) {
                throw new OpException("第{0}行工作证号为空", row);
            }
            SysUserView uv = sysUserService.findByCode(userCode);
            if (uv == null) {
                throw new OpException("第{0}行工作证号[{1}]不存在", row, userCode);
            }
            int userId = uv.getUserId();
            record.setUserId(userId);
            record.setUserType(uv.getType());

            Member member = memberService.get(userId);
            if(member==null){
                throw new OpException("第{0}行工作证号[{1}]不是党员", row, userCode);
            }
            record.setPartyId(member.getPartyId());
            record.setBranchId(member.getBranchId());

            String typeName = StringUtils.trimToNull(xlsRow.get(2));
            MetaType metaType = CmTag.getMetaTypeByName("mc_pmd_fee_type", typeName);
            if(metaType==null) {
                throw new OpException("第{0}行缴费类型不存在", row, userCode);
            }
            record.setType(metaType.getId());

            String _startMonth = StringUtils.trimToNull(xlsRow.get(3));
            Date startMonth = DateUtils.parseStringToDate(_startMonth);
            if (startMonth == null) {
                throw new OpException("第{0}行起始月份为空", row);
            }
            record.setStartMonth(startMonth);

            String _endMonth = StringUtils.trimToNull(xlsRow.get(4));
            Date endMonth = DateUtils.parseStringToDate(_endMonth);
            if (endMonth == null) {
                endMonth = startMonth;
            }
            record.setEndMonth(endMonth);

            try {
                checkDuplicate(null, userId, startMonth, endMonth);
            }catch (Exception ex){
                throw new OpException("第{0}行" + ex.getMessage(), row);
            }

            String _fee = StringUtils.trimToNull(xlsRow.get(5));
            if (StringUtils.isBlank(_fee) || !NumberUtils.isParsable(_fee) || new BigDecimal(_fee).compareTo(BigDecimal.ZERO)<=0) {
                throw new OpException("第{0}行缴费金额有误", row);
            }
            BigDecimal fee = new BigDecimal(_fee);
            record.setAmt(fee);

            record.setReason(StringUtils.trimToNull(xlsRow.get(6)));
            record.setRemark(StringUtils.trimToNull(xlsRow.get(7)));

            record.setIsOnlinePay(true);
            record.setHasPay(false);
            record.setStatus(PmdConstants.PMD_FEE_STATUS_NORMAL);

            records.add(record);
        }

        Collections.reverse(records); // 逆序排列，保证导入的顺序正确

        int addCount = pmdFeeService.bacthImport(records);
        int totalCount = records.size();
        Map<String, Object> resultMap = success(FormUtils.SUCCESS);
        resultMap.put("addCount", addCount);
        resultMap.put("total", totalCount);

        logger.info(log(LogConstants.LOG_ADMIN,
                "导入补缴记录成功，总共{0}条记录，其中成功导入{1}条记录，{2}条覆盖",
                totalCount, addCount, totalCount - addCount));

        return resultMap;
    }

    // 添加或缴费确认时，判断是否存在重复缴费记录
    private void checkDuplicate(Integer pmdFeeId, int userId, Date startMonth, Date endMonth){

        SysUserView uv = CmTag.getUserById(userId);

        // 判断是否已存在单独的补缴记录
        PmdFeeExample example = new PmdFeeExample();
        example.createCriteria().andUserIdEqualTo(userId)
                .andStartMonthLessThanOrEqualTo(endMonth)
                .andEndMonthGreaterThanOrEqualTo(startMonth);
        List<PmdFee> pmdFees = pmdFeeMapper.selectByExample(example);
        if(pmdFees.size()>1
                || (pmdFees.size()>0 && (pmdFeeId==null || pmdFees.get(0).getId().intValue()!=pmdFeeId))){
            throw new OpException("{0}已经存在补缴记录（缴费月份重叠）。", uv.getRealname());
        }

        // 判断是否已存在每月的缴费记录
        do{
            PmdMonth month = pmdMonthService.getMonth(startMonth);
            if(month!=null) {
                PmdMember pmdMember = pmdMemberService.get(month.getId(), userId);
                if(pmdMember!=null){

                    throw new OpException("{0}已经存在{1}月份的缴费记录。", uv.getRealname(),
                            DateUtils.formatDate(startMonth, DateUtils.YYYYMM));
                }
            }
            startMonth = DateUtils.getDateBeforeOrAfterMonthes(startMonth, 1);
        } while (startMonth.before(endMonth));
    }

    @RequiresPermissions("pmdFee:edit")
    @RequestMapping("/pmdFee_au")
    public String pmdFee_au(Integer id, ModelMap modelMap) {

        Boolean hasShowUser = ShiroHelper.isPermitted(RoleConstants.PERMISSION_PARTYVIEWALL) ||
                ShiroHelper.hasAnyRoles(RoleConstants.ROLE_PARTYADMIN,RoleConstants.ROLE_BRANCHADMIN);

        modelMap.put("hasShowUser",hasShowUser);
        if (id != null) {
            PmdFee pmdFee = pmdFeeMapper.selectByPrimaryKey(id);
            modelMap.put("pmdFee", pmdFee);
            modelMap.put("sysUser", CmTag.getUserById(pmdFee.getUserId()));
        }
        return "pmd/pmdFee/pmdFee_au";
    }

    @RequiresPermissions("pmdFee:pay")
    @RequestMapping("/pmdFee_confirm")
    public String pmdFee_confirm(int id, @RequestParam(required = false, defaultValue = "1")Boolean isSelfPay,
                                        ModelMap modelMap) {

        PmdFee pmdFee = pmdFeeMapper.selectByPrimaryKey(id);
        modelMap.put("pmdFee", pmdFee);

        return "pmd/pmdFee/pmdFee_confirm";
    }

    @RequiresPermissions("pmdFee:pay")
    @RequestMapping(value = "/pmdFee_confirm", method = RequestMethod.POST)
    @ResponseBody
    public Map do_pmdFee_confirm(int id,
                             @RequestParam(required = false, defaultValue = "0")Boolean isMobile,
                             HttpServletRequest request){

        PmdFee pmdFee = pmdFeeMapper.selectByPrimaryKey(id);

        checkDuplicate(id, pmdFee.getUserId(), pmdFee.getStartMonth(), pmdFee.getEndMonth());

        boolean isSelfPay = (pmdFee.getUserId().intValue()==ShiroHelper.getCurrentUserId());

        PmdOrder order = pmdOrderService.feeConfirm(id, isSelfPay, isMobile);
        logger.info(addLog(LogConstants.LOG_PMD, "支付已确认，跳转至支付页面...%s",
                JSONUtils.toString(order, false)));

        Gson gson = new Gson();
        Map<String, String> params =  gson.fromJson(order.getParams(), Map.class);
        params.put("sign", order.getSign());

        Map<String, Object> resultMap = success(FormUtils.SUCCESS);
        resultMap.put("order", params); // 订单所有的请求参数 + 签名值

        resultMap.put("formMap", order.getFormMap()); // 收银台参数

        if(CmTag.getBoolProperty("payTest")) {
            // for test
            resultMap.put("ret", Pay.getInstance().testCallbackParams(order.getSn(), order.getParams()));
        }

        return resultMap;
    }

    @RequiresPermissions("pmdFee:del")
    @RequestMapping(value = "/pmdFee_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map pmdFee_batchDel(HttpServletRequest request, Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            pmdFeeService.batchDel(ids);
            logger.info(log( LogConstants.LOG_PMD, "批量删除党员缴纳党费：{0}", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    public void pmdFee_export(PmdFeeExample example, HttpServletResponse response) {

        List<PmdFee> records = pmdFeeMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"缴费起始月份|100", "缴费截止月份|100", "姓名|100","所在党组织|250","所在党支部|200","缴费方式|100","缴费金额|100","缴费类型|100","缴费原因|100","状态|100"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            PmdFee record = records.get(i);
            String[] values = {
                            DateUtils.formatDate(record.getStartMonth(), DateUtils.YYYYMM),
                            DateUtils.formatDate(record.getEndMonth(), DateUtils.YYYYMM),
                            record.getUser().getRealname(),
                            record.getPartyId() == null ? "" : partyService.findAll().get(record.getPartyId()).getName(),
                            record.getBranchId() == null ? "" : branchService.findAll().get(record.getBranchId()).getName(),
                            record.getIsOnlinePay()?"线上缴费":"现金缴费",
                            record.getAmt()+"",
                            record.getType()==null?"":CmTag.getMetaType(record.getType()).getName(),
                            record.getReason(),
                            record.getHasPay()?"已缴费":"未缴费"
            };
            valuesList.add(values);
        }
        String fileName = String.format("党员补缴党费(%s)", DateUtils.formatDate(new Date(), "yyyyMMdd"));
        ExportHelper.export(titles, valuesList, fileName, response);
    }
}
