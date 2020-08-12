package controller.crs;

import domain.cadre.CadreView;
import domain.crs.CrsExpert;
import domain.crs.CrsExpertExample;
import domain.crs.CrsExpertView;
import domain.crs.CrsExpertViewExample;
import domain.sys.SysUserView;
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
import persistence.crs.common.ICrsExpert;
import sys.constants.CisConstants;
import sys.constants.LogConstants;
import sys.constants.SystemConstants;
import sys.spring.DateRange;
import sys.spring.RequestDateRange;
import sys.tool.paging.CommonList;
import sys.tool.tree.TreeNode;
import sys.utils.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Controller
public class CrsExpertController extends CrsBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    // 职务属性-干部列表（现任干部、校领导）
    @RequiresPermissions("crsExpert:list")
    @RequestMapping("/crsExpert/selectCadres_tree")
    @ResponseBody
    public Map selectCadres_tree() throws IOException {

        CrsExpertExample example = new CrsExpertExample();
        example.createCriteria();
        List<CrsExpert> crsExperts = crsExpertMapper.selectByExample(example);
        Set<Integer> disabledIdSet = new HashSet<>();
        for (CrsExpert crsExpert : crsExperts) {
            disabledIdSet.add(crsExpert.getUserId());
        }

        TreeNode tree = crsExpertService.getTree(new LinkedHashSet<CadreView>(cadreService.findAll().values()),
                disabledIdSet, true, false);

        Map<String, Object> resultMap = success();
        resultMap.put("tree", tree);
        return resultMap;
    }

    // 批量添加专家
    @RequiresPermissions("crsExpert:edit")
    @RequestMapping("/crsExpert/selectCadres")
    public String selectCadres() throws IOException {

        return "crs/crsExpert/selectCadres";
    }

    // 批量添加专家
    @RequiresPermissions("crsExpert:edit")
    @RequestMapping(value = "/crsExpert/selectCadres", method = RequestMethod.POST)
    @ResponseBody
    public Map do_select_posts(Integer[] userIds) {

        crsExpertService.batchAdd(userIds);
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("crsExpert:list")
    @RequestMapping("/crsExpert")
    public String crsExpert(HttpServletResponse response,
                            Integer userId,
                            @RequestParam(required = false, defaultValue =
                                    CisConstants.CIS_INSPECTOR_STATUS_NOW + "") Byte status,
                            ModelMap modelMap) {

        modelMap.put("status", status);
        if (userId != null) modelMap.put("sysUser", sysUserService.findById(userId));
        return "crs/crsExpert/crsExpert_page";
    }

    @RequiresPermissions("crsExpert:list")
    @RequestMapping("/crsExpert_data")
    public void crsExpert_data(HttpServletResponse response,
                               Integer userId,
                               @RequestParam(required = false, defaultValue =
                                       CisConstants.CIS_INSPECTOR_STATUS_NOW + "") Byte status,
                               @RequestDateRange DateRange meetingTime,
                               Byte orderType,
                               @RequestParam(required = false, defaultValue = "0") int export,
                               Integer[] ids, // 导出的记录
                               Integer pageSize, Integer pageNo) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);


        /*if (export == 1) {
            if (ids != null && ids.length > 0)
                criteria.andIdIn(Arrays.asList(ids));
            crsExpert_export(example, response);
            return;
        }*/

        int count = iCrsMapper.countExpertList(userId, status, meetingTime.getStart(), meetingTime.getEnd());
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }

        List<ICrsExpert> records = iCrsMapper.selectExpertList(userId, status, meetingTime.getStart(),
                meetingTime.getEnd(), orderType, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(crsExpert.class, crsExpertMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("crsExpert:edit")
    @RequestMapping(value = "/crsExpert_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_crsExpert_au(CrsExpert record, HttpServletRequest request) {

        Integer id = record.getId();

        if (id == null) {
            record.setStatus(CisConstants.CIS_INSPECTOR_STATUS_NOW);
            crsExpertService.insertSelective(record);
            logger.info(addLog(LogConstants.LOG_CRS, "添加干部考察组成员：%s", record.getId()));
        } else {

            crsExpertService.updateByPrimaryKeySelective(record);
            logger.info(addLog(LogConstants.LOG_CRS, "更新干部考察组成员：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("crsExpert:edit")
    @RequestMapping("/crsExpert_au")
    public String crsExpert_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            CrsExpert crsExpert = crsExpertMapper.selectByPrimaryKey(id);
            modelMap.put("crsExpert", crsExpert);
        }
        return "crs/crsExpert/crsExpert_au";
    }

    @RequiresPermissions("crsExpert:edit")
    @RequestMapping(value = "/crsExpert_abolish", method = RequestMethod.POST)
    @ResponseBody
    public Map do_crsExpert_abolish(HttpServletRequest request, Integer[] ids) {

        if (null != ids && ids.length > 0) {

            crsExpertService.abolish(ids);
            logger.info(addLog(LogConstants.LOG_CRS, "撤销干部考察组成员：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("crsExpert:edit")
    @RequestMapping(value = "/crsExpert_reuse", method = RequestMethod.POST)
    @ResponseBody
    public Map do_crsExpert_reuse(HttpServletRequest request, Integer[] ids) {

        if (null != ids && ids.length > 0) {

            crsExpertService.reuse(ids);
            logger.info(addLog(LogConstants.LOG_CRS, "重新任用干部考察组成员：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    // 逻辑删除
    @RequiresPermissions("crsExpert:del")
    @RequestMapping(value = "/crsExpert_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length > 0) {

            crsExpertService.batchDel(ids);
            logger.info(addLog(LogConstants.LOG_CRS, "批量删除干部考察组成员：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    public void crsExpert_export(CrsExpertViewExample example, HttpServletResponse response) {

        List<CrsExpertView> records = crsExpertViewMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"考察组成员", "排序"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            CrsExpertView record = records.get(i);
            String[] values = {
                    record.getUserId() + "",
                    record.getSortOrder() + ""
            };
            valuesList.add(values);
        }
        String fileName = "干部考察组成员_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
        ExportHelper.export(titles, valuesList, fileName, response);
    }

    // <crsExpert.id, realname>
    @RequestMapping("/crsExpert_selects")
    @ResponseBody
    public Map crsExpert_selects(Byte status, Integer pageSize, Integer pageNo, String searchStr) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        CrsExpertViewExample example = new CrsExpertViewExample();
        example.setOrderByClause("cadre_status asc, cadre_sort_order desc");

        if (StringUtils.isNotBlank(searchStr)) {
            CrsExpertViewExample.Criteria criteria = example.or().andUsernameLike(SqlUtils.like(searchStr));
            CrsExpertViewExample.Criteria criteria1 = example.or().andCodeLike(SqlUtils.like(searchStr));
            CrsExpertViewExample.Criteria criteria2 = example.or().andRealnameLike(SqlUtils.like(searchStr));
            if (status != null) {
                criteria.andStatusEqualTo(status);
                criteria1.andStatusEqualTo(status);
                criteria2.andStatusEqualTo(status);
            }
        } else if (status != null) {
            example.createCriteria().andStatusEqualTo(status);
        }

        long count = crsExpertViewMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<CrsExpertView> crsExperts = crsExpertViewMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));

        List<Map<String, String>> options = new ArrayList<Map<String, String>>();
        if (null != crsExperts && crsExperts.size() > 0) {

            for (CrsExpertView crsExpert : crsExperts) {

                Map<String, String> option = new HashMap<>();
                option.put("id", crsExpert.getUserId() + "");
                option.put("text", crsExpert.getRealname());
                SysUserView uv = sysUserService.findById(crsExpert.getUserId());
                if (StringUtils.isNotBlank(uv.getCode())) {
                    option.put("code", uv.getCode());
                    if (uv.getType() == SystemConstants.USER_TYPE_JZG) {
                        option.put("unit", uv.getUnit());
                    }
                }

                options.add(option);
            }
        }

        Map resultMap = success();
        resultMap.put("totalCount", count);
        resultMap.put("options", options);
        return resultMap;
    }
}
