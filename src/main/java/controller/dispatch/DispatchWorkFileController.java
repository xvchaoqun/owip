package controller.dispatch;

import controller.BaseController;
import domain.base.MetaType;
import domain.cadre.CadreView;
import domain.dispatch.DispatchWorkFile;
import domain.dispatch.DispatchWorkFileExample;
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
import shiro.ShiroHelper;
import sys.constants.SystemConstants;
import sys.tool.paging.CommonList;
import sys.tool.tree.TreeNode;
import sys.utils.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.*;

@Controller
public class DispatchWorkFileController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("dispatchWorkFile:list")
    @RequestMapping("/dispatchWorkFile")
    public String dispatchWorkFile_page(@RequestParam(required = false, defaultValue = "1") Boolean status, ModelMap modelMap) {

        modelMap.put("status", status);

        return "dispatch/dispatchWorkFile/dispatchWorkFile_page";
    }

    @RequiresPermissions("dispatchWorkFile:list")
    @RequestMapping("/dispatchWorkFile_data")
    public void dispatchWorkFile_data(HttpServletResponse response,
                                      Byte type,
                                      @RequestParam(required = false, defaultValue = "1") Boolean status,
                                      Integer unitType,
                                      Integer year,
                                      Integer workType,
                                      Integer privacyType,
                                      @RequestParam(required = false, defaultValue = "0") int export,
                                      @RequestParam(required = false, value = "ids[]") Integer[] ids, // 导出的记录
                                      Integer pageSize, Integer pageNo) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);


        /*if (export == 1) {
            if (ids != null && ids.length > 0)
                criteria.andIdIn(Arrays.asList(ids));
            dispatchWorkFile_export(example, response);
            return;
        }*/

        boolean isAdmin = ShiroHelper.hasAnyRoles(SystemConstants.ROLE_ADMIN, SystemConstants.ROLE_CADREADMIN);
        List<Integer> postIds = new ArrayList<>();
        if(!isAdmin){
            CadreView cadreView = cadreService.dbFindByUserId(ShiroHelper.getCurrentUserId());
            postIds.add(cadreView.getPostId());
        }

        long count = iDispatchMapper.countDispatchWorkFiles(isAdmin, postIds, type, status,
                unitType, year, workType, privacyType);
        ;
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<DispatchWorkFile> records = iDispatchMapper.findDispatchWorkFiles(isAdmin, postIds, type, status,
                unitType, year, workType, privacyType, new RowBounds((pageNo - 1) * pageSize, pageSize));

        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> sourceMixins = sourceMixins();
        //sourceMixins.put(dispatchWorkFile.class, dispatchWorkFileMixin.class);
        JSONUtils.jsonp(resultMap, sourceMixins);
        return;
    }

    // 查看文件对应的查看权限（职务属性）
    @RequiresPermissions("dispatchWorkFile:auth")
    @RequestMapping("/dispatchWorkFileAuth")
    public String dispatchWorkFileAuth(int id, ModelMap modelMap) throws IOException {

        DispatchWorkFile dispatchWorkFile = dispatchWorkFileMapper.selectByPrimaryKey(id);
        modelMap.put("dispatchWorkFile", dispatchWorkFile);

        return "dispatch/dispatchWorkFile/dispatchWorkFileAuth";
    }

    // 更新某类申请人身份下的干部
    @RequiresPermissions("dispatchWorkFile:auth")
    @RequestMapping(value = "/dispatchWorkFileAuth", method = RequestMethod.POST)
    @ResponseBody
    public Map do_select_posts(Integer id, @RequestParam(value = "postIds[]", required = false) Integer[] postIds) {

        dispatchWorkFileService.updatePostIds(id, postIds);
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("dispatchWorkFile:auth")
    @RequestMapping("/dispatchWorkFile_selectPosts_tree")
    @ResponseBody
    public Map dispatchWorkFile_selectPosts_tree(int id) throws IOException {

        Set<Integer> postIds = dispatchWorkFileService.getPostIds(id);
        TreeNode tree = dispatchWorkFileService.getPostTree(postIds);

        Map<String, Object> resultMap = success();
        resultMap.put("tree", tree);
        return resultMap;
    }

    @RequiresPermissions("dispatchWorkFile:edit")
    @RequestMapping(value = "/dispatchWorkFile_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_dispatchWorkFile_au(DispatchWorkFile record,
                                      MultipartFile _pdfFilePath,
                                      MultipartFile _wordFilePath,
                                      HttpServletRequest request) {

        Integer id = record.getId();

        if (dispatchWorkFileService.idDuplicate(id, record.getCode())) {
            return failed("添加重复");
        }

        boolean canUpload = false;
        Integer privacyType = record.getPrivacyType();
        if (privacyType != null) {
            MetaType _privacyType = metaTypeService.findAll().get(privacyType);
            if (_privacyType != null && _privacyType.getBoolAttr()) {
                canUpload = true;
            }
        }

        if (canUpload && _pdfFilePath != null) {
            String ext = FileUtils.getExtention(_pdfFilePath.getOriginalFilename());
            if (!StringUtils.equalsIgnoreCase(ext, ".pdf")) {
                throw new RuntimeException("文件格式错误，请上传pdf文件");
            }

            String fileName = UUID.randomUUID().toString();
            String realPath = FILE_SEPARATOR
                    + "dispatch_work_file" + FILE_SEPARATOR
                    + DateUtils.formatDate(new Date(), "yyyyMM") + FILE_SEPARATOR
                    + fileName;
            String savePath = realPath + ext;
            FileUtils.copyFile(_pdfFilePath, new File(springProps.uploadPath + savePath));

            try {
                String swfPath = realPath + ".swf";
                pdf2Swf(savePath, swfPath);
            } catch (IOException | InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            record.setPdfFilePath(savePath);
        }

        if (canUpload && _wordFilePath != null) {
            String ext = FileUtils.getExtention(_wordFilePath.getOriginalFilename());
            if (!StringUtils.equalsIgnoreCase(ext, ".doc") && !StringUtils.equalsIgnoreCase(ext, ".docx")) {
                throw new RuntimeException("文件格式错误，请上传word文件");
            }

            String fileName = UUID.randomUUID().toString();
            String realPath = FILE_SEPARATOR
                    + "dispatch_work_file" + FILE_SEPARATOR
                    + DateUtils.formatDate(new Date(), "yyyyMM") + FILE_SEPARATOR
                    + fileName;
            String savePath = realPath + ext;
            FileUtils.copyFile(_wordFilePath, new File(springProps.uploadPath + savePath));

            record.setWordFilePath(savePath);
        }

        if (!canUpload){
            record.setPdfFilePath(null);
            record.setWordFilePath(null);
        }

        if (id == null) {

            dispatchWorkFileService.insertSelective(record);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "添加干部工作文件：%s", record.getId()));
        } else {

            record.setType(null);
            record.setStatus(null);
            dispatchWorkFileService.updateByPrimaryKeySelective(record, canUpload);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "更新干部工作文件：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("dispatchWorkFile:edit")
    @RequestMapping("/dispatchWorkFile_au")
    public String dispatchWorkFile_au(Integer id,
                                      Byte type,
                                      ModelMap modelMap) {

        if (id != null) {
            DispatchWorkFile dispatchWorkFile = dispatchWorkFileMapper.selectByPrimaryKey(id);
            if (dispatchWorkFile != null) {
                type = dispatchWorkFile.getType();
            }
            modelMap.put("dispatchWorkFile", dispatchWorkFile);
        }

        modelMap.put("type", type);

        return "dispatch/dispatchWorkFile/dispatchWorkFile_au";
    }

    @RequiresPermissions("dispatchWorkFile:del")
    @RequestMapping(value = "/dispatchWorkFile_abolish", method = RequestMethod.POST)
    @ResponseBody
    public Map dispatchWorkFile_abolish(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length > 0) {
            dispatchWorkFileService.abolish(ids);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "批量作废干部工作文件：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    //批量转移
    @RequiresPermissions("dispatchWorkFile:edit")
    @RequestMapping(value = "/dispatchWorkFile_transfer")
    public String dispatchWorkFile_transfer(){

        return "dispatch/dispatchWorkFile/dispatchWorkFile_transfer";
    }
    @RequiresPermissions("dispatchWorkFile:edit")
    @RequestMapping(value = "/dispatchWorkFile_transfer", method = RequestMethod.POST)
    @ResponseBody
    public Map do_dispatchWorkFile_transfer(@RequestParam(value = "ids[]") Integer[] ids, byte type ) {

        dispatchWorkFileService.batchTransfer(ids, type);
        logger.info(addLog(SystemConstants.LOG_ADMIN, "批量转移干部工作文件：%s", StringUtils.join(ids, ",")));
        return success();
    }

    @RequiresPermissions("dispatchWorkFile:del")
    @RequestMapping(value = "/dispatchWorkFile_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length > 0) {
            dispatchWorkFileService.batchDel(ids);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "批量删除干部工作文件：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("dispatchWorkFile:changeOrder")
    @RequestMapping(value = "/dispatchWorkFile_changeOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map do_dispatchWorkFile_changeOrder(Integer id, Integer addNum, HttpServletRequest request) {

        dispatchWorkFileService.changeOrder(id, addNum);
        logger.info(addLog(SystemConstants.LOG_ADMIN, "干部工作文件调序：%s,%s", id, addNum));
        return success(FormUtils.SUCCESS);
    }

    public void dispatchWorkFile_export(DispatchWorkFileExample example, HttpServletResponse response) {

        List<DispatchWorkFile> records = dispatchWorkFileMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"发文单位", "年度", "所属专项工作", "排序", "发文号", "发文日期", "文件名", "保密级别", "备注"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            DispatchWorkFile record = records.get(i);
            String[] values = {
                    record.getUnitType() + "",
                    record.getYear() + "",
                    record.getWorkType() + "",
                    record.getSortOrder() + "",
                    record.getCode(),
                    DateUtils.formatDate(record.getPubDate(), DateUtils.YYYY_MM_DD),
                    record.getFileName(),
                    record.getPrivacyType() + "",
                    record.getRemark()
            };
            valuesList.add(values);
        }
        String fileName = "干部工作文件_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
        ExportHelper.export(titles, valuesList, fileName, response);
    }
}
