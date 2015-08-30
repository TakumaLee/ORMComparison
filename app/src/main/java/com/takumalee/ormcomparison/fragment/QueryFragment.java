package com.takumalee.ormcomparison.fragment;

import android.animation.Animator;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.j256.ormlite.dao.Dao;
import com.takumalee.ormcomparison.R;
import com.takumalee.ormcomparison.database.greendao.DBHelper;
import com.takumalee.ormcomparison.database.greendao.dao.CharacterDao;
import com.takumalee.ormcomparison.database.ormlite.dao.DAOFactory;
import com.takumalee.ormcomparison.database.ormlite.model.*;
import com.takumalee.ormcomparison.database.ormlite.model.Character;
import com.takumalee.ormcomparison.database.realm.model.RealmCharacter;
import com.takumalee.ormcomparison.fragment.base.ActivityBaseFragment;

import java.sql.SQLException;
import java.util.List;

import io.realm.Realm;

/**
 * Created by TakumaLee on 15/8/30.
 */
public class QueryFragment extends ActivityBaseFragment {

    private View view;

    private Button queryForAllOrmliteBtn;
    private Button queryForAllGreenDaoBtn;
    private Button queryForAllRealmBtn;

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
        queryForAllOrmliteBtn = (Button) view.findViewById(R.id.button_query_for_all_ormlite);
        queryForAllGreenDaoBtn = (Button) view.findViewById(R.id.button_query_for_all_greendao);
        queryForAllRealmBtn = (Button) view.findViewById(R.id.button_query_for_all_realm);
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
                CharacterDao characterDao = DBHelper.getInstance(activity).getCharacterDao();
                List<com.takumalee.ormcomparison.database.greendao.dao.Character> characterList = characterDao.queryBuilder().list();
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
    }

    @Override
    protected void initActionBar() {

    }

    @Override
    public void onAnimationStart() {

    }

    @Override
    public void onAnimationEnd(Animator animation) {

    }
}
