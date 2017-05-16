package controller.ces;

import controller.BaseController;
import domain.ces.CesTempPost;
import domain.ces.CesTempPostExample;
import domain.ces.CesTempPostExample.Criteria;
import interceptor.OrderParam;
import interceptor.SortParam;
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
import sys.constants.SystemConstants;
import sys.tool.paging.CommonList;
import sys.utils.DateUtils;
import sys.utils.ExportHelper;
import sys.utils.FormUtils;
import sys.utils.JSONUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Controller
public class CesTempPostController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("cesTempPost:list")
    @RequestMapping("/cesTempPost")
    public String cesTempPost() {

        return "index";
    }

    @RequiresPermissions("cesTempPost:list")
    @RequestMapping("/cesTempPost_page")
    public String cesTempPost_page(HttpServletResponse response,
                                   @SortParam(required = false, defaultValue = "sort_order", tableName = "ces_temp_post") String sort,
                                   @OrderParam(required = false, defaultValue = "desc") String order,
                                   Integer cadreId,
                                   Byte type,
                                   Boolean isFinished,
                                   Boolean isDeleted,
                                   @RequestParam(required = false, defaultValue = "0") int export,
                                   @RequestParam(required = false, value = "ids[]") Integer[] ids, // 导出的记录
                                   Integer pageSize, Integer pageNo, ModelMap modelMap) {

        return "ces/cesTempPost/cesTempPost_page";
    }

    @RequiresPermissions("cesTempPost:list")
    @RequestMapping("/cesTempPost_data")
    public void cesTempPost_data(HttpServletResponse response,
                                 Integer cadreId,
                                 Byte type,
                                 Boolean isFinished,
                                 Boolean isDeleted,
                                 @RequestParam(required = false, defaultValue = "0") int export,
                                 @RequestParam(required = false, value = "ids[]") Integer[] ids, // 导出的记录
                                 Integer pageSize, Integer pageNo) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        CesTempPostExample example = new CesTempPostExample();
        Criteria criteria = example.createCriteria();
        //example.setOrderByClause(String.format("%s %s", sort, order));

        if (cadreId != null) {
            criteria.andCadreIdEqualTo(cadreId);
        }
        if (type != null) {
            criteria.andTypeEqualTo(type);
        }
        if (isFinished != null) {
            criteria.andIsFinishedEqualTo(isFinished);
        }
        if (isDeleted != null) {
            criteria.andIsDeletedEqualTo(isDeleted);
        }

        if (export == 1) {
            if (ids != null && ids.length > 0)
                criteria.andIdIn(Arrays.asList(ids));
            cesTempPost_export(example, response);
            return;
        }

        long count = cesTempPostMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<CesTempPost> records = cesTempPostMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> sourceMixins = sourceMixins();
        //sourceMixins.put(cesTempPost.class, cesTempPostMixin.class);
        JSONUtils.jsonp(resultMap, sourceMixins);
        return;
    }

    @RequiresPermissions("cesTempPost:edit")
    @RequestMapping(value = "/cesTempPost_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cesTempPost_au(CesTempPost record, HttpServletRequest request) {

        Integer id = record.getId();

        if (id == null) {
            cesTempPostService.insertSelective(record);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "添加干部挂职锻炼：%s", record.getId()));
        } else {

            cesTempPostService.updateByPrimaryKeySelective(record);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "更新干部挂职锻炼：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cesTempPost:edit")
    @RequestMapping("/cesTempPost_au")
    public String cesTempPost_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            CesTempPost cesTempPost = cesTempPostMapper.selectByPrimaryKey(id);
            modelMap.put("cesTempPost", cesTempPost);
        }
        return "ces/cesTempPost/cesTempPost_au";
    }

    @RequiresPermissions("cesTempPost:del")
    @RequestMapping(value = "/cesTempPost_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cesTempPost_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            cesTempPostService.del(id);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "删除干部挂职锻炼：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cesTempPost:del")
    @RequestMapping(value = "/cesTempPost_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length > 0) {
            cesTempPostService.batchDel(ids);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "批量删除干部挂职锻炼：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }


    public void cesTempPost_export(CesTempPostExample example, HttpServletResponse response) {

        List<CesTempPost> records = cesTempPostMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"姓名", "是否现任干部", "时任职务", "委派单位", "挂职类别", "挂职单位及所任职务", "挂职开始时间", "挂职拟结束时间"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            CesTempPost record = records.get(i);
            String[] values = {
                    record.getRealname(),
                    record.getIsPresentCadre() ? "现任干部" : "非现任干部",
                    record.getPresentPost(),
                    record.getToUnitType() + "",
                    record.getTempPostType() + "",
                    record.getTitle(),
                    DateUtils.formatDate(record.getStartDate(), DateUtils.YYYY_MM_DD),
                    DateUtils.formatDate(record.getEndDate(), DateUtils.YYYY_MM_DD)
            };
            valuesList.add(values);
        }
        String fileName = "干部挂职锻炼_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
        ExportHelper.export(titles, valuesList, fileName, response);
    }
}
