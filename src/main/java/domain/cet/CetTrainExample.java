package domain.cet;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class CetTrainExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public CetTrainExample() {
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

        public Criteria andTypeIsNull() {
            addCriterion("type is null");
            return (Criteria) this;
        }

        public Criteria andTypeIsNotNull() {
            addCriterion("type is not null");
            return (Criteria) this;
        }

        public Criteria andTypeEqualTo(Integer value) {
            addCriterion("type =", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotEqualTo(Integer value) {
            addCriterion("type <>", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThan(Integer value) {
            addCriterion("type >", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("type >=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThan(Integer value) {
            addCriterion("type <", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThanOrEqualTo(Integer value) {
            addCriterion("type <=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeIn(List<Integer> values) {
            addCriterion("type in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotIn(List<Integer> values) {
            addCriterion("type not in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeBetween(Integer value1, Integer value2) {
            addCriterion("type between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("type not between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andNumIsNull() {
            addCriterion("num is null");
            return (Criteria) this;
        }

        public Criteria andNumIsNotNull() {
            addCriterion("num is not null");
            return (Criteria) this;
        }

        public Criteria andNumEqualTo(Integer value) {
            addCriterion("num =", value, "num");
            return (Criteria) this;
        }

        public Criteria andNumNotEqualTo(Integer value) {
            addCriterion("num <>", value, "num");
            return (Criteria) this;
        }

        public Criteria andNumGreaterThan(Integer value) {
            addCriterion("num >", value, "num");
            return (Criteria) this;
        }

        public Criteria andNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("num >=", value, "num");
            return (Criteria) this;
        }

        public Criteria andNumLessThan(Integer value) {
            addCriterion("num <", value, "num");
            return (Criteria) this;
        }

        public Criteria andNumLessThanOrEqualTo(Integer value) {
            addCriterion("num <=", value, "num");
            return (Criteria) this;
        }

        public Criteria andNumIn(List<Integer> values) {
            addCriterion("num in", values, "num");
            return (Criteria) this;
        }

        public Criteria andNumNotIn(List<Integer> values) {
            addCriterion("num not in", values, "num");
            return (Criteria) this;
        }

        public Criteria andNumBetween(Integer value1, Integer value2) {
            addCriterion("num between", value1, value2, "num");
            return (Criteria) this;
        }

        public Criteria andNumNotBetween(Integer value1, Integer value2) {
            addCriterion("num not between", value1, value2, "num");
            return (Criteria) this;
        }

        public Criteria andIsOnCampusIsNull() {
            addCriterion("is_on_campus is null");
            return (Criteria) this;
        }

        public Criteria andIsOnCampusIsNotNull() {
            addCriterion("is_on_campus is not null");
            return (Criteria) this;
        }

        public Criteria andIsOnCampusEqualTo(Boolean value) {
            addCriterion("is_on_campus =", value, "isOnCampus");
            return (Criteria) this;
        }

        public Criteria andIsOnCampusNotEqualTo(Boolean value) {
            addCriterion("is_on_campus <>", value, "isOnCampus");
            return (Criteria) this;
        }

        public Criteria andIsOnCampusGreaterThan(Boolean value) {
            addCriterion("is_on_campus >", value, "isOnCampus");
            return (Criteria) this;
        }

        public Criteria andIsOnCampusGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_on_campus >=", value, "isOnCampus");
            return (Criteria) this;
        }

        public Criteria andIsOnCampusLessThan(Boolean value) {
            addCriterion("is_on_campus <", value, "isOnCampus");
            return (Criteria) this;
        }

        public Criteria andIsOnCampusLessThanOrEqualTo(Boolean value) {
            addCriterion("is_on_campus <=", value, "isOnCampus");
            return (Criteria) this;
        }

        public Criteria andIsOnCampusIn(List<Boolean> values) {
            addCriterion("is_on_campus in", values, "isOnCampus");
            return (Criteria) this;
        }

        public Criteria andIsOnCampusNotIn(List<Boolean> values) {
            addCriterion("is_on_campus not in", values, "isOnCampus");
            return (Criteria) this;
        }

        public Criteria andIsOnCampusBetween(Boolean value1, Boolean value2) {
            addCriterion("is_on_campus between", value1, value2, "isOnCampus");
            return (Criteria) this;
        }

        public Criteria andIsOnCampusNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_on_campus not between", value1, value2, "isOnCampus");
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

        public Criteria andHasSummaryIsNull() {
            addCriterion("has_summary is null");
            return (Criteria) this;
        }

        public Criteria andHasSummaryIsNotNull() {
            addCriterion("has_summary is not null");
            return (Criteria) this;
        }

        public Criteria andHasSummaryEqualTo(Boolean value) {
            addCriterion("has_summary =", value, "hasSummary");
            return (Criteria) this;
        }

        public Criteria andHasSummaryNotEqualTo(Boolean value) {
            addCriterion("has_summary <>", value, "hasSummary");
            return (Criteria) this;
        }

        public Criteria andHasSummaryGreaterThan(Boolean value) {
            addCriterion("has_summary >", value, "hasSummary");
            return (Criteria) this;
        }

        public Criteria andHasSummaryGreaterThanOrEqualTo(Boolean value) {
            addCriterion("has_summary >=", value, "hasSummary");
            return (Criteria) this;
        }

        public Criteria andHasSummaryLessThan(Boolean value) {
            addCriterion("has_summary <", value, "hasSummary");
            return (Criteria) this;
        }

        public Criteria andHasSummaryLessThanOrEqualTo(Boolean value) {
            addCriterion("has_summary <=", value, "hasSummary");
            return (Criteria) this;
        }

        public Criteria andHasSummaryIn(List<Boolean> values) {
            addCriterion("has_summary in", values, "hasSummary");
            return (Criteria) this;
        }

        public Criteria andHasSummaryNotIn(List<Boolean> values) {
            addCriterion("has_summary not in", values, "hasSummary");
            return (Criteria) this;
        }

        public Criteria andHasSummaryBetween(Boolean value1, Boolean value2) {
            addCriterion("has_summary between", value1, value2, "hasSummary");
            return (Criteria) this;
        }

        public Criteria andHasSummaryNotBetween(Boolean value1, Boolean value2) {
            addCriterion("has_summary not between", value1, value2, "hasSummary");
            return (Criteria) this;
        }

        public Criteria andSummaryIsNull() {
            addCriterion("summary is null");
            return (Criteria) this;
        }

        public Criteria andSummaryIsNotNull() {
            addCriterion("summary is not null");
            return (Criteria) this;
        }

        public Criteria andSummaryEqualTo(String value) {
            addCriterion("summary =", value, "summary");
            return (Criteria) this;
        }

        public Criteria andSummaryNotEqualTo(String value) {
            addCriterion("summary <>", value, "summary");
            return (Criteria) this;
        }

        public Criteria andSummaryGreaterThan(String value) {
            addCriterion("summary >", value, "summary");
            return (Criteria) this;
        }

        public Criteria andSummaryGreaterThanOrEqualTo(String value) {
            addCriterion("summary >=", value, "summary");
            return (Criteria) this;
        }

        public Criteria andSummaryLessThan(String value) {
            addCriterion("summary <", value, "summary");
            return (Criteria) this;
        }

        public Criteria andSummaryLessThanOrEqualTo(String value) {
            addCriterion("summary <=", value, "summary");
            return (Criteria) this;
        }

        public Criteria andSummaryLike(String value) {
            addCriterion("summary like", value, "summary");
            return (Criteria) this;
        }

        public Criteria andSummaryNotLike(String value) {
            addCriterion("summary not like", value, "summary");
            return (Criteria) this;
        }

        public Criteria andSummaryIn(List<String> values) {
            addCriterion("summary in", values, "summary");
            return (Criteria) this;
        }

        public Criteria andSummaryNotIn(List<String> values) {
            addCriterion("summary not in", values, "summary");
            return (Criteria) this;
        }

        public Criteria andSummaryBetween(String value1, String value2) {
            addCriterion("summary between", value1, value2, "summary");
            return (Criteria) this;
        }

        public Criteria andSummaryNotBetween(String value1, String value2) {
            addCriterion("summary not between", value1, value2, "summary");
            return (Criteria) this;
        }

        public Criteria andTemplateIdIsNull() {
            addCriterion("template_id is null");
            return (Criteria) this;
        }

        public Criteria andTemplateIdIsNotNull() {
            addCriterion("template_id is not null");
            return (Criteria) this;
        }

        public Criteria andTemplateIdEqualTo(Integer value) {
            addCriterion("template_id =", value, "templateId");
            return (Criteria) this;
        }

        public Criteria andTemplateIdNotEqualTo(Integer value) {
            addCriterion("template_id <>", value, "templateId");
            return (Criteria) this;
        }

        public Criteria andTemplateIdGreaterThan(Integer value) {
            addCriterion("template_id >", value, "templateId");
            return (Criteria) this;
        }

        public Criteria andTemplateIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("template_id >=", value, "templateId");
            return (Criteria) this;
        }

        public Criteria andTemplateIdLessThan(Integer value) {
            addCriterion("template_id <", value, "templateId");
            return (Criteria) this;
        }

        public Criteria andTemplateIdLessThanOrEqualTo(Integer value) {
            addCriterion("template_id <=", value, "templateId");
            return (Criteria) this;
        }

        public Criteria andTemplateIdIn(List<Integer> values) {
            addCriterion("template_id in", values, "templateId");
            return (Criteria) this;
        }

        public Criteria andTemplateIdNotIn(List<Integer> values) {
            addCriterion("template_id not in", values, "templateId");
            return (Criteria) this;
        }

        public Criteria andTemplateIdBetween(Integer value1, Integer value2) {
            addCriterion("template_id between", value1, value2, "templateId");
            return (Criteria) this;
        }

        public Criteria andTemplateIdNotBetween(Integer value1, Integer value2) {
            addCriterion("template_id not between", value1, value2, "templateId");
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

        public Criteria andEnrollStatusIsNull() {
            addCriterion("enroll_status is null");
            return (Criteria) this;
        }

        public Criteria andEnrollStatusIsNotNull() {
            addCriterion("enroll_status is not null");
            return (Criteria) this;
        }

        public Criteria andEnrollStatusEqualTo(Byte value) {
            addCriterion("enroll_status =", value, "enrollStatus");
            return (Criteria) this;
        }

        public Criteria andEnrollStatusNotEqualTo(Byte value) {
            addCriterion("enroll_status <>", value, "enrollStatus");
            return (Criteria) this;
        }

        public Criteria andEnrollStatusGreaterThan(Byte value) {
            addCriterion("enroll_status >", value, "enrollStatus");
            return (Criteria) this;
        }

        public Criteria andEnrollStatusGreaterThanOrEqualTo(Byte value) {
            addCriterion("enroll_status >=", value, "enrollStatus");
            return (Criteria) this;
        }

        public Criteria andEnrollStatusLessThan(Byte value) {
            addCriterion("enroll_status <", value, "enrollStatus");
            return (Criteria) this;
        }

        public Criteria andEnrollStatusLessThanOrEqualTo(Byte value) {
            addCriterion("enroll_status <=", value, "enrollStatus");
            return (Criteria) this;
        }

        public Criteria andEnrollStatusIn(List<Byte> values) {
            addCriterion("enroll_status in", values, "enrollStatus");
            return (Criteria) this;
        }

        public Criteria andEnrollStatusNotIn(List<Byte> values) {
            addCriterion("enroll_status not in", values, "enrollStatus");
            return (Criteria) this;
        }

        public Criteria andEnrollStatusBetween(Byte value1, Byte value2) {
            addCriterion("enroll_status between", value1, value2, "enrollStatus");
            return (Criteria) this;
        }

        public Criteria andEnrollStatusNotBetween(Byte value1, Byte value2) {
            addCriterion("enroll_status not between", value1, value2, "enrollStatus");
            return (Criteria) this;
        }

        public Criteria andPubStatusIsNull() {
            addCriterion("pub_status is null");
            return (Criteria) this;
        }

        public Criteria andPubStatusIsNotNull() {
            addCriterion("pub_status is not null");
            return (Criteria) this;
        }

        public Criteria andPubStatusEqualTo(Byte value) {
            addCriterion("pub_status =", value, "pubStatus");
            return (Criteria) this;
        }

        public Criteria andPubStatusNotEqualTo(Byte value) {
            addCriterion("pub_status <>", value, "pubStatus");
            return (Criteria) this;
        }

        public Criteria andPubStatusGreaterThan(Byte value) {
            addCriterion("pub_status >", value, "pubStatus");
            return (Criteria) this;
        }

        public Criteria andPubStatusGreaterThanOrEqualTo(Byte value) {
            addCriterion("pub_status >=", value, "pubStatus");
            return (Criteria) this;
        }

        public Criteria andPubStatusLessThan(Byte value) {
            addCriterion("pub_status <", value, "pubStatus");
            return (Criteria) this;
        }

        public Criteria andPubStatusLessThanOrEqualTo(Byte value) {
            addCriterion("pub_status <=", value, "pubStatus");
            return (Criteria) this;
        }

        public Criteria andPubStatusIn(List<Byte> values) {
            addCriterion("pub_status in", values, "pubStatus");
            return (Criteria) this;
        }

        public Criteria andPubStatusNotIn(List<Byte> values) {
            addCriterion("pub_status not in", values, "pubStatus");
            return (Criteria) this;
        }

        public Criteria andPubStatusBetween(Byte value1, Byte value2) {
            addCriterion("pub_status between", value1, value2, "pubStatus");
            return (Criteria) this;
        }

        public Criteria andPubStatusNotBetween(Byte value1, Byte value2) {
            addCriterion("pub_status not between", value1, value2, "pubStatus");
            return (Criteria) this;
        }

        public Criteria andEvaCountIsNull() {
            addCriterion("eva_count is null");
            return (Criteria) this;
        }

        public Criteria andEvaCountIsNotNull() {
            addCriterion("eva_count is not null");
            return (Criteria) this;
        }

        public Criteria andEvaCountEqualTo(Integer value) {
            addCriterion("eva_count =", value, "evaCount");
            return (Criteria) this;
        }

        public Criteria andEvaCountNotEqualTo(Integer value) {
            addCriterion("eva_count <>", value, "evaCount");
            return (Criteria) this;
        }

        public Criteria andEvaCountGreaterThan(Integer value) {
            addCriterion("eva_count >", value, "evaCount");
            return (Criteria) this;
        }

        public Criteria andEvaCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("eva_count >=", value, "evaCount");
            return (Criteria) this;
        }

        public Criteria andEvaCountLessThan(Integer value) {
            addCriterion("eva_count <", value, "evaCount");
            return (Criteria) this;
        }

        public Criteria andEvaCountLessThanOrEqualTo(Integer value) {
            addCriterion("eva_count <=", value, "evaCount");
            return (Criteria) this;
        }

        public Criteria andEvaCountIn(List<Integer> values) {
            addCriterion("eva_count in", values, "evaCount");
            return (Criteria) this;
        }

        public Criteria andEvaCountNotIn(List<Integer> values) {
            addCriterion("eva_count not in", values, "evaCount");
            return (Criteria) this;
        }

        public Criteria andEvaCountBetween(Integer value1, Integer value2) {
            addCriterion("eva_count between", value1, value2, "evaCount");
            return (Criteria) this;
        }

        public Criteria andEvaCountNotBetween(Integer value1, Integer value2) {
            addCriterion("eva_count not between", value1, value2, "evaCount");
            return (Criteria) this;
        }

        public Criteria andEvaAnonymousIsNull() {
            addCriterion("eva_anonymous is null");
            return (Criteria) this;
        }

        public Criteria andEvaAnonymousIsNotNull() {
            addCriterion("eva_anonymous is not null");
            return (Criteria) this;
        }

        public Criteria andEvaAnonymousEqualTo(Boolean value) {
            addCriterion("eva_anonymous =", value, "evaAnonymous");
            return (Criteria) this;
        }

        public Criteria andEvaAnonymousNotEqualTo(Boolean value) {
            addCriterion("eva_anonymous <>", value, "evaAnonymous");
            return (Criteria) this;
        }

        public Criteria andEvaAnonymousGreaterThan(Boolean value) {
            addCriterion("eva_anonymous >", value, "evaAnonymous");
            return (Criteria) this;
        }

        public Criteria andEvaAnonymousGreaterThanOrEqualTo(Boolean value) {
            addCriterion("eva_anonymous >=", value, "evaAnonymous");
            return (Criteria) this;
        }

        public Criteria andEvaAnonymousLessThan(Boolean value) {
            addCriterion("eva_anonymous <", value, "evaAnonymous");
            return (Criteria) this;
        }

        public Criteria andEvaAnonymousLessThanOrEqualTo(Boolean value) {
            addCriterion("eva_anonymous <=", value, "evaAnonymous");
            return (Criteria) this;
        }

        public Criteria andEvaAnonymousIn(List<Boolean> values) {
            addCriterion("eva_anonymous in", values, "evaAnonymous");
            return (Criteria) this;
        }

        public Criteria andEvaAnonymousNotIn(List<Boolean> values) {
            addCriterion("eva_anonymous not in", values, "evaAnonymous");
            return (Criteria) this;
        }

        public Criteria andEvaAnonymousBetween(Boolean value1, Boolean value2) {
            addCriterion("eva_anonymous between", value1, value2, "evaAnonymous");
            return (Criteria) this;
        }

        public Criteria andEvaAnonymousNotBetween(Boolean value1, Boolean value2) {
            addCriterion("eva_anonymous not between", value1, value2, "evaAnonymous");
            return (Criteria) this;
        }

        public Criteria andEvaClosedIsNull() {
            addCriterion("eva_closed is null");
            return (Criteria) this;
        }

        public Criteria andEvaClosedIsNotNull() {
            addCriterion("eva_closed is not null");
            return (Criteria) this;
        }

        public Criteria andEvaClosedEqualTo(Boolean value) {
            addCriterion("eva_closed =", value, "evaClosed");
            return (Criteria) this;
        }

        public Criteria andEvaClosedNotEqualTo(Boolean value) {
            addCriterion("eva_closed <>", value, "evaClosed");
            return (Criteria) this;
        }

        public Criteria andEvaClosedGreaterThan(Boolean value) {
            addCriterion("eva_closed >", value, "evaClosed");
            return (Criteria) this;
        }

        public Criteria andEvaClosedGreaterThanOrEqualTo(Boolean value) {
            addCriterion("eva_closed >=", value, "evaClosed");
            return (Criteria) this;
        }

        public Criteria andEvaClosedLessThan(Boolean value) {
            addCriterion("eva_closed <", value, "evaClosed");
            return (Criteria) this;
        }

        public Criteria andEvaClosedLessThanOrEqualTo(Boolean value) {
            addCriterion("eva_closed <=", value, "evaClosed");
            return (Criteria) this;
        }

        public Criteria andEvaClosedIn(List<Boolean> values) {
            addCriterion("eva_closed in", values, "evaClosed");
            return (Criteria) this;
        }

        public Criteria andEvaClosedNotIn(List<Boolean> values) {
            addCriterion("eva_closed not in", values, "evaClosed");
            return (Criteria) this;
        }

        public Criteria andEvaClosedBetween(Boolean value1, Boolean value2) {
            addCriterion("eva_closed between", value1, value2, "evaClosed");
            return (Criteria) this;
        }

        public Criteria andEvaClosedNotBetween(Boolean value1, Boolean value2) {
            addCriterion("eva_closed not between", value1, value2, "evaClosed");
            return (Criteria) this;
        }

        public Criteria andEvaCloseTimeIsNull() {
            addCriterion("eva_close_time is null");
            return (Criteria) this;
        }

        public Criteria andEvaCloseTimeIsNotNull() {
            addCriterion("eva_close_time is not null");
            return (Criteria) this;
        }

        public Criteria andEvaCloseTimeEqualTo(Date value) {
            addCriterion("eva_close_time =", value, "evaCloseTime");
            return (Criteria) this;
        }

        public Criteria andEvaCloseTimeNotEqualTo(Date value) {
            addCriterion("eva_close_time <>", value, "evaCloseTime");
            return (Criteria) this;
        }

        public Criteria andEvaCloseTimeGreaterThan(Date value) {
            addCriterion("eva_close_time >", value, "evaCloseTime");
            return (Criteria) this;
        }

        public Criteria andEvaCloseTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("eva_close_time >=", value, "evaCloseTime");
            return (Criteria) this;
        }

        public Criteria andEvaCloseTimeLessThan(Date value) {
            addCriterion("eva_close_time <", value, "evaCloseTime");
            return (Criteria) this;
        }

        public Criteria andEvaCloseTimeLessThanOrEqualTo(Date value) {
            addCriterion("eva_close_time <=", value, "evaCloseTime");
            return (Criteria) this;
        }

        public Criteria andEvaCloseTimeIn(List<Date> values) {
            addCriterion("eva_close_time in", values, "evaCloseTime");
            return (Criteria) this;
        }

        public Criteria andEvaCloseTimeNotIn(List<Date> values) {
            addCriterion("eva_close_time not in", values, "evaCloseTime");
            return (Criteria) this;
        }

        public Criteria andEvaCloseTimeBetween(Date value1, Date value2) {
            addCriterion("eva_close_time between", value1, value2, "evaCloseTime");
            return (Criteria) this;
        }

        public Criteria andEvaCloseTimeNotBetween(Date value1, Date value2) {
            addCriterion("eva_close_time not between", value1, value2, "evaCloseTime");
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

        public Criteria andIsFinishedIsNull() {
            addCriterion("is_finished is null");
            return (Criteria) this;
        }

        public Criteria andIsFinishedIsNotNull() {
            addCriterion("is_finished is not null");
            return (Criteria) this;
        }

        public Criteria andIsFinishedEqualTo(Boolean value) {
            addCriterion("is_finished =", value, "isFinished");
            return (Criteria) this;
        }

        public Criteria andIsFinishedNotEqualTo(Boolean value) {
            addCriterion("is_finished <>", value, "isFinished");
            return (Criteria) this;
        }

        public Criteria andIsFinishedGreaterThan(Boolean value) {
            addCriterion("is_finished >", value, "isFinished");
            return (Criteria) this;
        }

        public Criteria andIsFinishedGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_finished >=", value, "isFinished");
            return (Criteria) this;
        }

        public Criteria andIsFinishedLessThan(Boolean value) {
            addCriterion("is_finished <", value, "isFinished");
            return (Criteria) this;
        }

        public Criteria andIsFinishedLessThanOrEqualTo(Boolean value) {
            addCriterion("is_finished <=", value, "isFinished");
            return (Criteria) this;
        }

        public Criteria andIsFinishedIn(List<Boolean> values) {
            addCriterion("is_finished in", values, "isFinished");
            return (Criteria) this;
        }

        public Criteria andIsFinishedNotIn(List<Boolean> values) {
            addCriterion("is_finished not in", values, "isFinished");
            return (Criteria) this;
        }

        public Criteria andIsFinishedBetween(Boolean value1, Boolean value2) {
            addCriterion("is_finished between", value1, value2, "isFinished");
            return (Criteria) this;
        }

        public Criteria andIsFinishedNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_finished not between", value1, value2, "isFinished");
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