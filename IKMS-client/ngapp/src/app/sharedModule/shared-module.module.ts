import {NgModule} from '@angular/core';
import {EnumTranslatePipe} from "../commons/pipes/enum-translate";
import {GroupService} from "./services/group.service";
import {RegistrationService} from "./services/registration.service";
import {PersonalDataService} from "./services/personal-data.service";
import {ParentService} from "./services/parent.service";
import {EmployeeService} from "./services/employee.service";
import {AddressService} from "./services/address.service";
import {ChildrenService} from "./services/children.service";
import {NotificationService} from "./services/notification.service";


@NgModule({
    declarations: [
        EnumTranslatePipe
    ],
    imports: [
    ],
    providers: [
            GroupService,
            RegistrationService,
            PersonalDataService,
            ParentService,
            EmployeeService,
            AddressService,
            ChildrenService,
            NotificationService
    ],
    exports: [EnumTranslatePipe],
})
export class SharedModule {
}
