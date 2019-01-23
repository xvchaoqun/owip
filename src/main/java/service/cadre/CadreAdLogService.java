package service.cadre;

import domain.cadre.Cadre;
import domain.cadre.CadreAdLog;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import service.BaseMapper;
import shiro.ShiroHelper;
import sys.utils.ContextHelper;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;

/**
 * Created by fafa on 2016/12/24.
 */
@Service
public class CadreAdLogService extends BaseMapper {

    private Logger logger = LoggerFactory.getLogger(getClass());

    public void addLog(int cadreId, String content, Byte module, Integer moduleId){

        Cadre cadre = cadreMapper.selectByPrimaryKey(cadreId);
        CadreAdLog record = new CadreAdLog();
        try {
            ConvertUtils.register(new DateConverter(null), Date.class);
            BeanUtils.copyProperties(record, cadre);
        } catch (IllegalAccessException e) {
            logger.error("异常", e);
        } catch (InvocationTargetException e) {
            logger.error("异常", e);
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
