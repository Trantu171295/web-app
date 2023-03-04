/**
 *
 */
/**
 *
 */
jQuery(document).ready(function() {
	$("#updBtn").hide();
	// 管理者フラグ
	var adminFlg = $("#adminFlg").val();
	if (adminFlg == '1') {
		$("#editBtn").show();
	} else {
		$("#editBtn").hide();
	}

	// カンマ区切り
	$("#price").val(editComma($("#price").val()));
	$("#quantity").val(editComma($("#quantity").val()));

	var saleFlag = $("#saleFlag").val();
	var quantity = $("#quantity").val();

	if (saleFlag == '1') {
		$("#saleFlag").val('Ngừng kinh doanh');
		$("#quantity").val(0);
		$("#saleFlag").css("color", "red");
	} else {
		if (quantity <= 0) {
			$("#saleFlag").val('Hết hàng');
			$("#saleFlag").css("color", "red");
		} else if (quantity > 0) {
			$("#saleFlag").val('Còn hàng');
		} else {
			$("#saleFlag").val('Chi tiết vui lòng liên hệ 0900-111-222');
		}
	}

	$('#productNm').prop("readonly", true);
	$('#categoryCd').prop("disabled", true);
	$('#makerName').prop("readonly", true);
	$('#price').prop("readonly", true);
	$('#quantity').prop("readonly", true);



	$("#quantity").change(function() {
		var quantity = $("#quantity").val();
		if (quantity > 0) {
			$("#quantity").val(editComma($("#quantity").val()));
			$("#saleFlag").val('Còn hàng');
			$("#saleFlag").css("color", "black");
		} else {
			$("#saleFlag").val('Hết hàng');
			$("#saleFlag").css("color", "red");
		}

	});

	$("#price").change(function() {
		var price = $("#price").val();
		if (price > 0) {
			$("#price").val(editComma($("#price").val()));
		}

	});


	// ログインボタンをクリックするとイベント発動
	$('#editBtn').click(function() {

		$('#productNm').prop("readonly", false);
		$('#categoryCd').prop("disabled", false);
		$('#makerName').prop("readonly", false);
		$('#price').prop("readonly", false);
		$('#quantity').prop("readonly", false);
		$("#editBtn").hide();
		$("#updBtn").show();
		return false;


	});


	// 更新ボタンをクリックするとイベント発動
	$('#updBtn').click(function() {
		var productNm = $("#productNm").val();
		var makerName = $("#makerName").val();
		var price = $("#price").val();
		var quantity = $("#quantity").val();
		var categoryCd = $('#categoryCd option:selected').val();

		if (!productNm || !makerName || categoryCd === '000' || price <= 0 || quantity <= 0) {
			alert("Vui lòng nhập chính xác thông tin sản phẩm");
			return false;
		} else {


			if (quantity > 0) {
				$("#saleFlag").val('0');
			} else {
				$("#quantity").val(0);
				$("#saleFlag").val('1');
			}
			// カンマ削除
			$("#price").val(removeComma($("#price").val()));
			$("#quantity").val(removeComma($("#quantity").val()));
			// 確認ダイアログの選択により処理する。
			if (!confirm('Bạn đồng ý cập nhật thông tin sản phẩm đã nhập ?')) {
				return false;
			} else {
				return true;
			}
		}

	});



	// ログアウトボタン押下する時
	logout();

	// 戻るボタンをクリックするとイベント発動
	back();


});

