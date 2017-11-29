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
                {
                    label: 'Lista użytkowników',
                    routerLink: ['/admin/user']
                }
            ]
        },
        {
            label: 'Pracownicy',
            icon: 'fa-address-card',
            items: [{
                label: 'Lista pracowników',
                routerLink: ['/admin/employee']
                }
            ]
        },
        {
            label: 'Rodzice',
            icon: 'fa-id-card-o',
            items: [
                {
                    label: 'Lista rodziców',
                    routerLink: ['/admin/parent']
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
        },
        {
            label: 'Zarządzanie',
            icon: 'fa-sliders',
            items: [
                {
                    label: 'Grupy',
                    routerLink: ['/admin/group']
                },
                {
                    label: 'Dodaj grupę',
                    routerLink: ['/admin/group/new']
                }
            ]
        },
        {
            label: 'Plany',
            icon: 'fa-calendar',
            items: [
                {
                    label: 'Zbiorczy',
                    routerLink: ['/admin/schedule/collective']
                },
                {
                    label: 'Dla pracowników',
                    routerLink: ['/admin/schedule/employee']
                },
                {
                    label: 'Dla dzieci',
                    routerLink: ['/admin/schedule/child']
                },
                {
                    label: 'Dla grup',
                    routerLink: ['/admin/schedule/group']
                }
            ]
        },
        {
            label: 'Powiadomienia',
            icon: 'fa-bell',
            items: [
                {
                    label: 'Powiadomienia',
                    routerLink: ['/admin/notification']
                }
            ]
        }
    ]
}
