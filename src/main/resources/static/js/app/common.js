/**
*  半角置換処理
 */
function toHalfWidth(strVal) {
	// 置換用文字列が空の場合
	if (strVal == '') { return; }
	// 半角変換
	var halfVal = strVal.replace(/[！-～]/g, function(tmpStr) {
		// 文字コードをシフト
		return String.fromCharCode(tmpStr.charCodeAt(0) - 0xFEE0);
	});
	return halfVal;
}


// キー押下のイベント
$("body").keydown(function(e) {
	// Enterキーを押下されたとき
	if ((e.which && e.which === 13) || (e.keyCode && e.keyCode === 13)) {
		// フォーカスされてるフォームのIDとtypeを取得
		var id = $(':focus').attr("id");
		var type = $(':focus').attr("type");

		// textareaを除外するためにフォーカスされているtagの種類を取得
		var tag = $("#" + id).prop("tagName");
		var tag = (tag !== undefined) ? tag.toLowerCase() : "";

		// typeがbutton・submit、またはtagがtextarea以外はEnterを無効
		if ((type !== 'button' && type !== 'submit' && tag !== "textarea")) {
			return false;
		}
	}
});

// ログアウトボタン押下処理
function logout() {
	// ログアウトボタンをクリックするとイベント発動
	$('#logoutBtn').click(function() {
		// 確認ダイアログの選択により処理する。
		if (!confirm('Bạn muốn đăng xuất tài khoản này')) {
			return false;
		} else {
			return true;
		}
	});
}

// 3桁カンマ区切りとする（小数点も考慮）.
function editComma(str) {
	var num = str.replace(/^0+/, '');
	var s = String(num).split('.');
	var ret = String(s[0]).replace(/(\d)(?=(\d\d\d)+(?!\d))/g, '$1,');
	if (s.length > 1) {
		ret += '.' + s[1];
	}
	return ret;
}

/**
* カンマ削除
*
*/
function removeComma(str) {
	var number = str.replace(/^0+/, '');
    var removed = number.replace(/,/g, '');
    return parseInt(removed, 10);
}

// 戻るボタンをクリックするとイベント発動
function back() {
	$('#backBtn').click(function() {
		// 確認ダイアログの選択により処理する。
		if (!confirm('Quay về trang trước ?')) {
			return false;
		} else {
			return true;
		}

	});
}


function removeComma(number) {
    var removed = number.replace(/,/g, '');
    return parseInt(removed, 10);
}
