document.addEventListener('DOMContentLoaded', () => {
    // 모달 함수
    function md5() {
        let lookUpBtn = document.querySelectorAll('.modal_on_btn');
        const main = document.getElementById('main');
        const modalCloseBtn = document.querySelectorAll('.modal_close');
    
        lookUpBtn.forEach((item) => {
            let _has = item.classList.contains('modal_on_btn');
            let _id = item.getAttribute('data-modal');
            
            if(_id !== '' && _has) {
                let lookupModalId = document.getElementById(_id);
        
                lookupModalId.addEventListener('click', (e) => {
                    let target = e.target;
                    if(target.classList.contains('modal') === false) {return};
        
                    lookupModalId.classList.remove('on');
                    main.classList.remove('modal_on');

                    const sendnext = document.querySelector('.send_next_btn');
                    if(sendnext !== null) {

                        let mBox = lookupModalId.querySelectorAll('.modal_box');
                        
                        mBox.forEach((item, idx) => {
                            if(idx === 0) {
                                item.classList.add('on');
                            } else {
                                item.classList.remove('on');
                            };
                        });
                    };

                });
            
                item.addEventListener('click', (e) => {
                    e.preventDefault();
                    main.classList.add('modal_on');
                    lookupModalId.classList.add('on');
                    const target = e.currentTarget;
                    const schId = target.dataset.sch_id;
                    
                    if(schId != undefined){
	                    selectSchedule(schId);
					}
                });
            
                modalCloseBtn.forEach((item) => {
                    item.addEventListener('click', () => {
                        lookupModalId.classList.remove('on');
                        main.classList.remove('modal_on');

                        const sendnext = document.querySelector('.send_next_btn');
                        if(sendnext !== null) {

                            let mBox = lookupModalId.querySelectorAll('.modal_box');
                            
                            mBox.forEach((item, idx) => {
                                if(idx === 0) {
                                    item.classList.add('on');
                                } else {
                                    item.classList.remove('on');
                                };
                            });
                        };
                    });
                });
            } else {
                return;
            }
        });
    };
    
    let FilColorNum = [0, 0, 1, 1, 2, 2, 3, 3, 4, 4, 5, 5, 2, 2, 1, 1, 5, 5, 2, 0, 3, 3, 1, 2, 3, 2, 2];
    let FilColor = ['orange', 'salmon', 'lightgray', 'darkseagreen', 'lightpink', 'peachpuff'];

    // 캘린더에 띄우기를 위한 객체 생성 ;
    class TestData {
        constructor(date1, text, color, date2, schType, schId, planId) {
            this.date1 = date1;
            this.date2 = date2;
            this.text = text;
            this.color = color;
            this.schType = schType;
            this.schId = schId;
            this.planId = planId;
        };
    };


	 let dateData = [];
	 let colorIndex = 0;
	
	 if(data != null) {
	
	 	console.log(data , "data")
	
	      for (let i = 0; i < data.length; i++) {
			
	 		let color = FilColor[colorIndex];
	 		let calender = null;
			
	 		if(data[i].plan_code == null || data[i].plan_code == '' ){
	 		calender = new TestData(dateIdChange2(data[i].start_date),data[i].name+","+data[i].sch_title, color ,dateIdChange2(data[i].end_date), data[i].sch_type, data[i].sch_id, data[i].plan_id);
	
	 		}else{
	         calender = new TestData(dateIdChange2(data[i].start_date),data[i].name+","+ (data[i].division == null ? data[i].easy_division : data[i].division ) , color ,dateIdChange2(data[i].end_date), data[i].sch_type, data[i].sch_id, data[i].plan_id);
				
	 		}
	         
	         dateData.push(calender);
	        	colorIndex++;
	       
	     if(colorIndex == 6){
	  		colorIndex = 0;
	      }
	   }
	 }
	


    ////// calendar 기능
    const taskSelectBox = document.querySelector('.date_box');
    const calendar = document.getElementById('calendar');
    const beforeBtn = document.querySelector('.before_btn');
    const afterBtn = document.querySelector('.after_btn');
    const _day = new Date();
    const day = new Date();

    let year = day.getFullYear();
    let month = day.getMonth();
    let YM = year + '년 ' + (month + 1) + '월';
    let firstDate = new Date(year,month,1).getDate();   
    let lastDate = new Date(year,month + 1,0).getDate();   
    let firstDay = new Date(year,month,1).getDay();   
    let weekArr = ['일', '월', '화', '수', '목', '금', '토'];
    
    let todayDate = day.getDate();
    let _todayDay = new Date(`${year}-${month + 1}-${todayDate}`).getDay();

    document.querySelector('.ym').innerHTML = YM;


    function thisWeek(date, day) {
        let arr = [[], [], []];
        
        if(day < 6 && day > 0) {
            for(let i = 0; i < day; i++) {arr[0].push(i)};
            for(let j = day; j < 6; j++) {arr[2].push(j)};
        } else if(day === 6) {
            for(let i = 0; i < day; i++) {arr[0].push(i)};
        } else if(day === 0) {
            for(let i = day ; i < 6; i++) {arr[2].push(i)};
        };
        arr[1].push(date);

        return arr;
    };
    
    let betwDay;
    let betwDate = [[], [], []];

    // 캘린더 함수
    function _calendar() {
        let row = calendar.insertRow();

        betwDay = thisWeek(todayDate, _todayDay);

        betwDay.forEach((item, idx) => {
            if(item.length !== 0) {
                if(idx === 0) {
                    for(let i = item.length; i >= 1; i--) {
                        let lessDay = todayDate - i;
                        betwDate[0].push(lessDay);
                    };
                } else if(idx === 1) {
                    betwDate[1] = item;
                } else {
                    for(let i = 1; i < item.length + 1; i++) {
                        let addDay = todayDate + i;
                        betwDate[2].push(addDay);
                    };
                };
            };
        });
        
        let weekDayText = document.querySelectorAll('.table_body > tr:first-child th');

        betwDate.forEach((item) => {
            if(item.length !== 0) {
                item.forEach((i) => {
                    let weekDayCell = document.querySelectorAll('.table_body > tr:last-child td');

                    
                    if(i <= 0) {
                        let cell = row.insertCell();
                        let _beforeMonDate = new Date(year,month,0).getDate(); 
                        let beDate = _beforeMonDate + i;
                        
                        cell.innerHTML = '<span class="date_day_num">'+ beDate +'</span>';
                        cell.setAttribute('class', 'beforeMonth');
                        let weekT;

                        if(month === 0) {   
                            weekT = new Date(`${year - 1}-${12}-${beDate}`).getDay();
                            cell.setAttribute('id', `${year - 1}_${12}_${beDate}`);
                        } else {
                            weekT = new Date(`${year}-${month}-${beDate}`).getDay();
                            cell.setAttribute('id', `${year}_${month}_${beDate}`);
                        };
                            
                        weekDayText[weekDayCell.length].innerHTML = weekArr[weekT];
                        
                    } else {
                        if(lastDate < i) {
                            let cell = row.insertCell();
                            let after = i - lastDate;
                            
                            if(month === 11) {
                                let weekT = new Date(`${year + 1}-${1}-${after}`).getDay();
                                cell.setAttribute('id', `${year + 1}_${1}_${after}`);

                                weekDayText[weekDayCell.length].innerHTML = weekArr[weekT];
                            } else {
                                let weekT = new Date(`${year}-${month + 2}-${after}`).getDay();
                                cell.setAttribute('id', `${year}_${month + 2}_${after}`);

                                weekDayText[weekDayCell.length].innerHTML = weekArr[weekT];
                            };

                            cell.setAttribute('class', 'afterMonth');
                            cell.innerHTML = `<span class="date_num">${after}</span>`;
                        } else {
                            let cell = row.insertCell();
                            let weekT;
                            weekT = new Date(`${year}-${month + 1}-` + i).getDay();
                            cell.setAttribute('id', `${year}_${month + 1}_` + i);


                            cell.setAttribute('class', 'cal_cell');
                            cell.innerHTML = `<span class="date_num">${i}</span>`;

                            weekDayText[weekDayCell.length].innerHTML = weekArr[weekT];
                        };
                    };
                });
            };
        });
    };

     // 생성한 객체 데이터를 생성함수에 적용
     function taskE(dt) {
        dt.sort(dateSort);
        for(let item of dt) { dataApennd(item.date1, item.date2, item.text, item.color, item.schType, item.schId, item.planId)};
    };

    // date1 날짜별 sort
    function dateSort(a, b) {
        let changA = dateIdChange(a.date1);
        let changB = dateIdChange(b.date1);

        let dateA = new Date(changA).getTime();
        let dateB = new Date(changB).getTime();

        let textA = a.text;
        let textB = b.text;
        
        let spTextA = textA;
        let spTextB = textB;

        let numTextA = a.id;
        let numTextB = b.id;


        if(dateA === dateB) {
            return numTextA > numTextB ? 1 : -1;
        } else {
            return dateA > dateB ? 1 : -1;
        };
    };

    // 전달 표시 함수
    function beforeMonth(_dt) {
        while(calendar.rows.length > 2) {
            calendar.deleteRow(calendar.rows.length - 1);
        };

        todayDate -= 7;

        if(todayDate < 0) {
            month = month - 1;
            if(month === 0) {
                // year = year - 1;
                
                let _lastDate = new Date(year,month + 1,0).getDate();
                
                todayDate = _lastDate;
                _todayDay = new Date(`${year}-${month + 1}-${todayDate}`).getDay();
                
            } else if(month === -1) {
                year = year - 1;
                month = month + 12;
                
                let _lastDate = new Date(year,month + 1,0).getDate();
                
                todayDate = _lastDate;
                _todayDay = new Date(`${year}-${month + 1}-${todayDate}`).getDay();
                
            } else {
                let _lastDate = new Date(year,month + 1,0).getDate();
                todayDate = _lastDate;
                
                _todayDay = new Date(`${year}-${month}-${todayDate}`).getDay();
            };
        };
        
        
        YM = year + '년 ' + (month + 1) + '월';
        document.querySelector('.ym').innerHTML = YM;

        firstDate = new Date(year,month,1).getDate();   
        lastDate = new Date(year,month + 1,0).getDate();   
        firstDay = new Date(year,month,1).getDay(); 

        betwDay = [[], [], []];
        betwDate = [[], [], []];

        // 캘린더 생성 함수
        _calendar();

        // 데이터 띄우기 함수
        taskE(_dt);
        spanMore();
        weekStartEnd();
        
        // 모달함수
        md5();

        if(_day.getMonth() === month && _day.getFullYear() === year) {todayColor()};
    };

    // 다음달 표시 함수
    function afterMonth(_dt) {
        while(calendar.rows.length > 2) {
            calendar.deleteRow(calendar.rows.length - 1);
        };


        todayDate += 7;

        if(todayDate > lastDate) {
            month = month + 1;

            if(month === 12) {
                year += 1;
                month = 0;
                
                let _lastDate = new Date(year,month + 1,0).getDate();
                todayDate = 1;

                _todayDay = new Date(`${year}-${month + 1}-${todayDate}`).getDay();
                
            } else {
                todayDate = 1;

                _todayDay = new Date(`${year}-${month + 1}-${todayDate}`).getDay();
            };
        };

        YM = year + '년 ' + (month + 1) + '월';
        document.querySelector('.ym').innerHTML = YM;

        firstDate = new Date(year,month,1).getDate();   
        lastDate = new Date(year,month + 1,0).getDate();   
        firstDay = new Date(year,month,1).getDay(); 

        betwDay = [[], [], []];
        betwDate = [[], [], []];

        // // 캘린더 생성 함수
        _calendar();

        // 데이터 띄우기 함수
        taskE(_dt);
        spanMore();
        weekStartEnd();

        // 모달함수
        md5();

        if(_day.getMonth() === month && _day.getFullYear() === year) {todayColor()};
    };

    // 오늘날짜 색 표시 함수
    function todayColor() {
        for(let i = 1; i <= lastDate; i++) {
            let getId;

            if(month === 1) {
                getId = document.getElementById(`${year}_${month }_` + i);
            } else {
                getId = document.getElementById(`${year}_${month + 1}_` + i);
            }; 

            if(getId !== null) {
                let IdVal = getId.getAttribute('id').split('_');
                if(IdVal[2] == _day.getDate()) {
                    getId.querySelector('span').classList.add('to_day');
                };
            };
        };
    };

   // 캘린더에 데이터 적용하는 함수
   function dataApennd(date1, date2, text, color, sch, schId , planId) {

       console.log(date1 , date2 , text , "week");


        let thisDate = document.getElementById(date1);
        let lastDate = document.getElementById(date2);

        // 날짜 사이의 추출한 날짜 문자열 id값 배열
        let betwDateArr = [];

        // 문자열 변환
        let chageFDte = dateIdChange(date1); 
        let chageLDte = dateIdChange(date2);  

        // 날짜 사이 날짜들 추출
        let beTwDate = getDatesStartToLast(chageFDte, chageLDte);

        // 날짜 사이 일수
        let difDate = dateDiff(chageFDte, chageLDte);

        // 날짜 사이의 추출한 날짜 문자열 id값 변환
        for(let i = 0; i < beTwDate.length; i++) {
            betwDateArr.push(dateIdChange2(beTwDate[i]));
        };

        // 데이터의 날짜가 현재 달에 있는지 확인
        let leng =  betwDateArr.length - 1;
        for(let i = 0; i < betwDateArr.length; i++) {
            let _beDate = document.getElementById(betwDateArr[i - 1]);
            let _thisDate = document.getElementById(betwDateArr[i]);

            if(_thisDate !== null) {

                let _title = [];
                if(_beDate !== null) {
                    let sameT = _beDate.querySelectorAll('.date_task > p');
                    
                    
                    sameT.forEach((item, idx) => {
                        let sameTitle = item.getAttribute('title');
                        if(idx === 0) {
                            let _beDate2 = document.getElementById(betwDateArr[i - 2]);

                            if(_beDate2 !== null) {
                                let _sameT = _beDate2.querySelectorAll('.date_task > p');

                                _sameT.forEach((items) => {
                                    let _sameTitle = items.getAttribute('title');
                                    let leng = items.parentNode.parentNode.querySelectorAll('.date_task');
                                    if(_sameTitle === sameTitle) {
                                        if(leng.length === 1) {
                                            _title.push(new Array(item.parentNode, sameTitle, idx, true, true));
                                        } else {
                                            _title.push(new Array(item.parentNode, sameTitle, idx, true, false));
                                        };
                                    }
                                });
                            };
                        } else {
                            _title.push(new Array(item.parentNode, sameTitle, idx, false));
                        };

                    });
                };

                let _day = betwDateArr[i].split('_');
                let hasDiv = _thisDate.querySelector('.date_task_wrap');
                let hasMBtn = _thisDate.querySelector('.more_popup_btn');
                let lengDiv = _thisDate.querySelectorAll('.date_task');
                let popupBox = _thisDate.querySelector('.more_popup');

                let thisListTotal = lengDiv.length * 22;
                for(let i = 0; i < lengDiv.length; i++) {
                    let listPt = lengDiv[i].style.paddingTop.split('p')[0];
                    
                    if(listPt !== '') {
                        thisListTotal += parseInt(listPt);
                    };
                };
                
                thisListTotal = (thisListTotal + 22) / 22;
                if(hasDiv === null && lengDiv.length >= 0 && lengDiv.length < 4) {
                    _thisDate.querySelector('span').remove();
                    
                    let daySpan = document.createElement('span');
                    let wrapDiv = document.createElement('div');
                    let modalDiv = document.createElement('div');
                    let taskP = document.createElement('p');
                    
                    daySpan.setAttribute('class', 'date_day_num');
                    wrapDiv.setAttribute('class', 'date_task_wrap');
                    modalDiv.setAttribute('class', 'date_task modal_on_btn');
                    modalDiv.dataset.sch_id=schId;

                    if(sch !== 2) {
                        modalDiv.setAttribute('data-modal', '_taskTable');
                    } else {
                        modalDiv.setAttribute('data-modal', '_schedule_status');
                    };

                    modalDiv.addEventListener('click',function(e){
                        selectSchedule(e);
                    })

                    taskP.setAttribute('title', text);
                    
                    // 데이터 시작, 중간, 끝 날짜 클라스 구분

                    if(i === 0 && betwDateArr.length > 1) {
                        modalDiv.classList.add('start');
                        taskP.innerText = text;
                    } else if (i === leng && betwDateArr.length > 1) {
                        modalDiv.classList.add('end');
                    } else if(i > 0 && i < leng) {
                        modalDiv.classList.add('btw');
                    } else if(i === 0 && betwDateArr.length === 1) {
                        modalDiv.classList.add('day');
                        taskP.innerText = text;
                    };
                    

                    if(_beDate !== null) {
                        let _leng = _beDate.querySelectorAll('.date_task');
                        let pTitle = taskP.getAttribute('title');

                        _title.forEach((item) => {
                            if(item[1] === pTitle) {
                                let prPadd = item[0].style.paddingTop.split('p')[0];
                                
                                
                                if(_title[4] !== undefined) {
                                    modalDiv.style.padding = (22 * (item[2]) + 1) + 'px 0 0 0';
                                    
                                } else if(prPadd !== '' && item[2] === lengDiv.length) {
                                    let numPaddin = parseInt(prPadd);
                                    
                                    modalDiv.style.paddingTop = numPaddin + 'px';
                                    
                                } else {
                                    let beListPar = item[0].parentNode;
                                    let beListParClas = beListPar.getAttribute('class');
                                    
                                    
                                    if(beListParClas === 'more_popup') {
                                        if(i === leng && betwDateArr.length > 1) {
                                            modalDiv.classList.remove('btw');
                                            modalDiv.classList.remove('end');
                                            modalDiv.classList.add('day');
                                        } else {
                                            modalDiv.classList.add('start');
                                        };

                                        taskP.innerText = text;
                                    } else {
                                        let paI = [];
                                        
                                        _leng.forEach((i, idx) => {
                                            let _prPadd = i.style.paddingTop.split('p')[0];
                                            
                                            if(_prPadd !== '' && idx === item[2]) {
                                                let _numPaddin = parseInt(_prPadd);
                                                paI.push(_numPaddin);

                                            } else if(_prPadd !== '' && idx < item[2]) {
                                                let _numPaddin = parseInt(_prPadd);
                                                _numPaddin += 22;
                                                paI.push(_numPaddin);
                                            } else if(_prPadd === '' && idx < item[2]) {
                                                paI.push(22);
                                            };
                                        });

                                        if(paI.length !== 0) {
                                            let totalPadd = 0;
                                            for(let i = 0; i < paI.length; i++) {totalPadd += paI[i]};
                                            modalDiv.style.padding = totalPadd + 'px 0 0 0';
                                        } else {
                                            modalDiv.style.padding = (22 * item[2]) + 'px 0 0 0';
                                        };
                                    };

                                    };
                            };
                        });
                    };
                    
                    daySpan.innerHTML = _day[2];
                    taskP.style.backgroundColor = color;

                    modalDiv.append(taskP);
                    wrapDiv.append(modalDiv);
                    _thisDate.append(wrapDiv);
                    _thisDate.prepend(daySpan);

                } else if(lengDiv.length >= 4  || thisListTotal >= 4) {
                    if(popupBox === null) {
                        let moreSpan = document.createElement('button');
                        let popupList = document.createElement('div');
                        let popupHead = document.createElement('div');
                        let popupHeadText = document.createElement('p');
                        let dateText = betwDateArr[i].split('_');
        
        
                        moreSpan.setAttribute('type', 'button');
                        moreSpan.setAttribute('class', 'more_popup_btn');
                        popupList.setAttribute('class', 'more_popup');
        
                        popupHead.setAttribute('class', 'more_pop_header');
                        popupHeadText.innerText = dateText[0] + '년 ' + dateText[1] + '월 ' + dateText[2] + '일';
        
                        popupHead.append(popupHeadText);
                        popupList.append(popupHead);
        
                        let modalDiv = document.createElement('div');
                        let taskP = document.createElement('p');
            
                        modalDiv.setAttribute('class', 'date_task modal_on_btn');

                        if(sch !== 2) {
                            modalDiv.setAttribute('data-modal', '_taskTable');
                        } else {
                            modalDiv.setAttribute('data-modal', '_schedule_status');
                        };

                         modalDiv.dataset.sch_id = schId;


                        taskP.setAttribute('title', text);

                        // 데이터 시작, 중간, 끝 날짜 클라스 구분
                        if(i === 0 && betwDateArr.length > 1) {
                            modalDiv.classList.add('start');
                            taskP.innerText = text;
                        } else if (i === leng && betwDateArr.length > 1) {
                            modalDiv.classList.add('end');
                        } else if(i > 0 && i < leng) {
                            modalDiv.classList.add('btw');
                        } else if(i === 0 && betwDateArr.length === 1) {
                            modalDiv.classList.add('day');
                            taskP.innerText = text;
                        };
                        

                        taskP.innerText = text;
                        taskP.style.backgroundColor = color;

                        modalDiv.append(taskP);
                        popupList.append(modalDiv);
        
                        
                        hasDiv.insertBefore(moreSpan, null);
                        hasDiv.insertBefore(popupList, null);

                    } else {
                        let modalDiv = document.createElement('div');
                        let taskP = document.createElement('p');
            
                        modalDiv.setAttribute('class', 'date_task modal_on_btn');

                        if(sch !== 2) {
                            modalDiv.setAttribute('data-modal', '_taskTable');
                        } else {
                            modalDiv.setAttribute('data-modal', '_schedule_status');
                        };


                        modalDiv.dataset.sch_id = schId;


                        taskP.setAttribute('title', text);
                        
                        // 데이터 시작, 중간, 끝 날짜 클라스 구분
                        if(i === 0 && betwDateArr.length > 1) {
                            modalDiv.classList.add('start');
                            taskP.innerText = text;
                        } else if (i === leng && betwDateArr.length > 1) {
                            modalDiv.classList.add('end');
                        } else if(i > 0 && i < leng) {
                            modalDiv.classList.add('btw');
                        } else if(i === 0 && betwDateArr.length === 1) {
                            modalDiv.classList.add('day');
                            taskP.innerText = text;
                        };

                        taskP.innerText = text;
                        taskP.style.backgroundColor = color;

                        modalDiv.append(taskP);
                        popupBox.append(modalDiv);
                    };

                } else if(lengDiv.length <= 4 && hasMBtn === null) {
                    let modalDiv = document.createElement('div');
                    let taskP = document.createElement('p');
                    
                    modalDiv.setAttribute('class', 'date_task modal_on_btn');
                    
                    
                    if(sch !== 2) {
                        modalDiv.setAttribute('data-modal', '_taskTable');
                    } else {
                        modalDiv.setAttribute('data-modal', '_schedule_status');
                    };
                    

                    modalDiv.dataset.sch_id = schId;
                    

                    taskP.setAttribute('title', text);
                    
                    
                    if(i === 0) {
                        taskP.innerText = text;
                    };

                    // 데이터 시작, 중간, 끝 날짜 클라스 구분
                    if(i === 0 && betwDateArr.length > 1) {
                        modalDiv.classList.add('start');
                        taskP.innerText = text;
                    } else if (i === leng && betwDateArr.length > 1) {
                        modalDiv.classList.add('end');
                    } else if(i > 0 && i < leng) {
                        modalDiv.classList.add('btw');
                    } else if(i === 0 && betwDateArr.length === 1) {
                        modalDiv.classList.add('day');
                        taskP.innerText = text;
                    };
                    
                    if(_beDate !== null) {
                        let _leng = _beDate.querySelector('.date_task_wrap');
                        let pTitle = taskP.getAttribute('title');
                        let spanB = _leng.querySelector('.more_popup');
                        
                        if(lengDiv.length !== _title.length) {
                            _title.forEach((item) => {
                                if(item[1] === pTitle) {
                                    if(item[2] !== lengDiv.length) {
                                        let prPadd = item[0].style.paddingTop.split('p');
                                        
                                        let numPaddin = parseInt(prPadd[0]);
                                        
                                        if(_title[4] !== undefined) {
                                            modalDiv.style.padding = (22 * (item[2]) + 1) + 'px 0 0 0';
                                            
                                        } else if(numPaddin > 0 ) {
                                            
                                            let bePT = item[0].style.paddingTop.split('p')[0];
                                            let bePTVal = bePT !== '' ? parseInt(bePT) : 0;
                                            let bePos = (item[2] * 22) + bePTVal;
                                            let thisPos = bePos - (lengDiv.length * 22);
                                            
                                            modalDiv.style.paddingTop = thisPos + 'px';
                                            
                                        } else {
                                            
                                            lengDiv.forEach(items => {
                                                let _prPadd = items.style.paddingTop.split('p');
                                                let _pNum = parseInt(_prPadd[0]);
                                                if(_pNum === 0 || _pNum === NaN) {
                                                    let beDataList = _leng.querySelectorAll('.date_task');

                                                    let beListTotal = 0;
                                                    for(let j = 0; j < beDataList.length; j++) {
                                                        let beListPt = beDataList[j].style.paddingTop.split('p')[0];

                                                        if(beListPt !== '') {beListTotal += parseInt(beListPt)};
                                                    };

                                                    let totalList = 0;
                                                    for(let i = 0; i < lengDiv.length; i++) {
                                                        let listPT = lengDiv[i].style.paddingTop.split('p')[0];

                                                        if(listPT !== '') {totalList += parseInt(listPT)};
                                                    };
                                                    
                                                    totalList = totalList / 22;
                                                    
                                                    modalDiv.style.padding = ((22 * (item[2] - (lengDiv.length + totalList))) + beListTotal) + 'px 0 0 0';
                                                };
                                            });
                                        };
                                    } else {
                                        let prPadd = item[0].style.paddingTop.split('p');
                                        let numPaddin = parseInt(prPadd[0]);
                                        
                                        
                                        if(numPaddin !== NaN) {
                                            modalDiv.style.padding = numPaddin + 'px 0 0 0';
                                        };
                                    };
                                };
                            });
                        };
                    };
                    
                    taskP.style.backgroundColor = color;
                
                    modalDiv.append(taskP);
                    hasDiv.append(modalDiv);

                    if(_beDate !== null) {
                        let _leng = _beDate.querySelector('.date_task_wrap');
                        let pTitle = taskP.getAttribute('title');
                        let spanB = _leng.querySelector('.more_popup');
                        
                        if(lengDiv.length !== _title.length) {
                            if(spanB !== null) {
                                let more = spanB.querySelectorAll('.date_task');
                                let thisPop = _thisDate.querySelector('.more_popup');
        
                                more.forEach(it => {
                                    let titl = it.firstElementChild.getAttribute('title');
                                    let list = _leng.querySelectorAll('.date_task');
                                    let listTotal = list.length * 22;

                                    for(let i = 0; i < list.length; i++) {
                                        listTotal += parseInt(list[i].style.paddingTop);
                                    };
                                    
                                    if(pTitle === titl && thisPop === null) {

                                        let moreSpan = document.createElement('button');
                                        let popupList = document.createElement('div');
                                        let popupHead = document.createElement('div');
                                        let popupHeadText = document.createElement('p');
                                        let dateText = betwDateArr[i].split('_');
                                        
                                        moreSpan.setAttribute('type', 'button');
                                        moreSpan.setAttribute('class', 'more_popup_btn');
                                        popupList.setAttribute('class', 'more_popup');
                                        taskP.innerText = text;
                                        popupHead.setAttribute('class', 'more_pop_header');
                                        popupHeadText.innerText = dateText[0] + '년 ' + dateText[1] + '월 ' + dateText[2] + '일';
                                        
                                        modalDiv.style.paddingTop = `0px`;
                                        
                                        popupHead.append(popupHeadText);
                                        popupList.append(popupHead);
                                        
                                        modalDiv.append(taskP);
                                        popupList.append(modalDiv);
                                        
                                        hasDiv.append(moreSpan);
                                        hasDiv.append(popupList);
                                        
                                        

                                    } else if(pTitle === titl && thisPop !== null) {
                                        taskP.innerText = text;
                                        
                                        modalDiv.style.paddingTop = `0px`;
        
                                        modalDiv.append(taskP);
                                        thisPop.insertBefore(modalDiv, null);
                                    };
                                });
                            };
                        };
                    };
                    

                } else {
                    let modalDiv = document.createElement('div');
                    let taskP = document.createElement('p');
                    
                    modalDiv.setAttribute('class', 'date_task modal_on_btn');
                    
                    if(sch !== 2) {
                        modalDiv.setAttribute('data-modal', '_taskTable');
                    } else {
                        modalDiv.setAttribute('data-modal', '_schedule_status');
                    };


                    modalDiv.dataset.sch_id = schId;
                    

                    taskP.setAttribute('title', text);
                    
                    if(i === 0) {
                        taskP.innerText = text;
                    };

                    // 데이터 시작, 중간, 끝 날짜 클라스 구분
                    if(i === 0 && betwDateArr.length > 1) {
                        modalDiv.classList.add('start');
                        taskP.innerText = text;
                    } else if (i === leng && betwDateArr.length > 1) {
                        modalDiv.classList.add('end');
                    } else if(i > 0 && i < leng) {
                        modalDiv.classList.add('btw');
                    } else if(i === 0 && betwDateArr.length === 1) {
                        modalDiv.classList.add('day');
                        taskP.innerText = text;
                    };

                    
                    taskP.style.backgroundColor = color;
                
                    modalDiv.append(taskP);
                    hasDiv.append(modalDiv);

                    if(_beDate !== null) {
                        let _leng = _beDate.querySelector('.date_task_wrap');
                        let pTitle = taskP.getAttribute('title');
                        let spanB = _leng.querySelector('.more_popup');
                        
                        if(lengDiv.length !== _title.length) {
                            
                            if(spanB !== null) {
                                let more = spanB.querySelectorAll('.date_task');
                                let thisPop = _thisDate.querySelector('.more_popup');
        
                                more.forEach(it => {
                                    let titl = it.firstElementChild.getAttribute('title');
                                    let list = _leng.querySelectorAll('.date_task');
                                    let listTotal = list.length * 22;

                                    for(let i = 0; i < list.length; i++) {
                                        listTotal += parseInt(list[i].style.paddingTop);
                                    };
                                    if(pTitle === titl && thisPop === null) {

                                        let moreSpan = document.createElement('button');
                                        let popupList = document.createElement('div');
                                        let popupHead = document.createElement('div');
                                        let popupHeadText = document.createElement('p');
                                        let dateText = betwDateArr[i].split('_');
                                        
                                        moreSpan.setAttribute('type', 'button');
                                        moreSpan.setAttribute('class', 'more_popup_btn');
                                        popupList.setAttribute('class', 'more_popup');
                                        taskP.innerText = text;
                                        popupHead.setAttribute('class', 'more_pop_header');
                                        popupHeadText.innerText = dateText[0] + '년 ' + dateText[1] + '월 ' + dateText[2] + '일';
                                        
                                        modalDiv.style.paddingTop = `0px`;
                                        
                                        popupHead.append(popupHeadText);
                                        popupList.append(popupHead);
                                        
                                        modalDiv.append(taskP);
                                        popupList.append(modalDiv);
                                        
                                        hasDiv.append(moreSpan);
                                        hasDiv.append(popupList);

                                       
                                        

                                    } else if(pTitle === titl && thisPop !== null) {
                                        taskP.innerText = text;
                                        
                                        modalDiv.style.paddingTop = `0px`;
        
                                        modalDiv.append(taskP);
                                        thisPop.insertBefore(modalDiv, null);
                                    };
                                });
                            };
                        };
                    } else {
                        let _leng = _thisDate.querySelector('.date_task_wrap');
                        let spanB = _leng.querySelector('.more_popup');

                        spanB.insertBefore(modalDiv, null);
                    };
                };
            };
        };
    };



    function weekStartEnd() {
        let weekDayCell = document.querySelectorAll('.table_body > tr:last-child td');
        weekDayCell.forEach((item, idx) => {
            if(idx === 0 || idx === weekDayCell.length - 1) {
                let list = item.querySelectorAll('.date_task');

                if(list.length !== 0) {

                    list.forEach(it => {
                        let par = it.parentNode;
                        let parClass = par.getAttribute('class');
                        
                        if(parClass !== 'more_popup') {
                            if(idx === 0) {
                                it.classList.remove('btw');
                                it.classList.add('start');
                            } else {
                                it.classList.remove('btw');
                                it.classList.add('end');
                            };
                        };
                    })
                } else {
                    return false;
                }
            };

        });
    };





    function spanMore() {
        const popupBox = document.querySelectorAll('.more_popup');
        const popupBtn = document.querySelectorAll('.more_popup_btn');

        popupBox.forEach(item => {
            let list = item.querySelectorAll('.date_task');
            let leng = list.length;
            let thisBtn = item.previousElementSibling;
            
            thisBtn.innerText = '+' + leng + ' more';
        });

        popupBtn.forEach(i => {
            i.addEventListener('click', () => {
                let _popup = i.nextElementSibling;
                _popup.classList.add('on');
            });
            document.addEventListener('click', (e) => {
                let _popup = i.nextElementSibling;
                let target = e.target;
                let curTarget = e.currentTarget.querySelector('.more_popup');
                
                if(curTarget !== null) {
                    if(target === curTarget || target === i || target === curTarget.childNode){return};
                };

                _popup.classList.remove('on');
            });
        });

    };

    // 날짜 비교
    function dateDiff(_date1, _date2) {
        let diffDate_1 = _date1 instanceof Date ? _date1 : new Date(_date1);
        let diffDate_2 = _date2 instanceof Date ? _date2 :new Date(_date2);
    
        diffDate_1 = new Date(diffDate_1.getFullYear(), diffDate_1.getMonth()+1, diffDate_1.getDate());
        diffDate_2 = new Date(diffDate_2.getFullYear(), diffDate_2.getMonth()+1, diffDate_2.getDate());
    
        let diff = Math.abs(diffDate_2.getTime() - diffDate_1.getTime());
        diff = Math.ceil(diff / (1000 * 3600 * 24));
    
        return diff;
    };

    // 날짜 사이에 날짜들 반환
    function getDatesStartToLast(startDate, lastDate) {
        let regex = RegExp(/^\d{4}-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])$/);
        if(!(regex.test(startDate) && regex.test(lastDate))) return "Not Date Format";
        let result = [];
        let curDate = new Date(startDate);
        while(curDate <= new Date(lastDate)) {
            result.push(curDate.toISOString().split("T")[0]);
            curDate.setDate(curDate.getDate() + 1);
        }
        return result;
    }


    // 날짜 문자열 변환
    function dateIdChange(date) {
        let chDate = date.replace(/_/gi, '-');
        let str = chDate.split('-');
        
        if(str[1].length === 1 || str[2].length === 1) {
            for(let i = 1; i < 3; i++) {
                if(str[i].length === 1) {
                    str[i] = '0' + str[i];
                };
            };

            chDate = str[0] + '-' + str[1] + '-' + str[2];

            return chDate;
        } else {
            return chDate;
        };
    };


    // 날짜 문자열 변환 반대
    function dateIdChange2(date) {
        let chDate = date.replace(/-/gi, '_');
        let str = chDate.split('_');
        
        if(str[1].indexOf('0') === 0 || str[2].indexOf('0') === 0 ) {
            for(let i = 1; i < 3; i++) {
                
                if(str[i].indexOf('0') === 0) {
                    str[i] = str[i].replace('0', '');
                };
            };
            
            chDate = str[0] + '_' + str[1] + '_' + str[2];
            
            return chDate;
        } else {
            return chDate;
        };
    };


    // 전체 함수 실행 함수
    function dataChangeCalendar(data) {
        // 캘린더 생성
        _calendar();

        // 오늘 날짜 표시
        todayColor();

        // 이전 버튼 클릭시, 이전달 이동. 다음 버튼 클릭시, 다음달 이동
        beforeBtn.addEventListener('click', () => {beforeMonth(data)});
        afterBtn.addEventListener('click', () => {afterMonth(data)});

        // 첫 로딩시 데이터 띄우기
        taskE(data);
        spanMore();
        weekStartEnd();
        // posChange();

        // 모달
        md5();
    };
    




    // let testData1 = new TestData('2021_12_9', 'text1', 'red', '2021_12_15', 2);
    // let testData2 = new TestData('2021_12_10', 'text2', 'blue', '2021_12_12', 2);
    // let testData3 = new TestData('2021_12_8', 'text3', 'green', '2021_12_10', 2);
    // let testData4 = new TestData('2021_12_10', 'text4', '#FF5858', '2021_12_20', 2);
    // let testData5 = new TestData('2021_12_11', 'text5', '#788DEE', '2021_12_17', 2);
    // let testData6 = new TestData('2021_12_13', 'text6', 'orange', '2021_12_12', 2);
    // let testData7 = new TestData('2021_12_10', 'text7', 'lightblue', '2021_12_13', 2);
    // let testData8 = new TestData('2021_12_13', 'text8', 'lightseagreen', '2021_12_19', 2);
    // let testData9 = new TestData('2021_12_12', 'text9', 'lightseagreen', '2021_12_31', 2);
    // let testData10 = new TestData('2021_12_1', 'text10', 'lightseagreen', '2021_12_9', 2);
    // let testData11 = new TestData('2021_12_8', 'text11', 'lightseagreen', '2021_12_11', 2);
    // let testData12 = new TestData('2021_12_19', 'text12', 'lightseagreen', '2021_12_30', 2);

    // let datArr = [testData1, testData2, testData3, testData4, testData5, testData6, testData7, testData8, testData9, testData10, testData11, testData12];


    // dataChangeCalendar(datArr);
	
	console.log(dateData , "dateData")
	
    // 전체 함수 실행
    dataChangeCalendar(dateData);

    function onClickSchedule(e){
        let curTarget = e.currentTarget;
        console.log(curTarget);
        let id = curTarget.dataset.id;
        console.log(id);
    }

    function selectSchedule(sch_id){
        
        console.log(sch_id , "sch_id")

        $.ajax({
            url: `/schedule/${sch_id}/month`,
            type: "GET",
            success: function (data) {
                console.log(data);

                if(data.type == 1){
                    let item = data.schedule;

                    $('#sch_id_modal').val(item.sch_id);
                    $('#new_sch_title_modal').val(item.sch_title);
                    $('#new_start_date_modal').val(item.start_date);
                    $('#new_end_date_modal').val(item.end_date);
                    $('#new_sch_contents_modal').val(item.sch_contents);
                }
                else if(data.type == 2){
                    $('.schedule_cont_box').children().remove();

                    console.log(data , "data");

                    let item = data.plan.schedule;

                    let geneticList = []
                    if(data.plan.plan.usee_AT_CODE == 0) {
                        data.plan.geneticData.map(item => {

                            item.geneticList.map(genetic => {
                                //console.log(genetic , "genetic")
                                geneticList.push(`${genetic.parents_name}(${genetic.genetic})`);
                            })
                        })
                    }
                    else if(data.plan.plan.usee_AT_CODE != 0){
                        console.log(data.plan)
                        console.log(`${data.plan.plan.genetic_depth_1}(${data.plan.genetic_depth_2})`,"genetic")
                        geneticList.push(`${data.plan.plan.genetic_depth_1}(${data.plan.plan.genetic_depth_2})`);
                    }

                    $('#analysis_title').val(data.plan.plan.plan_code+" 시료 분석 결과 입력");
                    $('#analysis_date').val(data.schedule.start_date+" ~ "+data.schedule.end_date);
                    $('#analysis_item').val(data.plan.plan.division_depth_4);
                    $('#analysis_genetic').val(geneticList);
                    $('.analysis_move_btn').attr('onclick' , 'location.href="/analysis/sample_analysis_detail?plan_id='+data.schedule.plan_id+'&regist_type='+data.registType+'"');
                    for(let i = 0; i<item.length; i++){

                        let batchManager = `<li class="schedule_cont_list">
                            				<div class="cont_list_cont">
                              			 		<span class="first_list_title list_title list_cont_text">배치 ${i+1}</span>
                              			 		<span class="first_list_title list_cont list_cont_text first_list_cont"><button type="button" class="btn_cont">배치</button></span>
                            				</div>
                            			`
                        let mainManager = '';
                        let subManager = '';

                        item[i].managerList.map( manager =>{

                            if(manager.mgr_type == 1){
                                mainManager += `<div class="cont_list_cont">
	                                				<span class="list_title list_cont_text">담당자(정)</span>
	                               					<span class="list_cont list_cont_text">${manager.user_name}</span>
	                           					</div>
	                           					`
                            } else if (manager.mgr_type == 2){
                                subManager += `<div class="cont_list_cont">
	                                				<span class="list_title list_cont_text">담당자(부)</span>
	                               					<span class="list_cont list_cont_text">${manager.user_name}</span>
	                           					</div>
	                           					`
                            }
                        })

                        batchManager += mainManager + subManager + `</li>`
                        $('.schedule_cont_box').append(batchManager);
                    }

                }
            }
        });
    }
});