<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <!-- PAGE CONTENT BEGINS -->
        <div id="body-content" class="myTableDiv"
             data-url-page="${ctx}/applySelf_page"
             data-url-export="${ctx}/applySelf_data"
             data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <c:set var="_query" value="${not empty param.cadreId ||not empty param._applyDate
            ||not empty param.type || not empty param.code || not empty param.isModify || not empty param.sort}"/>
            <div class="tabbable">
                <ul class="nav nav-tabs padding-12 tab-color-blue background-blue">
                    <li class="<c:if test="${status==0}">active</c:if>">
                        <a href="?status=0"><i class="fa fa-circle-o"></i> 因私出国境申请</a>
                    </li>
                    <li class="<c:if test="${status==1}">active</c:if>">
                        <a href="?status=1"><i class="fa fa-check"></i> 同意申请</a>
                    </li>
                    <li class="<c:if test="${status==2}">active</c:if>">
                        <a href="?status=2"><i class="fa fa-times"></i> 不同意申请</a>
                    </li>
                    <li class="<c:if test="${status==-1}">active</c:if>">
                        <a href="?status=-1"><i class="fa fa-trash"></i> 已删除</a>
                    </li>
                    <div class="buttons pull-right" style="top: -3px; right:10px; position: relative">
                        <a class="openView btn btn-success btn-sm"
                           data-url="${ctx}/htmlFragment_au?editContent=no&code=${HTML_FRAGMENT_APPLY_SELF_NOTE}"><i
                                class="fa fa-plus"></i> 申请说明</a>
                        <a class="openView btn btn-primary btn-sm"
                           data-url="${ctx}/htmlFragment_au?editContent=no&code=${HTML_FRAGMENT_APPLY_SELF_APPROVAL_NOTE}"><i
                                class="fa fa-plus"></i> 审批说明</a>
                    </div>
                </ul>

                <div class="tab-content">
                    <div id="home4" class="tab-pane in active">
                        <div class="jqgrid-vertical-offset buttons">
                            <c:if test="${status==0}">
                                <button data-url="${ctx}/applySelf_au"
                                        class="popupBtn btn btn-primary btn-sm">
                                    <i class="fa fa-plus"></i> 申请
                                </button>
                            </c:if>

                            <c:if test="${status==0||status==1}">
                                <shiro:hasPermission name="applySelf:edit">
                                    <button class="jqOpenViewBtn btn btn-success btn-sm"
                                            data-url="${ctx}/applySelf_change" data-open-by="page">
                                        <i class="fa fa-edit"></i> 行程变更
                                    </button>
                                </shiro:hasPermission>
                            </c:if>
                            <button class="jqOpenViewBtn btn btn-danger btn-sm"
                                    data-url="${ctx}/applySelfModify_page"
                                    data-id-name="applyId"
                                    data-open-by="page">
                                <i class="fa fa-search"></i> 变更记录
                            </button>

                            <button id="detailBtn" class="btn btn-warning btn-sm">
                                <i class="fa fa-info-circle"></i> 详情
                            </button>

                            <%--<button data-url="${ctx}/applySelf_view"
                                    data-open-by="page"
                                    class="jqOpenViewBtn btn btn-warning btn-sm">
                                <i class="fa fa-info-circle"></i> 详情
                            </button>--%>
                            <a class="jqExportBtn btn btn-info btn-sm tooltip-success"
                               data-rel="tooltip" data-placement="top" title="导出当前搜索的全部结果（按照当前排序）">
                                <i class="fa fa-download"></i> 导出</a>
                            <c:if test="${status>0}">
                                <button data-url="${ctx}/shortMsg_view"
                                        data-querystr="&type=applySelf"
                                        class="jqOpenViewBtn btn btn-primary btn-sm">
                                    <i class="fa fa-info-circle"></i> 短信提醒
                                </button>
                            </c:if>
                            <c:if test="${status>=0 && status!=1}">
                            <shiro:hasPermission name="applySelf:del">
                                <a class="jqBatchBtn btn btn-danger btn-sm"
                                   data-url="${ctx}/applySelf_batchDel" data-title="删除因私出国申请"
                                   data-msg="确定删除这{0}条申请记录吗？"><i class="fa fa-trash"></i> 删除</a>
                            </shiro:hasPermission>
                            </c:if>
                            <c:if test="${status==-1}">
                                <shiro:hasPermission name="applySelf:del">
                                    <a class="jqBatchBtn btn btn-success btn-sm"
                                       data-url="${ctx}/applySelf_batchUnDel" data-title="找回已删除因私出国申请"
                                       data-msg="确定恢复这{0}条申请记录吗？"><i class="fa fa-reply"></i> 恢复申请</a>
                                </shiro:hasPermission>
                                <shiro:hasPermission name="passportApply:del">
                                    <a class="jqBatchBtn btn btn-danger btn-sm"
                                       data-url="${ctx}/applySelf_doBatchDel" data-title="删除申请"
                                       data-msg="确定删除这{0}条申请记录吗（<span class='text-danger'>删除后不可以恢复，且相关数据都会删除</span>）？"><i class="fa fa-times"></i> 完全删除</a>
                                </shiro:hasPermission>
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
                                            <label>申请日期范围</label>

                                            <div class="input-group tooltip-success" data-rel="tooltip" title="申请日期范围">
                                                            <span class="input-group-addon">
                                                                <i class="fa fa-calendar bigger-110"></i>
                                                            </span>
                                                <input placeholder="请选择申请日期范围" data-rel="date-range-picker"
                                                       class="form-control date-range-picker" type="text"
                                                       name="_applyDate" value="${param._applyDate}"/>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label>出行时间范围</label>
                                            <select name="type" data-rel="select2" data-placeholder="请选择出行时间范围">
                                                <option></option>
                                                <c:forEach items="${APPLY_SELF_DATE_TYPE_MAP}" var="type">
                                                    <option value="${type.key}">${type.value}</option>
                                                </c:forEach>
                                            </select>
                                            <script>
                                                $("#searchForm select[name=type]").val('${param.type}');
                                            </script>
                                        </div>
                                        <div class="form-group">
                                            <label>是否修改</label>

                                            <div class="input-group">
                                                <select name="isModify" data-rel="select2" data-placeholder="请选择">
                                                    <option></option>
                                                    <option value="0">否</option>
                                                    <option value="1">是</option>
                                                </select>
                                                <script>
                                                    $("#searchForm select[name=isModify]").val("${param.isModify}");
                                                </script>
                                            </div>
                                        </div>
                                        <div class="clearfix form-actions center">
                                            <a class="jqSearchBtn btn btn-default btn-sm"><i class="fa fa-search"></i>
                                                查找</a>

                                            <c:if test="${_query || not empty param.sort}">&nbsp;
                                                <button type="button" class="resetBtn btn btn-warning btn-sm"
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
        <div id="item-content">
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
            data-url="${ctx}/applySelfModifyList?applyId={{=id}}"><i class="fa fa-search"></i> 查看
    </button>
