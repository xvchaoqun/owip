package controller.sc.scShift;

import controller.BaseController;
import controller.sc.ScBaseController;
import domain.sc.scShift.ScShift;
import domain.sc.scShift.ScShiftExample;
import domain.sc.scShift.ScShiftExample.Criteria;
import interceptor.OrderParam;
import interceptor.SortParam;
import mixin.MixinUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import persistence.sc.ScShift.ScShiftMapper;
import sys.constants.SystemConstants;
import sys.constants.LogConstants;
import sys.tool.paging.CommonList;
import sys.utils.DateUtils;
import sys.utils.ExportHelper;
import sys.utils.FormUtils;
import sys.utils.JSONUtils;
import sys.utils.SqlUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.Action;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/sc")
public class ScShiftController extends ScBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ScShiftMapper scShiftMapper;

    @RequiresPermissions("scShift:list")
    @RequestMapping("/scShift")
    public String scShift() {

        return "sc/scShift/scShift/scShift_page";
    }

    @RequiresPermissions("scShift:list")
    @RequestMapping("/scShift_data")
    @ResponseBody
    public void scShift_data(HttpServletResponse response,
                                    Integer userId,
                                    Integer postId,
                                    Integer assignPostId,
                                
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

        ScShiftExample example = new ScShiftExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("id desc");

        if (userId!=null) {
            criteria.andUserIdEqualTo(userId);
        }
        if (postId!=null) {
            criteria.andPostIdEqualTo(postId);
        }
        if (assignPostId!=null) {
            criteria.andAssignPostIdEqualTo(assignPostId);
        }

        if (export == 1) {
            if(ids!=null && ids.length>0)
                criteria.andIdIn(Arrays.asList(ids));
            scShift_export(example, response);
            return;
        }

        long count = scShiftMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<ScShift> records= scShiftMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(scShift.class, scShiftMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("scShift:edit")
    @RequestMapping(value = "/scShift_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_scShift_au(ScShift record, HttpServletRequest request) {

        Integer id = record.getId();

        if (id == null) {
            
            scShiftService.insertSelective(record);
            logger.info(log( LogConstants.LOG_SC_SHIFT, "添加交流轮岗：{0}", record.getId()));
        } else {

            scShiftService.updateByPrimaryKeySelective(record);
            logger.info(log( LogConstants.LOG_SC_SHIFT, "更新交流轮岗：{0}", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("scShift:edit")
    @RequestMapping("/scShift_au")
    public String scShift_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            ScShift scShift = scShiftMapper.selectByPrimaryKey(id);
            modelMap.put("scShift", scShift);
        }
        return "sc/scShift/scShift/scShift_au";
    }

    @RequiresPermissions("scShift:del")
    @RequestMapping(value = "/scShift_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_scShift_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            scShiftService.del(id);
            logger.info(log( LogConstants.LOG_SC_SHIFT, "删除交流轮岗：{0}", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("scShift:del")
    @RequestMapping(value = "/scShift_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map scShift_batchDel(HttpServletRequest request, Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            scShiftService.batchDel(ids);
            logger.info(log( LogConstants.LOG_SC_SHIFT, "批量删除交流轮岗：{0}", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }
    public void scShift_export(ScShiftExample example, HttpServletResponse response) {

        List<ScShift> records = scShiftMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"干部|100","拟调整岗位|100","拟任职岗位|100","拟任职岗位所在单位名称|100","拟任职岗位所在单位类型|100","是否正职|100","是否班子负责人|100","岗位级别|100","职务属性|100"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            ScShift record = records.get(i);
            String[] values = {
                record.getUserId()+"",
                            record.getPostId()+"",
                            record.getAssignPostId()+"",
                            record.getUnitName(),
                            record.getUnitTypeId()+"",
                            record.getIsPrincipal()+"",
                            record.getLeaderType()+"",
                            record.getAdminLevel()+"",
                            record.getPostType()+""
            };
            valuesList.add(values);
        }
        String fileName = String.format("交流轮岗(%s)", DateUtils.formatDate(new Date(), "yyyyMMdd"));
        ExportHelper.export(titles, valuesList, fileName, response);
    }

    @RequestMapping("/scShift_selects")
    @ResponseBody
    public Map scShift_selects(Integer pageSize, Integer pageNo,String searchStr) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        ScShiftExample example = new ScShiftExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("id desc");

        long count = scShiftMapper.countByExample(example);
        if((pageNo-1)*pageSize >= count){

            pageNo = Math.max(1, pageNo-1);
        }
        List<ScShift> records = scShiftMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo-1)*pageSize, pageSize));

        List options = new ArrayList<>();
        if(null != records && records.size()>0){

            for(ScShift record:records){

                Map<String, Object> option = new HashMap<>();
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
