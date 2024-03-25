const urlUser = '/user/currentUser/';

const currentUser = fetch(urlUser).then(response => response.json())

currentUser.then(user => {
    let userRoles = '';
    user.roles.forEach(role => {
        userRoles += role.name.replace('ROLE_', '') + ' ';
    })
    let result = ''
    result += `<tr>
                <td>${user.id}</td>
                <td>${user.name}</td>
                <td>${user.lastName}</td>
                <td>${user.age}</td>
                <td>${user.email}</td>
                <td>${userRoles}</td>
              </tr>`;
    document.getElementById("user-info-table").innerHTML = result;
    document.getElementById("navbar-email").innerHTML = user.email;
    document.getElementById("navbar-roles").innerHTML = userRoles;
});