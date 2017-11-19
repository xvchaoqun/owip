package domain.pcs;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PcsConfigExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public PcsConfigExample() {
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

        public Criteria andIsCurrentIsNull() {
            addCriterion("is_current is null");
            return (Criteria) this;
        }

        public Criteria andIsCurrentIsNotNull() {
            addCriterion("is_current is not null");
            return (Criteria) this;
        }

        public Criteria andIsCurrentEqualTo(Boolean value) {
            addCriterion("is_current =", value, "isCurrent");
            return (Criteria) this;
        }

        public Criteria andIsCurrentNotEqualTo(Boolean value) {
            addCriterion("is_current <>", value, "isCurrent");
            return (Criteria) this;
        }

        public Criteria andIsCurrentGreaterThan(Boolean value) {
            addCriterion("is_current >", value, "isCurrent");
            return (Criteria) this;
        }

        public Criteria andIsCurrentGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_current >=", value, "isCurrent");
            return (Criteria) this;
        }

        public Criteria andIsCurrentLessThan(Boolean value) {
            addCriterion("is_current <", value, "isCurrent");
            return (Criteria) this;
        }

        public Criteria andIsCurrentLessThanOrEqualTo(Boolean value) {
            addCriterion("is_current <=", value, "isCurrent");
            return (Criteria) this;
        }

        public Criteria andIsCurrentIn(List<Boolean> values) {
            addCriterion("is_current in", values, "isCurrent");
            return (Criteria) this;
        }

        public Criteria andIsCurrentNotIn(List<Boolean> values) {
            addCriterion("is_current not in", values, "isCurrent");
            return (Criteria) this;
        }

        public Criteria andIsCurrentBetween(Boolean value1, Boolean value2) {
            addCriterion("is_current between", value1, value2, "isCurrent");
            return (Criteria) this;
        }

        public Criteria andIsCurrentNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_current not between", value1, value2, "isCurrent");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNull() {
            addCriterion("create_time is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("create_time is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(Date value) {
            addCriterion("create_time =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(Date value) {
            addCriterion("create_time <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(Date value) {
            addCriterion("create_time >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("create_time >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(Date value) {
            addCriterion("create_time <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(Date value) {
            addCriterion("create_time <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<Date> values) {
            addCriterion("create_time in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<Date> values) {
            addCriterion("create_time not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(Date value1, Date value2) {
            addCriterion("create_time between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(Date value1, Date value2) {
            addCriterion("create_time not between", value1, value2, "createTime");
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

        public Criteria andProposalSubmitTimeIsNull() {
            addCriterion("proposal_submit_time is null");
            return (Criteria) this;
        }

        public Criteria andProposalSubmitTimeIsNotNull() {
            addCriterion("proposal_submit_time is not null");
            return (Criteria) this;
        }

        public Criteria andProposalSubmitTimeEqualTo(Date value) {
            addCriterion("proposal_submit_time =", value, "proposalSubmitTime");
            return (Criteria) this;
        }

        public Criteria andProposalSubmitTimeNotEqualTo(Date value) {
            addCriterion("proposal_submit_time <>", value, "proposalSubmitTime");
            return (Criteria) this;
        }

        public Criteria andProposalSubmitTimeGreaterThan(Date value) {
            addCriterion("proposal_submit_time >", value, "proposalSubmitTime");
            return (Criteria) this;
        }

        public Criteria andProposalSubmitTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("proposal_submit_time >=", value, "proposalSubmitTime");
            return (Criteria) this;
        }

        public Criteria andProposalSubmitTimeLessThan(Date value) {
            addCriterion("proposal_submit_time <", value, "proposalSubmitTime");
            return (Criteria) this;
        }

        public Criteria andProposalSubmitTimeLessThanOrEqualTo(Date value) {
            addCriterion("proposal_submit_time <=", value, "proposalSubmitTime");
            return (Criteria) this;
        }

        public Criteria andProposalSubmitTimeIn(List<Date> values) {
            addCriterion("proposal_submit_time in", values, "proposalSubmitTime");
            return (Criteria) this;
        }

        public Criteria andProposalSubmitTimeNotIn(List<Date> values) {
            addCriterion("proposal_submit_time not in", values, "proposalSubmitTime");
            return (Criteria) this;
        }

        public Criteria andProposalSubmitTimeBetween(Date value1, Date value2) {
            addCriterion("proposal_submit_time between", value1, value2, "proposalSubmitTime");
            return (Criteria) this;
        }

        public Criteria andProposalSubmitTimeNotBetween(Date value1, Date value2) {
            addCriterion("proposal_submit_time not between", value1, value2, "proposalSubmitTime");
            return (Criteria) this;
        }

        public Criteria andProposalSupportTimeIsNull() {
            addCriterion("proposal_support_time is null");
            return (Criteria) this;
        }

        public Criteria andProposalSupportTimeIsNotNull() {
            addCriterion("proposal_support_time is not null");
            return (Criteria) this;
        }

        public Criteria andProposalSupportTimeEqualTo(Date value) {
            addCriterion("proposal_support_time =", value, "proposalSupportTime");
            return (Criteria) this;
        }

        public Criteria andProposalSupportTimeNotEqualTo(Date value) {
            addCriterion("proposal_support_time <>", value, "proposalSupportTime");
            return (Criteria) this;
        }

        public Criteria andProposalSupportTimeGreaterThan(Date value) {
            addCriterion("proposal_support_time >", value, "proposalSupportTime");
            return (Criteria) this;
        }

        public Criteria andProposalSupportTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("proposal_support_time >=", value, "proposalSupportTime");
            return (Criteria) this;
        }

        public Criteria andProposalSupportTimeLessThan(Date value) {
            addCriterion("proposal_support_time <", value, "proposalSupportTime");
            return (Criteria) this;
        }

        public Criteria andProposalSupportTimeLessThanOrEqualTo(Date value) {
            addCriterion("proposal_support_time <=", value, "proposalSupportTime");
            return (Criteria) this;
        }

        public Criteria andProposalSupportTimeIn(List<Date> values) {
            addCriterion("proposal_support_time in", values, "proposalSupportTime");
            return (Criteria) this;
        }

        public Criteria andProposalSupportTimeNotIn(List<Date> values) {
            addCriterion("proposal_support_time not in", values, "proposalSupportTime");
            return (Criteria) this;
        }

        public Criteria andProposalSupportTimeBetween(Date value1, Date value2) {
            addCriterion("proposal_support_time between", value1, value2, "proposalSupportTime");
            return (Criteria) this;
        }

        public Criteria andProposalSupportTimeNotBetween(Date value1, Date value2) {
            addCriterion("proposal_support_time not between", value1, value2, "proposalSupportTime");
            return (Criteria) this;
        }

        public Criteria andProposalSupportCountIsNull() {
            addCriterion("proposal_support_count is null");
            return (Criteria) this;
        }

        public Criteria andProposalSupportCountIsNotNull() {
            addCriterion("proposal_support_count is not null");
            return (Criteria) this;
        }

        public Criteria andProposalSupportCountEqualTo(Integer value) {
            addCriterion("proposal_support_count =", value, "proposalSupportCount");
            return (Criteria) this;
        }

        public Criteria andProposalSupportCountNotEqualTo(Integer value) {
            addCriterion("proposal_support_count <>", value, "proposalSupportCount");
            return (Criteria) this;
        }

        public Criteria andProposalSupportCountGreaterThan(Integer value) {
            addCriterion("proposal_support_count >", value, "proposalSupportCount");
            return (Criteria) this;
        }

        public Criteria andProposalSupportCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("proposal_support_count >=", value, "proposalSupportCount");
            return (Criteria) this;
        }

        public Criteria andProposalSupportCountLessThan(Integer value) {
            addCriterion("proposal_support_count <", value, "proposalSupportCount");
            return (Criteria) this;
        }

        public Criteria andProposalSupportCountLessThanOrEqualTo(Integer value) {
            addCriterion("proposal_support_count <=", value, "proposalSupportCount");
            return (Criteria) this;
        }

        public Criteria andProposalSupportCountIn(List<Integer> values) {
            addCriterion("proposal_support_count in", values, "proposalSupportCount");
            return (Criteria) this;
        }

        public Criteria andProposalSupportCountNotIn(List<Integer> values) {
            addCriterion("proposal_support_count not in", values, "proposalSupportCount");
            return (Criteria) this;
        }

        public Criteria andProposalSupportCountBetween(Integer value1, Integer value2) {
            addCriterion("proposal_support_count between", value1, value2, "proposalSupportCount");
            return (Criteria) this;
        }

        public Criteria andProposalSupportCountNotBetween(Integer value1, Integer value2) {
            addCriterion("proposal_support_count not between", value1, value2, "proposalSupportCount");
            return (Criteria) this;
        }

        public Criteria andCommitteeJoinCountIsNull() {
            addCriterion("committee_join_count is null");
            return (Criteria) this;
        }

        public Criteria andCommitteeJoinCountIsNotNull() {
            addCriterion("committee_join_count is not null");
            return (Criteria) this;
        }

        public Criteria andCommitteeJoinCountEqualTo(Integer value) {
            addCriterion("committee_join_count =", value, "committeeJoinCount");
            return (Criteria) this;
        }

        public Criteria andCommitteeJoinCountNotEqualTo(Integer value) {
            addCriterion("committee_join_count <>", value, "committeeJoinCount");
            return (Criteria) this;
        }

        public Criteria andCommitteeJoinCountGreaterThan(Integer value) {
            addCriterion("committee_join_count >", value, "committeeJoinCount");
            return (Criteria) this;
        }

        public Criteria andCommitteeJoinCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("committee_join_count >=", value, "committeeJoinCount");
            return (Criteria) this;
        }

        public Criteria andCommitteeJoinCountLessThan(Integer value) {
            addCriterion("committee_join_count <", value, "committeeJoinCount");
            return (Criteria) this;
        }

        public Criteria andCommitteeJoinCountLessThanOrEqualTo(Integer value) {
            addCriterion("committee_join_count <=", value, "committeeJoinCount");
            return (Criteria) this;
        }

        public Criteria andCommitteeJoinCountIn(List<Integer> values) {
            addCriterion("committee_join_count in", values, "committeeJoinCount");
            return (Criteria) this;
        }

        public Criteria andCommitteeJoinCountNotIn(List<Integer> values) {
            addCriterion("committee_join_count not in", values, "committeeJoinCount");
            return (Criteria) this;
        }

        public Criteria andCommitteeJoinCountBetween(Integer value1, Integer value2) {
            addCriterion("committee_join_count between", value1, value2, "committeeJoinCount");
            return (Criteria) this;
        }

        public Criteria andCommitteeJoinCountNotBetween(Integer value1, Integer value2) {
            addCriterion("committee_join_count not between", value1, value2, "committeeJoinCount");
            return (Criteria) this;
        }

        public Criteria andDwSendVoteIsNull() {
            addCriterion("dw_send_vote is null");
            return (Criteria) this;
        }

        public Criteria andDwSendVoteIsNotNull() {
            addCriterion("dw_send_vote is not null");
            return (Criteria) this;
        }

        public Criteria andDwSendVoteEqualTo(Integer value) {
            addCriterion("dw_send_vote =", value, "dwSendVote");
            return (Criteria) this;
        }

        public Criteria andDwSendVoteNotEqualTo(Integer value) {
            addCriterion("dw_send_vote <>", value, "dwSendVote");
            return (Criteria) this;
        }

        public Criteria andDwSendVoteGreaterThan(Integer value) {
            addCriterion("dw_send_vote >", value, "dwSendVote");
            return (Criteria) this;
        }

        public Criteria andDwSendVoteGreaterThanOrEqualTo(Integer value) {
            addCriterion("dw_send_vote >=", value, "dwSendVote");
            return (Criteria) this;
        }

        public Criteria andDwSendVoteLessThan(Integer value) {
            addCriterion("dw_send_vote <", value, "dwSendVote");
            return (Criteria) this;
        }

        public Criteria andDwSendVoteLessThanOrEqualTo(Integer value) {
            addCriterion("dw_send_vote <=", value, "dwSendVote");
            return (Criteria) this;
        }

        public Criteria andDwSendVoteIn(List<Integer> values) {
            addCriterion("dw_send_vote in", values, "dwSendVote");
            return (Criteria) this;
        }

        public Criteria andDwSendVoteNotIn(List<Integer> values) {
            addCriterion("dw_send_vote not in", values, "dwSendVote");
            return (Criteria) this;
        }

        public Criteria andDwSendVoteBetween(Integer value1, Integer value2) {
            addCriterion("dw_send_vote between", value1, value2, "dwSendVote");
            return (Criteria) this;
        }

        public Criteria andDwSendVoteNotBetween(Integer value1, Integer value2) {
            addCriterion("dw_send_vote not between", value1, value2, "dwSendVote");
            return (Criteria) this;
        }

        public Criteria andJwSendVoteIsNull() {
            addCriterion("jw_send_vote is null");
            return (Criteria) this;
        }

        public Criteria andJwSendVoteIsNotNull() {
            addCriterion("jw_send_vote is not null");
            return (Criteria) this;
        }

        public Criteria andJwSendVoteEqualTo(Integer value) {
            addCriterion("jw_send_vote =", value, "jwSendVote");
            return (Criteria) this;
        }

        public Criteria andJwSendVoteNotEqualTo(Integer value) {
            addCriterion("jw_send_vote <>", value, "jwSendVote");
            return (Criteria) this;
        }

        public Criteria andJwSendVoteGreaterThan(Integer value) {
            addCriterion("jw_send_vote >", value, "jwSendVote");
            return (Criteria) this;
        }

        public Criteria andJwSendVoteGreaterThanOrEqualTo(Integer value) {
            addCriterion("jw_send_vote >=", value, "jwSendVote");
            return (Criteria) this;
        }

        public Criteria andJwSendVoteLessThan(Integer value) {
            addCriterion("jw_send_vote <", value, "jwSendVote");
            return (Criteria) this;
        }

        public Criteria andJwSendVoteLessThanOrEqualTo(Integer value) {
            addCriterion("jw_send_vote <=", value, "jwSendVote");
            return (Criteria) this;
        }

        public Criteria andJwSendVoteIn(List<Integer> values) {
            addCriterion("jw_send_vote in", values, "jwSendVote");
            return (Criteria) this;
        }

        public Criteria andJwSendVoteNotIn(List<Integer> values) {
            addCriterion("jw_send_vote not in", values, "jwSendVote");
            return (Criteria) this;
        }

        public Criteria andJwSendVoteBetween(Integer value1, Integer value2) {
            addCriterion("jw_send_vote between", value1, value2, "jwSendVote");
            return (Criteria) this;
        }

        public Criteria andJwSendVoteNotBetween(Integer value1, Integer value2) {
            addCriterion("jw_send_vote not between", value1, value2, "jwSendVote");
            return (Criteria) this;
        }

        public Criteria andDwBackVoteIsNull() {
            addCriterion("dw_back_vote is null");
            return (Criteria) this;
        }

        public Criteria andDwBackVoteIsNotNull() {
            addCriterion("dw_back_vote is not null");
            return (Criteria) this;
        }

        public Criteria andDwBackVoteEqualTo(Integer value) {
            addCriterion("dw_back_vote =", value, "dwBackVote");
            return (Criteria) this;
        }

        public Criteria andDwBackVoteNotEqualTo(Integer value) {
            addCriterion("dw_back_vote <>", value, "dwBackVote");
            return (Criteria) this;
        }

        public Criteria andDwBackVoteGreaterThan(Integer value) {
            addCriterion("dw_back_vote >", value, "dwBackVote");
            return (Criteria) this;
        }

        public Criteria andDwBackVoteGreaterThanOrEqualTo(Integer value) {
            addCriterion("dw_back_vote >=", value, "dwBackVote");
            return (Criteria) this;
        }

        public Criteria andDwBackVoteLessThan(Integer value) {
            addCriterion("dw_back_vote <", value, "dwBackVote");
            return (Criteria) this;
        }

        public Criteria andDwBackVoteLessThanOrEqualTo(Integer value) {
            addCriterion("dw_back_vote <=", value, "dwBackVote");
            return (Criteria) this;
        }

        public Criteria andDwBackVoteIn(List<Integer> values) {
            addCriterion("dw_back_vote in", values, "dwBackVote");
            return (Criteria) this;
        }

        public Criteria andDwBackVoteNotIn(List<Integer> values) {
            addCriterion("dw_back_vote not in", values, "dwBackVote");
            return (Criteria) this;
        }

        public Criteria andDwBackVoteBetween(Integer value1, Integer value2) {
            addCriterion("dw_back_vote between", value1, value2, "dwBackVote");
            return (Criteria) this;
        }

        public Criteria andDwBackVoteNotBetween(Integer value1, Integer value2) {
            addCriterion("dw_back_vote not between", value1, value2, "dwBackVote");
            return (Criteria) this;
        }

        public Criteria andJwBackVoteIsNull() {
            addCriterion("jw_back_vote is null");
            return (Criteria) this;
        }

        public Criteria andJwBackVoteIsNotNull() {
            addCriterion("jw_back_vote is not null");
            return (Criteria) this;
        }

        public Criteria andJwBackVoteEqualTo(Integer value) {
            addCriterion("jw_back_vote =", value, "jwBackVote");
            return (Criteria) this;
        }

        public Criteria andJwBackVoteNotEqualTo(Integer value) {
            addCriterion("jw_back_vote <>", value, "jwBackVote");
            return (Criteria) this;
        }

        public Criteria andJwBackVoteGreaterThan(Integer value) {
            addCriterion("jw_back_vote >", value, "jwBackVote");
            return (Criteria) this;
        }

        public Criteria andJwBackVoteGreaterThanOrEqualTo(Integer value) {
            addCriterion("jw_back_vote >=", value, "jwBackVote");
            return (Criteria) this;
        }

        public Criteria andJwBackVoteLessThan(Integer value) {
            addCriterion("jw_back_vote <", value, "jwBackVote");
            return (Criteria) this;
        }

        public Criteria andJwBackVoteLessThanOrEqualTo(Integer value) {
            addCriterion("jw_back_vote <=", value, "jwBackVote");
            return (Criteria) this;
        }

        public Criteria andJwBackVoteIn(List<Integer> values) {
            addCriterion("jw_back_vote in", values, "jwBackVote");
            return (Criteria) this;
        }

        public Criteria andJwBackVoteNotIn(List<Integer> values) {
            addCriterion("jw_back_vote not in", values, "jwBackVote");
            return (Criteria) this;
        }

        public Criteria andJwBackVoteBetween(Integer value1, Integer value2) {
            addCriterion("jw_back_vote between", value1, value2, "jwBackVote");
            return (Criteria) this;
        }

        public Criteria andJwBackVoteNotBetween(Integer value1, Integer value2) {
            addCriterion("jw_back_vote not between", value1, value2, "jwBackVote");
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