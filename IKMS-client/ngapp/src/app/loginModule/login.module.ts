import {
    CommonModule, NgModule, FormsModule,
    HttpModule, LoginRoutingModule, LoginComponent } from './index';

import {
    InputTextModule, ButtonModule,
    CodeHighlighterModule, MessagesModule,
    CheckboxModule, PasswordModule } from './index';


@NgModule({
  imports: [
    CommonModule,
    InputTextModule,
    ButtonModule,
    CodeHighlighterModule,
    LoginRoutingModule,
    FormsModule,
    HttpModule,
    MessagesModule,
    CheckboxModule,
    PasswordModule
  ],
  declarations: [LoginComponent]
})
export class LoginModule {
}
