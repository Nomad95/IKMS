export class MessageBoxMenu {
  static items = [{
    label: 'Wiadomości',
    items: [
      {
        id: 'inbox',
        label: 'Odebrane',
        icon: 'fa-inbox',
        routerLink: [ , ],
        queryParams: {type: 'inbox'}
      },
      {
        id: 'outbox',
        label: 'Wysłane',
        icon: 'fa-folder',
        routerLink: [ , ],
        queryParams: {type: 'outbox'}
      }
    ]
  }];

}
