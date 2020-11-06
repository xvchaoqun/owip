package service.cadre;

import controller.global.OpException;
import domain.cadre.CadrePost;
import domain.cadre.CadrePostExample;
import domain.unit.UnitPost;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;
import service.dispatch.DispatchCadreRelateService;
import service.global.CacheHelper;
import sys.constants.DispatchConstants;

import java.util.ArrayList;
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
    @Transactional
    public void addOrUpdateMainCadrePost(CadrePost record){

        record.setIsMainPost(true);
        record.setIsFirstMainPost(true);

        int cadreId = record.getCadreId();
        CadrePost mainCadrePost = getFirstMainCadrePost(cadreId);
        if(mainCadrePost!=null){
            record.setId(mainCadrePost.getId());
            updateByPrimaryKeySelective(record);
        }else{
            insertSelective(record);
        }
    }

    @Transactional
    public void insertSelective(CadrePost record) {

        // 如果是第一主职提交，则判断是否重复
        if (BooleanUtils.isTrue(record.getIsFirstMainPost())) {
            CadrePost cadreMainCadrePost = getFirstMainCadrePost(record.getCadreId());
            if (cadreMainCadrePost != null) {
                if (record.getId() == null || cadreMainCadrePost.getId().intValue() != record.getId()) {
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
            record.setIsCpc(unitPost.getIsCpc());
        }

        record.setSortOrder(getNextSortOrder("cadre_post", "cadre_id=" + record.getCadreId()
                + " and is_main_post=" + record.getIsMainPost()));
        cadrePostMapper.insertSelective(record);

        cacheHelper.clearCadreCache(record.getCadreId());
    }

    @Transactional
    public void batchDel(Integer[] ids) {

        if (ids == null || ids.length == 0) return;

        for (Integer id : ids) {

            CadrePost cadrePost = cadrePostMapper.selectByPrimaryKey(id);
            cacheHelper.clearCadreCache(cadrePost.getCadreId());

            cadrePostMapper.deleteByPrimaryKey(id);
        }

        // 同时删除关联的任免文件
        dispatchCadreRelateService.delDispatchCadreRelates(Arrays.asList(ids), DispatchConstants.DISPATCH_CADRE_RELATE_TYPE_POST);
    }

    @Transactional
    public void updateByPrimaryKeySelective(CadrePost record) {

        int cadreId = record.getCadreId();
        // 如果是第一主职提交，则判断是否重复
        if (BooleanUtils.isTrue(record.getIsFirstMainPost())) {
            CadrePost cadreMainCadrePost = getFirstMainCadrePost(cadreId);
            if (cadreMainCadrePost != null) {
                if (cadreMainCadrePost.getId().intValue() != record.getId()) {
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
            record.setIsCpc(unitPost.getIsCpc());

        }else{
            // 清除关联岗位
            commonMapper.excuteSql("update cadre_post set unit_post_id=null where id=" + record.getId());
        }

       /* record.setIsMainPost(null); */   // 不改变是否是主职字段
        cadrePostMapper.updateByPrimaryKeySelective(record);

        cacheHelper.clearCadreCache(cadreId);
    }

    public CadrePost getCadrePostById(Integer id) {

        if (id == null) return null;

        return cadrePostMapper.selectByPrimaryKey(id);
    }

    // 获取第一主职
    public CadrePost getFirstMainCadrePost(int cadreId) {

        CadrePostExample example = new CadrePostExample();
        example.createCriteria().andCadreIdEqualTo(cadreId)
                .andIsMainPostEqualTo(true)
                .andIsFirstMainPostEqualTo(true);

        List<CadrePost> cadrePosts = cadrePostMapper.selectByExample(example);
        if (cadrePosts.size() > 0) return cadrePosts.get(0);
        return null;
    }

    public List<CadrePost> getCadreMainCadrePosts(int cadreId) {

        CadrePostExample example = new CadrePostExample();
        example.createCriteria().andCadreIdEqualTo(cadreId)
                .andIsMainPostEqualTo(true);
        example.setOrderByClause("is_first_main_post desc, sort_order desc");

        return cadrePostMapper.selectByExample(example);
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
            CadrePost cadreMainCadrePost = getFirstMainCadrePost(cadreId);
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

            cacheHelper.clearCadreCache(cadreId);
        }

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
                record.setIsFirstMainPost(false);
                // 覆盖更新
                cadrePostMapper.updateByPrimaryKey(record);
            } else {

                record.setSortOrder(getNextSortOrder("cadre_post", "cadre_id=" + record.getCadreId()
                + " and is_main_post=" + record.getIsMainPost()));
                insertSelective(record);
                addCount++;
            }

            cacheHelper.clearCadreCache(cadreId);
        }

        return addCount;
    }

    // 批量导入任职时间
    @Transactional
    public int batchImportWorkTimes(List<CadrePost> records) {

        int addCount = 0;
        for (CadrePost record : records) {

            int cadreId = record.getCadreId();
            CadrePost cadreMainCadrePost = getFirstMainCadrePost(cadreId);
            if (cadreMainCadrePost != null) {
                record.setId(cadreMainCadrePost.getId());
                cadrePostMapper.updateByPrimaryKeySelective(record);
            } else {
                record.setIsMainPost(true);
                record.setIsFirstMainPost(true);
                record.setSortOrder(getNextSortOrder("cadre_post", "cadre_id=" + record.getCadreId()
                + " and is_main_post=" + record.getIsMainPost()));
                insertSelective(record);
                addCount++;
            }

            cacheHelper.clearCadreCache(cadreId);
        }

        return addCount;
    }

    @Transactional
    public void changeOrder(int id, int addNum) {

        CadrePost entity = cadrePostMapper.selectByPrimaryKey(id);
        boolean isMainPost = entity.getIsMainPost();
        int cadreId = entity.getCadreId();
        changeOrder("unit", "cadre_id=" + cadreId + " and is_main_post=" + isMainPost, ORDER_BY_DESC, id, addNum);
    }

    public List<CadrePost> getCadrePost(int cadreId, String postName,List<String> postUnitIds){

        List<CadrePost> cadrePostList = new ArrayList<>();
        if (StringUtils.isBlank(postName) && postUnitIds == null){
            CadrePostExample example = new CadrePostExample();
            example.createCriteria().andCadreIdEqualTo(cadreId);
            cadrePostList = cadrePostMapper.selectByExample(example);
        }else {
            for (String postUnitId : postUnitIds) {
                String _postName = postUnitId.split("_")[0];
                Integer _unitId = Integer.valueOf(postUnitId.split("_")[1]);
                CadrePostExample example = new CadrePostExample();
                CadrePostExample.Criteria criteria = example.createCriteria().andCadreIdEqualTo(cadreId).andUnitIdEqualTo(_unitId);
                if (StringUtils.isNotBlank(postName) && StringUtils.equals(postName, _postName)) {
                    criteria.andPostNameEqualTo(postName);
                    return cadrePostMapper.selectByExample(example);
                } else {
                    criteria.andPostNameEqualTo(_postName);
                }
                List<CadrePost> cadrePostList1 = cadrePostMapper.selectByExample(example);
                if (cadrePostList1 != null && cadrePostList1.size() > 0)
                    cadrePostList.addAll(cadrePostList1);

            }
        }
        return cadrePostList;
    }
}
