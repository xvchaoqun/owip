package service.cadre;

import domain.CadrePost;
import domain.CadrePostExample;
import org.springframework.stereotype.Service;
import service.BaseMapper;

import java.util.List;

/**
 * Created by fafa on 2015/11/27.
 */
@Service
public class CadrePostService extends BaseMapper {

    public CadrePost getPresentByCadreId(int cadreId){

        CadrePostExample example = new CadrePostExample();
        example.createCriteria().andCadreIdEqualTo(cadreId).andIsPresentEqualTo(true);

        List<CadrePost> cadrePosts = cadrePostMapper.selectByExample(example);
        if(cadrePosts.size()>0) return cadrePosts.get(0);

        return null;
    }
}
