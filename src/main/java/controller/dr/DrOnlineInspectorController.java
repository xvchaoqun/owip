package controller.dr;

import domain.dr.DrOnline;
import domain.dr.DrOnlineInspector;
import domain.dr.DrOnlineInspectorExample;
import domain.dr.DrOnlineInspectorExample.Criteria;
import freemarker.template.TemplateException;
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
import sys.constants.DrConstants;
import sys.constants.LogConstants;
import sys.tool.paging.CommonList;
import sys.utils.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("/dr")
public class DrOnlineInspectorController extends DrBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("drOnlineInspector:list")
    @RequestMapping("/drOnlineInspector")
    public String drOnlineInspector(Integer onlineId,
                                    Integer logId,
                                    ModelMap modelMap) {

        modelMap.put("onlineId", onlineId);
        modelMap.put("logId", logId);

        return "dr/drOnlineInspector/drOnlineInspector_page";
    }

    @RequiresPermissions("drOnlineInspector:list")
    @RequestMapping("/drOnlineInspector_data")
    @ResponseBody
    public void drOnlineInspector_data(HttpServletResponse response,
                                    Integer id,
                                    Integer logId,
                                    Byte pubStatus,
                                    Byte status,
                                    String username,
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

        DrOnlineInspectorExample example = new DrOnlineInspectorExample();
        Criteria criteria = example.createCriteria().andLogIdEqualTo(logId);
        example.setOrderByClause("id desc");

        if (id!=null) {
            criteria.andIdEqualTo(id);
        }
        if (pubStatus!=null) {
            criteria.andPubStatusEqualTo(pubStatus);
        }
        if (status!=null) {
            criteria.andStatusEqualTo(status);
        }
        if (StringUtils.isNotBlank(username)) {
            criteria.andUsernameLike(SqlUtils.like(username));
        }

        if (export == 1) {
            if(ids!=null && ids.length>0)
                criteria.andIdIn(Arrays.asList(ids));
            drOnlineInspector_export(example, response);
            return;
        }

        long count = drOnlineInspectorMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<DrOnlineInspector> records= drOnlineInspectorMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(drOnlineInspector.class, drOnlineInspectorMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("drOnlineInspector:edit")
    @RequestMapping(value = "/drOnlineInspector_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_drOnlineInspector_au(DrOnlineInspector record,
                                       Byte passwdChangeType,
                                       HttpServletRequest request) {

        Integer id = record.getId();

        if (id != null) {
            record.setPasswdChangeType(passwdChangeType);
            drOnlineInspectorService.updateByPrimaryKeySelective(record);
            logger.info(log( LogConstants.LOG_DR, "修改参评人密码：{0}", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("drOnlineInspector:edit")
    @RequestMapping("/drOnlineInspector_au")
    public String drOnlineInspector_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            DrOnlineInspector inspector = drOnlineInspectorMapper.selectByPrimaryKey(id);
            modelMap.put("inspector", inspector);
        }
        return "dr/drOnlineInspector/drOnlineInspector_au";
    }

    @RequiresPermissions("drOnlineInspector:del")
    @RequestMapping(value = "/drOnlineInspector_cancel", method = RequestMethod.POST)
    @ResponseBody
    public Map drOnlineInspector_cancel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            drOnlineInspectorService.cancel(ids);
            logger.info(log( LogConstants.LOG_DR, "批量作废参评人：{0}", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("drOnlineInspector:del")
    @RequestMapping(value = "/drOnlineInspector_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_drOnlineInspector_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            drOnlineInspectorService.del(id);
            logger.info(log( LogConstants.LOG_DR, "删除参评人：{0}", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("drOnlineInspector:del")
    @RequestMapping(value = "/drOnlineInspector_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map drOnlineInspector_batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            drOnlineInspectorService.batchDel(ids);
            logger.info(log( LogConstants.LOG_DR, "批量删除参评人：{0}", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    public void drOnlineInspector_export(DrOnlineInspectorExample example, HttpServletResponse response) {

        List<DrOnlineInspector> records = drOnlineInspectorMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"所属导出记录|100","推荐批次|100","登陆账号|100","登陆密码|100","更改密码方式|100","推荐人身份类型|100","所属单位|100","临时数据|100","状态（0可用、1作废、2完成 、3暂存）|100","是否手机端完成测评|100","分发状态（0未分发 1已分发）|100","测评反馈意见|100","提交时间|100","创建时间|100"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            DrOnlineInspector record = records.get(i);
            String[] values = {
                record.getLogId()+"",
                            record.getOnlineId()+"",
                            record.getUsername(),
                            record.getPasswd(),
                            record.getPasswdChangeType()+"",
                            record.getTypeId()+"",
                            record.getUnitId()+"",
                            record.getTempdata(),
                            record.getStatus()+"",
                            record.getIsMobile()+"",
                            record.getPubStatus()+"",
                            record.getRemark(),
                            DateUtils.formatDate(record.getSubmitTime(), DateUtils.YYYY_MM_DD_HH_MM_SS),
                            DateUtils.formatDate(record.getCreateTime(), DateUtils.YYYY_MM_DD_HH_MM_SS)
            };
            valuesList.add(values);
        }
        String fileName = String.format("参评人(%s)", DateUtils.formatDate(new Date(), "yyyyMMdd"));
        ExportHelper.export(titles, valuesList, fileName, response);
    }

    @RequiresPermissions("drOnlineInspector:edit")
    @RequestMapping("/drOnlineInspector_print")
    public String drOnlineInspector_print(Integer onlineId,
                                          Integer logId,
                                          ModelMap modelMap,
                                          HttpServletResponse response,
                                          HttpServletRequest request) throws IOException, TemplateException {

        String url = DrConstants.DR_ONLINE_URL;
        modelMap.put("url", url);

        DrOnlineInspectorExample example = new DrOnlineInspectorExample();
        DrOnlineInspectorExample.Criteria criteria = example.createCriteria().andStatusNotEqualTo(DrConstants.INSPECTOR_STATUS_ABOLISH).andPubStatusEqualTo(DrConstants.INSPECTOR_PUB_STATUS_RELEASE);

        if (logId != null){
            criteria.andLogIdEqualTo(logId);
        }
        if (onlineId != null){
            DrOnline drOnline = drOnlineMapper.selectByPrimaryKey(onlineId);
            modelMap.put("drOnline", drOnline);
        }
        List<DrOnlineInspector> inspectors = drOnlineInspectorMapper.selectByExample(example);
        modelMap.put("inspectors", inspectors);

        return "dr/drOnlineInspectorLog/drOnlineInspector_print";
    }

    @RequestMapping("/drOnlineInspector_selects")
    @ResponseBody
    public Map drOnlineInspector_selects(Integer pageSize, Integer pageNo,String searchStr) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        DrOnlineInspectorExample example = new DrOnlineInspectorExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("id desc");

        /*if(StringUtils.isNotBlank(searchStr)){
            criteria.andNameLike(SqlUtils.like(searchStr));
        }
*/
        long count = drOnlineInspectorMapper.countByExample(example);
        if((pageNo-1)*pageSize >= count){

            pageNo = Math.max(1, pageNo-1);
        }
        List<DrOnlineInspector> records = drOnlineInspectorMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo-1)*pageSize, pageSize));

        List options = new ArrayList<>();
        if(null != records && records.size()>0){

            for(DrOnlineInspector record:records){

                Map<String, Object> option = new HashMap<>();
                //option.put("text", record.getName());
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
