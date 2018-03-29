package persistence.cis.common;

import domain.cis.CisInspectObjView;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by lm on 2018/1/23.
 */
public interface ICisMapper {


    // 干部任免审批表-待选干部考察报告
    @ResultMap("persistence.cis.CisInspectObjViewMapper.BaseResultMap")
    @Select("select cio.* from cis_inspect_obj_view cio where cio.cadre_id=#{cadreId} " +
            "and cio.id not in(select obj_id from sc_ad_archive where cadre_id=#{cadreId} and id != #{archiveId})")
    public List<CisInspectObjView>  selectScAdCisInspectObjs(@Param("archiveId") int archiveId, @Param("cadreId") int cadreId);
}
