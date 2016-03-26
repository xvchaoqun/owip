<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <!-- PAGE CONTENT BEGINS -->
        <div id="body-content" class="myTableDiv"
             data-url-page="${ctx}/applySelfList_page"
             data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <c:set var="_query" value="${not empty param.cadreId ||not empty param._applyDate
            ||not empty param.type || not empty param.code || not empty param.sort}"/>
            <div class="tabbable">
                <ul class="nav nav-tabs padding-12 tab-color-blue background-blue">
                    <li  class="<c:if test="${status==0}">active</c:if>">
                        <a href="?status=0"><i class="fa fa-circle-o"></i> 待审批</a>
                    </li>
                    <li  class="<c:if test="${status==1}">active</c:if>">
                        <a href="?status=1"><i class="fa fa-check"></i> 已审批</a>
                    </li>
                </ul>

                <div class="tab-content">
                    <div id="home4" class="tab-pane in active">
                        <div class="jqgrid-vertical-offset buttons">
                            <button data-url="${ctx}/applySelf_view"
                                    data-open-by="page"
                                    class="jqOpenViewBtn btn btn-warning btn-sm">
                                <i class="fa fa-info-circle"></i> 详情
                            </button>
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
                                    <mytag:sort-form css="form-horizontal " id="searchForm">
                                        <div class="row">
                                            <div class="col-xs-4">
                                                <div class="form-group">
                                                    <label class="col-xs-3 control-label">姓名</label>
                                                    <div class="col-xs-6">
                                                        <div class="input-group">
                                                            <input type="hidden" name="status" value="${status}">
                                                            <select data-rel="select2-ajax" data-ajax-url="${ctx}/cadre_selects"
                                                                    name="cadreId" data-placeholder="请输入账号或姓名或学工号">
                                                                <option value="${cadre.id}">${sysUser.realname}</option>
                                                            </select>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="col-xs-4">
                                                <div class="form-group">
                                                    <label class="col-xs-3 control-label">申请日期范围</label>
                                                    <div class="col-xs-6">
                                                        <div class="input-group tooltip-success" data-rel="tooltip" title="申请日期范围">
                                                            <span class="input-group-addon">
                                                                <i class="fa fa-calendar bigger-110"></i>
                                                            </span>
                                                            <input placeholder="请选择申请日期范围" data-rel="date-range-picker" class="form-control date-range-picker" type="text" name="_applyDate" value="${param._applyDate}"/>
                                                        </div>
                                                    </div>
                                                </div>

                                            </div>
                                            <div class="col-xs-4">
                                                <div class="form-group">
                                                    <label class="col-xs-4 control-label">出行时间范围</label>
                                                    <div class="col-xs-6">
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
                                                </div>
                                            </div>

                                        </div>
                                        <div class="clearfix form-actions center">
                                            <a class="searchBtn btn btn-default btn-sm"><i class="fa fa-search"></i> 查找</a>

                                            <c:if test="${_query || not empty param.sort}">&nbsp;
                                                <button type="button" class="resetBtn btn btn-warning btn-sm" data-querystr="status=${status}">
                                                    <i class="fa fa-reply"></i> 重置
                                                </button>
                                            </c:if>
                                        </div>
                                    </mytag:sort-form>
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
<jsp:include page="/WEB-INF/jsp/common/daterangerpicker.jsp"/>
<script>
    $("#jqGrid").jqGrid({
        //forceFit:true,
        url: '${ctx}/applySelfList_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            { label: '申请日期', align:'center', name: 'applyDate', width: 100 ,frozen:true},
            { label: '工作证号', align:'center', name: 'user.code', width: 100 ,frozen:true},
            { label: '姓名',align:'center', name: 'user.realname',resizable:false, width: 75 ,frozen:true },
            { label: '所在单位及职务',  name: 'cadre.title', width: 250 },
            { label: '出行时间', align:'center', name: 'startDate', width: 100 },
            { label: '回国时间', align:'center', name: 'endDate', width: 100 },
            { label: '出行天数', align:'center', name: 'code', width: 80,formatter:function(cellvalue, options, rowObject){
                return DateDiff(rowObject.startDate, rowObject.endDate);
            }},
            { label:'前往国家或地区', align:'center',name: 'toCountry', width: 180},
            { label:'因私出国（境）事由', align:'center', name: 'reason', width: 200, formatter:function(cellvalue, options, rowObject){
                return cellvalue.replace('+++', ',');
            }},
            { label:'组织部初审', align:'center', name: 'expiryDate', width: 100, formatter:function(cellvalue, options, rowObject){
                var tdBean = rowObject.approvalTdBeanMap[-1];
                return processTdBean(tdBean)
            }},
            <c:forEach items="${approverTypeMap}" var="type">
                { label:'${type.value.name}审批', align:'center', name: 'approver${type.key}', width: 150,
                    cellattr:function(rowId, val, rowObject, cm, rdata) {
                        var tdBean = rowObject.approvalTdBeanMap['${type.key}'];
                        if(tdBean.tdType==2)
                            return "class='not_approval'"
                    }, formatter:function(cellvalue, options, rowObject){
                    var tdBean = rowObject.approvalTdBeanMap['${type.key}'];
                    return processTdBean(tdBean)
                } },
            </c:forEach>
            { label:'组织部终审', align:'center', name: 'expiryDate', width: 100 ,cellattr:function(rowId, val, rowObject, cm, rdata) {
                var tdBean = rowObject.approvalTdBeanMap[0];
                if(tdBean.tdType==2)
                    return "class='not_approval'"
            }, formatter:function(cellvalue, options, rowObject){
                var tdBean = rowObject.approvalTdBeanMap[0];
                return processTdBean(tdBean)
            }}
        ]}).jqGrid("setFrozenColumns").on("initGrid",function(){

        $(".approvalBtn").click(function(){
            loadModal("${ctx}/applySelf_approval?applySelfId="+ $(this).data("id") +"&approvalTypeId="+ $(this).data("approvaltypeid"));
        });
    });
    $(window).triggerHandler('resize.jqGrid');

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
                if(approvalTypeId==-1){
                    html = "<button {0} class=\"openView btn {1} btn-mini  btn-xs\"" +
                    "        data-url=\"${ctx}/applySelf_view?type=aproval&id={2}&approvalTypeId={3}\">" +
                    "        <i class=\"fa fa-edit\"></i> 审批" +
                    "        </button>";
                    html = html.format(canApproval ? "" : "disabled", canApproval ? "btn-success" : "btn-default", applySelfId, approvalTypeId);
                }else {
                    html = "<button {0} class=\"approvalBtn btn {1} btn-mini  btn-xs\"" +
                    "        data-id=\"{2}\" data-approvaltypeid=\"{3}\">" +
                    "        <i class=\"fa fa-edit\"></i> 审批" +
                    "        </button>";
                    html = html.format(canApproval ? "" : "disabled", canApproval ? "btn-success" : "btn-default", applySelfId, approvalTypeId);
                }
                //console.log("html=" + html)
            } break;
            case 5: html = "未通过"; break;
            case 6: html = "通过"; break;
        }

        return html;
    }

    function  _note(){
        loadModal("${ctx}/applySelf_note", 650);
    }


    $('#searchForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
    register_user_select($('[data-rel="select2-ajax"]'));
</script>