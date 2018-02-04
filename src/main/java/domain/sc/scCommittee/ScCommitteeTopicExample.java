package domain.sc.scCommittee;

import java.util.ArrayList;
import java.util.List;

public class ScCommitteeTopicExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public ScCommitteeTopicExample() {
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