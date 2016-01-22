<%@ page import="sys.constants.SystemConstants" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set var="MEMBER_TYPE_MAP" value="<%=SystemConstants.MEMBER_TYPE_MAP%>"/>
<c:set var="MEMBER_TRANSFER_STATUS_MAP" value="<%=SystemConstants.MEMBER_TRANSFER_STATUS_MAP%>"/>
<c:set var="MEMBER_TRANSFER_STATUS_APPLY" value="<%=SystemConstants.MEMBER_TRANSFER_STATUS_APPLY%>"/>
<c:set var="MEMBER_TRANSFER_STATUS_FROM_VERIFY" value="<%=SystemConstants.MEMBER_TRANSFER_STATUS_FROM_VERIFY%>"/>
<c:set var="MEMBER_TRANSFER_STATUS_TO_VERIFY" value="<%=SystemConstants.MEMBER_TRANSFER_STATUS_TO_VERIFY%>"/>
<div class="row">
    <div class="col-xs-12">
        <div id="body-content">
        <!-- PAGE CONTENT BEGINS -->
        <div class="myTableDiv"
             data-url-au="${ctx}/memberTransfer_au"
             data-url-page="${ctx}/memberTransfer_page"
             data-url-del="${ctx}/memberTransfer_del"
             data-url-bd="${ctx}/memberTransfer_batchDel"
             data-url-co="${ctx}/memberTransfer_changeOrder"
             data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <mytag:sort-form css="form-inline hidden-sm hidden-xs" id="searchForm">
                <select data-rel="select2-ajax" data-ajax-url="${ctx}/member_selects"
                        name="userId" data-placeholder="请输入账号或姓名或学工号">
                    <option value="${sysUser.id}">${sysUser.realname}</option>
                </select>
               <%-- <select data-rel="select2" name="type" data-placeholder="请选择类别">
                    <option></option>
                    <c:forEach items="${MEMBER_TYPE_MAP}" var="_type">
                        <option value="${_type.key}">${_type.value}</option>
                    </c:forEach>
                </select>
                <script>
                    $("#searchForm select[name=type]").val(${param.type});
                </script>--%>
                <a class="searchBtn btn btn-sm"><i class="fa fa-search"></i> 查找</a>
                <c:set var="_query" value="${not empty param.userId ||not empty param.type || not empty param.code || not empty param.sort}"/>
                <c:if test="${_query}">
                    <button type="button" class="resetBtn btn btn-warning btn-sm">
                        <i class="fa fa-reply"></i> 重置
                    </button>
                </c:if>
                <div class="vspace-12"></div>
                <div class="buttons pull-right">
                    <shiro:hasPermission name="memberTransfer:edit">
                        <a href="javascript:;" class="openView btn btn-info btn-sm" data-url="${ctx}/memberTransfer_au">
                    <%--<a class="editBtn btn btn-info btn-sm" data-width="1000">--%><i class="fa fa-plus"></i> 添加</a>
                    </shiro:hasPermission>
                    <c:if test="${commonList.recNum>0}">
                    <a class="exportBtn btn btn-success btn-sm tooltip-success"
                       data-rel="tooltip" data-placement="top" title="导出当前搜索的全部结果（按照当前排序）"><i class="fa fa-download"></i> 导出</a>
                    <shiro:hasPermission name="memberTransfer:del">
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
							<th>用户</th>
							<th>类别</th>
							<th>所属组织机构</th>
							<th>转入组织机构</th>
							<th>转出办理时间</th>
							<th>状态</th>
                        <th nowrap></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${memberTransfers}" var="memberTransfer" varStatus="st">
                        <c:set value="${cm:getUserById(memberTransfer.userId)}" var="_sysUser"/>
                        <tr>
                            <td class="center">
                                <label class="pos-rel">
                                    <input type="checkbox" value="${memberTransfer.id}" class="ace">
                                    <span class="lbl"></span>
                                </label>
                            </td>
								<td>
                                    <a href="javascript:;" class="openView" data-url="${ctx}/member_view?userId=${memberTransfer.userId}">
								${_sysUser.realname}
                                </a>
                                </td>
								<td>${MEMBER_TYPE_MAP.get(_sysUser.type)}</td>
                            <td>${partyMap.get(memberTransfer.partyId).name}
                                <c:if test="${not empty memberTransfer.branchId}">
                                    -${branchMap.get(memberTransfer.branchId).name}
                                </c:if></td>
								<td>${partyMap.get(memberTransfer.toPartyId).name}
                                <c:if test="${not empty memberTransfer.toBranchId}">
                                    ${branchMap.get(memberTransfer.toBranchId).name}
                                </c:if>
                                </td>
								<td>${cm:formatDate(memberTransfer.fromHandleTime,'yyyy-MM-dd')}</td>
								<td>${MEMBER_TRANSFER_STATUS_MAP.get(memberTransfer.status)}</td>
                            <td>
                                <div class="hidden-sm hidden-xs action-buttons">

                                    <c:if test="${memberTransfer.status==MEMBER_TRANSFER_STATUS_APPLY}">
                                        <button onclick="_deny(${memberTransfer.id})" class="btn btn-danger btn-mini">
                                            <i class="fa fa-times"></i> 不通过
                                        </button>
                                        <button onclick="_check1(${memberTransfer.id})" class="btn btn-success btn-mini">
                                            <i class="fa fa-check"></i> 审核1
                                        </button>
                                    </c:if>
                                    <c:if test="${memberTransfer.status==MEMBER_TRANSFER_STATUS_FROM_VERIFY}">
                                        <button onclick="_check2(${memberTransfer.id})" class="btn btn-success btn-mini">
                                            <i class="fa fa-check"></i> 审核2
                                        </button>
                                    </c:if>
                                    <shiro:hasPermission name="memberTransfer:edit">
                                    <button data-url="${ctx}/memberTransfer_au?id=${memberTransfer.id}" class="openView btn btn-mini" data-width="1000">
                                        <i class="fa fa-search"></i> 查看
                                    </button>
                                     </shiro:hasPermission>
                                </div>
                                <div class="hidden-md hidden-lg">
                                    <div class="inline pos-rel">
                                        <button class="btn btn-minier btn-primary dropdown-toggle" data-toggle="dropdown" data-position="auto">
                                            <i class="ace-icon fa fa-cog icon-only bigger-110"></i>
                                        </button>

                                        <ul class="dropdown-menu dropdown-only-icon dropdown-yellow dropdown-menu-right dropdown-caret dropdown-close">
                                            <%--<li>
                                            <a href="#" class="tooltip-info" data-rel="tooltip" title="查看">
                                                        <span class="blue">
                                                            <i class="ace-icon fa fa-search-plus bigger-120"></i>
                                                        </span>
                                            </a>
                                        </li>--%>
                                            <shiro:hasPermission name="memberTransfer:edit">
                                            <li>
                                                <a href="#" data-id="${memberTransfer.id}" class="editBtn tooltip-success" data-rel="tooltip" title="编辑">
                                                    <span class="green">
                                                        <i class="ace-icon fa fa-pencil-square-o bigger-120"></i>
                                                    </span>
                                                </a>
                                            </li>
                                            </shiro:hasPermission>
                                            <shiro:hasPermission name="memberTransfer:del">
                                            <li>
                                                <a href="#" data-id="${memberTransfer.id}" class="delBtn tooltip-error" data-rel="tooltip" title="删除">
                                                    <span class="red">
                                                        <i class="ace-icon fa fa-trash-o bigger-120"></i>
                                                    </span>
                                                </a>
                                            </li>
                                            </shiro:hasPermission>
                                        </ul>
                                    </div>
                                </div>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
                <wo:page commonList="${commonList}" uri="${ctx}/memberTransfer_page" target="#page-content" pageNum="5"
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

    function _deny(id){
        bootbox.confirm("确定拒绝该申请？", function (result) {
            if(result){
                $.post("${ctx}/memberTransfer_deny",{id:id},function(ret){
                    if(ret.success){
                        page_reload();
                        SysMsg.success('操作成功。', '成功');
                    }
                });
            }
        });
    }
    function _check1(id){
        bootbox.confirm("确定通过该申请？", function (result) {
            if(result){
                $.post("${ctx}/memberTransfer_check1",{id:id},function(ret){
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
                $.post("${ctx}/memberTransfer_check2",{id:id},function(ret){
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