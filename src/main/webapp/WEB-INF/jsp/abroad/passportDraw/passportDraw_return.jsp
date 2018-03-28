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
    <h3>归还证件</h3>
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
                    <c:set var="cadre" value="${cm:getCadreById(applySelf.cadreId)}"/>
                    <c:set var="sysUser" value="${cm:getUserById(cadre.userId)}"/>
                    <tr>
                        <td>S${applySelf.id}</td>
                        <td>${cm:formatDate(applySelf.applyDate,'yyyy-MM-dd')}</td>
                        <td>${ABROAD_APPLY_SELF_DATE_TYPE_MAP.get(applySelf.type)}</td>
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
                    <c:set var="cadre" value="${cm:getCadreById(passportDraw.cadreId)}"/>
                    <c:set var="sysUser" value="${cm:getUserById(cadre.userId)}"/>
                    <tr>

                        <td>${sysUser.code}</td>
                        <td><t:cadre cadreId="${passport.cadreId}" realname="${sysUser.realname}"/></td>
                        <td>${cadre.title}</td>
                        <td>${postMap.get(cadre.postId).name}</td>
                        <td>${passportTypeMap.get(passport.classId).name}</td>
                        <td>${passport.code}</td>
                        <td>${passport.authority}</td>
                        <td>${cm:formatDate(passport.issueDate,'yyyy-MM-dd')}</td>
                        <td>${cm:formatDate(passport.expiryDate,'yyyy-MM-dd')}</td>
                        <td>${cm:formatDate(passport.keepDate,'yyyy-MM-dd')}</td>
                        <td>${passport.safeBox.code}</td>
                        <td>${passport.isLent?"借出":"-"}</td>
                        <td>${ABROAD_PASSPORT_TYPE_MAP.get(passport.type)}</td>
                    </tr>
                    </tbody>
                </table>
            </div><!-- /.widget-main -->
        </div><!-- /.widget-body -->
    </div>
    <div class="well" style="margin-top: 20px;/* font-size: 20px*/">

            <form class="form-horizontal" action="${ctx}/abroad/passportDraw_return"
                  id="modalForm" method="post" enctype="multipart/form-data">
                <div class="row">
                <div class="col-xs-6" style="width: 500px;">
                <input type="hidden" value="${param.id}" name="id">
                <div class="form-group refuseReturnPassport">
                    <label class="col-xs-4 control-label">实交组织部日期</label>
                    <div class="col-xs-6">
                        <div class="input-group" style="width: 300px">
                            <input class="form-control date-picker" name="_realReturnDate" type="text"
                                   data-date-format="yyyy-mm-dd" value="${today}"/>
                            <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-xs-4 control-label">类别</label>
                    <div class="col-xs-8 label-text" style="font-size: 14px">
                        <input type="radio" name="usePassport" value="1" class="big" checked> 持证件出国（境）
                        <input type="radio" name="usePassport" value="0" class="big"> 未持证件出国（境）
                        <input type="radio" name="usePassport" value="2" class="big"> 拒不交回证件
                    </div>
                </div>

                <div id="illegalUsePassport" class="refuseReturnPassport">
                <div class="form-group">
                    <label class="col-xs-4 control-label">实际出发时间</label>
                    <div class="col-xs-6">
                        <div class="input-group" style="width: 300px">
                            <input class="form-control" name="_realStartDate" type="text"
                                   data-date-format="yyyy-mm-dd"/>
                            <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-xs-4 control-label">实际返回时间</label>
                    <div class="col-xs-6">
                        <div class="input-group" style="width: 300px">
                            <input class="form-control" name="_realEndDate" type="text"
                                   data-date-format="yyyy-mm-dd"/>
                            <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-xs-4 control-label">实际出国（境）天数</label>
                    <div class="col-xs-6 label-text">
                        <span id="_realDayCount"></span>
                    </div>
                </div>
                <c:if test="${not empty applySelf}">
                <div class="form-group">
                    <label class="col-xs-4 control-label">实际前往国家或地区</label>
                    <div class="col-xs-6">
                        <input type="text" name="realToCountry" id="form-field-tags"
                               <%--value="${applySelf.toCountry}"--%> placeholder="输入后选择国家或按回车 ..." />
                    </div>
                </div>
                </c:if>
                </div>
                <div class="form-group refuseReturnPassport">
                    <label class="col-xs-4 control-label">干部本人说明材料（要求pdf格式）</label>
                    <div class="col-xs-6"  style="width: 325px">
                        <input class="form-control" type="file" name="_attachment" />
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-xs-4 control-label">备注</label>
                    <div class="col-xs-6" >
                        <textarea class="form-control limited" style="width: 300px" type="text" name="remark" rows="5"></textarea>
                    </div>
                </div>
                </div>
                <div class="col-xs-6 refuseReturnPassport" style="width: 430px; margin-left: 50px;">
                    <div class="form-group">
                        <input type="file" name="_useRecord" />
                        <input type="hidden" name="_base64">
                        <input type="hidden" name="_rotate">
                        <div class="pull-right">或
                            <a href="javascript:" onclick="opencam()" class="btn btn-primary btn-xs">
                                <i class="fa fa-camera"></i>
                                点此拍照</a>
                            <a class="btn btn-warning btn-xs" onclick="_rotate()"><i class="fa fa-rotate-right"></i> 旋转</a>
                        </div>
                    </div>
                </div>
                </div>
            </form>

    </div>
