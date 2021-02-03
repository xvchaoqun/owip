<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>         
  <div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>批量导入</h3>
  </div>
  <div class="modal-body">
    <form class="form-horizontal" autocomplete="off" disableautocomplete id="modalForm"
          enctype="multipart/form-data" action="${ctx}/member_import" method="post">
        <c:if test="${not empty param.inSchool}">
            <input type="hidden" value="${param.inSchool}" name="inSchool">
        </c:if>
        <c:if test="${param.all==1}">
            <input type="hidden" value="${param.all}" name="all"/>
            <div class="form-group">
                <label class="col-xs-4 control-label"><span class="star">*</span> ${_p_partyName}起始编码</label>
                <div class="col-xs-6">
                    <input required class="form-control" style="width: 150px" type="text" name="startCode"/>
                </div>
            </div>
        </c:if>
		<div class="form-group">
            <label class="col-xs-4 control-label"><span class="star">*</span> Excel文件</label>
			<div class="col-xs-6">
				<input class="form-control" type="file" name="xlsx" required extension="xlsx"/>
			</div>
		</div>
        </form>
        <div class="well">
        <span class="help-inline">导入的文件请严格按照
            <c:if test="${param.all==1}">
                <a href="${ctx}/attach?code=sample_member_all_update">
                    党员信息一张表导入样表.xlsx</a>（点击下载）的数据格式</span>
            <ul>
                <li>导入顺序为先导入正式党员和预备党员，然后再导入发展党员</li>
                <li>填写起始编码，供系统插入新的基层党组织使用,建议输入4-5位数字作为起始编码，系统会生成两位数字与其实编码拼接为二级党委编码</li>
                <li>转移党员时，系统存在党员信息，则会进行转移，不存在则会先创建一条；
                    若基层党组织存在，则会直接将党员转入相应的基层党组织，没有则会先创建基层党组织</li>
                <li>导入完成，会返回一个excel，如果存在多个账号，会显示在最后一列</li>
            </ul>
            </c:if>
            <c:if test="${param.all!=1}">
                <a href="${ctx}/attach?code=sample_member_${param.inSchool==1?"inSchool":"outSchool"}">
                    党员录入样表（${param.inSchool==1?"校园门户账号":"系统注册账号"}）.xlsx</a>（点击下载）的数据格式</span>
            </c:if>
        </div>
  </div>
  <div class="modal-footer">
  <a href="javascript:;" data-dismiss="modal" class="btn btn-default">取消</a>
	  <button id="submitBtn" type="button" class="btn btn-primary"
			  data-loading-text="<i class='fa fa-spinner fa-spin '></i> 提交中，请不要关闭此窗口">确定</button>
  </div>

  <script>

	  $.fileInput($('#modalForm input[type=file]'))

		$("#submitBtn").click(function(){$("#modalForm").submit();return false;});
		$("#modalForm").validate({

				submitHandler: function (form) {
					var $btn = $("#submitBtn").button('loading');
					$(form).ajaxSubmit({
						dataType:"json",
						success:function(ret){
                            <c:if test="${param.all!=1}">
                                if(ret && ret.successCount>=0){
                                    var result = '操作成功，总共{0}条记录，其中成功导入{1}条记录，<font color="red">{2}条覆盖</font>';
                                    SysMsg.success(result.format(ret.total, ret.successCount, ret.total-ret.successCount), '成功',function(){
                                        page_reload();
                                    });
                                }
                            </c:if>
                            <c:if test="${param.all==1}">
                                if(ret && ret.successCount>=0){
                                    var result = '操作成功，导入{0}条${_p_partyName}记录，导入{1}条党支部记录，总共{2}条党员记录，其中成功导入{3}条正式党员和预备党员记录，导入{4}条发展中党员记录，<font color="red">{5}条党员记录覆盖</font>';
                                    SysMsg.success(result.format(ret.partyAdd, ret.branchAdd, ret.total, ret.successCount, ret.applyCount, ret.total-ret.successCount-ret.applyCount), '成功',function(){
                                        page_reload();
                                        var url = ("${ctx}/attach_download?path={0}&filename={1}")
                                            .format(ret.file, ret.filename)
                                        $btn.download(url);
                                    });
                                }
                                $.reloadMetaData(function(){
                                    $("#jqGrid").trigger("reloadGrid");
                                });
                            </c:if>
							$btn.button('reset');
						}
					});
				}
			});

</script>