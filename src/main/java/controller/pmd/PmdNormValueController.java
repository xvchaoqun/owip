package controller.pmd;

import domain.pmd.PmdNormValue;
import domain.pmd.PmdNormValueExample;
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
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/pmd")
public class PmdNormValueController extends PmdBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("pmdNorm:list")
    @RequestMapping("/pmdNormValue")
    public String pmdNormValue(HttpServletResponse response, int normId,
                                 Integer pageSize, Integer pageNo, ModelMap modelMap)  throws IOException{

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        PmdNormValueExample example = new PmdNormValueExample();
        example.createCriteria().andNormIdEqualTo(normId);
        example.setOrderByClause("id desc");

        long count = pmdNormValueMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<PmdNormValue> pmdNormValues= pmdNormValueMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        modelMap.put("pmdNormValues", pmdNormValues);

        String searchStr = "&pageSize=" + pageSize;
        commonList.setSearchStr(searchStr);
        modelMap.put("commonList", commonList);

        modelMap.put("pmdNorm", pmdNormMapper.selectByPrimaryKey(normId));

        return "pmd/pmdNormValue/pmdNormValue_page";
    }

    @RequiresPermissions("pmdNorm:edit")
    @RequestMapping(value = "/pmdNormValue_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_pmdNormValue_au(PmdNormValue record, HttpServletRequest request) {

        record.setIsEnabled(false);
        pmdNormValueService.insertSelective(record);
        logger.info(addLog(LogConstants.LOG_PMD, "添加固定额度：%s", record.getId()));

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("pmdNorm:edit")
    @RequestMapping(value = "/pmdNormValue_use", method = RequestMethod.POST)
    @ResponseBody
    public Map do_pmdNormValue_use(int id, HttpServletRequest request) {

        pmdNormValueService.use(id);
        logger.info(addLog(LogConstants.LOG_PMD, "启用固定额度：%s", id));

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("pmdNorm:del")
    @RequestMapping(value = "/pmdNormValue_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_pmdNormValue_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            pmdNormValueService.del(id);
            logger.info(addLog(LogConstants.LOG_PMD, "删除固定额度：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }
}
