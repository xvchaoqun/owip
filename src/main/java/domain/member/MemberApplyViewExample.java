package domain.member;

import org.apache.commons.lang3.StringUtils;
import shiro.ShiroHelper;
import sys.constants.OwConstants;
import sys.constants.SystemConstants;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class MemberApplyViewExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public MemberApplyViewExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        protected void addCriterionForJDBCDate(String condition, Date value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            addCriterion(condition, new java.sql.Date(value.getTime()), property);
        }

        protected void addCriterionForJDBCDate(String condition, List<Date> values, String property) {
            if (values == null || values.size() == 0) {
                throw new RuntimeException("Value list for " + property + " cannot be null or empty");
            }
            List<java.sql.Date> dateList = new ArrayList<java.sql.Date>();
            Iterator<Date> iter = values.iterator();
            while (iter.hasNext()) {
                dateList.add(new java.sql.Date(iter.next().getTime()));
            }
            addCriterion(condition, dateList, property);
        }

        protected void addCriterionForJDBCDate(String condition, Date value1, Date value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            addCriterion(condition, new java.sql.Date(value1.getTime()), new java.sql.Date(value2.getTime()), property);
        }

        public Criteria andUserIdIsNull() {
            addCriterion("user_id is null");
            return (Criteria) this;
        }

        public Criteria andUserIdIsNotNull() {
            addCriterion("user_id is not null");
            return (Criteria) this;
        }

        public Criteria andUserIdEqualTo(Integer value) {
            addCriterion("user_id =", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotEqualTo(Integer value) {
            addCriterion("user_id <>", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThan(Integer value) {
            addCriterion("user_id >", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("user_id >=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThan(Integer value) {
            addCriterion("user_id <", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThanOrEqualTo(Integer value) {
            addCriterion("user_id <=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdIn(List<Integer> values) {
            addCriterion("user_id in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotIn(List<Integer> values) {
            addCriterion("user_id not in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdBetween(Integer value1, Integer value2) {
            addCriterion("user_id between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotBetween(Integer value1, Integer value2) {
            addCriterion("user_id not between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andPartyIdIsNull() {
            addCriterion("party_id is null");
            return (Criteria) this;
        }

        public Criteria andPartyIdIsNotNull() {
            addCriterion("party_id is not null");
            return (Criteria) this;
        }

        public Criteria andPartyIdEqualTo(Integer value) {
            addCriterion("party_id =", value, "partyId");
            return (Criteria) this;
        }

        public Criteria andPartyIdNotEqualTo(Integer value) {
            addCriterion("party_id <>", value, "partyId");
            return (Criteria) this;
        }

        public Criteria andPartyIdGreaterThan(Integer value) {
            addCriterion("party_id >", value, "partyId");
            return (Criteria) this;
        }

        public Criteria andPartyIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("party_id >=", value, "partyId");
            return (Criteria) this;
        }

        public Criteria andPartyIdLessThan(Integer value) {
            addCriterion("party_id <", value, "partyId");
            return (Criteria) this;
        }

        public Criteria andPartyIdLessThanOrEqualTo(Integer value) {
            addCriterion("party_id <=", value, "partyId");
            return (Criteria) this;
        }

        public Criteria andPartyIdIn(List<Integer> values) {
            addCriterion("party_id in", values, "partyId");
            return (Criteria) this;
        }

        public Criteria andPartyIdNotIn(List<Integer> values) {
            addCriterion("party_id not in", values, "partyId");
            return (Criteria) this;
        }

        public Criteria andPartyIdBetween(Integer value1, Integer value2) {
            addCriterion("party_id between", value1, value2, "partyId");
            return (Criteria) this;
        }

        public Criteria andPartyIdNotBetween(Integer value1, Integer value2) {
            addCriterion("party_id not between", value1, value2, "partyId");
            return (Criteria) this;
        }

        public Criteria andBranchIdIsNull() {
            addCriterion("branch_id is null");
            return (Criteria) this;
        }

        public Criteria andBranchIdIsNotNull() {
            addCriterion("branch_id is not null");
            return (Criteria) this;
        }

        public Criteria andBranchIdEqualTo(Integer value) {
            addCriterion("branch_id =", value, "branchId");
            return (Criteria) this;
        }

        public Criteria andBranchIdNotEqualTo(Integer value) {
            addCriterion("branch_id <>", value, "branchId");
            return (Criteria) this;
        }

        public Criteria andBranchIdGreaterThan(Integer value) {
            addCriterion("branch_id >", value, "branchId");
            return (Criteria) this;
        }

        public Criteria andBranchIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("branch_id >=", value, "branchId");
            return (Criteria) this;
        }

        public Criteria andBranchIdLessThan(Integer value) {
            addCriterion("branch_id <", value, "branchId");
            return (Criteria) this;
        }

        public Criteria andBranchIdLessThanOrEqualTo(Integer value) {
            addCriterion("branch_id <=", value, "branchId");
            return (Criteria) this;
        }

        public Criteria andBranchIdIn(List<Integer> values) {
            addCriterion("branch_id in", values, "branchId");
            return (Criteria) this;
        }

        public Criteria andBranchIdNotIn(List<Integer> values) {
            addCriterion("branch_id not in", values, "branchId");
            return (Criteria) this;
        }

        public Criteria andBranchIdBetween(Integer value1, Integer value2) {
            addCriterion("branch_id between", value1, value2, "branchId");
            return (Criteria) this;
        }

        public Criteria andBranchIdNotBetween(Integer value1, Integer value2) {
            addCriterion("branch_id not between", value1, value2, "branchId");
            return (Criteria) this;
        }

        public Criteria andTypeIsNull() {
            addCriterion("type is null");
            return (Criteria) this;
        }

        public Criteria andTypeIsNotNull() {
            addCriterion("type is not null");
            return (Criteria) this;
        }

        public Criteria andTypeEqualTo(Byte value) {
            addCriterion("type =", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotEqualTo(Byte value) {
            addCriterion("type <>", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThan(Byte value) {
            addCriterion("type >", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThanOrEqualTo(Byte value) {
            addCriterion("type >=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThan(Byte value) {
            addCriterion("type <", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThanOrEqualTo(Byte value) {
            addCriterion("type <=", value, "type");
            return (Criteria) this;
        }

        public Criteria andPublicIdIsNullOrThis(Integer publicId, byte type) {

            String sql = "";
             if(type== OwConstants.OW_PARTY_PUBLIC_TYPE_GROW){
                 sql = "grow_public_id is null";
                 if(publicId != null){
                     sql +=" or grow_public_id = " + publicId;
                 }
             }else{

                 sql = "positive_public_id is null";
                 if(publicId != null){
                     sql +=" or positive_public_id = " + publicId;
                 }
             }

             addCriterion("("+sql+")");

            return (Criteria) this;
        }

        public Criteria andTypeIn(List<Byte> values) {
            addCriterion("type in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotIn(List<Byte> values) {
            addCriterion("type not in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeBetween(Byte value1, Byte value2) {
            addCriterion("type between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotBetween(Byte value1, Byte value2) {
            addCriterion("type not between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andApplyTimeIsNull() {
            addCriterion("apply_time is null");
            return (Criteria) this;
        }

        public Criteria andApplyTimeIsNotNull() {
            addCriterion("apply_time is not null");
            return (Criteria) this;
        }

        public Criteria andApplyTimeEqualTo(Date value) {
            addCriterionForJDBCDate("apply_time =", value, "applyTime");
            return (Criteria) this;
        }

        public Criteria andApplyTimeNotEqualTo(Date value) {
            addCriterionForJDBCDate("apply_time <>", value, "applyTime");
            return (Criteria) this;
        }

        public Criteria andApplyTimeGreaterThan(Date value) {
            addCriterionForJDBCDate("apply_time >", value, "applyTime");
            return (Criteria) this;
        }

        public Criteria andApplyTimeGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("apply_time >=", value, "applyTime");
            return (Criteria) this;
        }

        public Criteria andApplyTimeLessThan(Date value) {
            addCriterionForJDBCDate("apply_time <", value, "applyTime");
            return (Criteria) this;
        }

        public Criteria andApplyTimeLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("apply_time <=", value, "applyTime");
            return (Criteria) this;
        }

        public Criteria andApplyTimeIn(List<Date> values) {
            addCriterionForJDBCDate("apply_time in", values, "applyTime");
            return (Criteria) this;
        }

        public Criteria andApplyTimeNotIn(List<Date> values) {
            addCriterionForJDBCDate("apply_time not in", values, "applyTime");
            return (Criteria) this;
        }

        public Criteria andApplyTimeBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("apply_time between", value1, value2, "applyTime");
            return (Criteria) this;
        }

        public Criteria andApplyTimeNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("apply_time not between", value1, value2, "applyTime");
            return (Criteria) this;
        }

        public Criteria andFillTimeIsNull() {
            addCriterion("fill_time is null");
            return (Criteria) this;
        }

        public Criteria andFillTimeIsNotNull() {
            addCriterion("fill_time is not null");
            return (Criteria) this;
        }

        public Criteria andFillTimeEqualTo(Date value) {
            addCriterion("fill_time =", value, "fillTime");
            return (Criteria) this;
        }

        public Criteria andFillTimeNotEqualTo(Date value) {
            addCriterion("fill_time <>", value, "fillTime");
            return (Criteria) this;
        }

        public Criteria andFillTimeGreaterThan(Date value) {
            addCriterion("fill_time >", value, "fillTime");
            return (Criteria) this;
        }

        public Criteria andFillTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("fill_time >=", value, "fillTime");
            return (Criteria) this;
        }

        public Criteria andFillTimeLessThan(Date value) {
            addCriterion("fill_time <", value, "fillTime");
            return (Criteria) this;
        }

        public Criteria andFillTimeLessThanOrEqualTo(Date value) {
            addCriterion("fill_time <=", value, "fillTime");
            return (Criteria) this;
        }

        public Criteria andFillTimeIn(List<Date> values) {
            addCriterion("fill_time in", values, "fillTime");
            return (Criteria) this;
        }

        public Criteria andFillTimeNotIn(List<Date> values) {
            addCriterion("fill_time not in", values, "fillTime");
            return (Criteria) this;
        }

        public Criteria andFillTimeBetween(Date value1, Date value2) {
            addCriterion("fill_time between", value1, value2, "fillTime");
            return (Criteria) this;
        }

        public Criteria andFillTimeNotBetween(Date value1, Date value2) {
            addCriterion("fill_time not between", value1, value2, "fillTime");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNull() {
            addCriterion("remark is null");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNotNull() {
            addCriterion("remark is not null");
            return (Criteria) this;
        }

        public Criteria andRemarkEqualTo(String value) {
            addCriterion("remark =", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotEqualTo(String value) {
            addCriterion("remark <>", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThan(String value) {
            addCriterion("remark >", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThanOrEqualTo(String value) {
            addCriterion("remark >=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThan(String value) {
            addCriterion("remark <", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThanOrEqualTo(String value) {
            addCriterion("remark <=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLike(String value) {
            addCriterion("remark like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotLike(String value) {
            addCriterion("remark not like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkIn(List<String> values) {
            addCriterion("remark in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotIn(List<String> values) {
            addCriterion("remark not in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkBetween(String value1, String value2) {
            addCriterion("remark between", value1, value2, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotBetween(String value1, String value2) {
            addCriterion("remark not between", value1, value2, "remark");
            return (Criteria) this;
        }

        public Criteria andApplyStageIsNull() {
            addCriterion("apply_stage is null");
            return (Criteria) this;
        }

        public Criteria andApplyStageIsNotNull() {
            addCriterion("apply_stage is not null");
            return (Criteria) this;
        }

        public Criteria andApplyStageEqualTo(Byte value) {
            addCriterion("apply_stage =", value, "applyStage");
            return (Criteria) this;
        }

        public Criteria andApplyStageNotEqualTo(Byte value) {
            addCriterion("apply_stage <>", value, "applyStage");
            return (Criteria) this;
        }

        public Criteria andApplyStageGreaterThan(Byte value) {
            addCriterion("apply_stage >", value, "applyStage");
            return (Criteria) this;
        }

        public Criteria andApplyStageGreaterThanOrEqualTo(Byte value) {
            addCriterion("apply_stage >=", value, "applyStage");
            return (Criteria) this;
        }

        public Criteria andApplyStageLessThan(Byte value) {
            addCriterion("apply_stage <", value, "applyStage");
            return (Criteria) this;
        }

        public Criteria andApplyStageLessThanOrEqualTo(Byte value) {
            addCriterion("apply_stage <=", value, "applyStage");
            return (Criteria) this;
        }

        public Criteria andApplyStageIn(List<Byte> values) {
            addCriterion("apply_stage in", values, "applyStage");
            return (Criteria) this;
        }

        public Criteria andApplyStageNotIn(List<Byte> values) {
            addCriterion("apply_stage not in", values, "applyStage");
            return (Criteria) this;
        }

        public Criteria andApplyStageBetween(Byte value1, Byte value2) {
            addCriterion("apply_stage between", value1, value2, "applyStage");
            return (Criteria) this;
        }

        public Criteria andApplyStageNotBetween(Byte value1, Byte value2) {
            addCriterion("apply_stage not between", value1, value2, "applyStage");
            return (Criteria) this;
        }

        public Criteria andStageIsNull() {
            addCriterion("stage is null");
            return (Criteria) this;
        }

        public Criteria andStageIsNotNull() {
            addCriterion("stage is not null");
            return (Criteria) this;
        }

        public Criteria andStageEqualTo(Byte value) {
            addCriterion("stage =", value, "stage");
            return (Criteria) this;
        }

        public Criteria andStageNotEqualTo(Byte value) {
            addCriterion("stage <>", value, "stage");
            return (Criteria) this;
        }

        public Criteria andStageGreaterThan(Byte value) {
            addCriterion("stage >", value, "stage");
            return (Criteria) this;
        }

        public Criteria andStageGreaterThanOrEqualTo(Byte value) {
            addCriterion("stage >=", value, "stage");
            return (Criteria) this;
        }

        public Criteria andStageLessThan(Byte value) {
            addCriterion("stage <", value, "stage");
            return (Criteria) this;
        }

        public Criteria andStageLessThanOrEqualTo(Byte value) {
            addCriterion("stage <=", value, "stage");
            return (Criteria) this;
        }

        public Criteria andStageIn(List<Byte> values) {
            addCriterion("stage in", values, "stage");
            return (Criteria) this;
        }

        public Criteria andStageNotIn(List<Byte> values) {
            addCriterion("stage not in", values, "stage");
            return (Criteria) this;
        }

        public Criteria andStageBetween(Byte value1, Byte value2) {
            addCriterion("stage between", value1, value2, "stage");
            return (Criteria) this;
        }

        public Criteria andStageNotBetween(Byte value1, Byte value2) {
            addCriterion("stage not between", value1, value2, "stage");
            return (Criteria) this;
        }

        public Criteria andReasonIsNull() {
            addCriterion("reason is null");
            return (Criteria) this;
        }

        public Criteria andReasonIsNotNull() {
            addCriterion("reason is not null");
            return (Criteria) this;
        }

        public Criteria andReasonEqualTo(String value) {
            addCriterion("reason =", value, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonNotEqualTo(String value) {
            addCriterion("reason <>", value, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonGreaterThan(String value) {
            addCriterion("reason >", value, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonGreaterThanOrEqualTo(String value) {
            addCriterion("reason >=", value, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonLessThan(String value) {
            addCriterion("reason <", value, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonLessThanOrEqualTo(String value) {
            addCriterion("reason <=", value, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonLike(String value) {
            addCriterion("reason like", value, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonNotLike(String value) {
            addCriterion("reason not like", value, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonIn(List<String> values) {
            addCriterion("reason in", values, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonNotIn(List<String> values) {
            addCriterion("reason not in", values, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonBetween(String value1, String value2) {
            addCriterion("reason between", value1, value2, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonNotBetween(String value1, String value2) {
            addCriterion("reason not between", value1, value2, "reason");
            return (Criteria) this;
        }

        public Criteria andPassTimeIsNull() {
            addCriterion("pass_time is null");
            return (Criteria) this;
        }

        public Criteria andPassTimeIsNotNull() {
            addCriterion("pass_time is not null");
            return (Criteria) this;
        }

        public Criteria andPassTimeEqualTo(Date value) {
            addCriterionForJDBCDate("pass_time =", value, "passTime");
            return (Criteria) this;
        }

        public Criteria andPassTimeNotEqualTo(Date value) {
            addCriterionForJDBCDate("pass_time <>", value, "passTime");
            return (Criteria) this;
        }

        public Criteria andPassTimeGreaterThan(Date value) {
            addCriterionForJDBCDate("pass_time >", value, "passTime");
            return (Criteria) this;
        }

        public Criteria andPassTimeGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("pass_time >=", value, "passTime");
            return (Criteria) this;
        }

        public Criteria andPassTimeLessThan(Date value) {
            addCriterionForJDBCDate("pass_time <", value, "passTime");
            return (Criteria) this;
        }

        public Criteria andPassTimeLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("pass_time <=", value, "passTime");
            return (Criteria) this;
        }

        public Criteria andPassTimeIn(List<Date> values) {
            addCriterionForJDBCDate("pass_time in", values, "passTime");
            return (Criteria) this;
        }

        public Criteria andPassTimeNotIn(List<Date> values) {
            addCriterionForJDBCDate("pass_time not in", values, "passTime");
            return (Criteria) this;
        }

        public Criteria andPassTimeBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("pass_time between", value1, value2, "passTime");
            return (Criteria) this;
        }

        public Criteria andPassTimeNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("pass_time not between", value1, value2, "passTime");
            return (Criteria) this;
        }

        public Criteria andActiveTimeIsNull() {
            addCriterion("active_time is null");
            return (Criteria) this;
        }

        public Criteria andActiveTimeIsNotNull() {
            addCriterion("active_time is not null");
            return (Criteria) this;
        }

        public Criteria andActiveTimeEqualTo(Date value) {
            addCriterionForJDBCDate("active_time =", value, "activeTime");
            return (Criteria) this;
        }

        public Criteria andActiveTimeNotEqualTo(Date value) {
            addCriterionForJDBCDate("active_time <>", value, "activeTime");
            return (Criteria) this;
        }

        public Criteria andActiveTimeGreaterThan(Date value) {
            addCriterionForJDBCDate("active_time >", value, "activeTime");
            return (Criteria) this;
        }

        public Criteria andActiveTimeGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("active_time >=", value, "activeTime");
            return (Criteria) this;
        }

        public Criteria andActiveTimeLessThan(Date value) {
            addCriterionForJDBCDate("active_time <", value, "activeTime");
            return (Criteria) this;
        }

        public Criteria andActiveTimeLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("active_time <=", value, "activeTime");
            return (Criteria) this;
        }

        public Criteria andActiveTimeIn(List<Date> values) {
            addCriterionForJDBCDate("active_time in", values, "activeTime");
            return (Criteria) this;
        }

        public Criteria andActiveTimeNotIn(List<Date> values) {
            addCriterionForJDBCDate("active_time not in", values, "activeTime");
            return (Criteria) this;
        }

        public Criteria andActiveTimeBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("active_time between", value1, value2, "activeTime");
            return (Criteria) this;
        }

        public Criteria andActiveTimeNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("active_time not between", value1, value2, "activeTime");
            return (Criteria) this;
        }

        public Criteria andActiveTrainStartTimeIsNull() {
            addCriterion("active_train_start_time is null");
            return (Criteria) this;
        }

        public Criteria andActiveTrainStartTimeIsNotNull() {
            addCriterion("active_train_start_time is not null");
            return (Criteria) this;
        }

        public Criteria andActiveTrainStartTimeEqualTo(Date value) {
            addCriterionForJDBCDate("active_train_start_time =", value, "activeTrainStartTime");
            return (Criteria) this;
        }

        public Criteria andActiveTrainStartTimeNotEqualTo(Date value) {
            addCriterionForJDBCDate("active_train_start_time <>", value, "activeTrainStartTime");
            return (Criteria) this;
        }

        public Criteria andActiveTrainStartTimeGreaterThan(Date value) {
            addCriterionForJDBCDate("active_train_start_time >", value, "activeTrainStartTime");
            return (Criteria) this;
        }

        public Criteria andActiveTrainStartTimeGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("active_train_start_time >=", value, "activeTrainStartTime");
            return (Criteria) this;
        }

        public Criteria andActiveTrainStartTimeLessThan(Date value) {
            addCriterionForJDBCDate("active_train_start_time <", value, "activeTrainStartTime");
            return (Criteria) this;
        }

        public Criteria andActiveTrainStartTimeLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("active_train_start_time <=", value, "activeTrainStartTime");
            return (Criteria) this;
        }

        public Criteria andActiveTrainStartTimeIn(List<Date> values) {
            addCriterionForJDBCDate("active_train_start_time in", values, "activeTrainStartTime");
            return (Criteria) this;
        }

        public Criteria andActiveTrainStartTimeNotIn(List<Date> values) {
            addCriterionForJDBCDate("active_train_start_time not in", values, "activeTrainStartTime");
            return (Criteria) this;
        }

        public Criteria andActiveTrainStartTimeBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("active_train_start_time between", value1, value2, "activeTrainStartTime");
            return (Criteria) this;
        }

        public Criteria andActiveTrainStartTimeNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("active_train_start_time not between", value1, value2, "activeTrainStartTime");
            return (Criteria) this;
        }

        public Criteria andActiveTrainEndTimeIsNull() {
            addCriterion("active_train_end_time is null");
            return (Criteria) this;
        }

        public Criteria andActiveTrainEndTimeIsNotNull() {
            addCriterion("active_train_end_time is not null");
            return (Criteria) this;
        }

        public Criteria andActiveTrainEndTimeEqualTo(Date value) {
            addCriterionForJDBCDate("active_train_end_time =", value, "activeTrainEndTime");
            return (Criteria) this;
        }

        public Criteria andActiveTrainEndTimeNotEqualTo(Date value) {
            addCriterionForJDBCDate("active_train_end_time <>", value, "activeTrainEndTime");
            return (Criteria) this;
        }

        public Criteria andActiveTrainEndTimeGreaterThan(Date value) {
            addCriterionForJDBCDate("active_train_end_time >", value, "activeTrainEndTime");
            return (Criteria) this;
        }

        public Criteria andActiveTrainEndTimeGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("active_train_end_time >=", value, "activeTrainEndTime");
            return (Criteria) this;
        }

        public Criteria andActiveTrainEndTimeLessThan(Date value) {
            addCriterionForJDBCDate("active_train_end_time <", value, "activeTrainEndTime");
            return (Criteria) this;
        }

        public Criteria andActiveTrainEndTimeLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("active_train_end_time <=", value, "activeTrainEndTime");
            return (Criteria) this;
        }

        public Criteria andActiveTrainEndTimeIn(List<Date> values) {
            addCriterionForJDBCDate("active_train_end_time in", values, "activeTrainEndTime");
            return (Criteria) this;
        }

        public Criteria andActiveTrainEndTimeNotIn(List<Date> values) {
            addCriterionForJDBCDate("active_train_end_time not in", values, "activeTrainEndTime");
            return (Criteria) this;
        }

        public Criteria andActiveTrainEndTimeBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("active_train_end_time between", value1, value2, "activeTrainEndTime");
            return (Criteria) this;
        }

        public Criteria andActiveTrainEndTimeNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("active_train_end_time not between", value1, value2, "activeTrainEndTime");
            return (Criteria) this;
        }

        public Criteria andActiveGradeIsNull() {
            addCriterion("active_grade is null");
            return (Criteria) this;
        }

        public Criteria andActiveGradeIsNotNull() {
            addCriterion("active_grade is not null");
            return (Criteria) this;
        }

        public Criteria andActiveGradeEqualTo(String value) {
            addCriterion("active_grade =", value, "activeGrade");
            return (Criteria) this;
        }

        public Criteria andActiveGradeNotEqualTo(String value) {
            addCriterion("active_grade <>", value, "activeGrade");
            return (Criteria) this;
        }

        public Criteria andActiveGradeGreaterThan(String value) {
            addCriterion("active_grade >", value, "activeGrade");
            return (Criteria) this;
        }

        public Criteria andActiveGradeGreaterThanOrEqualTo(String value) {
            addCriterion("active_grade >=", value, "activeGrade");
            return (Criteria) this;
        }

        public Criteria andActiveGradeLessThan(String value) {
            addCriterion("active_grade <", value, "activeGrade");
            return (Criteria) this;
        }

        public Criteria andActiveGradeLessThanOrEqualTo(String value) {
            addCriterion("active_grade <=", value, "activeGrade");
            return (Criteria) this;
        }

        public Criteria andActiveGradeLike(String value) {
            addCriterion("active_grade like", value, "activeGrade");
            return (Criteria) this;
        }

        public Criteria andActiveGradeNotLike(String value) {
            addCriterion("active_grade not like", value, "activeGrade");
            return (Criteria) this;
        }

        public Criteria andActiveGradeIn(List<String> values) {
            addCriterion("active_grade in", values, "activeGrade");
            return (Criteria) this;
        }

        public Criteria andActiveGradeNotIn(List<String> values) {
            addCriterion("active_grade not in", values, "activeGrade");
            return (Criteria) this;
        }

        public Criteria andActiveGradeBetween(String value1, String value2) {
            addCriterion("active_grade between", value1, value2, "activeGrade");
            return (Criteria) this;
        }

        public Criteria andActiveGradeNotBetween(String value1, String value2) {
            addCriterion("active_grade not between", value1, value2, "activeGrade");
            return (Criteria) this;
        }

        public Criteria andContactUserIdsIsNull() {
            addCriterion("contact_user_ids is null");
            return (Criteria) this;
        }

        public Criteria andContactUserIdsIsNotNull() {
            addCriterion("contact_user_ids is not null");
            return (Criteria) this;
        }

        public Criteria andContactUserIdsEqualTo(String value) {
            addCriterion("contact_user_ids =", value, "contactUserIds");
            return (Criteria) this;
        }

        public Criteria andContactUserIdsNotEqualTo(String value) {
            addCriterion("contact_user_ids <>", value, "contactUserIds");
            return (Criteria) this;
        }

        public Criteria andContactUserIdsGreaterThan(String value) {
            addCriterion("contact_user_ids >", value, "contactUserIds");
            return (Criteria) this;
        }

        public Criteria andContactUserIdsGreaterThanOrEqualTo(String value) {
            addCriterion("contact_user_ids >=", value, "contactUserIds");
            return (Criteria) this;
        }

        public Criteria andContactUserIdsLessThan(String value) {
            addCriterion("contact_user_ids <", value, "contactUserIds");
            return (Criteria) this;
        }

        public Criteria andContactUserIdsLessThanOrEqualTo(String value) {
            addCriterion("contact_user_ids <=", value, "contactUserIds");
            return (Criteria) this;
        }

        public Criteria andContactUserIdsLike(String value) {
            addCriterion("contact_user_ids like", value, "contactUserIds");
            return (Criteria) this;
        }

        public Criteria andContactUserIdsNotLike(String value) {
            addCriterion("contact_user_ids not like", value, "contactUserIds");
            return (Criteria) this;
        }

        public Criteria andContactUserIdsIn(List<String> values) {
            addCriterion("contact_user_ids in", values, "contactUserIds");
            return (Criteria) this;
        }

        public Criteria andContactUserIdsNotIn(List<String> values) {
            addCriterion("contact_user_ids not in", values, "contactUserIds");
            return (Criteria) this;
        }

        public Criteria andContactUserIdsBetween(String value1, String value2) {
            addCriterion("contact_user_ids between", value1, value2, "contactUserIds");
            return (Criteria) this;
        }

        public Criteria andContactUserIdsNotBetween(String value1, String value2) {
            addCriterion("contact_user_ids not between", value1, value2, "contactUserIds");
            return (Criteria) this;
        }

        public Criteria andContactUsersIsNull() {
            addCriterion("contact_users is null");
            return (Criteria) this;
        }

        public Criteria andContactUsersIsNotNull() {
            addCriterion("contact_users is not null");
            return (Criteria) this;
        }

        public Criteria andContactUsersEqualTo(String value) {
            addCriterion("contact_users =", value, "contactUsers");
            return (Criteria) this;
        }

        public Criteria andContactUsersNotEqualTo(String value) {
            addCriterion("contact_users <>", value, "contactUsers");
            return (Criteria) this;
        }

        public Criteria andContactUsersGreaterThan(String value) {
            addCriterion("contact_users >", value, "contactUsers");
            return (Criteria) this;
        }

        public Criteria andContactUsersGreaterThanOrEqualTo(String value) {
            addCriterion("contact_users >=", value, "contactUsers");
            return (Criteria) this;
        }

        public Criteria andContactUsersLessThan(String value) {
            addCriterion("contact_users <", value, "contactUsers");
            return (Criteria) this;
        }

        public Criteria andContactUsersLessThanOrEqualTo(String value) {
            addCriterion("contact_users <=", value, "contactUsers");
            return (Criteria) this;
        }

        public Criteria andContactUsersLike(String value) {
            addCriterion("contact_users like", value, "contactUsers");
            return (Criteria) this;
        }

        public Criteria andContactUsersNotLike(String value) {
            addCriterion("contact_users not like", value, "contactUsers");
            return (Criteria) this;
        }

        public Criteria andContactUsersIn(List<String> values) {
            addCriterion("contact_users in", values, "contactUsers");
            return (Criteria) this;
        }

        public Criteria andContactUsersNotIn(List<String> values) {
            addCriterion("contact_users not in", values, "contactUsers");
            return (Criteria) this;
        }

        public Criteria andContactUsersBetween(String value1, String value2) {
            addCriterion("contact_users between", value1, value2, "contactUsers");
            return (Criteria) this;
        }

        public Criteria andContactUsersNotBetween(String value1, String value2) {
            addCriterion("contact_users not between", value1, value2, "contactUsers");
            return (Criteria) this;
        }

        public Criteria andCandidateTimeIsNull() {
            addCriterion("candidate_time is null");
            return (Criteria) this;
        }

        public Criteria andCandidateTimeIsNotNull() {
            addCriterion("candidate_time is not null");
            return (Criteria) this;
        }

        public Criteria andCandidateTimeEqualTo(Date value) {
            addCriterionForJDBCDate("candidate_time =", value, "candidateTime");
            return (Criteria) this;
        }

        public Criteria andCandidateTimeNotEqualTo(Date value) {
            addCriterionForJDBCDate("candidate_time <>", value, "candidateTime");
            return (Criteria) this;
        }

        public Criteria andCandidateTimeGreaterThan(Date value) {
            addCriterionForJDBCDate("candidate_time >", value, "candidateTime");
            return (Criteria) this;
        }

        public Criteria andCandidateTimeGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("candidate_time >=", value, "candidateTime");
            return (Criteria) this;
        }

        public Criteria andCandidateTimeLessThan(Date value) {
            addCriterionForJDBCDate("candidate_time <", value, "candidateTime");
            return (Criteria) this;
        }

        public Criteria andCandidateTimeLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("candidate_time <=", value, "candidateTime");
            return (Criteria) this;
        }

        public Criteria andCandidateTimeIn(List<Date> values) {
            addCriterionForJDBCDate("candidate_time in", values, "candidateTime");
            return (Criteria) this;
        }

        public Criteria andCandidateTimeNotIn(List<Date> values) {
            addCriterionForJDBCDate("candidate_time not in", values, "candidateTime");
            return (Criteria) this;
        }

        public Criteria andCandidateTimeBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("candidate_time between", value1, value2, "candidateTime");
            return (Criteria) this;
        }

        public Criteria andCandidateTimeNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("candidate_time not between", value1, value2, "candidateTime");
            return (Criteria) this;
        }

        public Criteria andCandidateTrainStartTimeIsNull() {
            addCriterion("candidate_train_start_time is null");
            return (Criteria) this;
        }

        public Criteria andCandidateTrainStartTimeIsNotNull() {
            addCriterion("candidate_train_start_time is not null");
            return (Criteria) this;
        }

        public Criteria andCandidateTrainStartTimeEqualTo(Date value) {
            addCriterionForJDBCDate("candidate_train_start_time =", value, "candidateTrainStartTime");
            return (Criteria) this;
        }

        public Criteria andCandidateTrainStartTimeNotEqualTo(Date value) {
            addCriterionForJDBCDate("candidate_train_start_time <>", value, "candidateTrainStartTime");
            return (Criteria) this;
        }

        public Criteria andCandidateTrainStartTimeGreaterThan(Date value) {
            addCriterionForJDBCDate("candidate_train_start_time >", value, "candidateTrainStartTime");
            return (Criteria) this;
        }

        public Criteria andCandidateTrainStartTimeGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("candidate_train_start_time >=", value, "candidateTrainStartTime");
            return (Criteria) this;
        }

        public Criteria andCandidateTrainStartTimeLessThan(Date value) {
            addCriterionForJDBCDate("candidate_train_start_time <", value, "candidateTrainStartTime");
            return (Criteria) this;
        }

        public Criteria andCandidateTrainStartTimeLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("candidate_train_start_time <=", value, "candidateTrainStartTime");
            return (Criteria) this;
        }

        public Criteria andCandidateTrainStartTimeIn(List<Date> values) {
            addCriterionForJDBCDate("candidate_train_start_time in", values, "candidateTrainStartTime");
            return (Criteria) this;
        }

        public Criteria andCandidateTrainStartTimeNotIn(List<Date> values) {
            addCriterionForJDBCDate("candidate_train_start_time not in", values, "candidateTrainStartTime");
            return (Criteria) this;
        }

        public Criteria andCandidateTrainStartTimeBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("candidate_train_start_time between", value1, value2, "candidateTrainStartTime");
            return (Criteria) this;
        }

        public Criteria andCandidateTrainStartTimeNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("candidate_train_start_time not between", value1, value2, "candidateTrainStartTime");
            return (Criteria) this;
        }

        public Criteria andCandidateTrainEndTimeIsNull() {
            addCriterion("candidate_train_end_time is null");
            return (Criteria) this;
        }

        public Criteria andCandidateTrainEndTimeIsNotNull() {
            addCriterion("candidate_train_end_time is not null");
            return (Criteria) this;
        }

        public Criteria andCandidateTrainEndTimeEqualTo(Date value) {
            addCriterionForJDBCDate("candidate_train_end_time =", value, "candidateTrainEndTime");
            return (Criteria) this;
        }

        public Criteria andCandidateTrainEndTimeNotEqualTo(Date value) {
            addCriterionForJDBCDate("candidate_train_end_time <>", value, "candidateTrainEndTime");
            return (Criteria) this;
        }

        public Criteria andCandidateTrainEndTimeGreaterThan(Date value) {
            addCriterionForJDBCDate("candidate_train_end_time >", value, "candidateTrainEndTime");
            return (Criteria) this;
        }

        public Criteria andCandidateTrainEndTimeGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("candidate_train_end_time >=", value, "candidateTrainEndTime");
            return (Criteria) this;
        }

        public Criteria andCandidateTrainEndTimeLessThan(Date value) {
            addCriterionForJDBCDate("candidate_train_end_time <", value, "candidateTrainEndTime");
            return (Criteria) this;
        }

        public Criteria andCandidateTrainEndTimeLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("candidate_train_end_time <=", value, "candidateTrainEndTime");
            return (Criteria) this;
        }

        public Criteria andCandidateTrainEndTimeIn(List<Date> values) {
            addCriterionForJDBCDate("candidate_train_end_time in", values, "candidateTrainEndTime");
            return (Criteria) this;
        }

        public Criteria andCandidateTrainEndTimeNotIn(List<Date> values) {
            addCriterionForJDBCDate("candidate_train_end_time not in", values, "candidateTrainEndTime");
            return (Criteria) this;
        }

        public Criteria andCandidateTrainEndTimeBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("candidate_train_end_time between", value1, value2, "candidateTrainEndTime");
            return (Criteria) this;
        }

        public Criteria andCandidateTrainEndTimeNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("candidate_train_end_time not between", value1, value2, "candidateTrainEndTime");
            return (Criteria) this;
        }

        public Criteria andCandidateGradeIsNull() {
            addCriterion("candidate_grade is null");
            return (Criteria) this;
        }

        public Criteria andCandidateGradeIsNotNull() {
            addCriterion("candidate_grade is not null");
            return (Criteria) this;
        }

        public Criteria andCandidateGradeEqualTo(String value) {
            addCriterion("candidate_grade =", value, "candidateGrade");
            return (Criteria) this;
        }

        public Criteria andCandidateGradeNotEqualTo(String value) {
            addCriterion("candidate_grade <>", value, "candidateGrade");
            return (Criteria) this;
        }

        public Criteria andCandidateGradeGreaterThan(String value) {
            addCriterion("candidate_grade >", value, "candidateGrade");
            return (Criteria) this;
        }

        public Criteria andCandidateGradeGreaterThanOrEqualTo(String value) {
            addCriterion("candidate_grade >=", value, "candidateGrade");
            return (Criteria) this;
        }

        public Criteria andCandidateGradeLessThan(String value) {
            addCriterion("candidate_grade <", value, "candidateGrade");
            return (Criteria) this;
        }

        public Criteria andCandidateGradeLessThanOrEqualTo(String value) {
            addCriterion("candidate_grade <=", value, "candidateGrade");
            return (Criteria) this;
        }

        public Criteria andCandidateGradeLike(String value) {
            addCriterion("candidate_grade like", value, "candidateGrade");
            return (Criteria) this;
        }

        public Criteria andCandidateGradeNotLike(String value) {
            addCriterion("candidate_grade not like", value, "candidateGrade");
            return (Criteria) this;
        }

        public Criteria andCandidateGradeIn(List<String> values) {
            addCriterion("candidate_grade in", values, "candidateGrade");
            return (Criteria) this;
        }

        public Criteria andCandidateGradeNotIn(List<String> values) {
            addCriterion("candidate_grade not in", values, "candidateGrade");
            return (Criteria) this;
        }

        public Criteria andCandidateGradeBetween(String value1, String value2) {
            addCriterion("candidate_grade between", value1, value2, "candidateGrade");
            return (Criteria) this;
        }

        public Criteria andCandidateGradeNotBetween(String value1, String value2) {
            addCriterion("candidate_grade not between", value1, value2, "candidateGrade");
            return (Criteria) this;
        }

        public Criteria andCandidateStatusIsNull() {
            addCriterion("candidate_status is null");
            return (Criteria) this;
        }

        public Criteria andCandidateStatusIsNotNull() {
            addCriterion("candidate_status is not null");
            return (Criteria) this;
        }

        public Criteria andCandidateStatusEqualTo(Byte value) {
            addCriterion("candidate_status =", value, "candidateStatus");
            return (Criteria) this;
        }

        public Criteria andCandidateStatusNotEqualTo(Byte value) {
            addCriterion("candidate_status <>", value, "candidateStatus");
            return (Criteria) this;
        }

        public Criteria andCandidateStatusGreaterThan(Byte value) {
            addCriterion("candidate_status >", value, "candidateStatus");
            return (Criteria) this;
        }

        public Criteria andCandidateStatusGreaterThanOrEqualTo(Byte value) {
            addCriterion("candidate_status >=", value, "candidateStatus");
            return (Criteria) this;
        }

        public Criteria andCandidateStatusLessThan(Byte value) {
            addCriterion("candidate_status <", value, "candidateStatus");
            return (Criteria) this;
        }

        public Criteria andCandidateStatusLessThanOrEqualTo(Byte value) {
            addCriterion("candidate_status <=", value, "candidateStatus");
            return (Criteria) this;
        }

        public Criteria andCandidateStatusIn(List<Byte> values) {
            addCriterion("candidate_status in", values, "candidateStatus");
            return (Criteria) this;
        }

        public Criteria andCandidateStatusNotIn(List<Byte> values) {
            addCriterion("candidate_status not in", values, "candidateStatus");
            return (Criteria) this;
        }

        public Criteria andCandidateStatusBetween(Byte value1, Byte value2) {
            addCriterion("candidate_status between", value1, value2, "candidateStatus");
            return (Criteria) this;
        }

        public Criteria andCandidateStatusNotBetween(Byte value1, Byte value2) {
            addCriterion("candidate_status not between", value1, value2, "candidateStatus");
            return (Criteria) this;
        }

        public Criteria andSponsorUserIdsIsNull() {
            addCriterion("sponsor_user_ids is null");
            return (Criteria) this;
        }

        public Criteria andSponsorUserIdsIsNotNull() {
            addCriterion("sponsor_user_ids is not null");
            return (Criteria) this;
        }

        public Criteria andSponsorUserIdsEqualTo(String value) {
            addCriterion("sponsor_user_ids =", value, "sponsorUserIds");
            return (Criteria) this;
        }

        public Criteria andSponsorUserIdsNotEqualTo(String value) {
            addCriterion("sponsor_user_ids <>", value, "sponsorUserIds");
            return (Criteria) this;
        }

        public Criteria andSponsorUserIdsGreaterThan(String value) {
            addCriterion("sponsor_user_ids >", value, "sponsorUserIds");
            return (Criteria) this;
        }

        public Criteria andSponsorUserIdsGreaterThanOrEqualTo(String value) {
            addCriterion("sponsor_user_ids >=", value, "sponsorUserIds");
            return (Criteria) this;
        }

        public Criteria andSponsorUserIdsLessThan(String value) {
            addCriterion("sponsor_user_ids <", value, "sponsorUserIds");
            return (Criteria) this;
        }

        public Criteria andSponsorUserIdsLessThanOrEqualTo(String value) {
            addCriterion("sponsor_user_ids <=", value, "sponsorUserIds");
            return (Criteria) this;
        }

        public Criteria andSponsorUserIdsLike(String value) {
            addCriterion("sponsor_user_ids like", value, "sponsorUserIds");
            return (Criteria) this;
        }

        public Criteria andSponsorUserIdsNotLike(String value) {
            addCriterion("sponsor_user_ids not like", value, "sponsorUserIds");
            return (Criteria) this;
        }

        public Criteria andSponsorUserIdsIn(List<String> values) {
            addCriterion("sponsor_user_ids in", values, "sponsorUserIds");
            return (Criteria) this;
        }

        public Criteria andSponsorUserIdsNotIn(List<String> values) {
            addCriterion("sponsor_user_ids not in", values, "sponsorUserIds");
            return (Criteria) this;
        }

        public Criteria andSponsorUserIdsBetween(String value1, String value2) {
            addCriterion("sponsor_user_ids between", value1, value2, "sponsorUserIds");
            return (Criteria) this;
        }

        public Criteria andSponsorUserIdsNotBetween(String value1, String value2) {
            addCriterion("sponsor_user_ids not between", value1, value2, "sponsorUserIds");
            return (Criteria) this;
        }

        public Criteria andSponsorUsersIsNull() {
            addCriterion("sponsor_users is null");
            return (Criteria) this;
        }

        public Criteria andSponsorUsersIsNotNull() {
            addCriterion("sponsor_users is not null");
            return (Criteria) this;
        }

        public Criteria andSponsorUsersEqualTo(String value) {
            addCriterion("sponsor_users =", value, "sponsorUsers");
            return (Criteria) this;
        }

        public Criteria andSponsorUsersNotEqualTo(String value) {
            addCriterion("sponsor_users <>", value, "sponsorUsers");
            return (Criteria) this;
        }

        public Criteria andSponsorUsersGreaterThan(String value) {
            addCriterion("sponsor_users >", value, "sponsorUsers");
            return (Criteria) this;
        }

        public Criteria andSponsorUsersGreaterThanOrEqualTo(String value) {
            addCriterion("sponsor_users >=", value, "sponsorUsers");
            return (Criteria) this;
        }

        public Criteria andSponsorUsersLessThan(String value) {
            addCriterion("sponsor_users <", value, "sponsorUsers");
            return (Criteria) this;
        }

        public Criteria andSponsorUsersLessThanOrEqualTo(String value) {
            addCriterion("sponsor_users <=", value, "sponsorUsers");
            return (Criteria) this;
        }

        public Criteria andSponsorUsersLike(String value) {
            addCriterion("sponsor_users like", value, "sponsorUsers");
            return (Criteria) this;
        }

        public Criteria andSponsorUsersNotLike(String value) {
            addCriterion("sponsor_users not like", value, "sponsorUsers");
            return (Criteria) this;
        }

        public Criteria andSponsorUsersIn(List<String> values) {
            addCriterion("sponsor_users in", values, "sponsorUsers");
            return (Criteria) this;
        }

        public Criteria andSponsorUsersNotIn(List<String> values) {
            addCriterion("sponsor_users not in", values, "sponsorUsers");
            return (Criteria) this;
        }

        public Criteria andSponsorUsersBetween(String value1, String value2) {
            addCriterion("sponsor_users between", value1, value2, "sponsorUsers");
            return (Criteria) this;
        }

        public Criteria andSponsorUsersNotBetween(String value1, String value2) {
            addCriterion("sponsor_users not between", value1, value2, "sponsorUsers");
            return (Criteria) this;
        }

        public Criteria andPlanTimeIsNull() {
            addCriterion("plan_time is null");
            return (Criteria) this;
        }

        public Criteria andPlanTimeIsNotNull() {
            addCriterion("plan_time is not null");
            return (Criteria) this;
        }

        public Criteria andPlanTimeEqualTo(Date value) {
            addCriterionForJDBCDate("plan_time =", value, "planTime");
            return (Criteria) this;
        }

        public Criteria andPlanTimeNotEqualTo(Date value) {
            addCriterionForJDBCDate("plan_time <>", value, "planTime");
            return (Criteria) this;
        }

        public Criteria andPlanTimeGreaterThan(Date value) {
            addCriterionForJDBCDate("plan_time >", value, "planTime");
            return (Criteria) this;
        }

        public Criteria andPlanTimeGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("plan_time >=", value, "planTime");
            return (Criteria) this;
        }

        public Criteria andPlanTimeLessThan(Date value) {
            addCriterionForJDBCDate("plan_time <", value, "planTime");
            return (Criteria) this;
        }

        public Criteria andPlanTimeLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("plan_time <=", value, "planTime");
            return (Criteria) this;
        }

        public Criteria andPlanTimeIn(List<Date> values) {
            addCriterionForJDBCDate("plan_time in", values, "planTime");
            return (Criteria) this;
        }

        public Criteria andPlanTimeNotIn(List<Date> values) {
            addCriterionForJDBCDate("plan_time not in", values, "planTime");
            return (Criteria) this;
        }

        public Criteria andPlanTimeBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("plan_time between", value1, value2, "planTime");
            return (Criteria) this;
        }

        public Criteria andPlanTimeNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("plan_time not between", value1, value2, "planTime");
            return (Criteria) this;
        }

        public Criteria andPlanStatusIsNull() {
            addCriterion("plan_status is null");
            return (Criteria) this;
        }

        public Criteria andPlanStatusIsNotNull() {
            addCriterion("plan_status is not null");
            return (Criteria) this;
        }

        public Criteria andPlanStatusEqualTo(Byte value) {
            addCriterion("plan_status =", value, "planStatus");
            return (Criteria) this;
        }

        public Criteria andPlanStatusNotEqualTo(Byte value) {
            addCriterion("plan_status <>", value, "planStatus");
            return (Criteria) this;
        }

        public Criteria andPlanStatusGreaterThan(Byte value) {
            addCriterion("plan_status >", value, "planStatus");
            return (Criteria) this;
        }

        public Criteria andPlanStatusGreaterThanOrEqualTo(Byte value) {
            addCriterion("plan_status >=", value, "planStatus");
            return (Criteria) this;
        }

        public Criteria andPlanStatusLessThan(Byte value) {
            addCriterion("plan_status <", value, "planStatus");
            return (Criteria) this;
        }

        public Criteria andPlanStatusLessThanOrEqualTo(Byte value) {
            addCriterion("plan_status <=", value, "planStatus");
            return (Criteria) this;
        }

        public Criteria andPlanStatusIn(List<Byte> values) {
            addCriterion("plan_status in", values, "planStatus");
            return (Criteria) this;
        }

        public Criteria andPlanStatusNotIn(List<Byte> values) {
            addCriterion("plan_status not in", values, "planStatus");
            return (Criteria) this;
        }

        public Criteria andPlanStatusBetween(Byte value1, Byte value2) {
            addCriterion("plan_status between", value1, value2, "planStatus");
            return (Criteria) this;
        }

        public Criteria andPlanStatusNotBetween(Byte value1, Byte value2) {
            addCriterion("plan_status not between", value1, value2, "planStatus");
            return (Criteria) this;
        }

        public Criteria andDrawTimeIsNull() {
            addCriterion("draw_time is null");
            return (Criteria) this;
        }

        public Criteria andDrawTimeIsNotNull() {
            addCriterion("draw_time is not null");
            return (Criteria) this;
        }

        public Criteria andDrawTimeEqualTo(Date value) {
            addCriterionForJDBCDate("draw_time =", value, "drawTime");
            return (Criteria) this;
        }

        public Criteria andDrawTimeNotEqualTo(Date value) {
            addCriterionForJDBCDate("draw_time <>", value, "drawTime");
            return (Criteria) this;
        }

        public Criteria andDrawTimeGreaterThan(Date value) {
            addCriterionForJDBCDate("draw_time >", value, "drawTime");
            return (Criteria) this;
        }

        public Criteria andDrawTimeGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("draw_time >=", value, "drawTime");
            return (Criteria) this;
        }

        public Criteria andDrawTimeLessThan(Date value) {
            addCriterionForJDBCDate("draw_time <", value, "drawTime");
            return (Criteria) this;
        }

        public Criteria andDrawTimeLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("draw_time <=", value, "drawTime");
            return (Criteria) this;
        }

        public Criteria andDrawTimeIn(List<Date> values) {
            addCriterionForJDBCDate("draw_time in", values, "drawTime");
            return (Criteria) this;
        }

        public Criteria andDrawTimeNotIn(List<Date> values) {
            addCriterionForJDBCDate("draw_time not in", values, "drawTime");
            return (Criteria) this;
        }

        public Criteria andDrawTimeBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("draw_time between", value1, value2, "drawTime");
            return (Criteria) this;
        }

        public Criteria andDrawTimeNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("draw_time not between", value1, value2, "drawTime");
            return (Criteria) this;
        }

        public Criteria andDrawStatusIsNull() {
            addCriterion("draw_status is null");
            return (Criteria) this;
        }

        public Criteria andDrawStatusIsNotNull() {
            addCriterion("draw_status is not null");
            return (Criteria) this;
        }

        public Criteria andDrawStatusEqualTo(Byte value) {
            addCriterion("draw_status =", value, "drawStatus");
            return (Criteria) this;
        }

        public Criteria andDrawStatusNotEqualTo(Byte value) {
            addCriterion("draw_status <>", value, "drawStatus");
            return (Criteria) this;
        }

        public Criteria andDrawStatusGreaterThan(Byte value) {
            addCriterion("draw_status >", value, "drawStatus");
            return (Criteria) this;
        }

        public Criteria andDrawStatusGreaterThanOrEqualTo(Byte value) {
            addCriterion("draw_status >=", value, "drawStatus");
            return (Criteria) this;
        }

        public Criteria andDrawStatusLessThan(Byte value) {
            addCriterion("draw_status <", value, "drawStatus");
            return (Criteria) this;
        }

        public Criteria andDrawStatusLessThanOrEqualTo(Byte value) {
            addCriterion("draw_status <=", value, "drawStatus");
            return (Criteria) this;
        }

        public Criteria andDrawStatusIn(List<Byte> values) {
            addCriterion("draw_status in", values, "drawStatus");
            return (Criteria) this;
        }

        public Criteria andDrawStatusNotIn(List<Byte> values) {
            addCriterion("draw_status not in", values, "drawStatus");
            return (Criteria) this;
        }

        public Criteria andDrawStatusBetween(Byte value1, Byte value2) {
            addCriterion("draw_status between", value1, value2, "drawStatus");
            return (Criteria) this;
        }

        public Criteria andDrawStatusNotBetween(Byte value1, Byte value2) {
            addCriterion("draw_status not between", value1, value2, "drawStatus");
            return (Criteria) this;
        }

        public Criteria andApplySnIdIsNull() {
            addCriterion("apply_sn_id is null");
            return (Criteria) this;
        }

        public Criteria andApplySnIdIsNotNull() {
            addCriterion("apply_sn_id is not null");
            return (Criteria) this;
        }

        public Criteria andApplySnIdEqualTo(Integer value) {
            addCriterion("apply_sn_id =", value, "applySnId");
            return (Criteria) this;
        }

        public Criteria andApplySnIdNotEqualTo(Integer value) {
            addCriterion("apply_sn_id <>", value, "applySnId");
            return (Criteria) this;
        }

        public Criteria andApplySnIdGreaterThan(Integer value) {
            addCriterion("apply_sn_id >", value, "applySnId");
            return (Criteria) this;
        }

        public Criteria andApplySnIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("apply_sn_id >=", value, "applySnId");
            return (Criteria) this;
        }

        public Criteria andApplySnIdLessThan(Integer value) {
            addCriterion("apply_sn_id <", value, "applySnId");
            return (Criteria) this;
        }

        public Criteria andApplySnIdLessThanOrEqualTo(Integer value) {
            addCriterion("apply_sn_id <=", value, "applySnId");
            return (Criteria) this;
        }

        public Criteria andApplySnIdIn(List<Integer> values) {
            addCriterion("apply_sn_id in", values, "applySnId");
            return (Criteria) this;
        }

        public Criteria andApplySnIdNotIn(List<Integer> values) {
            addCriterion("apply_sn_id not in", values, "applySnId");
            return (Criteria) this;
        }

        public Criteria andApplySnIdBetween(Integer value1, Integer value2) {
            addCriterion("apply_sn_id between", value1, value2, "applySnId");
            return (Criteria) this;
        }

        public Criteria andApplySnIdNotBetween(Integer value1, Integer value2) {
            addCriterion("apply_sn_id not between", value1, value2, "applySnId");
            return (Criteria) this;
        }

        public Criteria andApplySnIsNull() {
            addCriterion("apply_sn is null");
            return (Criteria) this;
        }

        public Criteria andApplySnIsNotNull() {
            addCriterion("apply_sn is not null");
            return (Criteria) this;
        }

        public Criteria andApplySnEqualTo(String value) {
            addCriterion("apply_sn =", value, "applySn");
            return (Criteria) this;
        }

        public Criteria andApplySnNotEqualTo(String value) {
            addCriterion("apply_sn <>", value, "applySn");
            return (Criteria) this;
        }

        public Criteria andApplySnGreaterThan(String value) {
            addCriterion("apply_sn >", value, "applySn");
            return (Criteria) this;
        }

        public Criteria andApplySnGreaterThanOrEqualTo(String value) {
            addCriterion("apply_sn >=", value, "applySn");
            return (Criteria) this;
        }

        public Criteria andApplySnLessThan(String value) {
            addCriterion("apply_sn <", value, "applySn");
            return (Criteria) this;
        }

        public Criteria andApplySnLessThanOrEqualTo(String value) {
            addCriterion("apply_sn <=", value, "applySn");
            return (Criteria) this;
        }

        public Criteria andApplySnLike(String value) {
            addCriterion("apply_sn like", value, "applySn");
            return (Criteria) this;
        }

        public Criteria andApplySnNotLike(String value) {
            addCriterion("apply_sn not like", value, "applySn");
            return (Criteria) this;
        }

        public Criteria andApplySnIn(List<String> values) {
            addCriterion("apply_sn in", values, "applySn");
            return (Criteria) this;
        }

        public Criteria andApplySnNotIn(List<String> values) {
            addCriterion("apply_sn not in", values, "applySn");
            return (Criteria) this;
        }

        public Criteria andApplySnBetween(String value1, String value2) {
            addCriterion("apply_sn between", value1, value2, "applySn");
            return (Criteria) this;
        }

        public Criteria andApplySnNotBetween(String value1, String value2) {
            addCriterion("apply_sn not between", value1, value2, "applySn");
            return (Criteria) this;
        }

        public Criteria andGrowPublicIdIsNull() {
            addCriterion("grow_public_id is null");
            return (Criteria) this;
        }

        public Criteria andGrowPublicIdIsNotNull() {
            addCriterion("grow_public_id is not null");
            return (Criteria) this;
        }

        public Criteria andGrowPublicIdEqualTo(Integer value) {
            addCriterion("grow_public_id =", value, "growPublicId");
            return (Criteria) this;
        }

        public Criteria andGrowPublicIdNotEqualTo(Integer value) {
            addCriterion("grow_public_id <>", value, "growPublicId");
            return (Criteria) this;
        }

        public Criteria andGrowPublicIdGreaterThan(Integer value) {
            addCriterion("grow_public_id >", value, "growPublicId");
            return (Criteria) this;
        }

        public Criteria andGrowPublicIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("grow_public_id >=", value, "growPublicId");
            return (Criteria) this;
        }

        public Criteria andGrowPublicIdLessThan(Integer value) {
            addCriterion("grow_public_id <", value, "growPublicId");
            return (Criteria) this;
        }

        public Criteria andGrowPublicIdLessThanOrEqualTo(Integer value) {
            addCriterion("grow_public_id <=", value, "growPublicId");
            return (Criteria) this;
        }

        public Criteria andGrowPublicIdIn(List<Integer> values) {
            addCriterion("grow_public_id in", values, "growPublicId");
            return (Criteria) this;
        }

        public Criteria andGrowPublicIdNotIn(List<Integer> values) {
            addCriterion("grow_public_id not in", values, "growPublicId");
            return (Criteria) this;
        }

        public Criteria andGrowPublicIdBetween(Integer value1, Integer value2) {
            addCriterion("grow_public_id between", value1, value2, "growPublicId");
            return (Criteria) this;
        }

        public Criteria andGrowPublicIdNotBetween(Integer value1, Integer value2) {
            addCriterion("grow_public_id not between", value1, value2, "growPublicId");
            return (Criteria) this;
        }

        public Criteria andGrowTimeIsNull() {
            addCriterion("grow_time is null");
            return (Criteria) this;
        }

        public Criteria andGrowTimeIsNotNull() {
            addCriterion("grow_time is not null");
            return (Criteria) this;
        }

        public Criteria andGrowTimeEqualTo(Date value) {
            addCriterionForJDBCDate("grow_time =", value, "growTime");
            return (Criteria) this;
        }

        public Criteria andGrowTimeNotEqualTo(Date value) {
            addCriterionForJDBCDate("grow_time <>", value, "growTime");
            return (Criteria) this;
        }

        public Criteria andGrowTimeGreaterThan(Date value) {
            addCriterionForJDBCDate("grow_time >", value, "growTime");
            return (Criteria) this;
        }

        public Criteria andGrowTimeGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("grow_time >=", value, "growTime");
            return (Criteria) this;
        }

        public Criteria andGrowTimeLessThan(Date value) {
            addCriterionForJDBCDate("grow_time <", value, "growTime");
            return (Criteria) this;
        }

        public Criteria andGrowTimeLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("grow_time <=", value, "growTime");
            return (Criteria) this;
        }

        public Criteria andGrowTimeIn(List<Date> values) {
            addCriterionForJDBCDate("grow_time in", values, "growTime");
            return (Criteria) this;
        }

        public Criteria andGrowTimeNotIn(List<Date> values) {
            addCriterionForJDBCDate("grow_time not in", values, "growTime");
            return (Criteria) this;
        }

        public Criteria andGrowTimeBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("grow_time between", value1, value2, "growTime");
            return (Criteria) this;
        }

        public Criteria andGrowTimeNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("grow_time not between", value1, value2, "growTime");
            return (Criteria) this;
        }

        public Criteria andGrowStatusIsNull() {
            addCriterion("grow_status is null");
            return (Criteria) this;
        }

        public Criteria andGrowStatusIsNotNull() {
            addCriterion("grow_status is not null");
            return (Criteria) this;
        }

        public Criteria andGrowStatusEqualTo(Byte value) {
            addCriterion("grow_status =", value, "growStatus");
            return (Criteria) this;
        }

        public Criteria andGrowStatusNotEqualTo(Byte value) {
            addCriterion("grow_status <>", value, "growStatus");
            return (Criteria) this;
        }

        public Criteria andGrowStatusGreaterThan(Byte value) {
            addCriterion("grow_status >", value, "growStatus");
            return (Criteria) this;
        }

        public Criteria andGrowStatusGreaterThanOrEqualTo(Byte value) {
            addCriterion("grow_status >=", value, "growStatus");
            return (Criteria) this;
        }

        public Criteria andGrowStatusLessThan(Byte value) {
            addCriterion("grow_status <", value, "growStatus");
            return (Criteria) this;
        }

        public Criteria andGrowStatusLessThanOrEqualTo(Byte value) {
            addCriterion("grow_status <=", value, "growStatus");
            return (Criteria) this;
        }

        public Criteria andGrowStatusIn(List<Byte> values) {
            addCriterion("grow_status in", values, "growStatus");
            return (Criteria) this;
        }

        public Criteria andGrowStatusNotIn(List<Byte> values) {
            addCriterion("grow_status not in", values, "growStatus");
            return (Criteria) this;
        }

        public Criteria andGrowStatusBetween(Byte value1, Byte value2) {
            addCriterion("grow_status between", value1, value2, "growStatus");
            return (Criteria) this;
        }

        public Criteria andGrowStatusNotBetween(Byte value1, Byte value2) {
            addCriterion("grow_status not between", value1, value2, "growStatus");
            return (Criteria) this;
        }

        public Criteria andGrowContactUserIdsIsNull() {
            addCriterion("grow_contact_user_ids is null");
            return (Criteria) this;
        }

        public Criteria andGrowContactUserIdsIsNotNull() {
            addCriterion("grow_contact_user_ids is not null");
            return (Criteria) this;
        }

        public Criteria andGrowContactUserIdsEqualTo(String value) {
            addCriterion("grow_contact_user_ids =", value, "growContactUserIds");
            return (Criteria) this;
        }

        public Criteria andGrowContactUserIdsNotEqualTo(String value) {
            addCriterion("grow_contact_user_ids <>", value, "growContactUserIds");
            return (Criteria) this;
        }

        public Criteria andGrowContactUserIdsGreaterThan(String value) {
            addCriterion("grow_contact_user_ids >", value, "growContactUserIds");
            return (Criteria) this;
        }

        public Criteria andGrowContactUserIdsGreaterThanOrEqualTo(String value) {
            addCriterion("grow_contact_user_ids >=", value, "growContactUserIds");
            return (Criteria) this;
        }

        public Criteria andGrowContactUserIdsLessThan(String value) {
            addCriterion("grow_contact_user_ids <", value, "growContactUserIds");
            return (Criteria) this;
        }

        public Criteria andGrowContactUserIdsLessThanOrEqualTo(String value) {
            addCriterion("grow_contact_user_ids <=", value, "growContactUserIds");
            return (Criteria) this;
        }

        public Criteria andGrowContactUserIdsLike(String value) {
            addCriterion("grow_contact_user_ids like", value, "growContactUserIds");
            return (Criteria) this;
        }

        public Criteria andGrowContactUserIdsNotLike(String value) {
            addCriterion("grow_contact_user_ids not like", value, "growContactUserIds");
            return (Criteria) this;
        }

        public Criteria andGrowContactUserIdsIn(List<String> values) {
            addCriterion("grow_contact_user_ids in", values, "growContactUserIds");
            return (Criteria) this;
        }

        public Criteria andGrowContactUserIdsNotIn(List<String> values) {
            addCriterion("grow_contact_user_ids not in", values, "growContactUserIds");
            return (Criteria) this;
        }

        public Criteria andGrowContactUserIdsBetween(String value1, String value2) {
            addCriterion("grow_contact_user_ids between", value1, value2, "growContactUserIds");
            return (Criteria) this;
        }

        public Criteria andGrowContactUserIdsNotBetween(String value1, String value2) {
            addCriterion("grow_contact_user_ids not between", value1, value2, "growContactUserIds");
            return (Criteria) this;
        }

        public Criteria andGrowContactUsersIsNull() {
            addCriterion("grow_contact_users is null");
            return (Criteria) this;
        }

        public Criteria andGrowContactUsersIsNotNull() {
            addCriterion("grow_contact_users is not null");
            return (Criteria) this;
        }

        public Criteria andGrowContactUsersEqualTo(String value) {
            addCriterion("grow_contact_users =", value, "growContactUsers");
            return (Criteria) this;
        }

        public Criteria andGrowContactUsersNotEqualTo(String value) {
            addCriterion("grow_contact_users <>", value, "growContactUsers");
            return (Criteria) this;
        }

        public Criteria andGrowContactUsersGreaterThan(String value) {
            addCriterion("grow_contact_users >", value, "growContactUsers");
            return (Criteria) this;
        }

        public Criteria andGrowContactUsersGreaterThanOrEqualTo(String value) {
            addCriterion("grow_contact_users >=", value, "growContactUsers");
            return (Criteria) this;
        }

        public Criteria andGrowContactUsersLessThan(String value) {
            addCriterion("grow_contact_users <", value, "growContactUsers");
            return (Criteria) this;
        }

        public Criteria andGrowContactUsersLessThanOrEqualTo(String value) {
            addCriterion("grow_contact_users <=", value, "growContactUsers");
            return (Criteria) this;
        }

        public Criteria andGrowContactUsersLike(String value) {
            addCriterion("grow_contact_users like", value, "growContactUsers");
            return (Criteria) this;
        }

        public Criteria andGrowContactUsersNotLike(String value) {
            addCriterion("grow_contact_users not like", value, "growContactUsers");
            return (Criteria) this;
        }

        public Criteria andGrowContactUsersIn(List<String> values) {
            addCriterion("grow_contact_users in", values, "growContactUsers");
            return (Criteria) this;
        }

        public Criteria andGrowContactUsersNotIn(List<String> values) {
            addCriterion("grow_contact_users not in", values, "growContactUsers");
            return (Criteria) this;
        }

        public Criteria andGrowContactUsersBetween(String value1, String value2) {
            addCriterion("grow_contact_users between", value1, value2, "growContactUsers");
            return (Criteria) this;
        }

        public Criteria andGrowContactUsersNotBetween(String value1, String value2) {
            addCriterion("grow_contact_users not between", value1, value2, "growContactUsers");
            return (Criteria) this;
        }

        public Criteria andPositivePublicIdIsNull() {
            addCriterion("positive_public_id is null");
            return (Criteria) this;
        }

        public Criteria andPositivePublicIdIsNotNull() {
            addCriterion("positive_public_id is not null");
            return (Criteria) this;
        }

        public Criteria andPositivePublicIdEqualTo(Integer value) {
            addCriterion("positive_public_id =", value, "positivePublicId");
            return (Criteria) this;
        }

        public Criteria andPositivePublicIdNotEqualTo(Integer value) {
            addCriterion("positive_public_id <>", value, "positivePublicId");
            return (Criteria) this;
        }

        public Criteria andPositivePublicIdGreaterThan(Integer value) {
            addCriterion("positive_public_id >", value, "positivePublicId");
            return (Criteria) this;
        }

        public Criteria andPositivePublicIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("positive_public_id >=", value, "positivePublicId");
            return (Criteria) this;
        }

        public Criteria andPositivePublicIdLessThan(Integer value) {
            addCriterion("positive_public_id <", value, "positivePublicId");
            return (Criteria) this;
        }

        public Criteria andPositivePublicIdLessThanOrEqualTo(Integer value) {
            addCriterion("positive_public_id <=", value, "positivePublicId");
            return (Criteria) this;
        }

        public Criteria andPositivePublicIdIn(List<Integer> values) {
            addCriterion("positive_public_id in", values, "positivePublicId");
            return (Criteria) this;
        }

        public Criteria andPositivePublicIdNotIn(List<Integer> values) {
            addCriterion("positive_public_id not in", values, "positivePublicId");
            return (Criteria) this;
        }

        public Criteria andPositivePublicIdBetween(Integer value1, Integer value2) {
            addCriterion("positive_public_id between", value1, value2, "positivePublicId");
            return (Criteria) this;
        }

        public Criteria andPositivePublicIdNotBetween(Integer value1, Integer value2) {
            addCriterion("positive_public_id not between", value1, value2, "positivePublicId");
            return (Criteria) this;
        }

        public Criteria andPositiveTimeIsNull() {
            addCriterion("positive_time is null");
            return (Criteria) this;
        }

        public Criteria andPositiveTimeIsNotNull() {
            addCriterion("positive_time is not null");
            return (Criteria) this;
        }

        public Criteria andPositiveTimeEqualTo(Date value) {
            addCriterionForJDBCDate("positive_time =", value, "positiveTime");
            return (Criteria) this;
        }

        public Criteria andPositiveTimeNotEqualTo(Date value) {
            addCriterionForJDBCDate("positive_time <>", value, "positiveTime");
            return (Criteria) this;
        }

        public Criteria andPositiveTimeGreaterThan(Date value) {
            addCriterionForJDBCDate("positive_time >", value, "positiveTime");
            return (Criteria) this;
        }

        public Criteria andPositiveTimeGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("positive_time >=", value, "positiveTime");
            return (Criteria) this;
        }

        public Criteria andPositiveTimeLessThan(Date value) {
            addCriterionForJDBCDate("positive_time <", value, "positiveTime");
            return (Criteria) this;
        }

        public Criteria andPositiveTimeLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("positive_time <=", value, "positiveTime");
            return (Criteria) this;
        }

        public Criteria andPositiveTimeIn(List<Date> values) {
            addCriterionForJDBCDate("positive_time in", values, "positiveTime");
            return (Criteria) this;
        }

        public Criteria andPositiveTimeNotIn(List<Date> values) {
            addCriterionForJDBCDate("positive_time not in", values, "positiveTime");
            return (Criteria) this;
        }

        public Criteria andPositiveTimeBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("positive_time between", value1, value2, "positiveTime");
            return (Criteria) this;
        }

        public Criteria andPositiveTimeNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("positive_time not between", value1, value2, "positiveTime");
            return (Criteria) this;
        }

        public Criteria andPositiveStatusIsNull() {
            addCriterion("positive_status is null");
            return (Criteria) this;
        }

        public Criteria andPositiveStatusIsNotNull() {
            addCriterion("positive_status is not null");
            return (Criteria) this;
        }

        public Criteria andPositiveStatusEqualTo(Byte value) {
            addCriterion("positive_status =", value, "positiveStatus");
            return (Criteria) this;
        }

        public Criteria andPositiveStatusNotEqualTo(Byte value) {
            addCriterion("positive_status <>", value, "positiveStatus");
            return (Criteria) this;
        }

        public Criteria andPositiveStatusGreaterThan(Byte value) {
            addCriterion("positive_status >", value, "positiveStatus");
            return (Criteria) this;
        }

        public Criteria andPositiveStatusGreaterThanOrEqualTo(Byte value) {
            addCriterion("positive_status >=", value, "positiveStatus");
            return (Criteria) this;
        }

        public Criteria andPositiveStatusLessThan(Byte value) {
            addCriterion("positive_status <", value, "positiveStatus");
            return (Criteria) this;
        }

        public Criteria andPositiveStatusLessThanOrEqualTo(Byte value) {
            addCriterion("positive_status <=", value, "positiveStatus");
            return (Criteria) this;
        }

        public Criteria andPositiveStatusIn(List<Byte> values) {
            addCriterion("positive_status in", values, "positiveStatus");
            return (Criteria) this;
        }

        public Criteria andPositiveStatusNotIn(List<Byte> values) {
            addCriterion("positive_status not in", values, "positiveStatus");
            return (Criteria) this;
        }

        public Criteria andPositiveStatusBetween(Byte value1, Byte value2) {
            addCriterion("positive_status between", value1, value2, "positiveStatus");
            return (Criteria) this;
        }

        public Criteria andPositiveStatusNotBetween(Byte value1, Byte value2) {
            addCriterion("positive_status not between", value1, value2, "positiveStatus");
            return (Criteria) this;
        }

        public Criteria andIsRemoveIsNull() {
            addCriterion("is_remove is null");
            return (Criteria) this;
        }

        public Criteria andIsRemoveIsNotNull() {
            addCriterion("is_remove is not null");
            return (Criteria) this;
        }

        public Criteria andIsRemoveEqualTo(Boolean value) {
            addCriterion("is_remove =", value, "isRemove");
            return (Criteria) this;
        }

        public Criteria andIsRemoveNotEqualTo(Boolean value) {
            addCriterion("is_remove <>", value, "isRemove");
            return (Criteria) this;
        }

        public Criteria andIsRemoveGreaterThan(Boolean value) {
            addCriterion("is_remove >", value, "isRemove");
            return (Criteria) this;
        }

        public Criteria andIsRemoveGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_remove >=", value, "isRemove");
            return (Criteria) this;
        }

        public Criteria andIsRemoveLessThan(Boolean value) {
            addCriterion("is_remove <", value, "isRemove");
            return (Criteria) this;
        }

        public Criteria andIsRemoveLessThanOrEqualTo(Boolean value) {
            addCriterion("is_remove <=", value, "isRemove");
            return (Criteria) this;
        }

        public Criteria andIsRemoveIn(List<Boolean> values) {
            addCriterion("is_remove in", values, "isRemove");
            return (Criteria) this;
        }

        public Criteria andIsRemoveNotIn(List<Boolean> values) {
            addCriterion("is_remove not in", values, "isRemove");
            return (Criteria) this;
        }

        public Criteria andIsRemoveBetween(Boolean value1, Boolean value2) {
            addCriterion("is_remove between", value1, value2, "isRemove");
            return (Criteria) this;
        }

        public Criteria andIsRemoveNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_remove not between", value1, value2, "isRemove");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNull() {
            addCriterion("create_time is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("create_time is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(Date value) {
            addCriterion("create_time =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(Date value) {
            addCriterion("create_time <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(Date value) {
            addCriterion("create_time >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("create_time >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(Date value) {
            addCriterion("create_time <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(Date value) {
            addCriterion("create_time <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<Date> values) {
            addCriterion("create_time in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<Date> values) {
            addCriterion("create_time not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(Date value1, Date value2) {
            addCriterion("create_time between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(Date value1, Date value2) {
            addCriterion("create_time not between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andStatusIsNull() {
            addCriterion("_status is null");
            return (Criteria) this;
        }

        public Criteria andStatusIsNotNull() {
            addCriterion("_status is not null");
            return (Criteria) this;
        }

        public Criteria andStatusEqualTo(Byte value) {
            addCriterion("_status =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(Byte value) {
            addCriterion("_status <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(Byte value) {
            addCriterion("_status >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(Byte value) {
            addCriterion("_status >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(Byte value) {
            addCriterion("_status <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(Byte value) {
            addCriterion("_status <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<Byte> values) {
            addCriterion("_status in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<Byte> values) {
            addCriterion("_status not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(Byte value1, Byte value2) {
            addCriterion("_status between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(Byte value1, Byte value2) {
            addCriterion("_status not between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andMemberStatusIsNull() {
            addCriterion("member_status is null");
            return (Criteria) this;
        }

        public Criteria andMemberStatusIsNotNull() {
            addCriterion("member_status is not null");
            return (Criteria) this;
        }

        public Criteria andMemberStatusEqualTo(Integer value) {
            addCriterion("member_status =", value, "memberStatus");
            return (Criteria) this;
        }

        public Criteria andMemberStatusNotEqualTo(Integer value) {
            addCriterion("member_status <>", value, "memberStatus");
            return (Criteria) this;
        }

        public Criteria andMemberStatusGreaterThan(Integer value) {
            addCriterion("member_status >", value, "memberStatus");
            return (Criteria) this;
        }

        public Criteria andMemberStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("member_status >=", value, "memberStatus");
            return (Criteria) this;
        }

        public Criteria andMemberStatusLessThan(Integer value) {
            addCriterion("member_status <", value, "memberStatus");
            return (Criteria) this;
        }

        public Criteria andMemberStatusLessThanOrEqualTo(Integer value) {
            addCriterion("member_status <=", value, "memberStatus");
            return (Criteria) this;
        }

        public Criteria andMemberStatusIn(List<Integer> values) {
            addCriterion("member_status in", values, "memberStatus");
            return (Criteria) this;
        }

        public Criteria andMemberStatusNotIn(List<Integer> values) {
            addCriterion("member_status not in", values, "memberStatus");
            return (Criteria) this;
        }

        public Criteria andMemberStatusBetween(Integer value1, Integer value2) {
            addCriterion("member_status between", value1, value2, "memberStatus");
            return (Criteria) this;
        }

        public Criteria andMemberStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("member_status not between", value1, value2, "memberStatus");
            return (Criteria) this;
        }

        public Criteria andPartySortOrderIsNull() {
            addCriterion("party_sort_order is null");
            return (Criteria) this;
        }

        public Criteria andPartySortOrderIsNotNull() {
            addCriterion("party_sort_order is not null");
            return (Criteria) this;
        }

        public Criteria andPartySortOrderEqualTo(Integer value) {
            addCriterion("party_sort_order =", value, "partySortOrder");
            return (Criteria) this;
        }

        public Criteria andPartySortOrderNotEqualTo(Integer value) {
            addCriterion("party_sort_order <>", value, "partySortOrder");
            return (Criteria) this;
        }

        public Criteria andPartySortOrderGreaterThan(Integer value) {
            addCriterion("party_sort_order >", value, "partySortOrder");
            return (Criteria) this;
        }

        public Criteria andPartySortOrderGreaterThanOrEqualTo(Integer value) {
            addCriterion("party_sort_order >=", value, "partySortOrder");
            return (Criteria) this;
        }

        public Criteria andPartySortOrderLessThan(Integer value) {
            addCriterion("party_sort_order <", value, "partySortOrder");
            return (Criteria) this;
        }

        public Criteria andPartySortOrderLessThanOrEqualTo(Integer value) {
            addCriterion("party_sort_order <=", value, "partySortOrder");
            return (Criteria) this;
        }

        public Criteria andPartySortOrderIn(List<Integer> values) {
            addCriterion("party_sort_order in", values, "partySortOrder");
            return (Criteria) this;
        }

        public Criteria andPartySortOrderNotIn(List<Integer> values) {
            addCriterion("party_sort_order not in", values, "partySortOrder");
            return (Criteria) this;
        }

        public Criteria andPartySortOrderBetween(Integer value1, Integer value2) {
            addCriterion("party_sort_order between", value1, value2, "partySortOrder");
            return (Criteria) this;
        }

        public Criteria andPartySortOrderNotBetween(Integer value1, Integer value2) {
            addCriterion("party_sort_order not between", value1, value2, "partySortOrder");
            return (Criteria) this;
        }

        public Criteria andBranchSortOrderIsNull() {
            addCriterion("branch_sort_order is null");
            return (Criteria) this;
        }

        public Criteria andBranchSortOrderIsNotNull() {
            addCriterion("branch_sort_order is not null");
            return (Criteria) this;
        }

        public Criteria andBranchSortOrderEqualTo(Integer value) {
            addCriterion("branch_sort_order =", value, "branchSortOrder");
            return (Criteria) this;
        }

        public Criteria andBranchSortOrderNotEqualTo(Integer value) {
            addCriterion("branch_sort_order <>", value, "branchSortOrder");
            return (Criteria) this;
        }

        public Criteria andBranchSortOrderGreaterThan(Integer value) {
            addCriterion("branch_sort_order >", value, "branchSortOrder");
            return (Criteria) this;
        }

        public Criteria andBranchSortOrderGreaterThanOrEqualTo(Integer value) {
            addCriterion("branch_sort_order >=", value, "branchSortOrder");
            return (Criteria) this;
        }

        public Criteria andBranchSortOrderLessThan(Integer value) {
            addCriterion("branch_sort_order <", value, "branchSortOrder");
            return (Criteria) this;
        }

        public Criteria andBranchSortOrderLessThanOrEqualTo(Integer value) {
            addCriterion("branch_sort_order <=", value, "branchSortOrder");
            return (Criteria) this;
        }

        public Criteria andBranchSortOrderIn(List<Integer> values) {
            addCriterion("branch_sort_order in", values, "branchSortOrder");
            return (Criteria) this;
        }

        public Criteria andBranchSortOrderNotIn(List<Integer> values) {
            addCriterion("branch_sort_order not in", values, "branchSortOrder");
            return (Criteria) this;
        }

        public Criteria andBranchSortOrderBetween(Integer value1, Integer value2) {
            addCriterion("branch_sort_order between", value1, value2, "branchSortOrder");
            return (Criteria) this;
        }

        public Criteria andBranchSortOrderNotBetween(Integer value1, Integer value2) {
            addCriterion("branch_sort_order not between", value1, value2, "branchSortOrder");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
        public Criteria addPermits(List<Integer> partyIdList, List<Integer> branchIdList) {

            if(ShiroHelper.isPermitted(SystemConstants.PERMISSION_PARTYVIEWALL))
                return this;

            if(partyIdList==null) partyIdList = new ArrayList<>();
            if(branchIdList==null) branchIdList = new ArrayList<>();

            if(!partyIdList.isEmpty() && !branchIdList.isEmpty())
                addCriterion("(party_id in(" + StringUtils.join(partyIdList, ",") + ") OR branch_id in(" + StringUtils.join(branchIdList, ",") + "))");
            if(partyIdList.isEmpty() && !branchIdList.isEmpty())
                andBranchIdIn(branchIdList);
            if(branchIdList.isEmpty() && !partyIdList.isEmpty())
                andPartyIdIn(partyIdList);
            if(branchIdList.isEmpty() && partyIdList.isEmpty())
                andUserIdIsNull();
            return this;
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}