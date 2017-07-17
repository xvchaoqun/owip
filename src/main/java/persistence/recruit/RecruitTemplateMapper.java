package persistence.recruit;

import domain.recruit.RecruitTemplate;
import domain.recruit.RecruitTemplateExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface RecruitTemplateMapper {
    int countByExample(RecruitTemplateExample example);

    int deleteByExample(RecruitTemplateExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(RecruitTemplate record);

    int insertSelective(RecruitTemplate record);

    List<RecruitTemplate> selectByExampleWithRowbounds(RecruitTemplateExample example, RowBounds rowBounds);

    List<RecruitTemplate> selectByExample(RecruitTemplateExample example);

    RecruitTemplate selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") RecruitTemplate record, @Param("example") RecruitTemplateExample example);

    int updateByExample(@Param("record") RecruitTemplate record, @Param("example") RecruitTemplateExample example);

    int updateByPrimaryKeySelective(RecruitTemplate record);

    int updateByPrimaryKey(RecruitTemplate record);
}