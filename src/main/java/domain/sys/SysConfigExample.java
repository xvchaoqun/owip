package domain.sys;

import java.util.ArrayList;
import java.util.List;

public class SysConfigExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public SysConfigExample() {
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

        public Criteria andXssIgnoreUriIsNull() {
            addCriterion("xss_ignore_uri is null");
            return (Criteria) this;
        }

        public Criteria andXssIgnoreUriIsNotNull() {
            addCriterion("xss_ignore_uri is not null");
            return (Criteria) this;
        }

        public Criteria andXssIgnoreUriEqualTo(String value) {
            addCriterion("xss_ignore_uri =", value, "xssIgnoreUri");
            return (Criteria) this;
        }

        public Criteria andXssIgnoreUriNotEqualTo(String value) {
            addCriterion("xss_ignore_uri <>", value, "xssIgnoreUri");
            return (Criteria) this;
        }

        public Criteria andXssIgnoreUriGreaterThan(String value) {
            addCriterion("xss_ignore_uri >", value, "xssIgnoreUri");
            return (Criteria) this;
        }

        public Criteria andXssIgnoreUriGreaterThanOrEqualTo(String value) {
            addCriterion("xss_ignore_uri >=", value, "xssIgnoreUri");
            return (Criteria) this;
        }

        public Criteria andXssIgnoreUriLessThan(String value) {
            addCriterion("xss_ignore_uri <", value, "xssIgnoreUri");
            return (Criteria) this;
        }

        public Criteria andXssIgnoreUriLessThanOrEqualTo(String value) {
            addCriterion("xss_ignore_uri <=", value, "xssIgnoreUri");
            return (Criteria) this;
        }

        public Criteria andXssIgnoreUriLike(String value) {
            addCriterion("xss_ignore_uri like", value, "xssIgnoreUri");
            return (Criteria) this;
        }

        public Criteria andXssIgnoreUriNotLike(String value) {
            addCriterion("xss_ignore_uri not like", value, "xssIgnoreUri");
            return (Criteria) this;
        }

        public Criteria andXssIgnoreUriIn(List<String> values) {
            addCriterion("xss_ignore_uri in", values, "xssIgnoreUri");
            return (Criteria) this;
        }

        public Criteria andXssIgnoreUriNotIn(List<String> values) {
            addCriterion("xss_ignore_uri not in", values, "xssIgnoreUri");
            return (Criteria) this;
        }

        public Criteria andXssIgnoreUriBetween(String value1, String value2) {
            addCriterion("xss_ignore_uri between", value1, value2, "xssIgnoreUri");
            return (Criteria) this;
        }

        public Criteria andXssIgnoreUriNotBetween(String value1, String value2) {
            addCriterion("xss_ignore_uri not between", value1, value2, "xssIgnoreUri");
            return (Criteria) this;
        }

        public Criteria andUploadMaxSizeIsNull() {
            addCriterion("upload_max_size is null");
            return (Criteria) this;
        }

        public Criteria andUploadMaxSizeIsNotNull() {
            addCriterion("upload_max_size is not null");
            return (Criteria) this;
        }

        public Criteria andUploadMaxSizeEqualTo(Integer value) {
            addCriterion("upload_max_size =", value, "uploadMaxSize");
            return (Criteria) this;
        }

        public Criteria andUploadMaxSizeNotEqualTo(Integer value) {
            addCriterion("upload_max_size <>", value, "uploadMaxSize");
            return (Criteria) this;
        }

        public Criteria andUploadMaxSizeGreaterThan(Integer value) {
            addCriterion("upload_max_size >", value, "uploadMaxSize");
            return (Criteria) this;
        }

        public Criteria andUploadMaxSizeGreaterThanOrEqualTo(Integer value) {
            addCriterion("upload_max_size >=", value, "uploadMaxSize");
            return (Criteria) this;
        }

        public Criteria andUploadMaxSizeLessThan(Integer value) {
            addCriterion("upload_max_size <", value, "uploadMaxSize");
            return (Criteria) this;
        }

        public Criteria andUploadMaxSizeLessThanOrEqualTo(Integer value) {
            addCriterion("upload_max_size <=", value, "uploadMaxSize");
            return (Criteria) this;
        }

        public Criteria andUploadMaxSizeIn(List<Integer> values) {
            addCriterion("upload_max_size in", values, "uploadMaxSize");
            return (Criteria) this;
        }

        public Criteria andUploadMaxSizeNotIn(List<Integer> values) {
            addCriterion("upload_max_size not in", values, "uploadMaxSize");
            return (Criteria) this;
        }

