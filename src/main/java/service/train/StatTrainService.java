package service.train;

import bean.analysis.StatTrainBean;
import domain.train.TrainCourse;
import domain.train.TrainEvaNorm;
import domain.train.TrainEvaRank;
import domain.train.TrainEvaResult;
import domain.train.TrainEvaResultExample;
import domain.train.TrainEvaTable;
import domain.train.TrainInspectorCourse;
import domain.train.TrainInspectorCourseExample;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.BaseMapper;
import sys.Utils;
import sys.constants.TrainConstants;
import sys.tool.xlsx.ExcelTool;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by fafa on 2017/3/2.
 */
@Service
public class StatTrainService extends BaseMapper {

    @Autowired
    private TrainEvaRankService trainEvaRankService;
    @Autowired
    private TrainCourseService trainCourseService;

    public XSSFWorkbook toXlsx(int trainId) throws IOException {

        XSSFWorkbook wb = new XSSFWorkbook();

        createTotalSheet(wb, trainId);

        Map<Integer, TrainCourse> trainCourseMap = trainCourseService.findAll(trainId);
        for (TrainCourse trainCourse : trainCourseMap.values()) {
            createCourseSheet(wb, trainCourse);
        }

        return wb;
    }

    public void createTotalSheet(XSSFWorkbook wb, int trainId) {

        Sheet sheet = wb.createSheet("汇总");
        //sheet.setDefaultColumnWidth(10*256); 有问题
        int rowNum = 0;

        {
            // 第一行
            int column = 0;
            Row row = sheet.createRow(rowNum);
            sheet.setColumnWidth(column, (short) (35.7 * 120));
            createCell(wb, row, getThStyle(wb), column++, "授课教师");
            sheet.setColumnWidth(column, (short) (35.7 * 300));
            createCell(wb, row, getThStyle(wb), column++, "平均得分");
            sheet.setColumnWidth(column, (short) (35.7 * 600));
            XSSFCellStyle thStyle = getThStyle(wb);
            thStyle.setAlignment(HorizontalAlignment.LEFT);
            createCell(wb, row, thStyle, column++, "意见建议和评价");
            rowNum++;
        }

        Map<Integer, TrainCourse> trainCourseMap = trainCourseService.findAll(trainId);

        Map<String, Object> statTrainMap = statTrain(trainId);
        Map<Integer, Double> courseScoreMap = (Map<Integer, Double>) statTrainMap.get("courseScoreMap");
        Map<Integer, List<String>> courseFeedbackMap = (Map<Integer, List<String>>) statTrainMap.get("courseFeedbackMap");

        for (TrainCourse trainCourse : trainCourseMap.values()) {

            List<String> feedbackList = courseFeedbackMap.get(trainCourse.getId());
            String feedbacks = "";
            if (feedbackList != null) {
                int size = feedbackList.size();
                if (feedbackList != null && size > 0) {
                    for (int i = 0; i < size; i++) {
                        feedbacks += (i + 1) + "、" + feedbackList.get(i);
                        if (i != size - 1) feedbacks += "\r\n";
                    }
                }
            }
            Row row = sheet.createRow(rowNum);
            createCell(wb, row, getBodyStyle(wb), 0,
                    BooleanUtils.isTrue(trainCourse.getIsGlobal()) ?
                            trainCourse.getName() : trainCourse.getTeacher());

            Double courseScore = courseScoreMap.get(trainCourse.getId());
            if(courseScore!=null)
                createNumCell(wb, row, getBodyStyle(wb), 1, courseScore);
            else
                createCell(wb, row, getBodyStyle(wb), 1, "");
            XSSFCellStyle bodyStyle = getBodyStyle(wb);
            bodyStyle.setAlignment(HorizontalAlignment.LEFT);
            createCell(wb, row, bodyStyle, 2, feedbacks);
            rowNum++;
        }
    }

