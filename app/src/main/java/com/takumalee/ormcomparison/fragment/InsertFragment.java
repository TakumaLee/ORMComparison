package com.takumalee.ormcomparison.fragment;

import android.animation.Animator;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.j256.ormlite.dao.Dao;
import com.takumalee.ormcomparison.R;
import com.takumalee.ormcomparison.database.greendao.DBHelper;
import com.takumalee.ormcomparison.database.greendao.dao.CharacterDao;
import com.takumalee.ormcomparison.database.ormlite.dao.DAOFactory;
import com.takumalee.ormcomparison.database.ormlite.model.*;
import com.takumalee.ormcomparison.database.ormlite.model.Character;
import com.takumalee.ormcomparison.database.realm.model.RealmCharacter;

import java.sql.SQLException;

import io.realm.Realm;

/**
 * Created by TakumaLee on 15/8/30.
 */
public class InsertFragment extends Fragment {

    String CAREER = "C";
    String ATTRIBUTES = "S";

    private EditText editTextOrmlite;
    private Button ormliteInsertBtn;
    private TextView textViewOrmlite;

    private Button buttonGreenDao;
    private TextView textViewGreenDao;

    private Button buttonRealm;
    private TextView textViewRealm;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_insert, container, false);
        editTextOrmlite = (EditText) view.findViewById(R.id.editText_ormlite_input_count);
        ormliteInsertBtn = (Button) view.findViewById(R.id.button_ormlite_insert);
        textViewOrmlite = (TextView) view.findViewById(R.id.textView_ormlite_time);

        buttonGreenDao = (Button) view.findViewById(R.id.button_greendao_insert);
        textViewGreenDao = (TextView) view.findViewById(R.id.textView_greendao_time);

        buttonRealm = (Button) view.findViewById(R.id.button_realm_insert);
        textViewRealm = (TextView) view.findViewById(R.id.textView_realm_time);

        ormliteInsertBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editTextOrmlite.getText().toString().isEmpty()) {
                    return;
                }
                truncateOrmlite();
                Dao<Character, Integer> dao = null;
                long ormliteTime = System.currentTimeMillis();
                try {
                    dao = DAOFactory.getInstance().getDbHelper().getDao(Character.class);
                    int ormCount = Integer.parseInt(editTextOrmlite.getText().toString());
                    DAOFactory.getInstance().beginTransaction();
                    for (int i = 0; i < ormCount; i++) {
                        Character characterOrmlite = new Character();
                        characterOrmlite.setCareers(CAREER + i);
                        characterOrmlite.setAttributes(ATTRIBUTES + i);
                        dao.create(characterOrmlite);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                DAOFactory.getInstance().commitTransaction();
                long ormliteElapsedTime = System.currentTimeMillis() - ormliteTime;
                textViewOrmlite.setText(String.valueOf(ormliteElapsedTime) + "ms");
            }
        });

        buttonGreenDao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editTextOrmlite.getText().toString().isEmpty()) {
                    return;
                }
                truncateGreenDao();
                int greenCount = Integer.parseInt(editTextOrmlite.getText().toString());
                long greenTime = System.currentTimeMillis();
                CharacterDao characterDao = DBHelper.getInstance(getActivity()).getCharacterDao();
                SQLiteDatabase db = characterDao.getDatabase();
                db.beginTransaction();
                for (int i = 0; i < greenCount; i++) {
                    com.takumalee.ormcomparison.database.greendao.dao.Character greenCharacter = new com.takumalee.ormcomparison.database.greendao.dao.Character(null, CAREER + i, ATTRIBUTES + i);
                    characterDao.insert(greenCharacter);
                }
                db.setTransactionSuccessful();
                db.endTransaction();
                long greenElapsedTime = System.currentTimeMillis() - greenTime;
                textViewGreenDao.setText(String.valueOf(greenElapsedTime) + "ms");
            }
        });

        buttonRealm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editTextOrmlite.getText().toString().isEmpty()) {
                    return;
                }
                truncateRealm();
                int realmCount = Integer.parseInt(editTextOrmlite.getText().toString());
                long realmTime = System.currentTimeMillis();

                Realm realm = Realm.getDefaultInstance();
                realm.beginTransaction();
                for (int i = 0; i < realmCount; i++) {
                    RealmCharacter realmCharacter = realm.createObject(RealmCharacter.class);
//                    realmCharacter.setId(i);
                    realmCharacter.setAttributes(ATTRIBUTES +i);
                    realmCharacter.setCareers(CAREER+i);
                }
                realm.commitTransaction();
                long realmElapsedTime = System.currentTimeMillis() - realmTime;
                textViewRealm.setText(String.valueOf(realmElapsedTime) + "ms");

            }
        });
        return view;
    }

    private void truncateOrmlite() {
        DAOFactory.getInstance().getDbHelper().eraseAllData();
    }

    private void truncateGreenDao() {
        CharacterDao characterDao = DBHelper.getInstance(getActivity()).getCharacterDao();
        characterDao.getDatabase().beginTransaction();
        characterDao.deleteAll();
        characterDao.getDatabase().setTransactionSuccessful();
        characterDao.getDatabase().endTransaction();
    }

    private void truncateRealm() {
        Realm.getDefaultInstance().beginTransaction();
        Realm.getDefaultInstance().clear(RealmCharacter.class);
        Realm.getDefaultInstance().commitTransaction();
    }
}
