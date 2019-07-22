package service.cadre;

import controller.global.OpException;
import domain.cadre.CadrePost;
import domain.cadre.CadrePostExample;
import domain.unit.UnitPost;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;
import service.dispatch.DispatchCadreRelateService;
import service.global.CacheHelper;
import sys.constants.DispatchConstants;

import java.util.Arrays;
import java.util.List;

/**
 * Created by fafa on 2015/11/27.
 */
@Service
public class CadrePostService extends BaseMapper {

    @Autowired(required = false)
    private DispatchCadreRelateService dispatchCadreRelateService;
    @Autowired(required = false)
    private CacheHelper cacheHelper;

    public CadrePost getByUnitPostId(int unitPostId) {

        CadrePostExample example = new CadrePostExample();
        example.createCriteria().andUnitPostIdEqualTo(unitPostId);

        List<CadrePost> cadrePosts = cadrePostMapper.selectByExampleWithRowbounds(example, new RowBounds(0, 1));

        return cadrePosts.size() > 0 ? cadrePosts.get(0) : null;
    }

    // 添加或更新第一主职
    public void addOrUpdateMainCadrePost(CadrePost record){

        record.setIsMainPost(true);
        record.setIsFirstMainPost(true);

        int cadreId = record.getCadreId();
        CadrePost mainCadrePost = getCadreMainCadrePost(cadreId);
        if(mainCadrePost!=null){
            record.setId(mainCadrePost.getId());
            cadrePostMapper.updateByPrimaryKeySelective(record);

            cacheHelper.clearCadreCache();
        }else{
            insertSelective(record);
        }
    }

    public void insertSelective(CadrePost record) {

        // 如果是第一主职提交，则判断是否重复
        if (BooleanUtils.isTrue(record.getIsFirstMainPost())) {
            CadrePost cadreMainCadrePost = getCadreMainCadrePost(record.getCadreId());
            if (cadreMainCadrePost != null) {
                if (record.getId() == null || cadreMainCadrePost.getId() != record.getId()) {
                    throw new OpException("已存在第一主职");
                }
            }
        }

        // 同步岗位信息
        if(record.getUnitPostId()!=null){

            UnitPost unitPost = unitPostMapper.selectByPrimaryKey(record.getUnitPostId());
            record.setPostName(unitPost.getName());
            record.setIsPrincipal(unitPost.getIsPrincipal());
            record.setPostType(unitPost.getPostType());
            record.setPostClassId(unitPost.getPostClass());
            record.setUnitId(unitPost.getUnitId());
        }

        record.setSortOrder(getNextSortOrder("cadre_post", "cadre_id=" + record.getCadreId()
                + " and is_main_post=" + record.getIsMainPost()));
        cadrePostMapper.insertSelective(record);

        cacheHelper.clearCadreCache();
    }

    @Transactional
    public void batchDel(Integer[] ids) {

        if (ids == null || ids.length == 0) return;

        CadrePostExample example = new CadrePostExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        cadrePostMapper.deleteByExample(example);

        // 同时删除关联的任免文件
        dispatchCadreRelateService.delDispatchCadreRelates(Arrays.asList(ids), DispatchConstants.DISPATCH_CADRE_RELATE_TYPE_POST);

        cacheHelper.clearCadreCache();
    }

    public void updateByPrimaryKeySelective(CadrePost record) {

        // 同步岗位信息
        if(record.getUnitPostId()!=null){

            UnitPost unitPost = unitPostMapper.selectByPrimaryKey(record.getUnitPostId());
            record.setPostName(unitPost.getName());
            record.setIsPrincipal(unitPost.getIsPrincipal());
            record.setPostType(unitPost.getPostType());
            record.setPostClassId(unitPost.getPostClass());
            record.setUnitId(unitPost.getUnitId());
        }else{
            // 清除关联岗位
            commonMapper.excuteSql("update cadre_post set unit_post_id=null where id=" + record.getId());
        }

        record.setIsMainPost(null); // 不改变是否是主职字段
        cadrePostMapper.updateByPrimaryKeySelective(record);

        cacheHelper.clearCadreCache();
    }

