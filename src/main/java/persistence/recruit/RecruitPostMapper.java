package persistence.recruit;

import domain.recruit.RecruitPost;
import domain.recruit.RecruitPostExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface RecruitPostMapper {
    int countByExample(RecruitPostExample example);

    int deleteByExample(RecruitPostExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(RecruitPost record);

    int insertSelective(RecruitPost record);

    List<RecruitPost> selectByExampleWithRowbounds(RecruitPostExample example, RowBounds rowBounds);

    List<RecruitPost> selectByExample(RecruitPostExample example);

    RecruitPost selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") RecruitPost record, @Param("example") RecruitPostExample example);

    int updateByExample(@Param("record") RecruitPost record, @Param("example") RecruitPostExample example);

    int updateByPrimaryKeySelective(RecruitPost record);

    int updateByPrimaryKey(RecruitPost record);
}