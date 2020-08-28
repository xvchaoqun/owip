<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<%@ include file="../constants.jsp"%>
<script>

    $('#candidateForm input[name=type]').change(function () {
        $("table").mask()
        _save($(this).val())
    })

    var $select = $.register.user_select($('select[data-rel=select2-ajax]'),
        {url:"${ctx}/user/pcs/member_selects?noAuth=1&partyId=${type==PCS_USER_TYPE_PR?inspector.partyId:''}&status=${MEMBER_STATUS_NORMAL}",
            theme:'default',language:"zh-CN"});

    var candidateUserIds=${empty candidateUserIds?'[]':candidateUserIds};//二下、三下候选人名单
    var selectedUserIds = [];//一下阶段选的人/二下、三下另选他人
    var $tip;

    //候选人radio初始化赋值
    /*defineWaschecked();
    function defineWaschecked() {
        $.each($(".candidate input[type=radio]"), function () {
            if ($(this).is(":checked")){
                $(this).data('waschecked', true);
            } else {
                $(this).data('waschecked', false);
            }
        })
    }*/

    $(".candidate input[type=radio]").click(function (e) {
        var $radio = $(this);
        var $otherTr = $("tr[data-candidate='" + $(this).attr("name") + "']");
        if ($radio.val() == ${RESULT_STATUS_AGREE} || $radio.val() == ${RESULT_STATUS_ABSTAIN}) {
            $otherTr.hide();
        } else {
            $otherTr.show();
        }

        if($radio.val()!=${RESULT_STATUS_DISAGREE}){
            //console.log("-----"+$radio.attr('name'))
            $("select[name="+$radio.attr('name')+"_4]").val(null).trigger("change");

            selectedUserIds = $.map($('select[data-rel=select2-ajax]'), function (sel) {
                return parseInt($(sel).val());
            });
        }

        /*if ($radio.data('waschecked') == true){
            $radio.attr('checked', false);
            $radio.data('waschecked', false);
        } else {
            $radio.attr('checked', true);
            $radio.data('waschecked', true);
        }
        //console.log($radio.parent().siblings("div").find('input[type="radio"]'))
        //将未选中的“waschecked”都设置为false
        $radio.parent().siblings("div").find('input[type="radio"]').data('waschecked', false);
        if ($(this).val() == ${RESULT_STATUS_DISAGREE}&&!$(this).is(":checked")){
                $otherTr.hide();
        }*/
    })

    $select.on("select2:select",function(e){

        var $this = $(this);
        if(<c:if test="${pcsPoll.stage!=PCS_POLL_FIRST_STAGE}">$.inArray(parseInt($this.val()), candidateUserIds)>=0||</c:if>
            $.inArray(parseInt($this.val()), selectedUserIds)>=0) {
            $tip = $.tip({
                $target: $this.closest("td").find(".select2-container"),
                at: 'top center', my: 'bottom center', type: 'success',
                msg: "您已经选择了该推荐人。"
            });
            $this.val(null).trigger("change");
        }else {
            if($tip!=undefined) {
                $tip.qtip('destroy', true);
            }
        }
        selectedUserIds = $.map($('select[data-rel=select2-ajax]'), function (sel) {
            return parseInt($(sel).val());
        });
    });
    $select.on("select2:unselect", function (evt) {
        var userId = $(this).val();
        selectedUserIds = $.map($('select[data-rel=select2-ajax]'), function (sel) {
            if(userId!=$(sel).val())
                return parseInt($(sel).val());
        });
    });

    function _confirm(isMobile) {
        if ($('#agree').is(':checked') == false) {
            $('#agree').qtip({content: '请您确认您已阅读推荐说明。', show: true});
            return false;
        }
        $("#agreeForm").ajaxSubmit({
            url: "${ctx}/user/pcs/agree?isMobile="+isMobile,
            success: function (data) {
                if (data.success) {
                    //console.log(data)
                    location.reload();
                }
            }
        });
    }

    //保存
    function _save(flag) {

        $("#saveBtn").button('loading');
        $("input[name=flag]").val(flag);//0保存按钮保存
        $("input[name=isSubmit]").val(0);
        $("#candidateForm").submit();
        return false;
    }

    //提交推荐数据
    function _submit(flag) {

        var isPositive = $("input[name=isPositive]:checked").val();
        if(isPositive!=0 && isPositive!=1){
            SysMsg.error("请选择投票人身份");
            return;
        }

        $("#checkSubmitBtn").button('loading');
        $("input[name=flag]").val(flag);//0保存按钮保存 4是先保存，然后弹出提示框,进行提交
        $("input[name=isSubmit]").val(0);
        $("#candidateForm").submit();
        return false;
    }

    var isMobile = $('#candidateForm input[name=isMobile]').val();
    $("#candidateForm").validate({
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                success: function (ret) {
                    if (ret.success) {
                        if ($("input[name=flag]").val() == 4) {
                            $.loadModal("${ctx}/user/pcs/submit_info");
                        }else if ($("input[name=flag]").val() != 0) {//切换人员类型时，保存数据
                            var type = $('#candidateForm input[name=type]:checked').val();
                            location.href="${ctx}/user/pcs/index?type="+type+"&isMobile="+isMobile;
                        }else if ($("input[name=isSubmit]").val() == 0) {
                            SysMsg.success('保存成功（数据还未提交，请填写完成后提交全部结果）。', '暂存')
                        }
                    }

                    $("#saveBtn").button('reset');
                    $("#checkSubmitBtn").button('reset');
                }
            });
        }
    });

    function _logout(isFinished) {
        location.href = "${ctx}/user/pcs/logout?isFinished="+$.trim(isFinished)+"&isMobile="+isMobile;
    }

</script>