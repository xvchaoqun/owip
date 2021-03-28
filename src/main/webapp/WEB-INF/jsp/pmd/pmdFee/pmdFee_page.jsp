<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <div id="body-content" class="rownumbers" data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <c:set var="_query" value="${not empty param.userId ||not empty param.partyId ||not empty param.branchId ||not empty param.isOnlinePay ||not empty param.hasPay ||not empty param.payTime || not empty param.code || not empty param.sort}"/>
            <div class="jqgrid-vertical-offset buttons">
                <shiro:hasPermission name="pmdFee:edit">
                    <button class="popupBtn btn btn-info btn-sm"
                            data-url="${ctx}/pmd/pmdFee_au">
                        <i class="fa fa-plus"></i> 添加</button>
                    <button id="editBtn" class="jqOpenViewBtn btn btn-primary btn-sm"
                       data-url="${ctx}/pmd/pmdFee_au"
                       data-grid-id="#jqGrid"><i class="fa fa-edit"></i>
                        修改</button>
                    <button id="delBtn" data-url="${ctx}/pmd/pmdFee_batchDel"
                            data-title="删除"
                            data-msg="确定删除这{0}条数据？<br/>（删除后无法恢复，请谨慎操作！！）"
                            data-grid-id="#jqGrid"
                            class="jqBatchBtn btn btn-danger btn-sm">
                        <i class="fa fa-trash"></i> 删除
                    </button>
                <button class="jqOpenViewBtn btn btn-info btn-sm"
                        data-url="${ctx}/sysApprovalLog"
                        data-querystr="&type=<%=SystemConstants.SYS_APPROVAL_LOG_TYPE_PMD_FEE%>"
                        data-open-by="page">
                    <i class="fa fa-search"></i> 操作记录
                </button>
                <button class="jqExportBtn btn btn-success btn-sm tooltip-success"
                   data-url="${ctx}/pmd/pmdFee_data"
                   data-rel="tooltip" data-placement="top" title="导出选中记录或所有搜索结果">
                    <i class="fa fa-download"></i> 导出</button>
                </shiro:hasPermission>
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
                            <shiro:hasPermission name="pmdFee:edit">
                        <div class="form-group">
                            <label>姓名</label>
                            <select name="userId"  class="form-control"
                                    data-rel="select2-ajax" data-width="264"
                                    data-ajax-url="${ctx}/member_selects?noAuth=1&status=${MEMBER_STATUS_NORMAL}"
                                    data-placeholder="请输入账号或姓名或学工号">
                                <option value="${sysUser.id}">${sysUser.realname}-${sysUser.code}</option>
                            </select>
                            <script type="text/javascript">
                                $("#searchForm select[name=userId]").val(${param.uesrId});
                            </script>
                        </div>
                            <div class="form-group">
                                <label>所在${_p_partyName}</label>
                                <select class="form-control" data-width="250"  data-rel="select2-ajax"
                                        data-ajax-url="${ctx}/party_selects?del=0"
                                        name="partyId" data-placeholder="请选择${_p_partyName}">
                                    <option value="${party.id}" delete="${party.isDeleted}">${party.name}</option>
                                </select>
                            </div>
                            <div class="form-group" style="${(empty branch)?'display: none':''}" id="branchDiv">
                                <label>所在党支部</label>
                                <select class="form-control"  data-rel="select2-ajax" data-ajax-url="${ctx}/branch_selects?del=0"
                                        name="branchId" data-placeholder="请选择党支部">
                                    <option value="${branch.id}" delete="${branch.isDeleted}">${branch.name}</option>
                                </select>
                            </div>
                            <script>
                                $.register.party_branch_select($("#searchForm"), "branchDiv",
                                    '${cm:getMetaTypeByCode("mt_direct_branch").id}', "${party.id}", "${party.classId}" );
                            </script>
                            </shiro:hasPermission>
                        <%--<div class="form-group">
                            <label>缴费方式</label>
                            <select required data-rel="select2" name="isOnlinePay" data-width="150"
                                    data-placeholder="请选择">
                                <option></option>
                                <option value="1">线上缴费</option>
                                <option value="0">现金缴费</option>
                            </select>
                            <script type="text/javascript">
                                $("#searchForm select[name=isOnlinePay]").val(${param.isOnlinePay});
                            </script>
                        </div>--%>
                        <div class="form-group">
                            <label>状态</label>
                            <select required data-rel="select2" name="hasPay" data-width="120"
                                    data-placeholder="请选择">
                                <option></option>
                                <option value="1">已缴费</option>
                                <option value="0">未缴费</option>
                            </select>
                            <script>
                                $("#searchForm select[name=hasPay]").val(${param.hasPay});
                            </script>
                        </div>
                            <div class="clearfix form-actions center">
                                <a class="jqSearchBtn btn btn-default btn-sm"
                                   data-url="${ctx}/pmd/pmdFee"
                                   data-target="#page-content"
                                   data-form="#searchForm"><i class="fa fa-search"></i> 查找</a>
                                <c:if test="${_query}">&nbsp;
                                    <button type="button" class="reloadBtn btn btn-warning btn-sm"
                                            data-url="${ctx}/pmd/pmdFee"
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
<script>
    $("#jqGrid").jqGrid({
        //rownumbers:true,
        url: '${ctx}/pmd/pmdFee_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
                /*{ label: '状态',name: 'hasPay',width: 80,formatter: function (cellvalue, options, rowObject) {
                        if (cellvalue == undefined) return '--';
                        return cellvalue?'<span class="text-success">已缴费</span>':'<span class="text-danger">未缴费</span>'
                    }},*/
                { label: '缴费',name: '_pay',width: 80,formatter: function (cellvalue, options, rowObject) {

                    if(rowObject.hasPay) return '<span class="text-success">已缴费</span>'
                        return ('<button class="popupBtn btn btn-xs btn-success" ' +
                            'data-url="${ctx}/pmd/pmdFee_confirm?id={0}" '
                            +'data-callback="_callback_popup"><i class="fa fa-rmb"></i> 缴费</button>').format(rowObject.id)
                }, frozen: true},
                { label: '缴费月份',name: 'payMonth',formatter: $.jgrid.formatter.date,formatoptions: {newformat: 'Y.m'}, frozen: true},
                { label: '学工号',name: 'user.code', width: 120, frozen: true},
                { label: '姓名',name: 'user.realname', frozen: true},
                { label: '所属${_p_partyName}',name: 'partyId',width: 300,align: 'left',formatter: function (cellvalue, options, rowObject) {
                    return cellvalue==undefined?"":_cMap.partyMap[cellvalue].name;
                    }},
                { label: '所在党支部',name: 'branchId',width: 250,align: 'left',formatter: function (cellvalue, options, rowObject) {
                    return cellvalue==undefined?"":_cMap.branchMap[cellvalue].name;
                    }},
                { label: '缴费方式',name: 'isOnlinePay',formatter: function (cellvalue, options, rowObject) {
                        if (cellvalue == undefined) return '--';
                        return cellvalue?"线上缴费":"现金缴费"
                    }},
                { label: '缴费金额',name: 'amt'},
                { label: '缴费类型',name: 'type',width: 130,formatter:function (cellvalue, options, rowObject) {
                    return _cMap.metaTypeMap[cellvalue].name;
                }},
                { label: '缴费说明',name: 'reason',width: 200},

                { label: '缴费订单号',name: 'orderNo', width: 180},
                { label: '缴费日期',name: 'payTime', formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y.m.d'}},
                { label: '缴费人',name: 'chargeUser.realname'},
                { label: '备注',name: 'remark',width: 200}, {hidden: true, name: 'hasPay'}
        ],
        onSelectRow: function (id, status) {
            saveJqgridSelected("#" + this.id, id, status);
            _onSelectRow(this)
        },
        onSelectAll: function (aRowids, status) {
            saveJqgridSelected("#" + this.id);
            _onSelectRow(this)
        }
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid');
    $.initNavGrid("jqGrid", "jqGridPager");

    function _onSelectRow(grid) {
        var ids = $(grid).getGridParam("selarrrow");

        if (ids.length > 1) {
            $("#editBtn").prop("disabled", true);
        } else if (ids.length == 1) {
            var rowData = $(grid).getRowData(ids[0]);
            var hasPay = (rowData.hasPay == "true");
            $("#editBtn, #delBtn").prop("disabled", hasPay);
        }
    }

    $.register.user_select($("#searchForm select[name=userId]"));
    $('#searchForm [data-rel="select2"]').select2();
    //$('[data-rel="tooltip"]').tooltip();
</script>