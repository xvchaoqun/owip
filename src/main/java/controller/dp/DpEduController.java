package controller.dp;

import domain.dp.DpEdu;
import domain.dp.DpEduExample;
import domain.dp.DpEduExample.Criteria;
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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import sys.constants.CadreConstants;
import sys.constants.LogConstants;
import sys.tags.CmTag;
import sys.tool.paging.CommonList;
import sys.utils.DateUtils;
import sys.utils.ExportHelper;
import sys.utils.FormUtils;
import sys.utils.JSONUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("/dp")
public class DpEduController extends DpBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("dpEdu:list")
    @RequestMapping("/dpEdu")
    public String dpEdu(ModelMap modelMap) {

        List<Integer> needTutorEduTypes = dpEduService.needTutorEduTypes();
        modelMap.put("needTutorEduTypes", needTutorEduTypes);
        return "dp/dpEdu/dpEdu_page";
    }

    @RequiresPermissions("dpEdu:list")
    @RequestMapping("/dpEdu_data")
    @ResponseBody
    public void dpEdu_data(HttpServletResponse response,
                                    Integer userId,
                                Byte status,
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

        DpEduExample example = new DpEduExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("sort_order desc");

        if (userId!=null) {
            criteria.andUserIdEqualTo(userId);
        }

        if (export == 1) {
            if(ids!=null && ids.length>0)
                criteria.andIdIn(Arrays.asList(ids));
            dpEdu_export(example, response);
            return;
        }

        long count = dpEduMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<DpEdu> records= dpEduMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(dpEdu.class, dpEduMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("dpEdu:edit")
    @RequestMapping(value = "/dpEdu_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_dpEdu_au(DpEdu record,
                           MultipartFile file1,
                           MultipartFile file2,
                           HttpServletRequest request) throws IOException, InterruptedException {

        Integer id = record.getId();

        if(record.getEnrolTime()!=null && record.getFinishTime()!=null
                && record.getFinishTime().before(record.getEnrolTime())){
            return failed("入学时间和毕业时间有误");
        }

        List<String> filePaths = new ArrayList<>();
        if(file1!=null){
            filePaths.add(upload(file1, "dp_edu"));
        }
        if(file2!=null){
            filePaths.add(upload(file2, "dp_edu"));
        }
        if (filePaths.size() > 0) {
            record.setCertificate(StringUtils.join(filePaths, ","));
        }
        record.setIsGraduated(BooleanUtils.isTrue(record.getIsGraduated()));
        record.setHasDegree(BooleanUtils.isTrue(record.getHasDegree()));

        if (record.getSchoolType()!=null && (record.getSchoolType() == CadreConstants.CADRE_SCHOOL_TYPE_THIS_SCHOOL ||
                record.getSchoolType() == CadreConstants.CADRE_SCHOOL_TYPE_DOMESTIC)) {
            record.setDegreeCountry("中国");
        }

        record.setIsHighEdu(BooleanUtils.isTrue(record.getIsHighEdu()));

        record.setIsHighDegree(BooleanUtils.isTrue(record.getIsHighDegree()));
        record.setIsSecondDegree(BooleanUtils.isTrue(record.getIsSecondDegree()));

        if (id == null) {
            //record.setStatus(true);
            dpEduService.insertSelective(record);
            logger.info(log( LogConstants.LOG_DPPARTY, "添加统战人员学习经历：{0}", record.getId()));
        } else {

            dpEduService.updateByPrimaryKeySelective(record);
            logger.info(log( LogConstants.LOG_DPPARTY, "更新统战人员学习经历：{0}", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("dpEdu:edit")
    @RequestMapping("/dpEdu_au")
    public String dpEdu_au(Integer id,
                           Integer userId,
                           ModelMap modelMap) {

        SysUserView uv = CmTag.getUserById(userId);
        modelMap.put("uv", uv);
        if (id != null) {
            DpEdu dpEdu = dpEduMapper.selectByPrimaryKey(id);
            modelMap.put("dpEdu", dpEdu);
        }
        return "dp/dpEdu/dpEdu_au";
    }

    @RequiresPermissions("dpEdu:del")
    @RequestMapping(value = "/dpEdu_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_dpEdu_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            dpEduService.del(id);
            logger.info(log( LogConstants.LOG_DPPARTY, "删除统战人员学习经历：{0}", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("dpEdu:del")
    @RequestMapping(value = "/dpEdu_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map dpEdu_batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            dpEduService.batchDel(ids);
            logger.info(log( LogConstants.LOG_DPPARTY, "批量删除统战人员学习经历：{0}", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }
    @RequiresPermissions("dpEdu:changeOrder")
    @RequestMapping(value = "/dpEdu_changeOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map do_dpEdu_changeOrder(Integer id, Integer addNum, HttpServletRequest request) {

        dpEduService.changeOrder(id, addNum);
        logger.info(log( LogConstants.LOG_DPPARTY, "统战人员学习经历调序：{0}, {1}", id, addNum));
        return success(FormUtils.SUCCESS);
    }

    public void dpEdu_export(DpEduExample example, HttpServletResponse response) {

        List<DpEdu> records = dpEduMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"学历|100","毕业/在读|100","是否最高学历|100","毕业/在读学校|100","院系|100","学科门类|100","一级学科|100","所学专业|100","学校类型|100","入学时间|100","毕业时间|100","学习方式|100","是否获得学位|100","是否为最高学位|100","学位授予国家|100","学位授予单位|100","学位授予日期|100","导师姓名|100","所在单位及职务（职称）|100","学历学位证书|100","备注|100"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            DpEdu record = records.get(i);
            String[] values = {
                record.getEduId()+"",
                            record.getIsGraduated()+"",
                            record.getIsHighEdu()+"",
                            record.getSchool(),
                            record.getDep(),
                            record.getSubject()+"",
                            record.getFirstSubject()+"",
                            record.getMajor(),
                            record.getSchoolType()+"",
                            DateUtils.formatDate(record.getEnrolTime(), DateUtils.YYYY_MM_DD),
                            DateUtils.formatDate(record.getFinishTime(), DateUtils.YYYY_MM_DD),
                            record.getLearnStyle()+"",
                            record.getHasDegree()+"",
                            record.getIsHighDegree()+"",
                            record.getDegreeCountry(),
                            record.getDegreeUnit(),
                            DateUtils.formatDate(record.getDegreeTime(), DateUtils.YYYY_MM_DD),
                            record.getTutorName(),
                            record.getTutorTitle(),
                            record.getCertificate(),
                            record.getRemark()
            };
            valuesList.add(values);
        }
        String fileName = String.format("统战人员学习经历(%s)", DateUtils.formatDate(new Date(), "yyyyMMdd"));
        ExportHelper.export(titles, valuesList, fileName, response);
    }

    @RequestMapping("/dpEdu_selects")
    @ResponseBody
    public Map dpEdu_selects(Integer pageSize, Integer pageNo,String searchStr) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        DpEduExample example = new DpEduExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("sort_order desc");

        if(StringUtils.isNotBlank(searchStr)){
            //criteria.andNameLike(SqlUtils.like(searchStr));
        }

        long count = dpEduMapper.countByExample(example);
        if((pageNo-1)*pageSize >= count){

            pageNo = Math.max(1, pageNo-1);
        }
        List<DpEdu> records = dpEduMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo-1)*pageSize, pageSize));

        List options = new ArrayList<>();
        if(null != records && records.size()>0){

            for(DpEdu record:records){

                Map<String, Object> option = new HashMap<>();
                option.put("text", record.getId());
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
