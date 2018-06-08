package sys.helper;

import domain.cis.CisInspectorView;
import service.cis.CisInspectObjService;
import service.cis.CisInspectorService;
import sys.tags.CmTag;

import java.util.List;

/**
 * Created by lm on 2018/6/8.
 */
public class CisHelper {

    public static List<CisInspectorView> getCisInspectors(Integer objId) {

        CisInspectObjService cisInspectObjService = CmTag.getBean(CisInspectObjService.class);
        return cisInspectObjService.getInspectors(objId);
    }

    public static CisInspectorView getCisInspector(Integer id) {

        CisInspectorService cisInspectorService = CmTag.getBean(CisInspectorService.class);
        return cisInspectorService.getInspector(id);
    }
}
