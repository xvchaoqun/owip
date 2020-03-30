package persistence.pmd;

import domain.pmd.PmdPartyDonate;
import domain.pmd.PmdPartyDonateExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface PmdPartyDonateMapper {
    long countByExample(PmdPartyDonateExample example);

    int deleteByExample(PmdPartyDonateExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(PmdPartyDonate record);

    int insertSelective(PmdPartyDonate record);

    List<PmdPartyDonate> selectByExampleWithBLOBsWithRowbounds(PmdPartyDonateExample example, RowBounds rowBounds);

    List<PmdPartyDonate> selectByExampleWithBLOBs(PmdPartyDonateExample example);

    List<PmdPartyDonate> selectByExampleWithRowbounds(PmdPartyDonateExample example, RowBounds rowBounds);

    List<PmdPartyDonate> selectByExample(PmdPartyDonateExample example);

    PmdPartyDonate selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") PmdPartyDonate record, @Param("example") PmdPartyDonateExample example);

    int updateByExampleWithBLOBs(@Param("record") PmdPartyDonate record, @Param("example") PmdPartyDonateExample example);

    int updateByExample(@Param("record") PmdPartyDonate record, @Param("example") PmdPartyDonateExample example);

    int updateByPrimaryKeySelective(PmdPartyDonate record);

    int updateByPrimaryKeyWithBLOBs(PmdPartyDonate record);

    int updateByPrimaryKey(PmdPartyDonate record);
}