import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { RecipeApplicationSharedModule } from 'app/shared';
import {
    ShoppingListComponent,
    ShoppingListDetailComponent,
    ShoppingListUpdateComponent,
    ShoppingListDeletePopupComponent,
    ShoppingListDeleteDialogComponent,
    shoppingListRoute,
    shoppingListPopupRoute
} from './';

const ENTITY_STATES = [...shoppingListRoute, ...shoppingListPopupRoute];

@NgModule({
    imports: [RecipeApplicationSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ShoppingListComponent,
        ShoppingListDetailComponent,
        ShoppingListUpdateComponent,
        ShoppingListDeleteDialogComponent,
        ShoppingListDeletePopupComponent
    ],
    entryComponents: [
        ShoppingListComponent,
        ShoppingListUpdateComponent,
        ShoppingListDeleteDialogComponent,
        ShoppingListDeletePopupComponent
    ],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RecipeApplicationShoppingListModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
