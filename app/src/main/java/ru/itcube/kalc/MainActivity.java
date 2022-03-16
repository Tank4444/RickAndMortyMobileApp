package ru.itcube.kalc;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.QuickContactBadge;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ru.itcube.kalc.listener.RickAndMortyListener;
import ru.itcube.kalc.model.RMAllResponse;
import ru.itcube.kalc.service.RickAndMortyService;
import ru.itcube.kalc.util.CharacterAdapter;

import ru.itcube.kalc.model.Character;


public class MainActivity extends AppCompatActivity {
    private RickAndMortyService service;
    private Button nextButton;
    private Button backButton;
    private TextView pageText;
    private int page=1;
    private RecyclerView char_list;
    private List<Character> list;
    private CharacterAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        service = RickAndMortyService.getInstance(this);
        nextButton = findViewById(R.id.nextButton);
        backButton = findViewById(R.id.backButton);
        pageText = findViewById(R.id.pageCount);
        char_list = findViewById(R.id.char_list);
        list = new ArrayList<Character>();

        adapter = new CharacterAdapter(this, list, new CharacterAdapter.CharClickListener() {
            @Override
            public void click(int position, Character character) {
                Intent intent =  new Intent(MainActivity.this,CharacterActivity.class);
                intent.putExtra("char",service.getGson().toJson(character));
                startActivity(intent);
            }
        });

        char_list.setLayoutManager(new LinearLayoutManager(this));
        char_list.setAdapter(adapter);

        service.getCharacter(page, new RickAndMortyListener() {
            @Override
            public void onSuccess(RMAllResponse response) {
                list.addAll(response.getResults());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onError(String message) {
                Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
            }
        });

        nextButton.setOnClickListener(view -> {
            service.getCharacter(page + 1, new RickAndMortyListener() {
                @Override
                public void onSuccess(RMAllResponse response) {
                    list.clear();
                    list.addAll(response.getResults());
                    adapter.notifyDataSetChanged();
                    page++;
                    pageText.setText(""+page);
                    char_list.smoothScrollToPosition(0);
                }

                @Override
                public void onError(String message) {
                    Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
                }
            });
        });
        backButton.setOnClickListener(view -> {
            if(page==1)return;
            service.getCharacter(page - 1, new RickAndMortyListener() {
                @Override
                public void onSuccess(RMAllResponse response) {
                    list.clear();
                    list.addAll(response.getResults());
                    adapter.notifyDataSetChanged();
                    page--;
                    pageText.setText(""+page);
                    char_list.smoothScrollToPosition(0);
                }

                @Override
                public void onError(String message) {
                    Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
                }
            });
        });






    }
}
