<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="widget-box transparent">
    <div class="widget-header widget-header-flat">
        <h4 class="widget-title lighter">
            <i class="ace-icon fa fa-star orange"></i>
            证件信息
        </h4>
        <div class="widget-toolbar">
            <a href="#" data-action="collapse">
                <i class="ace-icon fa fa-chevron-up"></i>
            </a>
        </div>
    </div>
    <div class="widget-body" style="display: block;">
        <div class="widget-main no-padding">
            <table class="table table-striped table-bordered table-hover table-condensed">
                <thead>
                <tr>
                    <th>工作证号</th>
                    <th>姓名</th>
                    <th>所在单位和职务</th>
                    <th>职位属性</th>
                    <th>证件名称</th>
                    <th>证件号码</th>
                    <th>发证机关</th>
                    <th>发证日期</th>
                    <th>有效期</th>
                    <th>集中保管日期</th>
                    <th>存放保险柜编号</th>
                    <th>是否借出</th>
                    <th>类型</th>
                </tr>
                </thead>
                <tbody>
                <c:set var="cadre" value="${cadreMap.get(passportDraw.cadreId)}"/>
                <c:set var="sysUser" value="${cm:getUserById(cadre.userId)}"/>
                <tr>

                    <td>${sysUser.code}</td>
                    <td><a href="javascript:;" class="openView" data-url="${ctx}/cadre_view?id=${passport.cadreId}">
                        ${sysUser.realname}
                    </a></td>
                    <td>${unitMap.get(cadre.unitId).name}-${cadre.title}</td>
                    <td>${postMap.get(cadre.postId).name}</td>
                    <td>${passportTypeMap.get(passport.classId).name}</td>
                    <td>${passport.code}</td>
                    <td>${passport.authority}</td>
                    <td>${cm:formatDate(passport.issueDate,'yyyy-MM-dd')}</td>
                    <td>${cm:formatDate(passport.expiryDate,'yyyy-MM-dd')}</td>
                    <td>${cm:formatDate(passport.keepDate,'yyyy-MM-dd')}</td>
                    <td>${safeBoxMap.get(passport.safeBoxId).code}</td>
                    <td>${passport.isLent?"借出":"-"}</td>
                    <td>${PASSPORT_TYPE_MAP.get(passport.type)}</td>
                </tr>
                </tbody>
            </table>
        </div><!-- /.widget-main -->
    </div><!-- /.widget-body -->
</div>
<div class="row passport_apply">
    <c:if test="${passportDraw.needSign}">
    <div class="preview">
        <iframe id="myframe" src="/report/passportSign?id=${param.id}" width="595" height="842" frameborder="0"  border="0" marginwidth="0" marginheight="0"></iframe>
    </div>
    </c:if>
    <div class="info">
        <div style="margin: 30px 0 30px 0;border: 1px dashed #aaaaaa;padding: 20px">
            <%--<form class="form-horizontal">
                <div class="form-group">
                    <label class="col-xs-3 control-label">证件应交回日期</label>
                    <div class="col-xs-6">
                        <div class="input-group">
                            <input required class="form-control date-picker" name="_expectDate" type="text"
                                   data-date-format="yyyy年mm月dd日"/>
                            <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                        </div>
                    </div>
                </div>
            </form>--%>
            <div>
                <button id="agree" class="btn btn-success btn-block" style="margin-top:20px;font-size: 20px">符合条件，同意领取证件</button>
            </div>
        </div>
        <div style="margin: 30px 0 30px 0;border: 1px dashed #aaaaaa;padding: 20px">
            <form class="form-horizontal">
                <div class="form-group">
                    <label class="col-xs-3 control-label">原因</label>
                    <div class="col-xs-6">
                        <textarea ${passportApply.status==PASSPORT_APPLY_STATUS_NOT_PASS?"disabled":""}
                                class="form-control limited" type="text" name="remark" rows="3">${passportApply.remark}</textarea>
                    </div>
                </div>
            </form>
            <div>
                <button id="disagree" class="btn btn-danger btn-block" style="margin-top:20px;font-size: 20px">不符合条件，不同意领取证件</button>
            </div>
        </div>
        <div class="center" style="margin-top: 40px">
            <button class="closeView btn btn-default btn-block" style="margin-top:20px;font-size: 30px">返回</button>
        </div>
    </div>
</div>
<script src="${ctx}/extend/js/jquery.jqprint-0.3.js"></script>
<script>

    $("#agree").click(function(){
        var _expectDate = $("input[name=_expectDate]").val();
        if( _expectDate == ''){
            SysMsg.info("请填写应交回日期");
            return false;
        }
        $.post("${ctx}/passportDraw_agree",{id:"${param.id}"/*, _expectDate:_expectDate*/ },function(ret){
            if(ret.success){
                SysMsg.success('审批成功', '提示', function(){
                    page_reload();
                });

            }
        });
    });
    $("#disagree").click(function(){
        var remark = $("textarea[name=remark]").val().trim();
        if( remark == ''){
            SysMsg.info("请填写原因");
            return false;
        }
        $.post("${ctx}/passportDraw_disagree",{id:"${param.id}", remark:remark },function(ret){
            if(ret.success){
                SysMsg.success('提交成功', '提示', function(){
                    page_reload();
                });
            }
        });
    });

    $('.date-picker').datepicker({
        language:"zh-CN",
        autoclose: true,
        todayHighlight: true
    })
    $('textarea.limited').inputlimiter();
</script>