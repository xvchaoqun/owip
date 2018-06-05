package domain.cla;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class ClaApplyModifyExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public ClaApplyModifyExample() {
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

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andModifyTypeIsNull() {
            addCriterion("modify_type is null");
            return (Criteria) this;
        }

        public Criteria andModifyTypeIsNotNull() {
            addCriterion("modify_type is not null");
            return (Criteria) this;
        }

        public Criteria andModifyTypeEqualTo(Byte value) {
            addCriterion("modify_type =", value, "modifyType");
            return (Criteria) this;
        }

        public Criteria andModifyTypeNotEqualTo(Byte value) {
            addCriterion("modify_type <>", value, "modifyType");
            return (Criteria) this;
        }

        public Criteria andModifyTypeGreaterThan(Byte value) {
            addCriterion("modify_type >", value, "modifyType");
            return (Criteria) this;
        }

        public Criteria andModifyTypeGreaterThanOrEqualTo(Byte value) {
            addCriterion("modify_type >=", value, "modifyType");
            return (Criteria) this;
        }

        public Criteria andModifyTypeLessThan(Byte value) {
            addCriterion("modify_type <", value, "modifyType");
            return (Criteria) this;
        }

        public Criteria andModifyTypeLessThanOrEqualTo(Byte value) {
            addCriterion("modify_type <=", value, "modifyType");
            return (Criteria) this;
        }

        public Criteria andModifyTypeIn(List<Byte> values) {
            addCriterion("modify_type in", values, "modifyType");
            return (Criteria) this;
        }

        public Criteria andModifyTypeNotIn(List<Byte> values) {
            addCriterion("modify_type not in", values, "modifyType");
            return (Criteria) this;
        }

        public Criteria andModifyTypeBetween(Byte value1, Byte value2) {
            addCriterion("modify_type between", value1, value2, "modifyType");
            return (Criteria) this;
        }

        public Criteria andModifyTypeNotBetween(Byte value1, Byte value2) {
            addCriterion("modify_type not between", value1, value2, "modifyType");
            return (Criteria) this;
        }

        public Criteria andApplyIdIsNull() {
            addCriterion("apply_id is null");
            return (Criteria) this;
        }

        public Criteria andApplyIdIsNotNull() {
            addCriterion("apply_id is not null");
            return (Criteria) this;
        }

        public Criteria andApplyIdEqualTo(Integer value) {
            addCriterion("apply_id =", value, "applyId");
            return (Criteria) this;
        }

        public Criteria andApplyIdNotEqualTo(Integer value) {
            addCriterion("apply_id <>", value, "applyId");
            return (Criteria) this;
        }

        public Criteria andApplyIdGreaterThan(Integer value) {
            addCriterion("apply_id >", value, "applyId");
            return (Criteria) this;
        }

        public Criteria andApplyIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("apply_id >=", value, "applyId");
            return (Criteria) this;
        }

        public Criteria andApplyIdLessThan(Integer value) {
            addCriterion("apply_id <", value, "applyId");
            return (Criteria) this;
        }

        public Criteria andApplyIdLessThanOrEqualTo(Integer value) {
            addCriterion("apply_id <=", value, "applyId");
            return (Criteria) this;
        }

        public Criteria andApplyIdIn(List<Integer> values) {
            addCriterion("apply_id in", values, "applyId");
            return (Criteria) this;
        }

        public Criteria andApplyIdNotIn(List<Integer> values) {
            addCriterion("apply_id not in", values, "applyId");
            return (Criteria) this;
        }

        public Criteria andApplyIdBetween(Integer value1, Integer value2) {
            addCriterion("apply_id between", value1, value2, "applyId");
            return (Criteria) this;
        }

        public Criteria andApplyIdNotBetween(Integer value1, Integer value2) {
            addCriterion("apply_id not between", value1, value2, "applyId");
            return (Criteria) this;
        }

        public Criteria andCadreIdIsNull() {
            addCriterion("cadre_id is null");
            return (Criteria) this;
        }

        public Criteria andCadreIdIsNotNull() {
            addCriterion("cadre_id is not null");
            return (Criteria) this;
        }

        public Criteria andCadreIdEqualTo(Integer value) {
            addCriterion("cadre_id =", value, "cadreId");
            return (Criteria) this;
        }

        public Criteria andCadreIdNotEqualTo(Integer value) {
            addCriterion("cadre_id <>", value, "cadreId");
            return (Criteria) this;
        }

        public Criteria andCadreIdGreaterThan(Integer value) {
            addCriterion("cadre_id >", value, "cadreId");
            return (Criteria) this;
        }

        public Criteria andCadreIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("cadre_id >=", value, "cadreId");
            return (Criteria) this;
        }

        public Criteria andCadreIdLessThan(Integer value) {
            addCriterion("cadre_id <", value, "cadreId");
            return (Criteria) this;
        }

        public Criteria andCadreIdLessThanOrEqualTo(Integer value) {
            addCriterion("cadre_id <=", value, "cadreId");
            return (Criteria) this;
        }

        public Criteria andCadreIdIn(List<Integer> values) {
            addCriterion("cadre_id in", values, "cadreId");
            return (Criteria) this;
        }

        public Criteria andCadreIdNotIn(List<Integer> values) {
            addCriterion("cadre_id not in", values, "cadreId");
            return (Criteria) this;
        }

        public Criteria andCadreIdBetween(Integer value1, Integer value2) {
            addCriterion("cadre_id between", value1, value2, "cadreId");
            return (Criteria) this;
        }

        public Criteria andCadreIdNotBetween(Integer value1, Integer value2) {
            addCriterion("cadre_id not between", value1, value2, "cadreId");
            return (Criteria) this;
        }

        public Criteria andApplyDateIsNull() {
            addCriterion("apply_date is null");
            return (Criteria) this;
        }

        public Criteria andApplyDateIsNotNull() {
            addCriterion("apply_date is not null");
            return (Criteria) this;
        }

        public Criteria andApplyDateEqualTo(Date value) {
            addCriterionForJDBCDate("apply_date =", value, "applyDate");
            return (Criteria) this;
        }

        public Criteria andApplyDateNotEqualTo(Date value) {
            addCriterionForJDBCDate("apply_date <>", value, "applyDate");
            return (Criteria) this;
        }

        public Criteria andApplyDateGreaterThan(Date value) {
            addCriterionForJDBCDate("apply_date >", value, "applyDate");
            return (Criteria) this;
        }

        public Criteria andApplyDateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("apply_date >=", value, "applyDate");
            return (Criteria) this;
        }

        public Criteria andApplyDateLessThan(Date value) {
            addCriterionForJDBCDate("apply_date <", value, "applyDate");
            return (Criteria) this;
        }

        public Criteria andApplyDateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("apply_date <=", value, "applyDate");
            return (Criteria) this;
        }

        public Criteria andApplyDateIn(List<Date> values) {
            addCriterionForJDBCDate("apply_date in", values, "applyDate");
            return (Criteria) this;
        }

        public Criteria andApplyDateNotIn(List<Date> values) {
            addCriterionForJDBCDate("apply_date not in", values, "applyDate");
            return (Criteria) this;
        }

        public Criteria andApplyDateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("apply_date between", value1, value2, "applyDate");
            return (Criteria) this;
        }

        public Criteria andApplyDateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("apply_date not between", value1, value2, "applyDate");
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

        public Criteria andStartTimeIsNull() {
            addCriterion("start_time is null");
            return (Criteria) this;
        }

        public Criteria andStartTimeIsNotNull() {
            addCriterion("start_time is not null");
            return (Criteria) this;
        }

        public Criteria andStartTimeEqualTo(Date value) {
            addCriterion("start_time =", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeNotEqualTo(Date value) {
            addCriterion("start_time <>", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeGreaterThan(Date value) {
            addCriterion("start_time >", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("start_time >=", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeLessThan(Date value) {
            addCriterion("start_time <", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeLessThanOrEqualTo(Date value) {
            addCriterion("start_time <=", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeIn(List<Date> values) {
            addCriterion("start_time in", values, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeNotIn(List<Date> values) {
            addCriterion("start_time not in", values, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeBetween(Date value1, Date value2) {
            addCriterion("start_time between", value1, value2, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeNotBetween(Date value1, Date value2) {
            addCriterion("start_time not between", value1, value2, "startTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeIsNull() {
            addCriterion("end_time is null");
            return (Criteria) this;
        }

        public Criteria andEndTimeIsNotNull() {
            addCriterion("end_time is not null");
            return (Criteria) this;
        }

        public Criteria andEndTimeEqualTo(Date value) {
            addCriterion("end_time =", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeNotEqualTo(Date value) {
            addCriterion("end_time <>", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeGreaterThan(Date value) {
            addCriterion("end_time >", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("end_time >=", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeLessThan(Date value) {
            addCriterion("end_time <", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeLessThanOrEqualTo(Date value) {
            addCriterion("end_time <=", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeIn(List<Date> values) {
            addCriterion("end_time in", values, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeNotIn(List<Date> values) {
            addCriterion("end_time not in", values, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeBetween(Date value1, Date value2) {
            addCriterion("end_time between", value1, value2, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeNotBetween(Date value1, Date value2) {
            addCriterion("end_time not between", value1, value2, "endTime");
            return (Criteria) this;
        }

        public Criteria andDestinationIsNull() {
            addCriterion("destination is null");
            return (Criteria) this;
        }

        public Criteria andDestinationIsNotNull() {
            addCriterion("destination is not null");
            return (Criteria) this;
        }

        public Criteria andDestinationEqualTo(String value) {
            addCriterion("destination =", value, "destination");
            return (Criteria) this;
        }

        public Criteria andDestinationNotEqualTo(String value) {
            addCriterion("destination <>", value, "destination");
            return (Criteria) this;
        }

        public Criteria andDestinationGreaterThan(String value) {
            addCriterion("destination >", value, "destination");
            return (Criteria) this;
        }

        public Criteria andDestinationGreaterThanOrEqualTo(String value) {
            addCriterion("destination >=", value, "destination");
            return (Criteria) this;
        }

        public Criteria andDestinationLessThan(String value) {
            addCriterion("destination <", value, "destination");
            return (Criteria) this;
        }

        public Criteria andDestinationLessThanOrEqualTo(String value) {
            addCriterion("destination <=", value, "destination");
            return (Criteria) this;
        }

        public Criteria andDestinationLike(String value) {
            addCriterion("destination like", value, "destination");
            return (Criteria) this;
        }

        public Criteria andDestinationNotLike(String value) {
            addCriterion("destination not like", value, "destination");
            return (Criteria) this;
        }

        public Criteria andDestinationIn(List<String> values) {
            addCriterion("destination in", values, "destination");
            return (Criteria) this;
        }

        public Criteria andDestinationNotIn(List<String> values) {
            addCriterion("destination not in", values, "destination");
            return (Criteria) this;
        }

        public Criteria andDestinationBetween(String value1, String value2) {
            addCriterion("destination between", value1, value2, "destination");
            return (Criteria) this;
        }

        public Criteria andDestinationNotBetween(String value1, String value2) {
            addCriterion("destination not between", value1, value2, "destination");
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

        public Criteria andPeerStaffIsNull() {
            addCriterion("peer_staff is null");
            return (Criteria) this;
        }

        public Criteria andPeerStaffIsNotNull() {
            addCriterion("peer_staff is not null");
            return (Criteria) this;
        }

        public Criteria andPeerStaffEqualTo(String value) {
            addCriterion("peer_staff =", value, "peerStaff");
            return (Criteria) this;
        }

        public Criteria andPeerStaffNotEqualTo(String value) {
            addCriterion("peer_staff <>", value, "peerStaff");
            return (Criteria) this;
        }

        public Criteria andPeerStaffGreaterThan(String value) {
            addCriterion("peer_staff >", value, "peerStaff");
            return (Criteria) this;
        }

        public Criteria andPeerStaffGreaterThanOrEqualTo(String value) {
            addCriterion("peer_staff >=", value, "peerStaff");
            return (Criteria) this;
        }

        public Criteria andPeerStaffLessThan(String value) {
            addCriterion("peer_staff <", value, "peerStaff");
            return (Criteria) this;
        }

        public Criteria andPeerStaffLessThanOrEqualTo(String value) {
            addCriterion("peer_staff <=", value, "peerStaff");
            return (Criteria) this;
        }

        public Criteria andPeerStaffLike(String value) {
            addCriterion("peer_staff like", value, "peerStaff");
            return (Criteria) this;
        }

        public Criteria andPeerStaffNotLike(String value) {
            addCriterion("peer_staff not like", value, "peerStaff");
            return (Criteria) this;
        }

        public Criteria andPeerStaffIn(List<String> values) {
            addCriterion("peer_staff in", values, "peerStaff");
            return (Criteria) this;
        }

        public Criteria andPeerStaffNotIn(List<String> values) {
            addCriterion("peer_staff not in", values, "peerStaff");
            return (Criteria) this;
        }

        public Criteria andPeerStaffBetween(String value1, String value2) {
            addCriterion("peer_staff between", value1, value2, "peerStaff");
            return (Criteria) this;
        }

        public Criteria andPeerStaffNotBetween(String value1, String value2) {
            addCriterion("peer_staff not between", value1, value2, "peerStaff");
            return (Criteria) this;
        }

        public Criteria andModifyProofIsNull() {
            addCriterion("modify_proof is null");
            return (Criteria) this;
        }

        public Criteria andModifyProofIsNotNull() {
            addCriterion("modify_proof is not null");
            return (Criteria) this;
        }

        public Criteria andModifyProofEqualTo(String value) {
            addCriterion("modify_proof =", value, "modifyProof");
            return (Criteria) this;
        }

        public Criteria andModifyProofNotEqualTo(String value) {
            addCriterion("modify_proof <>", value, "modifyProof");
            return (Criteria) this;
        }

        public Criteria andModifyProofGreaterThan(String value) {
            addCriterion("modify_proof >", value, "modifyProof");
            return (Criteria) this;
        }

        public Criteria andModifyProofGreaterThanOrEqualTo(String value) {
            addCriterion("modify_proof >=", value, "modifyProof");
            return (Criteria) this;
        }

        public Criteria andModifyProofLessThan(String value) {
            addCriterion("modify_proof <", value, "modifyProof");
            return (Criteria) this;
        }

        public Criteria andModifyProofLessThanOrEqualTo(String value) {
            addCriterion("modify_proof <=", value, "modifyProof");
            return (Criteria) this;
        }

        public Criteria andModifyProofLike(String value) {
            addCriterion("modify_proof like", value, "modifyProof");
            return (Criteria) this;
        }

        public Criteria andModifyProofNotLike(String value) {
            addCriterion("modify_proof not like", value, "modifyProof");
            return (Criteria) this;
        }

        public Criteria andModifyProofIn(List<String> values) {
            addCriterion("modify_proof in", values, "modifyProof");
            return (Criteria) this;
        }

        public Criteria andModifyProofNotIn(List<String> values) {
            addCriterion("modify_proof not in", values, "modifyProof");
            return (Criteria) this;
        }

        public Criteria andModifyProofBetween(String value1, String value2) {
            addCriterion("modify_proof between", value1, value2, "modifyProof");
            return (Criteria) this;
        }

        public Criteria andModifyProofNotBetween(String value1, String value2) {
            addCriterion("modify_proof not between", value1, value2, "modifyProof");
            return (Criteria) this;
        }

        public Criteria andModifyProofFileNameIsNull() {
            addCriterion("modify_proof_file_name is null");
            return (Criteria) this;
        }

        public Criteria andModifyProofFileNameIsNotNull() {
            addCriterion("modify_proof_file_name is not null");
            return (Criteria) this;
        }

        public Criteria andModifyProofFileNameEqualTo(String value) {
            addCriterion("modify_proof_file_name =", value, "modifyProofFileName");
            return (Criteria) this;
        }

        public Criteria andModifyProofFileNameNotEqualTo(String value) {
            addCriterion("modify_proof_file_name <>", value, "modifyProofFileName");
            return (Criteria) this;
        }

        public Criteria andModifyProofFileNameGreaterThan(String value) {
            addCriterion("modify_proof_file_name >", value, "modifyProofFileName");
            return (Criteria) this;
        }

        public Criteria andModifyProofFileNameGreaterThanOrEqualTo(String value) {
            addCriterion("modify_proof_file_name >=", value, "modifyProofFileName");
            return (Criteria) this;
        }

        public Criteria andModifyProofFileNameLessThan(String value) {
            addCriterion("modify_proof_file_name <", value, "modifyProofFileName");
            return (Criteria) this;
        }

        public Criteria andModifyProofFileNameLessThanOrEqualTo(String value) {
            addCriterion("modify_proof_file_name <=", value, "modifyProofFileName");
            return (Criteria) this;
        }

        public Criteria andModifyProofFileNameLike(String value) {
            addCriterion("modify_proof_file_name like", value, "modifyProofFileName");
            return (Criteria) this;
        }

        public Criteria andModifyProofFileNameNotLike(String value) {
            addCriterion("modify_proof_file_name not like", value, "modifyProofFileName");
            return (Criteria) this;
        }

        public Criteria andModifyProofFileNameIn(List<String> values) {
            addCriterion("modify_proof_file_name in", values, "modifyProofFileName");
            return (Criteria) this;
        }

        public Criteria andModifyProofFileNameNotIn(List<String> values) {
            addCriterion("modify_proof_file_name not in", values, "modifyProofFileName");
            return (Criteria) this;
        }

        public Criteria andModifyProofFileNameBetween(String value1, String value2) {
            addCriterion("modify_proof_file_name between", value1, value2, "modifyProofFileName");
            return (Criteria) this;
        }

        public Criteria andModifyProofFileNameNotBetween(String value1, String value2) {
            addCriterion("modify_proof_file_name not between", value1, value2, "modifyProofFileName");
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

        public Criteria andModifyUserIdIsNull() {
            addCriterion("modify_user_id is null");
            return (Criteria) this;
        }

        public Criteria andModifyUserIdIsNotNull() {
            addCriterion("modify_user_id is not null");
            return (Criteria) this;
        }

        public Criteria andModifyUserIdEqualTo(Integer value) {
            addCriterion("modify_user_id =", value, "modifyUserId");
            return (Criteria) this;
        }

        public Criteria andModifyUserIdNotEqualTo(Integer value) {
            addCriterion("modify_user_id <>", value, "modifyUserId");
            return (Criteria) this;
        }

        public Criteria andModifyUserIdGreaterThan(Integer value) {
            addCriterion("modify_user_id >", value, "modifyUserId");
            return (Criteria) this;
        }

        public Criteria andModifyUserIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("modify_user_id >=", value, "modifyUserId");
            return (Criteria) this;
        }

        public Criteria andModifyUserIdLessThan(Integer value) {
            addCriterion("modify_user_id <", value, "modifyUserId");
            return (Criteria) this;
        }

        public Criteria andModifyUserIdLessThanOrEqualTo(Integer value) {
            addCriterion("modify_user_id <=", value, "modifyUserId");
            return (Criteria) this;
        }

        public Criteria andModifyUserIdIn(List<Integer> values) {
            addCriterion("modify_user_id in", values, "modifyUserId");
            return (Criteria) this;
        }

        public Criteria andModifyUserIdNotIn(List<Integer> values) {
            addCriterion("modify_user_id not in", values, "modifyUserId");
            return (Criteria) this;
        }

        public Criteria andModifyUserIdBetween(Integer value1, Integer value2) {
            addCriterion("modify_user_id between", value1, value2, "modifyUserId");
            return (Criteria) this;
        }

        public Criteria andModifyUserIdNotBetween(Integer value1, Integer value2) {
            addCriterion("modify_user_id not between", value1, value2, "modifyUserId");
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

        public Criteria andIpIsNull() {
            addCriterion("ip is null");
            return (Criteria) this;
        }

        public Criteria andIpIsNotNull() {
            addCriterion("ip is not null");
            return (Criteria) this;
        }

        public Criteria andIpEqualTo(String value) {
            addCriterion("ip =", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpNotEqualTo(String value) {
            addCriterion("ip <>", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpGreaterThan(String value) {
            addCriterion("ip >", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpGreaterThanOrEqualTo(String value) {
            addCriterion("ip >=", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpLessThan(String value) {
            addCriterion("ip <", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpLessThanOrEqualTo(String value) {
            addCriterion("ip <=", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpLike(String value) {
            addCriterion("ip like", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpNotLike(String value) {
            addCriterion("ip not like", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpIn(List<String> values) {
            addCriterion("ip in", values, "ip");
            return (Criteria) this;
        }

        public Criteria andIpNotIn(List<String> values) {
            addCriterion("ip not in", values, "ip");
            return (Criteria) this;
        }

        public Criteria andIpBetween(String value1, String value2) {
            addCriterion("ip between", value1, value2, "ip");
            return (Criteria) this;
        }

        public Criteria andIpNotBetween(String value1, String value2) {
            addCriterion("ip not between", value1, value2, "ip");
            return (Criteria) this;
        }

        public Criteria andStatusIsNull() {
            addCriterion("status is null");
            return (Criteria) this;
        }

        public Criteria andStatusIsNotNull() {
            addCriterion("status is not null");
            return (Criteria) this;
        }

        public Criteria andStatusEqualTo(Boolean value) {
            addCriterion("status =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(Boolean value) {
            addCriterion("status <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(Boolean value) {
            addCriterion("status >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(Boolean value) {
            addCriterion("status >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(Boolean value) {
            addCriterion("status <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(Boolean value) {
            addCriterion("status <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<Boolean> values) {
            addCriterion("status in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<Boolean> values) {
            addCriterion("status not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(Boolean value1, Boolean value2) {
            addCriterion("status between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(Boolean value1, Boolean value2) {
            addCriterion("status not between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andIsFinishIsNull() {
            addCriterion("is_finish is null");
            return (Criteria) this;
        }

        public Criteria andIsFinishIsNotNull() {
            addCriterion("is_finish is not null");
            return (Criteria) this;
        }

        public Criteria andIsFinishEqualTo(Boolean value) {
            addCriterion("is_finish =", value, "isFinish");
            return (Criteria) this;
        }

        public Criteria andIsFinishNotEqualTo(Boolean value) {
            addCriterion("is_finish <>", value, "isFinish");
            return (Criteria) this;
        }

        public Criteria andIsFinishGreaterThan(Boolean value) {
            addCriterion("is_finish >", value, "isFinish");
            return (Criteria) this;
        }

        public Criteria andIsFinishGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_finish >=", value, "isFinish");
            return (Criteria) this;
        }

        public Criteria andIsFinishLessThan(Boolean value) {
            addCriterion("is_finish <", value, "isFinish");
            return (Criteria) this;
        }

        public Criteria andIsFinishLessThanOrEqualTo(Boolean value) {
            addCriterion("is_finish <=", value, "isFinish");
            return (Criteria) this;
        }

        public Criteria andIsFinishIn(List<Boolean> values) {
            addCriterion("is_finish in", values, "isFinish");
            return (Criteria) this;
        }

        public Criteria andIsFinishNotIn(List<Boolean> values) {
            addCriterion("is_finish not in", values, "isFinish");
            return (Criteria) this;
        }

        public Criteria andIsFinishBetween(Boolean value1, Boolean value2) {
            addCriterion("is_finish between", value1, value2, "isFinish");
            return (Criteria) this;
        }

        public Criteria andIsFinishNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_finish not between", value1, value2, "isFinish");
            return (Criteria) this;
        }

        public Criteria andFlowNodeIsNull() {
            addCriterion("flow_node is null");
            return (Criteria) this;
        }

        public Criteria andFlowNodeIsNotNull() {
            addCriterion("flow_node is not null");
            return (Criteria) this;
        }

        public Criteria andFlowNodeEqualTo(Integer value) {
            addCriterion("flow_node =", value, "flowNode");
            return (Criteria) this;
        }

        public Criteria andFlowNodeNotEqualTo(Integer value) {
            addCriterion("flow_node <>", value, "flowNode");
            return (Criteria) this;
        }

        public Criteria andFlowNodeGreaterThan(Integer value) {
            addCriterion("flow_node >", value, "flowNode");
            return (Criteria) this;
        }

        public Criteria andFlowNodeGreaterThanOrEqualTo(Integer value) {
            addCriterion("flow_node >=", value, "flowNode");
            return (Criteria) this;
        }

        public Criteria andFlowNodeLessThan(Integer value) {
            addCriterion("flow_node <", value, "flowNode");
            return (Criteria) this;
        }

        public Criteria andFlowNodeLessThanOrEqualTo(Integer value) {
            addCriterion("flow_node <=", value, "flowNode");
            return (Criteria) this;
        }

        public Criteria andFlowNodeIn(List<Integer> values) {
            addCriterion("flow_node in", values, "flowNode");
            return (Criteria) this;
        }

        public Criteria andFlowNodeNotIn(List<Integer> values) {
            addCriterion("flow_node not in", values, "flowNode");
            return (Criteria) this;
        }

        public Criteria andFlowNodeBetween(Integer value1, Integer value2) {
            addCriterion("flow_node between", value1, value2, "flowNode");
            return (Criteria) this;
        }

        public Criteria andFlowNodeNotBetween(Integer value1, Integer value2) {
            addCriterion("flow_node not between", value1, value2, "flowNode");
            return (Criteria) this;
        }

        public Criteria andFlowNodesIsNull() {
            addCriterion("flow_nodes is null");
            return (Criteria) this;
        }

        public Criteria andFlowNodesIsNotNull() {
            addCriterion("flow_nodes is not null");
            return (Criteria) this;
        }

        public Criteria andFlowNodesEqualTo(String value) {
            addCriterion("flow_nodes =", value, "flowNodes");
            return (Criteria) this;
        }

        public Criteria andFlowNodesNotEqualTo(String value) {
            addCriterion("flow_nodes <>", value, "flowNodes");
            return (Criteria) this;
        }

        public Criteria andFlowNodesGreaterThan(String value) {
            addCriterion("flow_nodes >", value, "flowNodes");
            return (Criteria) this;
        }

        public Criteria andFlowNodesGreaterThanOrEqualTo(String value) {
            addCriterion("flow_nodes >=", value, "flowNodes");
            return (Criteria) this;
        }

        public Criteria andFlowNodesLessThan(String value) {
            addCriterion("flow_nodes <", value, "flowNodes");
            return (Criteria) this;
        }

        public Criteria andFlowNodesLessThanOrEqualTo(String value) {
            addCriterion("flow_nodes <=", value, "flowNodes");
            return (Criteria) this;
        }

        public Criteria andFlowNodesLike(String value) {
            addCriterion("flow_nodes like", value, "flowNodes");
            return (Criteria) this;
        }

        public Criteria andFlowNodesNotLike(String value) {
            addCriterion("flow_nodes not like", value, "flowNodes");
            return (Criteria) this;
        }

        public Criteria andFlowNodesIn(List<String> values) {
            addCriterion("flow_nodes in", values, "flowNodes");
            return (Criteria) this;
        }

        public Criteria andFlowNodesNotIn(List<String> values) {
            addCriterion("flow_nodes not in", values, "flowNodes");
            return (Criteria) this;
        }

        public Criteria andFlowNodesBetween(String value1, String value2) {
            addCriterion("flow_nodes between", value1, value2, "flowNodes");
            return (Criteria) this;
        }

        public Criteria andFlowNodesNotBetween(String value1, String value2) {
            addCriterion("flow_nodes not between", value1, value2, "flowNodes");
            return (Criteria) this;
        }

        public Criteria andFlowUsersIsNull() {
            addCriterion("flow_users is null");
            return (Criteria) this;
        }

        public Criteria andFlowUsersIsNotNull() {
            addCriterion("flow_users is not null");
            return (Criteria) this;
        }

        public Criteria andFlowUsersEqualTo(String value) {
            addCriterion("flow_users =", value, "flowUsers");
            return (Criteria) this;
        }

        public Criteria andFlowUsersNotEqualTo(String value) {
            addCriterion("flow_users <>", value, "flowUsers");
            return (Criteria) this;
        }

        public Criteria andFlowUsersGreaterThan(String value) {
            addCriterion("flow_users >", value, "flowUsers");
            return (Criteria) this;
        }

        public Criteria andFlowUsersGreaterThanOrEqualTo(String value) {
            addCriterion("flow_users >=", value, "flowUsers");
            return (Criteria) this;
        }

        public Criteria andFlowUsersLessThan(String value) {
            addCriterion("flow_users <", value, "flowUsers");
            return (Criteria) this;
        }

        public Criteria andFlowUsersLessThanOrEqualTo(String value) {
            addCriterion("flow_users <=", value, "flowUsers");
            return (Criteria) this;
        }

        public Criteria andFlowUsersLike(String value) {
            addCriterion("flow_users like", value, "flowUsers");
            return (Criteria) this;
        }

        public Criteria andFlowUsersNotLike(String value) {
            addCriterion("flow_users not like", value, "flowUsers");
            return (Criteria) this;
        }

        public Criteria andFlowUsersIn(List<String> values) {
            addCriterion("flow_users in", values, "flowUsers");
            return (Criteria) this;
        }

        public Criteria andFlowUsersNotIn(List<String> values) {
            addCriterion("flow_users not in", values, "flowUsers");
            return (Criteria) this;
        }

        public Criteria andFlowUsersBetween(String value1, String value2) {
            addCriterion("flow_users between", value1, value2, "flowUsers");
            return (Criteria) this;
        }

        public Criteria andFlowUsersNotBetween(String value1, String value2) {
            addCriterion("flow_users not between", value1, value2, "flowUsers");
            return (Criteria) this;
        }

        public Criteria andIsAgreedIsNull() {
            addCriterion("is_agreed is null");
            return (Criteria) this;
        }

        public Criteria andIsAgreedIsNotNull() {
            addCriterion("is_agreed is not null");
            return (Criteria) this;
        }

        public Criteria andIsAgreedEqualTo(Boolean value) {
            addCriterion("is_agreed =", value, "isAgreed");
            return (Criteria) this;
        }

        public Criteria andIsAgreedNotEqualTo(Boolean value) {
            addCriterion("is_agreed <>", value, "isAgreed");
            return (Criteria) this;
        }

        public Criteria andIsAgreedGreaterThan(Boolean value) {
            addCriterion("is_agreed >", value, "isAgreed");
            return (Criteria) this;
        }

        public Criteria andIsAgreedGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_agreed >=", value, "isAgreed");
            return (Criteria) this;
        }

        public Criteria andIsAgreedLessThan(Boolean value) {
            addCriterion("is_agreed <", value, "isAgreed");
            return (Criteria) this;
        }

        public Criteria andIsAgreedLessThanOrEqualTo(Boolean value) {
            addCriterion("is_agreed <=", value, "isAgreed");
            return (Criteria) this;
        }

        public Criteria andIsAgreedIn(List<Boolean> values) {
            addCriterion("is_agreed in", values, "isAgreed");
            return (Criteria) this;
        }

        public Criteria andIsAgreedNotIn(List<Boolean> values) {
            addCriterion("is_agreed not in", values, "isAgreed");
            return (Criteria) this;
        }

        public Criteria andIsAgreedBetween(Boolean value1, Boolean value2) {
            addCriterion("is_agreed between", value1, value2, "isAgreed");
            return (Criteria) this;
        }

        public Criteria andIsAgreedNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_agreed not between", value1, value2, "isAgreed");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
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