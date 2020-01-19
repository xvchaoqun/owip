package service.cadre;

import domain.cadre.CadrePositionReport;
import domain.cadre.CadrePositionReportExample;
import domain.cadre.CadreView;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import service.BaseMapper;
import service.common.FreemarkerService;
import shiro.ShiroHelper;

import java.io.Writer;
import java.util.*;

@Service
public class CadrePositionReportService extends BaseMapper {
    @Autowired
    private CadreService cadreService;
    @Autowired
    FreemarkerService freemarkerService;

    public boolean idDuplicate(Integer id,Integer cadreId, Integer year){

        Assert.isTrue(year!=null, "null");

        CadrePositionReportExample example = new CadrePositionReportExample();
        CadrePositionReportExample.Criteria criteria = example.createCriteria().andCadreIdEqualTo(cadreId).andYearEqualTo(year);
        if(id!=null) criteria.andIdNotEqualTo(id);

        return cadrePositionReportMapper.countByExample(example) > 0;
    }

    @Transactional
    public void insertSelective(CadrePositionReport record){
        CadreView cadre = cadreService.dbFindByUserId(ShiroHelper.getCurrentUserId());
        if(!ShiroHelper.isPermitted("cadrePositionReport:adminMenu")&&!cadre.getId().equals(record.getCadreId())){
            throw new UnauthorizedException();
        }
        Assert.isTrue(!idDuplicate(null, record.getCadreId(),record.getYear()), "duplicate");
        record.setCreateTime(new Date());
        cadrePositionReportMapper.insertSelective(record);
    }

    @Transactional
    public void del(Integer id){
        CadrePositionReport record= cadrePositionReportMapper.selectByPrimaryKey(id);
        CadreView cadre = cadreService.dbFindByUserId(ShiroHelper.getCurrentUserId());
        if(!ShiroHelper.isPermitted("cadrePositionReport:adminMenu")&&!cadre.getId().equals(record.getCadreId())){
            throw new UnauthorizedException();
        }
        cadrePositionReportMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids){
        CadreView cadre = cadreService.dbFindByUserId(ShiroHelper.getCurrentUserId());
        if(!ShiroHelper.isPermitted("cadrePositionReport:adminMenu")){
            throw new UnauthorizedException();
        }
        if(ids==null || ids.length==0) return;

        CadrePositionReportExample example = new CadrePositionReportExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        cadrePositionReportMapper.deleteByExample(example);
    }

    @Transactional
    public void updateByPrimaryKeySelective(CadrePositionReport record){
        CadreView cadre = cadreService.dbFindByUserId(ShiroHelper.getCurrentUserId());
        if(!ShiroHelper.isPermitted("cadrePositionReport:adminMenu")&&!cadre.getId().equals(record.getCadreId())){
            throw new UnauthorizedException();
        }
        if(record.getYear()!=null)
            Assert.isTrue(!idDuplicate(record.getId(),record.getCadreId(), record.getYear()), "duplicate");
        record.setCreateTime(new Date());
        cadrePositionReportMapper.updateByPrimaryKeySelective(record);
    }

    @Transactional
    public void changeOrder(int id, int addNum) {

        changeOrder("cadre_position_report", null, ORDER_BY_DESC, id, addNum);
    }
    @Transactional
    public void export(Integer id, Writer out)throws Exception {

        Map<String, Object> dataMap = new HashMap<String, Object>();
        CadrePositionReport cpr = cadrePositionReportMapper.selectByPrimaryKey(id);

        cpr.setContent(freemarkerService.genTextareaSegment(cpr.getContent(), "/common/editor2.ftl"));
        dataMap.put("cpr",cpr);

        freemarkerService.process("cadre/cadrePositionReport.ftl", dataMap, out);
        out.close();
    }
    public Map<Integer, CadrePositionReport> findAll() {

        CadrePositionReportExample example = new CadrePositionReportExample();
        example.createCriteria();
        example.setOrderByClause("sort_order desc");
        List<CadrePositionReport> records = cadrePositionReportMapper.selectByExample(example);
        Map<Integer, CadrePositionReport> map = new LinkedHashMap<>();
        for (CadrePositionReport record : records) {
            map.put(record.getId(), record);
        }

        return map;
    }
}
