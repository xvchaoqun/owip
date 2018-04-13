<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <!-- PAGE CONTENT BEGINS -->
        <div id="body-content" class="myTableDiv"
             data-url-page="${ctx}/abroad/passportApply"
             data-url-co="${ctx}/abroad/passportApply_changeOrder"
             data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <c:set var="_query" value="${not empty param.year ||not empty param.cadreId ||not empty param.classId
            ||not empty param.applyDate || not empty param.code || not empty param.sort}"/>
            <div class="tabbable">
                <ul class="nav nav-tabs padding-12 tab-color-blue background-blue">
                    <li  class="<c:if test="${status==ABROAD_PASSPORT_APPLY_STATUS_INIT}">active</c:if>">
                        <a href="javascript:;" class="loadPage" data-url="${ctx}/abroad/passportApply?status=${ABROAD_PASSPORT_APPLY_STATUS_INIT}"><i class="fa fa-circle-o"></i> 办理证件审批</a>
                    </li>
                    <li class="dropdown <c:if test="${status==1||status==3||status==4}">active</c:if>" >
                        <a data-toggle="dropdown" class="dropdown-toggle" href="javascript:;">
                            <i class="fa fa-recycle"></i> 批准办理新证件${status==1?"(未交证件) ":(status==3)?"(已交证件) ":(status==4)?"(作废) ":" "}
                            <i class="ace-icon fa fa-caret-down bigger-110 width-auto"></i>
                        </a>
                        <ul class="dropdown-menu dropdown-info" style="min-width: 230px">
                            <li>
                                <a href="javascript:;" class="loadPage" data-url="${ctx}/abroad/passportApply?status=1">未交证件</a>
                            </li>
                            <li>
                                <a href="javascript:;" class="loadPage" data-url="${ctx}/abroad/passportApply?status=3">已交证件</a>
                            </li>
                            <li>
                                <a href="javascript:;" class="loadPage" data-url="${ctx}/abroad/passportApply?status=4">作废</a>
                            </li>
                        </ul>
                    </li>
                    <li  class="<c:if test="${status==ABROAD_PASSPORT_APPLY_STATUS_NOT_PASS}">active</c:if>">
                        <a href="javascript:;" class="loadPage" data-url="${ctx}/abroad/passportApply?status=${ABROAD_PASSPORT_APPLY_STATUS_NOT_PASS}"><i class="fa fa-times"></i> 未批准办理新证件</a>
                    </li>
                    <li class="<c:if test="${status==-1}">active</c:if>">
                        <a href="javascript:;" class="loadPage" data-url="${ctx}/abroad/passportApply?status=-1"><i class="fa fa-trash"></i> 已删除</a>
                    </li>
                </ul>

                <div class="tab-content">
                    <div class="tab-pane in active">
                        <div class="jqgrid-vertical-offset buttons">
                            <c:if test="${status==ABROAD_PASSPORT_APPLY_STATUS_INIT}">
                            <button data-url="${ctx}/abroad/passportApply_au"
                                    class="popupBtn btn btn-primary btn-sm">
                                <i class="fa fa-plus"></i> 申请办理证件
                            </button>
                            </c:if>
                            <c:if test="${status==ABROAD_PASSPORT_APPLY_STATUS_PASS}">
                                <button class="jqOpenViewBtn btn btn-danger btn-sm"
                                        data-url="${ctx}/shortMsg_view" data-querystr="&type=passportApplyDraw">
                                    <i class="fa fa-hand-paper-o"></i> 催交证件
                                </button>
                                <button class="jqOpenViewBtn btn btn-success btn-sm"
                                        data-url="${ctx}/abroad/passport_au" data-id-name="applyId">
                                    <i class="fa fa-hand-paper-o"></i> 交证件
                                </button>
                                <a class="jqBatchBtn btn btn-warning btn-sm"
                                   data-url="${ctx}/abroad/passportApply_abolish" data-title="申请作废"
                                   data-msg="确定将这{0}个申请作废吗？">
                                    <i class="fa fa-recycle"></i> 作废
                                </a>
                                <a class="jqOpenViewBtn btn btn-primary btn-sm"
                                   data-url="${ctx}/abroad/passportApply_update_expectDate"
                                   data-grid-id="#jqGrid"><i class="fa fa-edit"></i>
                                    修改应交日期</a>
                            </c:if>
                            <c:if test="${status!=ABROAD_PASSPORT_APPLY_STATUS_INIT && status!=-1}">
                            <button class="jqOpenViewBtn btn btn-warning btn-sm"
                                    data-open-by="page"
                                    data-url="${ctx}/abroad/passportApply_check">
                                    <i class="fa fa-info-circle"></i> 申请表
                            </button>
                            </c:if>
                            <c:if test="${status>=0}">
                                <shiro:hasPermission name="passportApply:del">
                                    <a class="jqBatchBtn btn btn-danger btn-sm"
                                       data-url="${ctx}/abroad/passportApply_batchDel" data-title="删除办理证件申请"
                                       data-msg="确定删除这{0}条申请记录吗？"><i class="fa fa-trash"></i> 删除</a>
                                </shiro:hasPermission>
                            </c:if>
                            <c:if test="${status==-1}">
                                <shiro:hasPermission name="passportApply:del">
                                    <a class="jqBatchBtn btn btn-success btn-sm"
                                       data-url="${ctx}/abroad/passportApply_batchUnDel" data-title="找回已删除办理证件申请"
                                       data-msg="确定恢复这{0}条申请记录吗？"><i class="fa fa-reply"></i> 恢复申请</a>
                                </shiro:hasPermission>
                                <shiro:hasPermission name="passportApply:del">
                                    <a class="jqBatchBtn btn btn-danger btn-sm"
                                       data-url="${ctx}/abroad/passportApply_doBatchDel" data-title="删除办理证件申请"
                                       data-msg="确定删除这{0}条申请记录吗（<span class='text-danger'>删除后不可以恢复，且办理的证件将从证件库中删除</span>）？"><i class="fa fa-times"></i> 完全删除</a>
                                </shiro:hasPermission>
                            </c:if>
                            </div>
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
                                                            <input type="hidden" name="status" value="${status}">
                                                            <select data-rel="select2-ajax" data-ajax-url="${ctx}/cadre_selects"
                                                                    name="cadreId" data-placeholder="请输入账号或姓名或学工号">
                                                                <option value="${cadre.id}">${sysUser.realname}-${sysUser.code}</option>
                                                            </select>
                                                        </div>
                                                </div>
                                                <div class="form-group">
                                                    <label>证件名称</label>
                                                        <select data-rel="select2" name="classId" data-placeholder="请选择证件名称">
                                                            <option></option>
                                                            <c:import url="/metaTypes?__code=mc_passport_type"/>
                                                        </select>
                                                        <script type="text/javascript">
                                                            $("#searchForm select[name=classId]").val(${param.classId});
                                                        </script>
                                                </div>
                                                <div class="form-group">
                                                    <label>年份</label>
                                                        <div class="input-group" style="width: 120px">
                                                            <input class="form-control date-picker" name="year" type="text"
                                                                   data-date-format="yyyy" placeholder="年份" data-date-min-view-mode="2" value="${param.year}" />
                                                            <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                                                        </div>
                                                </div>
                                        <div class="clearfix form-actions center">
                                            <a class="jqSearchBtn btn btn-default btn-sm"><i class="fa fa-search"></i> 查找</a>

                                            <c:if test="${_query || not empty param.sort}">&nbsp;
                                                <button type="button" class="resetBtn btn btn-warning btn-sm" data-querystr="status=${status}">
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
                </div></div></div>
        <div id="body-content-view">
        </div>
    </div>
