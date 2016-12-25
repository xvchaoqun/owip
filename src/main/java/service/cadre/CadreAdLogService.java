package service.cadre;

import domain.cadre.Cadre;
import domain.cadre.CadreAdLog;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import org.springframework.stereotype.Service;
import service.BaseMapper;
import sys.utils.ContextHelper;
import shiro.ShiroHelper;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;

/**
 * Created by fafa on 2016/12/24.
 */
@Service
public class CadreAdLogService extends BaseMapper {

    public void addLog(int cadreId, String content, Byte module, Integer moduleId){

        Cadre cadre = cadreMapper.selectByPrimaryKey(cadreId);
        CadreAdLog record = new CadreAdLog();
        try {
            ConvertUtils.register(new DateConverter(null), java.util.Date.class);
            BeanUtils.copyProperties(record, cadre);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        record.setId(null);
        record.setModule(module);
        record.setModuleId(moduleId);
        record.setContent(content);
        record.setUserId(ShiroHelper.getCurrentUserId());
        record.setCadreId(cadre.getId());
        record.setIp(ContextHelper.getRealIp());
        record.setCreateTime(new Date());

        cadreAdLogMapper.insertSelective(record);
    }
}
