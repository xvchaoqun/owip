<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <div id="body-content">

            <div class="myTableDiv"
                 data-url-page="${ctx}/memberApplyLog"
                 data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
                <c:set var="_query" value="${not empty param.userId ||not empty param.stage
            ||not empty param.partyId ||not empty param.branchId || not empty param.code || not empty param.sort}"/>
            <div class="widget-box transparent">
                <div class="widget-header">
                    <div class="widget-toolbar no-border">
                        <jsp:include page="menu.jsp"/>
                    </div>
                </div>
                <div class="widget-body">
                    <div class="widget-main padding-12 no-padding-left no-padding-right">
                        <div class="tab-content padding-4">
                            <div class="tab-pane in active">
                                <div class="jqgrid-vertical-offset widget-box ${_query?'':'collapsed'} hidden-sm hidden-xs">
                                    <div class="widget-header">
                                        <h4 class="widget-title">搜索</h4>

                                        <div class="widget-toolbar">
                                            <a href="javascript:;" data-action="collapse">
                                                <i class="ace-icon fa fa-chevron-${_query?'up':'down'}"></i>
                                            </a>
                                        </div>
                                    </div>
                                    <div class="widget-body">
                                        <div class="widget-main no-padding">
                                            <form class="form-inline search-form" id="searchForm">
                                                        <div class="form-group">
                                                            <label>姓名</label>
                                                            <div class="input-group">
                                                                <select data-rel="select2-ajax" data-ajax-url="${ctx}/sysUser_selects"
                                                                        name="userId" data-placeholder="请输入账号或姓名或学工号">
                                                                    <option value="${sysUser.id}">${sysUser.realname}-${sysUser.code}</option>
                                                                </select>
                                                            </div>
                                                        </div>
                                                        <div class="form-group">
                                                            <label>阶段</label>

                                                                <div class="input-group">
                                                                    <select class="form-control" name="stage" data-rel="select2" data-placeholder="请选择阶段">
                                                                        <option></option>
                                                                        <c:forEach items="${APPLY_STAGE_MAP}" var="applyStageType">
                                                                            <option value="${applyStageType.value}">${applyStageType.value}</option>
                                                                        </c:forEach>
                                                                    </select>
                                                                    <script>
                                                                        $("#searchForm select[name=stage]").val('${param.stage}');
                                                                    </script>
                                                                </div>
                                                        </div>

                                                        <div class="form-group">
                                                            <label>分党委</label>


                                                                <select class="form-control" data-rel="select2-ajax"
                                                                        data-ajax-url="${ctx}/party_selects"
                                                                        name="partyId" data-placeholder="请选择分党委">
                                                                    <option value="${party.id}">${party.name}</option>
                                                                </select>

                                                        </div>

                                                        <div class="form-group" style="${(empty branch)?'display: none':''}" id="branchDiv">
                                                            <label >党支部</label>
                                                                <select class="form-control" data-rel="select2-ajax"
                                                                        data-ajax-url="${ctx}/branch_selects"
                                                                        name="branchId" data-placeholder="请选择党支部">
                                                                    <option value="${branch.id}">${branch.name}</option>
                                                                </select>
                                                        </div>
                                                    <script>
                                                        $.register.party_branch_select($("#searchForm"), "branchDiv",
                                                                '${cm:getMetaTypeByCode("mt_direct_branch").id}', "${party.id}", "${party.classId}");
                                                    </script>
                                                <div class="clearfix form-actions center">
                                                    <a class="jqSearchBtn btn btn-default btn-sm"><i class="fa fa-search"></i> 查找</a>

                                                    <c:if test="${_query || not empty param.sort}">&nbsp;
                                                        <button type="button" class="resetBtn btn btn-warning btn-sm">
                                                            <i class="fa fa-reply"></i> 重置
                                                        </button>
                                                    </c:if>
                                                </div>
                                            </form>
                                        </div>
                                    </div>
                                </div>
                                <div class="space-4"></div>
                                <table id="jqGrid" class="jqGrid table-striped"> </table>
                                <div id="jqGridPager"> </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
                </div>
        </div>
        <div id="body-content-view">

        </div>
    </div>
</div>
<script>
    $("#jqGrid").jqGrid({
        multiselect:false,
        url: "${ctx}/applyApprovalLog_data?callback=?&type=${APPLY_APPROVAL_LOG_TYPE_MEMBER_APPLY}&${cm:encodeQueryString(pageContext.request.queryString)}",
        colModel: [
            {label: '${type==1?"学生证号":"工作证号"}', name: 'applyUser.code', width: 150, frozen:true},
            {label: '姓名', name: 'applyUser.realname', width: 100, frozen:true},
            { label: '阶段',  name: 'stage', width: 200 },
            { label: '审核时间',  name: 'createTime', width: 200 },
            { label: '审核人', name: 'user.realname', width: 150 },
            { label:'审核结果',  name: 'status', width: 100, formatter:function(cellvalue, options, rowObject){
                return cellvalue==0?"未通过":"通过";
            } },
            { label:'备注',  name: 'remark', width: 450 },
            { label:'IP',  name: 'ip', width: 150 }
        ],
        gridComplete:function(){
            $(window).triggerHandler('resize.jqGrid');
        }
    });

    $('#searchForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();

    $.register.user_select($('#searchForm select[name=userId]'));
</script>