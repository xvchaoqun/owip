package controller.pmd;

import domain.pmd.PmdNorm;
import domain.pmd.PmdNormExample;
import domain.pmd.PmdNormExample.Criteria;
import mixin.MixinUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import sys.constants.LogConstants;
import sys.constants.PmdConstants;
import sys.tool.paging.CommonList;
import sys.utils.FormUtils;
import sys.utils.JSONUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/pmd")
public class PmdNormController extends PmdBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("pmdNorm:list")
    @RequestMapping("/pmdNorm")
    public String pmdNorm(byte type, ModelMap modelMap) {

        modelMap.put("type", type);
        return "pmd/pmdNorm/pmdNorm_page";
    }

    @RequiresPermissions("pmdNorm:list")
    @RequestMapping("/pmdNorm_data")
    public void pmdNorm_data(HttpServletResponse response,
                             byte type,
                             String name,
                             Integer pageSize, Integer pageNo) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        PmdNormExample example = new PmdNormExample();
        Criteria criteria = example.createCriteria()
                .andTypeEqualTo(type).andStatusNotEqualTo(PmdConstants.PMD_NORM_STATUS_DELETE);
        example.setOrderByClause("sort_order asc");

        if (name != null) {
            criteria.andNameLike("%"+name+"%");
        }

        long count = pmdNormMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<PmdNorm> records = pmdNormMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(pmdNorm.class, pmdNormMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("pmdNorm:edit")
    @RequestMapping(value = "/pmdNorm_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_pmdNorm_au(PmdNorm record, HttpServletRequest request) {

        Integer id = record.getId();

        if (id == null) {
            record.setStatus(PmdConstants.PMD_NORM_STATUS_INIT);
            pmdNormService.insertSelective(record);
            logger.info(addLog(LogConstants.LOG_PMD, "添加收费标准：%s", record.getId()));
        } else {
            pmdNormService.updateByPrimaryKeySelective(record);
            logger.info(addLog(LogConstants.LOG_PMD, "更新收费标准：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("pmdNorm:edit")
    @RequestMapping(value = "/pmdNorm_use", method = RequestMethod.POST)
    @ResponseBody
    public Map do_pmdNorm_use(int id, HttpServletRequest request) {

        pmdNormService.use(id);
        logger.info(addLog(LogConstants.LOG_PMD, "启用收费标准：%s", id));

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("pmdNorm:edit")
    @RequestMapping(value = "/pmdNorm_abolish", method = RequestMethod.POST)
    @ResponseBody
    public Map do_pmdNorm_abolish(int id, HttpServletRequest request) {

        pmdNormService.abolish(id);
        logger.info(addLog(LogConstants.LOG_PMD, "作废收费标准：%s", id));

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("pmdNorm:edit")
    @RequestMapping("/pmdNorm_au")
    public String pmdNorm_au(Integer id, Byte type, ModelMap modelMap) {

        if (id != null) {
            PmdNorm pmdNorm = pmdNormMapper.selectByPrimaryKey(id);
            if(pmdNorm!=null)
                type = pmdNorm.getType();
            modelMap.put("pmdNorm", pmdNorm);
        }

        modelMap.put("type", type);

        return "pmd/pmdNorm/pmdNorm_au";
    }

    @RequiresPermissions("pmdNorm:changeOrder")
    @RequestMapping(value = "/pmdNorm_changeOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map do_pmdNorm_changeOrder(Integer id, Integer addNum, HttpServletRequest request) {

        pmdNormService.changeOrder(id, addNum);
        logger.info(addLog(LogConstants.LOG_PMD, "收费标准调序：%s,%s", id, addNum));
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("pmdNorm:del")
    @RequestMapping(value = "/pmdNorm_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length > 0) {
            pmdNormService.batchDel(ids);
            logger.info(addLog(LogConstants.LOG_PMD, "批量删除收费标准：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }
}
