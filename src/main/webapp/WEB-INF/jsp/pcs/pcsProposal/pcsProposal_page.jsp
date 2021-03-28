<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<%@ include file="../constants.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <c:if test="${proposalClosed}">
            <div class="well">
            提交提案截止时间为${cm:formatDate(_pcsConfig.proposalSubmitTime, "yyyy-MM-dd HH:mm")}
            </div>
        </c:if>
        <c:if test="${supportClosed}">
        <div class="well">
            征集附议人截止时间为${cm:formatDate(_pcsConfig.proposalSupportTime, "yyyy-MM-dd HH:mm")}
            </div>
        </c:if>
        <c:if test="${!proposalClosed  && !supportClosed}">

        <div id="body-content" class="myTableDiv"
                 data-url-page="${ctx}/pcs/pcsProposal"
                 data-url-export="${ctx}/pcs/pcsProposal_data"
                 data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <c:set var="_query" value="${not empty param.code ||not empty param.userId
            ||not empty param.name ||not empty param.keywords ||not empty param.types || not empty param.code || not empty param.sort}"/>

            <c:if test="${cls==1}">
            <jsp:include page="pr_menu.jsp"/>
            </c:if>
            <c:if test="${cls==8}">
            <%--<jsp:include page="ow_menu.jsp"/>--%>
            </c:if>
            <div class="space-4"></div>
            <div class="jqgrid-vertical-offset buttons">

                <c:if test="${cls==1 && module==1}">
                    <a class="popupBtn btn btn-warning btn-sm" data-width="900"
                       data-url="${ctx}/pdf_preview?code=af_pcs_proposal_info&np=1&nd=1"><i class="fa fa-info-circle"></i> 提案通知</a>

                    <a class="popupBtn btn btn-info btn-sm"
                       data-width="500"
                       data-url="${ctx}/hf_content?code=hf_pcs_qr_code">
                        <i class="fa fa-qrcode"></i> 党代表交流群</a>

                    <a class="openView btn btn-success btn-sm"
                       data-url="${ctx}/pcs/pcsProposal_au"><i class="fa fa-plus"></i> 新建</a>
                    <button id="editBtn" class="jqOpenViewBtn btn btn-primary btn-sm"
                       data-url="${ctx}/pcs/pcsProposal_au"
                       data-grid-id="#jqGrid"
                       data-open-by="page"
                       ><i class="fa fa-edit"></i>
                        修改</button>
                    <button id="delBtn" data-url="${ctx}/pcs/pcsProposal_batchDel"
                            data-title="删除"
                            data-msg="确定删除这{0}条数据？<br/>（删除后无法恢复，请谨慎操作！！）"
                            data-grid-id="#jqGrid"
                            class="jqBatchBtn btn btn-danger btn-sm">
                        <i class="fa fa-trash"></i> 删除
                    </button>
                </c:if>
                <%--
                <a class="jqExportBtn btn btn-success btn-sm tooltip-success"
                   data-rel="tooltip" data-placement="top" title="导出选中记录或所有搜索结果">
                    <i class="fa fa-download"></i> 导出</a>--%>
<c:if test="${cls==8}">
                <button data-url="/report/preview?url=${ctx}/report/pcsProposal&format=image"
                        data-target="_blank"
                        class="jqLinkBtn btn btn-success btn-sm"><i class="fa fa-search"></i> 预览</button>

                <button data-url="/report/preview?url=${ctx}/report/pcsProposal&type=1"
                        class="jqLinkBtn btn btn-warning btn-sm"><i class="fa fa-file-pdf-o"></i> 导出PDF</button>

                <button data-url="/report/preview?url=${ctx}/report/pcsProposal&type=2"
                        class="jqLinkBtn btn btn-primary btn-sm"><i class="fa fa-file-word-o"></i> 导出WORD</button>