        public Criteria andUploadMaxSizeBetween(Integer value1, Integer value2) {
            addCriterion("upload_max_size between", value1, value2, "uploadMaxSize");
            return (Criteria) this;
        }

        public Criteria andUploadMaxSizeNotBetween(Integer value1, Integer value2) {
            addCriterion("upload_max_size not between", value1, value2, "uploadMaxSize");
            return (Criteria) this;
        }

        public Criteria andShortMsgUrlIsNull() {
            addCriterion("short_msg_url is null");
            return (Criteria) this;
        }

        public Criteria andShortMsgUrlIsNotNull() {
            addCriterion("short_msg_url is not null");
            return (Criteria) this;
        }

        public Criteria andShortMsgUrlEqualTo(String value) {
            addCriterion("short_msg_url =", value, "shortMsgUrl");
            return (Criteria) this;
        }

        public Criteria andShortMsgUrlNotEqualTo(String value) {
            addCriterion("short_msg_url <>", value, "shortMsgUrl");
            return (Criteria) this;
        }

        public Criteria andShortMsgUrlGreaterThan(String value) {
            addCriterion("short_msg_url >", value, "shortMsgUrl");
            return (Criteria) this;
        }

        public Criteria andShortMsgUrlGreaterThanOrEqualTo(String value) {
            addCriterion("short_msg_url >=", value, "shortMsgUrl");
            return (Criteria) this;
        }

        public Criteria andShortMsgUrlLessThan(String value) {
            addCriterion("short_msg_url <", value, "shortMsgUrl");
            return (Criteria) this;
        }

        public Criteria andShortMsgUrlLessThanOrEqualTo(String value) {
            addCriterion("short_msg_url <=", value, "shortMsgUrl");
            return (Criteria) this;
        }

        public Criteria andShortMsgUrlLike(String value) {
            addCriterion("short_msg_url like", value, "shortMsgUrl");
            return (Criteria) this;
        }

        public Criteria andShortMsgUrlNotLike(String value) {
            addCriterion("short_msg_url not like", value, "shortMsgUrl");
            return (Criteria) this;
        }

        public Criteria andShortMsgUrlIn(List<String> values) {
            addCriterion("short_msg_url in", values, "shortMsgUrl");
            return (Criteria) this;
        }

        public Criteria andShortMsgUrlNotIn(List<String> values) {
            addCriterion("short_msg_url not in", values, "shortMsgUrl");
            return (Criteria) this;
        }

        public Criteria andShortMsgUrlBetween(String value1, String value2) {
            addCriterion("short_msg_url between", value1, value2, "shortMsgUrl");
            return (Criteria) this;
        }

        public Criteria andShortMsgUrlNotBetween(String value1, String value2) {
            addCriterion("short_msg_url not between", value1, value2, "shortMsgUrl");
            return (Criteria) this;
        }

        public Criteria andSchoolNameIsNull() {
            addCriterion("school_name is null");
            return (Criteria) this;
        }

        public Criteria andSchoolNameIsNotNull() {
            addCriterion("school_name is not null");
            return (Criteria) this;
        }

        public Criteria andSchoolNameEqualTo(String value) {
            addCriterion("school_name =", value, "schoolName");
            return (Criteria) this;
        }

        public Criteria andSchoolNameNotEqualTo(String value) {
            addCriterion("school_name <>", value, "schoolName");
            return (Criteria) this;
        }

        public Criteria andSchoolNameGreaterThan(String value) {
            addCriterion("school_name >", value, "schoolName");
            return (Criteria) this;
        }

        public Criteria andSchoolNameGreaterThanOrEqualTo(String value) {
            addCriterion("school_name >=", value, "schoolName");
            return (Criteria) this;
        }

        public Criteria andSchoolNameLessThan(String value) {
            addCriterion("school_name <", value, "schoolName");
            return (Criteria) this;
        }

        public Criteria andSchoolNameLessThanOrEqualTo(String value) {
            addCriterion("school_name <=", value, "schoolName");
            return (Criteria) this;
        }

        public Criteria andSchoolNameLike(String value) {
            addCriterion("school_name like", value, "schoolName");
            return (Criteria) this;
        }

        public Criteria andSchoolNameNotLike(String value) {
            addCriterion("school_name not like", value, "schoolName");
            return (Criteria) this;
        }

        public Criteria andSchoolNameIn(List<String> values) {
            addCriterion("school_name in", values, "schoolName");
            return (Criteria) this;
        }

