package persistence.pmd.common;

import domain.ext.ExtJzgSalary;
import domain.ext.ExtRetireSalary;
import domain.pmd.PmdMember;
import domain.pmd.PmdMemberPayView;
import domain.pmd.PmdOrder;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.session.RowBounds;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Created by lm on 2017/6/13.
 */
public interface IPmdMapper {

    @ResultMap("persistence.pmd.PmdMemberMapper.BaseResultMap")
    @Select("select pm.* from pmd_member pm, pmd_member_pay pmp " +
            "where pm.user_id=#{userId} and pm.id=pmp.member_id " +
            "and pm.is_delay=1 and pmp.has_pay=0 and pm.month_id<>#{currentPmdMonthId}")
    List<PmdMember> getDelayList(@Param("userId") int userId, @Param("currentPmdMonthId") int currentPmdMonthId);

    // 批量缴费的记录列表
    @Select("select u.user_id as userId, u.code, u.realname, poi.due_pay as duePay from pmd_order_item poi, pmd_member pm, sys_user_view u " +
            "where poi.sn=#{sn} and poi.member_id=pm.id and pm.user_id=u.id order by poi.id asc")
    public List<Map> listOrderItems(@Param("sn") String sn);

    // 批量缴费的记录列表（只返回memberId）
    @Select("select member_id from pmd_order_item where sn = #{sn} order by id asc")
    public List<Integer> listOrderMemberIds(@Param("sn") String sn);

    // 查询缴费记录关联的支付记录
    @ResultMap("persistence.pmd.PmdOrderMapper.BaseResultMap")
    @Select("select * from pmd_order where member_id=#{memberId} union " +
            "select po.* from pmd_order po, pmd_order_item poi where poi.member_id=#{memberId} and po.sn=poi.sn " +
            "order by create_time desc")
    public List<PmdOrder> findRelateOrders(@Param("memberId") Integer memberId);

    // 查询用户缴费记录
    public List<IPmdOrder> selectPayList(@Param("userId") Integer userId, RowBounds rowBounds);
    public int countPayList(@Param("userId") Integer userId);

    // 查询缴费记录对应的批量缴费记录（未关闭的）
    @ResultMap("persistence.pmd.PmdOrderMapper.BaseResultMap")
    @Select("select po.* from pmd_order_item poi, pmd_order po " +
            "where poi.member_id=#{memberId} and po.sn=poi.sn and po.is_batch=1 and po.is_closed=0")
    public List<PmdOrder> notClosedBatchOrder(@Param("memberId") int memberId);

    // 删除某个支部下的所有未缴费记录
    public void delNotPayMembers(@Param("currentMonthId") int currentMonthId,
                          @Param("partyId") int partyId,
                          @Param("branchId") int branchId);

    // 党费重新计算-可选月份
    @Select("select distinct rq from ext_jzg_salary where rq in(select distinct rq from ext_retire_salary) order by rq desc")
    public List<String> extSalaryMonthList();
    
    // 教职工工资月份
    @Select("select distinct rq from ext_jzg_salary order by rq desc")
    public List<String> extJzgSalaryMonthList();
    // 离退休工资月份
    @Select("select distinct rq from ext_retire_salary order by rq desc")
    public List<String> extRetireSalaryMonthList();

    // 读取当前缴费党员库中的教职工工资
    @ResultMap("persistence.ext.ExtJzgSalaryMapper.BaseResultMap")
    @Select("select ejs.* from pmd_config_member pcm, ext_jzg_salary ejs, sys_user u " +
            "where pcm.user_id= u.id and ejs.zgh=u.code and ejs.rq=#{rq};")
    public List<ExtJzgSalary> extJzgSalaryList(@Param("rq") String salaryMonth);

    // 读取全部的教职工工资
    @ResultMap("persistence.ext.ExtJzgSalaryMapper.BaseResultMap")
    @Select("select * from ext_jzg_salary where rq=#{rq};")
    public List<ExtJzgSalary> extJzgSalaryAllList(@Param("rq") String salaryMonth);

    @ResultMap("persistence.ext.ExtJzgSalaryMapper.BaseResultMap")
    @Select("select * from ext_jzg_salary where zgh=#{zgh} and rq=#{rq}")
    public ExtJzgSalary getExtJzgSalary(@Param("rq") String salaryMonth, @Param("zgh") String code);

