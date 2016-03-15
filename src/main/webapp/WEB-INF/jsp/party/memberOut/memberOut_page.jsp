<%@ page import="sys.constants.SystemConstants" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<c:set var="MEMBER_OUT_STATUS_MAP" value="<%=SystemConstants.MEMBER_OUT_STATUS_MAP%>"/>
<c:set var="MEMBER_OUT_STATUS_APPLY" value="<%=SystemConstants.MEMBER_OUT_STATUS_APPLY%>"/>
<c:set var="MEMBER_OUT_STATUS_PARTY_VERIFY" value="<%=SystemConstants.MEMBER_OUT_STATUS_PARTY_VERIFY%>"/>
<c:set var="MEMBER_OUT_STATUS_OW_VERIFY" value="<%=SystemConstants.MEMBER_OUT_STATUS_OW_VERIFY%>"/>
<div class="row">
    <div class="col-xs-12">
        <div id="body-content">
        <!-- PAGE CONTENT BEGINS -->
        <div class="myTableDiv"
             data-url-au="${ctx}/memberOut_au"
             data-url-page="${ctx}/memberOut_page"
             data-url-del="${ctx}/memberOut_del"
             data-url-bd="${ctx}/memberOut_batchDel"
             data-url-co="${ctx}/memberOut_changeOrder"
             data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <mytag:sort-form css="form-inline hidden-sm hidden-xs" id="searchForm">
                <select  data-rel="select2-ajax" data-ajax-url="${ctx}/member_selects"
                        name="userId" data-placeholder="请输入账号或姓名或学工号">
                    <option value="${sysUser.id}">${sysUser.realname}</option>
                </select>
                <select data-rel="select2" name="type" data-placeholder="请选择类别">
                    <option></option>
                    <c:forEach items="${MEMBER_INOUT_TYPE_MAP}" var="_type">
                        <option value="${_type.key}">${_type.value}</option>
                    </c:forEach>
                </select>
                <script>
                    $("#searchForm select[name=type]").val(${param.type});
                </script>
                <a class="searchBtn btn btn-default btn-sm"><i class="fa fa-search"></i> 查找</a>
                <c:set var="_query" value="${not empty param.userId ||not empty param.type || not empty param.code || not empty param.sort}"/>
                <c:if test="${_query}">
                    <button type="button" class="resetBtn btn btn-warning btn-sm">
                        <i class="fa fa-reply"></i> 重置
                    </button>
                </c:if>
                <div class="vspace-12"></div>
                <div class="buttons pull-right">
                    <shiro:hasPermission name="memberOut:edit">
                        <a href="javascript:;" class="openView btn btn-info btn-sm" data-url="${ctx}/memberOut_au">
                    <%--<a class="editBtn btn btn-info btn-sm" data-width="1000">--%><i class="fa fa-plus"></i> 添加</a>
                    </shiro:hasPermission>
                    <c:if test="${commonList.recNum>0}">
                    <a class="exportBtn btn btn-success btn-sm tooltip-success"
                       data-rel="tooltip" data-placement="top" title="导出当前搜索的全部结果（按照当前排序）"><i class="fa fa-download"></i> 导出</a>
                    <shiro:hasPermission name="memberOut:del">
                    <a class="batchDelBtn btn btn-danger btn-sm"><i class="fa fa-times"></i> 批量删除</a>
                     </shiro:hasPermission>
                    </c:if>
                </div>
            </mytag:sort-form>
            <div class="space-4"></div>
            <c:if test="${commonList.recNum>0}">
            <div class="table-container">
                <table style="min-width: 1800px"  class="overflow-y table table-actived table-striped table-bordered table-hover">
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
							<th>转入单位抬头</th>
							<th>转入单位</th>
							<th>转出单位</th>
							<th>介绍信有效期天数</th>
							<th>办理时间</th>
							<th>状态</th>
							<th>打印</th>
                        <th nowrap></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${memberOuts}" var="memberOut" varStatus="st">
                        <c:set value="${cm:getUserById(memberOut.userId)}" var="_sysUser"/>
                        <tr>
                            <td class="center">
                                <label class="pos-rel">
                                    <input type="checkbox" value="${memberOut.id}" class="ace">
                                    <span class="lbl"></span>
                                </label>
                            </td>
                            <td>${partyMap.get(memberOut.partyId).name}
                                <c:if test="${not empty memberOut.branchId}">
                                    -${branchMap.get(memberOut.branchId).name}
                                </c:if></td>
								<td>
                                    <a href="javascript:;" class="openView" data-url="${ctx}/member_view?userId=${memberOut.userId}">
								${_sysUser.realname}
                                </a>
                                </td>
								<td>${MEMBER_INOUT_TYPE_MAP.get(memberOut.type)}</td>
								<td>${memberOut.toTitle}</td>
								<td>${memberOut.toUnit}</td>
								<td>${memberOut.fromUnit}</td>
								<td>${memberOut.validDays}</td>
								<td>${cm:formatDate(memberOut.handleTime,'yyyy-MM-dd')}</td>
								<td>${MEMBER_OUT_STATUS_MAP.get(memberOut.status)}</td>
                                <td>
                                    <c:if test="${memberOut.type==MEMBER_INOUT_TYPE_INSIDE}">
                                    <button data-url="${ctx}/memberOut/printPreview?userId=${memberOut.userId}"
                                            class="openView btn btn-primary btn-mini btn-xs">
                                        <i class="fa fa-print"></i> 打印介绍信
                                    </button>
                                    </c:if>
                                    <c:if test="${memberOut.type==MEMBER_INOUT_TYPE_OUTSIDE}">
                                        <button data-url="${ctx}/memberOut/printPreview?userId=${memberOut.userId}"
                                                class="openView btn btn-warning btn-mini btn-xs">
                                            <i class="fa fa-print"></i> 介绍信套打
                                        </button>
                                    </c:if>
                                </td>
                            <td>
                                <div class="hidden-sm hidden-xs action-buttons">

                                    <c:if test="${memberOut.status==MEMBER_OUT_STATUS_APPLY}">
                                        <button onclick="_deny(${memberOut.id}, '${_sysUser.realname}')" class="btn btn-danger btn-mini btn-xs">
                                            <i class="fa fa-times"></i> 不通过
                                        </button>
                                        <button onclick="_check1(${memberOut.id})" class="btn btn-success btn-mini btn-xs">
                                            <i class="fa fa-check"></i> 审核
                                        </button>
                                    </c:if>
                                    <c:if test="${memberOut.status==MEMBER_OUT_STATUS_PARTY_VERIFY}">
                                        <button onclick="_check2(${memberOut.id})" class="btn btn-success btn-mini btn-xs">
                                            <i class="fa fa-check"></i> 组织部审核
                                        </button>
                                    </c:if>
                                    <shiro:hasPermission name="memberOut:edit">
                                    <button data-url="${ctx}/memberOut_au?id=${memberOut.id}" class="openView btn btn-default btn-mini btn-xs">
                                        <i class="fa fa-search"></i> 查看
                                    </button>
                                     </shiro:hasPermission>
                                </div>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
                </div>
                <wo:page commonList="${commonList}" uri="${ctx}/memberOut_page" target="#page-content" pageNum="5"
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

   stickheader();

    function _deny(id, realname){
        loadModal("${ctx}/memberOut_deny?id=" + id + "&realname="+encodeURIComponent(realname));
    }

    function _check1(id){
        bootbox.confirm("确定通过该申请？", function (result) {
            if(result){
                $.post("${ctx}/memberOut_check1",{id:id},function(ret){
                    if(ret.success){
                        page_reload();
                        SysMsg.success('操作成功。', '成功');
                    }
                });
            }
        });
    }
    function _check2(id){
        bootbox.confirm("确定通过该申请？", function (result) {
            if(result){
                $.post("${ctx}/memberOut_check2",{id:id},function(ret){
                    if(ret.success){
                        page_reload();
                        SysMsg.success('操作成功。', '成功');
                    }
                });
            }
        });
    }


    $('#searchForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
    register_user_select($('#searchForm select[name=userId]'));
</script>