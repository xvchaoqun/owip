package controller.cet;

import domain.cet.CetProjectType;
import domain.cet.CetRecord;
import domain.cet.CetRecordExample;
import domain.cet.CetRecordExample.Criteria;
import domain.cet.CetTraineeType;
import domain.sys.SysUserView;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import sys.constants.CetConstants;
import sys.spring.DateRange;
import sys.spring.RequestDateRange;
import sys.tags.CmTag;
import sys.tool.paging.CommonList;
import sys.utils.DateUtils;
import sys.utils.ExportHelper;
import sys.utils.JSONUtils;
import sys.utils.SqlUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("/cet")
public class CetRecordController extends CetBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    // 查看结业证书
    @RequestMapping("/cert")
    public String cert() {

        return "cet/cetRecord/cet_cert";
    }

    @RequiresPermissions("cetRecord:list")
    @RequestMapping("/cetRecord")
    public String cetRecord(Integer userId, ModelMap modelMap) {

        if(userId!=null){
            modelMap.put("sysUser", CmTag.getUserById(userId));
        }

        Map<Integer, CetProjectType> specialProjectTypeMap =
                cetProjectTypeService.findAll(CetConstants.CET_PROJECT_TYPE_CLS_1);
        specialProjectTypeMap.putAll(cetProjectTypeService.findAll(CetConstants.CET_PROJECT_TYPE_CLS_3));
        modelMap.put("specialProjectTypeMap", specialProjectTypeMap);

        Map<Integer, CetProjectType>  dailyProjectTypeMap =
                cetProjectTypeService.findAll(CetConstants.CET_PROJECT_TYPE_CLS_2);
        dailyProjectTypeMap.putAll(cetProjectTypeService.findAll(CetConstants.CET_PROJECT_TYPE_CLS_4));
        modelMap.put("dailyProjectTypeMap", dailyProjectTypeMap);

        return "cet/cetRecord/cetRecord_page";
    }

    @RequiresPermissions("cetRecord:list")
    @RequestMapping("/cetRecord_data")
    @ResponseBody
    public void cetRecord_data(HttpServletResponse response,
                               Integer year,
                               Byte type,
                               Integer userId,
                               Integer traineeTypeId,
                               @RequestDateRange DateRange trainDate,
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

        CetRecordExample example = new CetRecordExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("year desc, start_date desc");

        if (year != null) {
            criteria.andYearEqualTo(year);
        }
        if(type!=null){
            criteria.andTypeEqualTo(type);
        }
        if (userId != null) {
            criteria.andUserIdEqualTo(userId);
        }
        if (traineeTypeId != null) {
            criteria.andTraineeTypeIdEqualTo(traineeTypeId);
        }
        if(trainDate.getStart()!=null){
            criteria.andStartDateGreaterThanOrEqualTo(trainDate.getStart());
        }
        if(trainDate.getEnd()!=null){
            criteria.andStartDateLessThanOrEqualTo(trainDate.getEnd());
        }

        if (export == 1) {
            if (ids != null && ids.length > 0)
                criteria.andIdIn(Arrays.asList(ids));
            cetRecord_export(example, response);
            return;
        }

        long count = cetRecordMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<CetRecord> records = cetRecordMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(cetRecord.class, cetRecordMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    public void cetRecord_export(CetRecordExample example, HttpServletResponse response) {

        List<CetRecord> records = cetRecordMapper.selectByExample(example);
        int rownum = records.size();
        Map<Integer, CetTraineeType> traineeTypeMap = cetTraineeTypeService.findAll();

        String[] titles = {"年度|100", "参训人|100", "工作证号|100", "参训人员类型|100",
                "时任单位及职务|200|left", "起始时间|100", "结束时间|100", "培训班名称|300|left",
                "培训类型|150", "培训主办方|200|left", "完成学时总数|100",
                "线上完成学时数|70", "是否结业|100", "是否计入年度学习任务|100", "备注|200|left"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {

            CetRecord record = records.get(i);
            SysUserView uv = CmTag.getUserById(record.getUserId());
            String traineeType = "";
            if(record.getTraineeTypeId()!=null) {
                CetTraineeType cetTraineeType = traineeTypeMap.get(record.getTraineeTypeId());
                if(cetTraineeType!=null){
                    traineeType = cetTraineeType.getName();
                }
            }
            String type = CetConstants.CET_TYPE_MAP.get(record.getType());
            String[] values = {
                    record.getYear() + "",
                    uv.getRealname(),
                    uv.getCode(),
                    traineeType,
                    record.getTitle(),
                    DateUtils.formatDate(record.getStartDate(), DateUtils.YYYY_MM_DD),
                    DateUtils.formatDate(record.getEndDate(), DateUtils.YYYY_MM_DD),
                    record.getName(),
                    type,
                    record.getOrganizer(),
                    record.getPeriod() + "",
                    record.getOnlinePeriod()==null?"--":record.getOnlinePeriod()+"",
                    BooleanUtils.isTrue(record.getIsGraduate())?"是":"否",
                    BooleanUtils.isTrue(record.getIsValid())?"是":"否",
                    record.getRemark()
            };
            valuesList.add(values);
        }
        String fileName = String.format("培训记录明细汇总表(%s)", DateUtils.formatDate(new Date(), "yyyyMMdd"));
        ExportHelper.export(titles, valuesList, fileName, response);
    }

    @RequestMapping("/cetRecord_selects")
    @ResponseBody
    public Map cetRecord_selects(Integer pageSize, Integer pageNo, String searchStr) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        CetRecordExample example = new CetRecordExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("id desc");

        if (StringUtils.isNotBlank(searchStr)) {
            criteria.andNameLike(SqlUtils.like(searchStr));
        }

        long count = cetRecordMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<CetRecord> records = cetRecordMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));

        List options = new ArrayList<>();
        if (null != records && records.size() > 0) {

            for (CetRecord record : records) {

                Map<String, Object> option = new HashMap<>();
                option.put("text", record.getName());
                option.put("id", record.getId() + "");

                options.add(option);
            }
        }

        Map resultMap = success();
        resultMap.put("totalCount", count);
        resultMap.put("options", options);
        return resultMap;
    }
}
