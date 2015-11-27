package service;

import domain.ExtYjs;
import domain.ExtYjsExample;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by fafa on 2015/11/27.
 */
@Service
public class ExtYjsService extends BaseMapper{

    public ExtYjs getByCode(String code){

        if(StringUtils.isBlank(code)) return null;

        ExtYjsExample exmaple = new ExtYjsExample();
        exmaple.createCriteria().andXhEqualTo(code);
        List<ExtYjs> extYjss = extYjsMapper.selectByExample(exmaple);
        if(extYjss.size()>0) return extYjss.get(0);
        return null;
    }

}
