package persistence.pm;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.session.RowBounds;
import persistence.pm.common.PmMeeting2Stat;
import persistence.pm.common.PmMeetingStat;

import java.util.List;
import java.util.Map;

/**
 * Created by lm on 2017/6/13.
 */
public interface IPmMapper {

    @Select("select count(1) branchCount,count(case when pqb.is_exclude in (true) " +
            "then 'is_exclude' end) isExcludeCount, count(case  when pqb.is_exclude is  " +
            "not true  then 'clude' end) cludeCount from pm_quarter_branch pqb where pqb.quarter_id = #{id}")
    public PmQuarterCountBean getPmQuarterCount(@Param("id")Integer id);

    @Select("select count(1) from pm_meeting where  branch_id=#{branchId}")
    public int getMeetingCount(@Param("branchId")Integer branchId);

    Map selectPmInitCount(@Param("type") Byte type,
                          @Param("addPermits") Boolean addPermits,
                          @Param("adminPartyIdList") List<Integer> adminPartyIdList,
                          @Param("adminBranchIdList") List<Integer> adminBranchIdList);

    List<PmMeetingStat> selectPmMeetingStat(@Param("display") Byte display,
                                            @Param("year") Integer year,
                                            @Param("quarter") Byte quarter,
                                            @Param("month") Integer month,
                                            @Param("partyId") Integer partyId,
                                            @Param("branchId") Integer branchId,
                                            @Param("status") Byte status,
                                            @Param("addPermits") Boolean addPermits,
                                            @Param("adminPartyIdList") List<Integer> adminPartyIdList,
                                            @Param("adminBranchIdList") List<Integer> adminBranchIdList,
                                            RowBounds rowBounds);

    int countPmMeetingStat(@Param("display") Byte display,
                            @Param("year") Integer year,
                            @Param("quarter") Byte quarter,
                            @Param("month") Integer month,
                            @Param("partyId") Integer partyId,
                            @Param("branchId") Integer branchId,
                            @Param("status") Byte status,
                            @Param("addPermits") Boolean addPermits,
                            @Param("adminPartyIdList") List<Integer> adminPartyIdList,
                            @Param("adminBranchIdList") List<Integer> adminBranchIdList);

    List<PmMeeting2Stat> selectPmMeeting2Stat(@Param("display") Byte display,
                                             @Param("year") Integer year,
                                             @Param("quarter") Byte quarter,
                                             @Param("month") Integer month,
                                             @Param("partyId") Integer partyId,
                                             @Param("branchId") Integer branchId,
                                             @Param("status") Byte status,
                                             @Param("addPermits") Boolean addPermits,
                                             @Param("adminPartyIdList") List<Integer> adminPartyIdList,
                                             @Param("adminBranchIdList") List<Integer> adminBranchIdList,
                                             RowBounds rowBounds);
    int countPmMeeting2Stat(@Param("display") Byte display,
                           @Param("year") Integer year,
                           @Param("quarter") Byte quarter,
                           @Param("month") Integer month,
                           @Param("partyId") Integer partyId,
                           @Param("branchId") Integer branchId,
                           @Param("status") Byte status,
                           @Param("addPermits") Boolean addPermits,
                           @Param("adminPartyIdList") List<Integer> adminPartyIdList,
                           @Param("adminBranchIdList") List<Integer> adminBranchIdList);
//    Map selectPmBackCount(@Param("adminPartyIdList") List<Integer> adminPartyIdList,
//                          @Param("adminBranchIdList") List<Integer> adminBranchIdList);
//
//    Map selectPmPassCount(@Param("adminPartyIdList") List<Integer> adminPartyIdList,
//                          @Param("adminBranchIdList") List<Integer> adminBranchIdList);
//
//    Map selectPmDenyCount(@Param("adminPartyIdList") List<Integer> adminPartyIdList,
//                          @Param("adminBranchIdList") List<Integer> adminBranchIdList);
    //@Select("select count(1) from pm_meeting where  branch_id=#{branchId} and type=#{type}")
   // public int getMeetingCount(@Param("branchId")Integer branchId,@Param("type")Byte type);
}
