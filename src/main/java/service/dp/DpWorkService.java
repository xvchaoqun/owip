package service.dp;

import controller.global.OpException;
import domain.dp.DpEdu;
import domain.dp.DpWork;
import domain.dp.DpWorkExample;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import sys.constants.SystemConstants;

import java.util.*;

@Service
public class DpWorkService extends DpBaseMapper {

    public boolean idDuplicate(Integer id, String code){

        Assert.isTrue(StringUtils.isNotBlank(code), "null");

        DpWorkExample example = new DpWorkExample();
        DpWorkExample.Criteria criteria = example.createCriteria();
        if(id!=null) criteria.andIdNotEqualTo(id);

        return dpWorkMapper.countByExample(example) > 0;
    }

    @Transactional
    public void insertSelective(DpWork record){

        record.setSubWorkCount(0);
        record.setStatus(SystemConstants.RECORD_STATUS_FORMAL);
        //Assert.isTrue(!idDuplicate(null, record.getCode()), "duplicate");
        //record.setSortOrder(getNextSortOrder("dp_work", null));
        dpWorkMapper.insertSelective(record);

        updateSubWorkCount(record.getFid(), record.getIsEduWork()); // 必须放插入之后
    }

    @Transactional
    public void del(Integer id){

        dpWorkMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids, Integer userId){

        if(ids==null || ids.length==0) return;
        {
            // 干部信息本人直接修改数据校验
            DpWorkExample example = new DpWorkExample();
            example.createCriteria().andUserIdEqualTo(userId).andIdIn(Arrays.asList(ids));
            long count = dpWorkMapper.countByExample(example);
            if (count != ids.length) {
                throw new OpException("数据有误，请稍后再试。");
            }
        }

        {  // 先删除下面的其间工作经历（如果有）（不包括修改申请生成的记录，它们的fid将会更新为null）
            DpWorkExample example = new DpWorkExample();
            example.createCriteria().andFidIn(Arrays.asList(ids))
                    .andStatusEqualTo(SystemConstants.RECORD_STATUS_FORMAL);

            List<DpWork> subDpWorks = dpWorkMapper.selectByExample(example);
            if (subDpWorks.size() > 0) {
                dpWorkMapper.deleteByExample(example);

                List<Integer> subDpWorkIds = new ArrayList<>();
                for (DpWork subDpWork : subDpWorks) {
                    subDpWorkIds.add(subDpWork.getId());
                }
            }
        }

        //  如果待删除的记录是其间工作经历，则在删除之后，需要更新它的父工作经历的其间工作经历数量
        List<DpWork> subDpWorks = null;
        {
            // 1、读取待删除的记录中是其间工作经历的记录
            DpWorkExample example = new DpWorkExample();
            example.createCriteria().andIdIn(Arrays.asList(ids)).andFidIsNotNull();
            subDpWorks = dpWorkMapper.selectByExample(example);
        }
        {
            ///2、删除所有的记录
            DpWorkExample example = new DpWorkExample();
            example.createCriteria().andIdIn(Arrays.asList(ids));
            dpWorkMapper.deleteByExample(example);

            // 3、更新父工作经历的其间工作数量
            if (subDpWorks != null) {
                for (DpWork subDpWork : subDpWorks) {
                    updateSubWorkCount(subDpWork.getFid(), subDpWork.getIsEduWork());
                }
            }
        }
    }

    @Transactional
    public void updateByPrimaryKeySelective(DpWork record){
        record.setFid(null); // 不能更新所属工作经历
        record.setStatus(null);
       dpWorkMapper.updateByPrimaryKeySelective(record);
    }

    public Map<Integer, DpWork> findAll() {

        DpWorkExample example = new DpWorkExample();
        example.createCriteria();
        example.setOrderByClause("sort_order desc");
        List<DpWork> records = dpWorkMapper.selectByExample(example);
        Map<Integer, DpWork> map = new LinkedHashMap<>();
        for (DpWork record : records) {
            map.put(record.getId(), record);
        }

        return map;
    }

    // 更新 子工作经历的数量
    public void updateSubWorkCount(Integer fid, boolean isEduWork) {
        if (fid != null) {

            DpWorkExample example = new DpWorkExample();
            example.createCriteria().andFidEqualTo(fid).andIsEduWorkEqualTo(isEduWork)
                    .andStatusEqualTo(SystemConstants.RECORD_STATUS_FORMAL);
            int subWorkCount = (int) dpWorkMapper.countByExample(example);

            if (isEduWork) {
                DpEdu record = new DpEdu();
                record.setId(fid);
                record.setSubWorkCount(subWorkCount);
                dpEduMapper.updateByPrimaryKeySelective(record);
            } else {
                DpWork record = new DpWork();
                record.setId(fid);
                record.setSubWorkCount(subWorkCount);
                dpWorkMapper.updateByPrimaryKeySelective(record);
            }
        }
    }

    //简历-工作经历
    public List<DpWork> list(Integer userId){

        List<DpWork> dpWorks = null;
        {
            DpWorkExample example = new DpWorkExample();
            example.createCriteria().andUserIdEqualTo(userId).andFidIsNull()
                    .andStatusEqualTo(SystemConstants.RECORD_STATUS_FORMAL);
            example.setOrderByClause("start_time asc");
            dpWorks = dpWorkMapper.selectByExample(example);
        }
        if (dpWorks != null){
            for (DpWork dpWork : dpWorks){
                Integer fid = dpWork.getId();
                DpWorkExample example = new DpWorkExample();
                example.createCriteria().andUserIdEqualTo(userId).andFidEqualTo(fid)
                        .andIsEduWorkEqualTo(false).andStatusEqualTo(SystemConstants.RECORD_STATUS_FORMAL);
                example.setOrderByClause("start_time asc");
                List<DpWork> subDpWorks = dpWorkMapper.selectByExample(example);
                dpWork.setSubDpWorks(subDpWorks);
            }
        }

        return dpWorks;
    }
}
