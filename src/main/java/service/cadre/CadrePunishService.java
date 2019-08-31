package service.cadre;

import domain.cadre.CadrePunish;
import domain.cadre.CadrePunishExample;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;
import sys.constants.SystemConstants;

import java.util.Arrays;
import java.util.List;

@Service
public class CadrePunishService extends BaseMapper {

    // 受处分情况（用于任免审批表）
    public List<CadrePunish> list(int cadreId) {

        CadrePunishExample example = new CadrePunishExample();
        example.createCriteria().andCadreIdEqualTo(cadreId).andListInAdEqualTo(true)
                .andStatusEqualTo(SystemConstants.RECORD_STATUS_FORMAL);
        example.setOrderByClause("punish_time desc");

        return cadrePunishMapper.selectByExample(example);
    }

    @Transactional
    public void insertSelective(CadrePunish record) {

        record.setStatus(SystemConstants.RECORD_STATUS_FORMAL);
        cadrePunishMapper.insertSelective(record);
    }

    @Transactional
    public void batchDel(Integer[] ids) {

        if (ids == null || ids.length == 0) return;

        CadrePunishExample example = new CadrePunishExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        cadrePunishMapper.deleteByExample(example);
    }

    @Transactional
    public void updateByPrimaryKeySelective(CadrePunish record) {
        record.setStatus(null);
        cadrePunishMapper.updateByPrimaryKeySelective(record);
    }
}
