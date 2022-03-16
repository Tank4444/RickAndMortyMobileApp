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

public class CharacterAdapter extends RecyclerView.Adapter<CharacterAdapter.ViewHolder> {

    public interface CharClickListener{
        void click(int position, Character character);
    }

    private final CharClickListener listener;
    private final Context context;
    private final List<Character> list;
    private final LayoutInflater inflater;

    public CharacterAdapter(Context context, List<Character> list, CharClickListener listener) {
        this.context = context;
        this.list = list;
        this.inflater = LayoutInflater.from(context);
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.character_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Character character = list.get(position);
        holder.statusText.setText(character.getStatus());
        holder.nameText.setText(character.getName());
        Picasso.get().load(character.getImage()).into(holder.imageView);
        holder.itemView.setOnClickListener(view -> listener.click(position,character));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


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
