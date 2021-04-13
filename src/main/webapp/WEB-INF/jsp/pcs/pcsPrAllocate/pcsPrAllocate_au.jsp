<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<%@ include file="../constants.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <div class="allocateTable">
            <div class="search">
                ${_p_partyName}、党总支、直属党支部：<input type="text" id="searchInput"> <a href="javascript:;" id="clearBtn"><i class="fa fa-times-circle fa-lg grey"></i></a>
            </div>
            <table class="table table-bordered table-striped" data-offset-top="132">
                <thead class="multi">
                <tr>
                    <th width="40" rowspan="2">序号</th>
                    <th rowspan="2">${_p_partyName}、党总支、直属党支部</th>
                    <th rowspan="2" width="60">正式党员数</th>
                    <shiro:hasPermission name="pcsPoll:list">
                    <th rowspan="2" width="70">投票推荐代表上限</th>
                    </shiro:hasPermission>
                    <th rowspan="2" width="70">代表总数</th>
                    <th colspan="${prTypeNum}">代表构成</th>
                    <th colspan="3">其中</th>
                </tr>
                <tr>
                    <c:forEach items="${prTypes}" var="prType">
                        <th width="70">${prType.value.name}</th>
                    </c:forEach>
                    <th width="70">妇女代表</th>
                    <th width="70">少数民族代表</th>
                    <th width="70">50岁以下代表</th>
                </tr>
                </thead>
                <tbody>
                <c:set var="positiveCount" value="0"/>
                <c:set var="candidateCount" value="0"/>

                <c:set var="femaleCount" value="0"/>
                <c:set var="minorityCount" value="0"/>
                <c:set var="underFiftyCount" value="0"/>

                <c:forEach items="${records}" var="record" varStatus="vs">

                    <c:set var="positiveCount" value="${positiveCount + record.positiveCount}"/>
                    <c:set var="candidateCount" value="${candidateCount + record.candidateCount}"/>

                    <c:set var="femaleCount" value="${femaleCount + record.femaleCount}"/>
                    <c:set var="minorityCount" value="${minorityCount + record.minorityCount}"/>
                    <c:set var="underFiftyCount" value="${underFiftyCount + record.underFiftyCount}"/>
                    <tr data-party-id="${record.partyId}">
                        <td>${vs.count}</td>
                        <td class="partyName">${record.partyName}</td>
                        <td>${record.positiveCount}</td>
                        <shiro:hasPermission name="pcsPoll:list">
                        <td><input type="text" class="num" maxlength="4" name="candidateCount" value="${record.candidateCount}"></td>
                        </shiro:hasPermission>
                        <td></td>

                        <c:forEach items="${prTypes}" var="prType">
                            <td class="prType">
                                <input type="text" class="num" maxlength="4"
                                       data-type="${prType.key}" value="${record.prCountMap.get(prType.key)}">
                            </td>
                        </c:forEach>

                        <td>
                            <input type="text" class="num" maxlength="4" name="femaleCount"
                                   value="${record.femaleCount}">
                        </td>
                        <td>
                            <input type="text" class="num" maxlength="4" name="minorityCount"
                                   value="${record.minorityCount}">
                        </td>
                        <td>
                            <input type="text" class="num" maxlength="4" name="underFiftyCount"
                                   value="${record.underFiftyCount}">
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
                <tfoot>
                <tr>
                    <th colspan="2" style="text-align: center">合计</th>
                    <th>${positiveCount}</th>
                    <shiro:hasPermission name="pcsPoll:list">
                    <th>${candidateCount}</th>
                    </shiro:hasPermission>
                    <th></th>

                    <c:forEach items="${prTypes}" var="prType">
                        <th></th>
                    </c:forEach>

                    <th>${femaleCount}</th>
                    <th>${minorityCount}</th>
                    <th>${underFiftyCount}</th>
                </tr>
                <tr>
                    <th colspan="${8+prTypeNum}">
                        <div class="modal-footer center" style="margin-top: 20px">
                            <button id="submitBtn" data-loading-text="提交中..." data-success-text="已提交成功"
                                    autocomplete="off"
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
    #clearBtn{
        display: none;
        position: relative;
        left: -25px;
        opacity: .3;
    }
    #clearBtn:hover {
        opacity: .9;
    }
    .allocateTable .table {
        margin: 5px 20px;
    }
    .allocateTable .search{
        margin: 5px 20px;
    }
    .allocateTable tr td input {
        width: 50px;
        padding: 0px;
        text-align: center;
    }

    .allocateTable .table tr th, .allocateTable .table tr td {
        border-bottom-width: inherit;
        text-align: center;
        padding: 5px;
    }

    .allocateTable .table {
        width: auto;
    }

    .allocateTable .partyName {
        text-align: left!important;
    }
