package service.ext;

import domain.ExtJzg;
import domain.ExtJzgExample;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import service.BaseMapper;

import java.util.List;

/**
 * Created by fafa on 2015/11/27.
 */
@Service
public class ExtJzgService extends BaseMapper {

    public ExtJzg getByCode(String code){

        if(StringUtils.isBlank(code)) return null;

        ExtJzgExample exmaple = new ExtJzgExample();
        exmaple.createCriteria().andZghEqualTo(code);
        List<ExtJzg> extJzgs = extJzgMapper.selectByExample(exmaple);
        if(extJzgs.size()>0) return extJzgs.get(0);
        return null;
    }

}
