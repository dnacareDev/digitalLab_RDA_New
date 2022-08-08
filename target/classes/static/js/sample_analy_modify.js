window.addEventListener('DOMContentLoaded', () => {
    const stepTopList = document.querySelectorAll('.setting_top_list');
    const bottomSet = document.querySelectorAll('.bottom_setting');
    const contFooter = document.querySelectorAll('.cont_footer_btnbox');

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

            contFooter.forEach(item => {
                let dataId = item.getAttribute('data-footer');
                let tableId = document.getElementById(setId);
                let tableBtn1 = tableId.getAttribute('data-list');
                let tableBtn2 = tableId.getAttribute('data-down');
                let listBtn = item.querySelector('.list_btn');
                let downBtn = item.querySelector('.download_btn');

                if(dataId == setId) {
                    item.style.display = 'flex';

                    tableBtn1 === 'true' ?  listBtn.classList.remove('off') :  listBtn.classList.add('off');
    
                    tableBtn2 === 'true' ?  downBtn.classList.remove('off') :  downBtn.classList.add('off');
                };
            }); 
        };

        item.addEventListener('click', () => {
            let setId = item.getAttribute('data-setting');

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
            
            contFooter.forEach(items => {items.style.display = 'none'}); 

            contFooter.forEach(items => {
                let dataId = items.getAttribute('data-footer');
                let tableId = document.getElementById(setId);
                let tableBtn1 = tableId.getAttribute('data-list');
                let tableBtn2 = tableId.getAttribute('data-down');
                let listBtn = items.querySelector('.list_btn');
                let downBtn = items.querySelector('.download_btn');

                if(dataId == setId) {
                    items.style.display = 'flex';
            
                    tableBtn1 === 'true' ?  listBtn.classList.remove('off') :  listBtn.classList.add('off');
    
                    tableBtn2 === 'true' ?  downBtn.classList.remove('off') :  downBtn.classList.add('off');
                };

              
            }); 
            
            return false;
        });
    });
});