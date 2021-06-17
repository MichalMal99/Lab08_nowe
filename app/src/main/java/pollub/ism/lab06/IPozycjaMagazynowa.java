package pollub.ism.lab06;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "InfoPozycjaMagazynowa")
public class IPozycjaMagazynowa {
    @PrimaryKey(autoGenerate = true)
    public int _id;
    public String LOG;
    public int POZYCJAMAGAZYNOWA_ID;

    public IPozycjaMagazynowa(String LOG, int POZYCJAMAGAZYNOWA_ID) {
        this.LOG = LOG;
        this.POZYCJAMAGAZYNOWA_ID = POZYCJAMAGAZYNOWA_ID;
    }
}