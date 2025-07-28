
let isIdAvailable = false;

// 아이디 중복 체크 함수
function checkId() {
	// jQuery 선택자 수정: id 속성 선택 시 '#'을 붙여야 합니다.
	const id = $('#id').val();

	if (id) { // 아이디 입력값이 있을 경우에만 AJAX 통신 시도
		$.ajax({
			url: '/user/check-id',
			method: 'GET',
			data: { "id": id },
			success: function(response) {
				// 서버 응답 (JSON 객체)에서 hasId 값을 확인
				console.log("응답 결과:", response); // 응답 확인을 위한 로그 추가
				if (response && response.hasOwnProperty('hasId')) {
					if (response.hasId) {
						// hasId가 true이면 이미 존재하는 아이디입니다 alert풀력
						alert("이미 사용 중인 아이디입니다."); // 또는 "일치하는 아이디 입니다" (존재 의미)
						$('#idMessage').text("이미 사용 중인 아이디입니다.").css({ 'color': 'red' });
						isIdAvailable = false;
					} else {
						// hasId가 false이면 사용 가능한 아이디입니다.
						alert("사용하실 수 있는 아이디입니다.");
						$('#idMessage').text("사용하실 수 있는 아이디입니다.").css({ 'color': 'green' });
						isIdAvailable = true;
					}
				} else {
					// 예상치 못한 응답 형식일 경우
					alert("아이디 확인 중 오류가 발생했습니다.");
					$('#idMessage').text("아이디 확인 중 오류가 발생했습니다.").css({ 'color': 'red' });
				}
			},
			error: function(jqXHR, textStatus, errorThrown) {
				console.log("비동기 통신 실패!");
				console.log("상태:", textStatus);
				console.log("에러:", errorThrown);
				console.log("응답:", jqXHR.responseText);
				alert("아이디 확인 중 서버 오류가 발생했습니다.");
				$('#idMessage').text("아이디 확인 중 오류가 발생했습니다.").css({ 'color': 'red' });
			}
		});
	} else {
		// 아이디 입력값이 없을 경우
		alert("아이디를 입력해주세요.");
		$('#idMessage').text("아이디를 입력해주세요.").css({ 'color': 'orange' });
	}
}


// 비밀번호 확인 함수 만들 예정
// on : 키 입력시 반응
$('#pwConfirm').on('input', function() {
	const pw = $("#pw").val();
	const pwConfirm = $("#pwConfirm").val();
	const pwMsg = $("#pwMessage");

	if (pw === pwConfirm) {
		pwMsg.text("비밀번호가 일치합니다").css({ 'color': 'green' });
	} else {
		pwMsg.text("비밀번호가 일치하지 않습니다!").css({ 'color': 'red' });
	}
});


//  폼 배출시 id중복체크 여부 확인 및 pw일치 검사
$('#registerForm').on('submit', function(e) {
	// 아이디 중복체크  등록방지
	if (!isIdAvailable) {
		e.preventDefault()
		alert("아이디 중복체크를 완료해주세요")
	}

	const pw = $('#pw').val();
	const pwConfirm = $("#pwConfirm").val()

	// 비밀번호 불일치 등록방지
	if (pw !== pwConfirm) {
		e.preventDefault()
		alert("비밀번호를 일치시켜 주세요")
	}
})
