export class EmployeeSideMenu {
    static items = [
        {
            label: 'File',
            icon: 'fa-file-o',
            items: [{
                label: 'New',
                icon: 'fa-plus',
                items: [
                    {label: 'Project'},
                    {label: 'Other'},
                ]
                },
                    {label: 'Open'},
                    {label: 'Quit'}
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
        }
    ]
}
