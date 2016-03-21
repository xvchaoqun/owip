package service.sys;

import domain.*;
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
import service.party.MemberService;
import service.source.BnuBksImport;
import service.source.BnuJzgImport;
import service.source.BnuYjsImport;
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
    private  SysUserService sysUserService;
    @Autowired
    protected PasswordHelper passwordHelper;
    @Autowired
    private MemberService memberService;
    @Autowired
    private BnuJzgImport bnuJzgImport;
    @Autowired
    private BnuBksImport bnuBksImport;
    @Autowired
    private BnuYjsImport bnuYjsImport;

    private Logger logger = LoggerFactory.getLogger(getClass());

    public boolean lastSyncIsNotStop(byte type){

        SysUserSyncExample example = new SysUserSyncExample();
        example.createCriteria()
                .andTypeEqualTo(type).andIsStopEqualTo(false);

        return sysUserSyncMapper.countByExample(example)> 0;
    }

    // 同步教职工人事库
    public void syncJZG(boolean autoStart){

        if(lastSyncIsNotStop(SystemConstants.USER_SOURCE_JZG)){
            throw new RuntimeException("上一次同步仍在进行中");
        }

        SysUserSync sysUserSync = new SysUserSync();
        if(!autoStart) {
            ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
            sysUserSync.setUserId(shiroUser.getId());
        }
        sysUserSync.setAutoStart(autoStart);
        sysUserSync.setAutoStop(false);
        sysUserSync.setStartTime(new Date());
        sysUserSync.setType(SystemConstants.USER_SOURCE_JZG);
        sysUserSync.setIsStop(false);

        sysUserSync.setCurrentCount(0);
        sysUserSync.setCurrentPage(0);
        //sysUserSync.setTotalCount(count);
        //sysUserSync.setTotalPage(pageNo);
        sysUserSync.setInsertCount(0);
        sysUserSync.setUpdateCount(0);

        insertSelective(sysUserSync);

        // 先从师大导入数据
        bnuJzgImport.excute();

        int insertCount = 0;
        int updateCount = 0;

        int count = extJzgMapper.countByExample(new ExtJzgExample());
        int pageSize = 200;
        int pageNo = count / pageSize + (count % pageSize > 0 ? 1 : 0);

        for (int i=0; i <pageNo; i++) {
            logger.debug(String.format("总数：%s， 每页%s条， 当前%s页", count, pageSize, pageNo));
            List<ExtJzg> extJzgs = extJzgMapper.selectByExampleWithRowbounds(new ExtJzgExample(), new RowBounds(i * pageSize, pageSize));
            for (ExtJzg extJzg : extJzgs) {
                String code = StringUtils.trim(extJzg.getZgh());
                SysUser record = new SysUser();
                record.setUsername(code);
                record.setCode(code);
                record.setType(SystemConstants.USER_TYPE_JZG);
                record.setRealname(StringUtils.trim(extJzg.getXm()));
                record.setGender(NumberUtils.toByte(extJzg.getXbm()));
                record.setBirth(extJzg.getCsrq());
                record.setIdcard(StringUtils.trim(extJzg.getSfzh()));
                record.setMobile(StringUtils.trim(extJzg.getYddh()));
                record.setEmail(StringUtils.trim(extJzg.getDzxx()));
                record.setSource(SystemConstants.USER_SOURCE_JZG);
                record.setLocked(false);

                SysUser sysUser = sysUserService.findByUsername(code);
                try {
                    if (sysUser == null) {
                        SaltPassword encrypt = passwordHelper.encryptByRandomSalt(code); // 初始化密码与账号相同
                        record.setSalt(encrypt.getSalt());
                        record.setPasswd(encrypt.getPassword());
                        record.setCreateTime(new Date());
                        sysUserService.insertSelective(record);
                        insertCount++;
                    } else {
                        record.setId(sysUser.getId());
                        sysUserService.updateByPrimaryKeySelective(record, sysUser.getUsername());

                        // 同步党员信息
                        //if(memberService.get(sysUser.getId())!=null)
                            memberService.snycTeacher(sysUser.getId(), sysUser.getCode());

                        updateCount++;
                    }
                }catch (Exception ex){
                    logger.error("同步出错", ex);
                }
            }

            SysUserSync record = new SysUserSync();
            record.setId(sysUserSync.getId());
            record.setInsertCount(insertCount);
            record.setUpdateCount(updateCount);
            record.setTotalCount(count);
            record.setTotalPage(pageNo);
            record.setCurrentCount(((i+1) * pageSize>count)?count:(i+1)*pageSize);
            record.setCurrentPage(i+1);
            try {
                SysUserSync _sync = sysUserSyncMapper.selectByPrimaryKey(sysUserSync.getId());
                if(_sync.getIsStop()){
                    return ; // 强制结束
                }
                updateByPrimaryKeySelective(record);
            }catch (Exception ex){
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
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    // 同步研究生库
    public void syncYJS(boolean autoStart){

        if(lastSyncIsNotStop(SystemConstants.USER_SOURCE_YJS)){
            throw new RuntimeException("上一次同步仍在进行中");
        }


        SysUserSync sysUserSync = new SysUserSync();
        if(!autoStart) {
            ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
            sysUserSync.setUserId(shiroUser.getId());
        }
        sysUserSync.setAutoStart(autoStart);
        sysUserSync.setAutoStop(false);
        sysUserSync.setStartTime(new Date());
        sysUserSync.setType(SystemConstants.USER_SOURCE_YJS);
        sysUserSync.setIsStop(false);

        sysUserSync.setCurrentCount(0);
        sysUserSync.setCurrentPage(0);

        sysUserSync.setInsertCount(0);
        sysUserSync.setUpdateCount(0);

        insertSelective(sysUserSync);

        bnuYjsImport.excute();

        int insertCount = 0;
        int updateCount = 0;

        int count = extYjsMapper.countByExample(new ExtYjsExample());
        int pageSize = 200;
        int pageNo = count / pageSize + (count % pageSize > 0 ? 1 : 0);

        logger.debug(String.format("总数：%s， 每页%s条， 当前%s页", count, pageSize, pageNo));
        for (int i=0; i <pageNo; i++) {

            List<ExtYjs> extYjss = extYjsMapper.selectByExampleWithRowbounds(new ExtYjsExample(), new RowBounds(i * pageSize, pageSize));
            for (ExtYjs extYjs : extYjss) {
                String code = StringUtils.trim(extYjs.getXh());
                SysUser record = new SysUser();
                record.setUsername(code);
                record.setCode(code);
                record.setType(SystemConstants.USER_TYPE_YJS);
                record.setRealname(StringUtils.trim(extYjs.getXm()));
                record.setGender(NumberUtils.toByte(extYjs.getXbm()));
                if(StringUtils.isNotBlank(extYjs.getCsrq()))
                    record.setBirth(DateUtils.parseDate(extYjs.getCsrq(), "yyyyMMdd"));
                record.setIdcard(StringUtils.trim(extYjs.getSfzh()));
                //record.setMobile(StringUtils.trim(extYjs.getYddh()));
                //record.setEmail(StringUtils.trim(extYjs.getDzxx()));
                record.setSource(SystemConstants.USER_SOURCE_YJS);
                record.setLocked(false);

                SysUser sysUser = sysUserService.findByUsername(code);
                try {
                    if (sysUser == null) {
                        SaltPassword encrypt = passwordHelper.encryptByRandomSalt(code); // 初始化密码与账号相同
                        record.setSalt(encrypt.getSalt());
                        record.setPasswd(encrypt.getPassword());
                        record.setCreateTime(new Date());
                        sysUserService.insertSelective(record);
                        insertCount++;
                    } else {
                        record.setId(sysUser.getId());
                        sysUserService.updateByPrimaryKeySelective(record, sysUser.getUsername());

                        // 同步党员信息
                        //if(memberService.get(sysUser.getId())!=null)
                            memberService.snycStudent(sysUser.getId(), sysUser.getType(), sysUser.getCode());

                        updateCount++;
                    }
                }catch (Exception ex){
                    logger.error("同步出错", ex);
                }
            }

            SysUserSync record = new SysUserSync();
            record.setId(sysUserSync.getId());
            record.setInsertCount(insertCount);
            record.setUpdateCount(updateCount);
            record.setTotalCount(count);
            record.setTotalPage(pageNo);
            record.setCurrentCount(((i+1) * pageSize>count)?count:(i+1)*pageSize);
            record.setCurrentPage(i+1);
            try {
                SysUserSync _sync = sysUserSyncMapper.selectByPrimaryKey(sysUserSync.getId());
                if(_sync.getIsStop()){
                    return ; // 强制结束
                }
                updateByPrimaryKeySelective(record);
            }catch (Exception ex){
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
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }


    // 同步本科生库
    public void syncBks(boolean autoStart){

        if(lastSyncIsNotStop(SystemConstants.USER_SOURCE_BKS)){
            throw new RuntimeException("上一次同步仍在进行中");
        }
        SysUserSync sysUserSync = new SysUserSync();
        if(!autoStart) {
            ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
            sysUserSync.setUserId(shiroUser.getId());
        }
        sysUserSync.setAutoStart(autoStart);
        sysUserSync.setAutoStop(false);
        sysUserSync.setStartTime(new Date());
        sysUserSync.setType(SystemConstants.USER_SOURCE_BKS);
        sysUserSync.setIsStop(false);

        sysUserSync.setCurrentCount(0);
        sysUserSync.setCurrentPage(0);

        sysUserSync.setInsertCount(0);
        sysUserSync.setUpdateCount(0);

        insertSelective(sysUserSync);

        bnuBksImport.excute();

        int insertCount = 0;
        int updateCount = 0;

        int count = extBksMapper.countByExample(new ExtBksExample());
        int pageSize = 200;
        int pageNo = count / pageSize + (count % pageSize > 0 ? 1 : 0);

        logger.debug(String.format("总数：%s， 每页%s条， 当前%s页", count, pageSize, pageNo));
        for (int i=0; i <pageNo; i++) {

            List<ExtBks> extBkss = extBksMapper.selectByExampleWithRowbounds(new ExtBksExample(), new RowBounds(i * pageSize, pageSize));
            for (ExtBks extBks : extBkss) {
                String code = StringUtils.trim(extBks.getXh());
                SysUser record = new SysUser();
                record.setUsername(code);
                record.setCode(code);
                record.setType(SystemConstants.USER_TYPE_BKS);
                record.setRealname(StringUtils.trim(extBks.getXm()));
                if(StringUtils.equalsIgnoreCase(extBks.getXb(), "男"))
                    record.setGender(SystemConstants.GENDER_MALE);
                else if(StringUtils.equalsIgnoreCase(extBks.getXb(), "女"))
                    record.setGender(SystemConstants.GENDER_FEMALE);
                if(StringUtils.isNotBlank(extBks.getCsrq()))
                    record.setBirth(DateUtils.parseDate(extBks.getCsrq(), "yyyyMMdd"));
                record.setIdcard(StringUtils.trim(extBks.getSfzh()));
                //record.setMobile(StringUtils.trim(extBks.getYddh()));
                //record.setEmail(StringUtils.trim(extBks.getDzxx()));
                record.setSource(SystemConstants.USER_SOURCE_BKS);
                record.setLocked(false);

                SysUser sysUser = sysUserService.findByUsername(code);
                try {
                    if (sysUser == null) {
                        SaltPassword encrypt = passwordHelper.encryptByRandomSalt(code); // 初始化密码与账号相同
                        record.setSalt(encrypt.getSalt());
                        record.setPasswd(encrypt.getPassword());
                        record.setCreateTime(new Date());
                        sysUserService.insertSelective(record);
                        insertCount++;
                    } else {
                        record.setId(sysUser.getId());
                        sysUserService.updateByPrimaryKeySelective(record, sysUser.getUsername());

                        // 同步党员信息
                        //if(memberService.get(sysUser.getId())!=null)
                            memberService.snycStudent(sysUser.getId(), sysUser.getType(), sysUser.getCode());

                        updateCount++;
                    }
                }catch (Exception ex){
                    logger.error("同步出错", ex);
                }
            }

            SysUserSync record = new SysUserSync();
            record.setId(sysUserSync.getId());
            record.setInsertCount(insertCount);
            record.setUpdateCount(updateCount);
            record.setTotalCount(count);
            record.setTotalPage(pageNo);
            record.setCurrentCount(((i+1) * pageSize>count)?count:(i+1)*pageSize);
            record.setCurrentPage(i+1);
            try {
                SysUserSync _sync = sysUserSyncMapper.selectByPrimaryKey(sysUserSync.getId());
                if(_sync.getIsStop()){
                    return ; // 强制结束
                }
                updateByPrimaryKeySelective(record);
            }catch (Exception ex){
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
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    @Transactional
    public int insertSelective(SysUserSync record){

        Assert.isTrue(!lastSyncIsNotStop(record.getType()));
        return sysUserSyncMapper.insertSelective(record);
    }
    @Transactional
    public void del(Integer id){

        sysUserSyncMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        SysUserSyncExample example = new SysUserSyncExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        sysUserSyncMapper.deleteByExample(example);
    }

    @Transactional
    public int updateByPrimaryKeySelective(SysUserSync record){
        return sysUserSyncMapper.updateByPrimaryKeySelective(record);
    }
}
