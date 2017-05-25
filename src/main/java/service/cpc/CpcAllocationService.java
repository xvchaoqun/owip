package service.cpc;

import domain.base.MetaType;
import domain.cadre.CadrePost;
import domain.cadre.CadreView;
import domain.cpc.CpcAllocation;
import domain.cpc.CpcAllocationExample;
import domain.unit.Unit;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;
import service.BaseMapper;
import service.base.MetaTypeService;
import service.cadre.CadrePostService;
import service.unit.UnitService;
import sys.constants.SystemConstants;
import sys.utils.DateUtils;
import sys.utils.ExcelUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@Service
public class CpcAllocationService extends BaseMapper {
    @Autowired
    protected MetaTypeService metaTypeService;
    @Autowired
    protected UnitService unitService;
    @Autowired
    protected CadrePostService cadrePostService;

    private XSSFRichTextString getCadres(XSSFWorkbook wb, List<CadrePost> cadrePosts) {

        List<String> realnames = new ArrayList<>();
        List<String> isCpcPosList = new ArrayList<>();
        List<String> notCpcPosList = new ArrayList<>();

        for (CadrePost cadrePost : cadrePosts) {

            CadreView cadre = cadrePost.getCadre();
            String realname = cadre.getRealname();

            int pos = (realnames.size() == 0) ? 0 : StringUtils.join(realnames, "、").length() + 1;

            if (cadrePost.getIsMainPost()) {
                // 主职
                realnames.add(realname);
            } else if (cadrePost.getIsCpc()) {
                // 副职、占职数
                realnames.add("（" + realname + "）");
                isCpcPosList.add(pos + "," + (pos + realname.length() + 2));
            } else {
                // 副职、不占职数
                realnames.add("（" + realname + "）");
                notCpcPosList.add(pos + "," + (pos + realname.length() + 2));
            }
        }

        XSSFFont isCpcFont = wb.createFont();
        //isCpcFont.setFontHeightInPoints((short) 24); // 字体高度
        //isCpcFont.setFontName("宋体"); // 字体
        isCpcFont.setColor(IndexedColors.GREEN.getIndex());
        //isCpcFont.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);

        XSSFFont notCpcFont = wb.createFont();
        //notCpcFont.setFontHeightInPoints((short) 24); // 字体高度
        //notCpcFont.setFontName("宋体"); // 字体
        notCpcFont.setColor(IndexedColors.RED.getIndex());
        //notCpcFont.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);

        XSSFRichTextString ts = new XSSFRichTextString(StringUtils.join(realnames, "、"));
        for (String pos : isCpcPosList) {
            String[] split = pos.split(",");
            ts.applyFont(Integer.valueOf(split[0]), Integer.valueOf(split[1]), isCpcFont);
        }
        for (String pos : notCpcPosList) {
            String[] split = pos.split(",");
            ts.applyFont(Integer.valueOf(split[0]), Integer.valueOf(split[1]), notCpcFont);
        }

        return ts;
    }

