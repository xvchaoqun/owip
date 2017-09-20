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
            <a href="javascript:;" data-action="collapse">
                <i class="ace-icon fa fa-chevron-up"></i>
            </a>
        </div>
    </div>
    <div class="widget-body" style="display: block;">
        <div class="widget-main no-padding">
            <table class="table table-striped table-bordered table-hover">
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
                    <td>
                        <t:cadre cadreId="${passport.cadreId}" realname="${sysUser.realname}"/>
                    </td>
                    <td>${cadre.title}</td>
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
        <img data-src="${ctx}/report/passportSign?id=${param.id}" src="${ctx}/img/loading.gif"
             onload="lzld(this)" />
    </div>
    </c:if>
    <div class="info">
        <div style="margin: 30px 0 30px 0;border: 1px dashed #aaaaaa;padding: 20px">
            <form class="form-horizontal">
                <div class="form-group">
                    <label class="col-xs-3 control-label">备注</label>
                    <div class="col-xs-6">
                        <textarea class="form-control limited" type="text" name="remark" rows="3"></textarea>
                    </div>
                </div>
            </form>
            <div>
                <button id="agree" class="btn btn-success btn-block" style="margin-top:20px;font-size: 20px">符合条件，同意领取证件</button>
            </div>
        </div>
        <div style="margin: 30px 0 30px 0;border: 1px dashed #aaaaaa;padding: 20px">
            <form class="form-horizontal">
                <div class="form-group">
                    <label class="col-xs-3 control-label">原因</label>
                    <div class="col-xs-6">
                        <textarea class="form-control limited" type="text" name="reason" rows="3"></textarea>
                    </div>
                </div>
            </form>
            <div>
                <button id="disagree" class="btn btn-danger btn-block" style="margin-top:20px;font-size: 20px">不符合条件，不同意领取证件</button>
            </div>
        </div>
        <div class="center" style="margin-top: 40px">
            <button class="hideView btn btn-default btn-block" style="margin-top:20px;font-size: 30px">返回</button>
        </div>
    </div>
</div>
<script src="${ctx}/extend/js/jquery.jqprint-0.3.js"></script>
<script>

    $("#agree").click(function(){
        var remark = $("textarea[name=remark]").val().trim();
        $.post("${ctx}/abroad/passportDraw_agree",{id:"${param.id}", remark:remark},function(ret){
            if(ret.success){
                //SysMsg.success('审批成功', '提示', function(){
                    //page_reload();
                $.hideView();
                //});
            }
        });
    });
    $("#disagree").click(function(){
        var reason = $("textarea[name=reason]").val().trim();
        if( reason == ''){
            SysMsg.info("请填写原因");
            return false;
        }
        $.post("${ctx}/abroad/passportDraw_disagree",{id:"${param.id}", remark:reason},function(ret){
            if(ret.success){
                //SysMsg.success('提交成功', '提示', function(){
                    //page_reload();
                $.hideView();
                //});
            }
        });
    });
    register_date($('.date-picker'))
    $('textarea.limited').inputlimiter();
</script>