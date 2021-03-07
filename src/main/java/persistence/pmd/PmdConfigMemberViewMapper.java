package persistence.pmd;

import domain.pmd.PmdConfigMemberView;
import domain.pmd.PmdConfigMemberViewExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface PmdConfigMemberViewMapper {
    long countByExample(PmdConfigMemberViewExample example);

    int deleteByExample(PmdConfigMemberViewExample example);

    int insert(PmdConfigMemberView record);

    int insertSelective(PmdConfigMemberView record);

    List<PmdConfigMemberView> selectByExampleWithRowbounds(PmdConfigMemberViewExample example, RowBounds rowBounds);

    List<PmdConfigMemberView> selectByExample(PmdConfigMemberViewExample example);

    int updateByExampleSelective(@Param("record") PmdConfigMemberView record, @Param("example") PmdConfigMemberViewExample example);

    int updateByExample(@Param("record") PmdConfigMemberView record, @Param("example") PmdConfigMemberViewExample example);
}