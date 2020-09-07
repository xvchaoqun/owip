package controller.crs;

import controller.global.OpException;
import domain.base.MetaType;
import domain.crs.*;
import domain.crs.CrsPostExample.Criteria;
import domain.unit.Unit;
import domain.unit.UnitPost;
import mixin.MixinUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.usermodel.XSSFSheet;
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
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.util.HtmlUtils;
import shiro.ShiroHelper;
import sys.constants.CrsConstants;
import sys.constants.LogConstants;
import sys.constants.SystemConstants;
import sys.spring.DateRange;
import sys.spring.RequestDateRange;
import sys.tags.CmTag;
import sys.spring.UserResUtils;
import sys.tool.paging.CommonList;
import sys.utils.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Controller
public class CrsPostController extends CrsBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("crsPost:list")
    @RequestMapping("/crsPost")
    public String crsPost(@RequestParam(required = false, defaultValue = "1") Byte status,
                          Integer expertUserId,
                          ModelMap modelMap) {

        if (expertUserId != null) {
            modelMap.put("sysUser", sysUserService.findById(expertUserId));
        }
        modelMap.put("status", status);

        return "crs/crsPost/crsPost_page";
    }

    @RequiresPermissions("crsPost:list")
    @RequestMapping("/crsPost_data")
    public void crsPost_data(HttpServletResponse response,
                             Integer year,
                             String name,
                             Integer expertUserId, // 专家
                             @RequestDateRange DateRange meetingTime,
                             @RequestParam(required = false, defaultValue = "1") Byte status,
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

        CrsPostExample example = new CrsPostExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("create_time desc");

        if (status != -1) criteria.andStatusEqualTo(status);

        if (year != null) {
            criteria.andYearEqualTo(year);
        }
        if (StringUtils.isNotBlank(name)) {
            criteria.andNameLike(SqlUtils.like(name));
        }

        if (meetingTime.getStart() != null) {
            criteria.andMeetingTimeGreaterThanOrEqualTo(meetingTime.getStart());
        }

        if (meetingTime.getEnd() != null) {
            criteria.andMeetingTimeLessThanOrEqualTo(meetingTime.getEnd());
        }

        // 查询专家参与的招聘
        if (expertUserId != null) {

            List<Integer> crsPostIds = new ArrayList<>();
            CrsPostExpertExample example2 = new CrsPostExpertExample();
            example2.createCriteria().andUserIdEqualTo(expertUserId);
            List<CrsPostExpert> crsPostExperts = crsPostExpertMapper.selectByExample(example2);
            for (CrsPostExpert crsPostExpert : crsPostExperts) {
                crsPostIds.add(crsPostExpert.getPostId());
            }
            if (crsPostIds.size() > 0) {
                criteria.andIdIn(crsPostIds);
            } else {
                criteria.andIdIsNull();
            }

            // 不看已删除的
            criteria.andStatusIn(Arrays.asList(CrsConstants.CRS_POST_STATUS_NORMAL,
                    CrsConstants.CRS_POST_STATUS_FINISH));
        }

        if (export == 1) {
            if (ids != null && ids.length > 0)
                criteria.andIdIn(Arrays.asList(ids));
            crsPost_export(example, response);
            return;
        }

        long count = crsPostMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<CrsPost> records = crsPostMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(crsPost.class, crsPostMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("crsPost:edit")
    @RequestMapping(value = "/crsPost_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_crsPost_au(CrsPostWithBLOBs record, HttpServletRequest request) {

        Integer id = record.getId();

        if (id == null) {

            record.setRecordUserId(ShiroHelper.getCurrentUserId());
            crsPostService.insertSelective(record);
            logger.info(addLog(LogConstants.LOG_CRS, "添加岗位：%s", record.getId()));
        } else {
            CrsPost crsPost = crsPostMapper.selectByPrimaryKey(id);
            if (crsPost.getType().intValue() != record.getType()
                    || crsPost.getYear().intValue() != record.getYear()) { // 修改了类型或年份，要修改编号
                record.setSeq(crsPostService.genSeq(record.getType(), record.getYear()));
            }

            crsPostService.updateByPrimaryKeySelective(record);
            logger.info(addLog(LogConstants.LOG_CRS, "更新岗位：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("crsPost:edit")
    @RequestMapping("/crsPost_au")
    public String crsPost_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            CrsPost crsPost = crsPostMapper.selectByPrimaryKey(id);
            modelMap.put("crsPost", crsPost);

            if (iScMapper != null) {
                if (crsPost.getRecordId() != null) {
                    modelMap.put("scRecord", iScMapper.getScRecordView(crsPost.getRecordId()));
                }
                if (crsPost.getUnitPostId() != null) {
                    modelMap.put("unitPost", iUnitMapper.getUnitPost(crsPost.getUnitPostId()));
                }
            }
        }
        return "crs/crsPost/crsPost_au";
    }


    @RequiresPermissions("crsPost:edit")
    @RequestMapping(value = "/crsPost_uploadNotice", method = RequestMethod.POST)
    @ResponseBody
    public Map crsPost_uploadNotice(Integer id, MultipartFile file) throws IOException, InterruptedException {

        String originalFilename = file.getOriginalFilename();
        String ext = FileUtils.getExtention(originalFilename);
        if (!StringUtils.equalsIgnoreCase(ext, ".pdf")
                && !ContentTypeUtils.isAnyFormat(file, "pdf")) {
            throw new OpException("任免文件格式错误，请上传pdf文件");
        }

        String savePath = uploadPdf(file, "crs_post_notice");

        CrsPostWithBLOBs record = new CrsPostWithBLOBs();
        record.setId(id);
        record.setNotice(savePath);
        crsPostService.updateByPrimaryKeySelective(record);
        logger.info(addLog(LogConstants.LOG_CRS, "上传招聘公告：%s", id));

        Map<String, Object> resultMap = success();
        resultMap.put("file", UserResUtils.sign(savePath));

        return resultMap;
    }

    @RequiresPermissions("crsPost:edit")
    @RequestMapping("/crsPost_templateContent")
    public String crsPost_templateContent(Integer id, byte type, ModelMap modelMap) {

        if (id != null) {
            CrsPost crsPost = crsPostMapper.selectByPrimaryKey(id);
            modelMap.put("crsPost", crsPost);
            Map<Integer, CrsTemplate> templateMap
                    = crsTemplateService.findAll(type);
            modelMap.put("templateMap", templateMap);

            Map<Integer, String> templates = new HashMap<>();
            for (Map.Entry<Integer, CrsTemplate> entry : templateMap.entrySet()) {
                templates.put(entry.getKey(), HtmlUtils.htmlUnescape(entry.getValue().getContent()));
            }

            modelMap.put("templates", templates);
        }
        return "crs/crsPost/crsPost_templateContent";
    }

    @RequiresPermissions("crsPost:edit")
    @RequestMapping(value = "/crsPost_templateContent", method = RequestMethod.POST)
    @ResponseBody
    public Map do_crsPost_templateContent(Integer id, byte type, String content) {

        CrsPostWithBLOBs record = new CrsPostWithBLOBs();
        record.setId(id);
        if (type == CrsConstants.CRS_TEMPLATE_TYPE_BASE) {
            record.setRequirement(content);
        } else if (type == CrsConstants.CRS_TEMPLATE_TYPE_POST) {
            record.setQualification(content);
        } else if (type == CrsConstants.CRS_TEMPLATE_TYPE_MEETINGNOTICE) {
            record.setMeetingNotice(content);
        } else if (type == CrsConstants.CRS_TEMPLATE_TYPE_POST_DUTY) {
            record.setPostDuty(content);
        }

        crsPostService.updateByPrimaryKeySelective(record);
        logger.info(addLog(LogConstants.LOG_CRS, "更新岗位%s：%s",
                CrsConstants.CRS_TEMPLATE_TYPE_MAP.get(type), id));

        return success(FormUtils.SUCCESS);
    }


    @RequiresPermissions("crsPost:edit")
    @RequestMapping("/crsPost_meetingSummary")
    public String crsPost_meetingSummary(Integer id, ModelMap modelMap) {

        if (id != null) {
            CrsPost crsPost = crsPostMapper.selectByPrimaryKey(id);
            modelMap.put("crsPost", crsPost);
        }
        return "crs/crsPost/crsPost_meetingSummary";
    }

    @RequiresPermissions("crsPost:edit")
    @RequestMapping(value = "/crsPost_meetingSummary", method = RequestMethod.POST)
    @ResponseBody
    public Map do_crsPost_meetingSummary(Integer id, String meetingSummary) {

        CrsPostWithBLOBs record = new CrsPostWithBLOBs();
        record.setId(id);
        record.setMeetingSummary(meetingSummary);

        crsPostService.updateByPrimaryKeySelective(record);
        logger.info(addLog(LogConstants.LOG_CRS, "更新岗位会议备忘：%s", id));

        return success(FormUtils.SUCCESS);
    }


    @RequiresPermissions("crsPost:edit")
    @RequestMapping(value = "/crsPost_publish", method = RequestMethod.POST)
    @ResponseBody
    public Map crsPost_publish(HttpServletRequest request,
                               Integer[] ids,
                               Boolean publish, ModelMap modelMap) {

        if (null != ids && ids.length > 0) {

            publish = BooleanUtils.isTrue(publish);
            CrsPostWithBLOBs record = new CrsPostWithBLOBs();
            record.setPubStatus(publish ? CrsConstants.CRS_POST_PUB_STATUS_PUBLISHED
                    : CrsConstants.CRS_POST_PUB_STATUS_CANCEL);

            CrsPostExample example = new CrsPostExample();
            example.createCriteria().andIdIn(Arrays.asList(ids));
            crsPostMapper.updateByExampleSelective(record, example);

            logger.info(addLog(LogConstants.LOG_CRS, (BooleanUtils.isTrue(publish) ? "发布" : "取消发布") + "岗位：%s",
                    StringUtils.join(ids, ",")));
        }
        return success(FormUtils.SUCCESS);
    }


    @RequiresPermissions("crsPost:edit")
    @RequestMapping(value = "/crsPost_pptUploadClosed", method = RequestMethod.POST)
    @ResponseBody
    public Map crsPost_pptUploadClosed(HttpServletRequest request, Integer id, Boolean closed, ModelMap modelMap) {

        if (id != null) {

            closed = BooleanUtils.isTrue(closed);
            CrsPostWithBLOBs record = new CrsPostWithBLOBs();
            record.setId(id);
            record.setPptUploadClosed(closed);

            crsPostService.updateByPrimaryKeySelective(record);
            logger.info(addLog(LogConstants.LOG_CRS, (BooleanUtils.isTrue(closed) ? "关闭" : "开启") + "ppt上传，岗位：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("crsPost:edit")
    @RequestMapping(value = "/crsPost_updateStatus", method = RequestMethod.POST)
    @ResponseBody
    public Map crsPost_updateStatus(HttpServletRequest request,
                                    byte status,
                                    Integer[] ids,
                                    ModelMap modelMap) {

        if (null != ids && ids.length > 0) {
            crsPostService.updateStatus(ids, status);
            logger.info(addLog(LogConstants.LOG_CRS, "批量更新岗位[%s]状态：%s",
                    CrsConstants.CRS_POST_STATUS_MAP.get(status),
                    StringUtils.join(ids, ",")));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("crsPost:del")
    @RequestMapping(value = "/crsPost_realDel", method = RequestMethod.POST)
    @ResponseBody
    public Map crsPost_realDel(HttpServletRequest request, Integer[] ids, ModelMap modelMap) {

        if (null != ids && ids.length > 0) {
            crsPostService.realDel(ids);
            logger.info(addLog(LogConstants.LOG_CRS, "批量删除岗位：%s", StringUtils.join(ids, ",")));
        }
        return success(FormUtils.SUCCESS);
    }


    public void crsPost_export(CrsPostExample example, HttpServletResponse response) {

        List<CrsPostWithBLOBs> records = crsPostMapper.selectByExampleWithBLOBs(example);
        int rownum = records.size();
        String[] titles = {"年度", "招聘岗位", "行政级别", "所属单位", "基本条件", "任职资格", "报名情况", "常委会情况", "备注", "状态"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            CrsPostWithBLOBs record = records.get(i);
            String[] values = {
                    record.getYear() + "",
                    record.getName(),
                    record.getAdminLevel() + "",
                    record.getUnitId() + "",
                    record.getRequirement(),
                    record.getQualification(),
                    record.getEnrollStatus() + "",
                    record.getCommitteeStatus() + "",
                    record.getRemark(),
                    record.getStatus() + ""
            };
            valuesList.add(values);
        }
        String fileName = "岗位_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
        ExportHelper.export(titles, valuesList, fileName, response);
    }

    @RequestMapping("/crsPost_selects")
    @ResponseBody
    public Map crsPost_selects(Byte status, Integer pageSize, Integer pageNo, String searchStr) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        CrsPostExample example = new CrsPostExample();
        if (StringUtils.isNotBlank(searchStr))
            example.createCriteria().andNameLike(SqlUtils.like(searchStr));
        example.setOrderByClause("create_time desc");

        long count = crsPostMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<CrsPost> crsPosts = crsPostMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));

        List<Map<String, String>> options = new ArrayList<Map<String, String>>();
        if (null != crsPosts && crsPosts.size() > 0) {

            for (CrsPost crsPost : crsPosts) {

                Map<String, String> option = new HashMap<>();
                option.put("id", crsPost.getId() + "");
                option.put("text", crsPost.getName());

                options.add(option);
            }
        }

        Map resultMap = success();
        resultMap.put("totalCount", count);
        resultMap.put("options", options);
        return resultMap;
    }

    @RequiresPermissions("crsPost:edit")
    @RequestMapping("/crsPost_import")
    public String crsPost_import(ModelMap modelMap) {

        return "crs/crsPost/crsPost_import";
    }

    @RequiresPermissions("crsPost:edit")
    @RequestMapping(value = "/crsPost_import", method = RequestMethod.POST)
    @ResponseBody
    public Map do_crsPost_import(HttpServletRequest request) throws InvalidFormatException, IOException {

        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile xlsx = multipartRequest.getFile("xlsx");

        OPCPackage pkg = OPCPackage.open(xlsx.getInputStream());
        XSSFWorkbook workbook = new XSSFWorkbook(pkg);
        XSSFSheet sheet = workbook.getSheetAt(0);
        List<Map<Integer, String>> xlsRows = ExcelUtils.getRowData(sheet);

        List<CrsPostWithBLOBs> records = new ArrayList<>();
        int row = 1;
        for (Map<Integer, String> xlsRow : xlsRows) {

            CrsPostWithBLOBs record = new CrsPostWithBLOBs();
            row++;
            String _year = StringUtils.trimToNull(xlsRow.get(0));
            if (StringUtils.isBlank(_year) || !StringUtils.isNumeric(_year)) {
                throw new OpException("第{0}行年度有误", row);
            }
            record.setYear(Integer.valueOf(_year));

            String _type = StringUtils.trimToNull(xlsRow.get(1));
            if (StringUtils.equals(_type, "竞争上岗")) {
                record.setType(CrsConstants.CRS_POST_TYPE_COMPETE);
            } else {
                record.setType(CrsConstants.CRS_POST_TYPE_PUBLIC);
            }

            String _seq = StringUtils.trimToNull(xlsRow.get(2));
            if (StringUtils.isBlank(_seq) || !StringUtils.isNumeric(_seq)) {
                throw new OpException("第{0}行编号有误", row);
            }
            record.setSeq(Integer.valueOf(_seq));

            String postCode = StringUtils.trimToNull(xlsRow.get(3));
            if (StringUtils.isBlank(postCode)) {
                throw new OpException("第{0}行岗位编号为空", row);
            } else {
                UnitPost unitPost = unitPostService.getByCode(postCode);
                if (unitPost == null) {
                    throw new OpException("第{0}行岗位编码[{1}]不存在", row, postCode);
                }
                record.setUnitPostId(unitPost.getId());
                record.setName(unitPost.getName());
            }

            String name = StringUtils.trimToNull(xlsRow.get(4));
            if (StringUtils.isNotBlank(name)) {
                record.setName(name);
            }

            String job = StringUtils.trimToNull(xlsRow.get(5));
            if (StringUtils.isNotBlank(job)) {
                record.setJob(job);
            }

            String adminLevel = StringUtils.trimToNull(xlsRow.get(6));
            MetaType adminLevelType = CmTag.getMetaTypeByName("mc_admin_level", adminLevel);
            if (adminLevelType == null) throw new OpException("第{0}行行政级别[{1}]不存在", row, adminLevel);
            record.setAdminLevel(adminLevelType.getId());

            String unitCode = StringUtils.trimToNull(xlsRow.get(7));
            if(StringUtils.isBlank(unitCode)){
                throw new OpException("第{0}行单位编码为空", row);
            }
            Unit unit = unitService.findRunUnitByCode(unitCode);
            if(unit==null){
                throw new OpException("第{0}行单位编码[{1}]不存在", row, unitCode);
            }
            record.setUnitId(unit.getId());

            String _num = StringUtils.trimToNull(xlsRow.get(9));
            if (StringUtils.isBlank(_num) || !StringUtils.isNumeric(_num)) {
                throw new OpException("第{0}行招聘人数有误", row);
            }
            record.setNum(Integer.valueOf(_num));

            //岗位要求
            String requireRuleName = StringUtils.trimToNull(xlsRow.get(10));
            if(requireRuleName!=null) {
                CrsPostRequire crsPostRequire = crsPostRequireService.get(requireRuleName);
                if (crsPostRequire == null){
                    throw new OpException("第{0}行岗位要求不存在", row);
                }
                record.setPostRequireId(crsPostRequire.getId());
            }
            record.setStartTime(DateUtils.parseStringToDate(StringUtils.trimToNull(xlsRow.get(11))));
            record.setEndTime(DateUtils.parseStringToDate(StringUtils.trimToNull(xlsRow.get(12))));
            String _meetingApplyCount = StringUtils.trimToNull(xlsRow.get(13));
            if (StringUtils.isNotBlank(_meetingApplyCount) && !StringUtils.isNumeric(_meetingApplyCount)) {
                throw new OpException("第{0}行招聘会人数要求有误", row);
            }
            if(_meetingApplyCount!=null) {
                record.setMeetingApplyCount(Integer.valueOf(_meetingApplyCount));
            }

            record.setMeetingTime(DateUtils.parseStringToDate(StringUtils.trimToNull(xlsRow.get(14))));
            record.setMeetingAddress(StringUtils.trimToNull(xlsRow.get(15)));

            record.setReportDeadline(DateUtils.parseStringToDate(StringUtils.trimToNull(xlsRow.get(16))));
            record.setQuitDeadline(DateUtils.parseStringToDate(StringUtils.trimToNull(xlsRow.get(17))));
            record.setPptDeadline(DateUtils.parseStringToDate(StringUtils.trimToNull(xlsRow.get(18))));

            record.setRemark(StringUtils.trimToNull(xlsRow.get(19)));

            record.setCreateTime(new Date());
            record.setPubStatus(CrsConstants.CRS_POST_PUB_STATUS_UNPUBLISHED);
            record.setStatus(CrsConstants.CRS_POST_STATUS_NORMAL);

            record.setStatus(SystemConstants.UNIT_POST_STATUS_NORMAL);
            records.add(record);
        }

        Collections.reverse(records); // 逆序排列，保证导入的顺序正确

        int addCount = crsPostService.bacthImport(records);
        int totalCount = records.size();
        Map<String, Object> resultMap = success(FormUtils.SUCCESS);
        resultMap.put("addCount", addCount);
        resultMap.put("total", totalCount);

        logger.info(log(LogConstants.LOG_ADMIN,
                "导入招聘岗位成功，总共{0}条记录，其中成功导入{1}条记录，{2}条覆盖",
                totalCount, addCount, totalCount - addCount));

        return resultMap;
    }
}
