<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div style="width: 900px">
    <h3><c:if test="${graduateAbroad!=null}">编辑</c:if><c:if test="${graduateAbroad==null}">添加</c:if>毕业生党员出国（境）申请组织关系暂留</h3>
<hr/>
    <form class="form-horizontal" action="${ctx}/graduateAbroad_au" id="modalForm" method="post">
        <input type="hidden" name="id" value="${graduateAbroad.id}">
		<div class="row">
			<div class="col-xs-6">
			<div class="form-group">
				<label class="col-xs-6 control-label">用户</label>
				<c:if test="${not empty userBean}">
					<div class="col-xs-6 label-text">
						<input type="hidden" name="userId" value="${userBean.userId}">
					${userBean.realname} - ${userBean.code}
					</div>
				</c:if>
				<c:if test="${empty userBean}">
					<div class="col-xs-6">
						<select required data-rel="select2-ajax" data-ajax-url="${ctx}/member_selects"
								name="userId" data-placeholder="请输入账号或姓名或学工号">
							<option value="${userBean.userId}">${userBean.realname}</option>
						</select>
					</div>
				</c:if>
			</div>
				<div class="form-group">
					<label class="col-xs-6 control-label">手机</label>
					<div class="col-xs-6">
						<input required class="form-control" type="text" name="mobile" value="${graduateAbroad.mobile}">
					</div>
				</div>
				<div class="form-group">
					<label class="col-xs-6 control-label">家庭电话</label>
					<div class="col-xs-6">
						<input required class="form-control" type="text" name="phone" value="${graduateAbroad.phone}">
					</div>
				</div>
				<div class="form-group">
					<label class="col-xs-6 control-label">微信</label>
					<div class="col-xs-6">
						<input required class="form-control" type="text" name="weixin" value="${graduateAbroad.weixin}">
					</div>
				</div>
				<div class="form-group">
					<label class="col-xs-6 control-label">电子邮箱</label>
					<div class="col-xs-6">
						<input required class="form-control" type="text" name="email" value="${graduateAbroad.email}">
					</div>
				</div>
				<div class="form-group">
					<label class="col-xs-6 control-label">QQ号</label>
					<div class="col-xs-6">
						<input required class="form-control" type="text" name="qq" value="${graduateAbroad.qq}">
					</div>
				</div>
				<div class="form-group">
					<label class="col-xs-6 control-label">国内通讯地址</label>
					<div class="col-xs-6">
						<input required class="form-control" type="text" name="inAddress" value="${graduateAbroad.inAddress}">
					</div>
				</div>
				<div class="form-group">
					<label class="col-xs-6 control-label">国外通讯地址</label>
					<div class="col-xs-6">
						<input required class="form-control" type="text" name="outAddress" value="${graduateAbroad.outAddress}">
					</div>
				</div>
				<div class="form-group">
					<label class="col-xs-6 control-label">留学国家</label>
					<div class="col-xs-6">
						<select required  name="country" data-rel="select2" data-placeholder="请选择">
							<option></option>
							<c:forEach var="entity" items="${countryMap}">
								<option value="${entity.value.cninfo}">${entity.value.cninfo}</option>
							</c:forEach>
						</select>
						<script>
							$("#modalForm select[name=country]").val("${graduateAbroad.country}");
						</script>
					</div>
				</div>
				<div class="form-group">
					<label class="col-xs-6 control-label">留学学校（院系）</label>
					<div class="col-xs-6">
						<input required class="form-control" type="text" name="school" value="${graduateAbroad.school}">
					</div>
				</div>
				<div class="form-group">
					<label class="col-xs-6 control-label">留学起始时间</label>
					<div class="col-xs-6">
						<div class="input-group">
							<input required class="form-control date-picker" name="_startTime" type="text"
								   data-date-format="yyyy-mm-dd" value="${cm:formatDate(graduateAbroad.startTime,'yyyy-MM-dd')}" />
							<span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
						</div>
					</div>
				</div>
				<div class="form-group">
					<label class="col-xs-6 control-label">留学截止时间</label>
					<div class="col-xs-6">
						<div class="input-group">
							<input required class="form-control date-picker" name="_endTime" type="text"
								   data-date-format="yyyy-mm-dd" value="${cm:formatDate(graduateAbroad.endTime,'yyyy-MM-dd')}" />
							<span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
						</div>
					</div>
				</div>
				<div class="form-group">
					<label class="col-xs-6 control-label">留学方式</label>
					<div class="col-xs-6">
						<select required data-rel="select2" name="type" data-placeholder="请选择"  data-width="100%">
							<option></option>
							<c:forEach items="${GRADUATE_ABROAD_TYPE_MAP}" var="_type">
								<option value="${_type.key}">${_type.value}</option>
							</c:forEach>
						</select>
						<script>
							$("#modalForm select[name=type]").val(${graduateAbroad.type});
						</script>
					</div>
				</div>
				<div class="form-group">
					<label class="col-xs-6 control-label">申请保留组织关系起始时间</label>
					<div class="col-xs-6">
						<div class="input-group">
							<input required class="form-control date-picker" name="_saveStartTime" type="text"
								   data-date-format="yyyy-mm-dd" value="${cm:formatDate(graduateAbroad.saveStartTime,'yyyy-MM-dd')}" />
							<span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
						</div>
					</div>
				</div>
				<div class="form-group">
					<label class="col-xs-6 control-label">申请保留组织关系截止时间</label>
					<div class="col-xs-6">
						<div class="input-group">
							<input required class="form-control date-picker" name="_saveEndTime" type="text"
								   data-date-format="yyyy-mm-dd" value="${cm:formatDate(graduateAbroad.saveEndTime,'yyyy-MM-dd')}" />
							<span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
						</div>
					</div>
				</div>
				<div class="form-group">
					<label class="col-xs-6 control-label">党费交纳截止时间</label>
					<div class="col-xs-6">
						<div class="input-group">
							<input required class="form-control date-picker" name="_payTime" type="text"
								   data-date-format="yyyy-mm"
								   data-date-min-view-mode="1"  value="${cm:formatDate(graduateAbroad.payTime,'yyyy-MM')}" />
							<span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
						</div>
					</div>
				</div>
				</div>
			<div class="col-xs-6">
				<fieldset>
					<legend>国内第一联系人</legend>
					<div class="form-group">
						<label class="col-xs-5 control-label">姓名</label>
						<div class="col-xs-6">
							<input required class="form-control" type="text" name="name1" value="${graduateAbroad.name1}">
						</div>
					</div>
					<div class="form-group">
						<label class="col-xs-5 control-label">与本人关系</label>
						<div class="col-xs-6">
							<input required class="form-control" type="text" name="relate1" value="${graduateAbroad.relate1}">
						</div>
					</div>
					<div class="form-group">
						<label class="col-xs-5 control-label">单位</label>
						<div class="col-xs-6">
							<input required class="form-control" type="text" name="unit1" value="${graduateAbroad.unit1}">
						</div>
					</div>
					<div class="form-group">
						<label class="col-xs-5 control-label">职务</label>
						<div class="col-xs-6">
							<input required class="form-control" type="text" name="post1" value="${graduateAbroad.post1}">
						</div>
					</div>
					<div class="form-group">
						<label class="col-xs-5 control-label">办公电话</label>
						<div class="col-xs-6">
							<input required class="form-control" type="text" name="phone1" value="${graduateAbroad.phone1}">
						</div>
					</div>
					<div class="form-group">
						<label class="col-xs-5 control-label">手机号</label>
						<div class="col-xs-6">
							<input required class="form-control" type="text" name="mobile1" value="${graduateAbroad.mobile1}">
						</div>
					</div>
					<div class="form-group">
						<label class="col-xs-5 control-label">电子邮箱</label>
						<div class="col-xs-6">
							<input required class="form-control" type="text" name="email1" value="${graduateAbroad.email1}">
						</div>
					</div>
				</fieldset>
				<fieldset style="margin-bottom: 10px">
					<legend>国内第二联系人</legend>
					<div class="form-group">
						<label class="col-xs-5 control-label">姓名</label>
						<div class="col-xs-6">
							<input required class="form-control" type="text" name="name2" value="${graduateAbroad.name2}">
						</div>
					</div>
					<div class="form-group">
						<label class="col-xs-5 control-label">与本人关系</label>
						<div class="col-xs-6">
							<input required class="form-control" type="text" name="relate2" value="${graduateAbroad.relate2}">
						</div>
					</div>
					<div class="form-group">
						<label class="col-xs-5 control-label">单位</label>
						<div class="col-xs-6">
							<input required class="form-control" type="text" name="unit2" value="${graduateAbroad.unit2}">
						</div>
					</div>
					<div class="form-group">
						<label class="col-xs-5 control-label">职务</label>
						<div class="col-xs-6">
							<input required class="form-control" type="text" name="post2" value="${graduateAbroad.post2}">
						</div>
					</div>
					<div class="form-group">
						<label class="col-xs-5 control-label">办公电话</label>
						<div class="col-xs-6">
							<input required class="form-control" type="text" name="phone2" value="${graduateAbroad.phone2}">
						</div>
					</div>
					<div class="form-group">
						<label class="col-xs-5 control-label">手机号</label>
						<div class="col-xs-6">
							<input required class="form-control" type="text" name="mobile2" value="${graduateAbroad.mobile2}">
						</div>
					</div>
					<div class="form-group">
						<label class="col-xs-5 control-label">电子邮箱</label>
						<div class="col-xs-6">
							<input required class="form-control" type="text" name="email2" value="${graduateAbroad.email2}">
						</div>
					</div>
				</fieldset>

				</div>
			</div>
    </form>
