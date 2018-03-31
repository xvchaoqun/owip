package controller.cet;

import domain.cet.CetSpecial;
import domain.cet.CetSpecialExample;
import domain.cet.CetSpecialExample.Criteria;
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
import org.springframework.web.multipart.MultipartFile;
import sys.constants.SystemConstants;
import sys.tool.paging.CommonList;
import sys.utils.DateUtils;
import sys.utils.ExportHelper;
import sys.utils.FileUtils;
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
public class CetSpecialController extends CetBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("cetSpecial:list")
    @RequestMapping("/cetSpecial")
    public String cetSpecial() {

        return "cet/cetSpecial/cetSpecial_page";
    }

    @RequiresPermissions("cetSpecial:list")
    @RequestMapping("/cetSpecial_data")
    public void cetSpecial_data(HttpServletResponse response,
                                    Integer year,
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

        CetSpecialExample example = new CetSpecialExample();
        Criteria criteria = example.createCriteria();
        //example.setOrderByClause(String.format("%s %s", sort, order));

        if (year!=null) {
            criteria.andYearEqualTo(year);
        }
        if (StringUtils.isNotBlank(name)) {
            criteria.andNameLike("%" + name + "%");
        }

        if (export == 1) {
            if(ids!=null && ids.length>0)
                criteria.andIdIn(Arrays.asList(ids));
            cetSpecial_export(example, response);
            return;
        }

        long count = cetSpecialMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<CetSpecial> records= cetSpecialMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(cetSpecial.class, cetSpecialMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("cetSpecial:edit")
    @RequestMapping(value = "/cetSpecial_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cetSpecial_au(CetSpecial record,
                                MultipartFile _pdfFilePath,
                                MultipartFile _wordFilePath,
                                HttpServletRequest request) throws IOException, InterruptedException {

        Integer id = record.getId();

        if(record.getStartDate()!=null && record.getEndDate()!=null
                && record.getStartDate().after(record.getEndDate())){
            return failed("培训时间有误。");
        }

        record.setFileName(_pdfFilePath!=null? FileUtils.getFileName(_pdfFilePath.getOriginalFilename()):null);
        record.setPdfFilePath(uploadPdf(_pdfFilePath, "cet_special"));
        record.setWordFilePath(upload(_wordFilePath, "cet_special"));

        if (id == null) {
            cetSpecialService.insertSelective(record);
            logger.info(addLog( SystemConstants.LOG_CET, "添加专题培训：%s", record.getId()));
        } else {

            cetSpecialService.updateByPrimaryKeySelective(record);
            logger.info(addLog( SystemConstants.LOG_CET, "更新专题培训：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cetSpecial:edit")
    @RequestMapping("/cetSpecial_au")
    public String cetSpecial_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            CetSpecial cetSpecial = cetSpecialMapper.selectByPrimaryKey(id);
            modelMap.put("cetSpecial", cetSpecial);
        }
        return "cet/cetSpecial/cetSpecial_au";
    }

    @RequiresPermissions("cetSpecial:del")
    @RequestMapping(value = "/cetSpecial_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map cetSpecial_batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            cetSpecialService.batchDel(ids);
            logger.info(addLog( SystemConstants.LOG_CET, "批量删除专题培训：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    public void cetSpecial_export(CetSpecialExample example, HttpServletResponse response) {

        List<CetSpecial> records = cetSpecialMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"年度|100","培训时间（开始）|100","培训时间（结束）|100","培训班名称|100","文件名|100","pdf文件|100","word文件|100","总学时|100","达到结业要求的学时数|100","备注|100"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            CetSpecial record = records.get(i);
            String[] values = {
                record.getYear()+"",
                            DateUtils.formatDate(record.getStartDate(), DateUtils.YYYY_MM_DD),
                            DateUtils.formatDate(record.getEndDate(), DateUtils.YYYY_MM_DD),
                            record.getName(),
                            record.getFileName(),
                            record.getPdfFilePath(),
                            record.getWordFilePath(),
                            record.getPeriod() + "",
                            record.getRequirePeriod() + "",
                            record.getRemark()
            };
            valuesList.add(values);
        }
        String fileName = "专题培训_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
        ExportHelper.export(titles, valuesList, fileName, response);
    }

 /*   @RequestMapping("/cetSpecial_selects")
    @ResponseBody
    public Map cetSpecial_selects(Integer pageSize, Integer pageNo,String searchStr) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        CetSpecialExample example = new CetSpecialExample();
        Criteria criteria = example.createCriteria().andStatusEqualTo(true);
        example.setOrderByClause("sort_order desc");

        if(StringUtils.isNotBlank(searchStr)){
            criteria.andNameLike("%"+searchStr+"%");
        }

        long count = cetSpecialMapper.countByExample(example);
        if((pageNo-1)*pageSize >= count){

            pageNo = Math.max(1, pageNo-1);
        }
        List<CetSpecial> cetSpecials = cetSpecialMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo-1)*pageSize, pageSize));

        List options = new ArrayList<>();
        if(null != cetSpecials && cetSpecials.size()>0){

            for(CetSpecial cetSpecial:cetSpecials){

                Map<String, Object> option = new HashMap<>();
                option.put("text", cetSpecial.getName());
                option.put("id", cetSpecial.getId() + "");

                options.add(option);
            }
        }

        Map resultMap = success();
        resultMap.put("totalCount", count);
        resultMap.put("options", options);
        return resultMap;
    }*/
}
