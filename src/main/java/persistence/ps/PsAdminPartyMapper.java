package persistence.ps;

import domain.ps.PsAdminParty;
import domain.ps.PsAdminPartyExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface PsAdminPartyMapper {
    long countByExample(PsAdminPartyExample example);

    int deleteByExample(PsAdminPartyExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(PsAdminParty record);

    int insertSelective(PsAdminParty record);

    List<PsAdminParty> selectByExampleWithRowbounds(PsAdminPartyExample example, RowBounds rowBounds);

    List<PsAdminParty> selectByExample(PsAdminPartyExample example);

    PsAdminParty selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") PsAdminParty record, @Param("example") PsAdminPartyExample example);

    int updateByExample(@Param("record") PsAdminParty record, @Param("example") PsAdminPartyExample example);

    int updateByPrimaryKeySelective(PsAdminParty record);

    int updateByPrimaryKey(PsAdminParty record);
}