/**
 *
 */
jQuery(document).ready(function() {

	// 遷移元画面により制御
	var senimotoGamenId = $("#senimotoGamenId").val();
	if (senimotoGamenId == 'main') {
		$('input[name="roleId"]').prop('disabled', false);
		$("#backBtn").show();
		$("#loginBtn").hide();
	} else {
		$('input[name="roleId"]').prop('disabled', true);
		$("#backBtn").hide();
		$("#loginBtn").show();
	}

	// メールアドレス半角置換
	$(document).on('change', "#mail", function() {
		if ($(this).val() == '') { return; }
		$(this).val(toHalfWidth($(this).val()));
	});

	// 電話番号半角置換
	$(document).on('change', "#phoneNumber", function() {
		if ($(this).val() == '') { return; }
		$(this).val(toHalfWidth($(this).val()));
	});


	// 登録ボタンをクリックするとイベント発動
	$('#torokuBtn').click(function() {
		// 確認ダイアログの選択により処理する。
		if (!confirm('Đăng kí người dùng mới với thông tin đã nhập ?')) {
			return false;
		} else {
			$('input[name="roleId"]').prop('disabled', false);
			return true;
		}
	});

	// ログインボタンをクリックするとイベント発動
	$('#loginBtn').click(function() {
		// 確認ダイアログの選択により処理する。
		if (!confirm('Quay về trang Đăng nhập ?')) {
			return false;
		} else {
			return true;
		}
	});


	// ログアウトボタン押下する時
	logout();

});