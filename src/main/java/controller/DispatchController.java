package controller;

import domain.Dispatch;
import domain.DispatchExample;
import domain.DispatchExample.Criteria;
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
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import sys.tool.jackson.Select2Option;
import sys.tool.paging.CommonList;
import sys.utils.DateUtils;
import sys.utils.FileUtils;
import sys.utils.FormUtils;
import sys.utils.MSUtils;
import sys.constants.SystemConstants;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.*;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class DispatchController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("dispatch:list")
    @RequestMapping("/dispatch")
    public String dispatch() {

        return "index";
    }
    @RequiresPermissions("dispatch:list")
    @RequestMapping("/dispatch_page")
    public String dispatch_page(HttpServletResponse response,
                                 @RequestParam(required = false, defaultValue = "sort_order") String sort,
                                 @RequestParam(required = false, defaultValue = "desc") String order,
                                    Integer year,
                                    Integer typeId,
                                    String code,
                                    String _pubTime,
                                    String _workTime,
                                    String _meetingTime,
                                 @RequestParam(required = false, defaultValue = "0") int export,
                                 Integer pageSize, Integer pageNo, ModelMap modelMap) {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        DispatchExample example = new DispatchExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause(String.format("%s %s", sort, order));

        if (year!=null) {
            criteria.andYearEqualTo(year);
        }
        if (typeId!=null) {
            criteria.andTypeIdEqualTo(typeId);
        }
        if (StringUtils.isNotBlank(code)) {
            criteria.andCodeLike("%" + code + "%");
        }

        if(StringUtils.isNotBlank(_pubTime)) {
            String pubTimeStart = _pubTime.split(SystemConstants.DATERANGE_SEPARTOR)[0];
            String pubTimeEnd = _pubTime.split(SystemConstants.DATERANGE_SEPARTOR)[1];
            if (StringUtils.isNotBlank(pubTimeStart)) {
                criteria.andPubTimeGreaterThanOrEqualTo(DateUtils.parseDate(pubTimeStart, DateUtils.YYYY_MM_DD));
            }
            if (StringUtils.isNotBlank(pubTimeEnd)) {
                criteria.andPubTimeLessThanOrEqualTo(DateUtils.parseDate(pubTimeEnd, DateUtils.YYYY_MM_DD));
            }
        }
        if(StringUtils.isNotBlank(_workTime)) {
            String workTimeStart = _workTime.split(SystemConstants.DATERANGE_SEPARTOR)[0];
            String workTimeEnd = _workTime.split(SystemConstants.DATERANGE_SEPARTOR)[1];
            if (StringUtils.isNotBlank(workTimeStart)) {
                criteria.andWorkTimeGreaterThanOrEqualTo(DateUtils.parseDate(workTimeStart, DateUtils.YYYY_MM_DD));
            }
            if (StringUtils.isNotBlank(workTimeEnd)) {
                criteria.andWorkTimeLessThanOrEqualTo(DateUtils.parseDate(workTimeEnd, DateUtils.YYYY_MM_DD));
            }
        }
        if(StringUtils.isNotBlank(_meetingTime)) {
            String meetingTimeStart = _meetingTime.split(SystemConstants.DATERANGE_SEPARTOR)[0];
            String meetingTimeEnd = _meetingTime.split(SystemConstants.DATERANGE_SEPARTOR)[1];
            if (StringUtils.isNotBlank(meetingTimeStart)) {
                criteria.andMeetingTimeGreaterThanOrEqualTo(DateUtils.parseDate(meetingTimeStart, DateUtils.YYYY_MM_DD));
            }
            if (StringUtils.isNotBlank(meetingTimeEnd)) {
                criteria.andMeetingTimeLessThanOrEqualTo(DateUtils.parseDate(meetingTimeEnd, DateUtils.YYYY_MM_DD));
            }
        }

        if (export == 1) {
            dispatch_export(example, response);
            return null;
        }

        int count = dispatchMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<Dispatch> Dispatchs = dispatchMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        modelMap.put("dispatchs", Dispatchs);

        CommonList commonList = new CommonList(count, pageNo, pageSize);

        String searchStr = "&pageSize=" + pageSize;

        if (year!=null) {
            searchStr += "&year=" + year;
        }
        if (typeId!=null) {
            searchStr += "&typeId=" + typeId;
        }
        if (StringUtils.isNotBlank(code)) {
            searchStr += "&code=" + code;
        }
        if (StringUtils.isNotBlank(_pubTime)) {
            searchStr += "&_pubTime=" + _pubTime;
        }
        if (StringUtils.isNotBlank(_workTime)) {
            searchStr += "&_workTime=" + _workTime;
        }
        if (StringUtils.isNotBlank(_meetingTime)) {
            searchStr += "&_meetingTime=" + _meetingTime;
        }

        if (StringUtils.isNotBlank(sort)) {
            searchStr += "&sort=" + sort;
        }
        if (StringUtils.isNotBlank(order)) {
            searchStr += "&order=" + order;
        }
        commonList.setSearchStr(searchStr);
        modelMap.put("commonList", commonList);

        modelMap.put("metaTypeMap", metaTypeService.metaTypes("mc_dispatch"));

        return "dispatch/dispatch_page";
    }

    @RequiresPermissions("dispatch:edit")
    @RequestMapping(value = "/dispatch_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_dispatch_au(Dispatch record, String _pubTime,
                              String _workTime,
                              String _meetingTime,
                              MultipartFile _file,
                              MultipartFile _ppt,
                              HttpServletRequest request) {

        Integer id = record.getId();

        if (StringUtils.isNotBlank(record.getCode())
                && dispatchService.idDuplicate(id, record.getCode())) {
            return failed("发文号重复");
        }
        if(_file!=null){
            String originalFilename = _file.getOriginalFilename();
            String fileName = UUID.randomUUID().toString();
            String savePath = File.separator
                    + "dispatch" + File.separator + record.getYear() + File.separator
                    + "file" + File.separator
                    + fileName + FileUtils.getExtention(originalFilename);
            FileUtils.copyFile(_file, new File(springProps.uploadPath + savePath));
            record.setFileName(originalFilename);
            record.setFile(savePath);
        }

        if(_ppt!=null){
            String originalFilename = _ppt.getOriginalFilename();
            String fileName = UUID.randomUUID().toString();
            String savePath = File.separator
                    + "dispatch" + File.separator + record.getYear() + File.separator
                    + "ppt" + File.separator
                    + fileName + FileUtils.getExtention(originalFilename);
            FileUtils.copyFile(_ppt, new File(springProps.uploadPath + savePath));
            record.setPptName(originalFilename);
            record.setPpt(savePath);
        }

        record.setPubTime(DateUtils.parseDate(_pubTime, DateUtils.YYYY_MM_DD));
        record.setWorkTime(DateUtils.parseDate(_workTime, DateUtils.YYYY_MM_DD));
        record.setMeetingTime(DateUtils.parseDate(_meetingTime, DateUtils.YYYY_MM_DD));

        if (id == null) {

            record.setCode(dispatchService.genCode(record.getTypeId(), record.getYear()));
            dispatchService.insertSelective(record);
            logger.info(addLog(request, SystemConstants.LOG_ADMIN, "添加发文：%s", record.getId()));
        }else {

            Dispatch dispatch = dispatchMapper.selectByPrimaryKey(record.getId());
            if(_file!=null && StringUtils.isNotBlank(record.getFile())){
                FileUtils.delFile(springProps.uploadPath + dispatch.getFile()); // 删除原文件
            }
            if(_ppt!=null && StringUtils.isNotBlank(record.getPpt())){
                FileUtils.delFile(springProps.uploadPath + dispatch.getPpt()); // 删除原ppt
            }

            if(dispatch.getTypeId().intValue() != record.getTypeId()){ // 修改了类型，要修改发文号
                record.setCode(dispatchService.genCode(record.getTypeId(), record.getYear()));
            }
            dispatchService.updateByPrimaryKeySelective(record);
            logger.info(addLog(request, SystemConstants.LOG_ADMIN, "更新发文：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("dispatch:download")
    @RequestMapping("/dispatch_download")
    public void dispatch_download(Integer id,
                                  @RequestParam(required = false,defaultValue = "file")String type,
                                  HttpServletResponse response) throws IOException{

        Dispatch dispatch = dispatchMapper.selectByPrimaryKey(id);
        String filePath = springProps.uploadPath +
                (StringUtils.equalsIgnoreCase(type, "file")?dispatch.getFile():dispatch.getPpt());
        byte[] bytes = FileUtils.getBytes(filePath);

        String fileName = URLEncoder.encode(StringUtils.equalsIgnoreCase(type, "file")?
                dispatch.getFileName():dispatch.getPptName(), "UTF-8");
        response.reset();
        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
        response.addHeader("Content-Length", "" + bytes.length);
        response.setContentType("application/octet-stream;charset=UTF-8");
        OutputStream outputStream = new BufferedOutputStream(response.getOutputStream());
        outputStream.write(bytes);
        outputStream.flush();
        outputStream.close();
    }

    @RequiresPermissions("dispatch:edit")
    @RequestMapping("/dispatch_au")
    public String dispatch_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            Dispatch dispatch = dispatchMapper.selectByPrimaryKey(id);
            modelMap.put("dispatch", dispatch);
        }
        return "dispatch/dispatch_au";
    }

    @RequiresPermissions("dispatch:del")
    @RequestMapping(value = "/dispatch_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_dispatch_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            dispatchService.del(id);
            logger.info(addLog(request, SystemConstants.LOG_ADMIN, "删除发文：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("dispatch:del")
    @RequestMapping(value = "/dispatch_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            dispatchService.batchDel(ids);
            logger.info(addLog(request, SystemConstants.LOG_ADMIN, "批量删除发文：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("dispatch:changeOrder")
    @RequestMapping(value = "/dispatch_changeOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map do_dispatch_changeOrder(Integer id, Integer addNum, HttpServletRequest request) {

        dispatchService.changeOrder(id, addNum);
        logger.info(addLog(request, SystemConstants.LOG_ADMIN, "发文调序：%s,%s", id, addNum));
        return success(FormUtils.SUCCESS);
    }

    public void dispatch_export(DispatchExample example, HttpServletResponse response) {

        List<Dispatch> dispatchs = dispatchMapper.selectByExample(example);
        int rownum = dispatchMapper.countByExample(example);

        XSSFWorkbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet();
        XSSFRow firstRow = (XSSFRow) sheet.createRow(0);

        String[] titles = {"年份","发文类型","发文号","党委常委会日期","发文日期","任免日期","任免文件","上会ppt","备注"};
        for (int i = 0; i < titles.length; i++) {
            XSSFCell cell = firstRow.createCell(i);
            cell.setCellValue(titles[i]);
            cell.setCellStyle(MSUtils.getHeadStyle(wb));
        }

        for (int i = 0; i < rownum; i++) {

            Dispatch dispatch = dispatchs.get(i);
            String[] values = {
                        dispatch.getYear()+"",
                                            dispatch.getTypeId()+"",
                                            dispatch.getCode(),
                                            DateUtils.formatDate(dispatch.getMeetingTime(), DateUtils.YYYY_MM_DD),
                                            DateUtils.formatDate(dispatch.getPubTime(), DateUtils.YYYY_MM_DD),
                                            DateUtils.formatDate(dispatch.getWorkTime(), DateUtils.YYYY_MM_DD),
                                            dispatch.getFile(),
                                            dispatch.getPpt(),
                                            dispatch.getRemark()
                    };

            Row row = sheet.createRow(i + 1);
            for (int j = 0; j < titles.length; j++) {

                XSSFCell cell = (XSSFCell) row.createCell(j);
                cell.setCellValue(values[j]);
                cell.setCellStyle(MSUtils.getBodyStyle(wb));
            }
        }
        try {
            String fileName = "发文_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
            ServletOutputStream outputStream = response.getOutputStream();
            fileName = new String(fileName.getBytes(), "ISO8859_1");
            response.setHeader("Content-disposition", "attachment; filename=" + fileName + ".xlsx");
            wb.write(outputStream);
            outputStream.flush();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

   @RequestMapping("/dispatch_selects")
    @ResponseBody
    public Map dispatch_selects(Integer pageSize, Integer pageNo,String searchStr) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        DispatchExample example = new DispatchExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("sort_order desc");

        if(StringUtils.isNotBlank(searchStr)){
            criteria.andCodeLike("%" + searchStr+"%");
        }

        int count = dispatchMapper.countByExample(example);
        if((pageNo-1)*pageSize >= count){

            pageNo = Math.max(1, pageNo-1);
        }
        List<Dispatch> dispatchs = dispatchMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo-1)*pageSize, pageSize));

        List<Select2Option> options = new ArrayList<Select2Option>();
        if(null != dispatchs && dispatchs.size()>0){

            for(Dispatch dispatch:dispatchs){

                Select2Option option = new Select2Option();
                option.setText(dispatch.getCode());
                option.setId(dispatch.getId() + "");

                options.add(option);
            }
        }

        Map resultMap = success();
        resultMap.put("totalCount", count);
        resultMap.put("options", options);
        return resultMap;
    }
}
