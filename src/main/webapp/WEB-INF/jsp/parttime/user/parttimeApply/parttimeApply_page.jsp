<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<%@ include file="/WEB-INF/jsp/parttime/constants.jsp" %>
<div class="row">
    <div class="col-xs-12">

        <div id="body-content" class="myTableDiv"
             data-url-au="${ctx}/user/parttime/parttimeApply_au"
             data-url-page="${ctx}/user/parttime/parttimeApply"
             data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <c:set var="_query" value="${not empty param._applyDate }"/>
            <div class="jqgrid-vertical-offset buttons">
                <a class="popupBtn btn btn-info btn-sm"
                   data-width="650"
                   data-url="${ctx}/hf_content?code=hf_parttime_apply_note">
                    <i class="fa fa-info-circle"></i> 申请说明</a>
                <a class="openView btn btn-success btn-sm" data-url="${ctx}/user/parttime/parttimeApply_au"><i
                        class="fa fa-plus"></i> 申请</a>
                <button class="jqEditBtn btn btn-primary btn-sm tooltip-info"
                        data-url="${ctx}/user/parttime/parttimeApply_au"
                        data-open-by="page">
                    <i class="fa fa-edit"></i> 修改
                </button>
                <button id="abolishBtn" class="jqItemBtn btn btn-danger btn-sm"
                        data-url="${ctx}/user/parttime/parttimeApply_del" data-title="撤销申请"
                        data-msg="确定撤销该申请吗？">
                    <i class="fa fa-trash"></i> 撤销
                </button>
            </div>
            <div class="jqgrid-vertical-offset widget-box ${_query?'':'collapsed'} hidden-sm hidden-xs">
                <div class="widget-header">
                    <h4 class="widget-title">搜索</h4><span class="widget-note">${note_searchbar}</span>
                    <div class="widget-toolbar">
                        <a href="javascript:;" data-action="collapse">
                            <i class="ace-icon fa fa-chevron-${_query?'up':'down'}"></i>
                        </a>
                    </div>
                </div>
                <div class="widget-body">
                    <div class="widget-main no-padding">
                        <t:sort-form css="form-horizontal " id="searchForm">
                            <form class="form-inline search-form" id="searchForm">
                                <div class="row">

                                    <div class="col-xs-4">
                                        <div class="form-group">
                                            <label class="col-xs-3 control-label">申请日期范围</label>
                                            <div class="col-xs-6">
                                                <div class="input-group tooltip-success" data-rel="tooltip"
                                                     title="申请日期范围">
                                            <span class="input-group-addon">
                                                <i class="fa fa-calendar bigger-110"></i>
                                            </span>
                                                    <input placeholder="请选择申请日期范围" data-rel="date-range-picker"
                                                           class="form-control date-range-picker" type="text"
                                                           name="_applyDate" value="${param.applyTime}"/>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="clearfix form-actions center">
                                    <a class="jqSearchBtn btn btn-default btn-sm"><i class="fa fa-search"></i> 查找</a>

                                    <c:if test="${_query || not empty param.sort}">&nbsp;
                                        <button type="button" class="reloadBtn btn btn-warning btn-sm"
                                                data-querystr="status=${status}">
                                            <i class="fa fa-reply"></i> 重置
                                        </button>
                                    </c:if>
                                </div>
                            </form>
                        </t:sort-form>
                    </div>
                </div>
            </div>
            <div class="space-4"></div>
            <table id="jqGrid" class="jqGrid"></table>
            <div id="jqGridPager"></div>
        </div>
        <div id="body-content-view">
        </div>
    </div>
</div>
<script type="text/template" id="remark_tpl">
    <button class="popupBtn btn btn-xs btn-primary"
            data-url="${ctx}/parttime/parttimeApplyModifyList?applyId={{=id}}"><i class="fa fa-search"></i> 查看
    </button>
</script>
<jsp:include page="/WEB-INF/jsp/common/daterangerpicker.jsp"/>
<script>
    $("#jqGrid").jqGrid({

        url: '${ctx}/user/parttime/parttimeApply_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            {
                label: '编号', name: 'id', width: 80, formatter: function (cellvalue, options, rowObject) {
                    return "L{0}".format(rowObject.id);
                }
            },
            {label: '申请日期', name: 'applyTime', formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y.m.d'}},

            {label: '工作证号', name: 'user.code', width: 110, classes: 'can-wrap'},
            {
                label: '姓名', name: 'user.realname', width: 75, formatter: function (cellvalue, options, rowObject) {
                    return $.cadre(rowObject.cadre.id, cellvalue);
                }
            },
            {label: '兼职单位及职务', name: 'title', align: 'left', width: 250},

            {
                label: '组织部初审', name: 'approver-1', formatter: function (cellvalue, options, rowObject) {
                    var tdBean = rowObject.approvalTdBeanMap[-1];
                    return processTdBean(tdBean)
                }
            },
            {
                label: '审批', name: 'approver', width: 90,
                formatter: function (cellvalue, options, rowObject) {

                    return ('<button data-url="${ctx}/parttime/parttimeApply_view?id={0}" class="openView btn btn-warning btn-xs">'
                               + '<i class="fa fa-info-circle"></i> 详情</button>').format(rowObject.id);
                }
            },

            {
                label: '组织部终审', name: 'approver0', cellattr: function (rowId, val, rowObject, cm, rdata) {
                    var tdBean = rowObject.approvalTdBeanMap[0];
                    if (tdBean != undefined && tdBean.tdType == 2)
                        return "class='not_approval'"
                }, formatter: function (cellvalue, options, rowObject) {
                    var tdBean = rowObject.approvalTdBeanMap[0];
                    return processTdBean(tdBean)
                }
            },
            {
                hidden: true, name: 'firstType', formatter: function (cellvalue, options, rowObject) {
                    var tdBean = rowObject.approvalTdBeanMap[-1];
                    return tdBean != undefined ? tdBean.tdType : null;
                }
            },
            {
                hidden: true, name: 'isFinish', formatter: function (cellvalue, options, rowObject) {
                    return cellvalue ? 1 : 0;
                }
            },
            {
                hidden: true, name: 'isAgreed', formatter: function (cellvalue, options, rowObject) {
                    return cellvalue ? 1 : 0;
                }
            },
            {
                label: '备注', name: 'isModify', formatter: function (cellvalue, options, rowObject) {
                    if (cellvalue)
                        return _.template($("#remark_tpl").html().NoMultiSpace())({id: rowObject.id});
                    else return '--'
                }
            }
        ]
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid');

    $('[data-rel="tooltip"]').tooltip();
    $('#searchForm [data-rel="select2"]').select2();

    function processTdBean(tdBean) {

        if (tdBean == undefined) return '--';

        var applyId = tdBean.applyId;
        var approvalTypeId = tdBean.approvalTypeId;
        var type = tdBean.tdType;
        var canApproval = tdBean.canApproval;
        var html = "";
        switch (type) {
            case 1:
                html = "-";
                break;
            //not_approval
            case 2:
                html = "";
                break;
            case 3:
            case 4:
                html = "未审批";
                break;
            case 5:
                html = "未通过";
                break;
            case 6:
                html = "通过";
                break;
        }

        return html;
    }

</script>