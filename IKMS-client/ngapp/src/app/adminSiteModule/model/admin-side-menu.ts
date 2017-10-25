export class AdminSideMenu {
    static items = [
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
            label: 'Użytkownicy',
            icon: 'fa-file-o',
            items: [
                {
                    label: 'Dodaj użytkownika',
                    routerLink: ['/admin/addUser']
                },
                {
                    label: 'Zarządzaj'
                }
            ]
        },
        {
            label: 'Item2',
            icon: 'fa-file-o',
            items: [
                {
                    label: 'siemka1'
                },
                {
                    label: 'siemka2'
                }
            ]
        }
    ]
}
