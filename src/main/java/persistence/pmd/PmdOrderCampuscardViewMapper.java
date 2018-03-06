package persistence.pmd;

import domain.pmd.PmdOrderCampuscardView;
import domain.pmd.PmdOrderCampuscardViewExample;
import java.util.List;
import org.apache.ibatis.session.RowBounds;

public interface PmdOrderCampuscardViewMapper {
    long countByExample(PmdOrderCampuscardViewExample example);

    List<PmdOrderCampuscardView> selectByExampleWithRowbounds(PmdOrderCampuscardViewExample example, RowBounds rowBounds);

    List<PmdOrderCampuscardView> selectByExample(PmdOrderCampuscardViewExample example);
}