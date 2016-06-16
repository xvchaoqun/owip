package service.party;

import controller.BaseController;
import domain.Branch;
import domain.GraduateAbroad;
import domain.GraduateAbroadExample;
import domain.Member;
import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import service.BaseMapper;
import service.DBErrorException;
import service.LoginUserService;
import shiro.ShiroUser;
import sys.constants.SystemConstants;
import sys.tags.CmTag;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class GraduateAbroadService extends BaseMapper {

    @Autowired
    private LoginUserService loginUserService;
    @Autowired
    private PartyService partyService;
    @Autowired
    private BranchService branchService;
    @Autowired
    private MemberOpService memberOpService;

    @Autowired
    protected ApplyApprovalLogService applyApprovalLogService;
    private VerifyAuth<GraduateAbroad> checkVerityAuth(int id){
        GraduateAbroad graduateAbroad = graduateAbroadMapper.selectByPrimaryKey(id);
        return super.checkVerityAuth(graduateAbroad, graduateAbroad.getPartyId(), graduateAbroad.getBranchId());
    }

    private VerifyAuth<GraduateAbroad> checkVerityAuth2(int id){
        GraduateAbroad graduateAbroad = graduateAbroadMapper.selectByPrimaryKey(id);
        return super.checkVerityAuth2(graduateAbroad, graduateAbroad.getPartyId());
    }
    
    public int count(Integer partyId, Integer branchId, byte type){

        GraduateAbroadExample example = new GraduateAbroadExample();
        GraduateAbroadExample.Criteria criteria = example.createCriteria();

        criteria.addPermits(loginUserService.adminPartyIdList(), loginUserService.adminBranchIdList());

        if(type==1){ //支部审核
            criteria.andStatusEqualTo(SystemConstants.GRADUATE_ABROAD_STATUS_APPLY);
        } else if(type==2){ //分党委审核
            criteria.andStatusEqualTo(SystemConstants.GRADUATE_ABROAD_STATUS_BRANCH_VERIFY);
        } else if(type==3){ //组织部审核
            criteria.andStatusEqualTo(SystemConstants.GRADUATE_ABROAD_STATUS_PARTY_VERIFY);
        }else{
            throw new RuntimeException("审核类型错误");
        }
        if(partyId!=null) criteria.andPartyIdEqualTo(partyId);
        if(branchId!=null) criteria.andBranchIdEqualTo(branchId);

        return graduateAbroadMapper.countByExample(example);
    }

    // 上一个 （查找比当前记录的“创建时间”  小  的记录中的  最大  的“创建时间”的记录）
    public GraduateAbroad next(byte type, GraduateAbroad graduateAbroad){

        GraduateAbroadExample example = new GraduateAbroadExample();
        GraduateAbroadExample.Criteria criteria = example.createCriteria();

        criteria.addPermits(loginUserService.adminPartyIdList(), loginUserService.adminBranchIdList());

        if(type==1){ //支部审核
            criteria.andStatusEqualTo(SystemConstants.GRADUATE_ABROAD_STATUS_APPLY);
        } else if(type==2){ //分党委审核
            criteria.andStatusEqualTo(SystemConstants.GRADUATE_ABROAD_STATUS_BRANCH_VERIFY);
        } else if(type==3){ //组织部审核
            criteria.andStatusEqualTo(SystemConstants.GRADUATE_ABROAD_STATUS_PARTY_VERIFY);
        }else{
            throw new RuntimeException("审核类型错误");
        }

        if(graduateAbroad!=null)
            criteria.andUserIdNotEqualTo(graduateAbroad.getUserId()).andCreateTimeLessThanOrEqualTo(graduateAbroad.getCreateTime());
        example.setOrderByClause("create_time desc");

        List<GraduateAbroad> records = graduateAbroadMapper.selectByExampleWithRowbounds(example, new RowBounds(0, 1));
        return (records.size()==0)?null:records.get(0);
    }

    // 下一个（查找比当前记录的“创建时间” 大  的记录中的  最小  的“创建时间”的记录）
    public GraduateAbroad last(byte type, GraduateAbroad graduateAbroad){

        GraduateAbroadExample example = new GraduateAbroadExample();
        GraduateAbroadExample.Criteria criteria = example.createCriteria();

        criteria.addPermits(loginUserService.adminPartyIdList(), loginUserService.adminBranchIdList());

        if(type==1){ //支部审核
            criteria.andStatusEqualTo(SystemConstants.GRADUATE_ABROAD_STATUS_APPLY);
        } else if(type==2){ //分党委审核
            criteria.andStatusEqualTo(SystemConstants.GRADUATE_ABROAD_STATUS_BRANCH_VERIFY);
        } else if(type==3){ //组织部审核
            criteria.andStatusEqualTo(SystemConstants.GRADUATE_ABROAD_STATUS_PARTY_VERIFY);
        }else{
            throw new RuntimeException("审核类型错误");
        }

        if(graduateAbroad!=null)
            criteria.andUserIdNotEqualTo(graduateAbroad.getUserId()).andCreateTimeGreaterThanOrEqualTo(graduateAbroad.getCreateTime());
        example.setOrderByClause("create_time asc");

        List<GraduateAbroad> memberApplies = graduateAbroadMapper.selectByExampleWithRowbounds(example, new RowBounds(0, 1));
        return (memberApplies.size()==0)?null:memberApplies.get(0);
    }
    
    public boolean idDuplicate(Integer id, Integer userId){

        GraduateAbroadExample example = new GraduateAbroadExample();
        GraduateAbroadExample.Criteria criteria = example.createCriteria().andUserIdEqualTo(userId);
        if(id!=null) criteria.andIdNotEqualTo(id);

        return graduateAbroadMapper.countByExample(example) > 0;
    }

    public GraduateAbroad get(int userId) {

        GraduateAbroadExample example = new GraduateAbroadExample();
        example.createCriteria().andUserIdEqualTo(userId);
        List<GraduateAbroad> graduateAbroads = graduateAbroadMapper.selectByExample(example);
        if(graduateAbroads.size()>0) return graduateAbroads.get(0);

        return null;
    }

    // 本人撤回
    @Transactional
    public void back(int userId){

        GraduateAbroad graduateAbroad = get(userId);
        if(graduateAbroad.getStatus()!= SystemConstants.GRADUATE_ABROAD_STATUS_APPLY)
            throw new DBErrorException("状态异常");
        GraduateAbroad record = new GraduateAbroad();
        record.setId(graduateAbroad.getId());
        record.setStatus(SystemConstants.GRADUATE_ABROAD_STATUS_SELF_BACK);
        record.setUserId(graduateAbroad.getUserId());
        //record.setBranchId(graduateAbroad.getBranchId());
        record.setIsBack(false);
        updateByPrimaryKeySelective(record);

        ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
        applyApprovalLogService.add(graduateAbroad.getId(),
                graduateAbroad.getPartyId(), graduateAbroad.getBranchId(), graduateAbroad.getUserId(),
                shiroUser.getId(), SystemConstants.APPLY_APPROVAL_LOG_USER_TYPE_SELF,
                SystemConstants.APPLY_APPROVAL_LOG_TYPE_GRADUATE_ABROAD,
                "撤回",
                SystemConstants.APPLY_APPROVAL_LOG_STATUS_NONEED,
                "撤回暂留申请");
    }
    

    // 支部审核通过
    @Transactional
    public void check1(int id){

        GraduateAbroad graduateAbroad = graduateAbroadMapper.selectByPrimaryKey(id);
        if(graduateAbroad.getStatus()!= SystemConstants.GRADUATE_ABROAD_STATUS_APPLY)
            throw new DBErrorException("状态异常");
        GraduateAbroad record = new GraduateAbroad();
        record.setId(graduateAbroad.getId());
        record.setUserId(graduateAbroad.getUserId());
        record.setStatus(SystemConstants.GRADUATE_ABROAD_STATUS_BRANCH_VERIFY);
        record.setIsBack(false);
        updateByPrimaryKeySelective(record);
    }

    // 直属党支部审核通过
    @Transactional
    public void checkByDirectBranch(int id){

        GraduateAbroad graduateAbroad = graduateAbroadMapper.selectByPrimaryKey(id);
        if(graduateAbroad.getStatus()!= SystemConstants.GRADUATE_ABROAD_STATUS_APPLY)
            throw new DBErrorException("状态异常");
        GraduateAbroad record = new GraduateAbroad();
        record.setId(graduateAbroad.getId());
        record.setUserId(graduateAbroad.getUserId());
        record.setStatus(SystemConstants.GRADUATE_ABROAD_STATUS_PARTY_VERIFY);
        record.setIsBack(false);
        updateByPrimaryKeySelective(record);
    }

    // 分党委审核通过
    @Transactional
    public void check2(int id, int branchId){

        GraduateAbroad graduateAbroad = graduateAbroadMapper.selectByPrimaryKey(id);
        if(graduateAbroad.getStatus()!= SystemConstants.GRADUATE_ABROAD_STATUS_BRANCH_VERIFY)
            throw new DBErrorException("状态异常");
        GraduateAbroad record = new GraduateAbroad();
        record.setId(graduateAbroad.getId());
        record.setUserId(graduateAbroad.getUserId());
        record.setToBranchId(branchId);
        record.setStatus(SystemConstants.GRADUATE_ABROAD_STATUS_PARTY_VERIFY);
        record.setIsBack(false);
        updateByPrimaryKeySelective(record);

        // 支部转移
        if(graduateAbroad.getBranchId()!=branchId){
            Map<Integer, Branch> branchMap = branchService.findAll();
            Branch branch = branchMap.get(branchId);
            if(branch==null || branch.getPartyId().intValue()!=graduateAbroad.getPartyId()){
                throw new RuntimeException("转移支部不存在");
            }
            Member member = new Member();
            member.setUserId(graduateAbroad.getUserId());
            member.setBranchId(branchId);
            memberMapper.updateByPrimaryKeySelective(member);
        }
    }

    // 组织部审核通过
    @Transactional
    public void check3(int id){

        GraduateAbroad graduateAbroad = graduateAbroadMapper.selectByPrimaryKey(id);

        if(graduateAbroad.getStatus()!= SystemConstants.GRADUATE_ABROAD_STATUS_PARTY_VERIFY)
            throw new DBErrorException("状态异常");

        GraduateAbroad record = new GraduateAbroad();
        record.setId(graduateAbroad.getId());
        record.setStatus(SystemConstants.GRADUATE_ABROAD_STATUS_OW_VERIFY);
        record.setUserId(graduateAbroad.getUserId());
        record.setIsBack(false);
        updateByPrimaryKeySelective(record);
    }
    @Transactional
    public int insertSelective(GraduateAbroad record){

        memberOpService.checkOpAuth(record.getUserId());

        return graduateAbroadMapper.insertSelective(record);
    }
    @Transactional
    public void del(Integer id){

        graduateAbroadMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        GraduateAbroadExample example = new GraduateAbroadExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        graduateAbroadMapper.deleteByExample(example);
    }

    @Transactional
    public int updateByPrimaryKeySelective(GraduateAbroad record){

        memberOpService.checkOpAuth(record.getUserId());

        if(record.getPartyId()!=null && record.getBranchId()==null){
            // 修改为直属党支部
            Assert.isTrue(partyService.isDirectBranch(record.getPartyId()));
            updateMapper.updateToDirectBranch("ow_member_stay", "id", record.getId(), record.getPartyId());
        }

        return graduateAbroadMapper.updateByPrimaryKeySelective(record);
    }

    @Transactional
    public void graduateAbroad_check(Integer[] ids, byte type, Integer branchId, int loginUserId){

        for (int id : ids) {
            GraduateAbroad graduateAbroad = null;
            if(type==1) {
                BaseController.VerifyAuth<GraduateAbroad> verifyAuth = checkVerityAuth(id);
                graduateAbroad = verifyAuth.entity;
                boolean isDirectBranch = verifyAuth.isDirectBranch;

                if (isDirectBranch) {
                    checkByDirectBranch(graduateAbroad.getId());
                } else {
                    check1(graduateAbroad.getId());
                }
            }
            if(type==2) {
                BaseController.VerifyAuth<GraduateAbroad> verifyAuth = checkVerityAuth2(id);
                graduateAbroad = verifyAuth.entity;

                check2(graduateAbroad.getId(), branchId);
            }
            if(type==3) {
                SecurityUtils.getSubject().checkRole("odAdmin");
                graduateAbroad = graduateAbroadMapper.selectByPrimaryKey(id);
                check3(graduateAbroad.getId());
            }
            
            int userId = graduateAbroad.getUserId();
            applyApprovalLogService.add(graduateAbroad.getId(),
                    graduateAbroad.getPartyId(), graduateAbroad.getBranchId(), userId,
                    loginUserId,  (type == 1)?SystemConstants.APPLY_APPROVAL_LOG_USER_TYPE_BRANCH:
                            (type == 2)?SystemConstants.APPLY_APPROVAL_LOG_USER_TYPE_PARTY:SystemConstants.APPLY_APPROVAL_LOG_USER_TYPE_OW,
                    SystemConstants.APPLY_APPROVAL_LOG_TYPE_GRADUATE_ABROAD, (type == 1)
                            ? "支部审核" : (type == 2)
                            ? "分党委审核" : "组织部审核", (byte) 1, null);
        }
    }

    @Transactional
    public void graduateAbroad_back(Integer[] ids, byte status, String reason, int loginUserId){

        boolean odAdmin = SecurityUtils.getSubject().hasRole("odAdmin");
        for (int id : ids) {

            GraduateAbroad graduateAbroad = graduateAbroadMapper.selectByPrimaryKey(id);
            Boolean presentPartyAdmin = CmTag.isPresentPartyAdmin(loginUserId, graduateAbroad.getPartyId());
            Boolean presentBranchAdmin = CmTag.isPresentBranchAdmin(loginUserId, graduateAbroad.getPartyId(), graduateAbroad.getBranchId());

            if(status >= SystemConstants.GRADUATE_ABROAD_STATUS_PARTY_VERIFY){
                if(!odAdmin) throw new UnauthorizedException();
            }
            if(status >= SystemConstants.GRADUATE_ABROAD_STATUS_BRANCH_VERIFY){
                if(!odAdmin && !presentPartyAdmin) throw new UnauthorizedException();
            }
            if(status >= SystemConstants.GRADUATE_ABROAD_STATUS_BACK){
                if(!odAdmin && !presentPartyAdmin && !presentBranchAdmin) throw new UnauthorizedException();
            }

            back(graduateAbroad, status, loginUserId, reason);
        }
    }

    // 单条记录打回至某一状态
    private  void back(GraduateAbroad graduateAbroad, byte status, int loginUserId, String reason){

        byte _status = graduateAbroad.getStatus();
        if(_status==SystemConstants.GRADUATE_ABROAD_STATUS_OW_VERIFY){
            throw new RuntimeException("审核流程已经完成，不可以打回。");
        }
        if(status > _status || status<SystemConstants.GRADUATE_ABROAD_STATUS_BACK ){
            throw new RuntimeException("参数有误。");
        }
        Integer id = graduateAbroad.getId();
        Integer userId = graduateAbroad.getUserId();
        updateMapper.graduateAbroad_back(id, status);

        GraduateAbroad record = new GraduateAbroad();
        record.setId(id);
        record.setUserId(userId);
        record.setReason(reason);
        record.setIsBack(true);
        updateByPrimaryKeySelective(record);

        applyApprovalLogService.add(id,
                graduateAbroad.getPartyId(), graduateAbroad.getBranchId(), userId,
                loginUserId, SystemConstants.APPLY_APPROVAL_LOG_USER_TYPE_ADMIN,
                SystemConstants.APPLY_APPROVAL_LOG_TYPE_GRADUATE_ABROAD, SystemConstants.GRADUATE_ABROAD_STATUS_MAP.get(status),
                SystemConstants.APPLY_APPROVAL_LOG_STATUS_BACK, reason);
    }
}
