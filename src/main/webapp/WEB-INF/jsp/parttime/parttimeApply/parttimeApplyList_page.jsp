<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<%@ include file="/WEB-INF/jsp/parttime/constants.jsp" %>
<div class="row">
    <div class="col-xs-12">

        <div id="body-content" class="myTableDiv"
             data-url-page="${ctx}/parttime/parttimeApplyList"
             data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <c:set var="_query" value="${not empty param.cadreId ||not empty param._applyDate
            ||not empty param.type || not empty param.code || not empty param.sort}"/>
            <div class="tabbable">
                <ul class="jqgrid-vertical-offset nav nav-tabs padding-12 tab-color-blue background-blue">
                    <li class="<c:if test="${status==0}">active</c:if>">
                        <a href="javascript:;" class="loadPage" data-url="${ctx}/parttime/parttimeApplyList?status=0"><i class="fa fa-circle-o"></i> 待审批</a>
                    </li>
                    <li class="<c:if test="${status==1}">active</c:if>">
                        <a href="javascript:;" class="loadPage" data-url="${ctx}/parttime/parttimeApplyList?status=1"><i class="fa fa-check"></i> 已审批</a>
                    </li>
                </ul>

                <div class="tab-content">
                    <div class="tab-pane in active rownumbers">
                        <div class="jqgrid-vertical-offset buttons">
                            <a class="popupBtn btn btn-info btn-sm"
                               data-width="650"
                               data-url="${ctx}/hf_content?code=hf_cla_apply_approval_note">
                                <i class="fa fa-info-circle"></i> 审批说明</a>
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
                                        <div class="row">
                                            <div class="col-xs-4">
                                                <div class="form-group">
                                                    <label class="col-xs-3 control-label">姓名</label>

                                                    <div class="col-xs-6">
                                                        <div class="input-group">
                                                            <input type="hidden" name="status" value="${status}">
                                                            <select data-rel="select2-ajax"
                                                                    data-ajax-url="${ctx}/cadre_selects"
                                                                    name="cadreId" data-placeholder="请输入账号或姓名或学工号">
                                                                <option value="${cadre.id}">${sysUser.realname}-${sysUser.code}</option>
                                                            </select>
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
                                    </t:sort-form>
                                </div>
                            </div>
                        </div>
                        <div class="space-4"></div>
                        <table id="jqGrid" class="jqGrid"></table>
                        <div id="jqGridPager"></div>
                    </div>
                </div>
            </div>
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
        //forceFit:true,
        url: '${ctx}/parttime/parttimeApplyList_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        ondblClickRow: function (rowid, iRow, iCol, e) {
            $("#detailBtn").click();
        },
        colModel: [
            <c:if test="${status==0}">
            {
                label: '审批', name: '_approval', width: 80, formatter: function (cellvalue, options, rowObject) {
                console.log(rowObject)
                    return _approval(rowObject.approvalTdBeanMap, rowObject.isDeleted)
            }, frozen: true},
            </c:if>
            {
                label: '编号', name: 'id', width: 80, formatter: function (cellvalue, options, rowObject) {
                return "L{0}".format(rowObject.id);
            }, frozen: true
            },
            {label: '申请日期', name: 'applyTime', formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y.m.d'}},

            {label: '工作证号', name: 'user.code', width: 110, classes:'can-wrap'},
            {
                label: '姓名', name: 'user.realname', width: 75, formatter: function (cellvalue, options, rowObject) {
                    return $.cadre(rowObject.cadre.id, cellvalue);
                }
            },
            {label: '兼职单位及职务', name: 'title', align:'left', width: 250},
            {
                label: '组织部初审',
                name: 'expiryDate',

                formatter: function (cellvalue, options, rowObject) {
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
                label: '组织部终审',

                name: 'expiryDate',

                cellattr: function (rowId, val, rowObject, cm, rdata) {
                    var tdBean = rowObject.approvalTdBeanMap[0];
                    if (tdBean!=undefined && tdBean.tdType == 2)
                        return "class='not_approval'"
                },
                formatter: function (cellvalue, options, rowObject) {
                    var tdBean = rowObject.approvalTdBeanMap[0];
                    return processTdBean(tdBean)
                }
            },
            {
                label: '变更记录', name: 'isModify', formatter: function (cellvalue, options, rowObject) {
                if (cellvalue)
                    return _.template($("#remark_tpl").html().NoMultiSpace())({id: rowObject.id});
                else return '--'
            }
            }
        ]
    }).jqGrid("setFrozenColumns").on("initGrid", function () {

        $(".approvalBtn").click(function () {
            $.loadModal("${ctx}/parttime/parttimeApply_approval?applyId=" + $(this).data("id") + "&approvalTypeId=" + $(this).data("approvaltypeid"));
        });
    });
    $(window).triggerHandler('resize.jqGrid');

    function processTdBean(tdBean) {

        if(tdBean==undefined) return '--';

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
                html = "未审批";
                break;
            case 4:
                html = '<span class="text-danger">待审批</span>';
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

    function _approval(approvalTdBeanMap, isDeleted) {

        var html = "-";
        $.each(approvalTdBeanMap, function (i, tdBean) {
            //console.log(tdBean)
            var applyId = tdBean.applyId;
            var approvalTypeId = tdBean.approvalTypeId;
            var type = tdBean.tdType;
            var canApproval = tdBean.canApproval && !isDeleted;
            switch (type) {
                case 4:
                {
                    html = "<button {0} class=\"openView btn {1} btn-xs\"" +
                            "        data-url=\"${ctx}/parttime/parttimeApply_view?type=approval&id={2}&approvalTypeId={3}\">" +
                            "        <i class=\"fa fa-edit\"></i> 审批" +
                            "        </button>";
                    //console.log(html)
                    html = html.format(canApproval ? "" : "disabled",
                            canApproval ? "btn-success" : "btn-default",
                            applyId, approvalTypeId);
                   return html;
                } break;
            }
        })

        return html;
    }

    $('#searchForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
    $.register.user_select($('[data-rel="select2-ajax"]'));
</script>