<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <!-- PAGE CONTENT BEGINS -->
        <div id="body-content" class="myTableDiv"
             data-url-au="${ctx}/user/applySelf_au"
             data-url-page="${ctx}/user/applySelf_page"
             data-url-del="${ctx}/user/applySelf_del"
             data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
                <div class="buttons">
                    <a class="openView btn btn-success btn-sm" data-url="${ctx}/user/applySelf_au"><i class="fa fa-plus"></i> 申请因私出国（境）</a>
                    <a id="note" class="btn btn-info btn-sm"><i class="fa fa-info-circle"></i> 申请说明</a>
                    <button class="jqOpenViewBtn btn btn-warning btn-sm"
                            data-url="${ctx}/user/applySelf_view"
                            data-open-by="page">
                        <i class="fa fa-info-circle"></i> 详情
                    </button>
                        <button class="jqEditBtn btn btn-primary btn-sm"
                                data-url="${ctx}/user/applySelf_au"
                                data-open-by="page"
                                data-querystr="&edit=1">
                            <i class="fa fa-edit"></i> 重新申请
                        </button>
                            <button class="jqItemDelBtn btn btn-danger btn-sm">
                            <i class="fa fa-trash"></i> 删除
                            </button>
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
        url: '${ctx}/user/applySelf_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            { label: '编号', align:'center', name: 'id', width: 80 ,frozen:true,formatter:function(cellvalue, options, rowObject){
                return "S{0}".format(rowObject.id);
            }},
            { label: '申请日期', align:'center', name: 'applyDate', width: 100 ,frozen:true},
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
            }}
        ],
        onSelectRow: function(id,status){
            jgrid_sid=id;
            var firstType = $(this).getRowData(id).firstType;
            $(".jqEditBtn, .jqItemDelBtn").prop("disabled",status && firstType!=3&&firstType!=4)
        }
    }).jqGrid("setFrozenColumns").on("initGrid",function(){
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
            case 3,4: html = "未审批"; break;
            case 5: html = "未通过"; break;
            case 6: html = "通过"; break;
        }

        return html;
    }

    $("#note").click(function(){
        loadModal("${ctx}/user/applySelf_note", 650);
    });
</script>