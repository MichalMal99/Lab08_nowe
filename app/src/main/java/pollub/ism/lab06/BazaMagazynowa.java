package pollub.ism.lab06;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {PozycjaMagazynowa.class,IPozycjaMagazynowa.class}, version = pollub.ism.lab06.BazaMagazynowa.WERSJA, exportSchema = false)
    public abstract class BazaMagazynowa extends RoomDatabase {

        public static final String NAZWA_BAZY = "Stoisko z warzywami";
        public static final int WERSJA = 1;

        public abstract PozycjaMagazynowaDAO pozycjaMagazynowaDAO();

}
