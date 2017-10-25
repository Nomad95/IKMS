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
