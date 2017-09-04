package controller.dispatch;

import controller.BaseController;
import controller.global.OpException;
import domain.dispatch.Dispatch;
import domain.dispatch.DispatchExample;
import domain.dispatch.DispatchExample.Criteria;
import domain.dispatch.DispatchType;
import domain.dispatch.DispatchView;
import domain.dispatch.DispatchViewExample;
import mixin.DispatchMixin;
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
import sys.spring.DateRange;
import sys.spring.RequestDateRange;
import sys.tags.CmTag;
import sys.tool.paging.CommonList;
import sys.utils.ContentTypeUtils;
import sys.utils.DateUtils;
import sys.utils.ExportHelper;
import sys.utils.FileUtils;
import sys.utils.FormUtils;
import sys.utils.JSONUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
public class DispatchController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("dispatch:list")
    @RequestMapping("/dispatch")
    public String dispatch(@RequestParam(defaultValue = "1") Integer cls,
                                Integer dispatchTypeId, ModelMap modelMap) {

        modelMap.put("cls", cls);
        if (cls == 2) {
            return "forward:/dispatchCadre";
        }

        if (dispatchTypeId != null) {
            Map<Integer, DispatchType> dispatchTypeMap = dispatchTypeService.findAll();
            modelMap.put("dispatchType", dispatchTypeMap.get(dispatchTypeId));
        }

        return "dispatch/dispatch_page";
    }

    @RequiresPermissions("dispatch:list")
    @RequestMapping("/dispatch_data")
    public void dispatch_data(HttpServletResponse response,
                                 /*@SortParam(required = false, defaultValue = "sort_order", tableName = "dispatch") String sort,
                                 @OrderParam(required = false, defaultValue = "desc") String order,*/
                              Integer year,
                              Integer dispatchTypeId,
                              Integer code,
                              @RequestDateRange DateRange _pubTime,
                              @RequestDateRange DateRange _workTime,
                              @RequestDateRange DateRange _meetingTime,
                              @RequestParam(required = false, value = "ids[]") Integer[] ids, // 导出的记录
                              @RequestParam(required = false, defaultValue = "0") int export,
                              Integer pageSize, Integer pageNo) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        DispatchViewExample example = new DispatchViewExample();
        DispatchViewExample.Criteria criteria = example.createCriteria();

        if (year != null) {
            criteria.andYearEqualTo(year);
        }
        if (dispatchTypeId != null) {
            criteria.andDispatchTypeIdEqualTo(dispatchTypeId);
        }
        if (code != null) {
            criteria.andCodeEqualTo(code);
        }
        if (_pubTime.getStart() != null) {
            criteria.andPubTimeGreaterThanOrEqualTo(_pubTime.getStart());
        }

        if (_pubTime.getEnd() != null) {
            criteria.andPubTimeLessThanOrEqualTo(_pubTime.getEnd());
        }
        if (_workTime.getStart() != null) {
            criteria.andWorkTimeGreaterThanOrEqualTo(_workTime.getStart());
        }

        if (_workTime.getEnd() != null) {
            criteria.andWorkTimeLessThanOrEqualTo(_workTime.getEnd());
        }
        if (_meetingTime.getStart() != null) {
            criteria.andMeetingTimeGreaterThanOrEqualTo(_meetingTime.getStart());
        }

        if (_meetingTime.getEnd() != null) {
            criteria.andMeetingTimeLessThanOrEqualTo(_meetingTime.getEnd());
        }

        if (export == 1) {
            if (ids != null && ids.length > 0)
                criteria.andIdIn(Arrays.asList(ids));
            dispatch_export(example, response);
            return;
        }

        int count = dispatchViewMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<DispatchView> Dispatchs = dispatchViewMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));

        CommonList commonList = new CommonList(count, pageNo, pageSize);
        Map resultMap = new HashMap();
        resultMap.put("rows", Dispatchs);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        baseMixins.put(DispatchView.class, DispatchMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("dispatch:edit")
    @RequestMapping("/dispatch_au_page")
    public String dispatch_cadres(Integer id, ModelMap modelMap) {

        Dispatch dispatch = dispatchMapper.selectByPrimaryKey(id);
        modelMap.put("dispatch", dispatch);

        return "dispatch/dispatch_au_page";
    }

    @RequiresPermissions("dispatch:edit")
    @RequestMapping("/dispatch_au")
    public String dispatch_au(Integer id, ModelMap modelMap) {

        Integer year = null;
        if (id != null) {
            Dispatch dispatch = dispatchMapper.selectByPrimaryKey(id);
            modelMap.put("dispatch", dispatch);
            year = dispatch.getYear();

            Map<Integer, DispatchType> dispatchTypeMap = dispatchTypeService.findAll();
            modelMap.put("dispatchType", dispatchTypeMap.get(dispatch.getDispatchTypeId()));
        }

        if (year == null) {
            year = DateUtils.getCurrentYear();
        }
        modelMap.put("year", year);

        return "dispatch/dispatch_au";
    }

    /*private String uploadFile(MultipartFile _file) {

        String originalFilename = _file.getOriginalFilename();
        String ext = FileUtils.getExtention(originalFilename);
        if (!StringUtils.equalsIgnoreCase(ext, ".pdf")
                && !ContentTypeUtils.isFormat(_file, "pdf")) {
            throw new OpException("任免文件格式错误，请上传pdf文件");
        }

        String uploadDate = DateUtils.formatDate(new Date(), "yyyyMM");

        String fileName = UUID.randomUUID().toString();
        String realPath = FILE_SEPARATOR
                + "dispatch" + FILE_SEPARATOR + uploadDate + FILE_SEPARATOR
                + "file" + FILE_SEPARATOR
                + fileName;
        String savePath = realPath + FileUtils.getExtention(originalFilename);
        FileUtils.copyFile(_file, new File(springProps.uploadPath + savePath));

        try {
            String swfPath = realPath + ".swf";
            pdf2Swf(savePath, swfPath);
        } catch (IOException | InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

            return null;
        }

        return savePath;
    }*/

    @RequiresPermissions("dispatch:edit")
    @RequestMapping(value = "/dispatch_upload", method = RequestMethod.POST)
    @ResponseBody
    public Map do_dispatch_upload(MultipartFile file) throws InterruptedException, IOException {

        String originalFilename = file.getOriginalFilename();
        String ext = FileUtils.getExtention(originalFilename);
        if (!StringUtils.equalsIgnoreCase(ext, ".pdf")
                && !ContentTypeUtils.isFormat(file, "pdf")) {
            throw new OpException("任免文件格式错误，请上传pdf文件");
        }

        String savePath = uploadPdf(file, "dispatch");

        Map<String, Object> resultMap = success();
        resultMap.put("fileName", file.getOriginalFilename());
        resultMap.put("file", savePath);

        return resultMap;
    }

    @RequiresPermissions("dispatch:edit")
    @RequestMapping(value = "/dispatch_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_dispatch_au(Dispatch record, String _pubTime,
                              String _workTime,
                              String _meetingTime,
                              /*MultipartFile _file,*/
                              String file,
                              String fileName,
                              MultipartFile _ppt,
                              HttpServletRequest request) throws InterruptedException {

        Integer id = record.getId();

        if (record.getCode() != null
                && dispatchService.idDuplicate(id, record.getDispatchTypeId(), record.getYear(), record.getCode())) {
            return failed("发文号重复");
        }

        /*if(_file!=null){

            String savePath = uploadFile(_file);
            if(savePath!=null) {
                record.setFileName(_file.getOriginalFilename());
                record.setFile(savePath);
                if (_ppt != null) {
                    Thread.sleep(2000);
                }
            }
        }*/
        record.setFileName(StringUtils.trimToNull(fileName));
        record.setFile(StringUtils.trimToNull(file));

        if (_ppt != null) {
            String uploadDate = DateUtils.formatDate(new Date(), "yyyyMM");
            String ext = FileUtils.getExtention(_ppt.getOriginalFilename());
            if (!StringUtils.equalsIgnoreCase(ext, ".ppt") && !StringUtils.equalsIgnoreCase(ext, ".pptx")) {
                throw new RuntimeException("上会ppt文件格式错误，请上传ppt文件");
            }

            String originalFilename = _ppt.getOriginalFilename();
            String _fileName = UUID.randomUUID().toString();
            String realPath = FILE_SEPARATOR
                    + "dispatch" + FILE_SEPARATOR + uploadDate + FILE_SEPARATOR
                    + "ppt" + FILE_SEPARATOR
                    + _fileName;
            String savePath = realPath + FileUtils.getExtention(originalFilename);
            String pdfPath = realPath + ".pdf";
            FileUtils.copyFile(_ppt, new File(springProps.uploadPath + savePath));
            FileUtils.word2pdf(springProps.uploadPath + savePath, springProps.uploadPath + pdfPath);

            try {
                String swfPath = realPath + ".swf";
                pdf2Swf(pdfPath, swfPath);
            } catch (IOException | InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            record.setPptName(originalFilename);
            record.setPpt(savePath);
        }

        record.setPubTime(DateUtils.parseDate(_pubTime, DateUtils.YYYY_MM_DD));
        record.setWorkTime(DateUtils.parseDate(_workTime, DateUtils.YYYY_MM_DD));
        record.setMeetingTime(DateUtils.parseDate(_meetingTime, DateUtils.YYYY_MM_DD));

        if (id == null) {
            if (record.getCode() == null)
                record.setCode(dispatchService.genCode(record.getDispatchTypeId(), record.getYear()));
            dispatchService.insertSelective(record);
            id = record.getId(); // 新ID
            logger.info(addLog(SystemConstants.LOG_ADMIN, "添加发文：%s", id));
        } else {

            Dispatch dispatch = dispatchMapper.selectByPrimaryKey(id);
            if (dispatch != null && dispatch.getHasChecked()) {
                return failed("已经复核，不可修改。");
            }

            if (StringUtils.isNotBlank(file) && StringUtils.isNotBlank(record.getFile())) {
                FileUtils.delFile(springProps.uploadPath + dispatch.getFile()); // 删除原文件
            }
            if (_ppt != null && StringUtils.isNotBlank(record.getPpt())) {
                FileUtils.delFile(springProps.uploadPath + dispatch.getPpt()); // 删除原ppt
            }

            if (dispatch.getDispatchTypeId().intValue() != record.getDispatchTypeId()) { // 修改了类型，要修改发文号
                record.setCode(dispatchService.genCode(record.getDispatchTypeId(), record.getYear()));
            }
            dispatchService.updateByPrimaryKeySelective(record);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "更新发文：%s", id));
        }

        Map<String, Object> resultMap = success(FormUtils.SUCCESS);
        resultMap.put("id", id);
        return resultMap;
    }

    // swf内容
   /* @RequestMapping("/dispatch_swf")
    public void dispatch_swf(Integer id, @RequestParam(required = false,defaultValue = "file")String type
            , HttpServletResponse response) throws IOException{

        Dispatch dispatch = dispatchMapper.selectByPrimaryKey(id);
        String filePath = (StringUtils.equalsIgnoreCase(type, "file")?dispatch.getFile():dispatch.getPpt());
        filePath = springProps.uploadPath + FileUtils.getFileName(filePath) + ".swf";

        byte[] bytes = FileUtils.getBytes(filePath);
        if(bytes==null) return ;

        response.reset();
        response.addHeader("Content-Length", "" + bytes.length);
        response.setContentType("application/octet-stream;charset=UTF-8");
        OutputStream outputStream = new BufferedOutputStream(response.getOutputStream());
        outputStream.write(bytes);
        outputStream.flush();
        outputStream.close();
    }

    @RequestMapping("/swf_preview")
    public String swf_preview(Integer id,
                              @RequestParam(required = false,defaultValue = "file")String type,
                              @RequestParam(required = false,defaultValue = "1")int way,
                              ModelMap modelMap) {
        if(id!=null) {
            Dispatch dispatch = dispatchMapper.selectByPrimaryKey(id);
            if(dispatch!=null) {
                String filePath = null;
                String path = (StringUtils.equalsIgnoreCase(type, "file") ? dispatch.getFile() : dispatch.getPpt());
                if (StringUtils.isNotBlank(path))
                    filePath = springProps.uploadPath + path;
                modelMap.put("dispatch", dispatch);
                modelMap.put("filePath", filePath);
            }
        }

        switch (way) {
            case 1:
                return "dispatch/swf_preview";
            case 2:// 可以设置关闭js方法
                return "dispatch/swf_preview2";
            case 3: // 不弹出，在页面中打开
                return "dispatch/swf_preview3";
        }

        return null;
    }*/

    @RequiresPermissions("dispatch:download")
    @RequestMapping("/dispatch_download")
    public void dispatch_download(Integer id,
                                  @RequestParam(required = false, defaultValue = "file") String type,
                                  HttpServletResponse response) throws IOException {

        Dispatch dispatch = dispatchMapper.selectByPrimaryKey(id);
        String filePath = springProps.uploadPath +
                (StringUtils.equalsIgnoreCase(type, "file") ? dispatch.getFile() : dispatch.getPpt());
        byte[] bytes = FileUtils.getBytes(filePath);

        String fileName = URLEncoder.encode(StringUtils.equalsIgnoreCase(type, "file") ?
                dispatch.getFileName() : dispatch.getPptName(), "UTF-8");
        response.reset();
        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
        response.addHeader("Content-Length", "" + bytes.length);
        response.setContentType("application/octet-stream;charset=UTF-8");
        OutputStream outputStream = new BufferedOutputStream(response.getOutputStream());
        outputStream.write(bytes);
        outputStream.flush();
        outputStream.close();
    }

    // 复核
    @RequiresPermissions("dispatch:check")
    @RequestMapping(value = "/dispatch_check", method = RequestMethod.POST)
    @ResponseBody
    public Map dispatch_check(Integer id) {

        Dispatch dispatch = dispatchMapper.selectByPrimaryKey(id);

        int realAppointCount = dispatch.getRealAppointCount() == null ? 0 : dispatch.getRealAppointCount();
        int realDismissCount = dispatch.getRealDismissCount() == null ? 0 : dispatch.getRealDismissCount();
        int appointCount = dispatch.getAppointCount() == null ? 0 : dispatch.getAppointCount();
        int dismissCount = dispatch.getDismissCount() == null ? 0 : dispatch.getDismissCount();

        if ((realAppointCount + realDismissCount) > 0
                && appointCount == realAppointCount
                && dismissCount == realDismissCount) {
            Dispatch record = new Dispatch();
            record.setId(id);
            record.setHasChecked(true);
            dispatchService.updateByPrimaryKeySelective(record);
        } else {
            throw new RuntimeException("还未全部录入，不能进行复核操作");
        }

        return success(FormUtils.SUCCESS);
    }

    // 重新复核
    @RequiresPermissions("dispatch:check")
    @RequestMapping(value = "/dispatch_reset_check", method = RequestMethod.POST)
    @ResponseBody
    public Map dispatch_reset_check(Integer id) {

        Dispatch record = new Dispatch();
        record.setId(id);
        record.setHasChecked(false);
        dispatchService.updateByPrimaryKeySelective(record);

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("dispatch:del")
    @RequestMapping(value = "/dispatch_del_file", method = RequestMethod.POST)
    @ResponseBody
    public Map dispatch_del_file(Integer id, @RequestParam String type) {

        if (StringUtils.equalsIgnoreCase(type, "file")) {
            dispatchService.delFile(id);
        } else if (StringUtils.equalsIgnoreCase(type, "ppt")) {
            dispatchService.delPpt(id);
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("dispatch:del")
    @RequestMapping(value = "/dispatch_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_dispatch_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            dispatchService.del(id);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "删除发文：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("dispatch:del")
    @RequestMapping(value = "/dispatch_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length > 0) {
            dispatchService.batchDel(ids);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "批量删除发文：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("dispatch:changeOrder")
    @RequestMapping(value = "/dispatch_changeOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map do_dispatch_changeOrder(Integer id, Integer addNum, HttpServletRequest request) {

        dispatchService.changeOrder(id, addNum);
        logger.info(addLog(SystemConstants.LOG_ADMIN, "发文调序：%s,%s", id, addNum));
        return success(FormUtils.SUCCESS);
    }

    public void dispatch_export(DispatchViewExample example, HttpServletResponse response) {

        List<DispatchView> records = dispatchViewMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"年份", "发文类型", "发文号", "党委常委会日期", "发文日期", "任免日期", "任命人数", "录入任命人数",
                "免职人数", "录入免职人数", "是否全部录入", "是否复核", "备注"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            DispatchView record = records.get(i);
            int realAppointCount = record.getRealAppointCount() == null ? 0 : record.getRealAppointCount();
            int realDismissCount = record.getRealDismissCount() == null ? 0 : record.getRealDismissCount();
            int appointCount = record.getAppointCount() == null ? 0 : record.getAppointCount();
            int dismissCount = record.getDismissCount() == null ? 0 : record.getDismissCount();
            String[] values = {
                    record.getYear() + "",
                    record.getDispatchTypeId() == null ? "" : dispatchTypeService.findAll().get(record.getDispatchTypeId()).getName(),
                    CmTag.getDispatchCode(record.getCode(), record.getDispatchTypeId(), record.getYear()),
                    DateUtils.formatDate(record.getMeetingTime(), DateUtils.YYYY_MM_DD),
                    DateUtils.formatDate(record.getPubTime(), DateUtils.YYYY_MM_DD),
                    DateUtils.formatDate(record.getWorkTime(), DateUtils.YYYY_MM_DD),
                    appointCount + "",
                    realAppointCount + "",
                    dismissCount + "",
                    realDismissCount + "",
                    ((realAppointCount + realDismissCount) > 0
                            && appointCount == realAppointCount
                            && dismissCount == realDismissCount) ? "是" : "否",
                    record.getHasChecked() ? "已复核" : "否",
                    record.getRemark()
            };
            valuesList.add(values);
        }
        String fileName = "发文_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
        ExportHelper.export(titles, valuesList, fileName, response);
    }

    @RequestMapping("/dispatch_selects")
    @ResponseBody
    public Map dispatch_selects(Integer pageSize, Integer pageNo, Integer dispatchTypeId, String searchStr) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        DispatchExample example = new DispatchExample();
        Criteria criteria = example.createCriteria();
        if (dispatchTypeId != null)
            criteria.andDispatchTypeIdEqualTo(dispatchTypeId);

        example.setOrderByClause("sort_order desc");

        if (StringUtils.isNotBlank(searchStr)) {
            criteria.andCodeEqualTo(Integer.parseInt(searchStr));
        }

        int count = dispatchMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<Dispatch> dispatchs = dispatchMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));

        Map<Integer, DispatchType> dispatchTypeMap = dispatchTypeService.findAll();
        List<Map<String, Object>> options = new ArrayList<>();
        if (null != dispatchs && dispatchs.size() > 0) {

            for (Dispatch dispatch : dispatchs) {
                Map<String, Object> option = new HashMap<>();
                option.put("text", CmTag.getDispatchCode(dispatch.getCode(), dispatch.getDispatchTypeId(), dispatch.getYear()));
                option.put("id", dispatch.getId());
                option.put("year", dispatch.getYear());
                option.put("type", dispatchTypeMap.get(dispatch.getDispatchTypeId()).getName());

                options.add(option);
            }
        }

        Map resultMap = success();
        resultMap.put("totalCount", count);
        resultMap.put("options", options);
        return resultMap;
    }
}
