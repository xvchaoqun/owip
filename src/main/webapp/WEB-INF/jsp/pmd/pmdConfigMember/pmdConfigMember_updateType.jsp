<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="${pmdConfigMember!=null}">编辑</c:if><c:if test="${pmdConfigMember==null}">添加</c:if>党员缴费分类</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/pmd/pmdConfigMember_updateType" id="modalForm" method="post">
        <input type="hidden" name="id" value="${pmdConfigMember.id}">
        <div class="form-group">
            <label class="col-xs-3 control-label">党员</label>
            <div class="col-xs-6 label-text">
            ${pmdConfigMember.user.realname}
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label">党员类别</label>
            <div class="col-xs-6">
            <select data-rel="select2" name="configMemberType"
                    data-placeholder="请选择">
                <option></option>
                <c:forEach items="${PMD_MEMBER_TYPE_MAP}" var="_type">
                    <option value="${_type.key}">${_type.value}</option>
                </c:forEach>
            </select>
            <script type="text/javascript">
                $("#modalForm select[name=configMemberType]").val(${pmdConfigMember.configMemberType});
            </script>
                </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label">党员分类别</label>
            <div class="col-xs-6">
            <select data-rel="select2" name="configMemberTypeId"
                    data-placeholder="请选择">
                <option></option>
            </select>
            </div>
        </div>

    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <button id="submitBtn" class="btn btn-primary"><i class="fa fa-check"></i> <c:if test="${pmdConfigMember!=null}">确定</c:if><c:if test="${pmdConfigMember==null}">添加</c:if></button>
</div>

<script>
    var typeMap = ${cm:toJSONObject(typeMap)};
    $("select[name=configMemberType]").change(function () {
        var $this = $(this);
        var configMemberType = $this.val();
        if ($.trim(configMemberType) != '') {
            var types = typeMap[configMemberType];
            //console.log(types);
            var $configMemberTypeId = $("#modalForm select[name=configMemberTypeId]")
                    .empty().prepend("<option></option>").select2();
            for (i in types) {
                var selected = (types[i].id == '${param.configMemberTypeId}');
                $configMemberTypeId.append(new Option(types[i].name, types[i].id, selected, selected))
            }
            $configMemberTypeId.trigger('change');
        }
    }).change();

    $("#submitBtn").click(function(){$("#modalForm").submit();return false;});
    $("#modalForm").validate({
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
                        $("#modal").modal('hide');
                        $("#jqGrid").trigger("reloadGrid");
                    }
                }
            });
        }
    });
    $("#modalForm :checkbox").bootstrapSwitch();
    $('#modalForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>