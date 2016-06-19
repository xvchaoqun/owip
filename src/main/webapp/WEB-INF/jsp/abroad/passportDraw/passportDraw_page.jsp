<%@ page import="java.util.Date" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <!-- PAGE CONTENT BEGINS -->
        <div id="body-content">
                <div class="tabbable">
                    <ul class="nav nav-tabs padding-12 tab-color-blue background-blue">
                        <li  class="<c:if test="${type==PASSPORT_DRAW_TYPE_SELF}">active</c:if>">
                            <a href="?type=${PASSPORT_DRAW_TYPE_SELF}"><i class="fa fa-credit-card"></i> 因私出国（境）</a>
                        </li>
                        <li  class="<c:if test="${type==PASSPORT_DRAW_TYPE_TW}">active</c:if>">
                            <a href="?type=${PASSPORT_DRAW_TYPE_TW}"><i class="fa fa-credit-card"></i> 因公赴台、长期因公出国</a>
                        </li>
                        <li  class="<c:if test="${type==PASSPORT_DRAW_TYPE_OTHER}">active</c:if>">
                            <a href="?type=${PASSPORT_DRAW_TYPE_OTHER}"><i class="fa fa-credit-card"></i> 处理其他事务</a>
                        </li>
                    </ul>

                    <div class="tab-content">
                        <div id="home4" class="tab-pane in active">
                            <div class="jqgrid-vertical-offset buttons">
                                <button data-url="${ctx}/passportDraw_view" data-open-by="page"
                                        class="jqOpenViewBtn btn btn-success btn-sm">
                                    <i class="fa fa-info-circle"></i> 详情
                                </button>
                               <c:if test="${type==PASSPORT_DRAW_TYPE_SELF}">
                                <button class="printProofBtn btn btn-warning btn-sm">
                                    <i class="fa fa-print"></i> 打印在职证明
                                </button>
                               </c:if>
                            </div>
        <div class="myTableDiv"
             data-url-au="${ctx}/passportDraw_au"
             data-url-page="${ctx}/passportDraw_page"
             data-url-del="${ctx}/passportDraw_del"
             data-url-bd="${ctx}/passportDraw_batchDel"
             data-url-co="${ctx}/passportDraw_changeOrder"
             data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <c:set var="_query" value="${not empty param.cadreId ||not empty param._applyDate || not empty param.code}"/>
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
                                                <input type="hidden" name="type" value="${type}">
                                                <select data-rel="select2-ajax" data-ajax-url="${ctx}/cadre_selects"
                                                        name="cadreId" data-placeholder="请输入账号或姓名或学工号">
                                                    <option value="${cadre.id}">${sysUser.realname}-${sysUser.code}</option>
                                                </select>
                                            </div>
                                    </div>
                                    <div class="form-group">
                                        <label>申请日期</label>
                                            <div class="input-group tooltip-success" data-rel="tooltip" title="申请日期范围">
                                            <span class="input-group-addon">
                                                <i class="fa fa-calendar bigger-110"></i>
                                            </span>
                                                <input placeholder="请选择申请日期范围" data-rel="date-range-picker" class="form-control date-range-picker" type="text" name="_applyDate" value="${param._applyDate}"/>
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
    </div>
                    </div></div></div>
    <div id="item-content">
    </div>
    </div>
</div>
<jsp:include page="/WEB-INF/jsp/common/daterangerpicker.jsp"/>

