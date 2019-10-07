package persistence.cr.common;

import domain.cr.CrInfo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface ICrMapper {

    // 已报名的招聘
    @ResultMap("persistence.cr.CrInfoMapper.BaseResultMap")
    @Select("select ci.* from cr_info ci, cr_applicant ca where ca.user_id=#{userId} and ca.info_id=ci.id and ca.has_submit=1")
    public List<CrInfo> hasApplyInfos(@Param("userId") Integer userId);
}
