export class ParentSideMenu {
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
            label: 'Item1',
            icon: 'fa-file-o',
            items: [
                {
                    label: 'siemka1'
                },
                {
                    label: 'siemka2'
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
