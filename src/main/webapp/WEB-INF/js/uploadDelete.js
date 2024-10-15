    function checkAll(checkbox) {
        var checkboxes = document.querySelectorAll('input[name="imageCheckbox"]');
        for (var i = 0; i < checkboxes.length; i++) {
            checkboxes[i].checked = checkbox.checked; // 전체 체크박스 상태에 따라 체크
        }
    }

    function updateAll() {
        var checkboxes = document.querySelectorAll('input[name="imageCheckbox"]');
        var allChecked = true;

        for (var i = 0; i < checkboxes.length; i++) {
            if (!checkboxes[i].checked) {
                allChecked = false; 
                break;
            }
        }
        document.querySelector('input[name="selectAll"]').checked = allChecked; // 전체 체크박스 상태 업데이트
    }

    function deleteImg() {
        var checkArray = document.querySelectorAll('input[name="imageCheckbox"]:checked');
        var seqArray = [];
        for (var i = 0; i < checkArray.length; i++) {
            seqArray.push(checkArray[i].value); // 체크된 이미지의 seq 가져오기
        }
        
        if (seqArray.length === 0) {
            alert("삭제할 이미지를 선택해 주세요.");
            return;
        }

        if (!confirm("선택한 이미지를 정말 삭제하시겠습니까?")){
            return;
        }

        $.ajax({
            type: 'post',
            url: '/spring/user/uploadDelete',
            data: { seq: seqArray }, 
            success: function() {
                alert('선택한 이미지들 삭제 완료');
                location.href = '/spring/user/list';
            },
            error: function(e) {
                console.log(e);
            }
        }); // $.ajax
    } 