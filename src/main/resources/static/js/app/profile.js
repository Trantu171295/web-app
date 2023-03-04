/**
 *
 */

jQuery(document).ready(function() {

	// 初期表示
	$("#submitBtn").hide();


	// ログアウトボタンをクリックするとイベント発動
	$('#editBtn').click(function() {
		// 項目制御
		$('#userName').prop("readonly", false);
		$('#nickName').prop("readonly", false);
		$('#mail').prop("readonly", false);
		$('#phoneNumber').prop("readonly", false);

		// ボタン制御
		$("#submitBtn").show();
		$("#editBtn").hide();

		return false;

	});

	// ログアウトボタンをクリックするとイベント発動
	$('#submitBtn').click(function() {
		// 確認ダイアログの選択により処理する。
		if (!confirm('Bạn đồng ý cập nhật những thông tin đã nhập ?')) {
			return false;
		} else {
			return true;
		}

	});

	// ログアウトボタン押下する時
	logout();

});