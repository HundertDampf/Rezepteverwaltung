package ch.bbbaden.ims.rezepteverwaltung.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import ch.bbbaden.ims.rezepteverwaltung.R;
import ch.bbbaden.ims.rezepteverwaltung.objects.Rezept;
import ch.bbbaden.ims.rezepteverwaltung.services.AppDatabase;
import ch.bbbaden.ims.rezepteverwaltung.services.DataHolder;
import ch.bbbaden.ims.rezepteverwaltung.services.RezeptDAO;

public class AddRezeptActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;

    Button btnAdd;
    EditText editName;
    EditText editZutaten;
    EditText editZubereitung;
    EditText editDauer;

    Rezept addRezept;
    RezeptDAO rezeptDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_rezept);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        btnAdd = findViewById(R.id.btnAdd);
        editName = findViewById(R.id.editRezeptName);
        editZubereitung = findViewById(R.id.editZubereitung);
        editZutaten = findViewById(R.id.editZutat);
        editDauer = findViewById(R.id.editDauer);

        rezeptDAO = new RezeptDAO() {
            @Override
            public List<Rezept> getAll() {
                return null;
            }

            @Override
            public Rezept loadAllByIds(int rezeptIds) {
                return null;
            }

            @Override
            public Rezept findByName(String name) {
                return null;
            }

            @Override
            public void insertAll(Rezept... rezepte) {

            }

            @Override
            public void delete(Rezept rezept) {

            }
        };

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataHolder.getInstance().setRezept(saveRezept());
                goToNewActivity(RezeptActivity.class);
            }
        });
    }

    public Rezept saveRezept() {
        addRezept = new Rezept();
        addRezept.setRezeptName(editName.getText().toString());
        addRezept.setRezeptDauer(editDauer.getText().toString());
        addRezept.setRezeptZubereitung(editZubereitung.getText().toString());
//        List<String>[] zutaten = new List[1];
//        zutaten[0].add("1 Gramm");
//        zutaten[1].add(editZutaten.getText().toString());
        // addRezept.setRezeptZutaten(zutaten);


        finish();
        addRezept(AppDatabase.getAppDatabase(MainActivity.context), mDatabase, addRezept);
        return addRezept;
    }

    private static Rezept addRezept(final AppDatabase db, DatabaseReference mDatabase, Rezept rezept) {
        rezept.setRezeptId(db.rezeptDAO().getAll().size()+1);
        mDatabase.child("rezept").child("rezept" + rezept.getRezeptId()).setValue(rezept);
        db.rezeptDAO().insertAll(rezept);
        return rezept;
    }

    public void goToNewActivity(Class goToClass) {
        startActivity(new Intent(getApplicationContext(), goToClass));
    }
}
