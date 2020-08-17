package service.pcs;

import domain.cadre.CadreView;
import domain.member.MemberView;
import domain.pcs.PcsPrCandidate;
import domain.pcs.PcsPrCandidateExample;
import domain.pcs.PcsRecommend;
import domain.sys.StudentInfo;
import domain.sys.SysUserView;
import domain.sys.TeacherInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sys.constants.CadreConstants;
import sys.tags.CmTag;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class PcsPrCandidateService extends PcsBaseMapper {

    public static final String TABLE_NAME = "pcs_pr_candidate";

    // 获得某个分党委入选名单列表
    public Map<Integer, PcsPrCandidate> findSelectedMap(int configId, byte stage, int partyId){

        Map<Integer, PcsPrCandidate> resultMap = new LinkedHashMap<>();

        PcsPrCandidateExample example = createExample(configId, stage, partyId, null);
        List<PcsPrCandidate> pcsPrCandidates = pcsPrCandidateMapper.selectByExample(example);
        for (PcsPrCandidate ca : pcsPrCandidates) {

            resultMap.put(ca.getUserId(), ca);
        }

        return resultMap;
    }

    /**
     *全校先按分党委排序
     * 然后：
     * 先按“专业技术人员和干部”、“学生代表”、“离退休代表”；
     其中“学生代表”和“离退休代表”只按票数排序；
     其中“专业技术人员和干部”排序规则为：

     校领导在最前，顺序与校领导干部库顺序一致；
     其他所有的按票数来排。
     */
    // 用于查询入选名单
    public PcsPrCandidateExample createExample(int configId, byte stage, Integer partyId, Integer userId){

        PcsPrCandidateExample example = new PcsPrCandidateExample();
        PcsPrCandidateExample.Criteria criteria = example.createCriteria().andConfigIdEqualTo(configId).
                andStageEqualTo(stage);
        if (partyId != null) {
            criteria.andPartyIdEqualTo(partyId);
        }
        example.setOrderByClause("party_sort_order desc, type asc, leader_sort_order desc, vote desc, sort_order asc");
        if (userId != null) {
            criteria.andUserIdEqualTo(userId);
        }

        return example;
    }

    // 获得分党委某个类型下的被推荐人
    public List<PcsPrCandidate> find(int configId, byte stage, byte type, int partyId){

        PcsPrCandidateExample example = new PcsPrCandidateExample();
        example.createCriteria().andConfigIdEqualTo(configId).andStageEqualTo(stage)
                .andTypeEqualTo(type).andPartyIdEqualTo(partyId);

        example.setOrderByClause("sort_order asc");

        return pcsPrCandidateMapper.selectByExample(example);
    }

    // 同一阶段，同一用户只能选一次（即不同分党委不能同时选一个人，因为要保证本分党委推荐本单位的人）
    public PcsPrCandidate find(int userId, int configId, byte stage){

        PcsPrCandidateExample example = new PcsPrCandidateExample();
        example.createCriteria().andUserIdEqualTo(userId)
                .andConfigIdEqualTo(configId).andStageEqualTo(stage);

        List<PcsPrCandidate> pcsPrCandidates = pcsPrCandidateMapper.selectByExample(example);
        return pcsPrCandidates.size()>0?pcsPrCandidates.get(0):null;
    }

    // 清空一个党支部推荐的党委或纪委委员
    @Transactional
    public void clear(int recommendId){

        PcsPrCandidateExample example = new PcsPrCandidateExample();
        example.createCriteria().andRecommendIdEqualTo(recommendId);
        pcsPrCandidateMapper.deleteByExample(example);
    }

    @Transactional
    public void insertSelective(PcsPrCandidate record){

        int recommendId = record.getRecommendId();
        int userId = record.getUserId();
        PcsRecommend pr = pcsRecommendMapper.selectByPrimaryKey(recommendId);

        SysUserView uv = CmTag.getUserById(userId);
        record.setCode(uv.getCode());
        record.setRealname(uv.getRealname());
        record.setUnitName(uv.getUnit());
        record.setGender(uv.getGender());
        record.setNation(uv.getNation());
        record.setBirth(uv.getBirth());

        TeacherInfo ti = teacherInfoMapper.selectByPrimaryKey(userId);
        if(ti!=null) {
            record.setWorkTime(ti.getWorkTime());
            record.setProPost(ti.getProPost());
            record.setEducation(ti.getEducation());
            record.setIsRetire(ti.getIsRetire());
        }
        StudentInfo si = studentInfoMapper.selectByPrimaryKey(userId);
        if(si!=null){
            record.setEduLevel(si.getEduLevel());
        }

        record.setUserType((byte)3);
        MemberView mv = iMemberMapper.getMemberView(userId);
        if(mv!=null) {
            record.setGrowTime(mv.getGrowTime());
            record.setUserType((byte)2);
            record.setPartySortOrder(mv.getPartySortOrder());
        }

        record.setLeaderSortOrder(-1);
        CadreView cv = CmTag.getCadreByUserId(userId);
        if(cv!=null) {
            record.setEduId(cv.getEduId());
            record.setPost(cv.getPost());
            if(cv.getStatus()== CadreConstants.CADRE_STATUS_LEADER) {
                record.setLeaderSortOrder(cv.getSortOrder());
            }
            record.setUserType((byte)1);
        }

        record.setPartyId(pr.getPartyId());
        record.setConfigId(pr.getConfigId());
        record.setStage(pr.getStage());

        record.setSortOrder(getNextSortOrder(TABLE_NAME, "recommend_id=" + record.getRecommendId()
                + " and type=" + record.getType()));
        pcsPrCandidateMapper.insertSelective(record);
    }

    // 升序排列
   /* @Transactional
    public void changeOrder(int id, int addNum) {

        if (addNum == 0) return;
        byte orderBy = ORDER_BY_ASC;
        PcsPrCandidate entity = pcsPrCandidateMapper.selectByPrimaryKey(id);
        Integer baseSortOrder = entity.getSortOrder();
        int recommendId = entity.getRecommendId();
        byte type = entity.getType();

        PcsPrCandidateExample example = new PcsPrCandidateExample();
        if (addNum*orderBy > 0) {

            example.createCriteria().andRecommendIdEqualTo(recommendId).andTypeEqualTo(type).andSortOrderGreaterThan(baseSortOrder);
            example.setOrderByClause("sort_order asc");
        } else {

            example.createCriteria().andRecommendIdEqualTo(recommendId).andTypeEqualTo(type).andSortOrderLessThan(baseSortOrder);
            example.setOrderByClause("sort_order desc");
        }

        List<PcsPrCandidate> overEntities = pcsPrCandidateMapper.selectByExampleWithRowbounds(example, new RowBounds(0, Math.abs(addNum)));
        if (overEntities.size() > 0) {

            PcsPrCandidate targetEntity = overEntities.get(overEntities.size() - 1);

           if (addNum*orderBy > 0)
                commonMapper.downOrder(TABLE_NAME,  "recommend_id=" + recommendId
                        + " and type=" + type, baseSortOrder, targetEntity.getSortOrder());
            else
                commonMapper.upOrder(TABLE_NAME,  "recommend_id=" + recommendId
                        + " and type=" + type, baseSortOrder, targetEntity.getSortOrder());

            PcsPrCandidate record = new PcsPrCandidate();
            record.setId(id);
            record.setSortOrder(targetEntity.getSortOrder());
            pcsPrCandidateMapper.updateByPrimaryKeySelective(record);
        }
    }*/
}
