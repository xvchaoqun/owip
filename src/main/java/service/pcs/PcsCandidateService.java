package service.pcs;

import domain.cadre.Cadre;
import domain.member.MemberView;
import domain.pcs.PcsCandidate;
import domain.pcs.PcsCandidateExample;
import domain.pcs.PcsRecommend;
import domain.sys.SysUserView;
import domain.sys.TeacherInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sys.tags.CmTag;

import java.util.Arrays;
import java.util.List;

@Service
public class PcsCandidateService extends PcsBaseMapper {

    public static final String TABLE_NAME = "pcs_candidate";

    // 读取党支部下的党委委员、纪委委员
    public List<PcsCandidate> find(int partyId, Integer branchId, int configId, byte stage, byte type){

        PcsCandidateExample example = new PcsCandidateExample();
        PcsCandidateExample.Criteria criteria =
                example.createCriteria().andPartyIdEqualTo(partyId)
                        .andConfigIdEqualTo(configId).andStageEqualTo(stage)
                        .andTypeEqualTo(type);
        if(branchId!=null) criteria.andBranchIdEqualTo(branchId);
        example.setOrderByClause("sort_order asc");
        return pcsCandidateMapper.selectByExample(example);
    }

    public boolean idDuplicate(Integer id, int recommendId, int userId, byte type){

        PcsCandidateExample example = new PcsCandidateExample();
        PcsCandidateExample.Criteria criteria = example.createCriteria()
                .andRecommendIdEqualTo(recommendId).andUserIdEqualTo(userId).andTypeEqualTo(type);
        if(id!=null) criteria.andIdNotEqualTo(id);

        return pcsCandidateMapper.countByExample(example) > 0;
    }

    // 清空一个党支部推荐的党委或纪委委员
    @Transactional
    public void clear(int recommendId, byte type){

        PcsCandidateExample example = new PcsCandidateExample();
        example.createCriteria().andRecommendIdEqualTo(recommendId).andTypeEqualTo(type);
        pcsCandidateMapper.deleteByExample(example);
    }

    @Transactional
    public void insertSelective(PcsCandidate record){

        // 填充个人基本信息
        int recommendId = record.getRecommendId();
        int userId = record.getUserId();
        PcsRecommend pr = pcsRecommendMapper.selectByPrimaryKey(recommendId);

        Cadre cadre = CmTag.getCadre(userId);
        if(cadre!=null) {
            record.setTitle(cadre.getTitle());
        }

        SysUserView uv = CmTag.getUserById(userId);
        record.setCode(uv.getCode());
        record.setRealname(uv.getRealname());
        record.setExtUnit(uv.getUnit());
        record.setGender(uv.getGender());
        record.setNation(uv.getNation());
        record.setNativePlace(uv.getNativePlace());
        record.setBirth(uv.getBirth());

        TeacherInfo ti = teacherInfoMapper.selectByPrimaryKey(userId);
        if(ti!=null) {
            record.setWorkTime(ti.getWorkTime());
            record.setProPost(ti.getProPost());
        }
        MemberView mv = iMemberMapper.getMemberView(userId);
        if(mv!=null) {
            record.setGrowTime(mv.getGrowTime());
        }
        record.setPartyId(pr.getPartyId());
        record.setBranchId(pr.getBranchId());
        record.setConfigId(pr.getConfigId());
        record.setStage(pr.getStage());

        record.setSortOrder(getNextSortOrder(TABLE_NAME, "recommend_id=" + record.getRecommendId()
                + " and type=" + record.getType()));
        pcsCandidateMapper.insertSelective(record);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        PcsCandidateExample example = new PcsCandidateExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        pcsCandidateMapper.deleteByExample(example);
    }

    // 升序排列
    @Transactional
    public void changeOrder(int id, int addNum) {

        PcsCandidate entity = pcsCandidateMapper.selectByPrimaryKey(id);
        changeOrder(TABLE_NAME, "recommend_id=" + entity.getRecommendId()
                + " and type=" + entity.getType(), ORDER_BY_ASC, id, addNum);
    }
}
