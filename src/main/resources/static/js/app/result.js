/**
 *
 */
jQuery(document).ready(function() {

	// カンマ区切り
	$(" [id^='t1'][id$='price']").each(function(i) {
		var price = $("#t1" + i + "\\.price").text();
		var quantity = $("#t1" + i + "\\.quantity").text();
		$("#t1" + i + "\\.price").text(editComma(price));
		$("#t1" + i + "\\.quantity").text(editComma(quantity));

	});

	$('#t1').DataTable({
		// select: true,
		// ページング
		paging: false,
		// 1ページあたりの行数
		pageLength: 10,
		scrollY: 350,
		// 件数切替 無効
		lengthChange: false,
		// ソート 無効
		ordering: true,
		// 検索 無効
		searching: false,
		// Copy/CSV 無効
		buttons: [],
		info: false,
	});


	// ログインボタンをクリックするとイベント発動
	$('#productSearchBtn').click(function() {

		var productNm = $("#productNm").val();
		var productCd = $("#productCd").val();
		var makerName = $("#makerName").val();
		var categoryCd = $('#categoryCd option:selected').val();
		// 確認ダイアログの選択により処理する。
		if (!confirm('Bạn muốn tìm kiếm sản phẩm ?')) {
			return false;
		} else {
			if (!productNm && !productCd && !makerName && categoryCd === '000') {
				alert("Vui lòng nhập chính xác thông tin tìm kiếm");
				return false;
			} else {
				return true;
			}
		}

	});

	// 商品コードリンクをクリックするとイベント発動
	$("#productCdLink ").on("click", function() {
		var i = $(this).closest('tr').index();
		var selectProductCd = $("#t1" + i + "\\.productCd").text();
		var selectCategoryCd = $("#t1" + i + "\\.categoryCd").text();
		$("#productCdSubmit").val(selectProductCd);
		$("#categoryCdSubmit").val(selectCategoryCd);

		// productCdSubmitがNULLでない場合は呼び出す
		if ($("#productCdSubmit").val()) {
			if (!confirm('Bạn muốn xem chi tiết sản phẩm ?')) {
				return false;
			} else {
				return true;
			}
		} else {
			alert("Vui lòng chọn click vào mã sản phẩm muốn xem chi tiết");
			return false;
		}
	});


	// ログアウトボタン押下する時
	logout();

	// 戻るボタン押下する時
	back();


});
