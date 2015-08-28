package com.takumalee.ormcomparison.fragment;

import android.animation.Animator;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.analytics.HitBuilders;
import com.j256.ormlite.dao.Dao;
import com.takumalee.ormcomparison.R;
import com.takumalee.ormcomparison.adapter.TabFragmentAdapter;
import com.takumalee.ormcomparison.database.greendao.DBHelper;
import com.takumalee.ormcomparison.database.greendao.dao.CharacterDao;
import com.takumalee.ormcomparison.database.ormlite.dao.DAOFactory;
import com.takumalee.ormcomparison.database.ormlite.model.Character;
import com.takumalee.ormcomparison.database.realm.model.RealmCharacter;
import com.takumalee.ormcomparison.fragment.base.ActivityBaseFragment;

import java.sql.SQLException;

import io.realm.Realm;

public class HomeFragment extends ActivityBaseFragment {
    private static final String TAG = HomeFragment.class.getSimpleName();

    private View view;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private TabFragmentAdapter adapter;

    private EditText editTextOrmlite;
    private Button ormliteInsertBtn;
    private TextView textViewOrmlite;
    private Button ormliteClearBtn;

    private EditText editTextGreenDao;
    private Button buttonGreenDao;
    private TextView textViewGreenDao;
    private Button greenClearBtn;

    private EditText editTextRealm;
    private Button buttonRealm;
    private TextView textViewRealm;
    private Button realmClearBtn;

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tracker.setScreenName("首頁");
        tracker.send(new HitBuilders.ScreenViewBuilder().build());
        Log.v(TAG, "onCreate()");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.v(TAG, "onCreateView()");
        view = inflater.inflate(R.layout.fragment_home, container, false);

        editTextOrmlite = (EditText) view.findViewById(R.id.editText_ormlite_input_count);
        ormliteInsertBtn = (Button) view.findViewById(R.id.button_ormlite_insert);
        textViewOrmlite = (TextView) view.findViewById(R.id.textView_ormlite_time);
        ormliteClearBtn = (Button) view.findViewById(R.id.button_ormlite_clear);

        editTextGreenDao = (EditText) view.findViewById(R.id.editText_greendao_input_count);
        buttonGreenDao = (Button) view.findViewById(R.id.button_greendao_insert);
        textViewGreenDao = (TextView) view.findViewById(R.id.textView_greendao_time);
        greenClearBtn = (Button) view.findViewById(R.id.button_greendao_clear);

        editTextRealm = (EditText) view.findViewById(R.id.editText_realm_input_count);
        buttonRealm = (Button) view.findViewById(R.id.button_realm_insert);
        textViewRealm = (TextView) view.findViewById(R.id.textView_realm_time);
        realmClearBtn = (Button) view.findViewById(R.id.button_realm_clear);

        ormliteInsertBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editTextOrmlite.getText().toString().isEmpty()) {
                    return;
                }
                Dao<Character, Integer> dao = null;
                long ormliteTime = System.currentTimeMillis();
                try {
                    dao = DAOFactory.getInstance().getDbHelper().getDao(Character.class);
                    int ormCount = Integer.parseInt(editTextOrmlite.getText().toString());
                    DAOFactory.getInstance().beginTransaction();
                    for (int i = 0; i < ormCount; i++) {
                        Character characterOrmlite = new Character();
                        dao.createIfNotExists(characterOrmlite);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                DAOFactory.getInstance().commitTransaction();
                long ormliteElapsedTime = System.currentTimeMillis() - ormliteTime;
                textViewOrmlite.setText(String.valueOf(ormliteElapsedTime) + "ms");
            }
        });
        ormliteClearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DAOFactory.getInstance().getDbHelper().eraseAllData();
            }
        });

        buttonGreenDao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editTextGreenDao.getText().toString().isEmpty()) {
                    return;
                }
                int greenCount = Integer.parseInt(editTextGreenDao.getText().toString());
                long greenTime = System.currentTimeMillis();
                CharacterDao characterDao = DBHelper.getInstance(activity).getCharacterDao();
                SQLiteDatabase db = characterDao.getDatabase();
                db.beginTransaction();
                for (int i = 0; i < greenCount; i++) {
                    com.takumalee.ormcomparison.database.greendao.dao.Character greenCharacter = new com.takumalee.ormcomparison.database.greendao.dao.Character((long) i, "C", "S");
                    characterDao.insert(greenCharacter);
                }
                db.setTransactionSuccessful();
                db.endTransaction();
                long greenElapsedTime = System.currentTimeMillis() - greenTime;
                textViewGreenDao.setText(String.valueOf(greenElapsedTime) + "ms");
            }
        });
        greenClearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CharacterDao characterDao = DBHelper.getInstance(activity).getCharacterDao();
                characterDao.getDatabase().beginTransaction();
                characterDao.deleteAll();
                characterDao.getDatabase().setTransactionSuccessful();
                characterDao.getDatabase().endTransaction();
            }
        });

        buttonRealm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editTextRealm.getText().toString().isEmpty()) {
                    return;
                }
                int realmCount = Integer.parseInt(editTextRealm.getText().toString());
                long realmTime = System.currentTimeMillis();
                Realm.getDefaultInstance().beginTransaction();
                for (int i = 0; i < realmCount; i++) {
                    RealmCharacter realmCharacter = new RealmCharacter();
                    realmCharacter.setId(i);
                    realmCharacter.setAttributes(String.valueOf(i));
                    realmCharacter.setCareers(String.valueOf(i));
                    Realm.getDefaultInstance().copyToRealmOrUpdate(realmCharacter);
                }
                Realm.getDefaultInstance().commitTransaction();
                long realmElapsedTime = System.currentTimeMillis() - realmTime;
                textViewRealm.setText(String.valueOf(realmElapsedTime) + "ms");

            }
        });
        realmClearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Realm.getDefaultInstance().beginTransaction();
                Realm.getDefaultInstance().clear(RealmCharacter.class);
                Realm.getDefaultInstance().commitTransaction();
            }
        });

//        tabLayout = (TabLayout) inflater.inflate(R.layout.tablayout, container, false).findViewById(R.id.tabs);
//        tabLayout.setTabTextColors(Color.argb(255 * 54 / 100,
//                Color.red(Color.WHITE),
//                Color.green(Color.WHITE),
//                Color.blue(Color.WHITE)), Color.WHITE);
//
//        viewPager = (ViewPager) view.findViewById(R.id.viewpager);
//        setupViewPager(viewPager);
//        UIConfig.setupTabSelectorColor(tabLayout, Color.WHITE);
//        tabLayout.setupWithViewPager(viewPager);

        return view;
    }

    @Override
    public boolean onBackPressed() {
        return true;
    }

    private void setupViewPager(ViewPager viewPager) {
        adapter = new TabFragmentAdapter(getChildFragmentManager());
        adapter.addFragment(new Fragment(), "出境");
        adapter.addFragment(new Fragment(), "入境");
        viewPager.setAdapter(adapter);
    }


    @Override
    public void onResume() {
        super.onResume();
        Log.v(TAG, "onResume()");
//        ((ORMHomeActivity)activity).addCustomView(tabLayout);
//        historyFragment.setActionBarFunctionListener(actionBarFunctionListener);
//        historyDiagramFragment.setActionBarFunctionListener(actionBarFunctionListener);
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.v(TAG, "onStart()");
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
