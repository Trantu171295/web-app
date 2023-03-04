/**
 *
 */
jQuery(document).ready(function() {

	// 初期表示
	$("#adminTab").hide();

	// 管理者フラグ
	var adminFlg = $("#adminFlg").val();
	if (adminFlg == '1') {
		$("#adminTab").show();
	}
	if ($('#t1 tbody').find("tr").length == 0) {
		$('#all').prop("disabled", true);
	} else {
		$('#all').prop("disabled", false);
	}

	var rowLen = t1.rows.length - 1;
	for (var i = 0; i < rowLen; i++) {
		$('input:checkbox[id$="t1' + i + '.checkBoxFlg1"]').change(function() {
			hantei($(this));
		});
	}

	// 1. 「全選択」する
	$('#all').on('click', function() {
		$("input[id$='.checkBoxFlg1']").prop('checked', this.checked);
		for (var i = 0; i < rowLen; i++) {
			hantei($('input:checkbox[id$="t1' + i + '.checkBoxFlg1"]'));
		}
	});
	// 2. 「全選択」以外のチェックボックスがクリックされたら、
	$("input[id$='.checkBoxFlg1']").on('click', function() {
		if ($('#t1 input[id$=".checkBoxFlg1"]:checked').length === $('#t1 tbody').find("tr").length) {
			// 全てのチェックボックスにチェックが入っていたら、「全選択」 = checked
			$('#all').prop('checked', true);
		} else {
			// 1つでもチェックが入っていたら、「全選択」 = checked
			$('#all').prop('checked', false);
		}
	});

	// アカウン状態ラジオボタンの選択により制御
	var accSttFlg = $('input:radio[name="accSttFlg"]:checked').val();
	delRefBtnCon(accSttFlg);

	// 検索ユーザーIDのフォーカスアウト
	document.getElementById("searchUserId").onchange = function() {
		$('#userSearchBtn').trigger('click');
	};

	// アカウン状態のラジオボタンを変更
	$('input[name="accSttFlg"]:radio').change(function() {

		$('#userSearchBtn').trigger('click');

		// var searchUserId = $('#searchUserId').val();
		// var accSttFlg1 = $(this).val();
		// // json形式でデータを取得
		// getT1(searchUserId, accSttFlg1).done(function(data, dataType) {
		// 	createT1Body(data);

		// }).fail(function(jqXHR, textStatus, errorThrown) {
		// 	alert("異常終了しました。エラー項目名：" + jqXHR.responseText + "　。エラータイプ：" + textStatus + "　。エラータイプ内容：　" + errorThrown);
		// });

		// // テーブルボタン制御
		// delRefBtnCon($(this).val());

		// //
		// setTimeout(function(){
		//    alert("更新ボタンで最新情報を取得してください。");
		//    },1000);
	});



	// テーブル１制御
	$('#t1').DataTable({
		paging: false,
		info: false,
		lengthChange: false,
		ordering: false,
		searching: false,
		scrollX: true,
		scrollY: 300,
		button: []
	});
	// $('input').change(function() {
	// 	for (var i = 0; i < $('#t1 tbody').find("tr").length; i++) {
	// 		// t10.checkBoxFlg1
	// 		var id = "t1" + i + ".checkBoxFlg1";
	// 	alert(id);
	// 		if( $("#" + id).prop('checked')){
	// 			alert("チェックしました")
	// 			$(this).val("1");
	// 		}else{
	// 			alert("外しました");
	// 			$(this).val("0");
	// 		}
	// 		// $('input').change(function() {
	// 		// 	$('input:checked').each(function() {
	// 		// 		$(this).val("1");
	// 		// 	})
	// 		// })

	// 		// $('input:checkbox[id$=".checkBoxFlg1"]:checked').each(function() {
	// 		// 	alert($(this).val());
	// 		// });
	// 	}
	// });
	// $('input:checkbox[id$=".checkBoxFlg1"]:checked').each(function() {
	// 			alert($(this).val());
	// 		});
	// var rowLen = t1.rows.length - 1;
	// for (var i = 0; i < rowLen; i++) {

	// }

	// ログアウトボタン押下する時
	logout();


	// 検索ボタンをクリックするとイベント発動
	$('#userSearchBtn').click(function() {
		// var accSttFlg = $('input:radio[name="accSttFlg"]:checked').val();
		// 確認ダイアログの選択により処理する。
		// if (!confirm('登録済のユーザーを検索します。よろしいですか？')) {
		// 	return false;
		// } else {
		return true;
		// }
	});


	// 削除ボタンをクリックするとイベント発動
	$('#delBtn').click(function() {
		if ($('#t1 input[id$=".checkBoxFlg1"]:checked').length === 0) {
			alert("削除対象明細行が選択されていません。");
			return false;
		} else {
			if ($('#t1 input[id$=".checkBoxFlg1"]:checked').length === $('#t1 tbody').find("tr").length) {
				if (!confirm('すべてのユーザーを削除します。よろしいですか？')) {
					return false;
				} else {
					return true;
				}
			} else {
				// 確認ダイアログの選択により処理する。
				if (!confirm('選択のユーザ情報を削除します。よろしいですか？')) {
					return false;
				} else {
					return true;
				}
			}
		}
	});


	// 復元ボタンをクリックするとイベント発動
	$('#refBtn').click(function() {
		if ($('#t1 input[id$=".checkBoxFlg1"]:checked').length === 0) {
			alert("復元対象明細行が選択されていません。");
			return false;
		} else {
			if ($('#t1 input[id$=".checkBoxFlg1"]:checked').length === $('#t1 tbody').find("tr").length) {
				if (!confirm('すべてのユーザーを復元します。よろしいですか？')) {
					return false;
				} else {
					return true;
				}
			} else {
				// 確認ダイアログの選択により処理する。
				if (!confirm('選択のユーザ情報を復元します。よろしいですか？')) {
					return false;
				} else {
					return true;
				}
			}
		}
	});


});

