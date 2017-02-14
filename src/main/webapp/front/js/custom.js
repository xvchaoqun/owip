/**
 * toPageNo 显示第几页内容
 * pageNo 表示指定页码的变量名称，默认为"pageNo"
 * uri 请求列表的url地址
 * selector 放置列表代码的容器，jquery选择器
 * op 对装载的内容操作 html 放入到容器中，replace将容器替换，prepend 放入容器中的前面，append 放入容器中的后面,before放入容器外前面,after放入容器外后面 默认为html
 * searchStr是用来记录搜索时的条件字符串的，生产格式如下：“&name=abc&id=1&pid=8”;
 */
function _tunePage(toPageNo, pageNo, uri, selector, op, searchStr, method) {
	var topage = 1;
	if(typeof toPageNo == "string"){
		try{toPageNo = parseInt(toPageNo);}catch(_e){}
	}
    if (typeof(toPageNo) != "number" || toPageNo < 1) topage = 1;
    else topage = toPageNo;
    if(!pageNo){
    	pageNo = "pageNo";
    }
    try {
    	var para = pageNo+"=" + topage ;
        if (searchStr && searchStr != "null" && searchStr.length>0) {
            //  alert("add_searchStr_pathname=" + window.location.pathname +"?pageNo="+ toPageNo + searchStr);
            //window.location = window.location.pathname + "?pageNo=" + toPageNo + searchStr;
        	//_renderUrl(uri + "?"+pageNo+"=" + topage + searchStr, selector, replace);
        	para += searchStr;
        }
        else {
          //  _renderUrl(uri + "?"+pageNo+"=" + topage, selector, replace);
        }

        $(selector).renderUrl({
    		url : uri,
    		op : op,
			params : para,
			method:method
    	});
    }
    catch(e) {
		alert(e)
       // window.location = window.location.pathname + window.location.search;
       // _renderUrl(uri + "?"+pageNo+"=1", selector, replace);
    	$.error("分页出错");
    }
}
(function($) {
	//ajax装载一个页面到该对象中
	$.fn.renderUrl = function(options) {
		var defaults = {
			op : "html",
			params : {},
			url : "",
			//method:"POST",
			fn : function(){
		
			}
		};
		if(typeof options == "string"){
			options = {url : options};
		}
		options = $.extend(defaults, options);
		
		//alert(JSON.stringify(defaults))
		this.each(function(){
			if(options.url){
				var thisContainer = $(this);
				//var _img_load_div = $('<div class="center"><i class="icon-spinner icon-spin orange bigger-125"></i></div>');
				//thisContainer.html(_img_load_div);

				thisContainer.showLoading({'afterShow':
					function() {
						setTimeout( function(){
							thisContainer.hideLoading();
						}, 5000 );
					}});
				$.ajax( {
					url : options.url,
					type: options.method,
					data : options.params,
					cache : false,
					success : function(html) {
						//_img_load_div.remove();
						if(options.op == "replace"){
							$(thisContainer).replaceWith(html); 
						}else if(options.op == "append"){
							$(thisContainer).append(html); 
						}else if(options.op == "prepend"){
							$(thisContainer).prepend(html); 
						}else if(options.op == "before"){
							$(thisContainer).before(html); 
						}else if(options.op == "after"){
							$(thisContainer).after(html); 
						}else{
							//alert(html)
							$(thisContainer).empty().append(html);
						}
						if (options.fn) {
							options.fn(html);
						}
						thisContainer.hideLoading();
					},
					error : function() {
						$.error( "页面出错");
						thisContainer.html("页面出错");
					}
				});
			}
		});
		return this;
	};
})(jQuery);
