import {NgModule} from "@angular/core";
import {SharedModule} from "../sharedModule/shared-module.module";
import {MessageInboxComponent} from "./messagebox/inbox/message-inbox.component";
import {MessageOutboxComponent} from "./messagebox/outbox/message-outbox.component";
import {MessageBoxComponent} from "./messagebox/messagebox.component";
import {
  BreadcrumbModule,
  ButtonModule, CalendarModule, CheckboxModule, CodeHighlighterModule, ConfirmDialogModule, DataListModule,
  DataTableModule,
  DialogModule, DropdownModule,
  FieldsetModule,
  GrowlModule,
  InputMaskModule, InputTextareaModule,
  InputTextModule, MegaMenuModule, MenubarModule,
  MenuModule, MessagesModule, MultiSelectModule, PaginatorModule, PanelMenuModule, PanelModule, PickListModule,
  TabViewModule,
  TooltipModule
} from "primeng/primeng";
import {NotificationComponent} from "./notification/notificiationList/notification-list.component";
import {SendingNotificationComponent} from "./notification/sendingNotification/sending-notification.component";
import {HttpModule} from "@angular/http";
import {FormsModule} from "@angular/forms";
import {CommonModule} from "@angular/common";
import {DetailsInboxComponent} from "./messagebox/details/details-inbox.component";
import {DetailsOutboxComponent} from "./messagebox/details/details-outbox.component";
import {SendingMessageComponent} from "./messagebox/sending/sending-message.component";

@NgModule({
  declarations: [
    MessageInboxComponent,
    MessageOutboxComponent,
    MessageBoxComponent,
    DetailsInboxComponent,
    DetailsOutboxComponent,
    SendingMessageComponent,
    NotificationComponent,
    SendingNotificationComponent
  ],
  imports: [
    SharedModule,
    CheckboxModule,
    MenuModule,
    HttpModule,
    ButtonModule,
    InputTextModule,
    DropdownModule,
    InputMaskModule,
    DialogModule,
    ConfirmDialogModule,
    GrowlModule,
    TooltipModule,
    FormsModule,
    MultiSelectModule,
    CommonModule,
    SharedModule,
    FormsModule,
    HttpModule,
    PanelMenuModule,
    DataTableModule,
    ButtonModule,
    MessagesModule,
    TabViewModule,
    InputTextModule,
    CodeHighlighterModule,
    MegaMenuModule,
    DropdownModule,
    PaginatorModule,
    InputMaskModule,
    DialogModule,
    PanelModule,
    CalendarModule,
    ConfirmDialogModule,
    GrowlModule,
    BreadcrumbModule,
    TooltipModule,
    InputTextareaModule,
    PickListModule,
    MenubarModule,
    DataListModule,
    FieldsetModule,
    CheckboxModule
  ],
  providers: [

  ],
  exports: [],
})
export class CommunicationModule {
}
