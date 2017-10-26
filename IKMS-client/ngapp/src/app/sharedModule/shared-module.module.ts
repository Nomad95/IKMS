import {NgModule} from '@angular/core';
import {EnumTranslatePipe} from "../commons/pipes/enum-translate";


@NgModule({
    declarations: [
        EnumTranslatePipe
    ],
    imports: [
    ],
    providers: [],
    exports: [EnumTranslatePipe],
})
export class SharedModule {
}
