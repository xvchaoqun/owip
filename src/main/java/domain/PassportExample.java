package domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class PassportExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public PassportExample() {
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

        public Criteria andApplyIdIsNull() {
            addCriterion("apply_id is null");
            return (Criteria) this;
        }

        public Criteria andApplyIdIsNotNull() {
            addCriterion("apply_id is not null");
            return (Criteria) this;
        }

        public Criteria andApplyIdEqualTo(Integer value) {
            addCriterion("apply_id =", value, "applyId");
            return (Criteria) this;
        }

        public Criteria andApplyIdNotEqualTo(Integer value) {
            addCriterion("apply_id <>", value, "applyId");
            return (Criteria) this;
        }

        public Criteria andApplyIdGreaterThan(Integer value) {
            addCriterion("apply_id >", value, "applyId");
            return (Criteria) this;
        }

        public Criteria andApplyIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("apply_id >=", value, "applyId");
            return (Criteria) this;
        }

        public Criteria andApplyIdLessThan(Integer value) {
            addCriterion("apply_id <", value, "applyId");
            return (Criteria) this;
        }

        public Criteria andApplyIdLessThanOrEqualTo(Integer value) {
            addCriterion("apply_id <=", value, "applyId");
            return (Criteria) this;
        }

        public Criteria andApplyIdIn(List<Integer> values) {
            addCriterion("apply_id in", values, "applyId");
            return (Criteria) this;
        }

        public Criteria andApplyIdNotIn(List<Integer> values) {
            addCriterion("apply_id not in", values, "applyId");
            return (Criteria) this;
        }

        public Criteria andApplyIdBetween(Integer value1, Integer value2) {
            addCriterion("apply_id between", value1, value2, "applyId");
            return (Criteria) this;
        }

        public Criteria andApplyIdNotBetween(Integer value1, Integer value2) {
            addCriterion("apply_id not between", value1, value2, "applyId");
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

        public Criteria andClassIdIsNull() {
            addCriterion("class_id is null");
            return (Criteria) this;
        }

        public Criteria andClassIdIsNotNull() {
            addCriterion("class_id is not null");
            return (Criteria) this;
        }

        public Criteria andClassIdEqualTo(Integer value) {
            addCriterion("class_id =", value, "classId");
            return (Criteria) this;
        }

        public Criteria andClassIdNotEqualTo(Integer value) {
            addCriterion("class_id <>", value, "classId");
            return (Criteria) this;
        }

        public Criteria andClassIdGreaterThan(Integer value) {
            addCriterion("class_id >", value, "classId");
            return (Criteria) this;
        }

        public Criteria andClassIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("class_id >=", value, "classId");
            return (Criteria) this;
        }

        public Criteria andClassIdLessThan(Integer value) {
            addCriterion("class_id <", value, "classId");
            return (Criteria) this;
        }

        public Criteria andClassIdLessThanOrEqualTo(Integer value) {
            addCriterion("class_id <=", value, "classId");
            return (Criteria) this;
        }

        public Criteria andClassIdIn(List<Integer> values) {
            addCriterion("class_id in", values, "classId");
            return (Criteria) this;
        }

        public Criteria andClassIdNotIn(List<Integer> values) {
            addCriterion("class_id not in", values, "classId");
            return (Criteria) this;
        }

        public Criteria andClassIdBetween(Integer value1, Integer value2) {
            addCriterion("class_id between", value1, value2, "classId");
            return (Criteria) this;
        }

        public Criteria andClassIdNotBetween(Integer value1, Integer value2) {
            addCriterion("class_id not between", value1, value2, "classId");
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

        public Criteria andAuthorityIsNull() {
            addCriterion("authority is null");
            return (Criteria) this;
        }

        public Criteria andAuthorityIsNotNull() {
            addCriterion("authority is not null");
            return (Criteria) this;
        }

        public Criteria andAuthorityEqualTo(String value) {
            addCriterion("authority =", value, "authority");
            return (Criteria) this;
        }

        public Criteria andAuthorityNotEqualTo(String value) {
            addCriterion("authority <>", value, "authority");
            return (Criteria) this;
        }

        public Criteria andAuthorityGreaterThan(String value) {
            addCriterion("authority >", value, "authority");
            return (Criteria) this;
        }

        public Criteria andAuthorityGreaterThanOrEqualTo(String value) {
            addCriterion("authority >=", value, "authority");
            return (Criteria) this;
        }

        public Criteria andAuthorityLessThan(String value) {
            addCriterion("authority <", value, "authority");
            return (Criteria) this;
        }

        public Criteria andAuthorityLessThanOrEqualTo(String value) {
            addCriterion("authority <=", value, "authority");
            return (Criteria) this;
        }

        public Criteria andAuthorityLike(String value) {
            addCriterion("authority like", value, "authority");
            return (Criteria) this;
        }

        public Criteria andAuthorityNotLike(String value) {
            addCriterion("authority not like", value, "authority");
            return (Criteria) this;
        }

        public Criteria andAuthorityIn(List<String> values) {
            addCriterion("authority in", values, "authority");
            return (Criteria) this;
        }

        public Criteria andAuthorityNotIn(List<String> values) {
            addCriterion("authority not in", values, "authority");
            return (Criteria) this;
        }

        public Criteria andAuthorityBetween(String value1, String value2) {
            addCriterion("authority between", value1, value2, "authority");
            return (Criteria) this;
        }

        public Criteria andAuthorityNotBetween(String value1, String value2) {
            addCriterion("authority not between", value1, value2, "authority");
            return (Criteria) this;
        }

        public Criteria andIssueDateIsNull() {
            addCriterion("issue_date is null");
            return (Criteria) this;
        }

        public Criteria andIssueDateIsNotNull() {
            addCriterion("issue_date is not null");
            return (Criteria) this;
        }

        public Criteria andIssueDateEqualTo(Date value) {
            addCriterionForJDBCDate("issue_date =", value, "issueDate");
            return (Criteria) this;
        }

        public Criteria andIssueDateNotEqualTo(Date value) {
            addCriterionForJDBCDate("issue_date <>", value, "issueDate");
            return (Criteria) this;
        }

        public Criteria andIssueDateGreaterThan(Date value) {
            addCriterionForJDBCDate("issue_date >", value, "issueDate");
            return (Criteria) this;
        }

        public Criteria andIssueDateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("issue_date >=", value, "issueDate");
            return (Criteria) this;
        }

        public Criteria andIssueDateLessThan(Date value) {
            addCriterionForJDBCDate("issue_date <", value, "issueDate");
            return (Criteria) this;
        }

        public Criteria andIssueDateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("issue_date <=", value, "issueDate");
            return (Criteria) this;
        }

        public Criteria andIssueDateIn(List<Date> values) {
            addCriterionForJDBCDate("issue_date in", values, "issueDate");
            return (Criteria) this;
        }

        public Criteria andIssueDateNotIn(List<Date> values) {
            addCriterionForJDBCDate("issue_date not in", values, "issueDate");
            return (Criteria) this;
        }

        public Criteria andIssueDateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("issue_date between", value1, value2, "issueDate");
            return (Criteria) this;
        }

        public Criteria andIssueDateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("issue_date not between", value1, value2, "issueDate");
            return (Criteria) this;
        }

        public Criteria andExpiryDateIsNull() {
            addCriterion("expiry_date is null");
            return (Criteria) this;
        }

        public Criteria andExpiryDateIsNotNull() {
            addCriterion("expiry_date is not null");
            return (Criteria) this;
        }

        public Criteria andExpiryDateEqualTo(Date value) {
            addCriterionForJDBCDate("expiry_date =", value, "expiryDate");
            return (Criteria) this;
        }

        public Criteria andExpiryDateNotEqualTo(Date value) {
            addCriterionForJDBCDate("expiry_date <>", value, "expiryDate");
            return (Criteria) this;
        }

        public Criteria andExpiryDateGreaterThan(Date value) {
            addCriterionForJDBCDate("expiry_date >", value, "expiryDate");
            return (Criteria) this;
        }

        public Criteria andExpiryDateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("expiry_date >=", value, "expiryDate");
            return (Criteria) this;
        }

        public Criteria andExpiryDateLessThan(Date value) {
            addCriterionForJDBCDate("expiry_date <", value, "expiryDate");
            return (Criteria) this;
        }

        public Criteria andExpiryDateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("expiry_date <=", value, "expiryDate");
            return (Criteria) this;
        }

        public Criteria andExpiryDateIn(List<Date> values) {
            addCriterionForJDBCDate("expiry_date in", values, "expiryDate");
            return (Criteria) this;
        }

        public Criteria andExpiryDateNotIn(List<Date> values) {
            addCriterionForJDBCDate("expiry_date not in", values, "expiryDate");
            return (Criteria) this;
        }

        public Criteria andExpiryDateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("expiry_date between", value1, value2, "expiryDate");
            return (Criteria) this;
        }

        public Criteria andExpiryDateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("expiry_date not between", value1, value2, "expiryDate");
            return (Criteria) this;
        }

        public Criteria andKeepDateIsNull() {
            addCriterion("keep_date is null");
            return (Criteria) this;
        }

        public Criteria andKeepDateIsNotNull() {
            addCriterion("keep_date is not null");
            return (Criteria) this;
        }

        public Criteria andKeepDateEqualTo(Date value) {
            addCriterionForJDBCDate("keep_date =", value, "keepDate");
            return (Criteria) this;
        }

        public Criteria andKeepDateNotEqualTo(Date value) {
            addCriterionForJDBCDate("keep_date <>", value, "keepDate");
            return (Criteria) this;
        }

        public Criteria andKeepDateGreaterThan(Date value) {
            addCriterionForJDBCDate("keep_date >", value, "keepDate");
            return (Criteria) this;
        }

        public Criteria andKeepDateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("keep_date >=", value, "keepDate");
            return (Criteria) this;
        }

        public Criteria andKeepDateLessThan(Date value) {
            addCriterionForJDBCDate("keep_date <", value, "keepDate");
            return (Criteria) this;
        }

        public Criteria andKeepDateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("keep_date <=", value, "keepDate");
            return (Criteria) this;
        }

        public Criteria andKeepDateIn(List<Date> values) {
            addCriterionForJDBCDate("keep_date in", values, "keepDate");
            return (Criteria) this;
        }

        public Criteria andKeepDateNotIn(List<Date> values) {
            addCriterionForJDBCDate("keep_date not in", values, "keepDate");
            return (Criteria) this;
        }

        public Criteria andKeepDateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("keep_date between", value1, value2, "keepDate");
            return (Criteria) this;
        }

        public Criteria andKeepDateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("keep_date not between", value1, value2, "keepDate");
            return (Criteria) this;
        }

        public Criteria andSafeBoxIdIsNull() {
            addCriterion("safe_box_id is null");
            return (Criteria) this;
        }

        public Criteria andSafeBoxIdIsNotNull() {
            addCriterion("safe_box_id is not null");
            return (Criteria) this;
        }

        public Criteria andSafeBoxIdEqualTo(Integer value) {
            addCriterion("safe_box_id =", value, "safeBoxId");
            return (Criteria) this;
        }

        public Criteria andSafeBoxIdNotEqualTo(Integer value) {
            addCriterion("safe_box_id <>", value, "safeBoxId");
            return (Criteria) this;
        }

        public Criteria andSafeBoxIdGreaterThan(Integer value) {
            addCriterion("safe_box_id >", value, "safeBoxId");
            return (Criteria) this;
        }

        public Criteria andSafeBoxIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("safe_box_id >=", value, "safeBoxId");
            return (Criteria) this;
        }

        public Criteria andSafeBoxIdLessThan(Integer value) {
            addCriterion("safe_box_id <", value, "safeBoxId");
            return (Criteria) this;
        }

        public Criteria andSafeBoxIdLessThanOrEqualTo(Integer value) {
            addCriterion("safe_box_id <=", value, "safeBoxId");
            return (Criteria) this;
        }

        public Criteria andSafeBoxIdIn(List<Integer> values) {
            addCriterion("safe_box_id in", values, "safeBoxId");
            return (Criteria) this;
        }

        public Criteria andSafeBoxIdNotIn(List<Integer> values) {
            addCriterion("safe_box_id not in", values, "safeBoxId");
            return (Criteria) this;
        }

        public Criteria andSafeBoxIdBetween(Integer value1, Integer value2) {
            addCriterion("safe_box_id between", value1, value2, "safeBoxId");
            return (Criteria) this;
        }

        public Criteria andSafeBoxIdNotBetween(Integer value1, Integer value2) {
            addCriterion("safe_box_id not between", value1, value2, "safeBoxId");
            return (Criteria) this;
        }

        public Criteria andIsLentIsNull() {
            addCriterion("is_lent is null");
            return (Criteria) this;
        }

        public Criteria andIsLentIsNotNull() {
            addCriterion("is_lent is not null");
            return (Criteria) this;
        }

        public Criteria andIsLentEqualTo(Boolean value) {
            addCriterion("is_lent =", value, "isLent");
            return (Criteria) this;
        }

        public Criteria andIsLentNotEqualTo(Boolean value) {
            addCriterion("is_lent <>", value, "isLent");
            return (Criteria) this;
        }

        public Criteria andIsLentGreaterThan(Boolean value) {
            addCriterion("is_lent >", value, "isLent");
            return (Criteria) this;
        }

        public Criteria andIsLentGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_lent >=", value, "isLent");
            return (Criteria) this;
        }

        public Criteria andIsLentLessThan(Boolean value) {
            addCriterion("is_lent <", value, "isLent");
            return (Criteria) this;
        }

        public Criteria andIsLentLessThanOrEqualTo(Boolean value) {
            addCriterion("is_lent <=", value, "isLent");
            return (Criteria) this;
        }

        public Criteria andIsLentIn(List<Boolean> values) {
            addCriterion("is_lent in", values, "isLent");
            return (Criteria) this;
        }

        public Criteria andIsLentNotIn(List<Boolean> values) {
            addCriterion("is_lent not in", values, "isLent");
            return (Criteria) this;
        }

        public Criteria andIsLentBetween(Boolean value1, Boolean value2) {
            addCriterion("is_lent between", value1, value2, "isLent");
            return (Criteria) this;
        }

        public Criteria andIsLentNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_lent not between", value1, value2, "isLent");
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

        public Criteria andCancelTypeIsNull() {
            addCriterion("cancel_type is null");
            return (Criteria) this;
        }

        public Criteria andCancelTypeIsNotNull() {
            addCriterion("cancel_type is not null");
            return (Criteria) this;
        }

        public Criteria andCancelTypeEqualTo(Byte value) {
            addCriterion("cancel_type =", value, "cancelType");
            return (Criteria) this;
        }

        public Criteria andCancelTypeNotEqualTo(Byte value) {
            addCriterion("cancel_type <>", value, "cancelType");
            return (Criteria) this;
        }

        public Criteria andCancelTypeGreaterThan(Byte value) {
            addCriterion("cancel_type >", value, "cancelType");
            return (Criteria) this;
        }

        public Criteria andCancelTypeGreaterThanOrEqualTo(Byte value) {
            addCriterion("cancel_type >=", value, "cancelType");
            return (Criteria) this;
        }

        public Criteria andCancelTypeLessThan(Byte value) {
            addCriterion("cancel_type <", value, "cancelType");
            return (Criteria) this;
        }

        public Criteria andCancelTypeLessThanOrEqualTo(Byte value) {
            addCriterion("cancel_type <=", value, "cancelType");
            return (Criteria) this;
        }

        public Criteria andCancelTypeIn(List<Byte> values) {
            addCriterion("cancel_type in", values, "cancelType");
            return (Criteria) this;
        }

        public Criteria andCancelTypeNotIn(List<Byte> values) {
            addCriterion("cancel_type not in", values, "cancelType");
            return (Criteria) this;
        }

        public Criteria andCancelTypeBetween(Byte value1, Byte value2) {
            addCriterion("cancel_type between", value1, value2, "cancelType");
            return (Criteria) this;
        }

        public Criteria andCancelTypeNotBetween(Byte value1, Byte value2) {
            addCriterion("cancel_type not between", value1, value2, "cancelType");
            return (Criteria) this;
        }

        public Criteria andCancelConfirmIsNull() {
            addCriterion("cancel_confirm is null");
            return (Criteria) this;
        }

        public Criteria andCancelConfirmIsNotNull() {
            addCriterion("cancel_confirm is not null");
            return (Criteria) this;
        }

        public Criteria andCancelConfirmEqualTo(Boolean value) {
            addCriterion("cancel_confirm =", value, "cancelConfirm");
            return (Criteria) this;
        }

        public Criteria andCancelConfirmNotEqualTo(Boolean value) {
            addCriterion("cancel_confirm <>", value, "cancelConfirm");
            return (Criteria) this;
        }

        public Criteria andCancelConfirmGreaterThan(Boolean value) {
            addCriterion("cancel_confirm >", value, "cancelConfirm");
            return (Criteria) this;
        }

        public Criteria andCancelConfirmGreaterThanOrEqualTo(Boolean value) {
            addCriterion("cancel_confirm >=", value, "cancelConfirm");
            return (Criteria) this;
        }

        public Criteria andCancelConfirmLessThan(Boolean value) {
            addCriterion("cancel_confirm <", value, "cancelConfirm");
            return (Criteria) this;
        }

        public Criteria andCancelConfirmLessThanOrEqualTo(Boolean value) {
            addCriterion("cancel_confirm <=", value, "cancelConfirm");
            return (Criteria) this;
        }

        public Criteria andCancelConfirmIn(List<Boolean> values) {
            addCriterion("cancel_confirm in", values, "cancelConfirm");
            return (Criteria) this;
        }

        public Criteria andCancelConfirmNotIn(List<Boolean> values) {
            addCriterion("cancel_confirm not in", values, "cancelConfirm");
            return (Criteria) this;
        }

        public Criteria andCancelConfirmBetween(Boolean value1, Boolean value2) {
            addCriterion("cancel_confirm between", value1, value2, "cancelConfirm");
            return (Criteria) this;
        }

        public Criteria andCancelConfirmNotBetween(Boolean value1, Boolean value2) {
            addCriterion("cancel_confirm not between", value1, value2, "cancelConfirm");
            return (Criteria) this;
        }

        public Criteria andCancelPicIsNull() {
            addCriterion("cancel_pic is null");
            return (Criteria) this;
        }

        public Criteria andCancelPicIsNotNull() {
            addCriterion("cancel_pic is not null");
            return (Criteria) this;
        }

        public Criteria andCancelPicEqualTo(String value) {
            addCriterion("cancel_pic =", value, "cancelPic");
            return (Criteria) this;
        }

        public Criteria andCancelPicNotEqualTo(String value) {
            addCriterion("cancel_pic <>", value, "cancelPic");
            return (Criteria) this;
        }

        public Criteria andCancelPicGreaterThan(String value) {
            addCriterion("cancel_pic >", value, "cancelPic");
            return (Criteria) this;
        }

        public Criteria andCancelPicGreaterThanOrEqualTo(String value) {
            addCriterion("cancel_pic >=", value, "cancelPic");
            return (Criteria) this;
        }

        public Criteria andCancelPicLessThan(String value) {
            addCriterion("cancel_pic <", value, "cancelPic");
            return (Criteria) this;
        }

        public Criteria andCancelPicLessThanOrEqualTo(String value) {
            addCriterion("cancel_pic <=", value, "cancelPic");
            return (Criteria) this;
        }

        public Criteria andCancelPicLike(String value) {
            addCriterion("cancel_pic like", value, "cancelPic");
            return (Criteria) this;
        }

        public Criteria andCancelPicNotLike(String value) {
            addCriterion("cancel_pic not like", value, "cancelPic");
            return (Criteria) this;
        }

        public Criteria andCancelPicIn(List<String> values) {
            addCriterion("cancel_pic in", values, "cancelPic");
            return (Criteria) this;
        }

        public Criteria andCancelPicNotIn(List<String> values) {
            addCriterion("cancel_pic not in", values, "cancelPic");
            return (Criteria) this;
        }

        public Criteria andCancelPicBetween(String value1, String value2) {
            addCriterion("cancel_pic between", value1, value2, "cancelPic");
            return (Criteria) this;
        }

        public Criteria andCancelPicNotBetween(String value1, String value2) {
            addCriterion("cancel_pic not between", value1, value2, "cancelPic");
            return (Criteria) this;
        }

        public Criteria andCancelTimeIsNull() {
            addCriterion("cancel_time is null");
            return (Criteria) this;
        }

        public Criteria andCancelTimeIsNotNull() {
            addCriterion("cancel_time is not null");
            return (Criteria) this;
        }

        public Criteria andCancelTimeEqualTo(Date value) {
            addCriterion("cancel_time =", value, "cancelTime");
            return (Criteria) this;
        }

        public Criteria andCancelTimeNotEqualTo(Date value) {
            addCriterion("cancel_time <>", value, "cancelTime");
            return (Criteria) this;
        }

        public Criteria andCancelTimeGreaterThan(Date value) {
            addCriterion("cancel_time >", value, "cancelTime");
            return (Criteria) this;
        }

        public Criteria andCancelTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("cancel_time >=", value, "cancelTime");
            return (Criteria) this;
        }

        public Criteria andCancelTimeLessThan(Date value) {
            addCriterion("cancel_time <", value, "cancelTime");
            return (Criteria) this;
        }

        public Criteria andCancelTimeLessThanOrEqualTo(Date value) {
            addCriterion("cancel_time <=", value, "cancelTime");
            return (Criteria) this;
        }

        public Criteria andCancelTimeIn(List<Date> values) {
            addCriterion("cancel_time in", values, "cancelTime");
            return (Criteria) this;
        }

        public Criteria andCancelTimeNotIn(List<Date> values) {
            addCriterion("cancel_time not in", values, "cancelTime");
            return (Criteria) this;
        }

        public Criteria andCancelTimeBetween(Date value1, Date value2) {
            addCriterion("cancel_time between", value1, value2, "cancelTime");
            return (Criteria) this;
        }

        public Criteria andCancelTimeNotBetween(Date value1, Date value2) {
            addCriterion("cancel_time not between", value1, value2, "cancelTime");
            return (Criteria) this;
        }

        public Criteria andLostTimeIsNull() {
            addCriterion("lost_time is null");
            return (Criteria) this;
        }

        public Criteria andLostTimeIsNotNull() {
            addCriterion("lost_time is not null");
            return (Criteria) this;
        }

        public Criteria andLostTimeEqualTo(Date value) {
            addCriterion("lost_time =", value, "lostTime");
            return (Criteria) this;
        }

        public Criteria andLostTimeNotEqualTo(Date value) {
            addCriterion("lost_time <>", value, "lostTime");
            return (Criteria) this;
        }

        public Criteria andLostTimeGreaterThan(Date value) {
            addCriterion("lost_time >", value, "lostTime");
            return (Criteria) this;
        }

        public Criteria andLostTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("lost_time >=", value, "lostTime");
            return (Criteria) this;
        }

        public Criteria andLostTimeLessThan(Date value) {
            addCriterion("lost_time <", value, "lostTime");
            return (Criteria) this;
        }

        public Criteria andLostTimeLessThanOrEqualTo(Date value) {
            addCriterion("lost_time <=", value, "lostTime");
            return (Criteria) this;
        }

        public Criteria andLostTimeIn(List<Date> values) {
            addCriterion("lost_time in", values, "lostTime");
            return (Criteria) this;
        }

        public Criteria andLostTimeNotIn(List<Date> values) {
            addCriterion("lost_time not in", values, "lostTime");
            return (Criteria) this;
        }

        public Criteria andLostTimeBetween(Date value1, Date value2) {
            addCriterion("lost_time between", value1, value2, "lostTime");
            return (Criteria) this;
        }

        public Criteria andLostTimeNotBetween(Date value1, Date value2) {
            addCriterion("lost_time not between", value1, value2, "lostTime");
            return (Criteria) this;
        }

        public Criteria andLostProofIsNull() {
            addCriterion("lost_proof is null");
            return (Criteria) this;
        }

        public Criteria andLostProofIsNotNull() {
            addCriterion("lost_proof is not null");
            return (Criteria) this;
        }

        public Criteria andLostProofEqualTo(String value) {
            addCriterion("lost_proof =", value, "lostProof");
            return (Criteria) this;
        }

        public Criteria andLostProofNotEqualTo(String value) {
            addCriterion("lost_proof <>", value, "lostProof");
            return (Criteria) this;
        }

        public Criteria andLostProofGreaterThan(String value) {
            addCriterion("lost_proof >", value, "lostProof");
            return (Criteria) this;
        }

        public Criteria andLostProofGreaterThanOrEqualTo(String value) {
            addCriterion("lost_proof >=", value, "lostProof");
            return (Criteria) this;
        }

        public Criteria andLostProofLessThan(String value) {
            addCriterion("lost_proof <", value, "lostProof");
            return (Criteria) this;
        }

        public Criteria andLostProofLessThanOrEqualTo(String value) {
            addCriterion("lost_proof <=", value, "lostProof");
            return (Criteria) this;
        }

        public Criteria andLostProofLike(String value) {
            addCriterion("lost_proof like", value, "lostProof");
            return (Criteria) this;
        }

        public Criteria andLostProofNotLike(String value) {
            addCriterion("lost_proof not like", value, "lostProof");
            return (Criteria) this;
        }

        public Criteria andLostProofIn(List<String> values) {
            addCriterion("lost_proof in", values, "lostProof");
            return (Criteria) this;
        }

        public Criteria andLostProofNotIn(List<String> values) {
            addCriterion("lost_proof not in", values, "lostProof");
            return (Criteria) this;
        }

        public Criteria andLostProofBetween(String value1, String value2) {
            addCriterion("lost_proof between", value1, value2, "lostProof");
            return (Criteria) this;
        }

        public Criteria andLostProofNotBetween(String value1, String value2) {
            addCriterion("lost_proof not between", value1, value2, "lostProof");
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