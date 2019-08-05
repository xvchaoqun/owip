package persistence.ps.common;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface IPsMapper {
    Map count(@Param("partyIdList") List partyIdList);
}
