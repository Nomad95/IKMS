import {MessageService} from "../../sharedModule/services/message.service";
import {Component, Input, OnDestroy, OnInit} from "@angular/core";
import {ConfirmationService, MenuItem} from "primeng/primeng";
import {MessageBoxMenu} from "./model/messagebox-menu";
import {ActivatedRoute} from "@angular/router";
import {BreadMaker} from "../../commons/util/bread-maker";

@Component({
    selector: 'messagebox',
    templateUrl: './messagebox.component.html',
    providers: [MessageService, ConfirmationService]
})
export class MessageBoxComponent implements OnInit, OnDestroy {
    
    private items: MenuItem[] = MessageBoxMenu.items;
    private breadcrumbsItem: MenuItem[];
    private sub;
    private type;
    private typeToBreadcrumbs: string;
    
    constructor(private route: ActivatedRoute) {
    }
    
    ngOnInit() {
        this.getTypeAndSetBreadcrumbs();
    }
    
    getTypeAndSetBreadcrumbs(): void {
        this.sub = this.route.queryParams.subscribe(params => {
            this.type = params['type'];
            this.typeToBreadcrumbs = "Odebrane";
            if (this.type === "" || this.type == null) {
                this.type = 'inbox';
            }
            if (this.type === 'outbox') {
                this.typeToBreadcrumbs = 'Wysłane';
            }
            this.breadcrumbsItem = BreadMaker.makeBreadcrumbs("Wiadomości", this.typeToBreadcrumbs);
            
        });
    }
    
    ngOnDestroy(): void {
        this.sub.unsubscribe();
    }
}