        public Criteria andSchoolNameNotIn(List<String> values) {
            addCriterion("school_name not in", values, "schoolName");
            return (Criteria) this;
        }

        public Criteria andSchoolNameBetween(String value1, String value2) {
            addCriterion("school_name between", value1, value2, "schoolName");
            return (Criteria) this;
        }

        public Criteria andSchoolNameNotBetween(String value1, String value2) {
            addCriterion("school_name not between", value1, value2, "schoolName");
            return (Criteria) this;
        }

        public Criteria andSiteCopyrightIsNull() {
            addCriterion("site_copyright is null");
            return (Criteria) this;
        }

        public Criteria andSiteCopyrightIsNotNull() {
            addCriterion("site_copyright is not null");
            return (Criteria) this;
        }

        public Criteria andSiteCopyrightEqualTo(String value) {
            addCriterion("site_copyright =", value, "siteCopyright");
            return (Criteria) this;
        }

        public Criteria andSiteCopyrightNotEqualTo(String value) {
            addCriterion("site_copyright <>", value, "siteCopyright");
            return (Criteria) this;
        }

        public Criteria andSiteCopyrightGreaterThan(String value) {
            addCriterion("site_copyright >", value, "siteCopyright");
            return (Criteria) this;
        }

        public Criteria andSiteCopyrightGreaterThanOrEqualTo(String value) {
            addCriterion("site_copyright >=", value, "siteCopyright");
            return (Criteria) this;
        }

        public Criteria andSiteCopyrightLessThan(String value) {
            addCriterion("site_copyright <", value, "siteCopyright");
            return (Criteria) this;
        }

        public Criteria andSiteCopyrightLessThanOrEqualTo(String value) {
            addCriterion("site_copyright <=", value, "siteCopyright");
            return (Criteria) this;
        }

        public Criteria andSiteCopyrightLike(String value) {
            addCriterion("site_copyright like", value, "siteCopyright");
            return (Criteria) this;
        }

        public Criteria andSiteCopyrightNotLike(String value) {
            addCriterion("site_copyright not like", value, "siteCopyright");
            return (Criteria) this;
        }

        public Criteria andSiteCopyrightIn(List<String> values) {
            addCriterion("site_copyright in", values, "siteCopyright");
            return (Criteria) this;
        }

        public Criteria andSiteCopyrightNotIn(List<String> values) {
            addCriterion("site_copyright not in", values, "siteCopyright");
            return (Criteria) this;
        }

        public Criteria andSiteCopyrightBetween(String value1, String value2) {
            addCriterion("site_copyright between", value1, value2, "siteCopyright");
            return (Criteria) this;
        }

        public Criteria andSiteCopyrightNotBetween(String value1, String value2) {
            addCriterion("site_copyright not between", value1, value2, "siteCopyright");
            return (Criteria) this;
        }

        public Criteria andSiteHomeIsNull() {
            addCriterion("site_home is null");
            return (Criteria) this;
        }

        public Criteria andSiteHomeIsNotNull() {
            addCriterion("site_home is not null");
            return (Criteria) this;
        }

        public Criteria andSiteHomeEqualTo(String value) {
            addCriterion("site_home =", value, "siteHome");
            return (Criteria) this;
        }

        public Criteria andSiteHomeNotEqualTo(String value) {
            addCriterion("site_home <>", value, "siteHome");
            return (Criteria) this;
        }

        public Criteria andSiteHomeGreaterThan(String value) {
            addCriterion("site_home >", value, "siteHome");
            return (Criteria) this;
        }

        public Criteria andSiteHomeGreaterThanOrEqualTo(String value) {
            addCriterion("site_home >=", value, "siteHome");
            return (Criteria) this;
        }

        public Criteria andSiteHomeLessThan(String value) {
            addCriterion("site_home <", value, "siteHome");
            return (Criteria) this;
        }

        public Criteria andSiteHomeLessThanOrEqualTo(String value) {
            addCriterion("site_home <=", value, "siteHome");
            return (Criteria) this;
        }

        public Criteria andSiteHomeLike(String value) {
            addCriterion("site_home like", value, "siteHome");
            return (Criteria) this;
        }

        public Criteria andSiteHomeNotLike(String value) {
            addCriterion("site_home not like", value, "siteHome");
            return (Criteria) this;
        }

        public Criteria andSiteHomeIn(List<String> values) {
            addCriterion("site_home in", values, "siteHome");
            return (Criteria) this;
        }

