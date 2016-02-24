package persistence;

import domain.Passport;
import domain.PassportExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface PassportMapper {
    int countByExample(PassportExample example);

    int deleteByExample(PassportExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Passport record);

    int insertSelective(Passport record);

    List<Passport> selectByExampleWithRowbounds(PassportExample example, RowBounds rowBounds);

    List<Passport> selectByExample(PassportExample example);

    Passport selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Passport record, @Param("example") PassportExample example);

    int updateByExample(@Param("record") Passport record, @Param("example") PassportExample example);

    int updateByPrimaryKeySelective(Passport record);

    int updateByPrimaryKey(Passport record);
}