<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>导出工资数据</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal no-footer" id="modalForm" method="post">
        <div class="form-group">
            <label class="col-xs-4 control-label">类别</label>
            <div class="col-xs-6 label-text">
                <div class="input-group">
                    <label>
                        <input name="type" type="radio" class="ace" value="1"/>
                        <span class="lbl" style="padding-right: 5px;"> 在职教职工</span>
                    </label>
                    <label>
                        <input name="type" type="radio" class="ace" value="2"/>
                        <span class="lbl" style="padding-right: 5px;"> 离退休人员党费计算基数</span>
                    </label>
                </div>
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-4 control-label">导出范围</label>
            <div class="col-xs-6 label-text">
                <div class="input-group">
                    <label>
                        <input name="scope" type="radio" class="ace" value="1" checked/>
                        <span class="lbl" style="padding-right: 5px;"> 参与缴费党员</span>
                    </label>
                    <label>
                        <input name="scope" type="radio" class="ace" value="2"/>
                        <span class="lbl" style="padding-right: 5px;"> 所有人员</span>
                    </label>
                </div>
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-4 control-label"><span class="star">*</span>选择工资月份</label>
            <div class="col-xs-6">
                <select required data-rel="select2"
                        name="salaryMonth" data-placeholder="请选择">
                    <option></option>

                </select>
            </div>
        </div>

        <div class="clearfix form-actions">
            <div class="center">
                <button id="submitBtn" class="btn btn-info btn-sm"
                        data-loading-text="<i class='fa fa-spinner fa-spin '></i> 导出数据中，请稍后"
                        type="button">
                    <i class="ace-icon fa fa-download "></i>
                    导 出
                </button>
            </div>
            <div class="text-danger bolder">
                说明：<br/>
                导出的是当前教职工和离退休党员库中的人员工资。
            </div>
        </div>
    </form>
</div>
<script>
    var jzgSalaryMonthList = ${cm:toJSONArray(jzgSalaryMonthList)};
    var retireSalaryMonthList = ${cm:toJSONArray(retireSalaryMonthList)};
    $("#modalForm input[name=type]").click(function () {
        var type = $(this).val();
        var $salaryMonth = $("#modalForm select[name=salaryMonth]");
        $salaryMonth.empty().append("<option></option>");
        $.each((type == 1) ? jzgSalaryMonthList : retireSalaryMonthList, function (i, month) {
            $salaryMonth.append(new Option(month, month, false, true))
        })
        $salaryMonth.val(null).trigger('change');
    })

    $('#modalForm [data-rel="select2"]').select2();
    $("#modalForm #submitBtn").click(function () {

        var type = $("#modalForm input[name=type]:checked").val();
        var scope = $("#modalForm input[name=scope]:checked").val();
        var salaryMonth = $("#modalForm select[name=salaryMonth]").val();

        if($.trim(type)=='' || $.trim(scope)=='' || $.trim(salaryMonth)=='') return;

        var url = "${ctx}/pmd/pmdConfigMember_exportSalary?export=1&type={0}&scope={1}&salaryMonth={2}".format(type, scope, salaryMonth);

        $(this).download(url);
        return false;
    })

</script>