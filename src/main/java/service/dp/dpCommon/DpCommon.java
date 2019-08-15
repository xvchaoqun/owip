package service.dp.dpCommon;

import domain.sys.TeacherInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import sys.service.ApplicationContextSupport;
import sys.tags.CmTag;

public class DpCommon {

    private static Logger logger = LoggerFactory.getLogger(CmTag.class);
    public static ApplicationContext context = ApplicationContextSupport.getContext();
    static DpCommonService dpCommonService = context.getBean(DpCommonService.class);

    public static TeacherInfo getTeacherById(Integer id){
        if (id == null) return null;

        return dpCommonService.findById(id);
    }
}
