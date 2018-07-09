package persistence.sc.scPassport;

import domain.sc.scPassport.ScPassportHand;
import domain.sc.scPassport.ScPassportHandExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface ScPassportHandMapper {
    long countByExample(ScPassportHandExample example);

    int deleteByExample(ScPassportHandExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ScPassportHand record);

    int insertSelective(ScPassportHand record);

    List<ScPassportHand> selectByExampleWithRowbounds(ScPassportHandExample example, RowBounds rowBounds);

    List<ScPassportHand> selectByExample(ScPassportHandExample example);

    ScPassportHand selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ScPassportHand record, @Param("example") ScPassportHandExample example);

    int updateByExample(@Param("record") ScPassportHand record, @Param("example") ScPassportHandExample example);

    int updateByPrimaryKeySelective(ScPassportHand record);

    int updateByPrimaryKey(ScPassportHand record);
}