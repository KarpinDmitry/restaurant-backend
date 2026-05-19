-- Symmetric to V5 (dish_photos): cascade dish_ingredients when an Ingredient is deleted,
-- so DELETE /ingredients/{id} doesn't fail with FK violation.
ALTER TABLE dish_ingredients DROP CONSTRAINT IF EXISTS dish_ingredients_ingredient_id_fkey;
ALTER TABLE dish_ingredients
    ADD CONSTRAINT dish_ingredients_ingredient_id_fkey
        FOREIGN KEY (ingredient_id) REFERENCES ingredients (id) ON DELETE CASCADE;
