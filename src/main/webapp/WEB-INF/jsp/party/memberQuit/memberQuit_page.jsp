<%@ page import="sys.constants.SystemConstants" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set var="RETIRE_QUIT_TYPE_MAP" value="<%=SystemConstants.RETIRE_QUIT_TYPE_MAP%>"/>
<c:set var="MEMBER_STATUS_QUIT" value="<%=SystemConstants.MEMBER_STATUS_QUIT%>"/>
<c:set var="GENDER_MAP" value="<%=SystemConstants.GENDER_MAP%>"/>
<div id="body-content">
<div class="row">
    <div class="col-xs-12">
        <!-- PAGE CONTENT BEGINS -->
        <div class="myTableDiv"
             data-url-page="${ctx}/memberQuit_page"
             data-url-co="${ctx}/memberQuit_changeOrder"
             data-querystr="${pageContext.request.queryString}">
            <mytag:sort-form css="form-inline hidden-sm hidden-xs" id="searchForm">

                <select  class="form-control" data-rel="select2-ajax" data-ajax-url="${ctx}/member_selects?status=${MEMBER_STATUS_QUIT}"
                        name="userId" data-placeholder="请输入账号或姓名或学工号">
                    <option value="${sysUser.id}">${sysUser.realname}</option>
                </select>
                <select class="form-control" data-rel="select2" name="type" data-placeholder="请选择">
                    <option></option>
                    <c:forEach items="${RETIRE_QUIT_TYPE_MAP}" var="quitType">
                        <option value="${quitType.key}">${quitType.value}</option>
                    </c:forEach>
                </select>
                <script>
                    $("#searchForm select[name=type]").val("${param.type}");
                </script>
                <div class="input-group tooltip-success" data-rel="tooltip" title="出党时间范围">
                                                    <span class="input-group-addon">
                                                        <i class="fa fa-calendar bigger-110"></i>
                                                    </span>
                    <input placeholder="请选择出党时间范围" data-rel="date-range-picker" class="form-control date-range-picker" type="text" name="_quitTime" value="${param._quitTime}"/>
                </div>
                <a class="searchBtn btn btn-sm"><i class="fa fa-search"></i> 查找</a>
                <c:set var="_query" value="${not empty param.userId ||not empty param.partyId ||not empty param.branchId ||not empty param.type ||not empty param._quitTime || not empty param.code || not empty param.sort}"/>
                <c:if test="${_query}">
                    <button type="button" class="resetBtn btn btn-warning btn-sm">
                        <i class="fa fa-reply"></i> 重置
                    </button>
                </c:if>
                <div class="vspace-12"></div>
                <div class="buttons pull-right">
                    <shiro:hasPermission name="memberQuit:edit">
                    <a class="btn btn-info btn-sm" onclick="_au()"><i class="fa fa-plus"></i> 添加</a>
                    </shiro:hasPermission>
                    <c:if test="${commonList.recNum>0}">
                    <a class="exportBtn btn btn-success btn-sm tooltip-success"
                       data-rel="tooltip" data-placement="top" title="导出当前搜索的全部结果（按照当前排序）"><i class="fa fa-download"></i> 导出</a>
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
							<th>学工号</th>
							<th>姓名</th>
							<th>性别</th>
							<th>年龄</th>
							<th>入党时间</th>
							<th>所属组织机构</th>
							<th>出党原因</th>
							<th>出党时间</th>
                        <shiro:hasPermission name="memberQuit:changeOrder">
                            <c:if test="${!_query && commonList.recNum>1}">
                                <th nowrap class="hidden-480">排序</th>
                            </c:if>
                        </shiro:hasPermission>
                        <th nowrap></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${memberQuits}" var="memberQuit" varStatus="st">
                        <tr>
                            <td class="center">
                                <label class="pos-rel">
                                    <input type="checkbox" value="${memberQuit.userId}" class="ace">
                                    <span class="lbl"></span>
                                </label>
                            </td>
                            <c:set value="${cm:getUserById(memberQuit.userId)}" var="_sysUser"/>
								<td>${_sysUser.code}</td>
								<td>
                                    <a href="javascript:;" class="openView" data-url="${ctx}/member_view?userId=${memberQuit.userId}">
								${_sysUser.realname}
                                </a>
                                </td>
                                <td>${GENDER_MAP.get(_sysUser.gender)}</td>
                                <td>${cm:formatDate(_sysUser.birth, "yyyy-MM-dd")}</td>
								<td>${cm:formatDate(memberQuit.growTime, "yyyy-MM-dd")}</td>
								<td>${memberQuit.partyName}
                                <c:if test="${not empty memberQuit.branchName}">
                                    -${memberQuit.branchName}
                                </c:if>
                                </td>
								<td>${RETIRE_QUIT_TYPE_MAP.get(memberQuit.type)}</td>
								<td>${cm:formatDate(memberQuit.quitTime,'yyyy-MM-dd')}</td>
                            <td>
                                <c:if test="${memberQuit.status==1}">
                                    已完成
                                        </c:if>
                                <c:if test="${memberQuit.status!=1}">
                                <div class="hidden-sm hidden-xs action-buttons">
                                    <shiro:hasPermission name="memberQuit:edit">
                                    <button onclick="_au(${memberQuit.userId})" class="editBtn btn btn-mini">
                                        <i class="fa fa-edit"></i> 编辑
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
                                        </ul>
                                    </div>
                                </div>
                                </c:if>

                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
                <wo:page commonList="${commonList}" uri="${ctx}/memberQuit_page" target="#page-content" pageNum="5"
                         model="3"/>
            </c:if>
            <c:if test="${commonList.recNum==0}">
                <div class="well well-lg center">
                    <h4 class="green lighter">暂无记录</h4>
                </div>
            </c:if>
        </div>
    </div>
</div>
</div><div id="item-content"></div>
<link rel="stylesheet" href="${ctx}/assets/css/daterangepicker.css" />
<script src="${ctx}/assets/js/date-time/moment.js"></script>
<script src="${ctx}/assets/js/date-time/daterangepicker.js"></script>
<script src="${ctx}/extend/js/daterange-zh-CN.js"></script>
<script>

    function _au(userId){
        var url = "${ctx}/memberQuit_au";
        if(userId)
            url += "?userId=" + userId;
        loadModal(url);
    }
    $('[data-rel=date-range-picker]').daterangepicker({
        autoUpdateInput:false,
        'applyClass' : 'btn-sm btn-success',
        'cancelClass' : 'btn-sm btn-default',
        locale: {
            applyLabel: '确定',
            cancelLabel: '清除'
        }
    }).on('apply.daterangepicker', function(ev, picker) {
        $(this).val(picker.startDate.format('YYYY-MM-DD') + ' 至 ' + picker.endDate.format('YYYY-MM-DD'));
    }).on('cancel.daterangepicker', function(ev, picker) {
        $(this).val('');
    });

    $('#searchForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
    register_user_select($('#searchForm select[name=userId]'));
</script>