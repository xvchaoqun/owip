package domain.sc.scMotion;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class ScMotionExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public ScMotionExample() {
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

        public Criteria andYearIsNull() {
            addCriterion("year is null");
            return (Criteria) this;
        }

        public Criteria andYearIsNotNull() {
            addCriterion("year is not null");
            return (Criteria) this;
        }

        public Criteria andYearEqualTo(Short value) {
            addCriterion("year =", value, "year");
            return (Criteria) this;
        }

        public Criteria andYearNotEqualTo(Short value) {
            addCriterion("year <>", value, "year");
            return (Criteria) this;
        }

        public Criteria andYearGreaterThan(Short value) {
            addCriterion("year >", value, "year");
            return (Criteria) this;
        }

        public Criteria andYearGreaterThanOrEqualTo(Short value) {
            addCriterion("year >=", value, "year");
            return (Criteria) this;
        }

        public Criteria andYearLessThan(Short value) {
            addCriterion("year <", value, "year");
            return (Criteria) this;
        }

        public Criteria andYearLessThanOrEqualTo(Short value) {
            addCriterion("year <=", value, "year");
            return (Criteria) this;
        }

        public Criteria andYearIn(List<Short> values) {
            addCriterion("year in", values, "year");
            return (Criteria) this;
        }

        public Criteria andYearNotIn(List<Short> values) {
            addCriterion("year not in", values, "year");
            return (Criteria) this;
        }

        public Criteria andYearBetween(Short value1, Short value2) {
            addCriterion("year between", value1, value2, "year");
            return (Criteria) this;
        }

        public Criteria andYearNotBetween(Short value1, Short value2) {
            addCriterion("year not between", value1, value2, "year");
            return (Criteria) this;
        }

        public Criteria andHoldDateIsNull() {
            addCriterion("hold_date is null");
            return (Criteria) this;
        }

        public Criteria andHoldDateIsNotNull() {
            addCriterion("hold_date is not null");
            return (Criteria) this;
        }

        public Criteria andHoldDateEqualTo(Date value) {
            addCriterionForJDBCDate("hold_date =", value, "holdDate");
            return (Criteria) this;
        }

        public Criteria andHoldDateNotEqualTo(Date value) {
            addCriterionForJDBCDate("hold_date <>", value, "holdDate");
            return (Criteria) this;
        }

        public Criteria andHoldDateGreaterThan(Date value) {
            addCriterionForJDBCDate("hold_date >", value, "holdDate");
            return (Criteria) this;
        }

        public Criteria andHoldDateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("hold_date >=", value, "holdDate");
            return (Criteria) this;
        }

        public Criteria andHoldDateLessThan(Date value) {
            addCriterionForJDBCDate("hold_date <", value, "holdDate");
            return (Criteria) this;
        }

        public Criteria andHoldDateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("hold_date <=", value, "holdDate");
            return (Criteria) this;
        }

        public Criteria andHoldDateIn(List<Date> values) {
            addCriterionForJDBCDate("hold_date in", values, "holdDate");
            return (Criteria) this;
        }

        public Criteria andHoldDateNotIn(List<Date> values) {
            addCriterionForJDBCDate("hold_date not in", values, "holdDate");
            return (Criteria) this;
        }

        public Criteria andHoldDateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("hold_date between", value1, value2, "holdDate");
            return (Criteria) this;
        }

        public Criteria andHoldDateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("hold_date not between", value1, value2, "holdDate");
            return (Criteria) this;
        }

        public Criteria andSeqIsNull() {
            addCriterion("seq is null");
            return (Criteria) this;
        }

        public Criteria andSeqIsNotNull() {
            addCriterion("seq is not null");
            return (Criteria) this;
        }

        public Criteria andSeqEqualTo(String value) {
            addCriterion("seq =", value, "seq");
            return (Criteria) this;
        }

        public Criteria andSeqNotEqualTo(String value) {
            addCriterion("seq <>", value, "seq");
            return (Criteria) this;
        }

        public Criteria andSeqGreaterThan(String value) {
            addCriterion("seq >", value, "seq");
            return (Criteria) this;
        }

        public Criteria andSeqGreaterThanOrEqualTo(String value) {
            addCriterion("seq >=", value, "seq");
            return (Criteria) this;
        }

        public Criteria andSeqLessThan(String value) {
            addCriterion("seq <", value, "seq");
            return (Criteria) this;
        }

        public Criteria andSeqLessThanOrEqualTo(String value) {
            addCriterion("seq <=", value, "seq");
            return (Criteria) this;
        }

        public Criteria andSeqLike(String value) {
            addCriterion("seq like", value, "seq");
            return (Criteria) this;
        }

        public Criteria andSeqNotLike(String value) {
            addCriterion("seq not like", value, "seq");
            return (Criteria) this;
        }

        public Criteria andSeqIn(List<String> values) {
            addCriterion("seq in", values, "seq");
            return (Criteria) this;
        }

        public Criteria andSeqNotIn(List<String> values) {
            addCriterion("seq not in", values, "seq");
            return (Criteria) this;
        }

        public Criteria andSeqBetween(String value1, String value2) {
            addCriterion("seq between", value1, value2, "seq");
            return (Criteria) this;
        }

        public Criteria andSeqNotBetween(String value1, String value2) {
            addCriterion("seq not between", value1, value2, "seq");
            return (Criteria) this;
        }

        public Criteria andUnitPostIdIsNull() {
            addCriterion("unit_post_id is null");
            return (Criteria) this;
        }

        public Criteria andUnitPostIdIsNotNull() {
            addCriterion("unit_post_id is not null");
            return (Criteria) this;
        }

        public Criteria andUnitPostIdEqualTo(Integer value) {
            addCriterion("unit_post_id =", value, "unitPostId");
            return (Criteria) this;
        }

        public Criteria andUnitPostIdNotEqualTo(Integer value) {
            addCriterion("unit_post_id <>", value, "unitPostId");
            return (Criteria) this;
        }

        public Criteria andUnitPostIdGreaterThan(Integer value) {
            addCriterion("unit_post_id >", value, "unitPostId");
            return (Criteria) this;
        }

        public Criteria andUnitPostIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("unit_post_id >=", value, "unitPostId");
            return (Criteria) this;
        }

        public Criteria andUnitPostIdLessThan(Integer value) {
            addCriterion("unit_post_id <", value, "unitPostId");
            return (Criteria) this;
        }

        public Criteria andUnitPostIdLessThanOrEqualTo(Integer value) {
            addCriterion("unit_post_id <=", value, "unitPostId");
            return (Criteria) this;
        }

        public Criteria andUnitPostIdIn(List<Integer> values) {
            addCriterion("unit_post_id in", values, "unitPostId");
            return (Criteria) this;
        }

        public Criteria andUnitPostIdNotIn(List<Integer> values) {
            addCriterion("unit_post_id not in", values, "unitPostId");
            return (Criteria) this;
        }

        public Criteria andUnitPostIdBetween(Integer value1, Integer value2) {
            addCriterion("unit_post_id between", value1, value2, "unitPostId");
            return (Criteria) this;
        }

        public Criteria andUnitPostIdNotBetween(Integer value1, Integer value2) {
            addCriterion("unit_post_id not between", value1, value2, "unitPostId");
            return (Criteria) this;
        }

        public Criteria andScTypeIsNull() {
            addCriterion("sc_type is null");
            return (Criteria) this;
        }

        public Criteria andScTypeIsNotNull() {
            addCriterion("sc_type is not null");
            return (Criteria) this;
        }

        public Criteria andScTypeEqualTo(Integer value) {
            addCriterion("sc_type =", value, "scType");
            return (Criteria) this;
        }

        public Criteria andScTypeNotEqualTo(Integer value) {
            addCriterion("sc_type <>", value, "scType");
            return (Criteria) this;
        }

        public Criteria andScTypeGreaterThan(Integer value) {
            addCriterion("sc_type >", value, "scType");
            return (Criteria) this;
        }

        public Criteria andScTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("sc_type >=", value, "scType");
            return (Criteria) this;
        }

        public Criteria andScTypeLessThan(Integer value) {
            addCriterion("sc_type <", value, "scType");
            return (Criteria) this;
        }

        public Criteria andScTypeLessThanOrEqualTo(Integer value) {
            addCriterion("sc_type <=", value, "scType");
            return (Criteria) this;
        }

        public Criteria andScTypeIn(List<Integer> values) {
            addCriterion("sc_type in", values, "scType");
            return (Criteria) this;
        }

        public Criteria andScTypeNotIn(List<Integer> values) {
            addCriterion("sc_type not in", values, "scType");
            return (Criteria) this;
        }

        public Criteria andScTypeBetween(Integer value1, Integer value2) {
            addCriterion("sc_type between", value1, value2, "scType");
            return (Criteria) this;
        }

        public Criteria andScTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("sc_type not between", value1, value2, "scType");
            return (Criteria) this;
        }

        public Criteria andWayIsNull() {
            addCriterion("way is null");
            return (Criteria) this;
        }

        public Criteria andWayIsNotNull() {
            addCriterion("way is not null");
            return (Criteria) this;
        }

        public Criteria andWayEqualTo(Byte value) {
            addCriterion("way =", value, "way");
            return (Criteria) this;
        }

        public Criteria andWayNotEqualTo(Byte value) {
            addCriterion("way <>", value, "way");
            return (Criteria) this;
        }

        public Criteria andWayGreaterThan(Byte value) {
            addCriterion("way >", value, "way");
            return (Criteria) this;
        }

        public Criteria andWayGreaterThanOrEqualTo(Byte value) {
            addCriterion("way >=", value, "way");
            return (Criteria) this;
        }

        public Criteria andWayLessThan(Byte value) {
            addCriterion("way <", value, "way");
            return (Criteria) this;
        }

        public Criteria andWayLessThanOrEqualTo(Byte value) {
            addCriterion("way <=", value, "way");
            return (Criteria) this;
        }

        public Criteria andWayIn(List<Byte> values) {
            addCriterion("way in", values, "way");
            return (Criteria) this;
        }

        public Criteria andWayNotIn(List<Byte> values) {
            addCriterion("way not in", values, "way");
            return (Criteria) this;
        }

        public Criteria andWayBetween(Byte value1, Byte value2) {
            addCriterion("way between", value1, value2, "way");
            return (Criteria) this;
        }

        public Criteria andWayNotBetween(Byte value1, Byte value2) {
            addCriterion("way not between", value1, value2, "way");
            return (Criteria) this;
        }

        public Criteria andWayOtherIsNull() {
            addCriterion("way_other is null");
            return (Criteria) this;
        }

        public Criteria andWayOtherIsNotNull() {
            addCriterion("way_other is not null");
            return (Criteria) this;
        }

        public Criteria andWayOtherEqualTo(String value) {
            addCriterion("way_other =", value, "wayOther");
            return (Criteria) this;
        }

        public Criteria andWayOtherNotEqualTo(String value) {
            addCriterion("way_other <>", value, "wayOther");
            return (Criteria) this;
        }

        public Criteria andWayOtherGreaterThan(String value) {
            addCriterion("way_other >", value, "wayOther");
            return (Criteria) this;
        }

        public Criteria andWayOtherGreaterThanOrEqualTo(String value) {
            addCriterion("way_other >=", value, "wayOther");
            return (Criteria) this;
        }

        public Criteria andWayOtherLessThan(String value) {
            addCriterion("way_other <", value, "wayOther");
            return (Criteria) this;
        }

        public Criteria andWayOtherLessThanOrEqualTo(String value) {
            addCriterion("way_other <=", value, "wayOther");
            return (Criteria) this;
        }

        public Criteria andWayOtherLike(String value) {
            addCriterion("way_other like", value, "wayOther");
            return (Criteria) this;
        }

        public Criteria andWayOtherNotLike(String value) {
            addCriterion("way_other not like", value, "wayOther");
            return (Criteria) this;
        }

        public Criteria andWayOtherIn(List<String> values) {
            addCriterion("way_other in", values, "wayOther");
            return (Criteria) this;
        }

        public Criteria andWayOtherNotIn(List<String> values) {
            addCriterion("way_other not in", values, "wayOther");
            return (Criteria) this;
        }

        public Criteria andWayOtherBetween(String value1, String value2) {
            addCriterion("way_other between", value1, value2, "wayOther");
            return (Criteria) this;
        }

        public Criteria andWayOtherNotBetween(String value1, String value2) {
            addCriterion("way_other not between", value1, value2, "wayOther");
            return (Criteria) this;
        }

        public Criteria andTopicsIsNull() {
            addCriterion("topics is null");
            return (Criteria) this;
        }

        public Criteria andTopicsIsNotNull() {
            addCriterion("topics is not null");
            return (Criteria) this;
        }

        public Criteria andTopicsEqualTo(String value) {
            addCriterion("topics =", value, "topics");
            return (Criteria) this;
        }

        public Criteria andTopicsNotEqualTo(String value) {
            addCriterion("topics <>", value, "topics");
            return (Criteria) this;
        }

        public Criteria andTopicsGreaterThan(String value) {
            addCriterion("topics >", value, "topics");
            return (Criteria) this;
        }

        public Criteria andTopicsGreaterThanOrEqualTo(String value) {
            addCriterion("topics >=", value, "topics");
            return (Criteria) this;
        }

        public Criteria andTopicsLessThan(String value) {
            addCriterion("topics <", value, "topics");
            return (Criteria) this;
        }

        public Criteria andTopicsLessThanOrEqualTo(String value) {
            addCriterion("topics <=", value, "topics");
            return (Criteria) this;
        }

        public Criteria andTopicsLike(String value) {
            addCriterion("topics like", value, "topics");
            return (Criteria) this;
        }

        public Criteria andTopicsNotLike(String value) {
            addCriterion("topics not like", value, "topics");
            return (Criteria) this;
        }

        public Criteria andTopicsIn(List<String> values) {
            addCriterion("topics in", values, "topics");
            return (Criteria) this;
        }

        public Criteria andTopicsNotIn(List<String> values) {
            addCriterion("topics not in", values, "topics");
            return (Criteria) this;
        }

        public Criteria andTopicsBetween(String value1, String value2) {
            addCriterion("topics between", value1, value2, "topics");
            return (Criteria) this;
        }

        public Criteria andTopicsNotBetween(String value1, String value2) {
            addCriterion("topics not between", value1, value2, "topics");
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