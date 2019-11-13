package service.op;

import org.springframework.beans.factory.annotation.Autowired;
import persistence.op.OpAttatchMapper;
import persistence.op.OpRecordMapper;
import persistence.op.OpReportMapper;
import service.CoreBaseMapper;

public class OpBaseMapper extends CoreBaseMapper {

    @Autowired
    protected OpReportMapper opReportMapper;
    @Autowired
    protected OpAttatchMapper opAttatchMapper;
    @Autowired
    protected OpRecordMapper opRecordMapper;
}
