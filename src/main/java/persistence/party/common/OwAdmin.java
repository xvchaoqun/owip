package persistence.party.common;

import java.util.List;

// 分党委或支部管理员
public class OwAdmin {

    private Integer id; // 普通管理员的ID，即org_admin 表的id

    private Integer userId;
    private Integer partyId;
    private Integer branchId;  // 分党委管理员为空
    private Integer postId;
    public String username;
    public String realname;
    public String code;

    // 以下是搜索条件
    String query;
    Boolean normal; // 委员/普通管理员
    Boolean addPermits;
    List<Integer> adminPartyIdList;
    List<Integer> adminBranchIdList;

    public Integer getId() {
        return id;
    }

    public OwAdmin setId(Integer id) {
        this.id = id;
        return this;
    }

    public Integer getUserId() {
        return userId;
    }

    public OwAdmin setUserId(Integer userId) {
        this.userId = userId;
        return this;
    }

    public Integer getPartyId() {
        return partyId;
    }

    public OwAdmin setPartyId(Integer partyId) {
        this.partyId = partyId;
        return this;
    }

    public Integer getBranchId() {
        return branchId;
    }

    public OwAdmin setBranchId(Integer branchId) {
        this.branchId = branchId;
        return this;
    }

    public Integer getPostId() {
        return postId;
    }

    public OwAdmin setPostId(Integer postId) {
        this.postId = postId;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public OwAdmin setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getRealname() {
        return realname;
    }

    public OwAdmin setRealname(String realname) {
        this.realname = realname;
        return this;
    }

    public String getCode() {
        return code;
    }

    public OwAdmin setCode(String code) {
        this.code = code;
        return this;
    }

    public String getQuery() {
        return query;
    }

    public OwAdmin setQuery(String query) {
        this.query = query;
        return this;
    }

    public Boolean getNormal() {
        return normal;
    }

    public OwAdmin setNormal(Boolean normal) {
        this.normal = normal;
        return this;
    }

    public Boolean getAddPermits() {
        return addPermits;
    }

    public OwAdmin setAddPermits(Boolean addPermits) {
        this.addPermits = addPermits;
        return this;
    }

    public List<Integer> getAdminPartyIdList() {
        return adminPartyIdList;
    }

    public OwAdmin setAdminPartyIdList(List<Integer> adminPartyIdList) {
        this.adminPartyIdList = adminPartyIdList;
        return this;
    }

    public List<Integer> getAdminBranchIdList() {
        return adminBranchIdList;
    }

    public OwAdmin setAdminBranchIdList(List<Integer> adminBranchIdList) {
        this.adminBranchIdList = adminBranchIdList;
        return this;
    }
}
