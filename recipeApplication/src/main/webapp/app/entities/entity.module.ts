import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
    imports: [
        RouterModule.forChild([
            {
                path: 'ingredient',
                loadChildren: './ingredient/ingredient.module#RecipeApplicationIngredientModule'
            },
            {
                path: 'recipe',
                loadChildren: './recipe/recipe.module#RecipeApplicationRecipeModule'
            },
            {
                path: 'shopping-list',
                loadChildren: './shopping-list/shopping-list.module#RecipeApplicationShoppingListModule'
            }
            /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
        ])
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RecipeApplicationEntityModule {}
