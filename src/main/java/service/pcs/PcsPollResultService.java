package service.pcs;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.CompactWriter;
import com.thoughtworks.xstream.io.xml.DomDriver;
import domain.pcs.PcsPoll;
import domain.pcs.PcsPollInspector;
import domain.pcs.PcsPollResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import persistence.pcs.common.PcsTempResult;
import sys.constants.PcsConstants;

import java.io.StringWriter;
import java.util.*;

@Service
public class PcsPollResultService extends PcsBaseMapper {

    @Autowired
    private PcsPollInspectorService pcsPollInspectorService;

    //转换暂存票数
    public PcsTempResult getTempResult(String tempData) {

        PcsTempResult tempResult = null;

        XStream xStream = new XStream(new DomDriver());
        xStream.alias("tempResult", PcsTempResult.class);//将序列化中的类全量名称，用别名替换。

        if (StringUtils.isNotBlank(tempData)) {
            tempResult = (PcsTempResult) xStream.fromXML(tempData);//XML反序列化
        }

        tempResult = (tempResult == null) ? new PcsTempResult() : tempResult;

        return tempResult;
    }

    public String getStringTemp(PcsTempResult record) {

        XStream xStream = new XStream(new DomDriver());
        xStream.alias("tempResult", PcsTempResult.class);

        StringWriter sw = new StringWriter();
        xStream.marshal(record, new CompactWriter(sw));
        return sw.toString();
    }

    @Transactional
    public void submitResult(PcsPollInspector inspector) {

        PcsPoll pcsPoll = inspector.getPcsPoll();
        int pollId = pcsPoll.getId();
        Byte stage = pcsPoll.getStage();
        PcsTempResult tempResult = getTempResult(inspector.getTempdata());

        List<PcsPollResult> resultList = new ArrayList<>();

        if (stage != PcsConstants.PCS_POLL_FIRST_STAGE) {//提交二下/三下阶段推荐结果

            Map<String, Byte> secondResultMap = tempResult.getSecondResultMap();
            Map<String, Integer> otherResultMap = tempResult.getOtherResultMap();

            for (String key : secondResultMap.keySet()) {

                PcsPollResult result = new PcsPollResult();
                Byte type = Byte.valueOf(key.split("_")[0]);
                Integer userId = Integer.valueOf(key.split("_")[1]);
                Byte status = secondResultMap.get(key);
                result.setPollId(pollId);
                result.setCandidateUserId(userId);
                result.setInspectorId(inspector.getId());
                result.setStage(stage);
                result.setPartyId(pcsPoll.getPartyId());
                result.setBranchId(pcsPoll.getBranchId());
                result.setIsPositive(inspector.getIsPositive());
                result.setStatus(status);
                result.setType(type);

                if (status == PcsConstants.RESULT_STATUS_DISAGREE) {
                    String otherKey = key + "_4";
                    userId = otherResultMap.get(otherKey); // 允许不填
                }
                result.setUserId(userId);
                resultList.add(result);
            }

        } else {//提交一下阶段推荐结果

            Map<Byte, Set<Integer>> firstResultMap = tempResult.getFirstResultMap();

            for (Byte type : firstResultMap.keySet()) {
                Set<Integer> userIdList = firstResultMap.get(type);
                for (Integer userId : userIdList) {
                    PcsPollResult result = new PcsPollResult();
                    result.setPollId(pollId);
                    result.setInspectorId(inspector.getId());
                    result.setStage(stage);
                    result.setPartyId(inspector.getPartyId());
                    result.setBranchId(inspector.getBranchId());
                    result.setIsPositive(inspector.getIsPositive());
                    result.setStatus(PcsConstants.RESULT_STATUS_AGREE);
                    result.setType(type);
                    result.setUserId(userId);

                    resultList.add(result);
                }
            }
        }

        // 允许resultList为空，即全部不填
        for (PcsPollResult record : resultList) {
            pcsPollResultMapper.insertSelective(record);
        }

        //更新投票人状态
        inspector.setIsFinished(true);
        inspector.setSubmitTime(new Date());
        pcsPollInspectorService.updateByPrimaryKeySelective(inspector);

        iPcsMapper.updatePollInspectorCount(pollId);
    }

}
