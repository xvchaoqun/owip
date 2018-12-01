package domain.unit;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class UnitTeamPlanExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public UnitTeamPlanExample() {
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

        public Criteria andUnitTeamIdIsNull() {
            addCriterion("unit_team_id is null");
            return (Criteria) this;
        }

        public Criteria andUnitTeamIdIsNotNull() {
            addCriterion("unit_team_id is not null");
            return (Criteria) this;
        }

        public Criteria andUnitTeamIdEqualTo(Integer value) {
            addCriterion("unit_team_id =", value, "unitTeamId");
            return (Criteria) this;
        }

        public Criteria andUnitTeamIdNotEqualTo(Integer value) {
            addCriterion("unit_team_id <>", value, "unitTeamId");
            return (Criteria) this;
        }

        public Criteria andUnitTeamIdGreaterThan(Integer value) {
            addCriterion("unit_team_id >", value, "unitTeamId");
            return (Criteria) this;
        }

        public Criteria andUnitTeamIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("unit_team_id >=", value, "unitTeamId");
            return (Criteria) this;
        }

        public Criteria andUnitTeamIdLessThan(Integer value) {
            addCriterion("unit_team_id <", value, "unitTeamId");
            return (Criteria) this;
        }

        public Criteria andUnitTeamIdLessThanOrEqualTo(Integer value) {
            addCriterion("unit_team_id <=", value, "unitTeamId");
            return (Criteria) this;
        }

        public Criteria andUnitTeamIdIn(List<Integer> values) {
            addCriterion("unit_team_id in", values, "unitTeamId");
            return (Criteria) this;
        }

        public Criteria andUnitTeamIdNotIn(List<Integer> values) {
            addCriterion("unit_team_id not in", values, "unitTeamId");
            return (Criteria) this;
        }

        public Criteria andUnitTeamIdBetween(Integer value1, Integer value2) {
            addCriterion("unit_team_id between", value1, value2, "unitTeamId");
            return (Criteria) this;
        }

        public Criteria andUnitTeamIdNotBetween(Integer value1, Integer value2) {
            addCriterion("unit_team_id not between", value1, value2, "unitTeamId");
            return (Criteria) this;
        }

        public Criteria andMainPostsIsNull() {
            addCriterion("main_posts is null");
            return (Criteria) this;
        }

        public Criteria andMainPostsIsNotNull() {
            addCriterion("main_posts is not null");
            return (Criteria) this;
        }

        public Criteria andMainPostsEqualTo(String value) {
            addCriterion("main_posts =", value, "mainPosts");
            return (Criteria) this;
        }

        public Criteria andMainPostsNotEqualTo(String value) {
            addCriterion("main_posts <>", value, "mainPosts");
            return (Criteria) this;
        }

        public Criteria andMainPostsGreaterThan(String value) {
            addCriterion("main_posts >", value, "mainPosts");
            return (Criteria) this;
        }

        public Criteria andMainPostsGreaterThanOrEqualTo(String value) {
            addCriterion("main_posts >=", value, "mainPosts");
            return (Criteria) this;
        }

        public Criteria andMainPostsLessThan(String value) {
            addCriterion("main_posts <", value, "mainPosts");
            return (Criteria) this;
        }

        public Criteria andMainPostsLessThanOrEqualTo(String value) {
            addCriterion("main_posts <=", value, "mainPosts");
            return (Criteria) this;
        }

        public Criteria andMainPostsLike(String value) {
            addCriterion("main_posts like", value, "mainPosts");
            return (Criteria) this;
        }

        public Criteria andMainPostsNotLike(String value) {
            addCriterion("main_posts not like", value, "mainPosts");
            return (Criteria) this;
        }

        public Criteria andMainPostsIn(List<String> values) {
            addCriterion("main_posts in", values, "mainPosts");
            return (Criteria) this;
        }

        public Criteria andMainPostsNotIn(List<String> values) {
            addCriterion("main_posts not in", values, "mainPosts");
            return (Criteria) this;
        }

        public Criteria andMainPostsBetween(String value1, String value2) {
            addCriterion("main_posts between", value1, value2, "mainPosts");
            return (Criteria) this;
        }

        public Criteria andMainPostsNotBetween(String value1, String value2) {
            addCriterion("main_posts not between", value1, value2, "mainPosts");
            return (Criteria) this;
        }

        public Criteria andVicePostsIsNull() {
            addCriterion("vice_posts is null");
            return (Criteria) this;
        }

        public Criteria andVicePostsIsNotNull() {
            addCriterion("vice_posts is not null");
            return (Criteria) this;
        }

        public Criteria andVicePostsEqualTo(String value) {
            addCriterion("vice_posts =", value, "vicePosts");
            return (Criteria) this;
        }

        public Criteria andVicePostsNotEqualTo(String value) {
            addCriterion("vice_posts <>", value, "vicePosts");
            return (Criteria) this;
        }

        public Criteria andVicePostsGreaterThan(String value) {
            addCriterion("vice_posts >", value, "vicePosts");
            return (Criteria) this;
        }

        public Criteria andVicePostsGreaterThanOrEqualTo(String value) {
            addCriterion("vice_posts >=", value, "vicePosts");
            return (Criteria) this;
        }

        public Criteria andVicePostsLessThan(String value) {
            addCriterion("vice_posts <", value, "vicePosts");
            return (Criteria) this;
        }

        public Criteria andVicePostsLessThanOrEqualTo(String value) {
            addCriterion("vice_posts <=", value, "vicePosts");
            return (Criteria) this;
        }

        public Criteria andVicePostsLike(String value) {
            addCriterion("vice_posts like", value, "vicePosts");
            return (Criteria) this;
        }

        public Criteria andVicePostsNotLike(String value) {
            addCriterion("vice_posts not like", value, "vicePosts");
            return (Criteria) this;
        }

        public Criteria andVicePostsIn(List<String> values) {
            addCriterion("vice_posts in", values, "vicePosts");
            return (Criteria) this;
        }

        public Criteria andVicePostsNotIn(List<String> values) {
            addCriterion("vice_posts not in", values, "vicePosts");
            return (Criteria) this;
        }

        public Criteria andVicePostsBetween(String value1, String value2) {
            addCriterion("vice_posts between", value1, value2, "vicePosts");
            return (Criteria) this;
        }

        public Criteria andVicePostsNotBetween(String value1, String value2) {
            addCriterion("vice_posts not between", value1, value2, "vicePosts");
            return (Criteria) this;
        }

        public Criteria andStartDateIsNull() {
            addCriterion("start_date is null");
            return (Criteria) this;
        }

        public Criteria andStartDateIsNotNull() {
            addCriterion("start_date is not null");
            return (Criteria) this;
        }

        public Criteria andStartDateEqualTo(Date value) {
            addCriterionForJDBCDate("start_date =", value, "startDate");
            return (Criteria) this;
        }

        public Criteria andStartDateNotEqualTo(Date value) {
            addCriterionForJDBCDate("start_date <>", value, "startDate");
            return (Criteria) this;
        }

        public Criteria andStartDateGreaterThan(Date value) {
            addCriterionForJDBCDate("start_date >", value, "startDate");
            return (Criteria) this;
        }

        public Criteria andStartDateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("start_date >=", value, "startDate");
            return (Criteria) this;
        }

        public Criteria andStartDateLessThan(Date value) {
            addCriterionForJDBCDate("start_date <", value, "startDate");
            return (Criteria) this;
        }

        public Criteria andStartDateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("start_date <=", value, "startDate");
            return (Criteria) this;
        }

        public Criteria andStartDateIn(List<Date> values) {
            addCriterionForJDBCDate("start_date in", values, "startDate");
            return (Criteria) this;
        }

        public Criteria andStartDateNotIn(List<Date> values) {
            addCriterionForJDBCDate("start_date not in", values, "startDate");
            return (Criteria) this;
        }

        public Criteria andStartDateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("start_date between", value1, value2, "startDate");
            return (Criteria) this;
        }

        public Criteria andStartDateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("start_date not between", value1, value2, "startDate");
            return (Criteria) this;
        }

        public Criteria andEndDateIsNull() {
            addCriterion("end_date is null");
            return (Criteria) this;
        }

        public Criteria andEndDateIsNotNull() {
            addCriterion("end_date is not null");
            return (Criteria) this;
        }

        public Criteria andEndDateEqualTo(Date value) {
            addCriterionForJDBCDate("end_date =", value, "endDate");
            return (Criteria) this;
        }

        public Criteria andEndDateNotEqualTo(Date value) {
            addCriterionForJDBCDate("end_date <>", value, "endDate");
            return (Criteria) this;
        }

        public Criteria andEndDateGreaterThan(Date value) {
            addCriterionForJDBCDate("end_date >", value, "endDate");
            return (Criteria) this;
        }

        public Criteria andEndDateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("end_date >=", value, "endDate");
            return (Criteria) this;
        }

        public Criteria andEndDateLessThan(Date value) {
            addCriterionForJDBCDate("end_date <", value, "endDate");
            return (Criteria) this;
        }

        public Criteria andEndDateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("end_date <=", value, "endDate");
            return (Criteria) this;
        }

        public Criteria andEndDateIn(List<Date> values) {
            addCriterionForJDBCDate("end_date in", values, "endDate");
            return (Criteria) this;
        }

        public Criteria andEndDateNotIn(List<Date> values) {
            addCriterionForJDBCDate("end_date not in", values, "endDate");
            return (Criteria) this;
        }

        public Criteria andEndDateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("end_date between", value1, value2, "endDate");
            return (Criteria) this;
        }

        public Criteria andEndDateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("end_date not between", value1, value2, "endDate");
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