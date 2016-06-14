<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="jqgrid-vertical-offset buttons">
    <button class="btn btn-primary btn-mini btn-xs" onclick="_au()">
        <i class="fa fa-users"></i> 添加支部委员会
    </button>
    <a href="javascript:;" onclick="_au(1)" class="btn btn-primary btn-xs" >
        <i class="fa fa-edit"></i> 修改信息</a>

    <button onclick="_editMember()" class="btn btn-warning btn-xs">
        <i class="fa fa-user"></i> 编辑委员
    </button>

    <shiro:hasPermission name="partyMemberGroup:del">
        <a class="btn btn-danger btn-xs" onclick="_del()"><i class="fa fa-trash"></i> 删除</a>
    </shiro:hasPermission>
</div>
            <div class="space-4"></div>
<table id="jqGrid2" class="jqGrid2 table-striped"> </table>
<div id="jqGridPager2"> </div>
<script>
    $("#jqGrid2").jqGrid({
        pager:"jqGridPager2",
        url: '${ctx}/branchMemberGroup_data?callback=?&branchId=${param.branchId}',
        colModel: [
            { label: '名称',  name: 'name',align:'left',width: 400,formatter:function(cellvalue, options, rowObject){
                var str = '<span class="label label-sm label-primary arrowed-in arrowed-in-right" style="display: inline!important;"> 现任委员会</span>&nbsp;';
                return (rowObject.isPresent)?str+cellvalue:cellvalue;
            }},
            {
                label: '所属组织机构', name: 'party',  width: 450,
                formatter: function (cellvalue, options, rowObject) {
                    var party = rowObject.party;
                    var branch = rowObject.branch;
                    return party + (($.trim(branch) == '') ? '' : '-' + branch);
                }
            },
            { label: '应换届时间', align:'center', name: 'tranTime', width: 130 },
            { label: '实际换届时间', align:'center', name: 'actualTranTime', width: 130 },
            { label: '任命时间', align:'center', name: 'appointTime', width: 100 },
            { label:'发文号',  name: 'dispatchCode', width: 180},
            {  hidden:true, name: 'isPresent',formatter:function(cellvalue, options, rowObject){
                return (rowObject.isPresent)?1:0;
            }}
        ],
        rowattr: function(rowData, currentObj, rowId)
        {
            if(rowData.isPresent) {
                //console.log(rowData)
                return {'class':'success'}
            }
        }
    }).jqGrid("setFrozenColumns").on("initGrid",function(){
        $(window).triggerHandler('resize.jqGrid2');
    })

    function _au(type) {
        if(type==1) {
            var grid = $("#jqGrid2");
            var id = grid.getGridParam("selrow");
            var ids = grid.getGridParam("selarrrow")
            if (!id || ids.length > 1) {
                SysMsg.warning("请选择一行", "提示");
                return;
            }
        }
        url = "${ctx}/branchMemberGroup_au?type=view&branchId=${param.branchId}";
        if (id > 0)  url += "&id=" + id;
        loadModal(url);
    }

    function _del(){
        var grid = $("#jqGrid2");
        var id  = grid.getGridParam("selrow");
        var ids  = grid.getGridParam("selarrrow")
        if(!id || ids.length>1){
            SysMsg.warning("请选择一行", "提示");
            return ;
        }

        bootbox.confirm("确定删除该记录吗？", function (result) {
            if (result) {
                $.post("${ctx}/branchMemberGroup_del", {id: id}, function (ret) {
                    if (ret.success) {
                        _reload();
                        SysMsg.success('操作成功。', '成功');
                    }
                });
            }
        });
    }
    function _reload(){
        $("#modal").modal('hide');
        $("#view-box .tab-content").load("${ctx}/branchMemberGroup_view?${cm:encodeQueryString(pageContext.request.queryString)}");
    }

    function _editMember(){
        var grid = $("#jqGrid2");
        var id  = grid.getGridParam("selrow");
        var ids  = grid.getGridParam("selarrrow")
        if(!id || ids.length>1){
            SysMsg.warning("请选择一行", "提示");
            return ;
        }

        loadModal("${ctx}/branch_member?id="+ id);
    }

    $('[data-rel="select2"]').select2({width:300});
    $('[data-rel="tooltip"]').tooltip();
</script>