    public CadrePost getCadreMainCadrePostById(Integer id) {

        if (id == null) return null;

        return cadrePostMapper.selectByPrimaryKey(id);
    }

    // 获取第一主职
    public CadrePost getCadreMainCadrePost(int cadreId) {

        CadrePostExample example = new CadrePostExample();
        example.createCriteria().andCadreIdEqualTo(cadreId)
                .andIsMainPostEqualTo(true)
                .andIsFirstMainPostEqualTo(true);

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

    // 批量导入主职信息
    @Transactional
    public int batchImportMainPosts(List<CadrePost> records) {

        int addCount = 0;
        for (CadrePost record : records) {

            int cadreId = record.getCadreId();
            CadrePost cadreMainCadrePost = getCadreMainCadrePost(cadreId);
            if (cadreMainCadrePost != null) {

                record.setId(cadreMainCadrePost.getId());
                record.setSortOrder(cadreMainCadrePost.getSortOrder());
                // 覆盖更新
                cadrePostMapper.updateByPrimaryKey(record);
            } else {

                record.setSortOrder(getNextSortOrder("cadre_post", "cadre_id=" + record.getCadreId()
                + " and is_main_post=" + record.getIsMainPost()));
                insertSelective(record);
                addCount++;
            }
        }

        cacheHelper.clearCadreCache();

        return addCount;
    }

    // 批量导入兼职信息
    @Transactional
    public int batchImportSubPosts(List<CadrePost> records) {

        int addCount = 0;
        for (CadrePost record : records) {

            int cadreId = record.getCadreId();
            CadrePost subCadrePost = null;
            List<CadrePost> subCadrePosts = getSubCadrePosts(cadreId);
            for (CadrePost _cadrePost : subCadrePosts) {
                if(_cadrePost.getUnitId().intValue() == record.getUnitId()){
                    subCadrePost = _cadrePost; // 该兼职已存在
                    break;
                }
            }

            if (subCadrePost != null) {

                record.setId(subCadrePost.getId());
                record.setSortOrder(subCadrePost.getSortOrder());
                // 覆盖更新
                cadrePostMapper.updateByPrimaryKey(record);
            } else {

                record.setSortOrder(getNextSortOrder("cadre_post", "cadre_id=" + record.getCadreId()
                + " and is_main_post=" + record.getIsMainPost()));
                insertSelective(record);
                addCount++;
            }
        }

        cacheHelper.clearCadreCache();

        return addCount;
    }

    @Transactional
    public void changeOrder(int id, int addNum) {

        if (addNum == 0) return;

        CadrePost entity = cadrePostMapper.selectByPrimaryKey(id);
        Integer baseSortOrder = entity.getSortOrder();
        Boolean isMainPost = entity.getIsMainPost();
        int cadreId = entity.getCadreId();

        CadrePostExample example = new CadrePostExample();
        if (addNum > 0) {

            example.createCriteria().andCadreIdEqualTo(cadreId).andSortOrderGreaterThan(baseSortOrder)
                    .andIsMainPostEqualTo(isMainPost);
            example.setOrderByClause("sort_order asc");
        } else {

            example.createCriteria().andCadreIdEqualTo(cadreId).andSortOrderLessThan(baseSortOrder)
                    .andIsMainPostEqualTo(isMainPost);
            example.setOrderByClause("sort_order desc");
        }

        List<CadrePost> overEntities = cadrePostMapper.selectByExampleWithRowbounds(example, new RowBounds(0, Math.abs(addNum)));
        if (overEntities.size() > 0) {

            CadrePost targetEntity = overEntities.get(overEntities.size() - 1);
            if (addNum > 0)
                commonMapper.downOrder("cadre_post", "cadre_id=" + cadreId + " and is_main_post=" + isMainPost,
                        baseSortOrder, targetEntity.getSortOrder());
            else
                commonMapper.upOrder("cadre_post", "cadre_id=" + cadreId + " and is_main_post=" + isMainPost,
                        baseSortOrder, targetEntity.getSortOrder());

            CadrePost record = new CadrePost();
            record.setId(id);
            record.setSortOrder(targetEntity.getSortOrder());
            cadrePostMapper.updateByPrimaryKeySelective(record);
        }
    }
}
