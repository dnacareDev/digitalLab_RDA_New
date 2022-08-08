let batchData = null;
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

                if(!lookupModalId){
                    return;
                }
        
                _has = lookupModalId.classList.contains('on');
            
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
                    const target = e.currentTarget;           
                    const schId = target.dataset.sch_id;         
                    main.classList.add('modal_on');
                    lookupModalId.dataset.sch_id = schId;
                    lookupModalId.dataset.plan_id = target.dataset.plan_id;
                    lookupModalId.classList.add('on');
                    
                    selectSchedule(schId);
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

    let FilColorNum = [0, 0, 1, 1, 2, 2, 3, 3, 4, 4, 5, 5, 2, 2, 1, 1, 5, 5, 2, 0, 3, 3, 1, 3, 2, 2, 1];
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

    // for(let i = 1; i <= 27; i++) {
    
    //     eval('let data' + i + ' = new TestData(SetThisDate' + i + ', SetThisText' + i + ', FilColorNum['+ i +'], lastThisDate'+ i +'); dateData.push(data' + i + ');');
    // };




     function DateToText(date, text, isCut){
         const dateObj = new Date(date);      
         const year = dateObj.getFullYear();
         const month = dateObj.getMonth()+1 >= 10 
         ? dateObj.getMonth()+1
         : "0" + (dateObj.getMonth()+1);
         const day =  dateObj.getDate()+1 >= 10 
         ? dateObj.getDate()+1
         : "0" + (dateObj.getDate()+1);
        
         return (`${isCut ? ("" + year).slice(2,4) : year}${text}${month}${text}${day}`)
     }
     function renderLookupModal(schId){
         console.log(schId , "schId")
         if (!schId) return;
         const url = `/analyasis/sample-schedule`;
         console.log(url)
         $.ajax({
             url,
            type : "GET",
             dataType : "JSON",
             async: false,
             data : {
                 "sch_id" : schId
            },
             success : function(result)
             {
	
				planData = result;
				// plate check
				plan_well = result.plan.plan_well == 1 ? true : false;
				
				console.log(result , "result")
				
                 const { geneticData, plan, batch, schedule } = result;
                // 분석 정보
                 document.querySelector("#plan_note_number").innerText = plan?.plan_code;
                 /*
                 if (plan.seedList) {
	
                     // console.log(plan.seedList[0],plan.seedList[0].prj_dtl_no ,plan.seedList[0].report_name)
                     document.querySelector("#plan_note_code").value = `${plan.seedList[0].prj_dtl_no} - ${plan.seedList[0].prj_nm}` ;
                 }
                 
                 document.querySelector("#plan_note_item").value = plan?.division_depth_4 ?? "";
                 document.querySelector("#plan_note_method").value = plan?.division_depth_4 ?? "";
                 document.querySelector("#plan_note_seed_count").value = plan?.seed_ammount;
                 document.querySelector("#plan_note_cycle").value = plan?.plan_cycle;
                 document.querySelector("#plan_note_date").value = plan?.create_date ? DateToText(plan?.create_date, ".") : "";
                 document.querySelector("#plan_note_isWell").value = plan?.plan_well ? "Y" : "N";
                 document.querySelector("#plan_note_batchCount").value = plan?.plan_batch ?? 0;
                 document.querySelector("#plan_note_sampleCount").value = plan?.plan_sample ?? 0; 
                 document.querySelector("#plan_note_blank").value = plan?.plan_blank ?? 0;
                 document.querySelector("#plan_note_standard").value = plan?.plan_standard ?? 0;
                 document.querySelector("#plan_note_control").value = plan?.plan_control ?? 0;
                 document.querySelector("#plan_comment").value = plan?.plan_contents ?? "";
                 */
                 
                 document.querySelector("#plan_note_batchCount").innerText = plan?.plan_batch ?? 0;
                 document.querySelector("#plan_note_sampleCount").innerText = plan?.plan_sample ?? 0; 
                 document.querySelector("#plan_note_blank").innerText = plan?.plan_blank ?? 0;
                 document.querySelector("#plan_note_standard").innerText = plan?.plan_standard ?? 0;
                 document.querySelector("#plan_note_control").innerText = plan?.plan_control ?? 0;
                 document.querySelector("#plan_comment").innerText = plan?.plan_contents ?? "";
                 
                 //품종 리스트
                 const geneticBox = document.querySelector('#note_info_list');
                 let htmlStrings = "";
                 let arr = [];
                 if (Array.isArray(geneticData)) {
                     /* geneticData.forEach((data, sampleIndex) => {
						
						console.log(plan.division_depth_4 , "division")
						console.log(data , "분석데이터") */
						
						console.log(planData.plan.seedList , "seedList")
						
						const geneticArr = [];
						
						for (let i of planData.plan.seedList) {
				            let seed = {
				                kind: planData.plan.division_depth_4,
				                id: i.seed_code,
				                amount: i.seed_amount,
				                name: i.genetic_depth1,
				                gene: i.genetic_depth2,
				                each: i.each
				            }
				            geneticArr.push(seed);
				        }
				        
				        geneticArr.map(item => {
				            const htmlData = `
				            <ul class="analysis_data">
                            	<li class="analysis_data_list">
                            		<span class="list_th">분석항목</span>
                             		<span class="list_td">${item.kind}</span>
                                </li>
                                <li class="analysis_data_list">
                                    <span class="list_th">시료ID</span>
                                    <span class="list_td">${item.id}</span>
                                </li>
                                <li class="analysis_data_list">
                                  	<span class="list_th">시료량</span>
                                    <span class="list_td">${item.amount} ${item.each}</span>
                                </li>
                                <li class="analysis_data_list">
                                    <span class="list_th">작목명</span>
                                    <span class="list_td">${item.name}</span>
                                </li>
                                <li class="analysis_data_list">
                                    <span class="list_th">품종명/유전자명</span>
                                    <span class="list_td">${item.gene}</span>
                                </li>
                           </ul>
				        `
				        htmlStrings += htmlData;
				        
				        })
					
                         /*let list = "";
                         const checkDupli = [];
                         data.geneticList.forEach((el, index) => {
                             if (checkDupli.some(check => check === el?.genetic)) {
                                 return;
                             } else {
                                 checkDupli.push(el.genetic)
                                 list += `<div>${el.genetic}</div>`;                               
                            }                    
                         });
                        
                        const html = `
                           	<li class="info_list"
                               <div class="info_list_head">${data?.parent_name}</div>                
                                ${list}
                             </li>
                        `;
                        */
                        
                       /* arr = arr.concat(data.geneticList); 
                    	}) */
                    	
                     geneticBox.innerHTML = htmlStrings;
                 }
             }
         })
     }









    ////// calendar 기능
    const taskSelectBox = document.querySelector('.date_box');
    const calendar = document.getElementById('calendar');
    const beforeBtn = document.querySelector('.before_btn');
    const afterBtn = document.querySelector('.after_btn');
    const day = new Date();

    let year = day.getFullYear();
    let month = day.getMonth();
    let YM = year + '년 ' + (month + 1) + '월';
    let firstDate = new Date(year,month,1).getDate();   
    let lastDate = new Date(year,month + 1,0).getDate();   
    let firstDay = new Date(year,month,1).getDay();   

    document.querySelector('.ym').innerHTML = YM;
    
    // 캘린더 함수
    function _calendar() {
        let row = calendar.insertRow();
        let _beforeDate = firstDay - 1;
        let afterDate = 1;
        
        for(let i = 0; i < firstDay; i++) {

            let cell = row.insertCell();
            
            // 이전달 표시 및 클라스 적용
            let _beforeMonDate = new Date(year,month,0).getDate(); 
            let beDate = _beforeMonDate - _beforeDate;
            cell.innerHTML = '<span class="date_day_num">'+ beDate +'</span>';
            cell.setAttribute('class', 'beforeMonth');

            if(month === 0) {
                cell.setAttribute('id', `${year - 1}_${12}_${beDate}`);
            } else {
                cell.setAttribute('id', `${year}_${month}_${beDate}`);
            };

            _beforeDate = _beforeDate - 1;
        };  
        
        const _beforeMonLeng = document.querySelectorAll('.beforeMonth').length;
        let forLeng = 42 - _beforeMonLeng;

        for(let i = 1; i <= forLeng; i++) {
            if(i <= lastDate) {
                if(firstDay != 7) {
                    let cell = row.insertCell();
                    cell.setAttribute('id', `${year}_${month + 1}_` + i);
                    cell.setAttribute('class', 'cal_cell');
                    cell.innerHTML = `<span class="date_num">${i}</span>`;
                    firstDay += 1;
                } else {
                    row = calendar.insertRow();
                    let cell = row.insertCell();
                    cell.setAttribute('id', `${year}_${month + 1}_` + i);
                    cell.setAttribute('class', 'cal_cell');
                    cell.innerHTML = `<span class="date_num">${i}</span>`;
                    firstDay = firstDay - 6;
                };
              
            } else {
                if(firstDay != 7) {
                    let cell = row.insertCell();
                    cell.setAttribute('class', 'afterMonth');

                    if(month === 11) {
                        cell.setAttribute('id', `${year + 1}_${1}_${afterDate}`);
                    } else {
                        cell.setAttribute('id', `${year}_${month + 2}_${afterDate}`);
                    };

                    cell.innerHTML = '<span class="date_day_num">'+ afterDate +'</span>';
                    afterDate += 1;
                    firstDay += 1;
                } else {
                    row = calendar.insertRow();
                    let cell = row.insertCell();
                    cell.setAttribute('class', 'afterMonth');

                    if(month === 11) {
                        cell.setAttribute('id', `${year + 1}_${1}_${afterDate}`);
                    } else {
                        cell.setAttribute('id', `${year}_${month + 2}_${afterDate}`);
                    };

                    cell.innerHTML = '<span class="date_day_num">'+ afterDate +'</span>';
                    afterDate += 1;
                    firstDay = firstDay - 6;
                };
            };
        };
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

        let textA = a.schId;
        let textB = b.schId;
        
        let spTextA = textA;
        let spTextB = textB;

        //let numTextA = parseInt(spTextA[1]);
        //let numTextB = parseInt(spTextB[1]);
        let numTextA = spTextA;
        let numTextB = spTextB;


        if(dateA === dateB) {
            return numTextA > numTextB ? 1 : -1;
        } else {
            return dateA > dateB ? 1 : -1;
        };
    };

    let todayDate = day.getDate();

    // 전달 표시 함수
    function beforeMonth(_dt) {
        while(calendar.rows.length > 2) {
            calendar.deleteRow(calendar.rows.length - 1);
        };

        month = month - 1;

        if(month === -1) {
            year = year - 1;
            month = month + 12;
        };

        YM = year + '년 ' + (month + 1) + '월';
        document.querySelector('.ym').innerHTML = YM;

        firstDate = new Date(year,month,1).getDate();   
        lastDate = new Date(year,month + 1,0).getDate();   
        firstDay = new Date(year,month,1).getDay(); 

        // 캘린더 생성 함수
        _calendar();

        // 데이터 띄우기 함수
        taskE(_dt);
        spanMore();

        // 모달함수
        md5();

        if(day.getMonth() === month && day.getFullYear() === year) {todayColor()};
    };

    // 다음달 표시 함수
    function afterMonth(_dt) {
        while(calendar.rows.length > 2) {
            calendar.deleteRow(calendar.rows.length - 1);
        };

        month = month + 1;

        if(month === 12) {
            year = year + 1;
            month = 0;
        };

        YM = year + '년 ' + (month + 1) + '월';
        document.querySelector('.ym').innerHTML = YM;

        firstDate = new Date(year,month,1).getDate();   
        lastDate = new Date(year,month + 1,0).getDate();   
        firstDay = new Date(year,month,1).getDay(); 

        // 캘린더 생성 함수
        _calendar();

        // 데이터 띄우기 함수
        taskE(_dt);
        spanMore();

        // 모달함수
        md5();

        if(day.getMonth() === month && day.getFullYear() === year) {todayColor()};
    };

    // 오늘날짜 색 표시 함수
    function todayColor() {
        for(let i = 1; i <= lastDate; i++) {
            let getId = document.getElementById(`${year}_${month + 1}_` + i);
            let IdVal = getId.getAttribute('id').split('_');
            if(IdVal[2] == todayDate) {
                getId.querySelector('span').classList.add('to_day');
                // getId.style.background = '#6875E9';
                // getId.style.color = '#fff';
            };
        };
    };

    // 캘린더에 데이터 적용하는 함수
    function dataApennd(date1, date2, text, color, sch, schId, planId) {
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
                                        if(leng.length === 0) {
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
                let lengDiv = _thisDate.querySelectorAll('.date_task');
                let popupBox = _thisDate.querySelector('.more_popup');
                
                if(hasDiv === null && lengDiv.length == 0) {
                    _thisDate.querySelector('span').remove();
                    
                    let daySpan = document.createElement('span');
                    let wrapDiv = document.createElement('div');
                    let modalDiv = document.createElement('div');
                    let taskP = document.createElement('p');
                    
                    daySpan.setAttribute('class', 'date_day_num');
                    wrapDiv.setAttribute('class', 'date_task_wrap');
                    modalDiv.setAttribute('class', 'date_task modal_on_btn');
                    modalDiv.dataset.sch_id = schId
                    modalDiv.dataset.plan_id = planId
                    
                    modalDiv.setAttribute('data-modal', '_taskTable2');

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
                                let prPadd = item[0].style.paddingTop.split('p');
                                let numPaddin = parseInt(prPadd[0]);

                                if(_title[4] !== undefined) {
                                    modalDiv.style.padding = (22 * (item[2]) + 1) + 'px 0 0 0';
                                } else if(numPaddin > 0) {
                                    modalDiv.style.paddingTop = numPaddin + 'px';
                                } else {
                                    let paI = [];
                                    _leng.forEach((i, idx) => {
                                        let _prPadd = i.style.paddingTop.split('p');
                                        let _numPaddin = parseInt(_prPadd[0]);
                                        if(_numPaddin > 0) {paI.push(idx)};
                                    });
                                    if(paI.length !== 0) {
                                        modalDiv.style.padding = (22 * (item[2] + 1)) + 'px 0 0 0';
                                    } else {
                                        modalDiv.style.padding = (22 * item[2]) + 'px 0 0 0';
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

                    // if(_beDate !== null)
    
    
                } else if(lengDiv.length >= 4) {
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
                        
                        modalDiv.setAttribute('data-modal', '_taskTable2');
                        
                        modalDiv.setAttribute('data-sch_id', schId ?? 0)
                        modalDiv.setAttribute('data-plan_id', planId ?? 0)
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
                        modalDiv.setAttribute('data-sch_id', schId ?? 0)
                        modalDiv.setAttribute('data-plan_id', planId ?? 0)

                        modalDiv.setAttribute('data-modal', '_taskTable2');

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
    
                } else if(lengDiv.length <= 4) {
                    let modalDiv = document.createElement('div');
                    let taskP = document.createElement('p');
                    
                    modalDiv.setAttribute('class', 'date_task modal_on_btn');
                    modalDiv.setAttribute('data-sch_id', schId ?? 0)
                    modalDiv.setAttribute('data-plan_id', planId ?? 0)

                    modalDiv.setAttribute('data-modal', '_taskTable2');

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
                                            
                                            if(_title[4] !== false) {
                                                modalDiv.style.padding = (22 * (item[2] + 2)) + 'px 0 0 0';
                                            };
                                        } else if(numPaddin > 0) {
                                            modalDiv.style.paddingTop = numPaddin + 'px';
                                        } else {
                                            lengDiv.forEach(items => {
                                                let _prPadd = items.style.paddingTop.split('p');
                                                let _pNum = parseInt(_prPadd[0]);
                                                if(_pNum === 0 || _pNum === '') {
                                                    modalDiv.style.padding = (22 * (item[2] - lengDiv.length)) + 'px 0 0 0';
                                                    
                                                    return false;
                                                };
                                            });
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
        
                                more.forEach(it => {
                                    let titl = it.firstElementChild.getAttribute('title');
                                    
                                    if(pTitle === titl) {
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
                                    };
                                });
                            };
                        };
                    };




                };
            };
        };
    };

    // more 클릭시, 팝업 on, off 기능
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
        console.log(data);
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
        // posChange();

        // 모달
        md5();
    };

    function selectSchedule(sch_id){
		
		console.log(sch_id , "sch_id")
		
		$.ajax({
			url : `/schedule/${sch_id}/month`,
			type : "GET",
			success : function(data){
				
				$('.place_date_list_box').children().remove();			
				$('.step_list_box').children().remove();				
				$('#plate_list').children().remove();				
				$('#plate_list2').children().remove();
				
					item = data.plan;

					let batch = '';
					let step = '';
					let plate = '';
					let plate2 = '';	
					
					console.log(data, "data")
					console.log(data.plan, "plan")
					
					/*
					for(let i of item.plate){
						batch += `<li class="place_date_list">
								<span class="batch_name">Batch ${i.plate_index} : </span>
								<span class="place_date1">${i.start_date}</span> ~ 
								<span class="place_date2">${i.end_date}</span>
								</li>`
					}
					
					for(let i of item.step){
						step += `<li data-step ="${i.step_id}" data-name="${i.step_name}" data-index="${i.step_index}"
								class="step_list">Step ${i.step_index}</li>`
					}
					
					for(let i of item.plate) {
                        plate += `<li class="setting_top_list ${i.plate_index == 1 ? 'active' : ''}" data-grid="plateGrid_${i.plate_index}"
						          data-plate="${i.plate_id}" data-setting="Batch_${i.plate_index}"
						          onclick="onClickBatchTabBtn(event)">Batch #${i.plate_index}</li>`

                        plate2 += `<div class="sample_analysis_box bottom_setting"
									data-grid="plateGrid_${i.plate_index}" id="Batch_${i.plate_index}" ${i.plate_index == 1 ? 'style="display: block"' : ''}>
                                    <div id="plateGrid_${i.plate_index}" data-plate="${i.plate_id}" ></div>
                                </div>`
                    }
                    */
                    
                    for(let i of item.plate) {

                        plate2 += `<div class="sample_analysis_box bottom_setting"
									data-grid="plateGrid_${i.plate_index}" id="Batch_${i.plate_index}" ${i.plate_index == 1 ? 'style="display: block"' : ''}>
                                    <div id="plateGrid_${i.plate_index}" data-plate="${i.plate_id}" ></div>
                                </div>`
                    }
					
					/*
					$('.place_date_list_box').append(batch);
					$('.step_list_box').append(step);			
					$('#plate_list').append(plate);	
					*/		
					$('#plate_list2').append(plate2);	
					
				 	result_step = data.plan.step;
				 	plateData = data.plan;
				 	sampleArr = [];
                    gridMap = new Object();
                    batchData = data.batch;
                    
                    createGrid("plateGrid_1");
                    renderLookupModal(sch_id);
			}
		}).then(() =>{

		})
	}
   
    let dataAr = [];
	
	 if(data != null) {
         for (let i = 0; i < data.length; i++) {
			
			let color = FilColor[colorIndex];
            try {
                let calender = new TestData(dateIdChange2(data[i].start_date),data[i].name+"("+data[i]?.seedList[0].prj_dtl_no+") - "+data[i]?.seedList[0].prj_nm , color ,dateIdChange2(data[i].end_date), data[i].sch_type, data[i].sch_id, data[i].plan_id);
                dataAr.push(calender);
            }catch (e){
                console.log("undefined catch");
                continue;
            }finally{
                colorIndex++;
            }

        if(colorIndex == 6){
	 		colorIndex = 0;
         }
   	  }
	}
	
    dataChangeCalendar(dataAr);

});

