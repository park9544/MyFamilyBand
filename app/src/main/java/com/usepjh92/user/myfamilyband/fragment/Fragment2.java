package com.usepjh92.user.myfamilyband.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.usepjh92.user.myfamilyband.R;
import com.usepjh92.user.myfamilyband.activity.MoreActivity;
import com.usepjh92.user.myfamilyband.adapter.Frag2RecyclerAdapter;
import com.usepjh92.user.myfamilyband.item.Item;

import java.util.ArrayList;

public class Fragment2 extends android.support.v4.app.Fragment{

    ArrayList<Item> items = new ArrayList<>();
    Frag2RecyclerAdapter adapter;
    RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_tab2 , container , false);

        recyclerView = (RecyclerView)view.findViewById(R.id.page2_recycler);

        adapter = new Frag2RecyclerAdapter(items , getActivity());

        recyclerView.setAdapter(adapter);

        RecyclerView.LayoutManager recycler = new LinearLayoutManager(getActivity() , LinearLayoutManager.VERTICAL , false);

        items.add(new Item("Park" , "2017년8월1일 오후 4:22" , "안녕하세요 우리가족들!"));
        items.add(new Item("Park" , "2017년8월1일 오후 4:22" , "안녕하세요 우리가족들!"));
        items.add(new Item("Park" , "2017년8월1일 오후 4:22" , "안녕하세요 우리가족들!"));
        items.add(new Item("Park" , "2017년8월1일 오후 4:22" , "안녕하세요 우리가족들!"));
        items.add(new Item("Park" , "2017년8월1일 오후 4:22" , "안녕하세요 우리가족들!"));
        items.add(new Item("Park" , "2017년8월1일 오후 4:22" , "안녕하세요 우리가족들!"));

        recyclerView.setLayoutManager(recycler);


        return view;
    }
}
