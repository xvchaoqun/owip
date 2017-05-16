package domain.ces;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class CesTempPostExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public CesTempPostExample() {
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

        public Criteria andRealnameIsNull() {
            addCriterion("realname is null");
            return (Criteria) this;
        }

        public Criteria andRealnameIsNotNull() {
            addCriterion("realname is not null");
            return (Criteria) this;
        }

        public Criteria andRealnameEqualTo(String value) {
            addCriterion("realname =", value, "realname");
            return (Criteria) this;
        }

        public Criteria andRealnameNotEqualTo(String value) {
            addCriterion("realname <>", value, "realname");
            return (Criteria) this;
        }

        public Criteria andRealnameGreaterThan(String value) {
            addCriterion("realname >", value, "realname");
            return (Criteria) this;
        }

        public Criteria andRealnameGreaterThanOrEqualTo(String value) {
            addCriterion("realname >=", value, "realname");
            return (Criteria) this;
        }

        public Criteria andRealnameLessThan(String value) {
            addCriterion("realname <", value, "realname");
            return (Criteria) this;
        }

        public Criteria andRealnameLessThanOrEqualTo(String value) {
            addCriterion("realname <=", value, "realname");
            return (Criteria) this;
        }

        public Criteria andRealnameLike(String value) {
            addCriterion("realname like", value, "realname");
            return (Criteria) this;
        }

        public Criteria andRealnameNotLike(String value) {
            addCriterion("realname not like", value, "realname");
            return (Criteria) this;
        }

        public Criteria andRealnameIn(List<String> values) {
            addCriterion("realname in", values, "realname");
            return (Criteria) this;
        }

        public Criteria andRealnameNotIn(List<String> values) {
            addCriterion("realname not in", values, "realname");
            return (Criteria) this;
        }

        public Criteria andRealnameBetween(String value1, String value2) {
            addCriterion("realname between", value1, value2, "realname");
            return (Criteria) this;
        }

        public Criteria andRealnameNotBetween(String value1, String value2) {
            addCriterion("realname not between", value1, value2, "realname");
            return (Criteria) this;
        }

        public Criteria andIsPresentCadreIsNull() {
            addCriterion("is_present_cadre is null");
            return (Criteria) this;
        }

        public Criteria andIsPresentCadreIsNotNull() {
            addCriterion("is_present_cadre is not null");
            return (Criteria) this;
        }

        public Criteria andIsPresentCadreEqualTo(Boolean value) {
            addCriterion("is_present_cadre =", value, "isPresentCadre");
            return (Criteria) this;
        }

        public Criteria andIsPresentCadreNotEqualTo(Boolean value) {
            addCriterion("is_present_cadre <>", value, "isPresentCadre");
            return (Criteria) this;
        }

        public Criteria andIsPresentCadreGreaterThan(Boolean value) {
            addCriterion("is_present_cadre >", value, "isPresentCadre");
            return (Criteria) this;
        }

        public Criteria andIsPresentCadreGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_present_cadre >=", value, "isPresentCadre");
            return (Criteria) this;
        }

        public Criteria andIsPresentCadreLessThan(Boolean value) {
            addCriterion("is_present_cadre <", value, "isPresentCadre");
            return (Criteria) this;
        }

        public Criteria andIsPresentCadreLessThanOrEqualTo(Boolean value) {
            addCriterion("is_present_cadre <=", value, "isPresentCadre");
            return (Criteria) this;
        }

        public Criteria andIsPresentCadreIn(List<Boolean> values) {
            addCriterion("is_present_cadre in", values, "isPresentCadre");
            return (Criteria) this;
        }

        public Criteria andIsPresentCadreNotIn(List<Boolean> values) {
            addCriterion("is_present_cadre not in", values, "isPresentCadre");
            return (Criteria) this;
        }

        public Criteria andIsPresentCadreBetween(Boolean value1, Boolean value2) {
            addCriterion("is_present_cadre between", value1, value2, "isPresentCadre");
            return (Criteria) this;
        }

        public Criteria andIsPresentCadreNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_present_cadre not between", value1, value2, "isPresentCadre");
            return (Criteria) this;
        }

        public Criteria andPresentPostIsNull() {
            addCriterion("present_post is null");
            return (Criteria) this;
        }

        public Criteria andPresentPostIsNotNull() {
            addCriterion("present_post is not null");
            return (Criteria) this;
        }

        public Criteria andPresentPostEqualTo(String value) {
            addCriterion("present_post =", value, "presentPost");
            return (Criteria) this;
        }

        public Criteria andPresentPostNotEqualTo(String value) {
            addCriterion("present_post <>", value, "presentPost");
            return (Criteria) this;
        }

        public Criteria andPresentPostGreaterThan(String value) {
            addCriterion("present_post >", value, "presentPost");
            return (Criteria) this;
        }

        public Criteria andPresentPostGreaterThanOrEqualTo(String value) {
            addCriterion("present_post >=", value, "presentPost");
            return (Criteria) this;
        }

        public Criteria andPresentPostLessThan(String value) {
            addCriterion("present_post <", value, "presentPost");
            return (Criteria) this;
        }

        public Criteria andPresentPostLessThanOrEqualTo(String value) {
            addCriterion("present_post <=", value, "presentPost");
            return (Criteria) this;
        }

        public Criteria andPresentPostLike(String value) {
            addCriterion("present_post like", value, "presentPost");
            return (Criteria) this;
        }

        public Criteria andPresentPostNotLike(String value) {
            addCriterion("present_post not like", value, "presentPost");
            return (Criteria) this;
        }

        public Criteria andPresentPostIn(List<String> values) {
            addCriterion("present_post in", values, "presentPost");
            return (Criteria) this;
        }

        public Criteria andPresentPostNotIn(List<String> values) {
            addCriterion("present_post not in", values, "presentPost");
            return (Criteria) this;
        }

        public Criteria andPresentPostBetween(String value1, String value2) {
            addCriterion("present_post between", value1, value2, "presentPost");
            return (Criteria) this;
        }

        public Criteria andPresentPostNotBetween(String value1, String value2) {
            addCriterion("present_post not between", value1, value2, "presentPost");
            return (Criteria) this;
        }

        public Criteria andToUnitTypeIsNull() {
            addCriterion("to_unit_type is null");
            return (Criteria) this;
        }

        public Criteria andToUnitTypeIsNotNull() {
            addCriterion("to_unit_type is not null");
            return (Criteria) this;
        }

        public Criteria andToUnitTypeEqualTo(Integer value) {
            addCriterion("to_unit_type =", value, "toUnitType");
            return (Criteria) this;
        }

        public Criteria andToUnitTypeNotEqualTo(Integer value) {
            addCriterion("to_unit_type <>", value, "toUnitType");
            return (Criteria) this;
        }

        public Criteria andToUnitTypeGreaterThan(Integer value) {
            addCriterion("to_unit_type >", value, "toUnitType");
            return (Criteria) this;
        }

        public Criteria andToUnitTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("to_unit_type >=", value, "toUnitType");
            return (Criteria) this;
        }

        public Criteria andToUnitTypeLessThan(Integer value) {
            addCriterion("to_unit_type <", value, "toUnitType");
            return (Criteria) this;
        }

        public Criteria andToUnitTypeLessThanOrEqualTo(Integer value) {
            addCriterion("to_unit_type <=", value, "toUnitType");
            return (Criteria) this;
        }

        public Criteria andToUnitTypeIn(List<Integer> values) {
            addCriterion("to_unit_type in", values, "toUnitType");
            return (Criteria) this;
        }

        public Criteria andToUnitTypeNotIn(List<Integer> values) {
            addCriterion("to_unit_type not in", values, "toUnitType");
            return (Criteria) this;
        }

        public Criteria andToUnitTypeBetween(Integer value1, Integer value2) {
            addCriterion("to_unit_type between", value1, value2, "toUnitType");
            return (Criteria) this;
        }

        public Criteria andToUnitTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("to_unit_type not between", value1, value2, "toUnitType");
            return (Criteria) this;
        }

        public Criteria andToUnitIsNull() {
            addCriterion("to_unit is null");
            return (Criteria) this;
        }

        public Criteria andToUnitIsNotNull() {
            addCriterion("to_unit is not null");
            return (Criteria) this;
        }

        public Criteria andToUnitEqualTo(String value) {
            addCriterion("to_unit =", value, "toUnit");
            return (Criteria) this;
        }

        public Criteria andToUnitNotEqualTo(String value) {
            addCriterion("to_unit <>", value, "toUnit");
            return (Criteria) this;
        }

        public Criteria andToUnitGreaterThan(String value) {
            addCriterion("to_unit >", value, "toUnit");
            return (Criteria) this;
        }

        public Criteria andToUnitGreaterThanOrEqualTo(String value) {
            addCriterion("to_unit >=", value, "toUnit");
            return (Criteria) this;
        }

        public Criteria andToUnitLessThan(String value) {
            addCriterion("to_unit <", value, "toUnit");
            return (Criteria) this;
        }

        public Criteria andToUnitLessThanOrEqualTo(String value) {
            addCriterion("to_unit <=", value, "toUnit");
            return (Criteria) this;
        }

        public Criteria andToUnitLike(String value) {
            addCriterion("to_unit like", value, "toUnit");
            return (Criteria) this;
        }

        public Criteria andToUnitNotLike(String value) {
            addCriterion("to_unit not like", value, "toUnit");
            return (Criteria) this;
        }

        public Criteria andToUnitIn(List<String> values) {
            addCriterion("to_unit in", values, "toUnit");
            return (Criteria) this;
        }

        public Criteria andToUnitNotIn(List<String> values) {
            addCriterion("to_unit not in", values, "toUnit");
            return (Criteria) this;
        }

        public Criteria andToUnitBetween(String value1, String value2) {
            addCriterion("to_unit between", value1, value2, "toUnit");
            return (Criteria) this;
        }

        public Criteria andToUnitNotBetween(String value1, String value2) {
            addCriterion("to_unit not between", value1, value2, "toUnit");
            return (Criteria) this;
        }

        public Criteria andTempPostTypeIsNull() {
            addCriterion("temp_post_type is null");
            return (Criteria) this;
        }

        public Criteria andTempPostTypeIsNotNull() {
            addCriterion("temp_post_type is not null");
            return (Criteria) this;
        }

        public Criteria andTempPostTypeEqualTo(Integer value) {
            addCriterion("temp_post_type =", value, "tempPostType");
            return (Criteria) this;
        }

        public Criteria andTempPostTypeNotEqualTo(Integer value) {
            addCriterion("temp_post_type <>", value, "tempPostType");
            return (Criteria) this;
        }

        public Criteria andTempPostTypeGreaterThan(Integer value) {
            addCriterion("temp_post_type >", value, "tempPostType");
            return (Criteria) this;
        }

        public Criteria andTempPostTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("temp_post_type >=", value, "tempPostType");
            return (Criteria) this;
        }

        public Criteria andTempPostTypeLessThan(Integer value) {
            addCriterion("temp_post_type <", value, "tempPostType");
            return (Criteria) this;
        }

        public Criteria andTempPostTypeLessThanOrEqualTo(Integer value) {
            addCriterion("temp_post_type <=", value, "tempPostType");
            return (Criteria) this;
        }

        public Criteria andTempPostTypeIn(List<Integer> values) {
            addCriterion("temp_post_type in", values, "tempPostType");
            return (Criteria) this;
        }

        public Criteria andTempPostTypeNotIn(List<Integer> values) {
            addCriterion("temp_post_type not in", values, "tempPostType");
            return (Criteria) this;
        }

        public Criteria andTempPostTypeBetween(Integer value1, Integer value2) {
            addCriterion("temp_post_type between", value1, value2, "tempPostType");
            return (Criteria) this;
        }

        public Criteria andTempPostTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("temp_post_type not between", value1, value2, "tempPostType");
            return (Criteria) this;
        }

        public Criteria andTempPostIsNull() {
            addCriterion("temp_post is null");
            return (Criteria) this;
        }

        public Criteria andTempPostIsNotNull() {
            addCriterion("temp_post is not null");
            return (Criteria) this;
        }

        public Criteria andTempPostEqualTo(String value) {
            addCriterion("temp_post =", value, "tempPost");
            return (Criteria) this;
        }

        public Criteria andTempPostNotEqualTo(String value) {
            addCriterion("temp_post <>", value, "tempPost");
            return (Criteria) this;
        }

        public Criteria andTempPostGreaterThan(String value) {
            addCriterion("temp_post >", value, "tempPost");
            return (Criteria) this;
        }

        public Criteria andTempPostGreaterThanOrEqualTo(String value) {
            addCriterion("temp_post >=", value, "tempPost");
            return (Criteria) this;
        }

        public Criteria andTempPostLessThan(String value) {
            addCriterion("temp_post <", value, "tempPost");
            return (Criteria) this;
        }

        public Criteria andTempPostLessThanOrEqualTo(String value) {
            addCriterion("temp_post <=", value, "tempPost");
            return (Criteria) this;
        }

        public Criteria andTempPostLike(String value) {
            addCriterion("temp_post like", value, "tempPost");
            return (Criteria) this;
        }

        public Criteria andTempPostNotLike(String value) {
            addCriterion("temp_post not like", value, "tempPost");
            return (Criteria) this;
        }

        public Criteria andTempPostIn(List<String> values) {
            addCriterion("temp_post in", values, "tempPost");
            return (Criteria) this;
        }

        public Criteria andTempPostNotIn(List<String> values) {
            addCriterion("temp_post not in", values, "tempPost");
            return (Criteria) this;
        }

        public Criteria andTempPostBetween(String value1, String value2) {
            addCriterion("temp_post between", value1, value2, "tempPost");
            return (Criteria) this;
        }

        public Criteria andTempPostNotBetween(String value1, String value2) {
            addCriterion("temp_post not between", value1, value2, "tempPost");
            return (Criteria) this;
        }

        public Criteria andTitleIsNull() {
            addCriterion("title is null");
            return (Criteria) this;
        }

        public Criteria andTitleIsNotNull() {
            addCriterion("title is not null");
            return (Criteria) this;
        }

        public Criteria andTitleEqualTo(String value) {
            addCriterion("title =", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotEqualTo(String value) {
            addCriterion("title <>", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleGreaterThan(String value) {
            addCriterion("title >", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleGreaterThanOrEqualTo(String value) {
            addCriterion("title >=", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleLessThan(String value) {
            addCriterion("title <", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleLessThanOrEqualTo(String value) {
            addCriterion("title <=", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleLike(String value) {
            addCriterion("title like", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotLike(String value) {
            addCriterion("title not like", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleIn(List<String> values) {
            addCriterion("title in", values, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotIn(List<String> values) {
            addCriterion("title not in", values, "title");
            return (Criteria) this;
        }

        public Criteria andTitleBetween(String value1, String value2) {
            addCriterion("title between", value1, value2, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotBetween(String value1, String value2) {
            addCriterion("title not between", value1, value2, "title");
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

        public Criteria andRealEndDateIsNull() {
            addCriterion("real_end_date is null");
            return (Criteria) this;
        }

        public Criteria andRealEndDateIsNotNull() {
            addCriterion("real_end_date is not null");
            return (Criteria) this;
        }

        public Criteria andRealEndDateEqualTo(Date value) {
            addCriterionForJDBCDate("real_end_date =", value, "realEndDate");
            return (Criteria) this;
        }

        public Criteria andRealEndDateNotEqualTo(Date value) {
            addCriterionForJDBCDate("real_end_date <>", value, "realEndDate");
            return (Criteria) this;
        }

        public Criteria andRealEndDateGreaterThan(Date value) {
            addCriterionForJDBCDate("real_end_date >", value, "realEndDate");
            return (Criteria) this;
        }

        public Criteria andRealEndDateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("real_end_date >=", value, "realEndDate");
            return (Criteria) this;
        }

        public Criteria andRealEndDateLessThan(Date value) {
            addCriterionForJDBCDate("real_end_date <", value, "realEndDate");
            return (Criteria) this;
        }

        public Criteria andRealEndDateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("real_end_date <=", value, "realEndDate");
            return (Criteria) this;
        }

        public Criteria andRealEndDateIn(List<Date> values) {
            addCriterionForJDBCDate("real_end_date in", values, "realEndDate");
            return (Criteria) this;
        }

        public Criteria andRealEndDateNotIn(List<Date> values) {
            addCriterionForJDBCDate("real_end_date not in", values, "realEndDate");
            return (Criteria) this;
        }

        public Criteria andRealEndDateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("real_end_date between", value1, value2, "realEndDate");
            return (Criteria) this;
        }

        public Criteria andRealEndDateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("real_end_date not between", value1, value2, "realEndDate");
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