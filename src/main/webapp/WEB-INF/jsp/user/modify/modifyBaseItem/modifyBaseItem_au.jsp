<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>编辑字段</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/user/modifyBaseItem_au"
          id="modalForm" method="post" enctype="multipart/form-data">
            <input type="hidden" name="id" value="${record.id}">
			<div class="form-group">
				<label class="col-xs-3 control-label">${record.name}</label>
				<div class="col-xs-6">
                    <c:choose>
                        <c:when test="${record.type==MODIFY_BASE_ITEM_TYPE_STRING}">
                            <input required class="form-control" type="text" name="modifyValue" value="${record.modifyValue}">
                        </c:when>
                        <c:when test="${record.type==MODIFY_BASE_ITEM_TYPE_DATE}">
                            <div class="input-group" style="width: 150px">
                                <input class="form-control date-picker" type="text" name="modifyValue" data-date-format="yyyy-mm-dd" value="${record.modifyValue}"/>
                                <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                            </div>
                            <script>
                                register_date($('.date-picker'));
                            </script>
                        </c:when>
                        <c:when test="${record.type==MODIFY_BASE_ITEM_TYPE_IMAGE}">
                            <div   style="width:170px">
                            <input type="file" name="_avatar" id="_avatar"/>
                            </div>
                            <script>
                                $("#_avatar").ace_file_input({
                                    style:'well',
                                    btn_choose:'更换头像',
                                    btn_change:null,
                                    no_icon:'ace-icon fa fa-picture-o',
                                    thumbnail:'large',
                                    droppable:true,
                                    previewWidth: 143,
                                    previewHeight: 198,
                                    allowExt: ['jpg', 'jpeg', 'png', 'gif'],
                                    allowMime: ['image/jpg', 'image/jpeg', 'image/png', 'image/gif']
                                })
                                $("#_avatar").ace_file_input('show_file_list', [{type: 'image', name: '${ctx}/avatar?path=${fn:replace(record.modifyValue, '\\', '\\\\')}'}]);
                            </script>
                        </c:when>
                    </c:choose>
				</div>
			</div>


    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <input type="submit" class="btn btn-primary" value="确定"/>
</div>
<script>

    $("#modal form").validate({
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
                        $("#modal").modal('hide');
                        $("#jqGrid2").trigger("reloadGrid");
                    }
                }
            });
        }
    });

   // register_date($('.date-picker'));
</script>