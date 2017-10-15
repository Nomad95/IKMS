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
            label: 'Rodzice',
            icon: 'fa-id-card-o',
            items: [
                {
                    label: 'Lista rodziców',
                    routerLink: ['/admin/parent']
                },
                {
                    label: 'siemka2'
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
                    label: 'siemka2'
                }
            ]
        }
    ]
}
