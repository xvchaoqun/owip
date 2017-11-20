package service.pcs;

import controller.global.OpException;
import controller.pcs.vote.PcsVoteCandidateFormBean;
import domain.pcs.PcsVoteCandidate;
import domain.pcs.PcsVoteCandidateExample;
import domain.pcs.PcsVoteGroup;
import domain.pcs.PcsVoteGroupExample;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import persistence.common.bean.IPcsCandidateView;
import service.BaseMapper;
import service.sys.SysUserService;
import shiro.ShiroHelper;
import sys.constants.SystemConstants;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;

@Service
public class PcsVoteGroupService extends BaseMapper {

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    protected PcsConfigService pcsConfigService;

    public List<PcsVoteGroup> getAdminGroups(int recordUserId){

        PcsVoteGroupExample example = new PcsVoteGroupExample();
        example.createCriteria().andRecordUserIdEqualTo(recordUserId);
        return pcsVoteGroupMapper.selectByExample(example);
    }

    @Transactional
    public void insertSelective(PcsVoteGroup record) {

        pcsVoteGroupMapper.insertSelective(record);

        int type = record.getType();
        String role = (type == SystemConstants.PCS_USER_TYPE_DW ? SystemConstants.ROLE_PCS_VOTE_DW :
                SystemConstants.ROLE_PCS_VOTE_JW);
        if(record.getRecordUserId()!=null){
            sysUserService.addRole(record.getRecordUserId(), role);
        }
    }

    @Transactional
    public void updateByPrimaryKeySelective(PcsVoteGroup record) {

        PcsVoteGroup pcsVoteGroup = pcsVoteGroupMapper.selectByPrimaryKey(record.getId());
        int type = pcsVoteGroup.getType();
        String role = (type == SystemConstants.PCS_USER_TYPE_DW ? SystemConstants.ROLE_PCS_VOTE_DW :
                SystemConstants.ROLE_PCS_VOTE_JW);

        record.setType(null);
        pcsVoteGroupMapper.updateByPrimaryKeySelective(record);

        Integer oldRecordUserId = pcsVoteGroup.getRecordUserId();
        Integer newRecordUserId = record.getRecordUserId();

        // 先清空
        if(newRecordUserId==null){
            commonMapper.excuteSql("update pcs_vote_group set record_user_id=null where id="+ record.getId());
        }

        if(oldRecordUserId != null && (newRecordUserId==null || oldRecordUserId != newRecordUserId)){

            if(getAdminGroups(oldRecordUserId).size()==0) {
                sysUserService.delRole(oldRecordUserId, role);
            }
        }

        if(newRecordUserId!=null && (oldRecordUserId==null || oldRecordUserId!=newRecordUserId)){
            sysUserService.addRole(newRecordUserId, role);
        }
    }

    @Transactional
    public void batchDel(Integer[] ids) {

        if (ids == null || ids.length == 0) return;

        PcsVoteGroupExample example = new PcsVoteGroupExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        pcsVoteGroupMapper.deleteByExample(example);
    }

    public boolean allowModify(int groupId) {

        int userId = ShiroHelper.getCurrentUserId();
        PcsVoteGroup pcsVoteGroup = pcsVoteGroupMapper.selectByPrimaryKey(groupId);
        Integer recordUserId = pcsVoteGroup.getRecordUserId();

        return (recordUserId!=null && recordUserId==userId && !pcsVoteGroup.getHasReport());
    }

    @Transactional
    public void submit(PcsVoteGroup pcsVoteGroup, List<PcsVoteCandidateFormBean> beans) {

        int configId = pcsConfigService.getCurrentPcsConfig().getId();
        int groupId = pcsVoteGroup.getId();
        int valid = pcsVoteGroup.getValid();

        PcsVoteGroup _pcsVoteGroup = pcsVoteGroupMapper.selectByPrimaryKey(groupId);
        // 先清空
        PcsVoteCandidateExample example = new PcsVoteCandidateExample();
        example.createCriteria().andGroupIdEqualTo(groupId);
        pcsVoteCandidateMapper.deleteByExample(example);

        for (PcsVoteCandidateFormBean bean : beans) {

            PcsVoteCandidate record = new PcsVoteCandidate();
            try {
                PropertyUtils.copyProperties(record, bean);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }

            int userId = record.getUserId();
            byte type = _pcsVoteGroup.getType();

            List<IPcsCandidateView> records = iPcsMapper.selectPartyCandidates(userId, true,
                    configId, SystemConstants.PCS_STAGE_THIRD, type, new RowBounds(0,1));
            boolean isFromStage = (records.size()>0);
            record.setIsFromStage(isFromStage);
            if(isFromStage) {
                int degree = record.getDegree();
                int abstain = record.getAbstain();
                int agree = valid -(degree + abstain);
                if(agree<0){
                    throw new OpException("{0}票数有误。", bean.getRealname());
                }
                record.setAgree(agree);

                IPcsCandidateView candidate = records.get(0);
                record.setSortOrder(candidate.getSortOrder());

            }else{
                record.setDegree(0);
                record.setAbstain(0);
            }
            record.setGroupId(groupId);

            pcsVoteCandidateMapper.insertSelective(record);
        }

        pcsVoteGroupMapper.updateByPrimaryKeySelective(pcsVoteGroup);
    }

    @Transactional
    public void report(int groupId) {

        PcsVoteGroup record = new PcsVoteGroup();
        record.setHasReport(true);

        PcsVoteGroupExample example = new PcsVoteGroupExample();
        example.createCriteria().andIdEqualTo(groupId)
                .andRecordUserIdEqualTo(ShiroHelper.getCurrentUserId());
        pcsVoteGroupMapper.updateByExampleSelective(record, example);
    }

    @Transactional
    public void back(int groupId) {

        PcsVoteGroup record = new PcsVoteGroup();
        record.setId(groupId);
        record.setHasReport(false);

        pcsVoteGroupMapper.updateByPrimaryKeySelective(record);
    }
}
