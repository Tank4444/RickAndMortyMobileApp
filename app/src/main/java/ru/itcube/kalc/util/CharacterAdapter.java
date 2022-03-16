package ru.itcube.kalc.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import ru.itcube.kalc.R;
import ru.itcube.kalc.model.Character;


//Класс адаптер
//Он указывает RecyclerView как выводить наш список персонажей
public class CharacterAdapter extends RecyclerView.Adapter<CharacterAdapter.ViewHolder> {

    //Интерфейс который служит для написания отработки нажатия на элемент списка
    public interface CharClickListener{
        void click(int position, Character character);
    }
    //Обработчик нажатия на элемент
    private final CharClickListener listener;
    //Список персонажей который объект будет адаптировать для вывода на экран
    private final List<Character> list;
    //Специальный класс
    //Задача которго созадть новый элемент списка из макета
    private final LayoutInflater inflater;

    public CharacterAdapter(Context context, List<Character> list, CharClickListener listener) {
        this.list = list;
        this.inflater = LayoutInflater.from(context);
        this.listener = listener;
    }
    //Обязательная функция
    //Если у нас нету элемента который нужно отобразить в RecyclerView ( в случае прокрутки списка или добавления нового элемента)
    //Функция создаёт новый элемент из макета и возвращает его
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.character_item,parent,false);
        return new ViewHolder(view);
    }
    //Функция привязки элемента из списка персонажей к соответствующим элементу RecyclerView
    //По простому. Тут запоняются элементы RecyclerView
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Character character = list.get(position);
        holder.statusText.setText(character.getStatus());
        holder.nameText.setText(character.getName());
        Picasso.get().load(character.getImage()).into(holder.imageView);
        holder.itemView.setOnClickListener(view -> listener.click(position,character));
    }
    //Получение количества элементов списка персонажей
    @Override
    public int getItemCount() {
        return list.size();
    }

    //Объект который представляет собой элемент RecyclerView
    //в котором храняться переменные к которым привязаны текстовые поля и прочее из макета
    public static class ViewHolder extends RecyclerView.ViewHolder {
        final ImageView imageView;
        final TextView nameText;
        final TextView statusText;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.char_img);
            nameText = itemView.findViewById(R.id.char_name);
            statusText = itemView.findViewById(R.id.char_status);
        }
    }
}
