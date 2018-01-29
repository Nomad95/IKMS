import { UserRegistrationDTO } from "./userRegistrationDTO";
import { PersonalData } from "../personalData/personal-data";
import { Address } from "../address/address";
import { Employee } from "../employee/employee";
import { Parent } from "../../menu/model/parent/parent";

export class RegistrationDto {
    userRegistrationDto: UserRegistrationDTO;
    personalData: PersonalData;
    addressess: Address[];
    employee: Employee;
    parent: Parent;
    
    constructor() {
    }
}