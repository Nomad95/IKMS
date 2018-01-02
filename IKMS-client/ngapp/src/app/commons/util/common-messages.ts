export class CommonMessages {

    public static roleFetchingWentWrong(){
        return [{severity:'error', detail: 'Wystąpił błąd podczas logowania'}];
    }
    
    public static employeeDeletingError(){
        return [{severity:'error', detail: 'Nie można usunąć tego użytkownika'}];
    }
    
    public static userDeletingError(){
        return [{severity:'error', detail: 'Nie można usunąć tego użytkownika'}];
    }
    
    public static childDeletingError(){
        return [{severity:'error', detail: 'Nie można usunąć tego dziecka'}];
    }
    
    public static childCreatingSuccess(name){
        return [{severity:'success', detail: 'Dodano dziecko ' + name}];
    }
    
    public static childCreatingError(){
        return [{severity:'error', detail: 'Wystąpił błąd podczas dodawania dziecka'}];
    }
    
    public static groupChildListSuccess(){
        return [{severity:'success', detail: 'Edycja zakończona powodzeniem'}];
    }
    
    public static groupChildListError(){
        return [{severity:'error', detail: 'Wystąpił błąd podczas edycji listy'}];
    }
    
    public static groupCreatingSuccess(name){
        return [{severity:'success', detail: 'Dodano grupę ' + name}];
    }
    
    public static groupCreatingError(){
        return [{severity:'error', detail: 'Wystąpił błąd podczas tworzenia grupy'}];
    }
    
    public static classroomCreatingSuccess(name){
        return [{severity:'success', detail: 'Dodano salę ' + name}];
    }
    
    public static classroomCreatingError(){
        return [{severity:'error', detail: 'Wystąpił błąd podczas tworzenia sali'}];
    }
    
    public static groupDeletingError(){
        return [{severity:'error', detail: 'Wystąpił błąd podczas usuwania grupy'}];
    }
    
    public static groupDeletingSuccess(name){
        return [{severity:'success', detail: 'Usunięto grupę ' + name}];
    }
    
    public static classroomDeletingSuccess(name){
        return [{severity:'success', detail: 'Usunięto salę ' + name}];
    }
    
    public static classroomDeletingError(){
        return [{severity:'error', detail: 'Wystąpił błąd podczas usuwania sali'}];
    }
    
    public static editError(){
        return [{severity:'error', detail: 'Wystąpił błąd podczas edycji'}];
    }
    
    public static editSuccess(){
        return [{severity:'success', detail: 'Zmodyfikowano pomyślnie'}];
    }
    
    public static addressCreatingSuccess(){
        return [{severity:'success', detail: 'Dodano nowy adres'}];
    }
    
    public static addressCreatingError(){
        return [{severity:'error', detail: 'Wystąpił błąd podczas dodawania adresu'}];
    }
    
    public static scheduleCreatingSuccess(){
        return [{severity:'success', detail: 'Plan zmodyfikowany pomyślnie'}];
    }
    
    public static scheduleCreatingError(){
        return [{severity:'error', detail: 'Wystąpił błąd podczas edycji'}];
    }
    
    public static activityDeletingError(){
        return [{severity:'error', detail: 'Nie udało się usunąć zajęcia'}];
    }
    
    public static scheduleValidateError(){
        return [{severity:'error', detail: 'Wystąpił błąd podczas sprawdzania poprawności'}];
    }
    
    public static scheduleValidateSuccess(){
        return [{severity:'success', detail: 'Plan jest poprawny'}];
    }
    
    public static downloadFileError(){
        return [{severity:'error', detail: 'Wystąpił błąd podczas pobierania pliku'}];
    }
    
    public static getFilesError(){
        return [{severity:'error', detail: 'Wystąpił błąd podczas pobierania drzewa plików'}];
    }
    
    public static fileUploadError(){
        return [{severity:'error', detail: 'Wystąpił błąd podczas wstawiania pliku'}];
    }
    
    public static fileUploadSuccess(){
        return [{severity:'success', detail: 'Plik wysłano poprawnie'}];
    }
    
}
