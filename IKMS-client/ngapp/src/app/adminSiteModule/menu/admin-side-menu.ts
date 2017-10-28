export class AdminSideMenu {
    static items = [
        {
            label: 'Użytkownicy',
            icon: 'fa-users',
            items: [
                {
                    label: 'Dodaj użytkownika',
                    routerLink: ['/admin/addUser']
                },
            ]
        },
        {
            label: 'Pracownicy',
            icon: 'fa-address-card',
            items: [{
                label: 'Lista pracowników',
                routerLink: ['/admin/employee']
                },
                    {label: 'Open'},
                    {label: 'Quit'}
            ]
        },
        {
            label: 'Rodzice',
            icon: 'fa-id-card-o',
            items: [
              {
                  label: 'Lista rodziców',
                    routerLink: ['/admin/parent']
                },
                {
                    label: 'Zarządzaj'
                }
            ]
        },
        {
            label: 'Dzieci',
            icon: 'fa-child',
            items: [
                {
                    label: 'Lista dzieci',
                    routerLink: ['/admin/child']
                },
                {
                    label: 'Dodaj dziecko',
                    routerLink: ['/admin/child/new']
                }
            ]
        }
    ]
}