        public Criteria andSiteHomeNotIn(List<String> values) {
            addCriterion("site_home not in", values, "siteHome");
            return (Criteria) this;
        }

        public Criteria andSiteHomeBetween(String value1, String value2) {
            addCriterion("site_home between", value1, value2, "siteHome");
            return (Criteria) this;
        }

        public Criteria andSiteHomeNotBetween(String value1, String value2) {
            addCriterion("site_home not between", value1, value2, "siteHome");
            return (Criteria) this;
        }

        public Criteria andSiteKeywordsIsNull() {
            addCriterion("site_keywords is null");
            return (Criteria) this;
        }

        public Criteria andSiteKeywordsIsNotNull() {
            addCriterion("site_keywords is not null");
            return (Criteria) this;
        }

        public Criteria andSiteKeywordsEqualTo(String value) {
            addCriterion("site_keywords =", value, "siteKeywords");
            return (Criteria) this;
        }

        public Criteria andSiteKeywordsNotEqualTo(String value) {
            addCriterion("site_keywords <>", value, "siteKeywords");
            return (Criteria) this;
        }

        public Criteria andSiteKeywordsGreaterThan(String value) {
            addCriterion("site_keywords >", value, "siteKeywords");
            return (Criteria) this;
        }

        public Criteria andSiteKeywordsGreaterThanOrEqualTo(String value) {
            addCriterion("site_keywords >=", value, "siteKeywords");
            return (Criteria) this;
        }

        public Criteria andSiteKeywordsLessThan(String value) {
            addCriterion("site_keywords <", value, "siteKeywords");
            return (Criteria) this;
        }

        public Criteria andSiteKeywordsLessThanOrEqualTo(String value) {
            addCriterion("site_keywords <=", value, "siteKeywords");
            return (Criteria) this;
        }

        public Criteria andSiteKeywordsLike(String value) {
            addCriterion("site_keywords like", value, "siteKeywords");
            return (Criteria) this;
        }

        public Criteria andSiteKeywordsNotLike(String value) {
            addCriterion("site_keywords not like", value, "siteKeywords");
            return (Criteria) this;
        }

        public Criteria andSiteKeywordsIn(List<String> values) {
            addCriterion("site_keywords in", values, "siteKeywords");
            return (Criteria) this;
        }

        public Criteria andSiteKeywordsNotIn(List<String> values) {
            addCriterion("site_keywords not in", values, "siteKeywords");
            return (Criteria) this;
        }

        public Criteria andSiteKeywordsBetween(String value1, String value2) {
            addCriterion("site_keywords between", value1, value2, "siteKeywords");
            return (Criteria) this;
        }

        public Criteria andSiteKeywordsNotBetween(String value1, String value2) {
            addCriterion("site_keywords not between", value1, value2, "siteKeywords");
            return (Criteria) this;
        }

        public Criteria andSiteDescriptionIsNull() {
            addCriterion("site_description is null");
            return (Criteria) this;
        }

        public Criteria andSiteDescriptionIsNotNull() {
            addCriterion("site_description is not null");
            return (Criteria) this;
        }

        public Criteria andSiteDescriptionEqualTo(String value) {
            addCriterion("site_description =", value, "siteDescription");
            return (Criteria) this;
        }

        public Criteria andSiteDescriptionNotEqualTo(String value) {
            addCriterion("site_description <>", value, "siteDescription");
            return (Criteria) this;
        }

        public Criteria andSiteDescriptionGreaterThan(String value) {
            addCriterion("site_description >", value, "siteDescription");
            return (Criteria) this;
        }

        public Criteria andSiteDescriptionGreaterThanOrEqualTo(String value) {
            addCriterion("site_description >=", value, "siteDescription");
            return (Criteria) this;
        }

        public Criteria andSiteDescriptionLessThan(String value) {
            addCriterion("site_description <", value, "siteDescription");
            return (Criteria) this;
        }

        public Criteria andSiteDescriptionLessThanOrEqualTo(String value) {
            addCriterion("site_description <=", value, "siteDescription");
            return (Criteria) this;
        }

        public Criteria andSiteDescriptionLike(String value) {
            addCriterion("site_description like", value, "siteDescription");
            return (Criteria) this;
        }

        public Criteria andSiteDescriptionNotLike(String value) {
            addCriterion("site_description not like", value, "siteDescription");
            return (Criteria) this;
        }

        public Criteria andSiteDescriptionIn(List<String> values) {
            addCriterion("site_description in", values, "siteDescription");
            return (Criteria) this;
        }

