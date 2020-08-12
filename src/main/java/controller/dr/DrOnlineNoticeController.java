package controller.dr;

import domain.dr.DrOnlineNotice;
import domain.dr.DrOnlineNoticeExample;
import domain.dr.DrOnlineNoticeExample.Criteria;
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
@RequestMapping("/dr")
public class DrOnlineNoticeController extends DrBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("drOnlineNotice:list")
    @RequestMapping("/drOnlineNotice")
    public String drOnlineNotice(ModelMap modelMap,
                                 @RequestParam(required = false, defaultValue = "1") Byte cls) {

        modelMap.put("cls", cls);

        return "dr/drOnline/drOnlineNotice/drOnlineNotice_page";
    }

    @RequiresPermissions("drOnlineNotice:list")
    @RequestMapping("/drOnlineNotice_data")
    @ResponseBody
    public void drOnlineNotice_data(HttpServletResponse response,
                                    String name,
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

        DrOnlineNoticeExample example = new DrOnlineNoticeExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("id desc");

        if (StringUtils.isNotBlank(name)){
            criteria.andNameLike(SqlUtils.like(name));
        }

        if (export == 1) {
            if(ids!=null && ids.length>0)
                criteria.andIdIn(Arrays.asList(ids));
            drOnlineNotice_export(example, response);
            return;
        }

        long count = drOnlineNoticeMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<DrOnlineNotice> records= drOnlineNoticeMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(drOnlineNotice.class, drOnlineNoticeMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("drOnlineNotice:edit")
    @RequestMapping(value = "/drOnlineNotice_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_drOnlineNotice_au(DrOnlineNotice record, HttpServletRequest request) {

        Integer id = record.getId();
        record.setContent(record.getContent());

        if (id == null) {
            
            drOnlineNoticeService.insertSelective(record);
            logger.info(log( LogConstants.LOG_DR, "添加线上民主推荐说明模板：{0}", record.getName()));
        } else {

            drOnlineNoticeService.updateByPrimaryKeySelective(record);
            logger.info(log( LogConstants.LOG_DR, "更新线上民主推荐说明模板：{0}", record.getName()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("drOnlineNotice:edit")
    @RequestMapping("/drOnlineNotice_au")
    public String drOnlineNotice_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            DrOnlineNotice drOnlineNotice = drOnlineNoticeMapper.selectByPrimaryKey(id);
            modelMap.put("drOnlineNotice", drOnlineNotice);
        }
        return "dr/drOnline/drOnlineNotice/drOnlineNotice_au";
    }

    @RequiresPermissions("drOnlineNotice:del")
    @RequestMapping(value = "/drOnlineNotice_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_drOnlineNotice_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            drOnlineNoticeService.del(id);
            logger.info(log( LogConstants.LOG_DR, "删除线上民主推荐说明模板：{0}", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("drOnlineNotice:del")
    @RequestMapping(value = "/drOnlineNotice_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map drOnlineNotice_batchDel(HttpServletRequest request, Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            drOnlineNoticeService.batchDel(ids);
            logger.info(log( LogConstants.LOG_DR, "批量删除线上民主推荐说明模板：{0}", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }
    public void drOnlineNotice_export(DrOnlineNoticeExample example, HttpServletResponse response) {

        List<DrOnlineNotice> records = drOnlineNoticeMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"模板名称|100","模板内容|100"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            DrOnlineNotice record = records.get(i);
            String[] values = {
                record.getName(),
                            record.getContent()
            };
            valuesList.add(values);
        }
        String fileName = String.format("线上民主推荐说明模板(%s)", DateUtils.formatDate(new Date(), "yyyyMMdd"));
        ExportHelper.export(titles, valuesList, fileName, response);
    }

    @RequestMapping("/drOnlineNotice_selects")
    @ResponseBody
    public Map drOnlineNotice_selects(Integer pageSize, Integer pageNo,String searchStr) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        DrOnlineNoticeExample example = new DrOnlineNoticeExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("id desc");

        if(StringUtils.isNotBlank(searchStr)){
            criteria.andNameLike(SqlUtils.like(searchStr));
        }

        long count = drOnlineNoticeMapper.countByExample(example);
        if((pageNo-1)*pageSize >= count){

            pageNo = Math.max(1, pageNo-1);
        }
        List<DrOnlineNotice> records = drOnlineNoticeMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo-1)*pageSize, pageSize));

        List options = new ArrayList<>();
        if(null != records && records.size()>0){

            for(DrOnlineNotice record:records){

                Map<String, Object> option = new HashMap<>();
                option.put("text", record.getName());
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
