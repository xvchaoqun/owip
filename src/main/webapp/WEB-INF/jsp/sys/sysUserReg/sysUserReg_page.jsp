<%@ page import="sys.constants.SystemConstants" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <!-- PAGE CONTENT BEGINS -->
        <div id="body-content">
            <div class="myTableDiv"
                 data-url-au="${ctx}/sysUserReg_au"
                 data-url-page="${ctx}/sysUserReg_page"
                 data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
                <c:set var="_query" value="${not empty param.userId ||not empty param.username||not empty param.realname
                ||not empty param.partyId ||not empty param.idcard|| not empty param.code || not empty param.sort}"/>
                <div class="tabbable">
                    <ul class="nav nav-tabs padding-12 tab-color-blue background-blue">
                        <li class="${cls==1?'active':''}">
                            <a ${cls!=1?'href="?cls=1"':''}><i class="fa fa-circle-o"></i> 分党委审核</a>
                        </li>
                        <li class="${cls==2?'active':''}">
                            <a ${cls!=2?'href="?cls=2"':''}><i class="fa fa-times"></i> 未通过</a>
                        </li>
                        <li class="${cls==3?'active':''}">
                            <a ${cls!=3?'href="?cls=3"':''}><i class="fa fa-check"></i> 已审核</a>
                        </li>
                    </ul>
                    <div class="tab-content">
                        <div id="home4" class="tab-pane in active">
                            <div class="jqgrid-vertical-offset buttons">
                                <c:if test="${cls==1}">
                                <shiro:hasPermission name="sysUserReg:edit">
                                    <button id="editBtn" class="jqEditBtn btn btn-primary btn-sm">
                                        <i class="fa fa-edit"></i> 修改信息
                                    </button>
                                </shiro:hasPermission>
                                    <button id="partyApprovalBtn" ${partyApprovalCount>0?'':'disabled'}
                                            class="jqOpenViewBtn btn btn-warning btn-sm"
                                            data-url="${ctx}/sysUserReg_approval"
                                            data-open-by="page"
                                            data-need-id="false"
                                            data-count="${partyApprovalCount}">
                                        <i class="fa fa-sign-in"></i> 分党委审核（${partyApprovalCount}）
                                    </button>
                                </c:if>
                                <button class="jqOpenViewBtn btn btn-info btn-sm"
                                        data-url="${ctx}/applyApprovalLog_page"
                                        data-querystr="&type=${APPLY_APPROVAL_LOG_TYPE_USER_REG}"
                                        data-open-by="page">
                                    <i class="fa fa-check-circle-o"></i> 查看审批记录
                                </button>
                            </div>
                            <div class="jqgrid-vertical-offset widget-box ${_query?'':'collapsed'} hidden-sm hidden-xs">
                                <div class="widget-header">
                                    <h4 class="widget-title">搜索</h4>
                                    <div class="widget-toolbar">
                                        <a href="#" data-action="collapse">
                                            <i class="ace-icon fa fa-chevron-${_query?'up':'down'}"></i>
                                        </a>
                                    </div>
                                </div>
                                <div class="widget-body">
                                    <div class="widget-main no-padding">
                                        <form class="form-inline search-form" id="searchForm">

                                                    <input type="hidden" name="cls" value="${cls}">
                                                    <div class="form-group">
                                                        <label>注册账号</label>
                                                        <input type="text" name="username" value="${param.username}">
                                                    </div>

                                                    <div class="form-group">
                                                        <label>真实姓名</label>
                                                        <input type="text" name="realname" value="${param.realname}">
                                                    </div>
                                            <div class="form-group">
                                                <label>身份证号码</label>
                                                <input type="text" name="idcard" value="${param.idcard}">
                                            </div>
                                                    <div class="form-group">
                                                        <label>所属分党委</label>
                                                            <select name="partyId" data-rel="select2"
                                                                    data-placeholder="请选择所属分党委" data-width="350">
                                                                <option></option>
                                                                <c:forEach var="entity" items="${partyMap}">
                                                                    <option value="${entity.key}">${entity.value.name}</option>
                                                                </c:forEach>
                                                            </select>
                                                            <script>
                                                                $("#searchForm select[name=partyId]").val('${param.partyId}');
                                                            </script>
                                                    </div>

                                            <div class="form-group">
                                                <label>关联系统账号</label>
                                                <select data-rel="select2-ajax" data-ajax-url="${ctx}/sysUser_selects"
                                                        name="userId" data-placeholder="请输入账号或姓名或学工号">
                                                    <option value="${sysUser.id}">${sysUser.realname}-${sysUser.code}</option>
                                                </select>
                                            </div>
                                            <div class="clearfix form-actions center">
                                                <a class="jqSearchBtn btn btn-default btn-sm"><i class="fa fa-search"></i> 查找</a>

                                                <c:if test="${_query || not empty param.sort}">&nbsp;
                                                    <button type="button" class="resetBtn btn btn-warning btn-sm"
                                                            data-querystr="cls=${cls}">
                                                        <i class="fa fa-reply"></i> 重置
                                                    </button>
                                                </c:if>
                                            </div>
                                        </form>
                                    </div>
                                </div>
                            </div>

                            <div class="space-4"></div>
                            <table id="jqGrid" class="jqGrid table-striped"></table>
                            <div id="jqGridPager"></div>
                        </div></div></div>
            </div>
        </div>
        <div id="item-content">

        </div>
    </div>
