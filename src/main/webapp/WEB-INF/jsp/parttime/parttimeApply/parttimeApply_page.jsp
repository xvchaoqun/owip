<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<%@ include file="/WEB-INF/jsp/parttime/constants.jsp" %>
<div class="row">
    <div class="col-xs-12">

        <div id="body-content" class="myTableDiv"
             data-url-page="${ctx}/parttime/parttimeApply"
             data-url-export="${ctx}/parttime/parttimeApply_data"
             data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <c:set var="_query" value="${not empty param.cadreId ||not empty param.applyTime||not empty param.parttime
            ||not empty param.isFirst || not empty param.background || not empty param.hasPay}"/>
            <div class="tabbable">
                <ul class="nav nav-tabs padding-12 tab-color-blue background-blue">
                    <li class="<c:if test="${status==0}">active</c:if>">
                        <a href="javascript:;" class="loadPage" data-url="${ctx}/parttime/parttimeApply_page?status=0"><i
                                class="fa fa-circle-o"></i> 兼职申报</a>
                    </li>
                    <li class="<c:if test="${status==1}">active</c:if>">
                        <a href="javascript:;" class="loadPage" data-url="${ctx}/parttime/parttimeApply_page?status=1"><i
                                class="fa fa-check"></i> 同意申请</a>
                    </li>
                    <li class="<c:if test="${status==2}">active</c:if>">
                        <a href="javascript:;" class="loadPage" data-url="${ctx}/parttime/parttimeApply_page?status=2"><i
                                class="fa fa-times"></i> 不同意申请</a>
                    </li>
                    <li class="<c:if test="${status==3}">active</c:if>">
                        <a href="javascript:;" class="loadPage" data-url="${ctx}/parttime/parttimeApply_page?status=3"><i
                                class="fa fa-trash"></i> 已删除</a>
                    </li>
                    <div class="buttons pull-right" style="top: -3px; right:10px; position: relative">
                        <a class="openView btn btn-success btn-sm"
                           data-url="${ctx}/htmlFragment_au?editContent=no&code=hf_parttime_apply_note"><i
                                class="fa fa-plus"></i> 申请说明</a>
                        <a class="openView btn btn-primary btn-sm"
                           data-url="${ctx}/htmlFragment_au?editContent=no&code=hf_parttime_apply_approval_note"><i
                                class="fa fa-plus"></i> 审批说明</a>
                    </div>
                </ul>

                <div class="tab-content">
                    <div class="tab-pane in active">
                        <div class="jqgrid-vertical-offset buttons">
                            <c:if test="${status==0}">
                                <button data-url="${ctx}/parttimeApply_select"
                                        class="popupBtn btn btn-primary btn-sm">
                                    <i class="fa fa-plus"></i> 申请
                                </button>
                            </c:if>

                            <button class="jqOpenViewBtn btn btn-warning btn-sm"
                                    data-url="${ctx}/sysApprovalLog"
                                    data-querystr="&type=<%=SystemConstants.SYS_APPROVAL_LOG_TYPE_PARTTIME_APPLY%>"
                                    data-open-by="page">
                                <i class="fa fa-search"></i> 操作记录
                            </button>

                            <a class="jqExportBtn btn btn-info btn-sm tooltip-success"
                               data-rel="tooltip" data-placement="top" title="导出当前搜索的全部结果（按照当前排序）">
                                <i class="fa fa-download"></i> 导出</a>

                            <c:if test="${status!=3}">
                                <shiro:hasPermission name="parttimeApply:del">
                                    <a class="jqBatchBtn btn btn-danger btn-sm"
                                       data-url="${ctx}/parttimeApply_batchDel" data-title="删除兼职申报"
                                       data-msg="确定删除这{0}条申请记录吗？"><i class="fa fa-trash"></i> 删除</a>
                                </shiro:hasPermission>
                            </c:if>
                            <c:if test="${status==3}">
                                <shiro:hasPermission name="parttimeApply:del">
                                    <a class="jqBatchBtn btn btn-success btn-sm"
                                       data-url="${ctx}/parttime/parttimeApply_batchUnDel" data-title="找回已删除兼职申报申请"
                                       data-msg="确定恢复这{0}条申请记录吗？"><i class="fa fa-reply"></i> 恢复申请</a>
                                </shiro:hasPermission>
                                <shiro:hasPermission name="passportApply:del">
                                    <a class="jqBatchBtn btn btn-danger btn-sm"
                                       data-url="${ctx}/parttime/parttimeApply_doBatchDel" data-title="删除申请"
                                       data-msg="确定删除这{0}条申请记录吗（<span class='text-danger'>删除后不可以恢复，且相关数据都会删除</span>）？"><i
                                            class="fa fa-times"></i> 完全删除</a>
                                </shiro:hasPermission>
                            </c:if>
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
                                        <div class="clearfix form-actions center">
                                            <a class="jqSearchBtn btn btn-default btn-sm"><i class="fa fa-search"></i>
                                                查找</a>

                                            <c:if test="${_query || not empty param.sort}">&nbsp;
                                                <button type="button" class="reloadBtn btn btn-warning btn-sm"
                                                        data-querystr="status=${status}">
                                                    <i class="fa fa-reply"></i> 重置
                                                </button>
                                            </c:if>
                                        </div>
                                    </form>
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
<style>
    .tooltip-inner {
        background-color: #D13127;
        color: #fff;
    }

    .tooltip.top .tooltip-arrow {
        border-top-color: #D13127;
    }
</style>
<script type="text/template" id="remark_tpl">
    <button class="popupBtn btn btn-xs btn-primary"
            data-url="${ctx}/parttime/parttimeApplyModifyList?applyId={{=id}}"><i class="fa fa-search"></i> 查看
    </button>
