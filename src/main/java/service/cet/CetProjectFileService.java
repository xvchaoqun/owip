package service.cet;

import domain.cet.CetProjectFile;
import domain.cet.CetProjectFileExample;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class CetProjectFileService extends CetBaseMapper {

    @Transactional
    public void insertSelective(CetProjectFile record){

        record.setSortOrder(getNextSortOrder("cet_project_file", "project_id=" + record.getProjectId()));
        cetProjectFileMapper.insertSelective(record);
    }

    @Transactional
    public void del(Integer id){

        cetProjectFileMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        CetProjectFileExample example = new CetProjectFileExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        cetProjectFileMapper.deleteByExample(example);
    }

    @Transactional
    public void updateByPrimaryKeySelective(CetProjectFile record){
        cetProjectFileMapper.updateByPrimaryKeySelective(record);
    }

    public Map<Integer, CetProjectFile> findAll() {

        CetProjectFileExample example = new CetProjectFileExample();
        example.createCriteria();
        example.setOrderByClause("sort_order desc");
        List<CetProjectFile> records = cetProjectFileMapper.selectByExample(example);
        Map<Integer, CetProjectFile> map = new LinkedHashMap<>();
        for (CetProjectFile record : records) {
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

        CetProjectFile record = cetProjectFileMapper.selectByPrimaryKey(id);
        changeOrder("cet_project_file", "project_id=" + record.getProjectId(), ORDER_BY_DESC, id, addNum);
    }
}
