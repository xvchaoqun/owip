package service.ps;

import domain.ps.PsTask;
import domain.ps.PsTaskExample;
import domain.ps.PsTaskFile;
import domain.ps.PsTaskFileExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import persistence.ps.PsTaskFileMapper;

import java.util.Arrays;
import java.util.List;

@Service
public class PsTaskService extends PsBaseMapper {

    @Autowired
    protected PsTaskFileMapper psTaskFileMapper;

    @Transactional
    public void insertSelective(PsTask record){

        psTaskMapper.insertSelective(record);
    }

    @Transactional
    public void del(Integer id){

        psTaskMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        PsTaskExample example = new PsTaskExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        psTaskMapper.deleteByExample(example);
    }

    @Transactional
    public void updateByPrimaryKeySelective(PsTask record){

        psTaskMapper.updateByPrimaryKeySelective(record);
    }

    public List<PsTaskFile> getTaskFiles(Integer taskId){

        PsTaskFileExample psTaskFileExample = new PsTaskFileExample();
        psTaskFileExample.createCriteria().andTaskIdEqualTo(taskId);
        List<PsTaskFile> psTaskFiles = psTaskFileMapper.selectByExample(psTaskFileExample);
        return psTaskFiles;
    }
}
