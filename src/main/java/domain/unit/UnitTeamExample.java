package domain.unit;

import sys.spring.DateRange;
import sys.utils.DateUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class UnitTeamExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public UnitTeamExample() {
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

        public Criteria andUnitIdIsNull() {
            addCriterion("unit_id is null");
            return (Criteria) this;
        }

        public Criteria andUnitIdIsNotNull() {
            addCriterion("unit_id is not null");
            return (Criteria) this;
        }

        public Criteria andUnitIdEqualTo(Integer value) {
            addCriterion("unit_id =", value, "unitId");
            return (Criteria) this;
        }

        public Criteria andUnitIdNotEqualTo(Integer value) {
            addCriterion("unit_id <>", value, "unitId");
            return (Criteria) this;
        }

        public Criteria andUnitIdGreaterThan(Integer value) {
            addCriterion("unit_id >", value, "unitId");
            return (Criteria) this;
        }

        public Criteria andUnitIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("unit_id >=", value, "unitId");
            return (Criteria) this;
        }

        public Criteria andUnitIdLessThan(Integer value) {
            addCriterion("unit_id <", value, "unitId");
            return (Criteria) this;
        }

        public Criteria andUnitIdLessThanOrEqualTo(Integer value) {
            addCriterion("unit_id <=", value, "unitId");
            return (Criteria) this;
        }

        public Criteria andUnitIdIn(List<Integer> values) {
            addCriterion("unit_id in", values, "unitId");
            return (Criteria) this;
        }

        public Criteria andUnitIdNotIn(List<Integer> values) {
            addCriterion("unit_id not in", values, "unitId");
            return (Criteria) this;
        }

        public Criteria andUnitIdBetween(Integer value1, Integer value2) {
            addCriterion("unit_id between", value1, value2, "unitId");
            return (Criteria) this;
        }

        public Criteria andUnitIdNotBetween(Integer value1, Integer value2) {
            addCriterion("unit_id not between", value1, value2, "unitId");
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

        public Criteria andExpectDeposeDateIsNull() {
            addCriterion("expect_depose_date is null");
            return (Criteria) this;
        }

        public Criteria andExpectDeposeDateIsNotNull() {
            addCriterion("expect_depose_date is not null");
            return (Criteria) this;
        }

        public Criteria andExpectDeposeDateEqualTo(Date value) {
            addCriterionForJDBCDate("expect_depose_date =", value, "expectDeposeDate");
            return (Criteria) this;
        }

        public Criteria andExpectDeposeDateNotEqualTo(Date value) {
            addCriterionForJDBCDate("expect_depose_date <>", value, "expectDeposeDate");
            return (Criteria) this;
        }

        public Criteria andExpectDeposeDateGreaterThan(Date value) {
            addCriterionForJDBCDate("expect_depose_date >", value, "expectDeposeDate");
            return (Criteria) this;
        }

        public Criteria andExpectDeposeDateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("expect_depose_date >=", value, "expectDeposeDate");
            return (Criteria) this;
        }

        public Criteria andExpectDeposeDateLessThan(Date value) {
            addCriterionForJDBCDate("expect_depose_date <", value, "expectDeposeDate");
            return (Criteria) this;
        }

        public Criteria andExpectDeposeDateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("expect_depose_date <=", value, "expectDeposeDate");
            return (Criteria) this;
        }

        public Criteria andExpectDeposeDateIn(List<Date> values) {
            addCriterionForJDBCDate("expect_depose_date in", values, "expectDeposeDate");
            return (Criteria) this;
        }

        public Criteria andExpectDeposeDateNotIn(List<Date> values) {
            addCriterionForJDBCDate("expect_depose_date not in", values, "expectDeposeDate");
            return (Criteria) this;
        }

        public Criteria andExpectDeposeDateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("expect_depose_date between", value1, value2, "expectDeposeDate");
            return (Criteria) this;
        }

        public Criteria andExpectDeposeDateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("expect_depose_date not between", value1, value2, "expectDeposeDate");
            return (Criteria) this;
        }

        public Criteria andAppointDispatchCadreIdIsNull() {
            addCriterion("appoint_dispatch_cadre_id is null");
            return (Criteria) this;
        }

        public Criteria andAppointDispatchCadreIdIsNotNull() {
            addCriterion("appoint_dispatch_cadre_id is not null");
            return (Criteria) this;
        }

        public Criteria andAppointDispatchCadreIdEqualTo(Integer value) {
            addCriterion("appoint_dispatch_cadre_id =", value, "appointDispatchCadreId");
            return (Criteria) this;
        }

        public Criteria andAppointDispatchCadreIdNotEqualTo(Integer value) {
            addCriterion("appoint_dispatch_cadre_id <>", value, "appointDispatchCadreId");
            return (Criteria) this;
        }

        public Criteria andAppointDispatchCadreIdGreaterThan(Integer value) {
            addCriterion("appoint_dispatch_cadre_id >", value, "appointDispatchCadreId");
            return (Criteria) this;
        }

        public Criteria andAppointDispatchCadreIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("appoint_dispatch_cadre_id >=", value, "appointDispatchCadreId");
            return (Criteria) this;
        }

        public Criteria andAppointDispatchCadreIdLessThan(Integer value) {
            addCriterion("appoint_dispatch_cadre_id <", value, "appointDispatchCadreId");
            return (Criteria) this;
        }

        public Criteria andAppointDispatchCadreIdLessThanOrEqualTo(Integer value) {
            addCriterion("appoint_dispatch_cadre_id <=", value, "appointDispatchCadreId");
            return (Criteria) this;
        }

        public Criteria andAppointDispatchCadreIdIn(List<Integer> values) {
            addCriterion("appoint_dispatch_cadre_id in", values, "appointDispatchCadreId");
            return (Criteria) this;
        }

        public Criteria andAppointDispatchCadreIdNotIn(List<Integer> values) {
            addCriterion("appoint_dispatch_cadre_id not in", values, "appointDispatchCadreId");
            return (Criteria) this;
        }

        public Criteria andAppointDispatchCadreIdBetween(Integer value1, Integer value2) {
            addCriterion("appoint_dispatch_cadre_id between", value1, value2, "appointDispatchCadreId");
            return (Criteria) this;
        }

        public Criteria andAppointDispatchCadreIdNotBetween(Integer value1, Integer value2) {
            addCriterion("appoint_dispatch_cadre_id not between", value1, value2, "appointDispatchCadreId");
            return (Criteria) this;
        }

        public Criteria andAppointDateIsNull() {
            addCriterion("appoint_date is null");
            return (Criteria) this;
        }

        public Criteria andAppointDateIsNotNull() {
            addCriterion("appoint_date is not null");
            return (Criteria) this;
        }

        public Criteria andAppointDateEqualTo(Date value) {
            addCriterionForJDBCDate("appoint_date =", value, "appointDate");
            return (Criteria) this;
        }

        public Criteria andAppointDateNotEqualTo(Date value) {
            addCriterionForJDBCDate("appoint_date <>", value, "appointDate");
            return (Criteria) this;
        }

        public Criteria andAppointDateGreaterThan(Date value) {
            addCriterionForJDBCDate("appoint_date >", value, "appointDate");
            return (Criteria) this;
        }

        public Criteria andAppointDateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("appoint_date >=", value, "appointDate");
            return (Criteria) this;
        }

        public Criteria andAppointDateLessThan(Date value) {
            addCriterionForJDBCDate("appoint_date <", value, "appointDate");
            return (Criteria) this;
        }

        public Criteria andAppointDateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("appoint_date <=", value, "appointDate");
            return (Criteria) this;
        }

        public Criteria andAppointDateIn(List<Date> values) {
            addCriterionForJDBCDate("appoint_date in", values, "appointDate");
            return (Criteria) this;
        }

        public Criteria andAppointDateNotIn(List<Date> values) {
            addCriterionForJDBCDate("appoint_date not in", values, "appointDate");
            return (Criteria) this;
        }

        public Criteria andAppointDateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("appoint_date between", value1, value2, "appointDate");
            return (Criteria) this;
        }

        public Criteria andAppointDateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("appoint_date not between", value1, value2, "appointDate");
            return (Criteria) this;
        }

        public Criteria andDeposeDispatchCadreIdIsNull() {
            addCriterion("depose_dispatch_cadre_id is null");
            return (Criteria) this;
        }

        public Criteria andDeposeDispatchCadreIdIsNotNull() {
            addCriterion("depose_dispatch_cadre_id is not null");
            return (Criteria) this;
        }

        public Criteria andDeposeDispatchCadreIdEqualTo(Integer value) {
            addCriterion("depose_dispatch_cadre_id =", value, "deposeDispatchCadreId");
            return (Criteria) this;
        }

        public Criteria andDeposeDispatchCadreIdNotEqualTo(Integer value) {
            addCriterion("depose_dispatch_cadre_id <>", value, "deposeDispatchCadreId");
            return (Criteria) this;
        }

        public Criteria andDeposeDispatchCadreIdGreaterThan(Integer value) {
            addCriterion("depose_dispatch_cadre_id >", value, "deposeDispatchCadreId");
            return (Criteria) this;
        }

        public Criteria andDeposeDispatchCadreIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("depose_dispatch_cadre_id >=", value, "deposeDispatchCadreId");
            return (Criteria) this;
        }

        public Criteria andDeposeDispatchCadreIdLessThan(Integer value) {
            addCriterion("depose_dispatch_cadre_id <", value, "deposeDispatchCadreId");
            return (Criteria) this;
        }

        public Criteria andDeposeDispatchCadreIdLessThanOrEqualTo(Integer value) {
            addCriterion("depose_dispatch_cadre_id <=", value, "deposeDispatchCadreId");
            return (Criteria) this;
        }

        public Criteria andDeposeDispatchCadreIdIn(List<Integer> values) {
            addCriterion("depose_dispatch_cadre_id in", values, "deposeDispatchCadreId");
            return (Criteria) this;
        }

        public Criteria andDeposeDispatchCadreIdNotIn(List<Integer> values) {
            addCriterion("depose_dispatch_cadre_id not in", values, "deposeDispatchCadreId");
            return (Criteria) this;
        }

        public Criteria andDeposeDispatchCadreIdBetween(Integer value1, Integer value2) {
            addCriterion("depose_dispatch_cadre_id between", value1, value2, "deposeDispatchCadreId");
            return (Criteria) this;
        }

        public Criteria andDeposeDispatchCadreIdNotBetween(Integer value1, Integer value2) {
            addCriterion("depose_dispatch_cadre_id not between", value1, value2, "deposeDispatchCadreId");
            return (Criteria) this;
        }

        public Criteria andDeposeDateIsNull() {
            addCriterion("depose_date is null");
            return (Criteria) this;
        }

        public Criteria andDeposeDateIsNotNull() {
            addCriterion("depose_date is not null");
            return (Criteria) this;
        }

        public Criteria andDeposeDateEqualTo(Date value) {
            addCriterionForJDBCDate("depose_date =", value, "deposeDate");
            return (Criteria) this;
        }

        public Criteria andDeposeDateNotEqualTo(Date value) {
            addCriterionForJDBCDate("depose_date <>", value, "deposeDate");
            return (Criteria) this;
        }

        public Criteria andDeposeDateGreaterThan(Date value) {
            addCriterionForJDBCDate("depose_date >", value, "deposeDate");
            return (Criteria) this;
        }

        public Criteria andDeposeDateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("depose_date >=", value, "deposeDate");
            return (Criteria) this;
        }

        public Criteria andDeposeDateLessThan(Date value) {
            addCriterionForJDBCDate("depose_date <", value, "deposeDate");
            return (Criteria) this;
        }

        public Criteria andDeposeDateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("depose_date <=", value, "deposeDate");
            return (Criteria) this;
        }

        public Criteria andDeposeDateIn(List<Date> values) {
            addCriterionForJDBCDate("depose_date in", values, "deposeDate");
            return (Criteria) this;
        }

        public Criteria andDeposeDateNotIn(List<Date> values) {
            addCriterionForJDBCDate("depose_date not in", values, "deposeDate");
            return (Criteria) this;
        }

        public Criteria andDeposeDateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("depose_date between", value1, value2, "deposeDate");
            return (Criteria) this;
        }

        public Criteria andDeposeDateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("depose_date not between", value1, value2, "deposeDate");
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
    
        public void andTimeLevelEqualTo(Integer timeLevel, DateRange termTimeRange, DateRange _deposeTime) {
            if(timeLevel==null) return;
            Integer currentYear = DateUtils.getCurrentYear();
            if(timeLevel==0) {
                // 默认显示已到应换届时间的班子
                //addCriterion("expect_depose_date <= now() and depose_date is null");

                // 默认显示已到应换届时间的班子、本学期和本年度将要到届的班子
                /*if(termTimeRange!=null) {
                    String start = DateUtils.formatDate(termTimeRange.getStart(), DateUtils.YYYY_MM_DD);
                    String end = DateUtils.formatDate(termTimeRange.getEnd(), DateUtils.YYYY_MM_DD);
                    addCriterion("((expect_depose_date >= " + start + " and expect_depose_date<=" + end + ") " +
                            "or expect_depose_date<=now() or "
                            + "(left(expect_depose_date,4)="+ currentYear
                            + " or expect_depose_date<=now())" +") and depose_date is null");
                }else{
                     addCriterion("(expect_depose_date<=now() or "
                            + "(left(expect_depose_date,4)="+ currentYear
                            + " or expect_depose_date<=now())" +") and depose_date is null");
                }*/
                // 显示所有？
                addCriterion("depose_date is null");

            }else if(timeLevel==1){
                // 本年度应启动换届单位
                addCriterion("(left(expect_depose_date,4)="+ currentYear + " or expect_depose_date<=now()) " +
                        " and depose_date is null");
            }else if(timeLevel==2){
                if(termTimeRange==null) return;
                String start = DateUtils.formatDate(termTimeRange.getStart(), DateUtils.YYYY_MM_DD);
                String end = DateUtils.formatDate(termTimeRange.getEnd(), DateUtils.YYYY_MM_DD);
                // 本学期应启动换届单位
                addCriterion("((expect_depose_date >= "+ start + " and expect_depose_date<="+ end +") " +
                        "or expect_depose_date<=now()) and depose_date is null");
            }else if(timeLevel==3){
                if(_deposeTime==null || (_deposeTime.getStart()==null && _deposeTime.getEnd()==null)){

                    if(termTimeRange!=null) {
                        String start = DateUtils.formatDate(termTimeRange.getStart(), DateUtils.YYYY_MM_DD);
                        String end = DateUtils.formatDate(termTimeRange.getEnd(), DateUtils.YYYY_MM_DD);
                        addCriterion("((expect_depose_date < " + start + " or expect_depose_date>" + end + ") " +
                                "and expect_depose_date>now()) and depose_date is null");
                    }else{
                        addCriterion("expect_depose_date>now() and depose_date is null");
                    }

                    return;
                }
                String start = DateUtils.formatDate(_deposeTime.getStart(), DateUtils.YYYY_MM_DD);
                String end = DateUtils.formatDate(_deposeTime.getEnd(), DateUtils.YYYY_MM_DD);

                String searchStr = "";
                if(start!=null && end==null){
                    searchStr = "expect_depose_date >= "+ start;
                }else if(start==null && end!=null){
                    searchStr = "expect_depose_date <= "+ end;
                }else{
                    searchStr = "expect_depose_date >= "+ start + " and expect_depose_date <= "+ end;
                }
                // 其他时段应启动换届单位
                addCriterion("(" + searchStr + ") and depose_date is null");
            }
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