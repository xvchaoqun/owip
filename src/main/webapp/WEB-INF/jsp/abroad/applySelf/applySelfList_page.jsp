<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <!-- PAGE CONTENT BEGINS -->
        <div id="body-content" class="myTableDiv"
             data-url-page="${ctx}/abroad/applySelfList"
             data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <c:set var="_query" value="${not empty param.cadreId ||not empty param._applyDate
            ||not empty param.type || not empty param.code || not empty param.sort}"/>
            <div class="tabbable">
                <ul class="jqgrid-vertical-offset nav nav-tabs padding-12 tab-color-blue background-blue">
                    <li class="<c:if test="${status==0}">active</c:if>">
                        <a href="javascript:;" class="loadPage" data-url="${ctx}/abroad/applySelfList?status=0"><i class="fa fa-circle-o"></i> 待审批</a>
                    </li>
                    <li class="<c:if test="${status==1}">active</c:if>">
                        <a href="javascript:;" class="loadPage" data-url="${ctx}/abroad/applySelfList?status=1"><i class="fa fa-check"></i> 已审批</a>
                    </li>
                </ul>

                <div class="tab-content">
                    <div id="home4" class="tab-pane in active rownumbers">
                        <div class="jqgrid-vertical-offset buttons">
                            <c:if test="${status==1}">
                                <button id="detailBtn" data-url="${ctx}/abroad/applySelf_view"
                                        data-open-by="page"
                                        class="jqOpenViewBtn btn btn-warning btn-sm">
                                    <i class="fa fa-info-circle"></i> 详情
                                </button>
                            </c:if>
                            <c:if test="${status==0}">
                                <button id="detailBtn" class="btn btn-warning btn-sm">
                                    <i class="fa fa-info-circle"></i> 详情
                                </button>
                            </c:if>
                            <a class="popupBtn btn btn-info btn-sm"
                               data-width="650"
                               data-url="${ctx}/hf_content?code=${HTML_FRAGMENT_APPLY_SELF_APPROVAL_NOTE}">
                                <i class="fa fa-info-circle"></i> 审批说明</a>
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
                                            <div class="col-xs-4">
                                                <div class="form-group">
                                                    <label class="col-xs-4 control-label">申请日期范围</label>

                                                    <div class="col-xs-6">
                                                        <div class="input-group tooltip-success" data-rel="tooltip"
                                                             title="申请日期范围">
                                                            <span class="input-group-addon">
                                                                <i class="fa fa-calendar bigger-110"></i>
                                                            </span>
                                                            <input placeholder="请选择申请日期范围" data-rel="date-range-picker"
                                                                   class="form-control date-range-picker" type="text"
                                                                   name="_applyDate" value="${param._applyDate}"/>
                                                        </div>
                                                    </div>
                                                </div>

                                            </div>
                                            <div class="col-xs-4">
                                                <div class="form-group">
                                                    <label class="col-xs-4 control-label">出行时间范围</label>

                                                    <div class="col-xs-6">
                                                        <select name="type" data-rel="select2"
                                                                data-placeholder="请选择出行时间范围">
                                                            <option></option>
                                                            <c:forEach items="${APPLY_SELF_DATE_TYPE_MAP}" var="type">
                                                                <option value="${type.key}">${type.value}</option>
                                                            </c:forEach>
                                                        </select>
                                                        <script>
                                                            $("#searchForm select[name=type]").val('${param.type}');
                                                        </script>
                                                    </div>
                                                </div>
                                            </div>

                                        </div>
                                        <div class="clearfix form-actions center">
                                            <a class="jqSearchBtn btn btn-default btn-sm"><i class="fa fa-search"></i> 查找</a>

                                            <c:if test="${_query || not empty param.sort}">&nbsp;
                                                <button type="button" class="resetBtn btn btn-warning btn-sm"
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
        <div id="item-content">
        </div>
    </div>
</div>
<script type="text/template" id="remark_tpl">
    <button class="popupBtn btn btn-xs btn-primary"
            data-url="${ctx}/abroad/applySelfModifyList?applyId={{=id}}"><i class="fa fa-search"></i> 查看
    </button>
