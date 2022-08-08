window.onload = function() {
    // first card, sec card - table
    
    const leftbox = document.querySelectorAll('.switch_table_box');
    const rightbox = document.querySelectorAll('.switch_right_box')
    const rightBtn = document.querySelectorAll('.switch_right_btn');
    const leftBtn = document.querySelectorAll('.switch_left_btn');
    const none = document.querySelectorAll('.none');
    let forNum = 0;


    const btns = document.querySelectorAll('.for_btn');
    btns.forEach(el => el.addEventListener('click', renderMethod));

    ///////////////// first card
    // right 이동 기능 함수(tr의 data-id의 순서로 sort됨)
    function rightSwichFuc() {
        let tbody = leftbox[0].lastElementChild.lastElementChild;
        let tr = tbody.querySelectorAll('tr');
        let rightWarp = rightbox[0];
        let rightHas = rightbox[0].querySelectorAll('.right_list');
        let righ = rightbox[0].querySelectorAll('li.right_list');        
        let addFlag = false;

        const isFull = document.querySelectorAll('#method_right_box li.right_list').length >= 1;

        tr.forEach(item => {            
            let check = item.getElementsByTagName('input')[0];

            if(check.checked) {
                if (isFull) return alert("2개 이상 등록하실 수 없습니다.")
                // input - id 값
                let inputId = check.getAttribute('id');
                let trId = item.getAttribute('data-id');         
                // tr - data-id 값


                // list 생성, listd의 data-id값 적용
                let seed_pk = item.id;
                let rightList = document.createElement('li');
                rightList.setAttribute('data-id', trId);
                rightList.setAttribute('class', 'right_list');
                rightList.dataset.seed_pk = seed_pk;

                // input, label box 요소
                let inputBox = document.createElement('div');
                inputBox.setAttribute('class', 'list_in_wrap');
                
                // list - input, label 생성(class, id, type 적용)
                let rightCheck = document.createElement('input');
                rightCheck.setAttribute('type', 'checkbox');
                rightCheck.setAttribute('class', 'common_checkBox');
                rightCheck.setAttribute('id', inputId);

                let rightlabel = document.createElement('label');
                rightlabel.setAttribute('for', inputId);
                rightlabel.setAttribute('class', 'common_check_label');


                // list - list안에 input, label append
                inputBox.append(rightCheck, rightlabel);
                rightList.append(inputBox);

                const cloneNode = item.cloneNode(true);
                cloneNode.style.display = 'none';
                rightList.append(cloneNode);

                // tr - td 선택
                let data = item.querySelectorAll('td');

                let textBox = document.createElement('div');
                textBox.setAttribute('class', 'list_in_wrap for_list_bx');

                let _span = document.createElement('span');
                _span.setAttribute('class', 'right_data');
                
                _span.innerHTML = `
                	<div class="for_box_wrap">
                		<p>반복수</p>
                		<div class="for_box">
                			<button type="button" class="re_btn for_btn">-</button>
                			<span class="for_num">0</span>
                			<button type="button" class="add_btn for_btn">+</button>
                		</div>
                	</div>`;
                	
                textBox.append(_span);
                rightList.append(textBox);              

                const btns = document.querySelectorAll('.for_box_wrap button');
                btns.forEach(btn => {
                    btn.addEventListener('click', (e) => {
                        renderMethod()
                    })
                })

                data.forEach((items, idx) => {
                    if(idx !== 0) {
                        
                        let val = items.innerText;
                        let textBox = document.createElement('div');
                        textBox.setAttribute('class', 'list_in_wrap');

                        let span = document.createElement('span');
                        span.setAttribute('class', 'right_data');
                        span.setAttribute('title', val);
                        span.innerText = val;
                        textBox.append(span);
                        rightList.append(textBox);
                    };
                });
				
				/*
                const input = document.createElement("input")
                input.setAttribute('type', "text");
                input.setAttribute('class', 'data_comment');
                rightList.append(input);    

                input.addEventListener('change', renderMethod)
				*/
				
                if(rightHas.length === 0) {
                    rightbox[0].append(rightList);
                    item.remove();
                } else {
                    let sortArr = [];
                    let thisData = trId.split('_');

                    for(let i of rightHas) {
                        let sortData = i.getAttribute('data-id');
                        let spData = sortData.split('_');

                        sortArr.push(parseInt(spData[1]));
                    };

                    sortArr.push(parseInt(thisData[1]));

                    sortArr.sort(function(a, b)  {
                        if(a > b) return 1;
                        if(a === b) return 0;
                        if(a < b) return -1;
                    });
                    let pos = sortArr.indexOf(parseInt(thisData[1]));
                    if(pos === 0) {
                        rightHas[pos].insertAdjacentElement('beforebegin', rightList);
                        item.remove();
                    } else {
                        rightHas[pos - 1].insertAdjacentElement('afterend', rightList);
                        item.remove();
                    };
                };

                // 각 리스트별 반복수 조정
                let _listFor = rightList.querySelectorAll('.for_btn');
                let _listForText = rightList.querySelector('.for_num');

                _listFor[0].addEventListener('click', eachForReFuc);
                _listFor[1].addEventListener('click', eachForAddFuc);
            };
        });
        let not = rightbox[0].querySelectorAll('li');

        let notJ = not.length === 0 ? none[0].classList.remove('has') : none[0].classList.add('has');

        //forFuc(forNum);
        renderMethod();
    };

    // left 이동 기능 함수(list의 data-id의 순서로 sort됨)
    function leftSwichFuc() {
        let ul = rightbox[0];
        let li = ul.querySelectorAll('.right_list');
        li.forEach((item, idx,arr) => {
            const isHeader = item.classList.contains('right_header');
            if(!isHeader) {

                // let check = item.getElementsByTagName('input')[0];
                let check = item.querySelector("input[type='checkbox']");
                if(check?.checked) {
                    // input - id 값
                    let inputId = check.getAttribute('id');
                    item.querySelector('.for_list_bx').remove();
                    // tr - data-id 값
                    let liId = item.getAttribute('data-id');
    
                    // tr 생성, list의 data-id값 적용
                    let leftTr = document.createElement('li');
                    leftTr.setAttribute('data-id', liId);
    
                    // input, label box 요소
                    let inputBox = document.createElement('td');
                    let spanBox = document.createElement('span');
    
                    // tr - input, label 생성(class, id, type 적용)
                    let rightCheck = document.createElement('input');
                    rightCheck.setAttribute('type', 'checkbox');
                    rightCheck.setAttribute('class', 'common_checkBox');
                    rightCheck.setAttribute('id', inputId);
    
                    let rightlabel = document.createElement('label');
                    rightlabel.setAttribute('for', inputId);
                    rightlabel.setAttribute('class', 'common_check_label');
    
                    // list - list안에 input, label append
                    spanBox.append(rightCheck, rightlabel);
                    inputBox.append(spanBox);
                    leftTr.append(inputBox);


                    
                    // tr - td 선택
                    let data = item.querySelectorAll('.list_in_wrap > span');
    
                    // data.forEach((items) => {
                    //     let val = items.innerText;
                    //     let textBox = document.createElement('td');
                    //
                    //     let span = document.createElement('span');
                    //     span.innerText = val;
                    //
                    //     textBox.append(span);
                    // });

                    leftTr = item.querySelector('tr');

    
    
                    let leftWarp = leftbox[0].lastElementChild.lastElementChild;
                    let leftHas = leftWarp.querySelectorAll('tr');
    
                    if(leftHas.length === 0) {
                        leftWarp.append(leftTr);
                        item.remove();
                    } else {
                        let sortArr = [];
                        let thisData = liId.split('_');
    
                        for(let i of leftHas) {
                            let sortData = i.getAttribute('data-id');
                            let spData = sortData.split('_');
    
                            sortArr.push(parseInt(spData[1]));
                        };
    
                        sortArr.push(parseInt(thisData[1]));
    
                        sortArr.sort(function(a, b)  {
                            if(a > b) return 1;
                            if(a === b) return 0;
                            if(a < b) return -1;
                        });
                        let pos = sortArr.indexOf(parseInt(thisData[1]));
                        if(pos === 0) {
                            leftHas[pos].insertAdjacentElement('beforebegin', leftTr);
                            item.remove();
                        } else {
                            leftHas[pos - 1].insertAdjacentElement('afterend', leftTr);
                            item.remove();
                        };
                    };
                    reForFuc();
                    tableCellTitle();
                };
            };
        });
        let not = rightbox[0].querySelectorAll('li');
        let notJ = not.length === 0 ? none[0].classList.remove('has') : none[0].classList.add('has');
        renderMethod();
    };

    rightBtn[0].addEventListener('click', rightSwichFuc);
    leftBtn[0].addEventListener('click', leftSwichFuc);

    ///////////////// sec card
    // right 이동 기능 함수(tr의 data-id의 순서로 sort됨)
    function rightSwichFuc2() {
        let tbody = leftbox[1].lastElementChild.lastElementChild;
        let tr = tbody.querySelectorAll('tr');
        const newArr = [];
        tr.forEach(item => {
            let check = item.getElementsByTagName('input')[0];
            if(check.checked) {
                // input - id 값
                let inputId = check.getAttribute('id');
                // tr - data-id 값
                let trId = item.getAttribute('data-id');

                // list 생성, listd의 data-id값 적용
                let rightList = document.createElement('li');

                rightList.setAttribute('data-id', trId);
                rightList.setAttribute('class', 'right_list');

                // input, label box 요소
                let inputBox = document.createElement('div');
                inputBox.setAttribute('class', 'list_in_wrap');
                
                // list - input, label 생성(class, id, type 적용)
                let rightCheck = document.createElement('input');
                rightCheck.setAttribute('type', 'checkbox');
                rightCheck.setAttribute('class', 'common_checkBox');
                rightCheck.setAttribute('id', inputId);

                let rightlabel = document.createElement('label');
                rightlabel.setAttribute('for', inputId);
                rightlabel.setAttribute('class', 'common_check_label');

                // list - list안에 input, label append
                inputBox.append(rightCheck, rightlabel);
                rightList.append(inputBox);

                let cloneNode = item.cloneNode(true);
                cloneNode.style.display = 'none';
                rightList.append(cloneNode);
                
                
                // tr - td 선택
                let data = item.querySelectorAll('td');

                data.forEach((items, idx) => {
                    if(idx !== 0) {
                        
                        let val = items.innerText;
                        let textBox = document.createElement('div');
                        textBox.setAttribute('class', 'list_in_wrap');

                        let span = document.createElement('span');
                        span.setAttribute('class', 'right_data');
                        span.setAttribute('title', val);
                        span.innerText = val;
                        textBox.append(span);
                        rightList.append(textBox);
                    };
                });


                let rightWarp = rightbox[1];
                let rightHas = rightbox[1].querySelectorAll('.right_list');

                if(rightHas.length === 0) {
                    rightbox[1].append(rightList);
                    item.remove();
                } else {
                    let sortArr = [];
                    let thisData = trId.split('_');

                    for(let i of rightHas) {
                        let sortData = i.getAttribute('data-id');
                        let spData = sortData.split('_');

                        sortArr.push(parseInt(spData[1]));
                    };

                    sortArr.push(parseInt(thisData[1]));

                    sortArr.sort(function(a, b)  {
                        if(a > b) return 1;
                        if(a === b) return 0;
                        if(a < b) return -1;
                    });
                    let pos = sortArr.indexOf(parseInt(thisData[1]));
                    if(pos === 0) {
                        rightHas[pos].insertAdjacentElement('beforebegin', rightList);
                        item.remove();
                    } else {
                        rightHas[pos - 1].insertAdjacentElement('afterend', rightList);
                        item.remove();
                    };
                };
            };
        });



        let not = rightbox[1].querySelectorAll('li');

        let notJ = not.length === 0 ? none[1].classList.remove('has') : none[1].classList.add('has');
        const seed_tbody = document.querySelector("#seed_tbody");
        const seed_right_box = document.querySelector('#seed_right_box');

        renderSeed();

    };

    // left 이동 기능 함수(list의 data-id의 순서로 sort됨)
    function leftSwichFuc2() {
        let ul = rightbox[1];
        let li = ul.querySelectorAll('.right_list');
        const newArr = []
        li.forEach((item, idx) => {
            const isHeader = item.classList.contains('right_header');
            if(!isHeader) {
                let check = item.getElementsByTagName('input')[0];
    
                if(check.checked) {
                    // input - id 값
                    let inputId = check.getAttribute('id');
    
                    // tr - data-id 값
                    let liId = item.getAttribute('data-id');
    
                    // tr 생성, list의 data-id값 적용
                    let leftTr = document.createElement('tr');
                    leftTr.setAttribute('data-id', liId);
    
                    // input, label box 요소
                    let inputBox = document.createElement('td');
                    let spanBox = document.createElement('span');
    
                    // tr - input, label 생성(class, id, type 적용)
                    let rightCheck = document.createElement('input');
                    rightCheck.setAttribute('type', 'checkbox');
                    rightCheck.setAttribute('class', 'common_checkBox');
                    rightCheck.setAttribute('id', inputId);
    
                    let rightlabel = document.createElement('label');
                    rightlabel.setAttribute('for', inputId);
                    rightlabel.setAttribute('class', 'common_check_label');
    
                    // list - list안에 input, label append
                    spanBox.append(rightCheck, rightlabel);
                    inputBox.append(spanBox);
                    leftTr.append(inputBox);
                    
                    // tr - td 선택
                    let data = item.querySelectorAll('.list_in_wrap > span');
    
                    data.forEach((items) => {
                        let val = items.innerText;
                        let textBox = document.createElement('td');
    
                        let span = document.createElement('span');
                        span.innerText = val;
    
                        textBox.append(span);
                        leftTr.append(textBox);
                    });
    
                    let leftWarp = leftbox[1].lastElementChild.lastElementChild;
                    let leftHas = leftWarp.querySelectorAll('tr');
    
                    if(leftHas.length === 0) {
                        leftWarp.append(leftTr);
                        item.remove();
                    } else {
                        let sortArr = [];
                        let thisData = liId.split('_');
    
                        for(let i of leftHas) {
                            let sortData = i.getAttribute('data-id');
                            let spData = sortData.split('_');
    
                            sortArr.push(parseInt(spData[1]));
                        };
    
                        sortArr.push(parseInt(thisData[1]));
    
                        sortArr.sort(function(a, b) {
                            if(a > b) return 1;
                            if(a === b) return 0;
                            if(a < b) return -1;
                        });
                        let pos = sortArr.indexOf(parseInt(thisData[1]));
                        if(pos === 0) {
                            leftHas[pos].insertAdjacentElement('beforebegin', leftTr);
                            item.remove();
                        } else {
                            leftHas[pos - 1].insertAdjacentElement('afterend', leftTr);
                            item.remove();
                        };
                    };
                    tableCellTitle();
                };
            };
        });

        renderSeed();

        let not = rightbox[1].querySelectorAll('li');

        let notJ = not.length === 0 ? none[1].classList.remove('has') : none[1].classList.add('has');
    };

    rightBtn[1].addEventListener('click', rightSwichFuc2);
    leftBtn[1].addEventListener('click', leftSwichFuc2);

    //let reBtn = document.querySelector('.an_list_box .re_btn');
    //let addBtn = document.querySelector('.an_list_box .add_btn');

    ///// 2021-11-26 추가
    // 반복수 변경시 변경 
    function forFuc(num, e) {
		
        if(e === undefined) {
            const _rightList = document.querySelectorAll('.first_card li.right_list');
			const forNumText = document.querySelector('.an_list_box .for_num');
			
            forNumText.innerText = num;
            
            _rightList.forEach((item) => {
                let data = item.querySelectorAll('.list_in_wrap');
    
                data.forEach((i, idx) => {
                    if(idx === 6) {
                        let span = i.firstElementChild;
                        span.innerText = num;
                    } else if(idx === 1) {
                        let forBox = i.firstElementChild.querySelector('.for_num');
                        forBox.innerText = num;
                    };
                });
            });
            
        } else {
	
			console.log("else")
	
            let forText = e.querySelector('.for_num');
            let _list = e.parentNode.parentNode.parentNode.parentNode;
            let data = _list.querySelectorAll('.list_in_wrap');

            forText.innerText = num;
            data.forEach((item, idx) => {
                if(idx === 6) {
                    let _span = item.firstElementChild;
                    _span.innerText = num;
                };
                return false;
            });
        };
    };
    
    // 반복수 감소 기능 
    function forTextReFuc() {
	
		console.log("dsd")
	
        if(forNum <= 0) {
            return;
        } else {
            forNum--; 
            forFuc(forNum); 
        };
    };
    
    function forTextaddFuc() {
		forNum++; 
		forFuc(forNum)
	};
	
	// reBtn.addEventListener('click', forTextReFuc);
    // addBtn.addEventListener('click', forTextaddFuc);
    
    // 왼쪽으로 이동시 반복수 리셋
    function reForFuc() {
        const _rightList = document.querySelectorAll('.first_card .list_table tbody > tr');
        
        _rightList.forEach((item) => {
            let data = item.querySelectorAll('td');

            data.forEach((i, idx) => {
                if(idx === 5) {
                    let span = i.firstElementChild;
                    span.innerText = 0;
                };
            });
        });
        renderMethod()
    };

    ///// 2021-11-26 추가
    function eachForAddFuc(e) {
        let forBox = e.target.parentNode;
        let forText = forBox.querySelector('.for_num');
        let forDataVal = parseInt(forText.innerText);
       
        forDataVal++;
        forFuc(forDataVal, forBox);
        renderMethod()
    };
    
    ///// 2021-11-26 추가
    function eachForReFuc(e) {
		
        let forBox = e.target.parentNode;
        let forText = forBox.querySelector('.for_num');
        let forDataVal = parseInt(forText.innerText);
		
        if(forDataVal <= 0) {
            return;
        } else {
            forDataVal--; 
            forFuc(forDataVal, forBox);
        };
        renderMethod()
    };


    forFuc(forNum);
    
    function tableCellTitle() {
        const Alltable = document.querySelectorAll('table');
        
        
        for(let i = 0; i < Alltable.length; i++) {
            let Alltd = Alltable[i].querySelectorAll('td');
            
            Alltd.forEach(item => {
                let hasSpan = item.children;
                
                
                if(hasSpan.length === 0) {
                    item.setAttribute('title', item.innerText);
                } else {
                    let span = item.querySelector('span');
                    let a = item.querySelector('a');
    
                    if(span !== null) {
                        item.setAttribute('title', span.innerText);
                    } else if(a !== null) {
                        item.setAttribute('title', a.innerText);
                    };
                };
            });
        };
    };

    tableCellTitle();
};

