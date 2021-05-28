function validate() {
    let message = 'Вы пропустили поля для заполнения, проверьте форму'
    let login = $('#userLogin').val();
    let password = $('#userPassword').val();
    if (login === '' || password === '') {
        alert(message);
        return false;
    }
    return true;
}