    // 导出
    public XSSFWorkbook toXlsx() throws IOException {

        InputStream is = new FileInputStream(ResourceUtils.getFile("classpath:xlsx/cpc_template.xlsx"));
        XSSFWorkbook wb = new XSSFWorkbook(is);
        XSSFSheet sheet = wb.getSheetAt(0);

        List<CpcAllocationBean> beans = statCpc();

        XSSFRow row = sheet.getRow(1);
        XSSFCell cell = row.getCell(0);
        cell.setCellValue("统计日期：" + DateUtils.formatDate(new Date(), DateUtils.YYYY_MM_DD_CHINA));

        int cpRow = 5;
        int rowCount = beans.size() - 1;
        ExcelUtils.insertRow(wb, sheet, cpRow, rowCount - 1);


        int startRow = cpRow;
        for (int i = 0; i < rowCount; i++) {

            CpcAllocationBean bean = beans.get(i);

            int column = 0;
            row = sheet.getRow(startRow++);
            // 序号
            cell = row.getCell(column++);
            cell.setCellValue(i + 1);

            // 单位
            cell = row.getCell(column++);
            cell.setCellValue(bean.getUnit().getName());

            // 正处级 职数
            cell = row.getCell(column++);
            cell.setCellValue(bean.getMainNum());

            // 正处级 现任数
            cell = row.getCell(column++);
            cell.setCellValue(bean.getMainCount());

            // 正处级 现任干部
            cell = row.getCell(column++);
            cell.setCellValue(getCadres(wb, bean.getMains()));

            // 正处级 空缺数
            cell = row.getCell(column++);
            cell.setCellValue(bean.getMainLack());


            // 副处级 职数
            cell = row.getCell(column++);
            cell.setCellValue(bean.getViceNum());

            // 副处级 现任数
            cell = row.getCell(column++);
            cell.setCellValue(bean.getViceCount());

            // 副处级 现任干部
            cell = row.getCell(column++);
            cell.setCellValue(getCadres(wb, bean.getVices()));

            // 副处级 空缺数
            cell = row.getCell(column++);
            cell.setCellValue(bean.getViceLack());


            // 无行政级别 职数
            cell = row.getCell(column++);
            cell.setCellValue(bean.getNoneNum());

            // 无行政级别 现任数
            cell = row.getCell(column++);
            cell.setCellValue(bean.getNoneCount());

            // 无行政级别 现任干部
            cell = row.getCell(column++);
            cell.setCellValue(getCadres(wb, bean.getNones()));

            // 无行政级别 空缺数
            cell = row.getCell(column++);
            cell.setCellValue(bean.getNoneLack());
        }

        // 统计结果
        if (rowCount > 0) {

            CpcAllocationBean totalBean = beans.get(rowCount);
            row = sheet.getRow(startRow);
            int column = 2;
            // 正处级 职数
            cell = row.getCell(column++);
            cell.setCellValue(totalBean.getMainNum());

            // 正处级 现任数
            cell = row.getCell(column++);
            cell.setCellValue(totalBean.getMainCount());

            column++;

            // 正处级 空缺数
            cell = row.getCell(column++);
            cell.setCellValue(totalBean.getMainLack());

            // 副处级 职数
            cell = row.getCell(column++);
            cell.setCellValue(totalBean.getViceNum());

            // 副处级 现任数
            cell = row.getCell(column++);
            cell.setCellValue(totalBean.getViceCount());

            column++;

            // 副处级 空缺数
            cell = row.getCell(column++);
            cell.setCellValue(totalBean.getViceLack());


            // 无行政级别 职数
            cell = row.getCell(column++);
            cell.setCellValue(totalBean.getNoneNum());

            // 无行政级别 现任数
            cell = row.getCell(column++);
            cell.setCellValue(totalBean.getNoneCount());

            column++;

            // 无行政级别 空缺数
            cell = row.getCell(column++);
            cell.setCellValue(totalBean.getNoneLack());
        }

        return wb;
    }

