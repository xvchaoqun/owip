package domain.sc.scAd;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class ScAdUseExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public ScAdUseExample() {
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

        public Criteria andUseDateIsNull() {
            addCriterion("use_date is null");
            return (Criteria) this;
        }

        public Criteria andUseDateIsNotNull() {
            addCriterion("use_date is not null");
            return (Criteria) this;
        }

        public Criteria andUseDateEqualTo(Date value) {
            addCriterionForJDBCDate("use_date =", value, "useDate");
            return (Criteria) this;
        }

        public Criteria andUseDateNotEqualTo(Date value) {
            addCriterionForJDBCDate("use_date <>", value, "useDate");
            return (Criteria) this;
        }

        public Criteria andUseDateGreaterThan(Date value) {
            addCriterionForJDBCDate("use_date >", value, "useDate");
            return (Criteria) this;
        }

        public Criteria andUseDateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("use_date >=", value, "useDate");
            return (Criteria) this;
        }

        public Criteria andUseDateLessThan(Date value) {
            addCriterionForJDBCDate("use_date <", value, "useDate");
            return (Criteria) this;
        }

        public Criteria andUseDateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("use_date <=", value, "useDate");
            return (Criteria) this;
        }

        public Criteria andUseDateIn(List<Date> values) {
            addCriterionForJDBCDate("use_date in", values, "useDate");
            return (Criteria) this;
        }

        public Criteria andUseDateNotIn(List<Date> values) {
            addCriterionForJDBCDate("use_date not in", values, "useDate");
            return (Criteria) this;
        }

        public Criteria andUseDateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("use_date between", value1, value2, "useDate");
            return (Criteria) this;
        }

        public Criteria andUseDateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("use_date not between", value1, value2, "useDate");
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

        public Criteria andOutUnitIsNull() {
            addCriterion("out_unit is null");
            return (Criteria) this;
        }

        public Criteria andOutUnitIsNotNull() {
            addCriterion("out_unit is not null");
            return (Criteria) this;
        }

        public Criteria andOutUnitEqualTo(String value) {
            addCriterion("out_unit =", value, "outUnit");
            return (Criteria) this;
        }

        public Criteria andOutUnitNotEqualTo(String value) {
            addCriterion("out_unit <>", value, "outUnit");
            return (Criteria) this;
        }

        public Criteria andOutUnitGreaterThan(String value) {
            addCriterion("out_unit >", value, "outUnit");
            return (Criteria) this;
        }

        public Criteria andOutUnitGreaterThanOrEqualTo(String value) {
            addCriterion("out_unit >=", value, "outUnit");
            return (Criteria) this;
        }

        public Criteria andOutUnitLessThan(String value) {
            addCriterion("out_unit <", value, "outUnit");
            return (Criteria) this;
        }

        public Criteria andOutUnitLessThanOrEqualTo(String value) {
            addCriterion("out_unit <=", value, "outUnit");
            return (Criteria) this;
        }

        public Criteria andOutUnitLike(String value) {
            addCriterion("out_unit like", value, "outUnit");
            return (Criteria) this;
        }

        public Criteria andOutUnitNotLike(String value) {
            addCriterion("out_unit not like", value, "outUnit");
            return (Criteria) this;
        }

        public Criteria andOutUnitIn(List<String> values) {
            addCriterion("out_unit in", values, "outUnit");
            return (Criteria) this;
        }

        public Criteria andOutUnitNotIn(List<String> values) {
            addCriterion("out_unit not in", values, "outUnit");
            return (Criteria) this;
        }

        public Criteria andOutUnitBetween(String value1, String value2) {
            addCriterion("out_unit between", value1, value2, "outUnit");
            return (Criteria) this;
        }

        public Criteria andOutUnitNotBetween(String value1, String value2) {
            addCriterion("out_unit not between", value1, value2, "outUnit");
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

        public Criteria andIsAdformSavedIsNull() {
            addCriterion("is_adform_saved is null");
            return (Criteria) this;
        }

        public Criteria andIsAdformSavedIsNotNull() {
            addCriterion("is_adform_saved is not null");
            return (Criteria) this;
        }

        public Criteria andIsAdformSavedEqualTo(Boolean value) {
            addCriterion("is_adform_saved =", value, "isAdformSaved");
            return (Criteria) this;
        }

        public Criteria andIsAdformSavedNotEqualTo(Boolean value) {
            addCriterion("is_adform_saved <>", value, "isAdformSaved");
            return (Criteria) this;
        }

        public Criteria andIsAdformSavedGreaterThan(Boolean value) {
            addCriterion("is_adform_saved >", value, "isAdformSaved");
            return (Criteria) this;
        }

        public Criteria andIsAdformSavedGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_adform_saved >=", value, "isAdformSaved");
            return (Criteria) this;
        }

        public Criteria andIsAdformSavedLessThan(Boolean value) {
            addCriterion("is_adform_saved <", value, "isAdformSaved");
            return (Criteria) this;
        }

        public Criteria andIsAdformSavedLessThanOrEqualTo(Boolean value) {
            addCriterion("is_adform_saved <=", value, "isAdformSaved");
            return (Criteria) this;
        }

        public Criteria andIsAdformSavedIn(List<Boolean> values) {
            addCriterion("is_adform_saved in", values, "isAdformSaved");
            return (Criteria) this;
        }

        public Criteria andIsAdformSavedNotIn(List<Boolean> values) {
            addCriterion("is_adform_saved not in", values, "isAdformSaved");
            return (Criteria) this;
        }

        public Criteria andIsAdformSavedBetween(Boolean value1, Boolean value2) {
            addCriterion("is_adform_saved between", value1, value2, "isAdformSaved");
            return (Criteria) this;
        }

        public Criteria andIsAdformSavedNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_adform_saved not between", value1, value2, "isAdformSaved");
            return (Criteria) this;
        }

        public Criteria andFilePathIsNull() {
            addCriterion("file_path is null");
            return (Criteria) this;
        }

        public Criteria andFilePathIsNotNull() {
            addCriterion("file_path is not null");
            return (Criteria) this;
        }

        public Criteria andFilePathEqualTo(String value) {
            addCriterion("file_path =", value, "filePath");
            return (Criteria) this;
        }

        public Criteria andFilePathNotEqualTo(String value) {
            addCriterion("file_path <>", value, "filePath");
            return (Criteria) this;
        }

        public Criteria andFilePathGreaterThan(String value) {
            addCriterion("file_path >", value, "filePath");
            return (Criteria) this;
        }

        public Criteria andFilePathGreaterThanOrEqualTo(String value) {
            addCriterion("file_path >=", value, "filePath");
            return (Criteria) this;
        }

        public Criteria andFilePathLessThan(String value) {
            addCriterion("file_path <", value, "filePath");
            return (Criteria) this;
        }

        public Criteria andFilePathLessThanOrEqualTo(String value) {
            addCriterion("file_path <=", value, "filePath");
            return (Criteria) this;
        }

        public Criteria andFilePathLike(String value) {
            addCriterion("file_path like", value, "filePath");
            return (Criteria) this;
        }

        public Criteria andFilePathNotLike(String value) {
            addCriterion("file_path not like", value, "filePath");
            return (Criteria) this;
        }

        public Criteria andFilePathIn(List<String> values) {
            addCriterion("file_path in", values, "filePath");
            return (Criteria) this;
        }

        public Criteria andFilePathNotIn(List<String> values) {
            addCriterion("file_path not in", values, "filePath");
            return (Criteria) this;
        }

        public Criteria andFilePathBetween(String value1, String value2) {
            addCriterion("file_path between", value1, value2, "filePath");
            return (Criteria) this;
        }

        public Criteria andFilePathNotBetween(String value1, String value2) {
            addCriterion("file_path not between", value1, value2, "filePath");
            return (Criteria) this;
        }

        public Criteria andSignFilePathIsNull() {
            addCriterion("sign_file_path is null");
            return (Criteria) this;
        }

        public Criteria andSignFilePathIsNotNull() {
            addCriterion("sign_file_path is not null");
            return (Criteria) this;
        }

        public Criteria andSignFilePathEqualTo(String value) {
            addCriterion("sign_file_path =", value, "signFilePath");
            return (Criteria) this;
        }

        public Criteria andSignFilePathNotEqualTo(String value) {
            addCriterion("sign_file_path <>", value, "signFilePath");
            return (Criteria) this;
        }

        public Criteria andSignFilePathGreaterThan(String value) {
            addCriterion("sign_file_path >", value, "signFilePath");
            return (Criteria) this;
        }

        public Criteria andSignFilePathGreaterThanOrEqualTo(String value) {
            addCriterion("sign_file_path >=", value, "signFilePath");
            return (Criteria) this;
        }

        public Criteria andSignFilePathLessThan(String value) {
            addCriterion("sign_file_path <", value, "signFilePath");
            return (Criteria) this;
        }

        public Criteria andSignFilePathLessThanOrEqualTo(String value) {
            addCriterion("sign_file_path <=", value, "signFilePath");
            return (Criteria) this;
        }

        public Criteria andSignFilePathLike(String value) {
            addCriterion("sign_file_path like", value, "signFilePath");
            return (Criteria) this;
        }

        public Criteria andSignFilePathNotLike(String value) {
            addCriterion("sign_file_path not like", value, "signFilePath");
            return (Criteria) this;
        }

        public Criteria andSignFilePathIn(List<String> values) {
            addCriterion("sign_file_path in", values, "signFilePath");
            return (Criteria) this;
        }

        public Criteria andSignFilePathNotIn(List<String> values) {
            addCriterion("sign_file_path not in", values, "signFilePath");
            return (Criteria) this;
        }

        public Criteria andSignFilePathBetween(String value1, String value2) {
            addCriterion("sign_file_path between", value1, value2, "signFilePath");
            return (Criteria) this;
        }

        public Criteria andSignFilePathNotBetween(String value1, String value2) {
            addCriterion("sign_file_path not between", value1, value2, "signFilePath");
            return (Criteria) this;
        }

        public Criteria andUseageIsNull() {
            addCriterion("useage is null");
            return (Criteria) this;
        }

        public Criteria andUseageIsNotNull() {
            addCriterion("useage is not null");
            return (Criteria) this;
        }

        public Criteria andUseageEqualTo(String value) {
            addCriterion("useage =", value, "useage");
            return (Criteria) this;
        }

        public Criteria andUseageNotEqualTo(String value) {
            addCriterion("useage <>", value, "useage");
            return (Criteria) this;
        }

        public Criteria andUseageGreaterThan(String value) {
            addCriterion("useage >", value, "useage");
            return (Criteria) this;
        }

        public Criteria andUseageGreaterThanOrEqualTo(String value) {
            addCriterion("useage >=", value, "useage");
            return (Criteria) this;
        }

        public Criteria andUseageLessThan(String value) {
            addCriterion("useage <", value, "useage");
            return (Criteria) this;
        }

        public Criteria andUseageLessThanOrEqualTo(String value) {
            addCriterion("useage <=", value, "useage");
            return (Criteria) this;
        }

        public Criteria andUseageLike(String value) {
            addCriterion("useage like", value, "useage");
            return (Criteria) this;
        }

        public Criteria andUseageNotLike(String value) {
            addCriterion("useage not like", value, "useage");
            return (Criteria) this;
        }

        public Criteria andUseageIn(List<String> values) {
            addCriterion("useage in", values, "useage");
            return (Criteria) this;
        }

        public Criteria andUseageNotIn(List<String> values) {
            addCriterion("useage not in", values, "useage");
            return (Criteria) this;
        }

        public Criteria andUseageBetween(String value1, String value2) {
            addCriterion("useage between", value1, value2, "useage");
            return (Criteria) this;
        }

        public Criteria andUseageNotBetween(String value1, String value2) {
            addCriterion("useage not between", value1, value2, "useage");
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