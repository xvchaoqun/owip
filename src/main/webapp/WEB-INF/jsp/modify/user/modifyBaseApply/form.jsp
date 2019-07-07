<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<%@ include file="/WEB-INF/jsp/modify/constants.jsp"%>
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
            ${uv.realname}
          </td>

          <td class="bg-right">
            工作证号
          </td>
          <td class="bg-left" style="min-width: 150px;">
            ${uv.code}
          </td>
          <td class="bg-right">性别</td>
          <td class="bg-left" style="min-width: 150px;">
              ${GENDER_MAP.get(uv.gender)}
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
              ${uv.nation}
          </td>
          <td class="bg-right">出生日期</td>
          <td class="bg-left">
              ${cm:formatDate(uv.birth,'yyyy-MM-dd')}
          </td>
          <td class="bg-right">
            年龄
          </td>
          <td class="bg-left">
              ${uv.birth==null?'':cm:intervalYearsUntilNow(uv.birth)}
          </td>
        </tr>
        <tr>
          <td>政治面貌</td>
          <td>
            <%--<c:set var="original" value="${cm:cadreParty(cadre.isOw, cadre.owGrowTime, '中共党员', cadre.dpTypeId, cadre.dpGrowTime, false).get('partyName')}"/>
            <input type="text"
                   data-code="political_status"
                   data-table=""
                   data-table-id-name=""
                   data-name="政治面貌"
                   data-original="${original}"
                   data-type="${MODIFY_BASE_ITEM_TYPE_STRING}"
                   value="${original}">--%>

              <c:set var="original" value="${cm:cadreParty(cadre.isOw, cadre.owGrowTime, '中共党员', cadre.dpTypeId, cadre.dpGrowTime, false).get('partyName')}"/>
            <c:if test="${fn:contains(original, ',')}">${original}</c:if><!--有多个党派不允许在此修改-->
            <c:if test="${!fn:contains(original, ',')}">
                  <c:choose>
                      <c:when test="${cadre.dpTypeId>0}">
                          <c:set var="original" value="${cadre.dpTypeId}"/>
                      </c:when>
                      <c:when test="${cadre.isOw}">
                          <c:set var="original" value="0"/>
                      </c:when>
                  </c:choose>
                  <select data-rel="select2" name="dpTypeId"
                          data-code="political_status"
                          data-table=""
                          data-table-id-name=""
                          data-name="政治面貌"
                          data-original="${original}"
                          data-type="${MODIFY_BASE_ITEM_TYPE_INT}"
                          data-placeholder="请选择" data-width="162">
                      <option></option>
                      <option value="0">中共党员</option>
                       <jsp:include page="/metaTypes?__code=mc_democratic_party"/>
                  </select>
                    <div class="inline-block">注：政治面貌为“群众”等不在以上选项中的情况请留空</div>
                  <script type="text/javascript">
                      $("select[name=dpTypeId]").val(${original});
                  </script>
            </c:if>
          </td>
          <td>
            党派加入时间
              <c:if test="${cadre.dpTypeId>0}">
                <span>（${cm:getMetaType(cadre.dpTypeId).name}）</span>
            </c:if>
          </td>
          <td>
              <c:choose>
                  <c:when test="${cadre.dpTypeId>0}">
                      <c:set var="original" value="${cm:formatDate(cadre.dpGrowTime, 'yyyy-MM-dd')}"/>
                  </c:when>
                  <c:when test="${cadre.isOw}">
                      <c:set var="original" value="${cm:formatDate(cadre.owGrowTime, 'yyyy-MM-dd')}"/>
                  </c:when>
              </c:choose>
            <div class="input-group" style="width: 150px;">
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
            ${uv.country}
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
            ${uv.idcardType}
          </td>
          <td>
            证件号码
          </td>
          <td>
            ${uv.idcard}
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
                ${_pMap['nativePlaceHelpBlock']}
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
                ${_pMap['nativePlaceHelpBlock']}
            </div>
          </td>
        </tr>
        <tr>
          <td><span class="star">*</span>健康状况</td>
          <td>
              <c:set var="original" value="${uv.health}"/>
              <select required data-rel="select2" name="health"
                      data-code="health"
                      data-table="sys_user_info"
                      data-table-id-name="user_id"
                      data-name="健康状况"
                      data-original="${original}"
                      data-type="${MODIFY_BASE_ITEM_TYPE_INT}"
                      data-placeholder="请选择" data-width="162">
                  <option></option>
                  <c:import url="/metaTypes?__code=mc_health"/>
              </select>
              <script type="text/javascript">
                  $("select[name=health]").val(${original});
              </script>
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
            <i class="ace-icon fa fa-info-circle blue"></i>
            人事信息
        </h4>
    </div>

    <div class="widget-body">
        <div class="widget-main no-padding">
            <table class="table table-unhover table-bordered table-striped">
                <tbody>
                <tr>
                    <td style="width: 300px;">
                        参加工作时间
                    </td>
                    <td title="${hasVerifyWorkTime?'已根据您的档案记载对参加工作时间进行了组织认定':''}">
                        <c:set var="original" value="${cm:formatDate(cadre.workTime,'yyyy.MM')}"/>
                        <c:if test="${hasVerifyWorkTime}">${original}</c:if>
                        <c:if test="${!hasVerifyWorkTime}">
                        <div class="input-group" style="width: 180px">
                            <input class="form-control date-picker" type="text"
                                   data-date-min-view-mode="1" placeholder="yyyy.mm"
                                   data-date-format="yyyy.mm"
                                   data-code="work_time"
                                   data-table="sys_teacher_info"
                                   data-table-id-name="user_id"
                                   data-name="参加工作时间"
                                   data-original="${original}"
                                   data-type="${MODIFY_BASE_ITEM_TYPE_DATE}"
                                   value="${original}"/>
                            <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                        </div>
                        </c:if>
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
