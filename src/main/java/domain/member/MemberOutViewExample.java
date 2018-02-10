package domain.member;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import sys.constants.RoleConstants;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class MemberOutViewExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public MemberOutViewExample() {
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

        public Criteria andPhoneIsNull() {
            addCriterion("phone is null");
            return (Criteria) this;
        }

        public Criteria andPhoneIsNotNull() {
            addCriterion("phone is not null");
            return (Criteria) this;
        }

        public Criteria andPhoneEqualTo(String value) {
            addCriterion("phone =", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneNotEqualTo(String value) {
            addCriterion("phone <>", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneGreaterThan(String value) {
            addCriterion("phone >", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneGreaterThanOrEqualTo(String value) {
            addCriterion("phone >=", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneLessThan(String value) {
            addCriterion("phone <", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneLessThanOrEqualTo(String value) {
            addCriterion("phone <=", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneLike(String value) {
            addCriterion("phone like", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneNotLike(String value) {
            addCriterion("phone not like", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneIn(List<String> values) {
            addCriterion("phone in", values, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneNotIn(List<String> values) {
            addCriterion("phone not in", values, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneBetween(String value1, String value2) {
            addCriterion("phone between", value1, value2, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneNotBetween(String value1, String value2) {
            addCriterion("phone not between", value1, value2, "phone");
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

        public Criteria andToTitleIsNull() {
            addCriterion("to_title is null");
            return (Criteria) this;
        }

        public Criteria andToTitleIsNotNull() {
            addCriterion("to_title is not null");
            return (Criteria) this;
        }

        public Criteria andToTitleEqualTo(String value) {
            addCriterion("to_title =", value, "toTitle");
            return (Criteria) this;
        }

        public Criteria andToTitleNotEqualTo(String value) {
            addCriterion("to_title <>", value, "toTitle");
            return (Criteria) this;
        }

        public Criteria andToTitleGreaterThan(String value) {
            addCriterion("to_title >", value, "toTitle");
            return (Criteria) this;
        }

        public Criteria andToTitleGreaterThanOrEqualTo(String value) {
            addCriterion("to_title >=", value, "toTitle");
            return (Criteria) this;
        }

        public Criteria andToTitleLessThan(String value) {
            addCriterion("to_title <", value, "toTitle");
            return (Criteria) this;
        }

        public Criteria andToTitleLessThanOrEqualTo(String value) {
            addCriterion("to_title <=", value, "toTitle");
            return (Criteria) this;
        }

        public Criteria andToTitleLike(String value) {
            addCriterion("to_title like", value, "toTitle");
            return (Criteria) this;
        }

        public Criteria andToTitleNotLike(String value) {
            addCriterion("to_title not like", value, "toTitle");
            return (Criteria) this;
        }

        public Criteria andToTitleIn(List<String> values) {
            addCriterion("to_title in", values, "toTitle");
            return (Criteria) this;
        }

        public Criteria andToTitleNotIn(List<String> values) {
            addCriterion("to_title not in", values, "toTitle");
            return (Criteria) this;
        }

        public Criteria andToTitleBetween(String value1, String value2) {
            addCriterion("to_title between", value1, value2, "toTitle");
            return (Criteria) this;
        }

        public Criteria andToTitleNotBetween(String value1, String value2) {
            addCriterion("to_title not between", value1, value2, "toTitle");
            return (Criteria) this;
        }

        public Criteria andToUnitIsNull() {
            addCriterion("to_unit is null");
            return (Criteria) this;
        }

        public Criteria andToUnitIsNotNull() {
            addCriterion("to_unit is not null");
            return (Criteria) this;
        }

        public Criteria andToUnitEqualTo(String value) {
            addCriterion("to_unit =", value, "toUnit");
            return (Criteria) this;
        }

        public Criteria andToUnitNotEqualTo(String value) {
            addCriterion("to_unit <>", value, "toUnit");
            return (Criteria) this;
        }

        public Criteria andToUnitGreaterThan(String value) {
            addCriterion("to_unit >", value, "toUnit");
            return (Criteria) this;
        }

        public Criteria andToUnitGreaterThanOrEqualTo(String value) {
            addCriterion("to_unit >=", value, "toUnit");
            return (Criteria) this;
        }

        public Criteria andToUnitLessThan(String value) {
            addCriterion("to_unit <", value, "toUnit");
            return (Criteria) this;
        }

        public Criteria andToUnitLessThanOrEqualTo(String value) {
            addCriterion("to_unit <=", value, "toUnit");
            return (Criteria) this;
        }

        public Criteria andToUnitLike(String value) {
            addCriterion("to_unit like", value, "toUnit");
            return (Criteria) this;
        }

        public Criteria andToUnitNotLike(String value) {
            addCriterion("to_unit not like", value, "toUnit");
            return (Criteria) this;
        }

        public Criteria andToUnitIn(List<String> values) {
            addCriterion("to_unit in", values, "toUnit");
            return (Criteria) this;
        }

        public Criteria andToUnitNotIn(List<String> values) {
            addCriterion("to_unit not in", values, "toUnit");
            return (Criteria) this;
        }

        public Criteria andToUnitBetween(String value1, String value2) {
            addCriterion("to_unit between", value1, value2, "toUnit");
            return (Criteria) this;
        }

        public Criteria andToUnitNotBetween(String value1, String value2) {
            addCriterion("to_unit not between", value1, value2, "toUnit");
            return (Criteria) this;
        }

        public Criteria andFromUnitIsNull() {
            addCriterion("from_unit is null");
            return (Criteria) this;
        }

        public Criteria andFromUnitIsNotNull() {
            addCriterion("from_unit is not null");
            return (Criteria) this;
        }

        public Criteria andFromUnitEqualTo(String value) {
            addCriterion("from_unit =", value, "fromUnit");
            return (Criteria) this;
        }

        public Criteria andFromUnitNotEqualTo(String value) {
            addCriterion("from_unit <>", value, "fromUnit");
            return (Criteria) this;
        }

        public Criteria andFromUnitGreaterThan(String value) {
            addCriterion("from_unit >", value, "fromUnit");
            return (Criteria) this;
        }

        public Criteria andFromUnitGreaterThanOrEqualTo(String value) {
            addCriterion("from_unit >=", value, "fromUnit");
            return (Criteria) this;
        }

        public Criteria andFromUnitLessThan(String value) {
            addCriterion("from_unit <", value, "fromUnit");
            return (Criteria) this;
        }

        public Criteria andFromUnitLessThanOrEqualTo(String value) {
            addCriterion("from_unit <=", value, "fromUnit");
            return (Criteria) this;
        }

        public Criteria andFromUnitLike(String value) {
            addCriterion("from_unit like", value, "fromUnit");
            return (Criteria) this;
        }

        public Criteria andFromUnitNotLike(String value) {
            addCriterion("from_unit not like", value, "fromUnit");
            return (Criteria) this;
        }

        public Criteria andFromUnitIn(List<String> values) {
            addCriterion("from_unit in", values, "fromUnit");
            return (Criteria) this;
        }

        public Criteria andFromUnitNotIn(List<String> values) {
            addCriterion("from_unit not in", values, "fromUnit");
            return (Criteria) this;
        }

        public Criteria andFromUnitBetween(String value1, String value2) {
            addCriterion("from_unit between", value1, value2, "fromUnit");
            return (Criteria) this;
        }

        public Criteria andFromUnitNotBetween(String value1, String value2) {
            addCriterion("from_unit not between", value1, value2, "fromUnit");
            return (Criteria) this;
        }

        public Criteria andFromAddressIsNull() {
            addCriterion("from_address is null");
            return (Criteria) this;
        }

        public Criteria andFromAddressIsNotNull() {
            addCriterion("from_address is not null");
            return (Criteria) this;
        }

        public Criteria andFromAddressEqualTo(String value) {
            addCriterion("from_address =", value, "fromAddress");
            return (Criteria) this;
        }

        public Criteria andFromAddressNotEqualTo(String value) {
            addCriterion("from_address <>", value, "fromAddress");
            return (Criteria) this;
        }

        public Criteria andFromAddressGreaterThan(String value) {
            addCriterion("from_address >", value, "fromAddress");
            return (Criteria) this;
        }

        public Criteria andFromAddressGreaterThanOrEqualTo(String value) {
            addCriterion("from_address >=", value, "fromAddress");
            return (Criteria) this;
        }

        public Criteria andFromAddressLessThan(String value) {
            addCriterion("from_address <", value, "fromAddress");
            return (Criteria) this;
        }

        public Criteria andFromAddressLessThanOrEqualTo(String value) {
            addCriterion("from_address <=", value, "fromAddress");
            return (Criteria) this;
        }

        public Criteria andFromAddressLike(String value) {
            addCriterion("from_address like", value, "fromAddress");
            return (Criteria) this;
        }

        public Criteria andFromAddressNotLike(String value) {
            addCriterion("from_address not like", value, "fromAddress");
            return (Criteria) this;
        }

        public Criteria andFromAddressIn(List<String> values) {
            addCriterion("from_address in", values, "fromAddress");
            return (Criteria) this;
        }

        public Criteria andFromAddressNotIn(List<String> values) {
            addCriterion("from_address not in", values, "fromAddress");
            return (Criteria) this;
        }

        public Criteria andFromAddressBetween(String value1, String value2) {
            addCriterion("from_address between", value1, value2, "fromAddress");
            return (Criteria) this;
        }

        public Criteria andFromAddressNotBetween(String value1, String value2) {
            addCriterion("from_address not between", value1, value2, "fromAddress");
            return (Criteria) this;
        }

        public Criteria andFromPhoneIsNull() {
            addCriterion("from_phone is null");
            return (Criteria) this;
        }

        public Criteria andFromPhoneIsNotNull() {
            addCriterion("from_phone is not null");
            return (Criteria) this;
        }

        public Criteria andFromPhoneEqualTo(String value) {
            addCriterion("from_phone =", value, "fromPhone");
            return (Criteria) this;
        }

        public Criteria andFromPhoneNotEqualTo(String value) {
            addCriterion("from_phone <>", value, "fromPhone");
            return (Criteria) this;
        }

        public Criteria andFromPhoneGreaterThan(String value) {
            addCriterion("from_phone >", value, "fromPhone");
            return (Criteria) this;
        }

        public Criteria andFromPhoneGreaterThanOrEqualTo(String value) {
            addCriterion("from_phone >=", value, "fromPhone");
            return (Criteria) this;
        }

        public Criteria andFromPhoneLessThan(String value) {
            addCriterion("from_phone <", value, "fromPhone");
            return (Criteria) this;
        }

        public Criteria andFromPhoneLessThanOrEqualTo(String value) {
            addCriterion("from_phone <=", value, "fromPhone");
            return (Criteria) this;
        }

        public Criteria andFromPhoneLike(String value) {
            addCriterion("from_phone like", value, "fromPhone");
            return (Criteria) this;
        }

        public Criteria andFromPhoneNotLike(String value) {
            addCriterion("from_phone not like", value, "fromPhone");
            return (Criteria) this;
        }

        public Criteria andFromPhoneIn(List<String> values) {
            addCriterion("from_phone in", values, "fromPhone");
            return (Criteria) this;
        }

        public Criteria andFromPhoneNotIn(List<String> values) {
            addCriterion("from_phone not in", values, "fromPhone");
            return (Criteria) this;
        }

        public Criteria andFromPhoneBetween(String value1, String value2) {
            addCriterion("from_phone between", value1, value2, "fromPhone");
            return (Criteria) this;
        }

        public Criteria andFromPhoneNotBetween(String value1, String value2) {
            addCriterion("from_phone not between", value1, value2, "fromPhone");
            return (Criteria) this;
        }

        public Criteria andFromFaxIsNull() {
            addCriterion("from_fax is null");
            return (Criteria) this;
        }

        public Criteria andFromFaxIsNotNull() {
            addCriterion("from_fax is not null");
            return (Criteria) this;
        }

        public Criteria andFromFaxEqualTo(String value) {
            addCriterion("from_fax =", value, "fromFax");
            return (Criteria) this;
        }

        public Criteria andFromFaxNotEqualTo(String value) {
            addCriterion("from_fax <>", value, "fromFax");
            return (Criteria) this;
        }

        public Criteria andFromFaxGreaterThan(String value) {
            addCriterion("from_fax >", value, "fromFax");
            return (Criteria) this;
        }

        public Criteria andFromFaxGreaterThanOrEqualTo(String value) {
            addCriterion("from_fax >=", value, "fromFax");
            return (Criteria) this;
        }

        public Criteria andFromFaxLessThan(String value) {
            addCriterion("from_fax <", value, "fromFax");
            return (Criteria) this;
        }

        public Criteria andFromFaxLessThanOrEqualTo(String value) {
            addCriterion("from_fax <=", value, "fromFax");
            return (Criteria) this;
        }

        public Criteria andFromFaxLike(String value) {
            addCriterion("from_fax like", value, "fromFax");
            return (Criteria) this;
        }

        public Criteria andFromFaxNotLike(String value) {
            addCriterion("from_fax not like", value, "fromFax");
            return (Criteria) this;
        }

        public Criteria andFromFaxIn(List<String> values) {
            addCriterion("from_fax in", values, "fromFax");
            return (Criteria) this;
        }

        public Criteria andFromFaxNotIn(List<String> values) {
            addCriterion("from_fax not in", values, "fromFax");
            return (Criteria) this;
        }

        public Criteria andFromFaxBetween(String value1, String value2) {
            addCriterion("from_fax between", value1, value2, "fromFax");
            return (Criteria) this;
        }

        public Criteria andFromFaxNotBetween(String value1, String value2) {
            addCriterion("from_fax not between", value1, value2, "fromFax");
            return (Criteria) this;
        }

        public Criteria andFromPostCodeIsNull() {
            addCriterion("from_post_code is null");
            return (Criteria) this;
        }

        public Criteria andFromPostCodeIsNotNull() {
            addCriterion("from_post_code is not null");
            return (Criteria) this;
        }

        public Criteria andFromPostCodeEqualTo(String value) {
            addCriterion("from_post_code =", value, "fromPostCode");
            return (Criteria) this;
        }

        public Criteria andFromPostCodeNotEqualTo(String value) {
            addCriterion("from_post_code <>", value, "fromPostCode");
            return (Criteria) this;
        }

        public Criteria andFromPostCodeGreaterThan(String value) {
            addCriterion("from_post_code >", value, "fromPostCode");
            return (Criteria) this;
        }

        public Criteria andFromPostCodeGreaterThanOrEqualTo(String value) {
            addCriterion("from_post_code >=", value, "fromPostCode");
            return (Criteria) this;
        }

        public Criteria andFromPostCodeLessThan(String value) {
            addCriterion("from_post_code <", value, "fromPostCode");
            return (Criteria) this;
        }

        public Criteria andFromPostCodeLessThanOrEqualTo(String value) {
            addCriterion("from_post_code <=", value, "fromPostCode");
            return (Criteria) this;
        }

        public Criteria andFromPostCodeLike(String value) {
            addCriterion("from_post_code like", value, "fromPostCode");
            return (Criteria) this;
        }

        public Criteria andFromPostCodeNotLike(String value) {
            addCriterion("from_post_code not like", value, "fromPostCode");
            return (Criteria) this;
        }

        public Criteria andFromPostCodeIn(List<String> values) {
            addCriterion("from_post_code in", values, "fromPostCode");
            return (Criteria) this;
        }

        public Criteria andFromPostCodeNotIn(List<String> values) {
            addCriterion("from_post_code not in", values, "fromPostCode");
            return (Criteria) this;
        }

        public Criteria andFromPostCodeBetween(String value1, String value2) {
            addCriterion("from_post_code between", value1, value2, "fromPostCode");
            return (Criteria) this;
        }

        public Criteria andFromPostCodeNotBetween(String value1, String value2) {
            addCriterion("from_post_code not between", value1, value2, "fromPostCode");
            return (Criteria) this;
        }

        public Criteria andPayTimeIsNull() {
            addCriterion("pay_time is null");
            return (Criteria) this;
        }

        public Criteria andPayTimeIsNotNull() {
            addCriterion("pay_time is not null");
            return (Criteria) this;
        }

        public Criteria andPayTimeEqualTo(Date value) {
            addCriterionForJDBCDate("pay_time =", value, "payTime");
            return (Criteria) this;
        }

        public Criteria andPayTimeNotEqualTo(Date value) {
            addCriterionForJDBCDate("pay_time <>", value, "payTime");
            return (Criteria) this;
        }

        public Criteria andPayTimeGreaterThan(Date value) {
            addCriterionForJDBCDate("pay_time >", value, "payTime");
            return (Criteria) this;
        }

        public Criteria andPayTimeGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("pay_time >=", value, "payTime");
            return (Criteria) this;
        }

        public Criteria andPayTimeLessThan(Date value) {
            addCriterionForJDBCDate("pay_time <", value, "payTime");
            return (Criteria) this;
        }

        public Criteria andPayTimeLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("pay_time <=", value, "payTime");
            return (Criteria) this;
        }

        public Criteria andPayTimeIn(List<Date> values) {
            addCriterionForJDBCDate("pay_time in", values, "payTime");
            return (Criteria) this;
        }

        public Criteria andPayTimeNotIn(List<Date> values) {
            addCriterionForJDBCDate("pay_time not in", values, "payTime");
            return (Criteria) this;
        }

        public Criteria andPayTimeBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("pay_time between", value1, value2, "payTime");
            return (Criteria) this;
        }

        public Criteria andPayTimeNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("pay_time not between", value1, value2, "payTime");
            return (Criteria) this;
        }

        public Criteria andValidDaysIsNull() {
            addCriterion("valid_days is null");
            return (Criteria) this;
        }

        public Criteria andValidDaysIsNotNull() {
            addCriterion("valid_days is not null");
            return (Criteria) this;
        }

        public Criteria andValidDaysEqualTo(Integer value) {
            addCriterion("valid_days =", value, "validDays");
            return (Criteria) this;
        }

        public Criteria andValidDaysNotEqualTo(Integer value) {
            addCriterion("valid_days <>", value, "validDays");
            return (Criteria) this;
        }

        public Criteria andValidDaysGreaterThan(Integer value) {
            addCriterion("valid_days >", value, "validDays");
            return (Criteria) this;
        }

        public Criteria andValidDaysGreaterThanOrEqualTo(Integer value) {
            addCriterion("valid_days >=", value, "validDays");
            return (Criteria) this;
        }

        public Criteria andValidDaysLessThan(Integer value) {
            addCriterion("valid_days <", value, "validDays");
            return (Criteria) this;
        }

        public Criteria andValidDaysLessThanOrEqualTo(Integer value) {
            addCriterion("valid_days <=", value, "validDays");
            return (Criteria) this;
        }

        public Criteria andValidDaysIn(List<Integer> values) {
            addCriterion("valid_days in", values, "validDays");
            return (Criteria) this;
        }

        public Criteria andValidDaysNotIn(List<Integer> values) {
            addCriterion("valid_days not in", values, "validDays");
            return (Criteria) this;
        }

        public Criteria andValidDaysBetween(Integer value1, Integer value2) {
            addCriterion("valid_days between", value1, value2, "validDays");
            return (Criteria) this;
        }

        public Criteria andValidDaysNotBetween(Integer value1, Integer value2) {
            addCriterion("valid_days not between", value1, value2, "validDays");
            return (Criteria) this;
        }

        public Criteria andHandleTimeIsNull() {
            addCriterion("handle_time is null");
            return (Criteria) this;
        }

        public Criteria andHandleTimeIsNotNull() {
            addCriterion("handle_time is not null");
            return (Criteria) this;
        }

        public Criteria andHandleTimeEqualTo(Date value) {
            addCriterionForJDBCDate("handle_time =", value, "handleTime");
            return (Criteria) this;
        }

        public Criteria andHandleTimeNotEqualTo(Date value) {
            addCriterionForJDBCDate("handle_time <>", value, "handleTime");
            return (Criteria) this;
        }

        public Criteria andHandleTimeGreaterThan(Date value) {
            addCriterionForJDBCDate("handle_time >", value, "handleTime");
            return (Criteria) this;
        }

        public Criteria andHandleTimeGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("handle_time >=", value, "handleTime");
            return (Criteria) this;
        }

        public Criteria andHandleTimeLessThan(Date value) {
            addCriterionForJDBCDate("handle_time <", value, "handleTime");
            return (Criteria) this;
        }

        public Criteria andHandleTimeLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("handle_time <=", value, "handleTime");
            return (Criteria) this;
        }

        public Criteria andHandleTimeIn(List<Date> values) {
            addCriterionForJDBCDate("handle_time in", values, "handleTime");
            return (Criteria) this;
        }

        public Criteria andHandleTimeNotIn(List<Date> values) {
            addCriterionForJDBCDate("handle_time not in", values, "handleTime");
            return (Criteria) this;
        }

        public Criteria andHandleTimeBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("handle_time between", value1, value2, "handleTime");
            return (Criteria) this;
        }

        public Criteria andHandleTimeNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("handle_time not between", value1, value2, "handleTime");
            return (Criteria) this;
        }

        public Criteria andHasReceiptIsNull() {
            addCriterion("has_receipt is null");
            return (Criteria) this;
        }

        public Criteria andHasReceiptIsNotNull() {
            addCriterion("has_receipt is not null");
            return (Criteria) this;
        }

        public Criteria andHasReceiptEqualTo(Boolean value) {
            addCriterion("has_receipt =", value, "hasReceipt");
            return (Criteria) this;
        }

        public Criteria andHasReceiptNotEqualTo(Boolean value) {
            addCriterion("has_receipt <>", value, "hasReceipt");
            return (Criteria) this;
        }

        public Criteria andHasReceiptGreaterThan(Boolean value) {
            addCriterion("has_receipt >", value, "hasReceipt");
            return (Criteria) this;
        }

        public Criteria andHasReceiptGreaterThanOrEqualTo(Boolean value) {
            addCriterion("has_receipt >=", value, "hasReceipt");
            return (Criteria) this;
        }

        public Criteria andHasReceiptLessThan(Boolean value) {
            addCriterion("has_receipt <", value, "hasReceipt");
            return (Criteria) this;
        }

        public Criteria andHasReceiptLessThanOrEqualTo(Boolean value) {
            addCriterion("has_receipt <=", value, "hasReceipt");
            return (Criteria) this;
        }

        public Criteria andHasReceiptIn(List<Boolean> values) {
            addCriterion("has_receipt in", values, "hasReceipt");
            return (Criteria) this;
        }

        public Criteria andHasReceiptNotIn(List<Boolean> values) {
            addCriterion("has_receipt not in", values, "hasReceipt");
            return (Criteria) this;
        }

        public Criteria andHasReceiptBetween(Boolean value1, Boolean value2) {
            addCriterion("has_receipt between", value1, value2, "hasReceipt");
            return (Criteria) this;
        }

        public Criteria andHasReceiptNotBetween(Boolean value1, Boolean value2) {
            addCriterion("has_receipt not between", value1, value2, "hasReceipt");
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

        public Criteria andIsModifyIsNull() {
            addCriterion("is_modify is null");
            return (Criteria) this;
        }

        public Criteria andIsModifyIsNotNull() {
            addCriterion("is_modify is not null");
            return (Criteria) this;
        }

        public Criteria andIsModifyEqualTo(Boolean value) {
            addCriterion("is_modify =", value, "isModify");
            return (Criteria) this;
        }

        public Criteria andIsModifyNotEqualTo(Boolean value) {
            addCriterion("is_modify <>", value, "isModify");
            return (Criteria) this;
        }

        public Criteria andIsModifyGreaterThan(Boolean value) {
            addCriterion("is_modify >", value, "isModify");
            return (Criteria) this;
        }

        public Criteria andIsModifyGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_modify >=", value, "isModify");
            return (Criteria) this;
        }

        public Criteria andIsModifyLessThan(Boolean value) {
            addCriterion("is_modify <", value, "isModify");
            return (Criteria) this;
        }

        public Criteria andIsModifyLessThanOrEqualTo(Boolean value) {
            addCriterion("is_modify <=", value, "isModify");
            return (Criteria) this;
        }

        public Criteria andIsModifyIn(List<Boolean> values) {
            addCriterion("is_modify in", values, "isModify");
            return (Criteria) this;
        }

        public Criteria andIsModifyNotIn(List<Boolean> values) {
            addCriterion("is_modify not in", values, "isModify");
            return (Criteria) this;
        }

        public Criteria andIsModifyBetween(Boolean value1, Boolean value2) {
            addCriterion("is_modify between", value1, value2, "isModify");
            return (Criteria) this;
        }

        public Criteria andIsModifyNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_modify not between", value1, value2, "isModify");
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

        public Criteria andReasonIsNull() {
            addCriterion("reason is null");
            return (Criteria) this;
        }

        public Criteria andReasonIsNotNull() {
            addCriterion("reason is not null");
            return (Criteria) this;
        }

        public Criteria andReasonEqualTo(String value) {
            addCriterion("reason =", value, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonNotEqualTo(String value) {
            addCriterion("reason <>", value, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonGreaterThan(String value) {
            addCriterion("reason >", value, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonGreaterThanOrEqualTo(String value) {
            addCriterion("reason >=", value, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonLessThan(String value) {
            addCriterion("reason <", value, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonLessThanOrEqualTo(String value) {
            addCriterion("reason <=", value, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonLike(String value) {
            addCriterion("reason like", value, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonNotLike(String value) {
            addCriterion("reason not like", value, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonIn(List<String> values) {
            addCriterion("reason in", values, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonNotIn(List<String> values) {
            addCriterion("reason not in", values, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonBetween(String value1, String value2) {
            addCriterion("reason between", value1, value2, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonNotBetween(String value1, String value2) {
            addCriterion("reason not between", value1, value2, "reason");
            return (Criteria) this;
        }

        public Criteria andApplyTimeIsNull() {
            addCriterion("apply_time is null");
            return (Criteria) this;
        }

        public Criteria andApplyTimeIsNotNull() {
            addCriterion("apply_time is not null");
            return (Criteria) this;
        }

        public Criteria andApplyTimeEqualTo(Date value) {
            addCriterion("apply_time =", value, "applyTime");
            return (Criteria) this;
        }

        public Criteria andApplyTimeNotEqualTo(Date value) {
            addCriterion("apply_time <>", value, "applyTime");
            return (Criteria) this;
        }

        public Criteria andApplyTimeGreaterThan(Date value) {
            addCriterion("apply_time >", value, "applyTime");
            return (Criteria) this;
        }

        public Criteria andApplyTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("apply_time >=", value, "applyTime");
            return (Criteria) this;
        }

        public Criteria andApplyTimeLessThan(Date value) {
            addCriterion("apply_time <", value, "applyTime");
            return (Criteria) this;
        }

        public Criteria andApplyTimeLessThanOrEqualTo(Date value) {
            addCriterion("apply_time <=", value, "applyTime");
            return (Criteria) this;
        }

        public Criteria andApplyTimeIn(List<Date> values) {
            addCriterion("apply_time in", values, "applyTime");
            return (Criteria) this;
        }

        public Criteria andApplyTimeNotIn(List<Date> values) {
            addCriterion("apply_time not in", values, "applyTime");
            return (Criteria) this;
        }

        public Criteria andApplyTimeBetween(Date value1, Date value2) {
            addCriterion("apply_time between", value1, value2, "applyTime");
            return (Criteria) this;
        }

        public Criteria andApplyTimeNotBetween(Date value1, Date value2) {
            addCriterion("apply_time not between", value1, value2, "applyTime");
            return (Criteria) this;
        }

        public Criteria andPrintCountIsNull() {
            addCriterion("print_count is null");
            return (Criteria) this;
        }

        public Criteria andPrintCountIsNotNull() {
            addCriterion("print_count is not null");
            return (Criteria) this;
        }

        public Criteria andPrintCountEqualTo(Integer value) {
            addCriterion("print_count =", value, "printCount");
            return (Criteria) this;
        }

        public Criteria andPrintCountNotEqualTo(Integer value) {
            addCriterion("print_count <>", value, "printCount");
            return (Criteria) this;
        }

        public Criteria andPrintCountGreaterThan(Integer value) {
            addCriterion("print_count >", value, "printCount");
            return (Criteria) this;
        }

        public Criteria andPrintCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("print_count >=", value, "printCount");
            return (Criteria) this;
        }

        public Criteria andPrintCountLessThan(Integer value) {
            addCriterion("print_count <", value, "printCount");
            return (Criteria) this;
        }

        public Criteria andPrintCountLessThanOrEqualTo(Integer value) {
            addCriterion("print_count <=", value, "printCount");
            return (Criteria) this;
        }

        public Criteria andPrintCountIn(List<Integer> values) {
            addCriterion("print_count in", values, "printCount");
            return (Criteria) this;
        }

        public Criteria andPrintCountNotIn(List<Integer> values) {
            addCriterion("print_count not in", values, "printCount");
            return (Criteria) this;
        }

        public Criteria andPrintCountBetween(Integer value1, Integer value2) {
            addCriterion("print_count between", value1, value2, "printCount");
            return (Criteria) this;
        }

        public Criteria andPrintCountNotBetween(Integer value1, Integer value2) {
            addCriterion("print_count not between", value1, value2, "printCount");
            return (Criteria) this;
        }

        public Criteria andLastPrintTimeIsNull() {
            addCriterion("last_print_time is null");
            return (Criteria) this;
        }

        public Criteria andLastPrintTimeIsNotNull() {
            addCriterion("last_print_time is not null");
            return (Criteria) this;
        }

        public Criteria andLastPrintTimeEqualTo(Date value) {
            addCriterion("last_print_time =", value, "lastPrintTime");
            return (Criteria) this;
        }

        public Criteria andLastPrintTimeNotEqualTo(Date value) {
            addCriterion("last_print_time <>", value, "lastPrintTime");
            return (Criteria) this;
        }

        public Criteria andLastPrintTimeGreaterThan(Date value) {
            addCriterion("last_print_time >", value, "lastPrintTime");
            return (Criteria) this;
        }

        public Criteria andLastPrintTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("last_print_time >=", value, "lastPrintTime");
            return (Criteria) this;
        }

        public Criteria andLastPrintTimeLessThan(Date value) {
            addCriterion("last_print_time <", value, "lastPrintTime");
            return (Criteria) this;
        }

        public Criteria andLastPrintTimeLessThanOrEqualTo(Date value) {
            addCriterion("last_print_time <=", value, "lastPrintTime");
            return (Criteria) this;
        }

        public Criteria andLastPrintTimeIn(List<Date> values) {
            addCriterion("last_print_time in", values, "lastPrintTime");
            return (Criteria) this;
        }

        public Criteria andLastPrintTimeNotIn(List<Date> values) {
            addCriterion("last_print_time not in", values, "lastPrintTime");
            return (Criteria) this;
        }

        public Criteria andLastPrintTimeBetween(Date value1, Date value2) {
            addCriterion("last_print_time between", value1, value2, "lastPrintTime");
            return (Criteria) this;
        }

        public Criteria andLastPrintTimeNotBetween(Date value1, Date value2) {
            addCriterion("last_print_time not between", value1, value2, "lastPrintTime");
            return (Criteria) this;
        }

        public Criteria andLastPrintUserIdIsNull() {
            addCriterion("last_print_user_id is null");
            return (Criteria) this;
        }

        public Criteria andLastPrintUserIdIsNotNull() {
            addCriterion("last_print_user_id is not null");
            return (Criteria) this;
        }

        public Criteria andLastPrintUserIdEqualTo(Integer value) {
            addCriterion("last_print_user_id =", value, "lastPrintUserId");
            return (Criteria) this;
        }

        public Criteria andLastPrintUserIdNotEqualTo(Integer value) {
            addCriterion("last_print_user_id <>", value, "lastPrintUserId");
            return (Criteria) this;
        }

        public Criteria andLastPrintUserIdGreaterThan(Integer value) {
            addCriterion("last_print_user_id >", value, "lastPrintUserId");
            return (Criteria) this;
        }

        public Criteria andLastPrintUserIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("last_print_user_id >=", value, "lastPrintUserId");
            return (Criteria) this;
        }

        public Criteria andLastPrintUserIdLessThan(Integer value) {
            addCriterion("last_print_user_id <", value, "lastPrintUserId");
            return (Criteria) this;
        }

        public Criteria andLastPrintUserIdLessThanOrEqualTo(Integer value) {
            addCriterion("last_print_user_id <=", value, "lastPrintUserId");
            return (Criteria) this;
        }

        public Criteria andLastPrintUserIdIn(List<Integer> values) {
            addCriterion("last_print_user_id in", values, "lastPrintUserId");
            return (Criteria) this;
        }

        public Criteria andLastPrintUserIdNotIn(List<Integer> values) {
            addCriterion("last_print_user_id not in", values, "lastPrintUserId");
            return (Criteria) this;
        }

        public Criteria andLastPrintUserIdBetween(Integer value1, Integer value2) {
            addCriterion("last_print_user_id between", value1, value2, "lastPrintUserId");
            return (Criteria) this;
        }

        public Criteria andLastPrintUserIdNotBetween(Integer value1, Integer value2) {
            addCriterion("last_print_user_id not between", value1, value2, "lastPrintUserId");
            return (Criteria) this;
        }

        public Criteria andMemberTypeIsNull() {
            addCriterion("member_type is null");
            return (Criteria) this;
        }

        public Criteria andMemberTypeIsNotNull() {
            addCriterion("member_type is not null");
            return (Criteria) this;
        }

        public Criteria andMemberTypeEqualTo(Byte value) {
            addCriterion("member_type =", value, "memberType");
            return (Criteria) this;
        }

        public Criteria andMemberTypeNotEqualTo(Byte value) {
            addCriterion("member_type <>", value, "memberType");
            return (Criteria) this;
        }

        public Criteria andMemberTypeGreaterThan(Byte value) {
            addCriterion("member_type >", value, "memberType");
            return (Criteria) this;
        }

        public Criteria andMemberTypeGreaterThanOrEqualTo(Byte value) {
            addCriterion("member_type >=", value, "memberType");
            return (Criteria) this;
        }

        public Criteria andMemberTypeLessThan(Byte value) {
            addCriterion("member_type <", value, "memberType");
            return (Criteria) this;
        }

        public Criteria andMemberTypeLessThanOrEqualTo(Byte value) {
            addCriterion("member_type <=", value, "memberType");
            return (Criteria) this;
        }

        public Criteria andMemberTypeIn(List<Byte> values) {
            addCriterion("member_type in", values, "memberType");
            return (Criteria) this;
        }

        public Criteria andMemberTypeNotIn(List<Byte> values) {
            addCriterion("member_type not in", values, "memberType");
            return (Criteria) this;
        }

        public Criteria andMemberTypeBetween(Byte value1, Byte value2) {
            addCriterion("member_type between", value1, value2, "memberType");
            return (Criteria) this;
        }

        public Criteria andMemberTypeNotBetween(Byte value1, Byte value2) {
            addCriterion("member_type not between", value1, value2, "memberType");
            return (Criteria) this;
        }

        public Criteria andIsRetireIsNull() {
            addCriterion("is_retire is null");
            return (Criteria) this;
        }

        public Criteria andIsRetireIsNotNull() {
            addCriterion("is_retire is not null");
            return (Criteria) this;
        }

        public Criteria andIsRetireEqualTo(Boolean value) {
            addCriterion("is_retire =", value, "isRetire");
            return (Criteria) this;
        }

        public Criteria andIsRetireNotEqualTo(Boolean value) {
            addCriterion("is_retire <>", value, "isRetire");
            return (Criteria) this;
        }

        public Criteria andIsRetireGreaterThan(Boolean value) {
            addCriterion("is_retire >", value, "isRetire");
            return (Criteria) this;
        }

        public Criteria andIsRetireGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_retire >=", value, "isRetire");
            return (Criteria) this;
        }

        public Criteria andIsRetireLessThan(Boolean value) {
            addCriterion("is_retire <", value, "isRetire");
            return (Criteria) this;
        }

        public Criteria andIsRetireLessThanOrEqualTo(Boolean value) {
            addCriterion("is_retire <=", value, "isRetire");
            return (Criteria) this;
        }

        public Criteria andIsRetireIn(List<Boolean> values) {
            addCriterion("is_retire in", values, "isRetire");
            return (Criteria) this;
        }

        public Criteria andIsRetireNotIn(List<Boolean> values) {
            addCriterion("is_retire not in", values, "isRetire");
            return (Criteria) this;
        }

        public Criteria andIsRetireBetween(Boolean value1, Boolean value2) {
            addCriterion("is_retire between", value1, value2, "isRetire");
            return (Criteria) this;
        }

        public Criteria andIsRetireNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_retire not between", value1, value2, "isRetire");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
        public Criteria addPermits(List<Integer> partyIdList, List<Integer> branchIdList) {

            Subject subject = SecurityUtils.getSubject();
            if(subject.hasRole(RoleConstants.ROLE_ADMIN)
                    || subject.hasRole(RoleConstants.ROLE_ODADMIN))
                return this;

            if(partyIdList==null) partyIdList = new ArrayList<>();
            if(branchIdList==null) branchIdList = new ArrayList<>();

            if(!partyIdList.isEmpty() && !branchIdList.isEmpty())
                addCriterion("(party_id in(" + StringUtils.join(partyIdList, ",") + ") OR branch_id in(" + StringUtils.join(branchIdList, ",") + "))");
            if(partyIdList.isEmpty() && !branchIdList.isEmpty())
                andBranchIdIn(branchIdList);
            if(branchIdList.isEmpty() && !partyIdList.isEmpty())
                andPartyIdIn(partyIdList);
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