package service.sys;

import controller.global.OpException;
import domain.sys.SysUser;
import domain.sys.SysUserExample;
import domain.sys.SysUserInfo;
import domain.sys.SysUserReg;
import domain.sys.SysUserRegExample;
import domain.sys.SysUserView;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import service.BaseMapper;
import service.LoginUserService;
import service.global.CacheService;
import shiro.PasswordHelper;
import sys.constants.SystemConstants;
import sys.shiro.SaltPassword;
import sys.utils.FormUtils;
import sys.utils.PropertiesUtils;

import java.util.Date;
import java.util.List;

@Service
public class SysUserRegService extends BaseMapper {

    @Autowired
    private CacheService cacheService;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    protected PasswordHelper passwordHelper;

    @Autowired
    private LoginUserService loginUserService;

    public SysUserReg findByUserId(int userId){

        SysUserRegExample example = new SysUserRegExample();
        example.createCriteria().andUserIdEqualTo(userId);
        List<SysUserReg> sysUserRegs = sysUserRegMapper.selectByExample(example);
        if(sysUserRegs.size()==1) return sysUserRegs.get(0);
        return null;
    }

    public int count(Integer partyId){

        SysUserRegExample example = new SysUserRegExample();
        SysUserRegExample.Criteria criteria = example.createCriteria();

        criteria.addPermits(loginUserService.adminPartyIdList());
        criteria.andStatusEqualTo(SystemConstants.USER_REG_STATUS_APPLY);

        if(partyId!=null) criteria.andPartyIdEqualTo(partyId);

        return sysUserRegMapper.countByExample(example);
    }

    // 上一个 （查找比当前记录的“创建时间”  小  的记录中的  最大  的“创建时间”的记录）
    public SysUserReg next(SysUserReg sysUserReg){

        SysUserRegExample example = new SysUserRegExample();
        SysUserRegExample.Criteria criteria = example.createCriteria();

        criteria.addPermits(loginUserService.adminPartyIdList());

        criteria.andStatusEqualTo(SystemConstants.USER_REG_STATUS_APPLY);

        if(sysUserReg!=null)
            criteria.andUserIdNotEqualTo(sysUserReg.getUserId()).andCreateTimeLessThanOrEqualTo(sysUserReg.getCreateTime());
        example.setOrderByClause("create_time desc");

        List<SysUserReg> memberApplies = sysUserRegMapper.selectByExampleWithRowbounds(example, new RowBounds(0, 1));
        return (memberApplies.size()==0)?null:memberApplies.get(0);
    }

    // 下一个（查找比当前记录的“创建时间” 大  的记录中的  最小  的“创建时间”的记录）
    public SysUserReg last(SysUserReg sysUserReg){

        SysUserRegExample example = new SysUserRegExample();
        SysUserRegExample.Criteria criteria = example.createCriteria();

        criteria.addPermits(loginUserService.adminPartyIdList());

        criteria.andStatusEqualTo(SystemConstants.USER_REG_STATUS_APPLY);

        if(sysUserReg!=null)
            criteria.andUserIdNotEqualTo(sysUserReg.getUserId()).andCreateTimeGreaterThanOrEqualTo(sysUserReg.getCreateTime());
        example.setOrderByClause("create_time asc");

        List<SysUserReg> records = sysUserRegMapper.selectByExampleWithRowbounds(example, new RowBounds(0, 1));
        return (records.size()==0)?null:records.get(0);
    }
    // 不通过
    @Transactional
    public void deny(int id){

        SysUserReg sysUserReg = sysUserRegMapper.selectByPrimaryKey(id);
        {
            SysUserReg record = new SysUserReg();
            record.setId(id);
            record.setStatus(SystemConstants.USER_REG_STATUS_DENY);
            updateByPrimaryKeySelective(record);
        }
        // 删除账号
        int userId = sysUserReg.getUserId();
        sysUserInfoMapper.deleteByPrimaryKey(userId);
        sysUserService.deleteByPrimaryKey(userId);
    }

    // 通过
    @Transactional
    public void pass(int id){

        SysUserReg _sysUser = sysUserRegMapper.selectByPrimaryKey(id);
        {
            SysUserReg record = new SysUserReg();
            record.setId(id);
            record.setStatus(SystemConstants.USER_REG_STATUS_PASS);
            updateByPrimaryKeySelective(record);
        }
        {
            SysUser record = new SysUser();
            record.setId(_sysUser.getUserId());
            record.setUsername(_sysUser.getUsername());
            record.setCode(_sysUser.getCode());
            record.setType(_sysUser.getType());
            record.setSource(SystemConstants.USER_SOURCE_REG);
            record.setRoleIds(sysUserService.buildRoleIds(SystemConstants.ROLE_GUEST));

            sysUserService.updateByPrimaryKeySelective(record);
        }
        {
            SysUserInfo record = new SysUserInfo();
            record.setUserId(_sysUser.getUserId());
            record.setRealname(_sysUser.getRealname());
            record.setIdcard(_sysUser.getIdcard());
            record.setMobile(_sysUser.getPhone());

            sysUserInfoMapper.updateByPrimaryKeySelective(record);
        }

        cacheService.clearUserCache(_sysUser);
    }
    
    // 自动生成学工号,ZG开头+6位数字
    private String genCode(){

        String code = "ZG" + RandomStringUtils.randomNumeric(6);
        SysUserExample example = new SysUserExample();
        example.createCriteria().andCodeEqualTo(code);
        int count = sysUserMapper.countByExample(example);

        return (count==0)?code:genCode();
    }

