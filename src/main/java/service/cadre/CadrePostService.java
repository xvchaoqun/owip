package service.cadre;

import domain.cadre.CadrePost;
import domain.cadre.CadrePostExample;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;
import service.dispatch.DispatchCadreRelateService;
import sys.constants.SystemConstants;

import java.util.Arrays;
import java.util.List;

/**
 * Created by fafa on 2015/11/27.
 */
@Service
public class CadrePostService extends BaseMapper {

    @Autowired
    private DispatchCadreRelateService dispatchCadreRelateService;

    public void insertSelective(CadrePost record) {

        // 如果是主职提交，则判断是否重复
        if (BooleanUtils.isTrue(record.getIsMainPost())) {
            CadrePost cadreMainCadrePost = getCadreMainCadrePost(record.getCadreId());
            if (cadreMainCadrePost != null) {
                if (record.getId() == null || cadreMainCadrePost.getId() != record.getId()) {
                    throw new RuntimeException("主职重复");
                }
            }
        }

        record.setSortOrder(getNextSortOrder("cadre_post", "cadre_id=" + record.getCadreId()
                +" and is_main_post="+record.getIsMainPost()));
        cadrePostMapper.insertSelective(record);
    }

    @Transactional
    public void batchDel(Integer[] ids) {

        if (ids == null || ids.length == 0) return;

        CadrePostExample example = new CadrePostExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        cadrePostMapper.deleteByExample(example);

        // 同时删除关联的任免文件
        dispatchCadreRelateService.delDispatchCadreRelates(Arrays.asList(ids), SystemConstants.DISPATCH_CADRE_RELATE_TYPE_POST);
    }

    public void updateByPrimaryKeySelective(CadrePost record) {

        if (BooleanUtils.isNotTrue(record.getIsDouble())) { // 不是双肩挑
            updateMapper.del_cadrePost_doubleUnitId(record.getId());
        }

        record.setIsMainPost(null); // 不改变是否是主职字段
        cadrePostMapper.updateByPrimaryKeySelective(record);
    }

    public CadrePost getCadreMainCadrePostById(Integer id) {

        if (id == null) return null;

        return cadrePostMapper.selectByPrimaryKey(id);
    }

    public CadrePost getCadreMainCadrePost(int cadreId) {

        CadrePostExample example = new CadrePostExample();
        example.createCriteria().andCadreIdEqualTo(cadreId).andIsMainPostEqualTo(true);

        List<CadrePost> cadrePosts = cadrePostMapper.selectByExample(example);
        if (cadrePosts.size() > 0) return cadrePosts.get(0);
        return null;
    }

    public List<CadrePost> getSubCadrePosts(int cadreId) {

        CadrePostExample example = new CadrePostExample();
        example.createCriteria().andCadreIdEqualTo(cadreId).andIsMainPostEqualTo(false);

        example.setOrderByClause("sort_order desc");

        List<CadrePost> subCadrePosts = cadrePostMapper.selectByExample(example);
        return subCadrePosts;
    }

    @Transactional
    public void changeOrder(int id, int cadreId, int addNum) {

        if(addNum == 0) return ;

        CadrePost entity = cadrePostMapper.selectByPrimaryKey(id);
        Integer baseSortOrder = entity.getSortOrder();
        Boolean isMainPost = entity.getIsMainPost();

        CadrePostExample example = new CadrePostExample();
        if (addNum > 0) {

            example.createCriteria().andCadreIdEqualTo(cadreId).andSortOrderGreaterThan(baseSortOrder)
                    .andIsMainPostEqualTo(isMainPost) ;
            example.setOrderByClause("sort_order asc");
        }else {

            example.createCriteria().andCadreIdEqualTo(cadreId).andSortOrderLessThan(baseSortOrder)
                    .andIsMainPostEqualTo(isMainPost) ;
            example.setOrderByClause("sort_order desc");
        }

        List<CadrePost> overEntities = cadrePostMapper.selectByExampleWithRowbounds(example, new RowBounds(0, Math.abs(addNum)));
        if(overEntities.size()>0) {

            CadrePost targetEntity = overEntities.get(overEntities.size()-1);
            if (addNum > 0)
                commonMapper.downOrder("cadre_post", "cadre_id=" + cadreId +" and is_main_post="+isMainPost,
                        baseSortOrder, targetEntity.getSortOrder());
            else
                commonMapper.upOrder("cadre_post", "cadre_id=" + cadreId+" and is_main_post="+isMainPost,
                        baseSortOrder, targetEntity.getSortOrder());

            CadrePost record = new CadrePost();
            record.setId(id);
            record.setSortOrder(targetEntity.getSortOrder());
            cadrePostMapper.updateByPrimaryKeySelective(record);
        }
    }
}
