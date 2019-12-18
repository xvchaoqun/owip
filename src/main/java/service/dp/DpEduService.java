package service.dp;

import controller.global.OpException;
import domain.base.MetaType;
import domain.dp.DpEdu;
import domain.dp.DpEduExample;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import sys.constants.SystemConstants;
import sys.tags.CmTag;

import java.util.*;

@Service
public class DpEduService extends DpBaseMapper {

    public boolean idDuplicate(Integer id, String code){

        Assert.isTrue(StringUtils.isNotBlank(code), "null");

        DpEduExample example = new DpEduExample();
        DpEduExample.Criteria criteria = example.createCriteria();
        if(id!=null) criteria.andIdNotEqualTo(id);

        return dpEduMapper.countByExample(example) > 0;
    }

    @Transactional
    public void insertSelective(DpEdu record){

        checkUpdate(record);

        record.setSubWorkCount(0);
        record.setStatus(SystemConstants.RECORD_STATUS_FORMAL);
        dpEduMapper.insertSelective(record);

        adjustHighDegree(record.getId(), record.getUserId(), BooleanUtils.isTrue(record.getIsSecondDegree()));
    }

    @Transactional
    public void del(Integer id){

        dpEduMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        DpEduExample example = new DpEduExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        dpEduMapper.deleteByExample(example);
    }

    @Transactional
    public void updateByPrimaryKeySelective(DpEdu record){

        dpEduMapper.updateByPrimaryKeySelective(record);
    }

    public Map<Integer, DpEdu> findAll() {

        DpEduExample example = new DpEduExample();
        example.createCriteria();
        example.setOrderByClause("sort_order desc");
        List<DpEdu> records = dpEduMapper.selectByExample(example);
        Map<Integer, DpEdu> map = new LinkedHashMap<>();
        for (DpEdu record : records) {
            map.put(record.getId(), record);
        }

        return map;
    }
    /**
     * 排序 ，要求 1、sort_order>0且不可重复  2、sort_order 降序排序
     * @param id
     * @param addNum
     */
    @Transactional
    public void changeOrder(int id, int addNum) {

        changeOrder("dp_edu", null, ORDER_BY_DESC, id, addNum);
    }

    public List<Integer> needTutorEduTypes() {

        MetaType eduDoctor = CmTag.getMetaTypeByCode("mt_edu_doctor");
        MetaType eduMaster = CmTag.getMetaTypeByCode("mt_edu_master");
        MetaType eduSstd = CmTag.getMetaTypeByCode("mt_edu_sstd");
        List<Integer> needTutorEduTypeIds = new ArrayList<>();
        needTutorEduTypeIds.add(eduDoctor.getId());
        needTutorEduTypeIds.add(eduMaster.getId());
        needTutorEduTypeIds.add(eduSstd.getId());

        return needTutorEduTypeIds;
    }

    // 更新或添加时，检查规则
    public void checkUpdate(DpEdu record) {

        if (isNotGraduated(record.getId(), record.getUserId(), record.getIsGraduated())) {
            throw new OpException("已经存在一条在读记录");
        }

        // 非第二最高学位，不允许存在多个最高学历
        if (BooleanUtils.isNotTrue(record.getIsSecondDegree())
                && hasHighEdu(record.getId(), record.getUserId(), record.getIsHighEdu())) {

            if(BooleanUtils.isTrue(record.getIsHighDegree())){

                throw new OpException("已经存在最高学历（注：如获得了双学位请勾选“第二个最高学位”选项）");
            }else {
                throw new OpException("已经存在最高学历");
            }
        }
    }

    // 在读只允许一条记录
    public boolean isNotGraduated(Integer id, int userId, Boolean isGraduated) {

        if (BooleanUtils.isTrue(isGraduated)) return false;

        DpEduExample example = new DpEduExample();
        DpEduExample.Criteria criteria = example.createCriteria().andUserIdEqualTo(userId)
                .andIsGraduatedEqualTo(false).andStatusEqualTo(SystemConstants.RECORD_STATUS_FORMAL);
        if (id != null) criteria.andIdNotEqualTo(id);
        return dpEduMapper.countByExample(example) > 0;
    }

    public boolean hasHighEdu(Integer id, int userId, Boolean isHighEdu) {

        return hasHighEdu(id, userId, isHighEdu, SystemConstants.RECORD_STATUS_FORMAL);
    }

    public boolean hasHighEdu(Integer id, int userId, Boolean isHighEdu, byte status) {

        // isHighEdu=null return false
        if (BooleanUtils.isNotTrue(isHighEdu)) return false;

        DpEduExample example = new DpEduExample();
        DpEduExample.Criteria criteria = example.createCriteria().andUserIdEqualTo(userId)
                .andIsHighEduEqualTo(true).andStatusEqualTo(status);
        if (id != null) criteria.andIdNotEqualTo(id);
        return dpEduMapper.countByExample(example) > 0;
    }

