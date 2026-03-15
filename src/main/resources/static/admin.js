function buildHeader() {
    fetch('user/userPage').then(response => response.json()).then(user => {
        const formatedRole = user.roles .map(role => role.name.replace('ROLE_', ' ')).join(' ')
        document.getElementById('pageHeader')
            .innerHTML = '<b>' + user.email + '</b>'+ " with role " + formatedRole;
    })
}

buildHeader()

function buildNav() {
    fetch('/user/userPage').then(response => response.json()).then(user => {
        document.getElementById('userRolesLink').innerHTML =
            user.roles.map(role => '<a class="nav-link' + (role.name === 'ROLE_ADMIN' ? ' active" ' : '" ') + 'href="/' + role.name.toLowerCase().replace("role_", "") + '" role="tab">' + role.name.charAt(0).toUpperCase() + role.name.substring(1).toLowerCase() + '</a>').join('\n')
    })
}

buildNav()

function buildTable() {
    fetch('/admin/showAllUsers').then(response => response.json()).then(users => {
        document.getElementById('tableBody').innerHTML = users.map(user => '<tr>' +
            `<td>${user.id}</td>
            <td>${user.name}</td>
            <td>${user.surname}</td>
            <td>${user.age}</td>
            <td>${user.email}</td>` +
            '<td>' + user.roles.map(role => role.name).join(' ') + '</td>' +
            '<td><button type="button" class="btn btn-primary" data-toggle="modal" id="editButton" data-target="#editModal">Edit</button></td>' +
            '<td><button type="submit" class="btn btn-danger" data-toggle="modal" id="deleteButton" data-target="#deleteModal">Delete</button></td>' +
            '</tr>').join('\n')

    })
}

buildTable()

function buildNewRoles() {
    fetch('/admin/getAllRoles').then(response => response.json()).then(roles => {
        document.getElementById('newRoles').innerHTML = roles.map(role => `<option>${role.name}</option>`).join('\n')
    })
}

buildNewRoles()
function buildEditRoles() {
    fetch('/admin/getAllRoles').then(response => response.json()).then(roles => {
        document.getElementById('newRoles').innerHTML = roles.map(role => `<option>${role.name}</option>`).join('\n')
    })
}
buildEditRoles()


const listenClick = (selector, handler) => {
    document.addEventListener('click', e => {
        if (e.target.closest(selector)) {
            handler(e);
        }
    });
};

listenClick('#editButton', e => {
    let target = e.target.parentNode.parentNode
    document.getElementById('editId').value = target.children[0].innerHTML
    document.getElementById('editName').value = target.children[1].innerHTML
    document.getElementById('editSurname').value = target.children[2].innerHTML
    document.getElementById('editAge').value = target.children[3].innerHTML
    document.getElementById('editEmail').value = target.children[4].innerHTML
    document.getElementById('editPassword').value = ''
    fetch('/admin/getAllRoles').then(response => response.json()).then(roles => {
            document.getElementById('editRoles').innerHTML = roles.map(role => target.children[5].innerHTML.includes(role.name) ? `<option selected>${role.name}</option>` : `<option>${role.name}</option>`).join('\n')
        }
    )
})

listenClick('#deleteButton', e => {
    let target = e.target.parentNode.parentNode
    document.getElementById('deleteId').value = target.children[0].innerHTML
    document.getElementById('deleteName').value = target.children[1].innerHTML
    document.getElementById('deleteSurname').value = target.children[2].innerHTML
    document.getElementById('deleteAge').value = target.children[3].innerHTML
    document.getElementById('deleteEmail').value = target.children[4].innerHTML
    fetch('/admin/getAllRoles').then(response => response.json()).then(roles => {
            document.getElementById('deleteRoles').innerHTML = roles.map(role => target.children[5].
            innerHTML.includes(role.name) ? `<option selected>${role.name}</option>` : `<option>${role.name}</option>`).join('\n')
        }
    )
})


let roleArray = (select) => {
    const options = Array.from(select.selectedOptions).map(option => option.value)
    let roles = [];
    for (let i = 0; i < options.length; i++) {
        let role = {id:1, name: options[i]}
        roles.push(role)
    }
    return roles;
}


listenClick('#editFormButton', e => {
    e.preventDefault()
    let setRoles = roleArray(document.querySelector('#editRoles'))
    fetch('/admin/editUser', {
        method: 'PUT',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify({
                id: document.getElementById('editId').value,
                name: document.getElementById('editName').value,
                surname: document.getElementById('editSurname').value,
                age: document.getElementById('editAge').value,
                email: document.getElementById('editEmail').value,
                password: document.getElementById('editPassword').value,
                roles: setRoles
            }
        )
    }).then(buildTable)
    $('#editModal').modal('hide')
})


listenClick('#deleteFormButton', e => {
    e.preventDefault()
    let setRoles = roleArray(document.querySelector('#deleteRoles'))
    fetch(`/admin/deleteUser`, {
        method: 'DELETE',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify({
                id: document.getElementById('deleteId').value,
                name: document.getElementById('deleteName').value,
                surname: document.getElementById('deleteSurname').value,
                age: document.getElementById('deleteAge').value,
                email: document.getElementById('deleteEmail').value,
                password: '',
                roles: setRoles
            }
        )
    }).then(buildTable)
    $('#deleteModal').modal('hide')
})

listenClick('#newUserButton', e => {
    e.preventDefault();
    let user ={
        name: document.getElementById('newName').value,
        surname: document.getElementById('newSurname').value,
        age: document.getElementById('newAge').value,
        email: document.getElementById('newEmail').value,
        password: document.getElementById('newPassword').value,
    };
    let setRoles = roleArray(document.getElementById('newRoles'));
    let rolesIds = setRoles.map(role => role.id);
    let fetchBody = JSON.stringify({
        user: user,
        rolesIds: rolesIds
    })
    fetch('/admin/addUser', {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: fetchBody
    }).then(buildTable)
    $('#myTab a[href="#nav-users"]').tab('show')
})


