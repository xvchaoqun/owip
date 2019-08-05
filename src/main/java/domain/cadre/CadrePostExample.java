package domain.cadre;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class CadrePostExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public CadrePostExample() {
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

        public Criteria andUnitPostIdIsNull() {
            addCriterion("unit_post_id is null");
            return (Criteria) this;
        }

        public Criteria andUnitPostIdIsNotNull() {
            addCriterion("unit_post_id is not null");
            return (Criteria) this;
        }

        public Criteria andUnitPostIdEqualTo(Integer value) {
            addCriterion("unit_post_id =", value, "unitPostId");
            return (Criteria) this;
        }

        public Criteria andUnitPostIdNotEqualTo(Integer value) {
            addCriterion("unit_post_id <>", value, "unitPostId");
            return (Criteria) this;
        }

        public Criteria andUnitPostIdGreaterThan(Integer value) {
            addCriterion("unit_post_id >", value, "unitPostId");
            return (Criteria) this;
        }

        public Criteria andUnitPostIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("unit_post_id >=", value, "unitPostId");
            return (Criteria) this;
        }

        public Criteria andUnitPostIdLessThan(Integer value) {
            addCriterion("unit_post_id <", value, "unitPostId");
            return (Criteria) this;
        }

        public Criteria andUnitPostIdLessThanOrEqualTo(Integer value) {
            addCriterion("unit_post_id <=", value, "unitPostId");
            return (Criteria) this;
        }

        public Criteria andUnitPostIdIn(List<Integer> values) {
            addCriterion("unit_post_id in", values, "unitPostId");
            return (Criteria) this;
        }

        public Criteria andUnitPostIdNotIn(List<Integer> values) {
            addCriterion("unit_post_id not in", values, "unitPostId");
            return (Criteria) this;
        }

        public Criteria andUnitPostIdBetween(Integer value1, Integer value2) {
            addCriterion("unit_post_id between", value1, value2, "unitPostId");
            return (Criteria) this;
        }

        public Criteria andUnitPostIdNotBetween(Integer value1, Integer value2) {
            addCriterion("unit_post_id not between", value1, value2, "unitPostId");
            return (Criteria) this;
        }

        public Criteria andPostNameIsNull() {
            addCriterion("post_name is null");
            return (Criteria) this;
        }

        public Criteria andPostNameIsNotNull() {
            addCriterion("post_name is not null");
            return (Criteria) this;
        }

        public Criteria andPostNameEqualTo(String value) {
            addCriterion("post_name =", value, "postName");
            return (Criteria) this;
        }

        public Criteria andPostNameNotEqualTo(String value) {
            addCriterion("post_name <>", value, "postName");
            return (Criteria) this;
        }

        public Criteria andPostNameGreaterThan(String value) {
            addCriterion("post_name >", value, "postName");
            return (Criteria) this;
        }

        public Criteria andPostNameGreaterThanOrEqualTo(String value) {
            addCriterion("post_name >=", value, "postName");
            return (Criteria) this;
        }

        public Criteria andPostNameLessThan(String value) {
            addCriterion("post_name <", value, "postName");
            return (Criteria) this;
        }

        public Criteria andPostNameLessThanOrEqualTo(String value) {
            addCriterion("post_name <=", value, "postName");
            return (Criteria) this;
        }

        public Criteria andPostNameLike(String value) {
            addCriterion("post_name like", value, "postName");
            return (Criteria) this;
        }

        public Criteria andPostNameNotLike(String value) {
            addCriterion("post_name not like", value, "postName");
            return (Criteria) this;
        }

        public Criteria andPostNameIn(List<String> values) {
            addCriterion("post_name in", values, "postName");
            return (Criteria) this;
        }

        public Criteria andPostNameNotIn(List<String> values) {
            addCriterion("post_name not in", values, "postName");
            return (Criteria) this;
        }

        public Criteria andPostNameBetween(String value1, String value2) {
            addCriterion("post_name between", value1, value2, "postName");
            return (Criteria) this;
        }

        public Criteria andPostNameNotBetween(String value1, String value2) {
            addCriterion("post_name not between", value1, value2, "postName");
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

        public Criteria andLpDispatchIdIsNull() {
            addCriterion("lp_dispatch_id is null");
            return (Criteria) this;
        }

        public Criteria andLpDispatchIdIsNotNull() {
            addCriterion("lp_dispatch_id is not null");
            return (Criteria) this;
        }

        public Criteria andLpDispatchIdEqualTo(Integer value) {
            addCriterion("lp_dispatch_id =", value, "lpDispatchId");
            return (Criteria) this;
        }

        public Criteria andLpDispatchIdNotEqualTo(Integer value) {
            addCriterion("lp_dispatch_id <>", value, "lpDispatchId");
            return (Criteria) this;
        }

        public Criteria andLpDispatchIdGreaterThan(Integer value) {
            addCriterion("lp_dispatch_id >", value, "lpDispatchId");
            return (Criteria) this;
        }

        public Criteria andLpDispatchIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("lp_dispatch_id >=", value, "lpDispatchId");
            return (Criteria) this;
        }

        public Criteria andLpDispatchIdLessThan(Integer value) {
            addCriterion("lp_dispatch_id <", value, "lpDispatchId");
            return (Criteria) this;
        }

        public Criteria andLpDispatchIdLessThanOrEqualTo(Integer value) {
            addCriterion("lp_dispatch_id <=", value, "lpDispatchId");
            return (Criteria) this;
        }

        public Criteria andLpDispatchIdIn(List<Integer> values) {
            addCriterion("lp_dispatch_id in", values, "lpDispatchId");
            return (Criteria) this;
        }

        public Criteria andLpDispatchIdNotIn(List<Integer> values) {
            addCriterion("lp_dispatch_id not in", values, "lpDispatchId");
            return (Criteria) this;
        }

        public Criteria andLpDispatchIdBetween(Integer value1, Integer value2) {
            addCriterion("lp_dispatch_id between", value1, value2, "lpDispatchId");
            return (Criteria) this;
        }

        public Criteria andLpDispatchIdNotBetween(Integer value1, Integer value2) {
            addCriterion("lp_dispatch_id not between", value1, value2, "lpDispatchId");
            return (Criteria) this;
        }

        public Criteria andLpWorkTimeIsNull() {
            addCriterion("lp_work_time is null");
            return (Criteria) this;
        }

        public Criteria andLpWorkTimeIsNotNull() {
            addCriterion("lp_work_time is not null");
            return (Criteria) this;
        }

        public Criteria andLpWorkTimeEqualTo(Date value) {
            addCriterionForJDBCDate("lp_work_time =", value, "lpWorkTime");
            return (Criteria) this;
        }

        public Criteria andLpWorkTimeNotEqualTo(Date value) {
            addCriterionForJDBCDate("lp_work_time <>", value, "lpWorkTime");
            return (Criteria) this;
        }

        public Criteria andLpWorkTimeGreaterThan(Date value) {
            addCriterionForJDBCDate("lp_work_time >", value, "lpWorkTime");
            return (Criteria) this;
        }

        public Criteria andLpWorkTimeGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("lp_work_time >=", value, "lpWorkTime");
            return (Criteria) this;
        }

        public Criteria andLpWorkTimeLessThan(Date value) {
            addCriterionForJDBCDate("lp_work_time <", value, "lpWorkTime");
            return (Criteria) this;
        }

        public Criteria andLpWorkTimeLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("lp_work_time <=", value, "lpWorkTime");
            return (Criteria) this;
        }

        public Criteria andLpWorkTimeIn(List<Date> values) {
            addCriterionForJDBCDate("lp_work_time in", values, "lpWorkTime");
            return (Criteria) this;
        }

        public Criteria andLpWorkTimeNotIn(List<Date> values) {
            addCriterionForJDBCDate("lp_work_time not in", values, "lpWorkTime");
            return (Criteria) this;
        }

        public Criteria andLpWorkTimeBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("lp_work_time between", value1, value2, "lpWorkTime");
            return (Criteria) this;
        }

        public Criteria andLpWorkTimeNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("lp_work_time not between", value1, value2, "lpWorkTime");
            return (Criteria) this;
        }

        public Criteria andNpDispatchIdIsNull() {
            addCriterion("np_dispatch_id is null");
            return (Criteria) this;
        }

        public Criteria andNpDispatchIdIsNotNull() {
            addCriterion("np_dispatch_id is not null");
            return (Criteria) this;
        }

        public Criteria andNpDispatchIdEqualTo(Integer value) {
            addCriterion("np_dispatch_id =", value, "npDispatchId");
            return (Criteria) this;
        }

        public Criteria andNpDispatchIdNotEqualTo(Integer value) {
            addCriterion("np_dispatch_id <>", value, "npDispatchId");
            return (Criteria) this;
        }

        public Criteria andNpDispatchIdGreaterThan(Integer value) {
            addCriterion("np_dispatch_id >", value, "npDispatchId");
            return (Criteria) this;
        }

        public Criteria andNpDispatchIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("np_dispatch_id >=", value, "npDispatchId");
            return (Criteria) this;
        }

        public Criteria andNpDispatchIdLessThan(Integer value) {
            addCriterion("np_dispatch_id <", value, "npDispatchId");
            return (Criteria) this;
        }

        public Criteria andNpDispatchIdLessThanOrEqualTo(Integer value) {
            addCriterion("np_dispatch_id <=", value, "npDispatchId");
            return (Criteria) this;
        }

        public Criteria andNpDispatchIdIn(List<Integer> values) {
            addCriterion("np_dispatch_id in", values, "npDispatchId");
            return (Criteria) this;
        }

        public Criteria andNpDispatchIdNotIn(List<Integer> values) {
            addCriterion("np_dispatch_id not in", values, "npDispatchId");
            return (Criteria) this;
        }

        public Criteria andNpDispatchIdBetween(Integer value1, Integer value2) {
            addCriterion("np_dispatch_id between", value1, value2, "npDispatchId");
            return (Criteria) this;
        }

        public Criteria andNpDispatchIdNotBetween(Integer value1, Integer value2) {
            addCriterion("np_dispatch_id not between", value1, value2, "npDispatchId");
            return (Criteria) this;
        }

        public Criteria andNpWorkTimeIsNull() {
            addCriterion("np_work_time is null");
            return (Criteria) this;
        }

        public Criteria andNpWorkTimeIsNotNull() {
            addCriterion("np_work_time is not null");
            return (Criteria) this;
        }

        public Criteria andNpWorkTimeEqualTo(Date value) {
            addCriterionForJDBCDate("np_work_time =", value, "npWorkTime");
            return (Criteria) this;
        }

        public Criteria andNpWorkTimeNotEqualTo(Date value) {
            addCriterionForJDBCDate("np_work_time <>", value, "npWorkTime");
            return (Criteria) this;
        }

        public Criteria andNpWorkTimeGreaterThan(Date value) {
            addCriterionForJDBCDate("np_work_time >", value, "npWorkTime");
            return (Criteria) this;
        }

        public Criteria andNpWorkTimeGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("np_work_time >=", value, "npWorkTime");
            return (Criteria) this;
        }

        public Criteria andNpWorkTimeLessThan(Date value) {
            addCriterionForJDBCDate("np_work_time <", value, "npWorkTime");
            return (Criteria) this;
        }

        public Criteria andNpWorkTimeLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("np_work_time <=", value, "npWorkTime");
            return (Criteria) this;
        }

        public Criteria andNpWorkTimeIn(List<Date> values) {
            addCriterionForJDBCDate("np_work_time in", values, "npWorkTime");
            return (Criteria) this;
        }

        public Criteria andNpWorkTimeNotIn(List<Date> values) {
            addCriterionForJDBCDate("np_work_time not in", values, "npWorkTime");
            return (Criteria) this;
        }

        public Criteria andNpWorkTimeBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("np_work_time between", value1, value2, "npWorkTime");
            return (Criteria) this;
        }

        public Criteria andNpWorkTimeNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("np_work_time not between", value1, value2, "npWorkTime");
            return (Criteria) this;
        }

        public Criteria andPostTypeIsNull() {
            addCriterion("post_type is null");
            return (Criteria) this;
        }

        public Criteria andPostTypeIsNotNull() {
            addCriterion("post_type is not null");
            return (Criteria) this;
        }

        public Criteria andPostTypeEqualTo(Integer value) {
            addCriterion("post_type =", value, "postType");
            return (Criteria) this;
        }

        public Criteria andPostTypeNotEqualTo(Integer value) {
            addCriterion("post_type <>", value, "postType");
            return (Criteria) this;
        }

        public Criteria andPostTypeGreaterThan(Integer value) {
            addCriterion("post_type >", value, "postType");
            return (Criteria) this;
        }

        public Criteria andPostTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("post_type >=", value, "postType");
            return (Criteria) this;
        }

        public Criteria andPostTypeLessThan(Integer value) {
            addCriterion("post_type <", value, "postType");
            return (Criteria) this;
        }

        public Criteria andPostTypeLessThanOrEqualTo(Integer value) {
            addCriterion("post_type <=", value, "postType");
            return (Criteria) this;
        }

        public Criteria andPostTypeIn(List<Integer> values) {
            addCriterion("post_type in", values, "postType");
            return (Criteria) this;
        }

        public Criteria andPostTypeNotIn(List<Integer> values) {
            addCriterion("post_type not in", values, "postType");
            return (Criteria) this;
        }

        public Criteria andPostTypeBetween(Integer value1, Integer value2) {
            addCriterion("post_type between", value1, value2, "postType");
            return (Criteria) this;
        }

        public Criteria andPostTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("post_type not between", value1, value2, "postType");
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

        public Criteria andIsPrincipalIsNull() {
            addCriterion("is_principal is null");
            return (Criteria) this;
        }

        public Criteria andIsPrincipalIsNotNull() {
            addCriterion("is_principal is not null");
            return (Criteria) this;
        }

        public Criteria andIsPrincipalEqualTo(Boolean value) {
            addCriterion("is_principal =", value, "isPrincipal");
            return (Criteria) this;
        }

        public Criteria andIsPrincipalNotEqualTo(Boolean value) {
            addCriterion("is_principal <>", value, "isPrincipal");
            return (Criteria) this;
        }

        public Criteria andIsPrincipalGreaterThan(Boolean value) {
            addCriterion("is_principal >", value, "isPrincipal");
            return (Criteria) this;
        }

        public Criteria andIsPrincipalGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_principal >=", value, "isPrincipal");
            return (Criteria) this;
        }

        public Criteria andIsPrincipalLessThan(Boolean value) {
            addCriterion("is_principal <", value, "isPrincipal");
            return (Criteria) this;
        }

        public Criteria andIsPrincipalLessThanOrEqualTo(Boolean value) {
            addCriterion("is_principal <=", value, "isPrincipal");
            return (Criteria) this;
        }

        public Criteria andIsPrincipalIn(List<Boolean> values) {
            addCriterion("is_principal in", values, "isPrincipal");
            return (Criteria) this;
        }

        public Criteria andIsPrincipalNotIn(List<Boolean> values) {
            addCriterion("is_principal not in", values, "isPrincipal");
            return (Criteria) this;
        }

        public Criteria andIsPrincipalBetween(Boolean value1, Boolean value2) {
            addCriterion("is_principal between", value1, value2, "isPrincipal");
            return (Criteria) this;
        }

        public Criteria andIsPrincipalNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_principal not between", value1, value2, "isPrincipal");
            return (Criteria) this;
        }

        public Criteria andIsCpcIsNull() {
            addCriterion("is_cpc is null");
            return (Criteria) this;
        }

        public Criteria andIsCpcIsNotNull() {
            addCriterion("is_cpc is not null");
            return (Criteria) this;
        }

        public Criteria andIsCpcEqualTo(Boolean value) {
            addCriterion("is_cpc =", value, "isCpc");
            return (Criteria) this;
        }

        public Criteria andIsCpcNotEqualTo(Boolean value) {
            addCriterion("is_cpc <>", value, "isCpc");
            return (Criteria) this;
        }

        public Criteria andIsCpcGreaterThan(Boolean value) {
            addCriterion("is_cpc >", value, "isCpc");
            return (Criteria) this;
        }

        public Criteria andIsCpcGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_cpc >=", value, "isCpc");
            return (Criteria) this;
        }

        public Criteria andIsCpcLessThan(Boolean value) {
            addCriterion("is_cpc <", value, "isCpc");
            return (Criteria) this;
        }

        public Criteria andIsCpcLessThanOrEqualTo(Boolean value) {
            addCriterion("is_cpc <=", value, "isCpc");
            return (Criteria) this;
        }

        public Criteria andIsCpcIn(List<Boolean> values) {
            addCriterion("is_cpc in", values, "isCpc");
            return (Criteria) this;
        }

        public Criteria andIsCpcNotIn(List<Boolean> values) {
            addCriterion("is_cpc not in", values, "isCpc");
            return (Criteria) this;
        }

        public Criteria andIsCpcBetween(Boolean value1, Boolean value2) {
            addCriterion("is_cpc between", value1, value2, "isCpc");
            return (Criteria) this;
        }

        public Criteria andIsCpcNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_cpc not between", value1, value2, "isCpc");
            return (Criteria) this;
        }

        public Criteria andPostClassIdIsNull() {
            addCriterion("post_class_id is null");
            return (Criteria) this;
        }

        public Criteria andPostClassIdIsNotNull() {
            addCriterion("post_class_id is not null");
            return (Criteria) this;
        }

        public Criteria andPostClassIdEqualTo(Integer value) {
            addCriterion("post_class_id =", value, "postClassId");
            return (Criteria) this;
        }

        public Criteria andPostClassIdNotEqualTo(Integer value) {
            addCriterion("post_class_id <>", value, "postClassId");
            return (Criteria) this;
        }

        public Criteria andPostClassIdGreaterThan(Integer value) {
            addCriterion("post_class_id >", value, "postClassId");
            return (Criteria) this;
        }

        public Criteria andPostClassIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("post_class_id >=", value, "postClassId");
            return (Criteria) this;
        }

        public Criteria andPostClassIdLessThan(Integer value) {
            addCriterion("post_class_id <", value, "postClassId");
            return (Criteria) this;
        }

        public Criteria andPostClassIdLessThanOrEqualTo(Integer value) {
            addCriterion("post_class_id <=", value, "postClassId");
            return (Criteria) this;
        }

        public Criteria andPostClassIdIn(List<Integer> values) {
            addCriterion("post_class_id in", values, "postClassId");
            return (Criteria) this;
        }

        public Criteria andPostClassIdNotIn(List<Integer> values) {
            addCriterion("post_class_id not in", values, "postClassId");
            return (Criteria) this;
        }

        public Criteria andPostClassIdBetween(Integer value1, Integer value2) {
            addCriterion("post_class_id between", value1, value2, "postClassId");
            return (Criteria) this;
        }

        public Criteria andPostClassIdNotBetween(Integer value1, Integer value2) {
            addCriterion("post_class_id not between", value1, value2, "postClassId");
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

        public Criteria andIsMainPostIsNull() {
            addCriterion("is_main_post is null");
            return (Criteria) this;
        }

        public Criteria andIsMainPostIsNotNull() {
            addCriterion("is_main_post is not null");
            return (Criteria) this;
        }

        public Criteria andIsMainPostEqualTo(Boolean value) {
            addCriterion("is_main_post =", value, "isMainPost");
            return (Criteria) this;
        }

        public Criteria andIsMainPostNotEqualTo(Boolean value) {
            addCriterion("is_main_post <>", value, "isMainPost");
            return (Criteria) this;
        }

        public Criteria andIsMainPostGreaterThan(Boolean value) {
            addCriterion("is_main_post >", value, "isMainPost");
            return (Criteria) this;
        }

        public Criteria andIsMainPostGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_main_post >=", value, "isMainPost");
            return (Criteria) this;
        }

        public Criteria andIsMainPostLessThan(Boolean value) {
            addCriterion("is_main_post <", value, "isMainPost");
            return (Criteria) this;
        }

        public Criteria andIsMainPostLessThanOrEqualTo(Boolean value) {
            addCriterion("is_main_post <=", value, "isMainPost");
            return (Criteria) this;
        }

        public Criteria andIsMainPostIn(List<Boolean> values) {
            addCriterion("is_main_post in", values, "isMainPost");
            return (Criteria) this;
        }

        public Criteria andIsMainPostNotIn(List<Boolean> values) {
            addCriterion("is_main_post not in", values, "isMainPost");
            return (Criteria) this;
        }

        public Criteria andIsMainPostBetween(Boolean value1, Boolean value2) {
            addCriterion("is_main_post between", value1, value2, "isMainPost");
            return (Criteria) this;
        }

        public Criteria andIsMainPostNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_main_post not between", value1, value2, "isMainPost");
            return (Criteria) this;
        }

        public Criteria andIsFirstMainPostIsNull() {
            addCriterion("is_first_main_post is null");
            return (Criteria) this;
        }

        public Criteria andIsFirstMainPostIsNotNull() {
            addCriterion("is_first_main_post is not null");
            return (Criteria) this;
        }

        public Criteria andIsFirstMainPostEqualTo(Boolean value) {
            addCriterion("is_first_main_post =", value, "isFirstMainPost");
            return (Criteria) this;
        }

        public Criteria andIsFirstMainPostNotEqualTo(Boolean value) {
            addCriterion("is_first_main_post <>", value, "isFirstMainPost");
            return (Criteria) this;
        }

        public Criteria andIsFirstMainPostGreaterThan(Boolean value) {
            addCriterion("is_first_main_post >", value, "isFirstMainPost");
            return (Criteria) this;
        }

        public Criteria andIsFirstMainPostGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_first_main_post >=", value, "isFirstMainPost");
            return (Criteria) this;
        }

        public Criteria andIsFirstMainPostLessThan(Boolean value) {
            addCriterion("is_first_main_post <", value, "isFirstMainPost");
            return (Criteria) this;
        }

        public Criteria andIsFirstMainPostLessThanOrEqualTo(Boolean value) {
            addCriterion("is_first_main_post <=", value, "isFirstMainPost");
            return (Criteria) this;
        }

        public Criteria andIsFirstMainPostIn(List<Boolean> values) {
            addCriterion("is_first_main_post in", values, "isFirstMainPost");
            return (Criteria) this;
        }

        public Criteria andIsFirstMainPostNotIn(List<Boolean> values) {
            addCriterion("is_first_main_post not in", values, "isFirstMainPost");
            return (Criteria) this;
        }

        public Criteria andIsFirstMainPostBetween(Boolean value1, Boolean value2) {
            addCriterion("is_first_main_post between", value1, value2, "isFirstMainPost");
            return (Criteria) this;
        }

        public Criteria andIsFirstMainPostNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_first_main_post not between", value1, value2, "isFirstMainPost");
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