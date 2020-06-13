<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="${cetParty!=null}">编辑</c:if><c:if test="${cetParty==null}">添加</c:if>院系级党委</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/cet/cetParty_au" autocomplete="off" disableautocomplete id="modalForm" method="post">
        <c:if test="${empty cetParty}">
            <div class="col-xs-11">
                <div id="partiesTree" style="height: 400px;">
                    <div class="block-loading"/>
                </div>
            </div>
        </c:if>
        <c:if test="${not empty cetParty}">
            <input type="hidden" name="id" value="${cetParty.id}">
            <div class="form-group">
                <label class="col-xs-3 control-label">二级党委</label>
                <div class="col-xs-6">
                    <select name="partyId" data-rel="select2-ajax" data-ajax-url="${ctx}/party_selects?del=0"
                            data-placeholder="请选择党委">
                        <option value="${party.id}" delete="${party.isDeleted}">${party.name}</option>
                    </select>
                    <script>
                        $.register.del_select($("#modalForm select[name=partyId]"), 350)
                    </script>
                </div>
            </div>
            <div class="form-group">
                <label class="col-xs-3 control-label">二级党委名称</label>
                <div class="col-xs-6">
                    <input class="form-control" style="width: 350px" type="text" name="name" value="${cetParty.name}">
                </div>
            </div>
        </c:if>
    </form>
</div>
<div class="modal-footer">
    <c:if test="${empty cetParty}">
        <div class="pull-left">
            <input type="button" id="partyObjsSelectAll" class="btn btn-success btn-xs" value="二级党委全选"/>
            <input type="button" title="已添加过的二级党委除外" id="partObjsDeselectAll" class="btn btn-danger btn-xs" value="二级党委全不选"/>
        </div>
        <div class="pull-right">
    </c:if>
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <button id="submitBtn" class="btn btn-primary"><i class="fa fa-check"></i> <c:if test="${cetParty!=null}">确定</c:if><c:if test="${cetParty==null}">添加</c:if></button>
    <c:if test="${empty cetParty}">
        </div>
    </c:if>
</div>
<script>
    <c:if test="${empty cetParty}">
        $.getJSON("${ctx}/cet/selectparties_tree",{},function(data){
            var treeData = data.tree;

            $("#partiesTree").dynatree({
                checkbox: true,
                selectMode: 3,
                children: treeData,
                onSelect: function(select, node) {

                    node.expand(node.data.isFolder && node.isSelected());
                },
                cookieId: "dynatree-Cb3",
                idPrefix: "dynatree-Cb3-"
            });
        });

        $("#partyObjsSelectAll").click(function(){
            $("#partiesTree").dynatree("getRoot").visit(function(node){
                node.select(true);
            });
            return false;
        });
        $("#partObjsDeselectAll").click(function(){
            $("#partiesTree").dynatree("getRoot").visit(function(node){
                if(!node.data.isFolder && !node.data.unselectable)
                    node.select(false);
            });
            return false;
        });
    </c:if>
    <c:if test="${not empty cetParty}">
        $("#modalForm select[name=partyId]").on("change", function () {
            $("#modalForm input[name=name]").val($("#modalForm select[name=partyId] option:selected").text());
        })
    </c:if>
    
    $("#submitBtn").click(function(){$("#modalForm").submit();return false;});
    $("#modalForm").validate({
        submitHandler: function (form) {
            <c:if test="${empty cetParty}">
                var partyIds = $.map($("#partiesTree").dynatree("getSelectedNodes"), function(node){
                    if(!node.data.isFolder && !node.data.unselectable)
                        return node.data.key;
                });
                //console.log(partyIds.length);
                if (partyIds.length==0)
                    partyIds.push(0);
            //console.log(partyIds.length);
            </c:if>
            $(form).ajaxSubmit({
                <c:if test="${empty cetParty}">
                    data: {partyIds: partyIds},
                </c:if>
                success:function(ret){
                    if(ret.success){
                        $("#modal").modal('hide');
                        $("#jqGrid").trigger("reloadGrid");
                    }
                }
            });
        }
    });
</script>