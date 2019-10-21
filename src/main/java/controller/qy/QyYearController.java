package controller.qy;

import domain.qy.QyYear;
import domain.qy.QyYearExample;
import domain.qy.QyYearExample.Criteria;
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
import sys.constants.LogConstants;
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

public class QyYearController extends QyBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("qyReward:list")
    @RequestMapping("/qyYear")
    public String qyYear(@RequestParam(defaultValue = "2")Byte cls, ModelMap modelMap) {

        modelMap.put("cls", cls);
        return "qy/qyYear/qyYear_page";
    }

    @RequiresPermissions("qyReward:list")
    @RequestMapping("/qyYear_data")
    @ResponseBody
    public void qyYear_data(HttpServletResponse response,
                                    Integer year,
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

        QyYearExample example = new QyYearExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("id desc");

        if (year!=null) {
            criteria.andYearEqualTo(year);
        }

        if (export == 1) {
            if(ids!=null && ids.length>0)
                criteria.andIdIn(Arrays.asList(ids));
            qyYear_export(example, response);
            return;
        }

        long count = qyYearMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<QyYear> records= qyYearMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(qyYear.class, qyYearMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("qyReward:edit")
    @RequestMapping(value = "/qyYear_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_qyYear_au(QyYear record, HttpServletRequest request) {

        Integer id = record.getId();
        Integer year = record.getYear();
        if (qyYearService.idDuplicate(id, year)) {
            return failed("添加重复");
        }
        if (id == null) {
            
            qyYearService.insertSelective(record);
            logger.info(log( LogConstants.LOG_QY, "添加七一表彰_年度：{0}", record.getId()));
        } else {

            qyYearService.updateByPrimaryKeySelective(record);
            logger.info(log( LogConstants.LOG_QY, "更新七一表彰_年度：{0}", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("qyReward:edit")
    @RequestMapping("/qyYear_au")
    public String qyYear_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            QyYear qyYear = qyYearMapper.selectByPrimaryKey(id);
            modelMap.put("qyYear", qyYear);
        }
        return "qy/qyYear/qyYear_au";
    }
    @RequiresPermissions("qyReward:list")
    @RequestMapping("/qyYear_file")
    public String qyYear_file(Integer yearId,Byte type, ModelMap modelMap) {
        QyYear qyYear = qyYearMapper.selectByPrimaryKey(yearId);
        modelMap.put("qyYear",qyYear);
        return "qy/qyYear/qyYear_file";
    }

    @RequiresPermissions("qyReward:edit")
    @RequestMapping(value = "/qyYear_file", method = RequestMethod.POST)
    @ResponseBody
    public Map do_qyYear_file(int id, Byte type,MultipartFile pdf,MultipartFile word, HttpServletRequest request) throws IOException, InterruptedException {


            String pdfPath = upload(pdf, "qyYear");
            String wordPath = upload(word, "qyYear");
                QyYear record = new QyYear();
                record.setId(id);
                if(type==1){
                        record.setPlanPdf(pdfPath);
                    if(pdf!=null)
                        record.setPlanPdfName(pdf.getOriginalFilename());
                        record.setPlanWord(wordPath);
                    if(word!=null)
                        record.setPlanWordName(word.getOriginalFilename());
                }
                if(type==2){
                        record.setResultPdf(pdfPath);
                    if(pdf!=null)
                        record.setResultPdfName(pdf.getOriginalFilename());
                        record.setResultWord(wordPath);
                    if(word!=null)
                        record.setResultWordName(word.getOriginalFilename());
                }
                qyYearService.updateByPrimaryKeySelective(record);
                logger.info(addLog(LogConstants.LOG_QY, "上传附件：%s", record.getId()));
        return success(FormUtils.SUCCESS);
    }
    @RequiresPermissions("qyReward:edit")
    @RequestMapping(value = "/qyYear_delFile", method = RequestMethod.POST)
    @ResponseBody
    public Map do_qyYear_delFile(HttpServletRequest request, Integer id, Byte type, Byte fileType) {  //type=1 方案文件  2 结果文件    fileType=1 pdf文件  2 word文件

        if(id!=null&&type!=null&&fileType!=null){
            if(type==1&&fileType==1)
            commonMapper.excuteSql("update qy_year set plan_pdf=null,plan_pdf_name=null where id=" + id);
            if(type==1&&fileType==2)
                commonMapper.excuteSql("update qy_year set plan_word=null,plan_word_name=null where id=" + id);
            if(type==2&&fileType==1)
                commonMapper.excuteSql("update qy_year set result_pdf=null,result_pdf_name=null where id=" + id);
            if(type==2&&fileType==2)
                commonMapper.excuteSql("update qy_year set result_word=null,result_word_name=null where id=" + id);

            logger.info(log( LogConstants.LOG_QY, "删除七一表彰_年度附件：{0}", id));
        }
        return success(FormUtils.SUCCESS);
    }
    @RequiresPermissions("qyReward:edit")
    @RequestMapping(value = "/qyYear_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_qyYear_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            qyYearService.del(id);
            logger.info(log( LogConstants.LOG_QY, "删除七一表彰_年度：{0}", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("qyReward:edit")
    @RequestMapping(value = "/qyYear_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map qyYear_batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            qyYearService.batchDel(ids);
            logger.info(log( LogConstants.LOG_QY, "批量删除七一表彰_年度：{0}", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }
    public void qyYear_export(QyYearExample example, HttpServletResponse response) {

        List<QyYear> records = qyYearMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"年度|100","表彰方案pdf|100","表彰结果pdf|100","备注|100"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            QyYear record = records.get(i);
            String[] values = {
                record.getYear()+"",
                            record.getPlanPdf(),
                            record.getResultPdf(),
                            record.getRemark()
            };
            valuesList.add(values);
        }
        String fileName = String.format("七一表彰_年度(%s)", DateUtils.formatDate(new Date(), "yyyyMMdd"));
        ExportHelper.export(titles, valuesList, fileName, response);
    }

    @RequestMapping("/qyYear_selects")
    @ResponseBody
    public Map qyYear_selects(Integer pageSize, Integer pageNo,String searchStr) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        QyYearExample example = new QyYearExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("id desc");

      /*  if(StringUtils.isNotBlank(searchStr)){
            criteria.andNameLike(SqlUtils.like(searchStr));
        }*/

        long count = qyYearMapper.countByExample(example);
        if((pageNo-1)*pageSize >= count){

            pageNo = Math.max(1, pageNo-1);
        }
        List<QyYear> records = qyYearMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo-1)*pageSize, pageSize));

        List options = new ArrayList<>();
        if(null != records && records.size()>0){

            for(QyYear record:records){

                Map<String, Object> option = new HashMap<>();
               option.put("text", record.getYear());
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
