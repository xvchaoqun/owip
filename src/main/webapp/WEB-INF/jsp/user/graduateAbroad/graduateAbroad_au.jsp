<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div  style="width: 900px">
<c:if test="${graduateAbroad.status==GRADUATE_ABROAD_STATUS_BACK}">
    <div class="alert alert-danger">
        <button type="button" class="close" data-dismiss="alert">
            <i class="ace-icon fa fa-times"></i>
        </button>
        <strong><i class="ace-icon fa fa-times"></i>返回修改</strong><c:if test="${not empty graduateAbroad.reason}">: ${graduateAbroad.reason}</c:if>
        <br>
    </div>
</c:if>
<div class="widget-box">
    <div class="widget-header">
        <h4 class="widget-title"><i class="fa fa-paw blue"></i> 党员出国（境）申请组织关系暂留</h4>
    </div>
    <div class="widget-body">
        <div class="widget-main">
            <form class="form-horizontal" action="${ctx}/user/graduateAbroad_au" id="modalForm" method="post">
                <input type="hidden" name="id" value="${graduateAbroad.id}">
                <div class="row">
                    <div class="col-xs-6">
                        <div class="form-group">
                            <label class="col-xs-6 control-label">人员类别</label>
                            <div class="col-xs-6">
                                <select required data-rel="select2" name="userType" data-placeholder="请选择">
                                    <option></option>
                                    <jsp:include page="/metaTypes?__code=mc_abroad_user_type"/>
                                </select>
                                <script type="text/javascript">
                                    $("#modalForm select[name=userType]").val(${graduateAbroad.userType});
                                </script>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-xs-6 control-label">出国原因</label>
                            <div class="col-xs-6 choice">
                                <input name="_reason" type="checkbox" value="留学"> 留学&nbsp;
                                <input name="_reason" type="checkbox" value="访学"> 访学&nbsp;
                                <input name="_reason" type="checkbox" value="工作"> 工作&nbsp;
                                <br/>
                                <input name="_reason" type="checkbox" value="其他"> 其他
                                <input name="_reason_other" type="text" size="18">
                                <input name="abroadReason" type="hidden"/>
                            </div>
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
                                <input required class="form-control email" type="text" name="email" value="${graduateAbroad.email}">
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
                            <label class="col-xs-6 control-label">去往国家</label>
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
                            <label class="col-xs-6 control-label">留学学校或工作单位</label>
                            <div class="col-xs-6">
                                <input required class="form-control" type="text" name="school" value="${graduateAbroad.school}">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-xs-6 control-label">出国起始时间</label>
                            <div class="col-xs-6">
                                <div class="input-group">
                                    <input required class="form-control date-picker" name="_startTime" type="text"
                                           data-date-format="yyyy-mm-dd" value="${cm:formatDate(graduateAbroad.startTime,'yyyy-MM-dd')}" />
                                    <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-xs-6 control-label">出国截止时间</label>
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
                                    <input required class="form-control date-picker"
                                           name="_payTime" type="text"
                                           data-date-format="yyyy-mm"
                                           data-date-min-view-mode="1" value="${cm:formatDate(graduateAbroad.payTime,'yyyy-MM')}" />
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
                                    <input required class="form-control email" type="text" name="email1" value="${graduateAbroad.email1}">
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
                                <input required class="form-control email" type="text" name="email2" value="${graduateAbroad.email2}">
                            </div>
                        </div>
                            </fieldset>

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
<style>
    fieldset{
        border: 1px solid #c0c0c0 !important;
        text-align: center;
    }
    legend{
        width:auto!important;
        border-bottom:none!important;
    }
</style>
<script type="text/javascript" src="${ctx}/extend/js/location.js"></script>
<script>

    <c:forEach var="reason" items="${fn:split(graduateAbroad.abroadReason,'+++')}">
    $("input[name=_reason][value='${reason}']").prop("checked", true);
    <c:if test="${fn:startsWith(reason, '其他:')}">
    $("#modalForm input[name=_reason][value='其他']").prop("checked", true);
        $("input[name=_reason_other]").val('${fn:substringAfter(reason, '其他:')}');
    </c:if>
    </c:forEach>

    $('textarea.limited').inputlimiter();
    register_date($('.date-picker'));
    $("#modalForm").validate({
        submitHandler: function (form) {

            // 出国原因
            var $_reason = $("#modalForm input[name=_reason][value='其他']");
            var _reason_other = $("input[name=_reason_other]").val().trim();
            if($_reason.is(":checked")){
                if(_reason_other==''){
                    SysMsg.info("请输入其他出国原因", '', function(){
                        $("#modalForm input[name=_reason_other]").val('').focus();
                    });
                    return;
                }
            }
            var reasons = [];
            $.each($("#modalForm input[name=_reason]:checked"), function(){
                if($(this).val()=='其他'){
                    reasons.push("其他:"+_reason_other);
                }else
                    reasons.push($(this).val());
            });
            if(reasons.length==0){
                SysMsg.info("请选择出国原因");
                return;
            }
            $("#modalForm input[name=abroadReason]").val(reasons.join("+++"));

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
