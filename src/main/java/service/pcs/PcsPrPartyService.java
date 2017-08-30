package service.pcs;

import controller.global.OpException;
import controller.pcs.PcsPrCandidateFormBean;
import domain.member.Member;
import domain.pcs.PcsPrCandidate;
import domain.pcs.PcsPrRecommend;
import domain.pcs.PcsPrRecommendExample;
import domain.sys.SysUserView;
import domain.sys.TeacherInfo;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;
import service.cadre.CadreService;
import service.member.MemberService;
import service.sys.SysUserService;
import service.sys.TeacherInfoService;
import shiro.ShiroHelper;
import sys.constants.SystemConstants;

import java.util.Date;
import java.util.List;

@Service
public class PcsPrPartyService extends BaseMapper {

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

    // 分党委是否已上报
    public boolean hasReport(int partyId, int configId, byte stage){

        // 分党委已经下发名单
        //if(pcsOwService.hasIssue(configId, stage)) return true;

        PcsPrRecommend pcsPrRecommend = getPcsPrRecommend(configId, stage, partyId);
        return (pcsPrRecommend!=null && BooleanUtils.isTrue(pcsPrRecommend.getHasReport()));
    }

    // 管理员上报，上报后数据不可修改
    @Transactional
    public void report(int partyId, int configId, byte stage) {

        Integer userId = ShiroHelper.getCurrentUserId();

        PcsPrRecommend pcsPrRecommend = getPcsPrRecommend(configId, stage, partyId);
        if(pcsPrRecommend==null) throw new OpException("提交的数据有误。");

        PcsPrRecommend record = new PcsPrRecommend();
        record.setHasReport(true);
        record.setReportUserId(userId);
        record.setReportTime(new Date());

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

        /*if(pcsAdminService.hasReport(partyId, configId, stage)){
            throw  new OpException("已上报数据或已下发名单，不可修改。");
        }*/

        record.setConfigId(configId);
        record.setStage(stage);
        record.setPartyId(partyId);
        record.setHasReport(false);
        record.setStatus(SystemConstants.PCS_PR_RECOMMEND_STATUS_INIT);

        PcsPrRecommend pcsPrRecommend = getPcsPrRecommend(configId, stage, partyId);
        if(pcsPrRecommend==null || pcsPrRecommend.getId()==null){
            pcsPrRecommendMapper.insertSelective(record);
        }else{
            record.setId(pcsPrRecommend.getId());
            pcsPrRecommendMapper.updateByPrimaryKeySelective(record);
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
                    throw new OpException("用户不存在：%s", userId+"");
                }

                Member member = memberService.get(userId);
                if(member== null && member.getPoliticalStatus()
                        != SystemConstants.MEMBER_POLITICAL_STATUS_POSITIVE){
                    throw new OpException("用户%s不是正式党员", uv.getRealname());
                }

                int memberPartyId = member.getPartyId();
                if(memberPartyId != partyId){
                    throw new OpException("用户%s不是本单位人员", uv.getRealname());
                }

                // 类型校验
                if(type ==SystemConstants.PCS_PR_TYPE_PRO){
                    TeacherInfo teacherInfo = teacherInfoService.get(userId);
                    if(member.getType()!=SystemConstants.MEMBER_TYPE_TEACHER
                            || teacherInfo==null || BooleanUtils.isTrue(teacherInfo.getIsRetire())){
                        throw new OpException("用户%s不是在职教职工", uv.getRealname());
                    }
                }else if(type ==SystemConstants.PCS_PR_TYPE_RETIRE){

                    TeacherInfo teacherInfo = teacherInfoService.get(userId);
                    if(teacherInfo==null || BooleanUtils.isNotTrue(teacherInfo.getIsRetire())){
                        throw new OpException("用户%s不是离退休教职工", uv.getRealname());
                    }
                }else if(type ==SystemConstants.PCS_PR_TYPE_STU){
                    if(member.getType()!=SystemConstants.MEMBER_TYPE_STUDENT){
                        throw new OpException("用户%s不是学生", uv.getRealname());
                    }
                }else{
                    throw new OpException("用户%s类型有误", uv.getRealname());
                }

                PcsPrCandidate _candidate = new PcsPrCandidate();
                _candidate.setRecommendId(recommendId);
                _candidate.setType(type);
                _candidate.setUserId(userId);
                _candidate.setVote(bean.getVote());
                _candidate.setAddTime(now);
                _candidate.setIsFromStage(false);

                pcsPrCandidateService.insertSelective(_candidate);
            }
        }

    }

}