    @Transactional
    @CacheEvict(value="SysUserView", key="#username")
    public void reg(String username, String passwd, Byte type,
                       String realname, String idcard, String phone,
                       Integer party, String ip){

        if(usernameDuplicate(null, null, username))
            throw new RegException("该用户名已被注册。");
        if(idcardDuplicate(null, null, idcard))
            throw new RegException("该身份证已被注册。");

        if(!FormUtils.usernameFormatRight(username)){
            throw new RegException("用户名由3-10位的字母、下划线和数字组成，且不能以数字或下划线开头。");
        }

        if(!FormUtils.match(PropertiesUtils.getString("passwd.regex"), passwd)){
            throw new RegException("密码由6-16位的字母、下划线和数字组成");
        }
        String code = genCode();

        SysUser sysUser = new SysUser();
        sysUser.setUsername(username);
        sysUser.setCode(code);
        sysUser.setLocked(false);
        SaltPassword encrypt = passwordHelper.encryptByRandomSalt(passwd);
        sysUser.setSalt(encrypt.getSalt());
        sysUser.setPasswd(encrypt.getPassword());
        sysUser.setCreateTime(new Date());
        sysUser.setType(type);
        sysUser.setSource(SystemConstants.USER_SOURCE_REG);
        sysUser.setRoleIds(sysUserService.buildRoleIds(SystemConstants.ROLE_REG));
        sysUserService.insertSelective(sysUser);

        SysUserInfo sysUserInfo = new SysUserInfo();
        sysUserInfo.setUserId(sysUser.getId());
        sysUserInfo.setRealname(realname);
        sysUserInfo.setIdcard(idcard);
        sysUserInfo.setMobile(phone);
        sysUserInfoMapper.insertSelective(sysUserInfo);

        SysUserReg reg = new SysUserReg();
        reg.setUserId(sysUser.getId());
        reg.setPartyId(party);
        reg.setUsername(username);
        reg.setRealname(realname);
        reg.setPasswd(passwd);
        reg.setType(type);
        reg.setIdcard(idcard);
        reg.setPhone(phone);
        //reg.setUserId();
        reg.setCode(code);
        reg.setCreateTime(new Date());
        reg.setIp(ip);
        reg.setStatus(SystemConstants.USER_REG_STATUS_APPLY);
        sysUserRegMapper.insertSelective(reg);
    }

    public boolean usernameDuplicate(Integer id, Integer userId, String username){

        Assert.isTrue(StringUtils.isNotBlank(username), "username is blank");
        {
            SysUserRegExample example = new SysUserRegExample();
            SysUserRegExample.Criteria criteria = example.createCriteria().andUsernameEqualTo(username)
                    .andStatusNotEqualTo(SystemConstants.USER_REG_STATUS_DENY);
            if(id!=null) criteria.andIdNotEqualTo(id);

            if (sysUserRegMapper.countByExample(example) > 0) return true;
        }
        {
            SysUserExample example = new SysUserExample();
            SysUserExample.Criteria criteria = example.createCriteria().andUsernameEqualTo(username);
            if(userId!=null) criteria.andIdNotEqualTo(userId);

            if (sysUserMapper.countByExample(example) > 0) return true;
        }

        return false;
    }

    public boolean idcardDuplicate(Integer id, Integer userId, String idcard){

        // 每个身份证号都有1次机会通过注册账号的方式进行登陆

        Assert.isTrue(StringUtils.isNotBlank(idcard), "idcard is blank");
        {
            SysUserRegExample example = new SysUserRegExample();
            SysUserRegExample.Criteria criteria = example.createCriteria().andIdcardEqualTo(idcard)
                    .andStatusNotEqualTo(SystemConstants.USER_REG_STATUS_DENY);

            if(id!=null) criteria.andIdNotEqualTo(id);
            if (sysUserRegMapper.countByExample(example) > 0) return true;
        }

        /*{
            SysUserExample example = new SysUserExample();
            SysUserExample.Criteria criteria = example.createCriteria().andIdcardEqualTo(idcard);
            if(userId!=null) criteria.andIdNotEqualTo(userId);

            if (sysUserMapper.countByExample(example) > 0) return true;
        }*/

        return false;
    }

    @Transactional
    public SysUserView changepw(int id, String password){ // 返回值是为了清除缓存

        SysUserReg sysUserReg = sysUserRegMapper.selectByPrimaryKey(id);
        if(sysUserReg==null || sysUserReg.getUserId()==null) throw new OpException("参数错误");

        SysUserView _sysUser = sysUserService.findById(sysUserReg.getUserId());
        if(_sysUser==null) throw new OpException("用户不存在");

        SysUser record = new SysUser();
        record.setId(_sysUser.getId());
        SaltPassword encrypt = passwordHelper.encryptByRandomSalt(password);
        record.setSalt(encrypt.getSalt());
        record.setPasswd(encrypt.getPassword());
        sysUserService.updateByPrimaryKeySelective(record);

        SysUserReg _record = new SysUserReg();
        _record.setId(id);
        _record.setPasswd(password);
        updateByPrimaryKeySelective(_record);

        cacheService.clearUserCache(_sysUser);

        return _sysUser;
    }

   /* @Transactional
    public int insertSelective(SysUserReg record){

        Assert.isTrue(!usernameDuplicate(record.getId(), record.getUserId(), record.getUsername()));
        Assert.isTrue(!idcardDuplicate(record.getId(), record.getUserId(), record.getIdcard()));
        return sysUserRegMapper.insertSelective(record);
    }*/
    /*@Transactional
    public void del(Integer id){

        sysUserRegMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        SysUserRegExample example = new SysUserRegExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        sysUserRegMapper.deleteByExample(example);
    }*/

    @Transactional
    public int updateByPrimaryKeySelective(SysUserReg record){

        // 不可修改账号ID、账号名称、学工号
        record.setUserId(null);
        record.setUsername(null);
        record.setCode(null);

        return sysUserRegMapper.updateByPrimaryKeySelective(record);
    }

}
