package domain.sc.scCommittee;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class ScCommitteeTopicViewExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public ScCommitteeTopicViewExample() {
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

        public Criteria andHasVoteIsNull() {
            addCriterion("has_vote is null");
            return (Criteria) this;
        }

        public Criteria andHasVoteIsNotNull() {
            addCriterion("has_vote is not null");
            return (Criteria) this;
        }

        public Criteria andHasVoteEqualTo(Boolean value) {
            addCriterion("has_vote =", value, "hasVote");
            return (Criteria) this;
        }

        public Criteria andHasVoteNotEqualTo(Boolean value) {
            addCriterion("has_vote <>", value, "hasVote");
            return (Criteria) this;
        }

        public Criteria andHasVoteGreaterThan(Boolean value) {
            addCriterion("has_vote >", value, "hasVote");
            return (Criteria) this;
        }

        public Criteria andHasVoteGreaterThanOrEqualTo(Boolean value) {
            addCriterion("has_vote >=", value, "hasVote");
            return (Criteria) this;
        }

        public Criteria andHasVoteLessThan(Boolean value) {
            addCriterion("has_vote <", value, "hasVote");
            return (Criteria) this;
        }

        public Criteria andHasVoteLessThanOrEqualTo(Boolean value) {
            addCriterion("has_vote <=", value, "hasVote");
            return (Criteria) this;
        }

        public Criteria andHasVoteIn(List<Boolean> values) {
            addCriterion("has_vote in", values, "hasVote");
            return (Criteria) this;
        }

        public Criteria andHasVoteNotIn(List<Boolean> values) {
            addCriterion("has_vote not in", values, "hasVote");
            return (Criteria) this;
        }

        public Criteria andHasVoteBetween(Boolean value1, Boolean value2) {
            addCriterion("has_vote between", value1, value2, "hasVote");
            return (Criteria) this;
        }

        public Criteria andHasVoteNotBetween(Boolean value1, Boolean value2) {
            addCriterion("has_vote not between", value1, value2, "hasVote");
            return (Criteria) this;
        }

        public Criteria andHasOtherVoteIsNull() {
            addCriterion("has_other_vote is null");
            return (Criteria) this;
        }

        public Criteria andHasOtherVoteIsNotNull() {
            addCriterion("has_other_vote is not null");
            return (Criteria) this;
        }

        public Criteria andHasOtherVoteEqualTo(Boolean value) {
            addCriterion("has_other_vote =", value, "hasOtherVote");
            return (Criteria) this;
        }

        public Criteria andHasOtherVoteNotEqualTo(Boolean value) {
            addCriterion("has_other_vote <>", value, "hasOtherVote");
            return (Criteria) this;
        }

        public Criteria andHasOtherVoteGreaterThan(Boolean value) {
            addCriterion("has_other_vote >", value, "hasOtherVote");
            return (Criteria) this;
        }

        public Criteria andHasOtherVoteGreaterThanOrEqualTo(Boolean value) {
            addCriterion("has_other_vote >=", value, "hasOtherVote");
            return (Criteria) this;
        }

        public Criteria andHasOtherVoteLessThan(Boolean value) {
            addCriterion("has_other_vote <", value, "hasOtherVote");
            return (Criteria) this;
        }

        public Criteria andHasOtherVoteLessThanOrEqualTo(Boolean value) {
            addCriterion("has_other_vote <=", value, "hasOtherVote");
            return (Criteria) this;
        }

        public Criteria andHasOtherVoteIn(List<Boolean> values) {
            addCriterion("has_other_vote in", values, "hasOtherVote");
            return (Criteria) this;
        }

        public Criteria andHasOtherVoteNotIn(List<Boolean> values) {
            addCriterion("has_other_vote not in", values, "hasOtherVote");
            return (Criteria) this;
        }

        public Criteria andHasOtherVoteBetween(Boolean value1, Boolean value2) {
            addCriterion("has_other_vote between", value1, value2, "hasOtherVote");
            return (Criteria) this;
        }

        public Criteria andHasOtherVoteNotBetween(Boolean value1, Boolean value2) {
            addCriterion("has_other_vote not between", value1, value2, "hasOtherVote");
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

        public Criteria andCountIsNull() {
            addCriterion("count is null");
            return (Criteria) this;
        }

        public Criteria andCountIsNotNull() {
            addCriterion("count is not null");
            return (Criteria) this;
        }

        public Criteria andCountEqualTo(BigDecimal value) {
            addCriterion("count =", value, "count");
            return (Criteria) this;
        }

        public Criteria andCountNotEqualTo(BigDecimal value) {
            addCriterion("count <>", value, "count");
            return (Criteria) this;
        }

        public Criteria andCountGreaterThan(BigDecimal value) {
            addCriterion("count >", value, "count");
            return (Criteria) this;
        }

        public Criteria andCountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("count >=", value, "count");
            return (Criteria) this;
        }

        public Criteria andCountLessThan(BigDecimal value) {
            addCriterion("count <", value, "count");
            return (Criteria) this;
        }

        public Criteria andCountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("count <=", value, "count");
            return (Criteria) this;
        }

        public Criteria andCountIn(List<BigDecimal> values) {
            addCriterion("count in", values, "count");
            return (Criteria) this;
        }

        public Criteria andCountNotIn(List<BigDecimal> values) {
            addCriterion("count not in", values, "count");
            return (Criteria) this;
        }

        public Criteria andCountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("count between", value1, value2, "count");
            return (Criteria) this;
        }

        public Criteria andCountNotBetween(BigDecimal value1, BigDecimal value2) {
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

        public Criteria andAbsentCountEqualTo(BigDecimal value) {
            addCriterion("absent_count =", value, "absentCount");
            return (Criteria) this;
        }

        public Criteria andAbsentCountNotEqualTo(BigDecimal value) {
            addCriterion("absent_count <>", value, "absentCount");
            return (Criteria) this;
        }

        public Criteria andAbsentCountGreaterThan(BigDecimal value) {
            addCriterion("absent_count >", value, "absentCount");
            return (Criteria) this;
        }

        public Criteria andAbsentCountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("absent_count >=", value, "absentCount");
            return (Criteria) this;
        }

        public Criteria andAbsentCountLessThan(BigDecimal value) {
            addCriterion("absent_count <", value, "absentCount");
            return (Criteria) this;
        }

        public Criteria andAbsentCountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("absent_count <=", value, "absentCount");
            return (Criteria) this;
        }

        public Criteria andAbsentCountIn(List<BigDecimal> values) {
            addCriterion("absent_count in", values, "absentCount");
            return (Criteria) this;
        }

        public Criteria andAbsentCountNotIn(List<BigDecimal> values) {
            addCriterion("absent_count not in", values, "absentCount");
            return (Criteria) this;
        }

        public Criteria andAbsentCountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("absent_count between", value1, value2, "absentCount");
            return (Criteria) this;
        }

        public Criteria andAbsentCountNotBetween(BigDecimal value1, BigDecimal value2) {
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