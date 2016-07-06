package service.cadre;

import domain.cadre.CadreWork;
import domain.cadre.CadreWorkExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;
import service.dispatch.DispatchCadreRelateService;
import sys.constants.SystemConstants;

import java.util.*;

@Service
public class CadreWorkService extends BaseMapper {

    @Autowired
    private DispatchCadreRelateService dispatchCadreRelateService;

    // 获取树状列表
    public List<CadreWork> findCadreWorks(int cadreId){

        List<CadreWork> cadreWorks = null;
        {
            CadreWorkExample example = new CadreWorkExample();
            example.createCriteria().andCadreIdEqualTo(cadreId).andFidIsNull();
            example.setOrderByClause("start_time asc");
            cadreWorks = cadreWorkMapper.selectByExample(example);
        }
        if(cadreWorks!=null) {
            for (CadreWork cadreWork : cadreWorks) {
                Integer fid = cadreWork.getId();
                CadreWorkExample example = new CadreWorkExample();
                example.createCriteria().andFidEqualTo(fid);
                example.setOrderByClause("start_time asc");
                List<CadreWork> subCadreWorks = cadreWorkMapper.selectByExample(example);
                cadreWork.setSubCadreWorks(subCadreWorks);
            }
        }
        return cadreWorks;
    }

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
 /*   @Transactional
    public void del(Integer id){

        CadreWork cadreWork = cadreWorkMapper.selectByPrimaryKey(id);
        cadreWorkMapper.deleteByPrimaryKey(id); // 先删除

        updateSubWorkCount(cadreWork.getFid()); // 如果有父工作经历，则更新父工作经历的期间工作数量

        CadreWorkExample example = new CadreWorkExample();
        example.createCriteria().andFidEqualTo(id);

        List<CadreWork> subCadreWorks = cadreWorkMapper.selectByExample(example);
        if(subCadreWorks.size()>0) {
            cadreWorkMapper.deleteByExample(example); // 如果有子工作经历，则删除

            List<Integer> subCadreWorkIds = new ArrayList<>();
            for (CadreWork subCadreWork : subCadreWorks) {
                subCadreWorkIds.add(subCadreWork.getId());
            }
            // 同时删除关联的任免文件
            dispatchCadreRelateService.delDispatchCadreRelates(subCadreWorkIds, SystemConstants.DISPATCH_CADRE_RELATE_TYPE_WORK);
        }

        // 同时删除关联的任免文件
        dispatchCadreRelateService.delDispatchCadreRelates(Arrays.asList(id),  SystemConstants.DISPATCH_CADRE_RELATE_TYPE_WORK);
    }*/

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        {
            CadreWorkExample example = new CadreWorkExample();
            example.createCriteria().andFidIn(Arrays.asList(ids));

            List<CadreWork> subCadreWorks = cadreWorkMapper.selectByExample(example);
            if(subCadreWorks.size()>0) {
                cadreWorkMapper.deleteByExample(example); // 如果有期间工作，先删除

                List<Integer> subCadreWorkIds = new ArrayList<>();
                for (CadreWork subCadreWork : subCadreWorks) {
                    subCadreWorkIds.add(subCadreWork.getId());
                }
                // 同时删除关联的任免文件
                dispatchCadreRelateService.delDispatchCadreRelates(subCadreWorkIds, SystemConstants.DISPATCH_CADRE_RELATE_TYPE_WORK);
            }
        }

        List<CadreWork> topCadreWorks = null; //  读取所有父工作经历，下面待用
        {
            CadreWorkExample example = new CadreWorkExample();
            example.createCriteria().andIdIn(Arrays.asList(ids)).andFidIsNotNull();
            topCadreWorks = cadreWorkMapper.selectByExample(example);
        }
        {
            CadreWorkExample example = new CadreWorkExample();
            example.createCriteria().andIdIn(Arrays.asList(ids)); // 删除记录
            cadreWorkMapper.deleteByExample(example);

            if(topCadreWorks!=null) {
                for (CadreWork cadreWork : topCadreWorks) {
                    updateSubWorkCount(cadreWork.getFid()); // 更新父工作经历的期间工作数量
                }
            }
        }

        // 同时删除关联的任免文件
        dispatchCadreRelateService.delDispatchCadreRelates(Arrays.asList(ids),  SystemConstants.DISPATCH_CADRE_RELATE_TYPE_WORK);
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
