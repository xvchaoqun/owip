<%@ page import="sys.constants.SystemConstants" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set var="MEMBER_POLITICAL_STATUS_MAP" value="<%=SystemConstants.MEMBER_POLITICAL_STATUS_MAP%>"/>
<c:set var="GENDER_MAP" value="<%=SystemConstants.GENDER_MAP%>"/>
<c:set var="MEMBER_INOUT_TYPE_MAP" value="<%=SystemConstants.MEMBER_INOUT_TYPE_MAP%>"/>
<c:set var="MEMBER_IN_STATUS_MAP" value="<%=SystemConstants.MEMBER_IN_STATUS_MAP%>"/>
<c:set var="MEMBER_IN_STATUS_APPLY" value="<%=SystemConstants.MEMBER_IN_STATUS_APPLY%>"/>
<c:set var="MEMBER_IN_STATUS_PARTY_VERIFY" value="<%=SystemConstants.MEMBER_IN_STATUS_PARTY_VERIFY%>"/>

<div class="row">
    <div class="col-xs-12">
        <div id="body-content">
        <!-- PAGE CONTENT BEGINS -->
        <div class="myTableDiv"
             data-url-au="${ctx}/memberIn_au"
             data-url-page="${ctx}/memberIn_page"
             data-url-del="${ctx}/memberIn_del"
             data-url-bd="${ctx}/memberIn_batchDel"
             data-url-co="${ctx}/memberIn_changeOrder"
             data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <mytag:sort-form css="form-inline hidden-sm hidden-xs" id="searchForm">
                <select data-rel="select2-ajax" data-ajax-url="${ctx}/sysUser_selects"
                        name="userId" data-placeholder="请输入账号或姓名或学工号">
                    <option value="${sysUser.id}">${sysUser.realname}</option>
                </select>
                <select required data-rel="select2" name="type" data-placeholder="请选择">
                    <option></option>
                    <c:forEach items="${MEMBER_INOUT_TYPE_MAP}" var="_type">
                        <option value="${_type.key}">${_type.value}</option>
                    </c:forEach>
                </select>
                <script>
                    $("#searchForm select[name=type]").val(${param.type});
                </script>
                <select class="form-control"  data-rel="select2-ajax" data-ajax-url="${ctx}/party_selects"
                        name="partyId" data-placeholder="请选择分党委">
                    <option value="${party.id}">${party.name}</option>
                </select>
                <span style="${(empty branch)?'display: none':''}" id="branchDiv">
                <select class="form-control"  data-rel="select2-ajax" data-ajax-url="${ctx}/branch_selects"
                        name="branchId" data-placeholder="请选择党支部">
                    <option value="${branch.id}">${branch.name}</option>
                </select>
                </span>
                <script>
                    register_party_branch_select($("#searchForm"), "branchDiv",
                            '${cm:getMetaTypeByCode("mt_direct_branch").id}', "${party.id}", "${party.classId}" );
                </script>
                <a class="searchBtn btn btn-sm"><i class="fa fa-search"></i> 查找</a>
                <c:set var="_query" value="${not empty param.userId ||not empty param.type ||not empty param.partyId ||not empty param.branchId || not empty param.code || not empty param.sort}"/>
                <c:if test="${_query}">
                    <button type="button" class="resetBtn btn btn-warning btn-sm">
                        <i class="fa fa-reply"></i> 重置
                    </button>
                </c:if>
                <div class="vspace-12"></div>
                <div class="buttons pull-right">
                    <shiro:hasPermission name="memberIn:edit">
                        <a href="javascript:;" class="openView btn btn-info btn-sm" data-url="${ctx}/memberIn_au">
                    <%--<a class="editBtn btn btn-info btn-sm" data-width="1000">--%><i class="fa fa-plus"></i> 添加</a>
                    </shiro:hasPermission>
                    <c:if test="${commonList.recNum>0}">
                    <a class="exportBtn btn btn-success btn-sm tooltip-success"
                       data-rel="tooltip" data-placement="top" title="导出当前搜索的全部结果（按照当前排序）"><i class="fa fa-download"></i> 导出</a>
                    <shiro:hasPermission name="memberIn:del">
                    <a class="batchDelBtn btn btn-danger btn-sm"><i class="fa fa-times"></i> 批量删除</a>
                     </shiro:hasPermission>
                    </c:if>
                </div>
            </mytag:sort-form>
            <div class="space-4"></div>
            <c:if test="${commonList.recNum>0}">
                <table class="table table-actived table-striped table-bordered table-hover table-condensed">
                    <thead>
                    <tr>
                        <th class="center">
                            <label class="pos-rel">
                                <input type="checkbox" class="ace checkAll">
                                <span class="lbl"></span>
                            </label>
                        </th>
                            <th>所属组织机构</th>
							<th>用户</th>
							<th>类别</th>
							<th>转出单位</th>
							<th>转出单位抬头</th>
							<th>介绍信有效期天数</th>
							<th>转出办理时间</th>
							<th>状态</th>
                        <th nowrap></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${memberIns}" var="memberIn" varStatus="st">
                        <c:set value="${cm:getUserById(memberIn.userId)}" var="_sysUser"/>
                        <tr>
                            <td class="center">
                                <label class="pos-rel">
                                    <input type="checkbox" value="${memberIn.id}" class="ace">
                                    <span class="lbl"></span>
                                </label>
                            </td>
                            <td>${partyMap.get(memberIn.partyId).name}
                                <c:if test="${not empty memberIn.branchId}">
                                    -${branchMap.get(memberIn.branchId).name}
                                </c:if></td>
								<td>
                                    <a href="javascript:;" class="openView" data-url="${ctx}/member_view?userId=${memberIn.userId}">
								${_sysUser.realname}
                                        </a>
                                </td>
								<td>${MEMBER_INOUT_TYPE_MAP.get(memberIn.type)}</td>
								<td>${memberIn.fromUnit}</td>
								<td>${memberIn.fromTitle}</td>
								<td>${memberIn.validDays}</td>
								<td>${cm:formatDate(memberIn.fromHandleTime,'yyyy-MM-dd')}</td>
								<td>${MEMBER_IN_STATUS_MAP.get(memberIn.status)}</td>
                            <td>
                                <div class="hidden-sm hidden-xs action-buttons">

                                    <c:if test="${memberIn.status==MEMBER_IN_STATUS_APPLY}">
                                        <button onclick="_deny(${memberIn.id}, '${_sysUser.realname}')" class="btn btn-danger btn-mini">
                                            <i class="fa fa-times"></i> 不通过
                                        </button>
                                        <button onclick="_check1(${memberIn.id})" class="btn btn-success btn-mini">
                                            <i class="fa fa-check"></i> 审核1
                                        </button>
                                    </c:if>
                                    <c:if test="${memberIn.status==MEMBER_IN_STATUS_PARTY_VERIFY}">
                                        <button onclick="_check2(${memberIn.id})" class="btn btn-success btn-mini">
                                            <i class="fa fa-check"></i> 审核2
                                        </button>
                                    </c:if>
                                    
                                    <shiro:hasPermission name="memberIn:edit">
                                    <button data-url="${ctx}/memberIn_au?id=${memberIn.id}" class="openView btn btn-mini" data-width="1000">
                                        <i class="fa fa-edit"></i> 编辑
                                    </button>
                                     </shiro:hasPermission>
                                    
                                </div>

                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
                <wo:page commonList="${commonList}" uri="${ctx}/memberIn_page" target="#page-content" pageNum="5"
                         model="3"/>
            </c:if>
            <c:if test="${commonList.recNum==0}">
                <div class="well well-lg center">
                    <h4 class="green lighter">暂无记录</h4>
                </div>
            </c:if>
        </div>
        </div>
        <div id="item-content">

        </div>
    </div>
</div>
<script>
    function _deny(id, realname){
        loadModal("${ctx}/memberIn_deny?id=" + id + "&realname="+encodeURIComponent(realname));
    }
    function _check1(id){
        bootbox.confirm("确定通过该申请？", function (result) {
            if(result){
                $.post("${ctx}/memberIn_check1",{id:id},function(ret){
                    if(ret.success){
                        page_reload();
                        toastr.success('操作成功。', '成功');
                    }
                });
            }
        });
    }
    function _check2(id){
        bootbox.confirm("确定通过该申请？", function (result) {
            if(result){
                $.post("${ctx}/memberIn_check2",{id:id},function(ret){
                    if(ret.success){
                        page_reload();
                        toastr.success('操作成功。', '成功');
                    }
                });
            }
        });
    }


    $('#searchForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();

    register_user_select($('#searchForm select[name=userId]'));
</script>