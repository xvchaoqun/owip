package service.cadre;

import domain.Cadre;
import domain.CadreInfo;
import domain.CadreInfoExample;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;
import sys.constants.SystemConstants;

import java.util.Arrays;

@Service
public class CadreInfoService extends BaseMapper {

    @Autowired
    private CadreService cadreService;

    // 获取干部联系手机号码
    public String getCadreMobile(int userId){

        Cadre cadre = cadreService.findByUserId(userId);
        if(cadre==null) return null;

        return getCadreMobileByCadreId(cadre.getId());
    }
    public String getCadreMobileByCadreId(int cadreId){

        CadreInfo cadreInfo = cadreInfoMapper.selectByPrimaryKey(cadreId);
        return (cadreInfo==null)?null: StringUtils.trimToNull(cadreInfo.getMobile());
    }

    @Transactional
    public void del(Integer cadreId){

        cadreInfoMapper.deleteByPrimaryKey(cadreId);
    }

    @Transactional
    public void batchDel(Integer[] cadreIds){

        if(cadreIds==null || cadreIds.length==0) return;

        CadreInfoExample example = new CadreInfoExample();
        example.createCriteria().andCadreIdIn(Arrays.asList(cadreIds));
        cadreInfoMapper.deleteByExample(example);
    }

    @Transactional
    public void insertOrUpdate(CadreInfo record){

        Integer cadreId = record.getCadreId();
        CadreInfo cadreInfo = cadreInfoMapper.selectByPrimaryKey(cadreId);
        if (cadreInfo == null) {
            cadreInfoMapper.insertSelective(record);
        } else {
            cadreInfoMapper.updateByPrimaryKeySelective(record);
        }
    }
}