    public void createCourseSheet(XSSFWorkbook wb, TrainCourse trainCourse) {

        Map<String, Object> statCourseMap = statCourse(trainCourse.getId());

        Map<Integer, Double> inspectorTotalScoreMap = (Map<Integer, Double>) statCourseMap.get("inspectorTotalScoreMap");
        TrainEvaTable trainEvaTable = (TrainEvaTable) statCourseMap.get("trainEvaTable");
        Map<Integer, List<Double>> normScoresMap = (Map<Integer, List<Double>>) statCourseMap.get("normScoresMap");
        Map<Integer, Double> normTotalScoreMap = (Map<Integer, Double>) statCourseMap.get("normTotalScoreMap");
        Map<Integer, Double> topNormTotalScoreMap = (Map<Integer, Double>) statCourseMap.get("topNormTotalScoreMap");
        Map<Integer, Integer> normMinScoreMap = (Map<Integer, Integer>) statCourseMap.get("normMinScoreMap");
        Map<Integer, String> feedbackMap = (Map<Integer, String>) statCourseMap.get("feedbackMap");
        Double score = (Double) statCourseMap.get("score");

        int inspectorCount = inspectorTotalScoreMap.size();
        Sheet sheet = wb.createSheet(trainCourse.getIsGlobal() ? trainCourse.getName() : trainCourse.getTeacher());
        //sheet.setDefaultColumnWidth(10*256);

        sheet.setColumnWidth(0, (short) (35.7 * 100));
        sheet.setColumnWidth(1, (short) (35.7 * 200));

        int rowNum = 0;
        {   // 第一行
            Row row = sheet.createRow(rowNum);
            int columnNum = 0;
            XSSFCellStyle thStyle = getThStyle(wb);
            createCell(wb, row, thStyle, columnNum, "序号");

            CellRangeAddress cellRangeAddress = ExcelTool.getCellRangeAddress(rowNum, columnNum, rowNum, columnNum + 1);
            sheet.addMergedRegion(cellRangeAddress);
            setRegionBorder(sheet, cellRangeAddress, wb);

            columnNum = columnNum + 2;
            for (int i = 0; i < inspectorCount; i++) {
                createNumCell(wb, row, getThStyle(wb), columnNum++, Double.valueOf(i + 1));
            }

            createCell(wb, row, getThStyle(wb), columnNum, "各项平均");
            cellRangeAddress = ExcelTool.getCellRangeAddress(rowNum, columnNum, rowNum, columnNum + 1);
            sheet.addMergedRegion(cellRangeAddress);
            setRegionBorder(sheet, cellRangeAddress, wb);
            columnNum = columnNum + 2;

            sheet.setColumnWidth(columnNum, (short) (35.7 * 120));

            thStyle = getThStyle(wb);
            thStyle.getFont().setColor(IndexedColors.RED.index);
            createCell(wb, row, thStyle, columnNum, "各项最小值");

            rowNum++;
        }

        {
            // 指标行
            for (TrainEvaNorm topNorm : trainEvaTable.getNorms()) {

                int columnNum = 0;
                List<TrainEvaNorm> subNorms = topNorm.getSubNorms();
                int subNormNum = 0;
                if (subNorms != null) subNormNum = subNorms.size();

                Row row = sheet.createRow(rowNum);
                XSSFCellStyle bodyStyle = getBodyStyle(wb);
                createCell(wb, row, getThStyle(wb), columnNum, topNorm.getName());
                CellRangeAddress cellRangeAddress = ExcelTool.getCellRangeAddress(rowNum, columnNum,
                        rowNum + subNormNum - 1, columnNum + (subNormNum == 0 ? 1 : 0));
                if(subNormNum>1)
                    sheet.addMergedRegion(cellRangeAddress);
                setRegionBorder(sheet, cellRangeAddress, wb);
                columnNum = columnNum + (subNormNum == 0 ? 1 : 0) + 1;

                Integer topNormId = topNorm.getId();
                if (subNormNum == 0) {
                    List<Double> normScores = normScoresMap.get(topNormId);
                    for (int j = 0; j < inspectorCount; j++) {
                        createNumCell(wb, row, bodyStyle, columnNum++, normScores.get(j));
                    }
                    Double topNormTotalScore = topNormTotalScoreMap.get(topNormId);
                    if(topNormTotalScore!=null && inspectorCount>0)
                        createNumCell(wb, row, bodyStyle, columnNum, topNormTotalScore / inspectorCount);
                    else
                        createCell(wb, row, bodyStyle, columnNum, "");
                    cellRangeAddress = ExcelTool.getCellRangeAddress(rowNum, columnNum, rowNum, columnNum + 1);
                    sheet.addMergedRegion(cellRangeAddress);
                    setRegionBorder(sheet, cellRangeAddress, wb);
                    columnNum = columnNum + 2;

                    Integer normMinScore = normMinScoreMap.get(topNormId);
                    createNumCell(wb, row, bodyStyle, columnNum++, normMinScore==null?null:Double.valueOf(normMinScore));
                } else {

                    for (int i = 0; i < subNormNum; i++) {

                        int _columnNum = columnNum;
                        TrainEvaNorm subNorm = subNorms.get(i);
                        if (i == 0) {
                            bodyStyle = getThStyle(wb);
                            bodyStyle.setAlignment(HorizontalAlignment.LEFT);
                            createCell(wb, row, bodyStyle, _columnNum, subNorm.getName());
                        } else {
                            row = sheet.createRow(rowNum);
                            bodyStyle = getThStyle(wb);
                            bodyStyle.setAlignment(HorizontalAlignment.LEFT);
                            createCell(wb, row, bodyStyle, _columnNum, subNorm.getName());
                        }

                        _columnNum++;

                        Integer subNormId = subNorm.getId();
                        List<Double> normScores = normScoresMap.get(subNormId);
                        for (int j = 0; j < inspectorCount; j++) {
                            createNumCell(wb, row, bodyStyle, _columnNum++, normScores.get(j));
                        }
                        Double normTotalScore = normTotalScoreMap.get(subNormId);
                        if(normTotalScore!=null && inspectorCount>0)
                            createNumCell(wb, row, getThStyle(wb), _columnNum++, normTotalScore / inspectorCount);
                        else
                            createCell(wb, row, getThStyle(wb), _columnNum++, "");
                        if (i == 0) {
                            Double topNormTotalScore = topNormTotalScoreMap.get(topNormId);
                            if(topNormTotalScore!=null && inspectorCount>0)
                                createNumCell(wb, row, getThStyle(wb), _columnNum, topNormTotalScore / inspectorCount);
                            else
                                createCell(wb, row, getThStyle(wb), _columnNum, "");

                            cellRangeAddress = ExcelTool.getCellRangeAddress(rowNum, _columnNum,
                                    rowNum + subNormNum - 1, _columnNum);
                            if(subNormNum>1)
                                sheet.addMergedRegion(cellRangeAddress);
                            setRegionBorder(sheet, cellRangeAddress, wb);
                        }
                        _columnNum++;

                        Integer normMinScore = normMinScoreMap.get(subNormId);
                        bodyStyle = getThStyle(wb);
                        bodyStyle.getFont().setColor(IndexedColors.RED.index);
                        createNumCell(wb, row, bodyStyle, _columnNum++, normMinScore==null?null:Double.valueOf(normMinScore));

                        rowNum++;
                    }
                }

            }
        }

        {
            // 意见建议
            int columnNum = 0;
            Row row = sheet.createRow(rowNum);
            createCell(wb, row, getThStyle(wb), columnNum, "意见建议");
            CellRangeAddress cellRangeAddress = ExcelTool.getCellRangeAddress(rowNum, columnNum, rowNum, columnNum + 1);
            sheet.addMergedRegion(cellRangeAddress);
            setRegionBorder(sheet, cellRangeAddress, wb);
            columnNum = columnNum + 2;

            for (Map.Entry<Integer, Double> entry : inspectorTotalScoreMap.entrySet()) {

                int inspectorId = entry.getKey();
                String feedback = feedbackMap.get(inspectorId);
                createCell(wb, row, getBodyStyle(wb), columnNum++, StringUtils.trimToEmpty(feedback));
            }

            createCell(wb, row, getBodyStyle(wb), columnNum++, "");
            createCell(wb, row, getBodyStyle(wb), columnNum++, "");
            createCell(wb, row, getBodyStyle(wb), columnNum++, "");
            rowNum++;
        }

        {
            // 总分
            int columnNum = 0;
            Row row = sheet.createRow(rowNum);
            createCell(wb, row, getThStyle(wb), columnNum, "总分");
            CellRangeAddress cellRangeAddress = ExcelTool.getCellRangeAddress(rowNum, columnNum, rowNum, columnNum + 1);
            sheet.addMergedRegion(cellRangeAddress);
            setRegionBorder(sheet, cellRangeAddress, wb);
            columnNum = columnNum + 2;

            Double minInspectorTotalScore = Double.MAX_VALUE;
            for (Double inspectorTotalScore : inspectorTotalScoreMap.values()) {
                createNumCell(wb, row, getBodyStyle(wb), columnNum++, inspectorTotalScore);
                if (minInspectorTotalScore > inspectorTotalScore)
                    minInspectorTotalScore = inspectorTotalScore;
            }

            XSSFCellStyle thStyle = getThStyle(wb);
            thStyle.getFont().setColor(IndexedColors.RED.index);
            if(score!=null && inspectorCount>0)
                createNumCell(wb, row, thStyle, columnNum, score / inspectorCount);
            else
                createCell(wb, row, thStyle, columnNum, "");
            cellRangeAddress = ExcelTool.getCellRangeAddress(rowNum, columnNum, rowNum, columnNum + 1);
            sheet.addMergedRegion(cellRangeAddress);
            setRegionBorder(sheet, cellRangeAddress, wb);
            columnNum = columnNum + 2;

            createNumCell(wb, row, thStyle, columnNum++, minInspectorTotalScore);
        }

    }

