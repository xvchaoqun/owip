<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<html>
<head>
    <title>刷卡签到</title>
    <script type="text/javascript" src="${ctx}/assets/js/jquery.js"></script>
    <script type="text/javascript" src="${ctx}/js/prototype.js"></script>
    <script id="clientEventHandlersJS" language="javascript" for="ReadcardControl"
            event="ChipOver(code,AccountNo,CardNo,PID,ExpiredDate,Balance)">
        //code = '06141'
        $.post('${ctx}/cet/sign', {id: '${param.id}',token:'${param.token}', code: code}, function (ret) {
            if (ret.success) {
                var $li = $('<li class="list-group-item">{0}&nbsp;&nbsp;{1}&nbsp;&nbsp;{2}</li>'.format(code, ret.realname, ret.signTime));
                $("#history").prepend($li);
            } else {
                $("#history").prepend($('<li class="list-group-item"><span class="text-danger">{0}</span></li>'
                    .format(ret.msg)));
            }
        });
    </script>
</head>
<body>
<c:if test="${empty cetTrainCourse}">
<div style="padding: 10px;">签到页面已过期，请重新生成。</div>
</c:if>
<c:if test="${not empty cetTrainCourse}">
<div class="container">
    <div class="jumbotron">
        <h2 style="padding-bottom: 15px;">【${cetTrainCourse.cetCourse.name}】</h2>
        <%--<p>连接读卡器 安装驱动 调整安全设置 点击开始读卡</p>--%>
        <p>
            <input class="btn btn-primary btn-lg" id="btnStart" type="button" value="开始读卡" name="btnStart"
                   onclick="_start()"/>
            <input class="btn btn-primary btn-lg" id="btnStop" type="button" value="停止读卡" name="btnStop"
                   onclick="_stop()"/>
        </p>
        <div style="max-height: 400px;overflow-y: auto">
            <ul id="history" class="list-group">
                <c:forEach items="${cetTraineeCourseViews}" var="record">
                    <li class="list-group-item">${record.user.code}&nbsp;&nbsp;${record.user.realname}&nbsp;&nbsp;${cm:formatDate(record.signTime, "yyyy-MM-dd HH:mm:ss")}</li>
                </c:forEach>
                <li class="list-group-item">每次成功读卡，将在此列表显示状态。</li>
            </ul>
        </div>
    </div>
    <div>
        <h2>使用说明（校外请连接VPN后使用）:</h2>
        <h4>1.连接读卡器并安装驱动：</h4>
        <p>
            <a href="${ctx}/assets/sign/dkq.zip" target="_blank">点击下载驱动程序。</a><br/>
            将读卡器通过USB接口接入电脑，通过“计算机（右键）—管理—设备管理器”找到“新中新Synjones Full-speed USB Desk Dkq
            Device”，在此设备上点击右键，选择“更新驱动程序”。<br/>
            根据提示进行如下选择和操作“浏览计算机以查找驱动程序软件—浏览（选择驱动程序所在文件夹）—下一步”，驱动程序将自动安装，系统提示安装成功后，可进行下一步操作。<br/>
            提示：已经安装并使用过读卡器的电脑不需进行此操作。<br/></p>
        <h4>2.调整安全设置，安装ActiveX控件：</h4>
        <p>
            打开IE浏览器，进入报到注册网址，浏览器会提示“Internet Explorer组织了此网站安装ActiveX控件”，点击提示信息后的安装，安装控件成功安装后刷新页面，即可进行读卡。<br/>
            如果安装失败，请调整以下浏览器设置后刷新页面<br/>
            <a href="http://jingyan.baidu.com/article/9113f81b227f802b3314c76c.html" target="_blank">http://jingyan.baidu.com/article/9113f81b227f802b3314c76c.html<br/></a>
        </p>
        <%--<h4>3.注册：</h4>
        <p>点击开始读卡，即开始报到注册，注册历史信息会显示在列表中，请关注学生是否刷卡注册成功。</p>--%>
    </div>
    <OBJECT id="ReadcardControl" codebase="${ctx}/assets/sign/ReadChipControl.CAB#version=2,3,0,1"
            data="data:application/x-oleobject;base64,wfucGjGPW0eO92eRCyVX7QADAADUAAAA1AAAAA=="
            classid="clsid:1A9CFBC1-8F31-475B-8EF7-67910B2557ED" viewastext>
    </OBJECT>
    <script language="javascript">
        function _start() {
            try {
                ReadcardControl.SiosIP = "172.16.174.178";
                ReadcardControl.SysCode = 76;
                ReadcardControl.TerminalNo = 73;
                ReadcardControl.CardReaderType = 0;
                ReadcardControl.Start();
                //console.dir(ReadcardControl)
            } catch (e) {
                console.log(e.message)
                alert("启动读卡程序异常，本页面仅支持在IE浏览器下进行访问。");
            }
        }

        function _stop() {
            ReadcardControl.Stop()
            //console.dir(ReadcardControl)
        }

        //btnStop_onclick();
    </script>
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