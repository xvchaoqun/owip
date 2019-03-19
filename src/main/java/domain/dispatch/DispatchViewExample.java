package domain.dispatch;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class DispatchViewExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public DispatchViewExample() {
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

        public Criteria andScDispatchIdIsNull() {
            addCriterion("sc_dispatch_id is null");
            return (Criteria) this;
        }

        public Criteria andScDispatchIdIsNotNull() {
            addCriterion("sc_dispatch_id is not null");
            return (Criteria) this;
        }

        public Criteria andScDispatchIdEqualTo(Integer value) {
            addCriterion("sc_dispatch_id =", value, "scDispatchId");
            return (Criteria) this;
        }

        public Criteria andScDispatchIdNotEqualTo(Integer value) {
            addCriterion("sc_dispatch_id <>", value, "scDispatchId");
            return (Criteria) this;
        }

        public Criteria andScDispatchIdGreaterThan(Integer value) {
            addCriterion("sc_dispatch_id >", value, "scDispatchId");
            return (Criteria) this;
        }

        public Criteria andScDispatchIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("sc_dispatch_id >=", value, "scDispatchId");
            return (Criteria) this;
        }

        public Criteria andScDispatchIdLessThan(Integer value) {
            addCriterion("sc_dispatch_id <", value, "scDispatchId");
            return (Criteria) this;
        }

        public Criteria andScDispatchIdLessThanOrEqualTo(Integer value) {
            addCriterion("sc_dispatch_id <=", value, "scDispatchId");
            return (Criteria) this;
        }

        public Criteria andScDispatchIdIn(List<Integer> values) {
            addCriterion("sc_dispatch_id in", values, "scDispatchId");
            return (Criteria) this;
        }

        public Criteria andScDispatchIdNotIn(List<Integer> values) {
            addCriterion("sc_dispatch_id not in", values, "scDispatchId");
            return (Criteria) this;
        }

        public Criteria andScDispatchIdBetween(Integer value1, Integer value2) {
            addCriterion("sc_dispatch_id between", value1, value2, "scDispatchId");
            return (Criteria) this;
        }

        public Criteria andScDispatchIdNotBetween(Integer value1, Integer value2) {
            addCriterion("sc_dispatch_id not between", value1, value2, "scDispatchId");
            return (Criteria) this;
        }

        public Criteria andYearIsNull() {
            addCriterion("year is null");
            return (Criteria) this;
        }

        public Criteria andYearIsNotNull() {
            addCriterion("year is not null");
            return (Criteria) this;
        }

        public Criteria andYearEqualTo(Integer value) {
            addCriterion("year =", value, "year");
            return (Criteria) this;
        }

        public Criteria andYearNotEqualTo(Integer value) {
            addCriterion("year <>", value, "year");
            return (Criteria) this;
        }

        public Criteria andYearGreaterThan(Integer value) {
            addCriterion("year >", value, "year");
            return (Criteria) this;
        }

        public Criteria andYearGreaterThanOrEqualTo(Integer value) {
            addCriterion("year >=", value, "year");
            return (Criteria) this;
        }

        public Criteria andYearLessThan(Integer value) {
            addCriterion("year <", value, "year");
            return (Criteria) this;
        }

        public Criteria andYearLessThanOrEqualTo(Integer value) {
            addCriterion("year <=", value, "year");
            return (Criteria) this;
        }

        public Criteria andYearIn(List<Integer> values) {
            addCriterion("year in", values, "year");
            return (Criteria) this;
        }

        public Criteria andYearNotIn(List<Integer> values) {
            addCriterion("year not in", values, "year");
            return (Criteria) this;
        }

        public Criteria andYearBetween(Integer value1, Integer value2) {
            addCriterion("year between", value1, value2, "year");
            return (Criteria) this;
        }

        public Criteria andYearNotBetween(Integer value1, Integer value2) {
            addCriterion("year not between", value1, value2, "year");
            return (Criteria) this;
        }

        public Criteria andDispatchTypeIdIsNull() {
            addCriterion("dispatch_type_id is null");
            return (Criteria) this;
        }

        public Criteria andDispatchTypeIdIsNotNull() {
            addCriterion("dispatch_type_id is not null");
            return (Criteria) this;
        }

        public Criteria andDispatchTypeIdEqualTo(Integer value) {
            addCriterion("dispatch_type_id =", value, "dispatchTypeId");
            return (Criteria) this;
        }

        public Criteria andDispatchTypeIdNotEqualTo(Integer value) {
            addCriterion("dispatch_type_id <>", value, "dispatchTypeId");
            return (Criteria) this;
        }

        public Criteria andDispatchTypeIdGreaterThan(Integer value) {
            addCriterion("dispatch_type_id >", value, "dispatchTypeId");
            return (Criteria) this;
        }

        public Criteria andDispatchTypeIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("dispatch_type_id >=", value, "dispatchTypeId");
            return (Criteria) this;
        }

        public Criteria andDispatchTypeIdLessThan(Integer value) {
            addCriterion("dispatch_type_id <", value, "dispatchTypeId");
            return (Criteria) this;
        }

        public Criteria andDispatchTypeIdLessThanOrEqualTo(Integer value) {
            addCriterion("dispatch_type_id <=", value, "dispatchTypeId");
            return (Criteria) this;
        }

        public Criteria andDispatchTypeIdIn(List<Integer> values) {
            addCriterion("dispatch_type_id in", values, "dispatchTypeId");
            return (Criteria) this;
        }

        public Criteria andDispatchTypeIdNotIn(List<Integer> values) {
            addCriterion("dispatch_type_id not in", values, "dispatchTypeId");
            return (Criteria) this;
        }

        public Criteria andDispatchTypeIdBetween(Integer value1, Integer value2) {
            addCriterion("dispatch_type_id between", value1, value2, "dispatchTypeId");
            return (Criteria) this;
        }

        public Criteria andDispatchTypeIdNotBetween(Integer value1, Integer value2) {
            addCriterion("dispatch_type_id not between", value1, value2, "dispatchTypeId");
            return (Criteria) this;
        }

        public Criteria andCodeIsNull() {
            addCriterion("code is null");
            return (Criteria) this;
        }

        public Criteria andCodeIsNotNull() {
            addCriterion("code is not null");
            return (Criteria) this;
        }

        public Criteria andCodeEqualTo(String value) {
            addCriterion("code =", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeNotEqualTo(String value) {
            addCriterion("code <>", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeGreaterThan(String value) {
            addCriterion("code >", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeGreaterThanOrEqualTo(String value) {
            addCriterion("code >=", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeLessThan(String value) {
            addCriterion("code <", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeLessThanOrEqualTo(String value) {
            addCriterion("code <=", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeLike(String value) {
            addCriterion("code like", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeNotLike(String value) {
            addCriterion("code not like", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeIn(List<String> values) {
            addCriterion("code in", values, "code");
            return (Criteria) this;
        }

        public Criteria andCodeNotIn(List<String> values) {
            addCriterion("code not in", values, "code");
            return (Criteria) this;
        }

        public Criteria andCodeBetween(String value1, String value2) {
            addCriterion("code between", value1, value2, "code");
            return (Criteria) this;
        }

        public Criteria andCodeNotBetween(String value1, String value2) {
            addCriterion("code not between", value1, value2, "code");
            return (Criteria) this;
        }

        public Criteria andCategoryIsNull() {
            addCriterion("category is null");
            return (Criteria) this;
        }

        public Criteria andCategoryIsNotNull() {
            addCriterion("category is not null");
            return (Criteria) this;
        }

        public Criteria andCategoryEqualTo(String value) {
            addCriterion("category =", value, "category");
            return (Criteria) this;
        }

        public Criteria andCategoryNotEqualTo(String value) {
            addCriterion("category <>", value, "category");
            return (Criteria) this;
        }

        public Criteria andCategoryGreaterThan(String value) {
            addCriterion("category >", value, "category");
            return (Criteria) this;
        }

        public Criteria andCategoryGreaterThanOrEqualTo(String value) {
            addCriterion("category >=", value, "category");
            return (Criteria) this;
        }

        public Criteria andCategoryLessThan(String value) {
            addCriterion("category <", value, "category");
            return (Criteria) this;
        }

        public Criteria andCategoryLessThanOrEqualTo(String value) {
            addCriterion("category <=", value, "category");
            return (Criteria) this;
        }

        public Criteria andCategoryLike(String value) {
            addCriterion("category like", value, "category");
            return (Criteria) this;
        }

        public Criteria andCategoryNotLike(String value) {
            addCriterion("category not like", value, "category");
            return (Criteria) this;
        }

        public Criteria andCategoryIn(List<String> values) {
            addCriterion("category in", values, "category");
            return (Criteria) this;
        }

        public Criteria andCategoryNotIn(List<String> values) {
            addCriterion("category not in", values, "category");
            return (Criteria) this;
        }

        public Criteria andCategoryBetween(String value1, String value2) {
            addCriterion("category between", value1, value2, "category");
            return (Criteria) this;
        }

        public Criteria andCategoryNotBetween(String value1, String value2) {
            addCriterion("category not between", value1, value2, "category");
            return (Criteria) this;
        }

        public Criteria andMeetingTimeIsNull() {
            addCriterion("meeting_time is null");
            return (Criteria) this;
        }

        public Criteria andMeetingTimeIsNotNull() {
            addCriterion("meeting_time is not null");
            return (Criteria) this;
        }

        public Criteria andMeetingTimeEqualTo(Date value) {
            addCriterionForJDBCDate("meeting_time =", value, "meetingTime");
            return (Criteria) this;
        }

        public Criteria andMeetingTimeNotEqualTo(Date value) {
            addCriterionForJDBCDate("meeting_time <>", value, "meetingTime");
            return (Criteria) this;
        }

        public Criteria andMeetingTimeGreaterThan(Date value) {
            addCriterionForJDBCDate("meeting_time >", value, "meetingTime");
            return (Criteria) this;
        }

        public Criteria andMeetingTimeGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("meeting_time >=", value, "meetingTime");
            return (Criteria) this;
        }

        public Criteria andMeetingTimeLessThan(Date value) {
            addCriterionForJDBCDate("meeting_time <", value, "meetingTime");
            return (Criteria) this;
        }

        public Criteria andMeetingTimeLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("meeting_time <=", value, "meetingTime");
            return (Criteria) this;
        }

        public Criteria andMeetingTimeIn(List<Date> values) {
            addCriterionForJDBCDate("meeting_time in", values, "meetingTime");
            return (Criteria) this;
        }

        public Criteria andMeetingTimeNotIn(List<Date> values) {
            addCriterionForJDBCDate("meeting_time not in", values, "meetingTime");
            return (Criteria) this;
        }

        public Criteria andMeetingTimeBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("meeting_time between", value1, value2, "meetingTime");
            return (Criteria) this;
        }

        public Criteria andMeetingTimeNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("meeting_time not between", value1, value2, "meetingTime");
            return (Criteria) this;
        }

        public Criteria andPubTimeIsNull() {
            addCriterion("pub_time is null");
            return (Criteria) this;
        }

        public Criteria andPubTimeIsNotNull() {
            addCriterion("pub_time is not null");
            return (Criteria) this;
        }

        public Criteria andPubTimeEqualTo(Date value) {
            addCriterionForJDBCDate("pub_time =", value, "pubTime");
            return (Criteria) this;
        }

        public Criteria andPubTimeNotEqualTo(Date value) {
            addCriterionForJDBCDate("pub_time <>", value, "pubTime");
            return (Criteria) this;
        }

        public Criteria andPubTimeGreaterThan(Date value) {
            addCriterionForJDBCDate("pub_time >", value, "pubTime");
            return (Criteria) this;
        }

        public Criteria andPubTimeGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("pub_time >=", value, "pubTime");
            return (Criteria) this;
        }

        public Criteria andPubTimeLessThan(Date value) {
            addCriterionForJDBCDate("pub_time <", value, "pubTime");
            return (Criteria) this;
        }

        public Criteria andPubTimeLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("pub_time <=", value, "pubTime");
            return (Criteria) this;
        }

        public Criteria andPubTimeIn(List<Date> values) {
            addCriterionForJDBCDate("pub_time in", values, "pubTime");
            return (Criteria) this;
        }

        public Criteria andPubTimeNotIn(List<Date> values) {
            addCriterionForJDBCDate("pub_time not in", values, "pubTime");
            return (Criteria) this;
        }

        public Criteria andPubTimeBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("pub_time between", value1, value2, "pubTime");
            return (Criteria) this;
        }

        public Criteria andPubTimeNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("pub_time not between", value1, value2, "pubTime");
            return (Criteria) this;
        }

        public Criteria andWorkTimeIsNull() {
            addCriterion("work_time is null");
            return (Criteria) this;
        }

        public Criteria andWorkTimeIsNotNull() {
            addCriterion("work_time is not null");
            return (Criteria) this;
        }

        public Criteria andWorkTimeEqualTo(Date value) {
            addCriterionForJDBCDate("work_time =", value, "workTime");
            return (Criteria) this;
        }

        public Criteria andWorkTimeNotEqualTo(Date value) {
            addCriterionForJDBCDate("work_time <>", value, "workTime");
            return (Criteria) this;
        }

        public Criteria andWorkTimeGreaterThan(Date value) {
            addCriterionForJDBCDate("work_time >", value, "workTime");
            return (Criteria) this;
        }

        public Criteria andWorkTimeGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("work_time >=", value, "workTime");
            return (Criteria) this;
        }

        public Criteria andWorkTimeLessThan(Date value) {
            addCriterionForJDBCDate("work_time <", value, "workTime");
            return (Criteria) this;
        }

        public Criteria andWorkTimeLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("work_time <=", value, "workTime");
            return (Criteria) this;
        }

        public Criteria andWorkTimeIn(List<Date> values) {
            addCriterionForJDBCDate("work_time in", values, "workTime");
            return (Criteria) this;
        }

        public Criteria andWorkTimeNotIn(List<Date> values) {
            addCriterionForJDBCDate("work_time not in", values, "workTime");
            return (Criteria) this;
        }

        public Criteria andWorkTimeBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("work_time between", value1, value2, "workTime");
            return (Criteria) this;
        }

        public Criteria andWorkTimeNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("work_time not between", value1, value2, "workTime");
            return (Criteria) this;
        }

        public Criteria andAppointCountIsNull() {
            addCriterion("appoint_count is null");
            return (Criteria) this;
        }

        public Criteria andAppointCountIsNotNull() {
            addCriterion("appoint_count is not null");
            return (Criteria) this;
        }

        public Criteria andAppointCountEqualTo(Integer value) {
            addCriterion("appoint_count =", value, "appointCount");
            return (Criteria) this;
        }

        public Criteria andAppointCountNotEqualTo(Integer value) {
            addCriterion("appoint_count <>", value, "appointCount");
            return (Criteria) this;
        }

        public Criteria andAppointCountGreaterThan(Integer value) {
            addCriterion("appoint_count >", value, "appointCount");
            return (Criteria) this;
        }

        public Criteria andAppointCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("appoint_count >=", value, "appointCount");
            return (Criteria) this;
        }

        public Criteria andAppointCountLessThan(Integer value) {
            addCriterion("appoint_count <", value, "appointCount");
            return (Criteria) this;
        }

        public Criteria andAppointCountLessThanOrEqualTo(Integer value) {
            addCriterion("appoint_count <=", value, "appointCount");
            return (Criteria) this;
        }

        public Criteria andAppointCountIn(List<Integer> values) {
            addCriterion("appoint_count in", values, "appointCount");
            return (Criteria) this;
        }

        public Criteria andAppointCountNotIn(List<Integer> values) {
            addCriterion("appoint_count not in", values, "appointCount");
            return (Criteria) this;
        }

        public Criteria andAppointCountBetween(Integer value1, Integer value2) {
            addCriterion("appoint_count between", value1, value2, "appointCount");
            return (Criteria) this;
        }

        public Criteria andAppointCountNotBetween(Integer value1, Integer value2) {
            addCriterion("appoint_count not between", value1, value2, "appointCount");
            return (Criteria) this;
        }

        public Criteria andRealAppointCountIsNull() {
            addCriterion("real_appoint_count is null");
            return (Criteria) this;
        }

        public Criteria andRealAppointCountIsNotNull() {
            addCriterion("real_appoint_count is not null");
            return (Criteria) this;
        }

        public Criteria andRealAppointCountEqualTo(Integer value) {
            addCriterion("real_appoint_count =", value, "realAppointCount");
            return (Criteria) this;
        }

        public Criteria andRealAppointCountNotEqualTo(Integer value) {
            addCriterion("real_appoint_count <>", value, "realAppointCount");
            return (Criteria) this;
        }

        public Criteria andRealAppointCountGreaterThan(Integer value) {
            addCriterion("real_appoint_count >", value, "realAppointCount");
            return (Criteria) this;
        }

        public Criteria andRealAppointCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("real_appoint_count >=", value, "realAppointCount");
            return (Criteria) this;
        }

        public Criteria andRealAppointCountLessThan(Integer value) {
            addCriterion("real_appoint_count <", value, "realAppointCount");
            return (Criteria) this;
        }

        public Criteria andRealAppointCountLessThanOrEqualTo(Integer value) {
            addCriterion("real_appoint_count <=", value, "realAppointCount");
            return (Criteria) this;
        }

        public Criteria andRealAppointCountIn(List<Integer> values) {
            addCriterion("real_appoint_count in", values, "realAppointCount");
            return (Criteria) this;
        }

        public Criteria andRealAppointCountNotIn(List<Integer> values) {
            addCriterion("real_appoint_count not in", values, "realAppointCount");
            return (Criteria) this;
        }

        public Criteria andRealAppointCountBetween(Integer value1, Integer value2) {
            addCriterion("real_appoint_count between", value1, value2, "realAppointCount");
            return (Criteria) this;
        }

        public Criteria andRealAppointCountNotBetween(Integer value1, Integer value2) {
            addCriterion("real_appoint_count not between", value1, value2, "realAppointCount");
            return (Criteria) this;
        }

        public Criteria andDismissCountIsNull() {
            addCriterion("dismiss_count is null");
            return (Criteria) this;
        }

        public Criteria andDismissCountIsNotNull() {
            addCriterion("dismiss_count is not null");
            return (Criteria) this;
        }

        public Criteria andDismissCountEqualTo(Integer value) {
            addCriterion("dismiss_count =", value, "dismissCount");
            return (Criteria) this;
        }

        public Criteria andDismissCountNotEqualTo(Integer value) {
            addCriterion("dismiss_count <>", value, "dismissCount");
            return (Criteria) this;
        }

        public Criteria andDismissCountGreaterThan(Integer value) {
            addCriterion("dismiss_count >", value, "dismissCount");
            return (Criteria) this;
        }

        public Criteria andDismissCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("dismiss_count >=", value, "dismissCount");
            return (Criteria) this;
        }

        public Criteria andDismissCountLessThan(Integer value) {
            addCriterion("dismiss_count <", value, "dismissCount");
            return (Criteria) this;
        }

        public Criteria andDismissCountLessThanOrEqualTo(Integer value) {
            addCriterion("dismiss_count <=", value, "dismissCount");
            return (Criteria) this;
        }

        public Criteria andDismissCountIn(List<Integer> values) {
            addCriterion("dismiss_count in", values, "dismissCount");
            return (Criteria) this;
        }

        public Criteria andDismissCountNotIn(List<Integer> values) {
            addCriterion("dismiss_count not in", values, "dismissCount");
            return (Criteria) this;
        }

        public Criteria andDismissCountBetween(Integer value1, Integer value2) {
            addCriterion("dismiss_count between", value1, value2, "dismissCount");
            return (Criteria) this;
        }

        public Criteria andDismissCountNotBetween(Integer value1, Integer value2) {
            addCriterion("dismiss_count not between", value1, value2, "dismissCount");
            return (Criteria) this;
        }

        public Criteria andRealDismissCountIsNull() {
            addCriterion("real_dismiss_count is null");
            return (Criteria) this;
        }

        public Criteria andRealDismissCountIsNotNull() {
            addCriterion("real_dismiss_count is not null");
            return (Criteria) this;
        }

        public Criteria andRealDismissCountEqualTo(Integer value) {
            addCriterion("real_dismiss_count =", value, "realDismissCount");
            return (Criteria) this;
        }

        public Criteria andRealDismissCountNotEqualTo(Integer value) {
            addCriterion("real_dismiss_count <>", value, "realDismissCount");
            return (Criteria) this;
        }

        public Criteria andRealDismissCountGreaterThan(Integer value) {
            addCriterion("real_dismiss_count >", value, "realDismissCount");
            return (Criteria) this;
        }

        public Criteria andRealDismissCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("real_dismiss_count >=", value, "realDismissCount");
            return (Criteria) this;
        }

        public Criteria andRealDismissCountLessThan(Integer value) {
            addCriterion("real_dismiss_count <", value, "realDismissCount");
            return (Criteria) this;
        }

        public Criteria andRealDismissCountLessThanOrEqualTo(Integer value) {
            addCriterion("real_dismiss_count <=", value, "realDismissCount");
            return (Criteria) this;
        }

        public Criteria andRealDismissCountIn(List<Integer> values) {
            addCriterion("real_dismiss_count in", values, "realDismissCount");
            return (Criteria) this;
        }

        public Criteria andRealDismissCountNotIn(List<Integer> values) {
            addCriterion("real_dismiss_count not in", values, "realDismissCount");
            return (Criteria) this;
        }

        public Criteria andRealDismissCountBetween(Integer value1, Integer value2) {
            addCriterion("real_dismiss_count between", value1, value2, "realDismissCount");
            return (Criteria) this;
        }

        public Criteria andRealDismissCountNotBetween(Integer value1, Integer value2) {
            addCriterion("real_dismiss_count not between", value1, value2, "realDismissCount");
            return (Criteria) this;
        }

        public Criteria andHasCheckedIsNull() {
            addCriterion("has_checked is null");
            return (Criteria) this;
        }

        public Criteria andHasCheckedIsNotNull() {
            addCriterion("has_checked is not null");
            return (Criteria) this;
        }

        public Criteria andHasCheckedEqualTo(Boolean value) {
            addCriterion("has_checked =", value, "hasChecked");
            return (Criteria) this;
        }

        public Criteria andHasCheckedNotEqualTo(Boolean value) {
            addCriterion("has_checked <>", value, "hasChecked");
            return (Criteria) this;
        }

        public Criteria andHasCheckedGreaterThan(Boolean value) {
            addCriterion("has_checked >", value, "hasChecked");
            return (Criteria) this;
        }

        public Criteria andHasCheckedGreaterThanOrEqualTo(Boolean value) {
            addCriterion("has_checked >=", value, "hasChecked");
            return (Criteria) this;
        }

        public Criteria andHasCheckedLessThan(Boolean value) {
            addCriterion("has_checked <", value, "hasChecked");
            return (Criteria) this;
        }

        public Criteria andHasCheckedLessThanOrEqualTo(Boolean value) {
            addCriterion("has_checked <=", value, "hasChecked");
            return (Criteria) this;
        }

        public Criteria andHasCheckedIn(List<Boolean> values) {
            addCriterion("has_checked in", values, "hasChecked");
            return (Criteria) this;
        }

        public Criteria andHasCheckedNotIn(List<Boolean> values) {
            addCriterion("has_checked not in", values, "hasChecked");
            return (Criteria) this;
        }

        public Criteria andHasCheckedBetween(Boolean value1, Boolean value2) {
            addCriterion("has_checked between", value1, value2, "hasChecked");
            return (Criteria) this;
        }

        public Criteria andHasCheckedNotBetween(Boolean value1, Boolean value2) {
            addCriterion("has_checked not between", value1, value2, "hasChecked");
            return (Criteria) this;
        }

        public Criteria andFileIsNull() {
            addCriterion("file is null");
            return (Criteria) this;
        }

        public Criteria andFileIsNotNull() {
            addCriterion("file is not null");
            return (Criteria) this;
        }

        public Criteria andFileEqualTo(String value) {
            addCriterion("file =", value, "file");
            return (Criteria) this;
        }

        public Criteria andFileNotEqualTo(String value) {
            addCriterion("file <>", value, "file");
            return (Criteria) this;
        }

        public Criteria andFileGreaterThan(String value) {
            addCriterion("file >", value, "file");
            return (Criteria) this;
        }

        public Criteria andFileGreaterThanOrEqualTo(String value) {
            addCriterion("file >=", value, "file");
            return (Criteria) this;
        }

        public Criteria andFileLessThan(String value) {
            addCriterion("file <", value, "file");
            return (Criteria) this;
        }

        public Criteria andFileLessThanOrEqualTo(String value) {
            addCriterion("file <=", value, "file");
            return (Criteria) this;
        }

        public Criteria andFileLike(String value) {
            addCriterion("file like", value, "file");
            return (Criteria) this;
        }

        public Criteria andFileNotLike(String value) {
            addCriterion("file not like", value, "file");
            return (Criteria) this;
        }

        public Criteria andFileIn(List<String> values) {
            addCriterion("file in", values, "file");
            return (Criteria) this;
        }

        public Criteria andFileNotIn(List<String> values) {
            addCriterion("file not in", values, "file");
            return (Criteria) this;
        }

        public Criteria andFileBetween(String value1, String value2) {
            addCriterion("file between", value1, value2, "file");
            return (Criteria) this;
        }

        public Criteria andFileNotBetween(String value1, String value2) {
            addCriterion("file not between", value1, value2, "file");
            return (Criteria) this;
        }

        public Criteria andFileNameIsNull() {
            addCriterion("file_name is null");
            return (Criteria) this;
        }

        public Criteria andFileNameIsNotNull() {
            addCriterion("file_name is not null");
            return (Criteria) this;
        }

        public Criteria andFileNameEqualTo(String value) {
            addCriterion("file_name =", value, "fileName");
            return (Criteria) this;
        }

        public Criteria andFileNameNotEqualTo(String value) {
            addCriterion("file_name <>", value, "fileName");
            return (Criteria) this;
        }

        public Criteria andFileNameGreaterThan(String value) {
            addCriterion("file_name >", value, "fileName");
            return (Criteria) this;
        }

        public Criteria andFileNameGreaterThanOrEqualTo(String value) {
            addCriterion("file_name >=", value, "fileName");
            return (Criteria) this;
        }

        public Criteria andFileNameLessThan(String value) {
            addCriterion("file_name <", value, "fileName");
            return (Criteria) this;
        }

        public Criteria andFileNameLessThanOrEqualTo(String value) {
            addCriterion("file_name <=", value, "fileName");
            return (Criteria) this;
        }

        public Criteria andFileNameLike(String value) {
            addCriterion("file_name like", value, "fileName");
            return (Criteria) this;
        }

        public Criteria andFileNameNotLike(String value) {
            addCriterion("file_name not like", value, "fileName");
            return (Criteria) this;
        }

        public Criteria andFileNameIn(List<String> values) {
            addCriterion("file_name in", values, "fileName");
            return (Criteria) this;
        }

        public Criteria andFileNameNotIn(List<String> values) {
            addCriterion("file_name not in", values, "fileName");
            return (Criteria) this;
        }

        public Criteria andFileNameBetween(String value1, String value2) {
            addCriterion("file_name between", value1, value2, "fileName");
            return (Criteria) this;
        }

        public Criteria andFileNameNotBetween(String value1, String value2) {
            addCriterion("file_name not between", value1, value2, "fileName");
            return (Criteria) this;
        }

        public Criteria andPptIsNull() {
            addCriterion("ppt is null");
            return (Criteria) this;
        }

        public Criteria andPptIsNotNull() {
            addCriterion("ppt is not null");
            return (Criteria) this;
        }

        public Criteria andPptEqualTo(String value) {
            addCriterion("ppt =", value, "ppt");
            return (Criteria) this;
        }

        public Criteria andPptNotEqualTo(String value) {
            addCriterion("ppt <>", value, "ppt");
            return (Criteria) this;
        }

        public Criteria andPptGreaterThan(String value) {
            addCriterion("ppt >", value, "ppt");
            return (Criteria) this;
        }

        public Criteria andPptGreaterThanOrEqualTo(String value) {
            addCriterion("ppt >=", value, "ppt");
            return (Criteria) this;
        }

        public Criteria andPptLessThan(String value) {
            addCriterion("ppt <", value, "ppt");
            return (Criteria) this;
        }

        public Criteria andPptLessThanOrEqualTo(String value) {
            addCriterion("ppt <=", value, "ppt");
            return (Criteria) this;
        }

        public Criteria andPptLike(String value) {
            addCriterion("ppt like", value, "ppt");
            return (Criteria) this;
        }

        public Criteria andPptNotLike(String value) {
            addCriterion("ppt not like", value, "ppt");
            return (Criteria) this;
        }

        public Criteria andPptIn(List<String> values) {
            addCriterion("ppt in", values, "ppt");
            return (Criteria) this;
        }

        public Criteria andPptNotIn(List<String> values) {
            addCriterion("ppt not in", values, "ppt");
            return (Criteria) this;
        }

        public Criteria andPptBetween(String value1, String value2) {
            addCriterion("ppt between", value1, value2, "ppt");
            return (Criteria) this;
        }

        public Criteria andPptNotBetween(String value1, String value2) {
            addCriterion("ppt not between", value1, value2, "ppt");
            return (Criteria) this;
        }

        public Criteria andPptNameIsNull() {
            addCriterion("ppt_name is null");
            return (Criteria) this;
        }

        public Criteria andPptNameIsNotNull() {
            addCriterion("ppt_name is not null");
            return (Criteria) this;
        }

        public Criteria andPptNameEqualTo(String value) {
            addCriterion("ppt_name =", value, "pptName");
            return (Criteria) this;
        }

        public Criteria andPptNameNotEqualTo(String value) {
            addCriterion("ppt_name <>", value, "pptName");
            return (Criteria) this;
        }

        public Criteria andPptNameGreaterThan(String value) {
            addCriterion("ppt_name >", value, "pptName");
            return (Criteria) this;
        }

        public Criteria andPptNameGreaterThanOrEqualTo(String value) {
            addCriterion("ppt_name >=", value, "pptName");
            return (Criteria) this;
        }

        public Criteria andPptNameLessThan(String value) {
            addCriterion("ppt_name <", value, "pptName");
            return (Criteria) this;
        }

        public Criteria andPptNameLessThanOrEqualTo(String value) {
            addCriterion("ppt_name <=", value, "pptName");
            return (Criteria) this;
        }

        public Criteria andPptNameLike(String value) {
            addCriterion("ppt_name like", value, "pptName");
            return (Criteria) this;
        }

        public Criteria andPptNameNotLike(String value) {
            addCriterion("ppt_name not like", value, "pptName");
            return (Criteria) this;
        }

        public Criteria andPptNameIn(List<String> values) {
            addCriterion("ppt_name in", values, "pptName");
            return (Criteria) this;
        }

        public Criteria andPptNameNotIn(List<String> values) {
            addCriterion("ppt_name not in", values, "pptName");
            return (Criteria) this;
        }

        public Criteria andPptNameBetween(String value1, String value2) {
            addCriterion("ppt_name between", value1, value2, "pptName");
            return (Criteria) this;
        }

        public Criteria andPptNameNotBetween(String value1, String value2) {
            addCriterion("ppt_name not between", value1, value2, "pptName");
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