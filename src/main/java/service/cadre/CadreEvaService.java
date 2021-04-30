package service.cadre;

import domain.cadre.*;
import domain.sys.SysUserView;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import service.BaseMapper;
import service.base.MetaTypeService;
import sys.constants.CadreConstants;
import sys.tags.CmTag;
import sys.utils.DateUtils;
import sys.utils.ExportHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class CadreEvaService extends BaseMapper {

    @Autowired
    private MetaTypeService metaTypeService;
    private Logger logger = LoggerFactory.getLogger(getClass());

    public boolean idDuplicate(Integer id, Integer cadreId, Integer year){

        CadreEvaExample example = new CadreEvaExample();
        CadreEvaExample.Criteria criteria = example.createCriteria();
        if (cadreId != null) {
            criteria.andCadreIdEqualTo(cadreId);
        }
        if (year != null) {
            criteria.andYearEqualTo(year);
        }
        if(id!=null) criteria.andIdNotEqualTo(id);

        return cadreEvaMapper.countByExample(example) > 0;
    }
    public CadreEva get(int cadreId, int year){

        CadreEvaExample example = new CadreEvaExample();
        CadreEvaExample.Criteria criteria = example.createCriteria()
                .andCadreIdEqualTo(cadreId).andYearEqualTo(year);

        List<CadreEva> cadreEvas = cadreEvaMapper.selectByExampleWithRowbounds(example, new RowBounds(0, 1));

        return cadreEvas.size()>0?cadreEvas.get(0):null;
    }

    @Transactional
    public void insertSelective(CadreEva record){

        Assert.isTrue(!idDuplicate(null, record.getCadreId(), record.getYear()), "duplicate");
        cadreEvaMapper.insertSelective(record);
    }

    @Transactional
    public int batchImport(List<CadreEva> records) {

        int addCount = 0;
        for (CadreEva record : records) {

            int cadreId = record.getCadreId();
            int year = record.getYear();
            CadreEva cadreEva = get(cadreId, year);
            if(cadreEva==null) {
                insertSelective(record);
                addCount++;
            }else{
                int id = cadreEva.getId();
                record.setId(id);
                updateByPrimaryKeySelective(record);

                /*if(StringUtils.isBlank(record.getTitle())){
                    commonMapper.excuteSql("update cadre_eva set title = null where id="+id);
                }*/
            }
        }

        return addCount;
    }

    @Transactional
    public void del(Integer id){

        cadreEvaMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        CadreEvaExample example = new CadreEvaExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        cadreEvaMapper.deleteByExample(example);
    }

    @Transactional
    public int updateByPrimaryKeySelective(CadreEva record){
        if(record.getCadreId()!=null && record.getType()!=null)
            Assert.isTrue(!idDuplicate(record.getId(), record.getCadreId(), record.getYear()), "duplicate");

        return cadreEvaMapper.updateByPrimaryKeySelective(record);
    }

    // 导出年度考核结果
    public void export(int startYear, int endYear, Integer[] ids, Byte status, int exportType,
                       Integer reserveType, HttpServletResponse response) {

        List<CadreEva> cadreEvas = new ArrayList<>();
        String preStr = "";
        if (exportType == 0){
            cadreEvas = iCadreMapper.getCadreEvas(startYear, endYear, ids, status);//现任干部
        }else {
            preStr = metaTypeService.getName(reserveType);
            cadreEvas = iCadreMapper.getCadreReserveEvas(startYear, endYear, ids, reserveType, CadreConstants.CADRE_RESERVE_STATUS_NORMAL);
        }

        // <cadreId, <year, cadreEva>>
        Map<Integer, Map<Integer, CadreEva>> resultMap = new LinkedHashMap<>();

        for (CadreEva cadreEva : cadreEvas) {

            int cadreId = cadreEva.getCadreId();
            Map<Integer, CadreEva> cadreEvaMap = resultMap.get(cadreId);
            if(cadreEvaMap==null){
                cadreEvaMap = new HashMap<>();
                resultMap.put(cadreId, cadreEvaMap);
            }

            cadreEvaMap.put(cadreEva.getYear(), cadreEva);
        }

        List<String> titles = new ArrayList(Arrays.asList("工作证号|100", "姓名|80", "所在单位及职务|250|left"));
        for (int i = endYear; i >= startYear ; i--) {
            titles.add(i + "年|80");
        }

        List<List<String>> valuesList = new ArrayList<>();

        for (Map.Entry<Integer, Map<Integer, CadreEva>> entry : resultMap.entrySet()) {

            int cadreId = entry.getKey();
            Map<Integer, CadreEva> cadreEvaMap = entry.getValue();

            CadreView cadre = CmTag.getCadreById(cadreId);
            List<String> values = new ArrayList(Arrays.asList(cadre.getCode(), cadre.getRealname(), cadre.getTitle()));
            for (int i = endYear; i >= startYear ; i--) {

                String eva = null;
                CadreEva cadreEva = cadreEvaMap.get(i);
                if(cadreEva!=null){
                    eva = CmTag.getMetaTypeName(cadreEva.getType());
                }

                values.add(StringUtils.trimToEmpty(eva));
            }

            valuesList.add(values);
        }

        String fileName = preStr + "年度考核结果(" + startYear +"-" + endYear + ")";
        ExportHelper.export(titles, valuesList, fileName, response);
    }

    public void cadreEva_export(Integer[] ids, Integer cadreId, int exportType, byte status, HttpServletRequest request, HttpServletResponse response) {
        CadreEvaExample example =new CadreEvaExample();
        CadreEvaExample.Criteria criteria =example.createCriteria();
        example.setOrderByClause("year desc");
        //导出校级领导信息
        if (exportType == 2) {
            if (ids != null && ids.length > 0) {
                criteria.andCadreIdIn(Arrays.asList(ids));
            } else {
                List<CadreEdu> cadreEdus = iCadreMapper.getCadreEdus(ids, status);
                List<Integer> list = new ArrayList<>();
                for (CadreEdu cadreEdu: cadreEdus) {
                    list.add(cadreEdu.getCadreId());
                }
                criteria.andCadreIdIn(list);
                Integer now = DateUtils.getCurrentYear();
                criteria.andYearBetween(now - 5, DateUtils.getCurrentYear());
            }
        } else {
            //导出领导干部信息
            if(ids != null && ids.length > 0){
                criteria.andIdIn(Arrays.asList(ids));
            } else {
                if (cadreId != null) {
                    criteria.andCadreIdEqualTo(cadreId);
                }
            }
        }
        List<CadreEva> records = cadreEvaMapper.selectByExample(example);
        List<String> titles = new ArrayList();
        titles.add("年份|80");
        titles.add("工作证号|100");
        titles.add("姓名|100");
        titles.add("考核情况|100");
        titles.add("时任职务|250");
        titles.add("备注|200");

        int rownum = records.size();
        List<List<String>> valueList = new ArrayList<>();
        for (int i = 0; i< rownum; i++){

            CadreEva record = records.get(i);
            List<String> values = new ArrayList<>();
            values.add(record.getYear() + "");
            SysUserView user = record.getCadre().getUser();
            values.add(user.getCode());
            values.add(user.getRealname());
            values.add(metaTypeMapper.selectByPrimaryKey(record.getType()).getName());
            values.add(record.getTitle());
            values.add(record.getRemark());

            valueList.add(values);
        }

        String fileName = "干部年度考核记录.xlsx";
        ExportHelper.export(titles, valueList, fileName, response);
    }
}
