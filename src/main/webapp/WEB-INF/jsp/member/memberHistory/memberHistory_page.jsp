<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<%@ include file="/WEB-INF/jsp/member/constants.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <div id="body-content" class="rownumbers" data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <c:set var="_query" value="${not empty param.code ||not empty param.realname ||not empty param.idcard ||not empty param.partyName ||not empty param.branchName ||not empty param._growTime ||not empty param._positiveTime || not empty param.code
            ||not empty param.type||not empty param.gender||not empty param.politicalStatus||not empty param.detailReason||not empty param.outReason||not empty param.remark}"/>
                <div class="tabbable">
                    <ul class="nav nav-tabs padding-12 tab-color-blue background-blue">
                        <li class="${cls==0?'active':''}">
                            <a href="javascript:;" class="loadPage" data-url="${ctx}/member/memberHistory?cls=0"}><i class="fa fa-circle-o"></i> 历史党员库（${normalCount}）</a>
                        </li>
                        <li class="${cls==1?'active':''}">
                            <a href="javascript:;" class="loadPage" data-url="${ctx}/member/memberHistory?cls=1"}><i class="fa fa-times"></i> 已移除（${total-normalCount}）</a>
                        </li>
                    </ul>
                    <div class="tab-content">
                        <div class="multi-row-head-table tab-pane in active">
            <div class="jqgrid-vertical-offset buttons">
                <shiro:hasPermission name="memberHistory:edit">
                    <c:if test="${cls==0}">
                        <button class="openView btn btn-info btn-sm"
                                data-url="${ctx}/member/memberHistory_au">
                            <i class="fa fa-plus"></i> 添加</button>
                    </c:if>
                    <button class="jqEditBtn btn btn-primary btn-sm"
                       data-url="${ctx}/member/memberHistory_au?cls=${cls}"
                            data-open-by="page"
                       data-grid-id="#jqGrid"><i class="fa fa-edit"></i>
                        修改</button>
                    <c:if test="${cls==0}">
                        <button class="popupBtn btn btn-info btn-sm tooltip-info"
                                data-url="${ctx}/member/memberHistory_import"
                                data-rel="tooltip" data-placement="top" title="批量导入"><i class="fa fa-upload"></i>
                            批量导入
                        </button>
                        <a class="jqOpenViewBatchBtn btn btn-warning btn-sm"
                           data-callback="_reload"
                           data-url="${ctx}/member/memberHistory_out?cls=${cls}" data-title="从历史党员库移除"
                           data-msg="确定移除这{0}个历史党员吗？"><i class="fa fa-history"></i> 移除</a>
                    </c:if>
                    <c:if test="${cls==1}">
                        <button class="jqBatchBtn btn btn-success btn-sm"
                                data-url="${ctx}/member/memberHistory_recover?cls=${cls}" data-title="恢复至历史党员库"
                                data-grid-id="#jqGrid"
                                data-callback="_reload"
                                data-msg="确定恢复这{0}个人员至历史党员库吗？"><i class="fa fa-reply"></i> 恢复</button>
                    </c:if>
                </shiro:hasPermission>
                <button class="jqOpenViewBtn btn btn-info btn-sm"
                        data-url="${ctx}/sysApprovalLog"
                        data-querystr="&type=<%=SystemConstants.SYS_APPROVAL_LOG_TYPE_MEMBER_HISTORY%>"
                        data-open-by="page">
                    <i class="fa fa-search"></i> 操作记录
                </button>
                <c:if test="${cls==1}">
                <shiro:hasPermission name="memberHistory:del">
                    <button data-url="${ctx}/member/memberHistory_batchDel?cls=${cls}"
                            data-title="删除"
                            data-msg="确定删除这{0}条数据？(数据删除后不可恢复，请谨慎操作)"
                            data-grid-id="#jqGrid"
                            class="jqBatchBtn btn btn-danger btn-sm">
                        <i class="fa fa-trash"></i> 删除
                    </button>
                </shiro:hasPermission>
                </c:if>
                <button class="jqExportBtn btn btn-success btn-sm tooltip-success"
                   data-url="${ctx}/member/memberHistory_data?cls=${cls}"
                   data-rel="tooltip" data-placement="top" title="导出选中记录或所有搜索结果">
                    <i class="fa fa-download"></i> 导出</button>
            </div>
            <div class="jqgrid-vertical-offset widget-box ${_query?'':'collapsed'} hidden-sm hidden-xs">
                <div class="widget-header">
                    <h4 class="widget-title">搜索</h4>
                    <span class="widget-note">${note_searchbar}</span>
                    <div class="widget-toolbar">
                        <a href="#" data-action="collapse">
                            <i class="ace-icon fa fa-chevron-${_query?'up':'down'}"></i>
                        </a>
                    </div>
                </div>
                <div class="widget-body">
                    <div class="widget-main no-padding">
                        <form class="form-inline search-form" id="searchForm">
                            <input type="hidden" name="cls" value="${cls}"/>
                        <div class="form-group">
                            <label>学工号</label>
                            <input class="form-control search-query" name="code" type="text" value="${param.code}"
                                   placeholder="请输入学工号">
                        </div>
                        <div class="form-group">
                            <label>姓名</label>
                            <input class="form-control search-query" name="realname" type="text" value="${param.realname}"
                                   placeholder="请输入姓名">
                        </div>
                            <div class="form-group">
                                <label>身份证号</label>
                                <input class="form-control search-query" name="idcard" type="text" value="${param.idcard}"
                                       placeholder="请输入身份证号">
                            </div>
                            <div class="form-group">
                                <label>人员类别</label>
                                <select name="type" data-width="150" data-rel="select2"
                                        data-placeholder="请选择">
                                    <option></option>
                                    <c:forEach items="<%=SystemConstants.USER_TYPE_MAP%>" var="entry">
                                        <option value="${entry.key}">${entry.value}</option>
                                    </c:forEach>
                                </select>
                                <script>
                                    $("#searchForm select[name=type]").val('${param.type}');
                                </script>
                            </div>
                            <div class="form-group">
                                <label>性别</label>
                                <select name="gender" data-width="150" data-rel="select2"
                                        data-placeholder="请选择">
                                    <option></option>
                                    <c:forEach items="<%=SystemConstants.GENDER_MAP%>" var="entry">
                                        <option value="${entry.key}">${entry.value}</option>
                                    </c:forEach>
                                </select>
                                <script>
                                    $("#searchForm select[name=gender]").val('${param.gender}');
                                </script>
                            </div>
                        <div class="form-group">
                            <label>二级党组织名称</label>
                            <input class="form-control search-query" name="partyName" type="text" value="${param.partyName}"
                                   placeholder="请输入二级党组织名称">
                        </div>
                        <div class="form-group">
                            <label>党支部名称</label>
                            <input class="form-control search-query" name="branchName" type="text" value="${param.branchName}"
                                   placeholder="请输入党支部名称">
                        </div>
                            <div class="form-group">
                                <label>党籍状态</label>
                                <select name="politicalStatus" data-width="150" data-rel="select2"
                                        data-placeholder="请选择">
                                    <option></option>
                                    <c:forEach items="<%=MemberConstants.MEMBER_POLITICAL_STATUS_MAP%>" var="entry">
                                        <option value="${entry.key}">${entry.value}</option>
                                    </c:forEach>
                                </select>
                                <script>
                                    $("#searchForm select[name=politicalStatus]").val('${param.politicalStatus}');
                                </script>
                            </div>
                            <div class="form-group">
                                <label>入党时间</label>
                                <div class="input-group tooltip-success" data-rel="tooltip" title="入党时间范围">
                                                            <span class="input-group-addon">
                                                                <i class="fa fa-calendar bigger-110"></i>
                                                            </span>
                                    <input placeholder="请选择入党时间范围" data-rel="date-range-picker"
                                           class="form-control date-range-picker"
                                           type="text" name="_growTime" value="${param._growTime}"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label>转正时间</label>
                                <div class="input-group tooltip-success" data-rel="tooltip" title="入党时间范围">
                                                            <span class="input-group-addon">
                                                                <i class="fa fa-calendar bigger-110"></i>
                                                            </span>
                                    <input placeholder="请选择转正时间范围" data-rel="date-range-picker"
                                           class="form-control date-range-picker"
                                           type="text" name="_positiveTime" value="${param._positiveTime}"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label>转移至历史党员库详细原因</label>
                                <input class="form-control search-query" name="detailReason" type="text" value="${param.detailReason}"
                                       placeholder="请输入移除原因">
                            </div>
                            <c:if test="${cls==1}">
                                <div class="form-group">
                                    <label>移除原因</label>
                                    <input class="form-control search-query" name="outReason" type="text" value="${param.outReason}"
                                           placeholder="请输入移除原因">
                                </div>
                            </c:if>
                            <div class="form-group">
                                <label>备注</label>
                                <input class="form-control search-query" name="remark" type="text" value="${param.remark}"
                                       placeholder="请输入备注">
                            </div>
                            <div class="clearfix form-actions center">
                                <a class="jqSearchBtn btn btn-default btn-sm"
                                   data-url="${ctx}/member/memberHistory"
                                   data-target="#page-content"
                                   data-form="#searchForm"><i class="fa fa-search"></i> 查找</a>
                                <c:if test="${_query}">&nbsp;
                                    <button type="button" class="reloadBtn btn btn-warning btn-sm"
                                            data-url="${ctx}/member/memberHistory"
                                            data-target="#page-content">
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
                        </div>
                    </div>
                </div>
        </div>
        <div id="body-content-view"></div>
    </div>
