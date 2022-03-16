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


/*
Главное окно приложения

 */
public class MainActivity extends AppCompatActivity {

    //Переменные необходимые для работы приложения
    //Объект-сервис через который запрашивает данные с сервера и передаёт их нам для обработки
    private RickAndMortyService service;
    //Объеты для работы с элементами интерфейса (кнопки, текстовые поля и прочее)
    private Button nextButton;
    private Button backButton;
    private TextView pageText;
    private RecyclerView char_list;
    //Список персонажей который выводиться в RecyclerView
    private List<Character> list;
    //Объект прослойка. Служит для вывода списка персонажей в RecyclerView
    private CharacterAdapter adapter;
    //Переменная хранящая информацию о текущей странице персонажей
    private int page=1;
    //Главная функция окна приложения
    //Она вызываеться при создании окна
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Задаём нашему окну разметку по шаблону
        setContentView(R.layout.activity_main);
        //Получаем экземпляр объекта для обмена данными с сервером
        service = RickAndMortyService.getInstance(this);
        //Привязываем объекты UI к объектам программы
        nextButton = findViewById(R.id.nextButton);
        backButton = findViewById(R.id.backButton);
        pageText = findViewById(R.id.pageCount);
        char_list = findViewById(R.id.char_list);
        //Создаём пустой список персонажей
        list = new ArrayList<Character>();
        //Создаём новый объект адаптер
        //Параметры
        //1 - контекст приложения
        //2 - Список персонажей который мы хотим вывести
        //3 - новый объект слушатель в котором прописана функция
        // которая будет вызывана при нажатии на элементе списка
        adapter = new CharacterAdapter(this,
                list,
                new CharacterAdapter.CharClickListener() {
            @Override
            public void click(int position, Character character) {
                //При нажатии на элемент списка будет вызывно новое окно
                //Что бы вызвать новое окно нам нужно созать Intent в котором надо указать
                //Исходную активность и активность которую мы хотим вызвать
                Intent intent =  new Intent(MainActivity.this,CharacterActivity.class);
                //Так же мы передаём персонажа которого выведем в новом окне
                intent.putExtra("char",service.getGson().toJson(character));
                //Вызов новго окна
                startActivity(intent);
            }
        });
        //Задаём нашему RecyclerView менеджер, который задаёт как выводить элементы
        char_list.setLayoutManager(new LinearLayoutManager(this));
        //Привязываем к RecyclerView адаптер
        char_list.setAdapter(adapter);

        //Обращаемся к нашему сервису за списком персонажей
        //Параметры
        //- Страница которую надо запросить
        //- Объект который обработает возвращённый ответ от сервера(в случае успеха или провала)
        service.getCharacter(page, new RickAndMortyListener() {
            //Функция которая сработает в случае успеха
            //Функция заполнит список персонажей и "скажет" адаптеру обновить свои данные
            @Override
            public void onSuccess(RMAllResponse response) {
                list.addAll(response.getResults());
                adapter.notifyDataSetChanged();
            }
            //Функция которая сработает в случае провала
            //Функция выведет сообщение об ошибке
            @Override
            public void onError(String message) {
                Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
            }
        });

        //Обработчик нажатия на кнопку "далее"
        nextButton.setOnClickListener(view -> {
            //Обращаемся к нашему сервису за списком персонажей
            //Параметры
            //- Страница которую надо запросить
            //- Объект который обработает возвращённый ответ от сервера(в случае успеха или провала)
            service.getCharacter(page + 1, new RickAndMortyListener() {
                //В случае успеха
                //Очистим текущий список персонажей
                //Заполним его новыми персонажами
                //Функция заполнит список персонажей и "скажет" адаптеру обновить свои данные
                //увеличит текущий показатель страницы
                @Override
                public void onSuccess(RMAllResponse response) {
                    list.clear();
                    list.addAll(response.getResults());
                    adapter.notifyDataSetChanged();
                    page++;
                    pageText.setText(""+page);
                    char_list.smoothScrollToPosition(0);
                }
                //Функция которая сработает в случае провала
                //Функция выведет сообщение об ошибке
                @Override
                public void onError(String message) {
                    Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
                }
            });
        });

        //Обработчик нажатия на кнопку "назад"
        //Сделает так же как и прошлый но запросит предыдущую страницу если это возможно
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
