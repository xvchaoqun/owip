package controller.sc.scSubsidy;

import controller.global.OpException;
import controller.sc.ScBaseController;
import domain.base.AnnualType;
import domain.dispatch.Dispatch;
import domain.dispatch.DispatchExample;
import domain.sc.scSubsidy.ScSubsidy;
import domain.sc.scSubsidy.ScSubsidyExample;
import domain.sc.scSubsidy.ScSubsidyExample.Criteria;
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
import sys.constants.SystemConstants;
import sys.tool.paging.CommonList;
import sys.tool.tree.TreeNode;
import sys.utils.*;

import javax.persistence.criteria.CriteriaBuilder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("/sc")
public class ScSubsidyController extends ScBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("scSubsidy:list")
    @RequestMapping("/scSubsidy")
    public String scSubsidy(@RequestParam(required = false, defaultValue = "1") Byte cls,
                            Integer hrType,
                            Integer feType,
                            ModelMap modelMap) {
        modelMap.put("cls", cls);

        if(cls==2){
            return "forward:/sc/scSubsidyCadre";
        }else if(cls==3){
            return "forward:/sc/scSubsidyDispatch";
        }
        Map<Integer, AnnualType> annualTypeMap = annualTypeService.findAll(SystemConstants.ANNUAL_TYPE_MODULE_SUBSIDY);
        if(hrType!=null){
            modelMap.put("hrAnnualType", annualTypeMap.get(hrType));
        }
         if(feType!=null){
            modelMap.put("feAnnualType", annualTypeMap.get(feType));
        }
        return "sc/scSubsidy/scSubsidy/scSubsidy_page";
    }

    @RequiresPermissions("scSubsidy:list")
    @RequestMapping("/scSubsidy_data")
    @ResponseBody
    public void scSubsidy_data(HttpServletResponse response,
                                     Short year,
                                       Integer hrType,
                                       Integer hrNum,
                                 Integer feType,
                                    Integer feNum,
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

        ScSubsidyExample example = new ScSubsidyExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("id desc");

        if (year!=null) {
            criteria.andYearEqualTo(year);
        }
        if (hrType!=null) {
            criteria.andHrTypeEqualTo(hrType);
        }
        if (hrNum!=null) {
            criteria.andHrNumEqualTo(hrNum);
        }
        if (feType!=null) {
            criteria.andFeTypeEqualTo(feType);
        }
        if (feNum!=null) {
            criteria.andFeNumEqualTo(feNum);
        }

        if (export == 1) {
            if(ids!=null && ids.length>0)
                criteria.andIdIn(Arrays.asList(ids));
            scSubsidy_export(example, response);
            return;
        }

        long count = scSubsidyMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<ScSubsidy> records= scSubsidyMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(scSubsidy.class, scSubsidyMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("scSubsidy:edit")
    @RequestMapping(value = "/scSubsidy_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_scSubsidy_au(ScSubsidy record,
                               @RequestParam(value = "dispatchIds[]", required = false) Integer[] dispatchIds,
                               HttpServletRequest request) {

        if(dispatchIds==null || dispatchIds.length==0){
            return failed("请选择任免文件。");
        }

        scSubsidyService.insertOrUpdateSelective(record, dispatchIds);
        logger.info(addLog( LogConstants.LOG_SC_SUBSIDY, "添加/修改干部津贴变动：%s", record.getId()));

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("scSubsidy:edit")
    @RequestMapping("/scSubsidy_au")
    public String scSubsidy_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            ScSubsidy scSubsidy = scSubsidyMapper.selectByPrimaryKey(id);
            modelMap.put("scSubsidy", scSubsidy);
            Integer hrType = scSubsidy.getHrType();
            Integer feType = scSubsidy.getFeType();
            
            Map<Integer, AnnualType> annualTypeMap = annualTypeService.findAll(SystemConstants.ANNUAL_TYPE_MODULE_SUBSIDY);
            if(hrType!=null){
                modelMap.put("hrAnnualType", annualTypeMap.get(hrType));
            }
             if(feType!=null){
                modelMap.put("feAnnualType", annualTypeMap.get(feType));
            }
        }
        return "sc/scSubsidy/scSubsidy/scSubsidy_au";
    }

    @RequiresPermissions("scSubsidy:edit")
    @RequestMapping("/scSubsidy_selectDispatch_tree")
    @ResponseBody
    public Map scSubsidy_selectDispatch_tree(Integer year, Integer subsidyId, ModelMap modelMap) {


        DispatchExample example = new DispatchExample();
        example.createCriteria().andYearEqualTo(year);
        List<Dispatch> dispatches = dispatchMapper.selectByExample(example);

        List<Integer> scSubsidyDispatchIds = iScMapper.getScSubsidyDispatchIds(year);

        TreeNode tree = scSubsidyService.getDispatchTree(dispatches, subsidyId, new HashSet<>(scSubsidyDispatchIds));

        Map<String, Object> resultMap = success();
        resultMap.put("tree", tree);
        return resultMap;
    }

    @RequiresPermissions("scSubsidy:list")
    @RequestMapping("/scSubsidy_export")
    public void scSubsidy_export(Integer id, byte fileType, HttpServletResponse response) throws IOException {

        scSubsidyService.export(id, fileType, response);
    }

    @RequiresPermissions("scSubsidy:edit")
    @RequestMapping("/scSubsidy_upload")
    public String scSubsidy_upload(Integer id, ModelMap modelMap) {

        ScSubsidy scSubsidy = scSubsidyMapper.selectByPrimaryKey(id);
        modelMap.put("scSubsidy", scSubsidy);

        return "sc/scSubsidy/scSubsidy/scSubsidy_upload";
    }

    @RequiresPermissions("scSubsidy:edit")
    @RequestMapping(value = "/scSubsidy_upload", method = RequestMethod.POST)
    @ResponseBody
    public Map do_scSubsidy_upload(int id, byte fileType, MultipartFile file) throws InterruptedException, IOException {

        String originalFilename = file.getOriginalFilename();
        String ext = FileUtils.getExtention(originalFilename);
        if (!StringUtils.equalsIgnoreCase(ext, ".pdf")
                && !ContentTypeUtils.isFormat(file, "pdf")) {
            throw new OpException("文件格式错误，请上传pdf文件");
        }

        String savePath = uploadPdf(file, "scSubsidy");

        if(StringUtils.isNotBlank(savePath)){

            ScSubsidy record = new ScSubsidy();
            record.setId(id);
            if(fileType==1) {
                record.setHrFilePath(savePath);
            }else{
                record.setFeFilePath(savePath);
            }
            scSubsidyMapper.updateByPrimaryKeySelective(record);
        }

        Map<String, Object> resultMap = success();
        resultMap.put("fileName", file.getOriginalFilename());
        resultMap.put("file", savePath);

        return resultMap;
    }

    @RequiresPermissions("scSubsidy:del")
    @RequestMapping(value = "/scSubsidy_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map scSubsidy_batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            scSubsidyService.batchDel(ids);
            logger.info(addLog( LogConstants.LOG_SC_SUBSIDY, "批量删除干部津贴变动：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    public void scSubsidy_export(ScSubsidyExample example, HttpServletResponse response) {

        List<ScSubsidy> records = scSubsidyMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"年份|100","文号|100","通知日期|100","备注|100"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            ScSubsidy record = records.get(i);
            String[] values = {
                record.getYear()+"",
                            record.getHrType()+"",
                            DateUtils.formatDate(record.getInfoDate(), DateUtils.YYYY_MM_DD),
                            record.getRemark()
            };
            valuesList.add(values);
        }
        String fileName = "干部津贴变动_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
        ExportHelper.export(titles, valuesList, fileName, response);
    }

    @RequiresPermissions("scSubsidy:list")
    @RequestMapping("/scSubsidy_download")
    public void scSubsidy_download(Integer fileType, Integer id, HttpServletRequest request, HttpServletResponse response) throws IOException {

        ScSubsidy scSubsidy = scSubsidyMapper.selectByPrimaryKey(id);
        String path = fileType == 1? scSubsidy.getHrFilePath() : scSubsidy.getFeFilePath();
        String filename = fileType == 1? scSubsidy.getHrCode() : scSubsidy.getFeCode();

        DownloadUtils.download(request, response, springProps.uploadPath + path, filename);
    }
}
