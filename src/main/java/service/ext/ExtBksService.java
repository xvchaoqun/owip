package service.ext;

import domain.ext.ExtBks;
import domain.ext.ExtBksExample;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import service.BaseMapper;

import java.util.List;

/**
 * Created by fafa on 2015/11/27.
 */
@Service
public class ExtBksService extends BaseMapper {

    public ExtBks getByCode(String code){

        if(StringUtils.isBlank(code)) return null;

        ExtBksExample exmaple = new ExtBksExample();
        exmaple.createCriteria().andXhEqualTo(code);
        List<ExtBks> extBkss = extBksMapper.selectByExample(exmaple);
        if(extBkss.size()>0) return extBkss.get(0);
        return null;
    }

}
