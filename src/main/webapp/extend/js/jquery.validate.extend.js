jQuery.extend(jQuery.validator.messages, {
	required : "必选字段",
	remote : "请修正该字段",
	email : "请输入正确格式的电子邮件",
	url : "请输入合法的网址",
	date : "请输入合法的日期",
	dateISO : "请输入合法的日期 (ISO).",
	number : "请输入合法的数字",
	digits : "只能输入整数",
	creditcard : "请输入合法的信用卡号",
	equalTo : "请再次输入相同的值",
	accept : "请输入拥有合法后缀名的字符串",
	maxlength : jQuery.validator.format("请输入一个 长度最多是 {0} 的字符串"),
	minlength : jQuery.validator.format("请输入一个 长度最少是 {0} 的字符串"),
	rangelength : jQuery.validator.format("请输入 一个长度介于 {0} 和 {1} 之间的字符串"),
	range : jQuery.validator.format("请输入一个介于 {0} 和 {1} 之间的值"),
	max : jQuery.validator.format("请输入一个最大为{0} 的值"),
	min : jQuery.validator.format("请输入一个最小为{0} 的值")
});
jQuery.validator.setDefaults({
	errorElement: 'span',
	errorClass: 'help-block',
	focusInvalid: true,
	ignore:'',
	invalidHandler: function (event, validator) { //display error alert on form submit
	},
	highlight: function (e) {
	},
	success: function (e) {
		$(e).closest('div.form-group').removeClass('has-error').addClass('has-success')
		$(e).removeClass('has-error').addClass('has-success')
		$(e).parent().removeClass('has-error').addClass('has-success')
	},
	errorPlacement: function (error, element) {

		$(element).closest('div.form-group').removeClass('has-success')

		if(element.is(':file')){

			var $uploaderdiv = element.closest('.uploader');
			error.insertAfter($uploaderdiv.parent())
			return;
		}

		/*if(element.parent().is('.input-group')){

			error.insertAfter(element.parent().parent())
			return;
		}*/
		//console.log($(element).hasClass("date-picker")+"==================1111")
		if(element.is(":checkbox")){
			$(element).closest('div.form-group').removeClass('has-success').addClass('has-error')
			error.insertAfter(element.closest('div'));
		}else if(element.is(":radio") ){
			var parent = element.closest(".radio").parent();
			parent.removeClass('has-success').addClass('has-error')
			error.insertAfter(parent);
		}else if(element.is("select") ){
			//console.log("==================")
			$(element).closest('div.form-group').removeClass('has-success').addClass('has-error')
			error.appendTo($(element).parent());
		}else if($(element).hasClass("date-picker")){
			$(element).closest('div.form-group').removeClass('has-success').addClass('has-error')
			error.insertAfter($(element).closest("div.input-group"));
		}else {
			//element.parent().remove('.has-success').addClass('has-error')
			//error.insertAfter(element.parent());
			$(element).closest('div.form-group').removeClass('has-success').addClass('has-error')
			error.insertAfter(element);
		}
	}
})

//中文字两个字节
jQuery.validator.addMethod("byteRangeLength", function(value, element, param) {
    var length = value.length;
    for(var i = 0; i < value.length; i++){
        if(value.charCodeAt(i) > 127){
            length++;
        }
    }
  return this.optional(element) || ( length >= param[0] && length <= param[1] );   
}, $.validator.format("请确保输入的值在{0}-{1}个字符之间(一个中文字算2个字符)"));

//中文字两个字节
jQuery.validator.addMethod("byteMaxLength", function(value, element, param) {
    var length = value.length;
    for(var i = 0; i < value.length; i++){
        if(value.charCodeAt(i) > 127){
            length++;
        }
    }
    
  return this.optional(element) || ( length <= param);   
}, $.validator.format("输入长度最多是{0}的字符串(汉字算2个字符)"));

// 邮政编码验证   
jQuery.validator.addMethod("isZipCode", function(value, element) {   
    var tel = /^[0-9]{6}$/;
    return this.optional(element) || (tel.test(value));
}, "请正确填写您的邮政编码");
