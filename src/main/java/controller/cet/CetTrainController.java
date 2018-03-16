package controller.cet;

import domain.cet.CetTrain;
import domain.cet.CetTrainExample;
import domain.cet.CetTrainExample.Criteria;
import domain.cet.CetTrainView;
import domain.cet.CetTrainViewExample;
import domain.cet.CetTraineeType;
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
import sys.constants.CetConstants;
import sys.constants.SystemConstants;
import sys.tool.paging.CommonList;
import sys.utils.DateUtils;
import sys.utils.ExportHelper;
import sys.utils.FormUtils;
import sys.utils.JSONUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/cet")
public class CetTrainController extends CetBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("cetTrain:list")
    @RequestMapping("/cetTrain")
    public String cetTrain(@RequestParam(defaultValue = "1") Integer cls, ModelMap modelMap) {

        modelMap.put("cls", cls);

        return "cet/cetTrain/cetTrain_page";
    }

    @RequiresPermissions("cetTrain:list")
    @RequestMapping("/cetTrain_data")
    public void cetTrain_data(HttpServletResponse response,
                              @RequestParam(defaultValue = "1") Integer cls,
                                    Integer year,
                                    Integer num,
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

        CetTrainViewExample example = new CetTrainViewExample();
        CetTrainViewExample.Criteria criteria = example.createCriteria();

        if(cls==1){
            criteria.andIsDeletedEqualTo(false).andIsFinishedEqualTo(false);
        }else if(cls==2){
            criteria.andIsDeletedEqualTo(false).andIsFinishedEqualTo(true);
        }else if(cls==3){
            criteria.andIsDeletedEqualTo(true);
        }

        example.setOrderByClause("create_time desc");

        if (year!=null) {
            criteria.andYearEqualTo(year);
        }
        if (num!=null) {
            criteria.andNumEqualTo(num);
        }
        if (StringUtils.isNotBlank(name)) {
            criteria.andNameLike("%" + name + "%");
        }

        if (export == 1) {
            if(ids!=null && ids.length>0)
                criteria.andIdIn(Arrays.asList(ids));
            cetTrain_export(example, response);
            return;
        }

        long count = cetTrainViewMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<CetTrainView> records= cetTrainViewMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(cetTrain.class, cetTrainMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("cetTrain:edit")
    @RequestMapping("/cetTrain_detail")
    public String cetTrain_detail(Integer trainId, ModelMap modelMap) {

        if (trainId != null) {
            CetTrain cetTrain = cetTrainMapper.selectByPrimaryKey(trainId);
            modelMap.put("cetTrain", cetTrain);
        }
        return "cet/cetTrain/cetTrain_detail";
    }

    @RequiresPermissions("cetTrain:edit")
    @RequestMapping(value = "/cetTrain_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cetTrain_au(CetTrain record,
                              @RequestParam(value = "traineeTypeIds[]", required = false) Integer[] traineeTypeIds,
                              HttpServletRequest request) {

        Integer id = record.getId();

        if (id == null) {
            if (record.getNum() == null)
                record.setNum(cetTrainService.genNum(record.getType(), record.getYear()));
            cetTrainService.insertSelective(record, traineeTypeIds);
            logger.info(addLog( SystemConstants.LOG_CET, "添加培训班：%s", record.getId()));
        } else {
            CetTrain cetTrain = cetTrainMapper.selectByPrimaryKey(id);
            if (cetTrain.getType().intValue() != record.getType()) { // 修改了类型，要修改发文号
                record.setNum(cetTrainService.genNum(record.getType(), record.getYear()));
            }
            cetTrainService.updateWithTraineeTypes(record, traineeTypeIds);
            logger.info(addLog( SystemConstants.LOG_CET, "更新培训班：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cetTrain:edit")
    @RequestMapping("/cetTrain_au")
    public String cetTrain_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            CetTrain cetTrain = cetTrainMapper.selectByPrimaryKey(id);
            modelMap.put("cetTrain", cetTrain);

            List<Integer> traineeTypeIds = cetTrainService.findTraineeTypeIds(id);
            modelMap.put("traineeTypeIds", traineeTypeIds);
        }

        Map<Integer, List<CetTraineeType>> templateMap = new HashMap<>();
        Map<Integer, CetTraineeType> traineeTypeMap = cetTraineeTypeService.findAll();
        for (CetTraineeType cetTraineeType : traineeTypeMap.values()) {
            int templateId = cetTraineeType.getTemplateId();
            List<CetTraineeType> cetTraineeTypes = templateMap.get(templateId);
            if(cetTraineeTypes==null){
                cetTraineeTypes = new ArrayList<>();
                templateMap.put(templateId, cetTraineeTypes);
            }
            cetTraineeTypes.add(cetTraineeType);
        }
        modelMap.put("templateMap", templateMap);


        return "cet/cetTrain/cetTrain_au";
    }


    @RequiresPermissions("cetTrain:edit")
    @RequestMapping("/cetTrain_summary")
    public String cetTrain_summary(Integer id, ModelMap modelMap) {

        if (id != null) {
            CetTrain cetTrain = cetTrainMapper.selectByPrimaryKey(id);
            modelMap.put("cetTrain", cetTrain);
        }
        return "cet/cetTrain/cetTrain_summary";
    }

    @RequiresPermissions("cetTrain:edit")
    @RequestMapping(value = "/cetTrain_summary", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cetTrain_summary(Integer id, String summary) {

        CetTrain record = new CetTrain();
        record.setId(id);
        record.setSummary(summary);
        record.setHasSummary(StringUtils.isNotBlank(summary));
        cetTrainMapper.updateByPrimaryKeySelective(record);
        logger.info(addLog(SystemConstants.LOG_CET, "更新内容简介：%s", id));

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cetTrain:pub")
    @RequestMapping(value = "/cetTrain_pub", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cetTrain_pub(HttpServletRequest request, Integer id, Byte pubStatus) {

        if (id != null) {

            CetTrain record = new CetTrain();
            record.setId(id);
            record.setPubStatus(pubStatus);
            cetTrainMapper.updateByPrimaryKeySelective(record);

            logger.info(addLog(SystemConstants.LOG_CET,
                    CetConstants.CET_TRAIN_PUB_STATUS_MAP.get(pubStatus) + "培训班：%s", id));
        }

        return success(FormUtils.SUCCESS);
    }
    @RequiresPermissions("cetTrain:pub")
    @RequestMapping(value = "/cetTrain_finish", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cetTrain_finish(HttpServletRequest request, Integer id) {

        if (id != null) {

            CetTrain record = new CetTrain();
            record.setId(id);
            record.setIsFinished(true);
            cetTrainMapper.updateByPrimaryKeySelective(record);

            logger.info(addLog(SystemConstants.LOG_CET,
                    "培训班结课：%s", id));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cetTrain:del")
    @RequestMapping(value = "/cetTrain_fakeDel", method = RequestMethod.POST)
    @ResponseBody
    public Map cetTrain_fakeDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            cetTrainService.fakeDel(ids);
            logger.info(addLog( SystemConstants.LOG_CET, "批量删除培训班：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cetTrain:del")
    @RequestMapping(value = "/cetTrain_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map cetTrain_batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            cetTrainService.batchDel(ids);
            logger.info(addLog( SystemConstants.LOG_CET, "批量删除培训班：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    public void cetTrain_export(CetTrainViewExample example, HttpServletResponse response) {

        List<CetTrainView> records = cetTrainViewMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"年度|100","编号|100","培训班名称|100","内容简介|100","开课日期|100","结课日期|100","选课状态|100","备注|100"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            CetTrainView record = records.get(i);
            String[] values = {
                record.getYear()+"",
                            record.getNum()+"",
                            record.getName(),
                            record.getSummary(),
                            DateUtils.formatDate(record.getStartDate(), DateUtils.YYYY_MM_DD),
                            DateUtils.formatDate(record.getEndDate(), DateUtils.YYYY_MM_DD),
                            record.getEnrollStatus() + "",
                            record.getRemark()
            };
            valuesList.add(values);
        }
        String fileName = "培训班_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
        ExportHelper.export(titles, valuesList, fileName, response);
    }

    @RequestMapping("/cetTrain_selects")
    @ResponseBody
    public Map cetTrain_selects(Integer pageSize, Integer pageNo,String searchStr) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        CetTrainExample example = new CetTrainExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("create_time desc");

        if(StringUtils.isNotBlank(searchStr)){
            criteria.andNameLike("%"+searchStr+"%");
        }

        long count = cetTrainMapper.countByExample(example);
        if((pageNo-1)*pageSize >= count){

            pageNo = Math.max(1, pageNo-1);
        }
        List<CetTrain> cetTrains = cetTrainMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo-1)*pageSize, pageSize));

        List options = new ArrayList<>();
        if(null != cetTrains && cetTrains.size()>0){

            for(CetTrain cetTrain:cetTrains){

                Map<String, Object> option = new HashMap<>();
                option.put("text", cetTrain.getName());
                option.put("id", cetTrain.getId());
                option.put("sn", cetTrain.getSn());

                options.add(option);
            }
        }

        Map resultMap = success();
        resultMap.put("totalCount", count);
        resultMap.put("options", options);
        return resultMap;
    }
}
