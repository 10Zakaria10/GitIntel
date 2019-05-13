import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IShoppingList } from 'app/shared/model/shopping-list.model';

@Component({
    selector: 'jhi-shopping-list-detail',
    templateUrl: './shopping-list-detail.component.html'
})
export class ShoppingListDetailComponent implements OnInit {
    shoppingList: IShoppingList;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ shoppingList }) => {
            this.shoppingList = shoppingList;
        });
    }

    previousState() {
        window.history.back();
    }
}
