package domain.cadre;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class CadreAdminLevelExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public CadreAdminLevelExample() {
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

        public Criteria andSDispatchIdIsNull() {
            addCriterion("s_dispatch_id is null");
            return (Criteria) this;
        }

        public Criteria andSDispatchIdIsNotNull() {
            addCriterion("s_dispatch_id is not null");
            return (Criteria) this;
        }

        public Criteria andSDispatchIdEqualTo(Integer value) {
            addCriterion("s_dispatch_id =", value, "sDispatchId");
            return (Criteria) this;
        }

        public Criteria andSDispatchIdNotEqualTo(Integer value) {
            addCriterion("s_dispatch_id <>", value, "sDispatchId");
            return (Criteria) this;
        }

        public Criteria andSDispatchIdGreaterThan(Integer value) {
            addCriterion("s_dispatch_id >", value, "sDispatchId");
            return (Criteria) this;
        }

        public Criteria andSDispatchIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("s_dispatch_id >=", value, "sDispatchId");
            return (Criteria) this;
        }

        public Criteria andSDispatchIdLessThan(Integer value) {
            addCriterion("s_dispatch_id <", value, "sDispatchId");
            return (Criteria) this;
        }

        public Criteria andSDispatchIdLessThanOrEqualTo(Integer value) {
            addCriterion("s_dispatch_id <=", value, "sDispatchId");
            return (Criteria) this;
        }

        public Criteria andSDispatchIdIn(List<Integer> values) {
            addCriterion("s_dispatch_id in", values, "sDispatchId");
            return (Criteria) this;
        }

        public Criteria andSDispatchIdNotIn(List<Integer> values) {
            addCriterion("s_dispatch_id not in", values, "sDispatchId");
            return (Criteria) this;
        }

        public Criteria andSDispatchIdBetween(Integer value1, Integer value2) {
            addCriterion("s_dispatch_id between", value1, value2, "sDispatchId");
            return (Criteria) this;
        }

        public Criteria andSDispatchIdNotBetween(Integer value1, Integer value2) {
            addCriterion("s_dispatch_id not between", value1, value2, "sDispatchId");
            return (Criteria) this;
        }

        public Criteria andSWorkTimeIsNull() {
            addCriterion("s_work_time is null");
            return (Criteria) this;
        }

        public Criteria andSWorkTimeIsNotNull() {
            addCriterion("s_work_time is not null");
            return (Criteria) this;
        }

        public Criteria andSWorkTimeEqualTo(Date value) {
            addCriterionForJDBCDate("s_work_time =", value, "sWorkTime");
            return (Criteria) this;
        }

        public Criteria andSWorkTimeNotEqualTo(Date value) {
            addCriterionForJDBCDate("s_work_time <>", value, "sWorkTime");
            return (Criteria) this;
        }

        public Criteria andSWorkTimeGreaterThan(Date value) {
            addCriterionForJDBCDate("s_work_time >", value, "sWorkTime");
            return (Criteria) this;
        }

        public Criteria andSWorkTimeGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("s_work_time >=", value, "sWorkTime");
            return (Criteria) this;
        }

        public Criteria andSWorkTimeLessThan(Date value) {
            addCriterionForJDBCDate("s_work_time <", value, "sWorkTime");
            return (Criteria) this;
        }

        public Criteria andSWorkTimeLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("s_work_time <=", value, "sWorkTime");
            return (Criteria) this;
        }

        public Criteria andSWorkTimeIn(List<Date> values) {
            addCriterionForJDBCDate("s_work_time in", values, "sWorkTime");
            return (Criteria) this;
        }

        public Criteria andSWorkTimeNotIn(List<Date> values) {
            addCriterionForJDBCDate("s_work_time not in", values, "sWorkTime");
            return (Criteria) this;
        }

        public Criteria andSWorkTimeBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("s_work_time between", value1, value2, "sWorkTime");
            return (Criteria) this;
        }

        public Criteria andSWorkTimeNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("s_work_time not between", value1, value2, "sWorkTime");
            return (Criteria) this;
        }

        public Criteria andEDispatchIdIsNull() {
            addCriterion("e_dispatch_id is null");
            return (Criteria) this;
        }

        public Criteria andEDispatchIdIsNotNull() {
            addCriterion("e_dispatch_id is not null");
            return (Criteria) this;
        }

        public Criteria andEDispatchIdEqualTo(Integer value) {
            addCriterion("e_dispatch_id =", value, "eDispatchId");
            return (Criteria) this;
        }

        public Criteria andEDispatchIdNotEqualTo(Integer value) {
            addCriterion("e_dispatch_id <>", value, "eDispatchId");
            return (Criteria) this;
        }

        public Criteria andEDispatchIdGreaterThan(Integer value) {
            addCriterion("e_dispatch_id >", value, "eDispatchId");
            return (Criteria) this;
        }

        public Criteria andEDispatchIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("e_dispatch_id >=", value, "eDispatchId");
            return (Criteria) this;
        }

        public Criteria andEDispatchIdLessThan(Integer value) {
            addCriterion("e_dispatch_id <", value, "eDispatchId");
            return (Criteria) this;
        }

        public Criteria andEDispatchIdLessThanOrEqualTo(Integer value) {
            addCriterion("e_dispatch_id <=", value, "eDispatchId");
            return (Criteria) this;
        }

        public Criteria andEDispatchIdIn(List<Integer> values) {
            addCriterion("e_dispatch_id in", values, "eDispatchId");
            return (Criteria) this;
        }

        public Criteria andEDispatchIdNotIn(List<Integer> values) {
            addCriterion("e_dispatch_id not in", values, "eDispatchId");
            return (Criteria) this;
        }

        public Criteria andEDispatchIdBetween(Integer value1, Integer value2) {
            addCriterion("e_dispatch_id between", value1, value2, "eDispatchId");
            return (Criteria) this;
        }

        public Criteria andEDispatchIdNotBetween(Integer value1, Integer value2) {
            addCriterion("e_dispatch_id not between", value1, value2, "eDispatchId");
            return (Criteria) this;
        }

        public Criteria andEWorkTimeIsNull() {
            addCriterion("e_work_time is null");
            return (Criteria) this;
        }

        public Criteria andEWorkTimeIsNotNull() {
            addCriterion("e_work_time is not null");
            return (Criteria) this;
        }

        public Criteria andEWorkTimeEqualTo(Date value) {
            addCriterionForJDBCDate("e_work_time =", value, "eWorkTime");
            return (Criteria) this;
        }

        public Criteria andEWorkTimeNotEqualTo(Date value) {
            addCriterionForJDBCDate("e_work_time <>", value, "eWorkTime");
            return (Criteria) this;
        }

        public Criteria andEWorkTimeGreaterThan(Date value) {
            addCriterionForJDBCDate("e_work_time >", value, "eWorkTime");
            return (Criteria) this;
        }

        public Criteria andEWorkTimeGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("e_work_time >=", value, "eWorkTime");
            return (Criteria) this;
        }

        public Criteria andEWorkTimeLessThan(Date value) {
            addCriterionForJDBCDate("e_work_time <", value, "eWorkTime");
            return (Criteria) this;
        }

        public Criteria andEWorkTimeLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("e_work_time <=", value, "eWorkTime");
            return (Criteria) this;
        }

        public Criteria andEWorkTimeIn(List<Date> values) {
            addCriterionForJDBCDate("e_work_time in", values, "eWorkTime");
            return (Criteria) this;
        }

        public Criteria andEWorkTimeNotIn(List<Date> values) {
            addCriterionForJDBCDate("e_work_time not in", values, "eWorkTime");
            return (Criteria) this;
        }

        public Criteria andEWorkTimeBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("e_work_time between", value1, value2, "eWorkTime");
            return (Criteria) this;
        }

        public Criteria andEWorkTimeNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("e_work_time not between", value1, value2, "eWorkTime");
            return (Criteria) this;
        }

        public Criteria andSPostIsNull() {
            addCriterion("s_post is null");
            return (Criteria) this;
        }

        public Criteria andSPostIsNotNull() {
            addCriterion("s_post is not null");
            return (Criteria) this;
        }

        public Criteria andSPostEqualTo(String value) {
            addCriterion("s_post =", value, "sPost");
            return (Criteria) this;
        }

        public Criteria andSPostNotEqualTo(String value) {
            addCriterion("s_post <>", value, "sPost");
            return (Criteria) this;
        }

        public Criteria andSPostGreaterThan(String value) {
            addCriterion("s_post >", value, "sPost");
            return (Criteria) this;
        }

        public Criteria andSPostGreaterThanOrEqualTo(String value) {
            addCriterion("s_post >=", value, "sPost");
            return (Criteria) this;
        }

        public Criteria andSPostLessThan(String value) {
            addCriterion("s_post <", value, "sPost");
            return (Criteria) this;
        }

        public Criteria andSPostLessThanOrEqualTo(String value) {
            addCriterion("s_post <=", value, "sPost");
            return (Criteria) this;
        }

        public Criteria andSPostLike(String value) {
            addCriterion("s_post like", value, "sPost");
            return (Criteria) this;
        }

        public Criteria andSPostNotLike(String value) {
            addCriterion("s_post not like", value, "sPost");
            return (Criteria) this;
        }

        public Criteria andSPostIn(List<String> values) {
            addCriterion("s_post in", values, "sPost");
            return (Criteria) this;
        }

        public Criteria andSPostNotIn(List<String> values) {
            addCriterion("s_post not in", values, "sPost");
            return (Criteria) this;
        }

        public Criteria andSPostBetween(String value1, String value2) {
            addCriterion("s_post between", value1, value2, "sPost");
            return (Criteria) this;
        }

        public Criteria andSPostNotBetween(String value1, String value2) {
            addCriterion("s_post not between", value1, value2, "sPost");
            return (Criteria) this;
        }

        public Criteria andStartDispatchCadreIdIsNull() {
            addCriterion("start_dispatch_cadre_id is null");
            return (Criteria) this;
        }

        public Criteria andStartDispatchCadreIdIsNotNull() {
            addCriterion("start_dispatch_cadre_id is not null");
            return (Criteria) this;
        }

        public Criteria andStartDispatchCadreIdEqualTo(Integer value) {
            addCriterion("start_dispatch_cadre_id =", value, "startDispatchCadreId");
            return (Criteria) this;
        }

        public Criteria andStartDispatchCadreIdNotEqualTo(Integer value) {
            addCriterion("start_dispatch_cadre_id <>", value, "startDispatchCadreId");
            return (Criteria) this;
        }

        public Criteria andStartDispatchCadreIdGreaterThan(Integer value) {
            addCriterion("start_dispatch_cadre_id >", value, "startDispatchCadreId");
            return (Criteria) this;
        }

        public Criteria andStartDispatchCadreIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("start_dispatch_cadre_id >=", value, "startDispatchCadreId");
            return (Criteria) this;
        }

        public Criteria andStartDispatchCadreIdLessThan(Integer value) {
            addCriterion("start_dispatch_cadre_id <", value, "startDispatchCadreId");
            return (Criteria) this;
        }

        public Criteria andStartDispatchCadreIdLessThanOrEqualTo(Integer value) {
            addCriterion("start_dispatch_cadre_id <=", value, "startDispatchCadreId");
            return (Criteria) this;
        }

        public Criteria andStartDispatchCadreIdIn(List<Integer> values) {
            addCriterion("start_dispatch_cadre_id in", values, "startDispatchCadreId");
            return (Criteria) this;
        }

        public Criteria andStartDispatchCadreIdNotIn(List<Integer> values) {
            addCriterion("start_dispatch_cadre_id not in", values, "startDispatchCadreId");
            return (Criteria) this;
        }

        public Criteria andStartDispatchCadreIdBetween(Integer value1, Integer value2) {
            addCriterion("start_dispatch_cadre_id between", value1, value2, "startDispatchCadreId");
            return (Criteria) this;
        }

        public Criteria andStartDispatchCadreIdNotBetween(Integer value1, Integer value2) {
            addCriterion("start_dispatch_cadre_id not between", value1, value2, "startDispatchCadreId");
            return (Criteria) this;
        }

        public Criteria andEndDispatchCadreIdIsNull() {
            addCriterion("end_dispatch_cadre_id is null");
            return (Criteria) this;
        }

        public Criteria andEndDispatchCadreIdIsNotNull() {
            addCriterion("end_dispatch_cadre_id is not null");
            return (Criteria) this;
        }

        public Criteria andEndDispatchCadreIdEqualTo(Integer value) {
            addCriterion("end_dispatch_cadre_id =", value, "endDispatchCadreId");
            return (Criteria) this;
        }

        public Criteria andEndDispatchCadreIdNotEqualTo(Integer value) {
            addCriterion("end_dispatch_cadre_id <>", value, "endDispatchCadreId");
            return (Criteria) this;
        }

        public Criteria andEndDispatchCadreIdGreaterThan(Integer value) {
            addCriterion("end_dispatch_cadre_id >", value, "endDispatchCadreId");
            return (Criteria) this;
        }

        public Criteria andEndDispatchCadreIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("end_dispatch_cadre_id >=", value, "endDispatchCadreId");
            return (Criteria) this;
        }

        public Criteria andEndDispatchCadreIdLessThan(Integer value) {
            addCriterion("end_dispatch_cadre_id <", value, "endDispatchCadreId");
            return (Criteria) this;
        }

        public Criteria andEndDispatchCadreIdLessThanOrEqualTo(Integer value) {
            addCriterion("end_dispatch_cadre_id <=", value, "endDispatchCadreId");
            return (Criteria) this;
        }

        public Criteria andEndDispatchCadreIdIn(List<Integer> values) {
            addCriterion("end_dispatch_cadre_id in", values, "endDispatchCadreId");
            return (Criteria) this;
        }

        public Criteria andEndDispatchCadreIdNotIn(List<Integer> values) {
            addCriterion("end_dispatch_cadre_id not in", values, "endDispatchCadreId");
            return (Criteria) this;
        }

        public Criteria andEndDispatchCadreIdBetween(Integer value1, Integer value2) {
            addCriterion("end_dispatch_cadre_id between", value1, value2, "endDispatchCadreId");
            return (Criteria) this;
        }

        public Criteria andEndDispatchCadreIdNotBetween(Integer value1, Integer value2) {
            addCriterion("end_dispatch_cadre_id not between", value1, value2, "endDispatchCadreId");
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