    // 读取当前缴费党员库中的离退休工资
    @ResultMap("persistence.ext.ExtRetireSalaryMapper.BaseResultMap")
    @Select("select ers.* from pmd_config_member pcm, ext_retire_salary ers, sys_user u " +
            "where pcm.user_id= u.id and ers.zgh=u.code and ers.rq=#{rq};")
    public List<ExtRetireSalary> extRetireSalaryList(@Param("rq") String salaryMonth);

    // 读取全部的离退休工资
    @ResultMap("persistence.ext.ExtRetireSalaryMapper.BaseResultMap")
    @Select("select * from ext_retire_salary where rq=#{rq};")
    public List<ExtRetireSalary> extRetireSalaryAllList(@Param("rq") String salaryMonth);

    @ResultMap("persistence.ext.ExtRetireSalaryMapper.BaseResultMap")
    @Select("select * from ext_retire_salary where zgh=#{zgh} and rq=#{rq}")
    public ExtRetireSalary getExtRetireSalary(@Param("rq") String salaryMonth, @Param("zgh") String code);

    // 读取最新的离退休人员党费计算基数
    @Select("select base from ext_retire_salary where zgh = #{code} order by rq desc limit 1")
    BigDecimal getLatestRetireBase(@Param("code") String code);

    // 往月延迟缴费党员数（已启动缴费，同步了党员信息之后汇总）
    public int historyDelayMemberCount(@Param("currentMonthId") int currentMonthId,
                                       @Param("partyId") Integer partyId,
                                       @Param("branchId") Integer branchId);

    // 应补缴往月党费数
    public BigDecimal historyDelayPay(@Param("currentMonthId") int currentMonthId,
                                      @Param("partyId") Integer partyId,
                                      @Param("branchId") Integer branchId);

    // 补缴党员列列表
    public List<PmdMemberPayView> selectHistoryDelayMemberList(@Param("currentMonthId") int currentMonthId,
                                                               @Param("partyId") Integer partyId,
                                                               @Param("branchId") Integer branchId,
                                                               @Param("userId") Integer userId,
                                                               @Param("hasPay") Boolean hasPay,
                                                               RowBounds rowBounds);
    public int countHistoryDelayMemberList(@Param("currentMonthId") int currentMonthId,
                                           @Param("partyId") Integer partyId,
                                           @Param("branchId") Integer branchId,
                                           @Param("userId") Integer userId,
                                           @Param("hasPay") Boolean hasPay);

    // 已补缴党员列列表
    public List<PmdMemberPayView> selectHasPayHistoryDelayMemberList(@Param("monthId") int monthId,
                                                                     @Param("partyId") Integer partyId,
                                                                     @Param("branchId") Integer branchId,
                                                                     @Param("userId") Integer userId,
                                                                     RowBounds rowBounds);
    public int countHasPayHistoryDelayMemberList(@Param("monthId") int monthId,
                                                 @Param("partyId") Integer partyId,
                                                 @Param("branchId") Integer branchId,
                                                 @Param("userId") Integer userId);

    // 本月应交党费数
   /* public BigDecimal duePay(@Param("monthId") int monthId,
                             @Param("partyId") Integer partyId,
                             @Param("branchId") Integer branchId);*/

    // 读取已设定的缴费分党委ID列表
    @Select("select party_id from pmd_party where month_id=#{monthId}")
    public List<Integer> partyIdList(@Param("monthId") int monthId);

    // 读取已设定的缴费党支部ID列表
    @Select("select branch_id from pmd_branch where month_id=#{monthId} and party_id=#{partyId}")
    public List<Integer> branchIdList(@Param("monthId") int monthId,
                                      @Param("partyId") int partyId);

    // 组织部汇总
    public PmdReportBean getOwPmdReportBean(@Param("monthId") int monthId);

    // 分党委汇总
    public PmdReportBean getPartyPmdReportBean(@Param("monthId") int monthId,
                                               @Param("partyId") int partyId);

    // 党支部汇总
    public PmdReportBean getBranchPmdReportBean(@Param("monthId") int monthId,
                                                @Param("partyId") int partyId,
                                                @Param("branchId") Integer branchId);

    // 分党委报表
    public PmdExcelReportBean getPmdExcelReportBean(@Param("monthId") int monthId,
                                                    @Param("partyId") int partyId,
                                                    // 0 汇总 1 本月按时缴纳党费党员数 2 本月未缴纳党费党员数 3 补缴往月党费党员数
                                                    @Param("type") int type);
}
