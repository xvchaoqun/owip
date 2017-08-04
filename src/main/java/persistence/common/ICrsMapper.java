package persistence.common;

import domain.crs.CrsApplicant;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import persistence.common.bean.ICrsExpert;

import java.util.Date;
import java.util.List;

/**
 * Created by lm on 2017/6/13.
 */
public interface ICrsMapper {

    public int recommend(@Param("record") CrsApplicant record);

    public List<ICrsExpert> findExperts(@Param("userId") Integer userId,
                                        @Param("status") Byte status,
                                        @Param("meetingTimeStart") Date meetingTimeStart,
                                        @Param("meetingTimeEnd") Date meetingTimeEnd,
                                        @Param("orderType") Byte orderType,
                                        RowBounds rowBounds);
    public int countExperts(@Param("userId") Integer userId,
                                        @Param("status") Byte status,
                                        @Param("meetingTimeStart") Date meetingTimeStart,
                                        @Param("meetingTimeEnd") Date meetingTimeEnd);
}
