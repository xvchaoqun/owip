<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="modal-body"  style="min-width: 1200px">
<form class="form-horizontal" action="${ctx}/user/modifyBaseApply_au" id="modalForm" method="post" enctype="multipart/form-data">
<div class="widget-box transparent" id="view-box">
    <div class="widget-header">
        <h4 class="widget-title lighter smaller">
            <a href="javascript:;" class="closeView btn btn-xs btn-success">
                <i class="ace-icon fa fa-backward"></i>
                返回</a>
        </h4>
    </div>
    <div class="widget-body">
        <div class="widget-main no-padding">
            <div class="tab-content padding-8">
                <div class="widget-box transparent">
                    <div class="widget-header widget-header-flat">
                        <h4 class="widget-title lighter">
                            <i class="ace-icon fa fa-paw blue "></i>
                            基本信息
                        </h4>
                    </div>

                    <div class="widget-body">
                        <div class="widget-main no-padding">

                            <table class="table table-unhover table-bordered table-striped" >
                                <tbody>
                                <tr>
                                    <td id="_avatarTitle" class="bg-right" style="text-align: left!important;">头像：</td>

                                    <td class="bg-right">
                                        姓名
                                    </td>
                                    <td class="bg-left" style="min-width: 150px;">
                                        ${extJzg.xm}
                                    </td>

                                    <td class="bg-right">
                                        工作证号
                                    </td>
                                    <td class="bg-left" style="min-width: 150px;">
                                        ${extJzg.zgh}
                                    </td>
                                    <td class="bg-right">性别</td>
                                    <td class="bg-left" style="min-width: 150px;">
                                        ${extJzg.xb}
                                    </td>

                                </tr>
                                <tr>
                                    <td rowspan="5" style="text-align: center;
				                         width: 50px;background-color: #fff;">
                                        <div  style="width:170px">
                                            <input type="file" name="_avatar" id="_avatar"/>
                                        </div>
                                        <div>
                                            <a href="javascrip:;" class="btn btn-xs btn-primary" onclick='$("#_avatar").click()'>
                                                <i class="fa fa-upload"></i> 重传</a>
                                        </div>
                                    </td>
                                    <td class="bg-right">
                                        民族
                                    </td>
                                    <td class="bg-left">
                                        ${extJzg.mz}
                                    </td>
                                    <td class="bg-right">出生日期</td>
                                    <td class="bg-left">
                                        ${cm:formatDate(extJzg.csrq,'yyyy-MM-dd')}
                                    </td>
                                    <td class="bg-right">
                                        年龄
                                    </td>
                                    <td class="bg-left">
                                        ${empty xtJzg.csrq?'':cm:intervalYearsUntilNow(extJzg.csrq)}
                                    </td>
                                </tr>
                                <tr>
                                    <td>政治面貌</td>
                                    <td>
                                        <c:set var="original" value="${cadre.isDp?democraticPartyMap.get(cadre.dpTypeId).name:
                                                MEMBER_POLITICAL_STATUS_MAP.get(member.politicalStatus)}"/>
                                        <input type="text"
                                               data-code="political_status"
                                               data-table=""
                                               data-table-id-name=""
                                               data-name="政治面貌"
                                               data-original="${original}"
                                               data-type="${MODIFY_BASE_ITEM_TYPE_STRING}"
                                               value="${original}">
                                    </td>
                                    <td>
                                        党派加入时间
                                    </td>
                                    <td>
                                        <c:set var="original" value="${cadre.isDp?(cm:formatDate(cadre.dpAddTime,'yyyy-MM-dd')):(cm:formatDate(member.growTime,'yyyy-MM-dd'))}"/>
                                        <div class="input-group" style="width: 150px">
                                            <input class="form-control date-picker" type="text"
                                                   data-date-format="yyyy-mm-dd"
                                                   data-code="grow_time"
                                                   data-table=""
                                                   data-table-id-name=""
                                                   data-name="党派加入时间"
                                                   data-original="${original}"
                                                   data-type="${MODIFY_BASE_ITEM_TYPE_DATE}"
                                                   value="${original}"/>
                                            <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                                        </div>

                                    </td>

                                    <td>国家/地区</td>
                                    <td>
                                        ${extJzg.gj}
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        所在党组织
                                    </td>
                                    <td>
                                        ${cm:displayParty(member.partyId, member.branchId)}
                                    </td>

                                    <td>证件类型</td>
                                    <td>
                                        ${extJzg.name}
                                    </td>
                                    <td>
                                        证件号码
                                    </td>
                                    <td>
                                        ${extJzg.sfzh}
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        籍贯
                                    </td>
                                    <td style="min-width: 100px">
                                    <c:set var="original" value="${uv.nativePlace}"/>
                                    <input type="text"
                                           data-code="native_place"
                                           data-table="sys_user_info"
                                           data-table-id-name="user_id"
                                           data-name="籍贯"
                                           data-original="${original}"
                                           data-type="${MODIFY_BASE_ITEM_TYPE_STRING}"
                                           value="${original}">
                                    </td>
                                    <td>出生地</td>
                                    <td>
                                        <c:set var="original" value="${uv.homeplace}"/>
                                        <input type="text"
                                               data-code="homeplace"
                                               data-table="sys_user_info"
                                               data-table-id-name="user_id"
                                               data-name="出生地"
                                               data-original="${original}"
                                               data-type="${MODIFY_BASE_ITEM_TYPE_STRING}"
                                               value="${original}">
                                        <div class="inline-block">
                                            格式：“**省**市”或者“北京市***区”
                                        </div>
                                    </td>
                                    <td>
                                        户籍地
                                    </td>
                                    <td>
                                        <c:set var="original" value="${uv.household}"/>
                                        <input type="text"
                                               data-code="household"
                                               data-table="sys_user_info"
                                               data-table-id-name="user_id"
                                               data-name="户籍地"
                                               data-original="${original}"
                                               data-type="${MODIFY_BASE_ITEM_TYPE_STRING}"
                                               value="${original}">
                                        <div class="inline-block">
                                            格式：“**省**市”或者“北京市***区”
                                        </div>
                                    </td>
                                </tr>
                                <tr>
                                    <td>健康状况</td>
                                    <td>
                                        <c:set var="original" value="${uv.health}"/>
                                        <input type="text"
                                               data-code="health"
                                               data-table="sys_user_info"
                                               data-table-id-name="user_id"
                                               data-name="健康状况"
                                               data-original="${original}"
                                               data-type="${MODIFY_BASE_ITEM_TYPE_STRING}"
                                               value="${original}">
                                    </td>
                                    <td>
                                        熟悉专业有何专长
                                    </td>
                                    <td colspan="3">
                                        <c:set var="original" value="${uv.specialty}"/>
                                        <input type="text"
                                               data-code="specialty"
                                               data-table="sys_user_info"
                                               data-table-id-name="user_id"
                                               data-name="熟悉专业有何专长"
                                               data-original="${original}"
                                               data-type="${MODIFY_BASE_ITEM_TYPE_STRING}"
                                               value="${original}" style="width: 500px">
                                    </td>
                                </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
                <div class="widget-box transparent">
                    <div class="widget-header widget-header-flat">
                        <h4 class="widget-title lighter">
                            <i class="ace-icon fa fa-phone-square blue"></i>
                            联系方式
                        </h4>
                    </div>

                    <div class="widget-body">
                        <div class="widget-main no-padding">
                            <table class="table table-unhover table-bordered table-striped">
                                <tbody>
                                <tr>
                                    <td>
                                        手机号
                                    </td>
                                    <td style="min-width: 80px">
                                            <c:set var="original" value="${uv.mobile}"/>
                                            <input type="text"
                                                   data-code="mobile"
                                                   data-table="sys_user_info"
                                                   data-table-id-name="user_id"
                                                   data-name="手机号"
                                                   data-original="${original}"
                                                   data-type="${MODIFY_BASE_ITEM_TYPE_STRING}"
                                                   value="${original}">
                                    </td>
                                    <td>
                                        办公电话
                                    </td>
                                    <td style="min-width: 80px">
                                        <c:set var="original" value="${uv.phone}"/>
                                        <input type="text"
                                               data-code="phone"
                                               data-table="sys_user_info"
                                               data-table-id-name="user_id"
                                               data-name="办公电话"
                                               data-original="${original}"
                                               data-type="${MODIFY_BASE_ITEM_TYPE_STRING}"
                                               value="${original}">
                                    </td>

                                    <td>
                                        电子邮箱
                                    </td>
                                    <td style="min-width: 80px">
                                            <c:set var="original" value="${uv.email}"/>
                                            <input type="text"
                                                   data-code="email"
                                                   data-table="sys_user_info"
                                                   data-table-id-name="user_id"
                                                   data-name="电子邮箱"
                                                   data-original="${original}"
                                                   data-type="${MODIFY_BASE_ITEM_TYPE_STRING}"
                                                   value="${original}">
                                    </td>
                                </tr>

                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- /.widget-box -->
    <div class="clearfix form-actions center">
        <c:if test="${not empty mba}">
            您的申请已提交，请等待审核。
        </c:if>
        <c:if test="${empty mba}">
        <button class="btn btn-info" type="submit">
            <i class="ace-icon fa fa-check bigger-110"></i>
            保存
        </button>

        &nbsp; &nbsp; &nbsp;
        <button class="closeView btn" type="button">
            <i class="ace-icon fa fa-undo bigger-110"></i>
            取消
        </button>
        </c:if>
    </div>
