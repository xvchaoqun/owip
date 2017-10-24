package service.crs;

import domain.crs.CrsApplicantExample;
import domain.crs.CrsApplyRule;
import domain.crs.CrsApplyRuleExample;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;
import sys.constants.SystemConstants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class CrsApplyRuleService extends BaseMapper {

    // 根据报名规则，判断是否可以报名
    public boolean canApply(int userId, int postId){

        List<CrsApplyRule> crsApplyRules = null;
        {
            Date now = new Date();
            CrsApplyRuleExample example = new CrsApplyRuleExample();
            example.createCriteria().andContainPostId(postId)
                    .andStatusNotEqualTo(SystemConstants.CRS_APPLY_RULE_STATUS_DELETE)
                    .andEndTimeGreaterThanOrEqualTo(now);
            crsApplyRules = crsApplyRuleMapper.selectByExample(example);
        }
        if(crsApplyRules==null) return true;

        for (CrsApplyRule crsApplyRule : crsApplyRules) {

            int limit = crsApplyRule.getNum();
            String postIdsStr = crsApplyRule.getPostIds();
            String[] postIdsStrArray = postIdsStr.split(",");
            List<Integer> postIds = new ArrayList<>();
            for (String postIdStr : postIdsStrArray) {
                postIds.add(Integer.valueOf(postIdStr));
            }
            // 在当前规则下，已报名的数量
            CrsApplicantExample example = new CrsApplicantExample();
            example.createCriteria()
                    .andUserIdEqualTo(userId)
                    .andPostIdIn(postIds);
            int count = (int)crsApplicantMapper.countByExample(example);

            if(count>=limit) return false;
        }

        return true;
    }

    @Transactional
    public void insertSelective(CrsApplyRule record){

        crsApplyRuleMapper.insertSelective(record);
    }

    @Transactional
    public void del(Integer id){

        crsApplyRuleMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        CrsApplyRuleExample example = new CrsApplyRuleExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        crsApplyRuleMapper.deleteByExample(example);
    }

    @Transactional

    public int updateByPrimaryKeySelective(CrsApplyRule record){

        return crsApplyRuleMapper.updateByPrimaryKeySelective(record);
    }
}
