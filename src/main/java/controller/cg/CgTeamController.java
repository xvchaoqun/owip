package controller.cg;

import controller.global.OpException;
import domain.base.MetaType;
import domain.cg.CgTeam;
import domain.cg.CgTeamView;
import domain.cg.CgTeamViewExample;
import domain.cg.CgTeamViewExample.Criteria;
import freemarker.template.TemplateException;
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
import sys.constants.CgConstants;
import sys.constants.LogConstants;
import sys.tags.CmTag;
import sys.tool.paging.CommonList;
import sys.utils.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("/cg")
public class CgTeamController extends CgBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    //二级党校概况
    @RequiresPermissions("cgTeam:view")
    @RequestMapping("/cgTeam_base")
    public String cgTeam_base(Integer id, ModelMap modelMap){

        //委员会和领导小组
        modelMap.put("cgTeamBases",cgTeamService.getTeamBase(id));

        //分委会
        modelMap.put("cgBranchList",cgTeamService.getChilds(id,CgConstants.CG_TEAM_TYPE_BRANCH));

        //工作小组
        modelMap.put("cgWorkgroupList",cgTeamService.getChilds(id,CgConstants.CG_TEAM_TYPE_WORKGROUP));

        return "cg/cgTeam/cgTeam_base";
    }

    @RequiresPermissions("cgTeam:list")
    @RequestMapping("/cgTeam")
    public String cgTeam(@RequestParam(required = false, defaultValue = "1") boolean isCurrent,
                         Integer fid,
                         Integer unitId,
                         Integer userId,
                         ModelMap modelMap) {

        modelMap.put("isCurrent",isCurrent);
        if (unitId!=null){

            modelMap.put("unit",CmTag.getUnitById(unitId));
        }
        if (userId!=null){
            modelMap.put("user",CmTag.getUserById(userId));
        }
        if (fid!=null){
            return "cg/cgTeam/cgTeam_child_page";
        }

        return "cg/cgTeam/cgTeam_page";
    }

    @RequiresPermissions("cgTeam:view")
    @RequestMapping("/cgTeam_view")
    public String cgTeam_view(Integer fid,Integer teamId, ModelMap modelMap) {

        CgTeam cgTeam = cgTeamMapper.selectByPrimaryKey(teamId);
        modelMap.put("cgTeam", cgTeam);

        if(fid!=null) {
            CgTeam fCgTeam = cgTeamMapper.selectByPrimaryKey(fid);
            modelMap.put("fCgTeam",fCgTeam);
        }

        return "cg/cgTeam/cgTeam_view";
    }

    @RequiresPermissions("cgTeam:list")
    @RequestMapping("/cgTeam_data")
    @ResponseBody
    public void cgTeam_data(HttpServletResponse response,
                            String name,
                            Byte type,
                            Integer fid,
                            Integer category,
                            Integer unitId,
                            Integer userId,
                            @RequestParam(required = false, defaultValue = "1") Boolean isCurrent,
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

        CgTeamViewExample example = new CgTeamViewExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("sort_order desc");

        if (fid!=null){
            criteria.andFidEqualTo(fid);
        }else {
            criteria.andFidIsNull();
        }
        if (StringUtils.isNotBlank(name)) {
            criteria.andNameLike(SqlUtils.like(name));
        }
        if (type!=null) {
            criteria.andTypeEqualTo(type);
        }
        if (category!=null) {
            criteria.andCategoryEqualTo(category);
        }
        if (isCurrent!=null){
            criteria.andIsCurrentEqualTo(isCurrent);
        }
        if (unitId!=null){
            criteria.andUnitIdEqualTo(unitId);
        }
        if (userId!=null){
            criteria.andUserIdEqualTo(userId);
        }
        if (export == 1) {
            if(ids!=null && ids.length>0)
                criteria.andIdIn(Arrays.asList(ids));
            cgTeam_export(example, response);
            return;
        }

        long count = cgTeamViewMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<CgTeamView> records= cgTeamViewMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(cgTeam.class, cgTeamMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("cgTeam:edit")
    @RequestMapping(value = "/cgTeam_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cgTeam_au(CgTeam record, HttpServletRequest request) {

        Integer id = record.getId();

        if (id == null) {

            record.setIsCurrent(true);
            record.setNeedAdjust(BooleanUtils.isTrue(record.getNeedAdjust()));
            cgTeamService.insertSelective(record);
            logger.info(log( LogConstants.LOG_CG, "添加委员会和领导小组：{0}", record.getId()));
        } else {

            cgTeamService.updateByPrimaryKeySelective(record);
            logger.info(log( LogConstants.LOG_CG, "更新委员会和领导小组：{0}", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cgTeam:edit")
    @RequestMapping("/cgTeam_au")
    public String cgTeam_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            CgTeam cgTeam = cgTeamMapper.selectByPrimaryKey(id);
            modelMap.put("cgTeam", cgTeam);
        }
        return "cg/cgTeam/cgTeam_au";
    }

    @RequiresPermissions("cgTeam:del")
    @RequestMapping(value = "/cgTeam_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cgTeam_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            cgTeamService.del(id);
            logger.info(log( LogConstants.LOG_CG, "删除委员会和领导小组：{0}", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cgTeam:del")
    @RequestMapping(value = "/cgTeam_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map cgTeam_batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {

        if (null != ids && ids.length>0){
            cgTeamService.batchDel(ids);
            logger.info(log( LogConstants.LOG_CG, "批量删除委员会和领导小组：{0}", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }
    @RequiresPermissions("cgTeam:changeOrder")
    @RequestMapping(value = "/cgTeam_changeOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cgTeam_changeOrder(Integer id, Integer fid, Integer addNum, HttpServletRequest request) {

        cgTeamService.changeOrder(id, fid, addNum);
        logger.info(log( LogConstants.LOG_CG, "委员会和领导小组调序：{0}, {1}", id, addNum));
        return success(FormUtils.SUCCESS);
    }

    public void cgTeam_export(CgTeamViewExample example, HttpServletResponse response) {

        List<CgTeamView> records = cgTeamViewMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"名称|200","类型|100","类别|100","挂靠单位|250","办公室主任|100","联系电话|150","备注|100"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            CgTeamView record = records.get(i);
            MetaType metaType = CmTag.getMetaType(record.getCategory());
            String[] values = {
                            record.getName(),
                            CgConstants.CG_TEAM_TYPE_MAP.get(record.getType()),
                            metaType == null?"":metaType.getName(),
                            record.getUnit()==null?"":record.getUnit().getName(),
                            record.getUser()==null?"":record.getUser().getRealname(),
                            record.getPhone(),
                            record.getRemark()
            };
            valuesList.add(values);
        }
        String fileName = String.format("委员会和领导小组(%s)", DateUtils.formatDate(new Date(), "yyyyMMdd"));
        ExportHelper.export(titles, valuesList, fileName, response);
    }

    @RequestMapping("/cgTeam_selects")
    @ResponseBody
    public Map cgTeam_selects(Integer pageSize, Integer pageNo,String searchStr) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        CgTeamViewExample example = new CgTeamViewExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("sort_order desc");

        if(StringUtils.isNotBlank(searchStr)){
            criteria.andNameLike(SqlUtils.like(searchStr));
        }

        long count = cgTeamViewMapper.countByExample(example);
        if((pageNo-1)*pageSize >= count){

            pageNo = Math.max(1, pageNo-1);
        }
        List<CgTeamView> records = cgTeamViewMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo-1)*pageSize, pageSize));

        List options = new ArrayList<>();
        if(null != records && records.size()>0){

            for(CgTeamView record:records){

                Map<String, Object> option = new HashMap<>();
                option.put("text", record.getName());
                option.put("id", record.getId());
                options.add(option);
            }
        }

        Map resultMap = success();
        resultMap.put("totalCount", count);
        resultMap.put("options", options);
        return resultMap;
    }

    @RequiresPermissions("cgTeam:plan")
    @RequestMapping(value = "/cgTeam_plan", method = RequestMethod.POST)
    @ResponseBody
    public Map cgTeam_plan(@RequestParam(value = "ids[]") Integer[] ids, Boolean isCurrent) {

        if (null != ids && ids.length>0){
            cgTeamService.updateTeamStatus(ids,isCurrent);
            logger.info(addLog(LogConstants.LOG_CG, "批量%s委员会和领导小组：%s",isCurrent?"返回":"撤销", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

   @RequestMapping("/cgTeam_import")
    public String cgTeam_import(ModelMap modelMap) {

        return "cg/cgTeam/cgTeam_import";
    }

    @RequestMapping(value = "/cgTeam_import", method = RequestMethod.POST)
    @ResponseBody
    public Map do_unitPost_import(HttpServletRequest request) throws InvalidFormatException, IOException {

        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile xlsx = multipartRequest.getFile("xlsx");

        OPCPackage pkg = OPCPackage.open(xlsx.getInputStream());
        XSSFWorkbook workbook = new XSSFWorkbook(pkg);
        XSSFSheet sheet = workbook.getSheetAt(0);
        List<Map<Integer, String>> xlsRows = ExcelUtils.getRowData(sheet);

        List<CgTeam> records = new ArrayList<>();
        int row = 1;
        for (Map<Integer, String> xlsRow : xlsRows) {

            CgTeam record = new CgTeam();
            row++;

            String name = StringUtils.trimToNull(xlsRow.get(0));
            if(StringUtils.isBlank(name)){
                throw new OpException("第{0}行委员会和领导小组名称为空", row);
            }
            record.setName(name);

            String _type = StringUtils.trimToNull(xlsRow.get(1));
            Byte type = cgTeamService.findByType(_type);
            if (type==null){
                throw new OpException("第{0}行类型不存在", row, _type);
            }
            record.setType(type);

            String category = StringUtils.trimToNull(xlsRow.get(2));
            MetaType dpType = metaTypeService.findByName("mc_cg_type", category);
            if (dpType==null){
                throw new OpException("第{0}行类别不存在",row, category);
            }
            record.setCategory(dpType.getId());

            String iscurrent = StringUtils.trimToNull(xlsRow.get(3));
            if (StringUtils.isBlank(iscurrent)){
                throw new OpException("第{0}行是否当前委员会和领导小组为空", row);
            }
            record.setIsCurrent(StringUtils.equalsIgnoreCase(iscurrent, "是"));
            record.setNeedAdjust(false);

            records.add(record);
        }

        Collections.reverse(records); // 逆序排列，保证导入的顺序正确

        int addCount = cgTeamService.batchImport(records);
        int totalCount = records.size();
        Map<String, Object> resultMap = success(FormUtils.SUCCESS);
        resultMap.put("addCount", addCount);
        resultMap.put("total", totalCount);

        logger.info(log(LogConstants.LOG_ADMIN,
                "导入委员会和领导小组成功，总共{0}条记录，其中成功导入{1}条记录，{2}条覆盖",
                totalCount, addCount, totalCount - addCount));

        return resultMap;
    }

    @RequestMapping("/cgTeam_download")
    public void cgTeam_download(@RequestParam(required = false, value = "ids[]") Integer[] ids,
                                @RequestParam(required = false, value = "isWord", defaultValue = "1") Boolean isWord,
                                HttpServletRequest request, HttpServletResponse response) throws IOException, TemplateException {

        //如果没选则得到全部委员会和领导小组
        if (ids == null || ids.length == 0)
            ids = cgTeamService.getAllTeamId(ids);

        if (isWord){//导出word

            String filename = DateUtils.formatDate(new Date(), "yyyy.MM.dd") + "委员会和领导小组" + ".doc";
            response.reset();
            DownloadUtils.addFileDownloadCookieHeader(response);
            response.setHeader("Content-Disposition",
                    "attachment;filename=" + DownloadUtils.encodeFilename(request, filename));
            response.setContentType("application/msword;charset=UTF-8");

            cgTeamService.export(ids, response.getWriter());
        }else {// 导出zip
            cgTeamService.export(ids,request,response);
        }
    }

    @RequiresPermissions("cgMember:plan")
    @RequestMapping("/cgTeam_updateUser")
    public String cgMember_updateUser(@RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {

        if (null != ids && ids.length>0){
            //获取委员会和领导小组中需要更新席位制的人员
            List<Integer> cgMemberIdList = iCgMapper.getNeedAdjustMember(ids);
            //获取人员信息，用于页面显示
            List<Map> userList = cgMemberService.getOldAndNewUser(cgMemberIdList);
            modelMap.put("userList",userList);
        }
        return "cg/cgMember/cgMember_update";
    }
}
