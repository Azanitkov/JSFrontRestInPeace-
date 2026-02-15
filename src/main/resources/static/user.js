function buildHeader() {
    fetch('user/userPage').then(response => response.json()).then(user => {
        const formatedRole = user.roles .map(role => role.name.replace('ROLE_', ' ')).join(' ')
        document.getElementById('pageHeader')
            .innerHTML = '<b>' + user.email + '</b>'+ " with role " + formatedRole;
    })
}

buildHeader()

function buildInfo(){
    fetch('/user/userPage').then(response => response.json()).then(user =>{
        document.getElementById('userInfo').innerHTML = '<td>' +user.id+'</td>'+
            '<td>'+user.name+'</td>'+
            '<td>'+user.surname+'</td>'+
            '<td>'+user.age+'</td>'+
            '<td>'+user.email+'</td>'+
            '<td>'+user.roles.map(role => role.name+' ').join(' ')+'</td>'
    })
}

buildInfo()
function buildNav(){
    fetch('/user/getCurrentUser').then(response => response.json()).then(user => {
        document.getElementById('userRolesLink').innerHTML =
       user.roles.map(role => '<a class="nav-link' + (role.name === 'ROLE_USER' ? ' active" ' : '" ') + 'href="/' + role.name.toLowerCase().replace("role_", "") + '" role="tab">' + role.name.charAt(0).toUpperCase() + role.name.substring(1).toLowerCase() + '</a>').join('\n')
    })
}

buildNav()