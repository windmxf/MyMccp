package com.example.max.mymccp;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {
    GridView gridView;
    List Osteps = new ArrayList<>();
    List Xsteps = new ArrayList();
    List<Integer> allSteps=new ArrayList<Integer>();
    int count = 0;
    Button reset;
    ArrayAdapter<String> adapter;
    int numCols=19;
    int numInLine=5;
    boolean over=false;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main, container, false);
        try {
            gridView = (GridView) view.findViewById(R.id.grid);
            gridView.setNumColumns(numCols);
            initAdapter();
            gridView.setAdapter(adapter);
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    if (over){
                        return;
                    }
                    TextView t = (TextView) view.findViewById(R.id.item);
                    String sel;
                    int x = ((i % numCols) + 1);
                    int y = (((i)
                            / numCols) + 1);

                    int[] thisStep = {x, y};
                    if (allSteps.contains(i)){
                        return;
                    }
                    if (count % 2 == 0) {
                        sel = "O";
                        Osteps.add(thisStep);
                    } else {
                        sel = "X";
                        Xsteps.add(thisStep);
                    }
                    allSteps.add(i);
                    t.setText(sel);
                    count++;
                    if (count > ((numInLine-1)*2) && isLine(sel, x, y)) {
                        Toast.makeText(getActivity(), sel + "wins", Toast.LENGTH_LONG).show();
                        over=true;
                    }


                }
            });
            reset=(Button)view.findViewById(R.id.reset);
            reset.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    over=false;
                    count=0;
                    Osteps.clear();
                    Xsteps.clear();
                    allSteps.clear();
                    initAdapter();
                    gridView.setAdapter(adapter);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }
    private void initAdapter(){
        ArrayList list = new ArrayList();
        for (int i = 0; i < Math.pow(numCols,2); i++) {
            list.add("");
        }
        adapter = new ArrayAdapter(getActivity(), R.layout.item, R.id.item, list);


    }
    private boolean isLine(String sel, int x, int y) {
        int[] p1 = {x, y};
        int[] p2;
        List<int[]> lineStep = new ArrayList();
        if ("O".equals(sel)) {
            lineStep.addAll(Osteps);
        } else if ("X".equals(sel)) {
            lineStep.addAll(Xsteps);

        }
        for (int i = 0; i < lineStep.size() - 1; i++) {
            p2 = lineStep.get(i);
            double l12=Math.pow(p1[0] - p2[0],2)+ Math.pow(p1[1] - p2[1],2);
            if (l12==1||l12==2) {
                List<int[]> newLineStep=new ArrayList<>();
                newLineStep.addAll(lineStep);
                newLineStep.remove(i);
                if (lineInNum(p1,p2,newLineStep,2)){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     *
     * @param p1 顶点1
     * @param p2 顶点2
     * @param lineStep
     * @param count
     * @return
     */
    private boolean lineInNum(int[] p1,int[] p2,List<int[]> lineStep,int count){

        for (int i=0;i<lineStep.size()-1;i++) {
            int[] p3=lineStep.get(i);
            double l23=Math.pow(p2[0] - p3[0], 2)+ Math.pow(p2[1] - p3[1], 2);
            double l13=Math.pow(p1[0] - p3[0], 2)+ Math.pow(p1[1] - p3[1], 2);
            if ((p1[0] - p3[0]) * (p2[1] - p3[1]) == (p2[0] - p3[0]) * (p1[1] - p3[1])) {
                if (l13==1||l13==2){
                    count++;
                    if (count==numInLine){
                        return true;
                    }else {
                        List<int[]> nextLineStep=new ArrayList<>();
                        nextLineStep.addAll(lineStep);
                        nextLineStep.remove(i);
                        return lineInNum(p3,p2,nextLineStep,count);
                    }
                }else if (l23==1||l23==2){
                    count++;
                    if (count==numInLine){
                        return true;
                    }else {
                        List<int[]> nextLineStep=new ArrayList<>();
                        nextLineStep.addAll(lineStep);
                        nextLineStep.remove(i);
                        return lineInNum(p1,p3,nextLineStep,count);
                    }
                }
            }
        }
        return false;
    }
}
