package persistence.common;

import domain.pmd.PmdMemberPayView;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.session.RowBounds;
import persistence.common.bean.PmdExcelReportBean;
import persistence.common.bean.PmdReportBean;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by lm on 2017/6/13.
 */
public interface IPmdMapper {

    // 往月延迟缴费党员数（已启动缴费，同步了党员信息之后汇总）
    public int historyDelayMemberCount(@Param("currentMonthId") int currentMonthId,
                                       @Param("partyId") Integer partyId,
                                       @Param("branchId") Integer branchId);

    // 应补缴往月党费数
    public BigDecimal historyDelayPay(@Param("currentMonthId") int currentMonthId,
                                      @Param("partyId") Integer partyId,
                                      @Param("branchId") Integer branchId);

    // 补缴党员列列表
    public List<PmdMemberPayView> historyDelayMemberList(@Param("currentMonthId") int currentMonthId,
                                                         @Param("partyId") Integer partyId,
                                                         @Param("branchId") Integer branchId,
                                                         @Param("userId") Integer userId,
                                                         @Param("hasPay") Boolean hasPay,
                                                         RowBounds rowBounds);

    public int historyDelayMemberListCount(@Param("currentMonthId") int currentMonthId,
                                           @Param("partyId") Integer partyId,
                                           @Param("branchId") Integer branchId,
                                           @Param("userId") Integer userId,
                                           @Param("hasPay") Boolean hasPay);

    // 已补缴党员列列表
    public List<PmdMemberPayView> historyDelayMemberList2(@Param("monthId") int monthId,
                                                          @Param("partyId") Integer partyId,
                                                          @Param("branchId") Integer branchId,
                                                          @Param("userId") Integer userId,
                                                          RowBounds rowBounds);

    public int historyDelayMemberListCount2(@Param("monthId") int monthId,
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
