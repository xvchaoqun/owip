package persistence.ext;

import domain.ext.ExtJzg;
import domain.ext.ExtJzgExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface ExtJzgMapper {
    int countByExample(ExtJzgExample example);

    int deleteByExample(ExtJzgExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ExtJzg record);

    int insertSelective(ExtJzg record);

    List<ExtJzg> selectByExampleWithRowbounds(ExtJzgExample example, RowBounds rowBounds);

    List<ExtJzg> selectByExample(ExtJzgExample example);

    ExtJzg selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ExtJzg record, @Param("example") ExtJzgExample example);

    int updateByExample(@Param("record") ExtJzg record, @Param("example") ExtJzgExample example);

    int updateByPrimaryKeySelective(ExtJzg record);

    int updateByPrimaryKey(ExtJzg record);
}