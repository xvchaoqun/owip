package service.pmd;

import controller.global.OpException;
import domain.pmd.PmdNorm;
import domain.pmd.PmdNormExample;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shiro.ShiroHelper;
import sys.constants.PmdConstants;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class PmdNormService extends PmdBaseMapper {

    public static final String TABLE_NAME = "pmd_norm";

    public List<PmdNorm> list(byte type, Byte setType){

        PmdNormExample example = new PmdNormExample();
        PmdNormExample.Criteria criteria = example.createCriteria().andTypeEqualTo(type)
                .andStatusEqualTo(PmdConstants.PMD_NORM_STATUS_USE);
        if(setType!=null){
            criteria.andSetTypeEqualTo(setType);
        }
        example.setOrderByClause("sort_order asc");

        return pmdNormMapper.selectByExample(example);
    }

    @Transactional
    public void insertSelective(PmdNorm record) {

        record.setSortOrder(getNextSortOrder(TABLE_NAME, "type=" + record.getType()));
        pmdNormMapper.insertSelective(record);
    }

    @Transactional
    public void batchDel(Integer[] ids) {

        if (ids == null || ids.length == 0) return;

        PmdNormExample example = new PmdNormExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        PmdNorm record = new PmdNorm();
        record.setStatus(PmdConstants.PMD_NORM_STATUS_DELETE);

        pmdNormMapper.updateByExampleSelective(record, example);
    }

    @Transactional
    public void updateByPrimaryKeySelective(PmdNorm record) {

        pmdNormMapper.updateByPrimaryKeySelective(record);

        if(record.getSetType()!=null && record.getSetType() != PmdConstants.PMD_NORM_SET_TYPE_FORMULA){
            commonMapper.excuteSql("update pmd_norm set formula_type = null where id=" + record.getId());
        }
    }

    //启用收费标准
    @Transactional
    public void use(int id) {

        PmdNorm pmdNorm = pmdNormMapper.selectByPrimaryKey(id);
        PmdNormExample example = new PmdNormExample();
        example.createCriteria().andNameEqualTo(pmdNorm.getName())
                .andStatusEqualTo(PmdConstants.PMD_NORM_STATUS_USE);
        if(pmdNormMapper.countByExample(example)>0){
            throw new OpException("已经启用相同名称的标准。");
        }

        PmdNorm record = new PmdNorm();
        record.setId(id);
        record.setStatus(PmdConstants.PMD_NORM_STATUS_USE);
        record.setStartTime(new Date());
        record.setStartUserId(ShiroHelper.getCurrentUserId());

        pmdNormMapper.updateByPrimaryKeySelective(record);
    }

    //作废收费标准
    @Transactional
    public void abolish(int id) {

        PmdNorm record = new PmdNorm();
        record.setId(id);
        record.setStatus(PmdConstants.PMD_NORM_STATUS_ABOLISH);
        record.setEndTime(new Date());
        record.setEndUserId(ShiroHelper.getCurrentUserId());

        PmdNormExample example = new PmdNormExample();
        example.createCriteria().andIdEqualTo(id)
                .andStatusEqualTo(PmdConstants.PMD_NORM_STATUS_USE);

        pmdNormMapper.updateByExampleSelective(record, example);
    }

    // 升序排列
    @Transactional
    public void changeOrder(int chosenId, int addNum) {

        PmdNorm entity = pmdNormMapper.selectByPrimaryKey(chosenId);
        changeOrder(TABLE_NAME, "type=" + entity.getType(), ORDER_BY_ASC, chosenId, addNum);
    }
}
