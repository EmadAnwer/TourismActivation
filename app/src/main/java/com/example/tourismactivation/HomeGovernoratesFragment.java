package com.example.tourismactivation;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.tourismactivation.molde.Governorate;
import com.example.tourismactivation.molde.GovernoratesRecyclerViewAdapter;

import java.util.ArrayList;


public class HomeGovernoratesFragment extends Fragment {


    ArrayList<Governorate> governorates= new ArrayList<>();
    RecyclerView governorateRecyclerView;


    public HomeGovernoratesFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //setting test Governorates
        governorates.add(new Governorate("Alexandria","https://images.unsplash.com/photo-1601816500593-8f1276479ea6?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1050&q=80"));
        governorates.add(new Governorate("Aswan","https://s27363.pcdn.co/wp-content/uploads/2020/05/Things-to-do-in-Aswan.jpg.optimal.jpg"));
        governorates.add(new Governorate("Beni Suef","https://sceneeats.com/Content/Admin/Uploads/Articles/ArticlesMainPhoto/3492/08a3b54f-faf9-420a-9f46-c4c85c05b13c.jpg"));
        governorates.add(new Governorate("Dakahlia","https://globallytoday.com/wp-content/uploads/2020/03/cc-696x533.jpg"));
        governorates.add(new Governorate("Giza","https://lp-cms-production.imgix.net/2019-06/82e213048af4e026b6ba31e8f24cc923-pyramids-of-giza.jpg"));
        governorates.add(new Governorate("Cairo","https://d3rr2gvhjw0wwy.cloudfront.net/uploads/mandators/49581/file-manager/cairo-city-egypt-2020.jpg"));
        governorates.add(new Governorate("Suez","https://dneegypt.nyc3.digitaloceanspaces.com/2021/02/Suez-e1546642227546-1.jpg"));
        governorates.add(new Governorate("Matruh","https://i.pinimg.com/originals/31/b7/e2/31b7e27265bb011d85b2055da0955e1e.jpg"));
        governorates.add(new Governorate("New Valley","https://upload.wikimedia.org/wikipedia/commons/b/b0/Al_Farafrah%2C_New_Valley_Governorate%2C_Egypt_-_panoramio_%2852%29.jpg"));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home_governorates, container, false);
        //setting RecyclerView
        governorateRecyclerView = view.findViewById(R.id.governorateRecyclerView);
        governorateRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext(),LinearLayoutManager.HORIZONTAL,false);
        governorateRecyclerView.setLayoutManager(layoutManager);
        GovernoratesRecyclerViewAdapter adapter = new GovernoratesRecyclerViewAdapter(governorates,view.getContext());
        governorateRecyclerView.setAdapter(adapter);
        return view;
    }

}