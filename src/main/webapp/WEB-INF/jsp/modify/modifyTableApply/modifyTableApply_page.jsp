<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <!-- PAGE CONTENT BEGINS -->
        <div id="body-content" class="myTableDiv"
             data-url-page="${ctx}/modifyTableApply_page"
             data-url-export="${ctx}/modifyTableApply_data"
             data-url-bd="${ctx}/modifyTableApply_batchDel"
             data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <c:set var="_query" value="${not empty param.userId}"/>
            <div class="tabbable">
                <jsp:include page="menu.jsp"/>
                <div class="tab-content">
                    <div id="home4" class="tab-pane in active">
                        <div class="jqgrid-vertical-offset buttons">
                            <c:if test="${cls==1}">
                                <shiro:hasRole name="cadreAdmin">
                                    <a class="jqBatchBtn btn btn-danger btn-sm"
                                       data-url="${ctx}/modifyTableApply_batchDel" data-title="删除申请记录"
                                       data-msg="确定删除这{0}条申请记录吗？"><i class="fa fa-trash"></i> 删除</a>
                                </shiro:hasRole>
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
                                        <input type="hidden" name="status" value="${status}">

                                        <div class="form-group">
                                            <label>账号</label>
                                            <div class="input-group">
                                                <select data-rel="select2-ajax" data-ajax-url="${ctx}/sysUser_selects"
                                                        name="userId" data-placeholder="请输入账号或姓名或学工号">
                                                    <option value="${sysUser.id}">${sysUser.realname}-${sysUser.code}</option>
                                                </select>
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
                        <%--<div class="space-4"></div>--%>
                        <table id="jqGrid" class="jqGrid"> </table>
                        <div id="jqGridPager"> </div>
                    </div>
                </div></div>
        </div>
        <div id="item-content">
        </div>
    </div>
</div>
<style>
    .avatar{
        width: 16px;
        cursor: pointer;
    }
</style>
<link rel="stylesheet" type="text/css" href="${ctx}/extend/js/fancybox/source/jquery.fancybox.css?v=2.1.5" media="screen" />
<link rel="stylesheet" href="${ctx}/extend/js/fancybox/source/helpers/jquery.fancybox-buttons.css?v=1.0.5" type="text/css" media="screen" />

<script type="text/javascript" src="${ctx}/extend/js/fancybox/source/jquery.fancybox.js?v=2.1.5"></script>
<script type="text/javascript" src="${ctx}/extend/js/fancybox/source/helpers/jquery.fancybox-buttons.js?v=1.0.5"></script>
<script type="text/javascript" src="${ctx}/extend/js/jquery.mousewheel.pack.js"></script>
<script>
    //序号、申请时间、工作证号、姓名、所在单位及职务、修改方式、申请内容、组织部审核
    $("#jqGrid").jqGrid({
        //forceFit:true,
        url: '${ctx}/modifyTableApply_data?callback=?&module=${module}&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            { label: '序号', name: 'id', width: 50,frozen:true },
            {label: '申请时间', width: 150, name: 'createTime'/*,formatter:'date',formatoptions: {newformat:'Y-m-d'}*/},
            { label: '工作证号', name: 'cadre.user.code', width: 80,frozen:true },
            { label: '姓名', name: 'cadre.user.realname', width: 80,frozen:true },
            { label: '所在单位及职务', name: 'cadre.title', width: 250},
            { label: '修改方式', name: 'type',formatter:function(cellvalue, options, rowObject){

                if(cellvalue==undefined) return ''
                return _cMap.MODIFY_TABLE_APPLY_TYPE_MAP[cellvalue];
            }},
            { label: '申请内容', name: 'content', width: 80,formatter:function(cellvalue, options, rowObject){
                return '<button href="javascript:;" class="openView btn btn-primary btn-xs" data-url="${ctx}/modifyTableApply_detail?module=${module}&applyId={0}">'.format(rowObject.id)
                        +'<i class="fa fa-search"></i> 详情</button>';
            }},
            <shiro:hasRole name="cadreAdmin">
            <c:if test="${cls==1}">
            { label: '组织部审核', name: '_check', formatter:function(cellvalue, options, rowObject){

                return '<button data-url="${ctx}/modifyTableApply_detail?module=${module}&type=check&applyId={0}" class="openView btn btn-success btn-xs">'
                                .format(rowObject.id)
                        + '<i class="fa fa-check"></i> 审核</button>'
            }},
            </c:if>
            </shiro:hasRole>

            { label: '审核状态', name: 'status'  , formatter:function(cellvalue, options, rowObject){
                if(cellvalue==undefined) return ''
                return _cMap.MODIFY_TABLE_APPLY_STATUS_MAP[cellvalue];
            }},
            <c:if test="${cls==1}">
            { label: '操作', name: '_check', formatter:function(cellvalue, options, rowObject){
                if(rowObject.userId!='${_user.id}') return ''
                return '<button data-url="${ctx}/user/modifyTableApply_back?id={0}" data-msg="确定撤销申请？" data-callback="_reload" class="confirm btn btn-danger btn-xs">'
                                .format(rowObject.id)
                        + '<i class="fa fa-times"></i> 撤销</button>'
            }},
            </c:if>
            <c:if test="${cls==2}">
            { label: '审核人', name: 'checkUser.realname'},
            { label: '审核时间', name: 'checkTime', width: 150},
            { label:'依据', name: 'checkReason', width: 250 },
            { label:'备注', name: 'checkRemark', width: 250 },
            </c:if>
            {hidden:true, name:'_status', formatter: function (cellvalue, options, rowObject) {
                if(rowObject.status==undefined) return -1;
                return rowObject.status;
            }}]
        , onSelectRow: function (id, status) {
            saveJqgridSelected("#" + this.id, id, status);
            //console.log(id)
            var ids = $(this).getGridParam("selarrrow");
            if (ids.length > 1) {
                $("#backBtn").prop("disabled", true);
            } else if (ids.length == 1) {
                var rowData = $(this).getRowData(ids[0]);
                $("#backBtn").prop("disabled", rowData._status!='${MODIFY_BASE_APPLY_STATUS_APPLY}')
            } else {
                $("#backBtn").prop("disabled", false);
            }
        }
    }).jqGrid("setFrozenColumns");

    $(window).triggerHandler('resize.jqGrid');
    _initNavGrid("jqGrid", "jqGridPager");
    $('#searchForm [data-rel="select2"]').select2();
    register_user_select($('#searchForm select[name=userId]'));
    $('[data-rel="tooltip"]').tooltip();

    function _reload(){
        $("#modal").modal('hide');
        $("#jqGrid").trigger("reloadGrid");
    }
    $(".various").fancybox({
        live:true,
        tpl:{error: '<p class="fancybox-error">该文件不是有效的图片格式，请下载后查看。</p>'},
        maxWidth	: 800,
        maxHeight	: 600,
        fitToView	: false,
        width		: '70%',
        height		: '70%',
        autoSize	: false,
        closeClick	: false,
        openEffect	: 'none',
        closeEffect	: 'none',
        loop:false,
        arrows:false,
        prevEffect		: 'none',
        nextEffect		: 'none',
        closeBtn		: true,
        beforeShow: function() {
            this.wrap.draggable();
        }
    });
</script>