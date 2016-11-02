package service.sys;

import domain.sys.StudentInfo;
import org.springframework.stereotype.Service;
import service.BaseMapper;

/**
 * Created by fafa on 2015/12/11.
 */
@Service
public class StudentInfoService extends BaseMapper {

    public StudentInfo get(int userId){

        return studentInfoMapper.selectByPrimaryKey(userId);
    }
}
