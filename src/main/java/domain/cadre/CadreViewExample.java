package domain.cadre;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class CadreViewExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public CadreViewExample() {
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

        public Criteria andUserIdIsNull() {
            addCriterion("user_id is null");
            return (Criteria) this;
        }

        public Criteria andUserIdIsNotNull() {
            addCriterion("user_id is not null");
            return (Criteria) this;
        }

        public Criteria andUserIdEqualTo(Integer value) {
            addCriterion("user_id =", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotEqualTo(Integer value) {
            addCriterion("user_id <>", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThan(Integer value) {
            addCriterion("user_id >", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("user_id >=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThan(Integer value) {
            addCriterion("user_id <", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThanOrEqualTo(Integer value) {
            addCriterion("user_id <=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdIn(List<Integer> values) {
            addCriterion("user_id in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotIn(List<Integer> values) {
            addCriterion("user_id not in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdBetween(Integer value1, Integer value2) {
            addCriterion("user_id between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotBetween(Integer value1, Integer value2) {
            addCriterion("user_id not between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andTypeIdIsNull() {
            addCriterion("type_id is null");
            return (Criteria) this;
        }

        public Criteria andTypeIdIsNotNull() {
            addCriterion("type_id is not null");
            return (Criteria) this;
        }

        public Criteria andTypeIdEqualTo(Integer value) {
            addCriterion("type_id =", value, "typeId");
            return (Criteria) this;
        }

        public Criteria andTypeIdNotEqualTo(Integer value) {
            addCriterion("type_id <>", value, "typeId");
            return (Criteria) this;
        }

        public Criteria andTypeIdGreaterThan(Integer value) {
            addCriterion("type_id >", value, "typeId");
            return (Criteria) this;
        }

        public Criteria andTypeIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("type_id >=", value, "typeId");
            return (Criteria) this;
        }

        public Criteria andTypeIdLessThan(Integer value) {
            addCriterion("type_id <", value, "typeId");
            return (Criteria) this;
        }

        public Criteria andTypeIdLessThanOrEqualTo(Integer value) {
            addCriterion("type_id <=", value, "typeId");
            return (Criteria) this;
        }

        public Criteria andTypeIdIn(List<Integer> values) {
            addCriterion("type_id in", values, "typeId");
            return (Criteria) this;
        }

        public Criteria andTypeIdNotIn(List<Integer> values) {
            addCriterion("type_id not in", values, "typeId");
            return (Criteria) this;
        }

        public Criteria andTypeIdBetween(Integer value1, Integer value2) {
            addCriterion("type_id between", value1, value2, "typeId");
            return (Criteria) this;
        }

        public Criteria andTypeIdNotBetween(Integer value1, Integer value2) {
            addCriterion("type_id not between", value1, value2, "typeId");
            return (Criteria) this;
        }

        public Criteria andPostIdIsNull() {
            addCriterion("post_id is null");
            return (Criteria) this;
        }

        public Criteria andPostIdIsNotNull() {
            addCriterion("post_id is not null");
            return (Criteria) this;
        }

        public Criteria andPostIdEqualTo(Integer value) {
            addCriterion("post_id =", value, "postId");
            return (Criteria) this;
        }

        public Criteria andPostIdNotEqualTo(Integer value) {
            addCriterion("post_id <>", value, "postId");
            return (Criteria) this;
        }

        public Criteria andPostIdGreaterThan(Integer value) {
            addCriterion("post_id >", value, "postId");
            return (Criteria) this;
        }

        public Criteria andPostIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("post_id >=", value, "postId");
            return (Criteria) this;
        }

        public Criteria andPostIdLessThan(Integer value) {
            addCriterion("post_id <", value, "postId");
            return (Criteria) this;
        }

        public Criteria andPostIdLessThanOrEqualTo(Integer value) {
            addCriterion("post_id <=", value, "postId");
            return (Criteria) this;
        }

        public Criteria andPostIdIn(List<Integer> values) {
            addCriterion("post_id in", values, "postId");
            return (Criteria) this;
        }

        public Criteria andPostIdNotIn(List<Integer> values) {
            addCriterion("post_id not in", values, "postId");
            return (Criteria) this;
        }

        public Criteria andPostIdBetween(Integer value1, Integer value2) {
            addCriterion("post_id between", value1, value2, "postId");
            return (Criteria) this;
        }

        public Criteria andPostIdNotBetween(Integer value1, Integer value2) {
            addCriterion("post_id not between", value1, value2, "postId");
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

        public Criteria andDispatchCadreIdIsNull() {
            addCriterion("dispatch_cadre_id is null");
            return (Criteria) this;
        }

        public Criteria andDispatchCadreIdIsNotNull() {
            addCriterion("dispatch_cadre_id is not null");
            return (Criteria) this;
        }

        public Criteria andDispatchCadreIdEqualTo(Integer value) {
            addCriterion("dispatch_cadre_id =", value, "dispatchCadreId");
            return (Criteria) this;
        }

        public Criteria andDispatchCadreIdNotEqualTo(Integer value) {
            addCriterion("dispatch_cadre_id <>", value, "dispatchCadreId");
            return (Criteria) this;
        }

        public Criteria andDispatchCadreIdGreaterThan(Integer value) {
            addCriterion("dispatch_cadre_id >", value, "dispatchCadreId");
            return (Criteria) this;
        }

        public Criteria andDispatchCadreIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("dispatch_cadre_id >=", value, "dispatchCadreId");
            return (Criteria) this;
        }

        public Criteria andDispatchCadreIdLessThan(Integer value) {
            addCriterion("dispatch_cadre_id <", value, "dispatchCadreId");
            return (Criteria) this;
        }

        public Criteria andDispatchCadreIdLessThanOrEqualTo(Integer value) {
            addCriterion("dispatch_cadre_id <=", value, "dispatchCadreId");
            return (Criteria) this;
        }

        public Criteria andDispatchCadreIdIn(List<Integer> values) {
            addCriterion("dispatch_cadre_id in", values, "dispatchCadreId");
            return (Criteria) this;
        }

        public Criteria andDispatchCadreIdNotIn(List<Integer> values) {
            addCriterion("dispatch_cadre_id not in", values, "dispatchCadreId");
            return (Criteria) this;
        }

        public Criteria andDispatchCadreIdBetween(Integer value1, Integer value2) {
            addCriterion("dispatch_cadre_id between", value1, value2, "dispatchCadreId");
            return (Criteria) this;
        }

        public Criteria andDispatchCadreIdNotBetween(Integer value1, Integer value2) {
            addCriterion("dispatch_cadre_id not between", value1, value2, "dispatchCadreId");
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

        public Criteria andDpTypeIdIsNull() {
            addCriterion("dp_type_id is null");
            return (Criteria) this;
        }

        public Criteria andDpTypeIdIsNotNull() {
            addCriterion("dp_type_id is not null");
            return (Criteria) this;
        }

        public Criteria andDpTypeIdEqualTo(Integer value) {
            addCriterion("dp_type_id =", value, "dpTypeId");
            return (Criteria) this;
        }

        public Criteria andDpTypeIdNotEqualTo(Integer value) {
            addCriterion("dp_type_id <>", value, "dpTypeId");
            return (Criteria) this;
        }

        public Criteria andDpTypeIdGreaterThan(Integer value) {
            addCriterion("dp_type_id >", value, "dpTypeId");
            return (Criteria) this;
        }

        public Criteria andDpTypeIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("dp_type_id >=", value, "dpTypeId");
            return (Criteria) this;
        }

        public Criteria andDpTypeIdLessThan(Integer value) {
            addCriterion("dp_type_id <", value, "dpTypeId");
            return (Criteria) this;
        }

        public Criteria andDpTypeIdLessThanOrEqualTo(Integer value) {
            addCriterion("dp_type_id <=", value, "dpTypeId");
            return (Criteria) this;
        }

        public Criteria andDpTypeIdIn(List<Integer> values) {
            addCriterion("dp_type_id in", values, "dpTypeId");
            return (Criteria) this;
        }

        public Criteria andDpTypeIdNotIn(List<Integer> values) {
            addCriterion("dp_type_id not in", values, "dpTypeId");
            return (Criteria) this;
        }

        public Criteria andDpTypeIdBetween(Integer value1, Integer value2) {
            addCriterion("dp_type_id between", value1, value2, "dpTypeId");
            return (Criteria) this;
        }

        public Criteria andDpTypeIdNotBetween(Integer value1, Integer value2) {
            addCriterion("dp_type_id not between", value1, value2, "dpTypeId");
            return (Criteria) this;
        }

        public Criteria andDpAddTimeIsNull() {
            addCriterion("dp_add_time is null");
            return (Criteria) this;
        }

        public Criteria andDpAddTimeIsNotNull() {
            addCriterion("dp_add_time is not null");
            return (Criteria) this;
        }

        public Criteria andDpAddTimeEqualTo(Date value) {
            addCriterionForJDBCDate("dp_add_time =", value, "dpAddTime");
            return (Criteria) this;
        }

        public Criteria andDpAddTimeNotEqualTo(Date value) {
            addCriterionForJDBCDate("dp_add_time <>", value, "dpAddTime");
            return (Criteria) this;
        }

        public Criteria andDpAddTimeGreaterThan(Date value) {
            addCriterionForJDBCDate("dp_add_time >", value, "dpAddTime");
            return (Criteria) this;
        }

        public Criteria andDpAddTimeGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("dp_add_time >=", value, "dpAddTime");
            return (Criteria) this;
        }

        public Criteria andDpAddTimeLessThan(Date value) {
            addCriterionForJDBCDate("dp_add_time <", value, "dpAddTime");
            return (Criteria) this;
        }

        public Criteria andDpAddTimeLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("dp_add_time <=", value, "dpAddTime");
            return (Criteria) this;
        }

        public Criteria andDpAddTimeIn(List<Date> values) {
            addCriterionForJDBCDate("dp_add_time in", values, "dpAddTime");
            return (Criteria) this;
        }

        public Criteria andDpAddTimeNotIn(List<Date> values) {
            addCriterionForJDBCDate("dp_add_time not in", values, "dpAddTime");
            return (Criteria) this;
        }

        public Criteria andDpAddTimeBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("dp_add_time between", value1, value2, "dpAddTime");
            return (Criteria) this;
        }

        public Criteria andDpAddTimeNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("dp_add_time not between", value1, value2, "dpAddTime");
            return (Criteria) this;
        }

        public Criteria andDpPostIsNull() {
            addCriterion("dp_post is null");
            return (Criteria) this;
        }

        public Criteria andDpPostIsNotNull() {
            addCriterion("dp_post is not null");
            return (Criteria) this;
        }

        public Criteria andDpPostEqualTo(String value) {
            addCriterion("dp_post =", value, "dpPost");
            return (Criteria) this;
        }

        public Criteria andDpPostNotEqualTo(String value) {
            addCriterion("dp_post <>", value, "dpPost");
            return (Criteria) this;
        }

        public Criteria andDpPostGreaterThan(String value) {
            addCriterion("dp_post >", value, "dpPost");
            return (Criteria) this;
        }

        public Criteria andDpPostGreaterThanOrEqualTo(String value) {
            addCriterion("dp_post >=", value, "dpPost");
            return (Criteria) this;
        }

        public Criteria andDpPostLessThan(String value) {
            addCriterion("dp_post <", value, "dpPost");
            return (Criteria) this;
        }

        public Criteria andDpPostLessThanOrEqualTo(String value) {
            addCriterion("dp_post <=", value, "dpPost");
            return (Criteria) this;
        }

        public Criteria andDpPostLike(String value) {
            addCriterion("dp_post like", value, "dpPost");
            return (Criteria) this;
        }

        public Criteria andDpPostNotLike(String value) {
            addCriterion("dp_post not like", value, "dpPost");
            return (Criteria) this;
        }

        public Criteria andDpPostIn(List<String> values) {
            addCriterion("dp_post in", values, "dpPost");
            return (Criteria) this;
        }

        public Criteria andDpPostNotIn(List<String> values) {
            addCriterion("dp_post not in", values, "dpPost");
            return (Criteria) this;
        }

        public Criteria andDpPostBetween(String value1, String value2) {
            addCriterion("dp_post between", value1, value2, "dpPost");
            return (Criteria) this;
        }

        public Criteria andDpPostNotBetween(String value1, String value2) {
            addCriterion("dp_post not between", value1, value2, "dpPost");
            return (Criteria) this;
        }

        public Criteria andDpRemarkIsNull() {
            addCriterion("dp_remark is null");
            return (Criteria) this;
        }

        public Criteria andDpRemarkIsNotNull() {
            addCriterion("dp_remark is not null");
            return (Criteria) this;
        }

        public Criteria andDpRemarkEqualTo(String value) {
            addCriterion("dp_remark =", value, "dpRemark");
            return (Criteria) this;
        }

        public Criteria andDpRemarkNotEqualTo(String value) {
            addCriterion("dp_remark <>", value, "dpRemark");
            return (Criteria) this;
        }

        public Criteria andDpRemarkGreaterThan(String value) {
            addCriterion("dp_remark >", value, "dpRemark");
            return (Criteria) this;
        }

        public Criteria andDpRemarkGreaterThanOrEqualTo(String value) {
            addCriterion("dp_remark >=", value, "dpRemark");
            return (Criteria) this;
        }

        public Criteria andDpRemarkLessThan(String value) {
            addCriterion("dp_remark <", value, "dpRemark");
            return (Criteria) this;
        }

        public Criteria andDpRemarkLessThanOrEqualTo(String value) {
            addCriterion("dp_remark <=", value, "dpRemark");
            return (Criteria) this;
        }

        public Criteria andDpRemarkLike(String value) {
            addCriterion("dp_remark like", value, "dpRemark");
            return (Criteria) this;
        }

        public Criteria andDpRemarkNotLike(String value) {
            addCriterion("dp_remark not like", value, "dpRemark");
            return (Criteria) this;
        }

        public Criteria andDpRemarkIn(List<String> values) {
            addCriterion("dp_remark in", values, "dpRemark");
            return (Criteria) this;
        }

        public Criteria andDpRemarkNotIn(List<String> values) {
            addCriterion("dp_remark not in", values, "dpRemark");
            return (Criteria) this;
        }

        public Criteria andDpRemarkBetween(String value1, String value2) {
            addCriterion("dp_remark between", value1, value2, "dpRemark");
            return (Criteria) this;
        }

        public Criteria andDpRemarkNotBetween(String value1, String value2) {
            addCriterion("dp_remark not between", value1, value2, "dpRemark");
            return (Criteria) this;
        }

        public Criteria andIsDpIsNull() {
            addCriterion("is_dp is null");
            return (Criteria) this;
        }

        public Criteria andIsDpIsNotNull() {
            addCriterion("is_dp is not null");
            return (Criteria) this;
        }

        public Criteria andIsDpEqualTo(Boolean value) {
            addCriterion("is_dp =", value, "isDp");
            return (Criteria) this;
        }

        public Criteria andIsDpNotEqualTo(Boolean value) {
            addCriterion("is_dp <>", value, "isDp");
            return (Criteria) this;
        }

        public Criteria andIsDpGreaterThan(Boolean value) {
            addCriterion("is_dp >", value, "isDp");
            return (Criteria) this;
        }

        public Criteria andIsDpGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_dp >=", value, "isDp");
            return (Criteria) this;
        }

        public Criteria andIsDpLessThan(Boolean value) {
            addCriterion("is_dp <", value, "isDp");
            return (Criteria) this;
        }

        public Criteria andIsDpLessThanOrEqualTo(Boolean value) {
            addCriterion("is_dp <=", value, "isDp");
            return (Criteria) this;
        }

        public Criteria andIsDpIn(List<Boolean> values) {
            addCriterion("is_dp in", values, "isDp");
            return (Criteria) this;
        }

        public Criteria andIsDpNotIn(List<Boolean> values) {
            addCriterion("is_dp not in", values, "isDp");
            return (Criteria) this;
        }

        public Criteria andIsDpBetween(Boolean value1, Boolean value2) {
            addCriterion("is_dp between", value1, value2, "isDp");
            return (Criteria) this;
        }

        public Criteria andIsDpNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_dp not between", value1, value2, "isDp");
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

        public Criteria andMsgTitleIsNull() {
            addCriterion("msg_title is null");
            return (Criteria) this;
        }

        public Criteria andMsgTitleIsNotNull() {
            addCriterion("msg_title is not null");
            return (Criteria) this;
        }

        public Criteria andMsgTitleEqualTo(String value) {
            addCriterion("msg_title =", value, "msgTitle");
            return (Criteria) this;
        }

        public Criteria andMsgTitleNotEqualTo(String value) {
            addCriterion("msg_title <>", value, "msgTitle");
            return (Criteria) this;
        }

        public Criteria andMsgTitleGreaterThan(String value) {
            addCriterion("msg_title >", value, "msgTitle");
            return (Criteria) this;
        }

        public Criteria andMsgTitleGreaterThanOrEqualTo(String value) {
            addCriterion("msg_title >=", value, "msgTitle");
            return (Criteria) this;
        }

        public Criteria andMsgTitleLessThan(String value) {
            addCriterion("msg_title <", value, "msgTitle");
            return (Criteria) this;
        }

        public Criteria andMsgTitleLessThanOrEqualTo(String value) {
            addCriterion("msg_title <=", value, "msgTitle");
            return (Criteria) this;
        }

        public Criteria andMsgTitleLike(String value) {
            addCriterion("msg_title like", value, "msgTitle");
            return (Criteria) this;
        }

        public Criteria andMsgTitleNotLike(String value) {
            addCriterion("msg_title not like", value, "msgTitle");
            return (Criteria) this;
        }

        public Criteria andMsgTitleIn(List<String> values) {
            addCriterion("msg_title in", values, "msgTitle");
            return (Criteria) this;
        }

        public Criteria andMsgTitleNotIn(List<String> values) {
            addCriterion("msg_title not in", values, "msgTitle");
            return (Criteria) this;
        }

        public Criteria andMsgTitleBetween(String value1, String value2) {
            addCriterion("msg_title between", value1, value2, "msgTitle");
            return (Criteria) this;
        }

        public Criteria andMsgTitleNotBetween(String value1, String value2) {
            addCriterion("msg_title not between", value1, value2, "msgTitle");
            return (Criteria) this;
        }

        public Criteria andMobileIsNull() {
            addCriterion("mobile is null");
            return (Criteria) this;
        }

        public Criteria andMobileIsNotNull() {
            addCriterion("mobile is not null");
            return (Criteria) this;
        }

        public Criteria andMobileEqualTo(String value) {
            addCriterion("mobile =", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileNotEqualTo(String value) {
            addCriterion("mobile <>", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileGreaterThan(String value) {
            addCriterion("mobile >", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileGreaterThanOrEqualTo(String value) {
            addCriterion("mobile >=", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileLessThan(String value) {
            addCriterion("mobile <", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileLessThanOrEqualTo(String value) {
            addCriterion("mobile <=", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileLike(String value) {
            addCriterion("mobile like", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileNotLike(String value) {
            addCriterion("mobile not like", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileIn(List<String> values) {
            addCriterion("mobile in", values, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileNotIn(List<String> values) {
            addCriterion("mobile not in", values, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileBetween(String value1, String value2) {
            addCriterion("mobile between", value1, value2, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileNotBetween(String value1, String value2) {
            addCriterion("mobile not between", value1, value2, "mobile");
            return (Criteria) this;
        }

        public Criteria andPhoneIsNull() {
            addCriterion("phone is null");
            return (Criteria) this;
        }

        public Criteria andPhoneIsNotNull() {
            addCriterion("phone is not null");
            return (Criteria) this;
        }

        public Criteria andPhoneEqualTo(String value) {
            addCriterion("phone =", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneNotEqualTo(String value) {
            addCriterion("phone <>", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneGreaterThan(String value) {
            addCriterion("phone >", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneGreaterThanOrEqualTo(String value) {
            addCriterion("phone >=", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneLessThan(String value) {
            addCriterion("phone <", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneLessThanOrEqualTo(String value) {
            addCriterion("phone <=", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneLike(String value) {
            addCriterion("phone like", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneNotLike(String value) {
            addCriterion("phone not like", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneIn(List<String> values) {
            addCriterion("phone in", values, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneNotIn(List<String> values) {
            addCriterion("phone not in", values, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneBetween(String value1, String value2) {
            addCriterion("phone between", value1, value2, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneNotBetween(String value1, String value2) {
            addCriterion("phone not between", value1, value2, "phone");
            return (Criteria) this;
        }

        public Criteria andHomePhoneIsNull() {
            addCriterion("home_phone is null");
            return (Criteria) this;
        }

        public Criteria andHomePhoneIsNotNull() {
            addCriterion("home_phone is not null");
            return (Criteria) this;
        }

        public Criteria andHomePhoneEqualTo(String value) {
            addCriterion("home_phone =", value, "homePhone");
            return (Criteria) this;
        }

        public Criteria andHomePhoneNotEqualTo(String value) {
            addCriterion("home_phone <>", value, "homePhone");
            return (Criteria) this;
        }

        public Criteria andHomePhoneGreaterThan(String value) {
            addCriterion("home_phone >", value, "homePhone");
            return (Criteria) this;
        }

        public Criteria andHomePhoneGreaterThanOrEqualTo(String value) {
            addCriterion("home_phone >=", value, "homePhone");
            return (Criteria) this;
        }

        public Criteria andHomePhoneLessThan(String value) {
            addCriterion("home_phone <", value, "homePhone");
            return (Criteria) this;
        }

        public Criteria andHomePhoneLessThanOrEqualTo(String value) {
            addCriterion("home_phone <=", value, "homePhone");
            return (Criteria) this;
        }

        public Criteria andHomePhoneLike(String value) {
            addCriterion("home_phone like", value, "homePhone");
            return (Criteria) this;
        }

        public Criteria andHomePhoneNotLike(String value) {
            addCriterion("home_phone not like", value, "homePhone");
            return (Criteria) this;
        }

        public Criteria andHomePhoneIn(List<String> values) {
            addCriterion("home_phone in", values, "homePhone");
            return (Criteria) this;
        }

        public Criteria andHomePhoneNotIn(List<String> values) {
            addCriterion("home_phone not in", values, "homePhone");
            return (Criteria) this;
        }

        public Criteria andHomePhoneBetween(String value1, String value2) {
            addCriterion("home_phone between", value1, value2, "homePhone");
            return (Criteria) this;
        }

        public Criteria andHomePhoneNotBetween(String value1, String value2) {
            addCriterion("home_phone not between", value1, value2, "homePhone");
            return (Criteria) this;
        }

        public Criteria andEmailIsNull() {
            addCriterion("email is null");
            return (Criteria) this;
        }

        public Criteria andEmailIsNotNull() {
            addCriterion("email is not null");
            return (Criteria) this;
        }

        public Criteria andEmailEqualTo(String value) {
            addCriterion("email =", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailNotEqualTo(String value) {
            addCriterion("email <>", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailGreaterThan(String value) {
            addCriterion("email >", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailGreaterThanOrEqualTo(String value) {
            addCriterion("email >=", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailLessThan(String value) {
            addCriterion("email <", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailLessThanOrEqualTo(String value) {
            addCriterion("email <=", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailLike(String value) {
            addCriterion("email like", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailNotLike(String value) {
            addCriterion("email not like", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailIn(List<String> values) {
            addCriterion("email in", values, "email");
            return (Criteria) this;
        }

        public Criteria andEmailNotIn(List<String> values) {
            addCriterion("email not in", values, "email");
            return (Criteria) this;
        }

        public Criteria andEmailBetween(String value1, String value2) {
            addCriterion("email between", value1, value2, "email");
            return (Criteria) this;
        }

        public Criteria andEmailNotBetween(String value1, String value2) {
            addCriterion("email not between", value1, value2, "email");
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

        public Criteria andGenderIsNull() {
            addCriterion("gender is null");
            return (Criteria) this;
        }

        public Criteria andGenderIsNotNull() {
            addCriterion("gender is not null");
            return (Criteria) this;
        }

        public Criteria andGenderEqualTo(Byte value) {
            addCriterion("gender =", value, "gender");
            return (Criteria) this;
        }

        public Criteria andGenderNotEqualTo(Byte value) {
            addCriterion("gender <>", value, "gender");
            return (Criteria) this;
        }

        public Criteria andGenderGreaterThan(Byte value) {
            addCriterion("gender >", value, "gender");
            return (Criteria) this;
        }

        public Criteria andGenderGreaterThanOrEqualTo(Byte value) {
            addCriterion("gender >=", value, "gender");
            return (Criteria) this;
        }

        public Criteria andGenderLessThan(Byte value) {
            addCriterion("gender <", value, "gender");
            return (Criteria) this;
        }

        public Criteria andGenderLessThanOrEqualTo(Byte value) {
            addCriterion("gender <=", value, "gender");
            return (Criteria) this;
        }

        public Criteria andGenderIn(List<Byte> values) {
            addCriterion("gender in", values, "gender");
            return (Criteria) this;
        }

        public Criteria andGenderNotIn(List<Byte> values) {
            addCriterion("gender not in", values, "gender");
            return (Criteria) this;
        }

        public Criteria andGenderBetween(Byte value1, Byte value2) {
            addCriterion("gender between", value1, value2, "gender");
            return (Criteria) this;
        }

        public Criteria andGenderNotBetween(Byte value1, Byte value2) {
            addCriterion("gender not between", value1, value2, "gender");
            return (Criteria) this;
        }

        public Criteria andNationIsNull() {
            addCriterion("nation is null");
            return (Criteria) this;
        }

        public Criteria andNationIsNotNull() {
            addCriterion("nation is not null");
            return (Criteria) this;
        }

        public Criteria andNationEqualTo(String value) {
            addCriterion("nation =", value, "nation");
            return (Criteria) this;
        }

        public Criteria andNationNotEqualTo(String value) {
            addCriterion("nation <>", value, "nation");
            return (Criteria) this;
        }

        public Criteria andNationGreaterThan(String value) {
            addCriterion("nation >", value, "nation");
            return (Criteria) this;
        }

        public Criteria andNationGreaterThanOrEqualTo(String value) {
            addCriterion("nation >=", value, "nation");
            return (Criteria) this;
        }

        public Criteria andNationLessThan(String value) {
            addCriterion("nation <", value, "nation");
            return (Criteria) this;
        }

        public Criteria andNationLessThanOrEqualTo(String value) {
            addCriterion("nation <=", value, "nation");
            return (Criteria) this;
        }

        public Criteria andNationLike(String value) {
            addCriterion("nation like", value, "nation");
            return (Criteria) this;
        }

        public Criteria andNationNotLike(String value) {
            addCriterion("nation not like", value, "nation");
            return (Criteria) this;
        }

        public Criteria andNationIn(List<String> values) {
            addCriterion("nation in", values, "nation");
            return (Criteria) this;
        }

        public Criteria andNationNotIn(List<String> values) {
            addCriterion("nation not in", values, "nation");
            return (Criteria) this;
        }

        public Criteria andNationBetween(String value1, String value2) {
            addCriterion("nation between", value1, value2, "nation");
            return (Criteria) this;
        }

        public Criteria andNationNotBetween(String value1, String value2) {
            addCriterion("nation not between", value1, value2, "nation");
            return (Criteria) this;
        }

        public Criteria andNativePlaceIsNull() {
            addCriterion("native_place is null");
            return (Criteria) this;
        }

        public Criteria andNativePlaceIsNotNull() {
            addCriterion("native_place is not null");
            return (Criteria) this;
        }

        public Criteria andNativePlaceEqualTo(String value) {
            addCriterion("native_place =", value, "nativePlace");
            return (Criteria) this;
        }

        public Criteria andNativePlaceNotEqualTo(String value) {
            addCriterion("native_place <>", value, "nativePlace");
            return (Criteria) this;
        }

        public Criteria andNativePlaceGreaterThan(String value) {
            addCriterion("native_place >", value, "nativePlace");
            return (Criteria) this;
        }

        public Criteria andNativePlaceGreaterThanOrEqualTo(String value) {
            addCriterion("native_place >=", value, "nativePlace");
            return (Criteria) this;
        }

        public Criteria andNativePlaceLessThan(String value) {
            addCriterion("native_place <", value, "nativePlace");
            return (Criteria) this;
        }

        public Criteria andNativePlaceLessThanOrEqualTo(String value) {
            addCriterion("native_place <=", value, "nativePlace");
            return (Criteria) this;
        }

        public Criteria andNativePlaceLike(String value) {
            addCriterion("native_place like", value, "nativePlace");
            return (Criteria) this;
        }

        public Criteria andNativePlaceNotLike(String value) {
            addCriterion("native_place not like", value, "nativePlace");
            return (Criteria) this;
        }

        public Criteria andNativePlaceIn(List<String> values) {
            addCriterion("native_place in", values, "nativePlace");
            return (Criteria) this;
        }

        public Criteria andNativePlaceNotIn(List<String> values) {
            addCriterion("native_place not in", values, "nativePlace");
            return (Criteria) this;
        }

        public Criteria andNativePlaceBetween(String value1, String value2) {
            addCriterion("native_place between", value1, value2, "nativePlace");
            return (Criteria) this;
        }

        public Criteria andNativePlaceNotBetween(String value1, String value2) {
            addCriterion("native_place not between", value1, value2, "nativePlace");
            return (Criteria) this;
        }

        public Criteria andIdcardIsNull() {
            addCriterion("idcard is null");
            return (Criteria) this;
        }

        public Criteria andIdcardIsNotNull() {
            addCriterion("idcard is not null");
            return (Criteria) this;
        }

        public Criteria andIdcardEqualTo(String value) {
            addCriterion("idcard =", value, "idcard");
            return (Criteria) this;
        }

        public Criteria andIdcardNotEqualTo(String value) {
            addCriterion("idcard <>", value, "idcard");
            return (Criteria) this;
        }

        public Criteria andIdcardGreaterThan(String value) {
            addCriterion("idcard >", value, "idcard");
            return (Criteria) this;
        }

        public Criteria andIdcardGreaterThanOrEqualTo(String value) {
            addCriterion("idcard >=", value, "idcard");
            return (Criteria) this;
        }

        public Criteria andIdcardLessThan(String value) {
            addCriterion("idcard <", value, "idcard");
            return (Criteria) this;
        }

        public Criteria andIdcardLessThanOrEqualTo(String value) {
            addCriterion("idcard <=", value, "idcard");
            return (Criteria) this;
        }

        public Criteria andIdcardLike(String value) {
            addCriterion("idcard like", value, "idcard");
            return (Criteria) this;
        }

        public Criteria andIdcardNotLike(String value) {
            addCriterion("idcard not like", value, "idcard");
            return (Criteria) this;
        }

        public Criteria andIdcardIn(List<String> values) {
            addCriterion("idcard in", values, "idcard");
            return (Criteria) this;
        }

        public Criteria andIdcardNotIn(List<String> values) {
            addCriterion("idcard not in", values, "idcard");
            return (Criteria) this;
        }

        public Criteria andIdcardBetween(String value1, String value2) {
            addCriterion("idcard between", value1, value2, "idcard");
            return (Criteria) this;
        }

        public Criteria andIdcardNotBetween(String value1, String value2) {
            addCriterion("idcard not between", value1, value2, "idcard");
            return (Criteria) this;
        }

        public Criteria andBirthIsNull() {
            addCriterion("birth is null");
            return (Criteria) this;
        }

        public Criteria andBirthIsNotNull() {
            addCriterion("birth is not null");
            return (Criteria) this;
        }

        public Criteria andBirthEqualTo(Date value) {
            addCriterionForJDBCDate("birth =", value, "birth");
            return (Criteria) this;
        }

        public Criteria andBirthNotEqualTo(Date value) {
            addCriterionForJDBCDate("birth <>", value, "birth");
            return (Criteria) this;
        }

        public Criteria andBirthGreaterThan(Date value) {
            addCriterionForJDBCDate("birth >", value, "birth");
            return (Criteria) this;
        }

        public Criteria andBirthGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("birth >=", value, "birth");
            return (Criteria) this;
        }

        public Criteria andBirthLessThan(Date value) {
            addCriterionForJDBCDate("birth <", value, "birth");
            return (Criteria) this;
        }

        public Criteria andBirthLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("birth <=", value, "birth");
            return (Criteria) this;
        }

        public Criteria andBirthIn(List<Date> values) {
            addCriterionForJDBCDate("birth in", values, "birth");
            return (Criteria) this;
        }

        public Criteria andBirthNotIn(List<Date> values) {
            addCriterionForJDBCDate("birth not in", values, "birth");
            return (Criteria) this;
        }

        public Criteria andBirthBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("birth between", value1, value2, "birth");
            return (Criteria) this;
        }

        public Criteria andBirthNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("birth not between", value1, value2, "birth");
            return (Criteria) this;
        }

        public Criteria andPartyIdIsNull() {
            addCriterion("party_id is null");
            return (Criteria) this;
        }

        public Criteria andPartyIdIsNotNull() {
            addCriterion("party_id is not null");
            return (Criteria) this;
        }

        public Criteria andPartyIdEqualTo(Integer value) {
            addCriterion("party_id =", value, "partyId");
            return (Criteria) this;
        }

        public Criteria andPartyIdNotEqualTo(Integer value) {
            addCriterion("party_id <>", value, "partyId");
            return (Criteria) this;
        }

        public Criteria andPartyIdGreaterThan(Integer value) {
            addCriterion("party_id >", value, "partyId");
            return (Criteria) this;
        }

        public Criteria andPartyIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("party_id >=", value, "partyId");
            return (Criteria) this;
        }

        public Criteria andPartyIdLessThan(Integer value) {
            addCriterion("party_id <", value, "partyId");
            return (Criteria) this;
        }

        public Criteria andPartyIdLessThanOrEqualTo(Integer value) {
            addCriterion("party_id <=", value, "partyId");
            return (Criteria) this;
        }

        public Criteria andPartyIdIn(List<Integer> values) {
            addCriterion("party_id in", values, "partyId");
            return (Criteria) this;
        }

        public Criteria andPartyIdNotIn(List<Integer> values) {
            addCriterion("party_id not in", values, "partyId");
            return (Criteria) this;
        }

        public Criteria andPartyIdBetween(Integer value1, Integer value2) {
            addCriterion("party_id between", value1, value2, "partyId");
            return (Criteria) this;
        }

        public Criteria andPartyIdNotBetween(Integer value1, Integer value2) {
            addCriterion("party_id not between", value1, value2, "partyId");
            return (Criteria) this;
        }

        public Criteria andBranchIdIsNull() {
            addCriterion("branch_id is null");
            return (Criteria) this;
        }

        public Criteria andBranchIdIsNotNull() {
            addCriterion("branch_id is not null");
            return (Criteria) this;
        }

        public Criteria andBranchIdEqualTo(Integer value) {
            addCriterion("branch_id =", value, "branchId");
            return (Criteria) this;
        }

        public Criteria andBranchIdNotEqualTo(Integer value) {
            addCriterion("branch_id <>", value, "branchId");
            return (Criteria) this;
        }

        public Criteria andBranchIdGreaterThan(Integer value) {
            addCriterion("branch_id >", value, "branchId");
            return (Criteria) this;
        }

        public Criteria andBranchIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("branch_id >=", value, "branchId");
            return (Criteria) this;
        }

        public Criteria andBranchIdLessThan(Integer value) {
            addCriterion("branch_id <", value, "branchId");
            return (Criteria) this;
        }

        public Criteria andBranchIdLessThanOrEqualTo(Integer value) {
            addCriterion("branch_id <=", value, "branchId");
            return (Criteria) this;
        }

        public Criteria andBranchIdIn(List<Integer> values) {
            addCriterion("branch_id in", values, "branchId");
            return (Criteria) this;
        }

        public Criteria andBranchIdNotIn(List<Integer> values) {
            addCriterion("branch_id not in", values, "branchId");
            return (Criteria) this;
        }

        public Criteria andBranchIdBetween(Integer value1, Integer value2) {
            addCriterion("branch_id between", value1, value2, "branchId");
            return (Criteria) this;
        }

        public Criteria andBranchIdNotBetween(Integer value1, Integer value2) {
            addCriterion("branch_id not between", value1, value2, "branchId");
            return (Criteria) this;
        }

        public Criteria andGrowTimeIsNull() {
            addCriterion("grow_time is null");
            return (Criteria) this;
        }

        public Criteria andGrowTimeIsNotNull() {
            addCriterion("grow_time is not null");
            return (Criteria) this;
        }

        public Criteria andGrowTimeEqualTo(Date value) {
            addCriterionForJDBCDate("grow_time =", value, "growTime");
            return (Criteria) this;
        }

        public Criteria andGrowTimeNotEqualTo(Date value) {
            addCriterionForJDBCDate("grow_time <>", value, "growTime");
            return (Criteria) this;
        }

        public Criteria andGrowTimeGreaterThan(Date value) {
            addCriterionForJDBCDate("grow_time >", value, "growTime");
            return (Criteria) this;
        }

        public Criteria andGrowTimeGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("grow_time >=", value, "growTime");
            return (Criteria) this;
        }

        public Criteria andGrowTimeLessThan(Date value) {
            addCriterionForJDBCDate("grow_time <", value, "growTime");
            return (Criteria) this;
        }

        public Criteria andGrowTimeLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("grow_time <=", value, "growTime");
            return (Criteria) this;
        }

        public Criteria andGrowTimeIn(List<Date> values) {
            addCriterionForJDBCDate("grow_time in", values, "growTime");
            return (Criteria) this;
        }

        public Criteria andGrowTimeNotIn(List<Date> values) {
            addCriterionForJDBCDate("grow_time not in", values, "growTime");
            return (Criteria) this;
        }

        public Criteria andGrowTimeBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("grow_time between", value1, value2, "growTime");
            return (Criteria) this;
        }

        public Criteria andGrowTimeNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("grow_time not between", value1, value2, "growTime");
            return (Criteria) this;
        }

        public Criteria andMemberStatusIsNull() {
            addCriterion("member_status is null");
            return (Criteria) this;
        }

        public Criteria andMemberStatusIsNotNull() {
            addCriterion("member_status is not null");
            return (Criteria) this;
        }

        public Criteria andMemberStatusEqualTo(Byte value) {
            addCriterion("member_status =", value, "memberStatus");
            return (Criteria) this;
        }

        public Criteria andMemberStatusNotEqualTo(Byte value) {
            addCriterion("member_status <>", value, "memberStatus");
            return (Criteria) this;
        }

        public Criteria andMemberStatusGreaterThan(Byte value) {
            addCriterion("member_status >", value, "memberStatus");
            return (Criteria) this;
        }

        public Criteria andMemberStatusGreaterThanOrEqualTo(Byte value) {
            addCriterion("member_status >=", value, "memberStatus");
            return (Criteria) this;
        }

        public Criteria andMemberStatusLessThan(Byte value) {
            addCriterion("member_status <", value, "memberStatus");
            return (Criteria) this;
        }

        public Criteria andMemberStatusLessThanOrEqualTo(Byte value) {
            addCriterion("member_status <=", value, "memberStatus");
            return (Criteria) this;
        }

        public Criteria andMemberStatusIn(List<Byte> values) {
            addCriterion("member_status in", values, "memberStatus");
            return (Criteria) this;
        }

        public Criteria andMemberStatusNotIn(List<Byte> values) {
            addCriterion("member_status not in", values, "memberStatus");
            return (Criteria) this;
        }

        public Criteria andMemberStatusBetween(Byte value1, Byte value2) {
            addCriterion("member_status between", value1, value2, "memberStatus");
            return (Criteria) this;
        }

        public Criteria andMemberStatusNotBetween(Byte value1, Byte value2) {
            addCriterion("member_status not between", value1, value2, "memberStatus");
            return (Criteria) this;
        }

        public Criteria andCadreGrowTimeIsNull() {
            addCriterion("cadre_grow_time is null");
            return (Criteria) this;
        }

        public Criteria andCadreGrowTimeIsNotNull() {
            addCriterion("cadre_grow_time is not null");
            return (Criteria) this;
        }

        public Criteria andCadreGrowTimeEqualTo(Date value) {
            addCriterionForJDBCDate("cadre_grow_time =", value, "cadreGrowTime");
            return (Criteria) this;
        }

        public Criteria andCadreGrowTimeNotEqualTo(Date value) {
            addCriterionForJDBCDate("cadre_grow_time <>", value, "cadreGrowTime");
            return (Criteria) this;
        }

        public Criteria andCadreGrowTimeGreaterThan(Date value) {
            addCriterionForJDBCDate("cadre_grow_time >", value, "cadreGrowTime");
            return (Criteria) this;
        }

        public Criteria andCadreGrowTimeGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("cadre_grow_time >=", value, "cadreGrowTime");
            return (Criteria) this;
        }

        public Criteria andCadreGrowTimeLessThan(Date value) {
            addCriterionForJDBCDate("cadre_grow_time <", value, "cadreGrowTime");
            return (Criteria) this;
        }

        public Criteria andCadreGrowTimeLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("cadre_grow_time <=", value, "cadreGrowTime");
            return (Criteria) this;
        }

        public Criteria andCadreGrowTimeIn(List<Date> values) {
            addCriterionForJDBCDate("cadre_grow_time in", values, "cadreGrowTime");
            return (Criteria) this;
        }

        public Criteria andCadreGrowTimeNotIn(List<Date> values) {
            addCriterionForJDBCDate("cadre_grow_time not in", values, "cadreGrowTime");
            return (Criteria) this;
        }

        public Criteria andCadreGrowTimeBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("cadre_grow_time between", value1, value2, "cadreGrowTime");
            return (Criteria) this;
        }

        public Criteria andCadreGrowTimeNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("cadre_grow_time not between", value1, value2, "cadreGrowTime");
            return (Criteria) this;
        }

        public Criteria andCadreDpTypeIsNull() {
            addCriterion("cadre_dp_type is null");
            return (Criteria) this;
        }

        public Criteria andCadreDpTypeIsNotNull() {
            addCriterion("cadre_dp_type is not null");
            return (Criteria) this;
        }

        public Criteria andCadreDpTypeEqualTo(Long value) {
            addCriterion("cadre_dp_type =", value, "cadreDpType");
            return (Criteria) this;
        }

        public Criteria andCadreDpTypeNotEqualTo(Long value) {
            addCriterion("cadre_dp_type <>", value, "cadreDpType");
            return (Criteria) this;
        }

        public Criteria andCadreDpTypeGreaterThan(Long value) {
            addCriterion("cadre_dp_type >", value, "cadreDpType");
            return (Criteria) this;
        }

        public Criteria andCadreDpTypeGreaterThanOrEqualTo(Long value) {
            addCriterion("cadre_dp_type >=", value, "cadreDpType");
            return (Criteria) this;
        }

        public Criteria andCadreDpTypeLessThan(Long value) {
            addCriterion("cadre_dp_type <", value, "cadreDpType");
            return (Criteria) this;
        }

        public Criteria andCadreDpTypeLessThanOrEqualTo(Long value) {
            addCriterion("cadre_dp_type <=", value, "cadreDpType");
            return (Criteria) this;
        }

        public Criteria andCadreDpTypeIn(List<Long> values) {
            addCriterion("cadre_dp_type in", values, "cadreDpType");
            return (Criteria) this;
        }

        public Criteria andCadreDpTypeNotIn(List<Long> values) {
            addCriterion("cadre_dp_type not in", values, "cadreDpType");
            return (Criteria) this;
        }

        public Criteria andCadreDpTypeBetween(Long value1, Long value2) {
            addCriterion("cadre_dp_type between", value1, value2, "cadreDpType");
            return (Criteria) this;
        }

        public Criteria andCadreDpTypeNotBetween(Long value1, Long value2) {
            addCriterion("cadre_dp_type not between", value1, value2, "cadreDpType");
            return (Criteria) this;
        }

        public Criteria andEduIdIsNull() {
            addCriterion("edu_id is null");
            return (Criteria) this;
        }

        public Criteria andEduIdIsNotNull() {
            addCriterion("edu_id is not null");
            return (Criteria) this;
        }

        public Criteria andEduIdEqualTo(Integer value) {
            addCriterion("edu_id =", value, "eduId");
            return (Criteria) this;
        }

        public Criteria andEduIdNotEqualTo(Integer value) {
            addCriterion("edu_id <>", value, "eduId");
            return (Criteria) this;
        }

        public Criteria andEduIdGreaterThan(Integer value) {
            addCriterion("edu_id >", value, "eduId");
            return (Criteria) this;
        }

        public Criteria andEduIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("edu_id >=", value, "eduId");
            return (Criteria) this;
        }

        public Criteria andEduIdLessThan(Integer value) {
            addCriterion("edu_id <", value, "eduId");
            return (Criteria) this;
        }

        public Criteria andEduIdLessThanOrEqualTo(Integer value) {
            addCriterion("edu_id <=", value, "eduId");
            return (Criteria) this;
        }

        public Criteria andEduIdIn(List<Integer> values) {
            addCriterion("edu_id in", values, "eduId");
            return (Criteria) this;
        }

        public Criteria andEduIdNotIn(List<Integer> values) {
            addCriterion("edu_id not in", values, "eduId");
            return (Criteria) this;
        }

        public Criteria andEduIdBetween(Integer value1, Integer value2) {
            addCriterion("edu_id between", value1, value2, "eduId");
            return (Criteria) this;
        }

        public Criteria andEduIdNotBetween(Integer value1, Integer value2) {
            addCriterion("edu_id not between", value1, value2, "eduId");
            return (Criteria) this;
        }

        public Criteria andFinishTimeIsNull() {
            addCriterion("finish_time is null");
            return (Criteria) this;
        }

        public Criteria andFinishTimeIsNotNull() {
            addCriterion("finish_time is not null");
            return (Criteria) this;
        }

        public Criteria andFinishTimeEqualTo(Date value) {
            addCriterionForJDBCDate("finish_time =", value, "finishTime");
            return (Criteria) this;
        }

        public Criteria andFinishTimeNotEqualTo(Date value) {
            addCriterionForJDBCDate("finish_time <>", value, "finishTime");
            return (Criteria) this;
        }

        public Criteria andFinishTimeGreaterThan(Date value) {
            addCriterionForJDBCDate("finish_time >", value, "finishTime");
            return (Criteria) this;
        }

        public Criteria andFinishTimeGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("finish_time >=", value, "finishTime");
            return (Criteria) this;
        }

        public Criteria andFinishTimeLessThan(Date value) {
            addCriterionForJDBCDate("finish_time <", value, "finishTime");
            return (Criteria) this;
        }

        public Criteria andFinishTimeLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("finish_time <=", value, "finishTime");
            return (Criteria) this;
        }

        public Criteria andFinishTimeIn(List<Date> values) {
            addCriterionForJDBCDate("finish_time in", values, "finishTime");
            return (Criteria) this;
        }

        public Criteria andFinishTimeNotIn(List<Date> values) {
            addCriterionForJDBCDate("finish_time not in", values, "finishTime");
            return (Criteria) this;
        }

        public Criteria andFinishTimeBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("finish_time between", value1, value2, "finishTime");
            return (Criteria) this;
        }

        public Criteria andFinishTimeNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("finish_time not between", value1, value2, "finishTime");
            return (Criteria) this;
        }

        public Criteria andLearnStyleIsNull() {
            addCriterion("learn_style is null");
            return (Criteria) this;
        }

        public Criteria andLearnStyleIsNotNull() {
            addCriterion("learn_style is not null");
            return (Criteria) this;
        }

        public Criteria andLearnStyleEqualTo(Integer value) {
            addCriterion("learn_style =", value, "learnStyle");
            return (Criteria) this;
        }

        public Criteria andLearnStyleNotEqualTo(Integer value) {
            addCriterion("learn_style <>", value, "learnStyle");
            return (Criteria) this;
        }

        public Criteria andLearnStyleGreaterThan(Integer value) {
            addCriterion("learn_style >", value, "learnStyle");
            return (Criteria) this;
        }

        public Criteria andLearnStyleGreaterThanOrEqualTo(Integer value) {
            addCriterion("learn_style >=", value, "learnStyle");
            return (Criteria) this;
        }

        public Criteria andLearnStyleLessThan(Integer value) {
            addCriterion("learn_style <", value, "learnStyle");
            return (Criteria) this;
        }

        public Criteria andLearnStyleLessThanOrEqualTo(Integer value) {
            addCriterion("learn_style <=", value, "learnStyle");
            return (Criteria) this;
        }

        public Criteria andLearnStyleIn(List<Integer> values) {
            addCriterion("learn_style in", values, "learnStyle");
            return (Criteria) this;
        }

        public Criteria andLearnStyleNotIn(List<Integer> values) {
            addCriterion("learn_style not in", values, "learnStyle");
            return (Criteria) this;
        }

        public Criteria andLearnStyleBetween(Integer value1, Integer value2) {
            addCriterion("learn_style between", value1, value2, "learnStyle");
            return (Criteria) this;
        }

        public Criteria andLearnStyleNotBetween(Integer value1, Integer value2) {
            addCriterion("learn_style not between", value1, value2, "learnStyle");
            return (Criteria) this;
        }

        public Criteria andSchoolIsNull() {
            addCriterion("school is null");
            return (Criteria) this;
        }

        public Criteria andSchoolIsNotNull() {
            addCriterion("school is not null");
            return (Criteria) this;
        }

        public Criteria andSchoolEqualTo(String value) {
            addCriterion("school =", value, "school");
            return (Criteria) this;
        }

        public Criteria andSchoolNotEqualTo(String value) {
            addCriterion("school <>", value, "school");
            return (Criteria) this;
        }

        public Criteria andSchoolGreaterThan(String value) {
            addCriterion("school >", value, "school");
            return (Criteria) this;
        }

        public Criteria andSchoolGreaterThanOrEqualTo(String value) {
            addCriterion("school >=", value, "school");
            return (Criteria) this;
        }

        public Criteria andSchoolLessThan(String value) {
            addCriterion("school <", value, "school");
            return (Criteria) this;
        }

        public Criteria andSchoolLessThanOrEqualTo(String value) {
            addCriterion("school <=", value, "school");
            return (Criteria) this;
        }

        public Criteria andSchoolLike(String value) {
            addCriterion("school like", value, "school");
            return (Criteria) this;
        }

        public Criteria andSchoolNotLike(String value) {
            addCriterion("school not like", value, "school");
            return (Criteria) this;
        }

        public Criteria andSchoolIn(List<String> values) {
            addCriterion("school in", values, "school");
            return (Criteria) this;
        }

        public Criteria andSchoolNotIn(List<String> values) {
            addCriterion("school not in", values, "school");
            return (Criteria) this;
        }

        public Criteria andSchoolBetween(String value1, String value2) {
            addCriterion("school between", value1, value2, "school");
            return (Criteria) this;
        }

        public Criteria andSchoolNotBetween(String value1, String value2) {
            addCriterion("school not between", value1, value2, "school");
            return (Criteria) this;
        }

        public Criteria andDepIsNull() {
            addCriterion("dep is null");
            return (Criteria) this;
        }

        public Criteria andDepIsNotNull() {
            addCriterion("dep is not null");
            return (Criteria) this;
        }

        public Criteria andDepEqualTo(String value) {
            addCriterion("dep =", value, "dep");
            return (Criteria) this;
        }

        public Criteria andDepNotEqualTo(String value) {
            addCriterion("dep <>", value, "dep");
            return (Criteria) this;
        }

        public Criteria andDepGreaterThan(String value) {
            addCriterion("dep >", value, "dep");
            return (Criteria) this;
        }

        public Criteria andDepGreaterThanOrEqualTo(String value) {
            addCriterion("dep >=", value, "dep");
            return (Criteria) this;
        }

        public Criteria andDepLessThan(String value) {
            addCriterion("dep <", value, "dep");
            return (Criteria) this;
        }

        public Criteria andDepLessThanOrEqualTo(String value) {
            addCriterion("dep <=", value, "dep");
            return (Criteria) this;
        }

        public Criteria andDepLike(String value) {
            addCriterion("dep like", value, "dep");
            return (Criteria) this;
        }

        public Criteria andDepNotLike(String value) {
            addCriterion("dep not like", value, "dep");
            return (Criteria) this;
        }

        public Criteria andDepIn(List<String> values) {
            addCriterion("dep in", values, "dep");
            return (Criteria) this;
        }

        public Criteria andDepNotIn(List<String> values) {
            addCriterion("dep not in", values, "dep");
            return (Criteria) this;
        }

        public Criteria andDepBetween(String value1, String value2) {
            addCriterion("dep between", value1, value2, "dep");
            return (Criteria) this;
        }

        public Criteria andDepNotBetween(String value1, String value2) {
            addCriterion("dep not between", value1, value2, "dep");
            return (Criteria) this;
        }

        public Criteria andSchoolTypeIsNull() {
            addCriterion("school_type is null");
            return (Criteria) this;
        }

        public Criteria andSchoolTypeIsNotNull() {
            addCriterion("school_type is not null");
            return (Criteria) this;
        }

        public Criteria andSchoolTypeEqualTo(Byte value) {
            addCriterion("school_type =", value, "schoolType");
            return (Criteria) this;
        }

        public Criteria andSchoolTypeNotEqualTo(Byte value) {
            addCriterion("school_type <>", value, "schoolType");
            return (Criteria) this;
        }

        public Criteria andSchoolTypeGreaterThan(Byte value) {
            addCriterion("school_type >", value, "schoolType");
            return (Criteria) this;
        }

        public Criteria andSchoolTypeGreaterThanOrEqualTo(Byte value) {
            addCriterion("school_type >=", value, "schoolType");
            return (Criteria) this;
        }

        public Criteria andSchoolTypeLessThan(Byte value) {
            addCriterion("school_type <", value, "schoolType");
            return (Criteria) this;
        }

        public Criteria andSchoolTypeLessThanOrEqualTo(Byte value) {
            addCriterion("school_type <=", value, "schoolType");
            return (Criteria) this;
        }

        public Criteria andSchoolTypeIn(List<Byte> values) {
            addCriterion("school_type in", values, "schoolType");
            return (Criteria) this;
        }

        public Criteria andSchoolTypeNotIn(List<Byte> values) {
            addCriterion("school_type not in", values, "schoolType");
            return (Criteria) this;
        }

        public Criteria andSchoolTypeBetween(Byte value1, Byte value2) {
            addCriterion("school_type between", value1, value2, "schoolType");
            return (Criteria) this;
        }

        public Criteria andSchoolTypeNotBetween(Byte value1, Byte value2) {
            addCriterion("school_type not between", value1, value2, "schoolType");
            return (Criteria) this;
        }

        public Criteria andMajorIsNull() {
            addCriterion("major is null");
            return (Criteria) this;
        }

        public Criteria andMajorIsNotNull() {
            addCriterion("major is not null");
            return (Criteria) this;
        }

        public Criteria andMajorEqualTo(String value) {
            addCriterion("major =", value, "major");
            return (Criteria) this;
        }

        public Criteria andMajorNotEqualTo(String value) {
            addCriterion("major <>", value, "major");
            return (Criteria) this;
        }

        public Criteria andMajorGreaterThan(String value) {
            addCriterion("major >", value, "major");
            return (Criteria) this;
        }

        public Criteria andMajorGreaterThanOrEqualTo(String value) {
            addCriterion("major >=", value, "major");
            return (Criteria) this;
        }

        public Criteria andMajorLessThan(String value) {
            addCriterion("major <", value, "major");
            return (Criteria) this;
        }

        public Criteria andMajorLessThanOrEqualTo(String value) {
            addCriterion("major <=", value, "major");
            return (Criteria) this;
        }

        public Criteria andMajorLike(String value) {
            addCriterion("major like", value, "major");
            return (Criteria) this;
        }

        public Criteria andMajorNotLike(String value) {
            addCriterion("major not like", value, "major");
            return (Criteria) this;
        }

        public Criteria andMajorIn(List<String> values) {
            addCriterion("major in", values, "major");
            return (Criteria) this;
        }

        public Criteria andMajorNotIn(List<String> values) {
            addCriterion("major not in", values, "major");
            return (Criteria) this;
        }

        public Criteria andMajorBetween(String value1, String value2) {
            addCriterion("major between", value1, value2, "major");
            return (Criteria) this;
        }

        public Criteria andMajorNotBetween(String value1, String value2) {
            addCriterion("major not between", value1, value2, "major");
            return (Criteria) this;
        }

        public Criteria andDegreeIsNull() {
            addCriterion("degree is null");
            return (Criteria) this;
        }

        public Criteria andDegreeIsNotNull() {
            addCriterion("degree is not null");
            return (Criteria) this;
        }

        public Criteria andDegreeEqualTo(String value) {
            addCriterion("degree =", value, "degree");
            return (Criteria) this;
        }

        public Criteria andDegreeNotEqualTo(String value) {
            addCriterion("degree <>", value, "degree");
            return (Criteria) this;
        }

        public Criteria andDegreeGreaterThan(String value) {
            addCriterion("degree >", value, "degree");
            return (Criteria) this;
        }

        public Criteria andDegreeGreaterThanOrEqualTo(String value) {
            addCriterion("degree >=", value, "degree");
            return (Criteria) this;
        }

        public Criteria andDegreeLessThan(String value) {
            addCriterion("degree <", value, "degree");
            return (Criteria) this;
        }

        public Criteria andDegreeLessThanOrEqualTo(String value) {
            addCriterion("degree <=", value, "degree");
            return (Criteria) this;
        }

        public Criteria andDegreeLike(String value) {
            addCriterion("degree like", value, "degree");
            return (Criteria) this;
        }

        public Criteria andDegreeNotLike(String value) {
            addCriterion("degree not like", value, "degree");
            return (Criteria) this;
        }

        public Criteria andDegreeIn(List<String> values) {
            addCriterion("degree in", values, "degree");
            return (Criteria) this;
        }

        public Criteria andDegreeNotIn(List<String> values) {
            addCriterion("degree not in", values, "degree");
            return (Criteria) this;
        }

        public Criteria andDegreeBetween(String value1, String value2) {
            addCriterion("degree between", value1, value2, "degree");
            return (Criteria) this;
        }

        public Criteria andDegreeNotBetween(String value1, String value2) {
            addCriterion("degree not between", value1, value2, "degree");
            return (Criteria) this;
        }

        public Criteria andPostClassIsNull() {
            addCriterion("post_class is null");
            return (Criteria) this;
        }

        public Criteria andPostClassIsNotNull() {
            addCriterion("post_class is not null");
            return (Criteria) this;
        }

        public Criteria andPostClassEqualTo(String value) {
            addCriterion("post_class =", value, "postClass");
            return (Criteria) this;
        }

        public Criteria andPostClassNotEqualTo(String value) {
            addCriterion("post_class <>", value, "postClass");
            return (Criteria) this;
        }

        public Criteria andPostClassGreaterThan(String value) {
            addCriterion("post_class >", value, "postClass");
            return (Criteria) this;
        }

        public Criteria andPostClassGreaterThanOrEqualTo(String value) {
            addCriterion("post_class >=", value, "postClass");
            return (Criteria) this;
        }

        public Criteria andPostClassLessThan(String value) {
            addCriterion("post_class <", value, "postClass");
            return (Criteria) this;
        }

        public Criteria andPostClassLessThanOrEqualTo(String value) {
            addCriterion("post_class <=", value, "postClass");
            return (Criteria) this;
        }

        public Criteria andPostClassLike(String value) {
            addCriterion("post_class like", value, "postClass");
            return (Criteria) this;
        }

        public Criteria andPostClassNotLike(String value) {
            addCriterion("post_class not like", value, "postClass");
            return (Criteria) this;
        }

        public Criteria andPostClassIn(List<String> values) {
            addCriterion("post_class in", values, "postClass");
            return (Criteria) this;
        }

        public Criteria andPostClassNotIn(List<String> values) {
            addCriterion("post_class not in", values, "postClass");
            return (Criteria) this;
        }

        public Criteria andPostClassBetween(String value1, String value2) {
            addCriterion("post_class between", value1, value2, "postClass");
            return (Criteria) this;
        }

        public Criteria andPostClassNotBetween(String value1, String value2) {
            addCriterion("post_class not between", value1, value2, "postClass");
            return (Criteria) this;
        }

        public Criteria andSubPostClassIsNull() {
            addCriterion("sub_post_class is null");
            return (Criteria) this;
        }

        public Criteria andSubPostClassIsNotNull() {
            addCriterion("sub_post_class is not null");
            return (Criteria) this;
        }

        public Criteria andSubPostClassEqualTo(String value) {
            addCriterion("sub_post_class =", value, "subPostClass");
            return (Criteria) this;
        }

        public Criteria andSubPostClassNotEqualTo(String value) {
            addCriterion("sub_post_class <>", value, "subPostClass");
            return (Criteria) this;
        }

        public Criteria andSubPostClassGreaterThan(String value) {
            addCriterion("sub_post_class >", value, "subPostClass");
            return (Criteria) this;
        }

        public Criteria andSubPostClassGreaterThanOrEqualTo(String value) {
            addCriterion("sub_post_class >=", value, "subPostClass");
            return (Criteria) this;
        }

        public Criteria andSubPostClassLessThan(String value) {
            addCriterion("sub_post_class <", value, "subPostClass");
            return (Criteria) this;
        }

        public Criteria andSubPostClassLessThanOrEqualTo(String value) {
            addCriterion("sub_post_class <=", value, "subPostClass");
            return (Criteria) this;
        }

        public Criteria andSubPostClassLike(String value) {
            addCriterion("sub_post_class like", value, "subPostClass");
            return (Criteria) this;
        }

        public Criteria andSubPostClassNotLike(String value) {
            addCriterion("sub_post_class not like", value, "subPostClass");
            return (Criteria) this;
        }

        public Criteria andSubPostClassIn(List<String> values) {
            addCriterion("sub_post_class in", values, "subPostClass");
            return (Criteria) this;
        }

        public Criteria andSubPostClassNotIn(List<String> values) {
            addCriterion("sub_post_class not in", values, "subPostClass");
            return (Criteria) this;
        }

        public Criteria andSubPostClassBetween(String value1, String value2) {
            addCriterion("sub_post_class between", value1, value2, "subPostClass");
            return (Criteria) this;
        }

        public Criteria andSubPostClassNotBetween(String value1, String value2) {
            addCriterion("sub_post_class not between", value1, value2, "subPostClass");
            return (Criteria) this;
        }

        public Criteria andMainPostLevelIsNull() {
            addCriterion("main_post_level is null");
            return (Criteria) this;
        }

        public Criteria andMainPostLevelIsNotNull() {
            addCriterion("main_post_level is not null");
            return (Criteria) this;
        }

        public Criteria andMainPostLevelEqualTo(String value) {
            addCriterion("main_post_level =", value, "mainPostLevel");
            return (Criteria) this;
        }

        public Criteria andMainPostLevelNotEqualTo(String value) {
            addCriterion("main_post_level <>", value, "mainPostLevel");
            return (Criteria) this;
        }

        public Criteria andMainPostLevelGreaterThan(String value) {
            addCriterion("main_post_level >", value, "mainPostLevel");
            return (Criteria) this;
        }

        public Criteria andMainPostLevelGreaterThanOrEqualTo(String value) {
            addCriterion("main_post_level >=", value, "mainPostLevel");
            return (Criteria) this;
        }

        public Criteria andMainPostLevelLessThan(String value) {
            addCriterion("main_post_level <", value, "mainPostLevel");
            return (Criteria) this;
        }

        public Criteria andMainPostLevelLessThanOrEqualTo(String value) {
            addCriterion("main_post_level <=", value, "mainPostLevel");
            return (Criteria) this;
        }

        public Criteria andMainPostLevelLike(String value) {
            addCriterion("main_post_level like", value, "mainPostLevel");
            return (Criteria) this;
        }

        public Criteria andMainPostLevelNotLike(String value) {
            addCriterion("main_post_level not like", value, "mainPostLevel");
            return (Criteria) this;
        }

        public Criteria andMainPostLevelIn(List<String> values) {
            addCriterion("main_post_level in", values, "mainPostLevel");
            return (Criteria) this;
        }

        public Criteria andMainPostLevelNotIn(List<String> values) {
            addCriterion("main_post_level not in", values, "mainPostLevel");
            return (Criteria) this;
        }

        public Criteria andMainPostLevelBetween(String value1, String value2) {
            addCriterion("main_post_level between", value1, value2, "mainPostLevel");
            return (Criteria) this;
        }

        public Criteria andMainPostLevelNotBetween(String value1, String value2) {
            addCriterion("main_post_level not between", value1, value2, "mainPostLevel");
            return (Criteria) this;
        }

        public Criteria andProPostTimeIsNull() {
            addCriterion("pro_post_time is null");
            return (Criteria) this;
        }

        public Criteria andProPostTimeIsNotNull() {
            addCriterion("pro_post_time is not null");
            return (Criteria) this;
        }

        public Criteria andProPostTimeEqualTo(Date value) {
            addCriterionForJDBCDate("pro_post_time =", value, "proPostTime");
            return (Criteria) this;
        }

        public Criteria andProPostTimeNotEqualTo(Date value) {
            addCriterionForJDBCDate("pro_post_time <>", value, "proPostTime");
            return (Criteria) this;
        }

        public Criteria andProPostTimeGreaterThan(Date value) {
            addCriterionForJDBCDate("pro_post_time >", value, "proPostTime");
            return (Criteria) this;
        }

        public Criteria andProPostTimeGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("pro_post_time >=", value, "proPostTime");
            return (Criteria) this;
        }

        public Criteria andProPostTimeLessThan(Date value) {
            addCriterionForJDBCDate("pro_post_time <", value, "proPostTime");
            return (Criteria) this;
        }

        public Criteria andProPostTimeLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("pro_post_time <=", value, "proPostTime");
            return (Criteria) this;
        }

        public Criteria andProPostTimeIn(List<Date> values) {
            addCriterionForJDBCDate("pro_post_time in", values, "proPostTime");
            return (Criteria) this;
        }

        public Criteria andProPostTimeNotIn(List<Date> values) {
            addCriterionForJDBCDate("pro_post_time not in", values, "proPostTime");
            return (Criteria) this;
        }

        public Criteria andProPostTimeBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("pro_post_time between", value1, value2, "proPostTime");
            return (Criteria) this;
        }

        public Criteria andProPostTimeNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("pro_post_time not between", value1, value2, "proPostTime");
            return (Criteria) this;
        }

        public Criteria andProPostLevelIsNull() {
            addCriterion("pro_post_level is null");
            return (Criteria) this;
        }

        public Criteria andProPostLevelIsNotNull() {
            addCriterion("pro_post_level is not null");
            return (Criteria) this;
        }

        public Criteria andProPostLevelEqualTo(String value) {
            addCriterion("pro_post_level =", value, "proPostLevel");
            return (Criteria) this;
        }

        public Criteria andProPostLevelNotEqualTo(String value) {
            addCriterion("pro_post_level <>", value, "proPostLevel");
            return (Criteria) this;
        }

        public Criteria andProPostLevelGreaterThan(String value) {
            addCriterion("pro_post_level >", value, "proPostLevel");
            return (Criteria) this;
        }

        public Criteria andProPostLevelGreaterThanOrEqualTo(String value) {
            addCriterion("pro_post_level >=", value, "proPostLevel");
            return (Criteria) this;
        }

        public Criteria andProPostLevelLessThan(String value) {
            addCriterion("pro_post_level <", value, "proPostLevel");
            return (Criteria) this;
        }

        public Criteria andProPostLevelLessThanOrEqualTo(String value) {
            addCriterion("pro_post_level <=", value, "proPostLevel");
            return (Criteria) this;
        }

        public Criteria andProPostLevelLike(String value) {
            addCriterion("pro_post_level like", value, "proPostLevel");
            return (Criteria) this;
        }

        public Criteria andProPostLevelNotLike(String value) {
            addCriterion("pro_post_level not like", value, "proPostLevel");
            return (Criteria) this;
        }

        public Criteria andProPostLevelIn(List<String> values) {
            addCriterion("pro_post_level in", values, "proPostLevel");
            return (Criteria) this;
        }

        public Criteria andProPostLevelNotIn(List<String> values) {
            addCriterion("pro_post_level not in", values, "proPostLevel");
            return (Criteria) this;
        }

        public Criteria andProPostLevelBetween(String value1, String value2) {
            addCriterion("pro_post_level between", value1, value2, "proPostLevel");
            return (Criteria) this;
        }

        public Criteria andProPostLevelNotBetween(String value1, String value2) {
            addCriterion("pro_post_level not between", value1, value2, "proPostLevel");
            return (Criteria) this;
        }

        public Criteria andProPostLevelTimeIsNull() {
            addCriterion("pro_post_level_time is null");
            return (Criteria) this;
        }

        public Criteria andProPostLevelTimeIsNotNull() {
            addCriterion("pro_post_level_time is not null");
            return (Criteria) this;
        }

        public Criteria andProPostLevelTimeEqualTo(Date value) {
            addCriterionForJDBCDate("pro_post_level_time =", value, "proPostLevelTime");
            return (Criteria) this;
        }

        public Criteria andProPostLevelTimeNotEqualTo(Date value) {
            addCriterionForJDBCDate("pro_post_level_time <>", value, "proPostLevelTime");
            return (Criteria) this;
        }

        public Criteria andProPostLevelTimeGreaterThan(Date value) {
            addCriterionForJDBCDate("pro_post_level_time >", value, "proPostLevelTime");
            return (Criteria) this;
        }

        public Criteria andProPostLevelTimeGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("pro_post_level_time >=", value, "proPostLevelTime");
            return (Criteria) this;
        }

        public Criteria andProPostLevelTimeLessThan(Date value) {
            addCriterionForJDBCDate("pro_post_level_time <", value, "proPostLevelTime");
            return (Criteria) this;
        }

        public Criteria andProPostLevelTimeLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("pro_post_level_time <=", value, "proPostLevelTime");
            return (Criteria) this;
        }

        public Criteria andProPostLevelTimeIn(List<Date> values) {
            addCriterionForJDBCDate("pro_post_level_time in", values, "proPostLevelTime");
            return (Criteria) this;
        }

        public Criteria andProPostLevelTimeNotIn(List<Date> values) {
            addCriterionForJDBCDate("pro_post_level_time not in", values, "proPostLevelTime");
            return (Criteria) this;
        }

        public Criteria andProPostLevelTimeBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("pro_post_level_time between", value1, value2, "proPostLevelTime");
            return (Criteria) this;
        }

        public Criteria andProPostLevelTimeNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("pro_post_level_time not between", value1, value2, "proPostLevelTime");
            return (Criteria) this;
        }

        public Criteria andProPostIsNull() {
            addCriterion("pro_post is null");
            return (Criteria) this;
        }

        public Criteria andProPostIsNotNull() {
            addCriterion("pro_post is not null");
            return (Criteria) this;
        }

        public Criteria andProPostEqualTo(String value) {
            addCriterion("pro_post =", value, "proPost");
            return (Criteria) this;
        }

        public Criteria andProPostNotEqualTo(String value) {
            addCriterion("pro_post <>", value, "proPost");
            return (Criteria) this;
        }

        public Criteria andProPostGreaterThan(String value) {
            addCriterion("pro_post >", value, "proPost");
            return (Criteria) this;
        }

        public Criteria andProPostGreaterThanOrEqualTo(String value) {
            addCriterion("pro_post >=", value, "proPost");
            return (Criteria) this;
        }

        public Criteria andProPostLessThan(String value) {
            addCriterion("pro_post <", value, "proPost");
            return (Criteria) this;
        }

        public Criteria andProPostLessThanOrEqualTo(String value) {
            addCriterion("pro_post <=", value, "proPost");
            return (Criteria) this;
        }

        public Criteria andProPostLike(String value) {
            addCriterion("pro_post like", value, "proPost");
            return (Criteria) this;
        }

        public Criteria andProPostNotLike(String value) {
            addCriterion("pro_post not like", value, "proPost");
            return (Criteria) this;
        }

        public Criteria andProPostIn(List<String> values) {
            addCriterion("pro_post in", values, "proPost");
            return (Criteria) this;
        }

        public Criteria andProPostNotIn(List<String> values) {
            addCriterion("pro_post not in", values, "proPost");
            return (Criteria) this;
        }

        public Criteria andProPostBetween(String value1, String value2) {
            addCriterion("pro_post between", value1, value2, "proPost");
            return (Criteria) this;
        }

        public Criteria andProPostNotBetween(String value1, String value2) {
            addCriterion("pro_post not between", value1, value2, "proPost");
            return (Criteria) this;
        }

        public Criteria andManageLevelIsNull() {
            addCriterion("manage_level is null");
            return (Criteria) this;
        }

        public Criteria andManageLevelIsNotNull() {
            addCriterion("manage_level is not null");
            return (Criteria) this;
        }

        public Criteria andManageLevelEqualTo(String value) {
            addCriterion("manage_level =", value, "manageLevel");
            return (Criteria) this;
        }

        public Criteria andManageLevelNotEqualTo(String value) {
            addCriterion("manage_level <>", value, "manageLevel");
            return (Criteria) this;
        }

        public Criteria andManageLevelGreaterThan(String value) {
            addCriterion("manage_level >", value, "manageLevel");
            return (Criteria) this;
        }

        public Criteria andManageLevelGreaterThanOrEqualTo(String value) {
            addCriterion("manage_level >=", value, "manageLevel");
            return (Criteria) this;
        }

        public Criteria andManageLevelLessThan(String value) {
            addCriterion("manage_level <", value, "manageLevel");
            return (Criteria) this;
        }

        public Criteria andManageLevelLessThanOrEqualTo(String value) {
            addCriterion("manage_level <=", value, "manageLevel");
            return (Criteria) this;
        }

        public Criteria andManageLevelLike(String value) {
            addCriterion("manage_level like", value, "manageLevel");
            return (Criteria) this;
        }

        public Criteria andManageLevelNotLike(String value) {
            addCriterion("manage_level not like", value, "manageLevel");
            return (Criteria) this;
        }

        public Criteria andManageLevelIn(List<String> values) {
            addCriterion("manage_level in", values, "manageLevel");
            return (Criteria) this;
        }

        public Criteria andManageLevelNotIn(List<String> values) {
            addCriterion("manage_level not in", values, "manageLevel");
            return (Criteria) this;
        }

        public Criteria andManageLevelBetween(String value1, String value2) {
            addCriterion("manage_level between", value1, value2, "manageLevel");
            return (Criteria) this;
        }

        public Criteria andManageLevelNotBetween(String value1, String value2) {
            addCriterion("manage_level not between", value1, value2, "manageLevel");
            return (Criteria) this;
        }

        public Criteria andManageLevelTimeIsNull() {
            addCriterion("manage_level_time is null");
            return (Criteria) this;
        }

        public Criteria andManageLevelTimeIsNotNull() {
            addCriterion("manage_level_time is not null");
            return (Criteria) this;
        }

        public Criteria andManageLevelTimeEqualTo(Date value) {
            addCriterionForJDBCDate("manage_level_time =", value, "manageLevelTime");
            return (Criteria) this;
        }

        public Criteria andManageLevelTimeNotEqualTo(Date value) {
            addCriterionForJDBCDate("manage_level_time <>", value, "manageLevelTime");
            return (Criteria) this;
        }

        public Criteria andManageLevelTimeGreaterThan(Date value) {
            addCriterionForJDBCDate("manage_level_time >", value, "manageLevelTime");
            return (Criteria) this;
        }

        public Criteria andManageLevelTimeGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("manage_level_time >=", value, "manageLevelTime");
            return (Criteria) this;
        }

        public Criteria andManageLevelTimeLessThan(Date value) {
            addCriterionForJDBCDate("manage_level_time <", value, "manageLevelTime");
            return (Criteria) this;
        }

        public Criteria andManageLevelTimeLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("manage_level_time <=", value, "manageLevelTime");
            return (Criteria) this;
        }

        public Criteria andManageLevelTimeIn(List<Date> values) {
            addCriterionForJDBCDate("manage_level_time in", values, "manageLevelTime");
            return (Criteria) this;
        }

        public Criteria andManageLevelTimeNotIn(List<Date> values) {
            addCriterionForJDBCDate("manage_level_time not in", values, "manageLevelTime");
            return (Criteria) this;
        }

        public Criteria andManageLevelTimeBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("manage_level_time between", value1, value2, "manageLevelTime");
            return (Criteria) this;
        }

        public Criteria andManageLevelTimeNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("manage_level_time not between", value1, value2, "manageLevelTime");
            return (Criteria) this;
        }

        public Criteria andArriveTimeIsNull() {
            addCriterion("arrive_time is null");
            return (Criteria) this;
        }

        public Criteria andArriveTimeIsNotNull() {
            addCriterion("arrive_time is not null");
            return (Criteria) this;
        }

        public Criteria andArriveTimeEqualTo(Date value) {
            addCriterionForJDBCDate("arrive_time =", value, "arriveTime");
            return (Criteria) this;
        }

        public Criteria andArriveTimeNotEqualTo(Date value) {
            addCriterionForJDBCDate("arrive_time <>", value, "arriveTime");
            return (Criteria) this;
        }

        public Criteria andArriveTimeGreaterThan(Date value) {
            addCriterionForJDBCDate("arrive_time >", value, "arriveTime");
            return (Criteria) this;
        }

        public Criteria andArriveTimeGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("arrive_time >=", value, "arriveTime");
            return (Criteria) this;
        }

        public Criteria andArriveTimeLessThan(Date value) {
            addCriterionForJDBCDate("arrive_time <", value, "arriveTime");
            return (Criteria) this;
        }

        public Criteria andArriveTimeLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("arrive_time <=", value, "arriveTime");
            return (Criteria) this;
        }

        public Criteria andArriveTimeIn(List<Date> values) {
            addCriterionForJDBCDate("arrive_time in", values, "arriveTime");
            return (Criteria) this;
        }

        public Criteria andArriveTimeNotIn(List<Date> values) {
            addCriterionForJDBCDate("arrive_time not in", values, "arriveTime");
            return (Criteria) this;
        }

        public Criteria andArriveTimeBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("arrive_time between", value1, value2, "arriveTime");
            return (Criteria) this;
        }

        public Criteria andArriveTimeNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("arrive_time not between", value1, value2, "arriveTime");
            return (Criteria) this;
        }

        public Criteria andWorkStartTimeIsNull() {
            addCriterion("work_start_time is null");
            return (Criteria) this;
        }

        public Criteria andWorkStartTimeIsNotNull() {
            addCriterion("work_start_time is not null");
            return (Criteria) this;
        }

        public Criteria andWorkStartTimeEqualTo(Date value) {
            addCriterionForJDBCDate("work_start_time =", value, "workStartTime");
            return (Criteria) this;
        }

        public Criteria andWorkStartTimeNotEqualTo(Date value) {
            addCriterionForJDBCDate("work_start_time <>", value, "workStartTime");
            return (Criteria) this;
        }

        public Criteria andWorkStartTimeGreaterThan(Date value) {
            addCriterionForJDBCDate("work_start_time >", value, "workStartTime");
            return (Criteria) this;
        }

        public Criteria andWorkStartTimeGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("work_start_time >=", value, "workStartTime");
            return (Criteria) this;
        }

        public Criteria andWorkStartTimeLessThan(Date value) {
            addCriterionForJDBCDate("work_start_time <", value, "workStartTime");
            return (Criteria) this;
        }

        public Criteria andWorkStartTimeLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("work_start_time <=", value, "workStartTime");
            return (Criteria) this;
        }

        public Criteria andWorkStartTimeIn(List<Date> values) {
            addCriterionForJDBCDate("work_start_time in", values, "workStartTime");
            return (Criteria) this;
        }

        public Criteria andWorkStartTimeNotIn(List<Date> values) {
            addCriterionForJDBCDate("work_start_time not in", values, "workStartTime");
            return (Criteria) this;
        }

        public Criteria andWorkStartTimeBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("work_start_time between", value1, value2, "workStartTime");
            return (Criteria) this;
        }

        public Criteria andWorkStartTimeNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("work_start_time not between", value1, value2, "workStartTime");
            return (Criteria) this;
        }

        public Criteria andMainCadrePostIdIsNull() {
            addCriterion("main_cadre_post_id is null");
            return (Criteria) this;
        }

        public Criteria andMainCadrePostIdIsNotNull() {
            addCriterion("main_cadre_post_id is not null");
            return (Criteria) this;
        }

        public Criteria andMainCadrePostIdEqualTo(Integer value) {
            addCriterion("main_cadre_post_id =", value, "mainCadrePostId");
            return (Criteria) this;
        }

        public Criteria andMainCadrePostIdNotEqualTo(Integer value) {
            addCriterion("main_cadre_post_id <>", value, "mainCadrePostId");
            return (Criteria) this;
        }

        public Criteria andMainCadrePostIdGreaterThan(Integer value) {
            addCriterion("main_cadre_post_id >", value, "mainCadrePostId");
            return (Criteria) this;
        }

        public Criteria andMainCadrePostIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("main_cadre_post_id >=", value, "mainCadrePostId");
            return (Criteria) this;
        }

        public Criteria andMainCadrePostIdLessThan(Integer value) {
            addCriterion("main_cadre_post_id <", value, "mainCadrePostId");
            return (Criteria) this;
        }

        public Criteria andMainCadrePostIdLessThanOrEqualTo(Integer value) {
            addCriterion("main_cadre_post_id <=", value, "mainCadrePostId");
            return (Criteria) this;
        }

        public Criteria andMainCadrePostIdIn(List<Integer> values) {
            addCriterion("main_cadre_post_id in", values, "mainCadrePostId");
            return (Criteria) this;
        }

        public Criteria andMainCadrePostIdNotIn(List<Integer> values) {
            addCriterion("main_cadre_post_id not in", values, "mainCadrePostId");
            return (Criteria) this;
        }

        public Criteria andMainCadrePostIdBetween(Integer value1, Integer value2) {
            addCriterion("main_cadre_post_id between", value1, value2, "mainCadrePostId");
            return (Criteria) this;
        }

        public Criteria andMainCadrePostIdNotBetween(Integer value1, Integer value2) {
            addCriterion("main_cadre_post_id not between", value1, value2, "mainCadrePostId");
            return (Criteria) this;
        }

        public Criteria andIsDoubleIsNull() {
            addCriterion("is_double is null");
            return (Criteria) this;
        }

        public Criteria andIsDoubleIsNotNull() {
            addCriterion("is_double is not null");
            return (Criteria) this;
        }

        public Criteria andIsDoubleEqualTo(Boolean value) {
            addCriterion("is_double =", value, "isDouble");
            return (Criteria) this;
        }

        public Criteria andIsDoubleNotEqualTo(Boolean value) {
            addCriterion("is_double <>", value, "isDouble");
            return (Criteria) this;
        }

        public Criteria andIsDoubleGreaterThan(Boolean value) {
            addCriterion("is_double >", value, "isDouble");
            return (Criteria) this;
        }

        public Criteria andIsDoubleGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_double >=", value, "isDouble");
            return (Criteria) this;
        }

        public Criteria andIsDoubleLessThan(Boolean value) {
            addCriterion("is_double <", value, "isDouble");
            return (Criteria) this;
        }

        public Criteria andIsDoubleLessThanOrEqualTo(Boolean value) {
            addCriterion("is_double <=", value, "isDouble");
            return (Criteria) this;
        }

        public Criteria andIsDoubleIn(List<Boolean> values) {
            addCriterion("is_double in", values, "isDouble");
            return (Criteria) this;
        }

        public Criteria andIsDoubleNotIn(List<Boolean> values) {
            addCriterion("is_double not in", values, "isDouble");
            return (Criteria) this;
        }

        public Criteria andIsDoubleBetween(Boolean value1, Boolean value2) {
            addCriterion("is_double between", value1, value2, "isDouble");
            return (Criteria) this;
        }

        public Criteria andIsDoubleNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_double not between", value1, value2, "isDouble");
            return (Criteria) this;
        }

        public Criteria andDoubleUnitIdIsNull() {
            addCriterion("double_unit_id is null");
            return (Criteria) this;
        }

        public Criteria andDoubleUnitIdIsNotNull() {
            addCriterion("double_unit_id is not null");
            return (Criteria) this;
        }

        public Criteria andDoubleUnitIdEqualTo(Integer value) {
            addCriterion("double_unit_id =", value, "doubleUnitId");
            return (Criteria) this;
        }

        public Criteria andDoubleUnitIdNotEqualTo(Integer value) {
            addCriterion("double_unit_id <>", value, "doubleUnitId");
            return (Criteria) this;
        }

        public Criteria andDoubleUnitIdGreaterThan(Integer value) {
            addCriterion("double_unit_id >", value, "doubleUnitId");
            return (Criteria) this;
        }

        public Criteria andDoubleUnitIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("double_unit_id >=", value, "doubleUnitId");
            return (Criteria) this;
        }

        public Criteria andDoubleUnitIdLessThan(Integer value) {
            addCriterion("double_unit_id <", value, "doubleUnitId");
            return (Criteria) this;
        }

        public Criteria andDoubleUnitIdLessThanOrEqualTo(Integer value) {
            addCriterion("double_unit_id <=", value, "doubleUnitId");
            return (Criteria) this;
        }

        public Criteria andDoubleUnitIdIn(List<Integer> values) {
            addCriterion("double_unit_id in", values, "doubleUnitId");
            return (Criteria) this;
        }

        public Criteria andDoubleUnitIdNotIn(List<Integer> values) {
            addCriterion("double_unit_id not in", values, "doubleUnitId");
            return (Criteria) this;
        }

        public Criteria andDoubleUnitIdBetween(Integer value1, Integer value2) {
            addCriterion("double_unit_id between", value1, value2, "doubleUnitId");
            return (Criteria) this;
        }

        public Criteria andDoubleUnitIdNotBetween(Integer value1, Integer value2) {
            addCriterion("double_unit_id not between", value1, value2, "doubleUnitId");
            return (Criteria) this;
        }

        public Criteria andIsPrincipalPostIsNull() {
            addCriterion("is_principal_post is null");
            return (Criteria) this;
        }

        public Criteria andIsPrincipalPostIsNotNull() {
            addCriterion("is_principal_post is not null");
            return (Criteria) this;
        }

        public Criteria andIsPrincipalPostEqualTo(Boolean value) {
            addCriterion("is_principal_post =", value, "isPrincipalPost");
            return (Criteria) this;
        }

        public Criteria andIsPrincipalPostNotEqualTo(Boolean value) {
            addCriterion("is_principal_post <>", value, "isPrincipalPost");
            return (Criteria) this;
        }

        public Criteria andIsPrincipalPostGreaterThan(Boolean value) {
            addCriterion("is_principal_post >", value, "isPrincipalPost");
            return (Criteria) this;
        }

        public Criteria andIsPrincipalPostGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_principal_post >=", value, "isPrincipalPost");
            return (Criteria) this;
        }

        public Criteria andIsPrincipalPostLessThan(Boolean value) {
            addCriterion("is_principal_post <", value, "isPrincipalPost");
            return (Criteria) this;
        }

        public Criteria andIsPrincipalPostLessThanOrEqualTo(Boolean value) {
            addCriterion("is_principal_post <=", value, "isPrincipalPost");
            return (Criteria) this;
        }

        public Criteria andIsPrincipalPostIn(List<Boolean> values) {
            addCriterion("is_principal_post in", values, "isPrincipalPost");
            return (Criteria) this;
        }

        public Criteria andIsPrincipalPostNotIn(List<Boolean> values) {
            addCriterion("is_principal_post not in", values, "isPrincipalPost");
            return (Criteria) this;
        }

        public Criteria andIsPrincipalPostBetween(Boolean value1, Boolean value2) {
            addCriterion("is_principal_post between", value1, value2, "isPrincipalPost");
            return (Criteria) this;
        }

        public Criteria andIsPrincipalPostNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_principal_post not between", value1, value2, "isPrincipalPost");
            return (Criteria) this;
        }

        public Criteria andAdminLevelCodeIsNull() {
            addCriterion("admin_level_code is null");
            return (Criteria) this;
        }

        public Criteria andAdminLevelCodeIsNotNull() {
            addCriterion("admin_level_code is not null");
            return (Criteria) this;
        }

        public Criteria andAdminLevelCodeEqualTo(String value) {
            addCriterion("admin_level_code =", value, "adminLevelCode");
            return (Criteria) this;
        }

        public Criteria andAdminLevelCodeNotEqualTo(String value) {
            addCriterion("admin_level_code <>", value, "adminLevelCode");
            return (Criteria) this;
        }

        public Criteria andAdminLevelCodeGreaterThan(String value) {
            addCriterion("admin_level_code >", value, "adminLevelCode");
            return (Criteria) this;
        }

        public Criteria andAdminLevelCodeGreaterThanOrEqualTo(String value) {
            addCriterion("admin_level_code >=", value, "adminLevelCode");
            return (Criteria) this;
        }

        public Criteria andAdminLevelCodeLessThan(String value) {
            addCriterion("admin_level_code <", value, "adminLevelCode");
            return (Criteria) this;
        }

        public Criteria andAdminLevelCodeLessThanOrEqualTo(String value) {
            addCriterion("admin_level_code <=", value, "adminLevelCode");
            return (Criteria) this;
        }

        public Criteria andAdminLevelCodeLike(String value) {
            addCriterion("admin_level_code like", value, "adminLevelCode");
            return (Criteria) this;
        }

        public Criteria andAdminLevelCodeNotLike(String value) {
            addCriterion("admin_level_code not like", value, "adminLevelCode");
            return (Criteria) this;
        }

        public Criteria andAdminLevelCodeIn(List<String> values) {
            addCriterion("admin_level_code in", values, "adminLevelCode");
            return (Criteria) this;
        }

        public Criteria andAdminLevelCodeNotIn(List<String> values) {
            addCriterion("admin_level_code not in", values, "adminLevelCode");
            return (Criteria) this;
        }

        public Criteria andAdminLevelCodeBetween(String value1, String value2) {
            addCriterion("admin_level_code between", value1, value2, "adminLevelCode");
            return (Criteria) this;
        }

        public Criteria andAdminLevelCodeNotBetween(String value1, String value2) {
            addCriterion("admin_level_code not between", value1, value2, "adminLevelCode");
            return (Criteria) this;
        }

        public Criteria andAdminLevelNameIsNull() {
            addCriterion("admin_level_name is null");
            return (Criteria) this;
        }

        public Criteria andAdminLevelNameIsNotNull() {
            addCriterion("admin_level_name is not null");
            return (Criteria) this;
        }

        public Criteria andAdminLevelNameEqualTo(String value) {
            addCriterion("admin_level_name =", value, "adminLevelName");
            return (Criteria) this;
        }

        public Criteria andAdminLevelNameNotEqualTo(String value) {
            addCriterion("admin_level_name <>", value, "adminLevelName");
            return (Criteria) this;
        }

        public Criteria andAdminLevelNameGreaterThan(String value) {
            addCriterion("admin_level_name >", value, "adminLevelName");
            return (Criteria) this;
        }

        public Criteria andAdminLevelNameGreaterThanOrEqualTo(String value) {
            addCriterion("admin_level_name >=", value, "adminLevelName");
            return (Criteria) this;
        }

        public Criteria andAdminLevelNameLessThan(String value) {
            addCriterion("admin_level_name <", value, "adminLevelName");
            return (Criteria) this;
        }

        public Criteria andAdminLevelNameLessThanOrEqualTo(String value) {
            addCriterion("admin_level_name <=", value, "adminLevelName");
            return (Criteria) this;
        }

        public Criteria andAdminLevelNameLike(String value) {
            addCriterion("admin_level_name like", value, "adminLevelName");
            return (Criteria) this;
        }

        public Criteria andAdminLevelNameNotLike(String value) {
            addCriterion("admin_level_name not like", value, "adminLevelName");
            return (Criteria) this;
        }

        public Criteria andAdminLevelNameIn(List<String> values) {
            addCriterion("admin_level_name in", values, "adminLevelName");
            return (Criteria) this;
        }

        public Criteria andAdminLevelNameNotIn(List<String> values) {
            addCriterion("admin_level_name not in", values, "adminLevelName");
            return (Criteria) this;
        }

        public Criteria andAdminLevelNameBetween(String value1, String value2) {
            addCriterion("admin_level_name between", value1, value2, "adminLevelName");
            return (Criteria) this;
        }

        public Criteria andAdminLevelNameNotBetween(String value1, String value2) {
            addCriterion("admin_level_name not between", value1, value2, "adminLevelName");
            return (Criteria) this;
        }

        public Criteria andMaxCeEduAttrIsNull() {
            addCriterion("max_ce_edu_attr is null");
            return (Criteria) this;
        }

        public Criteria andMaxCeEduAttrIsNotNull() {
            addCriterion("max_ce_edu_attr is not null");
            return (Criteria) this;
        }

        public Criteria andMaxCeEduAttrEqualTo(String value) {
            addCriterion("max_ce_edu_attr =", value, "maxCeEduAttr");
            return (Criteria) this;
        }

        public Criteria andMaxCeEduAttrNotEqualTo(String value) {
            addCriterion("max_ce_edu_attr <>", value, "maxCeEduAttr");
            return (Criteria) this;
        }

        public Criteria andMaxCeEduAttrGreaterThan(String value) {
            addCriterion("max_ce_edu_attr >", value, "maxCeEduAttr");
            return (Criteria) this;
        }

        public Criteria andMaxCeEduAttrGreaterThanOrEqualTo(String value) {
            addCriterion("max_ce_edu_attr >=", value, "maxCeEduAttr");
            return (Criteria) this;
        }

        public Criteria andMaxCeEduAttrLessThan(String value) {
            addCriterion("max_ce_edu_attr <", value, "maxCeEduAttr");
            return (Criteria) this;
        }

        public Criteria andMaxCeEduAttrLessThanOrEqualTo(String value) {
            addCriterion("max_ce_edu_attr <=", value, "maxCeEduAttr");
            return (Criteria) this;
        }

        public Criteria andMaxCeEduAttrLike(String value) {
            addCriterion("max_ce_edu_attr like", value, "maxCeEduAttr");
            return (Criteria) this;
        }

        public Criteria andMaxCeEduAttrNotLike(String value) {
            addCriterion("max_ce_edu_attr not like", value, "maxCeEduAttr");
            return (Criteria) this;
        }

        public Criteria andMaxCeEduAttrIn(List<String> values) {
            addCriterion("max_ce_edu_attr in", values, "maxCeEduAttr");
            return (Criteria) this;
        }

        public Criteria andMaxCeEduAttrNotIn(List<String> values) {
            addCriterion("max_ce_edu_attr not in", values, "maxCeEduAttr");
            return (Criteria) this;
        }

        public Criteria andMaxCeEduAttrBetween(String value1, String value2) {
            addCriterion("max_ce_edu_attr between", value1, value2, "maxCeEduAttr");
            return (Criteria) this;
        }

        public Criteria andMaxCeEduAttrNotBetween(String value1, String value2) {
            addCriterion("max_ce_edu_attr not between", value1, value2, "maxCeEduAttr");
            return (Criteria) this;
        }

        public Criteria andMaxCeEduCodeIsNull() {
            addCriterion("max_ce_edu_code is null");
            return (Criteria) this;
        }

        public Criteria andMaxCeEduCodeIsNotNull() {
            addCriterion("max_ce_edu_code is not null");
            return (Criteria) this;
        }

        public Criteria andMaxCeEduCodeEqualTo(String value) {
            addCriterion("max_ce_edu_code =", value, "maxCeEduCode");
            return (Criteria) this;
        }

        public Criteria andMaxCeEduCodeNotEqualTo(String value) {
            addCriterion("max_ce_edu_code <>", value, "maxCeEduCode");
            return (Criteria) this;
        }

        public Criteria andMaxCeEduCodeGreaterThan(String value) {
            addCriterion("max_ce_edu_code >", value, "maxCeEduCode");
            return (Criteria) this;
        }

        public Criteria andMaxCeEduCodeGreaterThanOrEqualTo(String value) {
            addCriterion("max_ce_edu_code >=", value, "maxCeEduCode");
            return (Criteria) this;
        }

        public Criteria andMaxCeEduCodeLessThan(String value) {
            addCriterion("max_ce_edu_code <", value, "maxCeEduCode");
            return (Criteria) this;
        }

        public Criteria andMaxCeEduCodeLessThanOrEqualTo(String value) {
            addCriterion("max_ce_edu_code <=", value, "maxCeEduCode");
            return (Criteria) this;
        }

        public Criteria andMaxCeEduCodeLike(String value) {
            addCriterion("max_ce_edu_code like", value, "maxCeEduCode");
            return (Criteria) this;
        }

        public Criteria andMaxCeEduCodeNotLike(String value) {
            addCriterion("max_ce_edu_code not like", value, "maxCeEduCode");
            return (Criteria) this;
        }

        public Criteria andMaxCeEduCodeIn(List<String> values) {
            addCriterion("max_ce_edu_code in", values, "maxCeEduCode");
            return (Criteria) this;
        }

        public Criteria andMaxCeEduCodeNotIn(List<String> values) {
            addCriterion("max_ce_edu_code not in", values, "maxCeEduCode");
            return (Criteria) this;
        }

        public Criteria andMaxCeEduCodeBetween(String value1, String value2) {
            addCriterion("max_ce_edu_code between", value1, value2, "maxCeEduCode");
            return (Criteria) this;
        }

        public Criteria andMaxCeEduCodeNotBetween(String value1, String value2) {
            addCriterion("max_ce_edu_code not between", value1, value2, "maxCeEduCode");
            return (Criteria) this;
        }

        public Criteria andMaxCeEduNameIsNull() {
            addCriterion("max_ce_edu_name is null");
            return (Criteria) this;
        }

        public Criteria andMaxCeEduNameIsNotNull() {
            addCriterion("max_ce_edu_name is not null");
            return (Criteria) this;
        }

        public Criteria andMaxCeEduNameEqualTo(String value) {
            addCriterion("max_ce_edu_name =", value, "maxCeEduName");
            return (Criteria) this;
        }

        public Criteria andMaxCeEduNameNotEqualTo(String value) {
            addCriterion("max_ce_edu_name <>", value, "maxCeEduName");
            return (Criteria) this;
        }

        public Criteria andMaxCeEduNameGreaterThan(String value) {
            addCriterion("max_ce_edu_name >", value, "maxCeEduName");
            return (Criteria) this;
        }

        public Criteria andMaxCeEduNameGreaterThanOrEqualTo(String value) {
            addCriterion("max_ce_edu_name >=", value, "maxCeEduName");
            return (Criteria) this;
        }

        public Criteria andMaxCeEduNameLessThan(String value) {
            addCriterion("max_ce_edu_name <", value, "maxCeEduName");
            return (Criteria) this;
        }

        public Criteria andMaxCeEduNameLessThanOrEqualTo(String value) {
            addCriterion("max_ce_edu_name <=", value, "maxCeEduName");
            return (Criteria) this;
        }

        public Criteria andMaxCeEduNameLike(String value) {
            addCriterion("max_ce_edu_name like", value, "maxCeEduName");
            return (Criteria) this;
        }

        public Criteria andMaxCeEduNameNotLike(String value) {
            addCriterion("max_ce_edu_name not like", value, "maxCeEduName");
            return (Criteria) this;
        }

        public Criteria andMaxCeEduNameIn(List<String> values) {
            addCriterion("max_ce_edu_name in", values, "maxCeEduName");
            return (Criteria) this;
        }

        public Criteria andMaxCeEduNameNotIn(List<String> values) {
            addCriterion("max_ce_edu_name not in", values, "maxCeEduName");
            return (Criteria) this;
        }

        public Criteria andMaxCeEduNameBetween(String value1, String value2) {
            addCriterion("max_ce_edu_name between", value1, value2, "maxCeEduName");
            return (Criteria) this;
        }

        public Criteria andMaxCeEduNameNotBetween(String value1, String value2) {
            addCriterion("max_ce_edu_name not between", value1, value2, "maxCeEduName");
            return (Criteria) this;
        }

        public Criteria andUnitNameIsNull() {
            addCriterion("unit_name is null");
            return (Criteria) this;
        }

        public Criteria andUnitNameIsNotNull() {
            addCriterion("unit_name is not null");
            return (Criteria) this;
        }

        public Criteria andUnitNameEqualTo(String value) {
            addCriterion("unit_name =", value, "unitName");
            return (Criteria) this;
        }

        public Criteria andUnitNameNotEqualTo(String value) {
            addCriterion("unit_name <>", value, "unitName");
            return (Criteria) this;
        }

        public Criteria andUnitNameGreaterThan(String value) {
            addCriterion("unit_name >", value, "unitName");
            return (Criteria) this;
        }

        public Criteria andUnitNameGreaterThanOrEqualTo(String value) {
            addCriterion("unit_name >=", value, "unitName");
            return (Criteria) this;
        }

        public Criteria andUnitNameLessThan(String value) {
            addCriterion("unit_name <", value, "unitName");
            return (Criteria) this;
        }

        public Criteria andUnitNameLessThanOrEqualTo(String value) {
            addCriterion("unit_name <=", value, "unitName");
            return (Criteria) this;
        }

        public Criteria andUnitNameLike(String value) {
            addCriterion("unit_name like", value, "unitName");
            return (Criteria) this;
        }

        public Criteria andUnitNameNotLike(String value) {
            addCriterion("unit_name not like", value, "unitName");
            return (Criteria) this;
        }

        public Criteria andUnitNameIn(List<String> values) {
            addCriterion("unit_name in", values, "unitName");
            return (Criteria) this;
        }

        public Criteria andUnitNameNotIn(List<String> values) {
            addCriterion("unit_name not in", values, "unitName");
            return (Criteria) this;
        }

        public Criteria andUnitNameBetween(String value1, String value2) {
            addCriterion("unit_name between", value1, value2, "unitName");
            return (Criteria) this;
        }

        public Criteria andUnitNameNotBetween(String value1, String value2) {
            addCriterion("unit_name not between", value1, value2, "unitName");
            return (Criteria) this;
        }

        public Criteria andUnitTypeIdIsNull() {
            addCriterion("unit_type_id is null");
            return (Criteria) this;
        }

        public Criteria andUnitTypeIdIsNotNull() {
            addCriterion("unit_type_id is not null");
            return (Criteria) this;
        }

        public Criteria andUnitTypeIdEqualTo(Integer value) {
            addCriterion("unit_type_id =", value, "unitTypeId");
            return (Criteria) this;
        }

        public Criteria andUnitTypeIdNotEqualTo(Integer value) {
            addCriterion("unit_type_id <>", value, "unitTypeId");
            return (Criteria) this;
        }

        public Criteria andUnitTypeIdGreaterThan(Integer value) {
            addCriterion("unit_type_id >", value, "unitTypeId");
            return (Criteria) this;
        }

        public Criteria andUnitTypeIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("unit_type_id >=", value, "unitTypeId");
            return (Criteria) this;
        }

        public Criteria andUnitTypeIdLessThan(Integer value) {
            addCriterion("unit_type_id <", value, "unitTypeId");
            return (Criteria) this;
        }

        public Criteria andUnitTypeIdLessThanOrEqualTo(Integer value) {
            addCriterion("unit_type_id <=", value, "unitTypeId");
            return (Criteria) this;
        }

        public Criteria andUnitTypeIdIn(List<Integer> values) {
            addCriterion("unit_type_id in", values, "unitTypeId");
            return (Criteria) this;
        }

        public Criteria andUnitTypeIdNotIn(List<Integer> values) {
            addCriterion("unit_type_id not in", values, "unitTypeId");
            return (Criteria) this;
        }

        public Criteria andUnitTypeIdBetween(Integer value1, Integer value2) {
            addCriterion("unit_type_id between", value1, value2, "unitTypeId");
            return (Criteria) this;
        }

        public Criteria andUnitTypeIdNotBetween(Integer value1, Integer value2) {
            addCriterion("unit_type_id not between", value1, value2, "unitTypeId");
            return (Criteria) this;
        }

        public Criteria andUnitTypeCodeIsNull() {
            addCriterion("unit_type_code is null");
            return (Criteria) this;
        }

        public Criteria andUnitTypeCodeIsNotNull() {
            addCriterion("unit_type_code is not null");
            return (Criteria) this;
        }

        public Criteria andUnitTypeCodeEqualTo(String value) {
            addCriterion("unit_type_code =", value, "unitTypeCode");
            return (Criteria) this;
        }

        public Criteria andUnitTypeCodeNotEqualTo(String value) {
            addCriterion("unit_type_code <>", value, "unitTypeCode");
            return (Criteria) this;
        }

        public Criteria andUnitTypeCodeGreaterThan(String value) {
            addCriterion("unit_type_code >", value, "unitTypeCode");
            return (Criteria) this;
        }

        public Criteria andUnitTypeCodeGreaterThanOrEqualTo(String value) {
            addCriterion("unit_type_code >=", value, "unitTypeCode");
            return (Criteria) this;
        }

        public Criteria andUnitTypeCodeLessThan(String value) {
            addCriterion("unit_type_code <", value, "unitTypeCode");
            return (Criteria) this;
        }

        public Criteria andUnitTypeCodeLessThanOrEqualTo(String value) {
            addCriterion("unit_type_code <=", value, "unitTypeCode");
            return (Criteria) this;
        }

        public Criteria andUnitTypeCodeLike(String value) {
            addCriterion("unit_type_code like", value, "unitTypeCode");
            return (Criteria) this;
        }

        public Criteria andUnitTypeCodeNotLike(String value) {
            addCriterion("unit_type_code not like", value, "unitTypeCode");
            return (Criteria) this;
        }

        public Criteria andUnitTypeCodeIn(List<String> values) {
            addCriterion("unit_type_code in", values, "unitTypeCode");
            return (Criteria) this;
        }

        public Criteria andUnitTypeCodeNotIn(List<String> values) {
            addCriterion("unit_type_code not in", values, "unitTypeCode");
            return (Criteria) this;
        }

        public Criteria andUnitTypeCodeBetween(String value1, String value2) {
            addCriterion("unit_type_code between", value1, value2, "unitTypeCode");
            return (Criteria) this;
        }

        public Criteria andUnitTypeCodeNotBetween(String value1, String value2) {
            addCriterion("unit_type_code not between", value1, value2, "unitTypeCode");
            return (Criteria) this;
        }

        public Criteria andUnitTypeNameIsNull() {
            addCriterion("unit_type_name is null");
            return (Criteria) this;
        }

        public Criteria andUnitTypeNameIsNotNull() {
            addCriterion("unit_type_name is not null");
            return (Criteria) this;
        }

        public Criteria andUnitTypeNameEqualTo(String value) {
            addCriterion("unit_type_name =", value, "unitTypeName");
            return (Criteria) this;
        }

        public Criteria andUnitTypeNameNotEqualTo(String value) {
            addCriterion("unit_type_name <>", value, "unitTypeName");
            return (Criteria) this;
        }

        public Criteria andUnitTypeNameGreaterThan(String value) {
            addCriterion("unit_type_name >", value, "unitTypeName");
            return (Criteria) this;
        }

        public Criteria andUnitTypeNameGreaterThanOrEqualTo(String value) {
            addCriterion("unit_type_name >=", value, "unitTypeName");
            return (Criteria) this;
        }

        public Criteria andUnitTypeNameLessThan(String value) {
            addCriterion("unit_type_name <", value, "unitTypeName");
            return (Criteria) this;
        }

        public Criteria andUnitTypeNameLessThanOrEqualTo(String value) {
            addCriterion("unit_type_name <=", value, "unitTypeName");
            return (Criteria) this;
        }

        public Criteria andUnitTypeNameLike(String value) {
            addCriterion("unit_type_name like", value, "unitTypeName");
            return (Criteria) this;
        }

        public Criteria andUnitTypeNameNotLike(String value) {
            addCriterion("unit_type_name not like", value, "unitTypeName");
            return (Criteria) this;
        }

        public Criteria andUnitTypeNameIn(List<String> values) {
            addCriterion("unit_type_name in", values, "unitTypeName");
            return (Criteria) this;
        }

        public Criteria andUnitTypeNameNotIn(List<String> values) {
            addCriterion("unit_type_name not in", values, "unitTypeName");
            return (Criteria) this;
        }

        public Criteria andUnitTypeNameBetween(String value1, String value2) {
            addCriterion("unit_type_name between", value1, value2, "unitTypeName");
            return (Criteria) this;
        }

        public Criteria andUnitTypeNameNotBetween(String value1, String value2) {
            addCriterion("unit_type_name not between", value1, value2, "unitTypeName");
            return (Criteria) this;
        }

        public Criteria andUnitTypeAttrIsNull() {
            addCriterion("unit_type_attr is null");
            return (Criteria) this;
        }

        public Criteria andUnitTypeAttrIsNotNull() {
            addCriterion("unit_type_attr is not null");
            return (Criteria) this;
        }

        public Criteria andUnitTypeAttrEqualTo(String value) {
            addCriterion("unit_type_attr =", value, "unitTypeAttr");
            return (Criteria) this;
        }

        public Criteria andUnitTypeAttrNotEqualTo(String value) {
            addCriterion("unit_type_attr <>", value, "unitTypeAttr");
            return (Criteria) this;
        }

        public Criteria andUnitTypeAttrGreaterThan(String value) {
            addCriterion("unit_type_attr >", value, "unitTypeAttr");
            return (Criteria) this;
        }

        public Criteria andUnitTypeAttrGreaterThanOrEqualTo(String value) {
            addCriterion("unit_type_attr >=", value, "unitTypeAttr");
            return (Criteria) this;
        }

        public Criteria andUnitTypeAttrLessThan(String value) {
            addCriterion("unit_type_attr <", value, "unitTypeAttr");
            return (Criteria) this;
        }

        public Criteria andUnitTypeAttrLessThanOrEqualTo(String value) {
            addCriterion("unit_type_attr <=", value, "unitTypeAttr");
            return (Criteria) this;
        }

        public Criteria andUnitTypeAttrLike(String value) {
            addCriterion("unit_type_attr like", value, "unitTypeAttr");
            return (Criteria) this;
        }

        public Criteria andUnitTypeAttrNotLike(String value) {
            addCriterion("unit_type_attr not like", value, "unitTypeAttr");
            return (Criteria) this;
        }

        public Criteria andUnitTypeAttrIn(List<String> values) {
            addCriterion("unit_type_attr in", values, "unitTypeAttr");
            return (Criteria) this;
        }

        public Criteria andUnitTypeAttrNotIn(List<String> values) {
            addCriterion("unit_type_attr not in", values, "unitTypeAttr");
            return (Criteria) this;
        }

        public Criteria andUnitTypeAttrBetween(String value1, String value2) {
            addCriterion("unit_type_attr between", value1, value2, "unitTypeAttr");
            return (Criteria) this;
        }

        public Criteria andUnitTypeAttrNotBetween(String value1, String value2) {
            addCriterion("unit_type_attr not between", value1, value2, "unitTypeAttr");
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