package controller.unit;

import controller.BaseController;
import domain.unit.Unit;
import domain.unit.UnitFunction;
import domain.unit.UnitFunctionExample;
import domain.unit.UnitFunctionExample.Criteria;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.HtmlUtils;
import sys.constants.LogConstants;
import sys.tool.paging.CommonList;
import sys.utils.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Controller

public class UnitFunctionController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("unit:list")
    @RequestMapping("/unitFunction")
    public String unitFunction(Integer funId, int unitId,
                               @RequestParam(required = false, defaultValue = "0") boolean isEdit,
                               ModelMap modelMap) {

        // 单位职能
        List<UnitFunction> unitFunctions = unitFunctionService.getUnitFunctions(unitId);
        modelMap.put("unitFunctions", unitFunctions);

        modelMap.put("unitId", unitId);
        int currentId = -1;
        if (funId != null && funId>0) {

            currentId = funId;
            UnitFunction unitFunction = unitFunctionMapper.selectByPrimaryKey(funId);
            modelMap.put("unitFunction", unitFunction);
            unitId = unitFunction.getUnitId();

            if(!isEdit){
                modelMap.put("unitId", unitId);
                modelMap.put("unit", unitService.findAll().get(unitId));
                modelMap.put("currentId", currentId);
                return "unit/unitFunction/unitFunction_view";
            }
        }else if(funId != null && funId==-1){ // 添加页面

        }else{
            if(unitFunctions.size()>0){
                UnitFunction unitFunction = unitFunctions.get(0);
                modelMap.put("unitFunction", unitFunction);
                currentId = unitFunction.getId();
                modelMap.put("currentId", currentId);
                return "unit/unitFunction/unitFunction_view";
            }
        }
        modelMap.put("currentId", currentId);
        return "unit/unitFunction/unitFunction_au";
    }

    @RequiresPermissions("unit:list")
    @RequestMapping("/unitFunction_data")
    @ResponseBody
    public void unitFunction_data(HttpServletResponse response,
                                 Integer unitId,
                                 Boolean isCurrent,
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

        UnitFunctionExample example = new UnitFunctionExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("is_current desc, confirm_time desc");

        if (unitId!=null) {
            criteria.andUnitIdEqualTo(unitId);
        }
        if (isCurrent!=null) {
            criteria.andIsCurrentEqualTo(isCurrent);
        }

        if (export == 1) {
            if(ids!=null && ids.length>0)
                criteria.andIdIn(Arrays.asList(ids));
            unitFunction_export(example, response);
            return;
        }

        long count = unitFunctionMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<UnitFunction> records= unitFunctionMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(unitFunction.class, unitFunctionMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("unit:edit")
    @RequestMapping(value = "/unitFunction_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_unitFunction_au(UnitFunction record,
                                  MultipartFile _file,
                                  HttpServletRequest request) throws IOException, InterruptedException {

        Integer id = record.getId();
        record.setContent(record.getContent());

        if(_file!=null && !_file.isEmpty()) {
            String pdfFilePath = upload(_file, "unitFunction");
            record.setFilePath(pdfFilePath);
        }

        record.setIsCurrent(BooleanUtils.isTrue(record.getIsCurrent()));

        if (id == null) {
            
            unitFunctionService.insertSelective(record);
            logger.info(log( LogConstants.LOG_ADMIN, "添加单位职能：{0}", record.getId()));
        } else {

            unitFunctionService.updateByPrimaryKeySelective(record);
            logger.info(log( LogConstants.LOG_ADMIN, "更新单位职能：{0}", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("unit:edit")
    @RequestMapping("/unitFunction_au")
    public String unitFunction_au(Integer id, Integer unitId, ModelMap modelMap) {

        if (id != null) {
            UnitFunction unitFunction = unitFunctionMapper.selectByPrimaryKey(id);
            modelMap.put("unitFunction", unitFunction);
            unitId = unitFunction.getUnitId();
        }

        modelMap.put("unitId", unitId);

        return "unit/unitFunction/unitFunction_au";
    }

    @RequiresPermissions("unit:del")
    @RequestMapping(value = "/unitFunction_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map unitFunction_batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            unitFunctionService.batchDel(ids);
            logger.info(log( LogConstants.LOG_ADMIN, "批量删除单位职能：{0}", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    public void unitFunction_export(UnitFunctionExample example, HttpServletResponse response) {

        List<UnitFunction> records = unitFunctionMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"所属单位|100","是否当前职能|100","职能确定时间|100","职能内容|100","相关文件|100","备注|100"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            UnitFunction record = records.get(i);
            String[] values = {
                record.getUnitId()+"",
                            record.getIsCurrent()+"",
                            DateUtils.formatDate(record.getConfirmTime(), DateUtils.YYYY_MM_DD),
                            record.getContent(),
                            record.getFilePath(),
                            record.getRemark()
            };
            valuesList.add(values);
        }
        String fileName = "单位职能_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
        ExportHelper.export(titles, valuesList, fileName, response);
    }

    @RequiresPermissions("unit:list")
    @RequestMapping("/unitFunction_attachDownload")
    public void unitFunction_attachDownload(HttpServletRequest request,
                                  Integer id,
                                  Integer unitId,
                                  HttpServletResponse response) throws IOException {

        UnitFunctionExample example = new UnitFunctionExample();
        example.createCriteria().andIdEqualTo(id).andUnitIdEqualTo(unitId);
        List<UnitFunction> unitFunctions = unitFunctionMapper.selectByExample(example);

        if (unitFunctions != null && unitFunctions.size() > 0) {
            Unit unit = unitMapper.selectByPrimaryKey(unitId);
            UnitFunction unitFunction = unitFunctions.get(0);
            String path = HtmlUtils.htmlUnescape(unitFunction.getFilePath());
            String filename = HtmlUtils.htmlUnescape(unit.getName() + "单位职能" + DateUtils.formatDate(unitFunction.getConfirmTime(), "yyyyMMdd") + ".pdf");

            DownloadUtils.download(request, response, springProps.uploadPath + path, filename);
        }
    }
}
