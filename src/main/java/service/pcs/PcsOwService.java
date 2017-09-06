package service.pcs;

import controller.global.OpException;
import domain.pcs.PcsCandidateChosen;
import domain.pcs.PcsCandidateChosenExample;
import domain.pcs.PcsIssue;
import domain.pcs.PcsIssueExample;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;
import shiro.ShiroHelper;

import java.util.Date;
import java.util.List;

@Service
public class PcsOwService extends BaseMapper {

    public static final String TABLE_NAME = "pcs_candidate_chosen";

    // 入选名单
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
    public void changeOrder(int chosenId, int addNum) {

        if (addNum == 0) return;

        PcsCandidateChosen entity = pcsCandidateChosenMapper.selectByPrimaryKey(chosenId);
        Integer baseSortOrder = entity.getSortOrder();
        int configId = entity.getConfigId();
        byte stage = entity.getStage();
        byte type = entity.getType();

        PcsCandidateChosenExample example = new PcsCandidateChosenExample();
        if (addNum < 0) { // 升序

            example.createCriteria().andConfigIdEqualTo(configId)
                    .andStageEqualTo(stage)
                    .andTypeEqualTo(type).andSortOrderGreaterThan(baseSortOrder);
            example.setOrderByClause("sort_order asc");
        } else {

            example.createCriteria().andConfigIdEqualTo(configId)
                    .andStageEqualTo(stage)
                    .andTypeEqualTo(type).andSortOrderLessThan(baseSortOrder);
            example.setOrderByClause("sort_order desc");
        }

        List<PcsCandidateChosen> overEntities =
                pcsCandidateChosenMapper.selectByExampleWithRowbounds(example, new RowBounds(0, Math.abs(addNum)));
        if (overEntities.size() > 0) {

            PcsCandidateChosen targetEntity = overEntities.get(overEntities.size() - 1);

            if (addNum < 0)
                commonMapper.downOrder(TABLE_NAME,  "config_id=" + configId +
                        " and stage=" + stage +
                        " and type=" + type, baseSortOrder, targetEntity.getSortOrder());
            else
                commonMapper.upOrder(TABLE_NAME,  "config_id=" + configId +
                        " and stage=" + stage +
                        " and type=" + type, baseSortOrder, targetEntity.getSortOrder());

            PcsCandidateChosen record = new PcsCandidateChosen();
            record.setId(chosenId);
            record.setSortOrder(targetEntity.getSortOrder());
            pcsCandidateChosenMapper.updateByPrimaryKeySelective(record);
        }
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
