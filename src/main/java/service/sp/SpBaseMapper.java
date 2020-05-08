package service.sp;

import org.springframework.beans.factory.annotation.Autowired;
import persistence.sp.*;
import service.CoreBaseMapper;
import sys.constants.CadreConstants;

import java.util.ArrayList;
import java.util.List;

public class SpBaseMapper extends CoreBaseMapper {

    /**
     * 八类代表
     */
    @Autowired(required = false)
    protected SpCgMapper spCgMapper;

    @Autowired(required = false)
    protected SpTeachMapper spTeachMapper;

    @Autowired(required = false)
    protected SpDpMapper spDpMapper;

    @Autowired(required = false)
    protected SpRetireMapper spRetireMapper;

    @Autowired(required = false)
    protected SpNpcMapper spNpcMapper;

    @Autowired(required = false)
    protected SpTalentMapper spTalentMapper;

    //判断是否为现任干部
    public static Boolean isCurrentCadre(Byte status){

        List<Byte> currentList = new ArrayList<>();
        currentList.add(CadreConstants.CADRE_STATUS_LEADER);
        currentList.add(CadreConstants.CADRE_STATUS_CJ);
        currentList.add(CadreConstants.CADRE_STATUS_KJ);

        return currentList.contains(status);
    }

    //判断是否为离任干部
    public static Boolean isLeaveCadre(Byte status){

        List<Byte> leaveList = new ArrayList<>();
        leaveList.add(CadreConstants.CADRE_STATUS_LEADER_LEAVE);
        leaveList.add(CadreConstants.CADRE_STATUS_CJ_LEAVE);
        leaveList.add(CadreConstants.CADRE_STATUS_KJ_LEAVE);

        return leaveList.contains(status);
    }
}