</div>
<jsp:include page="/WEB-INF/jsp/common/daterangerpicker.jsp"/>
<script>

    function _reload(){
        $.loadPage({url:"${ctx}/member/memberHistory?cls=${cls}"});
    }

    $("#jqGrid").jqGrid({
        rownumbers:true,
        url: '${ctx}/member/memberHistory_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
                { label: '学工号',name: 'code',width: 120,frozen:true},
                { label: '姓名',name: 'realname',frozen:true},
                { label: '人员类别',name: 'type',formatter: function (cellvalue,options,rowObject){
                    return _cMap.USER_TYPE_MAP[cellvalue];
                    },frozen:true},
                { label: '性别',name: 'gender', width: 55, formatter: $.jgrid.formatter.GENDER},
                { label: '身份证号',name: 'idcard',width:160},
                { label: '民族',name: 'nation',width: 110},
                { label: '籍贯',name: 'nativePlace',width: 110},
                { label: '出生时间',name: 'birth',
                    width: 120,
                    formatter: $.jgrid.formatter.date,
                    formatoptions: {newformat: 'Y.m.d'}
                },
                { label: '年龄',name: 'birth', width: 55, formatter: $.jgrid.formatter.AGE, formatoptions: {newformat: '${_p_birthToDayFormat}'}},
                { label: '${_p_partyName}名称',name: 'partyName',align:'left',width:300},
                { label: '党支部名称',name: 'branchName',align:'left',width:300},
                { label: '党籍状态',name: 'politicalStatus', formatter: function (cellvalue, options, rowObject) {
                        if (cellvalue)
                            return _cMap.MEMBER_POLITICAL_STATUS_MAP[cellvalue];
                        return "-";
                    }},
                { label: '组织关系<br/>转入时间',name: 'transferTime',
                    formatter: $.jgrid.formatter.date,
                    formatoptions: {newformat: 'Y.m.d'}
                    },
                { label: '提交书面<br/>申请书时间',name: 'applyTime',
                    formatter: $.jgrid.formatter.date,
                    formatoptions: {newformat: 'Y.m.d'}
                },
                { label: '确定为入党<br/>积极分子时间',name: 'activeTime',
                    formatter: $.jgrid.formatter.date,
                    formatoptions: {newformat: 'Y.m.d'}
                },
                { label: '确定为<br/>发展对象时间',name: 'candidateTime',
                    formatter: $.jgrid.formatter.date,
                    formatoptions: {newformat: 'Y.m.d'}
                },
                { label: '入党介绍人',name: 'sponsor'},
                { label: '入党时间',name: 'growTime',
                    width: 120,
                    formatter: $.jgrid.formatter.date,
                    formatoptions: {newformat: 'Y.m.d'}
                },
                { label: '转正时间',name: 'positiveTime',
                    width: 120,
                    formatter: $.jgrid.formatter.date,
                    formatoptions: {newformat: 'Y.m.d'}
                },
                { label: '专业技术职务',name: 'proPost'},
                { label: '手机',name: 'phone',width:110},
                { label: '邮箱',name: 'email'},
            <c:if test="${cls==1}">
                { label: '移除原因',name: 'outReason',width:200},
            </c:if>
            { label: '添加人',name: 'addUser.realname'},
            { label: '添加时间',name: 'addDate',formatter:$.jgrid.formatter.date, formatoptions: {newformat: 'Y.m.d'}},
            <c:if test="${cm:getMetaTypes('mc_mh_lable').size()>0}">
                    { label: '标签',name: 'lable',width: 200,formatter:function (cellValue, options, rowObject){
                            if (cellValue==undefined) return '--';
                            return ($.map(cellValue.split(","), function(e){
                                return $.jgrid.formatter.MetaType(e);
                            })).join("，")
                        }},
                </c:if>
                { label: '转移原因',name: 'detailReason',width:250},
                { label: '备注',name: 'remark',width:150},
        ]
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid');
    $.initNavGrid("jqGrid", "jqGridPager");
    //$.register.user_select($('[data-rel="select2-ajax"]'));
    $('#searchForm [data-rel="select2"]').select2();
    //$('[data-rel="tooltip"]').tooltip();
    //$.register.date($('.date-picker'));
</script>