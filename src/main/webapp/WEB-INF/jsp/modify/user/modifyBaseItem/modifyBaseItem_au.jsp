<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<%@ include file="/WEB-INF/jsp/modify/constants.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>编辑字段</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/user/modifyBaseItem_au"
          autocomplete="off" disableautocomplete id="modalForm" method="post" enctype="multipart/form-data">
            <input type="hidden" name="id" value="${record.id}">
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>${record.name}</label>
				<div class="col-xs-6">
                    <c:choose>
                        <c:when test="${record.type==MODIFY_BASE_ITEM_TYPE_INT && record.code=='health'}">
                            <select required data-rel="select2" name="modifyValue"
                                    data-placeholder="请选择" data-width="162">
                                <option></option>
                                <c:import url="/metaTypes?__code=mc_health"/>
                            </select>
                            <script type="text/javascript">
                                $("select[name=modifyValue]").val('${record.modifyValue}');
                            </script>
                        </c:when>
                        <c:when test="${record.type==MODIFY_BASE_ITEM_TYPE_INT && record.code=='political_status'}">
                            <select required data-rel="select2" name="modifyValue"
                                    data-placeholder="请选择" data-width="162">
                                <option></option>
                                <option value="0">中共党员</option>
				                <jsp:include page="/metaTypes?__code=mc_democratic_party"/>
                            </select>
                            <script type="text/javascript">
                                $("select[name=modifyValue]").val('${record.modifyValue}');
                            </script>
                        </c:when>
                        <c:when test="${record.type==MODIFY_BASE_ITEM_TYPE_STRING}">
                            <input required class="form-control" type="text" name="modifyValue" value="${record.modifyValue}">
                        </c:when>
                        <c:when test="${record.type==MODIFY_BASE_ITEM_TYPE_DATE}">
                            <c:if test="${record.code=='work_time'}">
                            <div class="input-group" style="width: 150px">
                                <input class="form-control date-picker" type="text" name="modifyValue"
                                       data-date-min-view-mode="1"
                                       data-date-format="yyyy.mm" value="${record.modifyValue}"/>
                                <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                            </div>
                            </c:if>
                            <c:if test="${record.code!='work_time'}">
                            <div class="input-group" style="width: 150px">
                                <input class="form-control date-picker" type="text" name="modifyValue"
                                       data-date-format="yyyy-mm-dd" value="${record.modifyValue}"/>
                                <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                            </div>
                            </c:if>
                            <script>
                                $.register.date($('.date-picker'));
                            </script>
                        </c:when>
                        <c:when test="${record.type==MODIFY_BASE_ITEM_TYPE_IMAGE}">
                            <div   style="width:170px">
                            <input type="file" name="_avatar" id="_avatar"/>
                            </div>
                            <script>
                                $.fileInput($("#_avatar"), {
                                    style:'well',
                                    btn_choose:'更换头像',
                                    btn_change:null,
                                    no_icon:'ace-icon fa fa-picture-o',
                                    thumbnail:'large',
                                    maxSize:${_uploadMaxSize},
                                    droppable:true,
                                    previewWidth: 143,
                                    previewHeight: 198,
                                    allowExt: ['jpg', 'jpeg', 'png', 'gif'],
                                    allowMime: ['image/jpg', 'image/jpeg', 'image/png', 'image/gif']
                                })
                                $("#_avatar").ace_file_input('show_file_list', [{type: 'image',
                                    name: '${ctx}/avatar?path=${cm:encodeURI(record.modifyValue)}'}]);
                            </script>
                        </c:when>
                    </c:choose>
				</div>
			</div>


    </form>
</div>
<div class="modal-footer">
    <a href="javascript:;" data-dismiss="modal" class="btn btn-default">取消</a>
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
    $('[data-rel="select2"]').select2();
   // $.register.date($('.date-picker'));
</script>