package pollub.ism.lab06;

import android.icu.text.IDNA;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import pollub.ism.lab06.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private ArrayAdapter<CharSequence> adapter;

    private String wybraneWarzywoNazwa = null;
    private Integer wybraneWarzywoIlosc = null;
    private Integer wybraneWarzywoId = null;
    private String wybraneWarzywoCzas = null;

    public enum OperacjaMagazynowa {SKLADUJ, WYDAJ};

    private BazaMagazynowa bazaDanych;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        adapter = ArrayAdapter.createFromResource(this, R.array.Asortyment, android.R.layout.simple_dropdown_item_1line);
        binding.spinner.setAdapter(adapter);

        bazaDanych = Room.databaseBuilder(getApplicationContext(), BazaMagazynowa.class, BazaMagazynowa.NAZWA_BAZY)
                .allowMainThreadQueries().build();

        if(bazaDanych.pozycjaMagazynowaDAO().size() == 0){
            String[] asortyment = getResources().getStringArray(R.array.Asortyment);
            for(String nazwa : asortyment){
                PozycjaMagazynowa pozycjaMagazynowa = new PozycjaMagazynowa();
                pozycjaMagazynowa.NAME = nazwa; pozycjaMagazynowa.QUANTITY = 0;pozycjaMagazynowa.LASTCHANGETIME = "nigdy";
                bazaDanych.pozycjaMagazynowaDAO().insert(pozycjaMagazynowa);
            }
        }
        //listener przycisku skladuj
        binding.przyciskSkladuj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                zmienStan(OperacjaMagazynowa.SKLADUJ);

            }
        });
        //listener przycisku wydaj
        binding.przyciskWydaj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                zmienStan(OperacjaMagazynowa.WYDAJ);
            }
        });
        //listener spinneru
        binding.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                wybraneWarzywoNazwa = adapter.getItem(i).toString(); // <---
                wybraneWarzywoId = i; //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                aktualizuj();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                //Nie będziemy implementować, ale musi być
            }
        });

    }
    private void aktualizuj(){
        // String history = bazaDanych.pozycjaMagazynowaDAO().getHistoryByName(wybraneWarzywoNazwa);
        List<IPozycjaMagazynowa> history = bazaDanych.pozycjaMagazynowaDAO().getLogs(wybraneWarzywoId);
        wybraneWarzywoIlosc = bazaDanych.pozycjaMagazynowaDAO().findQuantityByName(wybraneWarzywoNazwa);
        wybraneWarzywoCzas = bazaDanych.pozycjaMagazynowaDAO().getLastChangeTimeByName(wybraneWarzywoNazwa);
        binding.tekstStanMagazynu.setText("Stan magazynu dla " + wybraneWarzywoNazwa + " wynosi: " + wybraneWarzywoIlosc);

        binding.tekstJednostka.setText(wybraneWarzywoCzas);
        binding.logText.getEditableText().clear();
        for(IPozycjaMagazynowa s : history) {

            binding.logText.append(s.LOG + "\n");
        }

    }
    private void zmienStan(OperacjaMagazynowa operacja){
        String newHistory = "";

        Integer zmianaIlosci = null, nowaIlosc = null;
        try {
            zmianaIlosci = Integer.parseInt(binding.edycjaIlosc.getText().toString());
        }catch(NumberFormatException ex){
            return;
        }finally {
            binding.edycjaIlosc.setText("");
        }

        switch (operacja){
            case SKLADUJ:
                nowaIlosc = wybraneWarzywoIlosc + zmianaIlosci;
                newHistory =/* czas + " " +*/ wybraneWarzywoIlosc + " -> " + nowaIlosc;

                break;
            case WYDAJ:
                //DODAJ LOGIKE SYTUACJI KIEDY WYDAJESZ WIECEJ NIZ JEST NA STANIE
                nowaIlosc = wybraneWarzywoIlosc - zmianaIlosci;
                newHistory =/* czas + " " + */wybraneWarzywoIlosc + " -> " + nowaIlosc;

                break;
        }
        IPozycjaMagazynowa newLog = new IPozycjaMagazynowa(newHistory,wybraneWarzywoId);
        bazaDanych.pozycjaMagazynowaDAO().insert(newLog);
        bazaDanych.pozycjaMagazynowaDAO().updateQuantityByName(wybraneWarzywoNazwa,nowaIlosc);

        aktualizuj();


    }
}
