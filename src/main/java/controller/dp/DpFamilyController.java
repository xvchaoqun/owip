package controller.dp;

import domain.dp.DpFamily;
import domain.dp.DpFamilyExample;
import domain.dp.DpFamilyExample.Criteria;
import domain.sys.SysUserView;
import mixin.MixinUtils;
import org.apache.commons.lang3.BooleanUtils;
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
import sys.tags.CmTag;
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
@RequestMapping("/dp")
public class DpFamilyController extends DpBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("dpFamily:list")
    @RequestMapping("/dpFamily")
    public String dpFamily() {

        return "dp/dpFamily/dpFamily_page";
    }

    @RequiresPermissions("dpFamily:list")
    @RequestMapping("/dpFamily_data")
    @ResponseBody
    public void dpFamily_data(HttpServletResponse response,
                                    Integer userId,
                                Byte status,
                                 @RequestParam(required = false, defaultValue = "0") int export,
                                 @RequestParam(required = false, value = "ids[]") Integer[] ids, // 导出的记录
                                 Integer pageSize, Integer pageNo)  throws IOException{

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        DpFamilyExample example = new DpFamilyExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("user_id asc,sort_order asc");

        if (userId!=null) {
            criteria.andUserIdEqualTo(userId);
        }

        if (export == 1) {
            if(ids!=null && ids.length>0)
                criteria.andIdIn(Arrays.asList(ids));
            dpFamily_export(example, response);
            return;
        }

        long count = dpFamilyMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<DpFamily> records= dpFamilyMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(dpFamily.class, dpFamilyMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("dpFamily:edit")
    @RequestMapping(value = "/dpFamily_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_dpFamily_au(DpFamily record,
                              String _birthday,
                              HttpServletRequest request) {

        Integer id = record.getId();

        if(StringUtils.isNotBlank(_birthday)){
            record.setBirthday(DateUtils.parseDate(_birthday, "yyyy-MM"));
        }
        record.setWithGod(BooleanUtils.isTrue(record.getWithGod()));

        if (id == null) {
            //record.setStatus(true);
            dpFamilyService.insertSelective(record);
            logger.info(log( LogConstants.LOG_DPPARTY, "添加家庭成员信息：{0}", record.getId()));
        } else {

            dpFamilyService.updateByPrimaryKeySelective(record);
            logger.info(log( LogConstants.LOG_DPPARTY, "更新家庭成员信息：{0}", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("dpFamily:edit")
    @RequestMapping("/dpFamily_au")
    public String dpFamily_au(Integer id, Integer userId, ModelMap modelMap) {

        SysUserView uv = CmTag.getUserById(userId);
        modelMap.put("uv", uv);
        if (id != null) {
            DpFamily dpFamily = dpFamilyMapper.selectByPrimaryKey(id);
            modelMap.put("dpFamily", dpFamily);
        }
        return "dp/dpFamily/dpFamily_au";
    }

    @RequiresPermissions("dpFamily:del")
    @RequestMapping(value = "/dpFamily_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_dpFamily_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            dpFamilyService.del(id);
            logger.info(log( LogConstants.LOG_DPPARTY, "删除家庭成员信息：{0}", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("dpFamily:del")
    @RequestMapping(value = "/dpFamily_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map dpFamily_batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            dpFamilyService.batchDel(ids);
            logger.info(log( LogConstants.LOG_DPPARTY, "批量删除家庭成员信息：{0}", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }
    @RequiresPermissions("dpFamily:changeOrder")
    @RequestMapping(value = "/dpFamily_changeOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map do_dpFamily_changeOrder(Integer id, Integer addNum, HttpServletRequest request) {

        dpFamilyService.changeOrder(id, addNum);
        logger.info(log( LogConstants.LOG_DPPARTY, "家庭成员信息调序：{0}, {1}", id, addNum));
        return success(FormUtils.SUCCESS);
    }

    public void dpFamily_export(DpFamilyExample example, HttpServletResponse response) {

        List<DpFamily> records = dpFamilyMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"所属统战人员|100","称谓|100","姓名|100","出生年月|100","政治面貌|100","工作单位及职务|100"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            DpFamily record = records.get(i);
            String[] values = {
                record.getUserId()+"",
                            record.getTitle()+"",
                            record.getRealname(),
                            DateUtils.formatDate(record.getBirthday(), DateUtils.YYYY_MM_DD),
                            record.getPoliticalStatus()+"",
                            record.getUnit()
            };
            valuesList.add(values);
        }
        String fileName = String.format("家庭成员信息(%s)", DateUtils.formatDate(new Date(), "yyyyMMdd"));
        ExportHelper.export(titles, valuesList, fileName, response);
    }

    @RequestMapping("/dpFamily_selects")
    @ResponseBody
    public Map dpFamily_selects(Integer pageSize, Integer pageNo,String searchStr) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        DpFamilyExample example = new DpFamilyExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("sort_order desc");

        if(StringUtils.isNotBlank(searchStr)){
            //criteria.andNameLike(SqlUtils.like(searchStr));
        }

        long count = dpFamilyMapper.countByExample(example);
        if((pageNo-1)*pageSize >= count){

            pageNo = Math.max(1, pageNo-1);
        }
        List<DpFamily> records = dpFamilyMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo-1)*pageSize, pageSize));

        List options = new ArrayList<>();
        if(null != records && records.size()>0){

            for(DpFamily record:records){

                Map<String, Object> option = new HashMap<>();
                option.put("text", record.getRealname());
                option.put("id", record.getId() + "");

                options.add(option);
            }
        }

        Map resultMap = success();
        resultMap.put("totalCount", count);
        resultMap.put("options", options);
        return resultMap;
    }
}