</form>
</div>
<div class="footer-margin"/>
<script>
    $("#_avatar").ace_file_input({
        style:'well',
        btn_choose:'更换头像',
        btn_change:null,
        no_icon:'ace-icon fa fa-picture-o',
        thumbnail:'large',
        maxSize:${_uploadMaxSize},
        droppable:true,
        previewWidth: 143,
        previewHeight: 198,
        allowExt: ['jpg', 'jpeg', 'png', 'gif'],
        allowMime: ['image/jpg', 'image/jpeg', 'image/png', 'image/gif']
    }).off('file.error.ace').on("file.error.ace",function(e, info){
        var size = info.error_list['size'];
        if(size!=undefined) alert("文件{0}超过${_uploadMaxSize/(1024*1024)}M大小".format(size));
        var ext = info.error_count['ext'];
        var mime = info.error_count['mime'];
        if(ext!=undefined||mime!=undefined) alert("请上传图片文件（jpg或png格式)".format(ext));
        e.preventDefault();

    }).end().find('button[type=reset]').on(ace.click_event, function(){
        //$('#user-profile input[type=file]').ace_file_input('reset_input');
        $("#_avatar").ace_file_input('show_file_list', [{type: 'image', name: '${ctx}/avatar/${uv.username}'}]);
    });
    $("#_avatar").ace_file_input('show_file_list', [{type: 'image', name: '${ctx}/avatar/${uv.username}'}]);

    <c:if test="${not empty mbis}">
    var mbis = ${cm:toJSONArray(mbis)};
    for(i in mbis){
        var mbi = mbis[i];
        if(mbi.code=='avatar'){
            $("#_avatarTitle").addClass("text-danger bolder");
            $("#_avatar").ace_file_input('show_file_list', [{type: 'image',
                name: '${ctx}/avatar?path={0}'.format(encodeURI(mbi.modifyValue))}]);
        }else {
            var $item = $("[data-code='{0}'][data-table='{1}']".format(mbi.code, mbi.tableName));
            $item.val(mbi.modifyValue);
            $item.closest("td").prev().addClass("text-danger bolder");
        }
    }
    </c:if>

    $("#modalForm").validate({
        submitHandler: function (form) {

            var codes=[], tables=[], tableIdNames=[], names=[], originals=[], modifys=[], types=[];
            $("input[data-code]").each(function(){
                codes.push($(this).data("code"));
                tables.push($(this).data("table"));
                tableIdNames.push($(this).data("table-id-name"));
                names.push($(this).data("name"));
                originals.push($(this).data("original"));
                modifys.push($(this).val());
                types.push($(this).data("type"));
            })
            //console.log(codes)
            //console.log(originals)
            //console.log(modifys)

            $(form).ajaxSubmit({
                data:{codes:codes, tables:tables, tableIdNames:tableIdNames,
                    names:names, originals:originals, modifys:modifys, types:types},
                success: function (ret) {
                    if (ret.success) {
                        $("#jqGrid").trigger("reloadGrid");
                        $(".closeView").click();
                    }
                }
            });
        }
    });

    register_date($('.date-picker'));
</script>