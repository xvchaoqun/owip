package controller.ps;

import domain.ps.PsInfo;
import domain.ps.PsInfoExample;
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
import sys.tool.paging.CommonList;
import sys.utils.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("/ps")
public class PsInfoController extends PsBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    // 基本信息
    @RequiresPermissions("psInfo:view")
    @RequestMapping("/psInfo_base")
    public String psInfo_base(Integer id, ModelMap modelMap) {

        PsInfo psInfo = psInfoMapper.selectByPrimaryKey(id);
        modelMap.put("psInfo", psInfo);

        return "ps/psInfo/psInfo_base";
    }

    @RequiresPermissions("psInfo:view")
    @RequestMapping("/psInfo_view")
    public String psInfo_view(HttpServletResponse response, int id, ModelMap modelMap) {

        PsInfo psInfo = psInfoMapper.selectByPrimaryKey(id);
        modelMap.put("psInfo", psInfo);

        return "ps/psInfo/psInfo_view";
    }

    @RequiresPermissions("psInfo:list")
    @RequestMapping("/psInfo")
    public String psInfo( @RequestParam(required = false, defaultValue = "0") boolean isHistory, ModelMap modelMap) {

        modelMap.put("isHistory", isHistory);

        return "ps/psInfo/psInfo_page";
    }

    @RequiresPermissions("psInfo:list")
    @RequestMapping("/psInfo_data")
    public void psInfo_data(HttpServletResponse response,
                                 String name,
                                 @RequestParam(required = false, defaultValue = "0") boolean isHistory,
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

        PsInfoExample example = new PsInfoExample();
        PsInfoExample.Criteria criteria = example.createCriteria().andIsHistoryEqualTo(isHistory);
        example.setOrderByClause("sort_order desc");

        if (StringUtils.isNotBlank(name)) {
            criteria.andNameLike(SqlUtils.like(name));
        }

        if (export == 1) {
            if(ids!=null && ids.length>0)
                criteria.andIdIn(Arrays.asList(ids));
            psInfo_export(example, response);
            return;
        }

        long count = psInfoMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<PsInfo> records= psInfoMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(psInfo.class, psInfoMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("psInfo:edit")
    @RequestMapping(value = "/psInfo_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_psInfo_au(PsInfo record, HttpServletRequest request) {

        Integer id = record.getId();

        if (id == null) {
            record.setIsHistory(false);
            psInfoService.insertSelective(record);
            logger.info(addLog(LogConstants.LOG_PS, "添加二级党校：%s", record.getId()));
        } else {

            psInfoService.updateByPrimaryKeySelective(record);
            logger.info(addLog(LogConstants.LOG_PS, "更新二级党校：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("psInfo:edit")
    @RequestMapping("/psInfo_au")
    public String psInfo_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            PsInfo psInfo = psInfoMapper.selectByPrimaryKey(id);
            modelMap.put("psInfo", psInfo);
        }
        return "ps/psInfo/psInfo_au";
    }

    @RequiresPermissions("psInfo:history")
    @RequestMapping(value = "/psInfo_history", method = RequestMethod.POST)
    @ResponseBody
    public Map psInfo_history(HttpServletRequest request,
                                   boolean isHistory,
                                   @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            psInfoService.history(ids, isHistory);
            logger.info(addLog(LogConstants.LOG_PS, "批量转移二级党校：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }
    @RequiresPermissions("psInfo:del")
    @RequestMapping(value = "/psInfo_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map psInfo_batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            psInfoService.batchDel(ids);
            logger.info(addLog(LogConstants.LOG_PS, "批量删除二级党校：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("psInfo:changeOrder")
    @RequestMapping(value = "/psInfo_changeOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map do_psInfo_changeOrder(Integer id, Integer addNum, HttpServletRequest request) {

        psInfoService.changeOrder(id, addNum);
        logger.info(addLog(LogConstants.LOG_PS, "二级党校调序：%s,%s", id, addNum));
        return success(FormUtils.SUCCESS);
    }

    public void psInfo_export(PsInfoExample example, HttpServletResponse response) {

        List<PsInfo> records = psInfoMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"二级党校名称|100","设立日期|100"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            PsInfo record = records.get(i);
            String[] values = {
                record.getName(),
                            DateUtils.formatDate(record.getFoundDate(), DateUtils.YYYY_MM_DD)
            };
            valuesList.add(values);
        }
        String fileName = "二级党校_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
        ExportHelper.export(titles, valuesList, fileName, response);
    }

    @RequestMapping("/psInfo_selects")
    @ResponseBody
    public Map psInfo_selects(Integer pageSize,
                                   Boolean isHistory,
                                   Integer pageNo,String searchStr) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        PsInfoExample example = new PsInfoExample();
        PsInfoExample.Criteria criteria = example.createCriteria().andIsHistoryEqualTo(false);
        example.setOrderByClause("is_history asc, sort_order desc");

        if(isHistory!=null){
            criteria.andIsHistoryEqualTo(isHistory);
        }

        if(StringUtils.isNotBlank(searchStr)){
            criteria.andNameLike("%"+searchStr.trim()+"%");
        }

        long count = psInfoMapper.countByExample(example);
        if((pageNo-1)*pageSize >= count){

            pageNo = Math.max(1, pageNo-1);
        }
        List<PsInfo> psInfos = psInfoMapper.selectByExampleWithRowbounds(example,
                new RowBounds((pageNo-1)*pageSize, pageSize));

        List options = new ArrayList<>();
        if(null != psInfos && psInfos.size()>0){

            for(PsInfo psInfo:psInfos){

                Map<String, Object> option = new HashMap<>();
                option.put("text", psInfo.getName());
                option.put("id", psInfo.getId() + "");
                option.put("del", psInfo.getIsHistory());
                options.add(option);
            }
        }

        Map resultMap = success();
        resultMap.put("totalCount", count);
        resultMap.put("options", options);
        return resultMap;
    }
}
