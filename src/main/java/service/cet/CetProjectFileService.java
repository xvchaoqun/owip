package service.cet;

import domain.cet.CetProjectFile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CetProjectFileService extends CetBaseMapper {

    @Transactional
    public void insertSelective(CetProjectFile record, CetProjectFile record1){

        int projectId = record==null? record1.getProjectId() : record.getProjectId();
        if (record != null) {
            record.setSortOrder(getNextSortOrder("cet_project_file", "project_id=" + projectId));
            cetProjectFileMapper.insertSelective(record);
        }

        if (record1 != null) {
            record1.setSortOrder(getNextSortOrder("cet_project_file", "project_id=" + projectId));
            cetProjectFileMapper.insertSelective(record1);
        }

        iCetMapper.refreshFileCount(projectId);
    }

    @Transactional
    public void del(Integer id){

        CetProjectFile cetProjectFile = cetProjectFileMapper.selectByPrimaryKey(id);
        int projectId = cetProjectFile.getProjectId();

        cetProjectFileMapper.deleteByPrimaryKey(id);

        iCetMapper.refreshFileCount(projectId);
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
