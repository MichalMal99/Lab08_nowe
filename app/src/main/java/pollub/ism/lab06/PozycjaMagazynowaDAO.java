package pollub.ism.lab06;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Dao
public interface PozycjaMagazynowaDAO {

    @Insert  //Automatyczna kwerenda wystarczy
    public void insert(PozycjaMagazynowa pozycja);
    @Insert  //Automatyczna kwerenda wystarczy
    public void insert(IPozycjaMagazynowa pozycjaInfo);
    @Update //Automatyczna kwerenda wystarczy
    public void update(PozycjaMagazynowa pozycja);

    @Query("SELECT QUANTITY FROM PozycjaMagazynowa WHERE NAME= :wybraneWarzywoNazwa") //Nasza kwerenda
    int findQuantityByName(String wybraneWarzywoNazwa);
    @Query("SELECT LASTCHANGETIME FROM PozycjaMagazynowa WHERE NAME= :wybraneWarzywoNazwa") //Nasza kwerenda
    String getLastChangeTimeByName(String wybraneWarzywoNazwa);
    @Query("UPDATE PozycjaMagazynowa SET QUANTITY = :wybraneWarzywoNowaIlosc WHERE NAME= :wybraneWarzywoNazwa")
    void updateQuantityByName(String wybraneWarzywoNazwa, int wybraneWarzywoNowaIlosc);
    @Query("UPDATE PozycjaMagazynowa SET LASTCHANGETIME = :time WHERE NAME= :wybraneWarzywoNazwa")
    void updateLastChangeTimeByName(String wybraneWarzywoNazwa, String time);
    //@Query("UPDATE Warzywniak SET HISTORY = :newHistory WHERE NAME= :wybraneWarzywoNazwa")
    //void updateHistoryByName(String wybraneWarzywoNazwa, String newHistory);

    // @Query("SELECT HISTORY FROM InfoPozycjaMagazynowa WHERE NAME= :wybraneWarzywoNazwa")
    // String getHistoryByName(String wybraneWarzywoNazwa);

    @Query("SELECT COUNT(*) FROM PozycjaMagazynowa") //Ile jest rekordów w tabeli
    int size();
    /*
    @Query("SELECT * FROM PozycjaMagazynowa")
    List<PozycjaMagazynowaWithInfo> loadTestModelsWithBooks();
    */

    @Query("SELECT * FROM InfoPozycjaMagazynowa WHERE POZYCJAMAGAZYNOWA_ID=:warzywoId")
    List<IPozycjaMagazynowa> getLogs(int warzywoId);

}