</div>
<script>
    $("#jqGrid").jqGrid({
        //forceFit:true,
        url: '${ctx}/abroad/passportApply_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            { label: '申请日期', name: 'applyDate',frozen:true, formatter: 'date', formatoptions: {newformat: 'Y-m-d'}},
            { label: '工作证号', name: 'applyUser.code',frozen:true},
            { label: '姓名',name: 'applyUser.realname', width: 75, formatter:function(cellvalue, options, rowObject){
                return $.cadre(rowObject.cadre.id, cellvalue);
            },frozen:true  },
            { label: '所在单位及职务',  name: 'cadre.title', width: 250 },
            { label: '申办证件名称', name: 'passportClass.name', width: 250 },
            <c:if test="${status==ABROAD_PASSPORT_APPLY_STATUS_INIT}">
            { label: '审批', name: 'statusName', width: 100, formatter:function(cellvalue, options, rowObject){
                var html = '<button class="jqOpenViewBtn btn btn-success btn-xs" data-open-by="page"'
                +'data-url="${ctx}/abroad/passportApply_check?id={0}"><i class="fa fa-check-square-o"></i> 审批</button>';
                html.format(rowObject.id);

                return html;
            }},
            </c:if>
            <c:if test="${status!=ABROAD_PASSPORT_APPLY_STATUS_INIT}">
            { label: '审批人', name: 'approvalUser.realname', formatter:function(cellvalue, options, rowObject){
                if(rowObject.approvalUser==undefined) return ''
                return '<a href="javascript:;" class="openView" data-url="${ctx}/sysUser_view?userId={0}">{1}</a>'
                        .format(rowObject.approvalUser.id, cellvalue);
            }},
            { label:'审批日期',name: 'approveTime', formatter: 'date', formatoptions: {newformat: 'Y-m-d'}},
                </c:if>
            <c:if test="${status==ABROAD_PASSPORT_APPLY_STATUS_PASS ||status==3}">
            { label:'应交日期', name: 'expectDate',cellattr:function(rowId, val, rowObject, cm, rdata) {
                <c:if test="${status==ABROAD_PASSPORT_APPLY_STATUS_PASS}">
                var expectDate = rowObject.expectDate;
                if(expectDate<=new Date().format('yyyy-MM-dd'))
                    return "class='danger'";
                </c:if>
            }, formatter: 'date', formatoptions: {newformat: 'Y-m-d'}},
            </c:if>
            <c:if test="${status==3}">
            { label:'实交日期', name: 'handleDate', formatter: 'date', formatoptions: {newformat: 'Y-m-d'}},
            { label:'证件号码', name: 'code'},
            { label:'接收人', name: 'handleUser.realname'},
            </c:if>
            <c:if test="${status==ABROAD_PASSPORT_APPLY_STATUS_NOT_PASS}">
                { label:'未批准原因', name: 'handleDate', width: 200 }
            </c:if>
        ]}).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid');
    $.initNavGrid("jqGrid", "jqGridPager");
    /*$(".printBtn").click(function(){
        $.print("${ctx}/report/passportApply?id="+ $(this).data("id"));
    });*/
    $('#searchForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
    $.register.date($('.date-picker'))
    $.register.user_select($('[data-rel="select2-ajax"]'));
</script>