</c:if>
            </div>
            <c:if test="${cls!=1}">
            <div class="jqgrid-vertical-offset widget-box ${_query?'':'collapsed'} hidden-sm hidden-xs">
                <div class="widget-header">
                    <h4 class="widget-title">搜索</h4><span class="widget-note">${note_searchbar}</span>

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
                            <input type="hidden" name="displayInvite" value="${param.displayInvite}"/>
                            <input type="hidden" name="orderType">
                        <div class="form-group">
                            <label>提案编号</label>
                            <input class="form-control search-query" name="code" type="text" value="${param.code}"
                                   placeholder="请输入提案编号">
                        </div>
                        <div class="form-group">
                            <label>提案人</label>
                            <select data-rel="select2-ajax" data-ajax-url="${ctx}/pcs/pcsProposal_pr_selects"
                                    name="userId" data-placeholder="请输入账号或姓名或工号">
                                <option value="${sysUser.id}">${sysUser.realname}-${sysUser.code}</option>
                            </select>
                        </div>
                        <div class="form-group">
                            <label>标题</label>
                            <input class="form-control search-query" name="name" type="text" value="${param.name}"
                                   placeholder="请输入标题">
                        </div>
                        <div class="form-group">
                            <label>关键字</label>
                            <input class="form-control search-query" name="keywords" type="text" value="${param.keywords}"
                                   placeholder="请输入关键字">
                        </div>
                        <div class="form-group">
                            <label>提案类型</label>
                            <select class="multiselect" multiple="" name="types">
                                <c:forEach items="${prTypes}" var="entry">
                                    <option value="${entry.id}">${entry.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                            <div class="clearfix form-actions center">
                                <a class="jqSearchBtn btn btn-default btn-sm"><i class="fa fa-search"></i> 查找</a>

                                <c:if test="${_query}">&nbsp;
                                    <button type="button"
                                            data-querystr="cls=${cls}&module=${module}"
                                            class="reloadBtn btn btn-warning btn-sm">
                                        <i class="fa fa-reply"></i> 重置
                                    </button>
                                </c:if>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
            </c:if>

            <c:if test="${cls!=1}">
            <span class="label label-warning"
                  style="height: 30px;font-size: 16px;font-weight: bolder;visibility: ${cls==2 || cls==3?'visible':'hidden'}">
                    <input style="padding-bottom: 5px;width: 15px;height: 15px" type="checkbox" id="displayInvite" ${param.displayInvite==1?'checked':''} value="1"> 仅显示被邀请附议
            </span>

            <div class="pull-right" style="line-height: 40px;">
                <input class="orderCheckbox" ${param.orderType!=1?"checked":""} type="checkbox" value="0"> 默认排序
                <input class="orderCheckbox" ${param.orderType==1?"checked":""} type="checkbox" value="1"> 按附议人多少排序
            </div>
                </c:if>
            <div class="space-4"></div>
            <div class="${!(cls==1 && module==1)?'rownumbers':''}">
            <table id="jqGrid" class="jqGrid table-striped"></table>
            <div id="jqGridPager"></div>
            </div>
        </div>
        <div id="body-content-view"></div>
        </c:if>
    </div>
</div>
<c:if test="${!proposalClosed  && !supportClosed}">
<script>

    $(".orderCheckbox").click(function(){
        $("#searchForm input[name=orderType]").val($(this).val());
        $("#searchForm .jqSearchBtn").click();
    })

    $("#displayInvite").click(function(){

        $("#searchForm input[name=displayInvite]").val($(this).prop("checked")?1:0);
        $("#searchForm .jqSearchBtn").click();
    })
    $.register.fancybox();
    $.register.user_select($("#searchForm select[name=userId]"))
    $.register.multiselect($('#searchForm select[name=types]'), ${cm:toJSONArray(selectTypes)});

    $("#jqGrid").jqGrid({
        <c:if test="${!(cls==1 && module==1)}">
        rownumbers: true,
        </c:if>
        <c:if test="${cls==2 || cls==3}">
        multiselect: false,
        </c:if>
        url: '${ctx}/pcs/pcsProposal_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            <c:if test="${cls==2}">
            { label: '附议',name: '_check', formatter: function (cellvalue, options, rowObject) {

                if(rowObject.userId=='${_user.id}') return '本人提案'
                var seconderIds = $.trim(rowObject.seconderIds);
                //console.log(seconderIds.split(","))
                if(seconderIds.split(",").indexOf('${_user.id}')>-1) return "已附议";

                return ('<button class="openView btn btn-primary btn-xs" ' +
                'data-url="${ctx}/pcs/pcsProposal_check?id={0}&type=2"><i class="fa fa-handshake-o"></i> 附议</button>')
                        .format(rowObject.id);
            }, frozen:true},
            </c:if>
            { label: '提案编号',name: 'code', frozen:true},
            { label: '提交日期',name: 'createTime', formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y.m.d'}, frozen:true},
            <c:if test="${!(cls==1 && module==1)}">
            { label: '提案人姓名',name: 'user.realname', frozen:true},
                </c:if>
            { label: '标题',name: 'name', align:'left', width:375, formatter: function (cellvalue, options, rowObject) {
                return ('<a href="javascript:;" class="openView" data-url="${ctx}/pcs/pcsProposal_check?id={0}&type=0">{1}</a>')
                        .format(rowObject.id, cellvalue);
            }, frozen:true},
            { label: '提案类型',name: 'type', width:250, formatter: $.jgrid.formatter.MetaType},
            { label: '关键字',name: 'keywords', align:'left', width:250},
                <c:if test="${cls==8}">
            { label: '审核',name: '_check', formatter: function (cellvalue, options, rowObject) {
                if(rowObject.status!='${PCS_PROPOSAL_STATUS_INIT}') return '已审核'
                return ('<button class="openView btn btn-success btn-xs" ' +
                'data-url="${ctx}/pcs/pcsProposal_check?id={0}&type=1"><i class="fa fa-check-square-o"></i> 审核</button>')
                        .format(rowObject.id);
            }},
            </c:if>
                <c:if test="${cls!=2}">
            { label: '状态',name: '_status', formatter: function (cellvalue, options, rowObject) {
                if (rowObject.status == undefined) return '--';
                return _cMap.PCS_PROPOSAL_STATUS_MAP[rowObject.status]
            }},
            </c:if>
            { label: '附议人',name: '_seconders', width:80, formatter: function (cellvalue, options, rowObject) {
                if($.trim(rowObject.seconderIds)=='') return '--';
                return ('<a href="javascript:;" class="popupBtn" data-url="${ctx}/pcs/pcsProposal_seconders?id={0}">{1}</a>')
                        .format(rowObject.id, rowObject.seconderIds.split(",").length);
            }},
            <c:if test="${cls==8}">
            { label: '是否达到立案标准',name: '_biaozhun', width:150, formatter: function (cellvalue, options, rowObject) {
                if($.trim(rowObject.seconderIds)=='') return '否';
                return rowObject.seconderIds.split(",").length>=${proposalSupportCount}?"是":"否";
            }},
            { label: '提案委员会处理',name: 'createTime', width:120, formatter: function (cellvalue, options, rowObject) {
                if($.trim(rowObject.seconderIds)=='') return '--';
                return rowObject.seconderIds.split(",").length>=${proposalSupportCount}?"处理意见":"-";
            }},
                </c:if>
            <c:if test="${(cls==1 && module==1)}">
            { label: '创建时间',name: 'createTime', width:180},
                </c:if>
            <c:if test="${cls==2 || cls==3 || (cls==1 && module==2)}">
            { label: '备注',name: '_remark', width:180, formatter: function (cellvalue, options, rowObject) {
                var inviteUserIds = $.trim(rowObject.inviteUserIds);
                if(inviteUserIds.split(",").indexOf('${_user.id}')>-1) return '提案人邀请附议'
                return '--'
            },cellattr:function(rowId, val, rowObject, cm, rdata) {
                <c:if test="${cls!=1}">
                var inviteUserIds = $.trim(rowObject.inviteUserIds);
                if(inviteUserIds.split(",").indexOf('${_user.id}')>-1)
                    return "class='danger'";
                </c:if>
            }},
            </c:if>
            {hidden: true, name: 'status'}
            /*{ label: '导出', width:210,name: '_export', formatter: function (cellvalue, options, rowObject) {

                return _.template($("#export_tpl").html().NoMultiSpace())({id:rowObject.id})
            }}*/
        ],
        onSelectRow: function(id,status){

            var data = $(this).getRowData(id);
            $("#editBtn, #delBtn").prop("disabled",data.status>=${PCS_PROPOSAL_STATUS_PASS});
        }
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid');
    $.initNavGrid("jqGrid", "jqGridPager");
    $('#searchForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>
</c:if>