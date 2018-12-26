package service.pcs;

import domain.pcs.PcsPrFileTemplate;
import domain.pcs.PcsPrFileTemplateExample;
import org.apache.ibatis.session.RowBounds;
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

        if (addNum == 0) return;
        byte orderBy = ORDER_BY_ASC;
        PcsPrFileTemplate entity = pcsPrFileTemplateMapper.selectByPrimaryKey(id);
        Integer baseSortOrder = entity.getSortOrder();
        boolean isDeleted = entity.getIsDeleted();

        PcsPrFileTemplateExample example = new PcsPrFileTemplateExample();
        if (addNum*orderBy > 0) { // 升序

            example.createCriteria().andIsDeletedEqualTo(isDeleted).andSortOrderGreaterThan(baseSortOrder);
            example.setOrderByClause("sort_order asc");
        } else {

            example.createCriteria().andIsDeletedEqualTo(isDeleted).andSortOrderLessThan(baseSortOrder);
            example.setOrderByClause("sort_order desc");
        }

        List<PcsPrFileTemplate> overEntities = pcsPrFileTemplateMapper.selectByExampleWithRowbounds(example, new RowBounds(0, Math.abs(addNum)));
        if (overEntities.size() > 0) {

            PcsPrFileTemplate targetEntity = overEntities.get(overEntities.size() - 1);

            if (addNum*orderBy > 0)
                commonMapper.downOrder(TABLE_NAME,  "is_deleted=" + isDeleted, baseSortOrder, targetEntity.getSortOrder());
            else
                commonMapper.upOrder(TABLE_NAME,  "is_deleted=" + isDeleted, baseSortOrder, targetEntity.getSortOrder());

            PcsPrFileTemplate record = new PcsPrFileTemplate();
            record.setId(id);
            record.setSortOrder(targetEntity.getSortOrder());
            pcsPrFileTemplateMapper.updateByPrimaryKeySelective(record);
        }
    }
}