    /**
     * 干部职数配置情况统计
     *
     * @return 最后一个bean是统计结果
     */
    public List<CpcAllocationBean> statCpc() {


        Map<String, MetaType> metaTypeMap = metaTypeService.codeKeyMap();
        MetaType mainMetaType = metaTypeMap.get("mt_admin_level_main");
        MetaType viceMetaType = metaTypeMap.get("mt_admin_level_vice");
        MetaType noneMetaType = metaTypeMap.get("mt_admin_level_none");

        Map<Integer, Unit> unitMap = unitService.findAll();
        List<CpcAllocation> cpcAllocations = cpcAllocationMapper.selectByExample(new CpcAllocationExample());

        // <unitId, <adminLevelId, num>>
        Map<Integer, Map<Integer, Integer>> _unitAdminLevelMap = new HashMap<>();
        for (CpcAllocation cpcAllocation : cpcAllocations) {

            Integer unitId = cpcAllocation.getUnitId();
            Integer adminLevelId = cpcAllocation.getAdminLevelId();
            int num = cpcAllocation.getNum();

            Map<Integer, Integer> _adminLevelMap = _unitAdminLevelMap.get(unitId);
            if (_adminLevelMap == null) _adminLevelMap = new HashMap<Integer, Integer>();
            _adminLevelMap.put(adminLevelId, num);

            _unitAdminLevelMap.put(unitId, _adminLevelMap);
        }

        List<CpcAllocationBean> beans = new ArrayList<>();

        // 统计结果
        CpcAllocationBean totalBean = new CpcAllocationBean();
        totalBean.setMainCount(0);
        totalBean.setViceCount(0);
        totalBean.setNoneCount(0);

        totalBean.setMainNum(0);
        totalBean.setViceNum(0);
        totalBean.setNoneNum(0);

        totalBean.setMainLack(0);
        totalBean.setViceLack(0);
        totalBean.setNoneLack(0);

        for (Unit unit : unitMap.values()) {

            Integer unitId = unit.getId();
            if (unit.getStatus() == SystemConstants.UNIT_STATUS_RUN
                    && (_unitAdminLevelMap.containsKey(unitId))) {

                CpcAllocationBean bean = new CpcAllocationBean();
                bean.setUnit(unit);

                Map<Integer, Integer> _adminLevelMap = _unitAdminLevelMap.get(unitId);
                Integer mainNum = _adminLevelMap.get(mainMetaType.getId());
                Integer viceNum = _adminLevelMap.get(viceMetaType.getId());
                Integer noneNum = _adminLevelMap.get(noneMetaType.getId());

                // 查找主职在此单位的干部、兼职在此单位的干部
                List<CadrePost> cadrePosts = cadrePostService.findByUnitId(unitId);

                List<CadrePost> mains = new ArrayList<>();
                List<CadrePost> vices = new ArrayList<>();
                List<CadrePost> nones = new ArrayList<>();
                int mainCount = 0;
                int viceCount = 0;
                int noneCount = 0;
                for (CadrePost cadrePost : cadrePosts) {

                    if (cadrePost.getAdminLevelId() == null) continue;

                    if (cadrePost.getAdminLevelId().intValue() == mainMetaType.getId()) {
                        mains.add(cadrePost);

                        if (cadrePost.getIsMainPost() || cadrePost.getIsCpc()) {
                            // 主职或者副职占职数，就计数
                            mainCount++;
                        }
                    }
                    if (cadrePost.getAdminLevelId().intValue() == viceMetaType.getId()) {
                        vices.add(cadrePost);
                        if (cadrePost.getIsMainPost() || cadrePost.getIsCpc()) {
                            // 主职或者副职占职数，就计数
                            viceCount++;
                        }
                    }
                    if (cadrePost.getAdminLevelId().intValue() == noneMetaType.getId()) {
                        nones.add(cadrePost);
                        if (cadrePost.getIsMainPost() || cadrePost.getIsCpc()) {
                            // 主职或者副职占职数，就计数
                            noneCount++;
                        }
                    }
                }

                bean.setMains(mains);
                bean.setVices(vices);
                bean.setNones(nones);

                bean.setMainCount(mainCount);
                bean.setViceCount(viceCount);
                bean.setNoneCount(noneCount);

                bean.setMainNum(mainNum == null ? 0 : mainNum);
                bean.setViceNum(viceNum == null ? 0 : viceNum);
                bean.setNoneNum(noneNum == null ? 0 : noneNum);

                bean.setMainLack(bean.getMainNum() > bean.getMainCount() ? bean.getMainNum() - bean.getMainCount() : 0);
                bean.setViceLack(bean.getViceNum() > bean.getViceCount() ? bean.getViceNum() - bean.getViceCount() : 0);
                bean.setNoneLack(bean.getNoneNum() > bean.getNoneCount() ? bean.getNoneNum() - bean.getNoneCount() : 0);


                totalBean.setMainCount(totalBean.getMainCount() + bean.getMainCount());
                totalBean.setViceCount(totalBean.getViceCount() + bean.getViceCount());
                totalBean.setNoneCount(totalBean.getNoneCount() + bean.getNoneCount());

                totalBean.setMainNum(totalBean.getMainNum() + bean.getMainNum());
                totalBean.setViceNum(totalBean.getViceNum() + bean.getViceNum());
                totalBean.setNoneNum(totalBean.getNoneNum() + bean.getNoneNum());

                totalBean.setMainLack(totalBean.getMainLack() + bean.getMainLack());
                totalBean.setViceLack(totalBean.getViceLack() + bean.getViceLack());
                totalBean.setNoneLack(totalBean.getNoneLack() + bean.getNoneLack());

                beans.add(bean);
            }
        }

        beans.add(totalBean);

        return beans;
    }

    @Transactional
    public void insertSelective(CpcAllocation record) {

        cpcAllocationMapper.insertSelective(record);
    }

    @Transactional
    public void del(Integer id) {

        cpcAllocationMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids) {

        if (ids == null || ids.length == 0) return;

        CpcAllocationExample example = new CpcAllocationExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        cpcAllocationMapper.deleteByExample(example);
    }

    @Transactional
    public int updateByPrimaryKeySelective(CpcAllocation record) {
        return cpcAllocationMapper.updateByPrimaryKeySelective(record);
    }

    // 更新配置
    public void update(List<CpcAllocation> records) {

        for (CpcAllocation record : records) {

            CpcAllocationExample example = new CpcAllocationExample();
            example.createCriteria().andUnitIdEqualTo(record.getUnitId()).andAdminLevelIdEqualTo(record.getAdminLevelId());
            cpcAllocationMapper.deleteByExample(example);

            cpcAllocationMapper.insertSelective(record);
        }
    }
}
