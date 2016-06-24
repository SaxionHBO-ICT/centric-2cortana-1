package com.example.larsmeulenbroek.kroegenapp.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.larsmeulenbroek.kroegenapp.Model.Bar;
import com.example.larsmeulenbroek.kroegenapp.Model.BarModel;
import com.example.larsmeulenbroek.kroegenapp.R;
import com.example.larsmeulenbroek.kroegenapp.Tasks.LoadBarDetailActivityTask;
import com.example.larsmeulenbroek.kroegenapp.View.DetailRouteBarListAdapter;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    private List<Bar> searchBars = new ArrayList<>();
    private ListView resultList;
    private EditText searchField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        searchField = (EditText) findViewById(R.id.editSearchInResults);
        resultList = (ListView) findViewById(R.id.listSearchResults);

        assert searchField != null;
        assert resultList != null;

        searchField.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    try {
                        searchBars.clear();
                    } catch (NullPointerException npe) {

                    }
                    String text = searchField.getText().toString().toLowerCase();
                    InputMethodManager in = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    in.hideSoftInputFromWindow(searchField.getApplicationWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

                    for(Bar b : BarModel.getInstance()) {
                        if(b.getName().toLowerCase().contains(text)) {
                            searchBars.add(b);
                        }
                    }
                    DetailRouteBarListAdapter adapter = new DetailRouteBarListAdapter(SearchActivity.this, R.layout.bar_view, searchBars);
                    resultList.setAdapter(adapter);
                    resultList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            int bar_id = searchBars.get(position).getBar_id();
                            LoadBarDetailActivityTask lbd = new LoadBarDetailActivityTask(getApplicationContext());
                            lbd.execute(bar_id);
                        }
                    });
                }
                return false;
            }
        });
    }
}
