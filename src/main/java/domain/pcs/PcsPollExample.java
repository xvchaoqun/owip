package domain.pcs;

import org.apache.commons.lang3.StringUtils;
import shiro.ShiroHelper;
import sys.constants.RoleConstants;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class PcsPollExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public PcsPollExample() {
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

        public Criteria andBranchNameIsNull() {
            addCriterion("branch_name is null");
            return (Criteria) this;
        }

        public Criteria andBranchNameIsNotNull() {
            addCriterion("branch_name is not null");
            return (Criteria) this;
        }

        public Criteria andBranchNameEqualTo(String value) {
            addCriterion("branch_name =", value, "branchName");
            return (Criteria) this;
        }

        public Criteria andBranchNameNotEqualTo(String value) {
            addCriterion("branch_name <>", value, "branchName");
            return (Criteria) this;
        }

        public Criteria andBranchNameGreaterThan(String value) {
            addCriterion("branch_name >", value, "branchName");
            return (Criteria) this;
        }

        public Criteria andBranchNameGreaterThanOrEqualTo(String value) {
            addCriterion("branch_name >=", value, "branchName");
            return (Criteria) this;
        }

        public Criteria andBranchNameLessThan(String value) {
            addCriterion("branch_name <", value, "branchName");
            return (Criteria) this;
        }

        public Criteria andBranchNameLessThanOrEqualTo(String value) {
            addCriterion("branch_name <=", value, "branchName");
            return (Criteria) this;
        }

        public Criteria andBranchNameLike(String value) {
            addCriterion("branch_name like", value, "branchName");
            return (Criteria) this;
        }

        public Criteria andBranchNameNotLike(String value) {
            addCriterion("branch_name not like", value, "branchName");
            return (Criteria) this;
        }

        public Criteria andBranchNameIn(List<String> values) {
            addCriterion("branch_name in", values, "branchName");
            return (Criteria) this;
        }

        public Criteria andBranchNameNotIn(List<String> values) {
            addCriterion("branch_name not in", values, "branchName");
            return (Criteria) this;
        }

        public Criteria andBranchNameBetween(String value1, String value2) {
            addCriterion("branch_name between", value1, value2, "branchName");
            return (Criteria) this;
        }

        public Criteria andBranchNameNotBetween(String value1, String value2) {
            addCriterion("branch_name not between", value1, value2, "branchName");
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

        public Criteria andConfigIdIsNull() {
            addCriterion("config_id is null");
            return (Criteria) this;
        }

        public Criteria andConfigIdIsNotNull() {
            addCriterion("config_id is not null");
            return (Criteria) this;
        }

        public Criteria andConfigIdEqualTo(Integer value) {
            addCriterion("config_id =", value, "configId");
            return (Criteria) this;
        }

        public Criteria andConfigIdNotEqualTo(Integer value) {
            addCriterion("config_id <>", value, "configId");
            return (Criteria) this;
        }

        public Criteria andConfigIdGreaterThan(Integer value) {
            addCriterion("config_id >", value, "configId");
            return (Criteria) this;
        }

        public Criteria andConfigIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("config_id >=", value, "configId");
            return (Criteria) this;
        }

        public Criteria andConfigIdLessThan(Integer value) {
            addCriterion("config_id <", value, "configId");
            return (Criteria) this;
        }

        public Criteria andConfigIdLessThanOrEqualTo(Integer value) {
            addCriterion("config_id <=", value, "configId");
            return (Criteria) this;
        }

        public Criteria andConfigIdIn(List<Integer> values) {
            addCriterion("config_id in", values, "configId");
            return (Criteria) this;
        }

        public Criteria andConfigIdNotIn(List<Integer> values) {
            addCriterion("config_id not in", values, "configId");
            return (Criteria) this;
        }

        public Criteria andConfigIdBetween(Integer value1, Integer value2) {
            addCriterion("config_id between", value1, value2, "configId");
            return (Criteria) this;
        }

        public Criteria andConfigIdNotBetween(Integer value1, Integer value2) {
            addCriterion("config_id not between", value1, value2, "configId");
            return (Criteria) this;
        }

        public Criteria andStageIsNull() {
            addCriterion("stage is null");
            return (Criteria) this;
        }

        public Criteria andStageIsNotNull() {
            addCriterion("stage is not null");
            return (Criteria) this;
        }

        public Criteria andStageEqualTo(Byte value) {
            addCriterion("stage =", value, "stage");
            return (Criteria) this;
        }

        public Criteria andStageNotEqualTo(Byte value) {
            addCriterion("stage <>", value, "stage");
            return (Criteria) this;
        }

        public Criteria andStageGreaterThan(Byte value) {
            addCriterion("stage >", value, "stage");
            return (Criteria) this;
        }

        public Criteria andStageGreaterThanOrEqualTo(Byte value) {
            addCriterion("stage >=", value, "stage");
            return (Criteria) this;
        }

        public Criteria andStageLessThan(Byte value) {
            addCriterion("stage <", value, "stage");
            return (Criteria) this;
        }

        public Criteria andStageLessThanOrEqualTo(Byte value) {
            addCriterion("stage <=", value, "stage");
            return (Criteria) this;
        }

        public Criteria andStageIn(List<Byte> values) {
            addCriterion("stage in", values, "stage");
            return (Criteria) this;
        }

        public Criteria andStageNotIn(List<Byte> values) {
            addCriterion("stage not in", values, "stage");
            return (Criteria) this;
        }

        public Criteria andStageBetween(Byte value1, Byte value2) {
            addCriterion("stage between", value1, value2, "stage");
            return (Criteria) this;
        }

        public Criteria andStageNotBetween(Byte value1, Byte value2) {
            addCriterion("stage not between", value1, value2, "stage");
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

        public Criteria andPrNumIsNull() {
            addCriterion("pr_num is null");
            return (Criteria) this;
        }

        public Criteria andPrNumIsNotNull() {
            addCriterion("pr_num is not null");
            return (Criteria) this;
        }

        public Criteria andPrNumEqualTo(Integer value) {
            addCriterion("pr_num =", value, "prNum");
            return (Criteria) this;
        }

        public Criteria andPrNumNotEqualTo(Integer value) {
            addCriterion("pr_num <>", value, "prNum");
            return (Criteria) this;
        }

        public Criteria andPrNumGreaterThan(Integer value) {
            addCriterion("pr_num >", value, "prNum");
            return (Criteria) this;
        }

        public Criteria andPrNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("pr_num >=", value, "prNum");
            return (Criteria) this;
        }

        public Criteria andPrNumLessThan(Integer value) {
            addCriterion("pr_num <", value, "prNum");
            return (Criteria) this;
        }

        public Criteria andPrNumLessThanOrEqualTo(Integer value) {
            addCriterion("pr_num <=", value, "prNum");
            return (Criteria) this;
        }

        public Criteria andPrNumIn(List<Integer> values) {
            addCriterion("pr_num in", values, "prNum");
            return (Criteria) this;
        }

        public Criteria andPrNumNotIn(List<Integer> values) {
            addCriterion("pr_num not in", values, "prNum");
            return (Criteria) this;
        }

        public Criteria andPrNumBetween(Integer value1, Integer value2) {
            addCriterion("pr_num between", value1, value2, "prNum");
            return (Criteria) this;
        }

        public Criteria andPrNumNotBetween(Integer value1, Integer value2) {
            addCriterion("pr_num not between", value1, value2, "prNum");
            return (Criteria) this;
        }

        public Criteria andDwNumIsNull() {
            addCriterion("dw_num is null");
            return (Criteria) this;
        }

        public Criteria andDwNumIsNotNull() {
            addCriterion("dw_num is not null");
            return (Criteria) this;
        }

        public Criteria andDwNumEqualTo(Integer value) {
            addCriterion("dw_num =", value, "dwNum");
            return (Criteria) this;
        }

        public Criteria andDwNumNotEqualTo(Integer value) {
            addCriterion("dw_num <>", value, "dwNum");
            return (Criteria) this;
        }

        public Criteria andDwNumGreaterThan(Integer value) {
            addCriterion("dw_num >", value, "dwNum");
            return (Criteria) this;
        }

        public Criteria andDwNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("dw_num >=", value, "dwNum");
            return (Criteria) this;
        }

        public Criteria andDwNumLessThan(Integer value) {
            addCriterion("dw_num <", value, "dwNum");
            return (Criteria) this;
        }

        public Criteria andDwNumLessThanOrEqualTo(Integer value) {
            addCriterion("dw_num <=", value, "dwNum");
            return (Criteria) this;
        }

        public Criteria andDwNumIn(List<Integer> values) {
            addCriterion("dw_num in", values, "dwNum");
            return (Criteria) this;
        }

        public Criteria andDwNumNotIn(List<Integer> values) {
            addCriterion("dw_num not in", values, "dwNum");
            return (Criteria) this;
        }

        public Criteria andDwNumBetween(Integer value1, Integer value2) {
            addCriterion("dw_num between", value1, value2, "dwNum");
            return (Criteria) this;
        }

        public Criteria andDwNumNotBetween(Integer value1, Integer value2) {
            addCriterion("dw_num not between", value1, value2, "dwNum");
            return (Criteria) this;
        }

        public Criteria andJwNumIsNull() {
            addCriterion("jw_num is null");
            return (Criteria) this;
        }

        public Criteria andJwNumIsNotNull() {
            addCriterion("jw_num is not null");
            return (Criteria) this;
        }

        public Criteria andJwNumEqualTo(Integer value) {
            addCriterion("jw_num =", value, "jwNum");
            return (Criteria) this;
        }

        public Criteria andJwNumNotEqualTo(Integer value) {
            addCriterion("jw_num <>", value, "jwNum");
            return (Criteria) this;
        }

        public Criteria andJwNumGreaterThan(Integer value) {
            addCriterion("jw_num >", value, "jwNum");
            return (Criteria) this;
        }

        public Criteria andJwNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("jw_num >=", value, "jwNum");
            return (Criteria) this;
        }

        public Criteria andJwNumLessThan(Integer value) {
            addCriterion("jw_num <", value, "jwNum");
            return (Criteria) this;
        }

        public Criteria andJwNumLessThanOrEqualTo(Integer value) {
            addCriterion("jw_num <=", value, "jwNum");
            return (Criteria) this;
        }

        public Criteria andJwNumIn(List<Integer> values) {
            addCriterion("jw_num in", values, "jwNum");
            return (Criteria) this;
        }

        public Criteria andJwNumNotIn(List<Integer> values) {
            addCriterion("jw_num not in", values, "jwNum");
            return (Criteria) this;
        }

        public Criteria andJwNumBetween(Integer value1, Integer value2) {
            addCriterion("jw_num between", value1, value2, "jwNum");
            return (Criteria) this;
        }

        public Criteria andJwNumNotBetween(Integer value1, Integer value2) {
            addCriterion("jw_num not between", value1, value2, "jwNum");
            return (Criteria) this;
        }

        public Criteria andExpectMemberCountIsNull() {
            addCriterion("expect_member_count is null");
            return (Criteria) this;
        }

        public Criteria andExpectMemberCountIsNotNull() {
            addCriterion("expect_member_count is not null");
            return (Criteria) this;
        }

        public Criteria andExpectMemberCountEqualTo(Integer value) {
            addCriterion("expect_member_count =", value, "expectMemberCount");
            return (Criteria) this;
        }

        public Criteria andExpectMemberCountNotEqualTo(Integer value) {
            addCriterion("expect_member_count <>", value, "expectMemberCount");
            return (Criteria) this;
        }

        public Criteria andExpectMemberCountGreaterThan(Integer value) {
            addCriterion("expect_member_count >", value, "expectMemberCount");
            return (Criteria) this;
        }

        public Criteria andExpectMemberCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("expect_member_count >=", value, "expectMemberCount");
            return (Criteria) this;
        }

        public Criteria andExpectMemberCountLessThan(Integer value) {
            addCriterion("expect_member_count <", value, "expectMemberCount");
            return (Criteria) this;
        }

        public Criteria andExpectMemberCountLessThanOrEqualTo(Integer value) {
            addCriterion("expect_member_count <=", value, "expectMemberCount");
            return (Criteria) this;
        }

        public Criteria andExpectMemberCountIn(List<Integer> values) {
            addCriterion("expect_member_count in", values, "expectMemberCount");
            return (Criteria) this;
        }

        public Criteria andExpectMemberCountNotIn(List<Integer> values) {
            addCriterion("expect_member_count not in", values, "expectMemberCount");
            return (Criteria) this;
        }

        public Criteria andExpectMemberCountBetween(Integer value1, Integer value2) {
            addCriterion("expect_member_count between", value1, value2, "expectMemberCount");
            return (Criteria) this;
        }

        public Criteria andExpectMemberCountNotBetween(Integer value1, Integer value2) {
            addCriterion("expect_member_count not between", value1, value2, "expectMemberCount");
            return (Criteria) this;
        }

        public Criteria andActualMemberCountIsNull() {
            addCriterion("actual_member_count is null");
            return (Criteria) this;
        }

        public Criteria andActualMemberCountIsNotNull() {
            addCriterion("actual_member_count is not null");
            return (Criteria) this;
        }

        public Criteria andActualMemberCountEqualTo(Integer value) {
            addCriterion("actual_member_count =", value, "actualMemberCount");
            return (Criteria) this;
        }

        public Criteria andActualMemberCountNotEqualTo(Integer value) {
            addCriterion("actual_member_count <>", value, "actualMemberCount");
            return (Criteria) this;
        }

        public Criteria andActualMemberCountGreaterThan(Integer value) {
            addCriterion("actual_member_count >", value, "actualMemberCount");
            return (Criteria) this;
        }

        public Criteria andActualMemberCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("actual_member_count >=", value, "actualMemberCount");
            return (Criteria) this;
        }

        public Criteria andActualMemberCountLessThan(Integer value) {
            addCriterion("actual_member_count <", value, "actualMemberCount");
            return (Criteria) this;
        }

        public Criteria andActualMemberCountLessThanOrEqualTo(Integer value) {
            addCriterion("actual_member_count <=", value, "actualMemberCount");
            return (Criteria) this;
        }

        public Criteria andActualMemberCountIn(List<Integer> values) {
            addCriterion("actual_member_count in", values, "actualMemberCount");
            return (Criteria) this;
        }

        public Criteria andActualMemberCountNotIn(List<Integer> values) {
            addCriterion("actual_member_count not in", values, "actualMemberCount");
            return (Criteria) this;
        }

        public Criteria andActualMemberCountBetween(Integer value1, Integer value2) {
            addCriterion("actual_member_count between", value1, value2, "actualMemberCount");
            return (Criteria) this;
        }

        public Criteria andActualMemberCountNotBetween(Integer value1, Integer value2) {
            addCriterion("actual_member_count not between", value1, value2, "actualMemberCount");
            return (Criteria) this;
        }

        public Criteria andReportDateIsNull() {
            addCriterion("report_date is null");
            return (Criteria) this;
        }

        public Criteria andReportDateIsNotNull() {
            addCriterion("report_date is not null");
            return (Criteria) this;
        }

        public Criteria andReportDateEqualTo(Date value) {
            addCriterionForJDBCDate("report_date =", value, "reportDate");
            return (Criteria) this;
        }

        public Criteria andReportDateNotEqualTo(Date value) {
            addCriterionForJDBCDate("report_date <>", value, "reportDate");
            return (Criteria) this;
        }

        public Criteria andReportDateGreaterThan(Date value) {
            addCriterionForJDBCDate("report_date >", value, "reportDate");
            return (Criteria) this;
        }

        public Criteria andReportDateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("report_date >=", value, "reportDate");
            return (Criteria) this;
        }

        public Criteria andReportDateLessThan(Date value) {
            addCriterionForJDBCDate("report_date <", value, "reportDate");
            return (Criteria) this;
        }

        public Criteria andReportDateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("report_date <=", value, "reportDate");
            return (Criteria) this;
        }

        public Criteria andReportDateIn(List<Date> values) {
            addCriterionForJDBCDate("report_date in", values, "reportDate");
            return (Criteria) this;
        }

        public Criteria andReportDateNotIn(List<Date> values) {
            addCriterionForJDBCDate("report_date not in", values, "reportDate");
            return (Criteria) this;
        }

        public Criteria andReportDateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("report_date between", value1, value2, "reportDate");
            return (Criteria) this;
        }

        public Criteria andReportDateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("report_date not between", value1, value2, "reportDate");
            return (Criteria) this;
        }

        public Criteria andInspectorNumIsNull() {
            addCriterion("inspector_num is null");
            return (Criteria) this;
        }

        public Criteria andInspectorNumIsNotNull() {
            addCriterion("inspector_num is not null");
            return (Criteria) this;
        }

        public Criteria andInspectorNumEqualTo(Integer value) {
            addCriterion("inspector_num =", value, "inspectorNum");
            return (Criteria) this;
        }

        public Criteria andInspectorNumNotEqualTo(Integer value) {
            addCriterion("inspector_num <>", value, "inspectorNum");
            return (Criteria) this;
        }

        public Criteria andInspectorNumGreaterThan(Integer value) {
            addCriterion("inspector_num >", value, "inspectorNum");
            return (Criteria) this;
        }

        public Criteria andInspectorNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("inspector_num >=", value, "inspectorNum");
            return (Criteria) this;
        }

        public Criteria andInspectorNumLessThan(Integer value) {
            addCriterion("inspector_num <", value, "inspectorNum");
            return (Criteria) this;
        }

        public Criteria andInspectorNumLessThanOrEqualTo(Integer value) {
            addCriterion("inspector_num <=", value, "inspectorNum");
            return (Criteria) this;
        }

        public Criteria andInspectorNumIn(List<Integer> values) {
            addCriterion("inspector_num in", values, "inspectorNum");
            return (Criteria) this;
        }

        public Criteria andInspectorNumNotIn(List<Integer> values) {
            addCriterion("inspector_num not in", values, "inspectorNum");
            return (Criteria) this;
        }

        public Criteria andInspectorNumBetween(Integer value1, Integer value2) {
            addCriterion("inspector_num between", value1, value2, "inspectorNum");
            return (Criteria) this;
        }

        public Criteria andInspectorNumNotBetween(Integer value1, Integer value2) {
            addCriterion("inspector_num not between", value1, value2, "inspectorNum");
            return (Criteria) this;
        }

        public Criteria andInspectorFinishNumIsNull() {
            addCriterion("inspector_finish_num is null");
            return (Criteria) this;
        }

        public Criteria andInspectorFinishNumIsNotNull() {
            addCriterion("inspector_finish_num is not null");
            return (Criteria) this;
        }

        public Criteria andInspectorFinishNumEqualTo(Integer value) {
            addCriterion("inspector_finish_num =", value, "inspectorFinishNum");
            return (Criteria) this;
        }

        public Criteria andInspectorFinishNumNotEqualTo(Integer value) {
            addCriterion("inspector_finish_num <>", value, "inspectorFinishNum");
            return (Criteria) this;
        }

        public Criteria andInspectorFinishNumGreaterThan(Integer value) {
            addCriterion("inspector_finish_num >", value, "inspectorFinishNum");
            return (Criteria) this;
        }

        public Criteria andInspectorFinishNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("inspector_finish_num >=", value, "inspectorFinishNum");
            return (Criteria) this;
        }

        public Criteria andInspectorFinishNumLessThan(Integer value) {
            addCriterion("inspector_finish_num <", value, "inspectorFinishNum");
            return (Criteria) this;
        }

        public Criteria andInspectorFinishNumLessThanOrEqualTo(Integer value) {
            addCriterion("inspector_finish_num <=", value, "inspectorFinishNum");
            return (Criteria) this;
        }

        public Criteria andInspectorFinishNumIn(List<Integer> values) {
            addCriterion("inspector_finish_num in", values, "inspectorFinishNum");
            return (Criteria) this;
        }

        public Criteria andInspectorFinishNumNotIn(List<Integer> values) {
            addCriterion("inspector_finish_num not in", values, "inspectorFinishNum");
            return (Criteria) this;
        }

        public Criteria andInspectorFinishNumBetween(Integer value1, Integer value2) {
            addCriterion("inspector_finish_num between", value1, value2, "inspectorFinishNum");
            return (Criteria) this;
        }

        public Criteria andInspectorFinishNumNotBetween(Integer value1, Integer value2) {
            addCriterion("inspector_finish_num not between", value1, value2, "inspectorFinishNum");
            return (Criteria) this;
        }

        public Criteria andPositiveFinishNumIsNull() {
            addCriterion("positive_finish_num is null");
            return (Criteria) this;
        }

        public Criteria andPositiveFinishNumIsNotNull() {
            addCriterion("positive_finish_num is not null");
            return (Criteria) this;
        }

        public Criteria andPositiveFinishNumEqualTo(Integer value) {
            addCriterion("positive_finish_num =", value, "positiveFinishNum");
            return (Criteria) this;
        }

        public Criteria andPositiveFinishNumNotEqualTo(Integer value) {
            addCriterion("positive_finish_num <>", value, "positiveFinishNum");
            return (Criteria) this;
        }

        public Criteria andPositiveFinishNumGreaterThan(Integer value) {
            addCriterion("positive_finish_num >", value, "positiveFinishNum");
            return (Criteria) this;
        }

        public Criteria andPositiveFinishNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("positive_finish_num >=", value, "positiveFinishNum");
            return (Criteria) this;
        }

        public Criteria andPositiveFinishNumLessThan(Integer value) {
            addCriterion("positive_finish_num <", value, "positiveFinishNum");
            return (Criteria) this;
        }

        public Criteria andPositiveFinishNumLessThanOrEqualTo(Integer value) {
            addCriterion("positive_finish_num <=", value, "positiveFinishNum");
            return (Criteria) this;
        }

        public Criteria andPositiveFinishNumIn(List<Integer> values) {
            addCriterion("positive_finish_num in", values, "positiveFinishNum");
            return (Criteria) this;
        }

        public Criteria andPositiveFinishNumNotIn(List<Integer> values) {
            addCriterion("positive_finish_num not in", values, "positiveFinishNum");
            return (Criteria) this;
        }

        public Criteria andPositiveFinishNumBetween(Integer value1, Integer value2) {
            addCriterion("positive_finish_num between", value1, value2, "positiveFinishNum");
            return (Criteria) this;
        }

        public Criteria andPositiveFinishNumNotBetween(Integer value1, Integer value2) {
            addCriterion("positive_finish_num not between", value1, value2, "positiveFinishNum");
            return (Criteria) this;
        }

        public Criteria andNoticeIsNull() {
            addCriterion("notice is null");
            return (Criteria) this;
        }

        public Criteria andNoticeIsNotNull() {
            addCriterion("notice is not null");
            return (Criteria) this;
        }

        public Criteria andNoticeEqualTo(String value) {
            addCriterion("notice =", value, "notice");
            return (Criteria) this;
        }

        public Criteria andNoticeNotEqualTo(String value) {
            addCriterion("notice <>", value, "notice");
            return (Criteria) this;
        }

        public Criteria andNoticeGreaterThan(String value) {
            addCriterion("notice >", value, "notice");
            return (Criteria) this;
        }

        public Criteria andNoticeGreaterThanOrEqualTo(String value) {
            addCriterion("notice >=", value, "notice");
            return (Criteria) this;
        }

        public Criteria andNoticeLessThan(String value) {
            addCriterion("notice <", value, "notice");
            return (Criteria) this;
        }

        public Criteria andNoticeLessThanOrEqualTo(String value) {
            addCriterion("notice <=", value, "notice");
            return (Criteria) this;
        }

        public Criteria andNoticeLike(String value) {
            addCriterion("notice like", value, "notice");
            return (Criteria) this;
        }

        public Criteria andNoticeNotLike(String value) {
            addCriterion("notice not like", value, "notice");
            return (Criteria) this;
        }

        public Criteria andNoticeIn(List<String> values) {
            addCriterion("notice in", values, "notice");
            return (Criteria) this;
        }

        public Criteria andNoticeNotIn(List<String> values) {
            addCriterion("notice not in", values, "notice");
            return (Criteria) this;
        }

        public Criteria andNoticeBetween(String value1, String value2) {
            addCriterion("notice between", value1, value2, "notice");
            return (Criteria) this;
        }

        public Criteria andNoticeNotBetween(String value1, String value2) {
            addCriterion("notice not between", value1, value2, "notice");
            return (Criteria) this;
        }

        public Criteria andMobileNoticeIsNull() {
            addCriterion("mobile_notice is null");
            return (Criteria) this;
        }

        public Criteria andMobileNoticeIsNotNull() {
            addCriterion("mobile_notice is not null");
            return (Criteria) this;
        }

        public Criteria andMobileNoticeEqualTo(String value) {
            addCriterion("mobile_notice =", value, "mobileNotice");
            return (Criteria) this;
        }

        public Criteria andMobileNoticeNotEqualTo(String value) {
            addCriterion("mobile_notice <>", value, "mobileNotice");
            return (Criteria) this;
        }

        public Criteria andMobileNoticeGreaterThan(String value) {
            addCriterion("mobile_notice >", value, "mobileNotice");
            return (Criteria) this;
        }

        public Criteria andMobileNoticeGreaterThanOrEqualTo(String value) {
            addCriterion("mobile_notice >=", value, "mobileNotice");
            return (Criteria) this;
        }

        public Criteria andMobileNoticeLessThan(String value) {
            addCriterion("mobile_notice <", value, "mobileNotice");
            return (Criteria) this;
        }

        public Criteria andMobileNoticeLessThanOrEqualTo(String value) {
            addCriterion("mobile_notice <=", value, "mobileNotice");
            return (Criteria) this;
        }

        public Criteria andMobileNoticeLike(String value) {
            addCriterion("mobile_notice like", value, "mobileNotice");
            return (Criteria) this;
        }

        public Criteria andMobileNoticeNotLike(String value) {
            addCriterion("mobile_notice not like", value, "mobileNotice");
            return (Criteria) this;
        }

        public Criteria andMobileNoticeIn(List<String> values) {
            addCriterion("mobile_notice in", values, "mobileNotice");
            return (Criteria) this;
        }

        public Criteria andMobileNoticeNotIn(List<String> values) {
            addCriterion("mobile_notice not in", values, "mobileNotice");
            return (Criteria) this;
        }

        public Criteria andMobileNoticeBetween(String value1, String value2) {
            addCriterion("mobile_notice between", value1, value2, "mobileNotice");
            return (Criteria) this;
        }

        public Criteria andMobileNoticeNotBetween(String value1, String value2) {
            addCriterion("mobile_notice not between", value1, value2, "mobileNotice");
            return (Criteria) this;
        }

        public Criteria andInspectorNoticeIsNull() {
            addCriterion("inspector_notice is null");
            return (Criteria) this;
        }

        public Criteria andInspectorNoticeIsNotNull() {
            addCriterion("inspector_notice is not null");
            return (Criteria) this;
        }

        public Criteria andInspectorNoticeEqualTo(String value) {
            addCriterion("inspector_notice =", value, "inspectorNotice");
            return (Criteria) this;
        }

        public Criteria andInspectorNoticeNotEqualTo(String value) {
            addCriterion("inspector_notice <>", value, "inspectorNotice");
            return (Criteria) this;
        }

        public Criteria andInspectorNoticeGreaterThan(String value) {
            addCriterion("inspector_notice >", value, "inspectorNotice");
            return (Criteria) this;
        }

        public Criteria andInspectorNoticeGreaterThanOrEqualTo(String value) {
            addCriterion("inspector_notice >=", value, "inspectorNotice");
            return (Criteria) this;
        }

        public Criteria andInspectorNoticeLessThan(String value) {
            addCriterion("inspector_notice <", value, "inspectorNotice");
            return (Criteria) this;
        }

        public Criteria andInspectorNoticeLessThanOrEqualTo(String value) {
            addCriterion("inspector_notice <=", value, "inspectorNotice");
            return (Criteria) this;
        }

        public Criteria andInspectorNoticeLike(String value) {
            addCriterion("inspector_notice like", value, "inspectorNotice");
            return (Criteria) this;
        }

        public Criteria andInspectorNoticeNotLike(String value) {
            addCriterion("inspector_notice not like", value, "inspectorNotice");
            return (Criteria) this;
        }

        public Criteria andInspectorNoticeIn(List<String> values) {
            addCriterion("inspector_notice in", values, "inspectorNotice");
            return (Criteria) this;
        }

        public Criteria andInspectorNoticeNotIn(List<String> values) {
            addCriterion("inspector_notice not in", values, "inspectorNotice");
            return (Criteria) this;
        }

        public Criteria andInspectorNoticeBetween(String value1, String value2) {
            addCriterion("inspector_notice between", value1, value2, "inspectorNotice");
            return (Criteria) this;
        }

        public Criteria andInspectorNoticeNotBetween(String value1, String value2) {
            addCriterion("inspector_notice not between", value1, value2, "inspectorNotice");
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
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
        public PcsPollExample.Criteria addPermits(List<Integer> partyIdList, List<Integer> branchIdList) {

            if(ShiroHelper.isPermitted(RoleConstants.PERMISSION_PARTYVIEWALL))
                return this;

            if(partyIdList==null) partyIdList = new ArrayList<>();
            if(branchIdList==null) branchIdList = new ArrayList<>();

            if(!partyIdList.isEmpty() && !branchIdList.isEmpty())
                addCriterion("(party_id in(" + StringUtils.join(partyIdList, ",") + ") OR branch_id in(" + StringUtils.join(branchIdList, ",") + "))");
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