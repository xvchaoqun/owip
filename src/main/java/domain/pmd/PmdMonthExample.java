package domain.pmd;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class PmdMonthExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public PmdMonthExample() {
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

        public Criteria andPayMonthIsNull() {
            addCriterion("pay_month is null");
            return (Criteria) this;
        }

        public Criteria andPayMonthIsNotNull() {
            addCriterion("pay_month is not null");
            return (Criteria) this;
        }

        public Criteria andPayMonthEqualTo(Date value) {
            addCriterionForJDBCDate("pay_month =", value, "payMonth");
            return (Criteria) this;
        }

        public Criteria andPayMonthNotEqualTo(Date value) {
            addCriterionForJDBCDate("pay_month <>", value, "payMonth");
            return (Criteria) this;
        }

        public Criteria andPayMonthGreaterThan(Date value) {
            addCriterionForJDBCDate("pay_month >", value, "payMonth");
            return (Criteria) this;
        }

        public Criteria andPayMonthGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("pay_month >=", value, "payMonth");
            return (Criteria) this;
        }

        public Criteria andPayMonthLessThan(Date value) {
            addCriterionForJDBCDate("pay_month <", value, "payMonth");
            return (Criteria) this;
        }

        public Criteria andPayMonthLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("pay_month <=", value, "payMonth");
            return (Criteria) this;
        }

        public Criteria andPayMonthIn(List<Date> values) {
            addCriterionForJDBCDate("pay_month in", values, "payMonth");
            return (Criteria) this;
        }

        public Criteria andPayMonthNotIn(List<Date> values) {
            addCriterionForJDBCDate("pay_month not in", values, "payMonth");
            return (Criteria) this;
        }

        public Criteria andPayMonthBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("pay_month between", value1, value2, "payMonth");
            return (Criteria) this;
        }

        public Criteria andPayMonthNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("pay_month not between", value1, value2, "payMonth");
            return (Criteria) this;
        }

        public Criteria andStartTimeIsNull() {
            addCriterion("start_time is null");
            return (Criteria) this;
        }

        public Criteria andStartTimeIsNotNull() {
            addCriterion("start_time is not null");
            return (Criteria) this;
        }

        public Criteria andStartTimeEqualTo(Date value) {
            addCriterion("start_time =", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeNotEqualTo(Date value) {
            addCriterion("start_time <>", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeGreaterThan(Date value) {
            addCriterion("start_time >", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("start_time >=", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeLessThan(Date value) {
            addCriterion("start_time <", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeLessThanOrEqualTo(Date value) {
            addCriterion("start_time <=", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeIn(List<Date> values) {
            addCriterion("start_time in", values, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeNotIn(List<Date> values) {
            addCriterion("start_time not in", values, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeBetween(Date value1, Date value2) {
            addCriterion("start_time between", value1, value2, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeNotBetween(Date value1, Date value2) {
            addCriterion("start_time not between", value1, value2, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartUserIdIsNull() {
            addCriterion("start_user_id is null");
            return (Criteria) this;
        }

        public Criteria andStartUserIdIsNotNull() {
            addCriterion("start_user_id is not null");
            return (Criteria) this;
        }

        public Criteria andStartUserIdEqualTo(Integer value) {
            addCriterion("start_user_id =", value, "startUserId");
            return (Criteria) this;
        }

        public Criteria andStartUserIdNotEqualTo(Integer value) {
            addCriterion("start_user_id <>", value, "startUserId");
            return (Criteria) this;
        }

        public Criteria andStartUserIdGreaterThan(Integer value) {
            addCriterion("start_user_id >", value, "startUserId");
            return (Criteria) this;
        }

        public Criteria andStartUserIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("start_user_id >=", value, "startUserId");
            return (Criteria) this;
        }

        public Criteria andStartUserIdLessThan(Integer value) {
            addCriterion("start_user_id <", value, "startUserId");
            return (Criteria) this;
        }

        public Criteria andStartUserIdLessThanOrEqualTo(Integer value) {
            addCriterion("start_user_id <=", value, "startUserId");
            return (Criteria) this;
        }

        public Criteria andStartUserIdIn(List<Integer> values) {
            addCriterion("start_user_id in", values, "startUserId");
            return (Criteria) this;
        }

        public Criteria andStartUserIdNotIn(List<Integer> values) {
            addCriterion("start_user_id not in", values, "startUserId");
            return (Criteria) this;
        }

        public Criteria andStartUserIdBetween(Integer value1, Integer value2) {
            addCriterion("start_user_id between", value1, value2, "startUserId");
            return (Criteria) this;
        }

        public Criteria andStartUserIdNotBetween(Integer value1, Integer value2) {
            addCriterion("start_user_id not between", value1, value2, "startUserId");
            return (Criteria) this;
        }

        public Criteria andEndTimeIsNull() {
            addCriterion("end_time is null");
            return (Criteria) this;
        }

        public Criteria andEndTimeIsNotNull() {
            addCriterion("end_time is not null");
            return (Criteria) this;
        }

        public Criteria andEndTimeEqualTo(Date value) {
            addCriterion("end_time =", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeNotEqualTo(Date value) {
            addCriterion("end_time <>", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeGreaterThan(Date value) {
            addCriterion("end_time >", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("end_time >=", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeLessThan(Date value) {
            addCriterion("end_time <", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeLessThanOrEqualTo(Date value) {
            addCriterion("end_time <=", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeIn(List<Date> values) {
            addCriterion("end_time in", values, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeNotIn(List<Date> values) {
            addCriterion("end_time not in", values, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeBetween(Date value1, Date value2) {
            addCriterion("end_time between", value1, value2, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeNotBetween(Date value1, Date value2) {
            addCriterion("end_time not between", value1, value2, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndUserIdIsNull() {
            addCriterion("end_user_id is null");
            return (Criteria) this;
        }

        public Criteria andEndUserIdIsNotNull() {
            addCriterion("end_user_id is not null");
            return (Criteria) this;
        }

        public Criteria andEndUserIdEqualTo(Integer value) {
            addCriterion("end_user_id =", value, "endUserId");
            return (Criteria) this;
        }

        public Criteria andEndUserIdNotEqualTo(Integer value) {
            addCriterion("end_user_id <>", value, "endUserId");
            return (Criteria) this;
        }

        public Criteria andEndUserIdGreaterThan(Integer value) {
            addCriterion("end_user_id >", value, "endUserId");
            return (Criteria) this;
        }

        public Criteria andEndUserIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("end_user_id >=", value, "endUserId");
            return (Criteria) this;
        }

        public Criteria andEndUserIdLessThan(Integer value) {
            addCriterion("end_user_id <", value, "endUserId");
            return (Criteria) this;
        }

        public Criteria andEndUserIdLessThanOrEqualTo(Integer value) {
            addCriterion("end_user_id <=", value, "endUserId");
            return (Criteria) this;
        }

        public Criteria andEndUserIdIn(List<Integer> values) {
            addCriterion("end_user_id in", values, "endUserId");
            return (Criteria) this;
        }

        public Criteria andEndUserIdNotIn(List<Integer> values) {
            addCriterion("end_user_id not in", values, "endUserId");
            return (Criteria) this;
        }

        public Criteria andEndUserIdBetween(Integer value1, Integer value2) {
            addCriterion("end_user_id between", value1, value2, "endUserId");
            return (Criteria) this;
        }

        public Criteria andEndUserIdNotBetween(Integer value1, Integer value2) {
            addCriterion("end_user_id not between", value1, value2, "endUserId");
            return (Criteria) this;
        }

        public Criteria andPartyCountIsNull() {
            addCriterion("party_count is null");
            return (Criteria) this;
        }

        public Criteria andPartyCountIsNotNull() {
            addCriterion("party_count is not null");
            return (Criteria) this;
        }

        public Criteria andPartyCountEqualTo(Integer value) {
            addCriterion("party_count =", value, "partyCount");
            return (Criteria) this;
        }

        public Criteria andPartyCountNotEqualTo(Integer value) {
            addCriterion("party_count <>", value, "partyCount");
            return (Criteria) this;
        }

        public Criteria andPartyCountGreaterThan(Integer value) {
            addCriterion("party_count >", value, "partyCount");
            return (Criteria) this;
        }

        public Criteria andPartyCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("party_count >=", value, "partyCount");
            return (Criteria) this;
        }

        public Criteria andPartyCountLessThan(Integer value) {
            addCriterion("party_count <", value, "partyCount");
            return (Criteria) this;
        }

        public Criteria andPartyCountLessThanOrEqualTo(Integer value) {
            addCriterion("party_count <=", value, "partyCount");
            return (Criteria) this;
        }

        public Criteria andPartyCountIn(List<Integer> values) {
            addCriterion("party_count in", values, "partyCount");
            return (Criteria) this;
        }

        public Criteria andPartyCountNotIn(List<Integer> values) {
            addCriterion("party_count not in", values, "partyCount");
            return (Criteria) this;
        }

        public Criteria andPartyCountBetween(Integer value1, Integer value2) {
            addCriterion("party_count between", value1, value2, "partyCount");
            return (Criteria) this;
        }

        public Criteria andPartyCountNotBetween(Integer value1, Integer value2) {
            addCriterion("party_count not between", value1, value2, "partyCount");
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

        public Criteria andCreateUserIdIsNull() {
            addCriterion("create_user_id is null");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdIsNotNull() {
            addCriterion("create_user_id is not null");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdEqualTo(Integer value) {
            addCriterion("create_user_id =", value, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdNotEqualTo(Integer value) {
            addCriterion("create_user_id <>", value, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdGreaterThan(Integer value) {
            addCriterion("create_user_id >", value, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("create_user_id >=", value, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdLessThan(Integer value) {
            addCriterion("create_user_id <", value, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdLessThanOrEqualTo(Integer value) {
            addCriterion("create_user_id <=", value, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdIn(List<Integer> values) {
            addCriterion("create_user_id in", values, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdNotIn(List<Integer> values) {
            addCriterion("create_user_id not in", values, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdBetween(Integer value1, Integer value2) {
            addCriterion("create_user_id between", value1, value2, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdNotBetween(Integer value1, Integer value2) {
            addCriterion("create_user_id not between", value1, value2, "createUserId");
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