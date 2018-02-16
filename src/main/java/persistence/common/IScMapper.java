package persistence.common;

import domain.sc.scCommittee.ScCommittee;
import domain.sc.scCommittee.ScCommitteeVoteView;
import domain.sc.scMatter.ScMatterAccess;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

/**
 * Created by lm on 2018/1/23.
 */
public interface IScMapper {

    @ResultMap("persistence.sc.scMatter.ScMatterAccessMapper.BaseResultMap")
    @Select("select sma.* from sc_matter_access sma, sc_matter_access_item smai " +
            "where smai.matter_item_id=#{matterItemId} and sma.is_deleted=0 and sma.id=smai.access_id order by sma.access_date desc, sma.id desc")
    public List<ScMatterAccess> selectScMatterAccessList(@Param("matterItemId") int matterItemId, RowBounds rowBounds);
    @Select("select count(distinct sma.id) from sc_matter_access sma, sc_matter_access_item smai " +
            "where smai.matter_item_id=#{matterItemId} and sma.is_deleted=0 and sma.id=smai.access_id")
    int countScMatterAccess(@Param("matterItemId") int matterItemId);

    @ResultMap("persistence.sc.scCommittee.ScCommitteeVoteViewMapper.BaseResultMap")
    @Select("select scv.* from sc_public_user spu, sc_committee_vote_view scv " +
            "where spu.public_id=#{publicId} and spu.vote_id=scv.id order by spu.sort_order")
    public List<ScCommitteeVoteView> getScPublicVotes(@Param("publicId") int publicId);

    // 文件起草签发-待选任免对象
    @ResultMap("persistence.sc.scCommittee.ScCommitteeVoteViewMapper.BaseResultMap")
    @Select("select scv.* from sc_committee_vote_view scv where scv.committee_id in(${committeeIds}) " +
            "and scv.id not in(select vote_id from sc_dispatch_user) order by scv.type asc, scv.sort_order")
    public List<ScCommitteeVoteView> getScDispatchUsers(@Param("committeeIds") String committeeIds);

    // 文件起草签发-已选任免对象
    @ResultMap("persistence.sc.scCommittee.ScCommitteeVoteViewMapper.BaseResultMap")
    @Select("select scv.* from sc_dispatch_user sdu, sc_committee_vote_view scv " +
            "where sdu.dispatch_id=#{dispatchId} and sdu.vote_id=scv.id order by sdu.type asc, sdu.sort_order")
    public List<ScCommitteeVoteView> getScDispatchVotes(@Param("dispatchId") int dispatchId);

    // 文件起草签发-已选常委会
    @ResultMap("persistence.sc.scCommittee.ScCommitteeMapper.BaseResultMap")
    @Select("select sc.* from sc_dispatch_committee sdc, sc_committee sc " +
            "where sdc.dispatch_id=#{dispatchId} and sdc.committee_id=sc.id order by sdc.id asc")
    public List<ScCommittee> getScDispatchCommittees(@Param("dispatchId") int dispatchId);
}
