// �������� ��������� ���������
function d(el) {
	return document.getElementById(el);
} 

// ������� ��� �������� ���������� ����. Code: href="javascript:close_modal()"
function close_modal() {
	d('modal_open').style.display = 'none'; // ������ ��������� ����
	d('modal_open').innerHTML = ''; // ������� �������  � ��������� ����
	d('modal').style.display = 'none'; // ������ ����� ��� �� ��������
}

// ������� ��� �������� ���������� ����. Code: href="javascript:open_modal(el,w,h)"
function open_modal(el,w,h) {
	d('modal').style.display = 'block'; // ������� ����� ��� �� ��������
	d('modal_open').style.marginTop = '-'+h+'px'; // h - ����������� ���� �����, �������������� � ��������
	d('modal_open').style.marginLeft = '-'+w+'px'; // w - ����������� ���� �����, �������������� � ��������
	d('modal_open').style.display = 'block'; // �������� ��������� ����
	d('modal_open').innerHTML = document.getElementById(el).innerHTML; // ������� � ��������� ���� ���������� �������� "el"
}

function validate_form(form) {
	var inputs = form.getElementsByTagName("input");
	for (var i = 0; i < inputs.length; i++) {
		if (inputs[i].value === "") {
			alert("Fill in the " + inputs[i].name);
			return;
		} 
		if (inputs[i].className === "numeric") {
			if(isNaN(inputs[i].value)) {
            			alert("Fill number in the " + inputs[i].name);
             			return;
			}
		}
	}	
	form.submit();
}