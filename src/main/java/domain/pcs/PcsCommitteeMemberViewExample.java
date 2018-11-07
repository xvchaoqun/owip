package domain.pcs;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class PcsCommitteeMemberViewExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public PcsCommitteeMemberViewExample() {
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

        public Criteria andTypeIsNull() {
            addCriterion("type is null");
            return (Criteria) this;
        }

        public Criteria andTypeIsNotNull() {
            addCriterion("type is not null");
            return (Criteria) this;
        }

        public Criteria andTypeEqualTo(Boolean value) {
            addCriterion("type =", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotEqualTo(Boolean value) {
            addCriterion("type <>", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThan(Boolean value) {
            addCriterion("type >", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThanOrEqualTo(Boolean value) {
            addCriterion("type >=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThan(Boolean value) {
            addCriterion("type <", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThanOrEqualTo(Boolean value) {
            addCriterion("type <=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeIn(List<Boolean> values) {
            addCriterion("type in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotIn(List<Boolean> values) {
            addCriterion("type not in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeBetween(Boolean value1, Boolean value2) {
            addCriterion("type between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotBetween(Boolean value1, Boolean value2) {
            addCriterion("type not between", value1, value2, "type");
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

        public Criteria andPostIsNull() {
            addCriterion("post is null");
            return (Criteria) this;
        }

        public Criteria andPostIsNotNull() {
            addCriterion("post is not null");
            return (Criteria) this;
        }

        public Criteria andPostEqualTo(Integer value) {
            addCriterion("post =", value, "post");
            return (Criteria) this;
        }

        public Criteria andPostNotEqualTo(Integer value) {
            addCriterion("post <>", value, "post");
            return (Criteria) this;
        }

        public Criteria andPostGreaterThan(Integer value) {
            addCriterion("post >", value, "post");
            return (Criteria) this;
        }

        public Criteria andPostGreaterThanOrEqualTo(Integer value) {
            addCriterion("post >=", value, "post");
            return (Criteria) this;
        }

        public Criteria andPostLessThan(Integer value) {
            addCriterion("post <", value, "post");
            return (Criteria) this;
        }

        public Criteria andPostLessThanOrEqualTo(Integer value) {
            addCriterion("post <=", value, "post");
            return (Criteria) this;
        }

        public Criteria andPostIn(List<Integer> values) {
            addCriterion("post in", values, "post");
            return (Criteria) this;
        }

        public Criteria andPostNotIn(List<Integer> values) {
            addCriterion("post not in", values, "post");
            return (Criteria) this;
        }

        public Criteria andPostBetween(Integer value1, Integer value2) {
            addCriterion("post between", value1, value2, "post");
            return (Criteria) this;
        }

        public Criteria andPostNotBetween(Integer value1, Integer value2) {
            addCriterion("post not between", value1, value2, "post");
            return (Criteria) this;
        }

        public Criteria andPostDateIsNull() {
            addCriterion("post_date is null");
            return (Criteria) this;
        }

        public Criteria andPostDateIsNotNull() {
            addCriterion("post_date is not null");
            return (Criteria) this;
        }

        public Criteria andPostDateEqualTo(Date value) {
            addCriterionForJDBCDate("post_date =", value, "postDate");
            return (Criteria) this;
        }

        public Criteria andPostDateNotEqualTo(Date value) {
            addCriterionForJDBCDate("post_date <>", value, "postDate");
            return (Criteria) this;
        }

        public Criteria andPostDateGreaterThan(Date value) {
            addCriterionForJDBCDate("post_date >", value, "postDate");
            return (Criteria) this;
        }

        public Criteria andPostDateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("post_date >=", value, "postDate");
            return (Criteria) this;
        }

        public Criteria andPostDateLessThan(Date value) {
            addCriterionForJDBCDate("post_date <", value, "postDate");
            return (Criteria) this;
        }

        public Criteria andPostDateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("post_date <=", value, "postDate");
            return (Criteria) this;
        }

        public Criteria andPostDateIn(List<Date> values) {
            addCriterionForJDBCDate("post_date in", values, "postDate");
            return (Criteria) this;
        }

        public Criteria andPostDateNotIn(List<Date> values) {
            addCriterionForJDBCDate("post_date not in", values, "postDate");
            return (Criteria) this;
        }

        public Criteria andPostDateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("post_date between", value1, value2, "postDate");
            return (Criteria) this;
        }

        public Criteria andPostDateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("post_date not between", value1, value2, "postDate");
            return (Criteria) this;
        }

        public Criteria andPostFilePathIsNull() {
            addCriterion("post_file_path is null");
            return (Criteria) this;
        }

        public Criteria andPostFilePathIsNotNull() {
            addCriterion("post_file_path is not null");
            return (Criteria) this;
        }

        public Criteria andPostFilePathEqualTo(String value) {
            addCriterion("post_file_path =", value, "postFilePath");
            return (Criteria) this;
        }

        public Criteria andPostFilePathNotEqualTo(String value) {
            addCriterion("post_file_path <>", value, "postFilePath");
            return (Criteria) this;
        }

        public Criteria andPostFilePathGreaterThan(String value) {
            addCriterion("post_file_path >", value, "postFilePath");
            return (Criteria) this;
        }

        public Criteria andPostFilePathGreaterThanOrEqualTo(String value) {
            addCriterion("post_file_path >=", value, "postFilePath");
            return (Criteria) this;
        }

        public Criteria andPostFilePathLessThan(String value) {
            addCriterion("post_file_path <", value, "postFilePath");
            return (Criteria) this;
        }

        public Criteria andPostFilePathLessThanOrEqualTo(String value) {
            addCriterion("post_file_path <=", value, "postFilePath");
            return (Criteria) this;
        }

        public Criteria andPostFilePathLike(String value) {
            addCriterion("post_file_path like", value, "postFilePath");
            return (Criteria) this;
        }

        public Criteria andPostFilePathNotLike(String value) {
            addCriterion("post_file_path not like", value, "postFilePath");
            return (Criteria) this;
        }

        public Criteria andPostFilePathIn(List<String> values) {
            addCriterion("post_file_path in", values, "postFilePath");
            return (Criteria) this;
        }

        public Criteria andPostFilePathNotIn(List<String> values) {
            addCriterion("post_file_path not in", values, "postFilePath");
            return (Criteria) this;
        }

        public Criteria andPostFilePathBetween(String value1, String value2) {
            addCriterion("post_file_path between", value1, value2, "postFilePath");
            return (Criteria) this;
        }

        public Criteria andPostFilePathNotBetween(String value1, String value2) {
            addCriterion("post_file_path not between", value1, value2, "postFilePath");
            return (Criteria) this;
        }

        public Criteria andIsQuitIsNull() {
            addCriterion("is_quit is null");
            return (Criteria) this;
        }

        public Criteria andIsQuitIsNotNull() {
            addCriterion("is_quit is not null");
            return (Criteria) this;
        }

        public Criteria andIsQuitEqualTo(Boolean value) {
            addCriterion("is_quit =", value, "isQuit");
            return (Criteria) this;
        }

        public Criteria andIsQuitNotEqualTo(Boolean value) {
            addCriterion("is_quit <>", value, "isQuit");
            return (Criteria) this;
        }

        public Criteria andIsQuitGreaterThan(Boolean value) {
            addCriterion("is_quit >", value, "isQuit");
            return (Criteria) this;
        }

        public Criteria andIsQuitGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_quit >=", value, "isQuit");
            return (Criteria) this;
        }

        public Criteria andIsQuitLessThan(Boolean value) {
            addCriterion("is_quit <", value, "isQuit");
            return (Criteria) this;
        }

        public Criteria andIsQuitLessThanOrEqualTo(Boolean value) {
            addCriterion("is_quit <=", value, "isQuit");
            return (Criteria) this;
        }

        public Criteria andIsQuitIn(List<Boolean> values) {
            addCriterion("is_quit in", values, "isQuit");
            return (Criteria) this;
        }

        public Criteria andIsQuitNotIn(List<Boolean> values) {
            addCriterion("is_quit not in", values, "isQuit");
            return (Criteria) this;
        }

        public Criteria andIsQuitBetween(Boolean value1, Boolean value2) {
            addCriterion("is_quit between", value1, value2, "isQuit");
            return (Criteria) this;
        }

        public Criteria andIsQuitNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_quit not between", value1, value2, "isQuit");
            return (Criteria) this;
        }

        public Criteria andQuitDateIsNull() {
            addCriterion("quit_date is null");
            return (Criteria) this;
        }

        public Criteria andQuitDateIsNotNull() {
            addCriterion("quit_date is not null");
            return (Criteria) this;
        }

        public Criteria andQuitDateEqualTo(Date value) {
            addCriterionForJDBCDate("quit_date =", value, "quitDate");
            return (Criteria) this;
        }

        public Criteria andQuitDateNotEqualTo(Date value) {
            addCriterionForJDBCDate("quit_date <>", value, "quitDate");
            return (Criteria) this;
        }

        public Criteria andQuitDateGreaterThan(Date value) {
            addCriterionForJDBCDate("quit_date >", value, "quitDate");
            return (Criteria) this;
        }

        public Criteria andQuitDateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("quit_date >=", value, "quitDate");
            return (Criteria) this;
        }

        public Criteria andQuitDateLessThan(Date value) {
            addCriterionForJDBCDate("quit_date <", value, "quitDate");
            return (Criteria) this;
        }

        public Criteria andQuitDateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("quit_date <=", value, "quitDate");
            return (Criteria) this;
        }

        public Criteria andQuitDateIn(List<Date> values) {
            addCriterionForJDBCDate("quit_date in", values, "quitDate");
            return (Criteria) this;
        }

        public Criteria andQuitDateNotIn(List<Date> values) {
            addCriterionForJDBCDate("quit_date not in", values, "quitDate");
            return (Criteria) this;
        }

        public Criteria andQuitDateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("quit_date between", value1, value2, "quitDate");
            return (Criteria) this;
        }

        public Criteria andQuitDateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("quit_date not between", value1, value2, "quitDate");
            return (Criteria) this;
        }

        public Criteria andQuitFilePathIsNull() {
            addCriterion("quit_file_path is null");
            return (Criteria) this;
        }

        public Criteria andQuitFilePathIsNotNull() {
            addCriterion("quit_file_path is not null");
            return (Criteria) this;
        }

        public Criteria andQuitFilePathEqualTo(String value) {
            addCriterion("quit_file_path =", value, "quitFilePath");
            return (Criteria) this;
        }

        public Criteria andQuitFilePathNotEqualTo(String value) {
            addCriterion("quit_file_path <>", value, "quitFilePath");
            return (Criteria) this;
        }

        public Criteria andQuitFilePathGreaterThan(String value) {
            addCriterion("quit_file_path >", value, "quitFilePath");
            return (Criteria) this;
        }

        public Criteria andQuitFilePathGreaterThanOrEqualTo(String value) {
            addCriterion("quit_file_path >=", value, "quitFilePath");
            return (Criteria) this;
        }

        public Criteria andQuitFilePathLessThan(String value) {
            addCriterion("quit_file_path <", value, "quitFilePath");
            return (Criteria) this;
        }

        public Criteria andQuitFilePathLessThanOrEqualTo(String value) {
            addCriterion("quit_file_path <=", value, "quitFilePath");
            return (Criteria) this;
        }

        public Criteria andQuitFilePathLike(String value) {
            addCriterion("quit_file_path like", value, "quitFilePath");
            return (Criteria) this;
        }

        public Criteria andQuitFilePathNotLike(String value) {
            addCriterion("quit_file_path not like", value, "quitFilePath");
            return (Criteria) this;
        }

        public Criteria andQuitFilePathIn(List<String> values) {
            addCriterion("quit_file_path in", values, "quitFilePath");
            return (Criteria) this;
        }

        public Criteria andQuitFilePathNotIn(List<String> values) {
            addCriterion("quit_file_path not in", values, "quitFilePath");
            return (Criteria) this;
        }

        public Criteria andQuitFilePathBetween(String value1, String value2) {
            addCriterion("quit_file_path between", value1, value2, "quitFilePath");
            return (Criteria) this;
        }

        public Criteria andQuitFilePathNotBetween(String value1, String value2) {
            addCriterion("quit_file_path not between", value1, value2, "quitFilePath");
            return (Criteria) this;
        }

        public Criteria andQuitReasonIsNull() {
            addCriterion("quit_reason is null");
            return (Criteria) this;
        }

        public Criteria andQuitReasonIsNotNull() {
            addCriterion("quit_reason is not null");
            return (Criteria) this;
        }

        public Criteria andQuitReasonEqualTo(String value) {
            addCriterion("quit_reason =", value, "quitReason");
            return (Criteria) this;
        }

        public Criteria andQuitReasonNotEqualTo(String value) {
            addCriterion("quit_reason <>", value, "quitReason");
            return (Criteria) this;
        }

        public Criteria andQuitReasonGreaterThan(String value) {
            addCriterion("quit_reason >", value, "quitReason");
            return (Criteria) this;
        }

        public Criteria andQuitReasonGreaterThanOrEqualTo(String value) {
            addCriterion("quit_reason >=", value, "quitReason");
            return (Criteria) this;
        }

        public Criteria andQuitReasonLessThan(String value) {
            addCriterion("quit_reason <", value, "quitReason");
            return (Criteria) this;
        }

        public Criteria andQuitReasonLessThanOrEqualTo(String value) {
            addCriterion("quit_reason <=", value, "quitReason");
            return (Criteria) this;
        }

        public Criteria andQuitReasonLike(String value) {
            addCriterion("quit_reason like", value, "quitReason");
            return (Criteria) this;
        }

        public Criteria andQuitReasonNotLike(String value) {
            addCriterion("quit_reason not like", value, "quitReason");
            return (Criteria) this;
        }

        public Criteria andQuitReasonIn(List<String> values) {
            addCriterion("quit_reason in", values, "quitReason");
            return (Criteria) this;
        }

        public Criteria andQuitReasonNotIn(List<String> values) {
            addCriterion("quit_reason not in", values, "quitReason");
            return (Criteria) this;
        }

        public Criteria andQuitReasonBetween(String value1, String value2) {
            addCriterion("quit_reason between", value1, value2, "quitReason");
            return (Criteria) this;
        }

        public Criteria andQuitReasonNotBetween(String value1, String value2) {
            addCriterion("quit_reason not between", value1, value2, "quitReason");
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

        public Criteria andIsCommmitteeMemberIsNull() {
            addCriterion("is_commmittee_member is null");
            return (Criteria) this;
        }

        public Criteria andIsCommmitteeMemberIsNotNull() {
            addCriterion("is_commmittee_member is not null");
            return (Criteria) this;
        }

        public Criteria andIsCommmitteeMemberEqualTo(Boolean value) {
            addCriterion("is_commmittee_member =", value, "isCommmitteeMember");
            return (Criteria) this;
        }

        public Criteria andIsCommmitteeMemberNotEqualTo(Boolean value) {
            addCriterion("is_commmittee_member <>", value, "isCommmitteeMember");
            return (Criteria) this;
        }

        public Criteria andIsCommmitteeMemberGreaterThan(Boolean value) {
            addCriterion("is_commmittee_member >", value, "isCommmitteeMember");
            return (Criteria) this;
        }

        public Criteria andIsCommmitteeMemberGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_commmittee_member >=", value, "isCommmitteeMember");
            return (Criteria) this;
        }

        public Criteria andIsCommmitteeMemberLessThan(Boolean value) {
            addCriterion("is_commmittee_member <", value, "isCommmitteeMember");
            return (Criteria) this;
        }

        public Criteria andIsCommmitteeMemberLessThanOrEqualTo(Boolean value) {
            addCriterion("is_commmittee_member <=", value, "isCommmitteeMember");
            return (Criteria) this;
        }

        public Criteria andIsCommmitteeMemberIn(List<Boolean> values) {
            addCriterion("is_commmittee_member in", values, "isCommmitteeMember");
            return (Criteria) this;
        }

        public Criteria andIsCommmitteeMemberNotIn(List<Boolean> values) {
            addCriterion("is_commmittee_member not in", values, "isCommmitteeMember");
            return (Criteria) this;
        }

        public Criteria andIsCommmitteeMemberBetween(Boolean value1, Boolean value2) {
            addCriterion("is_commmittee_member between", value1, value2, "isCommmitteeMember");
            return (Criteria) this;
        }

        public Criteria andIsCommmitteeMemberNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_commmittee_member not between", value1, value2, "isCommmitteeMember");
            return (Criteria) this;
        }

        public Criteria andPcsNameIsNull() {
            addCriterion("pcs_name is null");
            return (Criteria) this;
        }

        public Criteria andPcsNameIsNotNull() {
            addCriterion("pcs_name is not null");
            return (Criteria) this;
        }

        public Criteria andPcsNameEqualTo(String value) {
            addCriterion("pcs_name =", value, "pcsName");
            return (Criteria) this;
        }

        public Criteria andPcsNameNotEqualTo(String value) {
            addCriterion("pcs_name <>", value, "pcsName");
            return (Criteria) this;
        }

        public Criteria andPcsNameGreaterThan(String value) {
            addCriterion("pcs_name >", value, "pcsName");
            return (Criteria) this;
        }

        public Criteria andPcsNameGreaterThanOrEqualTo(String value) {
            addCriterion("pcs_name >=", value, "pcsName");
            return (Criteria) this;
        }

        public Criteria andPcsNameLessThan(String value) {
            addCriterion("pcs_name <", value, "pcsName");
            return (Criteria) this;
        }

        public Criteria andPcsNameLessThanOrEqualTo(String value) {
            addCriterion("pcs_name <=", value, "pcsName");
            return (Criteria) this;
        }

        public Criteria andPcsNameLike(String value) {
            addCriterion("pcs_name like", value, "pcsName");
            return (Criteria) this;
        }

        public Criteria andPcsNameNotLike(String value) {
            addCriterion("pcs_name not like", value, "pcsName");
            return (Criteria) this;
        }

        public Criteria andPcsNameIn(List<String> values) {
            addCriterion("pcs_name in", values, "pcsName");
            return (Criteria) this;
        }

        public Criteria andPcsNameNotIn(List<String> values) {
            addCriterion("pcs_name not in", values, "pcsName");
            return (Criteria) this;
        }

        public Criteria andPcsNameBetween(String value1, String value2) {
            addCriterion("pcs_name between", value1, value2, "pcsName");
            return (Criteria) this;
        }

        public Criteria andPcsNameNotBetween(String value1, String value2) {
            addCriterion("pcs_name not between", value1, value2, "pcsName");
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