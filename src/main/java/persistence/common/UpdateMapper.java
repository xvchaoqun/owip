package persistence.common;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * Created by fafa on 2016/1/5.
 */
public interface UpdateMapper {

    @Update("update base_dispatch set file=null, file_name=null where id=#{id}")
    void del_dispatch_file(@Param("id") int id);
    @Update("update base_dispatch set ppt=null, ppt_name=null where id=#{id}")
    void del_dispatch_ppt(@Param("id") int id);


}
