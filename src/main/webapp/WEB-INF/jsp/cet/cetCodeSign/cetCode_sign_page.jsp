<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<%@ include file="/WEB-INF/jsp/cet/constants.jsp" %>
<html>
<head>
    <title>二维码签到</title>
    <script type="text/javascript" src="${ctx}/assets/js/jquery.js"></script>
    <script type="text/javascript" src="${ctx}/js/prototype.js"></script>
</head>
<body>
<c:if test="${empty cetTrainCourse}">
</c:if>
<c:if test="${not empty cetTrainCourse}">
<div class="container">
    <div class="jumbotron">
        <table>
            <tr>
                <td width="500" align="center">
            <div id="codeSign">
        <c:if test="${cls==1}">
            <div style="position: absolute;left:-500px">
            <h2 style="padding-bottom: 15px;">【${cetTrainCourse.cetCourse.name}】签到</h2>
            <p>
            <label>参训人学工号：</label>
            <input oninput="changed()" type="text" name="codeSignIn" style="width: 300px;;height: 30px;">
            </p>
            </div>
        </c:if>
        <c:if test="${cls==0}">
            <div style="position: absolute;left:-500px">
            <h2 style="padding-bottom: 15px;">【${cetTrainCourse.cetCourse.name}】签退</h2>
            <p>
            <label class="control-label">参训人学工号：</label>
            <input oninput="changed()" type="text" name="codeSignOut" style="width: 300px;;height: 30px;">
            </p>
            </div>
        </c:if>
            </div>
            <c:set value="<%=RequestUtils.getHomeURL(request)%>" var="cetCodeSignLoginUrl"/>
            <c:set value="${cetCodeSignLoginUrl}/m/cet_eva/login" var="cetCodeSignLoginUrl"/>
                <c:set var="loginUrl" value="${cetCodeSignLoginUrl}?id=${cetTrainCourse.id}" scope="request"></c:set>
                <p><label>扫描二维码进行账号绑定</label></p>
                <img src="${ctx}/qrcode?content=${cm:encodeURI(requestScope.loginUrl)}" style="width: 200px; height:200px;margin: 20px"/>
                </td>
                <td>
        <div style="width: 500px;max-height: 400px;overflow-y: auto">
            <ul id="history" class="list-group">
                <c:forEach items="${cetTraineeCourseViews}" var="record">
                    <li class="list-group-item">${record.user.code}&nbsp;&nbsp;${record.user.realname}&nbsp;&nbsp;${cm:formatDate(record.signTime, "yyyy-MM-dd HH:mm:ss")}</li>
                </c:forEach>
                <li class="list-group-item">每次成功扫描，将在此列表显示状态。</li>
            </ul>
        </div>
                </td>
            </tr>
        </table>
    </div>
    <div>
        <h2>使用说明:</h2>
        <h4>
            1、将扫描器连接至电脑；
        </h4>
        <h4>
            2、签到成员先扫描二维码进行工号与微信号的绑定，首次绑定，微信号为必填项（请确认微信号输入无误），然后会得到一个签到二维码；
        </h4>
        <h4>
            3、将二维码防止扫描器上进行签到，签到信息会在页面右侧进行显示。
        </h4>
        <h4>
            4、刷新页面后，签到信息可在"后台签到"中查看。
        </h4>
    </div>
</div>
</c:if>
<link href="${ctx}/assets/css/bootstrap.css" rel="stylesheet">
<style>
    body {
        font-family : '微软雅黑';
    }
</style>
</body>
</html>
<script type="text/javascript">

    $(function(){
        getFocus();
        setInterval(function(){
            getFocus();
        },500);
    });

    function getFocus(){
        <c:if test="${cls==1}">
            $("input[name=codeSignIn]").focus();
        </c:if>
        <c:if test="${cls==0}">
            $("input[name=codeSignOut]").focus();
        </c:if>
        //console.log("每5秒获取一次焦点");
    }

    function changed() {

        var $codeSignIn = $("input[name=codeSignIn]").val();
        var $codeSignOut = $("input[name=codeSignOut]").val();
        var pattern = new RegExp("[*]");

        if ($codeSignIn == "" || $codeSignOut == ""){
            return;
        }
        if (pattern.test($codeSignIn) || pattern.test($codeSignOut)) {
            //alert("请不要输入特殊字符!");
            $.post('${ctx}/cet/codeSign', {codeSignIn: $codeSignIn, codeSignOut:$codeSignOut, trainCourse:${cetTrainCourse.id}}, function (ret) {
                console.log($codeSignIn);
                if (ret.success) {
                    //console.log($codeSignIn);
                    var $li = $('<li class="list-group-item">{0}&nbsp;&nbsp;{1}&nbsp;&nbsp;{2}</li>'.format(ret.uv.code, ret.uv.realname, ret.signTime));
                    $("#history").prepend($li);
                } else {
                    //console.log(ret.msg);
                    $("#history").prepend($('<li class="list-group-item"><span class="text-danger">{0}</span></li>'
                        .format(ret.msg)));
                }
                $("input[name=codeSignIn]").val("");
                $("input[name=codeSignOut]").val("");
            });

        }
        return;
    }

    var timearr = [0,0];
    $('input').keyup(function(e){
        if ($(this).val().length%2 != 0){
            //求余数不为0是奇数
            timearr[0] = new Date().getTime();
        } else {
            timearr[1] = new Date().getTime();
        }
        //当输入第二位时判断两次输入的间隔，判断是否为手动输入，间隔过长清空值
        if($(this).val().length > 1 && Math.abs(timearr[1] - timearr[0] > 30)){
            $(this).val('')
        }
        if (Math.abs(timearr[1] - timearr[0] == 0)){
            $(this).val('')
        }
    })

    /*function noPermitInput(e){
        var evt = window.event || e ;
        if(isIE()){
            evt.returnValue=false; //ie 禁止键盘输入
        }else{
            evt.preventDefault(); //fire fox 禁止键盘输入
        }
    }
    function isIE() {
        if (window.navigator.userAgent.toLowerCase().indexOf("msie") >= 1)
            return true;
        else
            return false;
    }*/
</script>