// js 에서 Model 에접근하는 방법

function deleteBoard() {
	if (confirm("게시글을 삭제 하시겠습니까?")) {

		$.ajax({
			url: "/delete/[[${detail.id}]]",
			type: "DELETE",
			success: function(res) {
				alert("삭제성공!")
				console.log(res)
				window.location.href = "/"
			},
			error: function(e) {
				alert("삭제가 실패하였습니다.")
				console.log(e)
			}
		})
	}
}