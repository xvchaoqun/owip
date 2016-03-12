<%--
  Created by IntelliJ IDEA.
  User: fafa
  Date: 2016/2/28
  Time: 10:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="modal-header">
    <h3>选择需要的证件并申请办理签注</h3>
</div>
<div class="modal-body">
    <div class="widget-box transparent">
        <div class="widget-header widget-header-flat">
            <h4 class="widget-title lighter">
                <i class="ace-icon fa fa-star orange"></i>
                因私出国（境）行程
            </h4>
            <div class="widget-toolbar">
                <a href="#" data-action="collapse">
                    <i class="ace-icon fa fa-chevron-up"></i>
                </a>
            </div>
        </div>
        <div class="widget-body" style="display: block;">
            <div class="widget-main no-padding">
                <table class="table table-striped table-bordered table-hover">
                    <thead>
                    <tr>
                        <th>编号</th>
                        <th>申请日期</th>
                        <th>出行时间</th>
                        <th>出发时间</th>
                        <th>返回时间</th>
                        <th>出行天数</th>
                        <th>前往国家或地区</th>
                        <th>事由</th>
                        <th>组织部初审</th>
                        <th>组织部终审</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <td>S${applySelf.id}</td>
                        <td>${cm:formatDate(applySelf.applyDate,'yyyy-MM-dd')}</td>
                        <td>${APPLY_SELF_DATE_TYPE_MAP.get(applySelf.type)}</td>
                        <td>${cm:formatDate(applySelf.startDate,'yyyy-MM-dd')}</td>
                        <td>${cm:formatDate(applySelf.endDate,'yyyy-MM-dd')}</td>
                        <td>${cm:getDayCountBetweenDate(applySelf.startDate, applySelf.endDate)}</td>
                        <td>${applySelf.toCountry}</td>
                        <td>${fn:replace(applySelf.reason, '+++', ',')}</td>
                        <td></td>
                        <td></td>

                    </tr>
                    </tbody>
                </table>
            </div><!-- /.widget-main -->
        </div><!-- /.widget-body -->
    </div>
    <form class="form-horizontal">
    <div class="well center" style="margin-top: 20px; font-size: 20px">
        <div class="row" style="padding-left: 100px">
            <c:forEach items="${passports}" var="passport">
                <c:set var="passportType" value="${cm:getMetaType('mc_passport_type', passport.classId)}"/>
                <div style="float: left; margin-right: 40px;">
                    <input type="checkbox" class="big" ${passport.id==param.passportId?"checked":""} disabled
                           data-sign="${passportType.code != 'mt_passport_normal'}" class="bigger"> ${passportType.name}
                    <c:if test="${passportType.code != 'mt_passport_normal'}">
                        <c:if test="${passport.id==param.passportId && param.sign==1}">
                            <span class="label label-success" style="vertical-align: 4px; margin-left: 10px">已申请办理签注</span>
                        </c:if>
                        <c:if test="${passport.id!=param.passportId || param.sign!=1}">
                        <span class="label" style="vertical-align: 4px; margin-left: 10px">未申请办理签注</span>
                        </c:if>
                    </c:if>
                </div>
            </c:forEach>
        </div>

    </div>
        <div class="form-group">
            <label class="col-xs-3 control-label">备注</label>
            <div class="col-xs-3">
                <textarea class="form-control limited" type="text" id="remark" rows="3"></textarea>
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label"></label>
            <div class="col-xs-3" style="font:bold 30px Verdana, Arial, Helvetica, sans-serif;padding: 20px; ">
                <input id="agree" type="checkbox" class="chkBox" style="width: 30px; height: 30px; margin: 0;"/> 信息已确认无误
            </div>
        </div>
    </form>
</div>
<div class="modal-footer center">
    <input id="submit" class="btn btn-success" value="提交申请"/>
    <input class="closeView btn btn-default" value="取消申请"/>
</div>
<script>
    $('textarea.limited').inputlimiter();
    $("#submit").click(function(){
        if($("#agree").is(":checked") == false){
            $('#agree').qtip({content:'请确认信息准确无误。',show: true, hide: 'unfocus'});
            return false;
        }

        $.post("${ctx}/user/passportDraw_self_au",
                {applyId:"${applySelf.id}",
                    passportId:"${param.passportId}",
                    needSign:"${param.sign}", remark:$("#remark").val()},function(ret){
            if(ret.success){
                SysMsg.success('您的申请已提交，谢谢！', '提示', function(){
                    location.href = "${ctx}/user/passportDraw?type=1";
                });
            }
        });
    });
</script>