<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <!-- PAGE CONTENT BEGINS -->
        <div id="body-content" class="myTableDiv"
             data-url-page="${ctx}/passportApply_page"
             data-url-co="${ctx}/passportApply_changeOrder"
             data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <c:set var="_query" value="${not empty param.year ||not empty param.cadreId ||not empty param.classId
            ||not empty param.applyDate || not empty param.code || not empty param.sort}"/>
            <div class="tabbable">
                <ul class="nav nav-tabs padding-12 tab-color-blue background-blue">
                    <li  class="<c:if test="${status==0}">active</c:if>">
                        <a href="?status=0"><i class="fa fa-circle-o"></i> 办理证件审批</a>
                    </li>
                    <li class="dropdown <c:if test="${status==1||status==3||status==4}">active</c:if>" >
                        <a data-toggle="dropdown" class="dropdown-toggle" href="#">
                            <i class="fa fa-recycle"></i> 批准办理新证件${status==1?"(未交证件) ":(status==3)?"(已交证件) ":(status==4)?"(作废) ":" "}
                            <i class="ace-icon fa fa-caret-down bigger-110 width-auto"></i>
                        </a>
                        <ul class="dropdown-menu dropdown-info" style="min-width: 230px">
                            <li>
                                <a href="?status=1">未交证件</a>
                            </li>
                            <li>
                                <a   href="?status=3">已交证件</a>
                            </li>
                            <li>
                                <a   href="?status=4">作废</a>
                            </li>
                        </ul>
                    </li>
                    <li  class="<c:if test="${status==2}">active</c:if>">
                        <a href="?status=2"><i class="fa fa-times"></i> 未批准办理新证件</a>
                    </li>

                </ul>

                <div class="tab-content">
                    <div id="home4" class="tab-pane in active">
                        <div class="jqgrid-vertical-offset buttons">
                            <c:if test="${status==PASSPORT_APPLY_STATUS_PASS}">
                                <button class="jqOpenViewBtn btn btn-danger btn-sm"
                                        data-url="${ctx}/shortMsg_view" data-querystr="&type=passportApplyDraw">
                                    <i class="fa fa-hand-paper-o"></i> 催交证件
                                </button>
                                <button class="jqOpenViewBtn btn btn-success btn-sm"
                                        data-url="${ctx}/passport_au" data-id-name="applyId">
                                    <i class="fa fa-hand-paper-o"></i> 交证件
                                </button>
                                <a class="jqBatchBtn btn btn-warning btn-sm"
                                   data-url="${ctx}/passportApply_abolish" data-title="申请作废"
                                   data-msg="确定将这{0}个申请作废吗？">
                                    <i class="fa fa-recycle"></i> 作废
                                </a>
                            </c:if>
                            <c:if test="${status!=PASSPORT_APPLY_STATUS_INIT}">
                            <button class="jqOpenViewBtn btn btn-warning btn-sm"
                                    data-open-by="page"
                                    data-url="${ctx}/passportApply_check?id=${passportApply.id}">
                                    <i class="fa fa-info-circle"></i> 申请表
                            </button>
                            </c:if>
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
        <div id="item-content">
        </div>
    </div>
</div>
<script>
    $("#jqGrid").jqGrid({
        //forceFit:true,
        url: '${ctx}/passportApply_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            { label: '申请日期', name: 'applyDate',frozen:true},
            { label: '工作证号', name: 'applyUser.code',frozen:true},
            { label: '姓名',name: 'applyUser.realname', width: 75, formatter:function(cellvalue, options, rowObject){
                return '<a href="javascript:;" class="openView" data-url="${ctx}/cadre_view?id={0}">{1}</a>'
                        .format(rowObject.cadre.id, cellvalue);
            },frozen:true  },
            { label: '所在单位及职务',  name: 'cadre.title', width: 250 },
            { label: '申办证件名称', name: 'passportClass.name', width: 250 },
            <c:if test="${status==0}">
            { label: '审批', name: 'statusName', width: 100, formatter:function(cellvalue, options, rowObject){
                var html = '<button class="jqOpenViewBtn btn btn-success btn-xs" data-open-by="page"'
                +'data-url="${ctx}/passportApply_check?id={0}"><i class="fa fa-check-square-o"></i> 审批</button>';
                html.format(rowObject.id);

                return html;
            }},
            </c:if>
            <c:if test="${status!=0}">
            { label: '审批人', name: 'approvalUser.realname', formatter:function(cellvalue, options, rowObject){
                return '<a href="javascript:;" class="openView" data-url="${ctx}/sysUser_view?userId={0}">{1}</a>'
                        .format(rowObject.approvalUser.id, cellvalue);
            }},
            { label:'审批日期', align:'center',name: 'approveTime'},
                </c:if>
            <c:if test="${status==1 ||status==3}">
            { label:'应交日期', name: 'expectDate',cellattr:function(rowId, val, rowObject, cm, rdata) {
                <c:if test="${status==1}">
                var expectDate = rowObject.expectDate;
                if(expectDate<=new Date().format('yyyy-MM-dd'))
                    return "class='danger'";
                </c:if>
            }},
            </c:if>
            <c:if test="${status==3}">
            { label:'实交日期', name: 'handleDate'},
            { label:'证件号码', name: 'code'},
            { label:'接收人', name: 'handleUser.realname'},
            </c:if>
            <c:if test="${status==2}">
                { label:'未批准原因', name: 'handleDate', width: 200 }
            </c:if>
        ]}).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid');
    _initNavGrid("jqGrid", "jqGridPager");
    /*$(".printBtn").click(function(){
        printWindow("${ctx}/report/passportApply?id="+ $(this).data("id"));
    });*/
    $('#searchForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
    $('.date-picker').datepicker({
        language:"zh-CN",
        autoclose: true,
        todayHighlight: true
    });
    register_user_select($('[data-rel="select2-ajax"]'));
</script>