    public static void setRegionBorder(Sheet sheet, CellRangeAddress cellRangeAddress, XSSFWorkbook wb) {

        RegionUtil.setBorderBottom(BorderStyle.THIN, cellRangeAddress, sheet);
        RegionUtil.setBorderLeft(BorderStyle.THIN, cellRangeAddress, sheet);
        RegionUtil.setBorderRight(BorderStyle.THIN, cellRangeAddress, sheet);
        RegionUtil.setBorderTop(BorderStyle.THIN, cellRangeAddress, sheet);
    }

    private static XSSFCellStyle getThStyle(XSSFWorkbook wb) {

        XSSFCellStyle cellStyle = wb.createCellStyle();
        // 设置单元格居中对齐
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        // 设置单元格垂直居中对齐
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        cellStyle.setWrapText(true);

        // 设置单元格字体样式
        XSSFFont font = wb.createFont();
        font.setFontName("宋体");
        font.setBold(true);
        font.setFontHeight((short) (20 * 11));
        font.setColor(IndexedColors.BLUE.index);
        cellStyle.setFont(font);

        // 设置单元格边框为细线条
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setBorderTop(BorderStyle.THIN);

        return cellStyle;
    }

    public static XSSFCellStyle getBodyStyle(XSSFWorkbook wb) {
        // 创建单元格样式
        XSSFCellStyle cellStyle = Utils.getBodyStyle(wb);

        // 设置单元格边框为细线条
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setBorderTop(BorderStyle.THIN);

        return cellStyle;
    }

