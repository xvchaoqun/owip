package service.pcs;

import controller.global.OpException;
import controller.pcs.pr.PcsPrCandidateFormBean;
import domain.cadre.CadreView;
import domain.member.Member;
import domain.party.Party;
import domain.pcs.*;
import domain.sys.StudentInfo;
import domain.sys.SysUserView;
import domain.sys.TeacherInfo;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.cadre.CadreService;
import service.party.MemberService;
import service.party.PartyService;
import service.sys.StudentInfoService;
import service.sys.SysUserService;
import service.sys.TeacherInfoService;
import shiro.ShiroHelper;
import sys.constants.CadreConstants;
import sys.constants.MemberConstants;
import sys.constants.PcsConstants;
import sys.constants.SystemConstants;
import sys.utils.DateUtils;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static sys.constants.MemberConstants.MEMBER_POLITICAL_STATUS_POSITIVE;

@Service
public class PcsPrPartyService extends PcsBaseMapper {

    @Autowired
    private CadreService cadreService;
    @Autowired
    private MemberService memberService;
    @Autowired
    private TeacherInfoService teacherInfoService;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private PcsPrCandidateService pcsPrCandidateService;
    @Autowired
    private PartyService partyService;
    @Autowired
    private StudentInfoService studentInfoService;
    @Autowired
    private PcsAdminService pcsAdminService;
    @Autowired
    private PcsConfigService pcsConfigService;
    @Autowired
    private PcsPartyService pcsPartyService;

    // 分党委是否可以修改当前阶段的数据
    public boolean allowModify(int partyId, int configId, byte stage){

        // 二下二上和三下三上，在组织部审批通过上一阶段前，不可填写
        if(stage== PcsConstants.PCS_STAGE_SECOND){
            PcsPrRecommend _check = getPcsPrRecommend(configId, PcsConstants.PCS_STAGE_FIRST, partyId);
            if(_check == null ||  _check.getStatus()==null || _check.getStatus() != PcsConstants.PCS_PR_RECOMMEND_STATUS_PASS) return false;
        }else if(stage==PcsConstants.PCS_STAGE_THIRD){
            PcsPrRecommend _check = getPcsPrRecommend(configId, PcsConstants.PCS_STAGE_SECOND, partyId);
            if(_check == null || _check.getStatus()==null || _check.getStatus() != PcsConstants.PCS_PR_RECOMMEND_STATUS_PASS) return false;
        }

        // 分党委已经报送之后，不可修改数据
        PcsPrRecommend pcsPrRecommend = getPcsPrRecommend(configId, stage, partyId);
        return (pcsPrRecommend==null || BooleanUtils.isNotTrue(pcsPrRecommend.getHasReport()));
    }

    // 分党委管理员报送，报送后数据不可修改
    @Transactional
    public void report(int partyId, int configId, byte stage) {

        Integer userId = ShiroHelper.getCurrentUserId();

        PcsPrRecommend pcsPrRecommend = getPcsPrRecommend(configId, stage, partyId);
        if(pcsPrRecommend==null) throw new OpException("提交的数据有误。");

        if(!allowModify(partyId, configId, stage)){
            throw new OpException("您已经报送，请勿重复操作。");
        }

        PcsPrRecommend record = new PcsPrRecommend();
        record.setId(pcsPrRecommend.getId());
        record.setHasReport(true);
        record.setReportUserId(userId);
        record.setReportTime(new Date());

        // 归档分党委数量
        PcsParty pcsParty = pcsPartyService.get(configId, partyId);
        record.setBranchCount(pcsParty.getBranchCount());
        record.setMemberCount(pcsParty.getMemberCount());
        record.setPositiveCount(pcsParty.getPositiveCount());
        record.setTeacherMemberCount(pcsParty.getTeacherMemberCount());
        record.setRetireMemberCount(pcsParty.getRetireMemberCount());
        record.setStudentMemberCount(pcsParty.getStudentMemberCount());

        // 报送后待审核
        record.setStatus(PcsConstants.PCS_PR_RECOMMEND_STATUS_INIT);

        pcsPrRecommendMapper.updateByPrimaryKeySelective(record);
    }

    // 获取一个已经填报的名单
    public PcsPrRecommend getPcsPrRecommend(int configId, byte stage, int partyId) {

        PcsPrRecommendExample example = new PcsPrRecommendExample();
        example.createCriteria().andConfigIdEqualTo(configId)
                .andStageEqualTo(stage).andPartyIdEqualTo(partyId);
        List<PcsPrRecommend> pcsPrRecommends = pcsPrRecommendMapper.selectByExample(example);

        return (pcsPrRecommends.size()>0)?pcsPrRecommends.get(0):null;
    }

