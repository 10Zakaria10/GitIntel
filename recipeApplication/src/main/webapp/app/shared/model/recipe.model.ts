import { IIngredient } from 'app/shared/model/ingredient.model';

export interface IRecipe {
    id?: number;
    name?: string;
    imageUrl?: string;
    description?: string;
    ingredients?: IIngredient[];
}

export class Recipe implements IRecipe {
    constructor(
        public id?: number,
        public name?: string,
        public imageUrl?: string,
        public description?: string,
        public ingredients?: IIngredient[]
    ) {}
}
