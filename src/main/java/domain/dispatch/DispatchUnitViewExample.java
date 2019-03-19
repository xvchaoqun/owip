package domain.dispatch;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class DispatchUnitViewExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public DispatchUnitViewExample() {
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

        public Criteria andDispatchIdIsNull() {
            addCriterion("dispatch_id is null");
            return (Criteria) this;
        }

        public Criteria andDispatchIdIsNotNull() {
            addCriterion("dispatch_id is not null");
            return (Criteria) this;
        }

        public Criteria andDispatchIdEqualTo(Integer value) {
            addCriterion("dispatch_id =", value, "dispatchId");
            return (Criteria) this;
        }

        public Criteria andDispatchIdNotEqualTo(Integer value) {
            addCriterion("dispatch_id <>", value, "dispatchId");
            return (Criteria) this;
        }

        public Criteria andDispatchIdGreaterThan(Integer value) {
            addCriterion("dispatch_id >", value, "dispatchId");
            return (Criteria) this;
        }

        public Criteria andDispatchIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("dispatch_id >=", value, "dispatchId");
            return (Criteria) this;
        }

        public Criteria andDispatchIdLessThan(Integer value) {
            addCriterion("dispatch_id <", value, "dispatchId");
            return (Criteria) this;
        }

        public Criteria andDispatchIdLessThanOrEqualTo(Integer value) {
            addCriterion("dispatch_id <=", value, "dispatchId");
            return (Criteria) this;
        }

        public Criteria andDispatchIdIn(List<Integer> values) {
            addCriterion("dispatch_id in", values, "dispatchId");
            return (Criteria) this;
        }

        public Criteria andDispatchIdNotIn(List<Integer> values) {
            addCriterion("dispatch_id not in", values, "dispatchId");
            return (Criteria) this;
        }

        public Criteria andDispatchIdBetween(Integer value1, Integer value2) {
            addCriterion("dispatch_id between", value1, value2, "dispatchId");
            return (Criteria) this;
        }

        public Criteria andDispatchIdNotBetween(Integer value1, Integer value2) {
            addCriterion("dispatch_id not between", value1, value2, "dispatchId");
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

        public Criteria andUnitIdContain(List<Integer> values) {
            String unitIds = StringUtils.join(values, ",");
            addCriterion("(unit_id in ("+unitIds+") or old_unit_id in ("+unitIds+"))");
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

        public Criteria andOldUnitIdIsNull() {
            addCriterion("old_unit_id is null");
            return (Criteria) this;
        }

        public Criteria andOldUnitIdIsNotNull() {
            addCriterion("old_unit_id is not null");
            return (Criteria) this;
        }

        public Criteria andOldUnitIdEqualTo(Integer value) {
            addCriterion("old_unit_id =", value, "oldUnitId");
            return (Criteria) this;
        }

        public Criteria andOldUnitIdNotEqualTo(Integer value) {
            addCriterion("old_unit_id <>", value, "oldUnitId");
            return (Criteria) this;
        }

        public Criteria andOldUnitIdGreaterThan(Integer value) {
            addCriterion("old_unit_id >", value, "oldUnitId");
            return (Criteria) this;
        }

        public Criteria andOldUnitIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("old_unit_id >=", value, "oldUnitId");
            return (Criteria) this;
        }

        public Criteria andOldUnitIdLessThan(Integer value) {
            addCriterion("old_unit_id <", value, "oldUnitId");
            return (Criteria) this;
        }

        public Criteria andOldUnitIdLessThanOrEqualTo(Integer value) {
            addCriterion("old_unit_id <=", value, "oldUnitId");
            return (Criteria) this;
        }

        public Criteria andOldUnitIdIn(List<Integer> values) {
            addCriterion("old_unit_id in", values, "oldUnitId");
            return (Criteria) this;
        }

        public Criteria andOldUnitIdNotIn(List<Integer> values) {
            addCriterion("old_unit_id not in", values, "oldUnitId");
            return (Criteria) this;
        }

        public Criteria andOldUnitIdBetween(Integer value1, Integer value2) {
            addCriterion("old_unit_id between", value1, value2, "oldUnitId");
            return (Criteria) this;
        }

        public Criteria andOldUnitIdNotBetween(Integer value1, Integer value2) {
            addCriterion("old_unit_id not between", value1, value2, "oldUnitId");
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

        public Criteria andCategoryContain(byte category, boolean contain) {
            addCriterion("find_in_set("+category+", category)="+contain);
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