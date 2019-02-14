package persistence.cis.common;

import domain.cis.CisInspectObjView;
import domain.cis.CisInspector;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

/**
 * Created by lm on 2018/1/23.
 */
public interface ICisMapper {

    // 根据账号、姓名、学工号查找考察组成员
    List<CisInspector> selectInspectorList(@Param("status") Byte status,
                                           @Param("search") String search, RowBounds rowBounds);
    int countInspectorList(@Param("status") Byte status, @Param("search") String search);

    // 干部任免审批表-待选干部考察报告
    @ResultMap("persistence.cis.CisInspectObjViewMapper.BaseResultMap")
    @Select("select cio.* from cis_inspect_obj_view cio where cio.cadre_id=#{cadreId} " +
            "and not exists(select 1 from sc_ad_archive where obj_id=cio.id and cadre_id=#{cadreId} and id != #{archiveId})")
    public List<CisInspectObjView>  selectScAdCisInspectObjs(@Param("archiveId") int archiveId, @Param("cadreId") int cadreId);
}
