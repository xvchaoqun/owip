package service.crs;

import domain.crs.CrsPostFile;
import domain.crs.CrsPostFileExample;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Service
public class CrsPostFileService extends CrsBaseMapper {

    public List<CrsPostFile> getPostFiles(int postId, byte type){

        CrsPostFileExample example = new CrsPostFileExample();
        example.createCriteria().andPostIdEqualTo(postId).andTypeEqualTo(type);

        return crsPostFileMapper.selectByExample(example);
    }

    @Transactional
    public void insertSelective(CrsPostFile record) {

        crsPostFileMapper.insertSelective(record);
    }

    @Transactional
    public void batchAdd(List<CrsPostFile> records) {

        for (CrsPostFile record : records) {

            crsPostFileMapper.insertSelective(record);
        }
    }

    @Transactional
    public void del(Integer id) {

        crsPostFileMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids) {

        if (ids == null || ids.length == 0) return;

        CrsPostFileExample example = new CrsPostFileExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        crsPostFileMapper.deleteByExample(example);
    }

    @Transactional
    public int updateByPrimaryKeySelective(CrsPostFile record) {
        return crsPostFileMapper.updateByPrimaryKeySelective(record);
    }

}
