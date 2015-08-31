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
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.takumalee.ormcomparison.R;
import com.takumalee.ormcomparison.database.greendao.DBHelper;
import com.takumalee.ormcomparison.database.greendao.dao.CharacterDao;
import com.takumalee.ormcomparison.database.ormlite.dao.DAOFactory;
import com.takumalee.ormcomparison.database.ormlite.model.*;
import com.takumalee.ormcomparison.database.ormlite.model.Character;
import com.takumalee.ormcomparison.database.realm.model.RealmCharacter;

import java.sql.SQLException;
import java.util.List;

import io.realm.Realm;

/**
 * Created by TakumaLee on 15/8/30.
 */
public class QueryFragment extends Fragment {

    private View view;

    private EditText editTextInput;

    private Button queryForAllOrmliteBtn;
    private Button queryForAllGreenDaoBtn;
    private Button queryForAllRealmBtn;

    private Button queryByNameOrmliteBtn;
    private Button queryByNameGreenDaoBtn;
    private Button queryByNameRealmBtn;

    private TextView textViewOrmlite;
    private TextView textViewGreenDao;
    private TextView textViewRealm;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_query, container, false);
        initView();
        setListener();
        return view;
    }

    private void initView() {
        editTextInput = (EditText) view.findViewById(R.id.editText_query);

        queryForAllOrmliteBtn = (Button) view.findViewById(R.id.button_query_for_all_ormlite);
        queryForAllGreenDaoBtn = (Button) view.findViewById(R.id.button_query_for_all_greendao);
        queryForAllRealmBtn = (Button) view.findViewById(R.id.button_query_for_all_realm);

        queryByNameOrmliteBtn = (Button) view.findViewById(R.id.button_query_by_name_ormlite);
        queryByNameGreenDaoBtn = (Button) view.findViewById(R.id.button_query_by_name_greendao);
        queryByNameRealmBtn = (Button) view.findViewById(R.id.button_query_by_name_realm);

        textViewOrmlite = (TextView) view.findViewById(R.id.textView_Result_Ormlite);
        textViewGreenDao = (TextView) view.findViewById(R.id.textView_Result_Greendao);
        textViewRealm = (TextView) view.findViewById(R.id.textView_Result_Realm);
    }

    private void setListener() {
        queryForAllOrmliteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long greenTime = System.currentTimeMillis();
                List<Character> characterList = null;
                try {
                    Dao dao = DAOFactory.getInstance().getDbHelper().getDao(Character.class);
                    characterList = dao.queryForAll();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                long greenElapsedTime = System.currentTimeMillis() - greenTime;
                textViewOrmlite.setText("Query數量: " + characterList.size() + " \n耗費時間: " + String.valueOf(greenElapsedTime) + "ms");
            }
        });

        queryForAllGreenDaoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long greenTime = System.currentTimeMillis();
                CharacterDao characterDao = DBHelper.getInstance(getActivity()).getCharacterDao();
                List<com.takumalee.ormcomparison.database.greendao.dao.Character> characterList = characterDao.queryBuilder().build().list();
                long greenElapsedTime = System.currentTimeMillis() - greenTime;
                textViewGreenDao.setText("Query數量: " + characterList.size() + " \n耗費時間: " + String.valueOf(greenElapsedTime) + "ms");
            }
        });

        queryForAllRealmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long greenTime = System.currentTimeMillis();
                Realm realm = Realm.getDefaultInstance();
                List<RealmCharacter> realmCharacterList = realm.where(RealmCharacter.class).findAll();
                long greenElapsedTime = System.currentTimeMillis() - greenTime;
                textViewRealm.setText("Query數量: " + realmCharacterList.size() + " \n耗費時間: " + String.valueOf(greenElapsedTime) + "ms");
            }
        });

        queryByNameOrmliteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editTextInput.getText().toString().isEmpty()) {
                    return;
                }
                long greenTime = System.currentTimeMillis();
                List<Character> characterList = null;
                try {
                    Dao dao = DAOFactory.getInstance().getDbHelper().getDao(Character.class);

                    QueryBuilder<Character, Integer> queryBuilder = dao.queryBuilder();
                    queryBuilder.where().like("careers", "%" + editTextInput.getText().toString() + "%");
                    PreparedQuery<Character> characterPreparedQuery = queryBuilder.prepare();
                    characterList = dao.query(characterPreparedQuery);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                long greenElapsedTime = System.currentTimeMillis() - greenTime;
                textViewOrmlite.setText("Query數量: " + characterList.size() + " \n耗費時間: " + String.valueOf(greenElapsedTime) + "ms");
            }
        });

        queryByNameGreenDaoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editTextInput.getText().toString().isEmpty()) {
                    return;
                }
                long greenTime = System.currentTimeMillis();
                CharacterDao characterDao = DBHelper.getInstance(getActivity()).getCharacterDao();
                List<com.takumalee.ormcomparison.database.greendao.dao.Character> characterList =
                        characterDao.queryBuilder()
                                .where(CharacterDao.Properties.Careers.like("%" + editTextInput.getText().toString() + "%"))
                                .list();

                long greenElapsedTime = System.currentTimeMillis() - greenTime;
                textViewGreenDao.setText("Query數量: " + characterList.size() + " \n耗費時間: " + String.valueOf(greenElapsedTime) + "ms");
            }
        });

        queryByNameRealmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editTextInput.getText().toString().isEmpty()) {
                    return;
                }
                long greenTime = System.currentTimeMillis();
                Realm realm = Realm.getDefaultInstance();
                List<RealmCharacter> realmCharacterList = realm.where(RealmCharacter.class)
                        .contains("careers", editTextInput.getText().toString())
                        .findAll();
                long greenElapsedTime = System.currentTimeMillis() - greenTime;
                textViewRealm.setText("Query數量: " + realmCharacterList.size() + " \n耗費時間: " + String.valueOf(greenElapsedTime) + "ms");
            }
        });
    }

}
