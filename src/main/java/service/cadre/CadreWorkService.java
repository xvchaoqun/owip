package service.cadre;

import domain.CadreWork;
import domain.CadreWorkExample;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class CadreWorkService extends BaseMapper {

    // 更新 子工作经历的数量
    private void updateSubWorkCount(Integer fid){
        if(fid!=null){
            CadreWorkExample example = new CadreWorkExample();
            example.createCriteria().andFidEqualTo(fid);
            int subWorkCount = cadreWorkMapper.countByExample(example);
            CadreWork _mainWork = new CadreWork();
            _mainWork.setId(fid);
            _mainWork.setSubWorkCount(subWorkCount);
            cadreWorkMapper.updateByPrimaryKeySelective(_mainWork);
        }
    }

    @Transactional
    public void insertSelective(CadreWork record){

        record.setSubWorkCount(0);
        cadreWorkMapper.insertSelective(record); // 先插入

        updateSubWorkCount(record.getFid()); // 必须放插入之后
    }
    @Transactional
    public void del(Integer id){

        CadreWork cadreWork = cadreWorkMapper.selectByPrimaryKey(id);
        cadreWorkMapper.deleteByPrimaryKey(id); // 先删除

        updateSubWorkCount(cadreWork.getFid()); // 如果有父工作经历，则更新父工作经历的期间工作数量

        CadreWorkExample example = new CadreWorkExample();
        example.createCriteria().andFidEqualTo(id);
        cadreWorkMapper.deleteByExample(example); // 如果有子工作经历，则删除
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;
        int count = 0;
        {
            CadreWorkExample example = new CadreWorkExample();
            example.createCriteria().andIdIn(Arrays.asList(ids)).andFidIsNull(); // 只能批量删除父工作经历
            count = cadreWorkMapper.deleteByExample(example);
        }
        if(count>0){

            CadreWorkExample example = new CadreWorkExample();
            example.createCriteria().andFidIn(Arrays.asList(ids));
            cadreWorkMapper.deleteByExample(example);
        }
    }

    @Transactional
    public int updateByPrimaryKeySelective(CadreWork record){

        record.setFid(null); // 不能更新所属工作经历
        return cadreWorkMapper.updateByPrimaryKeySelective(record);
    }

    public Map<Integer, CadreWork> findAll() {

        CadreWorkExample example = new CadreWorkExample();
        example.setOrderByClause("sort_order desc");
        List<CadreWork> cadreWorkes = cadreWorkMapper.selectByExample(example);
        Map<Integer, CadreWork> map = new LinkedHashMap<>();
        for (CadreWork cadreWork : cadreWorkes) {
            map.put(cadreWork.getId(), cadreWork);
        }

        return map;
    }
}
