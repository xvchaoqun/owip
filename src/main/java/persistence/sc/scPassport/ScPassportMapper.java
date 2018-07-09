package persistence.sc.scPassport;

import domain.sc.scPassport.ScPassport;
import domain.sc.scPassport.ScPassportExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface ScPassportMapper {
    long countByExample(ScPassportExample example);

    int deleteByExample(ScPassportExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ScPassport record);

    int insertSelective(ScPassport record);

    List<ScPassport> selectByExampleWithRowbounds(ScPassportExample example, RowBounds rowBounds);

    List<ScPassport> selectByExample(ScPassportExample example);

    ScPassport selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ScPassport record, @Param("example") ScPassportExample example);

    int updateByExample(@Param("record") ScPassport record, @Param("example") ScPassportExample example);

    int updateByPrimaryKeySelective(ScPassport record);

    int updateByPrimaryKey(ScPassport record);
}