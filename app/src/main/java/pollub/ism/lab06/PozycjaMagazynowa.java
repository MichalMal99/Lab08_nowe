package pollub.ism.lab06;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Entity(tableName = "PozycjaMagazynowa")
public class PozycjaMagazynowa {
    @PrimaryKey(autoGenerate = true)
    public int _id;
    public String NAME;
    public int QUANTITY;
    public String LASTCHANGETIME;


}