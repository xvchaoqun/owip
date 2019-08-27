package service.pcs;

import domain.pcs.PcsPrFileTemplate;
import domain.pcs.PcsPrFileTemplateExample;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class PcsPrFileTemplateService extends PcsBaseMapper {

    public static final String TABLE_NAME = "pcs_pr_file_template";

    // 所有模板列表
    public List<PcsPrFileTemplate> findAll(int configId){

        PcsPrFileTemplateExample example = new PcsPrFileTemplateExample();
        example.createCriteria().andConfigIdEqualTo(configId).andIsDeletedEqualTo(false);
        example.setOrderByClause("sort_order asc");
        List<PcsPrFileTemplate> templates = pcsPrFileTemplateMapper.selectByExample(example);

        return templates;
    }

    public void insertSelective(PcsPrFileTemplate record) {

        record.setIsDeleted(false);
        record.setCreateTime(new Date());
        record.setSortOrder(getNextSortOrder(TABLE_NAME, "is_deleted=0"));
        pcsPrFileTemplateMapper.insertSelective(record);
    }

    public void del(int id){

        PcsPrFileTemplate record = new PcsPrFileTemplate();
        record.setId(id);
        record.setIsDeleted(true);

        pcsPrFileTemplateMapper.updateByPrimaryKeySelective(record);
    }

    // 升序排列
    @Transactional
    public void changeOrder(int id, int addNum) {

        PcsPrFileTemplate entity = pcsPrFileTemplateMapper.selectByPrimaryKey(id);
        changeOrder(TABLE_NAME, "is_deleted=" + entity.getIsDeleted(), ORDER_BY_ASC, id, addNum);
    }
}
