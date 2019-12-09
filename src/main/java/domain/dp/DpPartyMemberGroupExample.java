package domain.dp;

import shiro.ShiroHelper;
import sys.constants.SystemConstants;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class DpPartyMemberGroupExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public DpPartyMemberGroupExample() {
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

        public Criteria andFidIsNull() {
            addCriterion("fid is null");
            return (Criteria) this;
        }

        public Criteria andFidIsNotNull() {
            addCriterion("fid is not null");
            return (Criteria) this;
        }

        public Criteria andFidEqualTo(Integer value) {
            addCriterion("fid =", value, "fid");
            return (Criteria) this;
        }

        public Criteria andFidNotEqualTo(Integer value) {
            addCriterion("fid <>", value, "fid");
            return (Criteria) this;
        }

        public Criteria andFidGreaterThan(Integer value) {
            addCriterion("fid >", value, "fid");
            return (Criteria) this;
        }

        public Criteria andFidGreaterThanOrEqualTo(Integer value) {
            addCriterion("fid >=", value, "fid");
            return (Criteria) this;
        }

        public Criteria andFidLessThan(Integer value) {
            addCriterion("fid <", value, "fid");
            return (Criteria) this;
        }

        public Criteria andFidLessThanOrEqualTo(Integer value) {
            addCriterion("fid <=", value, "fid");
            return (Criteria) this;
        }

        public Criteria andFidIn(List<Integer> values) {
            addCriterion("fid in", values, "fid");
            return (Criteria) this;
        }

        public Criteria andFidNotIn(List<Integer> values) {
            addCriterion("fid not in", values, "fid");
            return (Criteria) this;
        }

        public Criteria andFidBetween(Integer value1, Integer value2) {
            addCriterion("fid between", value1, value2, "fid");
            return (Criteria) this;
        }

        public Criteria andFidNotBetween(Integer value1, Integer value2) {
            addCriterion("fid not between", value1, value2, "fid");
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

        public Criteria andNameIsNull() {
            addCriterion("name is null");
            return (Criteria) this;
        }

        public Criteria andNameIsNotNull() {
            addCriterion("name is not null");
            return (Criteria) this;
        }

        public Criteria andNameEqualTo(String value) {
            addCriterion("name =", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotEqualTo(String value) {
            addCriterion("name <>", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThan(String value) {
            addCriterion("name >", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThanOrEqualTo(String value) {
            addCriterion("name >=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThan(String value) {
            addCriterion("name <", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThanOrEqualTo(String value) {
            addCriterion("name <=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLike(String value) {
            addCriterion("name like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotLike(String value) {
            addCriterion("name not like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameIn(List<String> values) {
            addCriterion("name in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotIn(List<String> values) {
            addCriterion("name not in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameBetween(String value1, String value2) {
            addCriterion("name between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotBetween(String value1, String value2) {
            addCriterion("name not between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andIsPresentIsNull() {
            addCriterion("is_present is null");
            return (Criteria) this;
        }

        public Criteria andIsPresentIsNotNull() {
            addCriterion("is_present is not null");
            return (Criteria) this;
        }

        public Criteria andIsPresentEqualTo(Boolean value) {
            addCriterion("is_present =", value, "isPresent");
            return (Criteria) this;
        }

        public Criteria andIsPresentNotEqualTo(Boolean value) {
            addCriterion("is_present <>", value, "isPresent");
            return (Criteria) this;
        }

        public Criteria andIsPresentGreaterThan(Boolean value) {
            addCriterion("is_present >", value, "isPresent");
            return (Criteria) this;
        }

        public Criteria andIsPresentGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_present >=", value, "isPresent");
            return (Criteria) this;
        }

        public Criteria andIsPresentLessThan(Boolean value) {
            addCriterion("is_present <", value, "isPresent");
            return (Criteria) this;
        }

        public Criteria andIsPresentLessThanOrEqualTo(Boolean value) {
            addCriterion("is_present <=", value, "isPresent");
            return (Criteria) this;
        }

        public Criteria andIsPresentIn(List<Boolean> values) {
            addCriterion("is_present in", values, "isPresent");
            return (Criteria) this;
        }

        public Criteria andIsPresentNotIn(List<Boolean> values) {
            addCriterion("is_present not in", values, "isPresent");
            return (Criteria) this;
        }

        public Criteria andIsPresentBetween(Boolean value1, Boolean value2) {
            addCriterion("is_present between", value1, value2, "isPresent");
            return (Criteria) this;
        }

        public Criteria andIsPresentNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_present not between", value1, value2, "isPresent");
            return (Criteria) this;
        }

        public Criteria andTranTimeIsNull() {
            addCriterion("tran_time is null");
            return (Criteria) this;
        }

        public Criteria andTranTimeIsNotNull() {
            addCriterion("tran_time is not null");
            return (Criteria) this;
        }

        public Criteria andTranTimeEqualTo(Date value) {
            addCriterionForJDBCDate("tran_time =", value, "tranTime");
            return (Criteria) this;
        }

        public Criteria andTranTimeNotEqualTo(Date value) {
            addCriterionForJDBCDate("tran_time <>", value, "tranTime");
            return (Criteria) this;
        }

        public Criteria andTranTimeGreaterThan(Date value) {
            addCriterionForJDBCDate("tran_time >", value, "tranTime");
            return (Criteria) this;
        }

        public Criteria andTranTimeGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("tran_time >=", value, "tranTime");
            return (Criteria) this;
        }

        public Criteria andTranTimeLessThan(Date value) {
            addCriterionForJDBCDate("tran_time <", value, "tranTime");
            return (Criteria) this;
        }

        public Criteria andTranTimeLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("tran_time <=", value, "tranTime");
            return (Criteria) this;
        }

        public Criteria andTranTimeIn(List<Date> values) {
            addCriterionForJDBCDate("tran_time in", values, "tranTime");
            return (Criteria) this;
        }

        public Criteria andTranTimeNotIn(List<Date> values) {
            addCriterionForJDBCDate("tran_time not in", values, "tranTime");
            return (Criteria) this;
        }

        public Criteria andTranTimeBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("tran_time between", value1, value2, "tranTime");
            return (Criteria) this;
        }

        public Criteria andTranTimeNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("tran_time not between", value1, value2, "tranTime");
            return (Criteria) this;
        }

        public Criteria andActualTranTimeIsNull() {
            addCriterion("actual_tran_time is null");
            return (Criteria) this;
        }

        public Criteria andActualTranTimeIsNotNull() {
            addCriterion("actual_tran_time is not null");
            return (Criteria) this;
        }

        public Criteria andActualTranTimeEqualTo(Date value) {
            addCriterionForJDBCDate("actual_tran_time =", value, "actualTranTime");
            return (Criteria) this;
        }

        public Criteria andActualTranTimeNotEqualTo(Date value) {
            addCriterionForJDBCDate("actual_tran_time <>", value, "actualTranTime");
            return (Criteria) this;
        }

        public Criteria andActualTranTimeGreaterThan(Date value) {
            addCriterionForJDBCDate("actual_tran_time >", value, "actualTranTime");
            return (Criteria) this;
        }

        public Criteria andActualTranTimeGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("actual_tran_time >=", value, "actualTranTime");
            return (Criteria) this;
        }

        public Criteria andActualTranTimeLessThan(Date value) {
            addCriterionForJDBCDate("actual_tran_time <", value, "actualTranTime");
            return (Criteria) this;
        }

        public Criteria andActualTranTimeLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("actual_tran_time <=", value, "actualTranTime");
            return (Criteria) this;
        }

        public Criteria andActualTranTimeIn(List<Date> values) {
            addCriterionForJDBCDate("actual_tran_time in", values, "actualTranTime");
            return (Criteria) this;
        }

        public Criteria andActualTranTimeNotIn(List<Date> values) {
            addCriterionForJDBCDate("actual_tran_time not in", values, "actualTranTime");
            return (Criteria) this;
        }

        public Criteria andActualTranTimeBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("actual_tran_time between", value1, value2, "actualTranTime");
            return (Criteria) this;
        }

        public Criteria andActualTranTimeNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("actual_tran_time not between", value1, value2, "actualTranTime");
            return (Criteria) this;
        }

        public Criteria andAppointTimeIsNull() {
            addCriterion("appoint_time is null");
            return (Criteria) this;
        }

        public Criteria andAppointTimeIsNotNull() {
            addCriterion("appoint_time is not null");
            return (Criteria) this;
        }

        public Criteria andAppointTimeEqualTo(Date value) {
            addCriterionForJDBCDate("appoint_time =", value, "appointTime");
            return (Criteria) this;
        }

        public Criteria andAppointTimeNotEqualTo(Date value) {
            addCriterionForJDBCDate("appoint_time <>", value, "appointTime");
            return (Criteria) this;
        }

        public Criteria andAppointTimeGreaterThan(Date value) {
            addCriterionForJDBCDate("appoint_time >", value, "appointTime");
            return (Criteria) this;
        }

        public Criteria andAppointTimeGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("appoint_time >=", value, "appointTime");
            return (Criteria) this;
        }

        public Criteria andAppointTimeLessThan(Date value) {
            addCriterionForJDBCDate("appoint_time <", value, "appointTime");
            return (Criteria) this;
        }

        public Criteria andAppointTimeLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("appoint_time <=", value, "appointTime");
            return (Criteria) this;
        }

        public Criteria andAppointTimeIn(List<Date> values) {
            addCriterionForJDBCDate("appoint_time in", values, "appointTime");
            return (Criteria) this;
        }

        public Criteria andAppointTimeNotIn(List<Date> values) {
            addCriterionForJDBCDate("appoint_time not in", values, "appointTime");
            return (Criteria) this;
        }

        public Criteria andAppointTimeBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("appoint_time between", value1, value2, "appointTime");
            return (Criteria) this;
        }

        public Criteria andAppointTimeNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("appoint_time not between", value1, value2, "appointTime");
            return (Criteria) this;
        }

        public Criteria andDispatchUnitIdIsNull() {
            addCriterion("dispatch_unit_id is null");
            return (Criteria) this;
        }

        public Criteria andDispatchUnitIdIsNotNull() {
            addCriterion("dispatch_unit_id is not null");
            return (Criteria) this;
        }

        public Criteria andDispatchUnitIdEqualTo(Integer value) {
            addCriterion("dispatch_unit_id =", value, "dispatchUnitId");
            return (Criteria) this;
        }

        public Criteria andDispatchUnitIdNotEqualTo(Integer value) {
            addCriterion("dispatch_unit_id <>", value, "dispatchUnitId");
            return (Criteria) this;
        }

        public Criteria andDispatchUnitIdGreaterThan(Integer value) {
            addCriterion("dispatch_unit_id >", value, "dispatchUnitId");
            return (Criteria) this;
        }

        public Criteria andDispatchUnitIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("dispatch_unit_id >=", value, "dispatchUnitId");
            return (Criteria) this;
        }

        public Criteria andDispatchUnitIdLessThan(Integer value) {
            addCriterion("dispatch_unit_id <", value, "dispatchUnitId");
            return (Criteria) this;
        }

        public Criteria andDispatchUnitIdLessThanOrEqualTo(Integer value) {
            addCriterion("dispatch_unit_id <=", value, "dispatchUnitId");
            return (Criteria) this;
        }

        public Criteria andDispatchUnitIdIn(List<Integer> values) {
            addCriterion("dispatch_unit_id in", values, "dispatchUnitId");
            return (Criteria) this;
        }

        public Criteria andDispatchUnitIdNotIn(List<Integer> values) {
            addCriterion("dispatch_unit_id not in", values, "dispatchUnitId");
            return (Criteria) this;
        }

        public Criteria andDispatchUnitIdBetween(Integer value1, Integer value2) {
            addCriterion("dispatch_unit_id between", value1, value2, "dispatchUnitId");
            return (Criteria) this;
        }

        public Criteria andDispatchUnitIdNotBetween(Integer value1, Integer value2) {
            addCriterion("dispatch_unit_id not between", value1, value2, "dispatchUnitId");
            return (Criteria) this;
        }

        public Criteria andSortOrderIsNull() {
            addCriterion("sort_order is null");
            return (Criteria) this;
        }

        public Criteria andSortOrderIsNotNull() {
            addCriterion("sort_order is not null");
            return (Criteria) this;
        }

        public Criteria andSortOrderEqualTo(Integer value) {
            addCriterion("sort_order =", value, "sortOrder");
            return (Criteria) this;
        }

        public Criteria andSortOrderNotEqualTo(Integer value) {
            addCriterion("sort_order <>", value, "sortOrder");
            return (Criteria) this;
        }

        public Criteria andSortOrderGreaterThan(Integer value) {
            addCriterion("sort_order >", value, "sortOrder");
            return (Criteria) this;
        }

        public Criteria andSortOrderGreaterThanOrEqualTo(Integer value) {
            addCriterion("sort_order >=", value, "sortOrder");
            return (Criteria) this;
        }

        public Criteria andSortOrderLessThan(Integer value) {
            addCriterion("sort_order <", value, "sortOrder");
            return (Criteria) this;
        }

        public Criteria andSortOrderLessThanOrEqualTo(Integer value) {
            addCriterion("sort_order <=", value, "sortOrder");
            return (Criteria) this;
        }

        public Criteria andSortOrderIn(List<Integer> values) {
            addCriterion("sort_order in", values, "sortOrder");
            return (Criteria) this;
        }

        public Criteria andSortOrderNotIn(List<Integer> values) {
            addCriterion("sort_order not in", values, "sortOrder");
            return (Criteria) this;
        }

        public Criteria andSortOrderBetween(Integer value1, Integer value2) {
            addCriterion("sort_order between", value1, value2, "sortOrder");
            return (Criteria) this;
        }

        public Criteria andSortOrderNotBetween(Integer value1, Integer value2) {
            addCriterion("sort_order not between", value1, value2, "sortOrder");
            return (Criteria) this;
        }

        public Criteria andIsDeletedIsNull() {
            addCriterion("is_deleted is null");
            return (Criteria) this;
        }

        public Criteria andIsDeletedIsNotNull() {
            addCriterion("is_deleted is not null");
            return (Criteria) this;
        }

        public Criteria andIsDeletedEqualTo(Boolean value) {
            addCriterion("is_deleted =", value, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedNotEqualTo(Boolean value) {
            addCriterion("is_deleted <>", value, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedGreaterThan(Boolean value) {
            addCriterion("is_deleted >", value, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_deleted >=", value, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedLessThan(Boolean value) {
            addCriterion("is_deleted <", value, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedLessThanOrEqualTo(Boolean value) {
            addCriterion("is_deleted <=", value, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedIn(List<Boolean> values) {
            addCriterion("is_deleted in", values, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedNotIn(List<Boolean> values) {
            addCriterion("is_deleted not in", values, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedBetween(Boolean value1, Boolean value2) {
            addCriterion("is_deleted between", value1, value2, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_deleted not between", value1, value2, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andGroupSessionIsNull() {
            addCriterion("group_session is null");
            return (Criteria) this;
        }

        public Criteria andGroupSessionIsNotNull() {
            addCriterion("group_session is not null");
            return (Criteria) this;
        }

        public Criteria andGroupSessionEqualTo(String value) {
            addCriterion("group_session =", value, "groupSession");
            return (Criteria) this;
        }

        public Criteria andGroupSessionNotEqualTo(String value) {
            addCriterion("group_session <>", value, "groupSession");
            return (Criteria) this;
        }

        public Criteria andGroupSessionGreaterThan(String value) {
            addCriterion("group_session >", value, "groupSession");
            return (Criteria) this;
        }

        public Criteria andGroupSessionGreaterThanOrEqualTo(String value) {
            addCriterion("group_session >=", value, "groupSession");
            return (Criteria) this;
        }

        public Criteria andGroupSessionLessThan(String value) {
            addCriterion("group_session <", value, "groupSession");
            return (Criteria) this;
        }

        public Criteria andGroupSessionLessThanOrEqualTo(String value) {
            addCriterion("group_session <=", value, "groupSession");
            return (Criteria) this;
        }

        public Criteria andGroupSessionLike(String value) {
            addCriterion("group_session like", value, "groupSession");
            return (Criteria) this;
        }

        public Criteria andGroupSessionNotLike(String value) {
            addCriterion("group_session not like", value, "groupSession");
            return (Criteria) this;
        }

        public Criteria andGroupSessionIn(List<String> values) {
            addCriterion("group_session in", values, "groupSession");
            return (Criteria) this;
        }

        public Criteria andGroupSessionNotIn(List<String> values) {
            addCriterion("group_session not in", values, "groupSession");
            return (Criteria) this;
        }

        public Criteria andGroupSessionBetween(String value1, String value2) {
            addCriterion("group_session between", value1, value2, "groupSession");
            return (Criteria) this;
        }

        public Criteria andGroupSessionNotBetween(String value1, String value2) {
            addCriterion("group_session not between", value1, value2, "groupSession");
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
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }

        public DpPartyMemberGroupExample.Criteria addPermits(List<Integer> partyIdList){

            if (ShiroHelper.isPermitted((SystemConstants.PERMISSION_DPPARTYVIEWALL)))
                return  this;
            if (partyIdList==null) partyIdList = new ArrayList<>();

            if (!partyIdList.isEmpty())
                andPartyIdIn(partyIdList);
            if (partyIdList.isEmpty())
                andPartyIdIsNull();
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