<c:if test="${graduateAbroad.status!=GRADUATE_ABROAD_STATUS_OW_VERIFY}">
	<div class="modal-footer center">
		<a href="#" class="btn btn-default closeView">取消</a>
		<input type="submit" class="btn btn-primary" value="<c:if test="${graduateAbroad!=null}">确定</c:if><c:if test="${graduateAbroad==null}">添加</c:if>"/>
	</div>
</c:if>
<c:if test="${graduateAbroad.status==GRADUATE_ABROAD_STATUS_OW_VERIFY}">
	<div class="modal-footer center">
		<a href="#" class="btn btn-default closeView">返回</a>
	</div>
</c:if>
</div>
<script>
	jgrid_left = $("#jqGrid").closest(".ui-jqgrid-bdiv").scrollLeft();
	jgrid_top = $("#jqGrid").closest(".ui-jqgrid-bdiv").scrollTop();
	$('textarea.limited').inputlimiter();
	register_date($('.date-picker'));
	$("#item-content input[type=submit]").click(function(){$("#modalForm").submit(); return false;});
	$("#modalForm").validate({
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
						SysMsg.success('提交成功。', '成功',function(){
							$("#jqGrid").trigger("reloadGrid");
							$(".closeView").click();
						});
                    }
                }
            });
        }
    });
    $('#modalForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();

	register_user_select($('#modalForm select[name=userId]'));
</script>