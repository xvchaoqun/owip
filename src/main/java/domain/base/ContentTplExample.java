package domain.base;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ContentTplExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public ContentTplExample() {
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

        public Criteria andRoleIdIsNull() {
            addCriterion("role_id is null");
            return (Criteria) this;
        }

        public Criteria andRoleIdIsNotNull() {
            addCriterion("role_id is not null");
            return (Criteria) this;
        }

        public Criteria andRoleIdEqualTo(Integer value) {
            addCriterion("role_id =", value, "roleId");
            return (Criteria) this;
        }

        public Criteria andRoleIdNotEqualTo(Integer value) {
            addCriterion("role_id <>", value, "roleId");
            return (Criteria) this;
        }

        public Criteria andRoleIdGreaterThan(Integer value) {
            addCriterion("role_id >", value, "roleId");
            return (Criteria) this;
        }

        public Criteria andRoleIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("role_id >=", value, "roleId");
            return (Criteria) this;
        }

        public Criteria andRoleIdLessThan(Integer value) {
            addCriterion("role_id <", value, "roleId");
            return (Criteria) this;
        }

        public Criteria andRoleIdLessThanOrEqualTo(Integer value) {
            addCriterion("role_id <=", value, "roleId");
            return (Criteria) this;
        }

        public Criteria andRoleIdIn(List<Integer> values) {
            addCriterion("role_id in", values, "roleId");
            return (Criteria) this;
        }

        public Criteria andRoleIdNotIn(List<Integer> values) {
            addCriterion("role_id not in", values, "roleId");
            return (Criteria) this;
        }

        public Criteria andRoleIdBetween(Integer value1, Integer value2) {
            addCriterion("role_id between", value1, value2, "roleId");
            return (Criteria) this;
        }

        public Criteria andRoleIdNotBetween(Integer value1, Integer value2) {
            addCriterion("role_id not between", value1, value2, "roleId");
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

        public Criteria andWxMsgTypeIsNull() {
            addCriterion("wx_msg_type is null");
            return (Criteria) this;
        }

        public Criteria andWxMsgTypeIsNotNull() {
            addCriterion("wx_msg_type is not null");
            return (Criteria) this;
        }

        public Criteria andWxMsgTypeEqualTo(Byte value) {
            addCriterion("wx_msg_type =", value, "wxMsgType");
            return (Criteria) this;
        }

        public Criteria andWxMsgTypeNotEqualTo(Byte value) {
            addCriterion("wx_msg_type <>", value, "wxMsgType");
            return (Criteria) this;
        }

        public Criteria andWxMsgTypeGreaterThan(Byte value) {
            addCriterion("wx_msg_type >", value, "wxMsgType");
            return (Criteria) this;
        }

        public Criteria andWxMsgTypeGreaterThanOrEqualTo(Byte value) {
            addCriterion("wx_msg_type >=", value, "wxMsgType");
            return (Criteria) this;
        }

        public Criteria andWxMsgTypeLessThan(Byte value) {
            addCriterion("wx_msg_type <", value, "wxMsgType");
            return (Criteria) this;
        }

        public Criteria andWxMsgTypeLessThanOrEqualTo(Byte value) {
            addCriterion("wx_msg_type <=", value, "wxMsgType");
            return (Criteria) this;
        }

        public Criteria andWxMsgTypeIn(List<Byte> values) {
            addCriterion("wx_msg_type in", values, "wxMsgType");
            return (Criteria) this;
        }

        public Criteria andWxMsgTypeNotIn(List<Byte> values) {
            addCriterion("wx_msg_type not in", values, "wxMsgType");
            return (Criteria) this;
        }

        public Criteria andWxMsgTypeBetween(Byte value1, Byte value2) {
            addCriterion("wx_msg_type between", value1, value2, "wxMsgType");
            return (Criteria) this;
        }

        public Criteria andWxMsgTypeNotBetween(Byte value1, Byte value2) {
            addCriterion("wx_msg_type not between", value1, value2, "wxMsgType");
            return (Criteria) this;
        }

        public Criteria andWxTitleIsNull() {
            addCriterion("wx_title is null");
            return (Criteria) this;
        }

        public Criteria andWxTitleIsNotNull() {
            addCriterion("wx_title is not null");
            return (Criteria) this;
        }

        public Criteria andWxTitleEqualTo(String value) {
            addCriterion("wx_title =", value, "wxTitle");
            return (Criteria) this;
        }

        public Criteria andWxTitleNotEqualTo(String value) {
            addCriterion("wx_title <>", value, "wxTitle");
            return (Criteria) this;
        }

        public Criteria andWxTitleGreaterThan(String value) {
            addCriterion("wx_title >", value, "wxTitle");
            return (Criteria) this;
        }

        public Criteria andWxTitleGreaterThanOrEqualTo(String value) {
            addCriterion("wx_title >=", value, "wxTitle");
            return (Criteria) this;
        }

        public Criteria andWxTitleLessThan(String value) {
            addCriterion("wx_title <", value, "wxTitle");
            return (Criteria) this;
        }

        public Criteria andWxTitleLessThanOrEqualTo(String value) {
            addCriterion("wx_title <=", value, "wxTitle");
            return (Criteria) this;
        }

        public Criteria andWxTitleLike(String value) {
            addCriterion("wx_title like", value, "wxTitle");
            return (Criteria) this;
        }

        public Criteria andWxTitleNotLike(String value) {
            addCriterion("wx_title not like", value, "wxTitle");
            return (Criteria) this;
        }

        public Criteria andWxTitleIn(List<String> values) {
            addCriterion("wx_title in", values, "wxTitle");
            return (Criteria) this;
        }

        public Criteria andWxTitleNotIn(List<String> values) {
            addCriterion("wx_title not in", values, "wxTitle");
            return (Criteria) this;
        }

        public Criteria andWxTitleBetween(String value1, String value2) {
            addCriterion("wx_title between", value1, value2, "wxTitle");
            return (Criteria) this;
        }

        public Criteria andWxTitleNotBetween(String value1, String value2) {
            addCriterion("wx_title not between", value1, value2, "wxTitle");
            return (Criteria) this;
        }

        public Criteria andWxUrlIsNull() {
            addCriterion("wx_url is null");
            return (Criteria) this;
        }

        public Criteria andWxUrlIsNotNull() {
            addCriterion("wx_url is not null");
            return (Criteria) this;
        }

        public Criteria andWxUrlEqualTo(String value) {
            addCriterion("wx_url =", value, "wxUrl");
            return (Criteria) this;
        }

        public Criteria andWxUrlNotEqualTo(String value) {
            addCriterion("wx_url <>", value, "wxUrl");
            return (Criteria) this;
        }

        public Criteria andWxUrlGreaterThan(String value) {
            addCriterion("wx_url >", value, "wxUrl");
            return (Criteria) this;
        }

        public Criteria andWxUrlGreaterThanOrEqualTo(String value) {
            addCriterion("wx_url >=", value, "wxUrl");
            return (Criteria) this;
        }

        public Criteria andWxUrlLessThan(String value) {
            addCriterion("wx_url <", value, "wxUrl");
            return (Criteria) this;
        }

        public Criteria andWxUrlLessThanOrEqualTo(String value) {
            addCriterion("wx_url <=", value, "wxUrl");
            return (Criteria) this;
        }

        public Criteria andWxUrlLike(String value) {
            addCriterion("wx_url like", value, "wxUrl");
            return (Criteria) this;
        }

        public Criteria andWxUrlNotLike(String value) {
            addCriterion("wx_url not like", value, "wxUrl");
            return (Criteria) this;
        }

        public Criteria andWxUrlIn(List<String> values) {
            addCriterion("wx_url in", values, "wxUrl");
            return (Criteria) this;
        }

        public Criteria andWxUrlNotIn(List<String> values) {
            addCriterion("wx_url not in", values, "wxUrl");
            return (Criteria) this;
        }

        public Criteria andWxUrlBetween(String value1, String value2) {
            addCriterion("wx_url between", value1, value2, "wxUrl");
            return (Criteria) this;
        }

        public Criteria andWxUrlNotBetween(String value1, String value2) {
            addCriterion("wx_url not between", value1, value2, "wxUrl");
            return (Criteria) this;
        }

        public Criteria andWxPicIsNull() {
            addCriterion("wx_pic is null");
            return (Criteria) this;
        }

        public Criteria andWxPicIsNotNull() {
            addCriterion("wx_pic is not null");
            return (Criteria) this;
        }

        public Criteria andWxPicEqualTo(String value) {
            addCriterion("wx_pic =", value, "wxPic");
            return (Criteria) this;
        }

        public Criteria andWxPicNotEqualTo(String value) {
            addCriterion("wx_pic <>", value, "wxPic");
            return (Criteria) this;
        }

        public Criteria andWxPicGreaterThan(String value) {
            addCriterion("wx_pic >", value, "wxPic");
            return (Criteria) this;
        }

        public Criteria andWxPicGreaterThanOrEqualTo(String value) {
            addCriterion("wx_pic >=", value, "wxPic");
            return (Criteria) this;
        }

        public Criteria andWxPicLessThan(String value) {
            addCriterion("wx_pic <", value, "wxPic");
            return (Criteria) this;
        }

        public Criteria andWxPicLessThanOrEqualTo(String value) {
            addCriterion("wx_pic <=", value, "wxPic");
            return (Criteria) this;
        }

        public Criteria andWxPicLike(String value) {
            addCriterion("wx_pic like", value, "wxPic");
            return (Criteria) this;
        }

        public Criteria andWxPicNotLike(String value) {
            addCriterion("wx_pic not like", value, "wxPic");
            return (Criteria) this;
        }

        public Criteria andWxPicIn(List<String> values) {
            addCriterion("wx_pic in", values, "wxPic");
            return (Criteria) this;
        }

        public Criteria andWxPicNotIn(List<String> values) {
            addCriterion("wx_pic not in", values, "wxPic");
            return (Criteria) this;
        }

        public Criteria andWxPicBetween(String value1, String value2) {
            addCriterion("wx_pic between", value1, value2, "wxPic");
            return (Criteria) this;
        }

        public Criteria andWxPicNotBetween(String value1, String value2) {
            addCriterion("wx_pic not between", value1, value2, "wxPic");
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

        public Criteria andContentTypeIsNull() {
            addCriterion("content_type is null");
            return (Criteria) this;
        }

        public Criteria andContentTypeIsNotNull() {
            addCriterion("content_type is not null");
            return (Criteria) this;
        }

        public Criteria andContentTypeEqualTo(Byte value) {
            addCriterion("content_type =", value, "contentType");
            return (Criteria) this;
        }

        public Criteria andContentTypeNotEqualTo(Byte value) {
            addCriterion("content_type <>", value, "contentType");
            return (Criteria) this;
        }

        public Criteria andContentTypeGreaterThan(Byte value) {
            addCriterion("content_type >", value, "contentType");
            return (Criteria) this;
        }

        public Criteria andContentTypeGreaterThanOrEqualTo(Byte value) {
            addCriterion("content_type >=", value, "contentType");
            return (Criteria) this;
        }

        public Criteria andContentTypeLessThan(Byte value) {
            addCriterion("content_type <", value, "contentType");
            return (Criteria) this;
        }

        public Criteria andContentTypeLessThanOrEqualTo(Byte value) {
            addCriterion("content_type <=", value, "contentType");
            return (Criteria) this;
        }

        public Criteria andContentTypeIn(List<Byte> values) {
            addCriterion("content_type in", values, "contentType");
            return (Criteria) this;
        }

        public Criteria andContentTypeNotIn(List<Byte> values) {
            addCriterion("content_type not in", values, "contentType");
            return (Criteria) this;
        }

        public Criteria andContentTypeBetween(Byte value1, Byte value2) {
            addCriterion("content_type between", value1, value2, "contentType");
            return (Criteria) this;
        }

        public Criteria andContentTypeNotBetween(Byte value1, Byte value2) {
            addCriterion("content_type not between", value1, value2, "contentType");
            return (Criteria) this;
        }

        public Criteria andEngineIsNull() {
            addCriterion("engine is null");
            return (Criteria) this;
        }

        public Criteria andEngineIsNotNull() {
            addCriterion("engine is not null");
            return (Criteria) this;
        }

        public Criteria andEngineEqualTo(Byte value) {
            addCriterion("engine =", value, "engine");
            return (Criteria) this;
        }

        public Criteria andEngineNotEqualTo(Byte value) {
            addCriterion("engine <>", value, "engine");
            return (Criteria) this;
        }

        public Criteria andEngineGreaterThan(Byte value) {
            addCriterion("engine >", value, "engine");
            return (Criteria) this;
        }

        public Criteria andEngineGreaterThanOrEqualTo(Byte value) {
            addCriterion("engine >=", value, "engine");
            return (Criteria) this;
        }

        public Criteria andEngineLessThan(Byte value) {
            addCriterion("engine <", value, "engine");
            return (Criteria) this;
        }

        public Criteria andEngineLessThanOrEqualTo(Byte value) {
            addCriterion("engine <=", value, "engine");
            return (Criteria) this;
        }

        public Criteria andEngineIn(List<Byte> values) {
            addCriterion("engine in", values, "engine");
            return (Criteria) this;
        }

        public Criteria andEngineNotIn(List<Byte> values) {
            addCriterion("engine not in", values, "engine");
            return (Criteria) this;
        }

        public Criteria andEngineBetween(Byte value1, Byte value2) {
            addCriterion("engine between", value1, value2, "engine");
            return (Criteria) this;
        }

        public Criteria andEngineNotBetween(Byte value1, Byte value2) {
            addCriterion("engine not between", value1, value2, "engine");
            return (Criteria) this;
        }

        public Criteria andParamCountIsNull() {
            addCriterion("param_count is null");
            return (Criteria) this;
        }

        public Criteria andParamCountIsNotNull() {
            addCriterion("param_count is not null");
            return (Criteria) this;
        }

        public Criteria andParamCountEqualTo(Integer value) {
            addCriterion("param_count =", value, "paramCount");
            return (Criteria) this;
        }

        public Criteria andParamCountNotEqualTo(Integer value) {
            addCriterion("param_count <>", value, "paramCount");
            return (Criteria) this;
        }

        public Criteria andParamCountGreaterThan(Integer value) {
            addCriterion("param_count >", value, "paramCount");
            return (Criteria) this;
        }

        public Criteria andParamCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("param_count >=", value, "paramCount");
            return (Criteria) this;
        }

        public Criteria andParamCountLessThan(Integer value) {
            addCriterion("param_count <", value, "paramCount");
            return (Criteria) this;
        }

        public Criteria andParamCountLessThanOrEqualTo(Integer value) {
            addCriterion("param_count <=", value, "paramCount");
            return (Criteria) this;
        }

        public Criteria andParamCountIn(List<Integer> values) {
            addCriterion("param_count in", values, "paramCount");
            return (Criteria) this;
        }

        public Criteria andParamCountNotIn(List<Integer> values) {
            addCriterion("param_count not in", values, "paramCount");
            return (Criteria) this;
        }

        public Criteria andParamCountBetween(Integer value1, Integer value2) {
            addCriterion("param_count between", value1, value2, "paramCount");
            return (Criteria) this;
        }

        public Criteria andParamCountNotBetween(Integer value1, Integer value2) {
            addCriterion("param_count not between", value1, value2, "paramCount");
            return (Criteria) this;
        }

        public Criteria andParamNamesIsNull() {
            addCriterion("param_names is null");
            return (Criteria) this;
        }

        public Criteria andParamNamesIsNotNull() {
            addCriterion("param_names is not null");
            return (Criteria) this;
        }

        public Criteria andParamNamesEqualTo(String value) {
            addCriterion("param_names =", value, "paramNames");
            return (Criteria) this;
        }

        public Criteria andParamNamesNotEqualTo(String value) {
            addCriterion("param_names <>", value, "paramNames");
            return (Criteria) this;
        }

        public Criteria andParamNamesGreaterThan(String value) {
            addCriterion("param_names >", value, "paramNames");
            return (Criteria) this;
        }

        public Criteria andParamNamesGreaterThanOrEqualTo(String value) {
            addCriterion("param_names >=", value, "paramNames");
            return (Criteria) this;
        }

        public Criteria andParamNamesLessThan(String value) {
            addCriterion("param_names <", value, "paramNames");
            return (Criteria) this;
        }

        public Criteria andParamNamesLessThanOrEqualTo(String value) {
            addCriterion("param_names <=", value, "paramNames");
            return (Criteria) this;
        }

        public Criteria andParamNamesLike(String value) {
            addCriterion("param_names like", value, "paramNames");
            return (Criteria) this;
        }

        public Criteria andParamNamesNotLike(String value) {
            addCriterion("param_names not like", value, "paramNames");
            return (Criteria) this;
        }

        public Criteria andParamNamesIn(List<String> values) {
            addCriterion("param_names in", values, "paramNames");
            return (Criteria) this;
        }

        public Criteria andParamNamesNotIn(List<String> values) {
            addCriterion("param_names not in", values, "paramNames");
            return (Criteria) this;
        }

        public Criteria andParamNamesBetween(String value1, String value2) {
            addCriterion("param_names between", value1, value2, "paramNames");
            return (Criteria) this;
        }

        public Criteria andParamNamesNotBetween(String value1, String value2) {
            addCriterion("param_names not between", value1, value2, "paramNames");
            return (Criteria) this;
        }

        public Criteria andParamDefValuesIsNull() {
            addCriterion("param_def_values is null");
            return (Criteria) this;
        }

        public Criteria andParamDefValuesIsNotNull() {
            addCriterion("param_def_values is not null");
            return (Criteria) this;
        }

        public Criteria andParamDefValuesEqualTo(String value) {
            addCriterion("param_def_values =", value, "paramDefValues");
            return (Criteria) this;
        }

        public Criteria andParamDefValuesNotEqualTo(String value) {
            addCriterion("param_def_values <>", value, "paramDefValues");
            return (Criteria) this;
        }

        public Criteria andParamDefValuesGreaterThan(String value) {
            addCriterion("param_def_values >", value, "paramDefValues");
            return (Criteria) this;
        }

        public Criteria andParamDefValuesGreaterThanOrEqualTo(String value) {
            addCriterion("param_def_values >=", value, "paramDefValues");
            return (Criteria) this;
        }

        public Criteria andParamDefValuesLessThan(String value) {
            addCriterion("param_def_values <", value, "paramDefValues");
            return (Criteria) this;
        }

        public Criteria andParamDefValuesLessThanOrEqualTo(String value) {
            addCriterion("param_def_values <=", value, "paramDefValues");
            return (Criteria) this;
        }

        public Criteria andParamDefValuesLike(String value) {
            addCriterion("param_def_values like", value, "paramDefValues");
            return (Criteria) this;
        }

        public Criteria andParamDefValuesNotLike(String value) {
            addCriterion("param_def_values not like", value, "paramDefValues");
            return (Criteria) this;
        }

        public Criteria andParamDefValuesIn(List<String> values) {
            addCriterion("param_def_values in", values, "paramDefValues");
            return (Criteria) this;
        }

        public Criteria andParamDefValuesNotIn(List<String> values) {
            addCriterion("param_def_values not in", values, "paramDefValues");
            return (Criteria) this;
        }

        public Criteria andParamDefValuesBetween(String value1, String value2) {
            addCriterion("param_def_values between", value1, value2, "paramDefValues");
            return (Criteria) this;
        }

        public Criteria andParamDefValuesNotBetween(String value1, String value2) {
            addCriterion("param_def_values not between", value1, value2, "paramDefValues");
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