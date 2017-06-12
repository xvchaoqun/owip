<%--
  Created by IntelliJ IDEA.
  User: fafa
  Date: 2016/2/28
  Time: 10:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div>
    <h3>领取证件</h3>
</div>
<div class="modal-body">
    <c:if test="${not empty applySelf}">
        <div class="widget-box transparent">
            <div class="widget-header widget-header-flat">
                <h4 class="widget-title lighter">
                    <i class="ace-icon fa fa-plane green"></i>
                    因私出国（境）行程
                </h4>
                <div class="widget-toolbar">
                    <a href="javascript:;" data-action="collapse">
                        <i class="ace-icon fa fa-chevron-up"></i>
                    </a>
                </div>
            </div>
            <div class="widget-body" style="display: block;">
                <div class="widget-main no-padding">
                    <table class="table table-actived table-striped table-bordered table-hover">
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
                            <th>审批情况</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:set var="cadre" value="${cadreMap.get(applySelf.cadreId)}"/>
                        <c:set var="sysUser" value="${cm:getUserById(cadre.userId)}"/>
                        <tr>
                            <td>S${applySelf.id}</td>
                            <td>${cm:formatDate(applySelf.applyDate,'yyyy-MM-dd')}</td>
                            <td>${APPLY_SELF_DATE_TYPE_MAP.get(applySelf.type)}</td>
                            <td>${cm:formatDate(applySelf.startDate,'yyyy-MM-dd')}</td>
                            <td>${cm:formatDate(applySelf.endDate,'yyyy-MM-dd')}</td>
                            <td>${cm:getDayCountBetweenDate(applySelf.startDate,applySelf.endDate)}</td>
                            <td>${applySelf.toCountry}</td>
                            <td>${fn:replace(applySelf.reason, '+++', ',')}</td>
                            <td>
                                    ${applySelf.isFinish?(cm:getMapValue(0, applySelf.approvalTdBeanMap).tdType==6?"通过":"未通过"):"未完成审批"}
                            </td>
                        </tr>
                        </tbody>
                    </table>
                </div><!-- /.widget-main -->
            </div><!-- /.widget-body -->
        </div>
    </c:if>
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
                        <td><mytag:cadre cadreId="${passport.cadreId}" realname="${sysUser.realname}"/></td>
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
    <div class="well center" style="margin-top: 20px; font-size: 20px">
        <div class="row" style="padding-left: 100px">
            <form class="form-horizontal" action="${ctx}/passportDraw_draw"
                  id="modalForm" method="post" enctype="multipart/form-data">
                <input type="hidden" value="${param.id}" name="id">
                <div class="form-group">
                    <label class="col-xs-3 control-label" style="line-height: 100px">领取证件拍照</label>
                    <div class="col-xs-2 file" style="width:300px;">
                        <input type="file" name="_drawRecord" />
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-xs-3 control-label">归还时间</label>
                    <div class="col-xs-6">
                        <div class="input-group" style="width: 200px">
                            <input required class="form-control date-picker" name="_returnDate" type="text"
                                   data-date-format="yyyy-mm-dd"/>
                            <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                        </div>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>
<div class="modal-footer center">
    <input type="submit" class="btn btn-success" value="提交" style="width: 150px"/>
    <input type="button" class="hideView btn btn-default" value="返回" style="width: 150px"/>
</div>
<script>
    $('input[type=file]').ace_file_input({
        style:'well',
        btn_choose:'请选择证件拍照',
        btn_change:null,
        no_icon:'ace-icon fa fa-picture-o',
        thumbnail:'fit',
        droppable:true,
        //previewWidth: 100,
        //previewHeight: 75,
        allowExt: ['jpg', 'jpeg', 'png', 'gif'],
        allowMime: ['image/jpg', 'image/jpeg', 'image/png', 'image/gif']
    }).end().find('button[type=reset]').on(ace.click_event, function(){
        $('input[type=file]').ace_file_input('reset_input');
    });

    var msg;
    $("input[type=submit]").click(function(){
        /*if($('input[type=file]').val()==''){
            SysMsg.info('请选择证件拍照');
            return;
        }*/
        if($('input[name=_returnDate]').val()==''){
            SysMsg.info('请选择归还时间','',function(){
                $('input[name=_returnDate]').focus();
            });
            return;
        }

        var date = (new Date($('input[name=_returnDate]').val())).format("yyyy年M月d日");
        msg ="${shortMsg}".format(null, null, date);

        bootbox.confirm({
            buttons: {
                confirm: {
                    label: '确认领取',
                    className: 'btn-success ${empty mobile?"disabled":""}'
                },
                cancel: {
                    label: '取消',
                    className: 'btn-default btn-show'
                }
            },
            message: '<p style="padding:30px;font-size:20px;text-indent: 2em; ">' + msg + '</p>',
            callback: function(result) {
                <c:if test="${not empty mobile}">
                if(result) {
                    $("#modalForm").submit();return false;
                }
                </c:if>
            },
            title: '短信发送【<i class="fa fa-mobile" aria-hidden="true"></i>${empty mobile?"<span style=\"color:red;font-weight:bolder\">手机号码为空</span>":mobile}】'
        });  });

    $("#modalForm").validate({
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
                        bootbox.hideAll();
                        $.post("${ctx}/shortMsg", {id:'${passportDraw.id}', type:'passportDraw'}, function(ret){
                            if(ret.success) {
                                //SysMsg.success('通知成功', '提示', function () {
                                $.hideView();
                                //});
                            }
                        })
                    }
                }
            });
        }
    });
    register_date($('.date-picker'))
    /*$('.date-picker').datepicker({
        language:"zh-CN",
        autoclose: true,
        todayHighlight: true
    })*/
</script>
