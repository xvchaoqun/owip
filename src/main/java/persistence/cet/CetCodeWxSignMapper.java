package persistence.cet;

import domain.cet.CetCodeWxSign;
import domain.cet.CetCodeWxSignExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface CetCodeWxSignMapper {
    long countByExample(CetCodeWxSignExample example);

    int deleteByExample(CetCodeWxSignExample example);

    int deleteByPrimaryKey(String code);

    int insert(CetCodeWxSign record);

    int insertSelective(CetCodeWxSign record);

    List<CetCodeWxSign> selectByExampleWithRowbounds(CetCodeWxSignExample example, RowBounds rowBounds);

    List<CetCodeWxSign> selectByExample(CetCodeWxSignExample example);

    CetCodeWxSign selectByPrimaryKey(String code);

    int updateByExampleSelective(@Param("record") CetCodeWxSign record, @Param("example") CetCodeWxSignExample example);

    int updateByExample(@Param("record") CetCodeWxSign record, @Param("example") CetCodeWxSignExample example);

    int updateByPrimaryKeySelective(CetCodeWxSign record);

    int updateByPrimaryKey(CetCodeWxSign record);
}