        public Criteria andSiteDescriptionNotIn(List<String> values) {
            addCriterion("site_description not in", values, "siteDescription");
            return (Criteria) this;
        }

        public Criteria andSiteDescriptionBetween(String value1, String value2) {
            addCriterion("site_description between", value1, value2, "siteDescription");
            return (Criteria) this;
        }

        public Criteria andSiteDescriptionNotBetween(String value1, String value2) {
            addCriterion("site_description not between", value1, value2, "siteDescription");
            return (Criteria) this;
        }

        public Criteria andSiteNameIsNull() {
            addCriterion("site_name is null");
            return (Criteria) this;
        }

        public Criteria andSiteNameIsNotNull() {
            addCriterion("site_name is not null");
            return (Criteria) this;
        }

        public Criteria andSiteNameEqualTo(String value) {
            addCriterion("site_name =", value, "siteName");
            return (Criteria) this;
        }

        public Criteria andSiteNameNotEqualTo(String value) {
            addCriterion("site_name <>", value, "siteName");
            return (Criteria) this;
        }

        public Criteria andSiteNameGreaterThan(String value) {
            addCriterion("site_name >", value, "siteName");
            return (Criteria) this;
        }

        public Criteria andSiteNameGreaterThanOrEqualTo(String value) {
            addCriterion("site_name >=", value, "siteName");
            return (Criteria) this;
        }

        public Criteria andSiteNameLessThan(String value) {
            addCriterion("site_name <", value, "siteName");
            return (Criteria) this;
        }

        public Criteria andSiteNameLessThanOrEqualTo(String value) {
            addCriterion("site_name <=", value, "siteName");
            return (Criteria) this;
        }

        public Criteria andSiteNameLike(String value) {
            addCriterion("site_name like", value, "siteName");
            return (Criteria) this;
        }

        public Criteria andSiteNameNotLike(String value) {
            addCriterion("site_name not like", value, "siteName");
            return (Criteria) this;
        }

        public Criteria andSiteNameIn(List<String> values) {
            addCriterion("site_name in", values, "siteName");
            return (Criteria) this;
        }

        public Criteria andSiteNameNotIn(List<String> values) {
            addCriterion("site_name not in", values, "siteName");
            return (Criteria) this;
        }

        public Criteria andSiteNameBetween(String value1, String value2) {
            addCriterion("site_name between", value1, value2, "siteName");
            return (Criteria) this;
        }

        public Criteria andSiteNameNotBetween(String value1, String value2) {
            addCriterion("site_name not between", value1, value2, "siteName");
            return (Criteria) this;
        }

        public Criteria andSiteShortNameIsNull() {
            addCriterion("site_short_name is null");
            return (Criteria) this;
        }

        public Criteria andSiteShortNameIsNotNull() {
            addCriterion("site_short_name is not null");
            return (Criteria) this;
        }

        public Criteria andSiteShortNameEqualTo(String value) {
            addCriterion("site_short_name =", value, "siteShortName");
            return (Criteria) this;
        }

        public Criteria andSiteShortNameNotEqualTo(String value) {
            addCriterion("site_short_name <>", value, "siteShortName");
            return (Criteria) this;
        }

        public Criteria andSiteShortNameGreaterThan(String value) {
            addCriterion("site_short_name >", value, "siteShortName");
            return (Criteria) this;
        }

        public Criteria andSiteShortNameGreaterThanOrEqualTo(String value) {
            addCriterion("site_short_name >=", value, "siteShortName");
            return (Criteria) this;
        }

        public Criteria andSiteShortNameLessThan(String value) {
            addCriterion("site_short_name <", value, "siteShortName");
            return (Criteria) this;
        }

        public Criteria andSiteShortNameLessThanOrEqualTo(String value) {
            addCriterion("site_short_name <=", value, "siteShortName");
            return (Criteria) this;
        }

        public Criteria andSiteShortNameLike(String value) {
            addCriterion("site_short_name like", value, "siteShortName");
            return (Criteria) this;
        }

        public Criteria andSiteShortNameNotLike(String value) {
            addCriterion("site_short_name not like", value, "siteShortName");
            return (Criteria) this;
        }

        public Criteria andSiteShortNameIn(List<String> values) {
            addCriterion("site_short_name in", values, "siteShortName");
            return (Criteria) this;
        }

        public Criteria andSiteShortNameNotIn(List<String> values) {
            addCriterion("site_short_name not in", values, "siteShortName");
            return (Criteria) this;
        }