function renderSeed(){
    const seed_box = document.querySelectorAll('#seed_right_box li.right_list > tr');
    const seed_tbody = document.getElementById('seed_tbody');
    seed_tbody.innerHTML= '';
    seed_box.forEach(tr =>{
        const cloneNode = tr.cloneNode(true);
        cloneNode.style.display = '';
        cloneNode.querySelector('.td_chk').remove();

        seed_tbody.append(cloneNode);
    })
}
function renderMethod(){
    const method_tbody = document.getElementById('method_tbody');
    const method_box = document.querySelectorAll('#method_right_box li.right_list > tr');
    
    method_tbody.innerHTML= '';
    method_box.forEach(tr =>{
        if (!tr) return;
        const loopNum = document.querySelector('#method_right_box .for_num').innerText;
        //const comment = document.querySelector('#method_right_box .data_comment').value;
        const cloneNode = tr.cloneNode(true);
        cloneNode.style.display = '';
        cloneNode.querySelector('.td_chk').remove();        

        const loop = cloneNode.querySelectorAll('td');
        
        //loop[loop.length - 1].innerText = loopNum;
        //loop[loop.length - 1].dataset.title = loopNum;
        //loop[loop.length - 1].dataset.title = loopNum;
        // cloneNode.append(loopNum)
        const td =  document.createElement('td');
        td.innerText = `${loopNum}`;
        cloneNode.append(td);
        //cloneNode.appendChild(td);
        method_tbody.append(cloneNode);
    })
}

