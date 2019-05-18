package controller.cet;

import domain.cet.CetTraineeType;
import domain.cet.CetTraineeTypeExample;
import domain.cet.CetTraineeTypeExample.Criteria;
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
import sys.tool.jackson.Select2Option;
import sys.tool.paging.CommonList;
import sys.utils.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("/cet")
public class CetTraineeTypeController extends CetBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("cetTraineeType:list")
    @RequestMapping("/cetTraineeType")
    public String cetTraineeType() {

        return "cet/cetTraineeType/cetTraineeType_page";
    }

    @RequiresPermissions("cetTraineeType:list")
    @RequestMapping("/cetTraineeType_data")
    public void cetTraineeType_data(HttpServletResponse response,
                                    String name,
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

        CetTraineeTypeExample example = new CetTraineeTypeExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("sort_order asc");

        if (StringUtils.isNotBlank(name)) {
            criteria.andNameLike(SqlUtils.like(name));
        }

        if (export == 1) {
            if(ids!=null && ids.length>0)
                criteria.andIdIn(Arrays.asList(ids));
            cetTraineeType_export(example, response);
            return;
        }

        long count = cetTraineeTypeMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<CetTraineeType> records= cetTraineeTypeMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(cetTraineeType.class, cetTraineeTypeMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("cetTraineeType:edit")
    @RequestMapping(value = "/cetTraineeType_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cetTraineeType_au(CetTraineeType record, HttpServletRequest request) {

        Integer id = record.getId();

        if (id == null) {
            cetTraineeTypeService.insertSelective(record);
            logger.info(addLog(LogConstants.LOG_CET, "添加参训人员类型：%s", record.getId()));
        } else {

            cetTraineeTypeService.updateByPrimaryKeySelective(record);
            logger.info(addLog(LogConstants.LOG_CET, "更新参训人员类型：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cetTraineeType:edit")
    @RequestMapping("/cetTraineeType_au")
    public String cetTraineeType_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            CetTraineeType cetTraineeType = cetTraineeTypeMapper.selectByPrimaryKey(id);
            modelMap.put("cetTraineeType", cetTraineeType);
        }
        return "cet/cetTraineeType/cetTraineeType_au";
    }

    @RequiresPermissions("cetTraineeType:del")
    @RequestMapping(value = "/cetTraineeType_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cetTraineeType_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            cetTraineeTypeService.del(id);
            logger.info(addLog(LogConstants.LOG_CET, "删除参训人员类型：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cetTraineeType:del")
    @RequestMapping(value = "/cetTraineeType_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map cetTraineeType_batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            cetTraineeTypeService.batchDel(ids);
            logger.info(addLog(LogConstants.LOG_CET, "批量删除参训人员类型：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cetTraineeType:changeOrder")
    @RequestMapping(value = "/cetTraineeType_changeOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cetTraineeType_changeOrder(Integer id, Integer addNum, HttpServletRequest request) {

        cetTraineeTypeService.changeOrder(id, addNum);
        logger.info(addLog(LogConstants.LOG_CET, "参训人员类型调序：%s,%s", id, addNum));
        return success(FormUtils.SUCCESS);
    }

    public void cetTraineeType_export(CetTraineeTypeExample example, HttpServletResponse response) {

        List<CetTraineeType> records = cetTraineeTypeMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"参训人员类型|100","信息模板|100","排序|100","备注|100"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            CetTraineeType record = records.get(i);
            String[] values = {
                record.getName(),
                            record.getTemplateId()+"",
                            record.getSortOrder()+"",
                            record.getRemark()
            };
            valuesList.add(values);
        }
        String fileName = "参训人员类型_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
        ExportHelper.export(titles, valuesList, fileName, response);
    }

    @RequestMapping("/cetTraineeType_selects")
    @ResponseBody
    public Map cetTraineeType_selects(Integer pageSize, Integer pageNo,String searchStr) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        CetTraineeTypeExample example = new CetTraineeTypeExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("sort_order desc");

        if(StringUtils.isNotBlank(searchStr)){
            criteria.andNameLike("%"+searchStr.trim()+"%");
        }

        long count = cetTraineeTypeMapper.countByExample(example);
        if((pageNo-1)*pageSize >= count){

            pageNo = Math.max(1, pageNo-1);
        }
        List<CetTraineeType> cetTraineeTypes = cetTraineeTypeMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo-1)*pageSize, pageSize));

        List<Select2Option> options = new ArrayList<Select2Option>();
        if(null != cetTraineeTypes && cetTraineeTypes.size()>0){

            for(CetTraineeType cetTraineeType:cetTraineeTypes){

                Select2Option option = new Select2Option();
                option.setText(cetTraineeType.getName());
                option.setId(cetTraineeType.getId() + "");

                options.add(option);
            }
        }

        Map resultMap = success();
        resultMap.put("totalCount", count);
        resultMap.put("options", options);
        return resultMap;
    }
}
