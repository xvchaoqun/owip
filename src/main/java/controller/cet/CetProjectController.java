package controller.cet;

import controller.global.OpException;
import domain.base.ContentTpl;
import domain.cet.CetProject;
import domain.cet.CetProjectExample;
import domain.cet.CetProjectType;
import mixin.MixinUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import shiro.ShiroHelper;
import sys.constants.CetConstants;
import sys.constants.ContentTplConstants;
import sys.constants.LogConstants;
import sys.constants.RoleConstants;
import sys.spring.UserRes;
import sys.spring.UserResUtils;
import sys.tags.CmTag;
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

        iCetMapper.refreshProjectObjs(projectId);

        cetProjectObjService.archiveProject(projectId);

        cetRecordService.syncAllProjectObj(projectId);

        return success();
    }
    
    @RequiresPermissions("cetProject:list")
    @RequestMapping("/cetProject")
    public String cetProject(
                            // 1 党校专题培训 2党校日常培训 3二级党委专题培训 4 二级党委日常培训
                            @RequestParam(defaultValue = "1") byte cls,
                            Byte status, // 二级党委报送状态
                             ModelMap modelMap) {

        modelMap.put("cls", cls);

        Map<Byte, Integer> statusCountMap = new HashMap<>();
        if(cls==3||cls==4){

            byte type = CetConstants.CET_PROJECT_TYPE_SPECIAL;
            if(cls==4) {
                type = CetConstants.CET_PROJECT_TYPE_DAILY;
            }
            boolean addPermits = ShiroHelper.lackRole(RoleConstants.ROLE_CET_ADMIN);
            List<Integer> adminPartyIdList = new ArrayList<>();
            if(addPermits) {
                adminPartyIdList = iCetMapper.getAdminPartyIds(ShiroHelper.getCurrentUserId());
            }
            List<Map> mapList = iCetMapper.projectGroupByStatus(type, addPermits, adminPartyIdList);

            for (Map resultMap : mapList) {
                byte st = ((Integer) resultMap.get("status")).byteValue();
                int num = ((Long) resultMap.get("num")).intValue();
                statusCountMap.put(st, num);
            }

            modelMap.put("statusCountMap", statusCountMap);
        }

        if(status==null) {
            int passCount = NumberUtils.trimToZero(statusCountMap.get(CetConstants.CET_PROJECT_STATUS_PASS));
            int unReportCount = NumberUtils.trimToZero(statusCountMap.get(CetConstants.CET_PROJECT_STATUS_UNREPORT));
            if (passCount == 0 || unReportCount > 0) {
                status = CetConstants.CET_PROJECT_STATUS_UNREPORT;
            } else {
                status = CetConstants.CET_PROJECT_STATUS_PASS;
            }
        }

        modelMap.put("status", status);

        Map<Integer, CetProjectType> cetProjectTypeMap = cetProjectTypeService.findAll(cls);
        modelMap.put("cetProjectTypeMap", cetProjectTypeMap);

        return "cet/cetProject/cetProject_page";
    }

    @RequiresPermissions("cetProject:list")
    @RequestMapping("/cetProject_data")
    public void cetProject_data(HttpServletResponse response,
                                // 1 党校专题培训 2党校日常培训 3二级党委专题培训 4 二级党委日常培训
                                @RequestParam(defaultValue = "1") byte cls,
                                Byte status, // 二级党委报送状态
                                Integer year,
                                String name,
                                Integer projectTypeId,
                                BigDecimal prePeriod,
                                BigDecimal subPeriod,
                                Integer objCount,
                                @RequestParam(required = false, defaultValue = "0") int export,
                                Integer[] ids, // 导出的记录
                                Integer pageSize, Integer pageNo)  throws IOException{

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        boolean isPartyProject = (cls==3 || cls==4);
        byte type = CetConstants.CET_PROJECT_TYPE_SPECIAL;
        if(cls==2 || cls==4) {
            type = CetConstants.CET_PROJECT_TYPE_DAILY;
        }

        CetProjectExample example = new CetProjectExample();
        CetProjectExample.Criteria criteria = example.createCriteria()
                .andIsPartyProjectEqualTo(isPartyProject).andTypeEqualTo(type).andIsDeletedEqualTo(false);
        example.setOrderByClause("year desc, id desc");

        if(ShiroHelper.lackRole(RoleConstants.ROLE_CET_ADMIN)) {
            if (isPartyProject) {

                List<Integer> adminPartyIdList = iCetMapper.getAdminPartyIds(ShiroHelper.getCurrentUserId());
                if (adminPartyIdList.size() == 0) {
                    criteria.andIdIsNull();
                } else {
                    criteria.andCetPartyIdIn(adminPartyIdList);
                }
            }else{
                criteria.andIdIsNull();
            }
        }
        if(isPartyProject){
            if(status==null) status=0;
            criteria.andStatusEqualTo(status);
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
                                // 1 党校专题培训 2党校日常培训 3二级党委专题培训 4 二级党委日常培训
                                @RequestParam(defaultValue = "1") byte cls,
                                Integer[] traineeTypeIds,
                                Integer otherTypeId,
                                String otherTraineeType,
                                MultipartFile _wordFilePath,
                                HttpServletRequest request) throws IOException, InterruptedException {

        boolean isPartyProject = (cls==3 || cls==4);
        byte type = CetConstants.CET_PROJECT_TYPE_SPECIAL;
        if(cls==2 || cls==4) {
            type = CetConstants.CET_PROJECT_TYPE_DAILY;
        }

        record.setIsPartyProject(isPartyProject);
        record.setType(type);

        if(isPartyProject && ShiroHelper.lackRole(RoleConstants.ROLE_CET_ADMIN)){

            List<Integer> adminPartyIdList = iCetMapper.getAdminPartyIds(ShiroHelper.getCurrentUserId());
            HashSet<Integer> adminPartyIdSet = new HashSet<>(adminPartyIdList);
            if(!adminPartyIdSet.contains(record.getCetPartyId())){

                throw new UnauthorizedException();
            }
        }

        Integer id = record.getId();

        if(record.getPdfFilePath()!=null) {
            UserRes resBean = UserResUtils.decode(record.getPdfFilePath());
            record.setPdfFilePath(resBean.getRes());
        }

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
    @RequestMapping("/cetProject_au")
    public String cetProject_au(Integer id,
                                byte cls,
                                ModelMap modelMap) {

        if (id != null) {
            CetProject cetProject = cetProjectMapper.selectByPrimaryKey(id);
            modelMap.put("cetProject", cetProject);
            if(cetProject!=null){

                modelMap.put("cetParty", cetPartyMapper.selectByPrimaryKey(cetProject.getCetPartyId()));
                modelMap.put("unit", unitService.findAll().get(cetProject.getUnitId()));
            }
            Set<Integer> traineeTypeIdSet = cetProjectService.findTraineeTypeIdSet(id);
            modelMap.put("traineeTypeIds", new ArrayList<>(traineeTypeIdSet));
        }

        Map<Integer, CetProjectType> cetProjectTypeMap = cetProjectTypeService.findAll(cls);
        modelMap.put("cetProjectTypes", cetProjectTypeMap.values());

        return "cet/cetProject/cetProject_au";
    }

        @RequiresPermissions("cetProject:edit")
    @RequestMapping(value = "/cetProject_upload", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cetProject_upload(MultipartFile file) throws InterruptedException, IOException {

        String originalFilename = file.getOriginalFilename();
        String ext = FileUtils.getExtention(originalFilename);
        if (!StringUtils.equalsIgnoreCase(ext, ".pdf")
                && !ContentTypeUtils.isAnyFormat(file, "pdf")) {
            throw new OpException("文件格式错误，请上传pdf文件");
        }

        String savePath = uploadPdf(file, "cet_project");

        Map<String, Object> resultMap = success();
        resultMap.put("fileName", FileUtils.getFileName(file.getOriginalFilename()));
        resultMap.put("pdfFilePath", UserResUtils.sign(savePath));

        return resultMap;
    }

    @RequiresPermissions("cetProject:edit")
    @RequestMapping("/cetProject_detail")
    public String cetProject_detail(Integer projectId, ModelMap modelMap) {

        if (projectId != null) {
            CetProject cetProject = cetProjectMapper.selectByPrimaryKey(projectId);
            modelMap.put("cetProject", cetProject);
        }
        return "cet/cetProject/cetProject_detail";
    }

    @RequiresPermissions("cetProject:edit")
    @RequestMapping("/cetProject_begin")
    public String cetProject_begin(int projectId, ModelMap modelMap) {

        CetProject cetProject = cetProjectMapper.selectByPrimaryKey(projectId);
        modelMap.put("cetProject", cetProject);

        List<ContentTpl> tplList = new ArrayList<>();
        tplList.add(CmTag.getContentTpl(ContentTplConstants.CONTENT_TPL_CET_MSG_1));
        modelMap.put("tplList", tplList);

        return "cet/cetProject/cetProject_begin";
    }

    @RequiresPermissions("cetProject:edit")
    @RequestMapping(value = "/cetProject_begin", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cetProject_begin(int projectId, @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")Date openTime,
                                          String openAddress) {

        CetProject record = new CetProject();
        record.setId(projectId);
        record.setOpenTime(openTime);
        record.setOpenAddress(openAddress);

        cetProjectMapper.updateByPrimaryKeySelective(record);

        logger.info(addLog(LogConstants.LOG_CET, "更新开班仪式设置：%s~%s",
                openTime, openAddress));

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cetProject:edit")
    @RequestMapping(value = "/cetProject_report", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cetProject_report(int id, ModelMap modelMap) {

        cetProjectService.report(id);
        logger.info(addLog(LogConstants.LOG_CET, "二级党委过程培训报送：%s", id));

        return success(FormUtils.SUCCESS);
    }

    @RequiresRoles(RoleConstants.ROLE_CET_ADMIN)
    @RequiresPermissions("cetProject:edit")
    @RequestMapping("/cetProject_check")
    public String cetProject_check(Integer[] ids, ModelMap modelMap) {

        if (ids != null && ids.length == 1)
            modelMap.put("cetProject", cetProjectMapper.selectByPrimaryKey(ids[0]));

        return "cet/cetProject/cetProject_check";
    }

    @RequiresRoles(RoleConstants.ROLE_CET_ADMIN)
    @RequiresPermissions("cetProject:edit")
    @RequestMapping(value = "/cetProject_check", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cetProject_check(HttpServletRequest request,
                                       Integer[] ids,
                                       Boolean pass, Boolean isValid, String backReason, ModelMap modelMap) {

        if (ids != null && ids.length > 0) {

            CetProject record = new CetProject();
            record.setStatus(BooleanUtils.isTrue(pass) ? CetConstants.CET_PROJECT_STATUS_PASS
                    : CetConstants.CET_PROJECT_STATUS_UNPASS);
            record.setBackReason(backReason);
            record.setIsValid(BooleanUtils.isTrue(isValid));

            CetProjectExample example = new CetProjectExample();
            example.createCriteria().andIdIn(Arrays.asList(ids))
                    .andStatusEqualTo(CetConstants.CET_PROJECT_STATUS_REPORT);
            cetProjectMapper.updateByExampleSelective(record, example);
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cetProject:edit")
    @RequestMapping(value = "/cetProject_back", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cetProject_back(HttpServletRequest request, Integer[] ids, ModelMap modelMap) {

        if (null != ids && ids.length > 0) {
            cetProjectService.back(ids);
            logger.info(addLog(LogConstants.LOG_CET, "返回报送二级党委过程培训：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cetProject:del")
    @RequestMapping(value = "/cetProject_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map cetProject_batchDel(HttpServletRequest request, Integer[] ids, ModelMap modelMap) {


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
