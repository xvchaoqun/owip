package service.cpc;

import domain.base.MetaType;
import domain.cadre.CadrePost;
import domain.cadre.CadreView;
import domain.cpc.CpcAllocation;
import domain.cpc.CpcAllocationExample;
import domain.unit.Unit;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;
import service.BaseMapper;
import service.base.MetaTypeService;
import service.cadre.CadrePostService;
import service.unit.UnitService;
import sys.constants.SystemConstants;
import sys.tags.CmTag;
import sys.utils.DateUtils;
import sys.utils.ExcelUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
    public XSSFWorkbook cpcInfo_Xlsx() throws IOException {

        InputStream is = new FileInputStream(ResourceUtils.getFile("classpath:xlsx/cpc/cpc_template.xlsx"));
        XSSFWorkbook wb = new XSSFWorkbook(is);
        XSSFSheet sheet = wb.getSheetAt(0);

        List<CpcInfoBean> beans = cpcInfo_data(null, true);

        XSSFRow row = sheet.getRow(0);
        XSSFCell cell = row.getCell(0);
        String str = cell.getStringCellValue()
                .replace("school", CmTag.getSysConfig().getSchoolName());
        cell.setCellValue(str);

        row = sheet.getRow(1);
        cell = row.getCell(0);
        cell.setCellValue("统计日期：" + DateUtils.formatDate(new Date(), DateUtils.YYYY_MM_DD_CHINA));

        int cpRow = 5;
        int rowCount = beans.size() - 1;
        if (rowCount > 1)
            ExcelUtils.insertRow(wb, sheet, cpRow, rowCount - 1);

        int startRow = cpRow;
        for (int i = 0; i < rowCount; i++) {

            CpcInfoBean bean = beans.get(i);

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

            CpcInfoBean totalBean = beans.get(rowCount);
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
     * 获取一个单位的配置情况
     *
     * @param unitId
     * @return  <adminLevelId,  num>
     */
    public Map<Integer, Integer> getCpcAdminLevelMap(int unitId){

        Map<Integer, Integer> resultMap = new HashMap<>();

        CpcAllocationExample example = new CpcAllocationExample();
        example.createCriteria().andUnitIdEqualTo(unitId);
        List<CpcAllocation> cpcAllocations = cpcAllocationMapper.selectByExample(example);
        for (CpcAllocation cpcAllocation : cpcAllocations) {

            Integer adminLevelId = cpcAllocation.getAdminLevelId();
            int num = cpcAllocation.getNum();

            resultMap.put(adminLevelId, num);
        }

        return resultMap;
    }
    /**
     * 获取已经设置了职数的单位
     *
     * @return <unitId, <adminLevelId, num>>
     */
    public Map<Integer, Map<Integer, Integer>> getUnitAdminLevelMap() {

        List<CpcAllocation> cpcAllocations = cpcAllocationMapper.selectByExample(new CpcAllocationExample());


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

        return _unitAdminLevelMap;
    }

    /**
     * 干部职数配置情况统计
     *
     * @return 最后一个bean是统计结果
     *
     * hasSetCpc = true 只读取设置了职数的单位
     */
    public List<CpcInfoBean> cpcInfo_data(Integer _unitId, boolean hasSetCpc) {


        Map<String, MetaType> metaTypeMap = metaTypeService.codeKeyMap();
        MetaType mainMetaType = metaTypeMap.get("mt_admin_level_main");
        MetaType viceMetaType = metaTypeMap.get("mt_admin_level_vice");
        MetaType noneMetaType = metaTypeMap.get("mt_admin_level_none");

        Map<Integer, Unit> _unitMap = unitService.findAll();
        Map<Integer, Unit> unitMap = new LinkedHashMap<>();
        if(_unitId!=null){
            unitMap.put(_unitId, _unitMap.get(_unitId));
        }else{
            unitMap.putAll(_unitMap);
        }

        List<CpcInfoBean> beans = new ArrayList<>();

        // 统计结果
        CpcInfoBean totalBean = new CpcInfoBean();
        totalBean.setMainCount(0);
        totalBean.setViceCount(0);
        totalBean.setNoneCount(0);

        totalBean.setMainNum(0);
        totalBean.setViceNum(0);
        totalBean.setNoneNum(0);

        totalBean.setMainLack(0);
        totalBean.setViceLack(0);
        totalBean.setNoneLack(0);

        Map<Integer, Map<Integer, Integer>> _unitAdminLevelMap = getUnitAdminLevelMap();

        for (Unit unit : unitMap.values()) {

            Integer unitId = unit.getId();
            if (unit.getStatus() == SystemConstants.UNIT_STATUS_RUN
                    && !(hasSetCpc && !_unitAdminLevelMap.containsKey(unitId))) {

                CpcInfoBean bean = new CpcInfoBean();
                bean.setUnit(unit);

                Integer mainNum = null;
                Integer viceNum = null;
                Integer noneNum = null;
                Map<Integer, Integer> _adminLevelMap = _unitAdminLevelMap.get(unitId);
                if(_adminLevelMap!=null) {
                    mainNum = _adminLevelMap.get(mainMetaType.getId());
                    viceNum = _adminLevelMap.get(viceMetaType.getId());
                    noneNum = _adminLevelMap.get(noneMetaType.getId());
                }

                // 查找主职、兼职在此单位的现任干部
                List<CadrePost> cadrePosts = iCadreMapper.findCadrePosts(unitId);

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

                /*bean.setMainLack(bean.getMainNum() > bean.getMainCount() ? bean.getMainNum() - bean.getMainCount() : 0);
                bean.setViceLack(bean.getViceNum() > bean.getViceCount() ? bean.getViceNum() - bean.getViceCount() : 0);
                bean.setNoneLack(bean.getNoneNum() > bean.getNoneCount() ? bean.getNoneNum() - bean.getNoneCount() : 0);*/

                bean.setMainLack(bean.getMainNum() - bean.getMainCount());
                bean.setViceLack(bean.getViceNum() - bean.getViceCount());
                bean.setNoneLack(bean.getNoneNum() - bean.getNoneCount());


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
    public void batchDel(Integer[] unitIds) {

        if (unitIds == null || unitIds.length == 0) return;

        CpcAllocationExample example = new CpcAllocationExample();
        example.createCriteria().andUnitIdIn(Arrays.asList(unitIds));
        cpcAllocationMapper.deleteByExample(example);
    }

    @Transactional
    public int updateByPrimaryKeySelective(CpcAllocation record) {
        return cpcAllocationMapper.updateByPrimaryKeySelective(record);
    }

    // 更新配置
    @Transactional
    public void update(List<CpcAllocation> records) {

        for (CpcAllocation record : records) {

            CpcAllocationExample example = new CpcAllocationExample();
            example.createCriteria().andUnitIdEqualTo(record.getUnitId()).andAdminLevelIdEqualTo(record.getAdminLevelId());
            cpcAllocationMapper.deleteByExample(example);

            cpcAllocationMapper.insertSelective(record);
        }
    }

    public XSSFWorkbook cpcStat_Xlsx() throws IOException {

        InputStream is = new FileInputStream(ResourceUtils.getFile("classpath:xlsx/cpc/cpc_stat_template.xlsx"));
        XSSFWorkbook wb = new XSSFWorkbook(is);
        XSSFSheet sheet = wb.getSheetAt(0);

        XSSFRow row = sheet.getRow(0);
        XSSFCell cell = row.getCell(0);
        String str = cell.getStringCellValue()
                .replace("school", CmTag.getSysConfig().getSchoolName());
        cell.setCellValue(str);

        row = sheet.getRow(1);
        cell = row.getCell(0);
        cell.setCellValue("统计日期：" + DateUtils.formatDate(new Date(), DateUtils.YYYY_MM_DD_CHINA));

        Map<String, List<Integer>> cpcStatDataMap = cpcStat_data();

        // 按表格行的顺序重新装入
        List<Integer> jgList = cpcStatDataMap.get(SystemConstants.UNIT_TYPE_ATTR_JG);
        List<Integer> xyList = cpcStatDataMap.get(SystemConstants.UNIT_TYPE_ATTR_XY);
        List<Integer> fsList = cpcStatDataMap.get(SystemConstants.UNIT_TYPE_ATTR_FS);
        List<Integer> totalList = cpcStatDataMap.get("total");

        List<List<Integer>> dataList = new ArrayList<>();
        dataList.add(jgList);
        dataList.add(xyList);
        dataList.add(fsList);
        dataList.add(totalList);

        int startRow = 5;
        for (List<Integer> list : dataList) {

            row = sheet.getRow(startRow++);
            int column = 2;
            for (Integer data : list) {

                cell = row.getCell(column++);
                cell.setCellValue(data);
            }
        }

        return wb;
    }

    /**
     *  机关、学院、附属单位、合计 4行数据
     *
     * @return <unitType, 表格的每行结果数据>
     *     汇总结果在最后一行(unitType='total')
      */
    public Map<String, List<Integer>> cpcStat_data() {

        Map<String, MetaType> metaTypeMap = metaTypeService.codeKeyMap();
        MetaType mainMetaType = metaTypeMap.get("mt_admin_level_main");
        MetaType viceMetaType = metaTypeMap.get("mt_admin_level_vice");
        MetaType noneMetaType = metaTypeMap.get("mt_admin_level_none");


        // 汇总结果
        int totalNum = 0;
        int totalMainCount = 0;
        int totalSubCount = 0;
        int totalLack = 0;

        int mainTotalNum = 0;
        int mainTotalMainCount = 0;
        int mainTotalSubCount = 0;
        int mainTotalLack = 0;

        int viceTotalNum = 0;
        int viceTotalMainCount = 0;
        int viceTotalSubCount = 0;
        int viceTotalLack = 0;

        int noneTotalNum = 0;
        int noneTotalMainCount = 0;
        int noneTotalSubCount = 0;
        int noneTotalLack = 0;


        // <unitType, 表格的每行结果数据>
        Map<String, List<Integer>> results = new HashMap<>();
        for (String unitType : SystemConstants.UNIT_TYPE_ATTR_MAP.keySet()) {

            List<Integer> dataList = new ArrayList<>();
            results.put(unitType, dataList);

            // =============统计设定的干部职数==============
            List<CpcStatBean> cpcStatBeans = iCpcMapper.cpcStat_setting(unitType);

            int mainNum = 0; // 正处
            int viceNum = 0;  // 副处
            int noneNum = 0;  // 无行政级别
            for (CpcStatBean bean : cpcStatBeans) {

                Integer adminLevelId = bean.getAdminLevelId();
                int num = (int)bean.getNum();

                if (adminLevelId.intValue() == mainMetaType.getId()) {
                    mainNum = num;
                }else if (adminLevelId.intValue() == viceMetaType.getId()) {
                    viceNum = num;
                }else if (adminLevelId.intValue() == noneMetaType.getId()) {
                    noneNum = num;
                }
            }

            // ===============统计实际的干部职数==============
            // 正处
            int mainCount = 0; // 全职
            int subCount = 0;  // 兼职

            // 副处
            int mainCount2 = 0; // 全职
            int subCount2 = 0;  // 兼职

            // 无行政级别
            int mainCount3 = 0; // 全职
            int subCount3 = 0;  // 兼职
            List<CpcStatBean> cpcStats = iCpcMapper.cpcStat_real(unitType);
            for (CpcStatBean bean : cpcStats) {

                Integer adminLevelId = bean.getAdminLevelId();
                boolean mainPost = bean.isMainPost();
                int num = (int)bean.getNum();



                if (adminLevelId.intValue() == mainMetaType.getId()) {
                    if (mainPost) mainCount = num;
                    else subCount = num;
                }else if (adminLevelId.intValue() == viceMetaType.getId()) {
                    if (mainPost) mainCount2 = num;
                    else subCount2 = num;
                }else if (adminLevelId.intValue() == noneMetaType.getId()) {
                    if (mainPost) mainCount3 = num;
                    else subCount3 = num;
                }
            }

            // 所有岗位
            int _totalNum = mainNum + viceNum + noneNum;
            int _totalMainCount = mainCount+mainCount2+mainCount3;
            int _totalSubCount = subCount+subCount2+subCount3;
            int _totalLack = mainNum+viceNum+noneNum - (mainCount+mainCount2+mainCount3 + subCount+subCount2+subCount3);
            dataList.add(_totalNum);
            dataList.add(_totalMainCount);
            dataList.add(_totalSubCount);
            dataList.add(_totalLack); // 空缺数

            totalNum += _totalNum;
            totalMainCount += _totalMainCount;
            totalSubCount += _totalSubCount;
            totalLack += _totalLack;


            // 正处级岗位
            int _mainLack = mainNum - (mainCount + subCount);
            dataList.add(mainNum);
            dataList.add(mainCount);
            dataList.add(subCount);
            dataList.add(_mainLack); // 空缺数

            mainTotalNum += mainNum;
            mainTotalMainCount += mainCount;
            mainTotalSubCount += subCount;
            mainTotalLack += _mainLack;


            // 副处级岗位
            int _viceLack = viceNum - (mainCount2 + subCount2);
            dataList.add(viceNum);
            dataList.add(mainCount2);
            dataList.add(subCount2);
            dataList.add(_viceLack); // 空缺数

            viceTotalNum += viceNum;
            viceTotalMainCount += mainCount2;
            viceTotalSubCount += subCount2;
            viceTotalLack += _viceLack;


            // 无行政级别岗位
            int _noneLack = noneNum - (mainCount3 + subCount3);
            dataList.add(noneNum);
            dataList.add(mainCount3);
            dataList.add(subCount3);
            dataList.add(_noneLack); // 空缺数

            noneTotalNum += noneNum;
            noneTotalMainCount += mainCount3;
            noneTotalSubCount += subCount3;
            noneTotalLack += _noneLack;
        }
        // 汇总结果
        List<Integer> totalList = new ArrayList<>();
        totalList.add(totalNum);
        totalList.add(totalMainCount);
        totalList.add(totalSubCount);
        totalList.add(totalLack);

        totalList.add(mainTotalNum);
        totalList.add(mainTotalMainCount);
        totalList.add(mainTotalSubCount);
        totalList.add(mainTotalLack);

        totalList.add(viceTotalNum);
        totalList.add(viceTotalMainCount);
        totalList.add(viceTotalSubCount);
        totalList.add(viceTotalLack);

        totalList.add(noneTotalNum);
        totalList.add(noneTotalMainCount);
        totalList.add(noneTotalSubCount);
        totalList.add(noneTotalLack);

        results.put("total", totalList);

        return results;
    }
}
