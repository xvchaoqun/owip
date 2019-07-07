<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<%@ include file="/WEB-INF/jsp/modify/constants.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>审核修改字段</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/modifyBaseItem_approval" autocomplete="off" disableautocomplete id="modalForm" method="post">
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
                <c:if test="${isOwParty && isDpParty}">
                （${cm:getMetaType(dpParty.classId).name}）
                </c:if>
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
                     <c:if test="${not empty record.orginalValue}">
                    <c:choose>
                        <c:when test="${record.code=='health'}">
                            ${cm:getMetaType(record.orginalValue).name}
                        </c:when>
                        <c:when test="${record.code=='political_status'}">
                            ${record.orginalValue==0?'中共党员':(cm:getMetaType(record.orginalValue).name)}
                        </c:when>
                        <c:otherwise>${record.orginalValue}</c:otherwise>
                    </c:choose>
                     </c:if>
                    <c:if test="${empty record.orginalValue}">无</c:if>
                </c:if>
                 &nbsp;<i class="fa fa-arrow-right"></i>
                <c:if test="${record.type==MODIFY_BASE_ITEM_TYPE_IMAGE}">
                    <a class="various" data-fancybox-type="image" href="${ctx}/avatar?path=${cm:encodeURI(record.modifyValue)}">
                    <img class="avatar" style="width: 120px" src="${ctx}/avatar?path=${cm:encodeURI(record.modifyValue)}"/>
                    </a>
                </c:if>
                <c:if test="${record.type!=MODIFY_BASE_ITEM_TYPE_IMAGE}">
                    <c:if test="${not empty record.modifyValue}">
                    <c:choose>
                        <c:when test="${record.code=='health'}">
                            ${cm:getMetaType(record.modifyValue).name}
                        </c:when>
                        <c:when test="${record.code=='political_status'}">
                            ${record.modifyValue==0?'中共党员':(cm:getMetaType(record.modifyValue).name)}
                        </c:when>
                        <c:otherwise>${record.modifyValue}</c:otherwise>
                    </c:choose>
                        </c:if>
                    <c:if test="${empty record.modifyValue}">无</c:if>
                </c:if>

                <span class="help-block red">
                    <ul>
                        <c:if test="${record.code=='political_status' && record.modifyValue==0}">
                        <li>审批通过后，该账号将被添加至【干部其他信息】-【中共党员干部库】中</li>
                        <c:if test="${_p_hasPartyModule}"><li>如需添加进入党员库中，请联系对应的支部管理员。</li></c:if>
                        </c:if>
                        <c:if test="${record.code=='political_status' && record.orginalValue==0}">
                        <li>审批通过后，该账号将从【干部其他信息】-【中共党员干部库】<c:if test="${_p_hasPartyModule}">和党员库（如果存在）</c:if>中删除</li>
                        </c:if>
                    </ul>
                </span>

            </div>
        </div>
        <c:if test="${record.code=='grow_time'}">
            <c:if test="${isOwParty && isDpParty}">
            <%--<div class="form-group">
                <label class="col-xs-3 control-label">选择党派类别</label>
                <div class="col-xs-8"  style="font-size: 15px;">
                    <div class="input-group">
                        <input required name="owType" type="radio" class="big" value="1" checked/> 中共党员
                        <input required name="owType" type="radio"  class="big" value="2"/> 民主党派
                    </div>
                    <span class="help-block red">
                        请选择该账号对应的一个党派类别
                    </span>
                </div>
             </div>--%>
                <input type="hidden" name="owType" value="2"> <!-- 两个党派以上时，默认为民主党派-->
            </c:if>
            <c:if test="${!isOwParty || !isDpParty}">
                <input type="hidden" name="owType" value="${isOwParty?1:2}">
            </c:if>
        </c:if>
        <div class="form-group">
            <label class="col-xs-3 control-label">审核意见</label>
            <div class="col-xs-8"  style="font-size: 15px;">
                <div class="input-group">
                    <input required name="pass" type="checkbox" class="big" value="1"/> 通过审核
                    <input required name="pass" type="checkbox"  class="big" value="2"/> 未通过审核
                </div>
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
    $("#modal input[name=pass]").click(function(){
        if($(this).prop("checked")){
            $("#modal input[name=pass]").not(this).prop("checked", false);
        }
    });
	$("#modalForm").validate({
        submitHandler: function (form) {

            var pass = $('#modal input[name=pass]:checked').val();
            if(pass!=1&&pass!=2){
                SysMsg.warning("请选择审核意见");
                return;
            }

            $(form).ajaxSubmit({
                data:{status:(pass==1)},
                success:function(ret){
                    if(ret.success){
                        _reload()
                    }
                }
            });
        }
    });
</script>