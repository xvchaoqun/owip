package service.sys;

import domain.member.Teacher;
import org.springframework.stereotype.Service;
import service.BaseMapper;

/**
 * Created by fafa on 2015/12/11.
 */
@Service
public class TeacherService extends BaseMapper {

    public Teacher get(int userId){

        return teacherMapper.selectByPrimaryKey(userId);
    }
}
