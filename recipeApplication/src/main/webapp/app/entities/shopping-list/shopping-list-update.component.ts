import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IShoppingList } from 'app/shared/model/shopping-list.model';
import { ShoppingListService } from './shopping-list.service';
import { IIngredient } from 'app/shared/model/ingredient.model';
import { IngredientService } from 'app/entities/ingredient';

@Component({
    selector: 'jhi-shopping-list-update',
    templateUrl: './shopping-list-update.component.html'
})
export class ShoppingListUpdateComponent implements OnInit {
    shoppingList: IShoppingList;
    isSaving: boolean;

    ingredients: IIngredient[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected shoppingListService: ShoppingListService,
        protected ingredientService: IngredientService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ shoppingList }) => {
            this.shoppingList = shoppingList;
        });
        this.ingredientService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IIngredient[]>) => mayBeOk.ok),
                map((response: HttpResponse<IIngredient[]>) => response.body)
            )
            .subscribe((res: IIngredient[]) => (this.ingredients = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.shoppingList.id !== undefined) {
            this.subscribeToSaveResponse(this.shoppingListService.update(this.shoppingList));
        } else {
            this.subscribeToSaveResponse(this.shoppingListService.create(this.shoppingList));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IShoppingList>>) {
        result.subscribe((res: HttpResponse<IShoppingList>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackIngredientById(index: number, item: IIngredient) {
        return item.id;
    }

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }
}
