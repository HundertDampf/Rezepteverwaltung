package ch.bbbaden.ims.rezepteverwaltung.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.util.List;
import java.util.Random;

import ch.bbbaden.ims.rezepteverwaltung.R;
import ch.bbbaden.ims.rezepteverwaltung.objects.Rezept;
import ch.bbbaden.ims.rezepteverwaltung.services.AppDatabase;
import ch.bbbaden.ims.rezepteverwaltung.services.DataHolder;
import ch.bbbaden.ims.rezepteverwaltung.services.PdfParser;
import ch.bbbaden.ims.rezepteverwaltung.services.Toaster;

public class MenuActivity extends AppCompatActivity {
    public static Context context;

    Button btnAlleRezepte;
    Button btnSuche;
    Button btnGluck;
    Button btnNeuesRezept;
    Button btnBackup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        context = this;

        new PdfParser().execute("http://www.stundenplan.alte-kanti-aarau.ch/Dateien_PDF_und_DOC/Abkuerzungen/Abk_Lehrpersonen.pdf");

//        FirebaseConector conector = new FirebaseConector();
//        conector.downloadAllRezepte();

        btnAlleRezepte = findViewById(R.id.btnAlleRezepte);
        btnSuche = findViewById(R.id.btnGoToSuche);
        btnGluck = findViewById(R.id.btnGluck);
        btnNeuesRezept = findViewById(R.id.btnNeuesRezept);
        btnBackup = findViewById(R.id.btnBackup);

        btnAlleRezepte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataHolder.getInstance().setRezepteListe(AppDatabase.getAppDatabase(MainActivity.context).rezeptDAO().getAll());
                goToNewActivity(RezepteListActivity.class);
            }
        });

        btnSuche.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToNewActivity(RezepteSucheActivity.class);
            }
        });
        btnNeuesRezept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToNewActivity(AddRezepteVariantenActivity.class);
            }
        });
        btnGluck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Rezept random = getRandomRezept();
                new Toaster(getApplicationContext(), random.getRezeptName() + " RezeptNamer", 1);
                DataHolder.getInstance().setRezept(random);
                goToNewActivity(RezeptActivity.class);
            }
        });


    }

    public Rezept getRandomRezept() {
        List<Rezept> rezepte = AppDatabase.getAppDatabase(MainActivity.context).rezeptDAO().getAll();
        Random rand = new Random();
        return rezepte.get(rand.nextInt(rezepte.size()));
    }

    public void goToNewActivity(Class goToClass) {
        startActivity(new Intent(getApplicationContext(), goToClass));
    }
}