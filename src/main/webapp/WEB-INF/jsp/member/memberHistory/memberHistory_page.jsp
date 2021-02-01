<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set value="${_pMap['postTimeToDay']=='true'?'Y.m.d':'Y.m'}" var="_p_postTimeToDayFormat"/>
<div class="row">
    <div class="col-xs-12">
        <div id="body-content" class="rownumbers" data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <c:set var="_query" value="${not empty param.realname ||not empty param.partyName ||not empty param.branchName ||not empty param._growTime ||not empty param._positiveTime || not empty param.code
            ||not empty param.type||not empty param.gender||not empty param.politicalStatus||not empty param.remark1||not empty param.remark2||not empty param.remark3}"/>
            <div class="jqgrid-vertical-offset buttons">
                <shiro:hasPermission name="memberHistory:edit">
                    <button class="openView btn btn-info btn-sm"
                            data-url="${ctx}/member/memberHistory_au">
                        <i class="fa fa-plus"></i> 添加</button>
                    <button class="jqEditBtn btn btn-primary btn-sm"
                       data-url="${ctx}/member/memberHistory_au"
                            data-open-by="page"
                       data-grid-id="#jqGrid"><i class="fa fa-edit"></i>
                        修改</button>
                    <button class="popupBtn btn btn-info btn-sm tooltip-info"
                            data-url="${ctx}/member/memberHistory_import"
                            data-rel="tooltip" data-placement="top" title="批量导入"><i class="fa fa-upload"></i>
                        批量导入
                    </button>
                </shiro:hasPermission>
                <shiro:hasPermission name="memberHistory:del">
                    <button data-url="${ctx}/member/memberHistory_batchDel"
                            data-title="删除"
                            data-msg="确定删除这{0}条数据？"
                            data-grid-id="#jqGrid"
                            class="jqBatchBtn btn btn-danger btn-sm">
                        <i class="fa fa-trash"></i> 删除
                    </button>
                </shiro:hasPermission>
                <button class="jqExportBtn btn btn-success btn-sm tooltip-success"
                   data-url="${ctx}/member/memberHistory_data"
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
                                <label>人员类别</label>
                                <select name="type" data-width="150" data-rel="select2"
                                        data-placeholder="请选择">
                                    <option></option>
                                    <c:forEach items="<%=MemberConstants.MEMBER_TYPE_MAP%>" var="entry">
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
                                <label>备注1</label>
                                <input class="form-control search-query" name="remark1" type="text" value="${param.remark1}"
                                       placeholder="请输入">
                            </div>
                            <div class="form-group">
                                <label>备注2</label>
                                <input class="form-control search-query" name="remark2" type="text" value="${param.remark2}"
                                       placeholder="请输入">
                            </div>
                            <div class="form-group">
                                <label>备注3</label>
                                <input class="form-control search-query" name="remark3" type="text" value="${param.remark3}"
                                       placeholder="请输入">
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
        <div id="body-content-view"></div>
    </div>
</div>
<jsp:include page="/WEB-INF/jsp/common/daterangerpicker.jsp"/>
<script>
    $("#jqGrid").jqGrid({
        rownumbers:true,
        url: '${ctx}/member/memberHistory_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
                { label: '学工号',name: 'code',float:true},
                { label: '姓名',name: 'realname',float:true},
                { label: '人员类别',name: 'type',formatter: function (cellvalue,options,rowObject){
                    return _cMap.MEMBER_TYPE_MAP[cellvalue];
                    },float:true},
                { label: '性别',name: 'gender', width: 55, formatter: $.jgrid.formatter.GENDER,float:true},
                { label: '身份证号',name: 'idCard',float:true,width:160},
                { label: '民族',name: 'nation',width: 110},
                { label: '籍贯',name: 'nativePlace',width: 110},
                { label: '出生时间',name: 'birth',
                    width: 120,
                    formatter: $.jgrid.formatter.date,
                    formatoptions: {newformat: 'Y.m.d'}
                },
                { label: '年龄',name: 'birth', width: 55, formatter: $.jgrid.formatter.AGE, formatoptions: {newformat: '${_p_birthToDayFormat}'}},
                { label: '二级党组织名称',name: 'partyName',align:'left',width:450},
                { label: '党支部名称',name: 'branchName',align:'left',width:450},
                { label: '党籍状态',name: 'politicalStatus', formatter: function (cellvalue, options, rowObject) {
                        if (cellvalue)
                            return _cMap.MEMBER_POLITICAL_STATUS_MAP[cellvalue];
                        return "-";
                    }},
                { label: '组织关系转入时间',name: 'transferTime',
                    width: 130,
                    formatter: $.jgrid.formatter.date,
                    formatoptions: {newformat: 'Y.m.d'}
                    },
                { label: '提交书面申请书时间',name: 'applyTime',
                    width: 130,
                    formatter: $.jgrid.formatter.date,
                    formatoptions: {newformat: 'Y.m.d'}
                },
                { label: '确定为入党积极分子时间',name: 'activeTime',
                    width: 140,
                    formatter: $.jgrid.formatter.date,
                    formatoptions: {newformat: 'Y.m.d'}
                },
                { label: '确定为发展对象时间',name: 'candidateTime',
                    width: 130,
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
                { label: '备注1',name: 'remark1',width:150},
                { label: '备注2',name: 'remark2',width:150},
                { label: '备注3',name: 'remark3',width:150},
        ]
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid');
    $.initNavGrid("jqGrid", "jqGridPager");
    //$.register.user_select($('[data-rel="select2-ajax"]'));
    $('#searchForm [data-rel="select2"]').select2();
    //$('[data-rel="tooltip"]').tooltip();
    //$.register.date($('.date-picker'));
</script>