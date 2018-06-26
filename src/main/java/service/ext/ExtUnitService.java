package service.ext;

import bean.SchoolUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by lm on 2018/6/22.
 */
@Service
public class ExtUnitService extends Source {

    private Logger logger = LoggerFactory.getLogger(getClass());

    public List<SchoolUnit> getSchoolUnits(){

        List<SchoolUnit> records = new ArrayList<>();

        return records;
    }

    @Override
    public void update(Map<String, Object> map, ResultSet rs) throws SQLException {}
}
