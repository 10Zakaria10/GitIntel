import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { RecipeApplicationSharedModule } from 'app/shared';
import {
    IngredientComponent,
    IngredientDetailComponent,
    IngredientUpdateComponent,
    IngredientDeletePopupComponent,
    IngredientDeleteDialogComponent,
    ingredientRoute,
    ingredientPopupRoute
} from './';

const ENTITY_STATES = [...ingredientRoute, ...ingredientPopupRoute];

@NgModule({
    imports: [RecipeApplicationSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        IngredientComponent,
        IngredientDetailComponent,
        IngredientUpdateComponent,
        IngredientDeleteDialogComponent,
        IngredientDeletePopupComponent
    ],
    entryComponents: [IngredientComponent, IngredientUpdateComponent, IngredientDeleteDialogComponent, IngredientDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RecipeApplicationIngredientModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
