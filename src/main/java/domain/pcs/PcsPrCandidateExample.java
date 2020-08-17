package domain.pcs;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class PcsPrCandidateExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public PcsPrCandidateExample() {
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

        public Criteria andRecommendIdIsNull() {
            addCriterion("recommend_id is null");
            return (Criteria) this;
        }

        public Criteria andRecommendIdIsNotNull() {
            addCriterion("recommend_id is not null");
            return (Criteria) this;
        }

        public Criteria andRecommendIdEqualTo(Integer value) {
            addCriterion("recommend_id =", value, "recommendId");
            return (Criteria) this;
        }

        public Criteria andRecommendIdNotEqualTo(Integer value) {
            addCriterion("recommend_id <>", value, "recommendId");
            return (Criteria) this;
        }

        public Criteria andRecommendIdGreaterThan(Integer value) {
            addCriterion("recommend_id >", value, "recommendId");
            return (Criteria) this;
        }

        public Criteria andRecommendIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("recommend_id >=", value, "recommendId");
            return (Criteria) this;
        }

        public Criteria andRecommendIdLessThan(Integer value) {
            addCriterion("recommend_id <", value, "recommendId");
            return (Criteria) this;
        }

        public Criteria andRecommendIdLessThanOrEqualTo(Integer value) {
            addCriterion("recommend_id <=", value, "recommendId");
            return (Criteria) this;
        }

        public Criteria andRecommendIdIn(List<Integer> values) {
            addCriterion("recommend_id in", values, "recommendId");
            return (Criteria) this;
        }

        public Criteria andRecommendIdNotIn(List<Integer> values) {
            addCriterion("recommend_id not in", values, "recommendId");
            return (Criteria) this;
        }

        public Criteria andRecommendIdBetween(Integer value1, Integer value2) {
            addCriterion("recommend_id between", value1, value2, "recommendId");
            return (Criteria) this;
        }

        public Criteria andRecommendIdNotBetween(Integer value1, Integer value2) {
            addCriterion("recommend_id not between", value1, value2, "recommendId");
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

        public Criteria andLeaderSortOrderIsNull() {
            addCriterion("leader_sort_order is null");
            return (Criteria) this;
        }

        public Criteria andLeaderSortOrderIsNotNull() {
            addCriterion("leader_sort_order is not null");
            return (Criteria) this;
        }

        public Criteria andLeaderSortOrderEqualTo(Integer value) {
            addCriterion("leader_sort_order =", value, "leaderSortOrder");
            return (Criteria) this;
        }

        public Criteria andLeaderSortOrderNotEqualTo(Integer value) {
            addCriterion("leader_sort_order <>", value, "leaderSortOrder");
            return (Criteria) this;
        }

        public Criteria andLeaderSortOrderGreaterThan(Integer value) {
            addCriterion("leader_sort_order >", value, "leaderSortOrder");
            return (Criteria) this;
        }

        public Criteria andLeaderSortOrderGreaterThanOrEqualTo(Integer value) {
            addCriterion("leader_sort_order >=", value, "leaderSortOrder");
            return (Criteria) this;
        }

        public Criteria andLeaderSortOrderLessThan(Integer value) {
            addCriterion("leader_sort_order <", value, "leaderSortOrder");
            return (Criteria) this;
        }

        public Criteria andLeaderSortOrderLessThanOrEqualTo(Integer value) {
            addCriterion("leader_sort_order <=", value, "leaderSortOrder");
            return (Criteria) this;
        }

        public Criteria andLeaderSortOrderIn(List<Integer> values) {
            addCriterion("leader_sort_order in", values, "leaderSortOrder");
            return (Criteria) this;
        }

        public Criteria andLeaderSortOrderNotIn(List<Integer> values) {
            addCriterion("leader_sort_order not in", values, "leaderSortOrder");
            return (Criteria) this;
        }

        public Criteria andLeaderSortOrderBetween(Integer value1, Integer value2) {
            addCriterion("leader_sort_order between", value1, value2, "leaderSortOrder");
            return (Criteria) this;
        }

        public Criteria andLeaderSortOrderNotBetween(Integer value1, Integer value2) {
            addCriterion("leader_sort_order not between", value1, value2, "leaderSortOrder");
            return (Criteria) this;
        }

        public Criteria andUserTypeIsNull() {
            addCriterion("user_type is null");
            return (Criteria) this;
        }

        public Criteria andUserTypeIsNotNull() {
            addCriterion("user_type is not null");
            return (Criteria) this;
        }

        public Criteria andUserTypeEqualTo(Byte value) {
            addCriterion("user_type =", value, "userType");
            return (Criteria) this;
        }

        public Criteria andUserTypeNotEqualTo(Byte value) {
            addCriterion("user_type <>", value, "userType");
            return (Criteria) this;
        }

        public Criteria andUserTypeGreaterThan(Byte value) {
            addCriterion("user_type >", value, "userType");
            return (Criteria) this;
        }

        public Criteria andUserTypeGreaterThanOrEqualTo(Byte value) {
            addCriterion("user_type >=", value, "userType");
            return (Criteria) this;
        }

        public Criteria andUserTypeLessThan(Byte value) {
            addCriterion("user_type <", value, "userType");
            return (Criteria) this;
        }

        public Criteria andUserTypeLessThanOrEqualTo(Byte value) {
            addCriterion("user_type <=", value, "userType");
            return (Criteria) this;
        }

        public Criteria andUserTypeIn(List<Byte> values) {
            addCriterion("user_type in", values, "userType");
            return (Criteria) this;
        }

        public Criteria andUserTypeNotIn(List<Byte> values) {
            addCriterion("user_type not in", values, "userType");
            return (Criteria) this;
        }

        public Criteria andUserTypeBetween(Byte value1, Byte value2) {
            addCriterion("user_type between", value1, value2, "userType");
            return (Criteria) this;
        }

        public Criteria andUserTypeNotBetween(Byte value1, Byte value2) {
            addCriterion("user_type not between", value1, value2, "userType");
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

        public Criteria andWorkTimeIsNull() {
            addCriterion("work_time is null");
            return (Criteria) this;
        }

        public Criteria andWorkTimeIsNotNull() {
            addCriterion("work_time is not null");
            return (Criteria) this;
        }

        public Criteria andWorkTimeEqualTo(Date value) {
            addCriterionForJDBCDate("work_time =", value, "workTime");
            return (Criteria) this;
        }

        public Criteria andWorkTimeNotEqualTo(Date value) {
            addCriterionForJDBCDate("work_time <>", value, "workTime");
            return (Criteria) this;
        }

        public Criteria andWorkTimeGreaterThan(Date value) {
            addCriterionForJDBCDate("work_time >", value, "workTime");
            return (Criteria) this;
        }

        public Criteria andWorkTimeGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("work_time >=", value, "workTime");
            return (Criteria) this;
        }

        public Criteria andWorkTimeLessThan(Date value) {
            addCriterionForJDBCDate("work_time <", value, "workTime");
            return (Criteria) this;
        }

        public Criteria andWorkTimeLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("work_time <=", value, "workTime");
            return (Criteria) this;
        }

        public Criteria andWorkTimeIn(List<Date> values) {
            addCriterionForJDBCDate("work_time in", values, "workTime");
            return (Criteria) this;
        }

        public Criteria andWorkTimeNotIn(List<Date> values) {
            addCriterionForJDBCDate("work_time not in", values, "workTime");
            return (Criteria) this;
        }

        public Criteria andWorkTimeBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("work_time between", value1, value2, "workTime");
            return (Criteria) this;
        }

        public Criteria andWorkTimeNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("work_time not between", value1, value2, "workTime");
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

        public Criteria andEducationIsNull() {
            addCriterion("education is null");
            return (Criteria) this;
        }

        public Criteria andEducationIsNotNull() {
            addCriterion("education is not null");
            return (Criteria) this;
        }

        public Criteria andEducationEqualTo(String value) {
            addCriterion("education =", value, "education");
            return (Criteria) this;
        }

        public Criteria andEducationNotEqualTo(String value) {
            addCriterion("education <>", value, "education");
            return (Criteria) this;
        }

        public Criteria andEducationGreaterThan(String value) {
            addCriterion("education >", value, "education");
            return (Criteria) this;
        }

        public Criteria andEducationGreaterThanOrEqualTo(String value) {
            addCriterion("education >=", value, "education");
            return (Criteria) this;
        }

        public Criteria andEducationLessThan(String value) {
            addCriterion("education <", value, "education");
            return (Criteria) this;
        }

        public Criteria andEducationLessThanOrEqualTo(String value) {
            addCriterion("education <=", value, "education");
            return (Criteria) this;
        }

        public Criteria andEducationLike(String value) {
            addCriterion("education like", value, "education");
            return (Criteria) this;
        }

        public Criteria andEducationNotLike(String value) {
            addCriterion("education not like", value, "education");
            return (Criteria) this;
        }

        public Criteria andEducationIn(List<String> values) {
            addCriterion("education in", values, "education");
            return (Criteria) this;
        }

        public Criteria andEducationNotIn(List<String> values) {
            addCriterion("education not in", values, "education");
            return (Criteria) this;
        }

        public Criteria andEducationBetween(String value1, String value2) {
            addCriterion("education between", value1, value2, "education");
            return (Criteria) this;
        }

        public Criteria andEducationNotBetween(String value1, String value2) {
            addCriterion("education not between", value1, value2, "education");
            return (Criteria) this;
        }

        public Criteria andIsRetireIsNull() {
            addCriterion("is_retire is null");
            return (Criteria) this;
        }

        public Criteria andIsRetireIsNotNull() {
            addCriterion("is_retire is not null");
            return (Criteria) this;
        }

        public Criteria andIsRetireEqualTo(Boolean value) {
            addCriterion("is_retire =", value, "isRetire");
            return (Criteria) this;
        }

        public Criteria andIsRetireNotEqualTo(Boolean value) {
            addCriterion("is_retire <>", value, "isRetire");
            return (Criteria) this;
        }

        public Criteria andIsRetireGreaterThan(Boolean value) {
            addCriterion("is_retire >", value, "isRetire");
            return (Criteria) this;
        }

        public Criteria andIsRetireGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_retire >=", value, "isRetire");
            return (Criteria) this;
        }

        public Criteria andIsRetireLessThan(Boolean value) {
            addCriterion("is_retire <", value, "isRetire");
            return (Criteria) this;
        }

        public Criteria andIsRetireLessThanOrEqualTo(Boolean value) {
            addCriterion("is_retire <=", value, "isRetire");
            return (Criteria) this;
        }

        public Criteria andIsRetireIn(List<Boolean> values) {
            addCriterion("is_retire in", values, "isRetire");
            return (Criteria) this;
        }

        public Criteria andIsRetireNotIn(List<Boolean> values) {
            addCriterion("is_retire not in", values, "isRetire");
            return (Criteria) this;
        }

        public Criteria andIsRetireBetween(Boolean value1, Boolean value2) {
            addCriterion("is_retire between", value1, value2, "isRetire");
            return (Criteria) this;
        }

        public Criteria andIsRetireNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_retire not between", value1, value2, "isRetire");
            return (Criteria) this;
        }

        public Criteria andEduLevelIsNull() {
            addCriterion("edu_level is null");
            return (Criteria) this;
        }

        public Criteria andEduLevelIsNotNull() {
            addCriterion("edu_level is not null");
            return (Criteria) this;
        }

        public Criteria andEduLevelEqualTo(String value) {
            addCriterion("edu_level =", value, "eduLevel");
            return (Criteria) this;
        }

        public Criteria andEduLevelNotEqualTo(String value) {
            addCriterion("edu_level <>", value, "eduLevel");
            return (Criteria) this;
        }

        public Criteria andEduLevelGreaterThan(String value) {
            addCriterion("edu_level >", value, "eduLevel");
            return (Criteria) this;
        }

        public Criteria andEduLevelGreaterThanOrEqualTo(String value) {
            addCriterion("edu_level >=", value, "eduLevel");
            return (Criteria) this;
        }

        public Criteria andEduLevelLessThan(String value) {
            addCriterion("edu_level <", value, "eduLevel");
            return (Criteria) this;
        }

        public Criteria andEduLevelLessThanOrEqualTo(String value) {
            addCriterion("edu_level <=", value, "eduLevel");
            return (Criteria) this;
        }

        public Criteria andEduLevelLike(String value) {
            addCriterion("edu_level like", value, "eduLevel");
            return (Criteria) this;
        }

        public Criteria andEduLevelNotLike(String value) {
            addCriterion("edu_level not like", value, "eduLevel");
            return (Criteria) this;
        }

        public Criteria andEduLevelIn(List<String> values) {
            addCriterion("edu_level in", values, "eduLevel");
            return (Criteria) this;
        }

        public Criteria andEduLevelNotIn(List<String> values) {
            addCriterion("edu_level not in", values, "eduLevel");
            return (Criteria) this;
        }

        public Criteria andEduLevelBetween(String value1, String value2) {
            addCriterion("edu_level between", value1, value2, "eduLevel");
            return (Criteria) this;
        }

        public Criteria andEduLevelNotBetween(String value1, String value2) {
            addCriterion("edu_level not between", value1, value2, "eduLevel");
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

        public Criteria andConfigIdIsNull() {
            addCriterion("config_id is null");
            return (Criteria) this;
        }

        public Criteria andConfigIdIsNotNull() {
            addCriterion("config_id is not null");
            return (Criteria) this;
        }

        public Criteria andConfigIdEqualTo(Integer value) {
            addCriterion("config_id =", value, "configId");
            return (Criteria) this;
        }

        public Criteria andConfigIdNotEqualTo(Integer value) {
            addCriterion("config_id <>", value, "configId");
            return (Criteria) this;
        }

        public Criteria andConfigIdGreaterThan(Integer value) {
            addCriterion("config_id >", value, "configId");
            return (Criteria) this;
        }

        public Criteria andConfigIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("config_id >=", value, "configId");
            return (Criteria) this;
        }

        public Criteria andConfigIdLessThan(Integer value) {
            addCriterion("config_id <", value, "configId");
            return (Criteria) this;
        }

        public Criteria andConfigIdLessThanOrEqualTo(Integer value) {
            addCriterion("config_id <=", value, "configId");
            return (Criteria) this;
        }

        public Criteria andConfigIdIn(List<Integer> values) {
            addCriterion("config_id in", values, "configId");
            return (Criteria) this;
        }

        public Criteria andConfigIdNotIn(List<Integer> values) {
            addCriterion("config_id not in", values, "configId");
            return (Criteria) this;
        }

        public Criteria andConfigIdBetween(Integer value1, Integer value2) {
            addCriterion("config_id between", value1, value2, "configId");
            return (Criteria) this;
        }

        public Criteria andConfigIdNotBetween(Integer value1, Integer value2) {
            addCriterion("config_id not between", value1, value2, "configId");
            return (Criteria) this;
        }

        public Criteria andStageIsNull() {
            addCriterion("stage is null");
            return (Criteria) this;
        }

        public Criteria andStageIsNotNull() {
            addCriterion("stage is not null");
            return (Criteria) this;
        }

        public Criteria andStageEqualTo(Byte value) {
            addCriterion("stage =", value, "stage");
            return (Criteria) this;
        }

        public Criteria andStageNotEqualTo(Byte value) {
            addCriterion("stage <>", value, "stage");
            return (Criteria) this;
        }

        public Criteria andStageGreaterThan(Byte value) {
            addCriterion("stage >", value, "stage");
            return (Criteria) this;
        }

        public Criteria andStageGreaterThanOrEqualTo(Byte value) {
            addCriterion("stage >=", value, "stage");
            return (Criteria) this;
        }

        public Criteria andStageLessThan(Byte value) {
            addCriterion("stage <", value, "stage");
            return (Criteria) this;
        }

        public Criteria andStageLessThanOrEqualTo(Byte value) {
            addCriterion("stage <=", value, "stage");
            return (Criteria) this;
        }

        public Criteria andStageIn(List<Byte> values) {
            addCriterion("stage in", values, "stage");
            return (Criteria) this;
        }

        public Criteria andStageNotIn(List<Byte> values) {
            addCriterion("stage not in", values, "stage");
            return (Criteria) this;
        }

        public Criteria andStageBetween(Byte value1, Byte value2) {
            addCriterion("stage between", value1, value2, "stage");
            return (Criteria) this;
        }

        public Criteria andStageNotBetween(Byte value1, Byte value2) {
            addCriterion("stage not between", value1, value2, "stage");
            return (Criteria) this;
        }

        public Criteria andPartySortOrderIsNull() {
            addCriterion("party_sort_order is null");
            return (Criteria) this;
        }

        public Criteria andPartySortOrderIsNotNull() {
            addCriterion("party_sort_order is not null");
            return (Criteria) this;
        }

        public Criteria andPartySortOrderEqualTo(Integer value) {
            addCriterion("party_sort_order =", value, "partySortOrder");
            return (Criteria) this;
        }

        public Criteria andPartySortOrderNotEqualTo(Integer value) {
            addCriterion("party_sort_order <>", value, "partySortOrder");
            return (Criteria) this;
        }

        public Criteria andPartySortOrderGreaterThan(Integer value) {
            addCriterion("party_sort_order >", value, "partySortOrder");
            return (Criteria) this;
        }

        public Criteria andPartySortOrderGreaterThanOrEqualTo(Integer value) {
            addCriterion("party_sort_order >=", value, "partySortOrder");
            return (Criteria) this;
        }

        public Criteria andPartySortOrderLessThan(Integer value) {
            addCriterion("party_sort_order <", value, "partySortOrder");
            return (Criteria) this;
        }

        public Criteria andPartySortOrderLessThanOrEqualTo(Integer value) {
            addCriterion("party_sort_order <=", value, "partySortOrder");
            return (Criteria) this;
        }

        public Criteria andPartySortOrderIn(List<Integer> values) {
            addCriterion("party_sort_order in", values, "partySortOrder");
            return (Criteria) this;
        }

        public Criteria andPartySortOrderNotIn(List<Integer> values) {
            addCriterion("party_sort_order not in", values, "partySortOrder");
            return (Criteria) this;
        }

        public Criteria andPartySortOrderBetween(Integer value1, Integer value2) {
            addCriterion("party_sort_order between", value1, value2, "partySortOrder");
            return (Criteria) this;
        }

        public Criteria andPartySortOrderNotBetween(Integer value1, Integer value2) {
            addCriterion("party_sort_order not between", value1, value2, "partySortOrder");
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

        public Criteria andBranchVoteIsNull() {
            addCriterion("branch_vote is null");
            return (Criteria) this;
        }

        public Criteria andBranchVoteIsNotNull() {
            addCriterion("branch_vote is not null");
            return (Criteria) this;
        }

        public Criteria andBranchVoteEqualTo(Integer value) {
            addCriterion("branch_vote =", value, "branchVote");
            return (Criteria) this;
        }

        public Criteria andBranchVoteNotEqualTo(Integer value) {
            addCriterion("branch_vote <>", value, "branchVote");
            return (Criteria) this;
        }

        public Criteria andBranchVoteGreaterThan(Integer value) {
            addCriterion("branch_vote >", value, "branchVote");
            return (Criteria) this;
        }

        public Criteria andBranchVoteGreaterThanOrEqualTo(Integer value) {
            addCriterion("branch_vote >=", value, "branchVote");
            return (Criteria) this;
        }

        public Criteria andBranchVoteLessThan(Integer value) {
            addCriterion("branch_vote <", value, "branchVote");
            return (Criteria) this;
        }

        public Criteria andBranchVoteLessThanOrEqualTo(Integer value) {
            addCriterion("branch_vote <=", value, "branchVote");
            return (Criteria) this;
        }

        public Criteria andBranchVoteIn(List<Integer> values) {
            addCriterion("branch_vote in", values, "branchVote");
            return (Criteria) this;
        }

        public Criteria andBranchVoteNotIn(List<Integer> values) {
            addCriterion("branch_vote not in", values, "branchVote");
            return (Criteria) this;
        }

        public Criteria andBranchVoteBetween(Integer value1, Integer value2) {
            addCriterion("branch_vote between", value1, value2, "branchVote");
            return (Criteria) this;
        }

        public Criteria andBranchVoteNotBetween(Integer value1, Integer value2) {
            addCriterion("branch_vote not between", value1, value2, "branchVote");
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

        public Criteria andVoteIsNull() {
            addCriterion("vote is null");
            return (Criteria) this;
        }

        public Criteria andVoteIsNotNull() {
            addCriterion("vote is not null");
            return (Criteria) this;
        }

        public Criteria andVoteEqualTo(Integer value) {
            addCriterion("vote =", value, "vote");
            return (Criteria) this;
        }

        public Criteria andVoteNotEqualTo(Integer value) {
            addCriterion("vote <>", value, "vote");
            return (Criteria) this;
        }

        public Criteria andVoteGreaterThan(Integer value) {
            addCriterion("vote >", value, "vote");
            return (Criteria) this;
        }

        public Criteria andVoteGreaterThanOrEqualTo(Integer value) {
            addCriterion("vote >=", value, "vote");
            return (Criteria) this;
        }

        public Criteria andVoteLessThan(Integer value) {
            addCriterion("vote <", value, "vote");
            return (Criteria) this;
        }

        public Criteria andVoteLessThanOrEqualTo(Integer value) {
            addCriterion("vote <=", value, "vote");
            return (Criteria) this;
        }

        public Criteria andVoteIn(List<Integer> values) {
            addCriterion("vote in", values, "vote");
            return (Criteria) this;
        }

        public Criteria andVoteNotIn(List<Integer> values) {
            addCriterion("vote not in", values, "vote");
            return (Criteria) this;
        }

        public Criteria andVoteBetween(Integer value1, Integer value2) {
            addCriterion("vote between", value1, value2, "vote");
            return (Criteria) this;
        }

        public Criteria andVoteNotBetween(Integer value1, Integer value2) {
            addCriterion("vote not between", value1, value2, "vote");
            return (Criteria) this;
        }

        public Criteria andVote3IsNull() {
            addCriterion("vote3 is null");
            return (Criteria) this;
        }

        public Criteria andVote3IsNotNull() {
            addCriterion("vote3 is not null");
            return (Criteria) this;
        }

        public Criteria andVote3EqualTo(Integer value) {
            addCriterion("vote3 =", value, "vote3");
            return (Criteria) this;
        }

        public Criteria andVote3NotEqualTo(Integer value) {
            addCriterion("vote3 <>", value, "vote3");
            return (Criteria) this;
        }

        public Criteria andVote3GreaterThan(Integer value) {
            addCriterion("vote3 >", value, "vote3");
            return (Criteria) this;
        }

        public Criteria andVote3GreaterThanOrEqualTo(Integer value) {
            addCriterion("vote3 >=", value, "vote3");
            return (Criteria) this;
        }

        public Criteria andVote3LessThan(Integer value) {
            addCriterion("vote3 <", value, "vote3");
            return (Criteria) this;
        }

        public Criteria andVote3LessThanOrEqualTo(Integer value) {
            addCriterion("vote3 <=", value, "vote3");
            return (Criteria) this;
        }

        public Criteria andVote3In(List<Integer> values) {
            addCriterion("vote3 in", values, "vote3");
            return (Criteria) this;
        }

        public Criteria andVote3NotIn(List<Integer> values) {
            addCriterion("vote3 not in", values, "vote3");
            return (Criteria) this;
        }

        public Criteria andVote3Between(Integer value1, Integer value2) {
            addCriterion("vote3 between", value1, value2, "vote3");
            return (Criteria) this;
        }

        public Criteria andVote3NotBetween(Integer value1, Integer value2) {
            addCriterion("vote3 not between", value1, value2, "vote3");
            return (Criteria) this;
        }

        public Criteria andIsChosenIsNull() {
            addCriterion("is_chosen is null");
            return (Criteria) this;
        }

        public Criteria andIsChosenIsNotNull() {
            addCriterion("is_chosen is not null");
            return (Criteria) this;
        }

        public Criteria andIsChosenEqualTo(Boolean value) {
            addCriterion("is_chosen =", value, "isChosen");
            return (Criteria) this;
        }

        public Criteria andIsChosenNotEqualTo(Boolean value) {
            addCriterion("is_chosen <>", value, "isChosen");
            return (Criteria) this;
        }

        public Criteria andIsChosenGreaterThan(Boolean value) {
            addCriterion("is_chosen >", value, "isChosen");
            return (Criteria) this;
        }

        public Criteria andIsChosenGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_chosen >=", value, "isChosen");
            return (Criteria) this;
        }

        public Criteria andIsChosenLessThan(Boolean value) {
            addCriterion("is_chosen <", value, "isChosen");
            return (Criteria) this;
        }

        public Criteria andIsChosenLessThanOrEqualTo(Boolean value) {
            addCriterion("is_chosen <=", value, "isChosen");
            return (Criteria) this;
        }

        public Criteria andIsChosenIn(List<Boolean> values) {
            addCriterion("is_chosen in", values, "isChosen");
            return (Criteria) this;
        }

        public Criteria andIsChosenNotIn(List<Boolean> values) {
            addCriterion("is_chosen not in", values, "isChosen");
            return (Criteria) this;
        }

        public Criteria andIsChosenBetween(Boolean value1, Boolean value2) {
            addCriterion("is_chosen between", value1, value2, "isChosen");
            return (Criteria) this;
        }

        public Criteria andIsChosenNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_chosen not between", value1, value2, "isChosen");
            return (Criteria) this;
        }

        public Criteria andIsProposalIsNull() {
            addCriterion("is_proposal is null");
            return (Criteria) this;
        }

        public Criteria andIsProposalIsNotNull() {
            addCriterion("is_proposal is not null");
            return (Criteria) this;
        }

        public Criteria andIsProposalEqualTo(Boolean value) {
            addCriterion("is_proposal =", value, "isProposal");
            return (Criteria) this;
        }

        public Criteria andIsProposalNotEqualTo(Boolean value) {
            addCriterion("is_proposal <>", value, "isProposal");
            return (Criteria) this;
        }

        public Criteria andIsProposalGreaterThan(Boolean value) {
            addCriterion("is_proposal >", value, "isProposal");
            return (Criteria) this;
        }

        public Criteria andIsProposalGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_proposal >=", value, "isProposal");
            return (Criteria) this;
        }

        public Criteria andIsProposalLessThan(Boolean value) {
            addCriterion("is_proposal <", value, "isProposal");
            return (Criteria) this;
        }

        public Criteria andIsProposalLessThanOrEqualTo(Boolean value) {
            addCriterion("is_proposal <=", value, "isProposal");
            return (Criteria) this;
        }

        public Criteria andIsProposalIn(List<Boolean> values) {
            addCriterion("is_proposal in", values, "isProposal");
            return (Criteria) this;
        }

        public Criteria andIsProposalNotIn(List<Boolean> values) {
            addCriterion("is_proposal not in", values, "isProposal");
            return (Criteria) this;
        }

        public Criteria andIsProposalBetween(Boolean value1, Boolean value2) {
            addCriterion("is_proposal between", value1, value2, "isProposal");
            return (Criteria) this;
        }

        public Criteria andIsProposalNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_proposal not between", value1, value2, "isProposal");
            return (Criteria) this;
        }

        public Criteria andProposalSortOrderIsNull() {
            addCriterion("proposal_sort_order is null");
            return (Criteria) this;
        }

        public Criteria andProposalSortOrderIsNotNull() {
            addCriterion("proposal_sort_order is not null");
            return (Criteria) this;
        }

        public Criteria andProposalSortOrderEqualTo(Integer value) {
            addCriterion("proposal_sort_order =", value, "proposalSortOrder");
            return (Criteria) this;
        }

        public Criteria andProposalSortOrderNotEqualTo(Integer value) {
            addCriterion("proposal_sort_order <>", value, "proposalSortOrder");
            return (Criteria) this;
        }

        public Criteria andProposalSortOrderGreaterThan(Integer value) {
            addCriterion("proposal_sort_order >", value, "proposalSortOrder");
            return (Criteria) this;
        }

        public Criteria andProposalSortOrderGreaterThanOrEqualTo(Integer value) {
            addCriterion("proposal_sort_order >=", value, "proposalSortOrder");
            return (Criteria) this;
        }

        public Criteria andProposalSortOrderLessThan(Integer value) {
            addCriterion("proposal_sort_order <", value, "proposalSortOrder");
            return (Criteria) this;
        }

        public Criteria andProposalSortOrderLessThanOrEqualTo(Integer value) {
            addCriterion("proposal_sort_order <=", value, "proposalSortOrder");
            return (Criteria) this;
        }

        public Criteria andProposalSortOrderIn(List<Integer> values) {
            addCriterion("proposal_sort_order in", values, "proposalSortOrder");
            return (Criteria) this;
        }

        public Criteria andProposalSortOrderNotIn(List<Integer> values) {
            addCriterion("proposal_sort_order not in", values, "proposalSortOrder");
            return (Criteria) this;
        }

        public Criteria andProposalSortOrderBetween(Integer value1, Integer value2) {
            addCriterion("proposal_sort_order between", value1, value2, "proposalSortOrder");
            return (Criteria) this;
        }

        public Criteria andProposalSortOrderNotBetween(Integer value1, Integer value2) {
            addCriterion("proposal_sort_order not between", value1, value2, "proposalSortOrder");
            return (Criteria) this;
        }

        public Criteria andIsFromStageIsNull() {
            addCriterion("is_from_stage is null");
            return (Criteria) this;
        }

        public Criteria andIsFromStageIsNotNull() {
            addCriterion("is_from_stage is not null");
            return (Criteria) this;
        }

        public Criteria andIsFromStageEqualTo(Boolean value) {
            addCriterion("is_from_stage =", value, "isFromStage");
            return (Criteria) this;
        }

        public Criteria andIsFromStageNotEqualTo(Boolean value) {
            addCriterion("is_from_stage <>", value, "isFromStage");
            return (Criteria) this;
        }

        public Criteria andIsFromStageGreaterThan(Boolean value) {
            addCriterion("is_from_stage >", value, "isFromStage");
            return (Criteria) this;
        }

        public Criteria andIsFromStageGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_from_stage >=", value, "isFromStage");
            return (Criteria) this;
        }

        public Criteria andIsFromStageLessThan(Boolean value) {
            addCriterion("is_from_stage <", value, "isFromStage");
            return (Criteria) this;
        }

        public Criteria andIsFromStageLessThanOrEqualTo(Boolean value) {
            addCriterion("is_from_stage <=", value, "isFromStage");
            return (Criteria) this;
        }

        public Criteria andIsFromStageIn(List<Boolean> values) {
            addCriterion("is_from_stage in", values, "isFromStage");
            return (Criteria) this;
        }

        public Criteria andIsFromStageNotIn(List<Boolean> values) {
            addCriterion("is_from_stage not in", values, "isFromStage");
            return (Criteria) this;
        }

        public Criteria andIsFromStageBetween(Boolean value1, Boolean value2) {
            addCriterion("is_from_stage between", value1, value2, "isFromStage");
            return (Criteria) this;
        }

        public Criteria andIsFromStageNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_from_stage not between", value1, value2, "isFromStage");
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

        public Criteria andRealnameSortOrderIsNull() {
            addCriterion("realname_sort_order is null");
            return (Criteria) this;
        }

        public Criteria andRealnameSortOrderIsNotNull() {
            addCriterion("realname_sort_order is not null");
            return (Criteria) this;
        }

        public Criteria andRealnameSortOrderEqualTo(Integer value) {
            addCriterion("realname_sort_order =", value, "realnameSortOrder");
            return (Criteria) this;
        }

        public Criteria andRealnameSortOrderNotEqualTo(Integer value) {
            addCriterion("realname_sort_order <>", value, "realnameSortOrder");
            return (Criteria) this;
        }

        public Criteria andRealnameSortOrderGreaterThan(Integer value) {
            addCriterion("realname_sort_order >", value, "realnameSortOrder");
            return (Criteria) this;
        }

        public Criteria andRealnameSortOrderGreaterThanOrEqualTo(Integer value) {
            addCriterion("realname_sort_order >=", value, "realnameSortOrder");
            return (Criteria) this;
        }

        public Criteria andRealnameSortOrderLessThan(Integer value) {
            addCriterion("realname_sort_order <", value, "realnameSortOrder");
            return (Criteria) this;
        }

        public Criteria andRealnameSortOrderLessThanOrEqualTo(Integer value) {
            addCriterion("realname_sort_order <=", value, "realnameSortOrder");
            return (Criteria) this;
        }

        public Criteria andRealnameSortOrderIn(List<Integer> values) {
            addCriterion("realname_sort_order in", values, "realnameSortOrder");
            return (Criteria) this;
        }

        public Criteria andRealnameSortOrderNotIn(List<Integer> values) {
            addCriterion("realname_sort_order not in", values, "realnameSortOrder");
            return (Criteria) this;
        }

        public Criteria andRealnameSortOrderBetween(Integer value1, Integer value2) {
            addCriterion("realname_sort_order between", value1, value2, "realnameSortOrder");
            return (Criteria) this;
        }

        public Criteria andRealnameSortOrderNotBetween(Integer value1, Integer value2) {
            addCriterion("realname_sort_order not between", value1, value2, "realnameSortOrder");
            return (Criteria) this;
        }

        public Criteria andAddTimeIsNull() {
            addCriterion("add_time is null");
            return (Criteria) this;
        }

        public Criteria andAddTimeIsNotNull() {
            addCriterion("add_time is not null");
            return (Criteria) this;
        }

        public Criteria andAddTimeEqualTo(Date value) {
            addCriterion("add_time =", value, "addTime");
            return (Criteria) this;
        }

        public Criteria andAddTimeNotEqualTo(Date value) {
            addCriterion("add_time <>", value, "addTime");
            return (Criteria) this;
        }

        public Criteria andAddTimeGreaterThan(Date value) {
            addCriterion("add_time >", value, "addTime");
            return (Criteria) this;
        }

        public Criteria andAddTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("add_time >=", value, "addTime");
            return (Criteria) this;
        }

        public Criteria andAddTimeLessThan(Date value) {
            addCriterion("add_time <", value, "addTime");
            return (Criteria) this;
        }

        public Criteria andAddTimeLessThanOrEqualTo(Date value) {
            addCriterion("add_time <=", value, "addTime");
            return (Criteria) this;
        }

        public Criteria andAddTimeIn(List<Date> values) {
            addCriterion("add_time in", values, "addTime");
            return (Criteria) this;
        }

        public Criteria andAddTimeNotIn(List<Date> values) {
            addCriterion("add_time not in", values, "addTime");
            return (Criteria) this;
        }

        public Criteria andAddTimeBetween(Date value1, Date value2) {
            addCriterion("add_time between", value1, value2, "addTime");
            return (Criteria) this;
        }

        public Criteria andAddTimeNotBetween(Date value1, Date value2) {
            addCriterion("add_time not between", value1, value2, "addTime");
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