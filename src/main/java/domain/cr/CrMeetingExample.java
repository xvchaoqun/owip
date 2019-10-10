package domain.cr;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class CrMeetingExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public CrMeetingExample() {
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

        public Criteria andInfoIdIsNull() {
            addCriterion("info_id is null");
            return (Criteria) this;
        }

        public Criteria andInfoIdIsNotNull() {
            addCriterion("info_id is not null");
            return (Criteria) this;
        }

        public Criteria andInfoIdEqualTo(Integer value) {
            addCriterion("info_id =", value, "infoId");
            return (Criteria) this;
        }

        public Criteria andInfoIdNotEqualTo(Integer value) {
            addCriterion("info_id <>", value, "infoId");
            return (Criteria) this;
        }

        public Criteria andInfoIdGreaterThan(Integer value) {
            addCriterion("info_id >", value, "infoId");
            return (Criteria) this;
        }

        public Criteria andInfoIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("info_id >=", value, "infoId");
            return (Criteria) this;
        }

        public Criteria andInfoIdLessThan(Integer value) {
            addCriterion("info_id <", value, "infoId");
            return (Criteria) this;
        }

        public Criteria andInfoIdLessThanOrEqualTo(Integer value) {
            addCriterion("info_id <=", value, "infoId");
            return (Criteria) this;
        }

        public Criteria andInfoIdIn(List<Integer> values) {
            addCriterion("info_id in", values, "infoId");
            return (Criteria) this;
        }

        public Criteria andInfoIdNotIn(List<Integer> values) {
            addCriterion("info_id not in", values, "infoId");
            return (Criteria) this;
        }

        public Criteria andInfoIdBetween(Integer value1, Integer value2) {
            addCriterion("info_id between", value1, value2, "infoId");
            return (Criteria) this;
        }

        public Criteria andInfoIdNotBetween(Integer value1, Integer value2) {
            addCriterion("info_id not between", value1, value2, "infoId");
            return (Criteria) this;
        }

        public Criteria andMeetingDateIsNull() {
            addCriterion("meeting_date is null");
            return (Criteria) this;
        }

        public Criteria andMeetingDateIsNotNull() {
            addCriterion("meeting_date is not null");
            return (Criteria) this;
        }

        public Criteria andMeetingDateEqualTo(Date value) {
            addCriterionForJDBCDate("meeting_date =", value, "meetingDate");
            return (Criteria) this;
        }

        public Criteria andMeetingDateNotEqualTo(Date value) {
            addCriterionForJDBCDate("meeting_date <>", value, "meetingDate");
            return (Criteria) this;
        }

        public Criteria andMeetingDateGreaterThan(Date value) {
            addCriterionForJDBCDate("meeting_date >", value, "meetingDate");
            return (Criteria) this;
        }

        public Criteria andMeetingDateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("meeting_date >=", value, "meetingDate");
            return (Criteria) this;
        }

        public Criteria andMeetingDateLessThan(Date value) {
            addCriterionForJDBCDate("meeting_date <", value, "meetingDate");
            return (Criteria) this;
        }

        public Criteria andMeetingDateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("meeting_date <=", value, "meetingDate");
            return (Criteria) this;
        }

        public Criteria andMeetingDateIn(List<Date> values) {
            addCriterionForJDBCDate("meeting_date in", values, "meetingDate");
            return (Criteria) this;
        }

        public Criteria andMeetingDateNotIn(List<Date> values) {
            addCriterionForJDBCDate("meeting_date not in", values, "meetingDate");
            return (Criteria) this;
        }

        public Criteria andMeetingDateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("meeting_date between", value1, value2, "meetingDate");
            return (Criteria) this;
        }

        public Criteria andMeetingDateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("meeting_date not between", value1, value2, "meetingDate");
            return (Criteria) this;
        }

        public Criteria andPostIdsIsNull() {
            addCriterion("post_ids is null");
            return (Criteria) this;
        }

        public Criteria andPostIdsIsNotNull() {
            addCriterion("post_ids is not null");
            return (Criteria) this;
        }

        public Criteria andPostIdsEqualTo(String value) {
            addCriterion("post_ids =", value, "postIds");
            return (Criteria) this;
        }

        public Criteria andPostIdsNotEqualTo(String value) {
            addCriterion("post_ids <>", value, "postIds");
            return (Criteria) this;
        }

        public Criteria andPostIdsGreaterThan(String value) {
            addCriterion("post_ids >", value, "postIds");
            return (Criteria) this;
        }

        public Criteria andPostIdsGreaterThanOrEqualTo(String value) {
            addCriterion("post_ids >=", value, "postIds");
            return (Criteria) this;
        }

        public Criteria andPostIdsLessThan(String value) {
            addCriterion("post_ids <", value, "postIds");
            return (Criteria) this;
        }

        public Criteria andPostIdsLessThanOrEqualTo(String value) {
            addCriterion("post_ids <=", value, "postIds");
            return (Criteria) this;
        }

        public Criteria andPostIdsLike(String value) {
            addCriterion("post_ids like", value, "postIds");
            return (Criteria) this;
        }

        public Criteria andPostIdsNotLike(String value) {
            addCriterion("post_ids not like", value, "postIds");
            return (Criteria) this;
        }

        public Criteria andPostIdsIn(List<String> values) {
            addCriterion("post_ids in", values, "postIds");
            return (Criteria) this;
        }

        public Criteria andPostIdsNotIn(List<String> values) {
            addCriterion("post_ids not in", values, "postIds");
            return (Criteria) this;
        }

        public Criteria andPostIdsBetween(String value1, String value2) {
            addCriterion("post_ids between", value1, value2, "postIds");
            return (Criteria) this;
        }

        public Criteria andPostIdsNotBetween(String value1, String value2) {
            addCriterion("post_ids not between", value1, value2, "postIds");
            return (Criteria) this;
        }

        public Criteria andRequireNumIsNull() {
            addCriterion("require_num is null");
            return (Criteria) this;
        }

        public Criteria andRequireNumIsNotNull() {
            addCriterion("require_num is not null");
            return (Criteria) this;
        }

        public Criteria andRequireNumEqualTo(Integer value) {
            addCriterion("require_num =", value, "requireNum");
            return (Criteria) this;
        }

        public Criteria andRequireNumNotEqualTo(Integer value) {
            addCriterion("require_num <>", value, "requireNum");
            return (Criteria) this;
        }

        public Criteria andRequireNumGreaterThan(Integer value) {
            addCriterion("require_num >", value, "requireNum");
            return (Criteria) this;
        }

        public Criteria andRequireNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("require_num >=", value, "requireNum");
            return (Criteria) this;
        }

        public Criteria andRequireNumLessThan(Integer value) {
            addCriterion("require_num <", value, "requireNum");
            return (Criteria) this;
        }

        public Criteria andRequireNumLessThanOrEqualTo(Integer value) {
            addCriterion("require_num <=", value, "requireNum");
            return (Criteria) this;
        }

        public Criteria andRequireNumIn(List<Integer> values) {
            addCriterion("require_num in", values, "requireNum");
            return (Criteria) this;
        }

        public Criteria andRequireNumNotIn(List<Integer> values) {
            addCriterion("require_num not in", values, "requireNum");
            return (Criteria) this;
        }

        public Criteria andRequireNumBetween(Integer value1, Integer value2) {
            addCriterion("require_num between", value1, value2, "requireNum");
            return (Criteria) this;
        }

        public Criteria andRequireNumNotBetween(Integer value1, Integer value2) {
            addCriterion("require_num not between", value1, value2, "requireNum");
            return (Criteria) this;
        }

        public Criteria andApplyNumIsNull() {
            addCriterion("apply_num is null");
            return (Criteria) this;
        }

        public Criteria andApplyNumIsNotNull() {
            addCriterion("apply_num is not null");
            return (Criteria) this;
        }

        public Criteria andApplyNumEqualTo(Integer value) {
            addCriterion("apply_num =", value, "applyNum");
            return (Criteria) this;
        }

        public Criteria andApplyNumNotEqualTo(Integer value) {
            addCriterion("apply_num <>", value, "applyNum");
            return (Criteria) this;
        }

        public Criteria andApplyNumGreaterThan(Integer value) {
            addCriterion("apply_num >", value, "applyNum");
            return (Criteria) this;
        }

        public Criteria andApplyNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("apply_num >=", value, "applyNum");
            return (Criteria) this;
        }

        public Criteria andApplyNumLessThan(Integer value) {
            addCriterion("apply_num <", value, "applyNum");
            return (Criteria) this;
        }

        public Criteria andApplyNumLessThanOrEqualTo(Integer value) {
            addCriterion("apply_num <=", value, "applyNum");
            return (Criteria) this;
        }

        public Criteria andApplyNumIn(List<Integer> values) {
            addCriterion("apply_num in", values, "applyNum");
            return (Criteria) this;
        }

        public Criteria andApplyNumNotIn(List<Integer> values) {
            addCriterion("apply_num not in", values, "applyNum");
            return (Criteria) this;
        }

        public Criteria andApplyNumBetween(Integer value1, Integer value2) {
            addCriterion("apply_num between", value1, value2, "applyNum");
            return (Criteria) this;
        }

        public Criteria andApplyNumNotBetween(Integer value1, Integer value2) {
            addCriterion("apply_num not between", value1, value2, "applyNum");
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