    @Transactional
    public void submit(int configId, byte stage, int partyId,
                       PcsPrRecommend record,
                       List<PcsPrCandidateFormBean> beans) {

        // for test
        if(!allowModify(partyId, configId, stage)){
            throw  new OpException("已报送数据或上一阶段未审核通过，不可修改。");
        }

        record.setConfigId(configId);
        record.setStage(stage);
        record.setPartyId(partyId);
        record.setHasReport(false);
        //record.setStatus(PcsConstants.PCS_PR_RECOMMEND_STATUS_INIT);

        PcsPrRecommend pcsPrRecommend = getPcsPrRecommend(configId, stage, partyId);
        if(pcsPrRecommend==null || pcsPrRecommend.getId()==null){
            pcsPrRecommendMapper.insertSelective(record);
        }else{
            record.setId(pcsPrRecommend.getId());
            pcsPrRecommendMapper.updateByPrimaryKeySelective(record);
        }

        Map<Integer, PcsPrCandidate> selectedMap = new LinkedHashMap<>();
        if(stage == PcsConstants.PCS_STAGE_SECOND){
            selectedMap = pcsPrCandidateService.findSelectedMap(configId, PcsConstants.PCS_STAGE_FIRST, partyId);
        }else if(stage == PcsConstants.PCS_STAGE_THIRD){
            selectedMap = pcsPrCandidateService.findSelectedMap(configId, PcsConstants.PCS_STAGE_SECOND, partyId);
        }

        int recommendId = record.getId();
        Date now = new Date();

        // 添加三类被推荐人
        // 先清空
        pcsPrCandidateService.clear(recommendId);
        if(beans!=null){
            for (PcsPrCandidateFormBean bean : beans) {

                byte type = bean.getType();
                int userId = bean.getUserId();
                SysUserView uv = sysUserService.findById(userId);
                if(uv==null){
                    throw new OpException("用户不存在：{0}", userId+"");
                }

                String alertMsg = "，请确认学工号是否正确。";
                // for test
                Member member = memberService.get(userId);
                if(member== null && member.getPoliticalStatus()
                        != MEMBER_POLITICAL_STATUS_POSITIVE){
                    throw new OpException("用户{0}不是正式党员" + alertMsg, uv.getRealname());
                }

                int memberPartyId = member.getPartyId();
                if(memberPartyId != partyId){
                    throw new OpException("用户{0}不是本单位人员" + alertMsg, uv.getRealname());
                }

                // 类型校验
                if(type ==PcsConstants.PCS_PR_TYPE_PRO){
                    TeacherInfo teacherInfo = teacherInfoService.get(userId);
                    if(member.getType()!= MemberConstants.MEMBER_TYPE_TEACHER
                            || teacherInfo==null || BooleanUtils.isTrue(teacherInfo.getIsRetire())){
                        throw new OpException("用户{0}不是在职教职工" + alertMsg, uv.getRealname());
                    }
                }else if(type ==PcsConstants.PCS_PR_TYPE_RETIRE){

                    TeacherInfo teacherInfo = teacherInfoService.get(userId);
                    if(teacherInfo==null || BooleanUtils.isNotTrue(teacherInfo.getIsRetire())){
                        throw new OpException("用户{0}不是离退休教职工" + alertMsg, uv.getRealname());
                    }
                }else if(type ==PcsConstants.PCS_PR_TYPE_STU){
                    if(member.getType()!=MemberConstants.MEMBER_TYPE_STUDENT){
                        throw new OpException("用户{0}不是学生" + alertMsg, uv.getRealname());
                    }
                }else{
                    throw new OpException("用户{0}类型有误" + alertMsg, uv.getRealname());
                }

                PcsPrCandidate pcsPrCandidate = pcsPrCandidateService.find(userId, configId, stage);
                if(pcsPrCandidate!=null){
                    Party party = partyService.findAll().get(pcsPrCandidate.getPartyId());
                    throw new OpException("用户{0}已是{1}的被推荐人，不可重复推荐。",
                            pcsPrCandidate.getRealname(), party.getName());
                }

                PcsPrCandidate _candidate = new PcsPrCandidate();
                _candidate.setRecommendId(recommendId);
                _candidate.setType(type);
                _candidate.setUserId(userId);
                _candidate.setBranchVote(bean.getBranchVote());
                _candidate.setVote(bean.getVote());
                _candidate.setPositiveVote(bean.getPositiveVote());
                _candidate.setGender(bean.getGender());
                _candidate.setBirth(DateUtils.parseDate(bean.getBirth(), DateUtils.YYYY_MM_DD));
                _candidate.setNation(StringUtils.trim(bean.getNation()));
                _candidate.setAddTime(now);
                _candidate.setIsFromStage(selectedMap.containsKey(userId));

                pcsPrCandidateService.insertSelective(_candidate);
            }
        }
    }

