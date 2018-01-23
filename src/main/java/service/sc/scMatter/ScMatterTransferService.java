package service.sc.scMatter;

import domain.sc.scMatter.ScMatterItem;
import domain.sc.scMatter.ScMatterItemExample;
import domain.sc.scMatter.ScMatterTransfer;
import domain.sc.scMatter.ScMatterTransferExample;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;

import java.text.MessageFormat;
import java.util.Arrays;

@Service
public class ScMatterTransferService extends BaseMapper {

    @Transactional
    public void insertSelective(ScMatterTransfer record){

        scMatterTransferMapper.insertSelective(record);
    }

    @Transactional
    public void del(Integer id){

        scMatterTransferMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        ScMatterTransferExample example = new ScMatterTransferExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        scMatterTransferMapper.deleteByExample(example);

        commonMapper.excuteSql(MessageFormat.format("update sc_matter_item set " +
                "transfer_id=null where transfer_id in({0})", StringUtils.join(ids)));
    }

    @Transactional
    public int updateByPrimaryKeySelective(ScMatterTransfer record){
        return scMatterTransferMapper.updateByPrimaryKeySelective(record);
    }

    // 更新移交包含的填报记录
    @Transactional
    public void transfer(int transferId, Integer[] matterItemIds) {

        commonMapper.excuteSql(MessageFormat.format("update sc_matter_item set " +
                "transfer_id=null where transfer_id={0}", transferId));

        if(matterItemIds!=null && matterItemIds.length>0) {
            ScMatterItem record = new ScMatterItem();
            record.setTransferId(transferId);

            ScMatterItemExample example = new ScMatterItemExample();
            example.createCriteria().andIdIn(Arrays.asList(matterItemIds));
            scMatterItemMapper.updateByExampleSelective(record, example);
        }
    }
}
