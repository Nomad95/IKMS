export class FileFormDataDto {
    description: string;
    folder: string;
    subfolder: string;
    
    constructor(){
        this.description = '';
        this.folder = '';
        this.subfolder = '';
    }
}