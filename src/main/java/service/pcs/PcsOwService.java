package service.pcs;

import controller.global.OpException;
import domain.pcs.*;
import org.apache.ibatis.session.RowBounds;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shiro.ShiroHelper;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PcsOwService extends PcsBaseMapper {

    public static final String TABLE_NAME = "pcs_candidate_chosen";


    public PcsCandidateChosen getPcsCandidateChosen(int userId, int configId, byte stage, byte type){
        PcsCandidateChosenExample example = new PcsCandidateChosenExample();
        example.createCriteria().andConfigIdEqualTo(configId)
                .andStageEqualTo(stage).andTypeEqualTo(type).andUserIdEqualTo(userId);

        List<PcsCandidateChosen> pcsCandidateChosens = pcsCandidateChosenMapper.selectByExample(example);
        return (pcsCandidateChosens.size()>0)?pcsCandidateChosens.get(0) : null;
    }

    public PcsCandidate getPcsCandidate(int userId, int configId, byte stage, byte type){

        PcsCandidateExample example = new PcsCandidateExample();
        example.createCriteria().andConfigIdEqualTo(configId)
                .andStageEqualTo(stage).andTypeEqualTo(type).andUserIdEqualTo(userId);

        List<PcsCandidate> pcsCandidates = pcsCandidateMapper.selectByExampleWithRowbounds(example, new RowBounds(0, 1));
        return (pcsCandidates.size()>0)?pcsCandidates.get(0) : null;
    }

    // 入选名单
    @CacheEvict(value = "DataCaches", key = "'pcsCandidateUserIds_' + #configId + '_' + #stage + '_' + #type")
    @Transactional
    public void choose(Integer[] ids, boolean choose, int configId, byte stage, byte type) {

        if (ids == null || ids.length == 0) return;

        if (hasIssue(configId, stage)) {
            throw new OpException("已下发名单，不可添加");
        }

        for (Integer userId : ids) {

            PcsCandidateChosenExample example = new PcsCandidateChosenExample();
            example.createCriteria().andConfigIdEqualTo(configId)
                    .andStageEqualTo(stage).andTypeEqualTo(type).andUserIdEqualTo(userId);
            List<PcsCandidateChosen> pcsCandidateChosens = pcsCandidateChosenMapper.selectByExample(example);
            PcsCandidateChosen pcsCandidateChosen = null;
            if(pcsCandidateChosens.size()>0) pcsCandidateChosen = pcsCandidateChosens.get(0);
            if(choose) {

                if(pcsCandidateChosen!=null) continue; // 已经在名单中

                PcsCandidateChosen record = new PcsCandidateChosen();
                record.setConfigId(configId);
                record.setStage(stage);
                record.setType(type);
                record.setUserId(userId);
                record.setAddTime(new Date());
                record.setSortOrder(getNextSortOrder(TABLE_NAME,
                        "config_id=" + configId +
                                " and stage=" + stage +
                                " and type=" + type));

                pcsCandidateChosenMapper.insertSelective(record);
            }else{
                if(pcsCandidateChosen!=null){
                    pcsCandidateChosenMapper.deleteByPrimaryKey(pcsCandidateChosen.getId());
                }
            }
        }
    }

    // 升序排列
    @Transactional
    @CacheEvict(value = "DataCaches", key = "'pcsCandidateUserIds_' + #result.configId + '_' + #result.stage + '_' + #result.type")
    public PcsCandidateChosen changeOrder(int candidateId, int addNum) {

        PcsCandidate pcsCandidate = pcsCandidateMapper.selectByPrimaryKey(candidateId);
        int userId = pcsCandidate.getUserId();
        int configId = pcsCandidate.getConfigId();
        byte stage = pcsCandidate.getStage();
        byte type = pcsCandidate.getType();

        PcsCandidateChosen entity = getPcsCandidateChosen(userId, configId, stage, type);

        changeOrder(TABLE_NAME, "config_id=" + entity.getConfigId() +
                " and stage=" + entity.getStage() +
                " and type=" + entity.getType(), ORDER_BY_ASC, entity.getId(), addNum);
        return entity;
    }

    @Cacheable(value = "DataCaches", key = "'pcsCandidateUserIds_' + #configId + '_' + #stage + '_' + #type")
    public List<Integer> getCandidateUserIds(int configId, byte stage, byte type){

        PcsCandidateChosenExample example = new PcsCandidateChosenExample();
        example.createCriteria().andConfigIdEqualTo(configId).andStageEqualTo(stage).andTypeEqualTo(type);
        List<PcsCandidateChosen> pcsCandidateChosens = pcsCandidateChosenMapper.selectByExample(example);

        //List<IPcsCandidate> candidates = iPcsMapper.selectPartyCandidateList(null, true, configId, stage, type, new RowBounds());
        return pcsCandidateChosens.stream().map(PcsCandidateChosen::getUserId).collect(Collectors.toList());
    }

    // 下发名单
    public void issue(int configId, byte stage) {

        PcsIssue record = new PcsIssue();
        record.setUserId(ShiroHelper.getCurrentUserId());
        record.setConfigId(configId);
        record.setStage(stage);
        record.setIssueTime(new Date());

        pcsIssueMapper.insertSelective(record);
    }

    // 判断是否下发
    public boolean hasIssue(int configId, byte stage) {

        PcsIssueExample example = new PcsIssueExample();
        example.createCriteria().andConfigIdEqualTo(configId)
                .andStageEqualTo(stage);

        return pcsIssueMapper.countByExample(example) > 0;
    }

    // 退回报送
    public void reportBack(int reportId) {

        pcsAdminReportMapper.deleteByPrimaryKey(reportId);
    }
}
