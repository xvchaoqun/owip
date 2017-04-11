package domain.verify;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class VerifyWorkTimeExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public VerifyWorkTimeExample() {
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

        public Criteria andOldWorkTimeIsNull() {
            addCriterion("old_work_time is null");
            return (Criteria) this;
        }

        public Criteria andOldWorkTimeIsNotNull() {
            addCriterion("old_work_time is not null");
            return (Criteria) this;
        }

        public Criteria andOldWorkTimeEqualTo(Date value) {
            addCriterionForJDBCDate("old_work_time =", value, "oldWorkTime");
            return (Criteria) this;
        }

        public Criteria andOldWorkTimeNotEqualTo(Date value) {
            addCriterionForJDBCDate("old_work_time <>", value, "oldWorkTime");
            return (Criteria) this;
        }

        public Criteria andOldWorkTimeGreaterThan(Date value) {
            addCriterionForJDBCDate("old_work_time >", value, "oldWorkTime");
            return (Criteria) this;
        }

        public Criteria andOldWorkTimeGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("old_work_time >=", value, "oldWorkTime");
            return (Criteria) this;
        }

        public Criteria andOldWorkTimeLessThan(Date value) {
            addCriterionForJDBCDate("old_work_time <", value, "oldWorkTime");
            return (Criteria) this;
        }

        public Criteria andOldWorkTimeLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("old_work_time <=", value, "oldWorkTime");
            return (Criteria) this;
        }

        public Criteria andOldWorkTimeIn(List<Date> values) {
            addCriterionForJDBCDate("old_work_time in", values, "oldWorkTime");
            return (Criteria) this;
        }

        public Criteria andOldWorkTimeNotIn(List<Date> values) {
            addCriterionForJDBCDate("old_work_time not in", values, "oldWorkTime");
            return (Criteria) this;
        }

        public Criteria andOldWorkTimeBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("old_work_time between", value1, value2, "oldWorkTime");
            return (Criteria) this;
        }

        public Criteria andOldWorkTimeNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("old_work_time not between", value1, value2, "oldWorkTime");
            return (Criteria) this;
        }

        public Criteria andVerifyWorkTimeIsNull() {
            addCriterion("verify_work_time is null");
            return (Criteria) this;
        }

        public Criteria andVerifyWorkTimeIsNotNull() {
            addCriterion("verify_work_time is not null");
            return (Criteria) this;
        }

        public Criteria andVerifyWorkTimeEqualTo(Date value) {
            addCriterionForJDBCDate("verify_work_time =", value, "verifyWorkTime");
            return (Criteria) this;
        }

        public Criteria andVerifyWorkTimeNotEqualTo(Date value) {
            addCriterionForJDBCDate("verify_work_time <>", value, "verifyWorkTime");
            return (Criteria) this;
        }

        public Criteria andVerifyWorkTimeGreaterThan(Date value) {
            addCriterionForJDBCDate("verify_work_time >", value, "verifyWorkTime");
            return (Criteria) this;
        }

        public Criteria andVerifyWorkTimeGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("verify_work_time >=", value, "verifyWorkTime");
            return (Criteria) this;
        }

        public Criteria andVerifyWorkTimeLessThan(Date value) {
            addCriterionForJDBCDate("verify_work_time <", value, "verifyWorkTime");
            return (Criteria) this;
        }

        public Criteria andVerifyWorkTimeLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("verify_work_time <=", value, "verifyWorkTime");
            return (Criteria) this;
        }

        public Criteria andVerifyWorkTimeIn(List<Date> values) {
            addCriterionForJDBCDate("verify_work_time in", values, "verifyWorkTime");
            return (Criteria) this;
        }

        public Criteria andVerifyWorkTimeNotIn(List<Date> values) {
            addCriterionForJDBCDate("verify_work_time not in", values, "verifyWorkTime");
            return (Criteria) this;
        }

        public Criteria andVerifyWorkTimeBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("verify_work_time between", value1, value2, "verifyWorkTime");
            return (Criteria) this;
        }

        public Criteria andVerifyWorkTimeNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("verify_work_time not between", value1, value2, "verifyWorkTime");
            return (Criteria) this;
        }

        public Criteria andMaterialNameIsNull() {
            addCriterion("material_name is null");
            return (Criteria) this;
        }

        public Criteria andMaterialNameIsNotNull() {
            addCriterion("material_name is not null");
            return (Criteria) this;
        }

        public Criteria andMaterialNameEqualTo(String value) {
            addCriterion("material_name =", value, "materialName");
            return (Criteria) this;
        }

        public Criteria andMaterialNameNotEqualTo(String value) {
            addCriterion("material_name <>", value, "materialName");
            return (Criteria) this;
        }

        public Criteria andMaterialNameGreaterThan(String value) {
            addCriterion("material_name >", value, "materialName");
            return (Criteria) this;
        }

        public Criteria andMaterialNameGreaterThanOrEqualTo(String value) {
            addCriterion("material_name >=", value, "materialName");
            return (Criteria) this;
        }

        public Criteria andMaterialNameLessThan(String value) {
            addCriterion("material_name <", value, "materialName");
            return (Criteria) this;
        }

        public Criteria andMaterialNameLessThanOrEqualTo(String value) {
            addCriterion("material_name <=", value, "materialName");
            return (Criteria) this;
        }

        public Criteria andMaterialNameLike(String value) {
            addCriterion("material_name like", value, "materialName");
            return (Criteria) this;
        }

        public Criteria andMaterialNameNotLike(String value) {
            addCriterion("material_name not like", value, "materialName");
            return (Criteria) this;
        }

        public Criteria andMaterialNameIn(List<String> values) {
            addCriterion("material_name in", values, "materialName");
            return (Criteria) this;
        }

        public Criteria andMaterialNameNotIn(List<String> values) {
            addCriterion("material_name not in", values, "materialName");
            return (Criteria) this;
        }

        public Criteria andMaterialNameBetween(String value1, String value2) {
            addCriterion("material_name between", value1, value2, "materialName");
            return (Criteria) this;
        }

        public Criteria andMaterialNameNotBetween(String value1, String value2) {
            addCriterion("material_name not between", value1, value2, "materialName");
            return (Criteria) this;
        }

        public Criteria andMaterialTimeIsNull() {
            addCriterion("material_time is null");
            return (Criteria) this;
        }

        public Criteria andMaterialTimeIsNotNull() {
            addCriterion("material_time is not null");
            return (Criteria) this;
        }

        public Criteria andMaterialTimeEqualTo(Date value) {
            addCriterionForJDBCDate("material_time =", value, "materialTime");
            return (Criteria) this;
        }

        public Criteria andMaterialTimeNotEqualTo(Date value) {
            addCriterionForJDBCDate("material_time <>", value, "materialTime");
            return (Criteria) this;
        }

        public Criteria andMaterialTimeGreaterThan(Date value) {
            addCriterionForJDBCDate("material_time >", value, "materialTime");
            return (Criteria) this;
        }

        public Criteria andMaterialTimeGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("material_time >=", value, "materialTime");
            return (Criteria) this;
        }

        public Criteria andMaterialTimeLessThan(Date value) {
            addCriterionForJDBCDate("material_time <", value, "materialTime");
            return (Criteria) this;
        }

        public Criteria andMaterialTimeLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("material_time <=", value, "materialTime");
            return (Criteria) this;
        }

        public Criteria andMaterialTimeIn(List<Date> values) {
            addCriterionForJDBCDate("material_time in", values, "materialTime");
            return (Criteria) this;
        }

        public Criteria andMaterialTimeNotIn(List<Date> values) {
            addCriterionForJDBCDate("material_time not in", values, "materialTime");
            return (Criteria) this;
        }

        public Criteria andMaterialTimeBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("material_time between", value1, value2, "materialTime");
            return (Criteria) this;
        }

        public Criteria andMaterialTimeNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("material_time not between", value1, value2, "materialTime");
            return (Criteria) this;
        }

        public Criteria andMaterialWorkTimeIsNull() {
            addCriterion("material_work_time is null");
            return (Criteria) this;
        }

        public Criteria andMaterialWorkTimeIsNotNull() {
            addCriterion("material_work_time is not null");
            return (Criteria) this;
        }

        public Criteria andMaterialWorkTimeEqualTo(Date value) {
            addCriterionForJDBCDate("material_work_time =", value, "materialWorkTime");
            return (Criteria) this;
        }

        public Criteria andMaterialWorkTimeNotEqualTo(Date value) {
            addCriterionForJDBCDate("material_work_time <>", value, "materialWorkTime");
            return (Criteria) this;
        }

        public Criteria andMaterialWorkTimeGreaterThan(Date value) {
            addCriterionForJDBCDate("material_work_time >", value, "materialWorkTime");
            return (Criteria) this;
        }

        public Criteria andMaterialWorkTimeGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("material_work_time >=", value, "materialWorkTime");
            return (Criteria) this;
        }

        public Criteria andMaterialWorkTimeLessThan(Date value) {
            addCriterionForJDBCDate("material_work_time <", value, "materialWorkTime");
            return (Criteria) this;
        }

        public Criteria andMaterialWorkTimeLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("material_work_time <=", value, "materialWorkTime");
            return (Criteria) this;
        }

        public Criteria andMaterialWorkTimeIn(List<Date> values) {
            addCriterionForJDBCDate("material_work_time in", values, "materialWorkTime");
            return (Criteria) this;
        }

        public Criteria andMaterialWorkTimeNotIn(List<Date> values) {
            addCriterionForJDBCDate("material_work_time not in", values, "materialWorkTime");
            return (Criteria) this;
        }

        public Criteria andMaterialWorkTimeBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("material_work_time between", value1, value2, "materialWorkTime");
            return (Criteria) this;
        }

        public Criteria andMaterialWorkTimeNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("material_work_time not between", value1, value2, "materialWorkTime");
            return (Criteria) this;
        }

        public Criteria andAdTimeIsNull() {
            addCriterion("ad_time is null");
            return (Criteria) this;
        }

        public Criteria andAdTimeIsNotNull() {
            addCriterion("ad_time is not null");
            return (Criteria) this;
        }

        public Criteria andAdTimeEqualTo(Date value) {
            addCriterionForJDBCDate("ad_time =", value, "adTime");
            return (Criteria) this;
        }

        public Criteria andAdTimeNotEqualTo(Date value) {
            addCriterionForJDBCDate("ad_time <>", value, "adTime");
            return (Criteria) this;
        }

        public Criteria andAdTimeGreaterThan(Date value) {
            addCriterionForJDBCDate("ad_time >", value, "adTime");
            return (Criteria) this;
        }

        public Criteria andAdTimeGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("ad_time >=", value, "adTime");
            return (Criteria) this;
        }

        public Criteria andAdTimeLessThan(Date value) {
            addCriterionForJDBCDate("ad_time <", value, "adTime");
            return (Criteria) this;
        }

        public Criteria andAdTimeLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("ad_time <=", value, "adTime");
            return (Criteria) this;
        }

        public Criteria andAdTimeIn(List<Date> values) {
            addCriterionForJDBCDate("ad_time in", values, "adTime");
            return (Criteria) this;
        }

        public Criteria andAdTimeNotIn(List<Date> values) {
            addCriterionForJDBCDate("ad_time not in", values, "adTime");
            return (Criteria) this;
        }

        public Criteria andAdTimeBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("ad_time between", value1, value2, "adTime");
            return (Criteria) this;
        }

        public Criteria andAdTimeNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("ad_time not between", value1, value2, "adTime");
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

        public Criteria andStatusIsNull() {
            addCriterion("status is null");
            return (Criteria) this;
        }

        public Criteria andStatusIsNotNull() {
            addCriterion("status is not null");
            return (Criteria) this;
        }

        public Criteria andStatusEqualTo(Byte value) {
            addCriterion("status =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(Byte value) {
            addCriterion("status <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(Byte value) {
            addCriterion("status >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(Byte value) {
            addCriterion("status >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(Byte value) {
            addCriterion("status <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(Byte value) {
            addCriterion("status <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<Byte> values) {
            addCriterion("status in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<Byte> values) {
            addCriterion("status not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(Byte value1, Byte value2) {
            addCriterion("status between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(Byte value1, Byte value2) {
            addCriterion("status not between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andSubmitUserIdIsNull() {
            addCriterion("submit_user_id is null");
            return (Criteria) this;
        }

        public Criteria andSubmitUserIdIsNotNull() {
            addCriterion("submit_user_id is not null");
            return (Criteria) this;
        }

        public Criteria andSubmitUserIdEqualTo(Integer value) {
            addCriterion("submit_user_id =", value, "submitUserId");
            return (Criteria) this;
        }

        public Criteria andSubmitUserIdNotEqualTo(Integer value) {
            addCriterion("submit_user_id <>", value, "submitUserId");
            return (Criteria) this;
        }

        public Criteria andSubmitUserIdGreaterThan(Integer value) {
            addCriterion("submit_user_id >", value, "submitUserId");
            return (Criteria) this;
        }

        public Criteria andSubmitUserIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("submit_user_id >=", value, "submitUserId");
            return (Criteria) this;
        }

        public Criteria andSubmitUserIdLessThan(Integer value) {
            addCriterion("submit_user_id <", value, "submitUserId");
            return (Criteria) this;
        }

        public Criteria andSubmitUserIdLessThanOrEqualTo(Integer value) {
            addCriterion("submit_user_id <=", value, "submitUserId");
            return (Criteria) this;
        }

        public Criteria andSubmitUserIdIn(List<Integer> values) {
            addCriterion("submit_user_id in", values, "submitUserId");
            return (Criteria) this;
        }

        public Criteria andSubmitUserIdNotIn(List<Integer> values) {
            addCriterion("submit_user_id not in", values, "submitUserId");
            return (Criteria) this;
        }

        public Criteria andSubmitUserIdBetween(Integer value1, Integer value2) {
            addCriterion("submit_user_id between", value1, value2, "submitUserId");
            return (Criteria) this;
        }

        public Criteria andSubmitUserIdNotBetween(Integer value1, Integer value2) {
            addCriterion("submit_user_id not between", value1, value2, "submitUserId");
            return (Criteria) this;
        }

        public Criteria andSubmitIpIsNull() {
            addCriterion("submit_ip is null");
            return (Criteria) this;
        }

        public Criteria andSubmitIpIsNotNull() {
            addCriterion("submit_ip is not null");
            return (Criteria) this;
        }

        public Criteria andSubmitIpEqualTo(String value) {
            addCriterion("submit_ip =", value, "submitIp");
            return (Criteria) this;
        }

        public Criteria andSubmitIpNotEqualTo(String value) {
            addCriterion("submit_ip <>", value, "submitIp");
            return (Criteria) this;
        }

        public Criteria andSubmitIpGreaterThan(String value) {
            addCriterion("submit_ip >", value, "submitIp");
            return (Criteria) this;
        }

        public Criteria andSubmitIpGreaterThanOrEqualTo(String value) {
            addCriterion("submit_ip >=", value, "submitIp");
            return (Criteria) this;
        }

        public Criteria andSubmitIpLessThan(String value) {
            addCriterion("submit_ip <", value, "submitIp");
            return (Criteria) this;
        }

        public Criteria andSubmitIpLessThanOrEqualTo(String value) {
            addCriterion("submit_ip <=", value, "submitIp");
            return (Criteria) this;
        }

        public Criteria andSubmitIpLike(String value) {
            addCriterion("submit_ip like", value, "submitIp");
            return (Criteria) this;
        }

        public Criteria andSubmitIpNotLike(String value) {
            addCriterion("submit_ip not like", value, "submitIp");
            return (Criteria) this;
        }

        public Criteria andSubmitIpIn(List<String> values) {
            addCriterion("submit_ip in", values, "submitIp");
            return (Criteria) this;
        }

        public Criteria andSubmitIpNotIn(List<String> values) {
            addCriterion("submit_ip not in", values, "submitIp");
            return (Criteria) this;
        }

        public Criteria andSubmitIpBetween(String value1, String value2) {
            addCriterion("submit_ip between", value1, value2, "submitIp");
            return (Criteria) this;
        }

        public Criteria andSubmitIpNotBetween(String value1, String value2) {
            addCriterion("submit_ip not between", value1, value2, "submitIp");
            return (Criteria) this;
        }

        public Criteria andSubmitTimeIsNull() {
            addCriterion("submit_time is null");
            return (Criteria) this;
        }

        public Criteria andSubmitTimeIsNotNull() {
            addCriterion("submit_time is not null");
            return (Criteria) this;
        }

        public Criteria andSubmitTimeEqualTo(Date value) {
            addCriterion("submit_time =", value, "submitTime");
            return (Criteria) this;
        }

        public Criteria andSubmitTimeNotEqualTo(Date value) {
            addCriterion("submit_time <>", value, "submitTime");
            return (Criteria) this;
        }

        public Criteria andSubmitTimeGreaterThan(Date value) {
            addCriterion("submit_time >", value, "submitTime");
            return (Criteria) this;
        }

        public Criteria andSubmitTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("submit_time >=", value, "submitTime");
            return (Criteria) this;
        }

        public Criteria andSubmitTimeLessThan(Date value) {
            addCriterion("submit_time <", value, "submitTime");
            return (Criteria) this;
        }

        public Criteria andSubmitTimeLessThanOrEqualTo(Date value) {
            addCriterion("submit_time <=", value, "submitTime");
            return (Criteria) this;
        }

        public Criteria andSubmitTimeIn(List<Date> values) {
            addCriterion("submit_time in", values, "submitTime");
            return (Criteria) this;
        }

        public Criteria andSubmitTimeNotIn(List<Date> values) {
            addCriterion("submit_time not in", values, "submitTime");
            return (Criteria) this;
        }

        public Criteria andSubmitTimeBetween(Date value1, Date value2) {
            addCriterion("submit_time between", value1, value2, "submitTime");
            return (Criteria) this;
        }

        public Criteria andSubmitTimeNotBetween(Date value1, Date value2) {
            addCriterion("submit_time not between", value1, value2, "submitTime");
            return (Criteria) this;
        }

        public Criteria andUpdateUserIdIsNull() {
            addCriterion("update_user_id is null");
            return (Criteria) this;
        }

        public Criteria andUpdateUserIdIsNotNull() {
            addCriterion("update_user_id is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateUserIdEqualTo(Integer value) {
            addCriterion("update_user_id =", value, "updateUserId");
            return (Criteria) this;
        }

        public Criteria andUpdateUserIdNotEqualTo(Integer value) {
            addCriterion("update_user_id <>", value, "updateUserId");
            return (Criteria) this;
        }

        public Criteria andUpdateUserIdGreaterThan(Integer value) {
            addCriterion("update_user_id >", value, "updateUserId");
            return (Criteria) this;
        }

        public Criteria andUpdateUserIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("update_user_id >=", value, "updateUserId");
            return (Criteria) this;
        }

        public Criteria andUpdateUserIdLessThan(Integer value) {
            addCriterion("update_user_id <", value, "updateUserId");
            return (Criteria) this;
        }

        public Criteria andUpdateUserIdLessThanOrEqualTo(Integer value) {
            addCriterion("update_user_id <=", value, "updateUserId");
            return (Criteria) this;
        }

        public Criteria andUpdateUserIdIn(List<Integer> values) {
            addCriterion("update_user_id in", values, "updateUserId");
            return (Criteria) this;
        }

        public Criteria andUpdateUserIdNotIn(List<Integer> values) {
            addCriterion("update_user_id not in", values, "updateUserId");
            return (Criteria) this;
        }

        public Criteria andUpdateUserIdBetween(Integer value1, Integer value2) {
            addCriterion("update_user_id between", value1, value2, "updateUserId");
            return (Criteria) this;
        }

        public Criteria andUpdateUserIdNotBetween(Integer value1, Integer value2) {
            addCriterion("update_user_id not between", value1, value2, "updateUserId");
            return (Criteria) this;
        }

        public Criteria andUpdateIpIsNull() {
            addCriterion("update_ip is null");
            return (Criteria) this;
        }

        public Criteria andUpdateIpIsNotNull() {
            addCriterion("update_ip is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateIpEqualTo(String value) {
            addCriterion("update_ip =", value, "updateIp");
            return (Criteria) this;
        }

        public Criteria andUpdateIpNotEqualTo(String value) {
            addCriterion("update_ip <>", value, "updateIp");
            return (Criteria) this;
        }

        public Criteria andUpdateIpGreaterThan(String value) {
            addCriterion("update_ip >", value, "updateIp");
            return (Criteria) this;
        }

        public Criteria andUpdateIpGreaterThanOrEqualTo(String value) {
            addCriterion("update_ip >=", value, "updateIp");
            return (Criteria) this;
        }

        public Criteria andUpdateIpLessThan(String value) {
            addCriterion("update_ip <", value, "updateIp");
            return (Criteria) this;
        }

        public Criteria andUpdateIpLessThanOrEqualTo(String value) {
            addCriterion("update_ip <=", value, "updateIp");
            return (Criteria) this;
        }

        public Criteria andUpdateIpLike(String value) {
            addCriterion("update_ip like", value, "updateIp");
            return (Criteria) this;
        }

        public Criteria andUpdateIpNotLike(String value) {
            addCriterion("update_ip not like", value, "updateIp");
            return (Criteria) this;
        }

        public Criteria andUpdateIpIn(List<String> values) {
            addCriterion("update_ip in", values, "updateIp");
            return (Criteria) this;
        }

        public Criteria andUpdateIpNotIn(List<String> values) {
            addCriterion("update_ip not in", values, "updateIp");
            return (Criteria) this;
        }

        public Criteria andUpdateIpBetween(String value1, String value2) {
            addCriterion("update_ip between", value1, value2, "updateIp");
            return (Criteria) this;
        }

        public Criteria andUpdateIpNotBetween(String value1, String value2) {
            addCriterion("update_ip not between", value1, value2, "updateIp");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNull() {
            addCriterion("update_time is null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNotNull() {
            addCriterion("update_time is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeEqualTo(Date value) {
            addCriterion("update_time =", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotEqualTo(Date value) {
            addCriterion("update_time <>", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThan(Date value) {
            addCriterion("update_time >", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("update_time >=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThan(Date value) {
            addCriterion("update_time <", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThanOrEqualTo(Date value) {
            addCriterion("update_time <=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIn(List<Date> values) {
            addCriterion("update_time in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotIn(List<Date> values) {
            addCriterion("update_time not in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeBetween(Date value1, Date value2) {
            addCriterion("update_time between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotBetween(Date value1, Date value2) {
            addCriterion("update_time not between", value1, value2, "updateTime");
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