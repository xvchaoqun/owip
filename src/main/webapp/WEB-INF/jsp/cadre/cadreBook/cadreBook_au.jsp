<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="${cadreBook!=null}">编辑</c:if><c:if test="${cadreBook==null}">添加</c:if>出版著作情况</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/cadreBook_au" id="modalForm" method="post">
        <input type="hidden" name="id" value="${cadreBook.id}">
        <input type="hidden" name="cadreId" value="${cadre.id}">
        <div class="form-group">
            <label class="col-xs-3 control-label">所属干部</label>
            <div class="col-xs-6 label-text">
                ${sysUser.realname}
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label">出版日期</label>
            <div class="col-xs-6">
                <div class="input-group" style="width: 120px">
                    <input required class="form-control date-picker" name="_pubTime" type="text"
                           data-date-format="yyyy-mm-dd" value="${cm:formatDate(cadreBook.pubTime,'yyyy-MM-dd')}" />
                    <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                </div>
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label">著作名称</label>
            <div class="col-xs-6">
                <input required class="form-control" type="text" name="name" value="${cadreBook.name}">
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label">类型</label>
            <div class="col-xs-6">
                <select required data-rel="select2" name="type"
                        data-placeholder="请选择" data-width="162">
                    <option></option>
                    <c:forEach items="${CADRE_BOOK_TYPE_MAP}" var="type">
                        <option value="${type.key}">${type.value}</option>
                    </c:forEach>
                </select>
                <script type="text/javascript">
                    $("#modal form select[name=type]").val(${cadreBook.type});
                </script>
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label">出版社</label>
            <div class="col-xs-6">
                <input required class="form-control" type="text" name="publisher" value="${cadreBook.publisher}">
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label">备注</label>
            <div class="col-xs-6">
                <textarea class="form-control" name="remark">${cadreBook.remark}</textarea>
            </div>
        </div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <input type="submit" class="btn btn-primary" value="<c:if test="${cadreBook!=null}">确定</c:if><c:if test="${cadreBook==null}">添加</c:if>"/>
</div>

<script>
    register_date($('.date-picker'));
    $('#modalForm input[type=file]').ace_file_input({
        no_file:'请选择文件 ...',
        btn_choose:'选择',
        btn_change:'更改',
        droppable:false,
        onchange:null,
        thumbnail:false //| true | large
        //whitelist:'gif|png|jpg|jpeg'
        //blacklist:'exe|php'
        //onchange:''
        //
    });

    $("#modal form").validate({
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
                        $("#modal").modal("hide");
                        $("#jqGrid_cadreBook").trigger("reloadGrid");
                    }
                }
            });
        }
    });
    $('#modalForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>