package domain.ps;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class PsInfoViewExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public PsInfoViewExample() {
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

        public Criteria andSeqIsNull() {
            addCriterion("seq is null");
            return (Criteria) this;
        }

        public Criteria andSeqIsNotNull() {
            addCriterion("seq is not null");
            return (Criteria) this;
        }

        public Criteria andSeqEqualTo(String value) {
            addCriterion("seq =", value, "seq");
            return (Criteria) this;
        }

        public Criteria andSeqNotEqualTo(String value) {
            addCriterion("seq <>", value, "seq");
            return (Criteria) this;
        }

        public Criteria andSeqGreaterThan(String value) {
            addCriterion("seq >", value, "seq");
            return (Criteria) this;
        }

        public Criteria andSeqGreaterThanOrEqualTo(String value) {
            addCriterion("seq >=", value, "seq");
            return (Criteria) this;
        }

        public Criteria andSeqLessThan(String value) {
            addCriterion("seq <", value, "seq");
            return (Criteria) this;
        }

        public Criteria andSeqLessThanOrEqualTo(String value) {
            addCriterion("seq <=", value, "seq");
            return (Criteria) this;
        }

        public Criteria andSeqLike(String value) {
            addCriterion("seq like", value, "seq");
            return (Criteria) this;
        }

        public Criteria andSeqNotLike(String value) {
            addCriterion("seq not like", value, "seq");
            return (Criteria) this;
        }

        public Criteria andSeqIn(List<String> values) {
            addCriterion("seq in", values, "seq");
            return (Criteria) this;
        }

        public Criteria andSeqNotIn(List<String> values) {
            addCriterion("seq not in", values, "seq");
            return (Criteria) this;
        }

        public Criteria andSeqBetween(String value1, String value2) {
            addCriterion("seq between", value1, value2, "seq");
            return (Criteria) this;
        }

        public Criteria andSeqNotBetween(String value1, String value2) {
            addCriterion("seq not between", value1, value2, "seq");
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

        public Criteria andFoundDateIsNull() {
            addCriterion("found_date is null");
            return (Criteria) this;
        }

        public Criteria andFoundDateIsNotNull() {
            addCriterion("found_date is not null");
            return (Criteria) this;
        }

        public Criteria andFoundDateEqualTo(Date value) {
            addCriterionForJDBCDate("found_date =", value, "foundDate");
            return (Criteria) this;
        }

        public Criteria andFoundDateNotEqualTo(Date value) {
            addCriterionForJDBCDate("found_date <>", value, "foundDate");
            return (Criteria) this;
        }

        public Criteria andFoundDateGreaterThan(Date value) {
            addCriterionForJDBCDate("found_date >", value, "foundDate");
            return (Criteria) this;
        }

        public Criteria andFoundDateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("found_date >=", value, "foundDate");
            return (Criteria) this;
        }

        public Criteria andFoundDateLessThan(Date value) {
            addCriterionForJDBCDate("found_date <", value, "foundDate");
            return (Criteria) this;
        }

        public Criteria andFoundDateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("found_date <=", value, "foundDate");
            return (Criteria) this;
        }

        public Criteria andFoundDateIn(List<Date> values) {
            addCriterionForJDBCDate("found_date in", values, "foundDate");
            return (Criteria) this;
        }

        public Criteria andFoundDateNotIn(List<Date> values) {
            addCriterionForJDBCDate("found_date not in", values, "foundDate");
            return (Criteria) this;
        }

        public Criteria andFoundDateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("found_date between", value1, value2, "foundDate");
            return (Criteria) this;
        }

        public Criteria andFoundDateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("found_date not between", value1, value2, "foundDate");
            return (Criteria) this;
        }

        public Criteria andAbolishDateIsNull() {
            addCriterion("abolish_date is null");
            return (Criteria) this;
        }

        public Criteria andAbolishDateIsNotNull() {
            addCriterion("abolish_date is not null");
            return (Criteria) this;
        }

        public Criteria andAbolishDateEqualTo(Date value) {
            addCriterionForJDBCDate("abolish_date =", value, "abolishDate");
            return (Criteria) this;
        }

        public Criteria andAbolishDateNotEqualTo(Date value) {
            addCriterionForJDBCDate("abolish_date <>", value, "abolishDate");
            return (Criteria) this;
        }

        public Criteria andAbolishDateGreaterThan(Date value) {
            addCriterionForJDBCDate("abolish_date >", value, "abolishDate");
            return (Criteria) this;
        }

        public Criteria andAbolishDateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("abolish_date >=", value, "abolishDate");
            return (Criteria) this;
        }

        public Criteria andAbolishDateLessThan(Date value) {
            addCriterionForJDBCDate("abolish_date <", value, "abolishDate");
            return (Criteria) this;
        }

        public Criteria andAbolishDateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("abolish_date <=", value, "abolishDate");
            return (Criteria) this;
        }

        public Criteria andAbolishDateIn(List<Date> values) {
            addCriterionForJDBCDate("abolish_date in", values, "abolishDate");
            return (Criteria) this;
        }

        public Criteria andAbolishDateNotIn(List<Date> values) {
            addCriterionForJDBCDate("abolish_date not in", values, "abolishDate");
            return (Criteria) this;
        }

        public Criteria andAbolishDateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("abolish_date between", value1, value2, "abolishDate");
            return (Criteria) this;
        }

        public Criteria andAbolishDateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("abolish_date not between", value1, value2, "abolishDate");
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

        public Criteria andIsHistoryIsNull() {
            addCriterion("is_history is null");
            return (Criteria) this;
        }

        public Criteria andIsHistoryIsNotNull() {
            addCriterion("is_history is not null");
            return (Criteria) this;
        }

        public Criteria andIsHistoryEqualTo(Boolean value) {
            addCriterion("is_history =", value, "isHistory");
            return (Criteria) this;
        }

        public Criteria andIsHistoryNotEqualTo(Boolean value) {
            addCriterion("is_history <>", value, "isHistory");
            return (Criteria) this;
        }

        public Criteria andIsHistoryGreaterThan(Boolean value) {
            addCriterion("is_history >", value, "isHistory");
            return (Criteria) this;
        }

        public Criteria andIsHistoryGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_history >=", value, "isHistory");
            return (Criteria) this;
        }

        public Criteria andIsHistoryLessThan(Boolean value) {
            addCriterion("is_history <", value, "isHistory");
            return (Criteria) this;
        }

        public Criteria andIsHistoryLessThanOrEqualTo(Boolean value) {
            addCriterion("is_history <=", value, "isHistory");
            return (Criteria) this;
        }

        public Criteria andIsHistoryIn(List<Boolean> values) {
            addCriterion("is_history in", values, "isHistory");
            return (Criteria) this;
        }

        public Criteria andIsHistoryNotIn(List<Boolean> values) {
            addCriterion("is_history not in", values, "isHistory");
            return (Criteria) this;
        }

        public Criteria andIsHistoryBetween(Boolean value1, Boolean value2) {
            addCriterion("is_history between", value1, value2, "isHistory");
            return (Criteria) this;
        }

        public Criteria andIsHistoryNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_history not between", value1, value2, "isHistory");
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

        public Criteria andIsDeletedIsNull() {
            addCriterion("is_deleted is null");
            return (Criteria) this;
        }

        public Criteria andIsDeletedIsNotNull() {
            addCriterion("is_deleted is not null");
            return (Criteria) this;
        }

        public Criteria andIsDeletedEqualTo(Boolean value) {
            addCriterion("is_deleted =", value, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedNotEqualTo(Boolean value) {
            addCriterion("is_deleted <>", value, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedGreaterThan(Boolean value) {
            addCriterion("is_deleted >", value, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_deleted >=", value, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedLessThan(Boolean value) {
            addCriterion("is_deleted <", value, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedLessThanOrEqualTo(Boolean value) {
            addCriterion("is_deleted <=", value, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedIn(List<Boolean> values) {
            addCriterion("is_deleted in", values, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedNotIn(List<Boolean> values) {
            addCriterion("is_deleted not in", values, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedBetween(Boolean value1, Boolean value2) {
            addCriterion("is_deleted between", value1, value2, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_deleted not between", value1, value2, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andRectorUserIdIsNull() {
            addCriterion("rector_user_id is null");
            return (Criteria) this;
        }

        public Criteria andRectorUserIdIsNotNull() {
            addCriterion("rector_user_id is not null");
            return (Criteria) this;
        }

        public Criteria andRectorUserIdEqualTo(Integer value) {
            addCriterion("rector_user_id =", value, "rectorUserId");
            return (Criteria) this;
        }

        public Criteria andRectorUserIdNotEqualTo(Integer value) {
            addCriterion("rector_user_id <>", value, "rectorUserId");
            return (Criteria) this;
        }

        public Criteria andRectorUserIdGreaterThan(Integer value) {
            addCriterion("rector_user_id >", value, "rectorUserId");
            return (Criteria) this;
        }

        public Criteria andRectorUserIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("rector_user_id >=", value, "rectorUserId");
            return (Criteria) this;
        }

        public Criteria andRectorUserIdLessThan(Integer value) {
            addCriterion("rector_user_id <", value, "rectorUserId");
            return (Criteria) this;
        }

        public Criteria andRectorUserIdLessThanOrEqualTo(Integer value) {
            addCriterion("rector_user_id <=", value, "rectorUserId");
            return (Criteria) this;
        }

        public Criteria andRectorUserIdIn(List<Integer> values) {
            addCriterion("rector_user_id in", values, "rectorUserId");
            return (Criteria) this;
        }

        public Criteria andRectorUserIdNotIn(List<Integer> values) {
            addCriterion("rector_user_id not in", values, "rectorUserId");
            return (Criteria) this;
        }

        public Criteria andRectorUserIdBetween(Integer value1, Integer value2) {
            addCriterion("rector_user_id between", value1, value2, "rectorUserId");
            return (Criteria) this;
        }

        public Criteria andRectorUserIdNotBetween(Integer value1, Integer value2) {
            addCriterion("rector_user_id not between", value1, value2, "rectorUserId");
            return (Criteria) this;
        }

        public Criteria andRectorTitleIsNull() {
            addCriterion("rector_title is null");
            return (Criteria) this;
        }

        public Criteria andRectorTitleIsNotNull() {
            addCriterion("rector_title is not null");
            return (Criteria) this;
        }

        public Criteria andRectorTitleEqualTo(String value) {
            addCriterion("rector_title =", value, "rectorTitle");
            return (Criteria) this;
        }

        public Criteria andRectorTitleNotEqualTo(String value) {
            addCriterion("rector_title <>", value, "rectorTitle");
            return (Criteria) this;
        }

        public Criteria andRectorTitleGreaterThan(String value) {
            addCriterion("rector_title >", value, "rectorTitle");
            return (Criteria) this;
        }

        public Criteria andRectorTitleGreaterThanOrEqualTo(String value) {
            addCriterion("rector_title >=", value, "rectorTitle");
            return (Criteria) this;
        }

        public Criteria andRectorTitleLessThan(String value) {
            addCriterion("rector_title <", value, "rectorTitle");
            return (Criteria) this;
        }

        public Criteria andRectorTitleLessThanOrEqualTo(String value) {
            addCriterion("rector_title <=", value, "rectorTitle");
            return (Criteria) this;
        }

        public Criteria andRectorTitleLike(String value) {
            addCriterion("rector_title like", value, "rectorTitle");
            return (Criteria) this;
        }

        public Criteria andRectorTitleNotLike(String value) {
            addCriterion("rector_title not like", value, "rectorTitle");
            return (Criteria) this;
        }

        public Criteria andRectorTitleIn(List<String> values) {
            addCriterion("rector_title in", values, "rectorTitle");
            return (Criteria) this;
        }

        public Criteria andRectorTitleNotIn(List<String> values) {
            addCriterion("rector_title not in", values, "rectorTitle");
            return (Criteria) this;
        }

        public Criteria andRectorTitleBetween(String value1, String value2) {
            addCriterion("rector_title between", value1, value2, "rectorTitle");
            return (Criteria) this;
        }

        public Criteria andRectorTitleNotBetween(String value1, String value2) {
            addCriterion("rector_title not between", value1, value2, "rectorTitle");
            return (Criteria) this;
        }

        public Criteria andRectorMobileIsNull() {
            addCriterion("rector_mobile is null");
            return (Criteria) this;
        }

        public Criteria andRectorMobileIsNotNull() {
            addCriterion("rector_mobile is not null");
            return (Criteria) this;
        }

        public Criteria andRectorMobileEqualTo(String value) {
            addCriterion("rector_mobile =", value, "rectorMobile");
            return (Criteria) this;
        }

        public Criteria andRectorMobileNotEqualTo(String value) {
            addCriterion("rector_mobile <>", value, "rectorMobile");
            return (Criteria) this;
        }

        public Criteria andRectorMobileGreaterThan(String value) {
            addCriterion("rector_mobile >", value, "rectorMobile");
            return (Criteria) this;
        }

        public Criteria andRectorMobileGreaterThanOrEqualTo(String value) {
            addCriterion("rector_mobile >=", value, "rectorMobile");
            return (Criteria) this;
        }

        public Criteria andRectorMobileLessThan(String value) {
            addCriterion("rector_mobile <", value, "rectorMobile");
            return (Criteria) this;
        }

        public Criteria andRectorMobileLessThanOrEqualTo(String value) {
            addCriterion("rector_mobile <=", value, "rectorMobile");
            return (Criteria) this;
        }

        public Criteria andRectorMobileLike(String value) {
            addCriterion("rector_mobile like", value, "rectorMobile");
            return (Criteria) this;
        }

        public Criteria andRectorMobileNotLike(String value) {
            addCriterion("rector_mobile not like", value, "rectorMobile");
            return (Criteria) this;
        }

        public Criteria andRectorMobileIn(List<String> values) {
            addCriterion("rector_mobile in", values, "rectorMobile");
            return (Criteria) this;
        }

        public Criteria andRectorMobileNotIn(List<String> values) {
            addCriterion("rector_mobile not in", values, "rectorMobile");
            return (Criteria) this;
        }

        public Criteria andRectorMobileBetween(String value1, String value2) {
            addCriterion("rector_mobile between", value1, value2, "rectorMobile");
            return (Criteria) this;
        }

        public Criteria andRectorMobileNotBetween(String value1, String value2) {
            addCriterion("rector_mobile not between", value1, value2, "rectorMobile");
            return (Criteria) this;
        }

        public Criteria andJointIdsIsNull() {
            addCriterion("joint_ids is null");
            return (Criteria) this;
        }

        public Criteria andJointIdsIsNotNull() {
            addCriterion("joint_ids is not null");
            return (Criteria) this;
        }

        public Criteria andJointIdsEqualTo(String value) {
            addCriterion("joint_ids =", value, "jointIds");
            return (Criteria) this;
        }

        public Criteria andJointIdsNotEqualTo(String value) {
            addCriterion("joint_ids <>", value, "jointIds");
            return (Criteria) this;
        }

        public Criteria andJointIdsGreaterThan(String value) {
            addCriterion("joint_ids >", value, "jointIds");
            return (Criteria) this;
        }

        public Criteria andJointIdsGreaterThanOrEqualTo(String value) {
            addCriterion("joint_ids >=", value, "jointIds");
            return (Criteria) this;
        }

        public Criteria andJointIdsLessThan(String value) {
            addCriterion("joint_ids <", value, "jointIds");
            return (Criteria) this;
        }

        public Criteria andJointIdsLessThanOrEqualTo(String value) {
            addCriterion("joint_ids <=", value, "jointIds");
            return (Criteria) this;
        }

        public Criteria andJointIdsLike(String value) {
            addCriterion("joint_ids like", value, "jointIds");
            return (Criteria) this;
        }

        public Criteria andJointIdsNotLike(String value) {
            addCriterion("joint_ids not like", value, "jointIds");
            return (Criteria) this;
        }

        public Criteria andJointIdsIn(List<String> values) {
            addCriterion("joint_ids in", values, "jointIds");
            return (Criteria) this;
        }

        public Criteria andJointIdsNotIn(List<String> values) {
            addCriterion("joint_ids not in", values, "jointIds");
            return (Criteria) this;
        }

        public Criteria andJointIdsBetween(String value1, String value2) {
            addCriterion("joint_ids between", value1, value2, "jointIds");
            return (Criteria) this;
        }

        public Criteria andJointIdsNotBetween(String value1, String value2) {
            addCriterion("joint_ids not between", value1, value2, "jointIds");
            return (Criteria) this;
        }

        public Criteria andHostIdIsNull() {
            addCriterion("host_id is null");
            return (Criteria) this;
        }

        public Criteria andHostIdIsNotNull() {
            addCriterion("host_id is not null");
            return (Criteria) this;
        }

        public Criteria andHostIdEqualTo(Integer value) {
            addCriterion("host_id =", value, "hostId");
            return (Criteria) this;
        }

        public Criteria andHostIdNotEqualTo(Integer value) {
            addCriterion("host_id <>", value, "hostId");
            return (Criteria) this;
        }

        public Criteria andHostIdGreaterThan(Integer value) {
            addCriterion("host_id >", value, "hostId");
            return (Criteria) this;
        }

        public Criteria andHostIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("host_id >=", value, "hostId");
            return (Criteria) this;
        }

        public Criteria andHostIdLessThan(Integer value) {
            addCriterion("host_id <", value, "hostId");
            return (Criteria) this;
        }

        public Criteria andHostIdLessThanOrEqualTo(Integer value) {
            addCriterion("host_id <=", value, "hostId");
            return (Criteria) this;
        }

        public Criteria andHostIdIn(List<Integer> values) {
            addCriterion("host_id in", values, "hostId");
            return (Criteria) this;
        }

        public Criteria andHostIdNotIn(List<Integer> values) {
            addCriterion("host_id not in", values, "hostId");
            return (Criteria) this;
        }

        public Criteria andHostIdBetween(Integer value1, Integer value2) {
            addCriterion("host_id between", value1, value2, "hostId");
            return (Criteria) this;
        }

        public Criteria andHostIdNotBetween(Integer value1, Integer value2) {
            addCriterion("host_id not between", value1, value2, "hostId");
            return (Criteria) this;
        }

        public Criteria andAdminUserIdIsNull() {
            addCriterion("admin_user_id is null");
            return (Criteria) this;
        }

        public Criteria andAdminUserIdIsNotNull() {
            addCriterion("admin_user_id is not null");
            return (Criteria) this;
        }

        public Criteria andAdminUserIdEqualTo(Integer value) {
            addCriterion("admin_user_id =", value, "adminUserId");
            return (Criteria) this;
        }

        public Criteria andAdminUserIdNotEqualTo(Integer value) {
            addCriterion("admin_user_id <>", value, "adminUserId");
            return (Criteria) this;
        }

        public Criteria andAdminUserIdGreaterThan(Integer value) {
            addCriterion("admin_user_id >", value, "adminUserId");
            return (Criteria) this;
        }

        public Criteria andAdminUserIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("admin_user_id >=", value, "adminUserId");
            return (Criteria) this;
        }

        public Criteria andAdminUserIdLessThan(Integer value) {
            addCriterion("admin_user_id <", value, "adminUserId");
            return (Criteria) this;
        }

        public Criteria andAdminUserIdLessThanOrEqualTo(Integer value) {
            addCriterion("admin_user_id <=", value, "adminUserId");
            return (Criteria) this;
        }

        public Criteria andAdminUserIdIn(List<Integer> values) {
            addCriterion("admin_user_id in", values, "adminUserId");
            return (Criteria) this;
        }

        public Criteria andAdminUserIdNotIn(List<Integer> values) {
            addCriterion("admin_user_id not in", values, "adminUserId");
            return (Criteria) this;
        }

        public Criteria andAdminUserIdBetween(Integer value1, Integer value2) {
            addCriterion("admin_user_id between", value1, value2, "adminUserId");
            return (Criteria) this;
        }

        public Criteria andAdminUserIdNotBetween(Integer value1, Integer value2) {
            addCriterion("admin_user_id not between", value1, value2, "adminUserId");
            return (Criteria) this;
        }

        public Criteria andAdminMobileIsNull() {
            addCriterion("admin_mobile is null");
            return (Criteria) this;
        }

        public Criteria andAdminMobileIsNotNull() {
            addCriterion("admin_mobile is not null");
            return (Criteria) this;
        }

        public Criteria andAdminMobileEqualTo(String value) {
            addCriterion("admin_mobile =", value, "adminMobile");
            return (Criteria) this;
        }

        public Criteria andAdminMobileNotEqualTo(String value) {
            addCriterion("admin_mobile <>", value, "adminMobile");
            return (Criteria) this;
        }

        public Criteria andAdminMobileGreaterThan(String value) {
            addCriterion("admin_mobile >", value, "adminMobile");
            return (Criteria) this;
        }

        public Criteria andAdminMobileGreaterThanOrEqualTo(String value) {
            addCriterion("admin_mobile >=", value, "adminMobile");
            return (Criteria) this;
        }

        public Criteria andAdminMobileLessThan(String value) {
            addCriterion("admin_mobile <", value, "adminMobile");
            return (Criteria) this;
        }

        public Criteria andAdminMobileLessThanOrEqualTo(String value) {
            addCriterion("admin_mobile <=", value, "adminMobile");
            return (Criteria) this;
        }

        public Criteria andAdminMobileLike(String value) {
            addCriterion("admin_mobile like", value, "adminMobile");
            return (Criteria) this;
        }

        public Criteria andAdminMobileNotLike(String value) {
            addCriterion("admin_mobile not like", value, "adminMobile");
            return (Criteria) this;
        }

        public Criteria andAdminMobileIn(List<String> values) {
            addCriterion("admin_mobile in", values, "adminMobile");
            return (Criteria) this;
        }

        public Criteria andAdminMobileNotIn(List<String> values) {
            addCriterion("admin_mobile not in", values, "adminMobile");
            return (Criteria) this;
        }

        public Criteria andAdminMobileBetween(String value1, String value2) {
            addCriterion("admin_mobile between", value1, value2, "adminMobile");
            return (Criteria) this;
        }

        public Criteria andAdminMobileNotBetween(String value1, String value2) {
            addCriterion("admin_mobile not between", value1, value2, "adminMobile");
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