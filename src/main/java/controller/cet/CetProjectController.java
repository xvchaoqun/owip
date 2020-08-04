package controller.cet;

import controller.global.OpException;
import domain.cet.CetProject;
import domain.cet.CetProjectExample;
import domain.cet.CetProjectType;
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
import sys.constants.CetConstants;
import sys.constants.LogConstants;
import sys.tool.paging.CommonList;
import sys.utils.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

@Controller
@RequestMapping("/cet")
public class CetProjectController extends CetBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("cetProject:list")
    @RequestMapping("/archiveProjectObj")
    @ResponseBody
    public Map archiveProjectObj( int projectId, int objId, ModelMap modelMap) {

        cetProjectObjService.archiveProjectObj(projectId, objId);
        
        return success();
    }
    
    @RequiresPermissions("cetProject:list")
    @RequestMapping("/archiveProject")
    @ResponseBody
    public Map archiveProject( int projectId, ModelMap modelMap) {

        cetProjectObjService.archiveProject(projectId);
        
        return success();
    }
    
    @RequiresPermissions("cetProject:list")
    @RequestMapping("/cetProject")
    public String cetProject(@RequestParam(defaultValue = "1") Integer cls, // 1：正常  2： 已删除
                             byte type,
                             ModelMap modelMap) {

        modelMap.put("cls", cls);
        modelMap.put("type", type);

        Map<Integer, CetProjectType> cetProjectTypeMap = cetProjectTypeService.findAll(type);
        modelMap.put("cetProjectTypeMap", cetProjectTypeMap);

        return "cet/cetProject/cetProject_page";
    }

    @RequiresPermissions("cetProject:list")
    @RequestMapping("/cetProject_data")
    public void cetProject_data(HttpServletResponse response,
                                @RequestParam(defaultValue = "1") Integer cls, // 1：正常  2： 已删除
                                byte type,
                                Integer year,
                                String name,
                                Integer projectTypeId,
                                BigDecimal prePeriod,
                                BigDecimal subPeriod,
                                Integer objCount,
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

        CetProjectExample example = new CetProjectExample();
        CetProjectExample.Criteria criteria = example.createCriteria().andTypeEqualTo(type);
        example.setOrderByClause("year desc, id desc");

        if(cls==1){
            criteria.andIsDeletedEqualTo(false);
        }else if(cls==2){
            criteria.andIsDeletedEqualTo(true);
        }else {
            criteria.andIdIsNull();
        }

        if (year!=null) {
            criteria.andYearEqualTo(year);
        }
        if (StringUtils.isNotBlank(name)) {
            criteria.andNameLike(SqlUtils.like(name));
        }
        if (projectTypeId != null) {
            criteria.andProjectTypeIdEqualTo(projectTypeId);
        }
        if (objCount != null) {
            criteria.andObjCountEqualTo(objCount);
        }
        if (prePeriod != null) {
            criteria.andPeriodGreaterThanOrEqualTo(prePeriod);
        }
        if (subPeriod != null) {
            criteria.andPeriodLessThanOrEqualTo(subPeriod);
        }

        if (export == 1) {
            if(ids!=null && ids.length>0)
                criteria.andIdIn(Arrays.asList(ids));
            cetProject_export(example, type, response);
            return;
        }

        long count = cetProjectMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<CetProject> records= cetProjectMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(cetProject.class, cetProjectMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("cetProject:edit")
    @RequestMapping(value = "/cetProject_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cetProject_au(CetProject record,
                                @RequestParam(value = "_traineeTypeIds[]", required = false) Integer[] traineeTypeIds,
                                Integer otherTypeId,
                                String otherTraineeType,
                                MultipartFile _wordFilePath,
                                HttpServletRequest request) throws IOException, InterruptedException {

        Integer id = record.getId();

        if(record.getStartDate()!=null && record.getEndDate()!=null
                && record.getStartDate().after(record.getEndDate())){
            return failed("培训时间有误。");
        }

        if((traineeTypeIds==null || traineeTypeIds.length==0) && otherTypeId == null){
            return failed("请选择参训人员类型。");
        }

        List<Integer> _traineeTypeIdList = new ArrayList<>();
        List<Integer> traineeTypeIdList = new ArrayList<>();
        if (traineeTypeIds != null) {
            _traineeTypeIdList = Arrays.asList(traineeTypeIds);
            traineeTypeIdList.addAll(_traineeTypeIdList);
        }
        if (otherTypeId != null){
            traineeTypeIdList.add(otherTypeId);
            record.setOtherTraineeType(otherTraineeType);
        }else {
            record.setOtherTraineeType("");
        }

        record.setWordFilePath(upload(_wordFilePath, "cet_project"));
        record.setIsValid(BooleanUtils.isTrue(record.getIsValid()));
        if (id == null) {
            cetProjectService.insertSelective(record, traineeTypeIdList);
            logger.info(addLog(LogConstants.LOG_CET, "添加培训项目：%s", record.getId()));
        } else {
            // 不改变培训类型
            record.setType(null);
            cetProjectService.updateWithTraineeTypes(record, traineeTypeIdList);
            logger.info(addLog(LogConstants.LOG_CET, "更新培训项目：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cetProject:edit")
    @RequestMapping(value = "/cetProject_upload", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cetProject_upload(MultipartFile file) throws InterruptedException, IOException {

        String originalFilename = file.getOriginalFilename();
        String ext = FileUtils.getExtention(originalFilename);
        if (!StringUtils.equalsIgnoreCase(ext, ".pdf")
                && !ContentTypeUtils.isFormat(file, "pdf")) {
            throw new OpException("文件格式错误，请上传pdf文件");
        }

        String savePath = uploadPdf(file, "cet_project");

        Map<String, Object> resultMap = success();
        resultMap.put("fileName", FileUtils.getFileName(file.getOriginalFilename()));
        resultMap.put("pdfFilePath", savePath);

        return resultMap;
    }

    @RequiresPermissions("cetProject:edit")
    @RequestMapping("/cetProject_au")
    public String cetProject_au(Integer id,
                                Byte _type,
                                ModelMap modelMap) {

        if (id != null) {
            CetProject cetProject = cetProjectMapper.selectByPrimaryKey(id);
            modelMap.put("cetProject", cetProject);
            if(cetProject!=null){
                _type = cetProject.getType();
            }
            Set<Integer> traineeTypeIdSet = cetProjectService.findTraineeTypeIdSet(id);
            modelMap.put("traineeTypeIds", new ArrayList<>(traineeTypeIdSet));
        }

        Map<Integer, CetProjectType> cetProjectTypeMap = cetProjectTypeService.findAll(_type);
        modelMap.put("cetProjectTypes", cetProjectTypeMap.values());

        modelMap.put("type", _type);

        return "cet/cetProject/cetProject_au";
    }

    @RequiresPermissions("cetProject:del")
    @RequestMapping(value = "/cetProject_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map cetProject_batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            cetProjectService.batchDel(ids);
            logger.info(addLog(LogConstants.LOG_CET, "批量删除培训项目：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }
    
    public void cetProject_export(CetProjectExample example, Byte type, HttpServletResponse response) {

        List<CetProject> records = cetProjectMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"年度|50","培训时间（开始）|100","培训时间（结束）|100","培训班名称|200","培训班类型|100","总学时|100","参训人数|100","是否计入年度学习任务|100","备注|100"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            CetProject record = records.get(i);
            String[] values = {
                    record.getYear()+"",
                    DateUtils.formatDate(record.getStartDate(), DateUtils.YYYYMMDD_DOT),
                    DateUtils.formatDate(record.getEndDate(), DateUtils.YYYYMMDD_DOT),
                    record.getName(),
                    cetProjectTypeMapper.selectByPrimaryKey(record.getProjectTypeId()).getName(),
                    record.getPeriod()==null?"--":(""+record.getPeriod()),
                    record.getObjCount() + "",
                    record.getIsValid() ? "是" : "否",
                    record.getRemark()
            };
            valuesList.add(values);
        }
        String fileName = CetConstants.CET_PROJECT_TYPE_MAP.get(type);
        ExportHelper.export(titles, valuesList, fileName, response);
    }
}
