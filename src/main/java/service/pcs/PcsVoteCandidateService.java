package service.pcs;

import domain.pcs.PcsCandidateChosen;
import domain.pcs.PcsCandidateView;
import domain.pcs.PcsVoteCandidate;
import domain.pcs.PcsVoteCandidateExample;
import domain.pcs.PcsVoteMember;
import domain.pcs.PcsVoteMemberExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;
import service.sys.SysUserService;
import sys.constants.SystemConstants;
import sys.utils.DateUtils;

import java.util.Arrays;
import java.util.List;

@Service
public class PcsVoteCandidateService extends BaseMapper {

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    protected PcsOwService pcsOwService;

    @Transactional
    public void batchDel(Integer[] ids) {

        if (ids == null || ids.length == 0) return;

        PcsVoteCandidateExample example = new PcsVoteCandidateExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        PcsVoteCandidate record = new PcsVoteCandidate();
        pcsVoteCandidateMapper.updateByExampleSelective(record, example);
    }

    @Transactional
    public void updateByPrimaryKeySelective(PcsVoteCandidate record) {

        pcsVoteCandidateMapper.updateByPrimaryKeySelective(record);
    }
    
    @Transactional
    public void choose(Integer[] ids, Boolean isChosen, int configId, byte type) {
        if (ids == null || ids.length == 0) return;
        

        for (Integer userId : ids) {

            PcsVoteMemberExample example = new PcsVoteMemberExample();
            example.createCriteria().andConfigIdEqualTo(configId)
                    .andTypeEqualTo(type).andUserIdEqualTo(userId);
            List<PcsVoteMember> pcsVoteMembers = pcsVoteMemberMapper.selectByExample(example);
            PcsVoteMember pcsVoteMember = null;
            if(pcsVoteMembers.size()>0) pcsVoteMember = pcsVoteMembers.get(0);

            if(pcsVoteMember!=null){
                pcsVoteMemberMapper.deleteByPrimaryKey(pcsVoteMember.getId());
            }

            if(isChosen) {

                List<PcsVoteCandidate> pcsVoteCandidates = iPcsMapper.selectVoteCandidateStatList(type, userId, null);
                PcsVoteCandidate pcsVoteCandidate = pcsVoteCandidates.get(0);

                PcsVoteMember record = new PcsVoteMember();
                record.setConfigId(configId);
                record.setType(type);
                record.setUserId(userId);
                record.setRealname(pcsVoteCandidate.getRealname());
                record.setAgree(pcsVoteCandidate.getAgree());
                record.setDegree(pcsVoteCandidate.getDegree());
                record.setAbstain(pcsVoteCandidate.getAbstain());

                PcsCandidateChosen pcsCandidateChosen = pcsOwService.getPcsCandidateChosen(userId,
                        configId, SystemConstants.PCS_STAGE_THIRD, type);
                if(pcsCandidateChosen!=null) {

                    record.setSortOrder(pcsCandidateChosen.getSortOrder());

                    PcsCandidateView candidate = pcsOwService.getPcsCandidateView(userId,
                            configId, SystemConstants.PCS_STAGE_THIRD, type);

                    record.setGender(candidate.getGender());
                    record.setNation(candidate.getNation());
                    record.setProPost(candidate.getProPost());
                    record.setBirth(candidate.getBirth());
                    record.setAge(DateUtils.yearOffNow(record.getBirth()));
                    record.setGrowTime(candidate.getGrowTime());
                    record.setTitle(candidate.getTitle());
                }else{
                    // 另选他人按笔画排序时排在后面
                    record.setSortOrder(Integer.MAX_VALUE);
                }

                pcsVoteMemberMapper.insertSelective(record);
            }
        }
    }
}
