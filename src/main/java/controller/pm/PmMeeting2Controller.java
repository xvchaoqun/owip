package controller.pm;

import controller.global.OpException;
import domain.party.Branch;
import domain.party.Party;
import domain.pm.PmMeeting2;
import domain.pm.PmMeeting2Example;
import domain.pm.PmMeeting2Example.Criteria;
import mixin.MixinUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.shiro.authz.UnauthorizedException;
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
import persistence.pm.common.PmMeeting2Stat;
import shiro.ShiroHelper;
import sys.constants.LogConstants;
import sys.constants.PmConstants;
import sys.constants.SystemConstants;
import sys.helper.PartyHelper;
import sys.tags.CmTag;
import sys.tool.paging.CommonList;
import sys.utils.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.*;

import static sys.constants.PmConstants.*;
import static sys.helper.PartyHelper.isDirectBranch;

@Controller

public class PmMeeting2Controller extends PmBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("pmMeeting2:list")
    @RequestMapping("/pmMeeting2")
    public String pmMeeting2(@RequestParam(defaultValue = "1") Integer cls, Integer partyId,
                             Integer branchId,ModelMap modelMap) {
        boolean addPermits = !ShiroHelper.isPermitted(SystemConstants.PERMISSION_PARTYVIEWALL);
        List<Integer> adminPartyIdList = loginUserService.adminPartyIdList();
        /*List<Integer> adminBranchIdList = loginUserService.adminBranchIdList();

        Map pmInitCount = iPmMapper.selectPmInitCount(addPermits,adminPartyIdList, adminBranchIdList);
        if (pmInitCount != null) {
            modelMap.putAll(pmInitCount);
        }*/

        modelMap.put("cls", cls);
        modelMap.put("adminPartyIdList", adminPartyIdList);
        modelMap.put("addPermits", addPermits);

        Map<Integer, Branch> branchMap = branchService.findAll();
        Map<Integer, Party> partyMap = partyService.findAll();
        if (partyId != null)
            modelMap.put("party", partyMap.get(partyId));
        if (branchId != null)
            modelMap.put("branch", branchMap.get(branchId));
        return "pm/pmMeeting2/pmMeeting2_page";
    }

    @RequiresPermissions("pmMeeting2:list")
    @RequestMapping("/pmMeeting2_data")
    @ResponseBody
    public void pmMeeting2_data(HttpServletResponse response,
                                @RequestParam(defaultValue = "1") int cls,
                                Integer partyId,
                                Integer branchId,
                                Integer year,
                                Byte quarter,
                                Integer month,
                                Byte type,
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

        PmMeeting2Example example = new PmMeeting2Example();
        Criteria criteria = example.createCriteria();
        criteria.addPermits(loginUserService.adminPartyIdList(), loginUserService.adminBranchIdList());
        criteria.andIsDeleteNotEqualTo(true);
        example.setOrderByClause("id desc");

        if (year!=null) {
            criteria.andYearEqualTo(year);
        }
        if (partyId!=null) {
            criteria.andPartyIdEqualTo(partyId);
        }
        if (branchId!=null) {
            criteria.andBranchIdEqualTo(branchId);
        }
        if (quarter!=null) {
            criteria.andQuarterEqualTo(quarter);
        }
        if (month!=null) {
            criteria.andMonthEqualTo(month);
        }
        if (type!=null) {
            criteria.andTypeEqualTo(type);
        }
        switch (cls) {
            case 1:
                criteria.andStatusEqualTo(PmConstants.PM_MEETING_STATUS_PASS);
                break;
            case 2:
                criteria.andStatusEqualTo(PmConstants.PM_MEETING_STATUS_INIT);
                break;
            case 3:
                criteria.andStatusEqualTo(PmConstants.PM_MEETING_STATUS_DENY);
                break;
            default:
                break;
        }
        if (export == 1) {
            if(ids!=null && ids.length>0)
                criteria.andIdIn(Arrays.asList(ids));
            pmMeeting2_export(example, response);
            return;
        }

        long count = pmMeeting2Mapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<PmMeeting2> records= pmMeeting2Mapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(pmMeeting2.class, pmMeeting2Mixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("pmMeeting2:edit")
    @RequestMapping(value = "/pmMeeting2_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_pmMeeting2_au(PmMeeting2 record, Byte[] type,Integer number1 ,String time1,Integer number2 ,String time2, MultipartFile _file, @RequestParam(defaultValue = "0")Byte reedit,    //reedit 重新编辑
                                HttpServletRequest request) {

        Integer id = record.getId();

        if (_file != null) {

            String originalFilename = _file.getOriginalFilename();
            String fileName = UUID.randomUUID().toString();
            String realPath = FILE_SEPARATOR
                    + "pmMeeting" + FILE_SEPARATOR
                    + "file" + FILE_SEPARATOR
                    + fileName;
            String savePath = realPath + FileUtils.getExtention(originalFilename);
            FileUtils.copyFile(_file, new File(springProps.uploadPath + savePath));

            record.setFileName(originalFilename);
            record.setFilePath(savePath);
        }

        if(type.length==2){
            record.setType1(type[0]);
            record.setType2(type[1]);
            record.setNumber1(number1);
            record.setNumber2(number2);
            record.setTime1(time1);
            record.setTime2(time2);
        }else {
            record.setType1(type[0]);
            record.setNumber1(number1);
            record.setTime1(time1);
        }

        if (id == null) {
            pmMeeting2Service.insertSelective(record);
            logger.info(log( LogConstants.LOG_PM, "添加三会一课2(支部会议)"));
        } else {
            if (reedit == 1) {
                record.setStatus(PM_MEETING_STATUS_INIT);
            }
            if(record.getDate()!=null){
                record.setYear(DateUtils.getYear(record.getDate()));
                record.setQuarter(DateUtils.getQuarter(record.getDate()));
                record.setMonth(DateUtils.getMonth(record.getDate()));
            }
            pmMeeting2Service.updateByPrimaryKeySelective(record);
            logger.info(log( LogConstants.LOG_PM, "更新三会一课2(支部会议)：{0}", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("pmMeeting2:edit")
    @RequestMapping("/pmMeeting2_au")
    public String pmMeeting2_au(Integer id,Boolean edit,Byte reedit, ModelMap modelMap) {
        PmMeeting2 pmMeeting2=new PmMeeting2();
        if (id != null) {
            pmMeeting2 = pmMeeting2Mapper.selectByPrimaryKey(id);

        } else {
            boolean odAdmin = ShiroHelper.isPermitted(SystemConstants.PERMISSION_PARTYVIEWALL);
            if (!odAdmin) {

                List<Integer> adminPartyIds = loginUserService.adminPartyIdList();
                List<Integer> adminBranchIds = loginUserService.adminBranchIdList();
                if(adminPartyIds.size()==0&&adminBranchIds.size()==1){
                    pmMeeting2.setPartyId(CmTag.getBranch(adminBranchIds.get(0)).getPartyId());
                    pmMeeting2.setBranchId(adminBranchIds.get(0));
                    modelMap.put("adminOnePartyOrBranch",true);

                }else if(adminPartyIds.size()==1&&adminBranchIds.size()==0&&isDirectBranch(adminPartyIds.get(0))){
                    pmMeeting2.setPartyId(adminPartyIds.get(0));
                    modelMap.put("adminOnePartyOrBranch",true);

                }else{
                    modelMap.put("adminOnePartyOrBranch",false);
                }
            }
        }
        modelMap.put("pmMeeting2", pmMeeting2);
        modelMap.put("edit",edit);
        return "pm/pmMeeting2/pmMeeting2_au";
    }

    @RequiresPermissions("pmMeeting2:edit")
    @RequestMapping(value = "/pmMeeting2_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map pmMeeting2_batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            pmMeeting2Service.batchDel(ids);
            logger.info(log( LogConstants.LOG_PM, "批量删除三会一课2(支部会议)：{0}", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("pmMeeting2:approve")
    @RequestMapping( "/pmMeeting2_check")
    public String pmMeeting2_check(Boolean check,HttpServletRequest request, ModelMap modelMap) {

        return "pm/pmMeeting2/pmMeeting2_check";
    }

    @RequiresPermissions("pmMeeting2:approve")
    @RequestMapping(value = "/pmMeeting2_check", method = RequestMethod.POST)
    @ResponseBody
    public Map do_pmMeeting2_check(@RequestParam(value = "ids[]") Integer[] ids,Boolean check,Boolean hasPass,String reason,HttpServletRequest request) {

        Byte status=PM_MEETING_STATUS_INIT;
        Boolean isBack=false;

        if(BooleanUtils.isTrue(check)){
            status=hasPass==null?PM_MEETING_STATUS_DENY:PM_MEETING_STATUS_PASS;
        }else{
            isBack=true;
        }

        pmMeeting2Service.check(ids,status,isBack,reason);

        logger.info(addLog(LogConstants.LOG_PM, "审核三会一课：%s", StringUtils.join(ids)));

        return success(FormUtils.SUCCESS);
    }

    public void pmMeeting2_export(PmMeeting2Example example, HttpServletResponse response) {

        List<PmMeeting2> records = pmMeeting2Mapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"年度","季度","所属"+ CmTag.getStringProperty("partyName")+"|250|left","所属党支部|250|left","实际时间|150","地点|150","活动名称|150|left","次数|100|left","时长|100|left",
                "主要内容|250", "应到人数","实到人数","主持人","记录人"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            PmMeeting2 record = records.get(i);
            /*byte _type = (byte)record.getType1().intValue();*/
            String[] values = {
                    record.getYear()+"",
                    record.getQuarter()+"",
                    CmTag.getParty(record.getPartyId()).getName(),
                    record.getBranchId()==null?"":CmTag.getBranch(record.getBranchId()).getName(),
                    DateUtils.formatDate(record.getDate(), DateUtils.YYYY_MM_DD_HH_MM),
                    record.getAddress(),
                    record.getType2()==null?PARTY_MEETING2_MAP.get(record.getType1()):PARTY_MEETING2_MAP.get(record.getType1())+","+PARTY_MEETING2_MAP.get(record.getType2()),
                    record.getNumber2()==null?"第"+record.getNumber1()+"次":"第"+record.getNumber1()+"/"+record.getNumber2()+"次",
                    record.getTime2()==null?record.getTime1()+"分钟":record.getTime1()+"/"+record.getTime2()+"分钟",
                    record.getShortContent(),
                    record.getDueNum()==null?"": String.valueOf(record.getDueNum()),
                    record.getAttendNum()==null?"": String.valueOf(record.getAttendNum()),
                    record.getPresenter()==null?"": CmTag.getUserById(record.getPresenter()).getRealname(),
                    record.getRecorder()==null?"": CmTag.getUserById(record.getRecorder()).getRealname()
            };
            valuesList.add(values);
        }
        String fileName = String.format("三会一课(支部会议)(%s)", DateUtils.formatDate(new Date(), "yyyyMMdd"));
        ExportHelper.export(titles, valuesList, fileName, response);
    }

    @RequiresPermissions("pmMeeting2:list")
    @RequestMapping("/pmMeeting2_count")
    public String pmMeeting2_count(Integer cls,
                                   Integer year,
                                   Byte quarter,
                                   Integer month,
                                   Integer partyId,
                                   Integer branchId,
                                   Byte type,ModelMap modelMap) {

        return "pm/pmMeeting2/pmMeeting2_count";
    }
    @RequiresPermissions("pmMeeting2:approve")
    @RequestMapping("/pmMeeting2_import")
    public String pmMeeting2_import(ModelMap modelMap) {

        return "pm/pmMeeting2/pmMeeting2_import";
    }

    // 导入会议
    @RequiresPermissions("pmMeeting2:approve")
    @RequestMapping(value = "/pmMeeting2_import", method = RequestMethod.POST)
    @ResponseBody
    public Map do_member_import(Byte type,HttpServletRequest request) throws InvalidFormatException, IOException, InterruptedException {

        Map<Integer, Party> partyMap = partyService.findAll();
        Map<String, Party> runPartyMap = new HashMap<>();
        for (Party party : partyMap.values()) {
            if (BooleanUtils.isNotTrue(party.getIsDeleted())) {
                runPartyMap.put(party.getCode(), party);
            }
        }
        Map<Integer, Branch> branchMap = branchService.findAll();
        Map<String, Branch> runBranchMap = new HashMap<>();
        for (Branch branch : branchMap.values()) {
            if (BooleanUtils.isNotTrue(branch.getIsDeleted())) {
                runBranchMap.put(branch.getCode(), branch);
            }
        }

        Map<String, Byte> partyMeetingMap = new HashMap<>();
        for (Map.Entry<Byte, String> entry : PARTY_MEETING2_MAP.entrySet()) {

            partyMeetingMap.put(entry.getValue(), entry.getKey());
        }

        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile xlsx = multipartRequest.getFile("xlsx");

        OPCPackage pkg = OPCPackage.open(xlsx.getInputStream());
        XSSFWorkbook workbook = new XSSFWorkbook(pkg);

        XSSFSheet sheet = workbook.getSheetAt(0);
        List<Map<Integer, String>> xlsRows = ExcelUtils.getRowData(sheet);

        Map<String, Object> resultMap = null;

        resultMap = importMeeting2(xlsRows, runPartyMap, runBranchMap, partyMeetingMap);

        int successCount = (int) resultMap.get("successCount");
        int totalCount = (int) resultMap.get("total");

        logger.info(log(LogConstants.LOG_PM,
                "导入会议成功，总共{0}条记录，其中成功导入{1}条记录",
                totalCount, successCount));

        return resultMap;
    }

    @RequiresPermissions("pmMeeting2Stat:list")
    @RequestMapping("/pmMeeting2Stat")
    public String pmMeeting2Stat(@RequestParam(defaultValue = "1") Integer cls, Integer partyId,
                             Integer branchId,ModelMap modelMap) {

        Map<Integer, Branch> branchMap = branchService.findAll();
        Map<Integer, Party> partyMap = partyService.findAll();
        if (partyId != null)
            modelMap.put("party", partyMap.get(partyId));
        if (branchId != null)
            modelMap.put("branch", branchMap.get(branchId));
        modelMap.put("cls", cls);
        return "pm/pmMeeting2/pmMeeting2_stat";
    }

    @RequiresPermissions("pmMeeting2Stat:list")
    @RequestMapping("/pmMeeting2_stat")
    @ResponseBody
    public void pmMeeting2_stat(HttpServletResponse response,
                                Byte cls,
                                Integer year,
                                Byte quarter,
                                Integer month,
                                Integer partyId,
                                Integer branchId,
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

        boolean addPermits = !ShiroHelper.isPermitted(SystemConstants.PERMISSION_PARTYVIEWALL);
        List<Integer> adminPartyIdList = loginUserService.adminPartyIdList();
        List<Integer> adminBranchIdList = loginUserService.adminBranchIdList();

        int count = iPmMapper.countPmMeeting2Stat(cls,year,quarter,month,partyId,branchId,PM_MEETING_STATUS_PASS, addPermits, adminPartyIdList, adminBranchIdList);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
       /* List<PmMeeting2View> records= pmMeeting2ViewMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));*/
        List<PmMeeting2Stat> records= iPmMapper.selectPmMeeting2Stat(cls,year,quarter,month,partyId,branchId,PM_MEETING_STATUS_PASS,addPermits, adminPartyIdList, adminBranchIdList,new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(pmMeeting2.class, pmMeeting2Mixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    private Map<String, Object> importMeeting2(List<Map<Integer, String>> xlsRows,
                                              Map<String, Party> runPartyMap,
                                              Map<String, Branch> runBranchMap,
                                              Map<String, Byte> partyMeetingMap) throws InterruptedException {

        List<PmMeeting2> records = new ArrayList<>();
        int row = 1;
        for (Map<Integer, String> xlsRow : xlsRows) {

            row++;
            PmMeeting2 record = new PmMeeting2();

            String partyCode = StringUtils.trim(xlsRow.get(0));
            if (StringUtils.isBlank(partyCode)) {
                throw new OpException("第{0}行分党委编码为空", row);
            }
            Party party = runPartyMap.get(partyCode);
            if (party == null) {
                throw new OpException("第{0}行分党委编码[{1}]不存在", row, partyCode);
            }
            if(!PartyHelper.hasPartyAuth(ShiroHelper.getCurrentUserId(),party.getId())){
                throw new OpException("您没有权限导入第{0}行党支部数据", row);
            }
            record.setPartyId(party.getId());

            String branchCode = StringUtils.trim(xlsRow.get(2));
            Branch branch = runBranchMap.get(branchCode);
            if (!partyService.isDirectBranch(party.getId())) {

                if (StringUtils.isBlank(branchCode)) {
                    throw new OpException("第{0}行党支部编码为空", row);
                }
                if (branch == null) {
                    throw new OpException("第{0}行党支部编码[{1}]不存在", row, partyCode);
                }
                record.setBranchId(branch.getId());
            }

            int col = 4;
            String _partyMeetingType = StringUtils.trimToNull(xlsRow.get(col++));
            if (StringUtils.isBlank(_partyMeetingType)) {
                throw new OpException("第{0}行会议名称为空", row);
            }

            Byte partyMeetingType = partyMeetingMap.get(_partyMeetingType);
            if (partyMeetingType == null) {
                throw new OpException("第{0}行会议名称[{1}]有误", row, _partyMeetingType);
            }
            record.setType1(partyMeetingType);

            String _date = StringUtils.trimToNull(xlsRow.get(col++));
            if (StringUtils.isBlank(_date)) {
                throw new OpException("第{0}行实际时间为空", row);
            }
            record.setDate(DateUtils.parseStringToDate(_date));

            String _address = StringUtils.trimToNull(xlsRow.get(col++));
            if (StringUtils.isBlank(_address)) {
                throw new OpException("第{0}行活动地点为空", row);
            }
            record.setAddress(_address);

            String _shortContent = StringUtils.trimToNull(xlsRow.get(col++));
            if (StringUtils.isBlank(_shortContent)) {
                throw new OpException("第{0}行主要内容为空", row);
            }
            record.setShortContent(_shortContent);

            String _number = StringUtils.trimToNull(xlsRow.get(col++));
            if(_number==null && !NumberUtils.isDigits(_number)){
                throw new OpException("第{0}行次数有误（必须是整数）", row, _number);
            }
            record.setNumber1(Integer.valueOf(_number));

            String _time = StringUtils.trimToNull(xlsRow.get(col++));
            if (StringUtils.isBlank(_time)) {
                throw new OpException("第{0}行时长为空", row);
            }
            record.setTime1(_time);

            records.add(record);
        }

        int successCount = pmMeeting2Service.pmMeeting2Import(records);

        Map<String, Object> resultMap = success(FormUtils.SUCCESS);
        resultMap.put("successCount", successCount);
        resultMap.put("total", records.size());

        return resultMap;
    }

    @RequestMapping("/pmMeeting2_download")
    public void pmMeeting2_download(HttpServletRequest request, Integer id, HttpServletResponse response) throws IOException {

        if(id!=null){
            PmMeeting2 pmMeeting2= pmMeeting2Mapper.selectByPrimaryKey(id);

            if(!PartyHelper.hasBranchAuth(ShiroHelper.getCurrentUserId(),pmMeeting2.getPartyId(),pmMeeting2.getBranchId())){
                throw new UnauthorizedException();
            }

            DownloadUtils.download(request, response, springProps.uploadPath + pmMeeting2.getFilePath(), pmMeeting2.getFileName());
        }
    }
}
