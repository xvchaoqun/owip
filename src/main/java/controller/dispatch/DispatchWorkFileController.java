package controller.dispatch;

import domain.base.MetaType;
import domain.cadre.CadreView;
import domain.dispatch.DispatchWorkFile;
import domain.dispatch.DispatchWorkFileExample;
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
import shiro.ShiroHelper;
import sys.constants.RoleConstants;
import sys.constants.SystemConstants;
import sys.tool.paging.CommonList;
import sys.tool.tree.TreeNode;
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
import java.util.Set;

@Controller
public class DispatchWorkFileController extends DispatchBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("dispatchWorkFile:list")
    @RequestMapping("/dispatchWorkFile")
    public String dispatchWorkFile_page(@RequestParam(required = false, defaultValue = "1") Boolean status,
                                        @RequestParam(required = false, value = "unitTypes")Integer[] unitTypes,
                                        @RequestParam(required = false, value = "workTypes")Integer[] workTypes,
                                        @RequestParam(required = false, value = "privacyTypes")Integer[] privacyTypes,
                                        ModelMap modelMap) {

        modelMap.put("status", status);

        if (unitTypes!=null) {
            modelMap.put("selectUnitTypes", Arrays.asList(unitTypes));
        }

        if (workTypes!=null) {
            modelMap.put("selectWorkTypes", Arrays.asList(workTypes));
        }

        if (privacyTypes!=null) {
            modelMap.put("selectPrivacyTypes", Arrays.asList(privacyTypes));
        }

        return "dispatch/dispatchWorkFile/dispatchWorkFile_page";
    }

    @RequiresPermissions("dispatchWorkFile:list")
    @RequestMapping("/dispatchWorkFile_data")
    public void dispatchWorkFile_data(HttpServletResponse response,
                                      Byte type,
                                      @RequestParam(required = false, defaultValue = "1") Boolean status,
                                      Integer startYear,
                                      Integer endYear,
                                      @RequestParam(required = false, value = "unitTypes")Integer[] unitTypes,
                                      @RequestParam(required = false, value = "workTypes")Integer[] workTypes,
                                      @RequestParam(required = false, value = "privacyTypes")Integer[] privacyTypes,
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

        boolean isAdmin = ShiroHelper.hasAnyRoles(RoleConstants.ROLE_ADMIN, RoleConstants.ROLE_CADREADMIN);
        List<Integer> postIds = new ArrayList<>();
        if(!isAdmin){
            CadreView cadreView = cadreService.dbFindByUserId(ShiroHelper.getCurrentUserId());
            if(cadreView!=null) postIds.add(cadreView.getPostId());
        }

        long count = iDispatchMapper.countDispatchWorkFileList(isAdmin, postIds, type, status,
                unitTypes, startYear, endYear, workTypes, privacyTypes);

        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<DispatchWorkFile> records = iDispatchMapper.selectDispatchWorkFileList(isAdmin, postIds, type, status,
                unitTypes, startYear, endYear, workTypes, privacyTypes, new RowBounds((pageNo - 1) * pageSize, pageSize));

        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(dispatchWorkFile.class, dispatchWorkFileMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
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
                                      HttpServletRequest request) throws IOException, InterruptedException {

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

        if (canUpload) {
            record.setPdfFilePath(uploadPdf(_pdfFilePath, "dispatch_work_file"));
            record.setWordFilePath(upload(_wordFilePath, "dispatch_work_file"));
        }else{
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
