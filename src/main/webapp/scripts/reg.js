function validate() {
    let message = 'Вы пропустили поля для заполнения, проверьте форму'
    let name = $('#userName').val();
    let login = $('#userLogin').val();
    let password = $('#userPassword').val();
    if (name === '' || login === '' || password === '') {
        alert(message);
        return false;
    }
    return true;
}
