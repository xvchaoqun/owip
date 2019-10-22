package controller.sp;

import domain.unit.Unit;
import domain.unit.UnitExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import persistence.sp.*;
import service.ps.PsBaseMapper;
import service.sp.*;
import sys.HttpResponseMethod;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class SpBaseController extends PsBaseMapper implements HttpResponseMethod {

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

    @Transactional
    public Unit getUnitByCode(String code){

        UnitExample example = new UnitExample();
        example.createCriteria();
        example.setOrderByClause("sort_order asc");
        List<Unit> unites = unitMapper.selectByExample(example);
        Map<String, Unit> map = new LinkedHashMap<>();
        for (Unit unit : unites) {
            map.put(unit.getCode(), unit);
        }

        return map.get(code);
    }
}
