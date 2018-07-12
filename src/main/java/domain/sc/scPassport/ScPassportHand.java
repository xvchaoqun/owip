package domain.sc.scPassport;

import domain.abroad.PassportExample;
import domain.cadre.CadreView;
import domain.dispatch.Dispatch;
import domain.dispatch.DispatchCadre;
import domain.unit.Unit;
import persistence.abroad.PassportMapper;
import persistence.sc.scPassport.ScPassportMapper;
import persistence.sc.scPassport.ScPassportMsgMapper;
import sys.constants.AbroadConstants;
import sys.tags.CmTag;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class ScPassportHand implements Serializable {

    public CadreView getCadre(){

        return CmTag.getCadreById(cadreId);
    }

    public Unit getUnit(){

        if(unitId==null) return null;
        return CmTag.getUnit(unitId);
    }

    public Dispatch getDispatch(){
        if(dispatchCadreId!=null){
            DispatchCadre dispatchCadre = CmTag.getDispatchCadre(dispatchCadreId);
            if(dispatchCadre!=null)
                return CmTag.getDispatch(dispatchCadre.getDispatchId());
        }
        return null;
    }
    // 集中保管的证件数量
    public int getPassportCount(){

        PassportMapper passportMapper = CmTag.getBean(PassportMapper.class);
        if(passportMapper==null) return 0;

        PassportExample example = new PassportExample();
        example.createCriteria().andCadreIdEqualTo(cadreId).andTypeEqualTo(AbroadConstants.ABROAD_PASSPORT_TYPE_KEEP);
        return (int) passportMapper.countByExample(example);
    }

    // 上交的证件（包括未拥有的证件）
    public List<ScPassport> getScPassports(){

        ScPassportMapper scPassportMapper = CmTag.getBean(ScPassportMapper.class);
        ScPassportExample example = new ScPassportExample();
        example.createCriteria().andHandIdEqualTo(id);
        return scPassportMapper.selectByExample(example);
    }

    // 短信通知数量
    public int getMsgCount(){

        ScPassportMsgMapper scPassportMsgMapper = CmTag.getBean(ScPassportMsgMapper.class);
        if(scPassportMsgMapper==null) return 0;

        ScPassportMsgExample example = new ScPassportMsgExample();
        example.createCriteria().andHandIdEqualTo(id);
        return (int) scPassportMsgMapper.countByExample(example);
    }

    private Integer id;

    private Integer cadreId;

    private Date appointDate;

    private Byte addType;

    private String post;

    private Integer postId;

    private Integer typeId;

    private Integer unitId;

    private Integer dispatchCadreId;

    private String remark;

    private Date abolishTime;

    private Byte status;

    private Boolean isKeep;

    private Date addTime;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCadreId() {
        return cadreId;
    }

    public void setCadreId(Integer cadreId) {
        this.cadreId = cadreId;
    }

    public Date getAppointDate() {
        return appointDate;
    }

    public void setAppointDate(Date appointDate) {
        this.appointDate = appointDate;
    }

    public Byte getAddType() {
        return addType;
    }

    public void setAddType(Byte addType) {
        this.addType = addType;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post == null ? null : post.trim();
    }

    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public Integer getUnitId() {
        return unitId;
    }

    public void setUnitId(Integer unitId) {
        this.unitId = unitId;
    }

    public Integer getDispatchCadreId() {
        return dispatchCadreId;
    }

    public void setDispatchCadreId(Integer dispatchCadreId) {
        this.dispatchCadreId = dispatchCadreId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Date getAbolishTime() {
        return abolishTime;
    }

    public void setAbolishTime(Date abolishTime) {
        this.abolishTime = abolishTime;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Boolean getIsKeep() {
        return isKeep;
    }

    public void setIsKeep(Boolean isKeep) {
        this.isKeep = isKeep;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }
}