        public Criteria andSiteShortNameBetween(String value1, String value2) {
            addCriterion("site_short_name between", value1, value2, "siteShortName");
            return (Criteria) this;
        }

        public Criteria andSiteShortNameNotBetween(String value1, String value2) {
            addCriterion("site_short_name not between", value1, value2, "siteShortName");
            return (Criteria) this;
        }

        public Criteria andMobilePlantformNameIsNull() {
            addCriterion("mobile_plantform_name is null");
            return (Criteria) this;
        }

        public Criteria andMobilePlantformNameIsNotNull() {
            addCriterion("mobile_plantform_name is not null");
            return (Criteria) this;
        }

        public Criteria andMobilePlantformNameEqualTo(String value) {
            addCriterion("mobile_plantform_name =", value, "mobilePlantformName");
            return (Criteria) this;
        }

        public Criteria andMobilePlantformNameNotEqualTo(String value) {
            addCriterion("mobile_plantform_name <>", value, "mobilePlantformName");
            return (Criteria) this;
        }

        public Criteria andMobilePlantformNameGreaterThan(String value) {
            addCriterion("mobile_plantform_name >", value, "mobilePlantformName");
            return (Criteria) this;
        }

        public Criteria andMobilePlantformNameGreaterThanOrEqualTo(String value) {
            addCriterion("mobile_plantform_name >=", value, "mobilePlantformName");
            return (Criteria) this;
        }

        public Criteria andMobilePlantformNameLessThan(String value) {
            addCriterion("mobile_plantform_name <", value, "mobilePlantformName");
            return (Criteria) this;
        }

        public Criteria andMobilePlantformNameLessThanOrEqualTo(String value) {
            addCriterion("mobile_plantform_name <=", value, "mobilePlantformName");
            return (Criteria) this;
        }

        public Criteria andMobilePlantformNameLike(String value) {
            addCriterion("mobile_plantform_name like", value, "mobilePlantformName");
            return (Criteria) this;
        }

        public Criteria andMobilePlantformNameNotLike(String value) {
            addCriterion("mobile_plantform_name not like", value, "mobilePlantformName");
            return (Criteria) this;
        }

        public Criteria andMobilePlantformNameIn(List<String> values) {
            addCriterion("mobile_plantform_name in", values, "mobilePlantformName");
            return (Criteria) this;
        }

        public Criteria andMobilePlantformNameNotIn(List<String> values) {
            addCriterion("mobile_plantform_name not in", values, "mobilePlantformName");
            return (Criteria) this;
        }

        public Criteria andMobilePlantformNameBetween(String value1, String value2) {
            addCriterion("mobile_plantform_name between", value1, value2, "mobilePlantformName");
            return (Criteria) this;
        }

        public Criteria andMobilePlantformNameNotBetween(String value1, String value2) {
            addCriterion("mobile_plantform_name not between", value1, value2, "mobilePlantformName");
            return (Criteria) this;
        }

        public Criteria andLogoIsNull() {
            addCriterion("logo is null");
            return (Criteria) this;
        }

        public Criteria andLogoIsNotNull() {
            addCriterion("logo is not null");
            return (Criteria) this;
        }

        public Criteria andLogoEqualTo(String value) {
            addCriterion("logo =", value, "logo");
            return (Criteria) this;
        }

        public Criteria andLogoNotEqualTo(String value) {
            addCriterion("logo <>", value, "logo");
            return (Criteria) this;
        }

        public Criteria andLogoGreaterThan(String value) {
            addCriterion("logo >", value, "logo");
            return (Criteria) this;
        }

        public Criteria andLogoGreaterThanOrEqualTo(String value) {
            addCriterion("logo >=", value, "logo");
            return (Criteria) this;
        }

        public Criteria andLogoLessThan(String value) {
            addCriterion("logo <", value, "logo");
            return (Criteria) this;
        }

        public Criteria andLogoLessThanOrEqualTo(String value) {
            addCriterion("logo <=", value, "logo");
            return (Criteria) this;
        }

        public Criteria andLogoLike(String value) {
            addCriterion("logo like", value, "logo");
            return (Criteria) this;
        }

        public Criteria andLogoNotLike(String value) {
            addCriterion("logo not like", value, "logo");
            return (Criteria) this;
        }

        public Criteria andLogoIn(List<String> values) {
            addCriterion("logo in", values, "logo");
            return (Criteria) this;
        }

        public Criteria andLogoNotIn(List<String> values) {
            addCriterion("logo not in", values, "logo");
            return (Criteria) this;
        }

