package service;

import domain.CadreMainWork;
import domain.CadreMainWorkExample;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by fafa on 2015/11/27.
 */
@Service
public class CadreMainWorkService extends BaseMapper{

    public CadreMainWork getByCadreId(int cadreId){

        CadreMainWorkExample example = new CadreMainWorkExample();
        example.createCriteria().andCadreIdEqualTo(cadreId);

        List<CadreMainWork> cadreMainWorks = cadreMainWorkMapper.selectByExample(example);
        if(cadreMainWorks.size()>0) return cadreMainWorks.get(0);

        return null;
    }
}
