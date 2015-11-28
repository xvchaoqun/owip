package service.cadre;

import domain.CadreSubWork;
import domain.CadreSubWorkExample;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import service.BaseMapper;

import java.util.List;

/**
 * Created by fafa on 2015/11/27.
 */
@Service
public class CadreSubWorkService extends BaseMapper {

    public List<CadreSubWork> findByCadreId(int cadreId){

        CadreSubWorkExample example = new CadreSubWorkExample();
        example.createCriteria().andCadreIdEqualTo(cadreId);
        example.setOrderByClause("start_time desc");

        List<CadreSubWork> cadreSubWorks = cadreSubWorkMapper.selectByExampleWithRowbounds(example, new RowBounds(0,2));

        return cadreSubWorks;
    }
}