        public Criteria andLogoBetween(String value1, String value2) {
            addCriterion("logo between", value1, value2, "logo");
            return (Criteria) this;
        }

        public Criteria andLogoNotBetween(String value1, String value2) {
            addCriterion("logo not between", value1, value2, "logo");
            return (Criteria) this;
        }

        public Criteria andLogoWhiteIsNull() {
            addCriterion("logo_white is null");
            return (Criteria) this;
        }

        public Criteria andLogoWhiteIsNotNull() {
            addCriterion("logo_white is not null");
            return (Criteria) this;
        }

        public Criteria andLogoWhiteEqualTo(String value) {
            addCriterion("logo_white =", value, "logoWhite");
            return (Criteria) this;
        }

        public Criteria andLogoWhiteNotEqualTo(String value) {
            addCriterion("logo_white <>", value, "logoWhite");
            return (Criteria) this;
        }

        public Criteria andLogoWhiteGreaterThan(String value) {
            addCriterion("logo_white >", value, "logoWhite");
            return (Criteria) this;
        }

        public Criteria andLogoWhiteGreaterThanOrEqualTo(String value) {
            addCriterion("logo_white >=", value, "logoWhite");
            return (Criteria) this;
        }

        public Criteria andLogoWhiteLessThan(String value) {
            addCriterion("logo_white <", value, "logoWhite");
            return (Criteria) this;
        }

        public Criteria andLogoWhiteLessThanOrEqualTo(String value) {
            addCriterion("logo_white <=", value, "logoWhite");
            return (Criteria) this;
        }

        public Criteria andLogoWhiteLike(String value) {
            addCriterion("logo_white like", value, "logoWhite");
            return (Criteria) this;
        }

        public Criteria andLogoWhiteNotLike(String value) {
            addCriterion("logo_white not like", value, "logoWhite");
            return (Criteria) this;
        }

        public Criteria andLogoWhiteIn(List<String> values) {
            addCriterion("logo_white in", values, "logoWhite");
            return (Criteria) this;
        }

        public Criteria andLogoWhiteNotIn(List<String> values) {
            addCriterion("logo_white not in", values, "logoWhite");
            return (Criteria) this;
        }

        public Criteria andLogoWhiteBetween(String value1, String value2) {
            addCriterion("logo_white between", value1, value2, "logoWhite");
            return (Criteria) this;
        }

        public Criteria andLogoWhiteNotBetween(String value1, String value2) {
            addCriterion("logo_white not between", value1, value2, "logoWhite");
            return (Criteria) this;
        }

        public Criteria andLoginBgIsNull() {
            addCriterion("login_bg is null");
            return (Criteria) this;
        }

        public Criteria andLoginBgIsNotNull() {
            addCriterion("login_bg is not null");
            return (Criteria) this;
        }

        public Criteria andLoginBgEqualTo(String value) {
            addCriterion("login_bg =", value, "loginBg");
            return (Criteria) this;
        }

        public Criteria andLoginBgNotEqualTo(String value) {
            addCriterion("login_bg <>", value, "loginBg");
            return (Criteria) this;
        }

        public Criteria andLoginBgGreaterThan(String value) {
            addCriterion("login_bg >", value, "loginBg");
            return (Criteria) this;
        }

        public Criteria andLoginBgGreaterThanOrEqualTo(String value) {
            addCriterion("login_bg >=", value, "loginBg");
            return (Criteria) this;
        }

        public Criteria andLoginBgLessThan(String value) {
            addCriterion("login_bg <", value, "loginBg");
            return (Criteria) this;
        }

        public Criteria andLoginBgLessThanOrEqualTo(String value) {
            addCriterion("login_bg <=", value, "loginBg");
            return (Criteria) this;
        }

        public Criteria andLoginBgLike(String value) {
            addCriterion("login_bg like", value, "loginBg");
            return (Criteria) this;
        }

        public Criteria andLoginBgNotLike(String value) {
            addCriterion("login_bg not like", value, "loginBg");
            return (Criteria) this;
        }

        public Criteria andLoginBgIn(List<String> values) {
            addCriterion("login_bg in", values, "loginBg");
            return (Criteria) this;
        }

        public Criteria andLoginBgNotIn(List<String> values) {
            addCriterion("login_bg not in", values, "loginBg");
            return (Criteria) this;
        }

        public Criteria andLoginBgBetween(String value1, String value2) {
            addCriterion("login_bg between", value1, value2, "loginBg");
            return (Criteria) this;
        }

