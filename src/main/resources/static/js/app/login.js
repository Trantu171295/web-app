/**
 *
 */
jQuery(document).ready(function() {

	// ユーザーコード半角置換
	$(document).on('change', "#userCd", function() {
		if ($(this).val() == '') { return; }
		$(this).val(toHalfWidth($(this).val()));
	});


	// ログインボタンをクリックするとイベント発動
	$('#loginBtn').click(function() {
		// 確認ダイアログの選択により処理する。
		if (!confirm('Bạn muốn đăng nhập tài khoản này ?')) {
			return false;
		} else {
			return true;
		}

	});

	// 新規登録ボタンをクリックするとイベント発動
	$('#singupBtn').click(function() {
		// 確認ダイアログの選択により処理する。
		if (!confirm('Chuyển đến trang đăng kí người dùng mới ?')) {
			return false;
		} else {
			window.location.href = '/singup';
			return false;
		}

	});

	// パスワードリセットボタンをクリックするとイベント発動
	$('#resetPassBtn').click(function() {
		// 確認ダイアログの選択により処理する。
		if (!confirm('Chuyển đến trang thay đổi mật khẩu?')) {
			return false;
		} else {
			window.location.href = '/forgot';
			return false;
		}

	});

});

