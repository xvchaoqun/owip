package persistence.sc.scAd;

import domain.sc.scAd.ScAdArchive;
import domain.sc.scAd.ScAdArchiveExample;
import domain.sc.scAd.ScAdArchiveWithBLOBs;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface ScAdArchiveMapper {
    long countByExample(ScAdArchiveExample example);

    int deleteByExample(ScAdArchiveExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ScAdArchiveWithBLOBs record);

    int insertSelective(ScAdArchiveWithBLOBs record);

    List<ScAdArchiveWithBLOBs> selectByExampleWithBLOBsWithRowbounds(ScAdArchiveExample example, RowBounds rowBounds);

    List<ScAdArchiveWithBLOBs> selectByExampleWithBLOBs(ScAdArchiveExample example);

    List<ScAdArchive> selectByExampleWithRowbounds(ScAdArchiveExample example, RowBounds rowBounds);

    List<ScAdArchive> selectByExample(ScAdArchiveExample example);

    ScAdArchiveWithBLOBs selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ScAdArchiveWithBLOBs record, @Param("example") ScAdArchiveExample example);

    int updateByExampleWithBLOBs(@Param("record") ScAdArchiveWithBLOBs record, @Param("example") ScAdArchiveExample example);

    int updateByExample(@Param("record") ScAdArchive record, @Param("example") ScAdArchiveExample example);

    int updateByPrimaryKeySelective(ScAdArchiveWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(ScAdArchiveWithBLOBs record);

    int updateByPrimaryKey(ScAdArchive record);
}