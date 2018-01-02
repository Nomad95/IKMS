export class ParentSideMenu {
    static items = [
        {
            label: 'Pracownicy',
            icon: 'fa-address-card',
            items: [{
                label: 'Lista pracowników',
                routerLink: ['/parent/employee']
            }
            ]
        },
        {
            label: 'Plany',
            icon: 'fa-calendar',
            items: [
                {
                    label: 'Dla dzieci',
                    routerLink: ['/parent/schedule/child']
                },
                {
                    label: 'Dla grup',
                    routerLink: ['/parent/schedule/group']
                }
            ]
        },
        {
            label: 'Pliki',
            icon: 'fa-file-o',
            items: [
                {
                    label: 'Materiały dydaktyczne',
                    routerLink: ['/parent/files/didactic']
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
