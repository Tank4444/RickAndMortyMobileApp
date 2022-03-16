package ru.itcube.kalc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import ru.itcube.kalc.databinding.ActivityCharacterBinding;
import ru.itcube.kalc.model.Character;
import ru.itcube.kalc.service.RickAndMortyService;

public class CharacterActivity extends AppCompatActivity {
    private Character character;
    private RickAndMortyService service;
    private TextView charActName;
    private TextView charActStatus;
    private TextView charActType;
    private TextView charActGender;
    private TextView charActOrigin;
    private TextView charActLocation;
    private ImageView charActImg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character);

        service = RickAndMortyService.getInstance(this);
        
        character = service.getGson().fromJson( getIntent().getExtras().getString("char"),Character.class);

        charActName = findViewById(R.id.char_act_name);
        charActStatus = findViewById(R.id.char_act_status);
        charActType = findViewById(R.id.char_act_type);
        charActGender = findViewById(R.id.char_act_gender);
        charActOrigin = findViewById(R.id.char_act_origin);
        charActLocation = findViewById(R.id.char_act_location);
        charActImg = findViewById(R.id.char_act_img);

        Picasso.get().load(character.getImage()).into(charActImg);

        charActName.setText(character.getName());
        charActStatus.setText(character.getStatus());
        charActType.setText(character.getType());
        charActGender.setText(character.getGender());
        charActOrigin.setText(character.getOrigin().getName());
        charActLocation.setText(character.getLocation().getName());




    }
}