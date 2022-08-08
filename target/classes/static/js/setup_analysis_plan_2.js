document.addEventListener('DOMContentLoaded', () => {
    // 신규 등록 - step 설정 - top 탭메뉴
    const bottomSet = document.querySelectorAll('.bottom_setting');
    const checkedBox = document.querySelectorAll('.batch_box .checked_box');
    const setAddBtn = document.querySelector('.setting_top_add_btn');
    const setBatchTable = document.getElementById('drag_table1');
    const batchTable = document.getElementById('noneCheckedTable1');
    const batchContainer = document.querySelector('#batch_container');
    const plateTable = document.querySelector('.sec_batch_table');
    const plateRows = plateTable.querySelectorAll('tbody tr');
    const batchCountEl = document.querySelector('#batch_count');
    const batchSampleEl = document.querySelector('#batch_by_sample');
    const addBatchBtn = document.querySelector('#add_batch_btn');
    const useCheckBox = document.querySelector('.well_used_box');
    const useCheck = useCheckBox.getElementsByTagName('input')[0];
	
	// 96well check
	let plate_check = false; 
	
    let stepDataByBatch = [
      
    ];
    plateTable.querySelectorAll('tbody tr').forEach(tr => {
        tr.querySelectorAll('td').forEach((td, index) => {
            if (index === 0) return;
            td.addEventListener('click', (e) => {
                const target = e.currentTarget;
                handleClick(target)
            })
        })
        
    })

    // 상세페이지 조회 로직 
    if (result) {
        const { geneticData, plan, batch, schedule } = result;
        schedule.sort((a, b) => {
            return a.plate_index - b.plate_index
        })
        const geneticBox = document.querySelector('.info_list_wrap');
        const dragTable = document.querySelector('#drag_table1');      
        // const cycle = parseInt(document.querySelector('#method_cycle').value);
        const cycle = plan?.planMethodList[0]?.method_cycle ?? 0;
        let htmlStrings = "";
        
        let arr = [];
        if (Array.isArray(geneticData)) {
            geneticData.forEach((data, sampleIndex) => {
                let list = "";
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
                    <li class="info_list">
                        <div class="info_list_head">${data?.parent_name}</div>                
                        ${list}
                    </li>
                `;
                htmlStrings += html;
                arr = arr.concat(data.geneticList);
            })
            let dragStrings = "";            
            if (cycle === 0) {
                let tr = "";
                arr.forEach((el, index) => {                  
                    const batchStr = `
                    <tr data-id="${el?.genetic_id}" data-seed_id="${el?.seed_id}">
                        <td></td>
                        <td>${el?.parents_name}</td>
                        <td>${el?.genetic}</td>
                        <td>${index + 1}-${1}</td>
                    </tr>
                    `;
                    tr += batchStr;                        
                });
                dragStrings += tr;
            } else {
                arr.forEach((el, index) => {    
                    let tr = "";   
                    for (let i = 0; i < cycle; i++) {
                        const batchStr = `
                        <tr data-id="${el?.genetic_id}" data-seed_id="${el?.seed_id}">
                            <td></td>
                            <td>${el?.parents_name}</td>
                            <td>${el?.genetic}</td>
                            <td>${index + 1}-${i + 1}</td>
                        </tr>
                        `;
                        tr += batchStr;   
                    }                           
                    dragStrings += tr;
                });       
                // for (let i = 0; i < cycle; i++) {
                //     let tr = "";
                    
                //     arr.forEach((el, index) => {                  
                //         const batchStr = `
                //         <tr data-id="${el?.genetic_id}" data-seed_id="${el?.seed_id}">
                //             <td></td>
                //             <td>${el?.parents_name}</td>
                //             <td>${el?.genetic}</td>
                //             <td>${i + 1}-${index + 1}</td>
                //         </tr>
                //         `;
                //         tr += batchStr;    
                    
                //     });                    
                //     dragStrings += tr;
                    
                // }
            }
            
            geneticBox.innerHTML = htmlStrings;
            dragTable.innerHTML = dragStrings;
        }

        if (batch.length > 0) {
            getBatchData(plan, batch)
        }

        if (schedule.length > 0) {
            setTimeout(() => {
                getScheduleData(schedule)
            }, 1000)
            
        }
        
    }

    function getBatchData(plan = {}, batchs = []){
        const batchCount = plan?.plan_batch ?? 1;
        const blankCount = plan?.plan_blank ?? 0;
        const standardCount = plan?.plan_standard ?? 0;
        const controlCount = plan?.plan_control ?? 0;
        const batchSample = plan?.plan_sample ?? 0;
        const batchWellOrder = plan?.plan_well ? "Horizontal" : "Vertical"
        
        document.querySelector('#batch_count').value = batchCount;
        document.querySelector('#batch_by_sample').value = batchSample;
        document.querySelector('#plan_blank').value = blankCount;
        document.querySelector('#plan_standard').value = standardCount;
        document.querySelector('#plan_bcontrol').value = controlCount;
        const orderInputBox = document.querySelector('.order_select')
        
        if(plate_check){
	        orderInputBox.classList.add('on');
	        orderInputBox.value = batchWellOrder;
		}
        // addBatch()
        setTimeout(() => {
            addBatch()    
            serializeOrderByBatch(batchs)
        }, 1000)
    }

    function serializeOrderByBatch(batchs = []){
        const batchOne = batchs.filter(batch => batch?.plate_index === 1)
        const batchTwo = batchs.filter(batch => batch?.plate_index === 2)
        const batchThree = batchs.filter(batch => batch?.plate_index === 3)
        const allBatchs = [batchOne, batchTwo, batchThree]
        
        const trueTable = document.querySelectorAll('.checked_true_box table')
        const falseTable = document.querySelectorAll('.checked_false_box table')

        trueTable.forEach((table, tableIndex) => {
            const trs = table.querySelectorAll('tbody tr')
            trs.forEach((tr, rowIndex) => {
                
                tr.querySelectorAll('td').forEach((td, index, arr) => {
                    if (index === arr.length - 1) {
                        return;
                    }
                    td.innerText = ""                    
                });
                tr.dataset.seed_id = 0;
                tr.dataset.id = 0;
            })
        })
        falseTable.forEach((table, tableIndex) => {
            const trs = table.querySelectorAll('tbody tr')
            trs.forEach((tr, rowIndex) => {
                
                tr.querySelectorAll('td').forEach((td, index, arr) => {
                    if (index === arr.length - 1) {
                        return;
                    }
                    td.innerText = ""                    
                });
                tr.dataset.seed_id = 0;
                tr.dataset.id = 0;
            })
        })
        allBatchs.forEach(batchData => {
            batchData.forEach((obj, index) => {
                const trs = trueTable[obj?.plate_index -1].querySelectorAll('tbody tr')
                const td = trs[obj?.batch_index].querySelectorAll('td')
                let geneticName = "";
                const batchSample = obj.batch_sample;
                if (batchSample.includes("bl")) {                    
                    geneticName = `Blank${batchSample.split("-")[2]}`
                } else if (batchSample.includes("st")) {                    
                    geneticName = `Standard${batchSample.split("-")[2]}`
                } else if (batchSample.includes("co")) {                    
                    geneticName = `Control${batchSample.split("-")[2]}`
                } else {
                    geneticName = obj.genetic_depth_1
                }
                td[1].innerText = geneticName
                td[2].innerText = obj.genetic_depth_2
                td[3].innerText = batchSample
                td[4].innerText = obj.batch_test
                trs[obj?.batch_index].dataset.seed_id = obj.seed_id === -1 ? "undefined" : obj.seed_id;
                trs[obj?.batch_index].dataset.id = obj.genetic_id === -1 ? "0" : obj.genetic_id;
                const trs2 = falseTable[obj?.plate_index -1].querySelectorAll('tbody tr')
                const td2 = trs2[obj?.batch_index].querySelectorAll('td')
                
                td2[1].innerText = geneticName
                td2[2].innerText = obj.genetic_depth_2
                td2[3].innerText = batchSample
                trs2[obj?.batch_index].dataset.seed_id = obj.seed_id === -1 ? "undefined" : obj.seed_id;
                trs2[obj?.batch_index].dataset.id = obj.genetic_id === -1 ? "0" : obj.genetic_id;
            })
        })
    }

    function getScheduleData(list = []){
	
		console.log(list , "list")
	
        const table = document.querySelector('.step_2_table tbody');
        const scheduleRows = document.querySelectorAll('.step_2_table tbody tr');
        table.innerHTML = ""
        for(var i = 0 ; i < list.length ; i++){
            const listData = list[i];
            const planManager = list[i].managerList.filter(v => v.mgr_type === 1)[0];
            const managers = list[i].managerList.filter(v => v.mgr_type === 2);
			
			console.log(planManager, "planManager")
			
            let subIds = "";
            let subTxt = ""
            managers.forEach((v) => {
                subIds += (v?.account + ",")
                subTxt += (v?.user_name + ",")
            })

            const tr = document.createElement('tr');
            tr.dataset.sch_id = listData.sch_id ?? "";
            tr.dataset.main_manager_id = planManager?.account ?? "";
            tr.dataset.sub_manager_ids = subIds !== "" ? subIds.slice(0, -1) : "";
            tr.dataset.comment = listData?.sch_contents ?? "";

            const td_0 = document.createElement('td');
            const td_1 = document.createElement('td');
            const td_2 = document.createElement('td');
            const td_3 = document.createElement('td');
            const td_4 = document.createElement('td');
            const td_5 = document.createElement('td');
            td_0.innerHTML = `
                            <div class="add_btn_box">
                                <button class="common_btn1 method_btn enroll_btn">Batch #${i+1}</button>
                                <ul class="add_list_box">
                                    <li class="add_list">
                                        <button type="button" class="modal_on_btn" data-modal="scheduleContSet">Batch별 설정</button>
                                    </li>
                                </ul>
                            </div>
                            `
            td_1.innerText = listData?.StepData ? "Step별 설정" : "Batch별 설정"
            td_2.innerText = planManager?.user_name ?? ""
            td_3.innerText = subTxt.slice(0, -1)
            td_4.innerText = listData?.start_date ? listData?.start_date.split(" ")[0] : ""
            td_5.innerText = listData?.end_date ? listData?.end_date.split(" ")[0] : ""
            
            tr.appendChild(td_0);
            tr.appendChild(td_1);
            tr.appendChild(td_2);
            tr.appendChild(td_3);
            tr.appendChild(td_4);
            tr.appendChild(td_5);
            table.appendChild(tr);
        }
        handleEnroll();
        handleScheduleContSet();
        return;


        scheduleRows.forEach((tr, rowIndex) => {
            if (!list[rowIndex]) return;
            const listData = list[rowIndex];
            const planManager = listData.managerList.filter(v => v.mgr_type === 1)[0];
            const managers = listData.managerList.filter(v => v.mgr_type === 2);
            let subIds = "";
            let subTxt = ""
            managers.forEach((v) => {
                subIds += (v?.account + ",")
                subTxt += (v?.user_name + ",")
            })
            
            tr.dataset.sch_id = listData.sch_id ?? "";
            tr.dataset.main_manager_id = planManager?.account ?? "";
            tr.dataset.sub_manager_ids = subIds !== "" ? subIds.slice(0, -1) : "";
            tr.dataset.comment = listData?.sch_contents ?? "";

            const td = tr.querySelectorAll('td');
            td[1].innerText = listData?.StepData ? "Step별 설정" : "Batch별 설정"
            td[2].innerText = planManager?.user_name ?? ""
            td[3].innerText = subTxt.slice(0, -1)
            td[4].innerText = listData?.start_date ? listData?.start_date.split(" ")[0] : ""
            td[5].innerText = listData?.end_date ? listData?.end_date.split(" ")[0] : ""
        })        
    }
 
    // 분석관리 등록 로직 
    function setPlateTable() {
        let plateHtmlStrings = "";
        let normalHtmlStrings = "";
        let num = 0;
        plateRows.forEach(row => {
            const tds = row.querySelectorAll('td');
            const code = tds[0].innerText;            
            let plateTrStrings = "";
            let normalTrStrings = "";
            tds.forEach((td, index) => {
                if (index === tds.length - 1) return;
                const plateTrString = `
                    <tr>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td>${code}${(index + 1) < 10 ? "0" + (index + 1) : (index + 1)}</td>
                    </tr>
                `;
                const normalTrString = `
                    <tr>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>                       
                    </tr>
                `
                plateTrStrings += plateTrString;
                normalTrStrings += normalTrString;
            })
            plateHtmlStrings += plateTrStrings;
            normalHtmlStrings += normalTrStrings;
        })
        const plateTable = batchContainer.querySelectorAll('.checked_true_box tbody');
        const normalTables = batchContainer.querySelectorAll('.checked_false_box tbody');
        
        plateTable.forEach(table => {
            table.innerHTML = plateHtmlStrings;
        })
        normalTables.forEach(table => {
            table.innerHTML = normalHtmlStrings;
        })
    }
    setPlateTable();

    // table 박스와 tab memu 기능
      // check 감지
  
    function checked(bool) {
        const dimm = document.querySelector('.sec_batch_dimm');
        const dimmBox = document.querySelector('.batch_right_box');
        if(!bool) {
            dimm.classList.add('on');
            dimmBox.style.overflow = 'hidden';
        } else {
            dimm.classList.remove('on');
            dimmBox.style.overflow = 'auto';
        };
        const stepTopList = document.querySelectorAll('.setting_top_list');
        // // table box - check
        checkedBox.forEach((i) => {
            let check = i.classList.contains('checked_' + bool + '_box');
            let chk = check ? i.style.display = 'block' : i.style.display = 'none';
        });

        // // table box - check => tab menu 
        stepTopList.forEach((item, idx) => {
            let has = item.classList.contains('active');
    
            if(has) {
                let setId = item.getAttribute('data-' + bool);


                bottomSet.forEach((items) => {
                    let _id = items.getAttribute('id');
    
                    if(_id == setId) {
                        let showId = document.getElementById(setId);
                        showId.style.display = 'block';
                        return false;
                    };
                });
            };
    
    
            item.addEventListener('click', (e) => {
                let setId = item.getAttribute('data-' + bool);

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
                renderSecBatchTable()                
                return false;
            });
        });
    };

    checked(useCheck.checked)  
    useCheck.addEventListener('change', (e) => {  
        const orderInputBox = document.querySelector('.order_select')
        const isCheck = e.target.checked;
        // check 여부 - table 박스와 tab memu 기능
        checked(isCheck)
        if (isCheck) {
            renderSecBatchTable()
            orderInputBox.classList.add('on')
        } else {
            orderInputBox.classList.remove('on')
        }	
    });
    // checked_true_box - table - td 내용없으면 화살표 X
    const secTrueTable = document.querySelectorAll('.checked_true_box .checked_table');

    secTrueTable.forEach((item) => {
        let tbody = item.getElementsByTagName('tbody')[0];
        let tr = tbody.childNodes;

        tr.forEach((i) => {
            if(i.nodeName !== "#text") {
                let inData = i.childNodes[3].innerText;

                if(inData !== '') {
                    i.classList.add('has');
                } else {
                    i.classList.remove('has');
                };
            };
        });

    });


    ///////////////// dargula
    
    dragula([setBatchTable]);
    for(let i = 1; i < 4; i++) {
        dragula([document.getElementById('noneCheckedTable' + i)])
        .on('dragend', () => {
            // renderBatchTable()
            matchDataTable()
            const orderType = document.querySelector('#order_select').value
            if (orderType === "Horizontal") {
                sortHorizontalTable()
            } else {
                sortVerticalTable()
            }
            setTestIdByRow()            
            renderSecBatchTable()
        });

        const table = document.getElementById('CheckedTable' + i);
        dragula([table])
        .on('dragend', () => {
            // renderBatchTable()
            const orderType = document.querySelector('#order_select').value
            matchDataTable(true)
            // setWellNoByRow()
            if (orderType === "Horizontal") {
                sortHorizontalTable()
            } else {
                sortVerticalTable()
            }
            setTestIdByRow()
            renderSecBatchTable()
        })
        
    };

    // Step 1. batch 설정
    const userAddButton = document.querySelector('#addBtn');
    const userAddSelect = document.querySelector('#user_add_select');
    const lookupInput = document.querySelector('.lookup_input input');
    const blankBox = document.querySelector('#blank_box + input')
    const standardBox = document.querySelector('#standard_box + input')
    const controlBox = document.querySelector('#control_box + input')

    function setNumber() {
        const numValue = lookupInput.value;
        const typeValue = userAddSelect.value;        
        if (typeValue && numValue) {
            switch(typeValue) {
                case "Blank":
                    blankBox.value = numValue;
                    return;
                case "Standard":
                    standardBox.value = numValue;
                    return;
                case "Control":
                    controlBox.value = numValue;
                    return;
                default:
                    return;
            }
        } else if (!typeValue){
            alert("사용자 추가 항목을 선택해주세요.")
            return;
        } else if (!numValue) {
            alert("숫자를 입력해주세요")
            return;
        }
    }
    userAddButton.addEventListener('click',setNumber)

    function sortHorizontalTable(){        
        setWellNoByRow()
        const tables = batch_container.querySelectorAll('.checked_true_box tbody');
        const sorted = [...tables[0].querySelectorAll('tbody tr')]
        .map(v => v.querySelectorAll('td')[5].innerText)
        .sort((a,b) => {            
            if (a.slice(0,1) > b.slice(0,1)) return 1
            if (a.slice(0,1) < b.slice(0,1)) return -1
            if (a.slice(0,1) === b.slice(0,1)) return (a.slice(1,3) - b.slice(1,3))
        })
        tables.forEach(table => {            
            const trs = table.querySelectorAll('tbody tr');
            trs.forEach((tr, rowIndex) => {
                tr.querySelectorAll('td')[5].innerText = sorted[rowIndex]
            })
        });      
    }

    function sortVerticalTable(){        
        setWellNoByRow()
        const tables = batch_container.querySelectorAll('.checked_true_box tbody');
        const sorted = [...tables[0].querySelectorAll('tbody tr')]
        .map(v => v.querySelectorAll('td')[5].innerText)
        .sort((a,b) =>  a.slice(1,3) - b.slice(1,3))
        tables.forEach(table => {            
            const trs = table.querySelectorAll('tbody tr');
            trs.forEach((tr, rowIndex) => {
                tr.querySelectorAll('td')[5].innerText = sorted[rowIndex]
            })
        });   
    }

    document.querySelector('#order_select').addEventListener('change', (e) => {
        const value = e.target.value;
        if (value === "Horizontal") {
            sortHorizontalTable()
        } else {
            sortVerticalTable()
        }
        renderSecBatchTable()
    })
   
    
    function clearTables(){
        const trs = [...batchContainer.querySelectorAll('tbody tr')];
        trs.forEach(tr => {
            tr.dataset.test_id = "";
            [...tr.children].forEach((td, index) => {
                if (index === 5) return;
                td.innerText = ""
            })
        })
    }

    function renderTabs() {
        const tabCounts = batchCountEl.value ? parseInt(batchCountEl.value) : 1;
        const tabContainer = document.querySelector('.batch_box .setting_top_list_box');
        
        if (!batchCountEl.value) {
            batchCountEl.value = 1;
        }
        tabContainer.innerHTML = "";
        let tab = "";
        stepDataByBatch = [];
        Array(tabCounts).fill().forEach((item, index) => {
            let li = `
                <li class="setting_top_list ${index === 0 && "active"}" data-false="Batch_${index + 1}" data-true="check_Batch_${index + 1}">Batch #${index + 1}</li>
            `
            tab += li;
            stepDataByBatch.push([])
        })
        tabContainer.innerHTML = tab;       
        renderStepTable()
        return true;
    }

    function checkSampleCounts(a = 0,b = 0,c = 0,d = 0,e = 0) {
        const batchCount = parseInt(document.querySelector('#batch_count').value);
        const batchBySample = document.querySelector('#batch_by_sample').value ? document.querySelector('#batch_by_sample').value : 0;      
        if (!batchBySample) {
            document.querySelector('#batch_by_sample').value = 96;
        }  
        const batchBySampleNum = parseInt(batchBySample || 96);
        const blankCount = document.querySelector('#blank_box + input').value ? document.querySelector('#blank_box + input').value : 0;
        const blankCountNum = parseInt(blankCount);
        const standardCount = document.querySelector('#standard_box + input').value ? document.querySelector('#standard_box + input').value : 0;
        const standardCountNum = parseInt(standardCount);
        const controlCount = document.querySelector('#control_box + input').value ? document.querySelector('#control_box + input').value : 0;
        const controlCountNum = parseInt(controlCount);
        console.table({batchBySampleNum , blankCountNum , standardCountNum , controlCountNum})
        return batchBySampleNum + blankCountNum + standardCountNum + controlCountNum <= 96
    }

    function createBatch(){
        const batchCount = parseInt(document.querySelector('#batch_count').value);
        const newArr = [];
        for(let i = 0; i < batchCount; i++) {
            newArr.push([])
        }
        return newArr
    }

    function insertSamples(arr = []){
        const dragTable = [...document.querySelectorAll('#drag_table1 > tr')];    
        let isSample = document.querySelector('#batch_by_sample').value !== "";
        let batchBySample = isSample ? parseInt(document.querySelector('#batch_by_sample').value) : 96;
        if (!isSample) {
            document.querySelector('#batch_by_sample').value = 96;
        }
        let createBatchNum = 0;
        let sampleNum = 0;
        dragTable.forEach((el, index) => {            
            const dataTd = el.querySelectorAll('td');
            const obj = {
                id : el.dataset.id,
                parents_name: dataTd[1].innerText,
                genetic: dataTd[2].innerText,
                sampleId: dataTd[3].innerText,
                seedId: el.dataset.seed_id
            }
            sampleNum++;            
            arr[createBatchNum].push(obj)
            if (arr[createBatchNum].length === batchBySample) {
                if (arr[createBatchNum + 1]) {
                    createBatchNum++;
                }                
            }            
        })
        return arr;
    }   

    function insertOptionSample(arr = []){
        const blankCount = parseInt(document.querySelector('#blank_box + input').value);
        const standardCount = parseInt(document.querySelector('#standard_box + input').value);
        const controlCount = parseInt(document.querySelector('#control_box + input').value);

        arr.forEach((array, batchIndex) => {
            for (let i = 0; i < blankCount; i++){
                const obj = {
                    id : 0,
                    parents_name: `Blank${i+1 > 10 ? i + 1 : "0" + (i + 1)}`,
                    genetic: "",
                    sampleId: `${batchIndex + 1}-bl-${i+1 > 10 ? i + 1 : "0" + (i + 1)}`,
                }
                array.push(obj)
            }
            for (let i = 0; i < standardCount; i++){
                const obj = {
                    id : 0,
                    parents_name: `Standard${i+1 > 10 ? i + 1 : "0" + (i + 1)}`,
                    genetic: "",
                    sampleId: `${batchIndex + 1}-st-${i+1 > 10 ? i + 1 : "0" + (i + 1)}`,
                }
                array.push(obj)
            }
            for (let i = 0; i < controlCount; i++){
                const obj = {
                    id : 0,
                    parents_name: `Control${i+1 > 10 ? i + 1 : "0" + (i + 1)}`,
                    genetic: "",
                    sampleId: `${batchIndex + 1}-co-${i+1 > 10 ? i + 1 : "0" + (i + 1)}`,
                }
                array.push(obj)
            }
        })
        return arr;
    }
 
    function matchDataTable(isPlate){
        if (isPlate) {
            const tables = [...batchContainer.querySelectorAll('.checked_true_box table')];
            const falseTable = [...batchContainer.querySelectorAll('.checked_false_box table')];
            const currentIndex = tables.findIndex(table => (table.style.display) === "block");
            const tableId = tables[currentIndex].id;
            const falseTableIndex = falseTable.findIndex((el) => tableId.includes(el.id))
            const falseTableRow = falseTable[falseTableIndex].querySelectorAll('tbody tr');
            falseTableRow.forEach((tr, index) => {
                const trueTableTrs = tables[currentIndex].querySelectorAll('tbody tr')[index];
                
                tr.dataset.id = trueTableTrs.querySelectorAll('td')[4].innerText;  
                tr.querySelectorAll('td')[1].innerText = trueTableTrs.querySelectorAll('td')[1].innerText;
                tr.querySelectorAll('td')[2].innerText = trueTableTrs.querySelectorAll('td')[2].innerText;
                tr.querySelectorAll('td')[3].innerText = trueTableTrs.querySelectorAll('td')[3].innerText;
                if (trueTableTrs.querySelectorAll('td')[1].innerText === "") {
                    tr.dataset.id = "";  
                } else {
                    tr.dataset.id = trueTableTrs.querySelectorAll('td')[4].innerText;  
                }
    
            })   
        } else {
            const tables = [...batchContainer.querySelectorAll('.checked_false_box table')];
            const trueTable = [...batchContainer.querySelectorAll('.checked_true_box table')];
            const currentIndex = tables.findIndex(table => (table.style.display) === "block");
            const tableId = tables[currentIndex].id;
            const trueTableIndex = trueTable.findIndex((el) => el.id.includes(tableId))
            const trueTableRow = trueTable[trueTableIndex].querySelectorAll('tbody tr');
    
            trueTableRow.forEach((tr, index) => {
                const falseTableTrs = tables[currentIndex].querySelectorAll('tbody tr')[index];
                
                tr.dataset.id = falseTableTrs.dataset.id;  
                tr.querySelectorAll('td')[1].innerText = falseTableTrs.querySelectorAll('td')[1].innerText;
                tr.querySelectorAll('td')[2].innerText = falseTableTrs.querySelectorAll('td')[2].innerText;
                tr.querySelectorAll('td')[3].innerText = falseTableTrs.querySelectorAll('td')[3].innerText;
                if (falseTableTrs.querySelectorAll('td')[1].innerText === "") {
                    tr.querySelectorAll('td')[4].innerText = "";  
                } else {
                    tr.querySelectorAll('td')[4].innerText = falseTableTrs.dataset.test_id;  
                }
    
            })   
        }
           
    }
   

    function renderBatchTable() {            
        const tables = [...batchContainer.querySelectorAll('table')];
        
            
        const createdbatchArr = createBatch();
        const batchDataArr = insertSamples(createdbatchArr)
        const results = insertOptionSample(batchDataArr)
        
        results.forEach((result, index) => {
            result.forEach((res, i) => {                
                const batchTbody = tables.filter(table => table.id.includes(index + 1))                
                const batchTr1 = batchTbody[0].querySelectorAll('tr')[i + 1];
                const batchTr2 = batchTbody[1].querySelectorAll('tr')[i + 1];
                if (batchTr1) {
                    batchTr1.dataset.id = res?.id;
                    batchTr1.dataset.seed_id = res?.seedId;
                    batchTr1.querySelectorAll('td')[1].innerText = res?.parents_name
                    batchTr1.querySelectorAll('td')[2].innerText = res?.genetic
                    batchTr1.querySelectorAll('td')[3].innerText = res?.sampleId
                }
                if (batchTr2) {
                    batchTr2.dataset.id = res?.id;      
                    batchTr2.dataset.seed_id = res?.seedId;
                    batchTr2.querySelectorAll('td')[1].innerText = res?.parents_name
                    batchTr2.querySelectorAll('td')[2].innerText = res?.genetic
                    batchTr2.querySelectorAll('td')[3].innerText = res?.sampleId    
                }
            })
        })
        setTestIdByRow(true)
        const orderType = document.querySelector('#order_select').value
        if (orderType === "Horizontal") {
            sortHorizontalTable()
        } else {
            sortVerticalTable()
        }
        return results
    }

    function setTestIdByRow(isSet){
        const tables = [...batchContainer.querySelectorAll('.checked_true_box table')];
        tables.forEach((table, batchNum) => {
            const rows = table.querySelectorAll('tr');
            rows.forEach((row, index) => {
                if (index === 0) return;
                const isRow = row.querySelectorAll('td')[1].innerText !== ""
                const testIdTd = row.querySelectorAll('td')[4];
                if (isRow && isSet) {
                    testIdTd.innerText = `b${batchNum + 1}-${index >= 10 ? index : "0" + (index)}`;                    
                }
               
            })
        })
        const falseTables = [...batchContainer.querySelectorAll('.checked_false_box table')];
        falseTables.forEach((table, batchNum) => {
            const rows = table.querySelectorAll('tr');
            rows.forEach((row, index) => {
                if (index === 0) return;
                const isRow = row.querySelectorAll('td')[1].innerText !== ""               
                if (isRow && isSet) {
                    row.dataset.test_id = `b${batchNum + 1}-${index >= 10 ? index : "0" + (index)}`;                    
                }
                
            })
        })

    }
    
    // 왼쪽 테이블 데이터로 오른쪽 테이블 새로 그리기
    function resetSecBatchTable(){
        const secTable = document.querySelector('.sec_batch_table');
        const secTableTd = secTable.querySelectorAll('tbody tr td span');
        secTableTd.forEach(span => {
            span.innerText = "";
            span.className = "";
        })
    }

    function setWellNoByRow(){
        const tables = [...batchContainer.querySelectorAll('.checked_true_box table')];
        const colArr = ["A","B","C","D","E","F","G","H"];

        tables.forEach(table => {
            const trs = table.querySelectorAll('tbody tr');
            let charIndex = 0;
            let numIndex = 0;
            trs.forEach((tr, rowIndex) => {
                tr.dataset.index = "";
                tr.querySelectorAll('td')[5].innerText = `${colArr[charIndex]}${numIndex + 1 >= 10 ? (numIndex + 1) : "0" + (numIndex + 1)}`
                numIndex++;
                if (numIndex >= 12) {
                    charIndex++;
                    numIndex = 0;
                }
            })
        })       
        
    }

    function renderSecBatchTable(){      
        resetSecBatchTable()
        
        let tables = null;
		
		if(plate_check){
			tables = [...batchContainer.querySelectorAll('.checked_true_box table')];      
		}else{
			tables = [...batchContainer.querySelectorAll('.checked_false_box table')];      
		}
        
        const currentIndex = tables.findIndex(table => (table.style.display) === "block");
        const secTable = document.querySelector('.sec_batch_table');
        const secTableTr = secTable.querySelectorAll('tbody tr');
           
        const colArr = ["A","B","C","D","E","F","G","H"];
        const options = ["Blank", "Standard", "Control"];
        const optionsClass = ["on_2", "on_3", "on_4"];
		
		if(plate_check){
	        tables[currentIndex].querySelectorAll('tbody tr').forEach((tr, rowIndex) => {
	            const testId = tr.querySelectorAll('td')[4]?.innerText;
	            const optionTr = tr.querySelectorAll('td')[1].innerText;
	            const wellNoChar = tr.querySelectorAll('td')[5].innerText.slice(0,1);
	            const wellNoNum = parseInt(tr.querySelectorAll('td')[5].innerText.slice(1,3));
	            const secRowIndex = colArr.findIndex(char => char === wellNoChar);
	            secTableTr[secRowIndex].querySelectorAll('td')[wellNoNum].dataset.index = rowIndex
	            if (options.some(opt => optionTr.includes(opt))){
	                const optIndex = options.findIndex((opt) => optionTr.includes(opt));                
	                secTableTr[secRowIndex].querySelectorAll('td')[wellNoNum].querySelector('span').className = "";
	                secTableTr[secRowIndex].querySelectorAll('td')[wellNoNum].querySelector('span').classList.add(optionsClass[optIndex]);
	                secTableTr[secRowIndex].querySelectorAll('td')[wellNoNum].querySelector('span').innerText = testId
	            } else if (testId){
	                secTableTr[secRowIndex].querySelectorAll('td')[wellNoNum].querySelector('span').className = "";
	                secTableTr[secRowIndex].querySelectorAll('td')[wellNoNum].querySelector('span').classList.add("on_1");
	                secTableTr[secRowIndex].querySelectorAll('td')[wellNoNum].querySelector('span').innerText = testId;
	            }                
	
	            // secTableTr[secRowIndex].querySelectorAll('td')[wellNoNum].removeEventListener('click',handleClick)
	            // secTableTr[secRowIndex].querySelectorAll('td')[wellNoNum].addEventListener('click',handleClick)
	        })
        } else {
			tables[currentIndex].querySelectorAll('tbody tr').forEach((tr, rowIndex) => {
				const sampleId = tr.querySelectorAll('td')[3]?.innerText;
	            const optionTr = tr.querySelectorAll('td')[1].innerText;
			})
		}
        
        handleBatchTrClick() 
    }

    function handleClick(target){       
        console.log(target)         
        const secTable = document.querySelector('.sec_batch_table');
        if (!target.innerText) return;
        secTable.querySelectorAll('td span').forEach(td => {
            td.classList.remove('selected')
        });
        target.classList.add('selected');
        const index = target.dataset.index;
        handleSecBatchClick(index)
    }

    function handleSecBatchClick(index){
        console.log(index)
        batchContainer.querySelectorAll('tr').forEach(tr => {
            tr.classList.remove('selected');
        })
        batchContainer.querySelectorAll('table').forEach((table, tableIndex) => {
            table.querySelectorAll('tbody tr').forEach((tr, rowIndex) => {
                if (rowIndex == index) {
                    console.log(tr)
                    tr.classList.add('selected')
                    $(tr.parentNode).animate({scrollTop : tr.offsetTop - 40 }, 500);        
                }
            })
        })
        
    }

    function handleBatchTrClick(){
        const trs = batchContainer.querySelectorAll('tbody tr');
        
        console.log(trs , "trs")
        
        trs.forEach((tr,index) => {
            function handleClick(e){
                if (!e || !e.currentTarget) return;
                if (!e.currentTarget.querySelectorAll('td')[1].innerText) return;
                trs.forEach(tr => tr.classList.remove('selected'));
                e.currentTarget.classList.add('selected');
                const batch = document.querySelectorAll('.sec_batch_table tbody td span');
                batch.forEach(bat => {
                    if (bat.innerText === e.currentTarget.querySelectorAll('td')[4]?.innerText) {
                        bat.classList.add('selected');
                    } else {
                        bat.classList.remove('selected');
                    }

                })
                

            }
            tr.removeEventListener('click',handleClick)
            tr.addEventListener('click', handleClick);
        })
    }

    function addBatch() {
        const drag_table1 = document.querySelector("#drag_table1");
        const batch_by_sample = document.querySelector("input#batch_by_sample");
        const batch_count = document.querySelector("input#batch_count");


        if(drag_table1.children.length < batch_by_sample.value * batch_count.value){
            batch_count.value = 1;
        };


        if(!renderTabs()) return alert("생성 Batch 수는 최대 3개까지 가능합니다.");        
        if (!checkSampleCounts()) {
            const batchBySample = document.querySelector('#batch_by_sample');
            const blankInput = document.querySelector('#blank_box + input');
            const standardInput = document.querySelector('#standard_box + input');
            const controlInput = document.querySelector('#control_box + input');

            const batchNum = Number(batchBySample.value);
            const blankNum = Number(blankInput.value);
            const standardNum = Number(standardInput.value);
            const controlNum = Number(controlInput.value);

            if(96 < blankNum + standardNum + controlNum){
                blankInput.value = 0;
                standardInput.value = 0;
                controlInput.value = 0;
            }else{
                batchBySample.value = 96 - (blankNum + standardNum + controlNum)
            }

            alert("샘플 수는 최대 96개까지 생성 가능합니다.");
            return
        } 
        clearTables()        
        renderBatchTable()
        
        plate_check = document.querySelector('#methodList').checked;
        checked(plate_check)
        
        renderSecBatchTable()
    }
    addBatchBtn.addEventListener('click', addBatch)

    
    
    // step2 일정 및 담당자 설정 
    function renderStepTable(){
        const batch_count = document.querySelector("#batch_count").value;
        if(batch_count == 0){alert("생성 Batch 수가 0입니다."); return;}
        const stepTable = document.querySelector('.step_2_table tbody');
        const tableTr = stepTable.querySelectorAll("tr");
        
        const firstHTML = (count) => {
            return`
            <tr>
                <td>
                    <div class="add_btn_box">
                        <button class="common_btn1 method_btn enroll_btn">Batch #${count}</button>
                        <ul class="add_list_box">
                            <li class="add_list">
                                <button type="button" class="modal_on_btn" data-modal="scheduleContSet">Batch별 설정</button>
                            </li>
                        </ul>
                    </div>
                </td>
                <td title=""></td>
                <td title=""></td>
                <td title=""></td>
                <td title=""></td>
                <td title=""></td>
            </tr>`
        }

        let _innerHTML = "";
        if(tableTr.length == 0){
            for(var i = 0 ; i < batch_count ; i++){
                _innerHTML += firstHTML(i + 1);
            }
        }else if(tableTr.length < batch_count){
            for(var i = 0 ; i < tableTr.length ; i++){
                _innerHTML += `${tableTr[i].outerHTML}`;
            }

            for(var i = 0 ; i < batch_count - tableTr.length ; i++){
                _innerHTML += firstHTML(tableTr.length + i + 1);
            }
        }else if(tableTr.length > batch_count){
            for(var i = 0 ; i < batch_count ; i++){
                _innerHTML += `${tableTr[i].outerHTML}`;
            }
        }else if(tableTr.length == batch_count){
            return;
        }

        stepTable.innerHTML = _innerHTML;
        handleEnroll()
        handleScheduleContSet()
    }
    
    function handleEnroll(){
        const enrollBtn = document.querySelectorAll('.enroll_btn');
        if(enrollBtn) {
            enrollBtn.forEach(item  => {
                item.addEventListener('click', () => {
                    let hasenroll = item.classList.contains('active');
                    if (hasenroll) {
                        item.classList.remove('active')
                    } else {
                        initEnroll();
                        item.classList.add('active');
                    };


                });
            });            
        };

        function initEnroll(){
            for(var i = 0 ; i < enrollBtn.length ; i++){
                if(enrollBtn[i].classList.contains('active')){
                    enrollBtn[i].classList.remove('active')
                }
            }
        }
        md7();
    }

    function md7() {
        const lookUpBtn = document.querySelectorAll('.modal_on_btn');
        const modal = document.querySelectorAll('.modal');
        const main = document.getElementById('main');
        const modalCloseBtn = document.querySelectorAll('.modal_close');
    
        lookUpBtn.forEach((item, index) => {
            let _has = item.classList.contains('modal_on_btn');
            let _id = item.getAttribute('data-modal');
            
            if(_id !== '' && _has) {
                let lookupModalId = document.getElementById(_id);
                let hasOn;
                let _has = lookupModalId.classList.contains('on');
            
                modal.forEach(i => {
                    let an_id = i.getAttribute('id');
                    
                    if(_id !== an_id) {
                        let has = i.classList.contains('on');

                        if(has) {
                            hasOn = an_id;
                            return false;
                        };
                        return false;
                    };
                    return false;
                });

                lookupModalId.addEventListener('click', (e) => {
                    const target = e.target;
                    if(target.classList.contains('modal') === false) {return};

                    if(hasOn === undefined) {
                        main.classList.remove('modal_on');
                        lookupModalId.classList.remove('on');
                    } else {
                        lookupModalId.classList.remove('on');
                    };
                });
            
                item.addEventListener('click', (e) => {
                    e.preventDefault();
                    main.classList.add('modal_on');
                    const scheduleModal =  document.querySelector('#scheduleContSet');       
                    if (item?.parentNode?.parentNode?.previousElementSibling?.innerText) {
                        scheduleModal.dataset.batch_index = item.parentNode.parentNode.previousElementSibling.innerText.slice(-1) - 1;  
                    }      
                    if (item.innerText === "Batch별 설정") {
                        renderScheduleModalByBatch(item);
                        // renderScheduleModalByStep(item)
                    }
                    lookupModalId.classList.add('on');
                    renderStepModal()
                    // renderScheduleModalByStep()
                });
            
                modalCloseBtn.forEach((item) => {
                    item.addEventListener('click', () => {
                        let thisModal = item.parentNode.parentNode.parentNode;
                        thisModal.classList.remove('on');
                        
                        if(hasOn !== undefined) {
                            main.classList.remove('modal_on');
                            return false;
                        };
                    });
                });

            } else {
                return;
            }
        });
        
    };
    
    function setCheckboxEventHandler(){
        const planMangerList = document.querySelector('#plan_manager_list');
        planMangerList.querySelectorAll('.checkbox').forEach((input, index) => {
            input.addEventListener('change', (e) => {
                const target = e.currentTarget;
                if (target.checked) {                    
                    const data = target.parentNode.parentNode.parentNode.parentNode.querySelectorAll('td');
                    planMangerList.querySelector('.manager_name_list').innerText = `${data[1].innerText}(${data[2].innerText})`;
                }
                checkModalInput()
            })
        })

        const manager_list = document.querySelector('#manager_list');
        manager_list.querySelectorAll('.checkbox').forEach((input, index, arr) => {
            input.addEventListener('change', (e) => { 
                let listString = "";               
                arr.forEach((el, i) => {
                    const data = el.parentNode.parentNode.parentNode.parentNode.querySelectorAll('td');
                    if (el.checked) {
                        listString +=`${data[1].innerText}(${data[2].innerText}),`;
                    }
                    checkModalInput()
                })
                
                manager_list.querySelector('.manager_name_list').innerText = listString.slice(0, -1)
            })
        })
    }

    function setInputEventHandler(){
        const dateInputs =  document.querySelectorAll('#scheduleContSet input[type="date"]');
        dateInputs.forEach(item => {
            item.addEventListener('change', (e) => checkModalInput())
        })
        const commentInput = document.querySelector('#scheduleContSet .manage_comments');
        commentInput.addEventListener('keyup', (e) => checkModalInput())
    }

    function checkModalInput(){
        const scheduleModal =  document.querySelector('#scheduleContSet');
        const planMangerList = scheduleModal.querySelector('#plan_manager_list');
        const manager_list = scheduleModal.querySelector('#manager_list');
        const isPlanCheck = [...planMangerList.querySelectorAll('.checkbox')].some(el => el.checked);
        const isManagerCheck = [...manager_list.querySelectorAll('.checkbox')].some(el => el.checked);
        const isDate =  [...scheduleModal.querySelectorAll('input[type="date"]')].filter(el => el.value === "").length === 0;
        const isComment = scheduleModal.querySelector('.manage_comments').value !== "";
        const isSave = isPlanCheck && isManagerCheck && isDate && isComment;
        const saveBtn = scheduleModal.querySelector('.modal_footer .modal_btn');
        if (isSave) {
            saveBtn.classList.remove('off')
            saveBtn.classList.add('on')
        } else {
            saveBtn.classList.remove('on')
            saveBtn.classList.add('off')
        }
    }

    function handleSaveModalInfo(){
        const saveBtn = document.querySelector('#scheduleContSet .modal_footer .modal_btn');
        saveBtn.addEventListener('click', (e) => {
            if (e.currentTarget.classList.contains('on')){
                saveModalInfo()
            } 
        })
    }
    handleSaveModalInfo()  

    function checkDate(){
        const startDate = document.querySelector('input[name="start_date"').value;
        const endDate = document.querySelector('input[name="end_date"').value;

        const start = new Date(startDate)
        const end = new Date(endDate);

        if (start > end) {
            alert("종료일이 시작일보다 과거일 수 없습니다.")
            return false
        } else if (end >= start) {
            return true;
        }
    }

    function saveModalInfo(){
        if (!checkDate()) return;


        const scheduleModal =  document.querySelector('#scheduleContSet');        
        const managerList = document.querySelector('#manager_list .manager_name_list').innerText;
        const supervisor = document.querySelector('#plan_manager_list .manager_name_list').innerText;        
        const date =  [...scheduleModal.querySelectorAll('input[type="date"]')].map(el => el.value);
        const comment = scheduleModal.querySelector('.manage_comments').value;
        const isBatch = scheduleModal.dataset.step_index === 0  
        const isStepModal = document.querySelector('#stepSetting').classList.contains('on');
        const mainManagerTable = document.querySelectorAll('.main_manager_table tbody tr')
        const subManagerTable = document.querySelectorAll('.sub_manager_table tbody tr')
        
        let mainMangerId = "";
        mainManagerTable.forEach(tr => {
            const checked = tr.querySelector('.checkbox')?.checked;
            if (checked) mainMangerId += (tr.dataset.user_id)
        })
        
        let subMangerIds = "";
        subManagerTable.forEach((tr, index) => {
            const checked = tr.querySelector('.checkbox')?.checked;
            if (checked) subMangerIds += (tr.dataset.user_id + ",")
        })
        
        subMangerIds = subMangerIds.slice(0, -1);

        if (!isStepModal) {
            const batchSettings = document.querySelectorAll('.step_2_table tbody tr')[scheduleModal.dataset.batch_index];
            document.querySelectorAll('.enroll_btn').forEach(btn => { 
                btn.classList.remove('active');
            })
            batchSettings.dataset.main_manager_id = mainMangerId
            batchSettings.dataset.sub_manager_ids = subMangerIds 
            batchSettings.dataset.comment = comment;
            batchSettings.querySelectorAll('td')[1].innerText = "Batch별 설정";
            batchSettings.querySelectorAll('td')[2].innerText = splitManageText(supervisor);
            batchSettings.querySelectorAll('td')[3].innerText = splitManageText(managerList);
            batchSettings.querySelectorAll('td')[4].innerText = date[0];
            batchSettings.querySelectorAll('td')[5].innerText = date[1];
            resetModalInfo() 
            scheduleModal.classList.remove('on');
            document.querySelector('#main').classList.remove('modal_on');         
        } else {
            const stepRows = document.querySelectorAll('.step_data_row')[scheduleModal.dataset.step_index];
            stepRows.dataset.main_manager_id = mainMangerId
            stepRows.dataset.sub_manager_ids = subMangerIds 
            stepRows.dataset.comment = comment; 
            stepRows.querySelectorAll('td')[2].innerText = splitManageText(supervisor);           
            stepRows.querySelectorAll('td')[3].innerText = splitManageText(managerList);
            stepRows.querySelectorAll('td')[4].innerText = date[0];
            stepRows.querySelectorAll('td')[5].innerText = date[1];
            resetModalInfo()      
            scheduleModal.classList.remove('on');             
        }    
    }
  
    function saveScheduleInfo(){        
        const saveBtn = document.querySelector('#schedule_save');
        saveBtn.addEventListener('click', (e) => {
            const batchIndex =  document.querySelector('#scheduleContSet').dataset.batch_index;        
            const stepModal =  document.querySelector('#stepSetting');        
            const trs = stepModal.querySelectorAll('.step_data_row');
            
            trs.forEach((tr, index) => {
                const data = tr.querySelectorAll('td');
                const supervisor = tr.querySelectorAll('td')[2].innerText
                const managers = tr.querySelectorAll('td')[3].innerText.split(",");
                const managerList = []
                const subMainIds = tr.dataset.sub_manager_ids?.split(",") ?? []
				
				console.log(supervisor , "supervisor");
				
                const obj = {
                    user_name: "",
                    user_id: 0,
                    mgr_type: 2 //정 1, 부 2
                }
                if (tr.dataset.main_manager_id) {
                    managerList.push({
                        ...obj ,
                        user_name: supervisor,
                        mgr_type: 1,
                        user_id: tr.dataset.main_manager_id 
                    })
                }

                if (subMainIds.length > 0) {
                    subMainIds.forEach((manager, index) => {
                        managerList.push({
                            ...obj ,
                            user_name: managers[index],
                            user_id: manager
                        })
                    })
                }
                
                const rowObj = {
                    index,
                    step_name: data[1].innerText,
                    step_supervisor: data[2].innerText,
                    step_managers: data[3].innerText,
                    batch_index: batchIndex,
                    step_id : tr.dataset.step_id,
                    step_info: data[1].innerText,
                    step_start_date: data[4].innerText,
                    step_end_date: data[5].innerText,                    
                    managerList,
                    comment: tr.dataset.comment,
                    sub_manager_ids: tr.dataset.sub_manager_ids,
                    main_manager_id: tr.dataset.main_manager_id,

                }
                stepDataByBatch[batchIndex][index] = rowObj;
            })
            resetStepModal()    
            stepModal.classList.remove('on')
            document.querySelectorAll('.enroll_btn').forEach(btn => { 
                btn.classList.remove('active');
            })
                   
            renderStepSettings()
            document.querySelector('#main').classList.remove('modal_on');
        })
    }
    

    function renderStepSettings(){
        const step2TableTr = document.querySelectorAll('.step_2_table tbody tr');
        stepDataByBatch.forEach((data, index) => {
            if (data[0]?.step_id) {
                const row = step2TableTr[index].querySelectorAll('td');
                row[1].innerText = "Step별 설정";
            }
        })
    }    

    function renderStepModal(isLoad){
        const scheduleContSet = document.querySelector('#scheduleContSet');
        const stepSetting = document.querySelectorAll('#stepSetting tbody tr');
        const batchIndex = scheduleContSet.dataset.batch_index;
        
        stepSetting.forEach((tr, index) => {                
            if (!stepDataByBatch[batchIndex][index]) return;
            
            tr.querySelectorAll('td')[2].innerText = stepDataByBatch[batchIndex][index]?.step_supervisor ?? "";
            tr.querySelectorAll('td')[3].innerText = stepDataByBatch[batchIndex][index]?.step_managers ?? "";
            tr.querySelectorAll('td')[4].innerText = stepDataByBatch[batchIndex][index]?.step_start_date ?? "";
            tr.querySelectorAll('td')[5].innerText = stepDataByBatch[batchIndex][index]?.step_end_date ?? "";
            tr.dataset.main_manager_id = stepDataByBatch[batchIndex][index].main_manager_id ?? "";
            tr.dataset.sub_manager_ids = stepDataByBatch[batchIndex][index].sub_manager_ids ?? "";
            // tr.dataset.index = stepDataByBatch[batchIndex][index].index ?? "";
            tr.dataset.step_id = stepDataByBatch[batchIndex][index].step_id ?? "";
            tr.dataset.comment = stepDataByBatch[batchIndex][index].comment ?? "";
            
        })     

    }

    function renderScheduleModalByStep(target){
        const supervisor = target.dataset.main_manager_id        
        const managers = target.dataset.sub_manager_ids?.split(",")
        const startDate = target.querySelectorAll('td')[4].innerText;
        const endDate = target.querySelectorAll('td')[5].innerText;
        const scheduleContSet = document.querySelector('#scheduleContSet');
        const planMangerList = scheduleContSet.querySelector('#plan_manager_list .manager_name_list');
        const manager_list = scheduleContSet.querySelector('#manager_list .manager_name_list');

        console.log("managers -- ");
        console.log(managers);

        if (!managers) return;
        scheduleContSet.querySelectorAll('.main_manager_table tbody tr').forEach(tr => {    
            const trId = tr.dataset.user_id;
            if (trId === supervisor) {
                tr.querySelector('.checkbox').checked = true;
                const data = tr.querySelectorAll('td');
                planMangerList.innerText = `${data[1].querySelector('span').innerText}(${data[2].querySelector('span').innerText})`;
            }
            
        })
        let listString = ""; 
        scheduleContSet.querySelectorAll('.sub_manager_table tbody tr').forEach(tr => {    
            const trId = tr.dataset.user_id;
            if (managers.some(el => el === trId)) {
                tr.querySelector('.checkbox').checked = true;
                const data = tr.querySelectorAll('td');
                listString +=`${data[1].querySelector('span').innerText}(${data[2].querySelector('span').innerText}),`;
            }
        })
        manager_list.innerText = listString;

        scheduleContSet.querySelector('input[name="start_date"]').value = startDate;
        scheduleContSet.querySelector('input[name="end_date"]').value = endDate;
        scheduleContSet.querySelector('.manage_comments').value = target.dataset.comment
    }

    function renderScheduleModalByBatch(target){
        const data = target.parentNode.parentNode.parentNode.parentNode.parentNode;
        if(!data.dataset.main_manager_id){
            resetModalInfo();
        }
        renderScheduleModalByStep(data)
    }

    function resetStepModal(){
        const stepModal =  document.querySelector('#stepSetting');  
        const trs = stepModal.querySelectorAll('.step_data_row');
        trs.forEach((tr, index) => {
            const data = tr.querySelectorAll('td');
            data[2].innerText = "";
            data[3].innerText = "";
            data[4].innerText = "";
            data[5].innerText = "";
            tr.dataset.main_manager_id = "";
            tr.dataset.sub_manager_ids = "";
            tr.dataset.comment = "";
        })
    }

    function splitManageText(str){
        const data = str.split(",");
        
        var names = data.length >= 1 ? data.map((v, i) => v.split("(")[0]).join(",") : data.join("")
        
        return names;
    }

    function resetModalInfo(){
        const scheduleModal =  document.querySelector('#scheduleContSet');    
        scheduleModal.querySelectorAll('.checkbox').forEach(el => {
            el.checked = false;
            $(el).removeAttr("checked");

        });       
        document.querySelector('#plan_manager_list .manager_name_list').innerText = ""
        document.querySelector('#manager_list .manager_name_list').innerText = ""
        scheduleModal.querySelectorAll('input[type="date"]').forEach(el => {
            el.value = "";
        })
        scheduleModal.querySelector('.manage_comments').value = "";
    }

    function handleScheduleContSet(){
        setCheckboxEventHandler()
        setInputEventHandler()
    }

    // step2 step별 설정
    let pageStepData;

    function setStepManagers(list) {
        console.log("--------------------------- -- ")
        let html = "";
        let html2 = "";
        list.forEach((manager) => {
			
			let groupArr = manager.groupName.split('/');
			let groupName = groupArr[groupArr.length-1]
			
            const tr = `
            <tr data-user_id="${manager?.account}">
                <td>
                    <div>
                        <label for="" class="check_wrap">
                            <input type="radio" name="kindInfo" id="" class="checkbox"> 
                        </label>
                    </div>
                </td>
                <td>
                    <span>${manager?.name}</span>
                </td>
                <td>
                    <span>${groupName}</span>
                </td>
            </tr>    
            `;
            html += tr;
            const tr2 = `
            <tr data-user_id="${manager?.account}">
                <td>
                    <div>
                        <label for="" class="check_wrap">
                            <input type="checkbox" id="" class="checkbox"> 
                        </label>
                    </div>
                </td>
                <td>
                    <span>${manager?.name}</span>
                </td>
                <td>
                    <span>${groupName}</span>
                </td>
            </tr>    
            `;
            html2 += tr2;
        })        
        const mainManagerTable = document.querySelector('.main_manager_table tbody');
        const subManagerTable = document.querySelector('.sub_manager_table tbody');
        mainManagerTable.innerHTML = html;
        subManagerTable.innerHTML = html2;
        
    }
   
    function getStepDataByMethodId(){
        const methodList = result?.plan?.planMethodList;
        // api/user/search_user
        $.ajax({
            url : `/api/user/search_user`,
            method : "GET",
            dataType : "JSON",            
            success : function(result) {                
                saveScheduleInfo()
                if (Array.isArray(result?.result)){
                    setStepManagers(result?.result)                    
                }             
            },
            error: (err) => {
                console.error(err)
                saveScheduleInfo()
            }
        })
        $.ajax({
            url : `/api/method/step/${methodList[0]?.plan_method_id ?? 0}`,
            method : "GET",
            dataType : "JSON",            
            success : function(result) {                
                if (Array.isArray(result)){
                    document.querySelector('.step_table tbody').innerHTML ="";
                    let html = "";
                    
                    result.forEach((obj, index) => {
                        let template = `
                        <tr class="modal_on_btn step_data_row" data-modal="scheduleContSet" data-index="${index}" data-step_id="${obj?.step_id}">
                            <td><span>${index + 1}</span></td>
                            <td title="${obj.step_name}"><span>${obj.step_name}</span></td>
                            <td title=""><span></span></td>
                            <td title=""><span></span></td>
                            <td title=""><span></span></td>
                            <td title=""><span></span></td>
                        </tr>
                        `;   
                        html += template;
                    })
                    document.querySelector('.step_table tbody').innerHTML = html;
                    pageStepData = result;
                    setStepHandler()
                    handleScheduleContSet()
                }             
            },
            error: (err) => console.error(err)
        })
    }
    getStepDataByMethodId()

    
    function setStepHandler(){
        const stepModal = document.querySelector('#scheduleContSet');
        const stepRows = document.querySelectorAll('.step_data_row');
        stepRows.forEach(row => {
            row.addEventListener('click', function(e){
                stepModal.dataset.step_index = e.currentTarget.dataset.index; 
                renderScheduleModalByStep(e.currentTarget)
            })
        })
    }

    function getSceduleData(){
        const rows = document.querySelectorAll('.step_2_table tbody tr');
        const newArr = []
        let schIds = result.schedule.map((v) => v?.sch_id);

        rows.forEach((row, index) => {
            const supervisor = row.querySelectorAll('td')[2].innerText
            const managers = row.querySelectorAll('td')[3].innerText.split(",");
            const step_id = row.dataset.step_id ?? 0;
            const subManagerId = row.dataset?.sub_manager_ids?.split(",") ?? [];
            let managerList = []
            let StepData;

            let loadScheduleIndex = result.schedule.findIndex(v => index === (v.plate_index - 1))
            let schedule = result.schedule[loadScheduleIndex]
            // let sch_id = schedule?.sch_id ?? -1
            let sch_id = Number(row.dataset.sch_id) || -1
            schIds = schIds.filter(v => v !== sch_id);
            const obj = {
                user_name: "",
                sch_id,
                account: 0,
                mgr_type: 2 //정 1, 부 2
            }            
            if (!row.querySelectorAll('td')[1].innerText) return;
            if (row.querySelectorAll('td')[1].innerText === "Step별 설정"){
                StepData = stepDataByBatch[index].map((v, i) => {
                    const accoutAdd = v.managerList.map((value) => ({
                        ...value, account: value.user_id
                    }))
                    const obj = {
                        ...v, managerList: accoutAdd
                    };
                    return obj
                });

                let newList = [];
                StepData.forEach((data) => {
                    newList = newList.concat(data?.managerList ?? [])
                })
                managerList = newList
                    .filter(v => v.user_id && v.user_name)
                    .map((v) => ({
                        ...v, account: v.user_id
                    }))
            } else {
                managerList.push({
                    ...obj ,
                    user_name: supervisor,
                    account: row.dataset.main_manager_id,
                    mgr_type: 1 
                })
                subManagerId.forEach((id,index) => {
                    if (managers[index]) {
                        managerList.push({
                            ...obj ,
                            user_name: managers[index],
                            account: id,
                            user_id: id
                        })
                    }
                    
                })
            }
            

            newArr.push({
                sch_id,
                step_id,
                plan_id: result.plan.plan_id,
                start_date : row.querySelectorAll('td')[4].innerText,
                end_date : row.querySelectorAll('td')[5].innerText,
                seed_id: row.dataset.seed_id,
                plate_id:0,
                plate_index: index + 1,
                sch_contents: row.dataset.comment,               
                managerList, //관리자 (부)
                StepData: JSON.stringify(StepData) ?? "",
            })
        })    
        return [newArr, schIds.join(",")];
    }

    function getPlanDigital(){
        const plan_well = useCheck.checked ? 1 : 0;
        const plan_batch = parseInt(document.querySelector('#batch_count').value)
        const plan_sample = parseInt(document.querySelector('#batch_by_sample').value)
        const plan_aspect = document.querySelector('#order_select').value === "Vertical" ? 1 : 0;
        const plan_blank = parseInt(document.querySelector('#plan_blank').value) 
        const plan_standard = parseInt(document.querySelector('#plan_standard').value) 
        const plan_bcontrol = parseInt(document.querySelector('#plan_bcontrol').value) 
        return [ plan_well,plan_batch, plan_sample,plan_aspect,plan_blank,plan_standard,plan_bcontrol ]          
    }

    function getRowsBatch(tr) {
        const batch_sample = tr.querySelectorAll('td')[3].innerText
        let batch_test = null;
        let batch_well = null;
        if(plate_check){
    	    batch_test  = tr.querySelectorAll('td')[4].innerText
	        batch_well  = tr.querySelectorAll('td')[5].innerText
		}
        const seed_id  = tr.dataset.seed_id !== "undefined" ? tr.dataset.seed_id : -1;
        const genetic_id  = tr.dataset.id === "0" ? -1 : tr.dataset.id;
        return [batch_sample, batch_test, batch_well, seed_id, genetic_id]
    }

    function getBatchList(){
        const checked = useCheck.checked;
        const batchTables = document.querySelectorAll(`.checked_${checked ? "true" : "false"}_box table`);
        const batchList = []
        batchTables.forEach((table, batch_index) => {
            const trs = table.querySelectorAll('tbody tr');
            trs.forEach((tr, index) => {
                if(!tr.querySelectorAll('td')[1].innerText) return;
                const [ batch_sample, batch_test, batch_well, seed_id, genetic_id ] = getRowsBatch(tr);                                            
                const obj = {
                    batch_index: index,                    
                    batch_sample, 
                    batch_test,
                    batch_well,
                    plate_id: 0, 
                    plate_index : batch_index + 1,
                    seed_id,
                    genetic_id
                }            
                batchList.push(obj)
            })            
        })        
		console.log(batchList)
        return batchList
    }

    function modifyPlanDetailData(){
        const [ plan_well, plan_batch, plan_sample, plan_aspect, plan_blank, plan_standard, plan_control ] = getPlanDigital();
        const [ scheduledata, deleteScheduleList ] = getSceduleData() 
        const batchList = getBatchList()
        const plan_contents = document.querySelector('#plan_contents').value
        const planData = {
            plan_id: result.plan.plan_id,
            scheduleList: JSON.stringify(scheduledata),
            deleteScheduleList,
            batchList: JSON.stringify(batchList),
            plan_well,// 체크 여부 0, 1
            plan_batch,
            plan_sample,
            plan_aspect,
            plan_blank,
            plan_standard,  
            plan_control,
            plan_contents,
        }

        // return;
        $.ajax({
            url : `/analysis/plan-set`,
            method : "POST",
            dataType : "JSON",    
            data: planData,
            success : function(result) {                
                console.log(result, "수정 요청 res");
                alert("수정 성공하셨습니다.")
                window.location.href = "/analysis_plan_list";
            },
            error : (err) => console.error(err)
        })
    }
    document.querySelector('#modify_btn').addEventListener('click', modifyPlanDetailData)
});

