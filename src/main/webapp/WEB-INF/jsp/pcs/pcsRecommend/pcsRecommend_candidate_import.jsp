<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<%@ include file="../constants.jsp" %>
  <div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>批量导入</h3>
  </div>
  <div class="modal-body">
    <form class="form-horizontal" autocomplete="off" disableautocomplete id="modalForm"
          enctype="multipart/form-data" action="${ctx}/pcs/pcsRecommend_candidate_import?partyId=${param.partyId}" method="post">
		<div class="form-group">
			<label class="col-xs-3 control-label"><span class="star">*</span>Excel文件</label>
			<div class="col-xs-6">
				<input class="form-control" type="file" name="xlsx" required extension="xlsx"/>
			</div>
		</div>
        </form>
        <div class="well">
        <span class="help-inline">导入的文件请严格按照
            <a href="${ctx}/attach?code=sample_pcsRecommend_Candidate">
                两委委员名单录入样表.xlsx</a>（点击下载）的数据格式</span>
        </div>
  </div>
  <div class="modal-footer">
  <a href="javascript:;" data-dismiss="modal" class="btn btn-default">取消</a>
	  <button id="importBtn" type="button" class="btn btn-primary"
			  data-loading-text="<i class='fa fa-spinner fa-spin '></i> 提交中，请不要关闭此窗口">确定</button>
  </div>

  <script>

	  $.fileInput($('#modalForm input[type=file]'))

		$("#importBtn").click(function(){$("#modalForm").submit();return false;});
		$("#modalForm").validate({

				submitHandler: function (form) {
					var $btn = $("#importBtn").button('loading');

					$(form).ajaxSubmit({
						dataType:"json",
						success:function(ret){

							if(ret.success){

							    $.each(ret.candidates, function(i, candidate){

                                   _importUser(candidate);

							    })

                                $("#modal").modal('hide');

							}
							$btn.button('reset');
						}
					});
				}
			});

      function _importUser(candidate) {
          var $select;
          if(candidate.type==${PCS_USER_TYPE_DW}){
              $select = $("#dwUserId");
          }else{
              $select = $("#jwUserId");
          }

          var $jqGrid = $("#jqGrid" + candidate.type);

          var rowData = $jqGrid.getRowData(candidate.userId);

          if (rowData.userId == undefined) {

                $jqGrid.jqGrid("addRowData", candidate.userId, candidate, "last");
                $select.val(null).trigger("change");
                $select.closest(".panel").find(".tip .count").html($jqGrid.jqGrid("getDataIDs").length);
          }
      }

</script>