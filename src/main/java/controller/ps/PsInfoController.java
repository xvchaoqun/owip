package controller.ps;

import controller.global.OpException;
import domain.ps.PsInfo;
import domain.ps.PsInfoExample;
import domain.ps.PsMember;
import domain.ps.PsParty;
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
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import shiro.ShiroHelper;
import sys.constants.LogConstants;
import sys.constants.PsConstants;
import sys.tool.paging.CommonList;
import sys.utils.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("/ps")
public class PsInfoController extends PsBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    // 基本信息
    @RequiresPermissions("psInfo:view")
    @RequestMapping("/psInfo_base")
    public String psInfo_base(Integer id, ModelMap modelMap) {

        //党校信息
        PsInfo psInfo = psInfoMapper.selectByPrimaryKey(id);
        modelMap.put("psInfo", psInfo);

        //建设单位信息
        PsParty hostParty = new PsParty();
        List<PsParty> psParties = psInfoService.getPsPsParty(true,id);
        if(psParties.size()>0) hostParty = psParties.get(0);//主建单位信息
        List<PsParty> jointPartyList = psInfoService.getPsPsParty(false,id);//联合建设单位信息
        modelMap.put("hostParty",hostParty);
        modelMap.put("jointPartyList",jointPartyList);

        //组织架构信息
        PsMember principal = new PsMember();
        List<PsMember> psMembers = psInfoService.getPsMember("ps_principal",id);
        if(psMembers.size()>0) principal = psMembers.get(0);//二级党校校长信息
        List<PsMember> viceprincipalList = psInfoService.getPsMember("ps_viceprincipal",id);//二级党校副校长信息
        modelMap.put("principal",principal);
        modelMap.put("viceprincipalList",viceprincipalList);

        //党员人数信息
        Map allPartyNubmerCount = iPsMapper.count(psInfoService.getPartyIdList(null,id));//全部建设单位
        Map hostPartyNumberCount = iPsMapper.count(psInfoService.getPartyIdList(true,id));//主建设单位
        Map notHostPartyNumberCount = iPsMapper.count(psInfoService.getPartyIdList(false,id));//联合建设单位
        modelMap.put("allPartyNubmerCount",allPartyNubmerCount);
        modelMap.put("hostPartyNumberCount",hostPartyNumberCount);
        modelMap.put("notHostPartyNumberCount",notHostPartyNumberCount);

        return "ps/psInfo/psInfo_base";
    }

    @RequiresPermissions("psInfo:view")
    @RequestMapping("/psInfo_view")
    public String psInfo_view(int id, ModelMap modelMap) {

        PsInfo psInfo = psInfoMapper.selectByPrimaryKey(id);
        modelMap.put("psInfo", psInfo);

        return "ps/psInfo/psInfo_view";
    }

    @RequiresPermissions("psInfo:list")
    @RequestMapping("/psInfo")
    public String psInfo(@RequestParam(required = false, defaultValue = "0") boolean isHistory,
                         Integer partyId, ModelMap modelMap) {

        if(partyId!=null){
            modelMap.put("party",partyMapper.selectByPrimaryKey(partyId));
        }
        modelMap.put("isHistory", isHistory);

        return "ps/psInfo/psInfo_page";
    }

    @RequiresPermissions("psInfo:list")
    @RequestMapping("/psInfo_data")
    public void psInfo_data(HttpServletResponse response,
                                 String name,
                                 Integer partyId,
                                 @RequestParam(required = false, defaultValue = "0") boolean isHistory,
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

        PsInfoExample example = new PsInfoExample();
        PsInfoExample.Criteria criteria =
                example.createCriteria().andIsHistoryEqualTo(isHistory)
                .andIsDeletedEqualTo(false);
        example.setOrderByClause("sort_order desc");

        if(!ShiroHelper.hasRole(PsConstants.ROLE_PS_ADMIN)){
            List<Integer> allAdminPsIds = iPsMapper.getAllAdminPsIds(ShiroHelper.getCurrentUserId());
            if(allAdminPsIds.size()>0){
                criteria.andIdIn(allAdminPsIds);
            }else{
                criteria.andIdIsNull();
            }
        }

        if (StringUtils.isNotBlank(name)) {
            criteria.andNameLike(SqlUtils.like(name));
        }
        if (partyId != null){
            criteria.andIdEqualTo(psInfoService.getPsInfoIdByparty(partyId));
        }

        if (export == 1) {
            if(ids!=null && ids.length>0)
                criteria.andIdIn(Arrays.asList(ids));
            psInfo_export(example, response);
            return;
        }

        long count = psInfoMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<PsInfo> records= psInfoMapper.selectByExampleWithRowbounds(example,
                new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(psInfo.class, psInfoMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    //添加、修改二级党校信息
    @RequiresPermissions("psInfo:edit")
    @RequestMapping(value = "/psInfo_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_psInfo_au(PsInfo record, HttpServletRequest request) {

        Integer id = record.getId();
        record.setIsDeleted(false);

        if (id == null) {

            record.setIsHistory(false);
            psInfoService.insertSelective(record);
            logger.info(addLog(LogConstants.LOG_PS, "添加二级党校：%s", record.getId()));
        } else {

            psInfoService.updateByPrimaryKeySelective(record);
            logger.info(addLog(LogConstants.LOG_PS, "更新二级党校：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("psInfo:edit")
    @RequestMapping("/psInfo_au")
    public String psInfo_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            PsInfo psInfo = psInfoMapper.selectByPrimaryKey(id);
            modelMap.put("psInfo", psInfo);
        }
        return "ps/psInfo/psInfo_au";
    }

    //批量撤销二级党校
    @RequiresPermissions("psInfo:history")
    @RequestMapping(value = "/psInfo_history", method = RequestMethod.POST)
    @ResponseBody
    public Map do_psInfo_history(@RequestParam(value = "ids[]") Integer[] ids,
                                 Boolean isHistory,  @DateTimeFormat(pattern = DateUtils.YYYYMMDD_DOT) Date _abolishDate) {

        if (null != ids && ids.length>0){
            psInfoService.updatePsInfoStatus(ids, BooleanUtils.isTrue(isHistory), _abolishDate);
            logger.info(addLog(LogConstants.LOG_PS, "批量转移二级党校：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("psInfo:history")
    @RequestMapping("/psInfo_history")
    public String psInfo_history() {

        return "ps/psInfo/psInfo_history";
    }

    //批量删除二级党校（假删除）
    @RequiresPermissions("psInfo:del")
    @RequestMapping(value = "/psInfo_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map psInfo_batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            psInfoService.batchDel(ids);
            logger.info(addLog(LogConstants.LOG_PS, "批量删除二级党校：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    //二级党校调序
    @RequiresPermissions("psInfo:edit")
    @RequestMapping(value = "/psInfo_changeOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map do_psInfo_changeOrder(Integer id, Integer addNum, HttpServletRequest request) {

        psInfoService.changeOrder(id, addNum);
        logger.info(addLog(LogConstants.LOG_PS, "二级党校调序：%s,%s", id, addNum));
        return success(FormUtils.SUCCESS);
    }

    public void psInfo_export(PsInfoExample example, HttpServletResponse response) {

        List<PsInfo> records = psInfoMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"二级党校名称|100","设立日期|100"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            PsInfo record = records.get(i);
            String[] values = {
                record.getName(),
                            DateUtils.formatDate(record.getFoundDate(), DateUtils.YYYY_MM_DD)
            };
            valuesList.add(values);
        }
        String fileName = "二级党校_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
        ExportHelper.export(titles, valuesList, fileName, response);
    }

    @RequestMapping("/psInfo_selects")
    @ResponseBody
    public Map psInfo_selects(Integer pageSize,
                                   Boolean isHistory,
                                   Integer pageNo,String searchStr) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        PsInfoExample example = new PsInfoExample();
        PsInfoExample.Criteria criteria = example.createCriteria()
                .andIsHistoryEqualTo(false)
                .andIsDeletedEqualTo(false);
        example.setOrderByClause("is_history asc, sort_order desc");

        if(isHistory!=null){
            criteria.andIsHistoryEqualTo(isHistory);
        }

        if(StringUtils.isNotBlank(searchStr)){
            criteria.andNameLike("%"+searchStr.trim()+"%");
        }

        long count = psInfoMapper.countByExample(example);
        if((pageNo-1)*pageSize >= count){

            pageNo = Math.max(1, pageNo-1);
        }
        List<PsInfo> psInfos = psInfoMapper.selectByExampleWithRowbounds(example,
                new RowBounds((pageNo-1)*pageSize, pageSize));

        List options = new ArrayList<>();
        if(null != psInfos && psInfos.size()>0){

            for(PsInfo psInfo:psInfos){

                Map<String, Object> option = new HashMap<>();
                option.put("text", psInfo.getName());
                option.put("id", psInfo.getId() + "");
                option.put("del", psInfo.getIsHistory());
                options.add(option);
            }
        }

        Map resultMap = success();
        resultMap.put("totalCount", count);
        resultMap.put("options", options);
        return resultMap;
    }

    @RequestMapping("/psInfo_import")
    public String psInfo_import(ModelMap modelMap) {

        return "ps/psInfo/psInfo_import";
    }

    @RequestMapping(value = "/psInfo_import", method = RequestMethod.POST)
    @ResponseBody
    public Map do_unitPost_import(HttpServletRequest request) throws InvalidFormatException, IOException {

        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile xlsx = multipartRequest.getFile("xlsx");

        OPCPackage pkg = OPCPackage.open(xlsx.getInputStream());
        XSSFWorkbook workbook = new XSSFWorkbook(pkg);
        XSSFSheet sheet = workbook.getSheetAt(0);
        List<Map<Integer, String>> xlsRows = ExcelUtils.getRowData(sheet);

        List<PsInfo> records = new ArrayList<>();
        int row = 1;
        for (Map<Integer, String> xlsRow : xlsRows) {

            PsInfo record = new PsInfo();
            row++;
            String seq = StringUtils.trimToNull(xlsRow.get(0));
            record.setSeq(seq);

            String name = StringUtils.trimToNull(xlsRow.get(1));
            if(StringUtils.isBlank(name)){
                throw new OpException("第{0}行二级党校名称为空", row);
            }
            record.setName(name);

            String foundDate = StringUtils.trimToNull(xlsRow.get(2));
            if (StringUtils.isNotBlank(foundDate)){
                record.setFoundDate(DateUtils.parseStringToDate(foundDate));
            }

            String abolishDate = StringUtils.trimToNull(xlsRow.get(3));
            if (StringUtils.isNotBlank(abolishDate)){
                record.setAbolishDate(DateUtils.parseStringToDate(abolishDate));
            }

            String isHistory = StringUtils.trimToNull(xlsRow.get(4));
            if (StringUtils.isBlank(isHistory)){
                throw new OpException("第{0}行是否历史党校为空", row);
            }
            record.setIsHistory(StringUtils.equalsIgnoreCase(isHistory, "是"));
            record.setRemark(StringUtils.trimToNull(xlsRow.get(5)));
            record.setIsDeleted(false);

            records.add(record);
        }

        Collections.reverse(records); // 逆序排列，保证导入的顺序正确

        int addCount = psInfoService.batchImport(records);
        int totalCount = records.size();
        Map<String, Object> resultMap = success(FormUtils.SUCCESS);
        resultMap.put("addCount", addCount);
        resultMap.put("total", totalCount);

        logger.info(log(LogConstants.LOG_ADMIN,
                "导入二级党校成功，总共{0}条记录，其中成功导入{1}条记录，{2}条覆盖",
                totalCount, addCount, totalCount - addCount));

        return resultMap;
    }
}
