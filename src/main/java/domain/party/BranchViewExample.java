package domain.party;

import org.apache.commons.lang3.StringUtils;
import shiro.ShiroHelper;
import sys.constants.RoleConstants;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.*;

public class BranchViewExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public BranchViewExample() {
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
            addCriterion(MessageFormat.format("(name like {0} or short_name like {0})",
                    "'"+value + "%'"));
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

        public Criteria andShortNameIsNull() {
            addCriterion("short_name is null");
            return (Criteria) this;
        }

        public Criteria andShortNameIsNotNull() {
            addCriterion("short_name is not null");
            return (Criteria) this;
        }

        public Criteria andShortNameEqualTo(String value) {
            addCriterion("short_name =", value, "shortName");
            return (Criteria) this;
        }

        public Criteria andShortNameNotEqualTo(String value) {
            addCriterion("short_name <>", value, "shortName");
            return (Criteria) this;
        }

        public Criteria andShortNameGreaterThan(String value) {
            addCriterion("short_name >", value, "shortName");
            return (Criteria) this;
        }

        public Criteria andShortNameGreaterThanOrEqualTo(String value) {
            addCriterion("short_name >=", value, "shortName");
            return (Criteria) this;
        }

        public Criteria andShortNameLessThan(String value) {
            addCriterion("short_name <", value, "shortName");
            return (Criteria) this;
        }

        public Criteria andShortNameLessThanOrEqualTo(String value) {
            addCriterion("short_name <=", value, "shortName");
            return (Criteria) this;
        }

        public Criteria andShortNameLike(String value) {
            addCriterion("short_name like", value, "shortName");
            return (Criteria) this;
        }

        public Criteria andShortNameNotLike(String value) {
            addCriterion("short_name not like", value, "shortName");
            return (Criteria) this;
        }

        public Criteria andShortNameIn(List<String> values) {
            addCriterion("short_name in", values, "shortName");
            return (Criteria) this;
        }

        public Criteria andShortNameNotIn(List<String> values) {
            addCriterion("short_name not in", values, "shortName");
            return (Criteria) this;
        }

        public Criteria andShortNameBetween(String value1, String value2) {
            addCriterion("short_name between", value1, value2, "shortName");
            return (Criteria) this;
        }

        public Criteria andShortNameNotBetween(String value1, String value2) {
            addCriterion("short_name not between", value1, value2, "shortName");
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

        public Criteria andTypesContain(Set<Integer> typeIds) {

            List<String> typeIdList = new ArrayList<>();
            for (Integer typeId : typeIds) {
                typeIdList.add("find_in_set("+typeId+", types)");
            }
            addCriterion("(" + StringUtils.join(typeIdList, " or ") + ")");

            return (Criteria) this;
        }

        public Criteria andTypesIsNull() {
            addCriterion("types is null");
            return (Criteria) this;
        }

        public Criteria andTypesIsNotNull() {
            addCriterion("types is not null");
            return (Criteria) this;
        }

        public Criteria andTypesEqualTo(String value) {
            addCriterion("types =", value, "types");
            return (Criteria) this;
        }

        public Criteria andTypesNotEqualTo(String value) {
            addCriterion("types <>", value, "types");
            return (Criteria) this;
        }

        public Criteria andTypesGreaterThan(String value) {
            addCriterion("types >", value, "types");
            return (Criteria) this;
        }

        public Criteria andTypesGreaterThanOrEqualTo(String value) {
            addCriterion("types >=", value, "types");
            return (Criteria) this;
        }

        public Criteria andTypesLessThan(String value) {
            addCriterion("types <", value, "types");
            return (Criteria) this;
        }

        public Criteria andTypesLessThanOrEqualTo(String value) {
            addCriterion("types <=", value, "types");
            return (Criteria) this;
        }

        public Criteria andTypesLike(String value) {
            addCriterion("types like", value, "types");
            return (Criteria) this;
        }

        public Criteria andTypesNotLike(String value) {
            addCriterion("types not like", value, "types");
            return (Criteria) this;
        }

        public Criteria andTypesIn(List<String> values) {
            addCriterion("types in", values, "types");
            return (Criteria) this;
        }

        public Criteria andTypesNotIn(List<String> values) {
            addCriterion("types not in", values, "types");
            return (Criteria) this;
        }

        public Criteria andTypesBetween(String value1, String value2) {
            addCriterion("types between", value1, value2, "types");
            return (Criteria) this;
        }

        public Criteria andTypesNotBetween(String value1, String value2) {
            addCriterion("types not between", value1, value2, "types");
            return (Criteria) this;
        }

        public Criteria andIsStaffIsNull() {
            addCriterion("is_staff is null");
            return (Criteria) this;
        }

        public Criteria andIsStaffIsNotNull() {
            addCriterion("is_staff is not null");
            return (Criteria) this;
        }

        public Criteria andIsStaffEqualTo(Boolean value) {
            addCriterion("is_staff =", value, "isStaff");
            return (Criteria) this;
        }

        public Criteria andIsStaffNotEqualTo(Boolean value) {
            addCriterion("is_staff <>", value, "isStaff");
            return (Criteria) this;
        }

        public Criteria andIsStaffGreaterThan(Boolean value) {
            addCriterion("is_staff >", value, "isStaff");
            return (Criteria) this;
        }

        public Criteria andIsStaffGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_staff >=", value, "isStaff");
            return (Criteria) this;
        }

        public Criteria andIsStaffLessThan(Boolean value) {
            addCriterion("is_staff <", value, "isStaff");
            return (Criteria) this;
        }

        public Criteria andIsStaffLessThanOrEqualTo(Boolean value) {
            addCriterion("is_staff <=", value, "isStaff");
            return (Criteria) this;
        }

        public Criteria andIsStaffIn(List<Boolean> values) {
            addCriterion("is_staff in", values, "isStaff");
            return (Criteria) this;
        }

        public Criteria andIsStaffNotIn(List<Boolean> values) {
            addCriterion("is_staff not in", values, "isStaff");
            return (Criteria) this;
        }

