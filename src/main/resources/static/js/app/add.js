/**
 *
 */
/**
 *
 */
jQuery(document).ready(function() {

	// カンマ区切り
	$("#price").val(editComma($("#price").val()));
	$("#quantity").val(editComma($("#quantity").val()));


	// カンマ区切り
	var insFlg = $("#insFlg").val();
	if (insFlg == '1') {
		$('#productNm').prop("readonly", true);
		$('#categoryCd').prop("disabled", true);
		$('#makerName').prop("readonly", true);
		$('#price').prop("readonly", true);
		$('#quantity').prop("readonly", true);
		$("#insBtn").hide();
		$("#editBtn").show();
	} else {
		$('#productNm').prop("readonly", false);
		$('#categoryCd').prop("disabled", false);
		$('#makerName').prop("readonly", false);
		$('#price').prop("readonly", false);
		$('#quantity').prop("readonly", false);
		$("#insBtn").show();
		$("#editBtn").hide();
	}


	//
	$("#price").change(function() {
		$("#price").val(editComma($("#price").val()));
	});

	//
	$("#quantity").change(function() {
		$("#quantity").val(editComma($("#quantity").val()));
	});

	// 更新ボタンをクリックするとイベント発動
	$('#insBtn').click(function() {
		// カンマ削除
		$("#price").val(removeComma($("#price").val()));
		$("#quantity").val(removeComma($("#quantity").val()));
		// 確認ダイアログの選択により処理する。
		if (!confirm('Bạn đồng ý thêm sản phẩm với thông tin đã nhập ?')) {
			return false;
		} else {
			return true;
		}

	});



	// ログアウトボタン押下する時
	logout();

	// 戻るボタンをクリックするとイベント発動
	back();


});

