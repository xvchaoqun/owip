package controller.pmd;

import domain.party.Branch;
import domain.party.Party;
import domain.pmd.*;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import persistence.pmd.PmdPayViewMapper;
import shiro.ShiroHelper;
import sys.tags.CmTag;
import sys.utils.DateUtils;
import sys.utils.ExportHelper;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/pmd")
public class PmdOwController extends PmdBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("pmdOw:admin")
    @RequestMapping("/pmdOw")
    public String pmdOw(@RequestParam(required = false, defaultValue = "1") byte cls, ModelMap modelMap) {

        modelMap.put("cls", cls);
        if (cls == 2) {
            // 党费管理账簿
            return "forward:/pmd/pmdAccount";
        }

        return "forward:/pmd/pmdMonth";
    }

    @RequiresPermissions("pmdOw:admin")
    @RequestMapping("/pmdOw_export_page")
    public String pmdOw_export_page(int monthId, ModelMap modelMap) throws IOException {

        modelMap.put("pmdMonth", pmdMonthMapper.selectByPrimaryKey(monthId));

        return "pmd/pmdMonth/pmdOw_export_page";
    }

    @RequiresPermissions("pmdOw:admin")
    @RequestMapping("/pmdOw_export")
    public String pmdOw_export(int monthId, HttpServletResponse response) throws IOException {

        XSSFWorkbook wb = pmdExportService.reportOw(monthId);
        if (wb != null) {
            ExportHelper.output(wb, CmTag.getSysConfig().getSchoolName()+"党费缴纳报表.xlsx", response);
        }

        return null;
    }

    @RequiresPermissions("pmdOw:admin")
    @RequestMapping("/pmdOw_parties_export")
    public String pmdOw_parties_export(int monthId, boolean isDetail, HttpServletResponse response) throws IOException {

        XSSFWorkbook wb = pmdExportService.reportParties(monthId, isDetail);
        if (wb != null) {
            ExportHelper.output(wb, CmTag.getSysConfig().getSchoolName()+"各党委线上缴纳党费"
                    +(isDetail?"明细":"总额")+".xlsx", response);
        }

        return null;
    }

    @Autowired
    private PmdPayViewMapper pmdPayViewMapper;

    @RequestMapping("/pmdOw_pay_export")
    public void pmdOw_pay_export(int monthId, Integer pmdPartyId, Integer pmdBranchId, HttpServletResponse response) throws IOException {

        PmdMonth pmdMonth = pmdMonthMapper.selectByPrimaryKey(monthId);

        String fileName = String.format("%s%s线上交纳党费明细",
                CmTag.getSysConfig().getSchoolName(), DateUtils.formatDate(pmdMonth.getPayMonth(),"yyyy年MM月"));

        PmdPayViewExample example = new PmdPayViewExample();
        example.setOrderByClause("create_time asc");
        PmdPayViewExample.Criteria criteria = example.createCriteria().andPayMonthIdEqualTo(pmdMonth.getId());
        if(pmdBranchId!=null) {

            PmdBranch pmdBranch = pmdBranchMapper.selectByPrimaryKey(pmdBranchId);
            criteria.andChargeBranchIdEqualTo(pmdBranch.getBranchId());
            Branch branch = CmTag.getBranch(pmdBranch.getBranchId());
            if(branch!=null) {
                fileName += "(" + branch.getName() + ")";
            }
        } else if(pmdPartyId!=null) {

            PmdParty pmdParty = pmdPartyMapper.selectByPrimaryKey(pmdPartyId);
            criteria.andChargePartyIdEqualTo(pmdParty.getPartyId());
            Party party = CmTag.getParty(pmdParty.getPartyId());
            if(party!=null) {
                fileName += "(" + party.getName() + ")";
            }
        }else{
            ShiroHelper.checkPermission("pmdOw:admin");
        }
        List<PmdPayView> records = pmdPayViewMapper.selectByExample(example);
        List<String> titles = new ArrayList<>(Arrays.asList(new String[]{"订单号|150",
                "交费月份|100", "所属党组织|150|left", "所属支部|150|left", "实缴人工作证号|120","实缴人姓名|100", "交费金额|80",
                "订单生成时间|150", "成功交费通知时间|150",
                "交费人工作证号|130","交费人姓名|100","是否补缴|100"}));

        List<List<String>> valuesList = new ArrayList<>();
        for (PmdPayView record:records) {

            Party party = CmTag.getParty(record.getChargePartyId());
            Branch branch = CmTag.getBranch(record.getChargeBranchId());
            List<String> values = new ArrayList<>(Arrays.asList(new String[]{
                    StringUtils.defaultIfBlank(record.getRealOrderNo(), record.getOrderNo()),
                    DateUtils.formatDate(record.getPayMonth(), "yyyy年MM月"),
                    party!=null?party.getName():"",
                    branch!=null?branch.getName():"",
                    StringUtils.defaultIfBlank(record.getPayer(), record.getOrderCode()),
                    StringUtils.defaultIfBlank(record.getPayername(), record.getOrderRealname()),
                    record.getRealPay() + "",
                    DateUtils.formatDate(record.getCreateTime(), DateUtils.YYYY_MM_DD_HH_MM_SS),
                    DateUtils.formatDate(record.getPayTime(), DateUtils.YYYY_MM_DD_HH_MM_SS),
                    record.getCode(),
                    record.getRealname(),
                    BooleanUtils.isTrue(record.getIsDelay())?"是":"否"
            }));

            valuesList.add(values);
        }

        ExportHelper.export(titles, valuesList, fileName, response);
    }
}