        public Criteria andIsStaffBetween(Boolean value1, Boolean value2) {
            addCriterion("is_staff between", value1, value2, "isStaff");
            return (Criteria) this;
        }

        public Criteria andIsStaffNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_staff not between", value1, value2, "isStaff");
            return (Criteria) this;
        }

        public Criteria andIsPrefessionalIsNull() {
            addCriterion("is_prefessional is null");
            return (Criteria) this;
        }

        public Criteria andIsPrefessionalIsNotNull() {
            addCriterion("is_prefessional is not null");
            return (Criteria) this;
        }

        public Criteria andIsPrefessionalEqualTo(Boolean value) {
            addCriterion("is_prefessional =", value, "isPrefessional");
            return (Criteria) this;
        }

        public Criteria andIsPrefessionalNotEqualTo(Boolean value) {
            addCriterion("is_prefessional <>", value, "isPrefessional");
            return (Criteria) this;
        }

        public Criteria andIsPrefessionalGreaterThan(Boolean value) {
            addCriterion("is_prefessional >", value, "isPrefessional");
            return (Criteria) this;
        }

        public Criteria andIsPrefessionalGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_prefessional >=", value, "isPrefessional");
            return (Criteria) this;
        }

        public Criteria andIsPrefessionalLessThan(Boolean value) {
            addCriterion("is_prefessional <", value, "isPrefessional");
            return (Criteria) this;
        }

        public Criteria andIsPrefessionalLessThanOrEqualTo(Boolean value) {
            addCriterion("is_prefessional <=", value, "isPrefessional");
            return (Criteria) this;
        }

        public Criteria andIsPrefessionalIn(List<Boolean> values) {
            addCriterion("is_prefessional in", values, "isPrefessional");
            return (Criteria) this;
        }

        public Criteria andIsPrefessionalNotIn(List<Boolean> values) {
            addCriterion("is_prefessional not in", values, "isPrefessional");
            return (Criteria) this;
        }

        public Criteria andIsPrefessionalBetween(Boolean value1, Boolean value2) {
            addCriterion("is_prefessional between", value1, value2, "isPrefessional");
            return (Criteria) this;
        }

        public Criteria andIsPrefessionalNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_prefessional not between", value1, value2, "isPrefessional");
            return (Criteria) this;
        }

        public Criteria andIsBaseTeamIsNull() {
            addCriterion("is_base_team is null");
            return (Criteria) this;
        }

        public Criteria andIsBaseTeamIsNotNull() {
            addCriterion("is_base_team is not null");
            return (Criteria) this;
        }

        public Criteria andIsBaseTeamEqualTo(Boolean value) {
            addCriterion("is_base_team =", value, "isBaseTeam");
            return (Criteria) this;
        }

        public Criteria andIsBaseTeamNotEqualTo(Boolean value) {
            addCriterion("is_base_team <>", value, "isBaseTeam");
            return (Criteria) this;
        }

        public Criteria andIsBaseTeamGreaterThan(Boolean value) {
            addCriterion("is_base_team >", value, "isBaseTeam");
            return (Criteria) this;
        }

        public Criteria andIsBaseTeamGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_base_team >=", value, "isBaseTeam");
            return (Criteria) this;
        }

        public Criteria andIsBaseTeamLessThan(Boolean value) {
            addCriterion("is_base_team <", value, "isBaseTeam");
            return (Criteria) this;
        }

        public Criteria andIsBaseTeamLessThanOrEqualTo(Boolean value) {
            addCriterion("is_base_team <=", value, "isBaseTeam");
            return (Criteria) this;
        }

        public Criteria andIsBaseTeamIn(List<Boolean> values) {
            addCriterion("is_base_team in", values, "isBaseTeam");
            return (Criteria) this;
        }

        public Criteria andIsBaseTeamNotIn(List<Boolean> values) {
            addCriterion("is_base_team not in", values, "isBaseTeam");
            return (Criteria) this;
        }

        public Criteria andIsBaseTeamBetween(Boolean value1, Boolean value2) {
            addCriterion("is_base_team between", value1, value2, "isBaseTeam");
            return (Criteria) this;
        }

        public Criteria andIsBaseTeamNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_base_team not between", value1, value2, "isBaseTeam");
            return (Criteria) this;
        }

        public Criteria andUnitTypeIdIsNull() {
            addCriterion("unit_type_id is null");
            return (Criteria) this;
        }

        public Criteria andUnitTypeIdIsNotNull() {
            addCriterion("unit_type_id is not null");
            return (Criteria) this;
        }

        public Criteria andUnitTypeIdEqualTo(Integer value) {
            addCriterion("unit_type_id =", value, "unitTypeId");
            return (Criteria) this;
        }

        public Criteria andUnitTypeIdNotEqualTo(Integer value) {
            addCriterion("unit_type_id <>", value, "unitTypeId");
            return (Criteria) this;
        }

        public Criteria andUnitTypeIdGreaterThan(Integer value) {
            addCriterion("unit_type_id >", value, "unitTypeId");
            return (Criteria) this;
        }

        public Criteria andUnitTypeIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("unit_type_id >=", value, "unitTypeId");
            return (Criteria) this;
        }

        public Criteria andUnitTypeIdLessThan(Integer value) {
            addCriterion("unit_type_id <", value, "unitTypeId");
            return (Criteria) this;
        }

        public Criteria andUnitTypeIdLessThanOrEqualTo(Integer value) {
            addCriterion("unit_type_id <=", value, "unitTypeId");
            return (Criteria) this;
        }

        public Criteria andUnitTypeIdIn(List<Integer> values) {
            addCriterion("unit_type_id in", values, "unitTypeId");
            return (Criteria) this;
        }

        public Criteria andUnitTypeIdNotIn(List<Integer> values) {
            addCriterion("unit_type_id not in", values, "unitTypeId");
            return (Criteria) this;
        }

        public Criteria andUnitTypeIdBetween(Integer value1, Integer value2) {
            addCriterion("unit_type_id between", value1, value2, "unitTypeId");
            return (Criteria) this;
        }

        public Criteria andUnitTypeIdNotBetween(Integer value1, Integer value2) {
            addCriterion("unit_type_id not between", value1, value2, "unitTypeId");
            return (Criteria) this;
        }

        public Criteria andIsEnterpriseBigIsNull() {
            addCriterion("is_enterprise_big is null");
            return (Criteria) this;
        }

        public Criteria andIsEnterpriseBigIsNotNull() {
            addCriterion("is_enterprise_big is not null");
            return (Criteria) this;
        }

        public Criteria andIsEnterpriseBigEqualTo(Boolean value) {
            addCriterion("is_enterprise_big =", value, "isEnterpriseBig");
            return (Criteria) this;
        }

        public Criteria andIsEnterpriseBigNotEqualTo(Boolean value) {
            addCriterion("is_enterprise_big <>", value, "isEnterpriseBig");
            return (Criteria) this;
        }

        public Criteria andIsEnterpriseBigGreaterThan(Boolean value) {
            addCriterion("is_enterprise_big >", value, "isEnterpriseBig");
            return (Criteria) this;
        }

        public Criteria andIsEnterpriseBigGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_enterprise_big >=", value, "isEnterpriseBig");
            return (Criteria) this;
        }

        public Criteria andIsEnterpriseBigLessThan(Boolean value) {
            addCriterion("is_enterprise_big <", value, "isEnterpriseBig");
            return (Criteria) this;
        }

        public Criteria andIsEnterpriseBigLessThanOrEqualTo(Boolean value) {
            addCriterion("is_enterprise_big <=", value, "isEnterpriseBig");
            return (Criteria) this;
        }

        public Criteria andIsEnterpriseBigIn(List<Boolean> values) {
            addCriterion("is_enterprise_big in", values, "isEnterpriseBig");
            return (Criteria) this;
        }

        public Criteria andIsEnterpriseBigNotIn(List<Boolean> values) {
            addCriterion("is_enterprise_big not in", values, "isEnterpriseBig");
            return (Criteria) this;
        }

        public Criteria andIsEnterpriseBigBetween(Boolean value1, Boolean value2) {
            addCriterion("is_enterprise_big between", value1, value2, "isEnterpriseBig");
            return (Criteria) this;
        }

        public Criteria andIsEnterpriseBigNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_enterprise_big not between", value1, value2, "isEnterpriseBig");
            return (Criteria) this;
        }

        public Criteria andIsEnterpriseNationalizedIsNull() {
            addCriterion("is_enterprise_nationalized is null");
            return (Criteria) this;
        }

        public Criteria andIsEnterpriseNationalizedIsNotNull() {
            addCriterion("is_enterprise_nationalized is not null");
            return (Criteria) this;
        }

        public Criteria andIsEnterpriseNationalizedEqualTo(Boolean value) {
            addCriterion("is_enterprise_nationalized =", value, "isEnterpriseNationalized");
            return (Criteria) this;
        }

        public Criteria andIsEnterpriseNationalizedNotEqualTo(Boolean value) {
            addCriterion("is_enterprise_nationalized <>", value, "isEnterpriseNationalized");
            return (Criteria) this;
        }

        public Criteria andIsEnterpriseNationalizedGreaterThan(Boolean value) {
            addCriterion("is_enterprise_nationalized >", value, "isEnterpriseNationalized");
            return (Criteria) this;
        }

        public Criteria andIsEnterpriseNationalizedGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_enterprise_nationalized >=", value, "isEnterpriseNationalized");
            return (Criteria) this;
        }

        public Criteria andIsEnterpriseNationalizedLessThan(Boolean value) {
            addCriterion("is_enterprise_nationalized <", value, "isEnterpriseNationalized");
            return (Criteria) this;
        }

        public Criteria andIsEnterpriseNationalizedLessThanOrEqualTo(Boolean value) {
            addCriterion("is_enterprise_nationalized <=", value, "isEnterpriseNationalized");
            return (Criteria) this;
        }

        public Criteria andIsEnterpriseNationalizedIn(List<Boolean> values) {
            addCriterion("is_enterprise_nationalized in", values, "isEnterpriseNationalized");
            return (Criteria) this;
        }

        public Criteria andIsEnterpriseNationalizedNotIn(List<Boolean> values) {
            addCriterion("is_enterprise_nationalized not in", values, "isEnterpriseNationalized");
            return (Criteria) this;
        }

        public Criteria andIsEnterpriseNationalizedBetween(Boolean value1, Boolean value2) {
            addCriterion("is_enterprise_nationalized between", value1, value2, "isEnterpriseNationalized");
            return (Criteria) this;
        }

        public Criteria andIsEnterpriseNationalizedNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_enterprise_nationalized not between", value1, value2, "isEnterpriseNationalized");
            return (Criteria) this;
        }

        public Criteria andIsUnionIsNull() {
            addCriterion("is_union is null");
            return (Criteria) this;
        }

        public Criteria andIsUnionIsNotNull() {
            addCriterion("is_union is not null");
            return (Criteria) this;
        }

        public Criteria andIsUnionEqualTo(Boolean value) {
            addCriterion("is_union =", value, "isUnion");
            return (Criteria) this;
        }

        public Criteria andIsUnionNotEqualTo(Boolean value) {
            addCriterion("is_union <>", value, "isUnion");
            return (Criteria) this;
        }

        public Criteria andIsUnionGreaterThan(Boolean value) {
            addCriterion("is_union >", value, "isUnion");
            return (Criteria) this;
        }

        public Criteria andIsUnionGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_union >=", value, "isUnion");
            return (Criteria) this;
        }

        public Criteria andIsUnionLessThan(Boolean value) {
            addCriterion("is_union <", value, "isUnion");
            return (Criteria) this;
        }

        public Criteria andIsUnionLessThanOrEqualTo(Boolean value) {
            addCriterion("is_union <=", value, "isUnion");
            return (Criteria) this;
        }

        public Criteria andIsUnionIn(List<Boolean> values) {
            addCriterion("is_union in", values, "isUnion");
            return (Criteria) this;
        }

        public Criteria andIsUnionNotIn(List<Boolean> values) {
            addCriterion("is_union not in", values, "isUnion");
            return (Criteria) this;
        }

        public Criteria andIsUnionBetween(Boolean value1, Boolean value2) {
            addCriterion("is_union between", value1, value2, "isUnion");
            return (Criteria) this;
        }

        public Criteria andIsUnionNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_union not between", value1, value2, "isUnion");
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

        public Criteria andFaxIsNull() {
            addCriterion("fax is null");
            return (Criteria) this;
        }

        public Criteria andFaxIsNotNull() {
            addCriterion("fax is not null");
            return (Criteria) this;
        }

        public Criteria andFaxEqualTo(String value) {
            addCriterion("fax =", value, "fax");
            return (Criteria) this;
        }

        public Criteria andFaxNotEqualTo(String value) {
            addCriterion("fax <>", value, "fax");
            return (Criteria) this;
        }

        public Criteria andFaxGreaterThan(String value) {
            addCriterion("fax >", value, "fax");
            return (Criteria) this;
        }

        public Criteria andFaxGreaterThanOrEqualTo(String value) {
            addCriterion("fax >=", value, "fax");
            return (Criteria) this;
        }

        public Criteria andFaxLessThan(String value) {
            addCriterion("fax <", value, "fax");
            return (Criteria) this;
        }

        public Criteria andFaxLessThanOrEqualTo(String value) {
            addCriterion("fax <=", value, "fax");
            return (Criteria) this;
        }

        public Criteria andFaxLike(String value) {
            addCriterion("fax like", value, "fax");
            return (Criteria) this;
        }

        public Criteria andFaxNotLike(String value) {
            addCriterion("fax not like", value, "fax");
            return (Criteria) this;
        }

        public Criteria andFaxIn(List<String> values) {
            addCriterion("fax in", values, "fax");
            return (Criteria) this;
        }

        public Criteria andFaxNotIn(List<String> values) {
            addCriterion("fax not in", values, "fax");
            return (Criteria) this;
        }

        public Criteria andFaxBetween(String value1, String value2) {
            addCriterion("fax between", value1, value2, "fax");
            return (Criteria) this;
        }

        public Criteria andFaxNotBetween(String value1, String value2) {
            addCriterion("fax not between", value1, value2, "fax");
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

        public Criteria andFoundTimeIsNull() {
            addCriterion("found_time is null");
            return (Criteria) this;
        }

        public Criteria andFoundTimeIsNotNull() {
            addCriterion("found_time is not null");
            return (Criteria) this;
        }

        public Criteria andFoundTimeEqualTo(Date value) {
            addCriterionForJDBCDate("found_time =", value, "foundTime");
            return (Criteria) this;
        }

        public Criteria andFoundTimeNotEqualTo(Date value) {
            addCriterionForJDBCDate("found_time <>", value, "foundTime");
            return (Criteria) this;
        }

        public Criteria andFoundTimeGreaterThan(Date value) {
            addCriterionForJDBCDate("found_time >", value, "foundTime");
            return (Criteria) this;
        }

        public Criteria andFoundTimeGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("found_time >=", value, "foundTime");
            return (Criteria) this;
        }

        public Criteria andFoundTimeLessThan(Date value) {
            addCriterionForJDBCDate("found_time <", value, "foundTime");
            return (Criteria) this;
        }

        public Criteria andFoundTimeLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("found_time <=", value, "foundTime");
            return (Criteria) this;
        }

        public Criteria andFoundTimeIn(List<Date> values) {
            addCriterionForJDBCDate("found_time in", values, "foundTime");
            return (Criteria) this;
        }

        public Criteria andFoundTimeNotIn(List<Date> values) {
            addCriterionForJDBCDate("found_time not in", values, "foundTime");
            return (Criteria) this;
        }

        public Criteria andFoundTimeBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("found_time between", value1, value2, "foundTime");
            return (Criteria) this;
        }

        public Criteria andFoundTimeNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("found_time not between", value1, value2, "foundTime");
            return (Criteria) this;
        }

        public Criteria andTransferCountIsNull() {
            addCriterion("transfer_count is null");
            return (Criteria) this;
        }

        public Criteria andTransferCountIsNotNull() {
            addCriterion("transfer_count is not null");
            return (Criteria) this;
        }

        public Criteria andTransferCountEqualTo(Integer value) {
            addCriterion("transfer_count =", value, "transferCount");
            return (Criteria) this;
        }

        public Criteria andTransferCountNotEqualTo(Integer value) {
            addCriterion("transfer_count <>", value, "transferCount");
            return (Criteria) this;
        }

        public Criteria andTransferCountGreaterThan(Integer value) {
            addCriterion("transfer_count >", value, "transferCount");
            return (Criteria) this;
        }

        public Criteria andTransferCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("transfer_count >=", value, "transferCount");
            return (Criteria) this;
        }

        public Criteria andTransferCountLessThan(Integer value) {
            addCriterion("transfer_count <", value, "transferCount");
            return (Criteria) this;
        }

        public Criteria andTransferCountLessThanOrEqualTo(Integer value) {
            addCriterion("transfer_count <=", value, "transferCount");
            return (Criteria) this;
        }

        public Criteria andTransferCountIn(List<Integer> values) {
            addCriterion("transfer_count in", values, "transferCount");
            return (Criteria) this;
        }

        public Criteria andTransferCountNotIn(List<Integer> values) {
            addCriterion("transfer_count not in", values, "transferCount");
            return (Criteria) this;
        }

        public Criteria andTransferCountBetween(Integer value1, Integer value2) {
            addCriterion("transfer_count between", value1, value2, "transferCount");
            return (Criteria) this;
        }

        public Criteria andTransferCountNotBetween(Integer value1, Integer value2) {
            addCriterion("transfer_count not between", value1, value2, "transferCount");
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

        public Criteria andUpdateTimeIsNull() {
            addCriterion("update_time is null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNotNull() {
            addCriterion("update_time is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeEqualTo(Date value) {
            addCriterion("update_time =", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotEqualTo(Date value) {
            addCriterion("update_time <>", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThan(Date value) {
            addCriterion("update_time >", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("update_time >=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThan(Date value) {
            addCriterion("update_time <", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThanOrEqualTo(Date value) {
            addCriterion("update_time <=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIn(List<Date> values) {
            addCriterion("update_time in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotIn(List<Date> values) {
            addCriterion("update_time not in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeBetween(Date value1, Date value2) {
            addCriterion("update_time between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotBetween(Date value1, Date value2) {
            addCriterion("update_time not between", value1, value2, "updateTime");
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

        public Criteria andIntegrityIsNull() {
            addCriterion("integrity is null");
            return (Criteria) this;
        }

        public Criteria andIntegrityIsNotNull() {
            addCriterion("integrity is not null");
            return (Criteria) this;
        }

        public Criteria andIntegrityEqualTo(BigDecimal value) {
            addCriterion("integrity =", value, "integrity");
            return (Criteria) this;
        }

        public Criteria andIntegrityNotEqualTo(BigDecimal value) {
            addCriterion("integrity <>", value, "integrity");
            return (Criteria) this;
        }

        public Criteria andIntegrityGreaterThan(BigDecimal value) {
            addCriterion("integrity >", value, "integrity");
            return (Criteria) this;
        }

        public Criteria andIntegrityGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("integrity >=", value, "integrity");
            return (Criteria) this;
        }

        public Criteria andIntegrityLessThan(BigDecimal value) {
            addCriterion("integrity <", value, "integrity");
            return (Criteria) this;
        }

        public Criteria andIntegrityLessThanOrEqualTo(BigDecimal value) {
            addCriterion("integrity <=", value, "integrity");
            return (Criteria) this;
        }

        public Criteria andIntegrityIn(List<BigDecimal> values) {
            addCriterion("integrity in", values, "integrity");
            return (Criteria) this;
        }

        public Criteria andIntegrityNotIn(List<BigDecimal> values) {
            addCriterion("integrity not in", values, "integrity");
            return (Criteria) this;
        }

        public Criteria andIntegrityBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("integrity between", value1, value2, "integrity");
            return (Criteria) this;
        }

        public Criteria andIntegrityNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("integrity not between", value1, value2, "integrity");
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

        public Criteria andMemberCountIsNull() {
            addCriterion("member_count is null");
            return (Criteria) this;
        }

        public Criteria andMemberCountIsNotNull() {
            addCriterion("member_count is not null");
            return (Criteria) this;
        }

        public Criteria andMemberCountEqualTo(Long value) {
            addCriterion("member_count =", value, "memberCount");
            return (Criteria) this;
        }

        public Criteria andMemberCountNotEqualTo(Long value) {
            addCriterion("member_count <>", value, "memberCount");
            return (Criteria) this;
        }

        public Criteria andMemberCountGreaterThan(Long value) {
            addCriterion("member_count >", value, "memberCount");
            return (Criteria) this;
        }

        public Criteria andMemberCountGreaterThanOrEqualTo(Long value) {
            addCriterion("member_count >=", value, "memberCount");
            return (Criteria) this;
        }

        public Criteria andMemberCountLessThan(Long value) {
            addCriterion("member_count <", value, "memberCount");
            return (Criteria) this;
        }

        public Criteria andMemberCountLessThanOrEqualTo(Long value) {
            addCriterion("member_count <=", value, "memberCount");
            return (Criteria) this;
        }

        public Criteria andMemberCountIn(List<Long> values) {
            addCriterion("member_count in", values, "memberCount");
            return (Criteria) this;
        }

        public Criteria andMemberCountNotIn(List<Long> values) {
            addCriterion("member_count not in", values, "memberCount");
            return (Criteria) this;
        }

        public Criteria andMemberCountBetween(Long value1, Long value2) {
            addCriterion("member_count between", value1, value2, "memberCount");
            return (Criteria) this;
        }

        public Criteria andMemberCountNotBetween(Long value1, Long value2) {
            addCriterion("member_count not between", value1, value2, "memberCount");
            return (Criteria) this;
        }

        public Criteria andPositiveCountIsNull() {
            addCriterion("positive_count is null");
            return (Criteria) this;
        }

        public Criteria andPositiveCountIsNotNull() {
            addCriterion("positive_count is not null");
            return (Criteria) this;
        }

        public Criteria andPositiveCountEqualTo(BigDecimal value) {
            addCriterion("positive_count =", value, "positiveCount");
            return (Criteria) this;
        }

        public Criteria andPositiveCountNotEqualTo(BigDecimal value) {
            addCriterion("positive_count <>", value, "positiveCount");
            return (Criteria) this;
        }

        public Criteria andPositiveCountGreaterThan(BigDecimal value) {
            addCriterion("positive_count >", value, "positiveCount");
            return (Criteria) this;
        }

        public Criteria andPositiveCountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("positive_count >=", value, "positiveCount");
            return (Criteria) this;
        }

        public Criteria andPositiveCountLessThan(BigDecimal value) {
            addCriterion("positive_count <", value, "positiveCount");
            return (Criteria) this;
        }

        public Criteria andPositiveCountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("positive_count <=", value, "positiveCount");
            return (Criteria) this;
        }

        public Criteria andPositiveCountIn(List<BigDecimal> values) {
            addCriterion("positive_count in", values, "positiveCount");
            return (Criteria) this;
        }

        public Criteria andPositiveCountNotIn(List<BigDecimal> values) {
            addCriterion("positive_count not in", values, "positiveCount");
            return (Criteria) this;
        }

        public Criteria andPositiveCountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("positive_count between", value1, value2, "positiveCount");
            return (Criteria) this;
        }

        public Criteria andPositiveCountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("positive_count not between", value1, value2, "positiveCount");
            return (Criteria) this;
        }

        public Criteria andStudentMemberCountIsNull() {
            addCriterion("student_member_count is null");
            return (Criteria) this;
        }

        public Criteria andStudentMemberCountIsNotNull() {
            addCriterion("student_member_count is not null");
            return (Criteria) this;
        }

        public Criteria andStudentMemberCountEqualTo(BigDecimal value) {
            addCriterion("student_member_count =", value, "studentMemberCount");
            return (Criteria) this;
        }

        public Criteria andStudentMemberCountNotEqualTo(BigDecimal value) {
            addCriterion("student_member_count <>", value, "studentMemberCount");
            return (Criteria) this;
        }

        public Criteria andStudentMemberCountGreaterThan(BigDecimal value) {
            addCriterion("student_member_count >", value, "studentMemberCount");
            return (Criteria) this;
        }

        public Criteria andStudentMemberCountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("student_member_count >=", value, "studentMemberCount");
            return (Criteria) this;
        }

        public Criteria andStudentMemberCountLessThan(BigDecimal value) {
            addCriterion("student_member_count <", value, "studentMemberCount");
            return (Criteria) this;
        }

        public Criteria andStudentMemberCountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("student_member_count <=", value, "studentMemberCount");
            return (Criteria) this;
        }

        public Criteria andStudentMemberCountIn(List<BigDecimal> values) {
            addCriterion("student_member_count in", values, "studentMemberCount");
            return (Criteria) this;
        }

        public Criteria andStudentMemberCountNotIn(List<BigDecimal> values) {
            addCriterion("student_member_count not in", values, "studentMemberCount");
            return (Criteria) this;
        }

        public Criteria andStudentMemberCountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("student_member_count between", value1, value2, "studentMemberCount");
            return (Criteria) this;
        }

        public Criteria andStudentMemberCountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("student_member_count not between", value1, value2, "studentMemberCount");
            return (Criteria) this;
        }

        public Criteria andTeacherMemberCountIsNull() {
            addCriterion("teacher_member_count is null");
            return (Criteria) this;
        }

        public Criteria andTeacherMemberCountIsNotNull() {
            addCriterion("teacher_member_count is not null");
            return (Criteria) this;
        }

        public Criteria andTeacherMemberCountEqualTo(BigDecimal value) {
            addCriterion("teacher_member_count =", value, "teacherMemberCount");
            return (Criteria) this;
        }

        public Criteria andTeacherMemberCountNotEqualTo(BigDecimal value) {
            addCriterion("teacher_member_count <>", value, "teacherMemberCount");
            return (Criteria) this;
        }

        public Criteria andTeacherMemberCountGreaterThan(BigDecimal value) {
            addCriterion("teacher_member_count >", value, "teacherMemberCount");
            return (Criteria) this;
        }

        public Criteria andTeacherMemberCountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("teacher_member_count >=", value, "teacherMemberCount");
            return (Criteria) this;
        }

        public Criteria andTeacherMemberCountLessThan(BigDecimal value) {
            addCriterion("teacher_member_count <", value, "teacherMemberCount");
            return (Criteria) this;
        }

        public Criteria andTeacherMemberCountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("teacher_member_count <=", value, "teacherMemberCount");
            return (Criteria) this;
        }

        public Criteria andTeacherMemberCountIn(List<BigDecimal> values) {
            addCriterion("teacher_member_count in", values, "teacherMemberCount");
            return (Criteria) this;
        }

        public Criteria andTeacherMemberCountNotIn(List<BigDecimal> values) {
            addCriterion("teacher_member_count not in", values, "teacherMemberCount");
            return (Criteria) this;
        }

        public Criteria andTeacherMemberCountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("teacher_member_count between", value1, value2, "teacherMemberCount");
            return (Criteria) this;
        }

        public Criteria andTeacherMemberCountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("teacher_member_count not between", value1, value2, "teacherMemberCount");
            return (Criteria) this;
        }

        public Criteria andRetireMemberCountIsNull() {
            addCriterion("retire_member_count is null");
            return (Criteria) this;
        }

        public Criteria andRetireMemberCountIsNotNull() {
            addCriterion("retire_member_count is not null");
            return (Criteria) this;
        }

        public Criteria andRetireMemberCountEqualTo(BigDecimal value) {
            addCriterion("retire_member_count =", value, "retireMemberCount");
            return (Criteria) this;
        }

        public Criteria andRetireMemberCountNotEqualTo(BigDecimal value) {
            addCriterion("retire_member_count <>", value, "retireMemberCount");
            return (Criteria) this;
        }

        public Criteria andRetireMemberCountGreaterThan(BigDecimal value) {
            addCriterion("retire_member_count >", value, "retireMemberCount");
            return (Criteria) this;
        }

        public Criteria andRetireMemberCountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("retire_member_count >=", value, "retireMemberCount");
            return (Criteria) this;
        }

        public Criteria andRetireMemberCountLessThan(BigDecimal value) {
            addCriterion("retire_member_count <", value, "retireMemberCount");
            return (Criteria) this;
        }

        public Criteria andRetireMemberCountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("retire_member_count <=", value, "retireMemberCount");
            return (Criteria) this;
        }

        public Criteria andRetireMemberCountIn(List<BigDecimal> values) {
            addCriterion("retire_member_count in", values, "retireMemberCount");
            return (Criteria) this;
        }

        public Criteria andRetireMemberCountNotIn(List<BigDecimal> values) {
            addCriterion("retire_member_count not in", values, "retireMemberCount");
            return (Criteria) this;
        }

        public Criteria andRetireMemberCountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("retire_member_count between", value1, value2, "retireMemberCount");
            return (Criteria) this;
        }

        public Criteria andRetireMemberCountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("retire_member_count not between", value1, value2, "retireMemberCount");
            return (Criteria) this;
        }

        public Criteria andGroupCountIsNull() {
            addCriterion("group_count is null");
            return (Criteria) this;
        }

        public Criteria andGroupCountIsNotNull() {
            addCriterion("group_count is not null");
            return (Criteria) this;
        }

        public Criteria andGroupCountEqualTo(Long value) {
            addCriterion("group_count =", value, "groupCount");
            return (Criteria) this;
        }

        public Criteria andGroupCountNotEqualTo(Long value) {
            addCriterion("group_count <>", value, "groupCount");
            return (Criteria) this;
        }

        public Criteria andGroupCountGreaterThan(Long value) {
            addCriterion("group_count >", value, "groupCount");
            return (Criteria) this;
        }

        public Criteria andGroupCountGreaterThanOrEqualTo(Long value) {
            addCriterion("group_count >=", value, "groupCount");
            return (Criteria) this;
        }

        public Criteria andGroupCountLessThan(Long value) {
            addCriterion("group_count <", value, "groupCount");
            return (Criteria) this;
        }

        public Criteria andGroupCountLessThanOrEqualTo(Long value) {
            addCriterion("group_count <=", value, "groupCount");
            return (Criteria) this;
        }

        public Criteria andGroupCountIn(List<Long> values) {
            addCriterion("group_count in", values, "groupCount");
            return (Criteria) this;
        }

        public Criteria andGroupCountNotIn(List<Long> values) {
            addCriterion("group_count not in", values, "groupCount");
            return (Criteria) this;
        }

        public Criteria andGroupCountBetween(Long value1, Long value2) {
            addCriterion("group_count between", value1, value2, "groupCount");
            return (Criteria) this;
        }

        public Criteria andGroupCountNotBetween(Long value1, Long value2) {
            addCriterion("group_count not between", value1, value2, "groupCount");
            return (Criteria) this;
        }

        public Criteria andPresentGroupIdIsNull() {
            addCriterion("present_group_id is null");
            return (Criteria) this;
        }

        public Criteria andPresentGroupIdIsNotNull() {
            addCriterion("present_group_id is not null");
            return (Criteria) this;
        }

        public Criteria andPresentGroupIdEqualTo(Integer value) {
            addCriterion("present_group_id =", value, "presentGroupId");
            return (Criteria) this;
        }

        public Criteria andPresentGroupIdNotEqualTo(Integer value) {
            addCriterion("present_group_id <>", value, "presentGroupId");
            return (Criteria) this;
        }

        public Criteria andPresentGroupIdGreaterThan(Integer value) {
            addCriterion("present_group_id >", value, "presentGroupId");
            return (Criteria) this;
        }

        public Criteria andPresentGroupIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("present_group_id >=", value, "presentGroupId");
            return (Criteria) this;
        }

        public Criteria andPresentGroupIdLessThan(Integer value) {
            addCriterion("present_group_id <", value, "presentGroupId");
            return (Criteria) this;
        }

        public Criteria andPresentGroupIdLessThanOrEqualTo(Integer value) {
            addCriterion("present_group_id <=", value, "presentGroupId");
            return (Criteria) this;
        }

        public Criteria andPresentGroupIdIn(List<Integer> values) {
            addCriterion("present_group_id in", values, "presentGroupId");
            return (Criteria) this;
        }

        public Criteria andPresentGroupIdNotIn(List<Integer> values) {
            addCriterion("present_group_id not in", values, "presentGroupId");
            return (Criteria) this;
        }

        public Criteria andPresentGroupIdBetween(Integer value1, Integer value2) {
            addCriterion("present_group_id between", value1, value2, "presentGroupId");
            return (Criteria) this;
        }

        public Criteria andPresentGroupIdNotBetween(Integer value1, Integer value2) {
            addCriterion("present_group_id not between", value1, value2, "presentGroupId");
            return (Criteria) this;
        }

        public Criteria andAppointTimeIsNull() {
            addCriterion("appoint_time is null");
            return (Criteria) this;
        }

        public Criteria andAppointTimeIsNotNull() {
            addCriterion("appoint_time is not null");
            return (Criteria) this;
        }

        public Criteria andAppointTimeEqualTo(Date value) {
            addCriterionForJDBCDate("appoint_time =", value, "appointTime");
            return (Criteria) this;
        }

        public Criteria andAppointTimeNotEqualTo(Date value) {
            addCriterionForJDBCDate("appoint_time <>", value, "appointTime");
            return (Criteria) this;
        }

        public Criteria andAppointTimeGreaterThan(Date value) {
            addCriterionForJDBCDate("appoint_time >", value, "appointTime");
            return (Criteria) this;
        }

        public Criteria andAppointTimeGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("appoint_time >=", value, "appointTime");
            return (Criteria) this;
        }

        public Criteria andAppointTimeLessThan(Date value) {
            addCriterionForJDBCDate("appoint_time <", value, "appointTime");
            return (Criteria) this;
        }

        public Criteria andAppointTimeLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("appoint_time <=", value, "appointTime");
            return (Criteria) this;
        }

        public Criteria andAppointTimeIn(List<Date> values) {
            addCriterionForJDBCDate("appoint_time in", values, "appointTime");
            return (Criteria) this;
        }

        public Criteria andAppointTimeNotIn(List<Date> values) {
            addCriterionForJDBCDate("appoint_time not in", values, "appointTime");
            return (Criteria) this;
        }

        public Criteria andAppointTimeBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("appoint_time between", value1, value2, "appointTime");
            return (Criteria) this;
        }

        public Criteria andAppointTimeNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("appoint_time not between", value1, value2, "appointTime");
            return (Criteria) this;
        }

        public Criteria andTranTimeIsNull() {
            addCriterion("tran_time is null");
            return (Criteria) this;
        }

        public Criteria andTranTimeIsNotNull() {
            addCriterion("tran_time is not null");
            return (Criteria) this;
        }

        public Criteria andTranTimeEqualTo(Date value) {
            addCriterionForJDBCDate("tran_time =", value, "tranTime");
            return (Criteria) this;
        }

        public Criteria andTranTimeNotEqualTo(Date value) {
            addCriterionForJDBCDate("tran_time <>", value, "tranTime");
            return (Criteria) this;
        }

        public Criteria andTranTimeGreaterThan(Date value) {
            addCriterionForJDBCDate("tran_time >", value, "tranTime");
            return (Criteria) this;
        }

        public Criteria andTranTimeGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("tran_time >=", value, "tranTime");
            return (Criteria) this;
        }

        public Criteria andTranTimeLessThan(Date value) {
            addCriterionForJDBCDate("tran_time <", value, "tranTime");
            return (Criteria) this;
        }

        public Criteria andTranTimeLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("tran_time <=", value, "tranTime");
            return (Criteria) this;
        }

        public Criteria andTranTimeIn(List<Date> values) {
            addCriterionForJDBCDate("tran_time in", values, "tranTime");
            return (Criteria) this;
        }

        public Criteria andTranTimeNotIn(List<Date> values) {
            addCriterionForJDBCDate("tran_time not in", values, "tranTime");
            return (Criteria) this;
        }

        public Criteria andTranTimeBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("tran_time between", value1, value2, "tranTime");
            return (Criteria) this;
        }

        public Criteria andTranTimeNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("tran_time not between", value1, value2, "tranTime");
            return (Criteria) this;
        }

        public Criteria andActualTranTimeIsNull() {
            addCriterion("actual_tran_time is null");
            return (Criteria) this;
        }

        public Criteria andActualTranTimeIsNotNull() {
            addCriterion("actual_tran_time is not null");
            return (Criteria) this;
        }

        public Criteria andActualTranTimeEqualTo(Date value) {
            addCriterionForJDBCDate("actual_tran_time =", value, "actualTranTime");
            return (Criteria) this;
        }

        public Criteria andActualTranTimeNotEqualTo(Date value) {
            addCriterionForJDBCDate("actual_tran_time <>", value, "actualTranTime");
            return (Criteria) this;
        }

        public Criteria andActualTranTimeGreaterThan(Date value) {
            addCriterionForJDBCDate("actual_tran_time >", value, "actualTranTime");
            return (Criteria) this;
        }

        public Criteria andActualTranTimeGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("actual_tran_time >=", value, "actualTranTime");
            return (Criteria) this;
        }

        public Criteria andActualTranTimeLessThan(Date value) {
            addCriterionForJDBCDate("actual_tran_time <", value, "actualTranTime");
            return (Criteria) this;
        }

        public Criteria andActualTranTimeLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("actual_tran_time <=", value, "actualTranTime");
            return (Criteria) this;
        }

        public Criteria andActualTranTimeIn(List<Date> values) {
            addCriterionForJDBCDate("actual_tran_time in", values, "actualTranTime");
            return (Criteria) this;
        }

        public Criteria andActualTranTimeNotIn(List<Date> values) {
            addCriterionForJDBCDate("actual_tran_time not in", values, "actualTranTime");
            return (Criteria) this;
        }

        public Criteria andActualTranTimeBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("actual_tran_time between", value1, value2, "actualTranTime");
            return (Criteria) this;
        }

        public Criteria andActualTranTimeNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("actual_tran_time not between", value1, value2, "actualTranTime");
            return (Criteria) this;
        }

        public Criteria andBgCountIsNull() {
            addCriterion("bg_count is null");
            return (Criteria) this;
        }

        public Criteria andBgCountIsNotNull() {
            addCriterion("bg_count is not null");
            return (Criteria) this;
        }

        public Criteria andBgCountEqualTo(Long value) {
            addCriterion("bg_count =", value, "bgCount");
            return (Criteria) this;
        }

        public Criteria andBgCountNotEqualTo(Long value) {
            addCriterion("bg_count <>", value, "bgCount");
            return (Criteria) this;
        }

        public Criteria andBgCountGreaterThan(Long value) {
            addCriterion("bg_count >", value, "bgCount");
            return (Criteria) this;
        }

        public Criteria andBgCountGreaterThanOrEqualTo(Long value) {
            addCriterion("bg_count >=", value, "bgCount");
            return (Criteria) this;
        }

        public Criteria andBgCountLessThan(Long value) {
            addCriterion("bg_count <", value, "bgCount");
            return (Criteria) this;
        }

        public Criteria andBgCountLessThanOrEqualTo(Long value) {
            addCriterion("bg_count <=", value, "bgCount");
            return (Criteria) this;
        }

        public Criteria andBgCountIn(List<Long> values) {
            addCriterion("bg_count in", values, "bgCount");
            return (Criteria) this;
        }

        public Criteria andBgCountNotIn(List<Long> values) {
            addCriterion("bg_count not in", values, "bgCount");
            return (Criteria) this;
        }

        public Criteria andBgCountBetween(Long value1, Long value2) {
            addCriterion("bg_count between", value1, value2, "bgCount");
            return (Criteria) this;
        }

        public Criteria andBgCountNotBetween(Long value1, Long value2) {
            addCriterion("bg_count not between", value1, value2, "bgCount");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }

        public BranchViewExample.Criteria addPermits(List<Integer> partyIdList, List<Integer> branchIdList) {

            if(ShiroHelper.isPermitted(RoleConstants.PERMISSION_PARTYVIEWALL))
                return this;

            if(partyIdList==null) partyIdList = new ArrayList<>();
            if(branchIdList==null) branchIdList = new ArrayList<>();

            if(!partyIdList.isEmpty() && !branchIdList.isEmpty())
                addCriterion("(party_id in(" + StringUtils.join(partyIdList, ",") + ") OR id in(" + StringUtils.join(branchIdList, ",") + "))");
            if(partyIdList.isEmpty() && !branchIdList.isEmpty())
                andIdIn(branchIdList);
            if(branchIdList.isEmpty() && !partyIdList.isEmpty())
                andPartyIdIn(partyIdList);
            if(branchIdList.isEmpty() && partyIdList.isEmpty())
                andIdIsNull();
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