    private static void createCell(XSSFWorkbook wb, Row row, CellStyle style, int column, String text) {
        Cell cell = row.createCell(column);
        cell.setCellValue(text==null?"":text);
        cell.setCellStyle(style);
    }

    private static void createNumCell(XSSFWorkbook wb, Row row, CellStyle style, int column, Double num) {

        if(num==null) {
            createCell(wb, row, style, column, "");
            return;
        }
        Cell cell = row.createCell(column);
        cell.setCellType(CellType.NUMERIC);
        cell.setCellValue(((int)(num*10))*1.0/10); // 保留1位小数
        cell.setCellStyle(style);
    }

    // 班次汇总统计
    public Map<String, Object> statTrain(int trainId) {

        Map<String, Object> resultMap = new HashMap<>();

        List<StatTrainBean> statTrainBeans = iTrainMapper.stat(trainId);

        // 课程总分列表 <courseId, score>
        Map<Integer, Double> courseScoreMap = new LinkedHashMap<>();
        Map<Integer, Double> courseTotalScoreMap = new HashMap<>();
        // 课程评价列表 <courseId, List<String>>
        Map<Integer, List<String>> courseFeedbackMap = new LinkedHashMap<>();
        // 测评账号列表
        Map<Integer, Integer> courseInspectorNumMap = new HashMap<>();

        for (StatTrainBean statTrainBean : statTrainBeans) {
            Integer _courseId = statTrainBean.getCourseId();
            Double _totalScore = statTrainBean.getTotalScore();

            Integer inspectorNum = courseInspectorNumMap.get(_courseId);
            if (inspectorNum == null) inspectorNum = 0;
            inspectorNum++;
            courseInspectorNumMap.put(_courseId, inspectorNum);

            Double totalScore = courseTotalScoreMap.get(_courseId);
            if (totalScore == null) totalScore = 0.0;
            totalScore += _totalScore;
            courseTotalScoreMap.put(_courseId, totalScore);

            List<String> feedbacks = courseFeedbackMap.get(_courseId);
            if (feedbacks == null) {
                feedbacks = new ArrayList<>();
                courseFeedbackMap.put(_courseId, feedbacks);
            }
            if (StringUtils.isNotBlank(statTrainBean.getFeedback()))
                feedbacks.add(statTrainBean.getFeedback());
        }


        for (Map.Entry<Integer, Double> entry : courseTotalScoreMap.entrySet()) {
            Integer _courseId = entry.getKey();
            Double totalScore = entry.getValue();

            courseScoreMap.put(_courseId, ((int) ((totalScore / courseInspectorNumMap.get(_courseId)) * 100)) * 1.0 / 100);
        }

        resultMap.put("courseScoreMap", courseScoreMap);
        resultMap.put("courseFeedbackMap", courseFeedbackMap);

        return resultMap;
    }

