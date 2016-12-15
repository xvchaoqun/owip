<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row footer-margin">
<h3>
    ${empty htmlFragment?"添加系统说明":htmlFragment.title}
</h3>
<hr/>
    <form class="form-horizontal" action="${ctx}/htmlFragment_au" id="modalForm" method="post">
        <input type="hidden" name="id" value="${htmlFragment.id}">
        <c:if test="${empty param.editContent}">
            <div class="form-group">
                <label class="col-xs-3 control-label">
                    父节点
                </label>
                <div class="col-xs-6 ">
                    <select name="fid"  data-width="350">
                        <option value="${htmlFragment.parent.id}">${htmlFragment.parent.title}</option>
                    </select>
                </div>
            </div>
        <div class="form-group">
            <label class="col-xs-3 control-label">名称</label>
            <div class="col-xs-6" style="width: 370px">
                <input required class="form-control" type="text" name="title" value="${htmlFragment.title}">
            </div>
        </div>
        <shiro:hasRole name="admin">
            <div class="form-group">
                <label class="col-xs-3 control-label">代码</label>

                <div class="col-xs-6" style="width: 370px">
                    <input class="form-control" type="text" name="code" value="${htmlFragment.code}">
                </div>
            </div>
        </shiro:hasRole>
        <div class="form-group">
            <label class="col-xs-3 control-label">备注</label>
            <div class="col-xs-6" style="width: 370px">
                <textarea name="remark" class="form-control">${htmlFragment.remark}</textarea>
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label">排序</label>
            <div class="col-xs-6 " style="width: 370px">
                <input class="form-control digits" type="text" name="sortOrder" value="${htmlFragment.sortOrder}">
            </div>
        </div>
        </c:if>
        <div class="form-group">
            <div class="col-xs-6 col-xs-offset-3">
                <textarea id="content">
                    ${htmlFragment.content}
                </textarea>
                <input type="hidden" name="content">
              </div>
        </div>

    </form>
<div class="clearfix form-actions">
    <div class="col-md-offset-3 col-md-9">
        <button class="btn btn-info" type="submit">
            <i class="ace-icon fa fa-check bigger-110"></i>
            <c:if test="${htmlFragment!=null}">确定</c:if><c:if test="${htmlFragment==null}">添加</c:if>
        </button>

        &nbsp; &nbsp; &nbsp;
        <button class="closeView btn" type="button">
            <i class="ace-icon fa fa-undo bigger-110"></i>
            取消
        </button>
    </div>
</div>
</div>
<script type="text/javascript" src="${ctx}/extend/ke4/kindeditor-all-min.js"></script>
<script>

    $("#modalForm select[name=fid]").select2({
        ajax: {
            url: "${ctx}/htmlFragment_selects",
            dataType: 'json',
            delay: 300,
            data: function (params) {
                return {
                    searchStr: params.term,
                    pageSize: 10,
                    pageNo: params.page
                };
            },
            processResults: function (data, params) {
                params.page = params.page || 1;
                return {results: data.options,  pagination: {
                    more: (params.page * 10) < data.totalCount
                }};
            },
            cache: true
        },
        dropdownCssClass: "bigdrop",
        placeholder: "请选择上级对象"
    });

    var ke = KindEditor.create('#content', {
        allowFileManager : true,
        uploadJson : '${ctx}/ke/upload_json',
        fileManagerJson : '${ctx}/ke/file_manager_json',
        height: '500px',
        width: '800px'
    });

    jgrid_left = $("#jqGrid").closest(".ui-jqgrid-bdiv").scrollLeft();
    jgrid_top = $("#jqGrid").closest(".ui-jqgrid-bdiv").scrollTop();
    $("#item-content button[type=submit]").click(function(){$("#modalForm").submit(); return false;});
    $("#modalForm").validate({
        submitHandler: function (form) {

            $("#modalForm input[name=content]").val(ke.html());

            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
                        $("#jqGrid").trigger("reloadGrid");
                        $(".closeView").click();
                    }
                }
            });
        }
    });

</script>