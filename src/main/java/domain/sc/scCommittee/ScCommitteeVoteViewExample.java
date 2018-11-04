package domain.sc.scCommittee;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class ScCommitteeVoteViewExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public ScCommitteeVoteViewExample() {
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

        public Criteria andTopicIdIsNull() {
            addCriterion("topic_id is null");
            return (Criteria) this;
        }

        public Criteria andTopicIdIsNotNull() {
            addCriterion("topic_id is not null");
            return (Criteria) this;
        }

        public Criteria andTopicIdEqualTo(Integer value) {
            addCriterion("topic_id =", value, "topicId");
            return (Criteria) this;
        }

        public Criteria andTopicIdNotEqualTo(Integer value) {
            addCriterion("topic_id <>", value, "topicId");
            return (Criteria) this;
        }

        public Criteria andTopicIdGreaterThan(Integer value) {
            addCriterion("topic_id >", value, "topicId");
            return (Criteria) this;
        }

        public Criteria andTopicIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("topic_id >=", value, "topicId");
            return (Criteria) this;
        }

        public Criteria andTopicIdLessThan(Integer value) {
            addCriterion("topic_id <", value, "topicId");
            return (Criteria) this;
        }

        public Criteria andTopicIdLessThanOrEqualTo(Integer value) {
            addCriterion("topic_id <=", value, "topicId");
            return (Criteria) this;
        }

        public Criteria andTopicIdIn(List<Integer> values) {
            addCriterion("topic_id in", values, "topicId");
            return (Criteria) this;
        }

        public Criteria andTopicIdNotIn(List<Integer> values) {
            addCriterion("topic_id not in", values, "topicId");
            return (Criteria) this;
        }

        public Criteria andTopicIdBetween(Integer value1, Integer value2) {
            addCriterion("topic_id between", value1, value2, "topicId");
            return (Criteria) this;
        }

        public Criteria andTopicIdNotBetween(Integer value1, Integer value2) {
            addCriterion("topic_id not between", value1, value2, "topicId");
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

        public Criteria andOriginalPostIsNull() {
            addCriterion("original_post is null");
            return (Criteria) this;
        }

        public Criteria andOriginalPostIsNotNull() {
            addCriterion("original_post is not null");
            return (Criteria) this;
        }

        public Criteria andOriginalPostEqualTo(String value) {
            addCriterion("original_post =", value, "originalPost");
            return (Criteria) this;
        }

        public Criteria andOriginalPostNotEqualTo(String value) {
            addCriterion("original_post <>", value, "originalPost");
            return (Criteria) this;
        }

        public Criteria andOriginalPostGreaterThan(String value) {
            addCriterion("original_post >", value, "originalPost");
            return (Criteria) this;
        }

        public Criteria andOriginalPostGreaterThanOrEqualTo(String value) {
            addCriterion("original_post >=", value, "originalPost");
            return (Criteria) this;
        }

        public Criteria andOriginalPostLessThan(String value) {
            addCriterion("original_post <", value, "originalPost");
            return (Criteria) this;
        }

        public Criteria andOriginalPostLessThanOrEqualTo(String value) {
            addCriterion("original_post <=", value, "originalPost");
            return (Criteria) this;
        }

        public Criteria andOriginalPostLike(String value) {
            addCriterion("original_post like", value, "originalPost");
            return (Criteria) this;
        }

        public Criteria andOriginalPostNotLike(String value) {
            addCriterion("original_post not like", value, "originalPost");
            return (Criteria) this;
        }

        public Criteria andOriginalPostIn(List<String> values) {
            addCriterion("original_post in", values, "originalPost");
            return (Criteria) this;
        }

        public Criteria andOriginalPostNotIn(List<String> values) {
            addCriterion("original_post not in", values, "originalPost");
            return (Criteria) this;
        }

        public Criteria andOriginalPostBetween(String value1, String value2) {
            addCriterion("original_post between", value1, value2, "originalPost");
            return (Criteria) this;
        }

        public Criteria andOriginalPostNotBetween(String value1, String value2) {
            addCriterion("original_post not between", value1, value2, "originalPost");
            return (Criteria) this;
        }

        public Criteria andOriginalPostTimeIsNull() {
            addCriterion("original_post_time is null");
            return (Criteria) this;
        }

        public Criteria andOriginalPostTimeIsNotNull() {
            addCriterion("original_post_time is not null");
            return (Criteria) this;
        }

        public Criteria andOriginalPostTimeEqualTo(Date value) {
            addCriterionForJDBCDate("original_post_time =", value, "originalPostTime");
            return (Criteria) this;
        }

        public Criteria andOriginalPostTimeNotEqualTo(Date value) {
            addCriterionForJDBCDate("original_post_time <>", value, "originalPostTime");
            return (Criteria) this;
        }

        public Criteria andOriginalPostTimeGreaterThan(Date value) {
            addCriterionForJDBCDate("original_post_time >", value, "originalPostTime");
            return (Criteria) this;
        }

        public Criteria andOriginalPostTimeGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("original_post_time >=", value, "originalPostTime");
            return (Criteria) this;
        }

        public Criteria andOriginalPostTimeLessThan(Date value) {
            addCriterionForJDBCDate("original_post_time <", value, "originalPostTime");
            return (Criteria) this;
        }

        public Criteria andOriginalPostTimeLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("original_post_time <=", value, "originalPostTime");
            return (Criteria) this;
        }

        public Criteria andOriginalPostTimeIn(List<Date> values) {
            addCriterionForJDBCDate("original_post_time in", values, "originalPostTime");
            return (Criteria) this;
        }

        public Criteria andOriginalPostTimeNotIn(List<Date> values) {
            addCriterionForJDBCDate("original_post_time not in", values, "originalPostTime");
            return (Criteria) this;
        }

        public Criteria andOriginalPostTimeBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("original_post_time between", value1, value2, "originalPostTime");
            return (Criteria) this;
        }

        public Criteria andOriginalPostTimeNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("original_post_time not between", value1, value2, "originalPostTime");
            return (Criteria) this;
        }

        public Criteria andCadreTypeIdIsNull() {
            addCriterion("cadre_type_id is null");
            return (Criteria) this;
        }

        public Criteria andCadreTypeIdIsNotNull() {
            addCriterion("cadre_type_id is not null");
            return (Criteria) this;
        }

        public Criteria andCadreTypeIdEqualTo(Integer value) {
            addCriterion("cadre_type_id =", value, "cadreTypeId");
            return (Criteria) this;
        }

        public Criteria andCadreTypeIdNotEqualTo(Integer value) {
            addCriterion("cadre_type_id <>", value, "cadreTypeId");
            return (Criteria) this;
        }

        public Criteria andCadreTypeIdGreaterThan(Integer value) {
            addCriterion("cadre_type_id >", value, "cadreTypeId");
            return (Criteria) this;
        }

        public Criteria andCadreTypeIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("cadre_type_id >=", value, "cadreTypeId");
            return (Criteria) this;
        }

        public Criteria andCadreTypeIdLessThan(Integer value) {
            addCriterion("cadre_type_id <", value, "cadreTypeId");
            return (Criteria) this;
        }

        public Criteria andCadreTypeIdLessThanOrEqualTo(Integer value) {
            addCriterion("cadre_type_id <=", value, "cadreTypeId");
            return (Criteria) this;
        }

        public Criteria andCadreTypeIdIn(List<Integer> values) {
            addCriterion("cadre_type_id in", values, "cadreTypeId");
            return (Criteria) this;
        }

        public Criteria andCadreTypeIdNotIn(List<Integer> values) {
            addCriterion("cadre_type_id not in", values, "cadreTypeId");
            return (Criteria) this;
        }

        public Criteria andCadreTypeIdBetween(Integer value1, Integer value2) {
            addCriterion("cadre_type_id between", value1, value2, "cadreTypeId");
            return (Criteria) this;
        }

        public Criteria andCadreTypeIdNotBetween(Integer value1, Integer value2) {
            addCriterion("cadre_type_id not between", value1, value2, "cadreTypeId");
            return (Criteria) this;
        }

        public Criteria andWayIdIsNull() {
            addCriterion("way_id is null");
            return (Criteria) this;
        }

        public Criteria andWayIdIsNotNull() {
            addCriterion("way_id is not null");
            return (Criteria) this;
        }

        public Criteria andWayIdEqualTo(Integer value) {
            addCriterion("way_id =", value, "wayId");
            return (Criteria) this;
        }

        public Criteria andWayIdNotEqualTo(Integer value) {
            addCriterion("way_id <>", value, "wayId");
            return (Criteria) this;
        }

        public Criteria andWayIdGreaterThan(Integer value) {
            addCriterion("way_id >", value, "wayId");
            return (Criteria) this;
        }

        public Criteria andWayIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("way_id >=", value, "wayId");
            return (Criteria) this;
        }

        public Criteria andWayIdLessThan(Integer value) {
            addCriterion("way_id <", value, "wayId");
            return (Criteria) this;
        }

        public Criteria andWayIdLessThanOrEqualTo(Integer value) {
            addCriterion("way_id <=", value, "wayId");
            return (Criteria) this;
        }

        public Criteria andWayIdIn(List<Integer> values) {
            addCriterion("way_id in", values, "wayId");
            return (Criteria) this;
        }

        public Criteria andWayIdNotIn(List<Integer> values) {
            addCriterion("way_id not in", values, "wayId");
            return (Criteria) this;
        }

        public Criteria andWayIdBetween(Integer value1, Integer value2) {
            addCriterion("way_id between", value1, value2, "wayId");
            return (Criteria) this;
        }

        public Criteria andWayIdNotBetween(Integer value1, Integer value2) {
            addCriterion("way_id not between", value1, value2, "wayId");
            return (Criteria) this;
        }

        public Criteria andProcedureIdIsNull() {
            addCriterion("procedure_id is null");
            return (Criteria) this;
        }

        public Criteria andProcedureIdIsNotNull() {
            addCriterion("procedure_id is not null");
            return (Criteria) this;
        }

        public Criteria andProcedureIdEqualTo(Integer value) {
            addCriterion("procedure_id =", value, "procedureId");
            return (Criteria) this;
        }

        public Criteria andProcedureIdNotEqualTo(Integer value) {
            addCriterion("procedure_id <>", value, "procedureId");
            return (Criteria) this;
        }

        public Criteria andProcedureIdGreaterThan(Integer value) {
            addCriterion("procedure_id >", value, "procedureId");
            return (Criteria) this;
        }

        public Criteria andProcedureIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("procedure_id >=", value, "procedureId");
            return (Criteria) this;
        }

        public Criteria andProcedureIdLessThan(Integer value) {
            addCriterion("procedure_id <", value, "procedureId");
            return (Criteria) this;
        }

        public Criteria andProcedureIdLessThanOrEqualTo(Integer value) {
            addCriterion("procedure_id <=", value, "procedureId");
            return (Criteria) this;
        }

        public Criteria andProcedureIdIn(List<Integer> values) {
            addCriterion("procedure_id in", values, "procedureId");
            return (Criteria) this;
        }

        public Criteria andProcedureIdNotIn(List<Integer> values) {
            addCriterion("procedure_id not in", values, "procedureId");
            return (Criteria) this;
        }

        public Criteria andProcedureIdBetween(Integer value1, Integer value2) {
            addCriterion("procedure_id between", value1, value2, "procedureId");
            return (Criteria) this;
        }

        public Criteria andProcedureIdNotBetween(Integer value1, Integer value2) {
            addCriterion("procedure_id not between", value1, value2, "procedureId");
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

        public Criteria andAdminLevelIdIsNull() {
            addCriterion("admin_level_id is null");
            return (Criteria) this;
        }

        public Criteria andAdminLevelIdIsNotNull() {
            addCriterion("admin_level_id is not null");
            return (Criteria) this;
        }

        public Criteria andAdminLevelIdEqualTo(Integer value) {
            addCriterion("admin_level_id =", value, "adminLevelId");
            return (Criteria) this;
        }

        public Criteria andAdminLevelIdNotEqualTo(Integer value) {
            addCriterion("admin_level_id <>", value, "adminLevelId");
            return (Criteria) this;
        }

        public Criteria andAdminLevelIdGreaterThan(Integer value) {
            addCriterion("admin_level_id >", value, "adminLevelId");
            return (Criteria) this;
        }

        public Criteria andAdminLevelIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("admin_level_id >=", value, "adminLevelId");
            return (Criteria) this;
        }

        public Criteria andAdminLevelIdLessThan(Integer value) {
            addCriterion("admin_level_id <", value, "adminLevelId");
            return (Criteria) this;
        }

        public Criteria andAdminLevelIdLessThanOrEqualTo(Integer value) {
            addCriterion("admin_level_id <=", value, "adminLevelId");
            return (Criteria) this;
        }

        public Criteria andAdminLevelIdIn(List<Integer> values) {
            addCriterion("admin_level_id in", values, "adminLevelId");
            return (Criteria) this;
        }

        public Criteria andAdminLevelIdNotIn(List<Integer> values) {
            addCriterion("admin_level_id not in", values, "adminLevelId");
            return (Criteria) this;
        }

        public Criteria andAdminLevelIdBetween(Integer value1, Integer value2) {
            addCriterion("admin_level_id between", value1, value2, "adminLevelId");
            return (Criteria) this;
        }

        public Criteria andAdminLevelIdNotBetween(Integer value1, Integer value2) {
            addCriterion("admin_level_id not between", value1, value2, "adminLevelId");
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

        public Criteria andAgreeCountIsNull() {
            addCriterion("agree_count is null");
            return (Criteria) this;
        }

        public Criteria andAgreeCountIsNotNull() {
            addCriterion("agree_count is not null");
            return (Criteria) this;
        }

        public Criteria andAgreeCountEqualTo(Integer value) {
            addCriterion("agree_count =", value, "agreeCount");
            return (Criteria) this;
        }

        public Criteria andAgreeCountNotEqualTo(Integer value) {
            addCriterion("agree_count <>", value, "agreeCount");
            return (Criteria) this;
        }

        public Criteria andAgreeCountGreaterThan(Integer value) {
            addCriterion("agree_count >", value, "agreeCount");
            return (Criteria) this;
        }

        public Criteria andAgreeCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("agree_count >=", value, "agreeCount");
            return (Criteria) this;
        }

        public Criteria andAgreeCountLessThan(Integer value) {
            addCriterion("agree_count <", value, "agreeCount");
            return (Criteria) this;
        }

        public Criteria andAgreeCountLessThanOrEqualTo(Integer value) {
            addCriterion("agree_count <=", value, "agreeCount");
            return (Criteria) this;
        }

        public Criteria andAgreeCountIn(List<Integer> values) {
            addCriterion("agree_count in", values, "agreeCount");
            return (Criteria) this;
        }

        public Criteria andAgreeCountNotIn(List<Integer> values) {
            addCriterion("agree_count not in", values, "agreeCount");
            return (Criteria) this;
        }

        public Criteria andAgreeCountBetween(Integer value1, Integer value2) {
            addCriterion("agree_count between", value1, value2, "agreeCount");
            return (Criteria) this;
        }

        public Criteria andAgreeCountNotBetween(Integer value1, Integer value2) {
            addCriterion("agree_count not between", value1, value2, "agreeCount");
            return (Criteria) this;
        }

        public Criteria andAbstainCountIsNull() {
            addCriterion("abstain_count is null");
            return (Criteria) this;
        }

        public Criteria andAbstainCountIsNotNull() {
            addCriterion("abstain_count is not null");
            return (Criteria) this;
        }

        public Criteria andAbstainCountEqualTo(Integer value) {
            addCriterion("abstain_count =", value, "abstainCount");
            return (Criteria) this;
        }

        public Criteria andAbstainCountNotEqualTo(Integer value) {
            addCriterion("abstain_count <>", value, "abstainCount");
            return (Criteria) this;
        }

        public Criteria andAbstainCountGreaterThan(Integer value) {
            addCriterion("abstain_count >", value, "abstainCount");
            return (Criteria) this;
        }

        public Criteria andAbstainCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("abstain_count >=", value, "abstainCount");
            return (Criteria) this;
        }

        public Criteria andAbstainCountLessThan(Integer value) {
            addCriterion("abstain_count <", value, "abstainCount");
            return (Criteria) this;
        }

        public Criteria andAbstainCountLessThanOrEqualTo(Integer value) {
            addCriterion("abstain_count <=", value, "abstainCount");
            return (Criteria) this;
        }

        public Criteria andAbstainCountIn(List<Integer> values) {
            addCriterion("abstain_count in", values, "abstainCount");
            return (Criteria) this;
        }

        public Criteria andAbstainCountNotIn(List<Integer> values) {
            addCriterion("abstain_count not in", values, "abstainCount");
            return (Criteria) this;
        }

        public Criteria andAbstainCountBetween(Integer value1, Integer value2) {
            addCriterion("abstain_count between", value1, value2, "abstainCount");
            return (Criteria) this;
        }

        public Criteria andAbstainCountNotBetween(Integer value1, Integer value2) {
            addCriterion("abstain_count not between", value1, value2, "abstainCount");
            return (Criteria) this;
        }

        public Criteria andDisagreeCountIsNull() {
            addCriterion("disagree_count is null");
            return (Criteria) this;
        }

        public Criteria andDisagreeCountIsNotNull() {
            addCriterion("disagree_count is not null");
            return (Criteria) this;
        }

        public Criteria andDisagreeCountEqualTo(Integer value) {
            addCriterion("disagree_count =", value, "disagreeCount");
            return (Criteria) this;
        }

        public Criteria andDisagreeCountNotEqualTo(Integer value) {
            addCriterion("disagree_count <>", value, "disagreeCount");
            return (Criteria) this;
        }

        public Criteria andDisagreeCountGreaterThan(Integer value) {
            addCriterion("disagree_count >", value, "disagreeCount");
            return (Criteria) this;
        }

        public Criteria andDisagreeCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("disagree_count >=", value, "disagreeCount");
            return (Criteria) this;
        }

        public Criteria andDisagreeCountLessThan(Integer value) {
            addCriterion("disagree_count <", value, "disagreeCount");
            return (Criteria) this;
        }

        public Criteria andDisagreeCountLessThanOrEqualTo(Integer value) {
            addCriterion("disagree_count <=", value, "disagreeCount");
            return (Criteria) this;
        }

        public Criteria andDisagreeCountIn(List<Integer> values) {
            addCriterion("disagree_count in", values, "disagreeCount");
            return (Criteria) this;
        }

        public Criteria andDisagreeCountNotIn(List<Integer> values) {
            addCriterion("disagree_count not in", values, "disagreeCount");
            return (Criteria) this;
        }

        public Criteria andDisagreeCountBetween(Integer value1, Integer value2) {
            addCriterion("disagree_count between", value1, value2, "disagreeCount");
            return (Criteria) this;
        }

        public Criteria andDisagreeCountNotBetween(Integer value1, Integer value2) {
            addCriterion("disagree_count not between", value1, value2, "disagreeCount");
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

        public Criteria andNameIsNull() {
            addCriterion("name is null");
            return (Criteria) this;
        }

        public Criteria andNameIsNotNull() {
            addCriterion("name is not null");
            return (Criteria) this;
        }

        public Criteria andNameEqualTo(String value) {
            addCriterion("name =", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotEqualTo(String value) {
            addCriterion("name <>", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThan(String value) {
            addCriterion("name >", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThanOrEqualTo(String value) {
            addCriterion("name >=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThan(String value) {
            addCriterion("name <", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThanOrEqualTo(String value) {
            addCriterion("name <=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLike(String value) {
            addCriterion("name like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotLike(String value) {
            addCriterion("name not like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameIn(List<String> values) {
            addCriterion("name in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotIn(List<String> values) {
            addCriterion("name not in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameBetween(String value1, String value2) {
            addCriterion("name between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotBetween(String value1, String value2) {
            addCriterion("name not between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andSeqIsNull() {
            addCriterion("seq is null");
            return (Criteria) this;
        }

        public Criteria andSeqIsNotNull() {
            addCriterion("seq is not null");
            return (Criteria) this;
        }

        public Criteria andSeqEqualTo(Integer value) {
            addCriterion("seq =", value, "seq");
            return (Criteria) this;
        }

        public Criteria andSeqNotEqualTo(Integer value) {
            addCriterion("seq <>", value, "seq");
            return (Criteria) this;
        }

        public Criteria andSeqGreaterThan(Integer value) {
            addCriterion("seq >", value, "seq");
            return (Criteria) this;
        }

        public Criteria andSeqGreaterThanOrEqualTo(Integer value) {
            addCriterion("seq >=", value, "seq");
            return (Criteria) this;
        }

        public Criteria andSeqLessThan(Integer value) {
            addCriterion("seq <", value, "seq");
            return (Criteria) this;
        }

        public Criteria andSeqLessThanOrEqualTo(Integer value) {
            addCriterion("seq <=", value, "seq");
            return (Criteria) this;
        }

        public Criteria andSeqIn(List<Integer> values) {
            addCriterion("seq in", values, "seq");
            return (Criteria) this;
        }

        public Criteria andSeqNotIn(List<Integer> values) {
            addCriterion("seq not in", values, "seq");
            return (Criteria) this;
        }

        public Criteria andSeqBetween(Integer value1, Integer value2) {
            addCriterion("seq between", value1, value2, "seq");
            return (Criteria) this;
        }

        public Criteria andSeqNotBetween(Integer value1, Integer value2) {
            addCriterion("seq not between", value1, value2, "seq");
            return (Criteria) this;
        }

        public Criteria andContentIsNull() {
            addCriterion("content is null");
            return (Criteria) this;
        }

        public Criteria andContentIsNotNull() {
            addCriterion("content is not null");
            return (Criteria) this;
        }

        public Criteria andContentEqualTo(String value) {
            addCriterion("content =", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentNotEqualTo(String value) {
            addCriterion("content <>", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentGreaterThan(String value) {
            addCriterion("content >", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentGreaterThanOrEqualTo(String value) {
            addCriterion("content >=", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentLessThan(String value) {
            addCriterion("content <", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentLessThanOrEqualTo(String value) {
            addCriterion("content <=", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentLike(String value) {
            addCriterion("content like", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentNotLike(String value) {
            addCriterion("content not like", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentIn(List<String> values) {
            addCriterion("content in", values, "content");
            return (Criteria) this;
        }

        public Criteria andContentNotIn(List<String> values) {
            addCriterion("content not in", values, "content");
            return (Criteria) this;
        }

        public Criteria andContentBetween(String value1, String value2) {
            addCriterion("content between", value1, value2, "content");
            return (Criteria) this;
        }

        public Criteria andContentNotBetween(String value1, String value2) {
            addCriterion("content not between", value1, value2, "content");
            return (Criteria) this;
        }

        public Criteria andCommitteeIdIsNull() {
            addCriterion("committee_id is null");
            return (Criteria) this;
        }

        public Criteria andCommitteeIdIsNotNull() {
            addCriterion("committee_id is not null");
            return (Criteria) this;
        }

        public Criteria andCommitteeIdEqualTo(Integer value) {
            addCriterion("committee_id =", value, "committeeId");
            return (Criteria) this;
        }

        public Criteria andCommitteeIdNotEqualTo(Integer value) {
            addCriterion("committee_id <>", value, "committeeId");
            return (Criteria) this;
        }

        public Criteria andCommitteeIdGreaterThan(Integer value) {
            addCriterion("committee_id >", value, "committeeId");
            return (Criteria) this;
        }

        public Criteria andCommitteeIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("committee_id >=", value, "committeeId");
            return (Criteria) this;
        }

        public Criteria andCommitteeIdLessThan(Integer value) {
            addCriterion("committee_id <", value, "committeeId");
            return (Criteria) this;
        }

        public Criteria andCommitteeIdLessThanOrEqualTo(Integer value) {
            addCriterion("committee_id <=", value, "committeeId");
            return (Criteria) this;
        }

        public Criteria andCommitteeIdIn(List<Integer> values) {
            addCriterion("committee_id in", values, "committeeId");
            return (Criteria) this;
        }

        public Criteria andCommitteeIdNotIn(List<Integer> values) {
            addCriterion("committee_id not in", values, "committeeId");
            return (Criteria) this;
        }

        public Criteria andCommitteeIdBetween(Integer value1, Integer value2) {
            addCriterion("committee_id between", value1, value2, "committeeId");
            return (Criteria) this;
        }

        public Criteria andCommitteeIdNotBetween(Integer value1, Integer value2) {
            addCriterion("committee_id not between", value1, value2, "committeeId");
            return (Criteria) this;
        }

        public Criteria andVoteFilePathIsNull() {
            addCriterion("vote_file_path is null");
            return (Criteria) this;
        }

        public Criteria andVoteFilePathIsNotNull() {
            addCriterion("vote_file_path is not null");
            return (Criteria) this;
        }

        public Criteria andVoteFilePathEqualTo(String value) {
            addCriterion("vote_file_path =", value, "voteFilePath");
            return (Criteria) this;
        }

        public Criteria andVoteFilePathNotEqualTo(String value) {
            addCriterion("vote_file_path <>", value, "voteFilePath");
            return (Criteria) this;
        }

        public Criteria andVoteFilePathGreaterThan(String value) {
            addCriterion("vote_file_path >", value, "voteFilePath");
            return (Criteria) this;
        }

        public Criteria andVoteFilePathGreaterThanOrEqualTo(String value) {
            addCriterion("vote_file_path >=", value, "voteFilePath");
            return (Criteria) this;
        }

        public Criteria andVoteFilePathLessThan(String value) {
            addCriterion("vote_file_path <", value, "voteFilePath");
            return (Criteria) this;
        }

        public Criteria andVoteFilePathLessThanOrEqualTo(String value) {
            addCriterion("vote_file_path <=", value, "voteFilePath");
            return (Criteria) this;
        }

        public Criteria andVoteFilePathLike(String value) {
            addCriterion("vote_file_path like", value, "voteFilePath");
            return (Criteria) this;
        }

        public Criteria andVoteFilePathNotLike(String value) {
            addCriterion("vote_file_path not like", value, "voteFilePath");
            return (Criteria) this;
        }

        public Criteria andVoteFilePathIn(List<String> values) {
            addCriterion("vote_file_path in", values, "voteFilePath");
            return (Criteria) this;
        }

        public Criteria andVoteFilePathNotIn(List<String> values) {
            addCriterion("vote_file_path not in", values, "voteFilePath");
            return (Criteria) this;
        }

        public Criteria andVoteFilePathBetween(String value1, String value2) {
            addCriterion("vote_file_path between", value1, value2, "voteFilePath");
            return (Criteria) this;
        }

        public Criteria andVoteFilePathNotBetween(String value1, String value2) {
            addCriterion("vote_file_path not between", value1, value2, "voteFilePath");
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

        public Criteria andHoldDateIsNull() {
            addCriterion("hold_date is null");
            return (Criteria) this;
        }

        public Criteria andHoldDateIsNotNull() {
            addCriterion("hold_date is not null");
            return (Criteria) this;
        }

        public Criteria andHoldDateEqualTo(Date value) {
            addCriterionForJDBCDate("hold_date =", value, "holdDate");
            return (Criteria) this;
        }

        public Criteria andHoldDateNotEqualTo(Date value) {
            addCriterionForJDBCDate("hold_date <>", value, "holdDate");
            return (Criteria) this;
        }

        public Criteria andHoldDateGreaterThan(Date value) {
            addCriterionForJDBCDate("hold_date >", value, "holdDate");
            return (Criteria) this;
        }

        public Criteria andHoldDateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("hold_date >=", value, "holdDate");
            return (Criteria) this;
        }

        public Criteria andHoldDateLessThan(Date value) {
            addCriterionForJDBCDate("hold_date <", value, "holdDate");
            return (Criteria) this;
        }

        public Criteria andHoldDateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("hold_date <=", value, "holdDate");
            return (Criteria) this;
        }

        public Criteria andHoldDateIn(List<Date> values) {
            addCriterionForJDBCDate("hold_date in", values, "holdDate");
            return (Criteria) this;
        }

        public Criteria andHoldDateNotIn(List<Date> values) {
            addCriterionForJDBCDate("hold_date not in", values, "holdDate");
            return (Criteria) this;
        }

        public Criteria andHoldDateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("hold_date between", value1, value2, "holdDate");
            return (Criteria) this;
        }

        public Criteria andHoldDateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("hold_date not between", value1, value2, "holdDate");
            return (Criteria) this;
        }

        public Criteria andCommitteeMemberCountIsNull() {
            addCriterion("committee_member_count is null");
            return (Criteria) this;
        }

        public Criteria andCommitteeMemberCountIsNotNull() {
            addCriterion("committee_member_count is not null");
            return (Criteria) this;
        }

        public Criteria andCommitteeMemberCountEqualTo(Integer value) {
            addCriterion("committee_member_count =", value, "committeeMemberCount");
            return (Criteria) this;
        }

        public Criteria andCommitteeMemberCountNotEqualTo(Integer value) {
            addCriterion("committee_member_count <>", value, "committeeMemberCount");
            return (Criteria) this;
        }

        public Criteria andCommitteeMemberCountGreaterThan(Integer value) {
            addCriterion("committee_member_count >", value, "committeeMemberCount");
            return (Criteria) this;
        }

        public Criteria andCommitteeMemberCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("committee_member_count >=", value, "committeeMemberCount");
            return (Criteria) this;
        }

        public Criteria andCommitteeMemberCountLessThan(Integer value) {
            addCriterion("committee_member_count <", value, "committeeMemberCount");
            return (Criteria) this;
        }

        public Criteria andCommitteeMemberCountLessThanOrEqualTo(Integer value) {
            addCriterion("committee_member_count <=", value, "committeeMemberCount");
            return (Criteria) this;
        }

        public Criteria andCommitteeMemberCountIn(List<Integer> values) {
            addCriterion("committee_member_count in", values, "committeeMemberCount");
            return (Criteria) this;
        }

        public Criteria andCommitteeMemberCountNotIn(List<Integer> values) {
            addCriterion("committee_member_count not in", values, "committeeMemberCount");
            return (Criteria) this;
        }

        public Criteria andCommitteeMemberCountBetween(Integer value1, Integer value2) {
            addCriterion("committee_member_count between", value1, value2, "committeeMemberCount");
            return (Criteria) this;
        }

        public Criteria andCommitteeMemberCountNotBetween(Integer value1, Integer value2) {
            addCriterion("committee_member_count not between", value1, value2, "committeeMemberCount");
            return (Criteria) this;
        }

        public Criteria andCountIsNull() {
            addCriterion("count is null");
            return (Criteria) this;
        }

        public Criteria andCountIsNotNull() {
            addCriterion("count is not null");
            return (Criteria) this;
        }

        public Criteria andCountEqualTo(Integer value) {
            addCriterion("count =", value, "count");
            return (Criteria) this;
        }

        public Criteria andCountNotEqualTo(Integer value) {
            addCriterion("count <>", value, "count");
            return (Criteria) this;
        }

        public Criteria andCountGreaterThan(Integer value) {
            addCriterion("count >", value, "count");
            return (Criteria) this;
        }

        public Criteria andCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("count >=", value, "count");
            return (Criteria) this;
        }

        public Criteria andCountLessThan(Integer value) {
            addCriterion("count <", value, "count");
            return (Criteria) this;
        }

        public Criteria andCountLessThanOrEqualTo(Integer value) {
            addCriterion("count <=", value, "count");
            return (Criteria) this;
        }

        public Criteria andCountIn(List<Integer> values) {
            addCriterion("count in", values, "count");
            return (Criteria) this;
        }

        public Criteria andCountNotIn(List<Integer> values) {
            addCriterion("count not in", values, "count");
            return (Criteria) this;
        }

        public Criteria andCountBetween(Integer value1, Integer value2) {
            addCriterion("count between", value1, value2, "count");
            return (Criteria) this;
        }

        public Criteria andCountNotBetween(Integer value1, Integer value2) {
            addCriterion("count not between", value1, value2, "count");
            return (Criteria) this;
        }

        public Criteria andAbsentCountIsNull() {
            addCriterion("absent_count is null");
            return (Criteria) this;
        }

        public Criteria andAbsentCountIsNotNull() {
            addCriterion("absent_count is not null");
            return (Criteria) this;
        }

        public Criteria andAbsentCountEqualTo(Integer value) {
            addCriterion("absent_count =", value, "absentCount");
            return (Criteria) this;
        }

        public Criteria andAbsentCountNotEqualTo(Integer value) {
            addCriterion("absent_count <>", value, "absentCount");
            return (Criteria) this;
        }

        public Criteria andAbsentCountGreaterThan(Integer value) {
            addCriterion("absent_count >", value, "absentCount");
            return (Criteria) this;
        }

        public Criteria andAbsentCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("absent_count >=", value, "absentCount");
            return (Criteria) this;
        }

        public Criteria andAbsentCountLessThan(Integer value) {
            addCriterion("absent_count <", value, "absentCount");
            return (Criteria) this;
        }

        public Criteria andAbsentCountLessThanOrEqualTo(Integer value) {
            addCriterion("absent_count <=", value, "absentCount");
            return (Criteria) this;
        }

        public Criteria andAbsentCountIn(List<Integer> values) {
            addCriterion("absent_count in", values, "absentCount");
            return (Criteria) this;
        }

        public Criteria andAbsentCountNotIn(List<Integer> values) {
            addCriterion("absent_count not in", values, "absentCount");
            return (Criteria) this;
        }

        public Criteria andAbsentCountBetween(Integer value1, Integer value2) {
            addCriterion("absent_count between", value1, value2, "absentCount");
            return (Criteria) this;
        }

        public Criteria andAbsentCountNotBetween(Integer value1, Integer value2) {
            addCriterion("absent_count not between", value1, value2, "absentCount");
            return (Criteria) this;
        }

        public Criteria andAttendUsersIsNull() {
            addCriterion("attend_users is null");
            return (Criteria) this;
        }

        public Criteria andAttendUsersIsNotNull() {
            addCriterion("attend_users is not null");
            return (Criteria) this;
        }

        public Criteria andAttendUsersEqualTo(String value) {
            addCriterion("attend_users =", value, "attendUsers");
            return (Criteria) this;
        }

        public Criteria andAttendUsersNotEqualTo(String value) {
            addCriterion("attend_users <>", value, "attendUsers");
            return (Criteria) this;
        }

        public Criteria andAttendUsersGreaterThan(String value) {
            addCriterion("attend_users >", value, "attendUsers");
            return (Criteria) this;
        }

        public Criteria andAttendUsersGreaterThanOrEqualTo(String value) {
            addCriterion("attend_users >=", value, "attendUsers");
            return (Criteria) this;
        }

        public Criteria andAttendUsersLessThan(String value) {
            addCriterion("attend_users <", value, "attendUsers");
            return (Criteria) this;
        }

        public Criteria andAttendUsersLessThanOrEqualTo(String value) {
            addCriterion("attend_users <=", value, "attendUsers");
            return (Criteria) this;
        }

        public Criteria andAttendUsersLike(String value) {
            addCriterion("attend_users like", value, "attendUsers");
            return (Criteria) this;
        }

        public Criteria andAttendUsersNotLike(String value) {
            addCriterion("attend_users not like", value, "attendUsers");
            return (Criteria) this;
        }

        public Criteria andAttendUsersIn(List<String> values) {
            addCriterion("attend_users in", values, "attendUsers");
            return (Criteria) this;
        }

        public Criteria andAttendUsersNotIn(List<String> values) {
            addCriterion("attend_users not in", values, "attendUsers");
            return (Criteria) this;
        }

        public Criteria andAttendUsersBetween(String value1, String value2) {
            addCriterion("attend_users between", value1, value2, "attendUsers");
            return (Criteria) this;
        }

        public Criteria andAttendUsersNotBetween(String value1, String value2) {
            addCriterion("attend_users not between", value1, value2, "attendUsers");
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

        public Criteria andLogFileIsNull() {
            addCriterion("log_file is null");
            return (Criteria) this;
        }

        public Criteria andLogFileIsNotNull() {
            addCriterion("log_file is not null");
            return (Criteria) this;
        }

        public Criteria andLogFileEqualTo(String value) {
            addCriterion("log_file =", value, "logFile");
            return (Criteria) this;
        }

        public Criteria andLogFileNotEqualTo(String value) {
            addCriterion("log_file <>", value, "logFile");
            return (Criteria) this;
        }

        public Criteria andLogFileGreaterThan(String value) {
            addCriterion("log_file >", value, "logFile");
            return (Criteria) this;
        }

        public Criteria andLogFileGreaterThanOrEqualTo(String value) {
            addCriterion("log_file >=", value, "logFile");
            return (Criteria) this;
        }

        public Criteria andLogFileLessThan(String value) {
            addCriterion("log_file <", value, "logFile");
            return (Criteria) this;
        }

        public Criteria andLogFileLessThanOrEqualTo(String value) {
            addCriterion("log_file <=", value, "logFile");
            return (Criteria) this;
        }

        public Criteria andLogFileLike(String value) {
            addCriterion("log_file like", value, "logFile");
            return (Criteria) this;
        }

        public Criteria andLogFileNotLike(String value) {
            addCriterion("log_file not like", value, "logFile");
            return (Criteria) this;
        }

        public Criteria andLogFileIn(List<String> values) {
            addCriterion("log_file in", values, "logFile");
            return (Criteria) this;
        }

        public Criteria andLogFileNotIn(List<String> values) {
            addCriterion("log_file not in", values, "logFile");
            return (Criteria) this;
        }

        public Criteria andLogFileBetween(String value1, String value2) {
            addCriterion("log_file between", value1, value2, "logFile");
            return (Criteria) this;
        }

        public Criteria andLogFileNotBetween(String value1, String value2) {
            addCriterion("log_file not between", value1, value2, "logFile");
            return (Criteria) this;
        }

        public Criteria andDispatchUserIdIsNull() {
            addCriterion("dispatch_user_id is null");
            return (Criteria) this;
        }

        public Criteria andDispatchUserIdIsNotNull() {
            addCriterion("dispatch_user_id is not null");
            return (Criteria) this;
        }

        public Criteria andDispatchUserIdEqualTo(Integer value) {
            addCriterion("dispatch_user_id =", value, "dispatchUserId");
            return (Criteria) this;
        }

        public Criteria andDispatchUserIdNotEqualTo(Integer value) {
            addCriterion("dispatch_user_id <>", value, "dispatchUserId");
            return (Criteria) this;
        }

        public Criteria andDispatchUserIdGreaterThan(Integer value) {
            addCriterion("dispatch_user_id >", value, "dispatchUserId");
            return (Criteria) this;
        }

        public Criteria andDispatchUserIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("dispatch_user_id >=", value, "dispatchUserId");
            return (Criteria) this;
        }

        public Criteria andDispatchUserIdLessThan(Integer value) {
            addCriterion("dispatch_user_id <", value, "dispatchUserId");
            return (Criteria) this;
        }

        public Criteria andDispatchUserIdLessThanOrEqualTo(Integer value) {
            addCriterion("dispatch_user_id <=", value, "dispatchUserId");
            return (Criteria) this;
        }

        public Criteria andDispatchUserIdIn(List<Integer> values) {
            addCriterion("dispatch_user_id in", values, "dispatchUserId");
            return (Criteria) this;
        }

        public Criteria andDispatchUserIdNotIn(List<Integer> values) {
            addCriterion("dispatch_user_id not in", values, "dispatchUserId");
            return (Criteria) this;
        }

        public Criteria andDispatchUserIdBetween(Integer value1, Integer value2) {
            addCriterion("dispatch_user_id between", value1, value2, "dispatchUserId");
            return (Criteria) this;
        }

        public Criteria andDispatchUserIdNotBetween(Integer value1, Integer value2) {
            addCriterion("dispatch_user_id not between", value1, value2, "dispatchUserId");
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