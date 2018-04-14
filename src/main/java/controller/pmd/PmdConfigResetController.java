package controller.pmd;

import domain.pmd.PmdConfigReset;
import domain.pmd.PmdConfigResetExample;
import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import sys.constants.LogConstants;
import sys.tool.paging.CommonList;
import sys.utils.FormUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/pmd")
public class PmdConfigResetController extends PmdBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("pmdConfigMember:list")
    @RequestMapping("/pmdConfigReset")
    public String pmdConfigReset(Integer pageSize, Integer pageNo, ModelMap modelMap) {

        if (null == pageSize) {
            pageSize = 5;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        PmdConfigResetExample example = new PmdConfigResetExample();
        example.setOrderByClause("create_time desc");

        long count = pmdConfigResetMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<PmdConfigReset> PmdConfigResets = pmdConfigResetMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        modelMap.put("pmdConfigResets", PmdConfigResets);

        CommonList commonList = new CommonList(count, pageNo, pageSize);

        String searchStr = "&pageSize=" + pageSize;

        commonList.setSearchStr(searchStr);
        modelMap.put("commonList", commonList);

        List<String> salaryMonthList = iPmdMapper.extSalaryMonthList();
        modelMap.put("salaryMonthList", salaryMonthList);

        return "pmd/pmdConfigReset/pmdConfigReset_page";
    }

    @RequiresPermissions("pmdConfigMember:edit")
    @RequestMapping(value = "/pmdConfigReset_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_pmdConfigReset_au(String salaryMonth, HttpServletRequest request) {

        pmdConfigResetService.reset(salaryMonth);
        logger.info(addLog(LogConstants.LOG_PMD, "党费收缴-设置党费重新计算工资月份：%s", salaryMonth));

        return success(FormUtils.SUCCESS);
    }
}
