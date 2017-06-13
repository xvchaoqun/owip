package persistence.common;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * Created by lm on 2017/6/13.
 */
public interface IModifyMapper {

    @Select("select cadre_id from modify_cadre_auth where is_unlimited=1 or " +
            "(is_unlimited=0 and ( (curdate() between start_time and end_time) " +
            "or (start_time is null and curdate() <= end_time) " +
            "or (end_time is null and curdate() >= start_time)))")
    List<Integer> selectValidModifyCadreAuth();

    @Update("update modify_cadre_auth set start_time=null, end_time=null, is_unlimited=1 where id=#{id}")
    void del_ModifyCadreAuth_time(@Param("id") int id);
}
