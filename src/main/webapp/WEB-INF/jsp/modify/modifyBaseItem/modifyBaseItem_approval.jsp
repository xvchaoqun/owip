<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<%@ include file="/WEB-INF/jsp/modify/constants.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>审核修改字段</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/modifyBaseItem_approval" id="modalForm" method="post">
        <input type="hidden" name="id" value="${record.id}">
        <div class="form-group">
            <label class="col-xs-3 control-label">工作证号</label>
            <div class="col-xs-6 label-text">
                ${sysUser.code}
            </div>
        </div>
		<div class="form-group">
			<label class="col-xs-3 control-label">姓名</label>
			<div class="col-xs-6 label-text">
                ${sysUser.realname}
			</div>
		</div>
        <div class="form-group">
            <label class="col-xs-3 control-label">所在单位及职务</label>
            <div class="col-xs-6 label-text">
                ${cadre.title}
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label">字段名称</label>
            <div class="col-xs-6 label-text">
                ${record.name}
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label">修改前后</label>
            <div class="col-xs-9 label-text">
                <c:if test="${record.type==MODIFY_BASE_ITEM_TYPE_IMAGE}">
                    <a class="various" data-fancybox-type="image" href="${ctx}/avatar?path=${cm:encodeURI(record.orginalValue)}">
                    <img class="avatar" style="width: 120px" src="${ctx}/avatar?path=${cm:encodeURI(record.orginalValue)}"/>
                    </a>
                </c:if>
                <c:if test="${record.type!=MODIFY_BASE_ITEM_TYPE_IMAGE}">
                    <c:if test="${record.code=='health'}">
                        ${cm:getMetaType(record.orginalValue).name}
                    </c:if>
                    <c:if test="${record.code!='health'}">
                        ${record.orginalValue}
                    </c:if>
                </c:if>
                 &nbsp;<i class="fa fa-arrow-right"></i>
                <c:if test="${record.type==MODIFY_BASE_ITEM_TYPE_IMAGE}">
                    <a class="various" data-fancybox-type="image" href="${ctx}/avatar?path=${cm:encodeURI(record.modifyValue)}">
                    <img class="avatar" style="width: 120px" src="${ctx}/avatar?path=${cm:encodeURI(record.modifyValue)}"/>
                    </a>
                </c:if>
                <c:if test="${record.type!=MODIFY_BASE_ITEM_TYPE_IMAGE}">
                    <c:if test="${record.code=='health'}">
                        ${cm:getMetaType(record.modifyValue).name}
                    </c:if>
                    <c:if test="${record.code!='health'}">
                        ${record.modifyValue}
                    </c:if>
                </c:if>
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label">审核意见</label>
            <div class="col-xs-8 label-text"  style="font-size: 15px;">
                <input type="checkbox" class="big" value="1"/> 通过审核
                <input type="checkbox"  class="big" value="2"/> 未通过审核
            </div>
        </div>
		<div class="form-group">
			<label class="col-xs-3 control-label">依据</label>
			<div class="col-xs-6">
				<textarea class="form-control limited" type="text" name="checkReason" rows="2"></textarea>
			</div>
		</div>
        <div class="form-group">
            <label class="col-xs-3 control-label">备注</label>
            <div class="col-xs-6">
                <textarea class="form-control limited" type="text" name="checkRemark" rows="2"></textarea>
            </div>
        </div>
    </form>
    </div>
<div class="modal-footer">
    <input type="submit" class="btn btn-primary" value="确认"/>
    <a href="javascript:;" data-dismiss="modal" class="btn btn-default">取消</a>
</div>

<script>
    $("input[type=checkbox]").click(function(){
        if($(this).prop("checked")){
            $("input[type=checkbox]").not(this).prop("checked", false);
        }
    });
	$("#modalForm").validate({
        submitHandler: function (form) {

            var type = $('#modal input[type=checkbox]:checked').val();
            if(type!=1&&type!=2){
                SysMsg.warning("请选择审核意见");
                return;
            }

            $(form).ajaxSubmit({
                data:{status:(type==1)},
                success:function(ret){
                    if(ret.success){
                        _reload()
                    }
                }
            });
        }
    });
</script>