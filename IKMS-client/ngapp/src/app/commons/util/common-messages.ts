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
}
