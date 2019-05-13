import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IIngredient } from 'app/shared/model/ingredient.model';
import { AccountService } from 'app/core';
import { IngredientService } from './ingredient.service';

@Component({
    selector: 'jhi-ingredient',
    templateUrl: './ingredient.component.html'
})
export class IngredientComponent implements OnInit, OnDestroy {
    ingredients: IIngredient[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected ingredientService: IngredientService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.ingredientService
            .query()
            .pipe(
                filter((res: HttpResponse<IIngredient[]>) => res.ok),
                map((res: HttpResponse<IIngredient[]>) => res.body)
            )
            .subscribe(
                (res: IIngredient[]) => {
                    this.ingredients = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInIngredients();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IIngredient) {
        return item.id;
    }

    registerChangeInIngredients() {
        this.eventSubscriber = this.eventManager.subscribe('ingredientListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