        public Criteria andLoginBgNotBetween(String value1, String value2) {
            addCriterion("login_bg not between", value1, value2, "loginBg");
            return (Criteria) this;
        }

        public Criteria andLoginMsgIsNull() {
            addCriterion("login_msg is null");
            return (Criteria) this;
        }

        public Criteria andLoginMsgIsNotNull() {
            addCriterion("login_msg is not null");
            return (Criteria) this;
        }

        public Criteria andLoginMsgEqualTo(String value) {
            addCriterion("login_msg =", value, "loginMsg");
            return (Criteria) this;
        }

        public Criteria andLoginMsgNotEqualTo(String value) {
            addCriterion("login_msg <>", value, "loginMsg");
            return (Criteria) this;
        }

        public Criteria andLoginMsgGreaterThan(String value) {
            addCriterion("login_msg >", value, "loginMsg");
            return (Criteria) this;
        }

        public Criteria andLoginMsgGreaterThanOrEqualTo(String value) {
            addCriterion("login_msg >=", value, "loginMsg");
            return (Criteria) this;
        }

        public Criteria andLoginMsgLessThan(String value) {
            addCriterion("login_msg <", value, "loginMsg");
            return (Criteria) this;
        }

        public Criteria andLoginMsgLessThanOrEqualTo(String value) {
            addCriterion("login_msg <=", value, "loginMsg");
            return (Criteria) this;
        }

        public Criteria andLoginMsgLike(String value) {
            addCriterion("login_msg like", value, "loginMsg");
            return (Criteria) this;
        }

        public Criteria andLoginMsgNotLike(String value) {
            addCriterion("login_msg not like", value, "loginMsg");
            return (Criteria) this;
        }

        public Criteria andLoginMsgIn(List<String> values) {
            addCriterion("login_msg in", values, "loginMsg");
            return (Criteria) this;
        }

        public Criteria andLoginMsgNotIn(List<String> values) {
            addCriterion("login_msg not in", values, "loginMsg");
            return (Criteria) this;
        }

        public Criteria andLoginMsgBetween(String value1, String value2) {
            addCriterion("login_msg between", value1, value2, "loginMsg");
            return (Criteria) this;
        }

        public Criteria andLoginMsgNotBetween(String value1, String value2) {
            addCriterion("login_msg not between", value1, value2, "loginMsg");
            return (Criteria) this;
        }

        public Criteria andDisplayLoginMsgIsNull() {
            addCriterion("display_login_msg is null");
            return (Criteria) this;
        }

        public Criteria andDisplayLoginMsgIsNotNull() {
            addCriterion("display_login_msg is not null");
            return (Criteria) this;
        }

        public Criteria andDisplayLoginMsgEqualTo(Boolean value) {
            addCriterion("display_login_msg =", value, "displayLoginMsg");
            return (Criteria) this;
        }

        public Criteria andDisplayLoginMsgNotEqualTo(Boolean value) {
            addCriterion("display_login_msg <>", value, "displayLoginMsg");
            return (Criteria) this;
        }

        public Criteria andDisplayLoginMsgGreaterThan(Boolean value) {
            addCriterion("display_login_msg >", value, "displayLoginMsg");
            return (Criteria) this;
        }

        public Criteria andDisplayLoginMsgGreaterThanOrEqualTo(Boolean value) {
            addCriterion("display_login_msg >=", value, "displayLoginMsg");
            return (Criteria) this;
        }

        public Criteria andDisplayLoginMsgLessThan(Boolean value) {
            addCriterion("display_login_msg <", value, "displayLoginMsg");
            return (Criteria) this;
        }

        public Criteria andDisplayLoginMsgLessThanOrEqualTo(Boolean value) {
            addCriterion("display_login_msg <=", value, "displayLoginMsg");
            return (Criteria) this;
        }

        public Criteria andDisplayLoginMsgIn(List<Boolean> values) {
            addCriterion("display_login_msg in", values, "displayLoginMsg");
            return (Criteria) this;
        }

        public Criteria andDisplayLoginMsgNotIn(List<Boolean> values) {
            addCriterion("display_login_msg not in", values, "displayLoginMsg");
            return (Criteria) this;
        }

        public Criteria andDisplayLoginMsgBetween(Boolean value1, Boolean value2) {
            addCriterion("display_login_msg between", value1, value2, "displayLoginMsg");
            return (Criteria) this;
        }

        public Criteria andDisplayLoginMsgNotBetween(Boolean value1, Boolean value2) {
            addCriterion("display_login_msg not between", value1, value2, "displayLoginMsg");
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