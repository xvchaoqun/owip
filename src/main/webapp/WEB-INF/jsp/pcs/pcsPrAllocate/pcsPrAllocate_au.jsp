<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <div class="allocateTable">
            <table class="table table-bordered table-striped" data-offset-height="101">
                <thead>
                <tr>
                    <th width="40" rowspan="2">序号</th>
                    <th rowspan="2">院系级党组织名称</th>
                    <th rowspan="2" width="60">正式党员数</th>
                    <th rowspan="2" width="70">代表总数</th>
                    <th colspan="3">代表构成</th>
                    <th colspan="3">其中</th>
                </tr>
                <tr>
                    <th width="110">专业技术人员和干部代表</th>
                    <th width="70">学生代表</th>
                    <th width="40">离退休代表</th>
                    <th width="70">妇女代表</th>
                    <th width="70">少数民族代表</th>
                    <th width="70">50岁以下代表</th>
                </tr>
                </thead>
                <tbody>
                <c:set var="positiveCount" value="0"/>
                <c:set var="rowCount" value="0"/>
                <c:set var="proCount" value="0"/>
                <c:set var="stuCount" value="0"/>
                <c:set var="retireCount" value="0"/>
                <c:set var="femaleCount" value="0"/>
                <c:set var="minorityCount" value="0"/>
                <c:set var="underFiftyCount" value="0"/>
                <c:forEach items="${records}" var="record" varStatus="vs">
                    <c:set var="_rowCount" value="${record.proCount + record.stuCount + record.retireCount}"/>
                    <c:set var="positiveCount" value="${positiveCount + record.positiveCount}"/>
                    <c:set var="proCount" value="${proCount + record.proCount}"/>
                    <c:set var="stuCount" value="${stuCount + record.stuCount}"/>
                    <c:set var="retireCount" value="${retireCount + record.retireCount}"/>
                    <c:set var="femaleCount" value="${femaleCount + record.femaleCount}"/>
                    <c:set var="minorityCount" value="${minorityCount + record.minorityCount}"/>
                    <c:set var="underFiftyCount" value="${underFiftyCount + record.underFiftyCount}"/>
                    <tr data-party-id="${record.partyId}">
                        <td>${vs.count}</td>
                        <td style="text-align: left">${record.partyName}</td>
                        <td>${record.positiveCount}</td>
                        <td>${_rowCount}</td>
                        <td>
                            <input type="text" class="num" maxlength="4" name="proCount" value="${record.proCount}">
                        </td>
                        <td>
                            <input type="text" class="num" maxlength="4" name="stuCount" value="${record.stuCount}">
                        </td>
                        <td>
                            <input type="text" class="num" maxlength="4" name="retireCount" value="${record.retireCount}">
                        </td>
                        <td>
                            <input type="text" class="num" maxlength="4" name="femaleCount" value="${record.femaleCount}">
                        </td>
                        <td>
                            <input type="text" class="num" maxlength="4" name="minorityCount" value="${record.minorityCount}">
                        </td>
                        <td>
                            <input type="text" class="num" maxlength="4" name="underFiftyCount" value="${record.underFiftyCount}">
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
                <tfoot>
                <tr>
                    <th colspan="2" style="text-align: center">合计</th>
                    <th>${positiveCount}</th>
                    <th>${proCount+stuCount+retireCount}</th>
                    <th>${proCount}</th>
                    <th>${stuCount}</th>
                    <th>${retireCount}</th>
                    <th>${femaleCount}</th>
                    <th>${minorityCount}</th>
                    <th>${underFiftyCount}</th>
                </tr>
                <tr>
                  <th colspan="10">
                      <div class="modal-footer center" style="margin-top: 20px">
                          <button id="submitBtn" data-loading-text="提交中..." data-success-text="已提交成功"
                                  autocomplete="off"  ${hasReport?"disabled":""}
                                  class="btn btn-success btn-lg btn-block"><i class="fa fa-random"></i> 保存
                          </button>
                      </div>
                  </th>
                </tr>
                </tfoot>
            </table>

        </div>
    </div>
</div>
<style>
    .allocateTable .table{
        margin: 20px;
    }
    .allocateTable input{
        width: 50px;
        padding: 0px;
        text-align: center;
    }
    .allocateTable .table thead tr {
        background-image: none !important;
        background-color: #f2f2f2 !important;
    }
    .allocateTable .table tr th, .allocateTable .table tr td {
        border-bottom-width: inherit;
        text-align: center;
        padding: 5px;
    }

    .allocateTable .table {
        width: auto;
    }
</style>
<script>
    $("#submitBtn").click(function(){

        var items = [];
        $(".allocateTable tbody tr").each(function(){

            var $this = $(this);
            var item = {};
            item.partyId = $this.data("party-id");
            item.proCount = $("input[type=text]", $this.find("td:eq(4)")).val();
            item.stuCount = $("input[type=text]", $this.find("td:eq(5)")).val();
            item.retireCount = $("input[type=text]", $this.find("td:eq(6)")).val();
            item.femaleCount = $("input[type=text]", $this.find("td:eq(7)")).val();
            item.minorityCount = $("input[type=text]", $this.find("td:eq(8)")).val();
            item.underFiftyCount = $("input[type=text]", $this.find("td:eq(9)")).val();
            items.push(item);
        })

        $.post("${ctx}/pcsPrAllocate_au",{items:new Base64().encode(JSON.stringify(items))}, function(ret){
            if(ret.success){
                toastr.success("保存成功。");
            }
        });
    });
    $(".allocateTable").on("keyup", "input", function () {

        var $tr = $(this).closest("tr");
        var $horizon = $tr.find("td:eq(3)");
        //console.log($horizon.html())
        var horizon = 0;
        $("input[type=text]", $tr.find("td:lt(7)")).each(function(){
            if($(this).val()>0)
                horizon += parseInt($(this).val());
        })
        $horizon.html(horizon);

        var idx =  $(this).parent().index();
        var vertical = 0;

        $("input[type=text]", $("tr").find("td:eq("+idx+")")).each(function(){

            if($(this).val()>0)
                vertical += parseInt($(this).val());
            //console.log(vertical)
        })
        var $footTr = $(".allocateTable table tfoot tr");
        $footTr.find("th:eq("+(idx-1)+")").html(vertical);

        var total = 0;
        total += parseInt($footTr.find("th:eq(3)").text());
        total += parseInt($footTr.find("th:eq(4)").text());
        total += parseInt($footTr.find("th:eq(5)").text());
        $footTr.find("th:eq(2)").html(total)
    })
    stickheader();
</script>