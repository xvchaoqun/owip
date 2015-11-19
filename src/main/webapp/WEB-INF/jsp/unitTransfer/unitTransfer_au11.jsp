<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="${unitTransfer!=null}">编辑</c:if><c:if test="${unitTransfer==null}">添加</c:if>单位变更</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/unitTransfer_au" id="modalForm" method="post">
        <input type="hidden" name="id" value="${unitTransfer.id}">
        <div class="form-group">
            <label class="col-xs-3 control-label">所属单位</label>
            <div class="col-xs-6">
                <select required class="form-control" name="unitId" data-rel="select2" data-placeholder="请选择所属单位">
                    <option></option>
                    <c:forEach items="${unitMap}" var="unit">
                        <option value="${unit.key}">${unit.value.name}</option>
                    </c:forEach>
                </select>
                <script>
                    $("#modalForm select[name=unitId]").val('${unitTransfer.unitId}');
                </script>
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label">文件主题</label>
            <div class="col-xs-6">
                <input required class="form-control" type="text" name="subject" value="${unitTransfer.subject}">
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label">文件具体内容</label>
            <div class="col-xs-6">
                <textarea class="form-control" name="content" rows="10">${unitTransfer.content}</textarea>
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label">日期</label>
            <div class="col-xs-6">
                <div  class="input-group">
                    <input required class="form-control date-picker" name="_pubTime" type="text"
                           data-date-format="yyyy-mm-dd" value="${cm:formatDate(unitTransfer.pubTime,'yyyy-MM-dd')}" />
                    <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                </div>
            </div>
        </div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <input type="submit" class="btn btn-primary" value="<c:if test="${unitTransfer!=null}">确定</c:if><c:if test="${unitTransfer==null}">添加</c:if>"/>
</div>

<script>

    $('.date-picker').datepicker({
        language:"zh-CN",
        autoclose: true,
        todayHighlight: true
    })

    $("#modal form").validate({
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
                        page_reload();
                        toastr.success('操作成功。', '成功');
                    }
                }
            });
        }
    });
    $('[data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
    $('[data-rel="select2-ajax"]').select2({
        ajax: {
            dataType: 'json',
            delay: 200,
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
        }
    });
</script>