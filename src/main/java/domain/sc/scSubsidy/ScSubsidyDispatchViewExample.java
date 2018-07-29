package domain.sc.scSubsidy;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class ScSubsidyDispatchViewExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public ScSubsidyDispatchViewExample() {
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