package service.member;

import controller.global.OpException;
import domain.member.MemberReg;
import domain.member.MemberRegExample;
import domain.sys.SysUser;
import domain.sys.SysUserExample;
import domain.sys.SysUserInfo;
import domain.sys.SysUserView;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import service.LoginUserService;
import service.global.CacheHelper;
import service.sys.SysUserService;
import shiro.PasswordHelper;
import shiro.ShiroHelper;
import sys.constants.OwConstants;
import sys.constants.RoleConstants;
import sys.constants.SystemConstants;
import sys.shiro.SaltPassword;
import sys.tags.CmTag;
import sys.utils.ContextHelper;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MemberRegService extends MemberBaseMapper {

    @Autowired
    private CacheHelper cacheHelper;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    protected PasswordHelper passwordHelper;
    @Autowired
    protected ApplyApprovalLogService applyApprovalLogService;

    @Autowired
    private LoginUserService loginUserService;

    public MemberReg findByUserId(int userId) {

        MemberRegExample example = new MemberRegExample();
        example.createCriteria().andUserIdEqualTo(userId);
        List<MemberReg> memberRegs = memberRegMapper.selectByExample(example);
        if (memberRegs.size() == 1) return memberRegs.get(0);
        return null;
    }

    public int count(Integer partyId) {

        MemberRegExample example = new MemberRegExample();
        MemberRegExample.Criteria criteria = example.createCriteria();

        criteria.addPermits(loginUserService.adminPartyIdList());
        criteria.andStatusEqualTo(SystemConstants.USER_REG_STATUS_APPLY);

        if (partyId != null) criteria.andPartyIdEqualTo(partyId);

        return (int) memberRegMapper.countByExample(example);
    }

    // 上一个 （查找比当前记录的“创建时间”  小  的记录中的  最大  的“创建时间”的记录）
    public MemberReg next(MemberReg memberReg) {

        MemberRegExample example = new MemberRegExample();
        MemberRegExample.Criteria criteria = example.createCriteria();

        criteria.addPermits(loginUserService.adminPartyIdList());

        criteria.andStatusEqualTo(SystemConstants.USER_REG_STATUS_APPLY);

        if (memberReg != null)
            criteria.andUserIdNotEqualTo(memberReg.getUserId()).andCreateTimeLessThanOrEqualTo(memberReg.getCreateTime());
        example.setOrderByClause("create_time desc");

        List<MemberReg> memberApplies = memberRegMapper.selectByExampleWithRowbounds(example, new RowBounds(0, 1));
        return (memberApplies.size() == 0) ? null : memberApplies.get(0);
    }

    // 下一个（查找比当前记录的“创建时间” 大  的记录中的  最小  的“创建时间”的记录）
    public MemberReg last(MemberReg memberReg) {

        MemberRegExample example = new MemberRegExample();
        MemberRegExample.Criteria criteria = example.createCriteria();

        criteria.addPermits(loginUserService.adminPartyIdList());

        criteria.andStatusEqualTo(SystemConstants.USER_REG_STATUS_APPLY);

        if (memberReg != null)
            criteria.andUserIdNotEqualTo(memberReg.getUserId()).andCreateTimeGreaterThanOrEqualTo(memberReg.getCreateTime());
        example.setOrderByClause("create_time asc");

        List<MemberReg> records = memberRegMapper.selectByExampleWithRowbounds(example, new RowBounds(0, 1));
        return (records.size() == 0) ? null : records.get(0);
    }

    // 不通过
    @Transactional
    public void deny(int id) {

        MemberReg memberReg = memberRegMapper.selectByPrimaryKey(id);
        {
            MemberReg record = new MemberReg();
            record.setId(id);
            record.setStatus(SystemConstants.USER_REG_STATUS_DENY);
            updateByPrimaryKeySelective(record);
        }
        // 删除账号
        int userId = memberReg.getUserId();
        sysUserInfoMapper.deleteByPrimaryKey(userId);
        sysUserService.deleteByPrimaryKey(userId);
    }

    // 通过
    @Transactional
    public void pass(int id) {

        MemberReg _sysUser = memberRegMapper.selectByPrimaryKey(id);
        {
            MemberReg record = new MemberReg();
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
            record.setRoleIds(sysUserService.buildRoleIds(RoleConstants.ROLE_GUEST));

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

        cacheHelper.clearUserCache(_sysUser);
    }

    @Transactional
   /* @CacheEvict(value = "SysUserView", key = "#username")*/
    public String reg(String passwd, Byte type,
                    String realname, String idcard, String phone,
                    Integer party, String ip) {

        if (idcardDuplicate(null, idcard))
            throw new OpException("该身份证已被注册。");

        if (!CmTag.validPasswd(passwd)) {
            throw new OpException(CmTag.getStringProperty("passwdMsg"));
        }

        String prefix = CmTag.getStringProperty("memberRegCodePrefix", "zg");
        String code = sysUserService.genCode(prefix);

        SysUser sysUser = new SysUser();
        sysUser.setUsername(code);
        sysUser.setCode(code);
        sysUser.setLocked(false);
        SaltPassword encrypt = passwordHelper.encryptByRandomSalt(passwd);
        sysUser.setSalt(encrypt.getSalt());
        sysUser.setPasswd(encrypt.getPassword());
        sysUser.setCreateTime(new Date());
        sysUser.setType(type);
        sysUser.setSource(SystemConstants.USER_SOURCE_REG);
        sysUser.setRoleIds(sysUserService.buildRoleIds(RoleConstants.ROLE_REG));
        sysUserService.insertSelective(sysUser);

        SysUserInfo sysUserInfo = new SysUserInfo();
        sysUserInfo.setUserId(sysUser.getId());
        sysUserInfo.setRealname(realname);
        sysUserInfo.setIdcard(idcard);
        sysUserInfo.setMobile(phone);
        sysUserService.insertOrUpdateUserInfoSelective(sysUserInfo);

        MemberReg reg = new MemberReg();
        reg.setUserId(sysUser.getId());
        reg.setPartyId(party);
        reg.setUsername(code);
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
        memberRegMapper.insertSelective(reg);

        applyApprovalLogService.add(reg.getId(),
                reg.getPartyId(), null, reg.getUserId(),
                reg.getUserId(), OwConstants.OW_APPLY_APPROVAL_LOG_USER_TYPE_SELF,
                OwConstants.OW_APPLY_APPROVAL_LOG_TYPE_USER_REG, "注册",
                OwConstants.OW_APPLY_APPROVAL_LOG_STATUS_NONEED, null);
        return code;
    }

    public boolean usernameDuplicate(Integer id, Integer userId, String username) {

        Assert.isTrue(StringUtils.isNotBlank(username), "username is blank");
        {
            MemberRegExample example = new MemberRegExample();
            MemberRegExample.Criteria criteria = example.createCriteria().andUsernameEqualTo(username)
                    .andStatusNotEqualTo(SystemConstants.USER_REG_STATUS_DENY);
            if (id != null) criteria.andIdNotEqualTo(id);

            if (memberRegMapper.countByExample(example) > 0) return true;
        }
        {
            SysUserExample example = new SysUserExample();
            SysUserExample.Criteria criteria = example.createCriteria().andUsernameEqualTo(username);
            if (userId != null) criteria.andIdNotEqualTo(userId);

            if (sysUserMapper.countByExample(example) > 0) return true;
        }

        return false;
    }

    public boolean idcardDuplicate(Integer id, String idcard) {

        // 每个身份证号都有1次机会通过注册账号的方式进行登陆
        Assert.isTrue(StringUtils.isNotBlank(idcard), "idcard is blank");
        {
            MemberRegExample example = new MemberRegExample();
            MemberRegExample.Criteria criteria = example.createCriteria().andIdcardEqualTo(idcard)
                    .andStatusNotEqualTo(SystemConstants.USER_REG_STATUS_DENY);

            if (id != null) criteria.andIdNotEqualTo(id);
            if (memberRegMapper.countByExample(example) > 0) return true;
        }

        return false;
    }

    @Transactional
    public SysUserView changepw(int id, String password) { // 返回值是为了清除缓存

        MemberReg memberReg = memberRegMapper.selectByPrimaryKey(id);
        if (memberReg == null || memberReg.getUserId() == null) throw new OpException("参数错误");

        SysUserView _sysUser = sysUserService.findById(memberReg.getUserId());
        if (_sysUser == null) throw new OpException("用户不存在");

        SysUser record = new SysUser();
        record.setId(_sysUser.getId());
        SaltPassword encrypt = passwordHelper.encryptByRandomSalt(password);
        record.setSalt(encrypt.getSalt());
        record.setPasswd(encrypt.getPassword());
        sysUserService.updateByPrimaryKeySelective(record);

        MemberReg _record = new MemberReg();
        _record.setId(id);
        _record.setPasswd(password);
        updateByPrimaryKeySelective(_record);

        cacheHelper.clearUserCache(_sysUser);

        return _sysUser;
    }

   /* @Transactional
    public int insertSelective(MemberReg record){

        Assert.isTrue(!usernameDuplicate(record.getId(), record.getUserId(), record.getUsername()));
        Assert.isTrue(!idcardDuplicate(record.getId(), record.getUserId(), record.getIdcard()));
        return memberRegMapper.insertSelective(record);
    }*/
    /*@Transactional
    public void del(Integer id){

        memberRegMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        MemberRegExample example = new MemberRegExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        memberRegMapper.deleteByExample(example);
    }*/

    @Transactional
    public int updateByPrimaryKeySelective(MemberReg record) {

        // 不可修改账号ID、账号名称、学工号
        record.setUserId(null);
        record.setUsername(null);
        record.setCode(null);

        return memberRegMapper.updateByPrimaryKeySelective(record);
    }

    @Transactional
    public synchronized Map<String, Object> bacthImport(List<MemberReg> records) {

        Map<String, Object> resultMap = new HashMap<>();

        Date now = new Date();
        String ip = ContextHelper.getRealIp();
        String prefix = CmTag.getStringProperty("memberRegPrefix", "dy");
        Integer importSeq = iMemberMapper.getMemberRegMaxSeq();
        if (importSeq == null) importSeq = 0;

        int addCount = 0;
        for (MemberReg record : records) {

            if (!idcardDuplicate(null, record.getIdcard())) {

                addMemberReg(record, prefix, importSeq+1, now, ip);
                addCount++;
            }
        }

        if (addCount > 0) {
            resultMap.put("importSeq", importSeq + 1);
        }
        resultMap.put("addCount", addCount);

        return resultMap;
    }

    // 添加一个账号
    @Transactional
    public MemberReg addMemberReg(MemberReg record, String prefix, int importSeq, Date now, String ip) {

        String code = sysUserService.genCode(prefix);
        String passwd = RandomStringUtils.randomNumeric(6);

        SysUser sysUser = new SysUser();
        sysUser.setUsername(code);
        sysUser.setCode(code);
        sysUser.setLocked(false);
        SaltPassword encrypt = passwordHelper.encryptByRandomSalt(passwd);
        sysUser.setSalt(encrypt.getSalt());
        sysUser.setPasswd(encrypt.getPassword());
        sysUser.setCreateTime(new Date());
        sysUser.setType(record.getType());
        sysUser.setSource(SystemConstants.USER_SOURCE_REG);
        sysUser.setRoleIds(sysUserService.buildRoleIds(RoleConstants.ROLE_GUEST));
        sysUserService.insertSelective(sysUser);

        SysUserInfo sysUserInfo = new SysUserInfo();
        sysUserInfo.setUserId(sysUser.getId());
        sysUserInfo.setRealname(record.getRealname());
        sysUserInfo.setIdcard(record.getIdcard());
        sysUserInfo.setMobile(record.getPhone());
        sysUserService.insertOrUpdateUserInfoSelective(sysUserInfo);

        Integer currentUserId = ShiroHelper.getCurrentUserId();
        record.setUserId(sysUser.getId());
        record.setUsername(code);
        record.setCode(code);
        record.setPasswd(passwd);
        record.setCreateTime(now);
        record.setIp(ip);
        record.setStatus(SystemConstants.USER_REG_STATUS_PASS);
        record.setImportUserId(currentUserId);
        record.setImportSeq(importSeq + 1);
        memberRegMapper.insertSelective(record);

        applyApprovalLogService.add(record.getId(),
                record.getPartyId(), null, record.getUserId(),
                currentUserId, OwConstants.OW_APPLY_APPROVAL_LOG_USER_TYPE_ADMIN,
                OwConstants.OW_APPLY_APPROVAL_LOG_TYPE_USER_REG, "后台添加",
                OwConstants.OW_APPLY_APPROVAL_LOG_STATUS_NONEED, null);

        return  record;
    }
}
