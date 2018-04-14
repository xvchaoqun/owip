package controller.base;

import controller.BaseController;
import domain.base.MetaClass;
import domain.base.MetaType;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import sys.constants.LogConstants;
import sys.utils.FormUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
public class MetaClassTypeController extends BaseController{

    private Logger logger = LoggerFactory.getLogger(getClass());

    private void checkPermission(String cls){

       /* String afterStr = null;
        try{
            String[] temp = cls.split("_");
            for(int i = 0; i<temp.length; i++){
                if(i==0)
                    afterStr += temp[i];
                else
                    afterStr += WordUtils.capitalize(temp[i]);
            }

        }catch(RuntimeException e){
            e.printStackTrace();
        }
        if(afterStr==null){
            throw new UnauthorizedException();
        }*/
        SecurityUtils.getSubject().checkPermission(cls+":*");
    }

    @RequestMapping("/metaClass_type_list")
    public String metaClass_type_list(String[] cls, ModelMap modelMap) {

        return "base/metaClass/metaClass_type_list";
    }

    @RequestMapping("/metaClass_type_list_item")
    public String metaClass_type_list_item(String code, ModelMap modelMap) {

        checkPermission(code);
        Map<String, MetaClass> metaClassMap = metaClassService.codeKeyMap();
        modelMap.put("metaClass", metaClassMap.get(code));
        return "base/metaClass/metaClass_type_list_item";
    }

    @RequestMapping("/metaClass_type_au")
    public String metaClass_type_au(Integer id, ModelMap modelMap) {

        if(id!=null){
            MetaType metaType = metaTypeMapper.selectByPrimaryKey(id);
            modelMap.put("metaType", metaType);
        }

        return "base/metaClass/metaClass_type_au";
    }

    @RequestMapping(value = "/metaClass_type_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_metaClass_type_au(String cls, MetaType record) {

        checkPermission(cls);

        MetaClass metaClass = metaClassService.codeKeyMap().get(cls);
        record.setClassId(metaClass.getId());

        if(record.getId()==null) {
            record.setCode(metaTypeService.genCode());
            metaTypeService.insertSelective(record);
        }else{
            metaTypeService.updateByPrimaryKeySelective(record);
        }

        return success(FormUtils.SUCCESS);
    }

    @RequestMapping(value = "/metaClass_type_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_metaClass_type_del(int id, String cls) {

        checkPermission(cls);

        MetaType metaType = metaTypeMapper.selectByPrimaryKey(id);
        MetaClass metaClass = metaClassService.codeKeyMap().get(cls);
        if(metaType.getClassId().intValue() != metaClass.getId()){
            throw new UnauthorizedException();
        }

        metaTypeService.del(id);

        return success(FormUtils.SUCCESS);
    }

    @RequestMapping(value = "/metaClass_type_changeOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map do_metaClass_type_changeOrder(Integer id, String cls, Integer addNum, HttpServletRequest request) {

        checkPermission(cls);

        MetaClass metaClass = metaClassService.codeKeyMap().get(cls);

        metaTypeService.changeOrder(id, addNum);
        logger.info(addLog(LogConstants.LOG_ADMIN, metaClass.getName() + "调序：%s, %s", id, addNum));
        return success(FormUtils.SUCCESS);
    }
}