    // 三下三上， 提交党员大会选举情况
    @Transactional
    public void submit3(int configId, int partyId, PcsPrRecommend record, List<PcsPrCandidateFormBean> beans) {

        byte stage = PcsConstants.PCS_STAGE_THIRD;
        record.setConfigId(configId);
        record.setStage(stage);
        record.setPartyId(partyId);
        record.setHasReport(false);
        record.setExpectMemberCount(0);
        record.setActualMemberCount(0);

        PcsPrRecommend pcsPrRecommend = getPcsPrRecommend(configId, stage, partyId);
        if(pcsPrRecommend==null || pcsPrRecommend.getId()==null){
            pcsPrRecommendMapper.insertSelective(record);
        }else{
            record.setId(pcsPrRecommend.getId());
            pcsPrRecommendMapper.updateByPrimaryKeySelective(record);
        }

        // 阶段三共用阶段二的候选人名单
        PcsPrRecommend pcsPrRecommend2 = getPcsPrRecommend(configId, PcsConstants.PCS_STAGE_SECOND, partyId);
        int recommendId = pcsPrRecommend2.getId();
        for (PcsPrCandidateFormBean bean : beans) {

            int userId = bean.getUserId();
            int vote = bean.getVote();

            PcsPrCandidate _candidate = new PcsPrCandidate();
            _candidate.setVote3(vote);

            PcsPrCandidateExample example = new PcsPrCandidateExample();
            example.createCriteria().andRecommendIdEqualTo(recommendId).andUserIdEqualTo(userId);
            int ret = pcsPrCandidateMapper.updateByExampleSelective(_candidate, example);

            if(ret!=1){
                throw new OpException("数据有误。");
            }
        }

    }

    // 读取党代表的基本信息
    @Transactional
    public PcsPrCandidate getCandidateInfo(Integer userId, byte stage){

        PcsAdmin pcsAdmin = pcsAdminService.getAdmin(ShiroHelper.getCurrentUserId());
        int partyId = pcsAdmin.getPartyId();
        int configId = pcsConfigService.getCurrentPcsConfig().getId();
        Map<Integer, PcsPrCandidate> selectedMap = pcsPrCandidateService.findSelectedMap(configId, stage, partyId);

        SysUserView uv = sysUserService.findById(userId);

        PcsPrCandidate candidate = new PcsPrCandidate();
        candidate.setUserId(uv.getId());
        candidate.setCode(uv.getCode());
        candidate.setRealname(uv.getRealname());
        candidate.setPartyId(partyId);

        PcsPrCandidate _candidate = selectedMap.get(userId);
        if(_candidate!=null){
            // 如果是上一阶段的入选名单，则读取之前填写的性别、民族、出生年月
            candidate.setGender(_candidate.getGender());
            candidate.setNation(_candidate.getNation());
            candidate.setBirth(_candidate.getBirth());
        }else {
            if (uv.getGender() != null && SystemConstants.GENDER_MAP.containsKey(uv.getGender()))
            candidate.setGender(uv.getGender());
            candidate.setNation(uv.getNation());
            candidate.setBirth(uv.getBirth());
        }

         if(uv.getType()==SystemConstants.USER_TYPE_JZG){

            TeacherInfo teacherInfo = teacherInfoService.get(userId);
            CadreView cv = cadreService.dbFindByUserId(userId);
            if(cv!=null && CadreConstants.CADRE_STATUS_NOW_SET.contains(cv.getStatus())){
                // 是干部
                candidate.setUserType(PcsConstants.PCS_PR_USER_TYPE_CADRE);
                candidate.setEduId(cv.getEduId());
                candidate.setWorkTime(cv.getWorkTime());
                candidate.setPost(cv.getPost());
            }else{
                // 是普通教师
                candidate.setUserType(PcsConstants.PCS_PR_USER_TYPE_TEACHER);
                candidate.setEducation(teacherInfo.getEducation());
                candidate.setWorkTime(teacherInfo.getWorkTime());
                candidate.setIsRetire(teacherInfo.getIsRetire());
                candidate.setProPost(teacherInfo.getProPost());
            }
        }else{
            StudentInfo studentInfo = studentInfoService.get(userId);
            // 学生
            candidate.setUserType(PcsConstants.PCS_PR_USER_TYPE_STU);
            candidate.setEduLevel(studentInfo.getEduLevel());
        }

        Member member = memberService.get(userId);
             if(member==null || member.getPoliticalStatus() != MEMBER_POLITICAL_STATUS_POSITIVE){
            throw new OpException(uv.getRealname() + "不是正式党员。");
        }

        candidate.setGrowTime(member.getGrowTime());

        return candidate;
    }
}
