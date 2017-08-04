package service.crs;

import domain.crs.CrsPost;
import domain.crs.CrsPostExample;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;
import sys.constants.SystemConstants;

import java.util.Arrays;
import java.util.List;

@Service
public class CrsPostService extends BaseMapper {


    public CrsPost get(int id){

        return crsPostMapper.selectByPrimaryKey(id);
    }
    // 生成编号
    public int genSeq(byte type, int year){

        int seq ;
        CrsPostExample example = new CrsPostExample();
        example.createCriteria().andYearEqualTo(year).andTypeEqualTo(type);
        example.setOrderByClause("seq desc");
        List<CrsPost> records = crsPostMapper.selectByExampleWithRowbounds(example, new RowBounds(0, 1));
        if(records.size()>0){
            seq = records.get(0).getSeq() + 1;
        }else{
            seq = 1;
        }

        return seq;
    }

    @Transactional
    public void insertSelective(CrsPost record){

        crsPostMapper.insertSelective(record);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        CrsPostExample example = new CrsPostExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        CrsPost record = new CrsPost();
        record.setStatus(SystemConstants.CRS_POST_STATUS_DELETE);
        crsPostMapper.updateByExampleSelective(record, example);
    }

    @Transactional
    public void realDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        CrsPostExample example = new CrsPostExample();
        example.createCriteria().andIdIn(Arrays.asList(ids)).andStatusEqualTo(SystemConstants.CRS_POST_STATUS_DELETE);

        crsPostMapper.deleteByExample(example);
    }

    @Transactional
    public int updateByPrimaryKeySelective(CrsPost record){

        return crsPostMapper.updateByPrimaryKeySelective(record);
    }
}
