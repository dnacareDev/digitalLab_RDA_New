let getStepData = null;
let resetStepsNumber = null;
let setStepData = null;

document.addEventListener('DOMContentLoaded', () => {
    const dataAddBtn = document.querySelector('.set_add_btn');
    const dataReBtn = document.querySelector('.set_remove_btn');
    const dataBox = document.querySelector('.setting_bottom_box');
    const protocolTextarea = document.querySelector('#method_protocol');
    const stepTopList = document.querySelectorAll('.setting_top_list');
    const bottomSet = document.querySelectorAll('.bottom_setting');
    const addBtn = document.querySelectorAll('.setting_top_add_btn');
    let rightDataBox = document.querySelector('.setting_area_list_box');
    let stepContainer = document.getElementById('dragSettingArea');
    // modal 기능
    function md() {
        let lookUpBtn = document.querySelectorAll('.modal_on_btn');
        const main = document.getElementById('main');
        const modalCloseBtn = document.querySelectorAll('.modal_close');

        lookUpBtn.forEach((item) => {
            let _has = item.classList.contains('modal_on_btn');
            let _id = item.getAttribute('data-modal');

            if(_id !== '' && _has) {
            let lookupModalId = document.getElementById(_id);
            let modalBox = lookupModalId.querySelectorAll('.modal_box');
            let _has = lookupModalId.classList.contains('on');

            lookupModalId.addEventListener('click', (e) => {
                let target = e.target;
                if(target.classList.contains('modal') === false) {return false};

                lookupModalId.classList.remove('on');
                main.classList.remove('modal_on');

                return false;
            });

            item.addEventListener('click', (e) => {
                e.preventDefault();
                main.classList.add('modal_on');
                lookupModalId.classList.add('on');
                modalBox[0].classList.add('on')
                if(modalBox.length > 1) {
                let _tab = item.getAttribute('data-add');

                modalBox.forEach(i => {
                    let dataTab = i.getAttribute('data-tab');

                    if(dataTab === _tab) {
                        modalBox.forEach(i => {
                            i.classList.remove('on')
                        });
                        i.classList.add('on');
                    };
                });
                    return false;
                };
            });

            modalCloseBtn.forEach((item) => {
                item.addEventListener('click', () => {
                    lookupModalId.classList.remove('on');
                    main.classList.remove('modal_on');
                });
            });
            } else {
                return false;
            };
        });
    };
    md();

   getStepData = async (result) => {
		
        //let result;
        
        /*$.ajax({
            url : `/api/method/step/${id ?? 0}`,
            type : "GET",
            async: false,
            success : function(list){
	
                console.log(list, "79line : 스텝 리스트")
                result = list;
                return list;
            },
            error: (err) => {
                console.error(err);
                return [];
            }
        })
        */

        const settingBottomBox = document.querySelector('.setting_bottom_box');
        result.forEach(data => {
            const stepType = data.step_type;
            
            console.log(data , "data")
            
            switch(stepType) {
                case 1: { //시료량
                    const container = settingBottomBox.querySelector('#sample');
                    const id = data.seed_id
                    const checkboxList = container.querySelectorAll('.set_list');
                    checkboxList.forEach(checkbox => {
                        const checkboxId = checkbox.dataset.id;
                        
                        if (checkboxId == id) {
                            checkbox.querySelector('.checkbox').checked = true;
                            return;
                        }
                    })
                   break
                }
                case 2: { //시약
                	
                    const container = settingBottomBox.querySelector('#reagent');
                    const id = data.reagent_id
                    const checkboxList = container.querySelectorAll('.set_list');
                    checkboxList.forEach(checkbox => {
                        const checkboxId = checkbox.dataset.id;
                        
                        if (checkboxId == id) {
                            checkbox.querySelector('.checkbox').checked = true;
                            return;
                        }
                    })

                   break
                }
                case 3: { // 장비
                    const container = settingBottomBox.querySelector('#equipment');
                    const id = data.equipment_id
                    const checkboxList = container.querySelectorAll('.set_list');
                    checkboxList.forEach(checkbox => {
                        const checkboxId = checkbox.dataset.id;
                        if (checkboxId == id) {
                            checkbox.querySelector('.checkbox').checked = true;
                            return;
                        }
                    })

                   break
                }
                case 4: { // 기구
                    const container = settingBottomBox.querySelector('#instrument');
                    const id = data.instrument_id
                    const checkboxList = container.querySelectorAll('.set_list');
                    checkboxList.forEach(checkbox => {
                        const checkboxId = checkbox.dataset.id;
                        if (checkboxId == id) {
                            checkbox.querySelector('.checkbox').checked = true;
                            return;
                        }
                    })

                   break

                }
                case 5: { // 기타
                    // const container = settingBottomBox.querySelector('#etc');
                    // const id = data.instrument_id
                    // const checkboxList = container.querySelectorAll('.set_list');
                    // checkboxList.forEach(checkbox => {
                    //     const checkboxId = checkbox.dataset.id;
                    //     if (checkboxId == id) {
                    //         checkbox.querySelector('.checkbox').checked = true;
                    //     }
                    // })
                   break

                }
                default:
                   break
            }
            
	        rightSwichFuc2();
        })
		
        return result;
    } 
    
    resetStepsNumber = () => {
		
        const steps = stepContainer.querySelectorAll('.set_step');
        let detailArr = [];

		steps.forEach((el, i) => {
            let has = el.innerText.indexOf('-');

            if(has < 0) {
                detailArr.push(i);
                el.innerText = `step ${detailArr.length}`;
            } else if(has > 0) {
                let detailList = el.innerText.split('-');
                el.innerText = `step ${detailArr.length}-${detailList[1]}`;
            }
		})
    }
	
	let step_index = 0;
	
   	setStepData = (result) => {
		
		console.log(result , "207line stepdata")
		
        const list = stepContainer.querySelectorAll('.setting_area_list');
        
        list.forEach((item, idx) => {
	
			let group_index = item.querySelectorAll('.set_area_inner')[0].dataset.group;
			
			// 닉네임 셋팅
			const nickname = item.querySelector('.top_set_input input');
			nickname.value = result[idx]?.step_nickName??'';
			
			// 커멘트 셋팅
            const comment = item.querySelector('textarea');
            comment.value = result[idx]?.step_comment??'';

            // 인풋 데이터 셋팅
            const input = item.querySelectorAll('.set_select_box input');
            const selectBox = item.querySelectorAll('.set_select_box .common_select');
            result[idx].inputList.forEach((el,index) => {
				
				// 인풋 셀렉박스 셋팅
				if(item.step_type != 4){
					
					for(let i = 0; i<selectBox[index].options.length; i++){
						
						let option = selectBox[index].options[i];
						
						if(option.value == el.each_id){
							
							option.selected = true;
						}
					}
				}
				
                input[index].value = el.input;
            })
            
           	let stepNum = Number(item.querySelectorAll('.set_area_inner')[0].dataset.group)+1
            let main_step_num = stepNum
            result[idx].sub_step_list.forEach((sub , index) => {
        	    
        	    // 주석
                $.ajax({
                	url : `/api/method/each/${sub.step_type}`,
                    type : "GET",
                    async:false,
                    success : function(data){
                    	
                   	let data_id = '';
        	    
	        	    switch (result[idx].step_type){
	        	    	case 1: data_id = result[idx].seed_id
	        	    	break;
	        	    	case 2: data_id = result[idx].reagent_id
	        	    	break;
	        	    	case 3: data_id = result[idx].equipment_id
	        	    	break;
	        	    	case 4: data_id = result[idx].instrument_id
	        	    	break;
	        	    }
	        	    
	        	    console.log(sub , "sub")
	        	    
	        	    const options = data
	                        
					let optList = `<option value="-1" hidden></option>`;
	                // 주석
					options.forEach(opt => {
						
						let html = `<option value="${opt.each_id}" ${opt.each_title === 1 ? "disabled class='title'" : ""}>${opt.each}</option>`
						
						optList += html;
					})
	                
	                const unitSelect = `
	                        <div class="set_select_box">
	                            <span></span>
	                            <div class="set_area_wrap">
	                                ${sub.step_type == 4 || sub.step_type == 5 
	                                    ? `<input type="text" name="" id="">
	                                    <select name="" id="" class="common_select">
                                    	</select>
	                                    `
	                                    : 
	                                    `
	                                    <input type="text" name="" id="">
	                                    <select name="" id="" class="common_select">
	                                        ${optList}
	                                    </select>
	                                    `
	                                }                                
	                            </div>
	                        </div>`;       
	                        
	        	    
	        	    let sub_div = document.createElement('div');
	        	    sub_div.setAttribute('class', 'setting_area_list');
	        	    
	        	    let html = `<div class="set_area_inner" data-type="${sub.step_type}" data-id="${data_id}" data-num="${index+1}" data-group=${group_index} data-sub=${index+1}>
	                                    <div class="top_set_area">
	                                        <label for="" class="check_wrap">
	                                            <input type="checkbox" name="" id="" class="checkbox">
	                                        </label>
	                                        <span class="set_step">step ${main_step_num}-${index+1}</span>
	                                        <p class="top_set_title">${sub.step_name}</p>
	                                        <div class="top_set_input">
	                                            <label for="">
	                                                <input type="text" value="${sub?.step_nickName??''}" id="">
	                                            </label>
	                                        </div>
	                                    </div>
	                                    <div class="bottom_set_area">
	                                        <div class="bottom_set_select">
	                                     		${Array(5).fill().map((v) => unitSelect).join("")}
	                                        </div>
	                                        <div class="set_textarea_box">
	                                            <label for="">
	                                                <textarea name="" id="" placeholder="Comment">${sub.step_comment != null ? sub.step_comment : ''}</textarea>
	                                            </label>
	                                        </div>
	                                    </div>
	                                </div>`;
	                
	                stepNum++;
	                
	                sub_div.innerHTML = html;  
	                
	                // 인풋 데이터 셋팅
		            const input = sub_div.querySelectorAll('.set_select_box input');
		            const selectBox = sub_div.querySelectorAll('.set_select_box .common_select');
		            sub.inputList.forEach((el,index) => {
						
						// 인풋 셀렉박스 셋팅
						if(sub_div.step_type != 4){
							
							for(let i = 0; i<selectBox[index].options.length; i++){
								
								let option = selectBox[index].options[i];
								
								if(option.value == el.each_id){
									
									option.selected = true;
								}
							}
						}
						
		                input[index].value = el.input;
		            })
	                
	                item.parentNode.append(sub_div)
	                
	                const selects = document.querySelectorAll('.set_area_inner select');
	                        const textArea = document.querySelectorAll('.set_area_inner textarea');
	                        selects.forEach(select => {
	                            select.addEventListener('change', (e) => {
	                                renderProtocol()
	                            })
	                        })
	                        textArea.forEach(input => {
	                            input.addEventListener('change', (e) => {
	                                renderProtocol()
	                            })
	                        })
	                        // textArea.addEventListener('change', renderProtocol)
	
	                        let title = sub_div.querySelector('.top_set_title');
	                        title.setAttribute('title', title.innerText);
	
	                        title.addEventListener('click', (e) => {
	                            let target = e.target;
	                            let list = target.parentNode.parentNode.parentNode;
	                            let has = list.classList.contains('on');
	
	                            let onHas = has ? list.classList.remove('on') : list.classList.add('on');
	                        });
	                    	
	                    }
	                })
        	    
 			})
 			
            item.querySelectorAll('.set_area_inner')[0].dataset.num = stepNum - 1;
        })
        
        renderProtocol();
    }
    
    if (document.location.pathname.includes("method-detail")) {
		
		 $.ajax({
            url : `/api/method/step/${method_id ?? 0}`,
            type : "GET",
            async: false,
            success : function(list){
				setTimeout(() => getStepData(list) , 300);
       			setTimeout(() => resetStepsNumber(), 1000);
       			setTimeout(() => setStepData(list), 1500);
            },
            error: (err) => {
                console.error(err);
            }
		})
    }

    // 탭 메뉴
    stepTopList.forEach((item, idx) => {
        let has = item.classList.contains('active');

        if(has) {
            let setId = item.getAttribute('data-setting');

            bottomSet.forEach((items) => {
                let _id = items.getAttribute('id');

                if(_id == setId) {
                    let showId = document.getElementById(setId);
                    showId.style.display = 'block';
                    return false;
                };
            });
        };

        item.addEventListener('click', () => {
            let setId = item.getAttribute('data-setting');

            stepTopList.forEach((i) => {
                i.classList.remove('active');
            });

            if(idx === 1) {
                item.classList.add('active');
            } else {
                item.classList.add('active');
            };

            bottomSet.forEach((items) => {
                items.style.display = 'none';
            });
            bottomSet.forEach((items) => {
                let _id = items.getAttribute('id');

                if(_id == setId) {
                    let showId = document.getElementById(setId);
                    showId.style.display = 'block';
                    return false;
                };
            });

            addBtn.forEach((i, idx) => {
                let data = i.getAttribute('data-add');

                if(data === setId) {
                    addBtn.forEach(it => {it.classList.remove('on')});
                    i.classList.add('on')
                } else if(idx === 0) {
                    addBtn.forEach(it => {it.classList.remove('on')});
                };
                return false;
            });
            return false;
        });
    });

    ///////////////// sec card
    // right 이동 기능 함수
	
    async function rightSwichFuc2() {
        let dataListBox = dataBox.querySelectorAll('.bottom_setting');
        let leng = [];
        let checkIdx;
        let checkIdxVal;
        
        dataListBox.forEach((item, indx) => {
            let dataList = item.querySelectorAll('.set_list');
            leng.push(new Array());
            dataList.forEach((list, idx) => {
                let check = list.getElementsByTagName('input')[0];

                if(check.checked) {
                    leng[indx].push(idx);
                };
            });
        });

        for(let  [index, value] of leng.entries()) {
            if(value.length > 0) {
                checkIdx = index;
            };
        };

        checkIdxVal = Math.max(...leng[checkIdx]);
		
        dataListBox.forEach((item, indx) => {
            let dataList = item.querySelectorAll('.set_list');
			
            // 주석
            dataList.forEach(async (list, idx) => {
                let check = list.getElementsByTagName('input')[0];
                    
                // 주석
                async function getOptions(type = 0){
                    const res = await $.ajax({
                        url : `/api/method/each/${type}`,
                        type : "GET",
                        async:false,
                        success : function(list){
                            return list
                        }
                    })
                    return res
                }
                
                if(check.checked) {
					//console.log("1-------",check, check.checked);
                    const rightBox = document.querySelector('.setting_area_list_box');
                    let rightList = rightBox.querySelectorAll('.setting_area_list');
                    let _listText = list.querySelector('.set_list_text');
                    let stepNum = rightList.length;
                    
                    // 기구, 기타 예외처리
                    const stepParentId = _listText?.parentNode?.parentNode?.id ?? "";
                    const isUnit = ["instrument", "etc"].some(el => stepParentId.includes(el))
                    //if(_listText !== null) {
                        let listText = _listText.innerText;
                        let areaList = document.createElement('li');
                        let areaBox = document.createElement('div');
                        areaBox.setAttribute('class', 'setting_area_list');
                        
                        // 주석
                        const type = list.dataset.type
                        const id = list.dataset.id;
                        const name = list.dataset.name;
                        
                        if(check.checked) {
							//console.log("2------", check.checked);
	                        check.checked = false;
	                        
	                    };
                        
                        // 주석
                        const options = await getOptions(type)
                        
                        
						let optList = `<option value="-1" hidden></option>`;
                        
                        
                        // 주석
						options.forEach(opt => {
							let html = `<option value="${opt.each_id}" ${opt.each_title === 1 ? "disabled class='title'" : ""}>${opt.each}</option>`
							optList += html;
						})
						
						
                        const unitSelect = `
                        <div class="set_select_box">
                            <span></span>
                            <div class="set_area_wrap">
                                ${isUnit 
                                    ? `<input type="text" name="" id="">
                                    <select name="" id="" class="common_select">
                                    </select>
                                    `
                                    : 
                                    `
                                    <input type="text" name="" id="">
                                    <select name="" id="" class="common_select">
                                        ${optList}
                                    </select>
                                    `
                                }                                
                            </div>
                        </div>`; 
                        
                        // 주석
                        areaBox.innerHTML =  `
                        <div class="set_area_inner" data-type=${type} data-id=${id} data-num="0" data-group=${step_index}>
                            <div class="top_set_area">
                                <label for="" class="check_wrap">
                                    <input type="checkbox" name="" id="" class="checkbox">
                                </label>
                                <span class="set_step">step ${stepNum + 1}</span>
                                <p class="top_set_title">${name}</p>
                                <div class="top_set_input">
                                    <label for="">
                                        <input type="text" value="" id="">
                                    </label>
                                    <div class="step_add_box">
                                        <label class="step_num_input">
                                            <input type="number" value="0">
                                        </label>
                                        <span class="new_tab_add">서브스텝추가 +</span>
                                    </div>
                                </div>
                            </div>
                            <div class="bottom_set_area">
                                <div class="bottom_set_select">
                                    ${Array(5).fill().map((v) => unitSelect).join("")}
                                </div>
                                <div class="set_textarea_box">
                                    <label for="">
                                        <textarea name="" id="" placeholder="Comment"></textarea>
                                    </label>
                                </div>
                            </div>
                        </div>`;
						//${Array(5).fill().map((v) => unitSelect).join("")}
                        areaList.append(areaBox);
                        rightBox.append(areaList);
                        
						
						// 그룹 네임 인덱스 변경
						step_index++;
						//
						
                        const selects = document.querySelectorAll('.set_area_inner select');
                        const textArea = document.querySelectorAll('.set_area_inner textarea');
                        selects.forEach(select => {
                            select.addEventListener('change', (e) => {
                                renderProtocol()
                            })
                        })
                        textArea.forEach(input => {
                            input.addEventListener('change', (e) => {
                                renderProtocol()
                            })
                        })
                        // textArea.addEventListener('change', renderProtocol)

                        let title = areaBox.querySelector('.top_set_title');
                        title.setAttribute('title', title.innerText);

                        title.addEventListener('click', (e) => {
                            let target = e.target;
                            let list = target.parentNode.parentNode.parentNode;
                            let has = list.classList.contains('on');

                            let onHas = has ? list.classList.remove('on') : list.classList.add('on');
                        });
                    //}
                    
					
                    if(checkIdx === indx && checkIdxVal === idx) {
	
						/*
                        dataListBox.forEach(item => {
                            let dataList = item.querySelectorAll('.set_list');
                            
                            //console.log(dataList , "dataList")
                            
                            dataList.forEach(list => {
								
                                let check = list.getElementsByTagName('input')[0];

                                if(check.checked) {
									console.log("2------", check.checked);
                                    check.checked = false;
                                    
                                };
                            });
                        });
                        
                        if(check.checked) {
							console.log("2------", check.checked);
                            check.checked = false;
                        };
                        */
                        noneHas();
                        settArea();
                    };

                };
            });
        });

        renderProtocol()
        setTimeout(() => resetStepsNumber(), 10)
        setTimeout(() => dataDetails(), 1000);
    };

    // 체크후 버튼클릭시 삭제 기능 함수
    function leftSwichFuc2() {
        rightDataBox = document.querySelector('.setting_area_list_box');
        let list = rightDataBox.querySelectorAll('#dragSettingArea > li');

        if(list.length > 0) {
            list.forEach(item => {
                let listDiv = item.querySelectorAll('.setting_area_list');
                
                listDiv.forEach(it => {
                    let check = it.getElementsByTagName('input')[0];
    
                    if(check.checked) {
                        it.remove();
                        noneHas();
                        settArea();
                    };
                });
            });
        };
        renderProtocol()

    };


    // 세부 step 생성 함수
    function dataDetails() {
	
        const _rightBox = document.querySelector('.setting_area_list_box');
        let _rightList = _rightBox.querySelectorAll('.setting_area_list');
		
        if(_rightList.length > 0) {
            _rightList.forEach((item, idx) => {
                let detailAdd = item.querySelector('.new_tab_add');

                if(detailAdd !== null) {
                    detailAdd.addEventListener('click', detailAddFuc);
                };
            });
        };
        
    };
    
    // 세부 탭 추가 버튼 클릭시 기능
    function detailAddFuc(e) {
        let dom = document;
        let target = e.target;
        let forNum = target.previousElementSibling.getElementsByTagName('input')[0];
        let forNumVal = Number(forNum.value);
        let dataTopList = target.parentNode.parentNode.parentNode;
        let parentBox = dataTopList.parentNode.parentNode;
        let parentWrap = parentBox.parentNode;
        
        // step text
        if(forNumVal > 0) {
            for(let i = 0; i < forNumVal; i++) {
                let stepText = dataTopList.querySelector('.set_step');
                let stepNum = stepText.innerText.split(' ')[1];
                
                // step data num
                let listDataNum = dataTopList.parentNode.getAttribute('data-num');
                dataTopList.parentNode.setAttribute('data-num', Number(listDataNum) + 1);
                listDataNum = dataTopList.parentNode.getAttribute('data-num');
        
                let cloneList = parentBox.cloneNode(true);
                
                // 서브 넘버
                cloneList.querySelector('.set_area_inner').setAttribute('data-sub' , listDataNum);
                
                let stepNumText = cloneList.querySelector('.set_step').innerText;
                cloneList.querySelector('.set_step').innerText = stepNumText + '-' + listDataNum;
                cloneList.querySelector('.step_add_box').remove();
                
                // 추가 순서 변경
                if(parentBox.nextElementSibling === null) {
                    parentWrap.insertBefore(cloneList, parentBox.nextElementSibling);
                } else {
                    parentWrap.insertBefore(cloneList, null);
                };
        
                const selects = dom.querySelectorAll('.set_area_inner select');
                const textArea = dom.querySelectorAll('.set_area_inner textarea');
        
                selects.forEach(select => {select.addEventListener('change', (e) => {renderProtocol()})});
                textArea.forEach(input => {input.addEventListener('change', (e) => {renderProtocol()})});
        
                let title = cloneList.querySelector('.top_set_title');
                title.setAttribute('title', title.innerText);
        
                title.addEventListener('click', (e) => {
                    let target = e.target;
                    let list = target.parentNode.parentNode.parentNode;
                    let has = list.classList.contains('on');
        
                    let onHas = has ? list.classList.remove('on') : list.classList.add('on');
                });
        
                renderProtocol();
                setTimeout(() => resetStepsNumber(), 10);
            };
            dataReBtn.addEventListener('click', leftSwichFuc2);
        };
    };
    



    function noneHas() {
        let hasList = rightDataBox.querySelectorAll('.setting_area_list');
        let hasText = document.querySelector('.none_has');
        let has = hasList.length > 0 ? hasText.classList.remove('on') : hasText.classList.add('on');
    };
    noneHas();


    dataAddBtn.addEventListener('click', rightSwichFuc2);
    dataReBtn.addEventListener('click', leftSwichFuc2);


    ///////////////// dragula.js
    var drake = dragula([stepContainer],{

    })
    .on('dragend', (el) => {
        resetStepsNumber()
        renderProtocol()
    });


    const metSharing = document.querySelector('.method_sharing');
    const metshareCheck = metSharing.getElementsByTagName('input')[0];

    // metshareCheck.addEventListener('click', () => {
    //     if(metshareCheck.checked === false) {
    //         alert('사용중인 메소드로 인해 공유 해제가 불가합니다.');
    //         metshareCheck.checked = true;
    //     };
    // });


    // 신규 등록 - step 설정 - select 선택시 위의 span에 값 입력
    function settArea() {
        const setAreaSelectBox = document.querySelectorAll('.set_area_wrap > .common_select');

        setAreaSelectBox.forEach((item) => {
            let _span = item.parentNode.previousElementSibling;

            item.addEventListener('change', () => {
                let val = item.options[item.selectedIndex];
                _span.innerText = val.text;
            });
        });
    };

    function renderProtocol() {
        protocolTextarea.value = "";
        const steps = stepContainer.querySelectorAll('.setting_area_list');
        
        let str = ``
        steps.forEach((step, index) => {
			
			let step_index = step.querySelector('.set_area_inner .set_step').innerHTML;
			
            const stepTitle = step.querySelector('.top_set_title');
            const selects = step.querySelectorAll('.common_select');
            const inputSelects = step.querySelectorAll('.set_area_wrap');
            const textarea = step.querySelector('textarea');
            let contents = `${step_index}: ${stepTitle.innerText} [`;

			const step_type = step.querySelector('.set_area_inner').dataset.type;
			const instrument_type = 4;
			
			if(step_type != instrument_type){
				
	            selects.forEach(select => {
	                const value = select.value;
	                const num = select.previousElementSibling.value
				  	const optionIndex = [...select.querySelectorAll('option')].findIndex(el => el.value == value);
	                const optionName = [...select.querySelectorAll('option')][optionIndex].innerText;
	                const string = (optionIndex !== -1) ? `${num != '' && optionName != '' ? `${num} ${optionName} , ` : ''}` : "";
	                
	                contents += string
	            })
			}else{
				inputSelects.forEach(item => {
					
					const value = item.querySelector('input[type="text"]').value;
					const string = value != '' ? value+', ' : '';
					
					contents += string
				})
			}
            
            contents += textarea.value ? `${textarea.value}]` : "]\n";
            str += `
                ${contents}`;

        });

        protocolTextarea.value = str;

    }
});