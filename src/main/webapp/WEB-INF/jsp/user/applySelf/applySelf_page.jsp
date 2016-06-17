<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <!-- PAGE CONTENT BEGINS -->
        <div id="body-content" class="myTableDiv"
             data-url-au="${ctx}/user/applySelf_au"
             data-url-page="${ctx}/user/applySelf_page"
             data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <c:set var="_query" value="${not empty param._applyDate
            ||not empty param.type }"/>
                <div class="buttons">
                    <a class="openView btn btn-success btn-sm" data-url="${ctx}/user/applySelf_au"><i class="fa fa-plus"></i> 申请因私出国（境）</a>
                    <a id="note" class="btn btn-info btn-sm"><i class="fa fa-info-circle"></i> 申请说明</a>
                    <button class="jqOpenViewBtn btn btn-warning btn-sm"
                            data-url="${ctx}/user/applySelf_view"
                            data-open-by="page">
                        <i class="fa fa-info-circle"></i> 详情
                    </button>
                        <button id="editBtn" class="jqEditBtn btn btn-primary btn-sm tooltip-info"
                                data-url="${ctx}/user/applySelf_au"
                                data-open-by="page"
                                data-querystr="&edit=1"
                                data-rel="tooltip" data-placement="bottom"
                                title="当因私出国（境）申请未审批或者初审未通过时，修改相关信息重新申请。">
                            <i class="fa fa-edit"></i> 重新申请
                        </button>
                            <button id="abolishBtn" class="jqItemBtn btn btn-danger btn-sm"
                                    data-url="${ctx}/user/applySelf_del" data-title="撤销申请"
                                    data-msg="确定撤销该申请吗？">
                            <i class="fa fa-trash"></i> 撤销申请
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
                                <a class="jqSearchBtn btn btn-default btn-sm"><i class="fa fa-search"></i> 查找</a>

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
        <div id="item-content">
        </div>
    </div>
</div>
<jsp:include page="/WEB-INF/jsp/common/daterangerpicker.jsp"/>
<script>
    $("#jqGrid").jqGrid({
        //forceFit:true,
        ondblClickRow : function(rowid,iRow,iCol,e){
            $(".jqOpenViewBtn").click();
        },
        url: '${ctx}/user/applySelf_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            { label: '编号', align:'center', name: 'id', width: 80 ,formatter:function(cellvalue, options, rowObject){
                return "S{0}".format(rowObject.id);
            },frozen:true},
            { label: '申请日期', align:'center', name: 'applyDate', width: 100,frozen:true },
            { label: '出行时间', align:'center', name: 'typeName', width: 100 },
            { label: '出发时间', align:'center', name: 'startDate', width: 100 },
            { label: '返回时间', align:'center', name: 'endDate', width: 100 },
            { label: '出行天数', align:'center', name: 'day', width: 80,formatter:function(cellvalue, options, rowObject){
                return DateDiff(rowObject.startDate, rowObject.endDate);
            }},
            { label:'前往国家或地区', align:'center',name: 'toCountry', width: 180},
            { label:'事由', align:'center', name: 'reason', width: 200, formatter:function(cellvalue, options, rowObject){
                return cellvalue.replace(/\+\+\+/g, ',');
            }},
            { label:'组织部初审', align:'center', name: 'approver-1', width: 100, formatter:function(cellvalue, options, rowObject){
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
            { label:'组织部终审', align:'center', name: 'approver0', width: 100 ,cellattr:function(rowId, val, rowObject, cm, rdata) {
                var tdBean = rowObject.approvalTdBeanMap[0];
                if(tdBean.tdType==2)
                    return "class='not_approval'"
            }, formatter:function(cellvalue, options, rowObject){
                var tdBean = rowObject.approvalTdBeanMap[0];
                return processTdBean(tdBean)
            }},
            {hidden:true, name:'firstType',formatter:function(cellvalue, options, rowObject) {
                var tdBean = rowObject.approvalTdBeanMap[-1];
                return tdBean.tdType
            }},
            {hidden:true, name:'isFinish',formatter:function(cellvalue, options, rowObject) {
                return cellvalue?1:0;
            }},
            {hidden:true, name:'isAgreed',formatter:function(cellvalue, options, rowObject) {
                return cellvalue?1:0;
            }}
        ],
        onSelectRow: function(id,status){
            jgrid_sid=id;
            var data = $(this).getRowData(id);
            //console.log(status + "  " +  data.isFinish + "  " +  data.isAgreed + "  " + (data.isAgreed==0))

            //$("#editBtn").prop("disabled",status &&data.isFinish==1&&data.isAgreed!=0)
            var firstType = data.firstType;
            //console.log(firstType)
            $("#abolishBtn").prop("disabled",status && firstType!=3&&firstType!=4)
            $("#editBtn").prop("disabled",status && firstType!=3&&firstType!=4&&firstType!=5)
        }
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid');

    $('[data-rel="tooltip"]').tooltip();
    $('#searchForm [data-rel="select2"]').select2();
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
            case 3:
            case 4: html = "未审批"; break;
            case 5: html = "未通过"; break;
            case 6: html = "通过"; break;
        }

        return html;
    }

    $("#note").click(function(){
        loadModal("${ctx}/user/applySelf_note", 650);
    });
</script>