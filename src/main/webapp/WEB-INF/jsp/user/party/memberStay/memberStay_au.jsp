<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row footer-margin" style="width: 900px">
<c:if test="${memberStay.status==MEMBER_STAY_STATUS_BACK}">
    <div class="alert alert-danger">
        <button type="button" class="close" data-dismiss="alert">
            <i class="ace-icon fa fa-times"></i>
        </button>
        <strong><i class="ace-icon fa fa-times"></i>返回修改</strong><c:if test="${not empty memberStay.reason}">: ${memberStay.reason}</c:if>
        <br>
    </div>
</c:if>
<div class="widget-box">
    <div class="widget-header">
        <h4 class="widget-title"><i class="fa fa-paw blue"></i> 公派留学生党员申请组织关系暂留</h4>

        <%--<div class="widget-toolbar">
            <a href="#" data-action="collapse">
                <i class="ace-icon fa fa-chevron-up"></i>
            </a>
        </div>--%>
    </div>
    <div class="widget-body">
        <div class="widget-main">
            <form class="form-horizontal" action="${ctx}/user/memberStay_au" id="modalForm" method="post">
                <input type="hidden" name="id" value="${memberStay.id}">
                <div class="row">
                    <div class="col-xs-6">
                        <div class="form-group">
                            <label class="col-xs-5 control-label">留学国别</label>
                            <div class="col-xs-6">
                                <select required  name="country" data-rel="select2" data-placeholder="请选择">
                                    <option></option>
                                    <c:forEach var="entity" items="${countryMap}">
                                        <option value="${entity.value.cninfo}">${entity.value.cninfo}</option>
                                    </c:forEach>
                                </select>
                                <script>
                                    $("#modalForm select[name=country]").val("${memberStay.country}");
                                </script>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-xs-5 control-label">出国时间</label>
                            <div class="col-xs-6">
                                <div class="input-group">
                                    <input required class="form-control date-picker" name="_abroadTime" type="text"
                                           data-date-format="yyyy-mm-dd" value="${cm:formatDate(memberStay.abroadTime,'yyyy-MM-dd')}" />
                                    <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-xs-5 control-label">预计回国时间</label>
                            <div class="col-xs-6">
                                <div class="input-group">
                                    <input required class="form-control date-picker" name="_returnTime" type="text"
                                           data-date-format="yyyy-mm-dd" value="${cm:formatDate(memberStay.returnTime,'yyyy-MM-dd')}" />
                                    <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-xs-5 control-label">党费缴纳至年月</label>
                            <div class="col-xs-6">
                                <div class="input-group">
                                    <input required class="form-control date-picker" name="_payTime" type="text"
                                           data-date-format="yyyy-mm"
                                           data-date-min-view-mode="1" value="${cm:formatDate(memberStay.payTime,'yyyy-MM')}" />
                                    <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-xs-6">
                        <div class="form-group">
                            <label class="col-xs-5 control-label">手机号码</label>
                            <div class="col-xs-6">
                                <input required class="form-control" type="text" name="mobile" value="${memberStay.mobile}">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-xs-5 control-label">电子邮箱</label>
                            <div class="col-xs-6">
                                <input required class="form-control email" type="text" name="email" value="${memberStay.email}">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-xs-5 control-label">国内联系人姓名</label>
                            <div class="col-xs-6">
                                <input required class="form-control" type="text" name="contactName" value="${memberStay.contactName}">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-xs-5 control-label">国内联系人手机号码</label>
                            <div class="col-xs-6">
                                <input required class="form-control" type="text" name="contactMobile" value="${memberStay.contactMobile}">
                            </div>
                        </div>
                    </div>
                </div>

                <div class="clearfix form-actions center">
                    <button class="btn btn-info" type="submit">
                        <i class="ace-icon fa fa-check bigger-110"></i>
                        提交
                    </button>
                </div>
            </form>
        </div>
    </div>
</div>
</div>
<script type="text/javascript" src="${ctx}/extend/js/location.js"></script>
<script>

    $('textarea.limited').inputlimiter();
    register_date($('.date-picker'));
    $("#modalForm").validate({
        submitHandler: function (form) {

            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
                        bootbox.alert('提交成功。',function(){
                            location.reload();
                        });
                    }
                }
            });
        }
    });
    $('#modalForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>
