import { Component, OnInit, OnDestroy } from '@angular/core';
import { RegistrationService } from "../../services/registration.service";


@Component({
  selector: 'admin-registration',
  templateUrl: './admin-registration.component.html',
  providers: [RegistrationService]
})

export class AdminRegistrationComponent implements OnInit, OnDestroy {

  constructor(private registrationService: RegistrationService) { }

  ngOnInit() {
  
  }

  ngOnDestroy() {
   
  }


}
