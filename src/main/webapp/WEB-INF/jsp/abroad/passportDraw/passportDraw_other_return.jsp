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
                        <td>${PASSPORT_TYPE_MAP.get(passport.type)}</td>
                    </tr>
                    </tbody>
                </table>
            </div><!-- /.widget-main -->
        </div><!-- /.widget-body -->
    </div>
    <div class="well" style="margin-top: 20px; font-size: 20px">
        <div class="row" style="padding-left: 100px">
            <form class="form-horizontal" action="${ctx}/abroad/passportDraw_return"
                  id="modalForm" method="post" enctype="multipart/form-data">
                <input type="hidden" value="${param.id}" name="id">
                <div class="form-group">
                    <label class="col-xs-3 control-label">类别</label>
                    <div class="col-xs-6">
                    <input type="radio" name="usePassport" value="0" class="bigger"> 没有使用证件出国（境）
                    <input type="radio" name="usePassport" value="1" class="bigger"> 违规使用证件出国（境）
                    <input type="radio" name="usePassport" value="2" class="bigger"> 拒不交回证件
                    </div>
                </div>
                <div id="illegalUsePassport" style="display: none">
                <div class="form-group">
                    <label class="col-xs-3 control-label" style="line-height: 100px">证件使用记录</label>
                    <div class="col-xs-2 file" style="width:300px;">
                        <input type="file" name="_useRecord" />
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-xs-3 control-label">出国（境）时间</label>
                    <div class="col-xs-6">
                        <div class="input-group" style="width: 200px">
                            <input class="form-control date-picker" name="_realStartDate" type="text"
                                   data-date-format="yyyy-mm-dd"/>
                            <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-xs-3 control-label">回国时间</label>
                    <div class="col-xs-6">
                        <div class="input-group" style="width: 200px">
                            <input class="form-control date-picker" name="_realEndDate" type="text"
                                   data-date-format="yyyy-mm-dd"/>
                            <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                        </div>
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-xs-3 control-label">所到国家/地区</label>
                    <div class="col-xs-6">
                        <input type="text" name="realToCountry" id="form-field-tags" placeholder="输入后选择国家或按回车 ..." />
                    </div>
                </div>
                </div>
                <div class="form-group">
                    <label class="col-xs-3 control-label">备注</label>
                    <div class="col-xs-4">
                        <textarea class="form-control limited" type="text" name="remark" rows="5"></textarea>
                    </div>
                </div>

            </form>
        </div>
    </div>
</div>
<div class="modal-footer center">
    <c:set var="passport" value="${cm:getPassport(passportDraw.passportId)}"/>
    <c:set var="passportType" value="${cm:getMetaType(passport.classId)}"/>
    <input type="submit" data-name="${sysUser.realname}"
           data-cls="${passportType.name}"
           class="btn btn-success" value="确认归还"/>
    <input  class="hideView btn btn-default" value="返回"/>
</div>
<script src="${ctx}/assets/js/bootstrap-tag.js"></script>
<script src="${ctx}/assets/js/ace/elements.typeahead.js"></script>
<script>
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

    $.fileInput($('input[type=file]'),{
        style:'well',
        btn_choose:'请选择证件使用记录拍照',
        btn_change:null,
        no_icon:'ace-icon fa fa-picture-o',
        thumbnail:'large',
        droppable:true,
        previewWidth: 80,
        previewHeight: 100,
        allowExt: ['jpg', 'jpeg', 'png', 'gif'],
        allowMime: ['image/jpg', 'image/jpeg', 'image/png', 'image/gif']
    });
    /*$('button[type=reset]').on(ace.click_event, function(){
        $('input[type=file]').ace_file_input('reset_input');
    });*/

    $("input[name=usePassport]").click(function(){
        var val = $(this).val();
        if(val==0){
            $("#illegalUsePassport").slideUp();
        }
        if(val==1){
            $("#illegalUsePassport").slideDown();
        }
        if(val==2){ // 拒不交回
            $("#illegalUsePassport").slideUp();
            $("textarea[name=remark]").attr("required", "required");
        }else{
            $("textarea[name=remark]").removeAttr("required");
        }
    });
    $("input[type=submit]").click(function(){

        var usePassport = $("input[name=usePassport]:checked").val();
        if(usePassport==undefined){
            SysMsg.info('请选择类别');
            return;
        }
        if(usePassport==1) {

            /*if ($('input[type=file]').val() == '') {
                SysMsg.info('请选择证件使用记录');
                return;
            }*/
            /*if ($('input[name=_realStartDate]').val() == '') {
                SysMsg.info('请选择出国（境）时间', '', function () {
                    $('input[name=_realStartDate]').focus();
                });
                return;
            }
            if ($('input[name=_realEndDate]').val() == '') {
                SysMsg.info('请选择回国时间', '', function () {
                    $('input[name=_realEndDate]').focus();
                });
                return;
            }*/
            /*if ($('input[name=realToCountry]').val().trim() == '') {
                SysMsg.info('请输入所到国家/地区', '', function () {
                    $('input[name=realToCountry]').focus();
                });
                return;
            }*/
        }
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
    register_date($('.date-picker'))
</script>
