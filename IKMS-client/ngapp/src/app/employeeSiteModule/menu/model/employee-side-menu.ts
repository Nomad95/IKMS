export class EmployeeSideMenu {
    static items = [
        {
            label: 'Pracownicy',
            icon: 'fa-address-card',
            items: [{
                label: 'Lista pracowników',
                routerLink: ['/employee/employee']
            }
            ]
        },
        {
            label: 'Rodzice',
            icon: 'fa-id-card-o',
            items: [
                {
                    label: 'Lista rodziców',
                    routerLink: ['/employee/parent']
                }
            ]
        },
        {
            label: 'Dzieci',
            icon: 'fa-child',
            items: [
                {
                    label: 'Lista dzieci',
                    routerLink: ['/employee/child']
                }
            ]
        },
        {
            label: 'Zarządzanie',
            icon: 'fa-sliders',
            items: [
                {
                    label: 'Grupy',
                    routerLink: ['/employee/group']
                }
            ]
        },
        {
            label: 'Plany',
            icon: 'fa-calendar',
            items: [
                {
                    label: 'Zbiorczy',
                    routerLink: ['/employee/schedule/collective']
                },
                {
                    label: 'Dla pracowników',
                    routerLink: ['/employee/schedule/employee']
                },
                {
                    label: 'Dla dzieci',
                    routerLink: ['/employee/schedule/child']
                },
                {
                    label: 'Dla grup',
                    routerLink: ['/employee/schedule/group']
                }
            ]
        },
        {
            label: 'Pliki',
            icon: 'fa-file-o',
            items: [
                {
                    label: 'Materiały dydaktyczne',
                    routerLink: ['/employee/files/didactic']
                }
            ]
        },
        {
            label: 'Dzienniki',
            icon: 'fa-book',
            items: [
                {
                    label: 'Dzienniki zajęć',
                    routerLink: ['/employee/diary']
                }
            ]
        },
    ]
}
