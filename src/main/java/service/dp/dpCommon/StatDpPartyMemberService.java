package service.dp.dpCommon;

import domain.base.MetaType;
import domain.dp.DpParty;
import domain.dp.DpPartyMember;
import domain.dp.DpPartyMemberExample;
import domain.dp.DpPartyMemberGroup;
import domain.sys.SysUserView;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.base.MetaTypeService;
import service.dp.DpBaseMapper;
import service.dp.DpPartyService;
import sys.utils.DateUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class StatDpPartyMemberService extends DpBaseMapper {

    @Autowired
    private DpPartyService dpPartyService;
    @Autowired
    private MetaTypeService metaTypeService;


    public XSSFWorkbook toXlsx(int groupId) throws IOException {

        InputStream is = getClass().getResourceAsStream("/xlsx/dp/dp_party_member_template.xlsx");
        XSSFWorkbook wb = new XSSFWorkbook(is);

        renderSheetData(wb,groupId);

        return wb;
    }

    private void renderSheetData(XSSFWorkbook wb, int groupId){

        DpPartyMemberGroup dpPartyMemberGroup = dpPartyMemberGroupMapper.selectByPrimaryKey(groupId);
        DpParty dpParty = dpPartyService.findAll().get(dpPartyMemberGroup.getPartyId());

        Sheet sheet = wb.getSheetAt(0);
        {//第二行
            Row titleRow = sheet.getRow(1);
            Cell cell = titleRow.getCell(0);
            cell.setCellValue("单位名称：" + dpParty.getName());
        }

        {//从第四行开始填充数据
            DpPartyMemberExample example = new DpPartyMemberExample();
            example.createCriteria().andGroupIdEqualTo(groupId);
            example.setOrderByClause("sort_order desc");
            List<DpPartyMember> dpPartyMemberViews = dpPartyMemberMapper.selectByExample(example);
            Map<Integer, MetaType> metaTypeMap = metaTypeService.findAll();
            int rowNum = 3;
            for (DpPartyMember record : dpPartyMemberViews){
                SysUserView user = record.getUser();
                List<String> typeNames = new ArrayList<>();
                String[] typeIds = StringUtils.split(record.getTypeIds(),",");
                if (typeIds != null && typeIds.length > 0){
                    for (String typeId : typeIds){
                        MetaType metaType = metaTypeMap.get(Integer.valueOf(typeId));
                        if (metaType != null) typeNames.add(metaType.getName());
                    }
                }

                int colunmNum = 0;
                Row row = sheet.getRow(rowNum);
                //序号
                Cell cell = row.getCell(colunmNum++);
                cell.setCellValue(rowNum-2);
                //职务
                cell = row.getCell(colunmNum++);
                cell.setCellValue(metaTypeService.getName(record.getPostId()));
                //姓名
                cell = row.getCell(colunmNum++);
                cell.setCellValue(user.getRealname());
                //工作证号
                cell = row.getCell(colunmNum++);
                cell.setCellValue(user.getCode());
                //分工
                cell = row.getCell(colunmNum++);
                cell.setCellValue(StringUtils.join(typeNames,","));
                //任职时间
                cell = row.getCell(colunmNum++);
                cell.setCellValue(DateUtils.formatDate(record.getAssignDate(),DateUtils.YYYYMM));
                //办公电话
                cell = row.getCell(colunmNum++);
                cell.setCellValue(record.getOfficePhone());
                //手机号
                cell = row.getCell(colunmNum++);
                cell.setCellValue(record.getMobile());

                rowNum++;

                if (rowNum > 12) break; //excel表中最多填充10个委员

             }
        }
        {
            //第14行
            Row titleRow = sheet.getRow(13);
            Cell cell = titleRow.getCell(0);
            cell.setCellValue("统计日期：" + DateUtils.formatDate(new Date(), DateUtils.YYYY_MM_DD_CHINA));
        }

    }

}
