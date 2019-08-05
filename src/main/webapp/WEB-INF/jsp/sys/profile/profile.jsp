<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <div id="user-profile" class="user-profile row">
            <div class="col-sm-offset-1 col-sm-10">

                <div class="space"></div>

                <form class="form-horizontal" method="post" action="${ctx}/profile" enctype="multipart/form-data">
                    <div class="tabbable">
                        <jsp:include page="menu.jsp"/>

                        <div class="tab-content profile-edit-tab-content">
                            <div id="edit-basic" class="tab-pane in active">
                                <h4 class="header blue bolder smaller">基本信息</h4>

                                <div class="row">
                                    <div class="col-xs-12 col-sm-4" style="width:170px">
                                        <%--<input type="file" name="_avatar"/>--%>
                                        <img src="${ctx}/avatar?path=${cm:encodeURI(_user.avatar)}" style="width: 120px">
                                    </div>
                                    <div class="vspace-12-sm"></div>

                                    <div class="row col-xs-12 col-sm-8">
                                        <div class="col-sm-6">
                                            <div class="form-group">
                                                <label class="col-sm-4 control-label no-padding-right">账号：</label>

                                                <div class="col-sm-8">
                                                    <div class="label-text"><shiro:principal property="username"/></div>
                                                </div>
                                            </div>
                                            <%--<div class="form-group">
                                                <label class="col-sm-4 control-label no-padding-right">身份：</label>

                                                <div class="col-sm-8">
                                                    <div class="label-text">
                                                        <c:forEach items="${fn:split(_user.roleIds,',')}" var="id"
                                                                   varStatus="vs">
                                                            ${roleMap.get(cm:toInt(id)).name}
                                                            <c:if test="${!vs.last}">, </c:if>
                                                        </c:forEach>
                                                    </div>
                                                </div>
                                            </div>--%>
                                            <div class="form-group">
                                                <label class="col-sm-4 control-label no-padding-right">类别：</label>

                                                <div class="col-sm-8">
                                                    <div class="label-text">
                                                        ${USER_TYPE_MAP.get(_user.type)}
                                                    </div>
                                                </div>
                                            </div>
                                            <c:if test="${fn:length(adminPartyIdList)>0}">
                                                <div class="form-group">
                                                    <label class="col-sm-4 control-label no-padding-right">管理${_p_partyName}：</label>

                                                    <div class="col-sm-8">
                                                        <div class="label-text">
                                                            <c:forEach items="${adminPartyIdList}" var="partyId"
                                                                       varStatus="vs">
                                                                ${cm:displayParty(partyId, null)}
                                                                <c:if test="${!vs.last}">,</c:if>
                                                            </c:forEach>
                                                        </div>
                                                    </div>
                                                </div>
                                            </c:if>
                                            <c:if test="${fn:length(adminBranchIdList)>0}">
                                                <div class="form-group">
                                                    <label class="col-sm-4 control-label no-padding-right">管理党支部：</label>

                                                    <div class="col-sm-8">
                                                        <div class="label-text">
                                                            <c:forEach items="${adminBranchIdList}" var="branchId"
                                                                       varStatus="vs">
                                                                <c:set var="branch" value="${branchMap.get(branchId)}"/>
                                                                ${cm:displayParty(branch.partyId, branch.id)}
                                                                <c:if test="${!vs.last}">,</c:if>
                                                            </c:forEach>
                                                        </div>
                                                    </div>
                                                </div>
                                            </c:if>

                                            <div class="space-4"></div>


                                        </div>
                                        <div class="col-sm-6">
                                            <div class="form-group">
                                                <label class="col-sm-4 control-label no-padding-right">姓名：</label>

                                                <div class="col-sm-8">
                                                    <div class="label-text">
                                                        <shiro:principal property="realname"/>
                                                    </div>
                                                    <%-- <input type="text" name="realname"  value="<shiro:principal property="realname"/>"/>--%>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-sm-4 control-label no-padding-right">出生年月：</label>

                                                <div class="col-sm-3">
                                                    <div class="input-medium">
                                                        <div class="label-text">
                                                            ${cm:formatDate(_user.birth,'yyyy-MM-dd')}
                                                        </div>
                                                        <%--<div class="input-group">
                                                          <input name="_birth" class="input-medium date-picker" id="form-field-date"
                                                                 type="text" data-date-format="yyyy-mm-dd"
                                                                 placeholder="yyyy-mm-dd" value="${cm:formatDate(_user.birth,'yyyy-MM-dd')}"/>
                                                    <span class="input-group-addon">
                                                        <i class="ace-icon fa fa-calendar"></i>
                                                    </span>
                                                        </div>--%>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-sm-4 control-label no-padding-right">性别：</label>

                                                <div class="col-sm-3">
                                                    <div class="input-medium">
                                                        <div class="label-text">
                                                            ${GENDER_MAP.get(_user.gender)}
                                                        </div>
                                                    </div>
                                                </div>
                                                <%-- <div class="label-text">
                                                   <div class="col-sm-8">
                                                     <label class="inline">
                                                       <input name="gender" ${_user.gender==GENDER_MALE?"checked":""} dis type="radio" class="ace" value="${GENDER_MALE}"/>
                                                       <span class="lbl middle"> 男</span>
                                                     </label>
                                                     <label class="inline">
                                                       <input name="gender" ${_user.gender==GENDER_FEMALE?"checked":""} type="radio" class="ace" value="${GENDER_FEMALE}"/>
                                                       <span class="lbl middle"> 女</span>
                                                     </label>
                                                   </div>
                                                 </div>--%>
                                            </div>
                                        </div>
                                    </div>
                                </div>


                                <h4 class="header blue bolder smaller">联系方式</h4>

                                <div class="form-group">
                                    <label class="col-sm-3 control-label no-padding-right">邮箱：</label>

                                    <div class="col-sm-9">
                                        <%--<span class="input-icon input-icon-right">
                                            <input name="email" type="email" id="form-field-email" value="${_user.email}"/>
                                            <i class="ace-icon fa fa-envelope"></i>
                                        </span>--%>
                                        <div class="label-text">${_user.email}</div>
                                    </div>
                                </div>

                                <div class="space-4"></div>

                                <div class="form-group">
                                    <label class="col-sm-3 control-label no-padding-right">手机号：</label>

                                    <div class="col-sm-9">
                                        <div class="label-text">${_user.mobile}</div>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-3 control-label no-padding-right">办公电话：</label>

                                    <div class="col-sm-9">
                                        <div class="label-text">${_user.phone}</div>
                                    </div>
                                </div>
                            </div>

                            <div id="edit-settings" class="tab-pane">
                                <div class="space-10"></div>

                                <div>
                                    <label class="inline">
                                        <input type="checkbox" name="form-field-checkbox" class="ace"/>
                                        <span class="lbl"> 信息公开</span>
                                    </label>
                                </div>

                                <div class="space-8"></div>

                                <div>
                                    <label class="inline">
                                        <input type="checkbox" name="form-field-checkbox" class="ace"/>
                                        <span class="lbl"> 邮件通知</span>
                                    </label>
                                </div>
                            </div>
                            <shiro:hasPermission name="profile:modifyPasswd">
                                <form id="passwordForm" action="${ctx}/password" method="post">
                                    <div id="edit-password" class="tab-pane">
                                        <div class="space-10"></div>

                                        <div class="form-group">
                                            <label class="col-sm-3 control-label no-padding-right">原密码</label>

                                            <div class="col-sm-9">
                                                <input type="password" name="oldPassword">
                                            </div>
                                        </div>

                                        <div class="form-group">
                                            <label class="col-sm-3 control-label no-padding-right">新密码</label>

                                            <div class="col-sm-9">
                                                <input type="password" name="password">
                                            </div>
                                        </div>

                                        <div class="space-4"></div>

                                        <div class="form-group">
                                            <label class="col-sm-3 control-label no-padding-right">新密码确认</label>

                                            <div class="col-sm-9">
                                                <input type="password" name="repassword">
                                            </div>
                                        </div>
                                    </div>
                                </form>
                            </shiro:hasPermission>
                        </div>
                    </div>

                    <%-- <div class="clearfix form-actions">
                       <div class="col-md-offset-3 col-md-9">
                         <button class="btn btn-info" type="submit">
                           <i class="ace-icon fa fa-check bigger-110"></i>
                           保存
                         </button>

                         &nbsp; &nbsp;
                         <button class="btn" type="reset">
                           <i class="ace-icon fa fa-undo bigger-110"></i>
                           重置
                         </button>
                       </div>
                     </div>--%>
                </form>
            </div>
        </div>
    </div>
