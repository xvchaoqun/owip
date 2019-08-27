package service.cadre;

import domain.cadre.CadreCompanyFile;
import domain.cadre.CadreCompanyFileExample;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;

import java.util.Arrays;
import java.util.List;

@Service
public class CadreCompanyFileService extends BaseMapper {

    /*public boolean idDuplicate(Integer id, String code){

        Assert.isTrue(StringUtils.isNotBlank(code), "null");

        CadreCompanyFileExample example = new CadreCompanyFileExample();
        CadreCompanyFileExample.Criteria criteria = example.createCriteria().andCodeEqualTo(code).andStatusEqualTo(true);
        if(id!=null) criteria.andIdNotEqualTo(id);

        return cadreCompanyFileMapper.countByExample(example) > 0;
    }*/

    @Transactional
    public void insertSelective(CadreCompanyFile record){

        record.setSortOrder(getNextSortOrder("cadre_company_file", null));
        cadreCompanyFileMapper.insertSelective(record);
    }

    @Transactional
    public void del(Integer id){

        cadreCompanyFileMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        CadreCompanyFileExample example = new CadreCompanyFileExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        cadreCompanyFileMapper.deleteByExample(example);
    }

    @Transactional
    public int updateByPrimaryKeySelective(CadreCompanyFile record){
        return cadreCompanyFileMapper.updateByPrimaryKeySelective(record);
    }

    public List<CadreCompanyFile> findAll(boolean type) {

        CadreCompanyFileExample example = new CadreCompanyFileExample();
        example.createCriteria().andTypeEqualTo(type);
        example.setOrderByClause("sort_order desc");

        return cadreCompanyFileMapper.selectByExample(example);
    }

    /**
     * 排序 ，要求 1、sort_order>0且不可重复  2、sort_order 降序排序
     * @param id
     * @param addNum
     */
    @Transactional
    public void changeOrder(int id, int addNum) {

        changeOrder("cadre_company_file", null, ORDER_BY_DESC, id, addNum);
    }
}
