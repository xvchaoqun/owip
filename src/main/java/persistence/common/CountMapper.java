package persistence.common;

import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * Created by fafa on 2017/4/14.
 */
public interface CountMapper {

    // 干部信息修改数量
    @Select("select module, count(*) as num from modify_table_apply where status=0 group by module")
    public List<Map> modifyTableApply();

    // 干部基本信息修改数量
    @Select("select count(*) as num from modify_base_apply where status in(0,1)")
    public int modifyBaseApply();

    // 领取证件申请数量
    @Select("select type, count(*) as num from abroad_passport_draw where status=0 and is_deleted=0 group by type")
    public List<Map> abroadPassportDraw();

    // 办理证件申请数量
    @Select("select count(*) from abroad_passport_apply where status=0 and is_deleted=0")
    public int abroadPassportApply();

    // 因私出国境（管理员）申请数量
    @Select("select count(*) from abroad_apply_self where status=1 and is_finish=0 and flow_node in(-1,0) and is_deleted=0")
    public int abroadApplySelf();

    // 因公赴台备案-提醒管理员选择办理新证件方式记录数量
    @Select("select count(*) from abroad_taiwan_record where handle_type is null and passport_code is null and end_date<=now()  and is_deleted=0")
    public int taiwanRecordHandleType();

    // 干部请假（管理员）申请数量
    @Select("select count(*) from cla_apply where status=1 and is_finish=0 and flow_node in(-1,0) and is_deleted=0")
    public int claApply();

}
