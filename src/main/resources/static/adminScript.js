const urlUsers = '/admin/users/';
const urlRoles = '/admin/roles/';

const allUsers = fetch(urlUsers, {method: 'GET'}).then(response => response.json());
const allRoles = fetch(urlRoles).then(response => response.json());

const deleteUserModal = new bootstrap.Modal(document.getElementById('deleteUserModal'));
const editUserModal = new bootstrap.Modal(document.getElementById('editUserModal'));

const on = (element, event, selector, handler) => {
    element.addEventListener(event, e => {
        if (e.target.closest(selector)) {
            handler(e.target);
        }
    });
}

// Заполнение таблицы пользователей
allUsers.then(users => {
    let result = '';
    users.forEach(user => {
        let userRoles ='';
        user.roles.forEach(role => {
            userRoles += role.name.replace('ROLE_', '') + ' ';
        });

        result += `<tr>
                    <td>${user.id}</td>
                    <td>${user.name}</td>
                    <td>${user.lastName}</td>
                    <td>${user.age}</td>
                    <td>${user.email}</td>
                    <td>${userRoles}</td>
                    <td>
                        <button type="button" class="btn btn-info btn-sm text-white" id="editUserBtn">Edit</button>
                    </td>
                    <td>
                        <button type="button" class="btn btn-danger btn-sm" id="deleteUserBtn">Delete</button>
                    </td>
                   </tr>`;
    });
    document.getElementById('users-info-table').innerHTML = result;
});

// Функция заполнения ролей
fillRoles = function (elementId) {
    allRoles.then(roles => {
        let result = '';
        roles.forEach(role => {
           result += `<option value = ${role.id}>${role.name.replace('ROLE_', '')}</option>`;
        });
        document.getElementById(elementId).innerHTML = result;
    });
}

fillRoles("role_select");

//Добавление нового пользователя
document.getElementById('newUserForm').addEventListener('submit', (e) => {
    e.preventDefault();

    let nameNew = document.getElementById('firstName_New').value;
    let lastNameNew = document.getElementById('lastName_New').value;
    let ageNew = document.getElementById('age_New').value;
    let emailNew = document.getElementById('email_New').value;
    let passwordNew = document.getElementById('password_New').value;

    let role = document.getElementById('role_select');
    let rolesAddUser = [];
    let rolesAddUserValue = '';
    for (let i = 0; i < role.options.length; i++) {
        if (role.options[i].selected) {
            rolesAddUser.push({id: role.options[i].value, name: 'ROLE_' + role.options[i].innerHTML});
            rolesAddUserValue += role.options[i].innerHTML + ' ';
        }
    }

    let userNew = {
        name: nameNew,
        lastName: lastNameNew,
        age: ageNew,
        email: emailNew,
        password: passwordNew,
        roles: rolesAddUser
    }

    let response = fetch(urlUsers, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json; charset=utf-8'
        },
        body: JSON.stringify(userNew)
    });
        response.then(response => response.json())
        .then(user => {
            let newRow = document.createElement('tr');
            newRow.innerHTML += `<tr>
                           <td>${user.id}</td>
                           <td>${user.name}</td>
                           <td>${user.lastName}</td>
                           <td>${user.age}</td>
                           <td>${user.email}</td>
                           <td>${rolesAddUserValue}</td>
                           <td><button type="button" class="btn btn-info btn-sm text-white" id="editUserBtn">Edit</button></td>
                           <td><button type="button" class="btn btn-danger btn-sm" id="deleteUserBtn">Delete</button></td>
                           </tr>`;
            document.getElementById('users-info-table').appendChild(newRow);
            document.getElementById('newUserForm').reset();
        });
   document.getElementById("all-users-tab").click();
});

// Изменение пользователя
const idEdit = document.getElementById('id_edit');
const firstNameEdit = document.getElementById('firstName_edit');
const lastNameEdit = document.getElementById('lastName_edit');
const ageEdit = document.getElementById('age_edit');
const emailEdit = document.getElementById('email_edit');
const passwordEdit = document.getElementById('password_edit');
const rolesEdit = document.getElementById('role_edit');

let rowEdit = null;

on(document, 'click', '#editUserBtn', e => {
    rowEdit = e.parentNode.parentNode;

    idEdit.value = rowEdit.children[0].innerHTML;
    firstNameEdit.value = rowEdit.children[1].innerHTML;
    lastNameEdit.value = rowEdit.children[2].innerHTML;
    ageEdit.value = rowEdit.children[3].innerHTML;
    emailEdit.value = rowEdit.children[4].innerHTML;
    passwordEdit.value = '';
    let option = '';
    allRoles.then(roles => {
        roles.forEach(role => {
            let selected = rowEdit.children[5].innerHTML.includes(role.name.replace('ROLE_', '')) ? 'selected' : '';
            option += `<option value="${role.id}" ${selected}>${role.name.replace('ROLE_', '')}</option>`;
        });
        rolesEdit.innerHTML = option;
    });
    editUserModal.show();
});

document.getElementById('edit_user_form').addEventListener('submit', (e) => {
    e.preventDefault();
    let role = document.getElementById('role_edit');
    let rolesUserEdit = [];
    let rolesUserEditValue = '';
    for (let i = 0; i < role.options.length; i++) {
        if (role.options[i].selected) {
            rolesUserEdit.push({id: role.options[i].value, name: 'ROLE_' + role.options[i].innerHTML});
            rolesUserEditValue += role.options[i].innerHTML + ' ';
        }
    }
    fetch(urlUsers, {
        method: 'PATCH',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            id: idEdit.value,
            name: firstNameEdit.value,
            lastName: lastNameEdit.value,
            age: ageEdit.value,
            email: emailEdit.value,
            password: passwordEdit.value,
            roles: rolesUserEdit
        })
    })
        .then(response => response.json());
    rowEdit.children[0].innerHTML = idEdit.value;
    rowEdit.children[1].innerHTML = firstNameEdit.value;
    rowEdit.children[2].innerHTML = lastNameEdit.value;
    rowEdit.children[3].innerHTML = ageEdit.value;
    rowEdit.children[4].innerHTML = emailEdit.value;
    rowEdit.children[5].innerHTML = rolesUserEditValue;
    editUserModal.hide();
});

// Удаление пользователя
let rowDelete = null
on(document, 'click', '#deleteUserBtn', e => {
    rowDelete = e.parentNode.parentNode;
    document.getElementById('id_delete').value = rowDelete.children[0].innerHTML;
    document.getElementById('firstName_delete').value = rowDelete.children[1].innerHTML;
    document.getElementById('lastName_delete').value = rowDelete.children[2].innerHTML;
    document.getElementById('age_delete').value = rowDelete.children[3].innerHTML;
    document.getElementById('email_delete').value = rowDelete.children[4].innerHTML;

    let option = '';
    allRoles.then(roles => {
        roles.forEach(role => {
            if (rowDelete.children[5].innerHTML.includes(role.name.replace('ROLE_', ''))) {
                option += `<option value="${role.id}">${role.name.replace('ROLE_', '')}</option>`;
            }
        });
        document.getElementById('role_delete').innerHTML = option;
    });
    deleteUserModal.show();
});

document.getElementById('delete_user_form').addEventListener('submit', (e) => {
    e.preventDefault();
    fetch(urlUsers + rowDelete.children[0].innerHTML, {
        method: 'DELETE'
    }).then(() => {
        deleteUserModal.hide();
        rowDelete.parentNode.removeChild(rowDelete);
    });
});