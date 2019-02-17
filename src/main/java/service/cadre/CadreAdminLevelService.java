package service.cadre;

import domain.cadre.CadreAdminLevel;
import domain.cadre.CadreAdminLevelExample;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by fafa on 2015/11/27.
 */
@Service
public class CadreAdminLevelService extends BaseMapper {

    // 查找别的记录已关联的发文
    public Set<Integer> findOtherDispatchCadreRelateSet(int caderId, int id) {

        CadreAdminLevelExample example = new CadreAdminLevelExample();
        example.createCriteria().andCadreIdEqualTo(caderId).andIdNotEqualTo(id);
        List<CadreAdminLevel> cadreAdminLevels = cadreAdminLevelMapper.selectByExample(example);
        Set<Integer> selectSet = new HashSet<>();
        for (CadreAdminLevel cadreAdminLevel : cadreAdminLevels) {
            if (cadreAdminLevel.getStartDispatchCadreId() != null)
                selectSet.add(cadreAdminLevel.getStartDispatchCadreId());
            if (cadreAdminLevel.getEndDispatchCadreId() != null)
                selectSet.add(cadreAdminLevel.getEndDispatchCadreId());
        }
        return selectSet;
    }

    public List<CadreAdminLevel> getCadreAdminLevels(int cadreId) {

        CadreAdminLevelExample example = new CadreAdminLevelExample();
        example.createCriteria().andCadreIdEqualTo(cadreId);

        return cadreAdminLevelMapper.selectByExample(example);
    }

    public CadreAdminLevel getPresentByCadreId(int cadreId, Integer adminLevel){

        if(adminLevel==null) return null;
        CadreAdminLevelExample example = new CadreAdminLevelExample();
        example.createCriteria().andCadreIdEqualTo(cadreId).andAdminLevelEqualTo(adminLevel);

        List<CadreAdminLevel> cadreAdminLevels = cadreAdminLevelMapper.selectByExampleWithRowbounds(example, new RowBounds(0, 1));
        return (cadreAdminLevels.size()==1)?cadreAdminLevels.get(0):null;
    }

    @Transactional
    public void batchDel(Integer[] ids) {

        if (ids == null || ids.length == 0) return;

        CadreAdminLevelExample example = new CadreAdminLevelExample();
        example.createCriteria().andIdIn(Arrays.asList(ids)); // 删除任职级经历
        cadreAdminLevelMapper.deleteByExample(example);
    }
}
