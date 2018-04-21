<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <div id="body-content">
            <div class="row">
                <div class="col-sm-8 infobox-container">
                    <div class="openView infobox infobox-blue2" data-open-by="page" style="padding-top: 12px"
                         data-url="${ctx}/m/cadre_search_byName">
                        <button class="btn btn-success btn-block"><i class="fa fa-arrow-right"></i> 按姓名查询</button>
                    </div>
                </div>
                <div class="col-sm-8 infobox-container">
                    <div class="openView infobox infobox-blue2" data-open-by="page" style="padding-top: 12px"
                         data-url="${ctx}/m/cadre_search_byUnit">
                        <button class="btn btn-info btn-block"><i class="fa fa-arrow-right"></i> 按单位查询</button>
                    </div>
                </div>
               <%-- <div class="col-sm-8 infobox-container">
                    <div class="openView infobox infobox-blue2" data-open-by="page" style="padding-top: 12px"
                         data-url="${ctx}/m/cadre_search">
                        <button class="btn btn-primary btn-block" style=><i class="fa fa-arrow-right"></i> 搜索<span
                                style="margin-left: 35px;"/></button>
                    </div>
                </div>--%>
                <div class="col-sm-8 infobox-container">
                    <div class="openView infobox infobox-blue2" data-open-by="page" style="padding-top: 12px"
                         data-url="${ctx}/m/cadre_compare">
                        <button class="btn btn-warning btn-block" style=><i class="fa fa-arrow-right"></i> 比对<span
                                style="margin-left: 37px;"/></button>
                    </div>
                </div>
            </div>
        </div>
        <div id="body-content-view"<%-- style="overflow-y: hidden"--%>>
        </div>
    </div>
</div>