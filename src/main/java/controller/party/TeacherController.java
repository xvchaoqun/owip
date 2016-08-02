package controller.party;

import controller.BaseController;
import domain.member.Teacher;
import domain.member.TeacherExample;
import domain.member.TeacherExample.Criteria;
import domain.sys.SysUser;
import interceptor.OrderParam;
import interceptor.SortParam;
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
import service.helper.ExportHelper;
import sys.constants.SystemConstants;
import sys.tool.paging.CommonList;
import sys.utils.DateUtils;
import sys.utils.FormUtils;
import sys.utils.JSONUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Controller
public class TeacherController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("memberTeacher:list")
    @RequestMapping("/teacher")
    public String teacher() {

        return "index";
    }
    @RequiresPermissions("memberTeacher:list")
    @RequestMapping("/teacher_page")
    public String teacher_page(HttpServletResponse response,
    Integer pageSize, Integer pageNo, ModelMap modelMap) {

        return "party/teacher/teacher_page";
    }

    @RequiresPermissions("memberTeacher:list")
    @RequestMapping("/teacher_data")
    public void teacher_data(HttpServletResponse response,
                                 @SortParam(required = false, defaultValue = "create_time", tableName = "ow_teacher") String sort,
                                 @OrderParam(required = false, defaultValue = "desc") String order,
                                    Integer userId,
                                    String code,
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

        TeacherExample example = new TeacherExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause(String.format("%s %s", sort, order));

        if (userId!=null) {
            criteria.andUserIdEqualTo(userId);
        }
        if (StringUtils.isNotBlank(code)) {
            criteria.andCodeLike("%" + code + "%");
        }

        if (export == 1) {
            if(ids!=null && ids.length>0)
                criteria.andUserIdIn(Arrays.asList(ids));
            teacher_export(example, response);
            return;
        }

        int count = teacherMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<Teacher> teachers = teacherMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", teachers);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> sourceMixins = sourceMixins();
        //sourceMixins.put(teacher.class, teacherMixin.class);
        JSONUtils.jsonp(resultMap, sourceMixins);
        return;
    }

    @RequiresPermissions("memberTeacher:edit")
    @RequestMapping(value = "/teacher_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_teacher_au(Teacher record, String _birth,String _degreeTime, String _arriveTime, String _retireTime, HttpServletRequest request) {

        Integer userId = record.getUserId();

        if(StringUtils.isNotBlank(_birth)){
            record.setBirth(DateUtils.parseDate(_birth, DateUtils.YYYY_MM_DD));
        }
        if(StringUtils.isNotBlank(_degreeTime)){
            record.setDegreeTime(_degreeTime);
        }
        if (StringUtils.isNotBlank(_arriveTime)){
            record.setArriveTime(_arriveTime);
        }
        if (StringUtils.isNotBlank(_retireTime)){
            record.setRetireTime(DateUtils.parseDate(_retireTime, DateUtils.YYYY_MM_DD));
        }
        record.setIsRetire((record.getIsRetire() == null) ? false : record.getIsRetire());
        record.setIsHonorRetire((record.getIsHonorRetire() == null) ? false : record.getIsHonorRetire());

        if (userId == null) {
            record.setCreateTime(new Date());
            teacherService.insertSelective(record);
            logger.info(addLog( SystemConstants.LOG_OW, "添加教职工党员信息：%s", record.getUserId()));
        } else {
            teacherService.updateByPrimaryKeySelective(record);
            logger.info(addLog( SystemConstants.LOG_OW, "更新教职工党员信息：%s", record.getUserId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("memberTeacher:edit")
    @RequestMapping("/teacher_au")
    public String teacher_au(int userId, ModelMap modelMap) {

        SysUser sysUser = sysUserService.findById(userId);
        Byte source = sysUser.getSource();
        if(source==SystemConstants.USER_SOURCE_BKS || source==SystemConstants.USER_SOURCE_YJS
                || source==SystemConstants.USER_SOURCE_JZG){
            throw new RuntimeException("只能修改非人事库的账号信息");
        }

        Teacher teacher = teacherMapper.selectByPrimaryKey(userId);
        modelMap.put("teacher", teacher);

        return "party/teacher/teacher_au";
    }

    /*@RequiresPermissions("memberTeacher:del")
    @RequestMapping(value = "/teacher_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_teacher_del(HttpServletRequest request, Integer userId) {

        if (userId != null) {

            teacherService.del(userId);
            logger.info(addLog( SystemConstants.LOG_OW, "删除教职工党员信息：%s", userId));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("memberTeacher:del")
    @RequestMapping(value = "/teacher_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] userIds, ModelMap modelMap) {


        if (null != userIds && userIds.length>0){
            teacherService.batchDel(userIds);
            logger.info(addLog( SystemConstants.LOG_OW, "批量删除教职工党员信息：%s", StringUtils.join(userIds, ",")));
        }

        return success(FormUtils.SUCCESS);
    }*/

    public void teacher_export(TeacherExample example, HttpServletResponse response) {

        List<Teacher> records = teacherMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"账号ID","工作证号","姓名","性别","出生日期","籍贯","民族","身份证号","最高学历","最高学位","学位授予日期","所学专业","毕业学校","毕业学校类型","到校日期","编制类别","人员分类","人员状态","岗位类别","岗位子类别","在岗情况","专业技术职务","专技岗位等级","职称级别","管理岗位等级","工勤岗位等级","行政职务","任职级别","人才/荣誉称号","居住地址","婚姻状况","联系邮箱","联系手机","家庭电话","是否退休","退休时间","是否离休","创建时间"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            Teacher record = records.get(i);
            String[] values = {
                record.getUserId()+"",
                            record.getCode(),
                            record.getRealname(),
                            SystemConstants.GENDER_MAP.get(record.getGender()),
                            DateUtils.formatDate(record.getBirth(), DateUtils.YYYY_MM_DD),
                            record.getNativePlace(),
                            record.getNation(),
                            record.getIdcard(),
                            record.getEducation(),
                            record.getDegree(),
                            record.getDegreeTime(),
                            record.getMajor(),
                            record.getSchool(),
                            record.getSchoolType(),
                            record.getArriveTime(),
                            record.getAuthorizedType(),
                            record.getStaffType(),
                            record.getStaffStatus(),
                            record.getPostClass(),
                            record.getPostType(),
                            record.getOnJob(),
                            record.getProPost(),
                            record.getProPostLevel(),
                            record.getTitleLevel(),
                            record.getManageLevel(),
                            record.getOfficeLevel(),
                            record.getPost(),
                            record.getPostLevel(),
                            record.getTalentTitle(),
                            record.getAddress(),
                            record.getMaritalStatus(),
                            record.getEmail(),
                            record.getMobile(),
                            record.getPhone(),
                            record.getIsRetire()+"",
                            DateUtils.formatDate(record.getRetireTime(), DateUtils.YYYY_MM_DD),
                            record.getIsHonorRetire()+"",
                            DateUtils.formatDate(record.getCreateTime(), DateUtils.YYYY_MM_DD_HH_MM_SS)
            };
            valuesList.add(values);
        }
        String fileName = "教职工党员信息_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
        ExportHelper.export(titles, valuesList, fileName, response);
    }
}
