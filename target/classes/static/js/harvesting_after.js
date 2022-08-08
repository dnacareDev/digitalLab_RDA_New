document.addEventListener('DOMContentLoaded', (e) => {
      // 분석 항목 조회 modal
      
      function md2() {
          const main = document.getElementById('main');
          const modalCloseBtn = document.querySelectorAll('.modal_close');
          let lookUpBtn = document.querySelectorAll('.modal_on_btn');
      
          lookUpBtn.forEach((item) => {
              let _has = item.classList.contains('modal_on_btn');
              let _id = item.getAttribute('data-modal');
              
              if(_id !== '' && _has) {
                  let lookupModalId = document.getElementById(_id);
          
                  let _has = lookupModalId.classList.contains('on');
              
                  lookupModalId.addEventListener('click', (e) => {
                      let target = e.target;
                      if(target.classList.contains('modal') === false) {return};
              
                      lookupModalId.classList.remove('on');
                      main.classList.remove('modal_on');
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
                      });
                  });
              } else {
                  return;
              }
          });
      };

    // 과제 비연계 체크시, select off
    const taskCheckBox = document.getElementById('task_unrelated');
    const taskBox = document.querySelector('.task_box');

    if(taskBox !== null) {
        const taskSelect = taskBox.getElementsByTagName('select')[0];
    
        if(taskCheckBox !== null) {
            taskCheckBox.addEventListener('change', (e) => {
                let target = e.target;
        
                if(target.checked) {
                    taskBox.classList.add('off');
                    taskSelect.disabled = true;
                } else {
                    taskBox.classList.remove('off');
                    taskSelect.disabled = false;
                };
            });
        }
    };


    const test = document.querySelectorAll('.modal_on_btn');
    


    // 폼요소 다 입력시 hidden 요소 보여짐
    let fileDataForm = document.querySelectorAll('.has_file_card > .card_cont_box');
    let idx = 1;

    function addRemoveForm(e) {
        let target = e.target;
        let has = target.classList.contains('remove');

        if(has) {
            let parent = target.parentNode.parentNode;
            let reForm = parent.nextElementSibling;
            let nextForm = parent.nextElementSibling.nextElementSibling;
            if(nextForm === null) {
                target.classList.remove('remove');
                target.innerText = '추가';
            };
            
            reForm.remove();
            
            fileDataForm = document.querySelectorAll('.has_file_card > .card_cont_box');
            
            fileDataForm.forEach((item) => {
               let addBtn = item.lastElementChild.lastElementChild;
               addBtn.addEventListener('click', addRemoveForm); 
           });
        };

        if(has === false) {
            target.classList.add('remove');
            target.innerText = '뺴기';

            let parent = target.parentNode.parentNode;

            let cloneForm = parent.cloneNode(true);
			
			// 삭제버튼 삭제
			if(cloneForm.querySelector('.appl_regist_box') != null){
				cloneForm.querySelector('.appl_regist_box').remove();
			}
            // 시료 ID 박스 삭제
            if(cloneForm.querySelector('.sample_box') != null){
				cloneForm.querySelector('.sample_box').remove();
			}
            // 추가된 시드 벨류 초기화
            
            if(cloneForm.querySelector('.seed_id') != null){
	            cloneForm.querySelector('.seed_id').value = ''
			}
            
            cloneForm.classList.remove('add_form' + (idx - 1));
            cloneForm.classList.add('add_form' + idx);

            let cloneBtn = cloneForm.lastElementChild.lastElementChild;
            cloneBtn.classList.remove('remove');
            cloneBtn.innerText = '추가';

            parent.parentNode.appendChild(cloneForm);
            idx += 1;

            fileDataForm = document.querySelectorAll('.has_file_card > .card_cont_box');
          
            fileDataForm.forEach((item) => {
                let addBtn = item.lastElementChild.lastElementChild;
                addBtn.addEventListener('click', addRemoveForm); 
                md2();
            });
        };
    };

    fileDataForm.forEach((item) => {
         // 추가 버튼 클릭시 추가
        let addBtn = item.lastElementChild.lastElementChild;

        addBtn.addEventListener('click', addRemoveForm); 
    });


    
    const saveBtn = document.querySelector('.save_btn');
    const hasFileCard = document.querySelector('.has_file_card');
    const saveTableCard = document.querySelector('.save_table_card');
    const flieBox = document.querySelector('.file_box');

    if(saveTableCard) {
        saveBtn.addEventListener('click', () => {
            let has = saveTableCard.classList.contains('on');
            let _has = hasFileCard.classList.contains('on');
    
            if(flieBox.style.display === 'none') {
                if(_has) {
                    hasFileCard.classList.remove('on');
                    saveTableCard.classList.add('on');
                } else {
                    return false;
                };
            } else {
                return false;
            };
        });
    };








});

    

