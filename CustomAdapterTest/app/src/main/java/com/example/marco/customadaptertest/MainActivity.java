package com.example.marco.customadaptertest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        testNameList();
    }

    protected void testNameList() {
        ListView lvNameList = (ListView) findViewById(R.id.lvNameList);

        Name[] someValues = {new Name("Allen", "Turing"), new Name("Leonhard", "Euler"), new Name("Albert", "Einstein")};
        List<Name> someList = Arrays.asList(someValues);

        NameAdapter nameAdapter = new NameAdapter(this, someList);

        lvNameList.setAdapter(nameAdapter);
    }
}