</script>
<jsp:include page="/WEB-INF/jsp/common/daterangerpicker.jsp"/>
<script>
    $("#detailBtn").click(function () {
        var grid = $("#jqGrid");
        var id = grid.getGridParam("selrow");
        var ids = grid.getGridParam("selarrrow");
        if (!$(this).hasClass("jqOpenViewBtn")) {
            if (!id || ids.length > 1) {
                SysMsg.warning("请选择一行", "提示");
                return;
            }
        }

        var approvalBtn = $("[role='row'][id=" + id + "]", "#jqGrid").find(".openView.btn-success");
        if (approvalBtn && approvalBtn.length == 1) {
            //alert(0)
            approvalBtn.click();
        }
    });
    $("#jqGrid").jqGrid({
        //forceFit:true,
        url: '${ctx}/abroad/applySelfList_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        ondblClickRow: function (rowid, iRow, iCol, e) {
            $("#detailBtn").click();
        },
        colModel: [
            {
                label: '编号', name: 'id', width: 80, formatter: function (cellvalue, options, rowObject) {
                return "S{0}".format(rowObject.id);
            }, frozen: true
            },
            {label: '申请日期', align: 'center', name: 'applyDate', width: 100, frozen: true, formatter: 'date', formatoptions: {newformat: 'Y-m-d'}},
            <c:if test="${status==0}">
            {
                label: '审批', name: '_approval', width: 80, formatter: function (cellvalue, options, rowObject) {
                return _approval(rowObject.approvalTdBeanMap, rowObject.isDeleted)
            }},
            </c:if>
            {label: '工作证号', align: 'center', name: 'user.code', width: 100, frozen: true},
            {label: '姓名', align: 'center', name: 'user.realname', width: 75, frozen: true},
            {label: '所在单位及职务', name: 'cadre.title', width: 250},
            {label: '出行时间', align: 'center', name: 'startDate', width: 100, formatter: 'date', formatoptions: {newformat: 'Y-m-d'}},
            {label: '回国时间', align: 'center', name: 'endDate', width: 100, formatter: 'date', formatoptions: {newformat: 'Y-m-d'}},
            {
                label: '出行天数',
                align: 'center',
                name: '_day',
                width: 80,
                formatter: function (cellvalue, options, rowObject) {
                    //console.log(rowObject.startDate)
                    //console.log(rowObject.endDate)
                    return $.dayDiff(rowObject.startDate, rowObject.endDate);
                }
            },
            {label: '前往国家或地区', align: 'center', name: 'toCountry', width: 180},
            {
                label: '因私出国（境）事由',
                align: 'center',
                name: 'reason',
                width: 200,
                formatter: function (cellvalue, options, rowObject) {
                    return cellvalue.replace(/\+\+\+/g, ',');
                }
            },
            {
                label: '组织部初审',
                align: 'center',
                name: 'expiryDate',
                width: 100,
                formatter: function (cellvalue, options, rowObject) {
                    var tdBean = rowObject.approvalTdBeanMap[-1];
                    return processTdBean(tdBean)
                }
            },
            <c:forEach items="${approverTypeMap}" var="type">
            {
                label: '${type.value.name}审批', align: 'center', name: 'approver${type.key}', width: 150,
                cellattr: function (rowId, val, rowObject, cm, rdata) {
                    var tdBean = rowObject.approvalTdBeanMap['${type.key}'];
                    if (tdBean!=undefined && tdBean.tdType == 2)
                        return "class='not_approval'"
                }, formatter: function (cellvalue, options, rowObject) {
                var tdBean = rowObject.approvalTdBeanMap['${type.key}'];
                return processTdBean(tdBean)
            }
            },
            </c:forEach>
            {
                label: '组织部终审',
                align: 'center',
                name: 'expiryDate',
                width: 100,
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
                label: '变更记录', name: 'isModify', width: 100, formatter: function (cellvalue, options, rowObject) {
                if (cellvalue)
                    return _.template($("#remark_tpl").html().NoMultiSpace())({id: rowObject.id});
                else return ''
            }
            }
        ]
    }).jqGrid("setFrozenColumns").on("initGrid", function () {

        $(".approvalBtn").click(function () {
            $.loadModal("${ctx}/abroad/applySelf_approval?applySelfId=" + $(this).data("id") + "&approvalTypeId=" + $(this).data("approvaltypeid"));
        });
    });
    $(window).triggerHandler('resize.jqGrid');

    function processTdBean(tdBean) {

        if(tdBean==undefined) return '';

        var applySelfId = tdBean.applySelfId;
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
        for (i in approvalTdBeanMap) {

            var tdBean = approvalTdBeanMap[i];
            //console.log(tdBean)
            var applySelfId = tdBean.applySelfId;
            var approvalTypeId = tdBean.approvalTypeId;
            var type = tdBean.tdType;
            var canApproval = tdBean.canApproval && !isDeleted;
            switch (type) {
                case 4:
                {
                    html = "<button {0} class=\"openView btn {1} btn-xs\"" +
                            "        data-url=\"${ctx}/abroad/applySelf_view?type=approval&id={2}&approvalTypeId={3}\">" +
                            "        <i class=\"fa fa-edit\"></i> 审批" +
                            "        </button>";
                    //console.log(html)
                    html = html.format(canApproval ? "" : "disabled",
                            canApproval ? "btn-success" : "btn-default",
                            applySelfId, approvalTypeId);
                   return html;
                } break;
            }
        }

        return html;
    }

    $('#searchForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
    register_user_select($('[data-rel="select2-ajax"]'));
</script>