</div>
<div class="modal-footer center" style="margin-bottom: 25px;">
    <c:set var="passport" value="${cm:getPassport(passportDraw.passportId)}"/>
    <c:set var="passportType" value="${cm:getMetaType(passport.classId)}"/>
    <input type="submit" data-name="${sysUser.realname}"
           data-cls="${passportType.name}"
           class="btn btn-success" value="确认"/>
    <input  class="hideView btn btn-default" value="返回"/>
</div>

<div class="webcam-container modal">
    <div class="modal-header">
        <button type="button" onclick="closecam()" class="close">&times;</button>
        <h3>点击允许，打开摄像头</h3>
    </div>
    <div class="modal-body">
    <div id="my_camera"></div>
        </div>
    <div class="modal-footer">
        <a href="javascript:" class="btn btn-success" onclick="snap()"><i class="fa fa-camera" aria-hidden="true" ></i> 拍照</a>
        <a href="javascript:" class="btn btn-default" onclick="closecam()"><i class="fa fa-close" aria-hidden="true" ></i> 取消</a>
    </div>
</div>

<style>
    /*.ace-file-multiple .ace-file-container{
        height: 350px;
    }*/
    .tags{
        width: 300px;
    }
    .ace-file-multiple .ace-file-container .ace-file-name .ace-icon{
        line-height: 260px;
        margin-top: -50px;
    }
    .ace-file-multiple .ace-file-container:before{
        line-height: 120px;
        font-size: 20pt;
    }
</style>
<script src="${ctx}/extend/js/webcam.min.js"></script>
<script src="${ctx}/extend/js/jQueryRotate.js"></script>

