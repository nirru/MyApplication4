package com.shareads.user.myapplication.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shareads.user.myapplication.AppController;
import com.shareads.user.myapplication.activity.Category;
import com.shareads.user.myapplication.activity.HomeActivityItemList;
import com.shareads.user.myapplication.R;
import com.shareads.user.myapplication.activity.BookDetail;
import com.shareads.user.myapplication.adapter.RecommendedAdapter;
import com.shareads.user.myapplication.adapter.TopSellingAdapter;
import com.shareads.user.myapplication.book;
import com.shareads.user.myapplication.adapter.exploreAdapter;
import com.shareads.user.myapplication.model.Recomended;
import com.shareads.user.myapplication.model.RecommendedResult;
import com.shareads.user.myapplication.model.TopSelling;
import com.shareads.user.myapplication.model.TopSellingResult;
import com.shareads.user.myapplication.retrofit.ApiClient;
import com.shareads.user.myapplication.retrofit.ApiInterface;
import com.shareads.user.myapplication.util.Utils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the

 * to handle interaction events.
 * Use the {@link home#newInstance} factory method to
 * create an instance of this fragment.
 */
public class home extends Fragment  {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    static ArrayList<RecommendedResult> ITEMS_RECOMMNEDED;
    static ArrayList<TopSellingResult> ITEMS_TOPSELLING;
    private ArrayList<book> ItemList;
    private RecyclerView recyclerView;
    private RecyclerView recyclerView2;
    private RecyclerView recyclerView3;
    private RecommendedAdapter mAdapter;
    private TopSellingAdapter mAdapter2;
    private exploreAdapter mAdapter3;
    TextView more;
    TextView seecategory;
    public static final String BOOK_UID = "bookid";
    public static final String ISVISIBLE = "isCartVisible";


    public home() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment home.
     */
    // TODO: Rename and change types and number of parameters
    public static home newInstance(String param1, String param2) {
        home fragment = new home();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        ITEMS_RECOMMNEDED = new ArrayList<>();
        ITEMS_TOPSELLING = new ArrayList<>();
        mAdapter = new RecommendedAdapter(getActivity(),ITEMS_RECOMMNEDED);
        mAdapter2 = new TopSellingAdapter(getActivity(), ITEMS_TOPSELLING);
        mAdapter.setOnItemClickListener(new RecommendedAdapter.MyClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                Intent i = new Intent(getActivity(),BookDetail.class);
                i.putExtra(ISVISIBLE,false);
                i.putExtra(BOOK_UID,ITEMS_RECOMMNEDED.get(position).getBookuniqueid());
                startActivity(i);

            }
        });
        mAdapter2.setOnItemClickListener(new TopSellingAdapter.MyClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                Intent i = new Intent(getActivity(),BookDetail.class);
                i.putExtra(ISVISIBLE,false);
                i.putExtra(BOOK_UID,ITEMS_TOPSELLING.get(position).getBookuniqueid());
                startActivity(i);
            }
        });
        getRecomendeData();

        Log.e("APP==", "" + AppController.getInstance().getBookPrefs().getUserId());

        recyclerView = (RecyclerView) rootView.findViewById(R.id.horizontal_recycler_view1);
        recyclerView2 = (RecyclerView) rootView.findViewById(R.id.horizontal_recycler_view2);
        recyclerView3 = (RecyclerView) rootView.findViewById(R.id.horizontal_recycler_view3);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        recyclerView.setAdapter(mAdapter);

        recyclerView2.setHasFixedSize(true);
        recyclerView2.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        recyclerView2.setAdapter(mAdapter2);

        recyclerView3.setHasFixedSize(true);
        recyclerView3.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        mAdapter3 = new exploreAdapter(getActivity(), ItemList);
        recyclerView3.setAdapter(mAdapter3);

        more = (TextView) rootView.findViewById(R.id.more1);
        more.setClickable(true);
        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity().getBaseContext(), HomeActivityItemList.class);
                startActivity(intent);
            }
        });
        seecategory=(TextView)rootView.findViewById(R.id.category);
        seecategory.setClickable(true);
        seecategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity().getBaseContext(), Category.class);
                startActivity(intent);
            }
        });

        return rootView;
    }




    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */



    public void getRecomendeData() {
       /* if (Utils.isConnectingToInternet(signin.this)) {*/
        final ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        final ProgressDialog pDialog = ProgressDialog.show(getActivity(), "", "Please wait ...", true);
        Call<Recomended> call = apiService.recommand(AppController.getInstance().getBookPrefs().getUserId());
        call.enqueue(new Callback<Recomended>() {
            @Override
            public void onResponse(Call<Recomended> call, Response<Recomended> response) {
//                pDialog.dismiss();
                if (response.code() == 200) {
                    Recomended data = response.body();
                    Log.e("dede","" + response.body().toString());
                    // Please run the code
                    for (RecommendedResult result: data.getResult()) {
                        Log.e("NAME",result.getTitle());
                        mAdapter.addItem(result);
                    }
                    getTopSellingData(pDialog);

                }
            }

            @Override
            public void onFailure(Call<Recomended> call, Throwable t) {
                // Log error here since request failed
                Log.e("", t.toString());
                if (pDialog != null && pDialog.isShowing())
                    pDialog.hide();
                Utils.AlertBox(getActivity(), "Opps!!!", t.toString());

            }
        });
        /*} else {
            Utils.AlertBox(signin.this, "", "Please check internet connection");
        }*/
    }

    public void getTopSellingData(final ProgressDialog pDialog) {
       /* if (Utils.isConnectingToInternet(signin.this)) {*/
        final ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        Call<TopSelling> call = apiService.topselling();
        call.enqueue(new Callback<TopSelling>() {
            @Override
            public void onResponse(Call<TopSelling> call, Response<TopSelling> response) {
                pDialog.dismiss();
                if (response.code() == 200) {
                    TopSelling data = response.body();
                    Log.e("dede","" + response.body().toString());
                    // Please run the code
                    for (TopSellingResult result: data.getResult()) {
                        Log.e("NAME",result.getTitle());
                        mAdapter2.addItem(result);
                    }
                    mAdapter2.notifyDataSetChanged();

                }
            }

            @Override
            public void onFailure(Call<TopSelling> call, Throwable t) {
                // Log error here since request failed
                if (pDialog != null && pDialog.isShowing())
                    pDialog.hide();
                Log.e("", t.toString());
                Utils.AlertBox(getActivity(), "Opps!!!", t.toString());

            }
        });
}
}