/**
*  削除・復元ボタン制御
 */
function delRefBtnCon(accSttFlg) {
	if (accSttFlg == '0') {
		$("#delBtn").show();
		$("#refBtn").hide();
	} else if (accSttFlg == '1') {
		$("#delBtn").hide();
		$("#refBtn").show();
	} else {
		$("#delBtn").hide();
		$("#refBtn").hide();
	}
}

/**
*  チェックボックスの制御により値を設定
 */
function hantei(checkBoxFlg) {
	if (checkBoxFlg.prop('checked')) {
		checkBoxFlg.val('1');
	} else {
		checkBoxFlg.val('0');
	}
}

/**
 *  ajaxでjson形式のデータ取得
 *
 */
// function getT1(searchUserId, accSttFlg) {
// 	var url = 'http://localhost:8080/main/accSttChange';
// 	var data = {
// 		searchUserId: searchUserId,
// 		accSttFlg: accSttFlg
// 	};
// 	return $.ajax({
// 		url: url,
// 		type: "POST",
// 		contentType: "application/json",
// 		data: JSON.stringify(data),
// 		dataType: "json"
// 	})
// }

// /**
//  * jsonデータからテーブル作成
//  */
// function createT1Body(data) {
// 	// 作成前にtbodyをクリアする。
// 	$("#t1  tbody tr").remove();
// 	var tr = '';

// 	// 行数分をループ
// 	if (data.length > 0) {
// 		$.each(data, function(i) {
// 			// 登録日時
// 			var y1 = data[i].regstTmstmp.date.year;
// 			var m1 = getZeroPadding(data[i].regstTmstmp.date.month, 2);
// 			var d1 = getZeroPadding(data[i].regstTmstmp.date.day, 2);
// 			var h1 = getZeroPadding(data[i].regstTmstmp.time.hour, 2);
// 			var mi1 = getZeroPadding(data[i].regstTmstmp.time.minute, 2);
// 			var s1 = getZeroPadding(data[i].regstTmstmp.time.second, 2);
// 			// 更新日時
// 			var y2 = data[i].updTmstmp.date.year;
// 			var m2 = getZeroPadding(data[i].updTmstmp.date.month, 2);
// 			var d2 = getZeroPadding(data[i].updTmstmp.date.day, 2);
// 			var h2 = getZeroPadding(data[i].updTmstmp.time.hour, 2);
// 			var mi2 = getZeroPadding(data[i].updTmstmp.time.minute, 2);
// 			var s2 = getZeroPadding(data[i].updTmstmp.time.second, 2);

// 			// tbody のhtml作成
// 			var no = '<td class="text-right">' + (i + 1) + '</td>';
// 			var checkBox = '<td><input type="checkbox" value="0" id="t1' + i + '.checkBoxFlg1" name="t1[' + i + '].checkBoxFlg"><input type="hidden" name="_t1[' + i + '].checkBoxFlg" value="on"></td>';
// 			var userId = '<td><input type="text" class="form-control input-sm" maxlength="14" readonly="" id="t1' + i + '.userId" name="t1[' + i + '].userId" value="' + data[i].userId + '"></td>';
// 			var mail = '<td><span>' + data[i].mail + '</span></td>';
// 			var userName = '<td><span>' + data[i].userName + '</span></td>';
// 			var nickName = '<td><span>' + data[i].nickName + '</span></td>';
// 			var phoneNumber = '<td><span>' + data[i].phoneNumber + '</span></td>';
// 			var regstTmstmp = '<td><span>' + y1 + '/' + m1 + '/' + d1 + ' ' + h1 + ':' + mi1 + ':' + s1 + '</span></td>';
// 			var updTmstmp = '<td><span>' + y2 + '/' + m2 + '/' + d2 + ' ' + h2 + ':' + mi2 + ':' + s2 + '</span></td>';

// 			// 奇数、偶数行の判定
// 			var className = '';
// 			if (i % 2 === 0) {
// 				className = 'odd'
// 			} else {
// 				className = 'even';
// 			}
// 			// tr(１行)作成
// 			tr = '<tr class="' + className + '">' + no + checkBox + userId + mail + userName + nickName + phoneNumber + regstTmstmp + updTmstmp + '</tr>'
// 			// 作成行をtbodyに追加
// 			$('#t1-body').append(tr);
// 		});
// 	} else {
// 		tr = '<tr class="odd"><td valign="top" colspan="9" class="dataTables_empty">No data available in table</td></tr>'
// 		// 作成行をtbodyに追加
// 		$('#t1-body').append(tr);
// 	}


// }

// /**
//  *
//  * Zero Padding
//  * @param {float} number
//  * @param {int} decimals
//  * @return {string}
//  */
// function getZeroPadding(number, decimals) {
// 	var number = String(number);

// 	// 0埋め指定数より桁数が大きい場合は処理を中止
// 	if (number.length > decimals) {
// 		return number;
// 	}

// 	// 値の前に10を乗算し0を追加、その後指定桁数へ切り出し
// 	return (Math.pow(10, decimals) + number).slice(decimals * -1);
// }