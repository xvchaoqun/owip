<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <!-- PAGE CONTENT BEGINS -->
        <div id="body-content" class="myTableDiv"
             data-url-au="${ctx}/applySelf_au"
             data-url-page="${ctx}/applySelf_page"
             data-url-export="${ctx}/applySelf_data"
             data-url-del="${ctx}/applySelf_del"
             data-url-bd="${ctx}/applySelf_batchDel"
             data-url-co="${ctx}/applySelf_changeOrder"
             data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <c:set var="_query" value="${not empty param.cadreId ||not empty param._applyDate
            ||not empty param.type || not empty param.code || not empty param.sort}"/>
            <div class="tabbable">
                <ul class="nav nav-tabs padding-12 tab-color-blue background-blue">
                    <li  class="<c:if test="${status==0}">active</c:if>">
                        <a href="?status=0"><i class="fa fa-circle-o"></i> 因私出国境申请</a>
                    </li>
                    <li  class="<c:if test="${status==1}">active</c:if>">
                        <a href="?status=1"><i class="fa fa-check"></i> 同意申请</a>
                    </li>
                    <li  class="<c:if test="${status==2}">active</c:if>">
                        <a href="?status=2"><i class="fa fa-times"></i> 不同意申请</a>
                    </li>

                    <div class="buttons pull-right" style="top: -3px; right:10px; position: relative">
                            <a class="btn btn-success btn-sm" onclick="_note('${SYS_CONFIG_APPLY_SELF_NOTE}')"><i class="fa fa-plus"></i> 申请说明</a>
                            <a class="btn btn-primary btn-sm" onclick="_note('${SYS_CONFIG_APPLY_SELF_APPROVAL_NOTE}')"><i class="fa fa-plus"></i> 审批说明</a>
                    </div>
                </ul>

                <div class="tab-content">
                    <div id="home4" class="tab-pane in active">
                        <div class="jqgrid-vertical-offset buttons">
                           <%-- <shiro:hasPermission name="applySelf:edit">
                                <a class="editBtn btn btn-success btn-sm"><i class="fa fa-plus"></i> 添加</a>
                            </shiro:hasPermission>
                            <c:if test="${status==0}">
                            <shiro:hasPermission name="applySelf:edit">
                                <button class="jqEditBtn btn btn-primary btn-sm">
                                    <i class="fa fa-edit"></i> 修改信息
                                </button>
                            </shiro:hasPermission>
                            </c:if>--%>
                            <button data-url="${ctx}/applySelf_view"
                                    data-open-by="page"
                                    class="jqOpenViewBtn btn btn-warning btn-sm">
                                <i class="fa fa-info-circle"></i> 详情
                            </button>
                            <a class="jqExportBtn btn btn-info btn-sm tooltip-success"
                               data-rel="tooltip" data-placement="top" title="导出当前搜索的全部结果（按照当前排序）">
                                <i class="fa fa-download"></i> 导出</a>
                            <c:if test="${status!=0}">
                            <button data-url="${ctx}/shortMsg_view"
                                    data-querystr="&type=applySelf"
                                    class="jqOpenViewBtn btn btn-primary btn-sm">
                                <i class="fa fa-info-circle"></i> 短信提醒
                            </button>
                                </c:if>
                                <%--<shiro:hasPermission name="applySelf:del">
                                    <a class="jqDelBtn btn btn-danger btn-sm"><i class="fa fa-trash"></i> 删除</a>
                                </shiro:hasPermission>--%>
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
                                                            <input placeholder="请选择申请日期范围" data-rel="date-range-picker" class="form-control date-range-picker" type="text" name="_applyDate" value="${param._applyDate}"/>
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
                        <table id="jqGrid" class="jqGrid"> </table>
                        <div id="jqGridPager"> </div>
                    </div>
                </div></div></div>
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
<jsp:include page="/WEB-INF/jsp/common/daterangerpicker.jsp"/>
<script>
    $("#jqGrid").jqGrid({
        //forceFit:true,
        url: '${ctx}/applySelf_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            { label: '编号', align:'center', name: 'id', width: 80 ,formatter:function(cellvalue, options, rowObject){
                return "S{0}".format(rowObject.id);
            },frozen:true},
            { label: '申请日期', align:'center', name: 'applyDate', width: 100,frozen:true },
            { label: '工作证号', align:'center', name: 'user.code', width: 100 ,frozen:true},
            { label: '姓名',align:'center', name: 'user.realname', width: 75, formatter:function(cellvalue, options, rowObject){
                return '<a href="javascript:;" class="openView" data-url="${ctx}/cadre_view?id={0}">{1}</a>'
                        .format(rowObject.cadre.id, cellvalue);
            },frozen:true  },
            { label: '所在单位及职务',  name: 'cadre.title', width: 250,frozen:true  },
            { label: '出行时间', align:'center', name: 'startDate', width: 100 },
            { label: '回国时间', align:'center', name: 'endDate', width: 100 },
            { label: '出行天数', align:'center', name: 'code', width: 80,formatter:function(cellvalue, options, rowObject){
                return DateDiff(rowObject.startDate, rowObject.endDate);
            }},
            { label:'前往国家或地区', align:'center',name: 'toCountry', width: 180},
            { label:'因私出国（境）事由', align:'center', name: 'reason', width: 200, formatter:function(cellvalue, options, rowObject){
                return cellvalue.replace(/\+\+\+/g, ',');
            }},
            { label:'组织部初审', align:'center', name: 'expiryDate', width: 100, cellattr:function(rowId, val, rowObject, cm, rdata) {
                var tdBean = rowObject.approvalTdBeanMap[-1];
                return approverTdAttrs(tdBean);
            }, formatter:function(cellvalue, options, rowObject){
                var tdBean = rowObject.approvalTdBeanMap[-1];
                return processTdBean(tdBean)
            }},
            <c:forEach items="${approverTypeMap}" var="type">
                { label:'${type.value.name}审批', align:'center', name: 'approver${type.key}', width: 150,
                    cellattr:function(rowId, val, rowObject, cm, rdata) {
                        var tdBean = rowObject.approvalTdBeanMap['${type.key}'];
                        return approverTdAttrs(tdBean);
                    }, formatter:function(cellvalue, options, rowObject){
                    var tdBean = rowObject.approvalTdBeanMap['${type.key}'];
                    return processTdBean(tdBean)
                } },
            </c:forEach>
            { label:'组织部终审', align:'center', name: 'expiryDate', width: 100 ,cellattr:function(rowId, val, rowObject, cm, rdata) {
                var tdBean = rowObject.approvalTdBeanMap[0];
                return approverTdAttrs(tdBean);
            }, formatter:function(cellvalue, options, rowObject){
                var tdBean = rowObject.approvalTdBeanMap[0];
                return processTdBean(tdBean)
            }}
        ],onCellSelect:function(rowid, iCol, cellcontent, e){
            //console.dir(e.target)
            var applySelfId = $(e.target).data("apply-self-id");
            var approvalTypeId = $(e.target).data("approval-type-id");
            var tdType = $(e.target).data("td-type");
            if(tdType!=1  && applySelfId>0){
                $.getJSON("${ctx}/applySelf_approvers", {applySelfId:applySelfId,
                    approvalTypeId:approvalTypeId},function(ret){
                    if(ret.success) {
                        var realnames = $.map(ret.approvers, function (item, idx) {
                            return item.realname;
                        });
                        $(e.target).qtip({content: {text:realnames.join("，"),title: {
                            text: '审批人',
                            button: true
                        }}, position: {
                            my: 'bottom center',
                            at: 'top center'
                        },show: true, hide: {
                            event: 'click',
                            inactive: 1500
                        }, button: 'Close' });
                    }
                });
            }
        }
    }).jqGrid("setFrozenColumns").on("initGrid",function(){

         $('[data-tooltip="tooltip"]').tooltip({container:'body'});
    });
    $(window).triggerHandler('resize.jqGrid');

    function approverTdAttrs(tdBean){
        var attrs = "data-td-type={0} data-apply-self-id={1} data-approval-type-id={2} ".format(tdBean.tdType, tdBean.applySelfId, tdBean.approvalTypeId);
        //console.log(tdBean.approvalTypeId + " " + tdBean.tdType)
        if(tdBean.approvalTypeId != -1 && tdBean.tdType==2)
            attrs += "class='not_approval' "
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
                    "        class=\"shortMsgBtn btn btn-primary btn-mini btn-xs\">\n" +
                    "        <i class=\"fa fa-info-circle\"></i> 短信提醒\n" +
                    "        </button>";
            html = html.format(applySelfId);
        }
        return html;
    }*/
    function processTdBean(tdBean){

        var applySelfId = tdBean.applySelfId;
        var approvalTypeId = tdBean.approvalTypeId;
        var type = tdBean.tdType;
        var canApproval = tdBean.canApproval;
        var html = "";
        switch (type){
            case 1: html = "-"; break;
            //not_approval
            case 2: html = ""; break;
            case 3: html = "未审批"; break;
            case 4:{
                    html = "<button {0} class=\"openView btn {1} btn-mini  btn-xs\"" +
                    "        data-url=\"${ctx}/applySelf_view?type=aproval&id={2}&approvalTypeId={3}\">" +
                    "        <i class=\"fa fa-edit\"></i> 审批" +
                    "        </button>";
                    html = html.format(canApproval ? "" : "disabled",
                            canApproval ? "btn-success" : "btn-default",
                            applySelfId, approvalTypeId);
            } break;
            case 5: html = "未通过"; break;
            case 6: html = "通过"; break;
        }

        return html;
    }

    function  _note(code){
        loadModal("${ctx}/sysConfig_au?editContent=true&code="+code, 700);
    }
    $('[data-rel="tooltip"]').tooltip();
    $('#searchForm [data-rel="select2"]').select2();
    register_user_select($('[data-rel="select2-ajax"]'));
</script>