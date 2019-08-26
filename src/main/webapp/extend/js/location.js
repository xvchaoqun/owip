ChinaLocation.prototype.find	= function(id) {
	//alert(this.items['0']);
	if(typeof(this.items[id]) == "undefined")
		return false;
	return this.items[id];
}

ChinaLocation.prototype.fillOption	= function($container, el_id , loc_id , selected_id) {

	var el	= $('.'+el_id, $container);
	var json	= this.find(loc_id);
	if (json) {
		var index	= 1;
		var selected_index	= 0;
		$.each(json , function(k , v) {
			var option	= '<option value="'+k+'">'+v+'</option>';
			el.append(option);
			
			if (k == selected_id) {
				selected_index	= index;
			}
			index++;
		})
		//el.attr('selectedIndex' , selected_index);
	}

	el.val(selected_id).trigger("change");
}

$.extend({
	showLocation: function (province, city, town, $container) {

		$container = $container || $(document);

		var loc = new ChinaLocation();
		var title = ['省份', '地级市', '市、县、区'];
		$.each(title, function (k, v) {
			title[k] = '<option value="">' + v + '</option>';
		})

		$('.loc_province', $container).append(title[0]);
		$('.loc_city', $container).append(title[1]);
		$('.loc_town', $container).append(title[2]);

		$(".loc_province,.loc_city,.loc_town", $container).select2()
		$('.loc_province', $container).change(function () {
			$('.loc_city', $container).empty();
			$('.loc_city', $container).append(title[1]);
			loc.fillOption($container, 'loc_city', '0,' + $('.loc_province', $container).val());
			$('.loc_city', $container).change()
		})

		$('.loc_city', $container).change(function () {
			$('.loc_town', $container).empty();
			$('.loc_town', $container).append(title[2]);
			loc.fillOption($container, 'loc_town', '0,' + $('.loc_province', $container).val() + ',' + $('.loc_city', $container).val());
		})

		$('.loc_town', $container).change(function () {
			$('input[@name=location_id]', $container).val($(this).val());
		})
		if (province) {
			loc.fillOption($container, 'loc_province', '0', province);

			if (city) {
				loc.fillOption($container, 'loc_city', '0,' + province, city);

				if (town) {
					loc.fillOption($container, 'loc_town', '0,' + province + ',' + city, town);
				}
			}

		} else {
			loc.fillOption($container, 'loc_province', '0');
		}

	}
});

