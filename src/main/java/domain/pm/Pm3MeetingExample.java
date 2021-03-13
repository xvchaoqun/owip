package domain.pm;

import org.apache.commons.lang3.StringUtils;
import shiro.ShiroHelper;
import sys.constants.RoleConstants;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Pm3MeetingExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public Pm3MeetingExample() {
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

        public Criteria andQuarterIsNull() {
            addCriterion("quarter is null");
            return (Criteria) this;
        }

        public Criteria andQuarterIsNotNull() {
            addCriterion("quarter is not null");
            return (Criteria) this;
        }

        public Criteria andQuarterEqualTo(Byte value) {
            addCriterion("quarter =", value, "quarter");
            return (Criteria) this;
        }

        public Criteria andQuarterNotEqualTo(Byte value) {
            addCriterion("quarter <>", value, "quarter");
            return (Criteria) this;
        }

        public Criteria andQuarterGreaterThan(Byte value) {
            addCriterion("quarter >", value, "quarter");
            return (Criteria) this;
        }

        public Criteria andQuarterGreaterThanOrEqualTo(Byte value) {
            addCriterion("quarter >=", value, "quarter");
            return (Criteria) this;
        }

        public Criteria andQuarterLessThan(Byte value) {
            addCriterion("quarter <", value, "quarter");
            return (Criteria) this;
        }

        public Criteria andQuarterLessThanOrEqualTo(Byte value) {
            addCriterion("quarter <=", value, "quarter");
            return (Criteria) this;
        }

        public Criteria andQuarterIn(List<Byte> values) {
            addCriterion("quarter in", values, "quarter");
            return (Criteria) this;
        }

        public Criteria andQuarterNotIn(List<Byte> values) {
            addCriterion("quarter not in", values, "quarter");
            return (Criteria) this;
        }

        public Criteria andQuarterBetween(Byte value1, Byte value2) {
            addCriterion("quarter between", value1, value2, "quarter");
            return (Criteria) this;
        }

        public Criteria andQuarterNotBetween(Byte value1, Byte value2) {
            addCriterion("quarter not between", value1, value2, "quarter");
            return (Criteria) this;
        }

        public Criteria andMonthIsNull() {
            addCriterion("month is null");
            return (Criteria) this;
        }

        public Criteria andMonthIsNotNull() {
            addCriterion("month is not null");
            return (Criteria) this;
        }

        public Criteria andMonthEqualTo(Integer value) {
            addCriterion("month =", value, "month");
            return (Criteria) this;
        }

        public Criteria andMonthNotEqualTo(Integer value) {
            addCriterion("month <>", value, "month");
            return (Criteria) this;
        }

        public Criteria andMonthGreaterThan(Integer value) {
            addCriterion("month >", value, "month");
            return (Criteria) this;
        }

        public Criteria andMonthGreaterThanOrEqualTo(Integer value) {
            addCriterion("month >=", value, "month");
            return (Criteria) this;
        }

        public Criteria andMonthLessThan(Integer value) {
            addCriterion("month <", value, "month");
            return (Criteria) this;
        }

        public Criteria andMonthLessThanOrEqualTo(Integer value) {
            addCriterion("month <=", value, "month");
            return (Criteria) this;
        }

        public Criteria andMonthIn(List<Integer> values) {
            addCriterion("month in", values, "month");
            return (Criteria) this;
        }

        public Criteria andMonthNotIn(List<Integer> values) {
            addCriterion("month not in", values, "month");
            return (Criteria) this;
        }

        public Criteria andMonthBetween(Integer value1, Integer value2) {
            addCriterion("month between", value1, value2, "month");
            return (Criteria) this;
        }

        public Criteria andMonthNotBetween(Integer value1, Integer value2) {
            addCriterion("month not between", value1, value2, "month");
            return (Criteria) this;
        }

        public Criteria andAddressIsNull() {
            addCriterion("address is null");
            return (Criteria) this;
        }

        public Criteria andAddressIsNotNull() {
            addCriterion("address is not null");
            return (Criteria) this;
        }

        public Criteria andAddressEqualTo(String value) {
            addCriterion("address =", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressNotEqualTo(String value) {
            addCriterion("address <>", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressGreaterThan(String value) {
            addCriterion("address >", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressGreaterThanOrEqualTo(String value) {
            addCriterion("address >=", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressLessThan(String value) {
            addCriterion("address <", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressLessThanOrEqualTo(String value) {
            addCriterion("address <=", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressLike(String value) {
            addCriterion("address like", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressNotLike(String value) {
            addCriterion("address not like", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressIn(List<String> values) {
            addCriterion("address in", values, "address");
            return (Criteria) this;
        }

        public Criteria andAddressNotIn(List<String> values) {
            addCriterion("address not in", values, "address");
            return (Criteria) this;
        }

        public Criteria andAddressBetween(String value1, String value2) {
            addCriterion("address between", value1, value2, "address");
            return (Criteria) this;
        }

        public Criteria andAddressNotBetween(String value1, String value2) {
            addCriterion("address not between", value1, value2, "address");
            return (Criteria) this;
        }

        public Criteria andPresenterIsNull() {
            addCriterion("presenter is null");
            return (Criteria) this;
        }

        public Criteria andPresenterIsNotNull() {
            addCriterion("presenter is not null");
            return (Criteria) this;
        }

        public Criteria andPresenterEqualTo(Integer value) {
            addCriterion("presenter =", value, "presenter");
            return (Criteria) this;
        }

        public Criteria andPresenterNotEqualTo(Integer value) {
            addCriterion("presenter <>", value, "presenter");
            return (Criteria) this;
        }

        public Criteria andPresenterGreaterThan(Integer value) {
            addCriterion("presenter >", value, "presenter");
            return (Criteria) this;
        }

        public Criteria andPresenterGreaterThanOrEqualTo(Integer value) {
            addCriterion("presenter >=", value, "presenter");
            return (Criteria) this;
        }

        public Criteria andPresenterLessThan(Integer value) {
            addCriterion("presenter <", value, "presenter");
            return (Criteria) this;
        }

        public Criteria andPresenterLessThanOrEqualTo(Integer value) {
            addCriterion("presenter <=", value, "presenter");
            return (Criteria) this;
        }

        public Criteria andPresenterIn(List<Integer> values) {
            addCriterion("presenter in", values, "presenter");
            return (Criteria) this;
        }

        public Criteria andPresenterNotIn(List<Integer> values) {
            addCriterion("presenter not in", values, "presenter");
            return (Criteria) this;
        }

        public Criteria andPresenterBetween(Integer value1, Integer value2) {
            addCriterion("presenter between", value1, value2, "presenter");
            return (Criteria) this;
        }

        public Criteria andPresenterNotBetween(Integer value1, Integer value2) {
            addCriterion("presenter not between", value1, value2, "presenter");
            return (Criteria) this;
        }

        public Criteria andRecorderIsNull() {
            addCriterion("recorder is null");
            return (Criteria) this;
        }

        public Criteria andRecorderIsNotNull() {
            addCriterion("recorder is not null");
            return (Criteria) this;
        }

        public Criteria andRecorderEqualTo(Integer value) {
            addCriterion("recorder =", value, "recorder");
            return (Criteria) this;
        }

        public Criteria andRecorderNotEqualTo(Integer value) {
            addCriterion("recorder <>", value, "recorder");
            return (Criteria) this;
        }

        public Criteria andRecorderGreaterThan(Integer value) {
            addCriterion("recorder >", value, "recorder");
            return (Criteria) this;
        }

        public Criteria andRecorderGreaterThanOrEqualTo(Integer value) {
            addCriterion("recorder >=", value, "recorder");
            return (Criteria) this;
        }

        public Criteria andRecorderLessThan(Integer value) {
            addCriterion("recorder <", value, "recorder");
            return (Criteria) this;
        }

        public Criteria andRecorderLessThanOrEqualTo(Integer value) {
            addCriterion("recorder <=", value, "recorder");
            return (Criteria) this;
        }

        public Criteria andRecorderIn(List<Integer> values) {
            addCriterion("recorder in", values, "recorder");
            return (Criteria) this;
        }

        public Criteria andRecorderNotIn(List<Integer> values) {
            addCriterion("recorder not in", values, "recorder");
            return (Criteria) this;
        }

        public Criteria andRecorderBetween(Integer value1, Integer value2) {
            addCriterion("recorder between", value1, value2, "recorder");
            return (Criteria) this;
        }

        public Criteria andRecorderNotBetween(Integer value1, Integer value2) {
            addCriterion("recorder not between", value1, value2, "recorder");
            return (Criteria) this;
        }

        public Criteria andAbsentsIsNull() {
            addCriterion("absents is null");
            return (Criteria) this;
        }

        public Criteria andAbsentsIsNotNull() {
            addCriterion("absents is not null");
            return (Criteria) this;
        }

        public Criteria andAbsentsEqualTo(String value) {
            addCriterion("absents =", value, "absents");
            return (Criteria) this;
        }

        public Criteria andAbsentsNotEqualTo(String value) {
            addCriterion("absents <>", value, "absents");
            return (Criteria) this;
        }

        public Criteria andAbsentsGreaterThan(String value) {
            addCriterion("absents >", value, "absents");
            return (Criteria) this;
        }

        public Criteria andAbsentsGreaterThanOrEqualTo(String value) {
            addCriterion("absents >=", value, "absents");
            return (Criteria) this;
        }

        public Criteria andAbsentsLessThan(String value) {
            addCriterion("absents <", value, "absents");
            return (Criteria) this;
        }

        public Criteria andAbsentsLessThanOrEqualTo(String value) {
            addCriterion("absents <=", value, "absents");
            return (Criteria) this;
        }

        public Criteria andAbsentsLike(String value) {
            addCriterion("absents like", value, "absents");
            return (Criteria) this;
        }

        public Criteria andAbsentsNotLike(String value) {
            addCriterion("absents not like", value, "absents");
            return (Criteria) this;
        }

        public Criteria andAbsentsIn(List<String> values) {
            addCriterion("absents in", values, "absents");
            return (Criteria) this;
        }

        public Criteria andAbsentsNotIn(List<String> values) {
            addCriterion("absents not in", values, "absents");
            return (Criteria) this;
        }

        public Criteria andAbsentsBetween(String value1, String value2) {
            addCriterion("absents between", value1, value2, "absents");
            return (Criteria) this;
        }

        public Criteria andAbsentsNotBetween(String value1, String value2) {
            addCriterion("absents not between", value1, value2, "absents");
            return (Criteria) this;
        }

        public Criteria andAbsentReasonIsNull() {
            addCriterion("absent_reason is null");
            return (Criteria) this;
        }

        public Criteria andAbsentReasonIsNotNull() {
            addCriterion("absent_reason is not null");
            return (Criteria) this;
        }

        public Criteria andAbsentReasonEqualTo(String value) {
            addCriterion("absent_reason =", value, "absentReason");
            return (Criteria) this;
        }

        public Criteria andAbsentReasonNotEqualTo(String value) {
            addCriterion("absent_reason <>", value, "absentReason");
            return (Criteria) this;
        }

        public Criteria andAbsentReasonGreaterThan(String value) {
            addCriterion("absent_reason >", value, "absentReason");
            return (Criteria) this;
        }

        public Criteria andAbsentReasonGreaterThanOrEqualTo(String value) {
            addCriterion("absent_reason >=", value, "absentReason");
            return (Criteria) this;
        }

        public Criteria andAbsentReasonLessThan(String value) {
            addCriterion("absent_reason <", value, "absentReason");
            return (Criteria) this;
        }

        public Criteria andAbsentReasonLessThanOrEqualTo(String value) {
            addCriterion("absent_reason <=", value, "absentReason");
            return (Criteria) this;
        }

        public Criteria andAbsentReasonLike(String value) {
            addCriterion("absent_reason like", value, "absentReason");
            return (Criteria) this;
        }

        public Criteria andAbsentReasonNotLike(String value) {
            addCriterion("absent_reason not like", value, "absentReason");
            return (Criteria) this;
        }

        public Criteria andAbsentReasonIn(List<String> values) {
            addCriterion("absent_reason in", values, "absentReason");
            return (Criteria) this;
        }

        public Criteria andAbsentReasonNotIn(List<String> values) {
            addCriterion("absent_reason not in", values, "absentReason");
            return (Criteria) this;
        }

        public Criteria andAbsentReasonBetween(String value1, String value2) {
            addCriterion("absent_reason between", value1, value2, "absentReason");
            return (Criteria) this;
        }

        public Criteria andAbsentReasonNotBetween(String value1, String value2) {
            addCriterion("absent_reason not between", value1, value2, "absentReason");
            return (Criteria) this;
        }

        public Criteria andInviteeIsNull() {
            addCriterion("Invitee is null");
            return (Criteria) this;
        }

        public Criteria andInviteeIsNotNull() {
            addCriterion("Invitee is not null");
            return (Criteria) this;
        }

        public Criteria andInviteeEqualTo(String value) {
            addCriterion("Invitee =", value, "invitee");
            return (Criteria) this;
        }

        public Criteria andInviteeNotEqualTo(String value) {
            addCriterion("Invitee <>", value, "invitee");
            return (Criteria) this;
        }

        public Criteria andInviteeGreaterThan(String value) {
            addCriterion("Invitee >", value, "invitee");
            return (Criteria) this;
        }

        public Criteria andInviteeGreaterThanOrEqualTo(String value) {
            addCriterion("Invitee >=", value, "invitee");
            return (Criteria) this;
        }

        public Criteria andInviteeLessThan(String value) {
            addCriterion("Invitee <", value, "invitee");
            return (Criteria) this;
        }

        public Criteria andInviteeLessThanOrEqualTo(String value) {
            addCriterion("Invitee <=", value, "invitee");
            return (Criteria) this;
        }

        public Criteria andInviteeLike(String value) {
            addCriterion("Invitee like", value, "invitee");
            return (Criteria) this;
        }

        public Criteria andInviteeNotLike(String value) {
            addCriterion("Invitee not like", value, "invitee");
            return (Criteria) this;
        }

        public Criteria andInviteeIn(List<String> values) {
            addCriterion("Invitee in", values, "invitee");
            return (Criteria) this;
        }

        public Criteria andInviteeNotIn(List<String> values) {
            addCriterion("Invitee not in", values, "invitee");
            return (Criteria) this;
        }

        public Criteria andInviteeBetween(String value1, String value2) {
            addCriterion("Invitee between", value1, value2, "invitee");
            return (Criteria) this;
        }

        public Criteria andInviteeNotBetween(String value1, String value2) {
            addCriterion("Invitee not between", value1, value2, "invitee");
            return (Criteria) this;
        }

        public Criteria andDueNumIsNull() {
            addCriterion("due_num is null");
            return (Criteria) this;
        }

        public Criteria andDueNumIsNotNull() {
            addCriterion("due_num is not null");
            return (Criteria) this;
        }

        public Criteria andDueNumEqualTo(Integer value) {
            addCriterion("due_num =", value, "dueNum");
            return (Criteria) this;
        }

        public Criteria andDueNumNotEqualTo(Integer value) {
            addCriterion("due_num <>", value, "dueNum");
            return (Criteria) this;
        }

        public Criteria andDueNumGreaterThan(Integer value) {
            addCriterion("due_num >", value, "dueNum");
            return (Criteria) this;
        }

        public Criteria andDueNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("due_num >=", value, "dueNum");
            return (Criteria) this;
        }

        public Criteria andDueNumLessThan(Integer value) {
            addCriterion("due_num <", value, "dueNum");
            return (Criteria) this;
        }

        public Criteria andDueNumLessThanOrEqualTo(Integer value) {
            addCriterion("due_num <=", value, "dueNum");
            return (Criteria) this;
        }

        public Criteria andDueNumIn(List<Integer> values) {
            addCriterion("due_num in", values, "dueNum");
            return (Criteria) this;
        }

        public Criteria andDueNumNotIn(List<Integer> values) {
            addCriterion("due_num not in", values, "dueNum");
            return (Criteria) this;
        }

        public Criteria andDueNumBetween(Integer value1, Integer value2) {
            addCriterion("due_num between", value1, value2, "dueNum");
            return (Criteria) this;
        }

        public Criteria andDueNumNotBetween(Integer value1, Integer value2) {
            addCriterion("due_num not between", value1, value2, "dueNum");
            return (Criteria) this;
        }

        public Criteria andAttendNumIsNull() {
            addCriterion("attend_num is null");
            return (Criteria) this;
        }

        public Criteria andAttendNumIsNotNull() {
            addCriterion("attend_num is not null");
            return (Criteria) this;
        }

        public Criteria andAttendNumEqualTo(Integer value) {
            addCriterion("attend_num =", value, "attendNum");
            return (Criteria) this;
        }

        public Criteria andAttendNumNotEqualTo(Integer value) {
            addCriterion("attend_num <>", value, "attendNum");
            return (Criteria) this;
        }

        public Criteria andAttendNumGreaterThan(Integer value) {
            addCriterion("attend_num >", value, "attendNum");
            return (Criteria) this;
        }

        public Criteria andAttendNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("attend_num >=", value, "attendNum");
            return (Criteria) this;
        }

        public Criteria andAttendNumLessThan(Integer value) {
            addCriterion("attend_num <", value, "attendNum");
            return (Criteria) this;
        }

        public Criteria andAttendNumLessThanOrEqualTo(Integer value) {
            addCriterion("attend_num <=", value, "attendNum");
            return (Criteria) this;
        }

        public Criteria andAttendNumIn(List<Integer> values) {
            addCriterion("attend_num in", values, "attendNum");
            return (Criteria) this;
        }

        public Criteria andAttendNumNotIn(List<Integer> values) {
            addCriterion("attend_num not in", values, "attendNum");
            return (Criteria) this;
        }

        public Criteria andAttendNumBetween(Integer value1, Integer value2) {
            addCriterion("attend_num between", value1, value2, "attendNum");
            return (Criteria) this;
        }

        public Criteria andAttendNumNotBetween(Integer value1, Integer value2) {
            addCriterion("attend_num not between", value1, value2, "attendNum");
            return (Criteria) this;
        }

        public Criteria andAbsentNumIsNull() {
            addCriterion("absent_num is null");
            return (Criteria) this;
        }

        public Criteria andAbsentNumIsNotNull() {
            addCriterion("absent_num is not null");
            return (Criteria) this;
        }

        public Criteria andAbsentNumEqualTo(Integer value) {
            addCriterion("absent_num =", value, "absentNum");
            return (Criteria) this;
        }

        public Criteria andAbsentNumNotEqualTo(Integer value) {
            addCriterion("absent_num <>", value, "absentNum");
            return (Criteria) this;
        }

        public Criteria andAbsentNumGreaterThan(Integer value) {
            addCriterion("absent_num >", value, "absentNum");
            return (Criteria) this;
        }

        public Criteria andAbsentNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("absent_num >=", value, "absentNum");
            return (Criteria) this;
        }

        public Criteria andAbsentNumLessThan(Integer value) {
            addCriterion("absent_num <", value, "absentNum");
            return (Criteria) this;
        }

        public Criteria andAbsentNumLessThanOrEqualTo(Integer value) {
            addCriterion("absent_num <=", value, "absentNum");
            return (Criteria) this;
        }

        public Criteria andAbsentNumIn(List<Integer> values) {
            addCriterion("absent_num in", values, "absentNum");
            return (Criteria) this;
        }

        public Criteria andAbsentNumNotIn(List<Integer> values) {
            addCriterion("absent_num not in", values, "absentNum");
            return (Criteria) this;
        }

        public Criteria andAbsentNumBetween(Integer value1, Integer value2) {
            addCriterion("absent_num between", value1, value2, "absentNum");
            return (Criteria) this;
        }

        public Criteria andAbsentNumNotBetween(Integer value1, Integer value2) {
            addCriterion("absent_num not between", value1, value2, "absentNum");
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

        public Criteria andCheckOpinionIsNull() {
            addCriterion("check_opinion is null");
            return (Criteria) this;
        }

        public Criteria andCheckOpinionIsNotNull() {
            addCriterion("check_opinion is not null");
            return (Criteria) this;
        }

        public Criteria andCheckOpinionEqualTo(String value) {
            addCriterion("check_opinion =", value, "checkOpinion");
            return (Criteria) this;
        }

        public Criteria andCheckOpinionNotEqualTo(String value) {
            addCriterion("check_opinion <>", value, "checkOpinion");
            return (Criteria) this;
        }

        public Criteria andCheckOpinionGreaterThan(String value) {
            addCriterion("check_opinion >", value, "checkOpinion");
            return (Criteria) this;
        }

        public Criteria andCheckOpinionGreaterThanOrEqualTo(String value) {
            addCriterion("check_opinion >=", value, "checkOpinion");
            return (Criteria) this;
        }

        public Criteria andCheckOpinionLessThan(String value) {
            addCriterion("check_opinion <", value, "checkOpinion");
            return (Criteria) this;
        }

        public Criteria andCheckOpinionLessThanOrEqualTo(String value) {
            addCriterion("check_opinion <=", value, "checkOpinion");
            return (Criteria) this;
        }

        public Criteria andCheckOpinionLike(String value) {
            addCriterion("check_opinion like", value, "checkOpinion");
            return (Criteria) this;
        }

        public Criteria andCheckOpinionNotLike(String value) {
            addCriterion("check_opinion not like", value, "checkOpinion");
            return (Criteria) this;
        }

        public Criteria andCheckOpinionIn(List<String> values) {
            addCriterion("check_opinion in", values, "checkOpinion");
            return (Criteria) this;
        }

        public Criteria andCheckOpinionNotIn(List<String> values) {
            addCriterion("check_opinion not in", values, "checkOpinion");
            return (Criteria) this;
        }

        public Criteria andCheckOpinionBetween(String value1, String value2) {
            addCriterion("check_opinion between", value1, value2, "checkOpinion");
            return (Criteria) this;
        }

        public Criteria andCheckOpinionNotBetween(String value1, String value2) {
            addCriterion("check_opinion not between", value1, value2, "checkOpinion");
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
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
        public Pm3MeetingExample.Criteria addPermits(List<Integer> partyIdList, List<Integer> branchIdList) {

            if(ShiroHelper.isPermitted(RoleConstants.PERMISSION_PARTYVIEWALL))
                return this;
            if(partyIdList==null) partyIdList = new ArrayList<>();
            if(branchIdList==null) branchIdList = new ArrayList<>();

            if(!partyIdList.isEmpty() && !branchIdList.isEmpty())
                addCriterion("(party_id in(" + StringUtils.join(partyIdList, ",") + ") or branch_id in(" + StringUtils.join(branchIdList, ",") + "))");
            if(partyIdList.isEmpty() && !branchIdList.isEmpty())
                andBranchIdIn(branchIdList);
            if(branchIdList.isEmpty() && !partyIdList.isEmpty())
                andPartyIdIn(partyIdList);

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