</script>

<jsp:include page="/WEB-INF/jsp/common/daterangerpicker.jsp"/>
<script>
    $("#detailBtn").click(function () {
        var grid = $("#jqGrid");
        var id = grid.getGridParam("selrow");
        var ids = grid.getGridParam("selarrrow");

        if (!id || ids.length > 1) {
            SysMsg.warning("请选择一行", "提示");
            return;
        }

        var url = "${ctx}/applySelf_view?id=" + id;

        var $tr = $("[role='row'][id=" + id + "]", "#jqGrid");
        var flowNote = $tr.data("flow-node");
        if (flowNote == -1 || flowNote == 0) {
            var finish = $tr.data("finish");
            if (!finish) {
                url += "&type=approval&approvalTypeId=" + $tr.data("approval-type-id");
            }
        }

        var $container = $("#body-content");
        $container.showLoading({
            'afterShow': function () {
                setTimeout(function () {
                    $container.hideLoading();
                }, 2000);
            }
        });
        $.get(url, {}, function (html) {
            $container.hideLoading().hide();
            $("#item-content").hide().html(html).fadeIn("slow");
        })

    });

    $("#jqGrid").jqGrid({
        //forceFit:true,
        ondblClickRow: function (rowid, iRow, iCol, e) {
            $("#detailBtn").click();
        },
        url: '${ctx}/applySelf_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            {
                label: '编号', name: 'id', width: 80, formatter: function (cellvalue, options, rowObject) {
                return "S{0}".format(rowObject.id);
            }, frozen: true
            },
            {label: '申请日期', name: 'applyDate', width: 100, frozen: true, formatter: 'date', formatoptions: {newformat: 'Y-m-d'}},
            {label: '工作证号', name: 'user.code', width: 100, frozen: true},
            {
                label: '姓名', name: 'user.realname', width: 75, formatter: function (cellvalue, options, rowObject) {
                return '<a href="javascript:;" class="openView" data-url="${ctx}/cadre_view?cadreId={0}">{1}</a>'
                        .format(rowObject.cadre.id, cellvalue);
            }, frozen: true
            },
            {label: '所在单位及职务', name: 'cadre.title', width: 250, frozen: true},
            {label: '出行时间', name: 'startDate', width: 100, formatter: 'date', formatoptions: {newformat: 'Y-m-d'}},
            {label: '回国时间', name: 'endDate', width: 100, formatter: 'date', formatoptions: {newformat: 'Y-m-d'}},
            {
                label: '出行天数', name: 'code', width: 80, formatter: function (cellvalue, options, rowObject) {
                return DateDiff(rowObject.startDate, rowObject.endDate);
            }
            },
            {label: '前往国家或地区', name: 'toCountry', width: 180},
            {
                label: '因私出国（境）事由', name: 'reason', width: 200, formatter: function (cellvalue, options, rowObject) {
                return cellvalue.replace(/\+\+\+/g, ',');
            }
            },
            {
                label: '组织部初审', name: 'expiryDate', width: 100, cellattr: function (rowId, val, rowObject, cm, rdata) {
                var tdBean = rowObject.approvalTdBeanMap[-1];
                return approverTdAttrs(tdBean);
            }, formatter: function (cellvalue, options, rowObject) {
                var tdBean = rowObject.approvalTdBeanMap[-1];
                return processTdBean(tdBean)
            }
            },
            <c:forEach items="${approverTypeMap}" var="type">
            {
                label: '${type.value.name}审批', name: 'approver${type.key}', width: 150,
                cellattr: function (rowId, val, rowObject, cm, rdata) {
                    var tdBean = rowObject.approvalTdBeanMap['${type.key}'];
                    return approverTdAttrs(tdBean);
                }, formatter: function (cellvalue, options, rowObject) {
                var tdBean = rowObject.approvalTdBeanMap['${type.key}'];
                return processTdBean(tdBean)
            }
            },
            </c:forEach>
            {
                label: '组织部终审', name: 'expiryDate', width: 100, cellattr: function (rowId, val, rowObject, cm, rdata) {
                var tdBean = rowObject.approvalTdBeanMap[0];
                return approverTdAttrs(tdBean);
            }, formatter: function (cellvalue, options, rowObject) {
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
        ],
        rowattr: function (rowData, currentObj, rowId) {
            var tdType, approvalTypeId, isFinish;
            if (currentObj.flowNode == -1 || currentObj.flowNode == 0) {
                var tdBean = currentObj.approvalTdBeanMap[currentObj.flowNode];
                isFinish = currentObj.isFinish;
                approvalTypeId = tdBean!=undefined?tdBean.approvalTypeId:null;
            }

            return {
                'data-flow-node': currentObj.flowNode,
                'data-finish': isFinish,
                'data-approval-type-id': approvalTypeId
            }

        }, onCellSelect: function (rowid, iCol, cellcontent, e) {
            //console.dir(e.target)
            var applySelfId = $(e.target).data("apply-self-id");
            var approvalTypeId = $(e.target).data("approval-type-id");
            var tdType = $(e.target).data("td-type");
            if (tdType != 1 && applySelfId > 0) {
                $.getJSON("${ctx}/applySelf_approvers", {
                    applySelfId: applySelfId,
                    approvalTypeId: approvalTypeId
                }, function (ret) {
                    if (ret.success) {
                        var realnames = $.map(ret.approvers, function (item, idx) {
                            return item.realname;
                        });
                        var text = realnames.join("，");
                        if(ret.uv){
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

        $('[data-tooltip="tooltip"]').tooltip({container: 'body'});
    });
    $(window).triggerHandler('resize.jqGrid');
    _initNavGrid("jqGrid", "jqGridPager");

    function approverTdAttrs(tdBean) {

        if(tdBean==undefined) return '';

        var attrs = "data-td-type={0} data-apply-self-id={1} data-approval-type-id={2} ".format(tdBean.tdType, tdBean.applySelfId, tdBean.approvalTypeId);
        //console.log(tdBean.approvalTypeId + " " + tdBean.tdType)
        if (tdBean.approvalTypeId != -1 && tdBean.tdType == 2)
            attrs += "class='not_approval' ";
        /*    if(tdBean.tdType!=1) {
         var apprvalRealnames = [];
         for (var i in tdBean.approverList) {
         var sysUser = tdBean.approverList[i];
         apprvalRealnames.push(sysUser.realname);
         }
         attrs += "data-tooltip=\"tooltip\" title=\"S{0}：{1}\"".format(tdBean.applySelfId, apprvalRealnames.join("，"))
         }*/
        return attrs;
    }
    //初审未通过，或者终审完成，需要短信提醒
    /*    function processMsgTdBean(rowObject){
     var html = "";
     var applySelfId = rowObject.id;
     var firstTdBean = rowObject.approvalTdBeanMap[-1];
     var lastTdBean = rowObject.approvalTdBeanMap[0];
     if(firstTdBean.tdType==5 || (lastTdBean.tdType==5||lastTdBean.tdType==6)){
     html ="<button data-id=\"{0}\" " +
     "        class=\"shortMsgBtn btn btn-primary btn-xs\">\n" +
     "        <i class=\"fa fa-info-circle\"></i> 短信提醒\n" +
     "        </button>";
     html = html.format(applySelfId);
     }
     return html;
     }*/
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
            {
                html = "<button {0} class=\"openView btn {1} btn-xs\"" +
                "        data-url=\"${ctx}/applySelf_view?type=approval&id={2}&approvalTypeId={3}\">" +
                "        <i class=\"fa fa-edit\"></i> 审批" +
                "        </button>";
                html = html.format(canApproval ? "" : "disabled",
                        canApproval ? "btn-success" : "btn-default",
                        applySelfId, approvalTypeId);
            }
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

    $('[data-rel="tooltip"]').tooltip();
    $('#searchForm [data-rel="select2"]').select2();
    register_user_select($('[data-rel="select2-ajax"]'));
</script>