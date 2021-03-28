package service.ces;

import domain.ces.CesResult;
import domain.ces.CesResultExample;
import domain.sys.SysUserView;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;
import sys.constants.SystemConstants;
import sys.utils.ExportHelper;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class CesResultService extends BaseMapper {

    public CesResult get(byte type, int unitId, Integer cadreId, int year, String name){

        CesResultExample example = new CesResultExample();
        CesResultExample.Criteria criteria = example.createCriteria().andTypeEqualTo(type)
                .andYearEqualTo(year).andNameEqualTo(name);
        if(type == SystemConstants.CES_RESULT_TYPE_UNIT){
            criteria.andUnitIdEqualTo(unitId);
        }else{
            // cadreId肯定不能为空
            criteria.andCadreIdEqualTo(cadreId);
        }

        List<CesResult> cesResults = cesResultMapper.selectByExampleWithRowbounds(example, new RowBounds(0, 1));

        return cesResults.size()>0?cesResults.get(0):null;
    }

    @Transactional
    public void insertSelective(CesResult record){

        cesResultMapper.insertSelective(record);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        CesResultExample example = new CesResultExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        cesResultMapper.deleteByExample(example);
    }

    @Transactional
    public void updateByPrimaryKeySelective(CesResult record){

        cesResultMapper.updateByPrimaryKeySelective(record);
    }

    @Transactional
    public int batchImport(byte type, List<CesResult> records) {

        int addCount = 0;
        for (CesResult record : records) {

            Integer unitId = record.getUnitId();
            Integer cadreId = record.getCadreId();
            int year = record.getYear();
            String name = record.getName();

            CesResult cesResult = get(type, unitId, cadreId, year, name);
            if(cesResult==null) {
                insertSelective(record);
                addCount++;
            }else{
                int id = cesResult.getId();
                record.setId(id);
                updateByPrimaryKeySelective(record);
            }
        }

        return addCount;
    }

    public void cesResult_export(byte type, CesResultExample example, HttpServletResponse response) {

        List<CesResult> records = cesResultMapper.selectByExample(example);
        List<String> titles = new ArrayList(Arrays.asList("年份|80"));
        if (type == 1) {
            titles.add("工作证号|100");
            titles.add("姓名|100");
        }
        titles.add(type == SystemConstants.CES_RESULT_TYPE_CADRE ? "时任单位|250" : "所在单位|250");
        titles.add(type == SystemConstants.CES_RESULT_TYPE_CADRE ?"时任职务|250" : "班子名称|250");
        titles.add("测评类别|200");
        titles.add("排名|100");
        titles.add(type == SystemConstants.CES_RESULT_TYPE_CADRE ? "总人数|100" : "班子总人数|100");
        titles.add("备注|200");

        int rownum = records.size();
        List<List<String>> valueList = new ArrayList<>();
        for (int i = 0; i< rownum; i++){

            CesResult record = records.get(i);
            List<String> values = new ArrayList<>();

            values.add(record.getYear() + "");
            if (type == 1) {
                SysUserView user = record.getCadre().getUser();
                values.add(user.getCode());
                values.add(user.getRealname());
            }
            values.add(record.getUnit().getName());
            values.add(record.getTitle());
            values.add(record.getName());
            values.add(record.getNum()+"");
            values.add(record.getRank()+"");
            values.add(record.getRemark());

            valueList.add(values);
        }

        String fileName = (type == 1 ? "干部" : "班子") + "年终考核结果.xlsx";
        ExportHelper.export(titles, valueList, fileName, response);
    }
}
