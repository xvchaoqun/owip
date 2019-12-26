package service.dp;

import controller.global.OpException;
import domain.dp.DpMember;
import domain.dp.DpMemberExample;
import domain.dp.DpMemberView;
import domain.dp.DpMemberViewExample;
import domain.member.Member;
import domain.sys.*;
import ext.service.SyncService;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import service.party.MemberService;
import service.sys.LogService;
import service.sys.SysUserService;
import service.sys.TeacherInfoService;
import shiro.PasswordHelper;
import sys.constants.DpConstants;
import sys.constants.RoleConstants;
import sys.constants.SystemConstants;
import sys.shiro.SaltPassword;
import sys.tags.CmTag;

import java.util.*;

@Service
public class DpMemberService extends DpBaseMapper {

    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SyncService syncService;
    @Autowired
    private LogService logService;
    @Autowired
    private TeacherInfoService teacherInfoService;
    @Autowired
    protected PasswordHelper passwordHelper;
    @Autowired
    protected MemberService memberService;

    public SysUserView getByIdCard(String idCard){

        SysUserViewExample example = new SysUserViewExample();
        example.createCriteria().andIdcardEqualTo(idCard);
        List<SysUserView> sysUserViews = sysUserViewMapper.selectByExample(example);

        return sysUserViews.size() > 0 ? sysUserViews.get(0) : null;
    }

    //生成账号
    @Transactional
    public SysUserView addDpMember(String realname) {

        String prefix = "dp";
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
        sysUser.setType(DpConstants.DP_MEMBER_TYPE_TEACHER);
        sysUser.setSource(SystemConstants.USER_SOURCE_DP_IMPORT);
        sysUser.setRoleIds(sysUserService.buildRoleIds(RoleConstants.ROLE_GUEST));
        sysUserService.insertSelective(sysUser);

        SysUserInfo sysUserInfo = new SysUserInfo();
        sysUserInfo.setUserId(sysUser.getId());
        sysUserInfo.setRealname(realname);
        sysUserService.insertOrUpdateUserInfoSelective(sysUserInfo);

        SysUserView uv = CmTag.getUserByCode(code);

        return uv;
    }


    //批量导入
    @Transactional
    public int batchImportInSchool(List<DpMember> records){
        int addCount = 0;
        for (DpMember dpMember : records){
            if (add(dpMember)){
                addCount++;
            }
        }
        return addCount;
    }

    //批量导入
    @Transactional
    public int batchImportOutSchool(List<DpMember> records,
                                    List<TeacherInfo> teacherInfos,
                                    List<SysUserInfo> sysUserInfos){

        int addCount = 0;
        for (DpMember record : records){
            if (add(record)){
                addCount++;
            }
        }
        for (TeacherInfo teacherInfo : teacherInfos){
            teacherInfoService.updateByPrimaryKeySelective(teacherInfo);
        }
        for (SysUserInfo sysUserInfo : sysUserInfos){
            sysUserService.insertOrUpdateUserInfoSelective(sysUserInfo);
        }

        return addCount;
    }

    public DpMember get(int userId) {

        if (dpMemberMapper == null) return null;
        return dpMemberMapper.selectByPrimaryKey(userId);
    }

    public boolean idDuplicate(Integer userId, String code){

        Assert.isTrue(userId != null, "null");

        DpMemberExample example = new DpMemberExample();
        DpMemberExample.Criteria criteria = example.createCriteria();
        if(userId!=null) criteria.andUserIdEqualTo(userId);

        return dpMemberMapper.countByExample(example) > 0;
    }

    //添加党派成员
    @Transactional
    public boolean add(DpMember record){

        Integer userId = record.getUserId();
        SysUserView uv = sysUserService.findById(userId);
        Byte type = uv.getType();

        if (type == SystemConstants.USER_TYPE_JZG){
            record.setType(DpConstants.DP_MEMBER_TYPE_TEACHER);
            syncService.snycTeacherInfo(userId, uv);
        }else {
            throw new OpException("账号不是教工。" + uv.getCode() + "," + uv.getRealname());
        }

        //是否是共产党员
        Member member = memberService.get(userId);
        record.setIsPartyMember(member != null && (member.getType()==1 || member.getType() == 4));

        boolean isAdd = false;
        DpMember dpMember = get(userId);
        if (dpMember == null) {
            Assert.isTrue(dpMemberMapper.insertSelective(record) == 1, "dp insert failed");
            isAdd = true;
        }else if (dpMember != null){
            Assert.isTrue(dpMemberMapper.updateByPrimaryKeySelective(record) == 1,"db insert failed");
        }
        sysUserService.changeRole(userId, RoleConstants.ROLE_GUEST,RoleConstants.ROLE_DP_PARTY);

        return isAdd;
    }

    @Transactional
    @CacheEvict(value="DpMember:ALL", allEntries = true)
    public void insertSelective(DpMember record){

        Assert.isTrue(!idDuplicate(null, String.valueOf(record.getUserId())), "duplicate");
        //record.setSortOrder(getNextSortOrder("dp_member", null));
        dpMemberMapper.insertSelective(record);
    }

    @Transactional
    @CacheEvict(value="DpMember:ALL", allEntries = true)
    public void del(Integer userId){

        dpMemberMapper.deleteByPrimaryKey(userId);
    }

    @Transactional
    @CacheEvict(value="DpMember:ALL", allEntries = true)
    public void batchDel(Integer[] userIds){

        if(userIds==null || userIds.length==0) return;
        {
            DpMemberExample example = new DpMemberExample();
            example.createCriteria().andUserIdIn(Arrays.asList(userIds));
            dpMemberMapper.deleteByExample(example);
        }

        //更新系统角色
        for(Integer userId: userIds){
            sysUserService.changeRole(userId, RoleConstants.ROLE_DP_PARTY, RoleConstants.ROLE_GUEST);
        }

    }

    //修改党籍信息时使用，保留修改记录
    @Transactional
    @CacheEvict(value="DpMember:ALL", allEntries = true)
    public int updateByPrimaryKeySelective(DpMember record, String reason){
        Integer userId = record.getUserId();

       return updateByPrimaryKeySelective(record);
    }

    //系统内部使用，更新党员状态，当机状态等
    @Transactional
    public int updateByPrimaryKeySelective(DpMember record){

        return dpMemberMapper.updateByPrimaryKeySelective(record);
    }

    @Cacheable(value="DpMember:ALL")
    public Map<Integer, DpMember> findAll() {

        DpMemberExample example = new DpMemberExample();
        example.createCriteria();
        example.setOrderByClause("sort_order desc");
        List<DpMember> records = dpMemberMapper.selectByExample(example);
        Map<Integer, DpMember> map = new LinkedHashMap<>();
        for (DpMember record : records) {
            map.put(record.getUserId(), record);
        }

        return map;
    }

    public DpMemberView findByUserId(Integer userId){

        DpMemberViewExample example = new DpMemberViewExample();
        example.createCriteria().andUserIdEqualTo(userId);
        List<DpMemberView> dpMemberViews = dpMemberViewMapper.selectByExample(example);

        return dpMemberViews.size() == 1 ? dpMemberViews.get(0) : null;
    }

}
