package domain.party;

import org.apache.commons.lang3.StringUtils;
import shiro.ShiroHelper;
import sys.constants.SystemConstants;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class BranchMemberViewExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public BranchMemberViewExample() {
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

        public Criteria andGroupIdIsNull() {
            addCriterion("group_id is null");
            return (Criteria) this;
        }

        public Criteria andGroupIdIsNotNull() {
            addCriterion("group_id is not null");
            return (Criteria) this;
        }

        public Criteria andGroupIdEqualTo(Integer value) {
            addCriterion("group_id =", value, "groupId");
            return (Criteria) this;
        }

        public Criteria andGroupIdNotEqualTo(Integer value) {
            addCriterion("group_id <>", value, "groupId");
            return (Criteria) this;
        }

        public Criteria andGroupIdGreaterThan(Integer value) {
            addCriterion("group_id >", value, "groupId");
            return (Criteria) this;
        }

        public Criteria andGroupIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("group_id >=", value, "groupId");
            return (Criteria) this;
        }

        public Criteria andGroupIdLessThan(Integer value) {
            addCriterion("group_id <", value, "groupId");
            return (Criteria) this;
        }

        public Criteria andGroupIdLessThanOrEqualTo(Integer value) {
            addCriterion("group_id <=", value, "groupId");
            return (Criteria) this;
        }

        public Criteria andGroupIdIn(List<Integer> values) {
            addCriterion("group_id in", values, "groupId");
            return (Criteria) this;
        }

        public Criteria andGroupIdNotIn(List<Integer> values) {
            addCriterion("group_id not in", values, "groupId");
            return (Criteria) this;
        }

        public Criteria andGroupIdBetween(Integer value1, Integer value2) {
            addCriterion("group_id between", value1, value2, "groupId");
            return (Criteria) this;
        }

        public Criteria andGroupIdNotBetween(Integer value1, Integer value2) {
            addCriterion("group_id not between", value1, value2, "groupId");
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

        public Criteria andTypeIdIsNull() {
            addCriterion("type_id is null");
            return (Criteria) this;
        }

        public Criteria andTypeIdIsNotNull() {
            addCriterion("type_id is not null");
            return (Criteria) this;
        }

        public Criteria andTypeIdEqualTo(Integer value) {
            addCriterion("type_id =", value, "typeId");
            return (Criteria) this;
        }

        public Criteria andTypeIdNotEqualTo(Integer value) {
            addCriterion("type_id <>", value, "typeId");
            return (Criteria) this;
        }

        public Criteria andTypeIdGreaterThan(Integer value) {
            addCriterion("type_id >", value, "typeId");
            return (Criteria) this;
        }

        public Criteria andTypeIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("type_id >=", value, "typeId");
            return (Criteria) this;
        }

        public Criteria andTypeIdLessThan(Integer value) {
            addCriterion("type_id <", value, "typeId");
            return (Criteria) this;
        }

        public Criteria andTypeIdLessThanOrEqualTo(Integer value) {
            addCriterion("type_id <=", value, "typeId");
            return (Criteria) this;
        }

        public Criteria andTypeIdIn(List<Integer> values) {
            addCriterion("type_id in", values, "typeId");
            return (Criteria) this;
        }

        public Criteria andTypeIdNotIn(List<Integer> values) {
            addCriterion("type_id not in", values, "typeId");
            return (Criteria) this;
        }

        public Criteria andTypeIdBetween(Integer value1, Integer value2) {
            addCriterion("type_id between", value1, value2, "typeId");
            return (Criteria) this;
        }

        public Criteria andTypeIdNotBetween(Integer value1, Integer value2) {
            addCriterion("type_id not between", value1, value2, "typeId");
            return (Criteria) this;
        }

        public Criteria andIsDoubleLeaderIsNull() {
            addCriterion("is_double_leader is null");
            return (Criteria) this;
        }

        public Criteria andIsDoubleLeaderIsNotNull() {
            addCriterion("is_double_leader is not null");
            return (Criteria) this;
        }

        public Criteria andIsDoubleLeaderEqualTo(Boolean value) {
            addCriterion("is_double_leader =", value, "isDoubleLeader");
            return (Criteria) this;
        }

        public Criteria andIsDoubleLeaderNotEqualTo(Boolean value) {
            addCriterion("is_double_leader <>", value, "isDoubleLeader");
            return (Criteria) this;
        }

        public Criteria andIsDoubleLeaderGreaterThan(Boolean value) {
            addCriterion("is_double_leader >", value, "isDoubleLeader");
            return (Criteria) this;
        }

        public Criteria andIsDoubleLeaderGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_double_leader >=", value, "isDoubleLeader");
            return (Criteria) this;
        }

        public Criteria andIsDoubleLeaderLessThan(Boolean value) {
            addCriterion("is_double_leader <", value, "isDoubleLeader");
            return (Criteria) this;
        }

        public Criteria andIsDoubleLeaderLessThanOrEqualTo(Boolean value) {
            addCriterion("is_double_leader <=", value, "isDoubleLeader");
            return (Criteria) this;
        }

        public Criteria andIsDoubleLeaderIn(List<Boolean> values) {
            addCriterion("is_double_leader in", values, "isDoubleLeader");
            return (Criteria) this;
        }

        public Criteria andIsDoubleLeaderNotIn(List<Boolean> values) {
            addCriterion("is_double_leader not in", values, "isDoubleLeader");
            return (Criteria) this;
        }

        public Criteria andIsDoubleLeaderBetween(Boolean value1, Boolean value2) {
            addCriterion("is_double_leader between", value1, value2, "isDoubleLeader");
            return (Criteria) this;
        }

        public Criteria andIsDoubleLeaderNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_double_leader not between", value1, value2, "isDoubleLeader");
            return (Criteria) this;
        }

        public Criteria andAssignDateIsNull() {
            addCriterion("assign_date is null");
            return (Criteria) this;
        }

        public Criteria andAssignDateIsNotNull() {
            addCriterion("assign_date is not null");
            return (Criteria) this;
        }

        public Criteria andAssignDateEqualTo(Date value) {
            addCriterionForJDBCDate("assign_date =", value, "assignDate");
            return (Criteria) this;
        }

        public Criteria andAssignDateNotEqualTo(Date value) {
            addCriterionForJDBCDate("assign_date <>", value, "assignDate");
            return (Criteria) this;
        }

        public Criteria andAssignDateGreaterThan(Date value) {
            addCriterionForJDBCDate("assign_date >", value, "assignDate");
            return (Criteria) this;
        }

        public Criteria andAssignDateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("assign_date >=", value, "assignDate");
            return (Criteria) this;
        }

        public Criteria andAssignDateLessThan(Date value) {
            addCriterionForJDBCDate("assign_date <", value, "assignDate");
            return (Criteria) this;
        }

        public Criteria andAssignDateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("assign_date <=", value, "assignDate");
            return (Criteria) this;
        }

        public Criteria andAssignDateIn(List<Date> values) {
            addCriterionForJDBCDate("assign_date in", values, "assignDate");
            return (Criteria) this;
        }

        public Criteria andAssignDateNotIn(List<Date> values) {
            addCriterionForJDBCDate("assign_date not in", values, "assignDate");
            return (Criteria) this;
        }

        public Criteria andAssignDateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("assign_date between", value1, value2, "assignDate");
            return (Criteria) this;
        }

        public Criteria andAssignDateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("assign_date not between", value1, value2, "assignDate");
            return (Criteria) this;
        }

        public Criteria andOfficePhoneIsNull() {
            addCriterion("office_phone is null");
            return (Criteria) this;
        }

        public Criteria andOfficePhoneIsNotNull() {
            addCriterion("office_phone is not null");
            return (Criteria) this;
        }

        public Criteria andOfficePhoneEqualTo(String value) {
            addCriterion("office_phone =", value, "officePhone");
            return (Criteria) this;
        }

        public Criteria andOfficePhoneNotEqualTo(String value) {
            addCriterion("office_phone <>", value, "officePhone");
            return (Criteria) this;
        }

        public Criteria andOfficePhoneGreaterThan(String value) {
            addCriterion("office_phone >", value, "officePhone");
            return (Criteria) this;
        }

        public Criteria andOfficePhoneGreaterThanOrEqualTo(String value) {
            addCriterion("office_phone >=", value, "officePhone");
            return (Criteria) this;
        }

        public Criteria andOfficePhoneLessThan(String value) {
            addCriterion("office_phone <", value, "officePhone");
            return (Criteria) this;
        }

        public Criteria andOfficePhoneLessThanOrEqualTo(String value) {
            addCriterion("office_phone <=", value, "officePhone");
            return (Criteria) this;
        }

        public Criteria andOfficePhoneLike(String value) {
            addCriterion("office_phone like", value, "officePhone");
            return (Criteria) this;
        }

        public Criteria andOfficePhoneNotLike(String value) {
            addCriterion("office_phone not like", value, "officePhone");
            return (Criteria) this;
        }

        public Criteria andOfficePhoneIn(List<String> values) {
            addCriterion("office_phone in", values, "officePhone");
            return (Criteria) this;
        }

        public Criteria andOfficePhoneNotIn(List<String> values) {
            addCriterion("office_phone not in", values, "officePhone");
            return (Criteria) this;
        }

        public Criteria andOfficePhoneBetween(String value1, String value2) {
            addCriterion("office_phone between", value1, value2, "officePhone");
            return (Criteria) this;
        }

        public Criteria andOfficePhoneNotBetween(String value1, String value2) {
            addCriterion("office_phone not between", value1, value2, "officePhone");
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

        public Criteria andIsAdminIsNull() {
            addCriterion("is_admin is null");
            return (Criteria) this;
        }

        public Criteria andIsAdminIsNotNull() {
            addCriterion("is_admin is not null");
            return (Criteria) this;
        }

        public Criteria andIsAdminEqualTo(Boolean value) {
            addCriterion("is_admin =", value, "isAdmin");
            return (Criteria) this;
        }

        public Criteria andIsAdminNotEqualTo(Boolean value) {
            addCriterion("is_admin <>", value, "isAdmin");
            return (Criteria) this;
        }

        public Criteria andIsAdminGreaterThan(Boolean value) {
            addCriterion("is_admin >", value, "isAdmin");
            return (Criteria) this;
        }

        public Criteria andIsAdminGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_admin >=", value, "isAdmin");
            return (Criteria) this;
        }

        public Criteria andIsAdminLessThan(Boolean value) {
            addCriterion("is_admin <", value, "isAdmin");
            return (Criteria) this;
        }

        public Criteria andIsAdminLessThanOrEqualTo(Boolean value) {
            addCriterion("is_admin <=", value, "isAdmin");
            return (Criteria) this;
        }

        public Criteria andIsAdminIn(List<Boolean> values) {
            addCriterion("is_admin in", values, "isAdmin");
            return (Criteria) this;
        }

        public Criteria andIsAdminNotIn(List<Boolean> values) {
            addCriterion("is_admin not in", values, "isAdmin");
            return (Criteria) this;
        }

        public Criteria andIsAdminBetween(Boolean value1, Boolean value2) {
            addCriterion("is_admin between", value1, value2, "isAdmin");
            return (Criteria) this;
        }

        public Criteria andIsAdminNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_admin not between", value1, value2, "isAdmin");
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

        public Criteria andMsgTitleIsNull() {
            addCriterion("msg_title is null");
            return (Criteria) this;
        }

        public Criteria andMsgTitleIsNotNull() {
            addCriterion("msg_title is not null");
            return (Criteria) this;
        }

        public Criteria andMsgTitleEqualTo(String value) {
            addCriterion("msg_title =", value, "msgTitle");
            return (Criteria) this;
        }

        public Criteria andMsgTitleNotEqualTo(String value) {
            addCriterion("msg_title <>", value, "msgTitle");
            return (Criteria) this;
        }

        public Criteria andMsgTitleGreaterThan(String value) {
            addCriterion("msg_title >", value, "msgTitle");
            return (Criteria) this;
        }

        public Criteria andMsgTitleGreaterThanOrEqualTo(String value) {
            addCriterion("msg_title >=", value, "msgTitle");
            return (Criteria) this;
        }

        public Criteria andMsgTitleLessThan(String value) {
            addCriterion("msg_title <", value, "msgTitle");
            return (Criteria) this;
        }

        public Criteria andMsgTitleLessThanOrEqualTo(String value) {
            addCriterion("msg_title <=", value, "msgTitle");
            return (Criteria) this;
        }

        public Criteria andMsgTitleLike(String value) {
            addCriterion("msg_title like", value, "msgTitle");
            return (Criteria) this;
        }

        public Criteria andMsgTitleNotLike(String value) {
            addCriterion("msg_title not like", value, "msgTitle");
            return (Criteria) this;
        }

        public Criteria andMsgTitleIn(List<String> values) {
            addCriterion("msg_title in", values, "msgTitle");
            return (Criteria) this;
        }

        public Criteria andMsgTitleNotIn(List<String> values) {
            addCriterion("msg_title not in", values, "msgTitle");
            return (Criteria) this;
        }

        public Criteria andMsgTitleBetween(String value1, String value2) {
            addCriterion("msg_title between", value1, value2, "msgTitle");
            return (Criteria) this;
        }

        public Criteria andMsgTitleNotBetween(String value1, String value2) {
            addCriterion("msg_title not between", value1, value2, "msgTitle");
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

        public Criteria andNativePlaceIsNull() {
            addCriterion("native_place is null");
            return (Criteria) this;
        }

        public Criteria andNativePlaceIsNotNull() {
            addCriterion("native_place is not null");
            return (Criteria) this;
        }

        public Criteria andNativePlaceEqualTo(String value) {
            addCriterion("native_place =", value, "nativePlace");
            return (Criteria) this;
        }

        public Criteria andNativePlaceNotEqualTo(String value) {
            addCriterion("native_place <>", value, "nativePlace");
            return (Criteria) this;
        }

        public Criteria andNativePlaceGreaterThan(String value) {
            addCriterion("native_place >", value, "nativePlace");
            return (Criteria) this;
        }

        public Criteria andNativePlaceGreaterThanOrEqualTo(String value) {
            addCriterion("native_place >=", value, "nativePlace");
            return (Criteria) this;
        }

        public Criteria andNativePlaceLessThan(String value) {
            addCriterion("native_place <", value, "nativePlace");
            return (Criteria) this;
        }

        public Criteria andNativePlaceLessThanOrEqualTo(String value) {
            addCriterion("native_place <=", value, "nativePlace");
            return (Criteria) this;
        }

        public Criteria andNativePlaceLike(String value) {
            addCriterion("native_place like", value, "nativePlace");
            return (Criteria) this;
        }

        public Criteria andNativePlaceNotLike(String value) {
            addCriterion("native_place not like", value, "nativePlace");
            return (Criteria) this;
        }

        public Criteria andNativePlaceIn(List<String> values) {
            addCriterion("native_place in", values, "nativePlace");
            return (Criteria) this;
        }

        public Criteria andNativePlaceNotIn(List<String> values) {
            addCriterion("native_place not in", values, "nativePlace");
            return (Criteria) this;
        }

        public Criteria andNativePlaceBetween(String value1, String value2) {
            addCriterion("native_place between", value1, value2, "nativePlace");
            return (Criteria) this;
        }

        public Criteria andNativePlaceNotBetween(String value1, String value2) {
            addCriterion("native_place not between", value1, value2, "nativePlace");
            return (Criteria) this;
        }

        public Criteria andIdcardIsNull() {
            addCriterion("idcard is null");
            return (Criteria) this;
        }

        public Criteria andIdcardIsNotNull() {
            addCriterion("idcard is not null");
            return (Criteria) this;
        }

        public Criteria andIdcardEqualTo(String value) {
            addCriterion("idcard =", value, "idcard");
            return (Criteria) this;
        }

        public Criteria andIdcardNotEqualTo(String value) {
            addCriterion("idcard <>", value, "idcard");
            return (Criteria) this;
        }

        public Criteria andIdcardGreaterThan(String value) {
            addCriterion("idcard >", value, "idcard");
            return (Criteria) this;
        }

        public Criteria andIdcardGreaterThanOrEqualTo(String value) {
            addCriterion("idcard >=", value, "idcard");
            return (Criteria) this;
        }

        public Criteria andIdcardLessThan(String value) {
            addCriterion("idcard <", value, "idcard");
            return (Criteria) this;
        }

        public Criteria andIdcardLessThanOrEqualTo(String value) {
            addCriterion("idcard <=", value, "idcard");
            return (Criteria) this;
        }

        public Criteria andIdcardLike(String value) {
            addCriterion("idcard like", value, "idcard");
            return (Criteria) this;
        }

        public Criteria andIdcardNotLike(String value) {
            addCriterion("idcard not like", value, "idcard");
            return (Criteria) this;
        }

        public Criteria andIdcardIn(List<String> values) {
            addCriterion("idcard in", values, "idcard");
            return (Criteria) this;
        }

        public Criteria andIdcardNotIn(List<String> values) {
            addCriterion("idcard not in", values, "idcard");
            return (Criteria) this;
        }

        public Criteria andIdcardBetween(String value1, String value2) {
            addCriterion("idcard between", value1, value2, "idcard");
            return (Criteria) this;
        }

        public Criteria andIdcardNotBetween(String value1, String value2) {
            addCriterion("idcard not between", value1, value2, "idcard");
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

        public Criteria andBranchIdIsNull() {
            addCriterion("branch_id is null");
            return (Criteria) this;
        }

        public Criteria andBranchIdIsNotNull() {
            addCriterion("branch_id is not null");
            return (Criteria) this;
        }

        public Criteria andBranchIdEqualTo(Integer value) {
            addCriterion("branch_id =", value, "branchId");
            return (Criteria) this;
        }

        public Criteria andBranchIdNotEqualTo(Integer value) {
            addCriterion("branch_id <>", value, "branchId");
            return (Criteria) this;
        }

        public Criteria andBranchIdGreaterThan(Integer value) {
            addCriterion("branch_id >", value, "branchId");
            return (Criteria) this;
        }

        public Criteria andBranchIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("branch_id >=", value, "branchId");
            return (Criteria) this;
        }

        public Criteria andBranchIdLessThan(Integer value) {
            addCriterion("branch_id <", value, "branchId");
            return (Criteria) this;
        }

        public Criteria andBranchIdLessThanOrEqualTo(Integer value) {
            addCriterion("branch_id <=", value, "branchId");
            return (Criteria) this;
        }

        public Criteria andBranchIdIn(List<Integer> values) {
            addCriterion("branch_id in", values, "branchId");
            return (Criteria) this;
        }

        public Criteria andBranchIdNotIn(List<Integer> values) {
            addCriterion("branch_id not in", values, "branchId");
            return (Criteria) this;
        }

        public Criteria andBranchIdBetween(Integer value1, Integer value2) {
            addCriterion("branch_id between", value1, value2, "branchId");
            return (Criteria) this;
        }

        public Criteria andBranchIdNotBetween(Integer value1, Integer value2) {
            addCriterion("branch_id not between", value1, value2, "branchId");
            return (Criteria) this;
        }

        public Criteria andMemberStatusIsNull() {
            addCriterion("member_status is null");
            return (Criteria) this;
        }

        public Criteria andMemberStatusIsNotNull() {
            addCriterion("member_status is not null");
            return (Criteria) this;
        }

        public Criteria andMemberStatusEqualTo(Byte value) {
            addCriterion("member_status =", value, "memberStatus");
            return (Criteria) this;
        }

        public Criteria andMemberStatusNotEqualTo(Byte value) {
            addCriterion("member_status <>", value, "memberStatus");
            return (Criteria) this;
        }

        public Criteria andMemberStatusGreaterThan(Byte value) {
            addCriterion("member_status >", value, "memberStatus");
            return (Criteria) this;
        }

        public Criteria andMemberStatusGreaterThanOrEqualTo(Byte value) {
            addCriterion("member_status >=", value, "memberStatus");
            return (Criteria) this;
        }

        public Criteria andMemberStatusLessThan(Byte value) {
            addCriterion("member_status <", value, "memberStatus");
            return (Criteria) this;
        }

        public Criteria andMemberStatusLessThanOrEqualTo(Byte value) {
            addCriterion("member_status <=", value, "memberStatus");
            return (Criteria) this;
        }

        public Criteria andMemberStatusIn(List<Byte> values) {
            addCriterion("member_status in", values, "memberStatus");
            return (Criteria) this;
        }

        public Criteria andMemberStatusNotIn(List<Byte> values) {
            addCriterion("member_status not in", values, "memberStatus");
            return (Criteria) this;
        }

        public Criteria andMemberStatusBetween(Byte value1, Byte value2) {
            addCriterion("member_status between", value1, value2, "memberStatus");
            return (Criteria) this;
        }

        public Criteria andMemberStatusNotBetween(Byte value1, Byte value2) {
            addCriterion("member_status not between", value1, value2, "memberStatus");
            return (Criteria) this;
        }

        public Criteria andGroupBranchIdIsNull() {
            addCriterion("group_branch_id is null");
            return (Criteria) this;
        }

        public Criteria andGroupBranchIdIsNotNull() {
            addCriterion("group_branch_id is not null");
            return (Criteria) this;
        }

        public Criteria andGroupBranchIdEqualTo(Integer value) {
            addCriterion("group_branch_id =", value, "groupBranchId");
            return (Criteria) this;
        }

        public Criteria andGroupBranchIdNotEqualTo(Integer value) {
            addCriterion("group_branch_id <>", value, "groupBranchId");
            return (Criteria) this;
        }

        public Criteria andGroupBranchIdGreaterThan(Integer value) {
            addCriterion("group_branch_id >", value, "groupBranchId");
            return (Criteria) this;
        }

        public Criteria andGroupBranchIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("group_branch_id >=", value, "groupBranchId");
            return (Criteria) this;
        }

        public Criteria andGroupBranchIdLessThan(Integer value) {
            addCriterion("group_branch_id <", value, "groupBranchId");
            return (Criteria) this;
        }

        public Criteria andGroupBranchIdLessThanOrEqualTo(Integer value) {
            addCriterion("group_branch_id <=", value, "groupBranchId");
            return (Criteria) this;
        }

        public Criteria andGroupBranchIdIn(List<Integer> values) {
            addCriterion("group_branch_id in", values, "groupBranchId");
            return (Criteria) this;
        }

        public Criteria andGroupBranchIdNotIn(List<Integer> values) {
            addCriterion("group_branch_id not in", values, "groupBranchId");
            return (Criteria) this;
        }

        public Criteria andGroupBranchIdBetween(Integer value1, Integer value2) {
            addCriterion("group_branch_id between", value1, value2, "groupBranchId");
            return (Criteria) this;
        }

        public Criteria andGroupBranchIdNotBetween(Integer value1, Integer value2) {
            addCriterion("group_branch_id not between", value1, value2, "groupBranchId");
            return (Criteria) this;
        }

        public Criteria andIsPresentIsNull() {
            addCriterion("is_present is null");
            return (Criteria) this;
        }

        public Criteria andIsPresentIsNotNull() {
            addCriterion("is_present is not null");
            return (Criteria) this;
        }

        public Criteria andIsPresentEqualTo(Boolean value) {
            addCriterion("is_present =", value, "isPresent");
            return (Criteria) this;
        }

        public Criteria andIsPresentNotEqualTo(Boolean value) {
            addCriterion("is_present <>", value, "isPresent");
            return (Criteria) this;
        }

        public Criteria andIsPresentGreaterThan(Boolean value) {
            addCriterion("is_present >", value, "isPresent");
            return (Criteria) this;
        }

        public Criteria andIsPresentGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_present >=", value, "isPresent");
            return (Criteria) this;
        }

        public Criteria andIsPresentLessThan(Boolean value) {
            addCriterion("is_present <", value, "isPresent");
            return (Criteria) this;
        }

        public Criteria andIsPresentLessThanOrEqualTo(Boolean value) {
            addCriterion("is_present <=", value, "isPresent");
            return (Criteria) this;
        }

        public Criteria andIsPresentIn(List<Boolean> values) {
            addCriterion("is_present in", values, "isPresent");
            return (Criteria) this;
        }

        public Criteria andIsPresentNotIn(List<Boolean> values) {
            addCriterion("is_present not in", values, "isPresent");
            return (Criteria) this;
        }

        public Criteria andIsPresentBetween(Boolean value1, Boolean value2) {
            addCriterion("is_present between", value1, value2, "isPresent");
            return (Criteria) this;
        }

        public Criteria andIsPresentNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_present not between", value1, value2, "isPresent");
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

        public Criteria andGroupPartyIdIsNull() {
            addCriterion("group_party_id is null");
            return (Criteria) this;
        }

        public Criteria andGroupPartyIdIsNotNull() {
            addCriterion("group_party_id is not null");
            return (Criteria) this;
        }

        public Criteria andGroupPartyIdEqualTo(Integer value) {
            addCriterion("group_party_id =", value, "groupPartyId");
            return (Criteria) this;
        }

        public Criteria andGroupPartyIdNotEqualTo(Integer value) {
            addCriterion("group_party_id <>", value, "groupPartyId");
            return (Criteria) this;
        }

        public Criteria andGroupPartyIdGreaterThan(Integer value) {
            addCriterion("group_party_id >", value, "groupPartyId");
            return (Criteria) this;
        }

        public Criteria andGroupPartyIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("group_party_id >=", value, "groupPartyId");
            return (Criteria) this;
        }

        public Criteria andGroupPartyIdLessThan(Integer value) {
            addCriterion("group_party_id <", value, "groupPartyId");
            return (Criteria) this;
        }

        public Criteria andGroupPartyIdLessThanOrEqualTo(Integer value) {
            addCriterion("group_party_id <=", value, "groupPartyId");
            return (Criteria) this;
        }

        public Criteria andGroupPartyIdIn(List<Integer> values) {
            addCriterion("group_party_id in", values, "groupPartyId");
            return (Criteria) this;
        }

        public Criteria andGroupPartyIdNotIn(List<Integer> values) {
            addCriterion("group_party_id not in", values, "groupPartyId");
            return (Criteria) this;
        }

        public Criteria andGroupPartyIdBetween(Integer value1, Integer value2) {
            addCriterion("group_party_id between", value1, value2, "groupPartyId");
            return (Criteria) this;
        }

        public Criteria andGroupPartyIdNotBetween(Integer value1, Integer value2) {
            addCriterion("group_party_id not between", value1, value2, "groupPartyId");
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

        public Criteria andBranchSortOrderIsNull() {
            addCriterion("branch_sort_order is null");
            return (Criteria) this;
        }

        public Criteria andBranchSortOrderIsNotNull() {
            addCriterion("branch_sort_order is not null");
            return (Criteria) this;
        }

        public Criteria andBranchSortOrderEqualTo(Integer value) {
            addCriterion("branch_sort_order =", value, "branchSortOrder");
            return (Criteria) this;
        }

        public Criteria andBranchSortOrderNotEqualTo(Integer value) {
            addCriterion("branch_sort_order <>", value, "branchSortOrder");
            return (Criteria) this;
        }

        public Criteria andBranchSortOrderGreaterThan(Integer value) {
            addCriterion("branch_sort_order >", value, "branchSortOrder");
            return (Criteria) this;
        }

        public Criteria andBranchSortOrderGreaterThanOrEqualTo(Integer value) {
            addCriterion("branch_sort_order >=", value, "branchSortOrder");
            return (Criteria) this;
        }

        public Criteria andBranchSortOrderLessThan(Integer value) {
            addCriterion("branch_sort_order <", value, "branchSortOrder");
            return (Criteria) this;
        }

        public Criteria andBranchSortOrderLessThanOrEqualTo(Integer value) {
            addCriterion("branch_sort_order <=", value, "branchSortOrder");
            return (Criteria) this;
        }

        public Criteria andBranchSortOrderIn(List<Integer> values) {
            addCriterion("branch_sort_order in", values, "branchSortOrder");
            return (Criteria) this;
        }

        public Criteria andBranchSortOrderNotIn(List<Integer> values) {
            addCriterion("branch_sort_order not in", values, "branchSortOrder");
            return (Criteria) this;
        }

        public Criteria andBranchSortOrderBetween(Integer value1, Integer value2) {
            addCriterion("branch_sort_order between", value1, value2, "branchSortOrder");
            return (Criteria) this;
        }

        public Criteria andBranchSortOrderNotBetween(Integer value1, Integer value2) {
            addCriterion("branch_sort_order not between", value1, value2, "branchSortOrder");
            return (Criteria) this;
        }

        public Criteria andPostClassIsNull() {
            addCriterion("post_class is null");
            return (Criteria) this;
        }

        public Criteria andPostClassIsNotNull() {
            addCriterion("post_class is not null");
            return (Criteria) this;
        }

        public Criteria andPostClassEqualTo(String value) {
            addCriterion("post_class =", value, "postClass");
            return (Criteria) this;
        }

        public Criteria andPostClassNotEqualTo(String value) {
            addCriterion("post_class <>", value, "postClass");
            return (Criteria) this;
        }

        public Criteria andPostClassGreaterThan(String value) {
            addCriterion("post_class >", value, "postClass");
            return (Criteria) this;
        }

        public Criteria andPostClassGreaterThanOrEqualTo(String value) {
            addCriterion("post_class >=", value, "postClass");
            return (Criteria) this;
        }

        public Criteria andPostClassLessThan(String value) {
            addCriterion("post_class <", value, "postClass");
            return (Criteria) this;
        }

        public Criteria andPostClassLessThanOrEqualTo(String value) {
            addCriterion("post_class <=", value, "postClass");
            return (Criteria) this;
        }

        public Criteria andPostClassLike(String value) {
            addCriterion("post_class like", value, "postClass");
            return (Criteria) this;
        }

        public Criteria andPostClassNotLike(String value) {
            addCriterion("post_class not like", value, "postClass");
            return (Criteria) this;
        }

        public Criteria andPostClassIn(List<String> values) {
            addCriterion("post_class in", values, "postClass");
            return (Criteria) this;
        }

        public Criteria andPostClassNotIn(List<String> values) {
            addCriterion("post_class not in", values, "postClass");
            return (Criteria) this;
        }

        public Criteria andPostClassBetween(String value1, String value2) {
            addCriterion("post_class between", value1, value2, "postClass");
            return (Criteria) this;
        }

        public Criteria andPostClassNotBetween(String value1, String value2) {
            addCriterion("post_class not between", value1, value2, "postClass");
            return (Criteria) this;
        }

        public Criteria andSubPostClassIsNull() {
            addCriterion("sub_post_class is null");
            return (Criteria) this;
        }

        public Criteria andSubPostClassIsNotNull() {
            addCriterion("sub_post_class is not null");
            return (Criteria) this;
        }

        public Criteria andSubPostClassEqualTo(String value) {
            addCriterion("sub_post_class =", value, "subPostClass");
            return (Criteria) this;
        }

        public Criteria andSubPostClassNotEqualTo(String value) {
            addCriterion("sub_post_class <>", value, "subPostClass");
            return (Criteria) this;
        }

        public Criteria andSubPostClassGreaterThan(String value) {
            addCriterion("sub_post_class >", value, "subPostClass");
            return (Criteria) this;
        }

        public Criteria andSubPostClassGreaterThanOrEqualTo(String value) {
            addCriterion("sub_post_class >=", value, "subPostClass");
            return (Criteria) this;
        }

        public Criteria andSubPostClassLessThan(String value) {
            addCriterion("sub_post_class <", value, "subPostClass");
            return (Criteria) this;
        }

        public Criteria andSubPostClassLessThanOrEqualTo(String value) {
            addCriterion("sub_post_class <=", value, "subPostClass");
            return (Criteria) this;
        }

        public Criteria andSubPostClassLike(String value) {
            addCriterion("sub_post_class like", value, "subPostClass");
            return (Criteria) this;
        }

        public Criteria andSubPostClassNotLike(String value) {
            addCriterion("sub_post_class not like", value, "subPostClass");
            return (Criteria) this;
        }

        public Criteria andSubPostClassIn(List<String> values) {
            addCriterion("sub_post_class in", values, "subPostClass");
            return (Criteria) this;
        }

        public Criteria andSubPostClassNotIn(List<String> values) {
            addCriterion("sub_post_class not in", values, "subPostClass");
            return (Criteria) this;
        }

        public Criteria andSubPostClassBetween(String value1, String value2) {
            addCriterion("sub_post_class between", value1, value2, "subPostClass");
            return (Criteria) this;
        }

        public Criteria andSubPostClassNotBetween(String value1, String value2) {
            addCriterion("sub_post_class not between", value1, value2, "subPostClass");
            return (Criteria) this;
        }

        public Criteria andMainPostLevelIsNull() {
            addCriterion("main_post_level is null");
            return (Criteria) this;
        }

        public Criteria andMainPostLevelIsNotNull() {
            addCriterion("main_post_level is not null");
            return (Criteria) this;
        }

        public Criteria andMainPostLevelEqualTo(String value) {
            addCriterion("main_post_level =", value, "mainPostLevel");
            return (Criteria) this;
        }

        public Criteria andMainPostLevelNotEqualTo(String value) {
            addCriterion("main_post_level <>", value, "mainPostLevel");
            return (Criteria) this;
        }

        public Criteria andMainPostLevelGreaterThan(String value) {
            addCriterion("main_post_level >", value, "mainPostLevel");
            return (Criteria) this;
        }

        public Criteria andMainPostLevelGreaterThanOrEqualTo(String value) {
            addCriterion("main_post_level >=", value, "mainPostLevel");
            return (Criteria) this;
        }

        public Criteria andMainPostLevelLessThan(String value) {
            addCriterion("main_post_level <", value, "mainPostLevel");
            return (Criteria) this;
        }

        public Criteria andMainPostLevelLessThanOrEqualTo(String value) {
            addCriterion("main_post_level <=", value, "mainPostLevel");
            return (Criteria) this;
        }

        public Criteria andMainPostLevelLike(String value) {
            addCriterion("main_post_level like", value, "mainPostLevel");
            return (Criteria) this;
        }

        public Criteria andMainPostLevelNotLike(String value) {
            addCriterion("main_post_level not like", value, "mainPostLevel");
            return (Criteria) this;
        }

        public Criteria andMainPostLevelIn(List<String> values) {
            addCriterion("main_post_level in", values, "mainPostLevel");
            return (Criteria) this;
        }

        public Criteria andMainPostLevelNotIn(List<String> values) {
            addCriterion("main_post_level not in", values, "mainPostLevel");
            return (Criteria) this;
        }

        public Criteria andMainPostLevelBetween(String value1, String value2) {
            addCriterion("main_post_level between", value1, value2, "mainPostLevel");
            return (Criteria) this;
        }

        public Criteria andMainPostLevelNotBetween(String value1, String value2) {
            addCriterion("main_post_level not between", value1, value2, "mainPostLevel");
            return (Criteria) this;
        }

        public Criteria andProPostTimeIsNull() {
            addCriterion("pro_post_time is null");
            return (Criteria) this;
        }

        public Criteria andProPostTimeIsNotNull() {
            addCriterion("pro_post_time is not null");
            return (Criteria) this;
        }

        public Criteria andProPostTimeEqualTo(Date value) {
            addCriterionForJDBCDate("pro_post_time =", value, "proPostTime");
            return (Criteria) this;
        }

        public Criteria andProPostTimeNotEqualTo(Date value) {
            addCriterionForJDBCDate("pro_post_time <>", value, "proPostTime");
            return (Criteria) this;
        }

        public Criteria andProPostTimeGreaterThan(Date value) {
            addCriterionForJDBCDate("pro_post_time >", value, "proPostTime");
            return (Criteria) this;
        }

        public Criteria andProPostTimeGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("pro_post_time >=", value, "proPostTime");
            return (Criteria) this;
        }

        public Criteria andProPostTimeLessThan(Date value) {
            addCriterionForJDBCDate("pro_post_time <", value, "proPostTime");
            return (Criteria) this;
        }

        public Criteria andProPostTimeLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("pro_post_time <=", value, "proPostTime");
            return (Criteria) this;
        }

        public Criteria andProPostTimeIn(List<Date> values) {
            addCriterionForJDBCDate("pro_post_time in", values, "proPostTime");
            return (Criteria) this;
        }

        public Criteria andProPostTimeNotIn(List<Date> values) {
            addCriterionForJDBCDate("pro_post_time not in", values, "proPostTime");
            return (Criteria) this;
        }

        public Criteria andProPostTimeBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("pro_post_time between", value1, value2, "proPostTime");
            return (Criteria) this;
        }

        public Criteria andProPostTimeNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("pro_post_time not between", value1, value2, "proPostTime");
            return (Criteria) this;
        }

        public Criteria andProPostLevelIsNull() {
            addCriterion("pro_post_level is null");
            return (Criteria) this;
        }

        public Criteria andProPostLevelIsNotNull() {
            addCriterion("pro_post_level is not null");
            return (Criteria) this;
        }

        public Criteria andProPostLevelEqualTo(String value) {
            addCriterion("pro_post_level =", value, "proPostLevel");
            return (Criteria) this;
        }

        public Criteria andProPostLevelNotEqualTo(String value) {
            addCriterion("pro_post_level <>", value, "proPostLevel");
            return (Criteria) this;
        }

        public Criteria andProPostLevelGreaterThan(String value) {
            addCriterion("pro_post_level >", value, "proPostLevel");
            return (Criteria) this;
        }

        public Criteria andProPostLevelGreaterThanOrEqualTo(String value) {
            addCriterion("pro_post_level >=", value, "proPostLevel");
            return (Criteria) this;
        }

        public Criteria andProPostLevelLessThan(String value) {
            addCriterion("pro_post_level <", value, "proPostLevel");
            return (Criteria) this;
        }

        public Criteria andProPostLevelLessThanOrEqualTo(String value) {
            addCriterion("pro_post_level <=", value, "proPostLevel");
            return (Criteria) this;
        }

        public Criteria andProPostLevelLike(String value) {
            addCriterion("pro_post_level like", value, "proPostLevel");
            return (Criteria) this;
        }

        public Criteria andProPostLevelNotLike(String value) {
            addCriterion("pro_post_level not like", value, "proPostLevel");
            return (Criteria) this;
        }

        public Criteria andProPostLevelIn(List<String> values) {
            addCriterion("pro_post_level in", values, "proPostLevel");
            return (Criteria) this;
        }

        public Criteria andProPostLevelNotIn(List<String> values) {
            addCriterion("pro_post_level not in", values, "proPostLevel");
            return (Criteria) this;
        }

        public Criteria andProPostLevelBetween(String value1, String value2) {
            addCriterion("pro_post_level between", value1, value2, "proPostLevel");
            return (Criteria) this;
        }

        public Criteria andProPostLevelNotBetween(String value1, String value2) {
            addCriterion("pro_post_level not between", value1, value2, "proPostLevel");
            return (Criteria) this;
        }

        public Criteria andProPostLevelTimeIsNull() {
            addCriterion("pro_post_level_time is null");
            return (Criteria) this;
        }

        public Criteria andProPostLevelTimeIsNotNull() {
            addCriterion("pro_post_level_time is not null");
            return (Criteria) this;
        }

        public Criteria andProPostLevelTimeEqualTo(Date value) {
            addCriterionForJDBCDate("pro_post_level_time =", value, "proPostLevelTime");
            return (Criteria) this;
        }

        public Criteria andProPostLevelTimeNotEqualTo(Date value) {
            addCriterionForJDBCDate("pro_post_level_time <>", value, "proPostLevelTime");
            return (Criteria) this;
        }

        public Criteria andProPostLevelTimeGreaterThan(Date value) {
            addCriterionForJDBCDate("pro_post_level_time >", value, "proPostLevelTime");
            return (Criteria) this;
        }

        public Criteria andProPostLevelTimeGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("pro_post_level_time >=", value, "proPostLevelTime");
            return (Criteria) this;
        }

        public Criteria andProPostLevelTimeLessThan(Date value) {
            addCriterionForJDBCDate("pro_post_level_time <", value, "proPostLevelTime");
            return (Criteria) this;
        }

        public Criteria andProPostLevelTimeLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("pro_post_level_time <=", value, "proPostLevelTime");
            return (Criteria) this;
        }

        public Criteria andProPostLevelTimeIn(List<Date> values) {
            addCriterionForJDBCDate("pro_post_level_time in", values, "proPostLevelTime");
            return (Criteria) this;
        }

        public Criteria andProPostLevelTimeNotIn(List<Date> values) {
            addCriterionForJDBCDate("pro_post_level_time not in", values, "proPostLevelTime");
            return (Criteria) this;
        }

        public Criteria andProPostLevelTimeBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("pro_post_level_time between", value1, value2, "proPostLevelTime");
            return (Criteria) this;
        }

        public Criteria andProPostLevelTimeNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("pro_post_level_time not between", value1, value2, "proPostLevelTime");
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

        public Criteria andManageLevelIsNull() {
            addCriterion("manage_level is null");
            return (Criteria) this;
        }

        public Criteria andManageLevelIsNotNull() {
            addCriterion("manage_level is not null");
            return (Criteria) this;
        }

        public Criteria andManageLevelEqualTo(String value) {
            addCriterion("manage_level =", value, "manageLevel");
            return (Criteria) this;
        }

        public Criteria andManageLevelNotEqualTo(String value) {
            addCriterion("manage_level <>", value, "manageLevel");
            return (Criteria) this;
        }

        public Criteria andManageLevelGreaterThan(String value) {
            addCriterion("manage_level >", value, "manageLevel");
            return (Criteria) this;
        }

        public Criteria andManageLevelGreaterThanOrEqualTo(String value) {
            addCriterion("manage_level >=", value, "manageLevel");
            return (Criteria) this;
        }

        public Criteria andManageLevelLessThan(String value) {
            addCriterion("manage_level <", value, "manageLevel");
            return (Criteria) this;
        }

        public Criteria andManageLevelLessThanOrEqualTo(String value) {
            addCriterion("manage_level <=", value, "manageLevel");
            return (Criteria) this;
        }

        public Criteria andManageLevelLike(String value) {
            addCriterion("manage_level like", value, "manageLevel");
            return (Criteria) this;
        }

        public Criteria andManageLevelNotLike(String value) {
            addCriterion("manage_level not like", value, "manageLevel");
            return (Criteria) this;
        }

        public Criteria andManageLevelIn(List<String> values) {
            addCriterion("manage_level in", values, "manageLevel");
            return (Criteria) this;
        }

        public Criteria andManageLevelNotIn(List<String> values) {
            addCriterion("manage_level not in", values, "manageLevel");
            return (Criteria) this;
        }

        public Criteria andManageLevelBetween(String value1, String value2) {
            addCriterion("manage_level between", value1, value2, "manageLevel");
            return (Criteria) this;
        }

        public Criteria andManageLevelNotBetween(String value1, String value2) {
            addCriterion("manage_level not between", value1, value2, "manageLevel");
            return (Criteria) this;
        }

        public Criteria andManageLevelTimeIsNull() {
            addCriterion("manage_level_time is null");
            return (Criteria) this;
        }

        public Criteria andManageLevelTimeIsNotNull() {
            addCriterion("manage_level_time is not null");
            return (Criteria) this;
        }

        public Criteria andManageLevelTimeEqualTo(Date value) {
            addCriterionForJDBCDate("manage_level_time =", value, "manageLevelTime");
            return (Criteria) this;
        }

        public Criteria andManageLevelTimeNotEqualTo(Date value) {
            addCriterionForJDBCDate("manage_level_time <>", value, "manageLevelTime");
            return (Criteria) this;
        }

        public Criteria andManageLevelTimeGreaterThan(Date value) {
            addCriterionForJDBCDate("manage_level_time >", value, "manageLevelTime");
            return (Criteria) this;
        }

        public Criteria andManageLevelTimeGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("manage_level_time >=", value, "manageLevelTime");
            return (Criteria) this;
        }

        public Criteria andManageLevelTimeLessThan(Date value) {
            addCriterionForJDBCDate("manage_level_time <", value, "manageLevelTime");
            return (Criteria) this;
        }

        public Criteria andManageLevelTimeLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("manage_level_time <=", value, "manageLevelTime");
            return (Criteria) this;
        }

        public Criteria andManageLevelTimeIn(List<Date> values) {
            addCriterionForJDBCDate("manage_level_time in", values, "manageLevelTime");
            return (Criteria) this;
        }

        public Criteria andManageLevelTimeNotIn(List<Date> values) {
            addCriterionForJDBCDate("manage_level_time not in", values, "manageLevelTime");
            return (Criteria) this;
        }

        public Criteria andManageLevelTimeBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("manage_level_time between", value1, value2, "manageLevelTime");
            return (Criteria) this;
        }

        public Criteria andManageLevelTimeNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("manage_level_time not between", value1, value2, "manageLevelTime");
            return (Criteria) this;
        }

        public Criteria andArriveTimeIsNull() {
            addCriterion("arrive_time is null");
            return (Criteria) this;
        }

        public Criteria andArriveTimeIsNotNull() {
            addCriterion("arrive_time is not null");
            return (Criteria) this;
        }

        public Criteria andArriveTimeEqualTo(Date value) {
            addCriterionForJDBCDate("arrive_time =", value, "arriveTime");
            return (Criteria) this;
        }

        public Criteria andArriveTimeNotEqualTo(Date value) {
            addCriterionForJDBCDate("arrive_time <>", value, "arriveTime");
            return (Criteria) this;
        }

        public Criteria andArriveTimeGreaterThan(Date value) {
            addCriterionForJDBCDate("arrive_time >", value, "arriveTime");
            return (Criteria) this;
        }

        public Criteria andArriveTimeGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("arrive_time >=", value, "arriveTime");
            return (Criteria) this;
        }

        public Criteria andArriveTimeLessThan(Date value) {
            addCriterionForJDBCDate("arrive_time <", value, "arriveTime");
            return (Criteria) this;
        }

        public Criteria andArriveTimeLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("arrive_time <=", value, "arriveTime");
            return (Criteria) this;
        }

        public Criteria andArriveTimeIn(List<Date> values) {
            addCriterionForJDBCDate("arrive_time in", values, "arriveTime");
            return (Criteria) this;
        }

        public Criteria andArriveTimeNotIn(List<Date> values) {
            addCriterionForJDBCDate("arrive_time not in", values, "arriveTime");
            return (Criteria) this;
        }

        public Criteria andArriveTimeBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("arrive_time between", value1, value2, "arriveTime");
            return (Criteria) this;
        }

        public Criteria andArriveTimeNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("arrive_time not between", value1, value2, "arriveTime");
            return (Criteria) this;
        }

        public Criteria andOwIdIsNull() {
            addCriterion("ow_id is null");
            return (Criteria) this;
        }

        public Criteria andOwIdIsNotNull() {
            addCriterion("ow_id is not null");
            return (Criteria) this;
        }

        public Criteria andOwIdEqualTo(Integer value) {
            addCriterion("ow_id =", value, "owId");
            return (Criteria) this;
        }

        public Criteria andOwIdNotEqualTo(Integer value) {
            addCriterion("ow_id <>", value, "owId");
            return (Criteria) this;
        }

        public Criteria andOwIdGreaterThan(Integer value) {
            addCriterion("ow_id >", value, "owId");
            return (Criteria) this;
        }

        public Criteria andOwIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("ow_id >=", value, "owId");
            return (Criteria) this;
        }

        public Criteria andOwIdLessThan(Integer value) {
            addCriterion("ow_id <", value, "owId");
            return (Criteria) this;
        }

        public Criteria andOwIdLessThanOrEqualTo(Integer value) {
            addCriterion("ow_id <=", value, "owId");
            return (Criteria) this;
        }

        public Criteria andOwIdIn(List<Integer> values) {
            addCriterion("ow_id in", values, "owId");
            return (Criteria) this;
        }

        public Criteria andOwIdNotIn(List<Integer> values) {
            addCriterion("ow_id not in", values, "owId");
            return (Criteria) this;
        }

        public Criteria andOwIdBetween(Integer value1, Integer value2) {
            addCriterion("ow_id between", value1, value2, "owId");
            return (Criteria) this;
        }

        public Criteria andOwIdNotBetween(Integer value1, Integer value2) {
            addCriterion("ow_id not between", value1, value2, "owId");
            return (Criteria) this;
        }

        public Criteria andIsOwIsNull() {
            addCriterion("is_ow is null");
            return (Criteria) this;
        }

        public Criteria andIsOwIsNotNull() {
            addCriterion("is_ow is not null");
            return (Criteria) this;
        }

        public Criteria andIsOwEqualTo(Boolean value) {
            addCriterion("is_ow =", value, "isOw");
            return (Criteria) this;
        }

        public Criteria andIsOwNotEqualTo(Boolean value) {
            addCriterion("is_ow <>", value, "isOw");
            return (Criteria) this;
        }

        public Criteria andIsOwGreaterThan(Boolean value) {
            addCriterion("is_ow >", value, "isOw");
            return (Criteria) this;
        }

        public Criteria andIsOwGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_ow >=", value, "isOw");
            return (Criteria) this;
        }

        public Criteria andIsOwLessThan(Boolean value) {
            addCriterion("is_ow <", value, "isOw");
            return (Criteria) this;
        }

        public Criteria andIsOwLessThanOrEqualTo(Boolean value) {
            addCriterion("is_ow <=", value, "isOw");
            return (Criteria) this;
        }

        public Criteria andIsOwIn(List<Boolean> values) {
            addCriterion("is_ow in", values, "isOw");
            return (Criteria) this;
        }

        public Criteria andIsOwNotIn(List<Boolean> values) {
            addCriterion("is_ow not in", values, "isOw");
            return (Criteria) this;
        }

        public Criteria andIsOwBetween(Boolean value1, Boolean value2) {
            addCriterion("is_ow between", value1, value2, "isOw");
            return (Criteria) this;
        }

        public Criteria andIsOwNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_ow not between", value1, value2, "isOw");
            return (Criteria) this;
        }

        public Criteria andOwGrowTimeIsNull() {
            addCriterion("ow_grow_time is null");
            return (Criteria) this;
        }

        public Criteria andOwGrowTimeIsNotNull() {
            addCriterion("ow_grow_time is not null");
            return (Criteria) this;
        }

        public Criteria andOwGrowTimeEqualTo(Date value) {
            addCriterionForJDBCDate("ow_grow_time =", value, "owGrowTime");
            return (Criteria) this;
        }

        public Criteria andOwGrowTimeNotEqualTo(Date value) {
            addCriterionForJDBCDate("ow_grow_time <>", value, "owGrowTime");
            return (Criteria) this;
        }

        public Criteria andOwGrowTimeGreaterThan(Date value) {
            addCriterionForJDBCDate("ow_grow_time >", value, "owGrowTime");
            return (Criteria) this;
        }

        public Criteria andOwGrowTimeGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("ow_grow_time >=", value, "owGrowTime");
            return (Criteria) this;
        }

        public Criteria andOwGrowTimeLessThan(Date value) {
            addCriterionForJDBCDate("ow_grow_time <", value, "owGrowTime");
            return (Criteria) this;
        }

        public Criteria andOwGrowTimeLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("ow_grow_time <=", value, "owGrowTime");
            return (Criteria) this;
        }

        public Criteria andOwGrowTimeIn(List<Date> values) {
            addCriterionForJDBCDate("ow_grow_time in", values, "owGrowTime");
            return (Criteria) this;
        }

        public Criteria andOwGrowTimeNotIn(List<Date> values) {
            addCriterionForJDBCDate("ow_grow_time not in", values, "owGrowTime");
            return (Criteria) this;
        }

        public Criteria andOwGrowTimeBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("ow_grow_time between", value1, value2, "owGrowTime");
            return (Criteria) this;
        }

        public Criteria andOwGrowTimeNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("ow_grow_time not between", value1, value2, "owGrowTime");
            return (Criteria) this;
        }

        public Criteria andOwRemarkIsNull() {
            addCriterion("ow_remark is null");
            return (Criteria) this;
        }

        public Criteria andOwRemarkIsNotNull() {
            addCriterion("ow_remark is not null");
            return (Criteria) this;
        }

        public Criteria andOwRemarkEqualTo(String value) {
            addCriterion("ow_remark =", value, "owRemark");
            return (Criteria) this;
        }

        public Criteria andOwRemarkNotEqualTo(String value) {
            addCriterion("ow_remark <>", value, "owRemark");
            return (Criteria) this;
        }

        public Criteria andOwRemarkGreaterThan(String value) {
            addCriterion("ow_remark >", value, "owRemark");
            return (Criteria) this;
        }

        public Criteria andOwRemarkGreaterThanOrEqualTo(String value) {
            addCriterion("ow_remark >=", value, "owRemark");
            return (Criteria) this;
        }

        public Criteria andOwRemarkLessThan(String value) {
            addCriterion("ow_remark <", value, "owRemark");
            return (Criteria) this;
        }

        public Criteria andOwRemarkLessThanOrEqualTo(String value) {
            addCriterion("ow_remark <=", value, "owRemark");
            return (Criteria) this;
        }

        public Criteria andOwRemarkLike(String value) {
            addCriterion("ow_remark like", value, "owRemark");
            return (Criteria) this;
        }

        public Criteria andOwRemarkNotLike(String value) {
            addCriterion("ow_remark not like", value, "owRemark");
            return (Criteria) this;
        }

        public Criteria andOwRemarkIn(List<String> values) {
            addCriterion("ow_remark in", values, "owRemark");
            return (Criteria) this;
        }

        public Criteria andOwRemarkNotIn(List<String> values) {
            addCriterion("ow_remark not in", values, "owRemark");
            return (Criteria) this;
        }

        public Criteria andOwRemarkBetween(String value1, String value2) {
            addCriterion("ow_remark between", value1, value2, "owRemark");
            return (Criteria) this;
        }

        public Criteria andOwRemarkNotBetween(String value1, String value2) {
            addCriterion("ow_remark not between", value1, value2, "owRemark");
            return (Criteria) this;
        }

        public Criteria andDpGrowTimeIsNull() {
            addCriterion("dp_grow_time is null");
            return (Criteria) this;
        }

        public Criteria andDpGrowTimeIsNotNull() {
            addCriterion("dp_grow_time is not null");
            return (Criteria) this;
        }

        public Criteria andDpGrowTimeEqualTo(Date value) {
            addCriterionForJDBCDate("dp_grow_time =", value, "dpGrowTime");
            return (Criteria) this;
        }

        public Criteria andDpGrowTimeNotEqualTo(Date value) {
            addCriterionForJDBCDate("dp_grow_time <>", value, "dpGrowTime");
            return (Criteria) this;
        }

        public Criteria andDpGrowTimeGreaterThan(Date value) {
            addCriterionForJDBCDate("dp_grow_time >", value, "dpGrowTime");
            return (Criteria) this;
        }

        public Criteria andDpGrowTimeGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("dp_grow_time >=", value, "dpGrowTime");
            return (Criteria) this;
        }

        public Criteria andDpGrowTimeLessThan(Date value) {
            addCriterionForJDBCDate("dp_grow_time <", value, "dpGrowTime");
            return (Criteria) this;
        }

        public Criteria andDpGrowTimeLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("dp_grow_time <=", value, "dpGrowTime");
            return (Criteria) this;
        }

        public Criteria andDpGrowTimeIn(List<Date> values) {
            addCriterionForJDBCDate("dp_grow_time in", values, "dpGrowTime");
            return (Criteria) this;
        }

        public Criteria andDpGrowTimeNotIn(List<Date> values) {
            addCriterionForJDBCDate("dp_grow_time not in", values, "dpGrowTime");
            return (Criteria) this;
        }

        public Criteria andDpGrowTimeBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("dp_grow_time between", value1, value2, "dpGrowTime");
            return (Criteria) this;
        }

        public Criteria andDpGrowTimeNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("dp_grow_time not between", value1, value2, "dpGrowTime");
            return (Criteria) this;
        }

        public Criteria andDpTypeIdIsNull() {
            addCriterion("dp_type_id is null");
            return (Criteria) this;
        }

        public Criteria andDpTypeIdIsNotNull() {
            addCriterion("dp_type_id is not null");
            return (Criteria) this;
        }

        public Criteria andDpTypeIdEqualTo(Integer value) {
            addCriterion("dp_type_id =", value, "dpTypeId");
            return (Criteria) this;
        }

        public Criteria andDpTypeIdNotEqualTo(Integer value) {
            addCriterion("dp_type_id <>", value, "dpTypeId");
            return (Criteria) this;
        }

        public Criteria andDpTypeIdGreaterThan(Integer value) {
            addCriterion("dp_type_id >", value, "dpTypeId");
            return (Criteria) this;
        }

        public Criteria andDpTypeIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("dp_type_id >=", value, "dpTypeId");
            return (Criteria) this;
        }

        public Criteria andDpTypeIdLessThan(Integer value) {
            addCriterion("dp_type_id <", value, "dpTypeId");
            return (Criteria) this;
        }

        public Criteria andDpTypeIdLessThanOrEqualTo(Integer value) {
            addCriterion("dp_type_id <=", value, "dpTypeId");
            return (Criteria) this;
        }

        public Criteria andDpTypeIdIn(List<Integer> values) {
            addCriterion("dp_type_id in", values, "dpTypeId");
            return (Criteria) this;
        }

        public Criteria andDpTypeIdNotIn(List<Integer> values) {
            addCriterion("dp_type_id not in", values, "dpTypeId");
            return (Criteria) this;
        }

        public Criteria andDpTypeIdBetween(Integer value1, Integer value2) {
            addCriterion("dp_type_id between", value1, value2, "dpTypeId");
            return (Criteria) this;
        }

        public Criteria andDpTypeIdNotBetween(Integer value1, Integer value2) {
            addCriterion("dp_type_id not between", value1, value2, "dpTypeId");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }

        public Criteria addPermits(List<Integer> partyIdList, List<Integer> branchIdList) {

            if(ShiroHelper.isPermitted(SystemConstants.PERMISSION_PARTYVIEWALL))
                return this;

            if(partyIdList==null) partyIdList = new ArrayList<>();
            if(branchIdList==null) branchIdList = new ArrayList<>();

            if(!partyIdList.isEmpty() && !branchIdList.isEmpty())
                addCriterion("(group_party_id in(" + StringUtils.join(partyIdList, ",") + ") OR group_branch_id in(" + StringUtils.join(branchIdList, ",") + "))");
            if(partyIdList.isEmpty() && !branchIdList.isEmpty())
                andGroupBranchIdIn(branchIdList);
            if(branchIdList.isEmpty() && !partyIdList.isEmpty())
                andGroupPartyIdIn(partyIdList);
            if(branchIdList.isEmpty() && partyIdList.isEmpty())
                andUserIdIsNull();
            return this;
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