    // 课程结果统计
    public Map<String, Object> statCourse(int courseId) {

        Map<String, Object> resultMap = new HashMap<>();

        // 查找某门课完成的所有账号列表
        List<TrainInspectorCourse> trainInspectorCourses = null;
        {
            TrainInspectorCourseExample example = new TrainInspectorCourseExample();
            example.createCriteria().andCourseIdEqualTo(courseId)
                    .andStatusEqualTo(TrainConstants.TRAIN_INSPECTOR_COURSE_STATUS_FINISH);
            trainInspectorCourses = trainInspectorCourseMapper.selectByExample(example);
        }

        // 指标列表
        TrainCourse trainCourse = trainCourseMapper.selectByPrimaryKey(courseId);
        Integer evaTableId = trainCourse.getEvaTableId();
        TrainEvaTable trainEvaTable = trainEvaTableMapper.selectByPrimaryKey(evaTableId);
        List<TrainEvaNorm> normList = trainEvaTable.getNormList();

        Map<Integer, TrainEvaRank> trainEvaRankMap = trainEvaRankService.findAll(evaTableId);
        // 结果列表 <normId_inspectorId，TrainEvaRank>
        Map<String, TrainEvaRank> normInspectorRankMap = new HashMap<>();
        {
            TrainEvaResultExample example = new TrainEvaResultExample();
            example.createCriteria().andCourseIdEqualTo(courseId);
            List<TrainEvaResult> trainEvaResults = trainEvaResultMapper.selectByExample(example);
            for (TrainEvaResult r : trainEvaResults) {
                normInspectorRankMap.put(r.getNormId() + "_" + r.getInspectorId(), trainEvaRankMap.get(r.getRankId()));
            }
        }

        // 指标得分列表 <normId, scores>
        Map<Integer, List<Double>> normScoresMap = new LinkedHashMap<>();
        // 测评人总分 <inspectorId, totalScore>
        Map<Integer, Double> inspectorTotalScoreMap = new LinkedHashMap<>();
        // 指标总分 <normId, totalScore>
        Map<Integer, Double> normTotalScoreMap = new LinkedHashMap<>();
        // 指标最低分 <normId, minScore>
        Map<Integer, Integer> normMinScoreMap = new LinkedHashMap<>();
        // 意见列表 <inspectorId, String>
        Map<Integer, String> feedbackMap = new HashMap<>();

        // 每个指标的勾选数量 <normId_rankId，num>
        Map<String, Integer> normRankNumMap = new HashMap<>();
        // 汇总勾选数量<rankId, num>
        Map<Integer, Integer> rankNumMap = new HashMap<>();


        for (TrainInspectorCourse tic : trainInspectorCourses) {

            Integer inspectorId = tic.getInspectorId();
            Double inspectorTotalScore = inspectorTotalScoreMap.get(inspectorId);
            if (inspectorTotalScore == null) inspectorTotalScore = 0.0;

            for (TrainEvaNorm n : normList) {

                Integer normId = n.getId();
                List<Double> scores = normScoresMap.get(normId);
                if (scores == null) {
                    scores = new ArrayList<>();
                    normScoresMap.put(normId, scores);
                }
                TrainEvaRank trainEvaRank = normInspectorRankMap.get(normId + "_" + inspectorId);

                Integer rankId = trainEvaRank.getId();
                {
                    Integer num = normRankNumMap.get(normId + "_" + rankId);
                    if (num == null) num = 0;
                    normRankNumMap.put(normId + "_" + rankId, num + 1);
                }
                {
                    Integer num = rankNumMap.get(rankId);
                    if (num == null) num = 0;
                    rankNumMap.put(rankId, num + 1);
                }

                Integer score = trainEvaRank.getScore();
                scores.add(Double.valueOf(score));
                inspectorTotalScore += score;

                Double normTotalScore = normTotalScoreMap.get(normId);
                if (normTotalScore == null) normTotalScore = 0.0;
                normTotalScore += score;
                normTotalScoreMap.put(normId, normTotalScore);

                Integer normMinScore = normMinScoreMap.get(normId);
                if (normMinScore == null) normMinScore = Integer.MAX_VALUE;
                normMinScore = (normMinScore > score) ? score : normMinScore;
                normMinScoreMap.put(normId, normMinScore);
            }

            inspectorTotalScoreMap.put(inspectorId, inspectorTotalScore);

            if (StringUtils.isNotBlank(tic.getFeedback()))
                feedbackMap.put(inspectorId, tic.getFeedback());
        }

        resultMap.put("trainEvaTable", trainEvaTable);
        resultMap.put("normScoresMap", normScoresMap);
        resultMap.put("inspectorTotalScoreMap", inspectorTotalScoreMap);
        resultMap.put("normTotalScoreMap", normTotalScoreMap);
        resultMap.put("normMinScoreMap", normMinScoreMap);
        resultMap.put("feedbackMap", feedbackMap);
        resultMap.put("normRankNumMap", normRankNumMap);
        resultMap.put("rankNumMap", rankNumMap);

        // 最终得分
        Double score = null;
        // 一级指标总分
        Map<Integer, Double> topNormTotalScoreMap = new LinkedHashMap<>();
        for (TrainEvaNorm norm : trainEvaTable.getNorms()) {

            Integer normId = norm.getId();
            Double topNormTotalScore = normTotalScoreMap.get(normId);
            if (topNormTotalScore == null) topNormTotalScore = 0.0;
            List<TrainEvaNorm> subNorms = norm.getSubNorms();
            if (subNorms != null && subNorms.size() > 0) {

                for (TrainEvaNorm subNorm : subNorms) {
                    Double subNormScore = normTotalScoreMap.get(subNorm.getId());
                    if (subNormScore != null)
                        topNormTotalScore += subNormScore;
                }

            } else {
                topNormTotalScore = normTotalScoreMap.get(normId);
            }
            score = (score==null)?0.0:score;
            score += topNormTotalScore;
            topNormTotalScoreMap.put(normId, topNormTotalScore);
        }

        resultMap.put("topNormTotalScoreMap", topNormTotalScoreMap);
        resultMap.put("score", score);

        return resultMap;
    }
}
