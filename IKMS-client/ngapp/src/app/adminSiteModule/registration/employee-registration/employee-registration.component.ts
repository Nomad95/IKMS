import { Component, OnInit, OnDestroy } from '@angular/core';
import { RegistrationService } from "../../services/registration.service";


@Component({
  selector: 'employee-registration',
  templateUrl: './employee-registration.component.html',
  providers: [RegistrationService]
})

export class EmployeeRegistrationComponent implements OnInit, OnDestroy {

  constructor(private registrationService: RegistrationService) { }

  ngOnInit() {
  
  }

  ngOnDestroy() {
   
  }


}
