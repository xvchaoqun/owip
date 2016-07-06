package service.sys;

import domain.member.Student;
import org.springframework.stereotype.Service;
import service.BaseMapper;

/**
 * Created by fafa on 2015/12/11.
 */
@Service
public class StudentService extends BaseMapper {

    public Student get(int userId){

        return studentMapper.selectByPrimaryKey(userId);
    }
}
