export class CommonMessages {

    public static roleFetchingWentWrong(){
        return [{severity:'error', detail: 'Wystąpił błąd podczas logowania'}];
    }
    
    public static employeeDeletingError(){
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
    
    public static groupDeletingError(){
        return [{severity:'error', detail: 'Wystąpił błąd podczas usuwania grupy'}];
    }
    
    public static groupDeletingSuccess(name){
        return [{severity:'success', detail: 'Usunięto grupę ' + name}];
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
}