</script>

<jsp:include page="/WEB-INF/jsp/common/daterangerpicker.jsp"/>
<script>

    $("#jqGrid").jqGrid({
        url: '${ctx}/parttime/parttimeApply_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            <c:if test="${status==0}">
            {
                label: '审批', name: '_approval', width: 80, formatter: function (cellvalue, options, rowObject) {
                    return _approval(rowObject.approvalTdBeanMap, rowObject.isDeleted)
                }
            },
            </c:if>
            {
                label: '编号', name: 'id', width: 80, formatter: function (cellvalue, options, rowObject) {
                    return "L{0}".format(rowObject.id);
                }
            },
            {label: '申请日期', name: 'applyTime', formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y.m.d'}},

            {label: '工作证号', name: 'user.code', width: 110, classes:'can-wrap'},
            {
                label: '姓名', name: 'user.realname', width: 75, formatter: function (cellvalue, options, rowObject) {
                    return $.cadre(rowObject.cadre.id, cellvalue);
                }
            },
            {label: '兼职单位及职务', name: 'title', align:'left', width: 250},
            /*{label: '兼职开始年月', name: 'startTime', formatter: $.jgrid.formatter.date, width: 150,
                formatoptions: {srcformat:'Y-m-d H:i:s',newformat: 'Y-m-d'}},
            {label: '兼职结束年月', name: 'endTime', formatter: $.jgrid.formatter.date, width: 150,
                formatoptions: {srcformat:'Y-m-d H:i:s',newformat: 'Y-m-d'}},
            {
                label: '首次/连任', name: 'isFirst', width: 80, formatter: function (cellvalue, options, rowObject) {
                    return rowObject.isFirst ? '首次' : '连任';
                }
            },
            {
                label: '是否有国境外背景', name: 'background', width: 180, formatter: function (cellvalue, options, rowObject) {
                    return rowObject.background ? '是' : '否';
                }
            },
            {
                label: '是否取酬', name: 'hasPay', width: 200, formatter: function (cellvalue, options, rowObject) {
                    return rowObject.hasPay ? '是' : '否';
                }
            },
            {label: '取酬金额', name: 'balance', width: 200},
            {label: '申请理由', name: 'reason', width: 200},*/
            {
                label: '组织部初审', name: 'expiryDate', cellattr: function (rowId, val, rowObject, cm, rdata) {
                    var tdBean = rowObject.approvalTdBeanMap[-1];
                    return approverTdAttrs(tdBean);
                }, formatter: function (cellvalue, options, rowObject) {
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
                label: '组织部终审', name: 'expiryDate', cellattr: function (rowId, val, rowObject, cm, rdata) {
                    var tdBean = rowObject.approvalTdBeanMap[0];
                    return approverTdAttrs(tdBean);
                }, formatter: function (cellvalue, options, rowObject) {
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
        ],
        rowattr: function (rowData, currentObj, rowId) {
            var tdType, approvalTypeId, isFinish;
            if (currentObj.flowNode == -1 || currentObj.flowNode == 0) {
                var tdBean = currentObj.approvalTdBeanMap[currentObj.flowNode];
                isFinish = currentObj.isFinish;
                approvalTypeId = tdBean != undefined ? tdBean.approvalTypeId : null;
            }

            return {
                'data-flow-node': currentObj.flowNode,
                'data-finish': isFinish,
                'data-approval-type-id': approvalTypeId
            }

        }, onCellSelect: function (rowid, iCol, cellcontent, e) {
            console.dir(e.target)
            var applyId = $(e.target).data("apply-id");
            var approvalTypeId = $(e.target).data("approval-type-id");
            var tdType = $(e.target).data("td-type");
            if (tdType != 1 && applyId > 0) {
                $.getJSON("${ctx}/parttimeApply_approvers", {
                    applyId: applyId,
                    approvalTypeId: approvalTypeId
                }, function (ret) {
                    if (ret.success) {
                        var realnames = $.map(ret.approvers, function (item, idx) {
                            return item.realname;
                        });
                        var text = realnames.join("，");
                        if (ret.uv) {
                            text += "<br/><span class='text-danger'>审批人：" + ret.uv.realname + "</span>";
                        }
                        $(e.target).qtip({
                            content: {
                                text: text, title: {
                                    text: '审批人',
                                    button: true
                                }
                            }, position: {
                                my: 'bottom center',
                                at: 'top center'
                            }, show: true, hide: {
                                event: 'click',
                                inactive: 1500
                            }, button: 'Close'
                        });
                    }
                });
            }
        }
    }).jqGrid("setFrozenColumns").on("initGrid", function () {

        $('[data-tooltip="tooltip"]').tooltip({container: '#page-content'});
    });
    $(window).triggerHandler('resize.jqGrid');
    $.initNavGrid("jqGrid", "jqGridPager");

    function approverTdAttrs(tdBean) {

        if (tdBean == undefined) return '--';

        var attrs = "data-td-type={0} data-apply-id={1} data-approval-type-id={2} ".format(tdBean.tdType, tdBean.applyId, tdBean.approvalTypeId);
        //console.log(tdBean.approvalTypeId + " " + tdBean.tdType)
        if (tdBean.approvalTypeId != -1 && tdBean.tdType == 2)
            attrs += "class='not_approval' ";

        return attrs;
    }

    function processTdBean(tdBean) {
        if (tdBean == undefined) return '--';
        var type = tdBean.tdType;
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

    $('[data-rel="tooltip"]').tooltip();
    $('#searchForm [data-rel="select2"]').select2();
    $.register.user_select($('[data-rel="select2-ajax"]'));
</script>