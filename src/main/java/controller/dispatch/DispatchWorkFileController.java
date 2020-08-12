package controller.dispatch;

import domain.base.MetaType;
import domain.cadre.CadreView;
import domain.dispatch.DispatchWorkFile;
import domain.dispatch.DispatchWorkFileExample;
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
import shiro.ShiroHelper;
import sys.constants.LogConstants;
import sys.constants.RoleConstants;
import sys.tags.CmTag;
import sys.tool.paging.CommonList;
import sys.tool.tree.TreeNode;
import sys.utils.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

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
                                      String fileName,
                                      Integer type,
                                      @RequestParam(required = false, defaultValue = "1") Boolean status,
                                      Integer startYear,
                                      Integer endYear,
                                      @RequestParam(required = false, value = "unitTypes")Integer[] unitTypes,
                                      @RequestParam(required = false, value = "workTypes")Integer[] workTypes,
                                      @RequestParam(required = false, value = "privacyTypes")Integer[] privacyTypes,
                                      @RequestParam(required = false, defaultValue = "0") int export,
                                      Integer[] ids, // 导出的记录
                                      Integer pageSize, Integer pageNo) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        boolean isAdmin = ShiroHelper.hasAnyRoles(RoleConstants.ROLE_ADMIN, RoleConstants.ROLE_CADREADMIN);
        List<Integer> postTypes = new ArrayList<>();
        if(!isAdmin){
            CadreView cadreView = cadreService.dbFindByUserId(ShiroHelper.getCurrentUserId());
            if(cadreView!=null) postTypes.add(cadreView.getPostType());
        }

        long count = iDispatchMapper.countDispatchWorkFileList(isAdmin, fileName, postTypes, type, status,
                unitTypes, startYear, endYear, workTypes, privacyTypes);

        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<DispatchWorkFile> records = iDispatchMapper.selectDispatchWorkFileList(isAdmin, fileName, postTypes, type, status,
                unitTypes, startYear, endYear, workTypes, privacyTypes, new RowBounds((pageNo - 1) * pageSize, pageSize));

        if (export == 1) {

            dispatchWorkFile_export(records,type,response);
            return;
        }

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
    @RequiresPermissions("dispatchWorkFile:list")
    @RequestMapping("/dispatchWorkFileAuth")
    public String dispatchWorkFileAuth(int id, ModelMap modelMap) throws IOException {

        DispatchWorkFile dispatchWorkFile = dispatchWorkFileMapper.selectByPrimaryKey(id);
        modelMap.put("dispatchWorkFile", dispatchWorkFile);

        return "dispatch/dispatchWorkFile/dispatchWorkFileAuth";
    }

    // 查询文件所属类型
    @RequiresPermissions("dispatchWorkFile:list")
    @RequestMapping("/dispatchWorkFile_search")
    public String dispatchWorkFile_search() throws IOException {

        return "dispatch/dispatchWorkFile/dispatchWorkFile_search";
    }

    @RequiresPermissions("dispatchWorkFile:list")
    @RequestMapping(value = "/dispatchWorkFile_search", method = RequestMethod.POST)
    @ResponseBody
    public Map do_dispatchWorkFile_search(String fileName) {

        DispatchWorkFileExample example = new DispatchWorkFileExample();
        example.createCriteria().andFileNameLike('%'+fileName.trim()+"%");
        example.setOrderByClause("type asc, status desc, sort_order desc");
        List<DispatchWorkFile> dispatchWorkFiles = dispatchWorkFileMapper.selectByExample(example);

        Map<String, Object> resultMap = success(FormUtils.SUCCESS);
        resultMap.put("dispatchWorkFiles", dispatchWorkFiles);
        return resultMap;
    }

    // 更新某类申请人身份下的干部
    @RequiresPermissions("dispatchWorkFile:auth")
    @RequestMapping(value = "/dispatchWorkFileAuth", method = RequestMethod.POST)
    @ResponseBody
    public Map do_dispatchWorkFileAuth(Integer id, Integer[] postTypes) {

        dispatchWorkFileService.updatePostTypes(id, postTypes);
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

    // for test
    /*@RequiresPermissions("dispatchWorkFile:edit")
    @RequestMapping(value = "/dispatchWorkFile_asyncPdf2jpg")
    @ResponseBody
    public Map dispatchWorkFile_asyncPdf2jpg(){
        
        List<DispatchWorkFile> dispatchWorkFiles = dispatchWorkFileMapper.selectByExample(new DispatchWorkFileExample());
        int i=0;
        for (DispatchWorkFile dispatchWorkFile : dispatchWorkFiles) {
    
            String pdfFilePath = dispatchWorkFile.getPdfFilePath();
            if(StringUtils.isBlank(pdfFilePath)) continue;
            if(FileUtils.exists(springProps.uploadPath + pdfFilePath+".jpg"))
                continue;
             // 异步pdf转图片
            cacheService.asyncPdf2jpg(pdfFilePath, null);
            i++;
        }
        return success(i+"add");
    }*/
    
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
            if (_privacyType != null && BooleanUtils.isTrue(_privacyType.getBoolAttr())) {
                canUpload = true;
            }
        }

        if (canUpload) {
            record.setPdfFilePath(uploadPdf(_pdfFilePath, "dispatch_work_file"));
            // 异步pdf转图片 第一页
            cacheService.asyncPdf2jpg(record.getPdfFilePath(), 1);
            
            record.setWordFilePath(upload(_wordFilePath, "dispatch_work_file"));
        }else{
            record.setPdfFilePath(null);
            record.setWordFilePath(null);
        }

        if (id == null) {

            dispatchWorkFileService.insertSelective(record);
            logger.info(addLog(LogConstants.LOG_ADMIN, "添加干部工作文件：%s", record.getId()));
        } else {

            record.setType(null);
            record.setStatus(null);
            dispatchWorkFileService.updateByPrimaryKeySelective(record, canUpload);
            logger.info(addLog(LogConstants.LOG_ADMIN, "更新干部工作文件：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("dispatchWorkFile:edit")
    @RequestMapping("/dispatchWorkFile_au")
    public String dispatchWorkFile_au(Integer id,
                                      Integer type,
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
    public Map dispatchWorkFile_abolish(HttpServletRequest request, Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length > 0) {
            dispatchWorkFileService.abolish(ids);
            logger.info(addLog(LogConstants.LOG_ADMIN, "批量作废干部工作文件：%s", StringUtils.join(ids, ",")));
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
    public Map do_dispatchWorkFile_transfer(Integer[] ids, int type ) {

        dispatchWorkFileService.batchTransfer(ids, type);
        logger.info(addLog(LogConstants.LOG_ADMIN, "批量转移干部工作文件：%s", StringUtils.join(ids, ",")));
        return success();
    }

    @RequiresPermissions("dispatchWorkFile:del")
    @RequestMapping(value = "/dispatchWorkFile_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length > 0) {
            dispatchWorkFileService.batchDel(ids);
            logger.info(addLog(LogConstants.LOG_ADMIN, "批量删除干部工作文件：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("dispatchWorkFile:changeOrder")
    @RequestMapping(value = "/dispatchWorkFile_changeOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map do_dispatchWorkFile_changeOrder(Integer id, Integer addNum, HttpServletRequest request) {

        dispatchWorkFileService.changeOrder(id, addNum);
        logger.info(addLog(LogConstants.LOG_ADMIN, "干部工作文件调序：%s,%s", id, addNum));
        return success(FormUtils.SUCCESS);
    }

    public void dispatchWorkFile_export(List<DispatchWorkFile> records,Integer type, HttpServletResponse response) {

        int rownum = records.size();
        String[] titles = {"文件名|350","发文单位|200","发文号|200", "发文日期|200","年度", "所属专项工作|200","保密级别|200", "备注|350"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            DispatchWorkFile record = records.get(i);
            String[] values = {
                    record.getFileName(),
                    record.getUnitType()!=null? CmTag.getMetaType(record.getUnitType()).getName():"",
                    record.getCode(),
                    DateUtils.formatDate(record.getPubDate(), DateUtils.YYYY_MM_DD),
                    record.getYear() + "",
                    record.getWorkType()!=null?CmTag.getMetaType(record.getWorkType()).getName():"",
                    record.getPrivacyType()!=null?CmTag.getMetaType(record.getPrivacyType()).getName():"" + "",
                    record.getRemark()
            };
            valuesList.add(values);
        }
        String fileName = type!=null?CmTag.getMetaType(type).getName():""+ "_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
        ExportHelper.export(titles, valuesList, fileName, response);
    }

    @RequestMapping("/dispatchWorkFile_selects")
    @ResponseBody
    public Map dispatchWorkFile_selects(Integer pageSize, Integer pageNo, String searchStr) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        DispatchWorkFileExample example = new DispatchWorkFileExample();
        DispatchWorkFileExample.Criteria criteria = example.createCriteria();
        criteria.andStatusEqualTo(true);
        example.setOrderByClause("sort_order desc");

        if(StringUtils.isNotBlank(searchStr)){
            criteria.andFileNameLike("%"+searchStr.trim()+"%");
        }

        long count = dispatchWorkFileMapper.countByExample(example);
        if((pageNo-1)*pageSize >= count){

            pageNo = Math.max(1, pageNo-1);
        }
        List<DispatchWorkFile> records = dispatchWorkFileMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo-1)*pageSize, pageSize));

        List<Map<String, Object> > options = new ArrayList<>();
        if(null != records && records.size()>0){
            for(DispatchWorkFile record:records){
                Map<String, Object> option = new HashMap<>();
                option.put("text", record.getFileName());
                option.put("del", !record.getStatus());
                option.put("id", record.getId());
                option.put("type", record.getType());
                options.add(option);
            }
        }

        Map resultMap = success();
        resultMap.put("totalCount", count);
        resultMap.put("options", options);
        return resultMap;
    }
}
