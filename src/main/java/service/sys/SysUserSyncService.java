package service.sys;

import domain.ext.*;
import domain.sys.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import service.BaseMapper;
import service.ext.ExtService;
import service.party.MemberService;
import service.source.ExtAbroadImport;
import service.source.ExtBksImport;
import service.source.ExtJzgImport;
import service.source.ExtYjsImport;
import shiro.PasswordHelper;
import shiro.SaltPassword;
import shiro.ShiroUser;
import sys.constants.SystemConstants;
import sys.utils.DateUtils;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class SysUserSyncService extends BaseMapper {

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    protected PasswordHelper passwordHelper;
    @Autowired
    private ExtService extService;
    @Autowired
    private ExtJzgImport extJzgImport;
    @Autowired
    private ExtBksImport extBksImport;
    @Autowired
    private ExtYjsImport extYjsImport;
    @Autowired
    private ExtAbroadImport extAbroadImport;

    private Logger logger = LoggerFactory.getLogger(getClass());

    public boolean lastSyncIsNotStop(byte type) {

        SysUserSyncExample example = new SysUserSyncExample();
        example.createCriteria()
                .andTypeEqualTo(type).andIsStopEqualTo(false);

        return sysUserSyncMapper.countByExample(example) > 0;
    }

    // 同步教职工党员出国境信息
    public void syncAllAbroad(boolean autoStart) {

        if (lastSyncIsNotStop(SystemConstants.SYNC_TYPE_ABROAD)) {
            throw new RuntimeException("上一次同步仍在进行中");
        }

        // 先从师大导入数据
        try {
            extAbroadImport.excute();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException("学校信息同步出错：" + ex.getMessage());
        }

        int count = extAbroadMapper.countByExample(new ExtAbroadExample());
        int pageSize = 200;
        int pageNo = count / pageSize + (count % pageSize > 0 ? 1 : 0);

        SysUserSync sysUserSync = new SysUserSync();
        if (!autoStart) {
            ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
            sysUserSync.setUserId(shiroUser.getId());
        }
        sysUserSync.setAutoStart(autoStart);
        sysUserSync.setAutoStop(false);
        sysUserSync.setStartTime(new Date());
        sysUserSync.setType(SystemConstants.SYNC_TYPE_ABROAD);
        sysUserSync.setIsStop(false);

        sysUserSync.setCurrentCount(0);
        sysUserSync.setCurrentPage(0);
        sysUserSync.setTotalCount(count);
        sysUserSync.setTotalPage(pageNo);
        sysUserSync.setInsertCount(0);
        sysUserSync.setUpdateCount(0);

        sysUserSync.setEndTime(new Date());
        sysUserSync.setAutoStop(true);
        sysUserSync.setIsStop(true);

        insertSelective(sysUserSync);
    }

    @Transactional
    public int syncExtJzg(ExtJzg extJzg) {

        String code = StringUtils.trim(extJzg.getZgh());
        SysUser record = new SysUser();
        record.setUsername(code);
        record.setCode(code);
        record.setType(SystemConstants.USER_TYPE_JZG);
        record.setSource(SystemConstants.USER_SOURCE_JZG);
        record.setLocked(false);

        int ret = -1;
        SysUserView sysUser = sysUserService.findByCode(code);
        try {
            if (sysUser == null) {
                SaltPassword encrypt = passwordHelper.encryptByRandomSalt(code); // 初始化密码与账号相同
                record.setSalt(encrypt.getSalt());
                record.setPasswd(encrypt.getPassword());
                record.setCreateTime(new Date());
                sysUserService.insertSelective(record);

                sysUser = sysUserService.findByCode(code); // 下面同步时要用
                ret = 1;
            } else {
                record.setId(sysUser.getId());
                sysUserService.updateByPrimaryKeySelective(record, sysUser.getUsername(), sysUser.getCode());
                ret = 0;
            }

            // 同步教职工信息
            snycTeacherInfo(sysUser.getId(), sysUser);
        } catch (Exception ex) {
            logger.error("同步出错", ex);
        }

        return ret;
    }

    // 同步教职工人事库
    public void syncAllJZG(boolean autoStart) {

        if (lastSyncIsNotStop(SystemConstants.SYNC_TYPE_JZG)) {
            throw new RuntimeException("上一次同步仍在进行中");
        }

        SysUserSync sysUserSync = new SysUserSync();
        if (!autoStart) {
            ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
            sysUserSync.setUserId(shiroUser.getId());
        }
        sysUserSync.setAutoStart(autoStart);
        sysUserSync.setAutoStop(false);
        sysUserSync.setStartTime(new Date());
        sysUserSync.setType(SystemConstants.SYNC_TYPE_JZG);
        sysUserSync.setIsStop(false);

        sysUserSync.setCurrentCount(0);
        sysUserSync.setCurrentPage(0);
        //sysUserSync.setTotalCount(count);
        //sysUserSync.setTotalPage(pageNo);
        sysUserSync.setInsertCount(0);
        sysUserSync.setUpdateCount(0);

        insertSelective(sysUserSync);

        // 先从师大导入数据
        try {
            extJzgImport.excute();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException("学校信息同步出错：" + ex.getMessage());
        }

        int insertCount = 0;
        int updateCount = 0;

        int count = extJzgMapper.countByExample(new ExtJzgExample());
        int pageSize = 200;
        int pageNo = count / pageSize + (count % pageSize > 0 ? 1 : 0);

        for (int i = 0; i < pageNo; i++) {
            logger.debug(String.format("总数：%s， 每页%s条， 当前%s页", count, pageSize, pageNo));
            List<ExtJzg> extJzgs = extJzgMapper.selectByExampleWithRowbounds(new ExtJzgExample(), new RowBounds(i * pageSize, pageSize));
            for (ExtJzg extJzg : extJzgs) {

                int ret = -1;
                try {
                    ret = syncExtJzg(extJzg);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                if (ret == 1) insertCount++;
                if (ret == 0) updateCount++;
            }

            SysUserSync record = new SysUserSync();
            record.setId(sysUserSync.getId());
            record.setInsertCount(insertCount);
            record.setUpdateCount(updateCount);
            record.setTotalCount(count);
            record.setTotalPage(pageNo);
            record.setCurrentCount(((i + 1) * pageSize > count) ? count : (i + 1) * pageSize);
            record.setCurrentPage(i + 1);
            try {
                SysUserSync _sync = sysUserSyncMapper.selectByPrimaryKey(sysUserSync.getId());
                if (_sync.getIsStop()) {
                    return; // 强制结束
                }
                updateByPrimaryKeySelective(record);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        SysUserSync record = new SysUserSync();
        record.setId(sysUserSync.getId());
        record.setEndTime(new Date());
        record.setAutoStop(true);
        record.setIsStop(true);
        try {
            updateByPrimaryKeySelective(record);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Transactional
    public int sysExtYjs(ExtYjs extYjs) {
        String code = StringUtils.trim(extYjs.getXh());
        SysUser record = new SysUser();
        record.setUsername(code);
        record.setCode(code);
        record.setType(SystemConstants.USER_TYPE_YJS);
        record.setSource(SystemConstants.USER_SOURCE_YJS);
        record.setLocked(false);

        int ret = -1;
        SysUserView sysUser = sysUserService.findByCode(code);
        try {
            if (sysUser == null) {
                SaltPassword encrypt = passwordHelper.encryptByRandomSalt(code); // 初始化密码与账号相同
                record.setSalt(encrypt.getSalt());
                record.setPasswd(encrypt.getPassword());
                record.setCreateTime(new Date());
                sysUserService.insertSelective(record);

                sysUser = sysUserService.findByCode(code); // 下面同步时要用
                ret = 1;
            } else {
                record.setId(sysUser.getId());
                sysUserService.updateByPrimaryKeySelective(record, sysUser.getUsername(), sysUser.getCode());

                ret = 0;
            }

            // 同步学生信息
            snycStudent(sysUser.getId(), sysUser);
        } catch (Exception ex) {
            logger.error("同步出错", ex);
        }

        return ret;
    }

    // 同步研究生库
    public void syncAllYJS(boolean autoStart) {

        if (lastSyncIsNotStop(SystemConstants.SYNC_TYPE_YJS)) {
            throw new RuntimeException("上一次同步仍在进行中");
        }

        SysUserSync sysUserSync = new SysUserSync();
        if (!autoStart) {
            ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
            sysUserSync.setUserId(shiroUser.getId());
        }
        sysUserSync.setAutoStart(autoStart);
        sysUserSync.setAutoStop(false);
        sysUserSync.setStartTime(new Date());
        sysUserSync.setType(SystemConstants.SYNC_TYPE_YJS);
        sysUserSync.setIsStop(false);

        sysUserSync.setCurrentCount(0);
        sysUserSync.setCurrentPage(0);

        sysUserSync.setInsertCount(0);
        sysUserSync.setUpdateCount(0);

        insertSelective(sysUserSync);

        // 先从师大导入数据
        try {
            extYjsImport.excute();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException("学校信息同步出错：" + ex.getMessage());
        }

        int insertCount = 0;
        int updateCount = 0;

        int count = extYjsMapper.countByExample(new ExtYjsExample());
        int pageSize = 200;
        int pageNo = count / pageSize + (count % pageSize > 0 ? 1 : 0);

        logger.debug(String.format("总数：%s， 每页%s条， 当前%s页", count, pageSize, pageNo));
        for (int i = 0; i < pageNo; i++) {

            List<ExtYjs> extYjss = extYjsMapper.selectByExampleWithRowbounds(new ExtYjsExample(), new RowBounds(i * pageSize, pageSize));
            for (ExtYjs extYjs : extYjss) {

                int ret = -1;
                try {
                    ret = sysExtYjs(extYjs);
                } catch (Exception ex) {

                    ex.printStackTrace();
                }
                if (ret == 1) insertCount++;
                if (ret == 0) updateCount++;
            }

            SysUserSync record = new SysUserSync();
            record.setId(sysUserSync.getId());
            record.setInsertCount(insertCount);
            record.setUpdateCount(updateCount);
            record.setTotalCount(count);
            record.setTotalPage(pageNo);
            record.setCurrentCount(((i + 1) * pageSize > count) ? count : (i + 1) * pageSize);
            record.setCurrentPage(i + 1);
            try {
                SysUserSync _sync = sysUserSyncMapper.selectByPrimaryKey(sysUserSync.getId());
                if (_sync.getIsStop()) {
                    return; // 强制结束
                }
                updateByPrimaryKeySelective(record);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        SysUserSync record = new SysUserSync();
        record.setId(sysUserSync.getId());
        record.setEndTime(new Date());
        record.setAutoStop(true);
        record.setIsStop(true);
        try {
            updateByPrimaryKeySelective(record);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // 返回1：插入 0：更新
    @Transactional
    public int syncExtBks(ExtBks extBks) {
        String code = StringUtils.trim(extBks.getXh());
        SysUser record = new SysUser();
        record.setUsername(code);
        record.setCode(code);
        record.setType(SystemConstants.USER_TYPE_BKS);

        record.setSource(SystemConstants.USER_SOURCE_BKS);
        record.setLocked(false);

        int ret = -1;
        SysUserView sysUser = sysUserService.findByCode(code);
        try {
            if (sysUser == null) {
                SaltPassword encrypt = passwordHelper.encryptByRandomSalt(code); // 初始化密码与账号相同
                record.setSalt(encrypt.getSalt());
                record.setPasswd(encrypt.getPassword());
                record.setCreateTime(new Date());
                sysUserService.insertSelective(record);

                sysUser = sysUserService.findByCode(code); // 下面同步时要用
                ret = 1;
            } else {
                record.setId(sysUser.getId());
                sysUserService.updateByPrimaryKeySelective(record, sysUser.getUsername(), sysUser.getCode());

                ret = 0;
            }

            // 同步学生信息
            snycStudent(sysUser.getId(), sysUser);
        } catch (Exception ex) {
            logger.error("同步出错", ex);
        }

        return ret; // 出错
    }

    // 同步本科生库
    public void syncAllBks(boolean autoStart) {

        if (lastSyncIsNotStop(SystemConstants.SYNC_TYPE_BKS)) {
            throw new RuntimeException("上一次同步仍在进行中");
        }

        SysUserSync sysUserSync = new SysUserSync();
        if (!autoStart) {
            ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
            sysUserSync.setUserId(shiroUser.getId());
        }
        sysUserSync.setAutoStart(autoStart);
        sysUserSync.setAutoStop(false);
        sysUserSync.setStartTime(new Date());
        sysUserSync.setType(SystemConstants.SYNC_TYPE_BKS);
        sysUserSync.setIsStop(false);

        sysUserSync.setCurrentCount(0);
        sysUserSync.setCurrentPage(0);

        sysUserSync.setInsertCount(0);
        sysUserSync.setUpdateCount(0);

        insertSelective(sysUserSync);

        // 先从师大导入数据
        try {
            extBksImport.excute();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException("学校信息同步出错：" + ex.getMessage());
        }

        int insertCount = 0;
        int updateCount = 0;

        int count = extBksMapper.countByExample(new ExtBksExample());
        int pageSize = 200;
        int pageNo = count / pageSize + (count % pageSize > 0 ? 1 : 0);

        logger.debug(String.format("总数：%s， 每页%s条， 当前%s页", count, pageSize, pageNo));
        for (int i = 0; i < pageNo; i++) {

            List<ExtBks> extBkss = extBksMapper.selectByExampleWithRowbounds(new ExtBksExample(), new RowBounds(i * pageSize, pageSize));
            for (ExtBks extBks : extBkss) {

                int ret = -1;
                try {
                    ret = syncExtBks(extBks);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                if (ret == 1) insertCount++;
                if (ret == 0) updateCount++;
            }

            SysUserSync record = new SysUserSync();
            record.setId(sysUserSync.getId());
            record.setInsertCount(insertCount);
            record.setUpdateCount(updateCount);
            record.setTotalCount(count);
            record.setTotalPage(pageNo);
            record.setCurrentCount(((i + 1) * pageSize > count) ? count : (i + 1) * pageSize);
            record.setCurrentPage(i + 1);
            try {
                SysUserSync _sync = sysUserSyncMapper.selectByPrimaryKey(sysUserSync.getId());
                if (_sync.getIsStop()) {
                    return; // 强制结束
                }
                updateByPrimaryKeySelective(record);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        SysUserSync record = new SysUserSync();
        record.setId(sysUserSync.getId());
        record.setEndTime(new Date());
        record.setAutoStop(true);
        record.setIsStop(true);
        try {
            updateByPrimaryKeySelective(record);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }



    // 同步教职工信息
    public  void snycTeacherInfo(int userId, SysUserView uv){

        TeacherInfo teacherInfo = teacherInfoMapper.selectByPrimaryKey(userId);

        String code = uv.getCode();
        // 教工信息
        TeacherInfo teacher = new TeacherInfo();
        teacher.setUserId(userId);
        teacher.setIsRetire(false); // 此值不能为空
        teacher.setCreateTime(new Date());

        ExtJzg extJzg = extService.getExtJzg(code);
        if(extJzg!=null){

            SysUserInfo sysUserInfo = sysUserInfoMapper.selectByPrimaryKey(userId);

            // 基本信息
            SysUserInfo ui = new SysUserInfo();
            ui.setUserId(userId);

            ui.setRealname(StringUtils.defaultString(StringUtils.trimToNull(extJzg.getXm()),
                    StringUtils.trim(extJzg.getXmpy())));
            ui.setGender(NumberUtils.toByte(extJzg.getXbm()));
            ui.setBirth(extJzg.getCsrq());
            ui.setIdcard(StringUtils.trim(extJzg.getSfzh()));
            ui.setNativePlace(extJzg.getJg());
            ui.setNation(extJzg.getMz());
            ui.setEmail(extJzg.getDzxx());

            // 手机号码为空才同步20161102  （手机号不同步人事库 20160616修改）
            /*if(sysUserInfo==null || StringUtils.isBlank(sysUserInfo.getMobile()))
                ui.setMobile(extJzg.getYddh());

            ui.setHomePhone(extJzg.getJtdh());*/  // 不同步手机号和办公电话、家庭电话20161116

            //+++++++++++++ 同步后面一系列属性

            teacher.setEducation(extJzg.getZhxlmc());
            teacher.setDegree(extJzg.getZhxw());
            //teacher.setDegreeTime(); 学位授予日期
            //teacher.setMajor(extJzg.getz); 所学专业
            teacher.setSchool(extJzg.getXlbyxx());
            //teacher.setSchoolType(); 毕业学校类型
            if(extJzg.getLxrq()!=null)
                teacher.setArriveTime(DateUtils.formatDate(extJzg.getLxrq(), DateUtils.YYYY_MM_DD));
            teacher.setAuthorizedType(extJzg.getBzlx());
            teacher.setStaffType(extJzg.getRylx());
            teacher.setStaffStatus(extJzg.getRyzt());
            teacher.setPostClass(extJzg.getGwlb());
            teacher.setPostType(extJzg.getGwjb());
            teacher.setOnJob(extJzg.getSfzg());
            //teacher.setProPost(); 专业技术职务
            teacher.setProPostLevel(extJzg.getZjgwdj());
            teacher.setTitleLevel(extJzg.getZc()); // 职称级别
            teacher.setManageLevel(extJzg.getGlgwdj());
            //teacher.setOfficeLevel(extJzg.getgq);  工勤岗位等级
            //teacher.setPost(extJzg.getXzjb());  行政职务
            // teacher.setPostLevel(); 任职级别
            teacher.setTalentTitle(extJzg.getRcch());
            // teacher.setAddress(extJzg.getjz); 居住地址
            // teacher.setMaritalStatus(); 婚姻状况

            // 是否退休 :在岗，退休，病休，离校，待聘,内退,离休, NULL
            //teacher.setIsRetire(!StringUtils.equals(extJzg.getSfzg(), "在岗"));

            // 人员状态：在职、离退、离校、离世、NULL
            teacher.setIsRetire(StringUtils.equals(extJzg.getRyzt(), "离退")
                    || StringUtils.equals(extJzg.getSfzg(), "离休") || StringUtils.equals(extJzg.getSfzg(), "内退")
                    || StringUtils.equals(extJzg.getSfzg(), "退休"));

            //teacher.setRetireTime(); 退休时间
            teacher.setIsHonorRetire(StringUtils.equals(extJzg.getSfzg(), "离休"));

            if(sysUserInfo==null)
                sysUserInfoMapper.insertSelective(ui);
            else
                sysUserInfoMapper.updateByPrimaryKeySelective(ui);
        }

        if(teacherInfo==null)
            teacherInfoMapper.insertSelective(teacher);
        else
            teacherInfoMapper.updateByPrimaryKeySelective(teacher);
    }

    // 同步学生党员信息
    public void snycStudent(int userId, SysUserView uv){

        StudentInfo studentInfo = studentInfoMapper.selectByPrimaryKey(userId);

        String code = uv.getCode();
        StudentInfo student = new StudentInfo();
        student.setUserId(userId);
        student.setCreateTime(new Date());
        byte userType = uv.getType();

        if(userType ==SystemConstants.USER_TYPE_BKS){  // 同步本科生信息
            ExtBks extBks = extService.getExtBks(code);
            if(extBks!=null){

                SysUserInfo sysUserInfo = sysUserInfoMapper.selectByPrimaryKey(userId);
                SysUserInfo ui = new SysUserInfo();
                ui.setUserId(userId);

                ui.setRealname(StringUtils.defaultString(StringUtils.trimToNull(extBks.getXm()),
                        StringUtils.trim(extBks.getXmpy())));

                if (StringUtils.equalsIgnoreCase(extBks.getXb(), "男"))
                    ui.setGender(SystemConstants.GENDER_MALE);
                else if (StringUtils.equalsIgnoreCase(extBks.getXb(), "女"))
                    ui.setGender(SystemConstants.GENDER_FEMALE);
                else
                    ui.setGender(SystemConstants.GENDER_UNKNOWN);

                if (StringUtils.isNotBlank(extBks.getCsrq()))
                    ui.setBirth(DateUtils.parseDate(extBks.getCsrq(), "yyyy-MM-dd"));
                ui.setIdcard(StringUtils.trim(extBks.getSfzh()));
                //ui.setMobile(StringUtils.trim(extBks.getYddh()));
                //ui.setEmail(StringUtils.trim(extBks.getDzxx()));
                ui.setNation(extBks.getMz());
                ui.setNativePlace(extBks.getSf()); // 籍贯
                ui.setIdcard(extBks.getSfzh());

                //+++++++++++++ 同步后面一系列属性
                student.setType(extBks.getKslb());
                //student.setEduLevel(extBks.getPycc()); 培养层次
                //student.setEduType(extBks.getPylx()); 培养类型
                //student.setEduCategory(extBks.getJylb()); 培养级别
                student.setEduWay(extBks.getPyfs());
                //student.setIsFullTime(extBks.get); 是否全日制
                //student.setEnrolYear(extBks.getZsnd()+""); 招生年度
                //student.setPeriod(extBks.getXz()+""); 学制
                student.setGrade(extBks.getNj());
                //student.setActualEnrolTime(DateUtils.parseDate(extBks.getSjrxny(), DateUtils.YYYYMMDD)); 实际入学年月
                //student.setExpectGraduateTime(DateUtils.parseDate(extBks.getYjbyny(), "yyyyMM")); 预计毕业年月
                //student.setDelayYear(extBks.getYqbynx()); 预计毕业年限
                //student.setActualGraduateTime(DateUtils.parseDate(extBks.getSjbyny(), "yyyyMM")); 实际毕业年月
                student.setSyncSource(SystemConstants.USER_SOURCE_BKS);
                student.setXjStatus(extBks.getXjbd());

                if(sysUserInfo==null)
                    sysUserInfoMapper.insertSelective(ui);
                else
                    sysUserInfoMapper.updateByPrimaryKeySelective(ui);
            }
        }

        if(userType==SystemConstants.USER_TYPE_YJS){  // 同步研究生信息
            ExtYjs extYjs = extService.getExtYjs(code);
            if(extYjs!=null){

                SysUserInfo sysUserInfo = sysUserInfoMapper.selectByPrimaryKey(userId);
                SysUserInfo ui = new SysUserInfo();
                ui.setUserId(userId);

                ui.setRealname(StringUtils.defaultString(StringUtils.trimToNull(extYjs.getXm()),
                        StringUtils.trim(extYjs.getXmpy())));
                ui.setGender(NumberUtils.toByte(extYjs.getXbm()));
                if (StringUtils.isNotBlank(extYjs.getCsrq()))
                    ui.setBirth(DateUtils.parseDate(StringUtils.substring(extYjs.getCsrq(), 0, 8), "yyyyMMdd"));
                ui.setIdcard(StringUtils.trim(extYjs.getSfzh()));
                //ui.setMobile(StringUtils.trim(extYjs.getYddh()));
                //ui.setEmail(StringUtils.trim(extYjs.getDzxx()));
                ui.setNation(extYjs.getMz());
                //ui.setNativePlace(extYjs.get); 籍贯
                ui.setIdcard(extYjs.getSfzh());

                //+++++++++++++ 同步后面一系列属性
                student.setType(extYjs.getXslb());
                student.setEduLevel(extYjs.getPycc());
                student.setEduType(extYjs.getPylx());
                student.setEduCategory(extYjs.getJylb());
                student.setEduWay(extYjs.getPyfs());
                //student.setIsFullTime(extYjs.get); 是否全日制
                student.setEnrolYear(extYjs.getZsnd()+"");
                student.setPeriod(extYjs.getXz() + "");
                student.setGrade(extYjs.getNj() + "");

                student.setActualEnrolTime(DateUtils.parseDate(StringUtils.substring(extYjs.getSjrxny(), 0, 6), "yyyyMM"));
                student.setExpectGraduateTime(DateUtils.parseDate(StringUtils.substring(extYjs.getYjbyny(), 0, 6), "yyyyMM"));
                student.setDelayYear(extYjs.getYqbynx());
                student.setActualGraduateTime(DateUtils.parseDate(StringUtils.substring(extYjs.getSjbyny(), 0, 6), "yyyyMM"));
                student.setSyncSource(SystemConstants.USER_SOURCE_YJS);
                student.setXjStatus(extYjs.getZt());

                if(sysUserInfo==null)
                    sysUserInfoMapper.insertSelective(ui);
                else
                    sysUserInfoMapper.updateByPrimaryKeySelective(ui);
            }
        }

        if(studentInfo==null)
            studentInfoMapper.insertSelective(student);
        else
            studentInfoMapper.updateByPrimaryKeySelective(student);
    }

    @Transactional
    public int insertSelective(SysUserSync record) {

        Assert.isTrue(!lastSyncIsNotStop(record.getType()));
        return sysUserSyncMapper.insertSelective(record);
    }

    @Transactional
    public void del(Integer id) {

        sysUserSyncMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids) {

        if (ids == null || ids.length == 0) return;

        SysUserSyncExample example = new SysUserSyncExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        sysUserSyncMapper.deleteByExample(example);
    }

    @Transactional
    public int updateByPrimaryKeySelective(SysUserSync record) {
        return sysUserSyncMapper.updateByPrimaryKeySelective(record);
    }
}
