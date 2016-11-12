package service.cadre;

import domain.cadre.Cadre;
import domain.cadre.CadreConcat;
import domain.cadre.CadreConcatExample;
import domain.sys.SysUserView;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;
import service.sys.SysUserService;

import java.util.Arrays;

@Service
public class CadreConcatService extends BaseMapper {

    @Autowired
    private CadreService cadreService;

    // 获取干部接收短信的称谓。如果没有设定短信称谓，则使用姓名
    public String getMsgTitle(int userId){

        Cadre cadre = cadreService.findByUserId(userId);
        if(cadre==null) return null;
        SysUserView user = cadre.getUser();
        CadreConcat cadreConcat = cadreConcatMapper.selectByPrimaryKey(cadre.getId());
        return StringUtils.isBlank(cadreConcat.getMsgTitle())?user.getRealname():
                cadreConcat.getMsgTitle();
    }

    // 获取干部联系手机号码
    public String getCadreMobile(int userId){

        Cadre cadre = cadreService.findByUserId(userId);
        if(cadre==null) return null;

        return getCadreMobileByCadreId(cadre.getId());
    }

    public String getCadreMobileByCadreId(int cadreId){

        CadreConcat cadreConcat = cadreConcatMapper.selectByPrimaryKey(cadreId);
        return (cadreConcat==null)?null: StringUtils.trimToNull(cadreConcat.getMobile());
    }

    @Transactional
    public void del(Integer cadreId){

        cadreConcatMapper.deleteByPrimaryKey(cadreId);
    }

    @Transactional
    public void batchDel(Integer[] cadreIds){

        if(cadreIds==null || cadreIds.length==0) return;

        CadreConcatExample example = new CadreConcatExample();
        example.createCriteria().andCadreIdIn(Arrays.asList(cadreIds));
        cadreConcatMapper.deleteByExample(example);
    }

    @Transactional
    public void insertOrUpdate(CadreConcat record){

        Integer cadreId = record.getCadreId();
        CadreConcat cadreConcat = cadreConcatMapper.selectByPrimaryKey(cadreId);
        if (cadreConcat == null) {
            cadreConcatMapper.insertSelective(record);
        } else {
            cadreConcatMapper.updateByPrimaryKeySelective(record);
        }
    }
}
