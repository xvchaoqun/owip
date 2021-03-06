package domain.pmd;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PmdPartyExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public PmdPartyExample() {
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

        public Criteria andMonthIdIsNull() {
            addCriterion("month_id is null");
            return (Criteria) this;
        }

        public Criteria andMonthIdIsNotNull() {
            addCriterion("month_id is not null");
            return (Criteria) this;
        }

        public Criteria andMonthIdEqualTo(Integer value) {
            addCriterion("month_id =", value, "monthId");
            return (Criteria) this;
        }

        public Criteria andMonthIdNotEqualTo(Integer value) {
            addCriterion("month_id <>", value, "monthId");
            return (Criteria) this;
        }

        public Criteria andMonthIdGreaterThan(Integer value) {
            addCriterion("month_id >", value, "monthId");
            return (Criteria) this;
        }

        public Criteria andMonthIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("month_id >=", value, "monthId");
            return (Criteria) this;
        }

        public Criteria andMonthIdLessThan(Integer value) {
            addCriterion("month_id <", value, "monthId");
            return (Criteria) this;
        }

        public Criteria andMonthIdLessThanOrEqualTo(Integer value) {
            addCriterion("month_id <=", value, "monthId");
            return (Criteria) this;
        }

        public Criteria andMonthIdIn(List<Integer> values) {
            addCriterion("month_id in", values, "monthId");
            return (Criteria) this;
        }

        public Criteria andMonthIdNotIn(List<Integer> values) {
            addCriterion("month_id not in", values, "monthId");
            return (Criteria) this;
        }

        public Criteria andMonthIdBetween(Integer value1, Integer value2) {
            addCriterion("month_id between", value1, value2, "monthId");
            return (Criteria) this;
        }

        public Criteria andMonthIdNotBetween(Integer value1, Integer value2) {
            addCriterion("month_id not between", value1, value2, "monthId");
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

        public Criteria andIsDirectBranchIsNull() {
            addCriterion("is_direct_branch is null");
            return (Criteria) this;
        }

        public Criteria andIsDirectBranchIsNotNull() {
            addCriterion("is_direct_branch is not null");
            return (Criteria) this;
        }

        public Criteria andIsDirectBranchEqualTo(Boolean value) {
            addCriterion("is_direct_branch =", value, "isDirectBranch");
            return (Criteria) this;
        }

        public Criteria andIsDirectBranchNotEqualTo(Boolean value) {
            addCriterion("is_direct_branch <>", value, "isDirectBranch");
            return (Criteria) this;
        }

        public Criteria andIsDirectBranchGreaterThan(Boolean value) {
            addCriterion("is_direct_branch >", value, "isDirectBranch");
            return (Criteria) this;
        }

        public Criteria andIsDirectBranchGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_direct_branch >=", value, "isDirectBranch");
            return (Criteria) this;
        }

        public Criteria andIsDirectBranchLessThan(Boolean value) {
            addCriterion("is_direct_branch <", value, "isDirectBranch");
            return (Criteria) this;
        }

        public Criteria andIsDirectBranchLessThanOrEqualTo(Boolean value) {
            addCriterion("is_direct_branch <=", value, "isDirectBranch");
            return (Criteria) this;
        }

        public Criteria andIsDirectBranchIn(List<Boolean> values) {
            addCriterion("is_direct_branch in", values, "isDirectBranch");
            return (Criteria) this;
        }

        public Criteria andIsDirectBranchNotIn(List<Boolean> values) {
            addCriterion("is_direct_branch not in", values, "isDirectBranch");
            return (Criteria) this;
        }

        public Criteria andIsDirectBranchBetween(Boolean value1, Boolean value2) {
            addCriterion("is_direct_branch between", value1, value2, "isDirectBranch");
            return (Criteria) this;
        }

        public Criteria andIsDirectBranchNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_direct_branch not between", value1, value2, "isDirectBranch");
            return (Criteria) this;
        }

        public Criteria andPartyNameIsNull() {
            addCriterion("party_name is null");
            return (Criteria) this;
        }

        public Criteria andPartyNameIsNotNull() {
            addCriterion("party_name is not null");
            return (Criteria) this;
        }

        public Criteria andPartyNameEqualTo(String value) {
            addCriterion("party_name =", value, "partyName");
            return (Criteria) this;
        }

        public Criteria andPartyNameNotEqualTo(String value) {
            addCriterion("party_name <>", value, "partyName");
            return (Criteria) this;
        }

        public Criteria andPartyNameGreaterThan(String value) {
            addCriterion("party_name >", value, "partyName");
            return (Criteria) this;
        }

        public Criteria andPartyNameGreaterThanOrEqualTo(String value) {
            addCriterion("party_name >=", value, "partyName");
            return (Criteria) this;
        }

        public Criteria andPartyNameLessThan(String value) {
            addCriterion("party_name <", value, "partyName");
            return (Criteria) this;
        }

        public Criteria andPartyNameLessThanOrEqualTo(String value) {
            addCriterion("party_name <=", value, "partyName");
            return (Criteria) this;
        }

        public Criteria andPartyNameLike(String value) {
            addCriterion("party_name like", value, "partyName");
            return (Criteria) this;
        }

        public Criteria andPartyNameNotLike(String value) {
            addCriterion("party_name not like", value, "partyName");
            return (Criteria) this;
        }

        public Criteria andPartyNameIn(List<String> values) {
            addCriterion("party_name in", values, "partyName");
            return (Criteria) this;
        }

        public Criteria andPartyNameNotIn(List<String> values) {
            addCriterion("party_name not in", values, "partyName");
            return (Criteria) this;
        }

        public Criteria andPartyNameBetween(String value1, String value2) {
            addCriterion("party_name between", value1, value2, "partyName");
            return (Criteria) this;
        }

        public Criteria andPartyNameNotBetween(String value1, String value2) {
            addCriterion("party_name not between", value1, value2, "partyName");
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

        public Criteria andPayStatusIsNull() {
            addCriterion("pay_status is null");
            return (Criteria) this;
        }

        public Criteria andPayStatusIsNotNull() {
            addCriterion("pay_status is not null");
            return (Criteria) this;
        }

        public Criteria andPayStatusEqualTo(Boolean value) {
            addCriterion("pay_status =", value, "payStatus");
            return (Criteria) this;
        }

        public Criteria andPayStatusNotEqualTo(Boolean value) {
            addCriterion("pay_status <>", value, "payStatus");
            return (Criteria) this;
        }

        public Criteria andPayStatusGreaterThan(Boolean value) {
            addCriterion("pay_status >", value, "payStatus");
            return (Criteria) this;
        }

        public Criteria andPayStatusGreaterThanOrEqualTo(Boolean value) {
            addCriterion("pay_status >=", value, "payStatus");
            return (Criteria) this;
        }

        public Criteria andPayStatusLessThan(Boolean value) {
            addCriterion("pay_status <", value, "payStatus");
            return (Criteria) this;
        }

        public Criteria andPayStatusLessThanOrEqualTo(Boolean value) {
            addCriterion("pay_status <=", value, "payStatus");
            return (Criteria) this;
        }

        public Criteria andPayStatusIn(List<Boolean> values) {
            addCriterion("pay_status in", values, "payStatus");
            return (Criteria) this;
        }

        public Criteria andPayStatusNotIn(List<Boolean> values) {
            addCriterion("pay_status not in", values, "payStatus");
            return (Criteria) this;
        }

        public Criteria andPayStatusBetween(Boolean value1, Boolean value2) {
            addCriterion("pay_status between", value1, value2, "payStatus");
            return (Criteria) this;
        }

        public Criteria andPayStatusNotBetween(Boolean value1, Boolean value2) {
            addCriterion("pay_status not between", value1, value2, "payStatus");
            return (Criteria) this;
        }

        public Criteria andPayTipIsNull() {
            addCriterion("pay_tip is null");
            return (Criteria) this;
        }

        public Criteria andPayTipIsNotNull() {
            addCriterion("pay_tip is not null");
            return (Criteria) this;
        }

        public Criteria andPayTipEqualTo(String value) {
            addCriterion("pay_tip =", value, "payTip");
            return (Criteria) this;
        }

        public Criteria andPayTipNotEqualTo(String value) {
            addCriterion("pay_tip <>", value, "payTip");
            return (Criteria) this;
        }

        public Criteria andPayTipGreaterThan(String value) {
            addCriterion("pay_tip >", value, "payTip");
            return (Criteria) this;
        }

        public Criteria andPayTipGreaterThanOrEqualTo(String value) {
            addCriterion("pay_tip >=", value, "payTip");
            return (Criteria) this;
        }

        public Criteria andPayTipLessThan(String value) {
            addCriterion("pay_tip <", value, "payTip");
            return (Criteria) this;
        }

        public Criteria andPayTipLessThanOrEqualTo(String value) {
            addCriterion("pay_tip <=", value, "payTip");
            return (Criteria) this;
        }

        public Criteria andPayTipLike(String value) {
            addCriterion("pay_tip like", value, "payTip");
            return (Criteria) this;
        }

        public Criteria andPayTipNotLike(String value) {
            addCriterion("pay_tip not like", value, "payTip");
            return (Criteria) this;
        }

        public Criteria andPayTipIn(List<String> values) {
            addCriterion("pay_tip in", values, "payTip");
            return (Criteria) this;
        }

        public Criteria andPayTipNotIn(List<String> values) {
            addCriterion("pay_tip not in", values, "payTip");
            return (Criteria) this;
        }

        public Criteria andPayTipBetween(String value1, String value2) {
            addCriterion("pay_tip between", value1, value2, "payTip");
            return (Criteria) this;
        }

        public Criteria andPayTipNotBetween(String value1, String value2) {
            addCriterion("pay_tip not between", value1, value2, "payTip");
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

        public Criteria andBranchCountIsNull() {
            addCriterion("branch_count is null");
            return (Criteria) this;
        }

        public Criteria andBranchCountIsNotNull() {
            addCriterion("branch_count is not null");
            return (Criteria) this;
        }

        public Criteria andBranchCountEqualTo(Integer value) {
            addCriterion("branch_count =", value, "branchCount");
            return (Criteria) this;
        }

        public Criteria andBranchCountNotEqualTo(Integer value) {
            addCriterion("branch_count <>", value, "branchCount");
            return (Criteria) this;
        }

        public Criteria andBranchCountGreaterThan(Integer value) {
            addCriterion("branch_count >", value, "branchCount");
            return (Criteria) this;
        }

        public Criteria andBranchCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("branch_count >=", value, "branchCount");
            return (Criteria) this;
        }

        public Criteria andBranchCountLessThan(Integer value) {
            addCriterion("branch_count <", value, "branchCount");
            return (Criteria) this;
        }

        public Criteria andBranchCountLessThanOrEqualTo(Integer value) {
            addCriterion("branch_count <=", value, "branchCount");
            return (Criteria) this;
        }

        public Criteria andBranchCountIn(List<Integer> values) {
            addCriterion("branch_count in", values, "branchCount");
            return (Criteria) this;
        }

        public Criteria andBranchCountNotIn(List<Integer> values) {
            addCriterion("branch_count not in", values, "branchCount");
            return (Criteria) this;
        }

        public Criteria andBranchCountBetween(Integer value1, Integer value2) {
            addCriterion("branch_count between", value1, value2, "branchCount");
            return (Criteria) this;
        }

        public Criteria andBranchCountNotBetween(Integer value1, Integer value2) {
            addCriterion("branch_count not between", value1, value2, "branchCount");
            return (Criteria) this;
        }

        public Criteria andHistoryDelayMemberCountIsNull() {
            addCriterion("history_delay_member_count is null");
            return (Criteria) this;
        }

        public Criteria andHistoryDelayMemberCountIsNotNull() {
            addCriterion("history_delay_member_count is not null");
            return (Criteria) this;
        }

        public Criteria andHistoryDelayMemberCountEqualTo(Integer value) {
            addCriterion("history_delay_member_count =", value, "historyDelayMemberCount");
            return (Criteria) this;
        }

        public Criteria andHistoryDelayMemberCountNotEqualTo(Integer value) {
            addCriterion("history_delay_member_count <>", value, "historyDelayMemberCount");
            return (Criteria) this;
        }

        public Criteria andHistoryDelayMemberCountGreaterThan(Integer value) {
            addCriterion("history_delay_member_count >", value, "historyDelayMemberCount");
            return (Criteria) this;
        }

        public Criteria andHistoryDelayMemberCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("history_delay_member_count >=", value, "historyDelayMemberCount");
            return (Criteria) this;
        }

        public Criteria andHistoryDelayMemberCountLessThan(Integer value) {
            addCriterion("history_delay_member_count <", value, "historyDelayMemberCount");
            return (Criteria) this;
        }

        public Criteria andHistoryDelayMemberCountLessThanOrEqualTo(Integer value) {
            addCriterion("history_delay_member_count <=", value, "historyDelayMemberCount");
            return (Criteria) this;
        }

        public Criteria andHistoryDelayMemberCountIn(List<Integer> values) {
            addCriterion("history_delay_member_count in", values, "historyDelayMemberCount");
            return (Criteria) this;
        }

        public Criteria andHistoryDelayMemberCountNotIn(List<Integer> values) {
            addCriterion("history_delay_member_count not in", values, "historyDelayMemberCount");
            return (Criteria) this;
        }

        public Criteria andHistoryDelayMemberCountBetween(Integer value1, Integer value2) {
            addCriterion("history_delay_member_count between", value1, value2, "historyDelayMemberCount");
            return (Criteria) this;
        }

        public Criteria andHistoryDelayMemberCountNotBetween(Integer value1, Integer value2) {
            addCriterion("history_delay_member_count not between", value1, value2, "historyDelayMemberCount");
            return (Criteria) this;
        }

        public Criteria andHistoryDelayPayIsNull() {
            addCriterion("history_delay_pay is null");
            return (Criteria) this;
        }

        public Criteria andHistoryDelayPayIsNotNull() {
            addCriterion("history_delay_pay is not null");
            return (Criteria) this;
        }

        public Criteria andHistoryDelayPayEqualTo(BigDecimal value) {
            addCriterion("history_delay_pay =", value, "historyDelayPay");
            return (Criteria) this;
        }

        public Criteria andHistoryDelayPayNotEqualTo(BigDecimal value) {
            addCriterion("history_delay_pay <>", value, "historyDelayPay");
            return (Criteria) this;
        }

        public Criteria andHistoryDelayPayGreaterThan(BigDecimal value) {
            addCriterion("history_delay_pay >", value, "historyDelayPay");
            return (Criteria) this;
        }

        public Criteria andHistoryDelayPayGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("history_delay_pay >=", value, "historyDelayPay");
            return (Criteria) this;
        }

        public Criteria andHistoryDelayPayLessThan(BigDecimal value) {
            addCriterion("history_delay_pay <", value, "historyDelayPay");
            return (Criteria) this;
        }

        public Criteria andHistoryDelayPayLessThanOrEqualTo(BigDecimal value) {
            addCriterion("history_delay_pay <=", value, "historyDelayPay");
            return (Criteria) this;
        }

        public Criteria andHistoryDelayPayIn(List<BigDecimal> values) {
            addCriterion("history_delay_pay in", values, "historyDelayPay");
            return (Criteria) this;
        }

        public Criteria andHistoryDelayPayNotIn(List<BigDecimal> values) {
            addCriterion("history_delay_pay not in", values, "historyDelayPay");
            return (Criteria) this;
        }

        public Criteria andHistoryDelayPayBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("history_delay_pay between", value1, value2, "historyDelayPay");
            return (Criteria) this;
        }

        public Criteria andHistoryDelayPayNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("history_delay_pay not between", value1, value2, "historyDelayPay");
            return (Criteria) this;
        }

        public Criteria andHasReportCountIsNull() {
            addCriterion("has_report_count is null");
            return (Criteria) this;
        }

        public Criteria andHasReportCountIsNotNull() {
            addCriterion("has_report_count is not null");
            return (Criteria) this;
        }

        public Criteria andHasReportCountEqualTo(Integer value) {
            addCriterion("has_report_count =", value, "hasReportCount");
            return (Criteria) this;
        }

        public Criteria andHasReportCountNotEqualTo(Integer value) {
            addCriterion("has_report_count <>", value, "hasReportCount");
            return (Criteria) this;
        }

        public Criteria andHasReportCountGreaterThan(Integer value) {
            addCriterion("has_report_count >", value, "hasReportCount");
            return (Criteria) this;
        }

        public Criteria andHasReportCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("has_report_count >=", value, "hasReportCount");
            return (Criteria) this;
        }

        public Criteria andHasReportCountLessThan(Integer value) {
            addCriterion("has_report_count <", value, "hasReportCount");
            return (Criteria) this;
        }

        public Criteria andHasReportCountLessThanOrEqualTo(Integer value) {
            addCriterion("has_report_count <=", value, "hasReportCount");
            return (Criteria) this;
        }

        public Criteria andHasReportCountIn(List<Integer> values) {
            addCriterion("has_report_count in", values, "hasReportCount");
            return (Criteria) this;
        }

        public Criteria andHasReportCountNotIn(List<Integer> values) {
            addCriterion("has_report_count not in", values, "hasReportCount");
            return (Criteria) this;
        }

        public Criteria andHasReportCountBetween(Integer value1, Integer value2) {
            addCriterion("has_report_count between", value1, value2, "hasReportCount");
            return (Criteria) this;
        }

        public Criteria andHasReportCountNotBetween(Integer value1, Integer value2) {
            addCriterion("has_report_count not between", value1, value2, "hasReportCount");
            return (Criteria) this;
        }

        public Criteria andMemberCountIsNull() {
            addCriterion("member_count is null");
            return (Criteria) this;
        }

        public Criteria andMemberCountIsNotNull() {
            addCriterion("member_count is not null");
            return (Criteria) this;
        }

        public Criteria andMemberCountEqualTo(Integer value) {
            addCriterion("member_count =", value, "memberCount");
            return (Criteria) this;
        }

        public Criteria andMemberCountNotEqualTo(Integer value) {
            addCriterion("member_count <>", value, "memberCount");
            return (Criteria) this;
        }

        public Criteria andMemberCountGreaterThan(Integer value) {
            addCriterion("member_count >", value, "memberCount");
            return (Criteria) this;
        }

        public Criteria andMemberCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("member_count >=", value, "memberCount");
            return (Criteria) this;
        }

        public Criteria andMemberCountLessThan(Integer value) {
            addCriterion("member_count <", value, "memberCount");
            return (Criteria) this;
        }

        public Criteria andMemberCountLessThanOrEqualTo(Integer value) {
            addCriterion("member_count <=", value, "memberCount");
            return (Criteria) this;
        }

        public Criteria andMemberCountIn(List<Integer> values) {
            addCriterion("member_count in", values, "memberCount");
            return (Criteria) this;
        }

        public Criteria andMemberCountNotIn(List<Integer> values) {
            addCriterion("member_count not in", values, "memberCount");
            return (Criteria) this;
        }

        public Criteria andMemberCountBetween(Integer value1, Integer value2) {
            addCriterion("member_count between", value1, value2, "memberCount");
            return (Criteria) this;
        }

        public Criteria andMemberCountNotBetween(Integer value1, Integer value2) {
            addCriterion("member_count not between", value1, value2, "memberCount");
            return (Criteria) this;
        }

        public Criteria andDuePayIsNull() {
            addCriterion("due_pay is null");
            return (Criteria) this;
        }

        public Criteria andDuePayIsNotNull() {
            addCriterion("due_pay is not null");
            return (Criteria) this;
        }

        public Criteria andDuePayEqualTo(BigDecimal value) {
            addCriterion("due_pay =", value, "duePay");
            return (Criteria) this;
        }

        public Criteria andDuePayNotEqualTo(BigDecimal value) {
            addCriterion("due_pay <>", value, "duePay");
            return (Criteria) this;
        }

        public Criteria andDuePayGreaterThan(BigDecimal value) {
            addCriterion("due_pay >", value, "duePay");
            return (Criteria) this;
        }

        public Criteria andDuePayGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("due_pay >=", value, "duePay");
            return (Criteria) this;
        }

        public Criteria andDuePayLessThan(BigDecimal value) {
            addCriterion("due_pay <", value, "duePay");
            return (Criteria) this;
        }

        public Criteria andDuePayLessThanOrEqualTo(BigDecimal value) {
            addCriterion("due_pay <=", value, "duePay");
            return (Criteria) this;
        }

        public Criteria andDuePayIn(List<BigDecimal> values) {
            addCriterion("due_pay in", values, "duePay");
            return (Criteria) this;
        }

        public Criteria andDuePayNotIn(List<BigDecimal> values) {
            addCriterion("due_pay not in", values, "duePay");
            return (Criteria) this;
        }

        public Criteria andDuePayBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("due_pay between", value1, value2, "duePay");
            return (Criteria) this;
        }

        public Criteria andDuePayNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("due_pay not between", value1, value2, "duePay");
            return (Criteria) this;
        }

        public Criteria andFinishMemberCountIsNull() {
            addCriterion("finish_member_count is null");
            return (Criteria) this;
        }

        public Criteria andFinishMemberCountIsNotNull() {
            addCriterion("finish_member_count is not null");
            return (Criteria) this;
        }

        public Criteria andFinishMemberCountEqualTo(Integer value) {
            addCriterion("finish_member_count =", value, "finishMemberCount");
            return (Criteria) this;
        }

        public Criteria andFinishMemberCountNotEqualTo(Integer value) {
            addCriterion("finish_member_count <>", value, "finishMemberCount");
            return (Criteria) this;
        }

        public Criteria andFinishMemberCountGreaterThan(Integer value) {
            addCriterion("finish_member_count >", value, "finishMemberCount");
            return (Criteria) this;
        }

        public Criteria andFinishMemberCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("finish_member_count >=", value, "finishMemberCount");
            return (Criteria) this;
        }

        public Criteria andFinishMemberCountLessThan(Integer value) {
            addCriterion("finish_member_count <", value, "finishMemberCount");
            return (Criteria) this;
        }

        public Criteria andFinishMemberCountLessThanOrEqualTo(Integer value) {
            addCriterion("finish_member_count <=", value, "finishMemberCount");
            return (Criteria) this;
        }

        public Criteria andFinishMemberCountIn(List<Integer> values) {
            addCriterion("finish_member_count in", values, "finishMemberCount");
            return (Criteria) this;
        }

        public Criteria andFinishMemberCountNotIn(List<Integer> values) {
            addCriterion("finish_member_count not in", values, "finishMemberCount");
            return (Criteria) this;
        }

        public Criteria andFinishMemberCountBetween(Integer value1, Integer value2) {
            addCriterion("finish_member_count between", value1, value2, "finishMemberCount");
            return (Criteria) this;
        }

        public Criteria andFinishMemberCountNotBetween(Integer value1, Integer value2) {
            addCriterion("finish_member_count not between", value1, value2, "finishMemberCount");
            return (Criteria) this;
        }

        public Criteria andOnlineFinishMemberCountIsNull() {
            addCriterion("online_finish_member_count is null");
            return (Criteria) this;
        }

        public Criteria andOnlineFinishMemberCountIsNotNull() {
            addCriterion("online_finish_member_count is not null");
            return (Criteria) this;
        }

        public Criteria andOnlineFinishMemberCountEqualTo(Integer value) {
            addCriterion("online_finish_member_count =", value, "onlineFinishMemberCount");
            return (Criteria) this;
        }

        public Criteria andOnlineFinishMemberCountNotEqualTo(Integer value) {
            addCriterion("online_finish_member_count <>", value, "onlineFinishMemberCount");
            return (Criteria) this;
        }

        public Criteria andOnlineFinishMemberCountGreaterThan(Integer value) {
            addCriterion("online_finish_member_count >", value, "onlineFinishMemberCount");
            return (Criteria) this;
        }

        public Criteria andOnlineFinishMemberCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("online_finish_member_count >=", value, "onlineFinishMemberCount");
            return (Criteria) this;
        }

        public Criteria andOnlineFinishMemberCountLessThan(Integer value) {
            addCriterion("online_finish_member_count <", value, "onlineFinishMemberCount");
            return (Criteria) this;
        }

        public Criteria andOnlineFinishMemberCountLessThanOrEqualTo(Integer value) {
            addCriterion("online_finish_member_count <=", value, "onlineFinishMemberCount");
            return (Criteria) this;
        }

        public Criteria andOnlineFinishMemberCountIn(List<Integer> values) {
            addCriterion("online_finish_member_count in", values, "onlineFinishMemberCount");
            return (Criteria) this;
        }

        public Criteria andOnlineFinishMemberCountNotIn(List<Integer> values) {
            addCriterion("online_finish_member_count not in", values, "onlineFinishMemberCount");
            return (Criteria) this;
        }

        public Criteria andOnlineFinishMemberCountBetween(Integer value1, Integer value2) {
            addCriterion("online_finish_member_count between", value1, value2, "onlineFinishMemberCount");
            return (Criteria) this;
        }

        public Criteria andOnlineFinishMemberCountNotBetween(Integer value1, Integer value2) {
            addCriterion("online_finish_member_count not between", value1, value2, "onlineFinishMemberCount");
            return (Criteria) this;
        }

        public Criteria andRealPayIsNull() {
            addCriterion("real_pay is null");
            return (Criteria) this;
        }

        public Criteria andRealPayIsNotNull() {
            addCriterion("real_pay is not null");
            return (Criteria) this;
        }

        public Criteria andRealPayEqualTo(BigDecimal value) {
            addCriterion("real_pay =", value, "realPay");
            return (Criteria) this;
        }

        public Criteria andRealPayNotEqualTo(BigDecimal value) {
            addCriterion("real_pay <>", value, "realPay");
            return (Criteria) this;
        }

        public Criteria andRealPayGreaterThan(BigDecimal value) {
            addCriterion("real_pay >", value, "realPay");
            return (Criteria) this;
        }

        public Criteria andRealPayGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("real_pay >=", value, "realPay");
            return (Criteria) this;
        }

        public Criteria andRealPayLessThan(BigDecimal value) {
            addCriterion("real_pay <", value, "realPay");
            return (Criteria) this;
        }

        public Criteria andRealPayLessThanOrEqualTo(BigDecimal value) {
            addCriterion("real_pay <=", value, "realPay");
            return (Criteria) this;
        }

        public Criteria andRealPayIn(List<BigDecimal> values) {
            addCriterion("real_pay in", values, "realPay");
            return (Criteria) this;
        }

        public Criteria andRealPayNotIn(List<BigDecimal> values) {
            addCriterion("real_pay not in", values, "realPay");
            return (Criteria) this;
        }

        public Criteria andRealPayBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("real_pay between", value1, value2, "realPay");
            return (Criteria) this;
        }

        public Criteria andRealPayNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("real_pay not between", value1, value2, "realPay");
            return (Criteria) this;
        }

        public Criteria andOnlineRealPayIsNull() {
            addCriterion("online_real_pay is null");
            return (Criteria) this;
        }

        public Criteria andOnlineRealPayIsNotNull() {
            addCriterion("online_real_pay is not null");
            return (Criteria) this;
        }

        public Criteria andOnlineRealPayEqualTo(BigDecimal value) {
            addCriterion("online_real_pay =", value, "onlineRealPay");
            return (Criteria) this;
        }

        public Criteria andOnlineRealPayNotEqualTo(BigDecimal value) {
            addCriterion("online_real_pay <>", value, "onlineRealPay");
            return (Criteria) this;
        }

        public Criteria andOnlineRealPayGreaterThan(BigDecimal value) {
            addCriterion("online_real_pay >", value, "onlineRealPay");
            return (Criteria) this;
        }

        public Criteria andOnlineRealPayGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("online_real_pay >=", value, "onlineRealPay");
            return (Criteria) this;
        }

        public Criteria andOnlineRealPayLessThan(BigDecimal value) {
            addCriterion("online_real_pay <", value, "onlineRealPay");
            return (Criteria) this;
        }

        public Criteria andOnlineRealPayLessThanOrEqualTo(BigDecimal value) {
            addCriterion("online_real_pay <=", value, "onlineRealPay");
            return (Criteria) this;
        }

        public Criteria andOnlineRealPayIn(List<BigDecimal> values) {
            addCriterion("online_real_pay in", values, "onlineRealPay");
            return (Criteria) this;
        }

        public Criteria andOnlineRealPayNotIn(List<BigDecimal> values) {
            addCriterion("online_real_pay not in", values, "onlineRealPay");
            return (Criteria) this;
        }

        public Criteria andOnlineRealPayBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("online_real_pay between", value1, value2, "onlineRealPay");
            return (Criteria) this;
        }

        public Criteria andOnlineRealPayNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("online_real_pay not between", value1, value2, "onlineRealPay");
            return (Criteria) this;
        }

        public Criteria andCashRealPayIsNull() {
            addCriterion("cash_real_pay is null");
            return (Criteria) this;
        }

        public Criteria andCashRealPayIsNotNull() {
            addCriterion("cash_real_pay is not null");
            return (Criteria) this;
        }

        public Criteria andCashRealPayEqualTo(BigDecimal value) {
            addCriterion("cash_real_pay =", value, "cashRealPay");
            return (Criteria) this;
        }

        public Criteria andCashRealPayNotEqualTo(BigDecimal value) {
            addCriterion("cash_real_pay <>", value, "cashRealPay");
            return (Criteria) this;
        }

        public Criteria andCashRealPayGreaterThan(BigDecimal value) {
            addCriterion("cash_real_pay >", value, "cashRealPay");
            return (Criteria) this;
        }

        public Criteria andCashRealPayGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("cash_real_pay >=", value, "cashRealPay");
            return (Criteria) this;
        }

        public Criteria andCashRealPayLessThan(BigDecimal value) {
            addCriterion("cash_real_pay <", value, "cashRealPay");
            return (Criteria) this;
        }

        public Criteria andCashRealPayLessThanOrEqualTo(BigDecimal value) {
            addCriterion("cash_real_pay <=", value, "cashRealPay");
            return (Criteria) this;
        }

        public Criteria andCashRealPayIn(List<BigDecimal> values) {
            addCriterion("cash_real_pay in", values, "cashRealPay");
            return (Criteria) this;
        }

        public Criteria andCashRealPayNotIn(List<BigDecimal> values) {
            addCriterion("cash_real_pay not in", values, "cashRealPay");
            return (Criteria) this;
        }

        public Criteria andCashRealPayBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("cash_real_pay between", value1, value2, "cashRealPay");
            return (Criteria) this;
        }

        public Criteria andCashRealPayNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("cash_real_pay not between", value1, value2, "cashRealPay");
            return (Criteria) this;
        }

        public Criteria andDelayPayIsNull() {
            addCriterion("delay_pay is null");
            return (Criteria) this;
        }

        public Criteria andDelayPayIsNotNull() {
            addCriterion("delay_pay is not null");
            return (Criteria) this;
        }

        public Criteria andDelayPayEqualTo(BigDecimal value) {
            addCriterion("delay_pay =", value, "delayPay");
            return (Criteria) this;
        }

        public Criteria andDelayPayNotEqualTo(BigDecimal value) {
            addCriterion("delay_pay <>", value, "delayPay");
            return (Criteria) this;
        }

        public Criteria andDelayPayGreaterThan(BigDecimal value) {
            addCriterion("delay_pay >", value, "delayPay");
            return (Criteria) this;
        }

        public Criteria andDelayPayGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("delay_pay >=", value, "delayPay");
            return (Criteria) this;
        }

        public Criteria andDelayPayLessThan(BigDecimal value) {
            addCriterion("delay_pay <", value, "delayPay");
            return (Criteria) this;
        }

        public Criteria andDelayPayLessThanOrEqualTo(BigDecimal value) {
            addCriterion("delay_pay <=", value, "delayPay");
            return (Criteria) this;
        }

        public Criteria andDelayPayIn(List<BigDecimal> values) {
            addCriterion("delay_pay in", values, "delayPay");
            return (Criteria) this;
        }

        public Criteria andDelayPayNotIn(List<BigDecimal> values) {
            addCriterion("delay_pay not in", values, "delayPay");
            return (Criteria) this;
        }

        public Criteria andDelayPayBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("delay_pay between", value1, value2, "delayPay");
            return (Criteria) this;
        }

        public Criteria andDelayPayNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("delay_pay not between", value1, value2, "delayPay");
            return (Criteria) this;
        }

        public Criteria andDelayMemberCountIsNull() {
            addCriterion("delay_member_count is null");
            return (Criteria) this;
        }

        public Criteria andDelayMemberCountIsNotNull() {
            addCriterion("delay_member_count is not null");
            return (Criteria) this;
        }

        public Criteria andDelayMemberCountEqualTo(Integer value) {
            addCriterion("delay_member_count =", value, "delayMemberCount");
            return (Criteria) this;
        }

        public Criteria andDelayMemberCountNotEqualTo(Integer value) {
            addCriterion("delay_member_count <>", value, "delayMemberCount");
            return (Criteria) this;
        }

        public Criteria andDelayMemberCountGreaterThan(Integer value) {
            addCriterion("delay_member_count >", value, "delayMemberCount");
            return (Criteria) this;
        }

        public Criteria andDelayMemberCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("delay_member_count >=", value, "delayMemberCount");
            return (Criteria) this;
        }

        public Criteria andDelayMemberCountLessThan(Integer value) {
            addCriterion("delay_member_count <", value, "delayMemberCount");
            return (Criteria) this;
        }

        public Criteria andDelayMemberCountLessThanOrEqualTo(Integer value) {
            addCriterion("delay_member_count <=", value, "delayMemberCount");
            return (Criteria) this;
        }

        public Criteria andDelayMemberCountIn(List<Integer> values) {
            addCriterion("delay_member_count in", values, "delayMemberCount");
            return (Criteria) this;
        }

        public Criteria andDelayMemberCountNotIn(List<Integer> values) {
            addCriterion("delay_member_count not in", values, "delayMemberCount");
            return (Criteria) this;
        }

        public Criteria andDelayMemberCountBetween(Integer value1, Integer value2) {
            addCriterion("delay_member_count between", value1, value2, "delayMemberCount");
            return (Criteria) this;
        }

        public Criteria andDelayMemberCountNotBetween(Integer value1, Integer value2) {
            addCriterion("delay_member_count not between", value1, value2, "delayMemberCount");
            return (Criteria) this;
        }

        public Criteria andRealDelayMemberCountIsNull() {
            addCriterion("real_delay_member_count is null");
            return (Criteria) this;
        }

        public Criteria andRealDelayMemberCountIsNotNull() {
            addCriterion("real_delay_member_count is not null");
            return (Criteria) this;
        }

        public Criteria andRealDelayMemberCountEqualTo(Integer value) {
            addCriterion("real_delay_member_count =", value, "realDelayMemberCount");
            return (Criteria) this;
        }

        public Criteria andRealDelayMemberCountNotEqualTo(Integer value) {
            addCriterion("real_delay_member_count <>", value, "realDelayMemberCount");
            return (Criteria) this;
        }

        public Criteria andRealDelayMemberCountGreaterThan(Integer value) {
            addCriterion("real_delay_member_count >", value, "realDelayMemberCount");
            return (Criteria) this;
        }

        public Criteria andRealDelayMemberCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("real_delay_member_count >=", value, "realDelayMemberCount");
            return (Criteria) this;
        }

        public Criteria andRealDelayMemberCountLessThan(Integer value) {
            addCriterion("real_delay_member_count <", value, "realDelayMemberCount");
            return (Criteria) this;
        }

        public Criteria andRealDelayMemberCountLessThanOrEqualTo(Integer value) {
            addCriterion("real_delay_member_count <=", value, "realDelayMemberCount");
            return (Criteria) this;
        }

        public Criteria andRealDelayMemberCountIn(List<Integer> values) {
            addCriterion("real_delay_member_count in", values, "realDelayMemberCount");
            return (Criteria) this;
        }

        public Criteria andRealDelayMemberCountNotIn(List<Integer> values) {
            addCriterion("real_delay_member_count not in", values, "realDelayMemberCount");
            return (Criteria) this;
        }

        public Criteria andRealDelayMemberCountBetween(Integer value1, Integer value2) {
            addCriterion("real_delay_member_count between", value1, value2, "realDelayMemberCount");
            return (Criteria) this;
        }

        public Criteria andRealDelayMemberCountNotBetween(Integer value1, Integer value2) {
            addCriterion("real_delay_member_count not between", value1, value2, "realDelayMemberCount");
            return (Criteria) this;
        }

        public Criteria andRealDelayPayIsNull() {
            addCriterion("real_delay_pay is null");
            return (Criteria) this;
        }

        public Criteria andRealDelayPayIsNotNull() {
            addCriterion("real_delay_pay is not null");
            return (Criteria) this;
        }

        public Criteria andRealDelayPayEqualTo(BigDecimal value) {
            addCriterion("real_delay_pay =", value, "realDelayPay");
            return (Criteria) this;
        }

        public Criteria andRealDelayPayNotEqualTo(BigDecimal value) {
            addCriterion("real_delay_pay <>", value, "realDelayPay");
            return (Criteria) this;
        }

        public Criteria andRealDelayPayGreaterThan(BigDecimal value) {
            addCriterion("real_delay_pay >", value, "realDelayPay");
            return (Criteria) this;
        }

        public Criteria andRealDelayPayGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("real_delay_pay >=", value, "realDelayPay");
            return (Criteria) this;
        }

        public Criteria andRealDelayPayLessThan(BigDecimal value) {
            addCriterion("real_delay_pay <", value, "realDelayPay");
            return (Criteria) this;
        }

        public Criteria andRealDelayPayLessThanOrEqualTo(BigDecimal value) {
            addCriterion("real_delay_pay <=", value, "realDelayPay");
            return (Criteria) this;
        }

        public Criteria andRealDelayPayIn(List<BigDecimal> values) {
            addCriterion("real_delay_pay in", values, "realDelayPay");
            return (Criteria) this;
        }

        public Criteria andRealDelayPayNotIn(List<BigDecimal> values) {
            addCriterion("real_delay_pay not in", values, "realDelayPay");
            return (Criteria) this;
        }

        public Criteria andRealDelayPayBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("real_delay_pay between", value1, value2, "realDelayPay");
            return (Criteria) this;
        }

        public Criteria andRealDelayPayNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("real_delay_pay not between", value1, value2, "realDelayPay");
            return (Criteria) this;
        }

        public Criteria andOnlineRealDelayPayIsNull() {
            addCriterion("online_real_delay_pay is null");
            return (Criteria) this;
        }

        public Criteria andOnlineRealDelayPayIsNotNull() {
            addCriterion("online_real_delay_pay is not null");
            return (Criteria) this;
        }

        public Criteria andOnlineRealDelayPayEqualTo(BigDecimal value) {
            addCriterion("online_real_delay_pay =", value, "onlineRealDelayPay");
            return (Criteria) this;
        }

        public Criteria andOnlineRealDelayPayNotEqualTo(BigDecimal value) {
            addCriterion("online_real_delay_pay <>", value, "onlineRealDelayPay");
            return (Criteria) this;
        }

        public Criteria andOnlineRealDelayPayGreaterThan(BigDecimal value) {
            addCriterion("online_real_delay_pay >", value, "onlineRealDelayPay");
            return (Criteria) this;
        }

        public Criteria andOnlineRealDelayPayGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("online_real_delay_pay >=", value, "onlineRealDelayPay");
            return (Criteria) this;
        }

        public Criteria andOnlineRealDelayPayLessThan(BigDecimal value) {
            addCriterion("online_real_delay_pay <", value, "onlineRealDelayPay");
            return (Criteria) this;
        }

        public Criteria andOnlineRealDelayPayLessThanOrEqualTo(BigDecimal value) {
            addCriterion("online_real_delay_pay <=", value, "onlineRealDelayPay");
            return (Criteria) this;
        }

        public Criteria andOnlineRealDelayPayIn(List<BigDecimal> values) {
            addCriterion("online_real_delay_pay in", values, "onlineRealDelayPay");
            return (Criteria) this;
        }

        public Criteria andOnlineRealDelayPayNotIn(List<BigDecimal> values) {
            addCriterion("online_real_delay_pay not in", values, "onlineRealDelayPay");
            return (Criteria) this;
        }

        public Criteria andOnlineRealDelayPayBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("online_real_delay_pay between", value1, value2, "onlineRealDelayPay");
            return (Criteria) this;
        }

        public Criteria andOnlineRealDelayPayNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("online_real_delay_pay not between", value1, value2, "onlineRealDelayPay");
            return (Criteria) this;
        }

        public Criteria andCashRealDelayPayIsNull() {
            addCriterion("cash_real_delay_pay is null");
            return (Criteria) this;
        }

        public Criteria andCashRealDelayPayIsNotNull() {
            addCriterion("cash_real_delay_pay is not null");
            return (Criteria) this;
        }

        public Criteria andCashRealDelayPayEqualTo(BigDecimal value) {
            addCriterion("cash_real_delay_pay =", value, "cashRealDelayPay");
            return (Criteria) this;
        }

        public Criteria andCashRealDelayPayNotEqualTo(BigDecimal value) {
            addCriterion("cash_real_delay_pay <>", value, "cashRealDelayPay");
            return (Criteria) this;
        }

        public Criteria andCashRealDelayPayGreaterThan(BigDecimal value) {
            addCriterion("cash_real_delay_pay >", value, "cashRealDelayPay");
            return (Criteria) this;
        }

        public Criteria andCashRealDelayPayGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("cash_real_delay_pay >=", value, "cashRealDelayPay");
            return (Criteria) this;
        }

        public Criteria andCashRealDelayPayLessThan(BigDecimal value) {
            addCriterion("cash_real_delay_pay <", value, "cashRealDelayPay");
            return (Criteria) this;
        }

        public Criteria andCashRealDelayPayLessThanOrEqualTo(BigDecimal value) {
            addCriterion("cash_real_delay_pay <=", value, "cashRealDelayPay");
            return (Criteria) this;
        }

        public Criteria andCashRealDelayPayIn(List<BigDecimal> values) {
            addCriterion("cash_real_delay_pay in", values, "cashRealDelayPay");
            return (Criteria) this;
        }

        public Criteria andCashRealDelayPayNotIn(List<BigDecimal> values) {
            addCriterion("cash_real_delay_pay not in", values, "cashRealDelayPay");
            return (Criteria) this;
        }

        public Criteria andCashRealDelayPayBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("cash_real_delay_pay between", value1, value2, "cashRealDelayPay");
            return (Criteria) this;
        }

        public Criteria andCashRealDelayPayNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("cash_real_delay_pay not between", value1, value2, "cashRealDelayPay");
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

        public Criteria andReportIpIsNull() {
            addCriterion("report_ip is null");
            return (Criteria) this;
        }

        public Criteria andReportIpIsNotNull() {
            addCriterion("report_ip is not null");
            return (Criteria) this;
        }

        public Criteria andReportIpEqualTo(String value) {
            addCriterion("report_ip =", value, "reportIp");
            return (Criteria) this;
        }

        public Criteria andReportIpNotEqualTo(String value) {
            addCriterion("report_ip <>", value, "reportIp");
            return (Criteria) this;
        }

        public Criteria andReportIpGreaterThan(String value) {
            addCriterion("report_ip >", value, "reportIp");
            return (Criteria) this;
        }

        public Criteria andReportIpGreaterThanOrEqualTo(String value) {
            addCriterion("report_ip >=", value, "reportIp");
            return (Criteria) this;
        }

        public Criteria andReportIpLessThan(String value) {
            addCriterion("report_ip <", value, "reportIp");
            return (Criteria) this;
        }

        public Criteria andReportIpLessThanOrEqualTo(String value) {
            addCriterion("report_ip <=", value, "reportIp");
            return (Criteria) this;
        }

        public Criteria andReportIpLike(String value) {
            addCriterion("report_ip like", value, "reportIp");
            return (Criteria) this;
        }

        public Criteria andReportIpNotLike(String value) {
            addCriterion("report_ip not like", value, "reportIp");
            return (Criteria) this;
        }

        public Criteria andReportIpIn(List<String> values) {
            addCriterion("report_ip in", values, "reportIp");
            return (Criteria) this;
        }

        public Criteria andReportIpNotIn(List<String> values) {
            addCriterion("report_ip not in", values, "reportIp");
            return (Criteria) this;
        }

        public Criteria andReportIpBetween(String value1, String value2) {
            addCriterion("report_ip between", value1, value2, "reportIp");
            return (Criteria) this;
        }

        public Criteria andReportIpNotBetween(String value1, String value2) {
            addCriterion("report_ip not between", value1, value2, "reportIp");
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