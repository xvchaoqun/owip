package controller.sp;

import org.springframework.beans.factory.annotation.Autowired;
import persistence.sp.*;
import service.ps.PsBaseMapper;
import service.sp.*;
import service.unit.UnitService;
import sys.HttpResponseMethod;

public class SpBaseController extends PsBaseMapper implements HttpResponseMethod {

    @Autowired
    protected UnitService unitService;

    @Autowired
    protected SpCgService spCgService;
    @Autowired
    protected SpCgMapper spCgMapper;

    @Autowired
    protected SpTeachService spTeachService;
    @Autowired
    protected SpTeachMapper spTeachMapper;

    @Autowired
    protected SpDpService spDpService;
    @Autowired
    protected SpDpMapper spDpMapper;

    @Autowired
    protected SpRetireService spRetireService;
    @Autowired
    protected SpRetireMapper spRetireMapper;

    @Autowired
    protected SpNpcService spNpcService;
    @Autowired
    protected SpNpcMapper spNpcMapper;

    @Autowired
    protected SpTalentService spTalentService;
    @Autowired
    protected SpTalentMapper spTalentMapper;
}
