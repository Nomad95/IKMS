import { Component, OnInit, OnDestroy } from '@angular/core';
import { RegistrationService } from "../../services/registration.service";


@Component({
  selector: 'parent-registration',
  templateUrl: './parent-registration.component.html',
  providers: [RegistrationService]
})

export class ParentRegistrationComponent implements OnInit, OnDestroy {

  constructor(private registrationService: RegistrationService) { }

  ngOnInit() {
  
  }

  ngOnDestroy() {
   
  }


}