<script>

    $(".printProofBtn").click(function(){
        var grid = $("#jqGrid");
        var ids  = grid.getGridParam("selarrrow");

        if(ids.length==0){
            SysMsg.warning("请选择行", "提示");
            return ;
        }
        printWindow("${ctx}/report/abroad_draw_proof?ids[]="+ ids)
    });

    $("#jqGrid").jqGrid({
        url: '${ctx}/passportDraw_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            { label: '编号', align:'center', name: 'id', width: 50 , formatter:function(cellvalue, options, rowObject){
                return 'A{0}'.format(cellvalue);
            },frozen:true},
            { label: '申请日期', align:'center', name: 'applyDate', width: 100,frozen:true },
            <c:if test="${type==PASSPORT_DRAW_TYPE_TW}">
            {
                label: '申请类型', name: 'type', formatter: function (cellvalue, options, rowObject) {
                return _cMap.PASSPORT_DRAW_TYPE_MAP[cellvalue];
            }, width: 100, frozen: true
            },
            </c:if>
            { label: '工作证号', align:'center', name: 'user.code', width: 80,frozen:true },
            { label: '姓名',align:'center', name: 'user.realname', width: 75, formatter:function(cellvalue, options, rowObject){
                return '<a href="javascript:;" class="openView" data-url="${ctx}/cadre_view?id={0}">{1}</a>'
                        .format(rowObject.cadre.id, cellvalue);
            },frozen:true  },
            { label: '所在单位及职务',  name: 'cadre.title', width: 250 },
            { label: '申请领取证件名称', align:'center', name: 'passportClass.name', width: 180 },
            <c:if test="${type==PASSPORT_DRAW_TYPE_SELF}">
            { label: '因私出国（境）行程', align:'center', name: 'applyId', width: 150 , formatter:function(cellvalue, options, rowObject){
                return '<a class="openView" href="javascript:;" ' +
                        'data-url="${ctx}/applySelf_view?id={0}">S{1}</a>'.format(cellvalue,cellvalue);
            } },
            </c:if>
            <c:if test="${type==PASSPORT_DRAW_TYPE_TW || type==PASSPORT_DRAW_TYPE_OTHER}">
            {label: '${type==PASSPORT_DRAW_TYPE_TW?"出行时间":"使用时间"}', name: 'startDate', width: 100},
            {label: '${type==PASSPORT_DRAW_TYPE_TW?"回国时间":"归还时间"}', name: 'endDate', width: 100},
            {
                label: '${type==PASSPORT_DRAW_TYPE_TW?"出行天数":"使用天数"}',
                name: 'day',
                width: 80,
                formatter: function (cellvalue, options, rowObject) {
                    return DateDiff(rowObject.startDate, rowObject.endDate);
                }
            },
            {
                label: '${type==PASSPORT_DRAW_TYPE_TW?"因公事由":"事由"}',
                name: 'reason',
                width: 200,
                formatter: function (cellvalue, options, rowObject) {
                    return cellvalue.replace(/\+\+\+/g, ',');
                }
            },
            <c:if test="${type==PASSPORT_DRAW_TYPE_TW}">
            {label: '费用来源', name: 'costSource', width: 100},
            </c:if>
            {label: '${type==PASSPORT_DRAW_TYPE_TW?"批件":"说明材料"}', name: 'files', width: 150, formatter: function (cellvalue, options, rowObject) {

                var filesArray = []
                for(var i in rowObject.files){
                    var file = rowObject.files[i];
                    filesArray.push('<a href="${ctx}/attach/passportDrawFile?id={0}">${type==PASSPORT_DRAW_TYPE_TW?"批件":"材料"}{1}</a>'.format(file.id, i+1));
                }
                return filesArray.join("，");
            }},
            </c:if>
            <c:if test="${type!=PASSPORT_DRAW_TYPE_OTHER}">
            { label: '是否签注', align:'center', name: 'needSign', width: 80,formatter:function(cellvalue, options, rowObject){
                if(rowObject.passportClass.code=='mt_passport_normal'){
                    return '-';
                }
                return (cellvalue)?"是":"否";
            } },
            { label:'签注申请表', align:'center', width: 100,formatter:function(cellvalue, options, rowObject){
                if(rowObject.passportClass.code=='mt_passport_normal' || !rowObject.needSign){
                    return '-';
                }
                return '<a href="${ctx}/report/passportSign?id={0}" target="_blank">签注申请表 </a>'.format(rowObject.id);
            }},
            { label:'打印签注申请表', align:'center', width: 150,formatter:function(cellvalue, options, rowObject){
                if(rowObject.passportClass.code=='mt_passport_normal' || !rowObject.needSign){
                    return '-';
                }
                return '<button data-id="{0}" class="printBtn btn btn-info btn-mini btn-xs"><i class="fa fa-print"></i> 打印签注申请表</button>'
                        .format(rowObject.id);
            }},
            </c:if>
            { label:'组织部审批', align:'center', width: 100,formatter:function(cellvalue, options, rowObject) {
                if(rowObject.status=='${PASSPORT_DRAW_STATUS_INIT}')
                    return '<button data-url="${ctx}/passportDraw_check?id={0}"  class="openView btn btn-success btn-mini btn-xs">'
                                    .format(rowObject.id)
                            +'<i class="fa fa-check"></i> 组织部审批</button>';
                else
                     return rowObject.statusName;
            }},
            { label:'短信通知', align:'center', width: 100 ,formatter:function(cellvalue, options, rowObject) {
                if(rowObject.status=='${PASSPORT_DRAW_STATUS_INIT}'){
                    return '-';
                }
                return '<button data-id="{0}" class="shortMsgBtn btn btn-warning btn-mini btn-xs">'
                                .format(rowObject.id)
                        +'<i class="fa fa-info"></i> 短信通知</button>';
            }},
            { label:'领取证件', align:'center', width: 100,formatter:function(cellvalue, options, rowObject) {
                if(rowObject.status!='${PASSPORT_DRAW_STATUS_PASS}'){
                    return '-';
                }
                if(rowObject.drawStatus=='${PASSPORT_DRAW_DRAW_STATUS_UNDRAW}'){
                    return '<button data-url="${ctx}/passportDraw_draw?id={0}" class="openView btn btn-info btn-mini btn-xs">'
                                    .format(rowObject.id)
                            +'<i class="fa fa-hand-lizard-o"></i> 领取证件</button>'
                }
                return rowObject.drawStatusName;
            }},
            { label:'应交组织部日期', align:'center', name: 'returnDate', width: 130 },
            { label:'催交证件', align:'center', name: 'passportType', width: 100 ,formatter:function(cellvalue, options, rowObject) {
                if(rowObject.drawStatus!='${PASSPORT_DRAW_DRAW_STATUS_DRAW}' || rowObject.returnDateNotNow){
                    return '-';
                }
                return '<button data-id="{0}" class="returnMsgBtn btn btn-danger btn-mini btn-xs">'
                                .format(rowObject.id)
                        +'<i class="fa fa-hand-paper-o"></i> 催交证件</button>';
            }},
            { label:'归还证件', align:'center', name: 'lostTime', width: 100 ,formatter:function(cellvalue, options, rowObject) {
                if(rowObject.drawStatus!='${PASSPORT_DRAW_DRAW_STATUS_DRAW}'){
                    return '-';
                }
                return '<button data-url="${ctx}/passportDraw_return?id={0}" class="openView btn btn-default btn-mini btn-xs">'
                                .format(rowObject.id)
                        +'<i class="fa fa-reply"></i> 归还证件</button>'
            }},
            { label:'实交组织部日期', align:'center', name:'realReturnDate',width: 130}
        ]
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid');

    $(document).on('click',".returnMsgBtn",function(){
        var id = $(this).data("id");
        loadModal("${ctx}/shortMsg_view?id={0}&type=passportDrawReturn".format(id));
    });

    $(document).on('click',".shortMsgBtn",function(){
        var id = $(this).data("id");
        loadModal("${ctx}/shortMsg_view?id={0}&type=passportDrawApply".format(id));
    });

    $(document).on('click',".printBtn",function(){
        printWindow("${ctx}/report/passportSign?id="+ $(this).data("id"));
    });

    $('#searchForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
    register_user_select($('[data-rel="select2-ajax"]'));
</script>