<%--
  Created by IntelliJ IDEA.
  User: fafa
  Date: 2015/11/11
  Time: 15:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
  <div class="col-xs-12">
    <!-- PAGE CONTENT BEGINS -->
    <!-- #section:pages/error -->
    <div class="error-container">
      <div class="well">
        <h1 class="grey lighter smaller">
					<span class="blue bigger-125">
						<i class="ace-icon fa fa-sitemap"></i>
						500
					</span>
          系统错误${exception}
        </h1>
        <hr />
        <div style="padding-left: 50px">
          <div class="space"></div>
          <ul class="list-unstyled spaced inline bigger-110 margin-15 ">


            <li>
              <h3><i class="ace-icon fa fa-hand-o-right blue"></i> 请联系系统管理员</h3>
            </li>

          </ul>
        </div>
        <hr />
        <div class="space"></div>
        <div class="center">

          <a href="/" class="btn btn-primary">
            <i class="ace-icon fa fa-home"></i>
            回到首页
          </a>

          <a href="/logout" class="btn btn-success">
            <i class="ace-icon fa fa-home"></i>
            安全退出
          </a>
        </div>
      </div>
    </div>

</div>