<script src="${ctx}/assets/js/bootstrap-tag.js"></script>
<script src="${ctx}/assets/js/ace/elements.typeahead.js"></script>
<script>

    var i=1;
    function _rotate(){
        $(".ace-file-input img").rotate(90*i);
        $("input[name=_rotate]").val(90*i);
        i++;
        //console.log($(".ace-file-input img").attr("src"))
    }
    function reset_rotate(){
        //alert("reset")
        i=1;
        $("input[name=_rotate]").val('');
    }

    function snap(){
        Webcam.snap( function(data_uri) {
            //console.log(data_uri)
            reset_rotate();
            $("input[name=_base64]").val(data_uri);
            $('input[type=file][name=_useRecord]').ace_file_input('show_file_list', [
                {type: 'image', name: '使用记录拍照.jpg', path: data_uri}]);
        } );
        Webcam.reset();
        $(".webcam-container").modal('hide');
    }
    function closecam(){
        Webcam.reset();
        $(".webcam-container").modal('hide');
    }

    function opencam(){

        Webcam.set({
            width: 640,
            height: 480,
            //force_flash: true,
            //flip_horiz:true,
            image_format: 'jpeg',
            jpeg_quality: 100
        });

        Webcam.attach('#my_camera');

        $(".webcam-container").modal('show').draggable({handle :".modal-header"});
    }

    var tag_input = $('#form-field-tags');
    try{
        tag_input.tag(
                {
                    placeholder:tag_input.attr('placeholder'),
                    //enable typeahead by specifying the source array
                    source: ${countryList}
                }
        )
    } catch(e) {
        //display a textarea for old IE, because it doesn't support this plugin or another one I tried!
        tag_input.after('<textarea id="'+tag_input.attr('id')+'" name="'+tag_input.attr('name')+'" rows="3">'+tag_input.val()+'</textarea>').remove();
        //autosize($('#form-field-tags'));
    }

    $.fileInput($('input[type=file][name=_useRecord]'),{
        style:'well',
        btn_choose:'请点击选择证件使用记录拍照',
        btn_change:null,
        no_icon:'ace-icon fa fa-picture-o',
        thumbnail:'fit',
        droppable:true,
        //previewWidth: 420,
        //previewHeight: 280,
        allowExt: ['jpg', 'jpeg', 'png', 'gif'],
        allowMime: ['image/jpg', 'image/jpeg', 'image/png', 'image/gif'],
        before_change:function(){
            reset_rotate();
            return true;
        },
        before_remove:function(){
            reset_rotate();
            $("input[name=_base64]").val('');
            return true;
        }
    })/*.end().find('button[type=reset]').on(ace.click_event, function(){

        $('input[type=file][name=_useRecord]').ace_file_input('reset_input');
    })*/

    $.fileInput($('input[type=file][name=_attachment]'),{
        allowExt: ['pdf']
    })

    $("input[name=usePassport]").click(function(){
        var val = $(this).val();
        if(val==0){
            $("#illegalUsePassport").slideUp();
        }else if(val==1){
            $("#illegalUsePassport").slideDown();
        }
        if(val==2){ // 拒不交回
            $(".refuseReturnPassport").hide();
            $("textarea[name=remark]").attr("required", "required");
        }else{
            $(".refuseReturnPassport").show();
            $("textarea[name=remark]").removeAttr("required");
        }
    });

    $("input[type=submit]").click(function(){

        var usePassport = $("input[name=usePassport]:checked").val();
        if(usePassport==undefined){
            SysMsg.info('请选择类别');
            return;
        }

       /* if($('input[type=file]').val()==''){
            SysMsg.info('请选择证件使用记录');
            return;
        }*/
        /*if($('input[name=_realStartDate]').val()==''){
            SysMsg.info('请选择实际出发时间','',function(){
                $('input[name=_realStartDate]').focus();
            });
            return;
        }
        if($('input[name=_realEndDate]').val()==''){
            SysMsg.info('请选择实际返回时间','',function(){
                $('input[name=_realEndDate]').focus();
            });
            return;
        }*/
        <%--<c:if test="${not empty applySelf}">
        if($('input[name=realToCountry]').val().trim()==''){
            SysMsg.info('请输入实际前往国家或地区','',function(){
                $('input[name=realToCountry]').focus();
            });
            return;
        }
            </c:if>--%>
        $("#modalForm").submit();return false;
    });
    $("#modalForm").validate({
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
                        bootbox.hideAll();
                        //SysMsg.success('归还成功。', '成功',function(){
                        $.hideView();
                        //});
                    }
                }
            });
        }
    });
    $.register.date($('.date-picker'))
    $.register.date($('input[name=_realStartDate], input[name=_realEndDate]'))
            .on("changeDate", function(e){
                var _realStartDate = $('input[name=_realStartDate]').val();
                var _realEndDate = $('input[name=_realEndDate]').val();
                $("#_realDayCount").html((_realStartDate>_realEndDate)?"日期选择错误":$.dayDiff(_realStartDate, _realEndDate))
            })
/*    $('.date-picker').datepicker({
        language:"zh-CN",
        autoclose: true,
        todayHighlight: true
    })*/
</script>
