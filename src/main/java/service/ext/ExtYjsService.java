package service.ext;

import domain.ext.ExtYjs;
import domain.ext.ExtYjsExample;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import service.BaseMapper;

import java.util.List;

/**
 * Created by fafa on 2015/11/27.
 */
@Service
public class ExtYjsService extends BaseMapper {

    public ExtYjs getByCode(String code){

        if(StringUtils.isBlank(code)) return null;

        ExtYjsExample example = new ExtYjsExample();
        example.createCriteria().andXhEqualTo(code);
        List<ExtYjs> extYjss = extYjsMapper.selectByExample(example);
        if(extYjss.size()>0) return extYjss.get(0);
        return null;
    }

}
