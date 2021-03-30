package ext.service;

import controller.global.OpException;
import domain.sys.*;
import ext.domain.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import persistence.sys.StudentInfoMapper;
import persistence.sys.TeacherInfoMapper;
import service.BaseMapper;
import service.SpringProps;
import service.sys.SysSyncService;
import service.sys.SysUserService;
import shiro.PasswordHelper;
import shiro.ShiroHelper;
import sys.constants.RoleConstants;
import sys.constants.SystemConstants;
import sys.shiro.SaltPassword;
import sys.tags.CmTag;
import sys.utils.DateUtils;
import sys.utils.SqlUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SyncService extends BaseMapper {

    @Autowired
    private ExtCommonService extCommonService;
    @Autowired
    private SysSyncService sysSyncService;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    protected SpringProps springProps;
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
    @Autowired
    private ExtRetireSalaryImport extRetireSalaryImport;
    @Autowired
    private ExtJzgSalaryImport extJzgSalaryImport;
    @Autowired(required = false)
    private TeacherInfoMapper teacherInfoMapper;
    @Autowired(required = false)
    private StudentInfoMapper studentInfoMapper;

    private Logger logger = LoggerFactory.getLogger(getClass());

    // 同步在职工资信息
    public synchronized void syncJzgSalary(boolean autoStart) {

        if (sysSyncService.lastSyncIsNotStop(SystemConstants.SYNC_TYPE_JZG_SALARY)) {
            sysSyncService.stopAll(SystemConstants.SYNC_TYPE_JZG_SALARY);
            logger.info("系统自动停止上一次同步任务:" + SystemConstants.SYNC_TYPE_MAP.get(SystemConstants.SYNC_TYPE_JZG_SALARY));
        }

        SysSync sysSync = new SysSync();
        if (!autoStart) {
            sysSync.setUserId(ShiroHelper.getCurrentUserId());
        }
        sysSync.setAutoStart(autoStart);
        sysSync.setAutoStop(false);
        sysSync.setStartTime(new Date());
        sysSync.setType(SystemConstants.SYNC_TYPE_JZG_SALARY);
        sysSync.setIsStop(false);

        sysSync.setCurrentCount(0);
        sysSync.setCurrentPage(0);

        sysSync.setInsertCount(0);
        sysSync.setUpdateCount(0);

        sysSyncService.insertSelective(sysSync);

        int syncId = sysSync.getId();

        // 先从学校导入数据
        int ret = 0;
        try {
            ret = extJzgSalaryImport.excute(syncId);
        } catch (Exception ex) {
            logger.error("异常", ex);
            throw new OpException("教职工工资信息同步出错：" + ex.getMessage());
        }

        SysSync record = new SysSync();
        record.setId(syncId);
        record.setTotalCount(ret);

        record.setEndTime(new Date());
        record.setAutoStop(true);
        record.setIsStop(true);

        sysSyncService.updateByPrimaryKeySelective(record);
    }

    // 同步离退休工资信息
    public synchronized void syncRetireSalary(boolean autoStart) {

        if (sysSyncService.lastSyncIsNotStop(SystemConstants.SYNC_TYPE_RETIRE_SALARY)) {
            sysSyncService.stopAll(SystemConstants.SYNC_TYPE_RETIRE_SALARY);
            logger.info("系统自动停止上一次同步任务:" + SystemConstants.SYNC_TYPE_MAP.get(SystemConstants.SYNC_TYPE_RETIRE_SALARY));
        }
        SysSync sysSync = new SysSync();
        if (!autoStart) {
            sysSync.setUserId(ShiroHelper.getCurrentUserId());
        }
        sysSync.setAutoStart(autoStart);
        sysSync.setAutoStop(false);
        sysSync.setStartTime(new Date());
        sysSync.setType(SystemConstants.SYNC_TYPE_RETIRE_SALARY);
        sysSync.setIsStop(false);

        sysSync.setCurrentCount(0);
        sysSync.setCurrentPage(0);
        sysSync.setInsertCount(0);
        sysSync.setUpdateCount(0);

        sysSyncService.insertSelective(sysSync);

        int syncId = sysSync.getId();
        // 先从学校导入数据
        int ret = 0;
        try {
            ret = extRetireSalaryImport.excute(syncId);
        } catch (Exception ex) {
            logger.error("异常", ex);
            throw new OpException("离退休工资信息同步出错：" + ex.getMessage());
        }

        SysSync record = new SysSync();
        record.setId(syncId);
        record.setTotalCount(ret);
        record.setEndTime(new Date());
        record.setAutoStop(true);
        record.setIsStop(true);

        sysSyncService.updateByPrimaryKeySelective(record);
    }

    // 同步教职工党员出国境信息
    public synchronized void syncAllAbroad(boolean autoStart) {

        if (sysSyncService.lastSyncIsNotStop(SystemConstants.SYNC_TYPE_ABROAD)) {
            sysSyncService.stopAll(SystemConstants.SYNC_TYPE_ABROAD);
            logger.info("系统自动停止上一次同步任务:" + SystemConstants.SYNC_TYPE_MAP.get(SystemConstants.SYNC_TYPE_ABROAD));
        }

        SysSync sysSync = new SysSync();
        if (!autoStart) {
            sysSync.setUserId(ShiroHelper.getCurrentUserId());
        }
        sysSync.setAutoStart(autoStart);
        sysSync.setAutoStop(false);
        sysSync.setStartTime(new Date());
        sysSync.setType(SystemConstants.SYNC_TYPE_ABROAD);
        sysSync.setIsStop(false);

        sysSync.setCurrentCount(0);
        sysSync.setCurrentPage(0);

        sysSync.setInsertCount(0);
        sysSync.setUpdateCount(0);

        sysSyncService.insertSelective(sysSync);

        int syncId = sysSync.getId();

        // 先从学校导入数据
        try {
            extAbroadImport.excute(syncId);
        } catch (Exception ex) {
            logger.error("异常", ex);
            throw new OpException("学校信息同步出错：" + ex.getMessage());
        }

        int count = (int) extAbroadMapper.countByExample(new ExtAbroadExample());
        int pageSize = 200;
        int pageNo = count / pageSize + (count % pageSize > 0 ? 1 : 0);

        SysSync record = new SysSync();
        record.setId(syncId);

        record.setTotalCount(count);
        record.setTotalPage(pageNo);

        record.setEndTime(new Date());
        record.setAutoStop(true);
        record.setIsStop(true);
        try {
            sysSyncService.updateByPrimaryKeySelective(record);
        } catch (Exception ex) {
            logger.error("异常", ex);
        }
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
        SysUserView sysUser = null;
        List<SysUserView> uvs = sysUserService.dbFindByUsernameOrCode(code);
        if(uvs.size()>1){
            logger.error("账号同步异常，用户名、学工号重复：" + code);
            return ret;
        }else if(uvs.size()==1){
            sysUser = uvs.get(0); // 账号或学工号查到了，都覆盖更新？
        }
        try {
            if (sysUser == null) {
                SaltPassword encrypt = passwordHelper.encryptByRandomSalt(code); // 初始化密码与账号相同
                record.setSalt(encrypt.getSalt());
                record.setPasswd(encrypt.getPassword());
                record.setCreateTime(new Date());
                sysUserService.insertSelective(record);

                sysUser = sysUserService.dbFindByCode(code); // 下面同步时要用
                ret = 1;
            } else {
                record.setId(sysUser.getId());
                sysUserService.updateByPrimaryKeySelective(record);
                ret = 0;
            }

            // 同步教职工信息
            snycTeacherInfo(sysUser.getId(), code);
        } catch (Exception ex) {
            logger.error("同步出错", ex);
        }

        sysUserService.addRole(record.getId(), RoleConstants.ROLE_TEACHER);
        return ret;
    }

    // 同步教职工人事库
    @Async
    public synchronized void syncAllJZG(boolean autoStart) {

        if (sysSyncService.lastSyncIsNotStop(SystemConstants.SYNC_TYPE_JZG)) {
            sysSyncService.stopAll(SystemConstants.SYNC_TYPE_JZG);
            logger.info("系统自动停止上一次同步任务:" + SystemConstants.SYNC_TYPE_MAP.get(SystemConstants.SYNC_TYPE_JZG));
        }

        SysSync sysSync = new SysSync();
        if (!autoStart) {
            sysSync.setUserId(ShiroHelper.getCurrentUserId());
        }
        sysSync.setAutoStart(autoStart);
        sysSync.setAutoStop(false);
        sysSync.setStartTime(new Date());
        sysSync.setType(SystemConstants.SYNC_TYPE_JZG);
        sysSync.setIsStop(false);

        sysSync.setCurrentCount(0);
        sysSync.setCurrentPage(0);
        //sysSync.setTotalCount(count);
        //sysSync.setTotalPage(pageNo);
        sysSync.setInsertCount(0);
        sysSync.setUpdateCount(0);

        sysSyncService.insertSelective(sysSync);

        int syncId = sysSync.getId();

        // 先从学校导入数据
        try {
            extJzgImport.excute(syncId);
        } catch (Exception ex) {
            logger.error("异常", ex);
            throw new OpException("学校信息同步出错：" + ex.getMessage());
        }

        int insertCount = 0;
        int updateCount = 0;

        int count = (int) extJzgMapper.countByExample(new ExtJzgExample());
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
                    logger.error("异常", ex);
                }
                if (ret == 1) insertCount++;
                if (ret == 0) updateCount++;
            }

            SysSync record = new SysSync();
            record.setId(syncId);
            record.setInsertCount(insertCount);
            record.setUpdateCount(updateCount);
            record.setTotalCount(count);
            record.setTotalPage(pageNo);
            //record.setCurrentCount(((i + 1) * pageSize > count) ? count : (i + 1) * pageSize);
            record.setCurrentPage(i + 1);
            try {
                if (sysSyncService.isStop(sysSync.getId())) {
                    return; // 强制结束
                }
                sysSyncService.updateByPrimaryKeySelective(record);
            } catch (Exception ex) {
                logger.error("异常", ex);
            }
        }

        SysSync record = new SysSync();
        record.setId(sysSync.getId());
        record.setEndTime(new Date());
        record.setAutoStop(true);
        record.setIsStop(true);
        try {
            sysSyncService.updateByPrimaryKeySelective(record);
        } catch (Exception ex) {
            logger.error("异常", ex);
        }
    }

    @Transactional
    public int sysExtYjs(ExtYjs extYjs) {

        String code = StringUtils.trim(extYjs.getXh());
        SysUser record = new SysUser();
        record.setUsername(code);
        record.setCode(code);
        if(StringUtils.contains(extYjs.getPycc(), "博士")){
            record.setType(SystemConstants.USER_TYPE_BS);
        }else{
            record.setType(SystemConstants.USER_TYPE_SS);
        }
        record.setSource(SystemConstants.USER_SOURCE_YJS);
        record.setLocked(false);

        int ret = -1;
        SysUserView sysUser = null;
        List<SysUserView> uvs = sysUserService.dbFindByUsernameOrCode(code);
        if(uvs.size()>1){
            logger.error("账号同步异常，用户名、学工号重复：" + code);
            return ret;
        }else if(uvs.size()==1){
            sysUser = uvs.get(0); // 账号或学工号查到了，都覆盖更新？
        }
        try {
            if (sysUser == null) {
                SaltPassword encrypt = passwordHelper.encryptByRandomSalt(code); // 初始化密码与账号相同
                record.setSalt(encrypt.getSalt());
                record.setPasswd(encrypt.getPassword());
                record.setCreateTime(new Date());
                sysUserService.insertSelective(record);

                sysUser = sysUserService.dbFindByCode(code); // 下面同步时要用
                ret = 1;
            } else {
                record.setId(sysUser.getId());
                sysUserService.updateByPrimaryKeySelective(record);

                ret = 0;
            }

            // 同步学生信息
            snycStudent(sysUser.getId(), code, record.getType());
        } catch (Exception ex) {
            logger.error("同步出错", ex);
        }

        return ret;
    }

    // 同步研究生库
    @Async
    public synchronized void syncAllYJS(boolean autoStart) {

        if (sysSyncService.lastSyncIsNotStop(SystemConstants.SYNC_TYPE_YJS)) {
            sysSyncService.stopAll(SystemConstants.SYNC_TYPE_YJS);
            logger.info("系统自动停止上一次同步任务:" + SystemConstants.SYNC_TYPE_MAP.get(SystemConstants.SYNC_TYPE_YJS));
        }

        SysSync sysSync = new SysSync();
        if (!autoStart) {
            sysSync.setUserId(ShiroHelper.getCurrentUserId());
        }
        sysSync.setAutoStart(autoStart);
        sysSync.setAutoStop(false);
        sysSync.setStartTime(new Date());
        sysSync.setType(SystemConstants.SYNC_TYPE_YJS);
        sysSync.setIsStop(false);

        sysSync.setCurrentCount(0);
        sysSync.setCurrentPage(0);

        sysSync.setInsertCount(0);
        sysSync.setUpdateCount(0);

        sysSyncService.insertSelective(sysSync);

        int syncId = sysSync.getId();

        // 先从学校导入数据
        try {
            extYjsImport.excute(syncId);
        } catch (Exception ex) {
            logger.error("异常", ex);
            throw new OpException("学校信息同步出错：" + ex.getMessage());
        }

        int insertCount = 0;
        int updateCount = 0;

        int count = (int) extYjsMapper.countByExample(new ExtYjsExample());
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

                    logger.error("异常", ex);
                }
                if (ret == 1) insertCount++;
                if (ret == 0) updateCount++;
            }

            SysSync record = new SysSync();
            record.setId(syncId);
            record.setInsertCount(insertCount);
            record.setUpdateCount(updateCount);
            record.setTotalCount(count);
            record.setTotalPage(pageNo);
            //record.setCurrentCount(((i + 1) * pageSize > count) ? count : (i + 1) * pageSize);
            record.setCurrentPage(i + 1);
            try {
                if (sysSyncService.isStop(sysSync.getId())) {
                    return; // 强制结束
                }
                sysSyncService.updateByPrimaryKeySelective(record);
            } catch (Exception ex) {
                logger.error("异常", ex);
            }
        }

        SysSync record = new SysSync();
        record.setId(sysSync.getId());
        record.setEndTime(new Date());
        record.setAutoStop(true);
        record.setIsStop(true);
        try {
            sysSyncService.updateByPrimaryKeySelective(record);
        } catch (Exception ex) {
            logger.error("异常", ex);
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
        SysUserView sysUser = null;
        List<SysUserView> uvs = sysUserService.dbFindByUsernameOrCode(code);
        if(uvs.size()>1){
            logger.error("账号同步异常，用户名、学工号重复：" + code);
            return ret;
        }else if(uvs.size()==1){
            sysUser = uvs.get(0); // 账号或学工号查到了，都覆盖更新？
        }
        try {
            if (sysUser == null) {
                SaltPassword encrypt = passwordHelper.encryptByRandomSalt(code); // 初始化密码与账号相同
                record.setSalt(encrypt.getSalt());
                record.setPasswd(encrypt.getPassword());
                record.setCreateTime(new Date());
                sysUserService.insertSelective(record);

                sysUser = sysUserService.dbFindByCode(code); // 下面同步时要用
                ret = 1;
            } else {
                record.setId(sysUser.getId());
                sysUserService.updateByPrimaryKeySelective(record);

                ret = 0;
            }

            // 同步学生信息
            snycStudent(sysUser.getId(), code, record.getType());
        } catch (Exception ex) {
            logger.error("同步出错", ex);
        }

        return ret; // 出错
    }

    // 同步本科生库
    @Async
    public synchronized void syncAllBks(boolean autoStart) {

        if (sysSyncService.lastSyncIsNotStop(SystemConstants.SYNC_TYPE_BKS)) {

            sysSyncService.stopAll(SystemConstants.SYNC_TYPE_BKS);
            logger.info("系统自动停止上一次同步任务:" + SystemConstants.SYNC_TYPE_MAP.get(SystemConstants.SYNC_TYPE_BKS));
        }

        SysSync sysSync = new SysSync();
        if (!autoStart) {
            sysSync.setUserId(ShiroHelper.getCurrentUserId());
        }
        sysSync.setAutoStart(autoStart);
        sysSync.setAutoStop(false);
        sysSync.setStartTime(new Date());
        sysSync.setType(SystemConstants.SYNC_TYPE_BKS);
        sysSync.setIsStop(false);

        sysSync.setCurrentCount(0);
        sysSync.setCurrentPage(0);

        sysSync.setInsertCount(0);
        sysSync.setUpdateCount(0);

        sysSyncService.insertSelective(sysSync);

        int syncId = sysSync.getId();

        // 先从学校导入数据
        try {
            extBksImport.excute(syncId);
        } catch (Exception ex) {
            logger.error("异常", ex);
            throw new OpException("学校信息同步出错：" + ex.getMessage());
        }

        int insertCount = 0;
        int updateCount = 0;

        int count = (int) extBksMapper.countByExample(new ExtBksExample());
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
                    logger.error("异常", ex);
                }
                if (ret == 1) insertCount++;
                if (ret == 0) updateCount++;
            }

            SysSync record = new SysSync();
            record.setId(syncId);
            record.setInsertCount(insertCount);
            record.setUpdateCount(updateCount);
            record.setTotalCount(count);
            record.setTotalPage(pageNo);
            //record.setCurrentCount(((i + 1) * pageSize > count) ? count : (i + 1) * pageSize);
            record.setCurrentPage(i + 1);
            try {
                if (sysSyncService.isStop(sysSync.getId())) {
                    return; // 强制结束
                }
                sysSyncService.updateByPrimaryKeySelective(record);
            } catch (Exception ex) {
                logger.error("异常", ex);
            }
        }

        SysSync record = new SysSync();
        record.setId(sysSync.getId());
        record.setEndTime(new Date());
        record.setAutoStop(true);
        record.setIsStop(true);
        try {
            sysSyncService.updateByPrimaryKeySelective(record);
        } catch (Exception ex) {
            logger.error("异常", ex);
        }
    }

    // 同步教职工信息
    public void snycTeacherInfo(int userId, String code) {

        SysUserView uv = CmTag.getUserByCode(code);
        if(!uv.isCasUser()) return;

        TeacherInfo teacherInfo = teacherInfoMapper.selectByPrimaryKey(userId);

        //String code = uv.getCode();
        // 教工信息
        TeacherInfo record = new TeacherInfo();
        record.setUserId(userId);
        record.setIsTemp("0"); // 防止没更新字段而报错
        ExtJzg extJzg = extService.getExtJzg(code);
        if (extJzg != null) {

            // 基本信息
            SysUserInfo ui = new SysUserInfo();
            ui.setUserId(userId);

            ui.setRealname(StringUtils.defaultString(StringUtils.trimToNull(extJzg.getXm()),
                    StringUtils.trim(extJzg.getXmpy())));
            ui.setGender(NumberUtils.toByte(extJzg.getXbm()));
            ui.setBirth(extJzg.getCsrq());
            ui.setIdcardType(extJzg.getName());
            ui.setIdcard(StringUtils.trim(extJzg.getSfzh()));
            ui.setNativePlace(extJzg.getJg());
            ui.setNation(extCommonService.formatNation(extJzg.getMz()));
            ui.setCountry(extJzg.getGj());
            ui.setUnit(extJzg.getDwmc());
            ui.setEmail(extJzg.getDzxx());
            ui.setMobile(extJzg.getYddh());
            ui.setHomePhone(extJzg.getJtdh());

            //+++++++++++++ 同步后面一系列属性

            record.setExtPhone(extJzg.getYddh());
            record.setEducation(extJzg.getZhxlmc());
            record.setDegree(extJzg.getZhxw());
            //teacher.setDegreeTime(); 学位授予日期
            //teacher.setMajor(extJzg.getz); 所学专业
            record.setSchool(extJzg.getXlbyxx()); // 学历毕业学校
            record.setDegreeSchool(extJzg.getXwsyxx()); // 学位授予学校
            //teacher.setSchoolType(); 毕业学校类型
            if (extJzg.getLxrq() != null)
                record.setArriveTime(extJzg.getLxrq());
            record.setAuthorizedType(extJzg.getBzlx());
            record.setStaffType(extJzg.getRylx());
            record.setStaffStatus(StringUtils.equals(extJzg.getSfzg(), "离休")?"离休":extJzg.getRyzt()); // 离退
            record.setPostClass(extJzg.getGwlx()); // 岗位类型
            record.setSubPostClass(extJzg.getGwzlbmc()); // 岗位子类别
            record.setMainPostLevel(extJzg.getZgdjmmc()); // 主岗等级
            if (StringUtils.isNotBlank(extJzg.getGlqsrq())) // 工龄起算日期
                record.setWorkStartTime(DateUtils.parseStringToDate(extJzg.getGlqsrq()));
            record.setWorkBreak(extJzg.getJdgl()); // 间断工龄
            if (StringUtils.isNotBlank(extJzg.getZzdjsj())) // 转正定级时间
                record.setRegularTime(DateUtils.parseStringToDate(extJzg.getZzdjsj()));
            record.setOnJob(extJzg.getSfzg());
            record.setPersonnelStatus(extJzg.getRszf());
            record.setProPost(extJzg.getZc()); // 职称？？
            //teacher.setTitleLevel(extJzg.get); // 职称级别
            //teacher.setPost(extJzg.getXzjb());  行政职务
            // teacher.setPostLevel(); 任职级别
            record.setTalentTitle(extJzg.getRcch());
            record.setTalentType(extJzg.getRclx());
            // teacher.setAddress(extJzg.getjz); 居住地址
            // teacher.setMaritalStatus(); 婚姻状况

            // 状态已经变更为退休状态的，不再同步人事库
            if(!uv.isRetire()){
                if(StringUtils.containsAny(record.getStaffStatus(),
                        "离退", "内退", "退休", "离休", "离世")){

                    sysUserService.updateUserType(uv.getId(), SystemConstants.USER_TYPE_RETIRE);
                }
            }

            //teacher.setRetireTime(); 退休时间
            //record.setIsHonorRetire(StringUtils.equals(extJzg.getSfzg(), "离休"));

            ui.setUserStatus(StringUtils.defaultIfEmpty(record.getStaffStatus(), extJzg.getSfzg()));
            extCommonService.syncFilter(ui, teacherInfo==null?null:record, null);
            sysUserService.insertOrUpdateUserInfoSelective(ui);
        }

        if (teacherInfo == null)
            teacherInfoMapper.insertSelective(record);
        else if(extJzg!=null) {
            teacherInfoMapper.updateByPrimaryKeySelective(record);
        }

        // 干部档案页默认同步人事库信息，不启用系统本身的岗位过程信息； 如果是系统注册账号，则不同步人事库信息
        if (!CmTag.getBoolProperty("useCadrePost") && extJzg != null) {

            String proPost = extJzg.getZc();
            if(teacherInfo!=null && StringUtils.isBlank(proPost)){ // 保证下面的职称只同步一次，且不被空值覆盖
                proPost = teacherInfo.getProPost();
            }
            proPost = SqlUtils.toParamValue(proPost);

            String proPostTime = SqlUtils.toParamValue(DateUtils.formatDate(DateUtils.parseStringToDate(extJzg.getZyjszwpdsj()),
                    DateUtils.YYYY_MM_DD));

            String proPostLevel = extJzg.getZjgwdj();
            if(teacherInfo!=null && StringUtils.isBlank(proPostLevel)){ // 保证下面的职称级别只同步一次，且不被空值覆盖
                proPostLevel = teacherInfo.getProPostLevel();
            }
            proPostLevel = SqlUtils.toParamValue(ExtCommonService.formatProPostLevel(proPostLevel));

            String proPostLevelTime = SqlUtils.toParamValue(DateUtils.formatDate(DateUtils.parseStringToDate(extJzg.getZjgwfjsj()),
                    DateUtils.YYYY_MM_DD));
            String manageLevel = SqlUtils.toParamValue(extJzg.getGlgwdj());
            String manageLevelTime = SqlUtils.toParamValue(DateUtils.formatDate(DateUtils.parseStringToDate(extJzg.getGlgwfjsj()),
                    DateUtils.YYYY_MM_DD));
            String officeLevel = SqlUtils.toParamValue(extJzg.getGqgwdjmc());
            String officeLevelTime = SqlUtils.toParamValue(DateUtils.formatDate(DateUtils.parseStringToDate(extJzg.getGqgwfjsj()),
                    DateUtils.YYYY_MM_DD));

            commonMapper.excuteSql(String.format("update sys_teacher_info set pro_post=%s, " +
                            "pro_post_time=%s, pro_post_level=%s, pro_post_level_time=%s," +
                            "manage_level=%s, manage_level_time=%s, office_level=%s,office_level_time=%s where user_id=%s",
                    proPost, proPostTime, proPostLevel, proPostLevelTime, manageLevel, manageLevelTime,
                    officeLevel, officeLevelTime, userId));

        }
    }

    // 同步学生党员信息
    public void snycStudent(int userId, String code, byte userType) {

        SysUserView uv = CmTag.getUserByCode(code);
        if(!uv.isCasUser()) return;

        StudentInfo studentInfo = studentInfoMapper.selectByPrimaryKey(userId);

        StudentInfo record = new StudentInfo();
        record.setUserId(userId);
        record.setCreateTime(new Date());

        if (userType == SystemConstants.USER_TYPE_BKS) {  // 同步本科生信息

            record.setSyncSource(SystemConstants.USER_SOURCE_BKS);

            ExtBks extBks = extService.getExtBks(code);
            if (extBks != null) {

                //SysUserInfo sysUserInfo = sysUserInfoMapper.selectByPrimaryKey(userId);
                SysUserInfo ui = new SysUserInfo();
                ui.setUserId(userId);

                ui.setRealname(StringUtils.defaultString(StringUtils.trimToNull(extBks.getXm()),
                        StringUtils.trim(extBks.getXmpy())));

                if (StringUtils.contains(extBks.getXb(), "男"))
                    ui.setGender(SystemConstants.GENDER_MALE);
                else if (StringUtils.contains(extBks.getXb(), "女"))
                    ui.setGender(SystemConstants.GENDER_FEMALE);

                if (StringUtils.isNotBlank(extBks.getCsrq()))
                    ui.setBirth(DateUtils.parseStringToDate(extBks.getCsrq()));
                ui.setIdcard(StringUtils.trim(extBks.getSfzh()));
                //ui.setMobile(StringUtils.trim(extBks.getYddh()));
                //ui.setEmail(StringUtils.trim(extBks.getDzxx()));
                ui.setNation(extCommonService.formatNation(extBks.getMz()));
                ui.setNativePlace(extBks.getSf()); // 籍贯

                //+++++++++++++ 同步后面一系列属性
                record.setType(extBks.getKslb());
                //student.setEduLevel(extBks.getPycc()); 培养层次
                //student.setEduType(extBks.getPylx()); 培养类型
                //student.setEduCategory(extBks.getJylb()); 培养级别
                record.setEduWay(extBks.getPyfs());
                //student.setIsFullTime(extBks.get); 是否全日制
                record.setEnrolYear(extBks.getNj()+""); // 招生年度
                //student.setPeriod(extBks.getXz()+""); 学制
                //record.setGrade(extBks.getNj());
                //student.setActualEnrolTime(DateUtils.parseStringToDate(extBks.getSjrxny())); 实际入学年月
                //student.setExpectGraduateTime(DateUtils.parseStringToDate(extBks.getYjbyny())); 预计毕业年月
                //student.setDelayYear(extBks.getYqbynx()); 预计毕业年限
                //student.setActualGraduateTime(DateUtils.parseStringToDate(extBks.getSjbyny())); 实际毕业年月

                record.setXjStatus(extBks.getXjzt());

                ui.setUserStatus(record.getXjStatus());
                extCommonService.syncFilter(ui, null, studentInfo==null?null:record);
                sysUserService.insertOrUpdateUserInfoSelective(ui);
            }
        }

        if (uv.isYJS()) {  // 同步研究生信息

            record.setSyncSource(SystemConstants.USER_SOURCE_YJS);

            ExtYjs extYjs = extService.getExtYjs(code);
            if (extYjs != null) {

                //SysUserInfo sysUserInfo = sysUserInfoMapper.selectByPrimaryKey(userId);
                SysUserInfo ui = new SysUserInfo();
                ui.setUserId(userId);

                ui.setRealname(StringUtils.defaultString(StringUtils.trimToNull(extYjs.getXm()),
                        StringUtils.trim(extYjs.getXmpy())));
                ui.setGender(NumberUtils.toByte(extYjs.getXbm()));
                if (StringUtils.isNotBlank(extYjs.getCsrq()))
                    ui.setBirth(DateUtils.parseStringToDate(extYjs.getCsrq()));
                ui.setIdcard(StringUtils.trim(extYjs.getSfzh()));
                //ui.setMobile(StringUtils.trim(extYjs.getYddh()));
                //ui.setEmail(StringUtils.trim(extYjs.getDzxx()));
                ui.setNation(extCommonService.formatNation(extYjs.getMz()));
                ui.setNativePlace(StringUtils.defaultIfBlank(extYjs.getSyszd(), extYjs.getHkszd()));

                //+++++++++++++ 同步后面一系列属性
                record.setType(extYjs.getXslb());
                record.setEduLevel(extYjs.getPycc());
                record.setEduType(extYjs.getPylx());
                record.setEduCategory(extYjs.getJylb());
                record.setEduWay(extYjs.getPyfs());
                //student.setIsFullTime(extYjs.get); 是否全日制
                record.setEnrolYear(extYjs.getZsnd() + "");
                record.setPeriod(extYjs.getXz() + "");
                //record.setGrade(extYjs.getNj() + "");

                record.setActualEnrolTime(DateUtils.parseStringToDate(extYjs.getSjrxny()));
                record.setExpectGraduateTime(DateUtils.parseStringToDate(extYjs.getYjbyny()));
                record.setDelayYear(extYjs.getYqbynx());
                record.setActualGraduateTime(DateUtils.parseStringToDate(extYjs.getSjbyny()));

                record.setXjStatus(extYjs.getZt());

                ui.setUserStatus(record.getXjStatus());
                extCommonService.syncFilter(ui, null, studentInfo==null?null:record);
                sysUserService.insertOrUpdateUserInfoSelective(ui);
            }
        }

        if (studentInfo == null)
            studentInfoMapper.insertSelective(record);
        else
            studentInfoMapper.updateByPrimaryKeySelective(record);
    }

    //计算所在年级和是否毕业年级
    public Map<Integer, Boolean> updateGrade(String enrolYear, String period){

        if (StringUtils.isNotBlank(enrolYear) && StringUtils.isNotBlank(period)) {
            String[] periods1 = {"2.5"};
            String[] periods2 = {"1","2","3","4","5"};
            Map<Integer, Boolean> gradeMap = new HashMap<>();
            Integer _enrolYear = Integer.valueOf(enrolYear);
            float _period = 0;
            for (String str : periods1) {
                if (StringUtils.contains(period, period)) {
                    _period = Float.parseFloat(str);
                }
            }
            if (_period == 0){
                for (String str : periods2) {
                    if (StringUtils.contains(period, period)) {
                        _period = Float.parseFloat(str);
                    }
                }
            }
            if (_period != 0){

                Integer graduateYear = (int) _period + _enrolYear;//毕业年份  2014级的，2015年毕业，但是所在年级为2014级
                Date now = new Date();
                Integer year = DateUtils.getYear(now);
                if (_period == 2.5){
                    int com = Integer.compare(year, graduateYear);
                    if (com == -1){
                        if (DateUtils.compareDate(now, DateUtils.parseStringToDate(year + ".7.1"))){
                            gradeMap.put(year, false);
                        }else {
                            gradeMap.put(year - 1, false);
                        }
                    }else if(com == 0){
                        if (DateUtils.compareDate(now, DateUtils.parseStringToDate(year + ".7.1"))){
                            gradeMap.put(year, true);
                        }else {
                            gradeMap.put(year - 1, false);
                        }
                    }else if (com == 1){
                        gradeMap.put(graduateYear, true);
                    }
                }else {
                    int com = Integer.compare(year, graduateYear);
                    if (com == -1){
                        if (DateUtils.compareDate(now, DateUtils.parseStringToDate(year + ".7.1"))){
                            gradeMap.put(year , false);
                        }else {
                            gradeMap.put(year - 1, false);
                        }
                    }else if (com == 0){
                        gradeMap.put(year - 1, true);
                    }else if (com == 1){
                        gradeMap.put(graduateYear - 1, true);
                    }
                }

                return gradeMap;
            }
        }

        return null;
    }
}
