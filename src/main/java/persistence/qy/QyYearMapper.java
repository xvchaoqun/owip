package persistence.qy;

import domain.qy.QyYear;
import domain.qy.QyYearExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface QyYearMapper {
    long countByExample(QyYearExample example);

    int deleteByExample(QyYearExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(QyYear record);

    int insertSelective(QyYear record);

    List<QyYear> selectByExampleWithRowbounds(QyYearExample example, RowBounds rowBounds);

    List<QyYear> selectByExample(QyYearExample example);

    QyYear selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") QyYear record, @Param("example") QyYearExample example);

    int updateByExample(@Param("record") QyYear record, @Param("example") QyYearExample example);

    int updateByPrimaryKeySelective(QyYear record);

    int updateByPrimaryKey(QyYear record);
}