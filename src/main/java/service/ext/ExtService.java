package service.ext;

import domain.ext.ExtBks;
import domain.ext.ExtBksExample;
import domain.ext.ExtJzg;
import domain.ext.ExtJzgExample;
import domain.ext.ExtYjs;
import domain.ext.ExtYjsExample;
import org.springframework.stereotype.Service;
import service.BaseMapper;

import java.util.List;

/**
 * Created by fafa on 2015/11/21.
 */
@Service
public class ExtService extends BaseMapper {

    public ExtBks getExtBks(String code){

        ExtBksExample example = new ExtBksExample();
        example.createCriteria().andXhEqualTo(code);
        List<ExtBks> extBkses = extBksMapper.selectByExample(example);
        if(extBkses.size()>0) return extBkses.get(0);

        return null;
    }

    public ExtYjs getExtYjs(String code){

        ExtYjsExample example = new ExtYjsExample();
        example.createCriteria().andXhEqualTo(code);
        List<ExtYjs> extYjses = extYjsMapper.selectByExample(example);
        if(extYjses.size()>0) return extYjses.get(0);

        return null;
    }

    public ExtJzg getExtJzg(String code){

        ExtJzgExample example = new ExtJzgExample();
        example.createCriteria().andZghEqualTo(code);
        List<ExtJzg> extJzges = extJzgMapper.selectByExample(example);
        if(extJzges.size()>0) return extJzges.get(0);

        return null;
    }
}
