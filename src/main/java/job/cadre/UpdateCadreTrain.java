package job.cadre;

import domain.cadre.Cadre;
import domain.cadre.CadreExample;
import domain.cet.CetRecord;
import domain.cet.CetRecordExample;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import persistence.cadre.CadreMapper;
import persistence.cet.CetRecordMapper;
import service.cadre.CadreTrainService;

import java.util.List;

public class UpdateCadreTrain implements Job {

    @Autowired
    private CadreMapper cadreMapper;
    @Autowired
    private CetRecordMapper cetRecordMapper;
    @Autowired
    private CadreTrainService cadreTrainService;

    //更新培训模块的培训信息至领导干部的培训信息
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

        List<Cadre> cadreList = cadreMapper.selectByExample(new CadreExample());
        for (Cadre cadre : cadreList) {
            int userId = cadre.getUserId();
            CetRecordExample example = new CetRecordExample();
            example.createCriteria().andUserIdEqualTo(userId);
            example.setOrderByClause("start_date asc");
            List<CetRecord> cetRecords = cetRecordMapper.selectByExample(example);

            cadreTrainService.updateCadreTrain(cetRecords, cadre.getId());
        }

    }
}
