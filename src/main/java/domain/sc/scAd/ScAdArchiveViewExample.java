package domain.sc.scAd;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class ScAdArchiveViewExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public ScAdArchiveViewExample() {
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

        public Criteria andCadreIdIsNull() {
            addCriterion("cadre_id is null");
            return (Criteria) this;
        }

        public Criteria andCadreIdIsNotNull() {
            addCriterion("cadre_id is not null");
            return (Criteria) this;
        }

        public Criteria andCadreIdEqualTo(Integer value) {
            addCriterion("cadre_id =", value, "cadreId");
            return (Criteria) this;
        }

        public Criteria andCadreIdNotEqualTo(Integer value) {
            addCriterion("cadre_id <>", value, "cadreId");
            return (Criteria) this;
        }

        public Criteria andCadreIdGreaterThan(Integer value) {
            addCriterion("cadre_id >", value, "cadreId");
            return (Criteria) this;
        }

        public Criteria andCadreIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("cadre_id >=", value, "cadreId");
            return (Criteria) this;
        }

        public Criteria andCadreIdLessThan(Integer value) {
            addCriterion("cadre_id <", value, "cadreId");
            return (Criteria) this;
        }

        public Criteria andCadreIdLessThanOrEqualTo(Integer value) {
            addCriterion("cadre_id <=", value, "cadreId");
            return (Criteria) this;
        }

        public Criteria andCadreIdIn(List<Integer> values) {
            addCriterion("cadre_id in", values, "cadreId");
            return (Criteria) this;
        }

        public Criteria andCadreIdNotIn(List<Integer> values) {
            addCriterion("cadre_id not in", values, "cadreId");
            return (Criteria) this;
        }

        public Criteria andCadreIdBetween(Integer value1, Integer value2) {
            addCriterion("cadre_id between", value1, value2, "cadreId");
            return (Criteria) this;
        }

        public Criteria andCadreIdNotBetween(Integer value1, Integer value2) {
            addCriterion("cadre_id not between", value1, value2, "cadreId");
            return (Criteria) this;
        }

        public Criteria andIsAdformSavedIsNull() {
            addCriterion("is_adform_saved is null");
            return (Criteria) this;
        }

        public Criteria andIsAdformSavedIsNotNull() {
            addCriterion("is_adform_saved is not null");
            return (Criteria) this;
        }

        public Criteria andIsAdformSavedEqualTo(Boolean value) {
            addCriterion("is_adform_saved =", value, "isAdformSaved");
            return (Criteria) this;
        }

        public Criteria andIsAdformSavedNotEqualTo(Boolean value) {
            addCriterion("is_adform_saved <>", value, "isAdformSaved");
            return (Criteria) this;
        }

        public Criteria andIsAdformSavedGreaterThan(Boolean value) {
            addCriterion("is_adform_saved >", value, "isAdformSaved");
            return (Criteria) this;
        }

        public Criteria andIsAdformSavedGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_adform_saved >=", value, "isAdformSaved");
            return (Criteria) this;
        }

        public Criteria andIsAdformSavedLessThan(Boolean value) {
            addCriterion("is_adform_saved <", value, "isAdformSaved");
            return (Criteria) this;
        }

        public Criteria andIsAdformSavedLessThanOrEqualTo(Boolean value) {
            addCriterion("is_adform_saved <=", value, "isAdformSaved");
            return (Criteria) this;
        }

        public Criteria andIsAdformSavedIn(List<Boolean> values) {
            addCriterion("is_adform_saved in", values, "isAdformSaved");
            return (Criteria) this;
        }

        public Criteria andIsAdformSavedNotIn(List<Boolean> values) {
            addCriterion("is_adform_saved not in", values, "isAdformSaved");
            return (Criteria) this;
        }

        public Criteria andIsAdformSavedBetween(Boolean value1, Boolean value2) {
            addCriterion("is_adform_saved between", value1, value2, "isAdformSaved");
            return (Criteria) this;
        }

        public Criteria andIsAdformSavedNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_adform_saved not between", value1, value2, "isAdformSaved");
            return (Criteria) this;
        }

        public Criteria andHasAppointIsNull() {
            addCriterion("has_appoint is null");
            return (Criteria) this;
        }

        public Criteria andHasAppointIsNotNull() {
            addCriterion("has_appoint is not null");
            return (Criteria) this;
        }

        public Criteria andHasAppointEqualTo(Boolean value) {
            addCriterion("has_appoint =", value, "hasAppoint");
            return (Criteria) this;
        }

        public Criteria andHasAppointNotEqualTo(Boolean value) {
            addCriterion("has_appoint <>", value, "hasAppoint");
            return (Criteria) this;
        }

        public Criteria andHasAppointGreaterThan(Boolean value) {
            addCriterion("has_appoint >", value, "hasAppoint");
            return (Criteria) this;
        }

        public Criteria andHasAppointGreaterThanOrEqualTo(Boolean value) {
            addCriterion("has_appoint >=", value, "hasAppoint");
            return (Criteria) this;
        }

        public Criteria andHasAppointLessThan(Boolean value) {
            addCriterion("has_appoint <", value, "hasAppoint");
            return (Criteria) this;
        }

        public Criteria andHasAppointLessThanOrEqualTo(Boolean value) {
            addCriterion("has_appoint <=", value, "hasAppoint");
            return (Criteria) this;
        }

        public Criteria andHasAppointIn(List<Boolean> values) {
            addCriterion("has_appoint in", values, "hasAppoint");
            return (Criteria) this;
        }

        public Criteria andHasAppointNotIn(List<Boolean> values) {
            addCriterion("has_appoint not in", values, "hasAppoint");
            return (Criteria) this;
        }

        public Criteria andHasAppointBetween(Boolean value1, Boolean value2) {
            addCriterion("has_appoint between", value1, value2, "hasAppoint");
            return (Criteria) this;
        }

        public Criteria andHasAppointNotBetween(Boolean value1, Boolean value2) {
            addCriterion("has_appoint not between", value1, value2, "hasAppoint");
            return (Criteria) this;
        }

        public Criteria andObjIdIsNull() {
            addCriterion("obj_id is null");
            return (Criteria) this;
        }

        public Criteria andObjIdIsNotNull() {
            addCriterion("obj_id is not null");
            return (Criteria) this;
        }

        public Criteria andObjIdEqualTo(Integer value) {
            addCriterion("obj_id =", value, "objId");
            return (Criteria) this;
        }

        public Criteria andObjIdNotEqualTo(Integer value) {
            addCriterion("obj_id <>", value, "objId");
            return (Criteria) this;
        }

        public Criteria andObjIdGreaterThan(Integer value) {
            addCriterion("obj_id >", value, "objId");
            return (Criteria) this;
        }

        public Criteria andObjIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("obj_id >=", value, "objId");
            return (Criteria) this;
        }

        public Criteria andObjIdLessThan(Integer value) {
            addCriterion("obj_id <", value, "objId");
            return (Criteria) this;
        }

        public Criteria andObjIdLessThanOrEqualTo(Integer value) {
            addCriterion("obj_id <=", value, "objId");
            return (Criteria) this;
        }

        public Criteria andObjIdIn(List<Integer> values) {
            addCriterion("obj_id in", values, "objId");
            return (Criteria) this;
        }

        public Criteria andObjIdNotIn(List<Integer> values) {
            addCriterion("obj_id not in", values, "objId");
            return (Criteria) this;
        }

        public Criteria andObjIdBetween(Integer value1, Integer value2) {
            addCriterion("obj_id between", value1, value2, "objId");
            return (Criteria) this;
        }

        public Criteria andObjIdNotBetween(Integer value1, Integer value2) {
            addCriterion("obj_id not between", value1, value2, "objId");
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

        public Criteria andSignFilePathIsNull() {
            addCriterion("sign_file_path is null");
            return (Criteria) this;
        }

        public Criteria andSignFilePathIsNotNull() {
            addCriterion("sign_file_path is not null");
            return (Criteria) this;
        }

        public Criteria andSignFilePathEqualTo(String value) {
            addCriterion("sign_file_path =", value, "signFilePath");
            return (Criteria) this;
        }

        public Criteria andSignFilePathNotEqualTo(String value) {
            addCriterion("sign_file_path <>", value, "signFilePath");
            return (Criteria) this;
        }

        public Criteria andSignFilePathGreaterThan(String value) {
            addCriterion("sign_file_path >", value, "signFilePath");
            return (Criteria) this;
        }

        public Criteria andSignFilePathGreaterThanOrEqualTo(String value) {
            addCriterion("sign_file_path >=", value, "signFilePath");
            return (Criteria) this;
        }

        public Criteria andSignFilePathLessThan(String value) {
            addCriterion("sign_file_path <", value, "signFilePath");
            return (Criteria) this;
        }

        public Criteria andSignFilePathLessThanOrEqualTo(String value) {
            addCriterion("sign_file_path <=", value, "signFilePath");
            return (Criteria) this;
        }

        public Criteria andSignFilePathLike(String value) {
            addCriterion("sign_file_path like", value, "signFilePath");
            return (Criteria) this;
        }

        public Criteria andSignFilePathNotLike(String value) {
            addCriterion("sign_file_path not like", value, "signFilePath");
            return (Criteria) this;
        }

        public Criteria andSignFilePathIn(List<String> values) {
            addCriterion("sign_file_path in", values, "signFilePath");
            return (Criteria) this;
        }

        public Criteria andSignFilePathNotIn(List<String> values) {
            addCriterion("sign_file_path not in", values, "signFilePath");
            return (Criteria) this;
        }

        public Criteria andSignFilePathBetween(String value1, String value2) {
            addCriterion("sign_file_path between", value1, value2, "signFilePath");
            return (Criteria) this;
        }

        public Criteria andSignFilePathNotBetween(String value1, String value2) {
            addCriterion("sign_file_path not between", value1, value2, "signFilePath");
            return (Criteria) this;
        }

        public Criteria andCisFilePathIsNull() {
            addCriterion("cis_file_path is null");
            return (Criteria) this;
        }

        public Criteria andCisFilePathIsNotNull() {
            addCriterion("cis_file_path is not null");
            return (Criteria) this;
        }

        public Criteria andCisFilePathEqualTo(String value) {
            addCriterion("cis_file_path =", value, "cisFilePath");
            return (Criteria) this;
        }

        public Criteria andCisFilePathNotEqualTo(String value) {
            addCriterion("cis_file_path <>", value, "cisFilePath");
            return (Criteria) this;
        }

        public Criteria andCisFilePathGreaterThan(String value) {
            addCriterion("cis_file_path >", value, "cisFilePath");
            return (Criteria) this;
        }

        public Criteria andCisFilePathGreaterThanOrEqualTo(String value) {
            addCriterion("cis_file_path >=", value, "cisFilePath");
            return (Criteria) this;
        }

        public Criteria andCisFilePathLessThan(String value) {
            addCriterion("cis_file_path <", value, "cisFilePath");
            return (Criteria) this;
        }

        public Criteria andCisFilePathLessThanOrEqualTo(String value) {
            addCriterion("cis_file_path <=", value, "cisFilePath");
            return (Criteria) this;
        }

        public Criteria andCisFilePathLike(String value) {
            addCriterion("cis_file_path like", value, "cisFilePath");
            return (Criteria) this;
        }

        public Criteria andCisFilePathNotLike(String value) {
            addCriterion("cis_file_path not like", value, "cisFilePath");
            return (Criteria) this;
        }

        public Criteria andCisFilePathIn(List<String> values) {
            addCriterion("cis_file_path in", values, "cisFilePath");
            return (Criteria) this;
        }

        public Criteria andCisFilePathNotIn(List<String> values) {
            addCriterion("cis_file_path not in", values, "cisFilePath");
            return (Criteria) this;
        }

        public Criteria andCisFilePathBetween(String value1, String value2) {
            addCriterion("cis_file_path between", value1, value2, "cisFilePath");
            return (Criteria) this;
        }

        public Criteria andCisFilePathNotBetween(String value1, String value2) {
            addCriterion("cis_file_path not between", value1, value2, "cisFilePath");
            return (Criteria) this;
        }

        public Criteria andCisSignFilePathIsNull() {
            addCriterion("cis_sign_file_path is null");
            return (Criteria) this;
        }

        public Criteria andCisSignFilePathIsNotNull() {
            addCriterion("cis_sign_file_path is not null");
            return (Criteria) this;
        }

        public Criteria andCisSignFilePathEqualTo(String value) {
            addCriterion("cis_sign_file_path =", value, "cisSignFilePath");
            return (Criteria) this;
        }

        public Criteria andCisSignFilePathNotEqualTo(String value) {
            addCriterion("cis_sign_file_path <>", value, "cisSignFilePath");
            return (Criteria) this;
        }

        public Criteria andCisSignFilePathGreaterThan(String value) {
            addCriterion("cis_sign_file_path >", value, "cisSignFilePath");
            return (Criteria) this;
        }

        public Criteria andCisSignFilePathGreaterThanOrEqualTo(String value) {
            addCriterion("cis_sign_file_path >=", value, "cisSignFilePath");
            return (Criteria) this;
        }

        public Criteria andCisSignFilePathLessThan(String value) {
            addCriterion("cis_sign_file_path <", value, "cisSignFilePath");
            return (Criteria) this;
        }

        public Criteria andCisSignFilePathLessThanOrEqualTo(String value) {
            addCriterion("cis_sign_file_path <=", value, "cisSignFilePath");
            return (Criteria) this;
        }

        public Criteria andCisSignFilePathLike(String value) {
            addCriterion("cis_sign_file_path like", value, "cisSignFilePath");
            return (Criteria) this;
        }

        public Criteria andCisSignFilePathNotLike(String value) {
            addCriterion("cis_sign_file_path not like", value, "cisSignFilePath");
            return (Criteria) this;
        }

        public Criteria andCisSignFilePathIn(List<String> values) {
            addCriterion("cis_sign_file_path in", values, "cisSignFilePath");
            return (Criteria) this;
        }

        public Criteria andCisSignFilePathNotIn(List<String> values) {
            addCriterion("cis_sign_file_path not in", values, "cisSignFilePath");
            return (Criteria) this;
        }

        public Criteria andCisSignFilePathBetween(String value1, String value2) {
            addCriterion("cis_sign_file_path between", value1, value2, "cisSignFilePath");
            return (Criteria) this;
        }

        public Criteria andCisSignFilePathNotBetween(String value1, String value2) {
            addCriterion("cis_sign_file_path not between", value1, value2, "cisSignFilePath");
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

        public Criteria andCommitteeFilePathIsNull() {
            addCriterion("committee_file_path is null");
            return (Criteria) this;
        }

        public Criteria andCommitteeFilePathIsNotNull() {
            addCriterion("committee_file_path is not null");
            return (Criteria) this;
        }

        public Criteria andCommitteeFilePathEqualTo(String value) {
            addCriterion("committee_file_path =", value, "committeeFilePath");
            return (Criteria) this;
        }

        public Criteria andCommitteeFilePathNotEqualTo(String value) {
            addCriterion("committee_file_path <>", value, "committeeFilePath");
            return (Criteria) this;
        }

        public Criteria andCommitteeFilePathGreaterThan(String value) {
            addCriterion("committee_file_path >", value, "committeeFilePath");
            return (Criteria) this;
        }

        public Criteria andCommitteeFilePathGreaterThanOrEqualTo(String value) {
            addCriterion("committee_file_path >=", value, "committeeFilePath");
            return (Criteria) this;
        }

        public Criteria andCommitteeFilePathLessThan(String value) {
            addCriterion("committee_file_path <", value, "committeeFilePath");
            return (Criteria) this;
        }

        public Criteria andCommitteeFilePathLessThanOrEqualTo(String value) {
            addCriterion("committee_file_path <=", value, "committeeFilePath");
            return (Criteria) this;
        }

        public Criteria andCommitteeFilePathLike(String value) {
            addCriterion("committee_file_path like", value, "committeeFilePath");
            return (Criteria) this;
        }

        public Criteria andCommitteeFilePathNotLike(String value) {
            addCriterion("committee_file_path not like", value, "committeeFilePath");
            return (Criteria) this;
        }

        public Criteria andCommitteeFilePathIn(List<String> values) {
            addCriterion("committee_file_path in", values, "committeeFilePath");
            return (Criteria) this;
        }

        public Criteria andCommitteeFilePathNotIn(List<String> values) {
            addCriterion("committee_file_path not in", values, "committeeFilePath");
            return (Criteria) this;
        }

        public Criteria andCommitteeFilePathBetween(String value1, String value2) {
            addCriterion("committee_file_path between", value1, value2, "committeeFilePath");
            return (Criteria) this;
        }

        public Criteria andCommitteeFilePathNotBetween(String value1, String value2) {
            addCriterion("committee_file_path not between", value1, value2, "committeeFilePath");
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