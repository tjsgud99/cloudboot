function searchBoard(event){
	event.preventDefault();
	
	const type = document.getElementById("searchType").value;
	const keyword = document.getElementById("keyword").value;
	
	// 비동기 통신
	$.ajax({
		url : "/search",
		type : "GET",
		data : {type : type , keyword : keyword},
		success: function(data){
			const tableBody = $("#boardTable")
			tableBody.empty();
			
			if (data.length === 0) {
                tableBody.append(`<tr><td colspan="4" class="text-center">검색 결과가 없습니다.</td></tr>`);
            } else {
                $.each(data, function(index, board) {
                    const row = `<tr>
                        			<td>${board.id}</td>
                        			<td><a href="/board/detail/${board.id}">${board.title}</a></td>
                        			<td>${board.writer}</td>
                        			<td>${board.writeDay}</td>
                    			</tr>`;
                    tableBody.append(row);
                });
            } // if else문 End
		},
		error : function(error){
			alert(error,"에러발생")
		} 
	})
}
