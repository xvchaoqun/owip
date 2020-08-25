package service.dispatch;

import domain.cadre.CadrePost;
import domain.dispatch.Dispatch;
import domain.dispatch.DispatchCadreRelate;
import domain.dispatch.DispatchCadreRelateExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import persistence.dispatch.common.DispatchCadreRelateBean;
import service.BaseMapper;
import service.global.CacheHelper;
import sys.constants.DispatchConstants;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class DispatchCadreRelateService extends BaseMapper {

    @Autowired
    private CacheHelper cacheHelper;

    // 删除已关联的干部发文
    public int delDispatchCadreRelates(List<Integer> relateIds, byte relateType){

        DispatchCadreRelateExample example = new DispatchCadreRelateExample();
        example.createCriteria().andRelateIdIn(relateIds).andRelateTypeEqualTo(relateType);
        return dispatchCadreRelateMapper.deleteByExample(example);
    }

    // 查找已关联的干部发文
    public List<DispatchCadreRelate> findDispatchCadreRelates(int relateId, byte relateType){

        DispatchCadreRelateExample example = new DispatchCadreRelateExample();
        example.createCriteria().andRelateIdEqualTo(relateId).andRelateTypeEqualTo(relateType);
        return dispatchCadreRelateMapper.selectByExample(example);
    }

    // 查找模块内其他记录已关联的干部发文 Set<dispatchCadreId>
    public Set<Integer> findOtherDispatchCadreRelateSet(int relateId, byte relateType){

        DispatchCadreRelateExample example = new DispatchCadreRelateExample();
        example.createCriteria().andRelateIdNotEqualTo(relateId).andRelateTypeEqualTo(relateType);
        List<DispatchCadreRelate> dispatchCadreRelates = dispatchCadreRelateMapper.selectByExample(example);
        Set<Integer> cadreDispatchIdSet = new HashSet<>();
        for (DispatchCadreRelate dispatchCadreRelate : dispatchCadreRelates) {
            cadreDispatchIdSet.add(dispatchCadreRelate.getDispatchCadreId());
        }
        return cadreDispatchIdSet;
    }

    // 更新关联的干部发文
    @Transactional
    public void updateDispatchCadreRelates(int relateId, byte relateType, Integer[] ids){

        // 先删除
        DispatchCadreRelateExample example = new DispatchCadreRelateExample();
        example.createCriteria().andRelateIdEqualTo(relateId).andRelateTypeEqualTo(relateType);
        dispatchCadreRelateMapper.deleteByExample(example);

        if(ids!=null && ids.length>0) {
            for (Integer id : ids) {
                DispatchCadreRelate record = new DispatchCadreRelate();
                record.setDispatchCadreId(id);
                record.setRelateId(relateId);
                record.setRelateType(relateType);
                dispatchCadreRelateMapper.insertSelective(record);
            }
        }

        // 任职关联文件，更新任职时间等
        if(relateType== DispatchConstants.DISPATCH_CADRE_RELATE_TYPE_POST){

            commonMapper.excuteSql("update cadre_post set np_dispatch_id=null, " +
                    "lp_dispatch_id=null, np_work_time=null, lp_work_time=null where id=" + relateId);

            if(ids!=null && ids.length>0) {
                CadrePost cadrePost = cadrePostMapper.selectByPrimaryKey(relateId);
                DispatchCadreRelateBean dispatchCadreRelateBean = cadrePost.getDispatchCadreRelateBean();

                Dispatch first = dispatchCadreRelateBean.getFirst();
                Dispatch last = dispatchCadreRelateBean.getLast();

                CadrePost record = new CadrePost();
                record.setId(cadrePost.getId());
                record.setNpDispatchId(first.getId());
                record.setNpWorkTime(first.getWorkTime());
                record.setLpDispatchId(last.getId());
                record.setLpWorkTime(last.getWorkTime());

                cadrePostMapper.updateByPrimaryKeySelective(record);

                cacheHelper.clearCadreCache(cadrePost.getCadreId());
            }
        }
    }
}
