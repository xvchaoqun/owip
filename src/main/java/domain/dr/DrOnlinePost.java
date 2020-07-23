package domain.dr;

import domain.unit.Unit;
import domain.unit.UnitPost;
import org.apache.commons.lang.StringUtils;
import persistence.dr.DrOnlineCandidateMapper;
import persistence.dr.DrOnlineMapper;
import persistence.unit.UnitMapper;
import persistence.unit.UnitPostMapper;
import sys.tags.CmTag;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DrOnlinePost implements Serializable {
    public DrOnline getDrOnline(){
        DrOnlineMapper drOnlineMapper = CmTag.getBean(DrOnlineMapper.class);
        DrOnline drOnline = drOnlineMapper.selectByPrimaryKey(onlineId);
        return drOnline;
    }

    public List<String> getCans(){

        List<String> _candidates = new ArrayList<>();
        if(StringUtils.isNotBlank(candidates)) {
            DrOnlineCandidateMapper drOnlineCandidateMapper = CmTag.getBean(DrOnlineCandidateMapper.class);
            DrOnlineCandidateExample example = new DrOnlineCandidateExample();
            example.createCriteria().andPostIdEqualTo(id);
            List<DrOnlineCandidate> candidateList = drOnlineCandidateMapper.selectByExample(example);
            for (DrOnlineCandidate can : candidateList){
                _candidates.add(can.getRealname());
            }
        }

        return _candidates;
    }
    public UnitPost getUnitPost(){
        UnitPostMapper unitPostMapper = CmTag.getBean(UnitPostMapper.class);
        return unitPostMapper.selectByPrimaryKey(unitPostId);
    }
    public Unit getUnit(){
        UnitMapper unitMapper = CmTag.getBean(UnitMapper.class);
        return unitMapper.selectByPrimaryKey(getUnitPost().getUnitId());
    }
    private Integer id;

    private Integer unitPostId;

    private String name;

    private Integer onlineId;

    private Boolean hasCandidate;

    private String candidates;

    private Boolean hasCompetitive;

    private Integer competitiveNum;

    private Integer sortOrder;

    private String remark;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUnitPostId() {
        return unitPostId;
    }

    public void setUnitPostId(Integer unitPostId) {
        this.unitPostId = unitPostId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getOnlineId() {
        return onlineId;
    }

    public void setOnlineId(Integer onlineId) {
        this.onlineId = onlineId;
    }

    public Boolean getHasCandidate() {
        return hasCandidate;
    }

    public void setHasCandidate(Boolean hasCandidate) {
        this.hasCandidate = hasCandidate;
    }

    public String getCandidates() {
        return candidates;
    }

    public void setCandidates(String candidates) {
        this.candidates = candidates == null ? null : candidates.trim();
    }

    public Boolean getHasCompetitive() {
        return hasCompetitive;
    }

    public void setHasCompetitive(Boolean hasCompetitive) {
        this.hasCompetitive = hasCompetitive;
    }

    public Integer getCompetitiveNum() {
        return competitiveNum;
    }

    public void setCompetitiveNum(Integer competitiveNum) {
        this.competitiveNum = competitiveNum;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}