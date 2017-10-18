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
}