    /**
     * 如果成员的最高学位是双学位或多个学位，则调整成员的最高学位，
     * 使其只有一个最高学位（is_second_degree=0)，其他都是第二个学位（is_second_degree=1)
     *
     * @param id             待添加或更新的记录ID
     * @param isSecondDegree 更新为是否第二个学位
     */
    public void adjustHighDegree(Integer id, int userId, boolean isSecondDegree) {

        DpEdu dpEdu = dpEduMapper.selectByPrimaryKey(id);
        if (dpEdu == null || BooleanUtils.isNotTrue(dpEdu.getIsHighDegree())) {

            // 修改非最高学位或删除记录或异常的情况，找不到当前记录，则检验一下
            adjustHighDegreeAnyway(userId);
        } else {

            userId = dpEdu.getUserId();
            {
                // 先更新当前学历
                DpEdu record = new DpEdu();
                record.setId(id);
                record.setIsSecondDegree(isSecondDegree);
                dpEduMapper.updateByPrimaryKeySelective(record);
            }

            if (isSecondDegree) {

                adjustHighDegreeAnyway(userId);
            } else {
                /**
                 * 当前记录更新为第一个最高学位，则其他最高学位均更新为第二个最高学位
                 */
                DpEduExample example = new DpEduExample();
                example.createCriteria().andUserIdEqualTo(userId)
                        .andIsHighDegreeEqualTo(true)
                        .andStatusEqualTo(SystemConstants.RECORD_STATUS_FORMAL)
                        .andIdNotEqualTo(id);

                DpEdu record = new DpEdu();
                record.setIsSecondDegree(true);
                dpEduMapper.updateByExampleSelective(record, example);
            }
        }
    }

    // 校正第一个最高学位
    private void adjustHighDegreeAnyway(int userId) {
        /**
         * 当前记录更新为第二个最高学位，必须要存在第一个最高学位，否则选择一个时间最早的为第一个最高学位
         */
        int firstHighDegreeCount = firstHighDegreeCount(null, userId, SystemConstants.RECORD_STATUS_FORMAL);
        if (firstHighDegreeCount == 0) {

            DpEduExample example = new DpEduExample();
            example.createCriteria().andUserIdEqualTo(userId)
                    .andIsHighDegreeEqualTo(true)
                    .andStatusEqualTo(SystemConstants.RECORD_STATUS_FORMAL);
            example.setOrderByClause("enrol_time asc");
            List<DpEdu> dpEdus = dpEduMapper.selectByExampleWithRowbounds(example, new RowBounds(0, 1));
            if (dpEdus.size() > 0) {
                DpEdu record = new DpEdu();
                record.setId(dpEdus.get(0).getId());
                record.setIsSecondDegree(false);
                dpEduMapper.updateByPrimaryKeySelective(record);
            }
        } else if (firstHighDegreeCount > 1) { // 如果存在多个第一个最高学位，则仅保留时间最早的一个

            DpEduExample example = new DpEduExample();
            example.createCriteria().andUserIdEqualTo(userId)
                    .andIsHighDegreeEqualTo(true)
                    .andIsSecondDegreeEqualTo(false)
                    .andStatusEqualTo(SystemConstants.RECORD_STATUS_FORMAL);
            example.setOrderByClause("enrol_time asc");
            List<DpEdu> dpEdus = dpEduMapper.selectByExampleWithRowbounds(example, new RowBounds(0, 1));
            if (dpEdus.size() > 0) {

                example = new DpEduExample();
                example.createCriteria().andUserIdEqualTo(userId)
                        .andIsHighDegreeEqualTo(true)
                        .andStatusEqualTo(SystemConstants.RECORD_STATUS_FORMAL)
                        .andIdNotEqualTo(dpEdus.get(0).getId());

                DpEdu record = new DpEdu();
                record.setIsSecondDegree(true);
                dpEduMapper.updateByExampleSelective(record, example);
            }
        }
    }

    public int firstHighDegreeCount(Integer id, int userId, byte status) {

        DpEduExample example = new DpEduExample();
        DpEduExample.Criteria criteria = example.createCriteria().andUserIdEqualTo(userId)
                .andIsHighDegreeEqualTo(true).andIsSecondDegreeEqualTo(false)
                .andStatusEqualTo(status);
        if (id != null) criteria.andIdNotEqualTo(id);
        return (int) dpEduMapper.countByExample(example);
    }

}