</style>
<script>
    function _calTotal(){
        $("th", "tfoot tr:eq(0)").each(function(){
            var idx = $(this).index();
            if(idx==0) return true;
            var total = 0;
            $("td:eq("+(idx+1)+")", $("tbody tr").not(":hidden")).each(function(){
                var val;
                if(idx==1 || idx==2){
                    val = parseInt($(this).text());
                }else{
                    val = parseInt($("input", this).val());
                }
                total += isNaN(val)?0:val;
            })
            //console.log(total)
            $(this).html(total);
        })
    }

    $(document).on("keyup", "#searchInput", function () {
        var txt = $.trim($(this).val());
        if (txt != '') {
            $("#clearBtn").show();
        } else {
            $("#clearBtn").hide();
        }
        //console.log($(".partyName").length)
        $("tbody .partyName").each(function () {
            if (!$(this).text().match(txt)) {
                $(this).closest("tr").hide();
            }else{
                $(this).closest("tr").show();
            }
        });
        _calTotal();
    });
    $("#clearBtn").click(function () {
        $("#searchInput").val('');
        $("tbody tr").show();
        $(this).hide();
        _calTotal();
    })

    var startCol = ${cm:isPermitted("pcsPoll:list")?5:4}; // 起始列
    var endCol = startCol + ${prTypeNum};
    $("#submitBtn").click(function () {

        var items = [];
        $(".allocateTable tbody tr").each(function () {

            var $this = $(this);
            var item = {};
            item.partyId = $this.data("party-id");
            item.candidateCount = $("input[type=text]", $this.find("td:eq("+ (startCol-2) +")")).val();

            var prCount = {};
            for (var i=startCol; i < endCol ; i++) {
                var $input = $("input[type=text]", $this.find("td:eq("+ i +")"));
                var type = $input.data("type");
                var count = $.trim($input.val());
                if(count!='') {
                    prCount[type] = count;
                }
            }
            item.prCount = JSON.stringify(prCount);

            item.femaleCount = $("input[type=text]", $this.find("td:eq(" + (endCol) + ")")).val();
            item.minorityCount = $("input[type=text]", $this.find("td:eq(" + (endCol+1) + ")")).val();
            item.underFiftyCount = $("input[type=text]", $this.find("td:eq(" + (endCol+2) + ")")).val();
            items.push(item);
        })
        //console.log(items)
        $.post("${ctx}/pcs/pcsPrAllocate_au", {items: $.base64.encode(JSON.stringify(items))}, function (ret) {
            if (ret.success) {
                SysMsg.success("保存成功。");
            }
        });
    });
    $(".allocateTable").on("keyup", "tbody input", function () {

        var $tr = $(this).closest("tr");
        var $rowTotal = $tr.find("td:eq("+ (startCol-1) +")");
        var rowTotal = 0;
        $("input[type=text]", $tr.find("td.prType")).each(function () {
            if ($(this).val() > 0) {
                rowTotal += parseInt($(this).val());
            }
        })
        if(rowTotal>0) {
            $rowTotal.html(rowTotal);
        }

        var idx = $(this).parent().index();
        var colTotal = 0;
        $("input[type=text]", $("tr").find("td:eq(" + idx + ")")).each(function () {

            if ($(this).val() > 0) {
                colTotal += parseInt($(this).val());
            }
            //console.log(vertical)
        })
        var $footTr = $(".allocateTable table tfoot tr");
        $footTr.find("th:eq(" + (idx - 1) + ")").html(colTotal);

        var total = 0;
        for (var i = startCol-1; i < endCol-1 ; i++) {
            var colTotal = parseInt($footTr.find("th:eq("+ i +")").text());
            if(colTotal > 0) {
                total += colTotal;
            }
        }
        $footTr.find("th:eq("+ (startCol-2) +")").html(total)
    });
    $("input[type=text]", ".allocateTable tbody tr").keyup();
    stickheader();
</script>