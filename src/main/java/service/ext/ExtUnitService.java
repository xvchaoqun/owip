package service.ext;

import bean.SchoolUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lm on 2018/6/22.
 */
@Service
public class ExtUnitService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    public List<SchoolUnit> getSchoolUnits(){

        List<SchoolUnit> records = new ArrayList<>();

        return records;
    }
}
