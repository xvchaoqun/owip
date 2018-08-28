package domain.sc.scSubsidy;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class ScSubsidyCadreViewExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public ScSubsidyCadreViewExample() {
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

        public Criteria andSubsidyIdIsNull() {
            addCriterion("subsidy_id is null");
            return (Criteria) this;
        }

        public Criteria andSubsidyIdIsNotNull() {
            addCriterion("subsidy_id is not null");
            return (Criteria) this;
        }

        public Criteria andSubsidyIdEqualTo(Integer value) {
            addCriterion("subsidy_id =", value, "subsidyId");
            return (Criteria) this;
        }

        public Criteria andSubsidyIdNotEqualTo(Integer value) {
            addCriterion("subsidy_id <>", value, "subsidyId");
            return (Criteria) this;
        }

        public Criteria andSubsidyIdGreaterThan(Integer value) {
            addCriterion("subsidy_id >", value, "subsidyId");
            return (Criteria) this;
        }

        public Criteria andSubsidyIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("subsidy_id >=", value, "subsidyId");
            return (Criteria) this;
        }

        public Criteria andSubsidyIdLessThan(Integer value) {
            addCriterion("subsidy_id <", value, "subsidyId");
            return (Criteria) this;
        }

        public Criteria andSubsidyIdLessThanOrEqualTo(Integer value) {
            addCriterion("subsidy_id <=", value, "subsidyId");
            return (Criteria) this;
        }

        public Criteria andSubsidyIdIn(List<Integer> values) {
            addCriterion("subsidy_id in", values, "subsidyId");
            return (Criteria) this;
        }

        public Criteria andSubsidyIdNotIn(List<Integer> values) {
            addCriterion("subsidy_id not in", values, "subsidyId");
            return (Criteria) this;
        }

        public Criteria andSubsidyIdBetween(Integer value1, Integer value2) {
            addCriterion("subsidy_id between", value1, value2, "subsidyId");
            return (Criteria) this;
        }

        public Criteria andSubsidyIdNotBetween(Integer value1, Integer value2) {
            addCriterion("subsidy_id not between", value1, value2, "subsidyId");
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

        public Criteria andPostIsNull() {
            addCriterion("post is null");
            return (Criteria) this;
        }

        public Criteria andPostIsNotNull() {
            addCriterion("post is not null");
            return (Criteria) this;
        }

        public Criteria andPostEqualTo(String value) {
            addCriterion("post =", value, "post");
            return (Criteria) this;
        }

        public Criteria andPostNotEqualTo(String value) {
            addCriterion("post <>", value, "post");
            return (Criteria) this;
        }

        public Criteria andPostGreaterThan(String value) {
            addCriterion("post >", value, "post");
            return (Criteria) this;
        }

        public Criteria andPostGreaterThanOrEqualTo(String value) {
            addCriterion("post >=", value, "post");
            return (Criteria) this;
        }

        public Criteria andPostLessThan(String value) {
            addCriterion("post <", value, "post");
            return (Criteria) this;
        }

        public Criteria andPostLessThanOrEqualTo(String value) {
            addCriterion("post <=", value, "post");
            return (Criteria) this;
        }

        public Criteria andPostLike(String value) {
            addCriterion("post like", value, "post");
            return (Criteria) this;
        }

        public Criteria andPostNotLike(String value) {
            addCriterion("post not like", value, "post");
            return (Criteria) this;
        }

        public Criteria andPostIn(List<String> values) {
            addCriterion("post in", values, "post");
            return (Criteria) this;
        }

        public Criteria andPostNotIn(List<String> values) {
            addCriterion("post not in", values, "post");
            return (Criteria) this;
        }

        public Criteria andPostBetween(String value1, String value2) {
            addCriterion("post between", value1, value2, "post");
            return (Criteria) this;
        }

        public Criteria andPostNotBetween(String value1, String value2) {
            addCriterion("post not between", value1, value2, "post");
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

        public Criteria andAdminLevelIsNull() {
            addCriterion("admin_level is null");
            return (Criteria) this;
        }

        public Criteria andAdminLevelIsNotNull() {
            addCriterion("admin_level is not null");
            return (Criteria) this;
        }

        public Criteria andAdminLevelEqualTo(Integer value) {
            addCriterion("admin_level =", value, "adminLevel");
            return (Criteria) this;
        }

        public Criteria andAdminLevelNotEqualTo(Integer value) {
            addCriterion("admin_level <>", value, "adminLevel");
            return (Criteria) this;
        }

        public Criteria andAdminLevelGreaterThan(Integer value) {
            addCriterion("admin_level >", value, "adminLevel");
            return (Criteria) this;
        }

        public Criteria andAdminLevelGreaterThanOrEqualTo(Integer value) {
            addCriterion("admin_level >=", value, "adminLevel");
            return (Criteria) this;
        }

        public Criteria andAdminLevelLessThan(Integer value) {
            addCriterion("admin_level <", value, "adminLevel");
            return (Criteria) this;
        }

        public Criteria andAdminLevelLessThanOrEqualTo(Integer value) {
            addCriterion("admin_level <=", value, "adminLevel");
            return (Criteria) this;
        }

        public Criteria andAdminLevelIn(List<Integer> values) {
            addCriterion("admin_level in", values, "adminLevel");
            return (Criteria) this;
        }

        public Criteria andAdminLevelNotIn(List<Integer> values) {
            addCriterion("admin_level not in", values, "adminLevel");
            return (Criteria) this;
        }

        public Criteria andAdminLevelBetween(Integer value1, Integer value2) {
            addCriterion("admin_level between", value1, value2, "adminLevel");
            return (Criteria) this;
        }

        public Criteria andAdminLevelNotBetween(Integer value1, Integer value2) {
            addCriterion("admin_level not between", value1, value2, "adminLevel");
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

        public Criteria andInfoDateIsNull() {
            addCriterion("info_date is null");
            return (Criteria) this;
        }

        public Criteria andInfoDateIsNotNull() {
            addCriterion("info_date is not null");
            return (Criteria) this;
        }

        public Criteria andInfoDateEqualTo(Date value) {
            addCriterionForJDBCDate("info_date =", value, "infoDate");
            return (Criteria) this;
        }

        public Criteria andInfoDateNotEqualTo(Date value) {
            addCriterionForJDBCDate("info_date <>", value, "infoDate");
            return (Criteria) this;
        }

        public Criteria andInfoDateGreaterThan(Date value) {
            addCriterionForJDBCDate("info_date >", value, "infoDate");
            return (Criteria) this;
        }

        public Criteria andInfoDateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("info_date >=", value, "infoDate");
            return (Criteria) this;
        }

        public Criteria andInfoDateLessThan(Date value) {
            addCriterionForJDBCDate("info_date <", value, "infoDate");
            return (Criteria) this;
        }

        public Criteria andInfoDateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("info_date <=", value, "infoDate");
            return (Criteria) this;
        }

        public Criteria andInfoDateIn(List<Date> values) {
            addCriterionForJDBCDate("info_date in", values, "infoDate");
            return (Criteria) this;
        }

        public Criteria andInfoDateNotIn(List<Date> values) {
            addCriterionForJDBCDate("info_date not in", values, "infoDate");
            return (Criteria) this;
        }

        public Criteria andInfoDateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("info_date between", value1, value2, "infoDate");
            return (Criteria) this;
        }

        public Criteria andInfoDateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("info_date not between", value1, value2, "infoDate");
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

        public Criteria andHrTypeIsNull() {
            addCriterion("hr_type is null");
            return (Criteria) this;
        }

        public Criteria andHrTypeIsNotNull() {
            addCriterion("hr_type is not null");
            return (Criteria) this;
        }

        public Criteria andHrTypeEqualTo(Integer value) {
            addCriterion("hr_type =", value, "hrType");
            return (Criteria) this;
        }

        public Criteria andHrTypeNotEqualTo(Integer value) {
            addCriterion("hr_type <>", value, "hrType");
            return (Criteria) this;
        }

        public Criteria andHrTypeGreaterThan(Integer value) {
            addCriterion("hr_type >", value, "hrType");
            return (Criteria) this;
        }

        public Criteria andHrTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("hr_type >=", value, "hrType");
            return (Criteria) this;
        }

        public Criteria andHrTypeLessThan(Integer value) {
            addCriterion("hr_type <", value, "hrType");
            return (Criteria) this;
        }

        public Criteria andHrTypeLessThanOrEqualTo(Integer value) {
            addCriterion("hr_type <=", value, "hrType");
            return (Criteria) this;
        }

        public Criteria andHrTypeIn(List<Integer> values) {
            addCriterion("hr_type in", values, "hrType");
            return (Criteria) this;
        }

        public Criteria andHrTypeNotIn(List<Integer> values) {
            addCriterion("hr_type not in", values, "hrType");
            return (Criteria) this;
        }

        public Criteria andHrTypeBetween(Integer value1, Integer value2) {
            addCriterion("hr_type between", value1, value2, "hrType");
            return (Criteria) this;
        }

        public Criteria andHrTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("hr_type not between", value1, value2, "hrType");
            return (Criteria) this;
        }

        public Criteria andHrNumIsNull() {
            addCriterion("hr_num is null");
            return (Criteria) this;
        }

        public Criteria andHrNumIsNotNull() {
            addCriterion("hr_num is not null");
            return (Criteria) this;
        }

        public Criteria andHrNumEqualTo(Integer value) {
            addCriterion("hr_num =", value, "hrNum");
            return (Criteria) this;
        }

        public Criteria andHrNumNotEqualTo(Integer value) {
            addCriterion("hr_num <>", value, "hrNum");
            return (Criteria) this;
        }

        public Criteria andHrNumGreaterThan(Integer value) {
            addCriterion("hr_num >", value, "hrNum");
            return (Criteria) this;
        }

        public Criteria andHrNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("hr_num >=", value, "hrNum");
            return (Criteria) this;
        }

        public Criteria andHrNumLessThan(Integer value) {
            addCriterion("hr_num <", value, "hrNum");
            return (Criteria) this;
        }

        public Criteria andHrNumLessThanOrEqualTo(Integer value) {
            addCriterion("hr_num <=", value, "hrNum");
            return (Criteria) this;
        }

        public Criteria andHrNumIn(List<Integer> values) {
            addCriterion("hr_num in", values, "hrNum");
            return (Criteria) this;
        }

        public Criteria andHrNumNotIn(List<Integer> values) {
            addCriterion("hr_num not in", values, "hrNum");
            return (Criteria) this;
        }

        public Criteria andHrNumBetween(Integer value1, Integer value2) {
            addCriterion("hr_num between", value1, value2, "hrNum");
            return (Criteria) this;
        }

        public Criteria andHrNumNotBetween(Integer value1, Integer value2) {
            addCriterion("hr_num not between", value1, value2, "hrNum");
            return (Criteria) this;
        }

        public Criteria andHrFilePathIsNull() {
            addCriterion("hr_file_path is null");
            return (Criteria) this;
        }

        public Criteria andHrFilePathIsNotNull() {
            addCriterion("hr_file_path is not null");
            return (Criteria) this;
        }

        public Criteria andHrFilePathEqualTo(String value) {
            addCriterion("hr_file_path =", value, "hrFilePath");
            return (Criteria) this;
        }

        public Criteria andHrFilePathNotEqualTo(String value) {
            addCriterion("hr_file_path <>", value, "hrFilePath");
            return (Criteria) this;
        }

        public Criteria andHrFilePathGreaterThan(String value) {
            addCriterion("hr_file_path >", value, "hrFilePath");
            return (Criteria) this;
        }

        public Criteria andHrFilePathGreaterThanOrEqualTo(String value) {
            addCriterion("hr_file_path >=", value, "hrFilePath");
            return (Criteria) this;
        }

        public Criteria andHrFilePathLessThan(String value) {
            addCriterion("hr_file_path <", value, "hrFilePath");
            return (Criteria) this;
        }

        public Criteria andHrFilePathLessThanOrEqualTo(String value) {
            addCriterion("hr_file_path <=", value, "hrFilePath");
            return (Criteria) this;
        }

        public Criteria andHrFilePathLike(String value) {
            addCriterion("hr_file_path like", value, "hrFilePath");
            return (Criteria) this;
        }

        public Criteria andHrFilePathNotLike(String value) {
            addCriterion("hr_file_path not like", value, "hrFilePath");
            return (Criteria) this;
        }

        public Criteria andHrFilePathIn(List<String> values) {
            addCriterion("hr_file_path in", values, "hrFilePath");
            return (Criteria) this;
        }

        public Criteria andHrFilePathNotIn(List<String> values) {
            addCriterion("hr_file_path not in", values, "hrFilePath");
            return (Criteria) this;
        }

        public Criteria andHrFilePathBetween(String value1, String value2) {
            addCriterion("hr_file_path between", value1, value2, "hrFilePath");
            return (Criteria) this;
        }

        public Criteria andHrFilePathNotBetween(String value1, String value2) {
            addCriterion("hr_file_path not between", value1, value2, "hrFilePath");
            return (Criteria) this;
        }

        public Criteria andFeTypeIsNull() {
            addCriterion("fe_type is null");
            return (Criteria) this;
        }

        public Criteria andFeTypeIsNotNull() {
            addCriterion("fe_type is not null");
            return (Criteria) this;
        }

        public Criteria andFeTypeEqualTo(Integer value) {
            addCriterion("fe_type =", value, "feType");
            return (Criteria) this;
        }

        public Criteria andFeTypeNotEqualTo(Integer value) {
            addCriterion("fe_type <>", value, "feType");
            return (Criteria) this;
        }

        public Criteria andFeTypeGreaterThan(Integer value) {
            addCriterion("fe_type >", value, "feType");
            return (Criteria) this;
        }

        public Criteria andFeTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("fe_type >=", value, "feType");
            return (Criteria) this;
        }

        public Criteria andFeTypeLessThan(Integer value) {
            addCriterion("fe_type <", value, "feType");
            return (Criteria) this;
        }

        public Criteria andFeTypeLessThanOrEqualTo(Integer value) {
            addCriterion("fe_type <=", value, "feType");
            return (Criteria) this;
        }

        public Criteria andFeTypeIn(List<Integer> values) {
            addCriterion("fe_type in", values, "feType");
            return (Criteria) this;
        }

        public Criteria andFeTypeNotIn(List<Integer> values) {
            addCriterion("fe_type not in", values, "feType");
            return (Criteria) this;
        }

        public Criteria andFeTypeBetween(Integer value1, Integer value2) {
            addCriterion("fe_type between", value1, value2, "feType");
            return (Criteria) this;
        }

        public Criteria andFeTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("fe_type not between", value1, value2, "feType");
            return (Criteria) this;
        }

        public Criteria andFeNumIsNull() {
            addCriterion("fe_num is null");
            return (Criteria) this;
        }

        public Criteria andFeNumIsNotNull() {
            addCriterion("fe_num is not null");
            return (Criteria) this;
        }

        public Criteria andFeNumEqualTo(Integer value) {
            addCriterion("fe_num =", value, "feNum");
            return (Criteria) this;
        }

        public Criteria andFeNumNotEqualTo(Integer value) {
            addCriterion("fe_num <>", value, "feNum");
            return (Criteria) this;
        }

        public Criteria andFeNumGreaterThan(Integer value) {
            addCriterion("fe_num >", value, "feNum");
            return (Criteria) this;
        }

        public Criteria andFeNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("fe_num >=", value, "feNum");
            return (Criteria) this;
        }

        public Criteria andFeNumLessThan(Integer value) {
            addCriterion("fe_num <", value, "feNum");
            return (Criteria) this;
        }

        public Criteria andFeNumLessThanOrEqualTo(Integer value) {
            addCriterion("fe_num <=", value, "feNum");
            return (Criteria) this;
        }

        public Criteria andFeNumIn(List<Integer> values) {
            addCriterion("fe_num in", values, "feNum");
            return (Criteria) this;
        }

        public Criteria andFeNumNotIn(List<Integer> values) {
            addCriterion("fe_num not in", values, "feNum");
            return (Criteria) this;
        }

        public Criteria andFeNumBetween(Integer value1, Integer value2) {
            addCriterion("fe_num between", value1, value2, "feNum");
            return (Criteria) this;
        }

        public Criteria andFeNumNotBetween(Integer value1, Integer value2) {
            addCriterion("fe_num not between", value1, value2, "feNum");
            return (Criteria) this;
        }

        public Criteria andFeFilePathIsNull() {
            addCriterion("fe_file_path is null");
            return (Criteria) this;
        }

        public Criteria andFeFilePathIsNotNull() {
            addCriterion("fe_file_path is not null");
            return (Criteria) this;
        }

        public Criteria andFeFilePathEqualTo(String value) {
            addCriterion("fe_file_path =", value, "feFilePath");
            return (Criteria) this;
        }

        public Criteria andFeFilePathNotEqualTo(String value) {
            addCriterion("fe_file_path <>", value, "feFilePath");
            return (Criteria) this;
        }

        public Criteria andFeFilePathGreaterThan(String value) {
            addCriterion("fe_file_path >", value, "feFilePath");
            return (Criteria) this;
        }

        public Criteria andFeFilePathGreaterThanOrEqualTo(String value) {
            addCriterion("fe_file_path >=", value, "feFilePath");
            return (Criteria) this;
        }

        public Criteria andFeFilePathLessThan(String value) {
            addCriterion("fe_file_path <", value, "feFilePath");
            return (Criteria) this;
        }

        public Criteria andFeFilePathLessThanOrEqualTo(String value) {
            addCriterion("fe_file_path <=", value, "feFilePath");
            return (Criteria) this;
        }

        public Criteria andFeFilePathLike(String value) {
            addCriterion("fe_file_path like", value, "feFilePath");
            return (Criteria) this;
        }

        public Criteria andFeFilePathNotLike(String value) {
            addCriterion("fe_file_path not like", value, "feFilePath");
            return (Criteria) this;
        }

        public Criteria andFeFilePathIn(List<String> values) {
            addCriterion("fe_file_path in", values, "feFilePath");
            return (Criteria) this;
        }

        public Criteria andFeFilePathNotIn(List<String> values) {
            addCriterion("fe_file_path not in", values, "feFilePath");
            return (Criteria) this;
        }

        public Criteria andFeFilePathBetween(String value1, String value2) {
            addCriterion("fe_file_path between", value1, value2, "feFilePath");
            return (Criteria) this;
        }

        public Criteria andFeFilePathNotBetween(String value1, String value2) {
            addCriterion("fe_file_path not between", value1, value2, "feFilePath");
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