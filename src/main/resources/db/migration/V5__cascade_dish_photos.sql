-- ON DELETE CASCADE so DELETE /photos/{id} also removes dish_photos rows
-- instead of failing with a FK violation.
ALTER TABLE dish_photos DROP CONSTRAINT IF EXISTS dish_photos_photo_id_fkey;
ALTER TABLE dish_photos
    ADD CONSTRAINT dish_photos_photo_id_fkey
        FOREIGN KEY (photo_id) REFERENCES photos (id) ON DELETE CASCADE;
