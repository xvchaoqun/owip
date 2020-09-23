package service.oa;

import controller.global.OpException;
import domain.oa.OaGrid;
import domain.oa.OaGridParty;
import domain.oa.OaGridPartyExample;
import domain.party.Party;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import shiro.ShiroHelper;
import sys.constants.OaConstants;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;

@Service
public class OaGridPartyService extends OaBaseMapper {

    @Autowired
    protected OaGridPartyDataService oaGridPartyDataService;

    //添加excel并处理数据
    @Transactional
    public void insertSelective(OaGridParty record, MultipartFile _excelFilePath, HttpServletRequest request) throws IOException, InvalidFormatException {

        //记录数据
        if(_excelFilePath != null){
            oaGridPartyDataService.importData(record, _excelFilePath);
        }

        OaGrid oaGrid = oaGridMapper.selectByPrimaryKey(record.getGridId());
        Party party = partyMapper.selectByPrimaryKey(record.getPartyId());

        if (oaGrid != null) {
            record.setGridName(oaGrid.getName());
        }
        if (party != null){
            record.setPartyName(party.getName());
        }
        record.setStatus(OaConstants.OA_GRID_PARTY_INI);
        oaGridPartyMapper.insertSelective(record);
    }

    //修改，重新上传excel并处理数据
    @Transactional
    public void update(OaGridParty record, MultipartFile _excelFilePath, HttpServletRequest request) throws IOException, InvalidFormatException {

        if(_excelFilePath != null){
            oaGridPartyDataService.importData(record, _excelFilePath);
        }
        record.setStatus(OaConstants.OA_GRID_PARTY_SAVE);
        oaGridPartyMapper.updateByPrimaryKeySelective(record);
    }

    @Transactional
    public void updateByPrimaryKeySelective(OaGridParty record){

        oaGridPartyMapper.updateByPrimaryKeySelective(record);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        OaGridPartyExample example = new OaGridPartyExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        oaGridPartyMapper.deleteByExample(example);
    }

    public Map<Integer, OaGridParty> findAll() {

        OaGridPartyExample example = new OaGridPartyExample();
        example.createCriteria();
        example.setOrderByClause("sort_order desc");
        List<OaGridParty> records = oaGridPartyMapper.selectByExample(example);
        Map<Integer, OaGridParty> map = new LinkedHashMap<>();
        for (OaGridParty record : records) {
            map.put(record.getId(), record);
        }

        return map;
    }

    @Transactional
    public void delReportFile(Integer id, String filePath, String fileName) {

        OaGridParty record = oaGridPartyMapper.selectByPrimaryKey(id);
        if (StringUtils.isNotBlank(record.getFilePath()) && record.getFilePath().contains(filePath)) {
            List<String> filePathList = new ArrayList<String>(Arrays.asList(StringUtils.split(record.getFilePath(), ";")));
            List<String> fileNameList = new ArrayList<String>(Arrays.asList(StringUtils.split(record.getFileName(), ";")));
            int index = filePathList.indexOf(filePath);
            filePathList.remove(index);
            fileNameList.remove(index);
            if(filePathList.size() == 0){
                commonMapper.excuteSql("update oa_grid_party set file_name=null,file_path=null where id="+id);
            }else{
                record.setFileName(StringUtils.join(fileNameList, ";"));
                record.setFilePath(StringUtils.join(filePathList, ";"));
                updateByPrimaryKeySelective(record);
            }
        }
    }

    @Transactional
    public void batchReport(Integer[] ids, Byte report, String backReason) {

        OaGridParty record = new OaGridParty();
        record.setStatus(report);
        if (report == OaConstants.OA_GRID_PARTY_REPORT){
            record.setReportUserId(ShiroHelper.getCurrentUserId());
            record.setReportTime(new Date());
        }else {
            record.setBackReason(backReason);
        }

        OaGridPartyExample example = new OaGridPartyExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        oaGridPartyMapper.updateByExampleSelective(record, example);
    }

    public void checkReportData(OaGridParty oaGridParty) {

        if (StringUtils.isBlank(oaGridParty.getExcelFilePath()))
            throw new OpException("还没有上传报送文件，不可报送");

        if (StringUtils.isBlank(oaGridParty.getFileName()) || StringUtils.isBlank(oaGridParty.getFilePath()))
            throw new OpException("还没有上传签名文件，不可报送");

    }
}
