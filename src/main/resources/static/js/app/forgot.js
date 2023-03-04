/**
 *
 */
jQuery(document).ready(function() {
	// 送信ボタンをクリックするとイベント発動
	$('#resetBtn').click(function() {
		// 確認ダイアログの選択により処理する。
		if (!confirm('Gửi thông tin hướng dẫn để cài đặt lại mật khẩu ?')) {
			return false;
		} else {
			return true;
		}

	});

	// ログインボタンをクリックするとイベント発動
	$('#backBtn').click(function() {
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