</div>
<div class="footer-margin lower"/>
<script>
    /*$.fileInput($('#user-profile input[type=file]'), {
        style: 'well',
        btn_choose: '更换头像',
        btn_change: null,
        no_icon: 'ace-icon fa fa-picture-o',
        thumbnail: 'large',
        droppable: true,
        previewWidth: 143,
        previewHeight: 198,
        allowExt: ['jpg', 'jpeg', 'png', 'gif'],
        allowMime: ['image/jpg', 'image/jpeg', 'image/png', 'image/gif']
    });
     $('#user-profile input[type=file]').ace_file_input('show_file_list', [{
     type: 'image',
     name: '${ctx}/avatar?path=${_user.avatar}'
     }]);
    $('#user-profile input[type=file]').find('button[type=reset]').on(ace.click_event, function () {
        //$('#user-profile input[type=file]').ace_file_input('reset_input');
        $('#user-profile').find('input[type=file]').ace_file_input('show_file_list', [{
            type: 'image',
            name: '${ctx}/avatar?path=${_user.avatar}'
        }]);
    })*/

    $.register.date($('.date-picker'), {defaultViewDate: {year: 1980}})
    $("#user-profile form").validate({
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                success: function (ret) {
                    if (ret.success) {
                        //page_reload();
                        //SysMsg.success('更新成功。', '成功');
                    }
                }
            });
        }
    });

</script>