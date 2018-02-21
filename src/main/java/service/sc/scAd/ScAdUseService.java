package service.sc.scAd;

import bean.CadreAdform;
import domain.sc.scAd.ScAdUse;
import domain.sc.scAd.ScAdUseExample;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;
import service.cadre.CadreAdformService;
import sys.utils.XmlSerializeUtils;

import java.util.Arrays;

@Service
public class ScAdUseService extends BaseMapper {

    @Autowired
    private CadreAdformService cadreAdformService;

    @Transactional
    public void insertSelective(ScAdUse record) {

        record.setIsAdformSaved(false);
        scAdUseMapper.insertSelective(record);
    }

    @Transactional
    public void del(Integer id) {

        scAdUseMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids) {

        if (ids == null || ids.length == 0) return;

        ScAdUseExample example = new ScAdUseExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        scAdUseMapper.deleteByExample(example);
    }

    @Transactional
    public void updateByPrimaryKeySelective(ScAdUse record) {

        scAdUseMapper.updateByPrimaryKeySelective(record);
        if(BooleanUtils.isTrue(record.getIsOnCampus())){
            commonMapper.excuteSql("update sc_ad_use set out_unit=null where id=" + record.getId());
        }else{
            commonMapper.excuteSql("update sc_ad_use set unit_id=null where id=" + record.getId());
        }
    }

    @Transactional
    public void save(Integer useId) {

        ScAdUse scAdUse = scAdUseMapper.selectByPrimaryKey(useId);
        CadreAdform cadreAdForm = cadreAdformService.getCadreAdform(scAdUse.getCadreId());

        ScAdUse record = new ScAdUse();
        record.setId(scAdUse.getId());
        record.setAdform(XmlSerializeUtils.serialize(cadreAdForm));
        record.setIsAdformSaved(true);

        scAdUseMapper.updateByPrimaryKeySelective(record);
    }
}
