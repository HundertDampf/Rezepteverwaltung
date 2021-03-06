package ch.bbbaden.ims.rezepteverwaltung.objects;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by Noah on 19.02.2018.
 */
@Entity(tableName = "rezepte")
public class Rezept {

    @Getter
    @Setter
    @PrimaryKey
    private int rezeptId;

    @Getter
    @Setter
    private String rezeptName;

//    @Getter
//    @Setter
//    private List<String>[] rezeptZutaten;

    @Getter
    @Setter
    private String rezeptZubereitung;

    @Getter
    @Setter
    private String rezeptDauer;

    public Rezept() {
    }

    public Rezept(int rezeptId, String rezeptName, String rezeptZubereitung, String rezeptDauer) {
        this.rezeptId = rezeptId;
        this.rezeptName = rezeptName;
        this.rezeptZubereitung = rezeptZubereitung;
        this.rezeptDauer = rezeptDauer;
    }
}
