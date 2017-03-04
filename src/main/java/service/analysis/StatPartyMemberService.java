package service.analysis;

import domain.base.MetaType;
import domain.party.Party;
import domain.party.PartyMemberGroup;
import domain.party.PartyMemberView;
import domain.party.PartyMemberViewExample;
import domain.sys.SysUserView;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.BaseMapper;
import service.base.MetaTypeService;
import service.party.PartyService;
import sys.utils.DateUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class StatPartyMemberService extends BaseMapper {

    @Autowired
    private PartyService partyService;
    @Autowired
    private MetaTypeService metaTypeService;

    // 导出
    public XSSFWorkbook toXlsx(int groupId) throws IOException {

        InputStream is = getClass().getResourceAsStream("/xlsx/party_member_template.xlsx");
        XSSFWorkbook wb=new XSSFWorkbook(is);

        renderSheetData(wb, groupId);

        return wb;
    }

    private void renderSheetData(XSSFWorkbook wb, int groupId)  {

        PartyMemberGroup partyMemberGroup = partyMemberGroupMapper.selectByPrimaryKey(groupId);
        Party party = partyService.findAll().get(partyMemberGroup.getPartyId());

        Sheet sheet = wb.getSheetAt(0);

        { // 第二行
            Row titleRow = sheet.getRow(1);
            Cell cell = titleRow.getCell(0);
            cell.setCellValue("单位名称：" + party.getName());
        }

        {// 从第四行开始填充数据
            PartyMemberViewExample example = new PartyMemberViewExample();
            example.createCriteria().andGroupIdEqualTo(groupId);
            example.setOrderByClause("sort_order desc");
            List<PartyMemberView> partyMemberViews = partyMemberViewMapper.selectByExample(example);
            Map<Integer, MetaType> metaTypeMap = metaTypeService.findAll();
            int rowNum = 3;
            for (PartyMemberView record : partyMemberViews) {

                SysUserView user = record.getUser();
                List<String> typeNames = new ArrayList();
                String[] _typeIds = StringUtils.split(record.getTypeIds(), ",");
                if(_typeIds!=null && _typeIds.length>0){
                    for (String typeId : _typeIds) {
                        MetaType metaType = metaTypeMap.get(Integer.valueOf(typeId));
                        if(metaType!=null) typeNames.add(metaType.getName());
                    }
                }

                int colunmNum = 0;
                Row row = sheet.getRow(rowNum);
                // 序号
                Cell cell = row.getCell(colunmNum++);
                cell.setCellValue(rowNum-2);
                // 职务
                cell = row.getCell(colunmNum++);
                cell.setCellValue(metaTypeService.getName(record.getPostId()));
                // 姓名
                cell = row.getCell(colunmNum++);
                cell.setCellValue(user.getRealname());
                // 工作证号
                cell = row.getCell(colunmNum++);
                cell.setCellValue(user.getCode());
                // 分工
                cell = row.getCell(colunmNum++);
                cell.setCellValue(StringUtils.join(typeNames, ","));
                // 任职时间
                cell = row.getCell(colunmNum++);
                cell.setCellValue(DateUtils.formatDate(record.getAssignDate(), "yyyy.MM"));
                // 办公电话
                cell = row.getCell(colunmNum++);
                cell.setCellValue(record.getOfficePhone());
                // 手机号
                cell = row.getCell(colunmNum++);
                cell.setCellValue(record.getMobile());

                rowNum++;

                if(rowNum > 12) break; // excel表中最多填充10个委员
            }

        }

        { // 第14行
            Row titleRow = sheet.getRow(13);
            Cell cell = titleRow.getCell(0);
            cell.setCellValue("统计日期：" + DateUtils.formatDate(new Date(), DateUtils.YYYY_MM_DD_CHINA));
        }
    }
}
