package domain.oa;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class OaTaskUserViewExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public OaTaskUserViewExample() {
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

        public Criteria andTaskIdIsNull() {
            addCriterion("task_id is null");
            return (Criteria) this;
        }

        public Criteria andTaskIdIsNotNull() {
            addCriterion("task_id is not null");
            return (Criteria) this;
        }

        public Criteria andTaskIdEqualTo(Integer value) {
            addCriterion("task_id =", value, "taskId");
            return (Criteria) this;
        }

        public Criteria andTaskIdNotEqualTo(Integer value) {
            addCriterion("task_id <>", value, "taskId");
            return (Criteria) this;
        }

        public Criteria andTaskIdGreaterThan(Integer value) {
            addCriterion("task_id >", value, "taskId");
            return (Criteria) this;
        }

        public Criteria andTaskIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("task_id >=", value, "taskId");
            return (Criteria) this;
        }

        public Criteria andTaskIdLessThan(Integer value) {
            addCriterion("task_id <", value, "taskId");
            return (Criteria) this;
        }

        public Criteria andTaskIdLessThanOrEqualTo(Integer value) {
            addCriterion("task_id <=", value, "taskId");
            return (Criteria) this;
        }

        public Criteria andTaskIdIn(List<Integer> values) {
            addCriterion("task_id in", values, "taskId");
            return (Criteria) this;
        }

        public Criteria andTaskIdNotIn(List<Integer> values) {
            addCriterion("task_id not in", values, "taskId");
            return (Criteria) this;
        }

        public Criteria andTaskIdBetween(Integer value1, Integer value2) {
            addCriterion("task_id between", value1, value2, "taskId");
            return (Criteria) this;
        }

        public Criteria andTaskIdNotBetween(Integer value1, Integer value2) {
            addCriterion("task_id not between", value1, value2, "taskId");
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

        // 任务的任务对象或者指定的负责人
        public Criteria isTaskUser(Integer value) {
            addCriterion("(user_id =" + value + " or assign_user_id="+value + ")");
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

        public Criteria andAssignUserIdIsNull() {
            addCriterion("assign_user_id is null");
            return (Criteria) this;
        }

        public Criteria andAssignUserIdIsNotNull() {
            addCriterion("assign_user_id is not null");
            return (Criteria) this;
        }

        public Criteria andAssignUserIdEqualTo(Integer value) {
            addCriterion("assign_user_id =", value, "assignUserId");
            return (Criteria) this;
        }

        public Criteria andAssignUserIdNotEqualTo(Integer value) {
            addCriterion("assign_user_id <>", value, "assignUserId");
            return (Criteria) this;
        }

        public Criteria andAssignUserIdGreaterThan(Integer value) {
            addCriterion("assign_user_id >", value, "assignUserId");
            return (Criteria) this;
        }

        public Criteria andAssignUserIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("assign_user_id >=", value, "assignUserId");
            return (Criteria) this;
        }

        public Criteria andAssignUserIdLessThan(Integer value) {
            addCriterion("assign_user_id <", value, "assignUserId");
            return (Criteria) this;
        }

        public Criteria andAssignUserIdLessThanOrEqualTo(Integer value) {
            addCriterion("assign_user_id <=", value, "assignUserId");
            return (Criteria) this;
        }

        public Criteria andAssignUserIdIn(List<Integer> values) {
            addCriterion("assign_user_id in", values, "assignUserId");
            return (Criteria) this;
        }

        public Criteria andAssignUserIdNotIn(List<Integer> values) {
            addCriterion("assign_user_id not in", values, "assignUserId");
            return (Criteria) this;
        }

        public Criteria andAssignUserIdBetween(Integer value1, Integer value2) {
            addCriterion("assign_user_id between", value1, value2, "assignUserId");
            return (Criteria) this;
        }

        public Criteria andAssignUserIdNotBetween(Integer value1, Integer value2) {
            addCriterion("assign_user_id not between", value1, value2, "assignUserId");
            return (Criteria) this;
        }

        public Criteria andAssignUserMobileIsNull() {
            addCriterion("assign_user_mobile is null");
            return (Criteria) this;
        }

        public Criteria andAssignUserMobileIsNotNull() {
            addCriterion("assign_user_mobile is not null");
            return (Criteria) this;
        }

        public Criteria andAssignUserMobileEqualTo(String value) {
            addCriterion("assign_user_mobile =", value, "assignUserMobile");
            return (Criteria) this;
        }

        public Criteria andAssignUserMobileNotEqualTo(String value) {
            addCriterion("assign_user_mobile <>", value, "assignUserMobile");
            return (Criteria) this;
        }

        public Criteria andAssignUserMobileGreaterThan(String value) {
            addCriterion("assign_user_mobile >", value, "assignUserMobile");
            return (Criteria) this;
        }

        public Criteria andAssignUserMobileGreaterThanOrEqualTo(String value) {
            addCriterion("assign_user_mobile >=", value, "assignUserMobile");
            return (Criteria) this;
        }

        public Criteria andAssignUserMobileLessThan(String value) {
            addCriterion("assign_user_mobile <", value, "assignUserMobile");
            return (Criteria) this;
        }

        public Criteria andAssignUserMobileLessThanOrEqualTo(String value) {
            addCriterion("assign_user_mobile <=", value, "assignUserMobile");
            return (Criteria) this;
        }

        public Criteria andAssignUserMobileLike(String value) {
            addCriterion("assign_user_mobile like", value, "assignUserMobile");
            return (Criteria) this;
        }

        public Criteria andAssignUserMobileNotLike(String value) {
            addCriterion("assign_user_mobile not like", value, "assignUserMobile");
            return (Criteria) this;
        }

        public Criteria andAssignUserMobileIn(List<String> values) {
            addCriterion("assign_user_mobile in", values, "assignUserMobile");
            return (Criteria) this;
        }

        public Criteria andAssignUserMobileNotIn(List<String> values) {
            addCriterion("assign_user_mobile not in", values, "assignUserMobile");
            return (Criteria) this;
        }

        public Criteria andAssignUserMobileBetween(String value1, String value2) {
            addCriterion("assign_user_mobile between", value1, value2, "assignUserMobile");
            return (Criteria) this;
        }

        public Criteria andAssignUserMobileNotBetween(String value1, String value2) {
            addCriterion("assign_user_mobile not between", value1, value2, "assignUserMobile");
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

        public Criteria andHasReportIsNull() {
            addCriterion("has_report is null");
            return (Criteria) this;
        }

        public Criteria andHasReportIsNotNull() {
            addCriterion("has_report is not null");
            return (Criteria) this;
        }

        public Criteria andHasReportEqualTo(Boolean value) {
            addCriterion("has_report =", value, "hasReport");
            return (Criteria) this;
        }

        public Criteria andHasReportNotEqualTo(Boolean value) {
            addCriterion("has_report <>", value, "hasReport");
            return (Criteria) this;
        }

        public Criteria andHasReportGreaterThan(Boolean value) {
            addCriterion("has_report >", value, "hasReport");
            return (Criteria) this;
        }

        public Criteria andHasReportGreaterThanOrEqualTo(Boolean value) {
            addCriterion("has_report >=", value, "hasReport");
            return (Criteria) this;
        }

        public Criteria andHasReportLessThan(Boolean value) {
            addCriterion("has_report <", value, "hasReport");
            return (Criteria) this;
        }

        public Criteria andHasReportLessThanOrEqualTo(Boolean value) {
            addCriterion("has_report <=", value, "hasReport");
            return (Criteria) this;
        }

        public Criteria andHasReportIn(List<Boolean> values) {
            addCriterion("has_report in", values, "hasReport");
            return (Criteria) this;
        }

        public Criteria andHasReportNotIn(List<Boolean> values) {
            addCriterion("has_report not in", values, "hasReport");
            return (Criteria) this;
        }

        public Criteria andHasReportBetween(Boolean value1, Boolean value2) {
            addCriterion("has_report between", value1, value2, "hasReport");
            return (Criteria) this;
        }

        public Criteria andHasReportNotBetween(Boolean value1, Boolean value2) {
            addCriterion("has_report not between", value1, value2, "hasReport");
            return (Criteria) this;
        }

        public Criteria andReportUserIdIsNull() {
            addCriterion("report_user_id is null");
            return (Criteria) this;
        }

        public Criteria andReportUserIdIsNotNull() {
            addCriterion("report_user_id is not null");
            return (Criteria) this;
        }

        public Criteria andReportUserIdEqualTo(Integer value) {
            addCriterion("report_user_id =", value, "reportUserId");
            return (Criteria) this;
        }

        public Criteria andReportUserIdNotEqualTo(Integer value) {
            addCriterion("report_user_id <>", value, "reportUserId");
            return (Criteria) this;
        }

        public Criteria andReportUserIdGreaterThan(Integer value) {
            addCriterion("report_user_id >", value, "reportUserId");
            return (Criteria) this;
        }

        public Criteria andReportUserIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("report_user_id >=", value, "reportUserId");
            return (Criteria) this;
        }

        public Criteria andReportUserIdLessThan(Integer value) {
            addCriterion("report_user_id <", value, "reportUserId");
            return (Criteria) this;
        }

        public Criteria andReportUserIdLessThanOrEqualTo(Integer value) {
            addCriterion("report_user_id <=", value, "reportUserId");
            return (Criteria) this;
        }

        public Criteria andReportUserIdIn(List<Integer> values) {
            addCriterion("report_user_id in", values, "reportUserId");
            return (Criteria) this;
        }

        public Criteria andReportUserIdNotIn(List<Integer> values) {
            addCriterion("report_user_id not in", values, "reportUserId");
            return (Criteria) this;
        }

        public Criteria andReportUserIdBetween(Integer value1, Integer value2) {
            addCriterion("report_user_id between", value1, value2, "reportUserId");
            return (Criteria) this;
        }

        public Criteria andReportUserIdNotBetween(Integer value1, Integer value2) {
            addCriterion("report_user_id not between", value1, value2, "reportUserId");
            return (Criteria) this;
        }

        public Criteria andReportTimeIsNull() {
            addCriterion("report_time is null");
            return (Criteria) this;
        }

        public Criteria andReportTimeIsNotNull() {
            addCriterion("report_time is not null");
            return (Criteria) this;
        }

        public Criteria andReportTimeEqualTo(Date value) {
            addCriterion("report_time =", value, "reportTime");
            return (Criteria) this;
        }

        public Criteria andReportTimeNotEqualTo(Date value) {
            addCriterion("report_time <>", value, "reportTime");
            return (Criteria) this;
        }

        public Criteria andReportTimeGreaterThan(Date value) {
            addCriterion("report_time >", value, "reportTime");
            return (Criteria) this;
        }

        public Criteria andReportTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("report_time >=", value, "reportTime");
            return (Criteria) this;
        }

        public Criteria andReportTimeLessThan(Date value) {
            addCriterion("report_time <", value, "reportTime");
            return (Criteria) this;
        }

        public Criteria andReportTimeLessThanOrEqualTo(Date value) {
            addCriterion("report_time <=", value, "reportTime");
            return (Criteria) this;
        }

        public Criteria andReportTimeIn(List<Date> values) {
            addCriterion("report_time in", values, "reportTime");
            return (Criteria) this;
        }

        public Criteria andReportTimeNotIn(List<Date> values) {
            addCriterion("report_time not in", values, "reportTime");
            return (Criteria) this;
        }

        public Criteria andReportTimeBetween(Date value1, Date value2) {
            addCriterion("report_time between", value1, value2, "reportTime");
            return (Criteria) this;
        }

        public Criteria andReportTimeNotBetween(Date value1, Date value2) {
            addCriterion("report_time not between", value1, value2, "reportTime");
            return (Criteria) this;
        }

        public Criteria andIsBackIsNull() {
            addCriterion("is_back is null");
            return (Criteria) this;
        }

        public Criteria andIsBackIsNotNull() {
            addCriterion("is_back is not null");
            return (Criteria) this;
        }

        public Criteria andIsBackEqualTo(Boolean value) {
            addCriterion("is_back =", value, "isBack");
            return (Criteria) this;
        }

        public Criteria andIsBackNotEqualTo(Boolean value) {
            addCriterion("is_back <>", value, "isBack");
            return (Criteria) this;
        }

        public Criteria andIsBackGreaterThan(Boolean value) {
            addCriterion("is_back >", value, "isBack");
            return (Criteria) this;
        }

        public Criteria andIsBackGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_back >=", value, "isBack");
            return (Criteria) this;
        }

        public Criteria andIsBackLessThan(Boolean value) {
            addCriterion("is_back <", value, "isBack");
            return (Criteria) this;
        }

        public Criteria andIsBackLessThanOrEqualTo(Boolean value) {
            addCriterion("is_back <=", value, "isBack");
            return (Criteria) this;
        }

        public Criteria andIsBackIn(List<Boolean> values) {
            addCriterion("is_back in", values, "isBack");
            return (Criteria) this;
        }

        public Criteria andIsBackNotIn(List<Boolean> values) {
            addCriterion("is_back not in", values, "isBack");
            return (Criteria) this;
        }

        public Criteria andIsBackBetween(Boolean value1, Boolean value2) {
            addCriterion("is_back between", value1, value2, "isBack");
            return (Criteria) this;
        }

        public Criteria andIsBackNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_back not between", value1, value2, "isBack");
            return (Criteria) this;
        }

        public Criteria andIsDeleteIsNull() {
            addCriterion("is_delete is null");
            return (Criteria) this;
        }

        public Criteria andIsDeleteIsNotNull() {
            addCriterion("is_delete is not null");
            return (Criteria) this;
        }

        public Criteria andIsDeleteEqualTo(Boolean value) {
            addCriterion("is_delete =", value, "isDelete");
            return (Criteria) this;
        }

        public Criteria andIsDeleteNotEqualTo(Boolean value) {
            addCriterion("is_delete <>", value, "isDelete");
            return (Criteria) this;
        }

        public Criteria andIsDeleteGreaterThan(Boolean value) {
            addCriterion("is_delete >", value, "isDelete");
            return (Criteria) this;
        }

        public Criteria andIsDeleteGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_delete >=", value, "isDelete");
            return (Criteria) this;
        }

        public Criteria andIsDeleteLessThan(Boolean value) {
            addCriterion("is_delete <", value, "isDelete");
            return (Criteria) this;
        }

        public Criteria andIsDeleteLessThanOrEqualTo(Boolean value) {
            addCriterion("is_delete <=", value, "isDelete");
            return (Criteria) this;
        }

        public Criteria andIsDeleteIn(List<Boolean> values) {
            addCriterion("is_delete in", values, "isDelete");
            return (Criteria) this;
        }

        public Criteria andIsDeleteNotIn(List<Boolean> values) {
            addCriterion("is_delete not in", values, "isDelete");
            return (Criteria) this;
        }

        public Criteria andIsDeleteBetween(Boolean value1, Boolean value2) {
            addCriterion("is_delete between", value1, value2, "isDelete");
            return (Criteria) this;
        }

        public Criteria andIsDeleteNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_delete not between", value1, value2, "isDelete");
            return (Criteria) this;
        }

        public Criteria andCheckRemarkIsNull() {
            addCriterion("check_remark is null");
            return (Criteria) this;
        }

        public Criteria andCheckRemarkIsNotNull() {
            addCriterion("check_remark is not null");
            return (Criteria) this;
        }

        public Criteria andCheckRemarkEqualTo(String value) {
            addCriterion("check_remark =", value, "checkRemark");
            return (Criteria) this;
        }

        public Criteria andCheckRemarkNotEqualTo(String value) {
            addCriterion("check_remark <>", value, "checkRemark");
            return (Criteria) this;
        }

        public Criteria andCheckRemarkGreaterThan(String value) {
            addCriterion("check_remark >", value, "checkRemark");
            return (Criteria) this;
        }

        public Criteria andCheckRemarkGreaterThanOrEqualTo(String value) {
            addCriterion("check_remark >=", value, "checkRemark");
            return (Criteria) this;
        }

        public Criteria andCheckRemarkLessThan(String value) {
            addCriterion("check_remark <", value, "checkRemark");
            return (Criteria) this;
        }

        public Criteria andCheckRemarkLessThanOrEqualTo(String value) {
            addCriterion("check_remark <=", value, "checkRemark");
            return (Criteria) this;
        }

        public Criteria andCheckRemarkLike(String value) {
            addCriterion("check_remark like", value, "checkRemark");
            return (Criteria) this;
        }

        public Criteria andCheckRemarkNotLike(String value) {
            addCriterion("check_remark not like", value, "checkRemark");
            return (Criteria) this;
        }

        public Criteria andCheckRemarkIn(List<String> values) {
            addCriterion("check_remark in", values, "checkRemark");
            return (Criteria) this;
        }

        public Criteria andCheckRemarkNotIn(List<String> values) {
            addCriterion("check_remark not in", values, "checkRemark");
            return (Criteria) this;
        }

        public Criteria andCheckRemarkBetween(String value1, String value2) {
            addCriterion("check_remark between", value1, value2, "checkRemark");
            return (Criteria) this;
        }

        public Criteria andCheckRemarkNotBetween(String value1, String value2) {
            addCriterion("check_remark not between", value1, value2, "checkRemark");
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

        public Criteria andTaskNameIsNull() {
            addCriterion("task_name is null");
            return (Criteria) this;
        }

        public Criteria andTaskNameIsNotNull() {
            addCriterion("task_name is not null");
            return (Criteria) this;
        }

        public Criteria andTaskNameEqualTo(String value) {
            addCriterion("task_name =", value, "taskName");
            return (Criteria) this;
        }

        public Criteria andTaskNameNotEqualTo(String value) {
            addCriterion("task_name <>", value, "taskName");
            return (Criteria) this;
        }

        public Criteria andTaskNameGreaterThan(String value) {
            addCriterion("task_name >", value, "taskName");
            return (Criteria) this;
        }

        public Criteria andTaskNameGreaterThanOrEqualTo(String value) {
            addCriterion("task_name >=", value, "taskName");
            return (Criteria) this;
        }

        public Criteria andTaskNameLessThan(String value) {
            addCriterion("task_name <", value, "taskName");
            return (Criteria) this;
        }

        public Criteria andTaskNameLessThanOrEqualTo(String value) {
            addCriterion("task_name <=", value, "taskName");
            return (Criteria) this;
        }

        public Criteria andTaskNameLike(String value) {
            addCriterion("task_name like", value, "taskName");
            return (Criteria) this;
        }

        public Criteria andTaskNameNotLike(String value) {
            addCriterion("task_name not like", value, "taskName");
            return (Criteria) this;
        }

        public Criteria andTaskNameIn(List<String> values) {
            addCriterion("task_name in", values, "taskName");
            return (Criteria) this;
        }

        public Criteria andTaskNameNotIn(List<String> values) {
            addCriterion("task_name not in", values, "taskName");
            return (Criteria) this;
        }

        public Criteria andTaskNameBetween(String value1, String value2) {
            addCriterion("task_name between", value1, value2, "taskName");
            return (Criteria) this;
        }

        public Criteria andTaskNameNotBetween(String value1, String value2) {
            addCriterion("task_name not between", value1, value2, "taskName");
            return (Criteria) this;
        }

        public Criteria andTaskDeadlineIsNull() {
            addCriterion("task_deadline is null");
            return (Criteria) this;
        }

        public Criteria andTaskDeadlineIsNotNull() {
            addCriterion("task_deadline is not null");
            return (Criteria) this;
        }

        public Criteria andTaskDeadlineEqualTo(Date value) {
            addCriterion("task_deadline =", value, "taskDeadline");
            return (Criteria) this;
        }

        public Criteria andTaskDeadlineNotEqualTo(Date value) {
            addCriterion("task_deadline <>", value, "taskDeadline");
            return (Criteria) this;
        }

        public Criteria andTaskDeadlineGreaterThan(Date value) {
            addCriterion("task_deadline >", value, "taskDeadline");
            return (Criteria) this;
        }

        public Criteria andTaskDeadlineGreaterThanOrEqualTo(Date value) {
            addCriterion("task_deadline >=", value, "taskDeadline");
            return (Criteria) this;
        }

        public Criteria andTaskDeadlineLessThan(Date value) {
            addCriterion("task_deadline <", value, "taskDeadline");
            return (Criteria) this;
        }

        public Criteria andTaskDeadlineLessThanOrEqualTo(Date value) {
            addCriterion("task_deadline <=", value, "taskDeadline");
            return (Criteria) this;
        }

        public Criteria andTaskDeadlineIn(List<Date> values) {
            addCriterion("task_deadline in", values, "taskDeadline");
            return (Criteria) this;
        }

        public Criteria andTaskDeadlineNotIn(List<Date> values) {
            addCriterion("task_deadline not in", values, "taskDeadline");
            return (Criteria) this;
        }

        public Criteria andTaskDeadlineBetween(Date value1, Date value2) {
            addCriterion("task_deadline between", value1, value2, "taskDeadline");
            return (Criteria) this;
        }

        public Criteria andTaskDeadlineNotBetween(Date value1, Date value2) {
            addCriterion("task_deadline not between", value1, value2, "taskDeadline");
            return (Criteria) this;
        }

        public Criteria andTaskContactIsNull() {
            addCriterion("task_contact is null");
            return (Criteria) this;
        }

        public Criteria andTaskContactIsNotNull() {
            addCriterion("task_contact is not null");
            return (Criteria) this;
        }

        public Criteria andTaskContactEqualTo(String value) {
            addCriterion("task_contact =", value, "taskContact");
            return (Criteria) this;
        }

        public Criteria andTaskContactNotEqualTo(String value) {
            addCriterion("task_contact <>", value, "taskContact");
            return (Criteria) this;
        }

        public Criteria andTaskContactGreaterThan(String value) {
            addCriterion("task_contact >", value, "taskContact");
            return (Criteria) this;
        }

        public Criteria andTaskContactGreaterThanOrEqualTo(String value) {
            addCriterion("task_contact >=", value, "taskContact");
            return (Criteria) this;
        }

        public Criteria andTaskContactLessThan(String value) {
            addCriterion("task_contact <", value, "taskContact");
            return (Criteria) this;
        }

        public Criteria andTaskContactLessThanOrEqualTo(String value) {
            addCriterion("task_contact <=", value, "taskContact");
            return (Criteria) this;
        }

        public Criteria andTaskContactLike(String value) {
            addCriterion("task_contact like", value, "taskContact");
            return (Criteria) this;
        }

        public Criteria andTaskContactNotLike(String value) {
            addCriterion("task_contact not like", value, "taskContact");
            return (Criteria) this;
        }

        public Criteria andTaskContactIn(List<String> values) {
            addCriterion("task_contact in", values, "taskContact");
            return (Criteria) this;
        }

        public Criteria andTaskContactNotIn(List<String> values) {
            addCriterion("task_contact not in", values, "taskContact");
            return (Criteria) this;
        }

        public Criteria andTaskContactBetween(String value1, String value2) {
            addCriterion("task_contact between", value1, value2, "taskContact");
            return (Criteria) this;
        }

        public Criteria andTaskContactNotBetween(String value1, String value2) {
            addCriterion("task_contact not between", value1, value2, "taskContact");
            return (Criteria) this;
        }

        public Criteria andTaskIsDeleteIsNull() {
            addCriterion("task_is_delete is null");
            return (Criteria) this;
        }

        public Criteria andTaskIsDeleteIsNotNull() {
            addCriterion("task_is_delete is not null");
            return (Criteria) this;
        }

        public Criteria andTaskIsDeleteEqualTo(Boolean value) {
            addCriterion("task_is_delete =", value, "taskIsDelete");
            return (Criteria) this;
        }

        public Criteria andTaskIsDeleteNotEqualTo(Boolean value) {
            addCriterion("task_is_delete <>", value, "taskIsDelete");
            return (Criteria) this;
        }

        public Criteria andTaskIsDeleteGreaterThan(Boolean value) {
            addCriterion("task_is_delete >", value, "taskIsDelete");
            return (Criteria) this;
        }

        public Criteria andTaskIsDeleteGreaterThanOrEqualTo(Boolean value) {
            addCriterion("task_is_delete >=", value, "taskIsDelete");
            return (Criteria) this;
        }

        public Criteria andTaskIsDeleteLessThan(Boolean value) {
            addCriterion("task_is_delete <", value, "taskIsDelete");
            return (Criteria) this;
        }

        public Criteria andTaskIsDeleteLessThanOrEqualTo(Boolean value) {
            addCriterion("task_is_delete <=", value, "taskIsDelete");
            return (Criteria) this;
        }

        public Criteria andTaskIsDeleteIn(List<Boolean> values) {
            addCriterion("task_is_delete in", values, "taskIsDelete");
            return (Criteria) this;
        }

        public Criteria andTaskIsDeleteNotIn(List<Boolean> values) {
            addCriterion("task_is_delete not in", values, "taskIsDelete");
            return (Criteria) this;
        }

        public Criteria andTaskIsDeleteBetween(Boolean value1, Boolean value2) {
            addCriterion("task_is_delete between", value1, value2, "taskIsDelete");
            return (Criteria) this;
        }

        public Criteria andTaskIsDeleteNotBetween(Boolean value1, Boolean value2) {
            addCriterion("task_is_delete not between", value1, value2, "taskIsDelete");
            return (Criteria) this;
        }

        public Criteria andTaskIsPublishIsNull() {
            addCriterion("task_is_publish is null");
            return (Criteria) this;
        }

        public Criteria andTaskIsPublishIsNotNull() {
            addCriterion("task_is_publish is not null");
            return (Criteria) this;
        }

        public Criteria andTaskIsPublishEqualTo(Boolean value) {
            addCriterion("task_is_publish =", value, "taskIsPublish");
            return (Criteria) this;
        }

        public Criteria andTaskIsPublishNotEqualTo(Boolean value) {
            addCriterion("task_is_publish <>", value, "taskIsPublish");
            return (Criteria) this;
        }

        public Criteria andTaskIsPublishGreaterThan(Boolean value) {
            addCriterion("task_is_publish >", value, "taskIsPublish");
            return (Criteria) this;
        }

        public Criteria andTaskIsPublishGreaterThanOrEqualTo(Boolean value) {
            addCriterion("task_is_publish >=", value, "taskIsPublish");
            return (Criteria) this;
        }

        public Criteria andTaskIsPublishLessThan(Boolean value) {
            addCriterion("task_is_publish <", value, "taskIsPublish");
            return (Criteria) this;
        }

        public Criteria andTaskIsPublishLessThanOrEqualTo(Boolean value) {
            addCriterion("task_is_publish <=", value, "taskIsPublish");
            return (Criteria) this;
        }

        public Criteria andTaskIsPublishIn(List<Boolean> values) {
            addCriterion("task_is_publish in", values, "taskIsPublish");
            return (Criteria) this;
        }

        public Criteria andTaskIsPublishNotIn(List<Boolean> values) {
            addCriterion("task_is_publish not in", values, "taskIsPublish");
            return (Criteria) this;
        }

        public Criteria andTaskIsPublishBetween(Boolean value1, Boolean value2) {
            addCriterion("task_is_publish between", value1, value2, "taskIsPublish");
            return (Criteria) this;
        }

        public Criteria andTaskIsPublishNotBetween(Boolean value1, Boolean value2) {
            addCriterion("task_is_publish not between", value1, value2, "taskIsPublish");
            return (Criteria) this;
        }

        public Criteria andTaskStatusIsNull() {
            addCriterion("task_status is null");
            return (Criteria) this;
        }

        public Criteria andTaskStatusIsNotNull() {
            addCriterion("task_status is not null");
            return (Criteria) this;
        }

        public Criteria andTaskStatusEqualTo(Byte value) {
            addCriterion("task_status =", value, "taskStatus");
            return (Criteria) this;
        }

        public Criteria andTaskStatusNotEqualTo(Byte value) {
            addCriterion("task_status <>", value, "taskStatus");
            return (Criteria) this;
        }

        public Criteria andTaskStatusGreaterThan(Byte value) {
            addCriterion("task_status >", value, "taskStatus");
            return (Criteria) this;
        }

        public Criteria andTaskStatusGreaterThanOrEqualTo(Byte value) {
            addCriterion("task_status >=", value, "taskStatus");
            return (Criteria) this;
        }

        public Criteria andTaskStatusLessThan(Byte value) {
            addCriterion("task_status <", value, "taskStatus");
            return (Criteria) this;
        }

        public Criteria andTaskStatusLessThanOrEqualTo(Byte value) {
            addCriterion("task_status <=", value, "taskStatus");
            return (Criteria) this;
        }

        public Criteria andTaskStatusIn(List<Byte> values) {
            addCriterion("task_status in", values, "taskStatus");
            return (Criteria) this;
        }

        public Criteria andTaskStatusNotIn(List<Byte> values) {
            addCriterion("task_status not in", values, "taskStatus");
            return (Criteria) this;
        }

        public Criteria andTaskStatusBetween(Byte value1, Byte value2) {
            addCriterion("task_status between", value1, value2, "taskStatus");
            return (Criteria) this;
        }

        public Criteria andTaskStatusNotBetween(Byte value1, Byte value2) {
            addCriterion("task_status not between", value1, value2, "taskStatus");
            return (Criteria) this;
        }

        public Criteria andTaskPubDateIsNull() {
            addCriterion("task_pub_date is null");
            return (Criteria) this;
        }

        public Criteria andTaskPubDateIsNotNull() {
            addCriterion("task_pub_date is not null");
            return (Criteria) this;
        }

        public Criteria andTaskPubDateEqualTo(Date value) {
            addCriterionForJDBCDate("task_pub_date =", value, "taskPubDate");
            return (Criteria) this;
        }

        public Criteria andTaskPubDateNotEqualTo(Date value) {
            addCriterionForJDBCDate("task_pub_date <>", value, "taskPubDate");
            return (Criteria) this;
        }

        public Criteria andTaskPubDateGreaterThan(Date value) {
            addCriterionForJDBCDate("task_pub_date >", value, "taskPubDate");
            return (Criteria) this;
        }

        public Criteria andTaskPubDateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("task_pub_date >=", value, "taskPubDate");
            return (Criteria) this;
        }

        public Criteria andTaskPubDateLessThan(Date value) {
            addCriterionForJDBCDate("task_pub_date <", value, "taskPubDate");
            return (Criteria) this;
        }

        public Criteria andTaskPubDateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("task_pub_date <=", value, "taskPubDate");
            return (Criteria) this;
        }

        public Criteria andTaskPubDateIn(List<Date> values) {
            addCriterionForJDBCDate("task_pub_date in", values, "taskPubDate");
            return (Criteria) this;
        }

        public Criteria andTaskPubDateNotIn(List<Date> values) {
            addCriterionForJDBCDate("task_pub_date not in", values, "taskPubDate");
            return (Criteria) this;
        }

        public Criteria andTaskPubDateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("task_pub_date between", value1, value2, "taskPubDate");
            return (Criteria) this;
        }

        public Criteria andTaskPubDateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("task_pub_date not between", value1, value2, "taskPubDate");
            return (Criteria) this;
        }

        public Criteria andTaskTypeIsNull() {
            addCriterion("task_type is null");
            return (Criteria) this;
        }

        public Criteria andTaskTypeIsNotNull() {
            addCriterion("task_type is not null");
            return (Criteria) this;
        }

        public Criteria andTaskTypeEqualTo(Integer value) {
            addCriterion("task_type =", value, "taskType");
            return (Criteria) this;
        }

        public Criteria andTaskTypeNotEqualTo(Integer value) {
            addCriterion("task_type <>", value, "taskType");
            return (Criteria) this;
        }

        public Criteria andTaskTypeGreaterThan(Integer value) {
            addCriterion("task_type >", value, "taskType");
            return (Criteria) this;
        }

        public Criteria andTaskTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("task_type >=", value, "taskType");
            return (Criteria) this;
        }

        public Criteria andTaskTypeLessThan(Integer value) {
            addCriterion("task_type <", value, "taskType");
            return (Criteria) this;
        }

        public Criteria andTaskTypeLessThanOrEqualTo(Integer value) {
            addCriterion("task_type <=", value, "taskType");
            return (Criteria) this;
        }

        public Criteria andTaskTypeIn(List<Integer> values) {
            addCriterion("task_type in", values, "taskType");
            return (Criteria) this;
        }

        public Criteria andTaskTypeNotIn(List<Integer> values) {
            addCriterion("task_type not in", values, "taskType");
            return (Criteria) this;
        }

        public Criteria andTaskTypeBetween(Integer value1, Integer value2) {
            addCriterion("task_type between", value1, value2, "taskType");
            return (Criteria) this;
        }

        public Criteria andTaskTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("task_type not between", value1, value2, "taskType");
            return (Criteria) this;
        }

        public Criteria andAssignCodeIsNull() {
            addCriterion("assign_code is null");
            return (Criteria) this;
        }

        public Criteria andAssignCodeIsNotNull() {
            addCriterion("assign_code is not null");
            return (Criteria) this;
        }

        public Criteria andAssignCodeEqualTo(String value) {
            addCriterion("assign_code =", value, "assignCode");
            return (Criteria) this;
        }

        public Criteria andAssignCodeNotEqualTo(String value) {
            addCriterion("assign_code <>", value, "assignCode");
            return (Criteria) this;
        }

        public Criteria andAssignCodeGreaterThan(String value) {
            addCriterion("assign_code >", value, "assignCode");
            return (Criteria) this;
        }

        public Criteria andAssignCodeGreaterThanOrEqualTo(String value) {
            addCriterion("assign_code >=", value, "assignCode");
            return (Criteria) this;
        }

        public Criteria andAssignCodeLessThan(String value) {
            addCriterion("assign_code <", value, "assignCode");
            return (Criteria) this;
        }

        public Criteria andAssignCodeLessThanOrEqualTo(String value) {
            addCriterion("assign_code <=", value, "assignCode");
            return (Criteria) this;
        }

        public Criteria andAssignCodeLike(String value) {
            addCriterion("assign_code like", value, "assignCode");
            return (Criteria) this;
        }

        public Criteria andAssignCodeNotLike(String value) {
            addCriterion("assign_code not like", value, "assignCode");
            return (Criteria) this;
        }

        public Criteria andAssignCodeIn(List<String> values) {
            addCriterion("assign_code in", values, "assignCode");
            return (Criteria) this;
        }

        public Criteria andAssignCodeNotIn(List<String> values) {
            addCriterion("assign_code not in", values, "assignCode");
            return (Criteria) this;
        }

        public Criteria andAssignCodeBetween(String value1, String value2) {
            addCriterion("assign_code between", value1, value2, "assignCode");
            return (Criteria) this;
        }

        public Criteria andAssignCodeNotBetween(String value1, String value2) {
            addCriterion("assign_code not between", value1, value2, "assignCode");
            return (Criteria) this;
        }

        public Criteria andAssignRealnameIsNull() {
            addCriterion("assign_realname is null");
            return (Criteria) this;
        }

        public Criteria andAssignRealnameIsNotNull() {
            addCriterion("assign_realname is not null");
            return (Criteria) this;
        }

        public Criteria andAssignRealnameEqualTo(String value) {
            addCriterion("assign_realname =", value, "assignRealname");
            return (Criteria) this;
        }

        public Criteria andAssignRealnameNotEqualTo(String value) {
            addCriterion("assign_realname <>", value, "assignRealname");
            return (Criteria) this;
        }

        public Criteria andAssignRealnameGreaterThan(String value) {
            addCriterion("assign_realname >", value, "assignRealname");
            return (Criteria) this;
        }

        public Criteria andAssignRealnameGreaterThanOrEqualTo(String value) {
            addCriterion("assign_realname >=", value, "assignRealname");
            return (Criteria) this;
        }

        public Criteria andAssignRealnameLessThan(String value) {
            addCriterion("assign_realname <", value, "assignRealname");
            return (Criteria) this;
        }

        public Criteria andAssignRealnameLessThanOrEqualTo(String value) {
            addCriterion("assign_realname <=", value, "assignRealname");
            return (Criteria) this;
        }

        public Criteria andAssignRealnameLike(String value) {
            addCriterion("assign_realname like", value, "assignRealname");
            return (Criteria) this;
        }

        public Criteria andAssignRealnameNotLike(String value) {
            addCriterion("assign_realname not like", value, "assignRealname");
            return (Criteria) this;
        }

        public Criteria andAssignRealnameIn(List<String> values) {
            addCriterion("assign_realname in", values, "assignRealname");
            return (Criteria) this;
        }

        public Criteria andAssignRealnameNotIn(List<String> values) {
            addCriterion("assign_realname not in", values, "assignRealname");
            return (Criteria) this;
        }

        public Criteria andAssignRealnameBetween(String value1, String value2) {
            addCriterion("assign_realname between", value1, value2, "assignRealname");
            return (Criteria) this;
        }

        public Criteria andAssignRealnameNotBetween(String value1, String value2) {
            addCriterion("assign_realname not between", value1, value2, "assignRealname");
            return (Criteria) this;
        }

        public Criteria andReportCodeIsNull() {
            addCriterion("report_code is null");
            return (Criteria) this;
        }

        public Criteria andReportCodeIsNotNull() {
            addCriterion("report_code is not null");
            return (Criteria) this;
        }

        public Criteria andReportCodeEqualTo(String value) {
            addCriterion("report_code =", value, "reportCode");
            return (Criteria) this;
        }

        public Criteria andReportCodeNotEqualTo(String value) {
            addCriterion("report_code <>", value, "reportCode");
            return (Criteria) this;
        }

        public Criteria andReportCodeGreaterThan(String value) {
            addCriterion("report_code >", value, "reportCode");
            return (Criteria) this;
        }

        public Criteria andReportCodeGreaterThanOrEqualTo(String value) {
            addCriterion("report_code >=", value, "reportCode");
            return (Criteria) this;
        }

        public Criteria andReportCodeLessThan(String value) {
            addCriterion("report_code <", value, "reportCode");
            return (Criteria) this;
        }

        public Criteria andReportCodeLessThanOrEqualTo(String value) {
            addCriterion("report_code <=", value, "reportCode");
            return (Criteria) this;
        }

        public Criteria andReportCodeLike(String value) {
            addCriterion("report_code like", value, "reportCode");
            return (Criteria) this;
        }

        public Criteria andReportCodeNotLike(String value) {
            addCriterion("report_code not like", value, "reportCode");
            return (Criteria) this;
        }

        public Criteria andReportCodeIn(List<String> values) {
            addCriterion("report_code in", values, "reportCode");
            return (Criteria) this;
        }

        public Criteria andReportCodeNotIn(List<String> values) {
            addCriterion("report_code not in", values, "reportCode");
            return (Criteria) this;
        }

        public Criteria andReportCodeBetween(String value1, String value2) {
            addCriterion("report_code between", value1, value2, "reportCode");
            return (Criteria) this;
        }

        public Criteria andReportCodeNotBetween(String value1, String value2) {
            addCriterion("report_code not between", value1, value2, "reportCode");
            return (Criteria) this;
        }

        public Criteria andReportRealnameIsNull() {
            addCriterion("report_realname is null");
            return (Criteria) this;
        }

        public Criteria andReportRealnameIsNotNull() {
            addCriterion("report_realname is not null");
            return (Criteria) this;
        }

        public Criteria andReportRealnameEqualTo(String value) {
            addCriterion("report_realname =", value, "reportRealname");
            return (Criteria) this;
        }

        public Criteria andReportRealnameNotEqualTo(String value) {
            addCriterion("report_realname <>", value, "reportRealname");
            return (Criteria) this;
        }

        public Criteria andReportRealnameGreaterThan(String value) {
            addCriterion("report_realname >", value, "reportRealname");
            return (Criteria) this;
        }

        public Criteria andReportRealnameGreaterThanOrEqualTo(String value) {
            addCriterion("report_realname >=", value, "reportRealname");
            return (Criteria) this;
        }

        public Criteria andReportRealnameLessThan(String value) {
            addCriterion("report_realname <", value, "reportRealname");
            return (Criteria) this;
        }

        public Criteria andReportRealnameLessThanOrEqualTo(String value) {
            addCriterion("report_realname <=", value, "reportRealname");
            return (Criteria) this;
        }

        public Criteria andReportRealnameLike(String value) {
            addCriterion("report_realname like", value, "reportRealname");
            return (Criteria) this;
        }

        public Criteria andReportRealnameNotLike(String value) {
            addCriterion("report_realname not like", value, "reportRealname");
            return (Criteria) this;
        }

        public Criteria andReportRealnameIn(List<String> values) {
            addCriterion("report_realname in", values, "reportRealname");
            return (Criteria) this;
        }

        public Criteria andReportRealnameNotIn(List<String> values) {
            addCriterion("report_realname not in", values, "reportRealname");
            return (Criteria) this;
        }

        public Criteria andReportRealnameBetween(String value1, String value2) {
            addCriterion("report_realname between", value1, value2, "reportRealname");
            return (Criteria) this;
        }

        public Criteria andReportRealnameNotBetween(String value1, String value2) {
            addCriterion("report_realname not between", value1, value2, "reportRealname");
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