</div>
<script>
    function goto_next(goToNext) {
        if (goToNext) {
            if ($("#next").hasClass("disabled") && $("#last").hasClass("disabled"))
                $(".closeView").click();
            else if (!$("#next").hasClass("disabled"))
                $("#next").click();
            else
                $("#last").click();
        }
    }
    function apply_deny(id, goToNext) {

        loadModal("${ctx}/sysUserReg_deny?id=" + id  +"&goToNext="+((goToNext!=undefined&&goToNext)?"1":"0"));
    }
    function apply_pass(id, goToNext) {
        bootbox.confirm("确定通过该申请？", function (result) {
            if (result) {
                $.post("${ctx}/sysUserReg_check", {id: id}, function (ret) {
                    if (ret.success) {
                        SysMsg.success('操作成功。', '成功', function () {
                            //page_reload();
                            goto_next(goToNext);
                        });
                    }
                });
            }
        });
    }

    $("#jqGrid").jqGrid({
        url: '${ctx}/sysUserReg_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            {label: '账号', name: 'username', width: 100, frozen: true},
            {label: '真实姓名', name: 'realname', width: 100, frozen: true},
            {label: '类别', name: 'typeName', width: 200, formatter: function (cellvalue, options, rowObject) {
                return _cMap.USER_TYPE_MAP[rowObject.type];
            }},
            {label: '学工号', name: 'code', width: 100, frozen: true},
            {label: '身份证号码', name: 'idcard', width: 100, frozen: true},
            {label: '手机号码', name: 'phone', width: 100, frozen: true},
            {label: '所属组织机构', name: 'party', width: 250},
            {label: '注册时间', name: 'createTime', width: 150},
            {label: 'IP', name: 'ip', width: 150}, {hidden: true, name: 'status'}
        ],
        onSelectRow: function (id, status) {
            jgrid_sid = id;
            //console.log(id)
            var ids = $(this).getGridParam("selarrrow");
            if (ids.length > 1) {
                $("#partyApprovalBtn").prop("disabled", true);
            } else if (status) {
                var rowData = $(this).getRowData(id);
                $("#partyApprovalBtn").prop("disabled", rowData.status != "${MEMBER_STAY_STATUS_APPLY}");
            } else {
                $("*[data-count]").each(function(){
                    $(this).prop("disabled", $(this).data("count") == 0);
                })
            }
        },
        onSelectAll: function (aRowids, status) {
            $("*[data-count]").each(function(){
                $(this).prop("disabled", $(this).data("count") == 0);
            })
        }
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid');

    $('[data-rel="select2"]').select2();
    register_user_select($('#searchForm select[name=userId]'));
</script>
