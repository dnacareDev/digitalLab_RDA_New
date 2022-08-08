window.onload = function() {
    // 현재 HTML 이름 + 확장자
    let thisHTMLName = document.URL.substring(document.URL.lastIndexOf('/') + 1, document.URL.length);



    //////////////// home
    
    // input date 기본값
    const dateInput = document.querySelectorAll('.date_select');

    dateInput.forEach((item) => {
        item.value = new Date().toISOString().substring(0, 10);
    });



    //////////////// Method 관리 - Method 관리

    // table page num 클릭시 on / table page prev, next - on
    const pageNumBox = document.querySelectorAll('.page_num');
    const pageBtn = document.querySelectorAll('.page_btn');
    let pageNumBoxLeng = pageNumBox.length;

    pageNumBox.forEach((item, idx) => {
        item.addEventListener('click', () => {
            pageNumBox.forEach((item) => {
                item.classList.remove('on');
            });

            item.classList.add('on');

            if(idx > 0) {
                pageBtn.forEach((items) => {
                    items.classList.remove('on');
                });
                pageBtn[0].classList.add('on');
            } else {
                pageBtn.forEach((items) => {
                    items.classList.remove('on');
                });
                pageBtn[1].classList.add('on');
            };
        });
    });


    // 등록버튼
    const wrap = document.getElementById('wrap');
    const enrollBtn = document.querySelectorAll('.enroll_btn');
    // let hasenroll = enrollBtn.classList.contains('active');
    
    if(enrollBtn) {
        enrollBtn.forEach(item  => {
            item.addEventListener('click', () => {
                let hasenroll = item.classList.contains('active');
                let add = hasenroll ? item.classList.remove('active') : item.classList.add('active');
            });
        });
        
    };

    // list check시 활용버튼 on
    const tableTr = document.querySelectorAll('.method_table > tbody > tr');
    const usesBtn = document.querySelector('.uses_btn');
    
    let _checked = [];
    
    tableTr.forEach((item) => {
        let checkTd = item.firstElementChild;

        if(checkTd !== null ) {
            let check = checkTd.getElementsByTagName('input')[0];

            if(check) {
                check.addEventListener('change', () => {
                    let checked = check.checked;
    
                    if(checked) {
                        usesBtn.classList.remove('off');
                        _checked.push(check);
                    } else {
                        _checked.pop();
    
                        if(_checked.length === 0) {usesBtn.classList.add('off')};
                        return false;
                    };
                });
            };
        };
        return false;
    });

    
    



    

    //////////////// Method 관리 - Method 관리 - 신규등록

    // file 이름, file 추가시 폼요소 추가
    const fileSelect = document.querySelectorAll('.file_select_input');
    const hasFileBox = document.querySelector('.has_file_box');
    
    fileSelect.forEach((item) => {
        let fileName = item.previousElementSibling;

        item.addEventListener('change', () => {
            let _file = item.files[0].name;
            fileName.value = _file;
        });
    });



    // 알림 popup
    const alram = document.querySelector('.alarm');
    
    if(alram !== null) {
        alram.addEventListener('click', () => {
            let has = alram.classList.contains('on');
            has ? alram.classList.remove('on') : alram.classList.add('on');
        });
    };





    // 분석 항목 조회 modal
    if(thisHTMLName !== 'method_management2_new.html' && thisHTMLName !== 'analysis_plan_modify.html') {
        console.log(thisHTMLName);
        let lookUpBtn = document.querySelectorAll('.modal_on_btn');
        
        function md() {
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
                        main.classList.add('modal_on');
                        lookupModalId.classList.add('on');
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
        md();
    };

    // 분석 항목 조회 modal - form
    const lookUpForm = document.querySelector('.lookup_form');
    const lookUphiddForm = document.querySelector('.lookup_form > .form_box');
    let lookUpChild;
    let modalArr = [];
    if(lookUpForm) {
        lookUpChild = lookUpForm.childNodes;

        // lookUpChild.forEach((item, idx) => {
        //     if(item.nodeName !== "#text") {
        //         let select = item.getElementsByTagName('select')[0];
        //         let input = item.getElementsByTagName('input')[0];
        //         let btnWrap = item.parentNode.nextSibling.nextSibling;
        //         let btn = btnWrap.firstElementChild;
                
        //          if(thisHTMLName === 'harvesting_after.html' && idx < 7) {
        //             if(select) {
        //                 select.addEventListener('change', () => {
        //                     let selectOps = select.options[select.selectedIndex].hidden;
        
        //                     modalArr.push(selectOps);
        
        //                     let modalLeng = modalArr.length;
                            
        //                     if(modalLeng === 2) {
        //                         lookUphiddForm.classList.remove('off');
        //                         btn.classList.remove('off');
        //                         modalLeng = [];
        //                     };
        //                 });
        //             };
        //         };

        //         if(thisHTMLName === 'method_management2_new.html' && idx < 9) {
        //             select.addEventListener('change', () => {
        //                 let selectOps = select.options[select.selectedIndex].hidden;
    
        //                 modalArr.push(selectOps);
    
        //                 let modalLeng = modalArr.length;
        
        //                 if(modalLeng === 4) {
        //                     lookUphiddForm.classList.remove('off');
        //                     btn.classList.remove('off');
        //                     modalLeng = [];
        //                 };
        //             });
        //         };
    
        //     };
        // });
    };



    // reagent add modal - form
    // const reagentForm = document.querySelector('.reagent_form');
    // const reagentBtn = document.querySelector('.reagent_modal_btn');
    // let reagentchild;
    // let reagentArr = [];
    // if(reagentForm) {
    //     reagentchild = reagentForm.childNodes;

    //     reagentchild.forEach((item, idx) => {
    //         if(item.nodeName !== "#text") {
    //             let input = item.getElementsByTagName('input')[0];
    //             let select = item.getElementsByTagName('select')[0];
    //             let textarea = item.getElementsByTagName('textarea')[0];
    //             if(idx === 17) {
    //                 let _twoBox = item.getElementsByClassName('modal_inner_box')[0];
    //                 let _innerBox = _twoBox.childNodes;
                    
    //                 _innerBox.forEach((i) => {
    //                     if(i.nodeName !== "#text") { 
    //                         if(i.nodeName === "INPUT") {
    //                             let _val = i.value;
                         
    //                             if(_val === '') {
    //                                 reagentArr.push(_val);
    //                             };
    //                             i.addEventListener('change', () => {
    //                                 let val = i.value;
                                    
    //                                 if(val !== '') {
    //                                     reagentArr.pop();
    //                                 } else {
    //                                     reagentArr.push(val);
    //                                 };
    
    //                                 if(reagentArr.length === 0) {
    //                                     reagentBtn.classList.remove('off');
    //                                     reagentArr = [];
    //                                 } else {
    //                                     reagentBtn.classList.add('off');
    //                                 };
    //                             });
    //                         } else {
    //                             let _ops = i.options[i.selectedIndex];
                        
    //                             if(_ops.hidden) {   
    //                                 reagentArr.push(_ops.text);
    //                             };
            
    //                             select.addEventListener('change', () => {
    //                                 let ops = select.options[i.selectedIndex];
            
    //                                 if(ops.hidden === false) {   
    //                                     reagentArr.pop();
    //                                 } else {
    //                                     reagentArr.push(_ops.text);
    //                                 };
    
    //                                 if(reagentArr.length === 0) {
    //                                     reagentBtn.classList.remove('off');
    //                                     reagentArr = [];
    //                                 } else {
    //                                     reagentBtn.classList.add('off');
    //                                 };
    //                             });
    //                         };
    //                     };
    //                 });
    //             } else {
    //                 if(input) {
    //                     let _val = input.value;
                         
    //                     if(_val === '') {
    //                         reagentArr.push(_val);
    //                     };
    //                     input.addEventListener('change', () => {
    //                         let val = input.value;
                            
    //                         if(val !== '') {
    //                             reagentArr.pop();
    //                         } else {
    //                             reagentArr.push(val);
    //                         };
    
    //                         if(reagentArr.length === 0) {
    //                             reagentBtn.classList.remove('off');
    //                             reagentArr = [];
    //                         } else {
    //                             reagentBtn.classList.add('off');
    //                         };
    //                     });
    //                 } else if(select) {
    //                     let _ops = select.options[select.selectedIndex];
                        
    //                     if(_ops.hidden) {   
    //                         reagentArr.push(_ops.text);
    //                     };
    
    //                     select.addEventListener('change', () => {
    //                         let ops = select.options[select.selectedIndex];
    
    //                         if(ops.hidden === false) {   
    //                             reagentArr.pop();
    //                         }  else {
    //                             reagentArr.push(_ops.text);
    //                         };
    
    //                         if(reagentArr.length === 0) {
    //                             reagentBtn.classList.remove('off');
    //                             reagentArr = [];
    //                         } else {
    //                             reagentBtn.classList.add('off');
    //                         };
    //                     });
    //                 } else if(textarea) {
    //                     let _val = textarea.value;
    
    //                     if(_val === '') {
    //                         reagentArr.push(_val);
    //                     };
    
    //                     textarea.addEventListener('change', () => {
    //                         let val = textarea.value;
    
    //                         if(val !== '') {
    //                             reagentArr.pop();
    //                         } else {
    //                             reagentArr.push(val);
    //                         };
                            
    //                         if(reagentArr.length === 0) {
    //                             reagentBtn.classList.remove('off');
    //                             reagentArr = [];
    //                         } else {
    //                             reagentBtn.classList.add('off');
    //                         };
    //                     });
    
    //                 };
    //             };
    //         };
    //     });
    // };


  



    // 신규 등록 - step 설정 - top 탭메뉴
    const stepTopList = document.querySelectorAll('.setting_top_list');
    const bottomSet = document.querySelectorAll('.bottom_setting');

    const setAddBtn = document.querySelector('.setting_top_add_btn');

    if(thisHTMLName !== 'method_management2_new.html' || thisHTMLName === 'sample_analysis2.html') {
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
                    getModal();
                });
                
                if(idx === 1) {
                    item.classList.add('active');
                    setModal();
                    
                    md();
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
    
                return false;
            });
        });
    };
    

    function setModal() {
        if(thisHTMLName === 'method_management2_new.html') {
            setAddBtn.setAttribute('data-modal', '_reagent');
            setAddBtn.classList.add('modal_on_btn');
            lookUpBtn = document.querySelectorAll('.modal_on_btn');
        };
    };

    function getModal() {
        if(thisHTMLName === 'method_management2_new.html') {
            setAddBtn.removeAttribute('data-modal');
            setAddBtn.classList.remove('modal_on_btn');
            lookUpBtn = document.querySelectorAll('.modal_on_btn');
        };
    };
    



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

    // const send = document.querySelector('.send_pdf_btn');
        
    // if(send !== null) {
    //     send.addEventListener('click', () => {
    //         alert('전송이 완료되었습니다.');
    //     });
    // };


    const sendListBtn = document.querySelectorAll('.send_list_btn');

    if(sendListBtn !== null) {
        sendListBtn.forEach((item, idx) => {
            item.addEventListener('click', () => {
                let sendListTable = item.previousElementSibling;
                let has = sendListTable.classList.contains('on');
    
                let onOff = has ? sendListTable.classList.remove('on') : sendListTable.classList.add('on');
                
            });
        });
    };


    
    // 모달 버튼 off시, disabled.
    const modalBtn = document.querySelectorAll('.modal_btn');

    // let modalBtnObserver = new MutationObserver((mutations) => {
    //         console.log(mutations[0].target);
    //         let has = mutations[0].target.classList.contains('off');
    //
    //         let hasOff = has ? mutations[0].target.disabled = true : mutations[0].target.disabled = false;
    // })

    let option = {
        attributes: true,
        characterData: true
    };

    // if(modalBtn.length !== 0) {
    //     for(let i = 0; i < modalBtn.length; i++) {
    //         let has = modalBtn[i].classList.contains('off');
    //
    //         if(has) {modalBtn[i].disabled = true};
    //         modalBtnObserver.observe(modalBtn[i], option);
    //     };
    // };


};








