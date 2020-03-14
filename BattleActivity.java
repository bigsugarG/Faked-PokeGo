package com.example.projectb;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.net.URI;

import static com.example.projectb.MainActivity.EXTRA_MESSAGE;

public class BattleActivity extends AppCompatActivity {
    private int point;
    private int totalpoint;
    private int id;
    int hp;
    int pipimiNumber=0;
    int drugStock;
    int ballStock;

    TextView pointview;
    TextView totalpointview;
    ImageView imageView;
    TextView hpview1;
    TextView hpview2;
    TextView hpview3;
    TextView hpview4;
    ImageView bombview;
    TextView drugview;
    TextView ballview;

    @Override
    public void onBackPressed(){
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battle);
        Intent intent =getIntent();  //intentによる引数の受け取り方
        point=intent.getIntExtra("A",0);//0はデフォルト値なので何でもよい
        totalpoint=intent.getIntExtra("B",0);//0はデフォルト値なので何でもよい
        id=intent.getIntExtra("C",0);
        drugStock=intent.getIntExtra("D",0);
        ballStock=intent.getIntExtra("E",0);
        hp = point;

        imageView = findViewById(R.id.pokemon);
        hpview1 = findViewById(R.id.hp1);
        hpview2 = findViewById(R.id.hp2);
        hpview3 = findViewById(R.id.hp3);
        hpview4 = findViewById(R.id.hp4);
        bombview = findViewById(R.id.bomb);

        pointview = (TextView)findViewById(R.id.point);
        totalpointview = (TextView)findViewById(R.id.totalpoint);
        pointview.setText("You will get "+String.valueOf(point)+" points!");
        totalpointview.setText("totalpoint is "+String.valueOf(totalpoint-point)+" ⇒ "+String.valueOf(totalpoint));

        drugview = findViewById(R.id.drugStock);
        drugview.setText("キズ薬："+String.valueOf(drugStock));
        ballview = findViewById(R.id.ballStock);
        ballview.setText("モンスターボール："+String.valueOf(ballStock));

        showPokemon(id);
    }

    public void showPokemon(int id){
            if(id==1){imageView.setImageResource(R.drawable.asimari);
            }else if(id==2){imageView.setImageResource(R.drawable.wanpati);
            }else if(id==3){imageView.setImageResource(R.drawable.ibui);
            }else if(id==4){imageView.setImageResource(R.drawable.locon);
            }else if(id==5){imageView.setImageResource(R.drawable.yadon);
            }else if(id==6){imageView.setImageResource(R.drawable.pikachu02);
            }else if(id==7){imageView.setImageResource(R.drawable.nyasu);
            }else if(id==8){imageView.setImageResource(R.drawable.mokurow);
            }else if(id==9){imageView.setImageResource(R.drawable.koduck);
            }else if(id==10){imageView.setImageResource(R.drawable.kabigon);
            }else if(id==11){imageView.setImageResource(R.drawable.koiking);
            }else if(id==12){imageView.setImageResource(R.drawable.nyabi);
            }else if(id==13){imageView.setImageResource(R.drawable.prin);
            }else if(id==14){imageView.setImageResource(R.drawable.sonance);
            }else if(id==15){imageView.setImageResource(R.drawable.zenigame);
            }else if(id==16){imageView.setImageResource(R.drawable.hitokage);
            }else if(id==17){imageView.setImageResource(R.drawable.metamon);
            }else if(id==18){imageView.setImageResource(R.drawable.lucky);
            }else if(id==19){imageView.setImageResource(R.drawable.raprace);
            }else if(id==20){imageView.setImageResource(R.drawable.gyarados);
            }else if(id==21){imageView.setImageResource(R.drawable.genga);
            }else if(id==22){imageView.setImageResource(R.drawable.thunder);
            }else if(id==23){imageView.setImageResource(R.drawable.thunders);
            }else if(id==24){imageView.setImageResource(R.drawable.shawerz);
            }else if(id==25){imageView.setImageResource(R.drawable.pityu);
            }else if(id==26){imageView.setImageResource(R.drawable.fire);
            }else if(id==27){imageView.setImageResource(R.drawable.freezer);
            }else if(id==28){imageView.setImageResource(R.drawable.miu);
            }else if(id==29){imageView.setImageResource(R.drawable.miutwo);
            }else if(id==30){imageView.setImageResource(R.drawable.rityu);
            }else if(id==31){imageView.setImageResource(R.drawable.booster);
            }else if(id==32){imageView.setImageResource(R.drawable.rizardon);
            }else if(id==33) {imageView.setImageResource(R.drawable.fusigidane);
            }else if(id==34){imageView.setImageResource(R.drawable.kairyu);
            }else{imageView.setImageResource(R.drawable.popukoaikon); }
    }

    public void onClickEscape(View v){
        double r = Math.random()*3;
        if(r<2.0) {
            Context context = getApplicationContext();
            Toast toast = Toast.makeText(context, "うまく逃げ切れた！", Toast.LENGTH_LONG);
            toast.setGravity(0, 0, 4);
            toast.show();
            losePhase();
        }else{
            Context context = getApplicationContext();
            Toast toast = Toast.makeText(context, "逃げられない！", Toast.LENGTH_LONG);
            toast.setGravity(0, 0, 4);
            toast.show();
            enemyAttack();
        }
    }

    public void onClickBattle(View v){
        if(bombview.getVisibility()==View.INVISIBLE){
            bombview.setVisibility(View.VISIBLE);
            Context contextb = getApplicationContext();
            Toast toastb =Toast.makeText(contextb,"ポプ子は爆発した！", Toast.LENGTH_LONG);
            toastb.setGravity(0,0,4);
            toastb.show();
            hp = hp-1;
            if(hp==0){
                victoryPhase();//勝利フェーズ
            }
        }else{
            bombview.setVisibility(View.INVISIBLE);
            enemyAttack();
        }
    }

    public void onClickBag(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("何を使う？")
                .setTitle("バッグメニュー")
                .setNegativeButton("モンスターボール", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        useBall();
                    }
                })
                .setPositiveButton("キズ薬", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        useDrug();
                    }
                });
        builder.show();
    }

    public void useBall(){
        if(ballStock==0){
            Context context = getApplicationContext();
            Toast toast = Toast.makeText(context, "モンスターボールを持っていない！", Toast.LENGTH_SHORT);
            toast.setGravity(0, 0, 4);
            toast.show();
        }else if(ballStock>0){
            catchAnime();
            ballStock--;
            double r = Math.random();
            if(r>0.40){
                Context context = getApplicationContext();
                Toast toast = Toast.makeText(context, "捕獲成功！", Toast.LENGTH_SHORT);
                toast.setGravity(0, 0, 4);
                toast.show();
                victoryPhase();
            }else {
                Context context = getApplicationContext();
                Toast toast = Toast.makeText(context, "捕獲失敗．．．", Toast.LENGTH_SHORT);
                toast.setGravity(0, 0, 4);
                toast.show();
                enemyAttack();
            }
            ballview.setText("モンスターボール："+String.valueOf(ballStock));
        }
    }

    public void useDrug(){
        if (drugStock>0){
            double r = Math.random();
            if (bombview.getVisibility() == View.VISIBLE) {
                Context context = getApplicationContext();
                Toast toast = Toast.makeText(context, "今は使えない！", Toast.LENGTH_LONG);
                toast.setGravity(0, 0, 4);
                toast.show();
            } else if (bombview.getVisibility() == View.INVISIBLE && hpview4.getVisibility() == View.INVISIBLE) {
                hpview4.setVisibility(View.VISIBLE);
                hpview3.setVisibility(View.VISIBLE);
                drugStock=drugStock-1;
                drugview.setText("キズ薬："+String.valueOf(drugStock)+"個所持");
                if (r < 0.5) {
                    enemyAttack();
                } else {
                    enemyLazy();
                }
            } else if (bombview.getVisibility() == View.INVISIBLE && hpview4.getVisibility() == View.VISIBLE && hpview3.getVisibility() == View.INVISIBLE) {
                hpview3.setVisibility(View.VISIBLE);
                hpview2.setVisibility(View.VISIBLE);
                drugStock=drugStock-1;
                drugview.setText("キズ薬："+String.valueOf(drugStock)+"個所持");
                if (r < 0.5) {
                    enemyAttack();
                } else {
                    enemyLazy();
                }
            } else if (bombview.getVisibility() == View.INVISIBLE && hpview3.getVisibility() == View.VISIBLE && hpview2.getVisibility() == View.INVISIBLE) {
                hpview2.setVisibility(View.VISIBLE);
                hpview1.setVisibility(View.VISIBLE);
                drugStock=drugStock-1;
                drugview.setText("キズ薬："+String.valueOf(drugStock)+"個所持");
                if (r < 0.5) {
                    enemyAttack();
                } else {
                    enemyLazy();
                }
            } else if (bombview.getVisibility() == View.INVISIBLE && hpview2.getVisibility() == View.VISIBLE && hpview1.getVisibility() == View.INVISIBLE) {
                hpview1.setVisibility(View.VISIBLE);
                drugStock=drugStock-1;
                drugview.setText("キズ薬："+String.valueOf(drugStock)+"個所持");
                if (r < 0.5) {
                    enemyAttack();
                } else {
                    enemyLazy();
                }
            } else {
                Context context = getApplicationContext();
                Toast toast = Toast.makeText(context, "これ以上回復できません。", Toast.LENGTH_SHORT);
                toast.setGravity(0, 0, 4);
                toast.show();
            }
        }else if(drugStock==0){
            Context context = getApplicationContext();
            Toast toast = Toast.makeText(context, "キズ薬を持っていない！", Toast.LENGTH_SHORT);
            toast.setGravity(0, 0, 4);
            toast.show();
        }
    }

    public void catchAnime(){
        Context context = getApplicationContext();
        Toast toast = Toast.makeText(context, "モンスターボールを投げた！", Toast.LENGTH_SHORT);
        toast.setGravity(0, 0, 4);
        toast.show();
    }

    public void enemyAttack(){
        Context context = getApplicationContext();
        Toast toast =Toast.makeText(context,"敵の攻撃！", Toast.LENGTH_SHORT);
        toast.setGravity(0,0,4);
        toast.show();
        if(hpview1.getVisibility()==View.VISIBLE){
            hpview1.setVisibility(View.INVISIBLE);
        }else if(hpview1.getVisibility()==View.INVISIBLE&&hpview2.getVisibility()==View.VISIBLE){
            hpview2.setVisibility(View.INVISIBLE);
        }else if(hpview2.getVisibility()==View.INVISIBLE&&hpview3.getVisibility()==View.VISIBLE){
            hpview3.setVisibility(View.INVISIBLE);
        }else if(hpview3.getVisibility()==View.INVISIBLE&&hpview4.getVisibility()==View.VISIBLE){
            hpview4.setVisibility(View.INVISIBLE);
        }else if(hpview4.getVisibility()==View.INVISIBLE){
            Context context1 = getApplicationContext();
            Toast toast1 =Toast.makeText(context1,"バトルに負けてしまった．．．", Toast.LENGTH_LONG);
            toast1.setGravity(0,0,4);
            toast1.show();
            losePhase();
        }else{
            Context context1 = getApplicationContext();
            Toast toast1 =Toast.makeText(context1,"バトルに負けてしまった．．．", Toast.LENGTH_LONG);
            toast1.setGravity(0,0,4);
            toast1.show();
            losePhase();}
    }

    public void enemyLazy(){
        Context context = getApplicationContext();
        Toast toast =Toast.makeText(context,"相手のポケモンはなんだかのんびりしている。", Toast.LENGTH_SHORT);
        toast.setGravity(0,0,4);
        toast.show();
    }

    public void onClickPipimi(View v){
        if(pipimiNumber==0){
            double r = Math.random()*3;
            if(r<=1.5) {
                Context context = getApplicationContext();
                Toast toast = Toast.makeText(context, "ピピ美「ステイ！」", Toast.LENGTH_LONG);
                toast.setGravity(0, 0, 4);
                toast.show();
                pipimiNumber = pipimiNumber + 1;
            }else if(r>1.5&&r<=2.6){
                Context context = getApplicationContext();
                Toast toast = Toast.makeText(context, "ピピ美「怒ってないよ」", Toast.LENGTH_LONG);
                toast.setGravity(0, 0, 4);
                toast.show();
                totalpoint = totalpoint - point - 10;
                Intent intent = new Intent(BattleActivity.this,MainActivity.class);//特殊敗北フェーズ
                intent.putExtra(EXTRA_MESSAGE,totalpoint);
                intent.putExtra("DRUG",drugStock);
                intent.putExtra("BALL",ballStock);
                startActivity(intent);
            }else{
                trickyVictoryPhase();//特殊勝利フェーズ
            }
        }else{
            Context context = getApplicationContext();
            Toast toast = Toast.makeText(context, "これは2度は使えない！", Toast.LENGTH_LONG);
            toast.setGravity(0, 0, 4);
            toast.show();
        }
    }

    public void losePhase(){
        totalpoint = totalpoint - point;
        Intent intent = new Intent(BattleActivity.this, MainActivity.class);
        intent.putExtra(EXTRA_MESSAGE, totalpoint);
        intent.putExtra("DRUG",drugStock);
        intent.putExtra("BALL",ballStock);
        startActivity(intent);
    }

    public void victoryPhase(){
        imageView.setVisibility(View.INVISIBLE);
        Context context = getApplicationContext();
        Toast toast =Toast.makeText(context,"バトルに勝った！", Toast.LENGTH_LONG);
        toast.setGravity(0,0,4);
        toast.show();
        Intent intent = new Intent(BattleActivity.this,MainActivity.class);
        intent.putExtra(EXTRA_MESSAGE,totalpoint);
        intent.putExtra("DRUG",drugStock);
        intent.putExtra("BALL",ballStock);
        startActivity(intent);
    }

    public void trickyVictoryPhase(){//特殊勝利フェーズ
        imageView.setVisibility(View.INVISIBLE);
        Context context = getApplicationContext();
        Toast toast = Toast.makeText(context, "ピピ美「フン...」", Toast.LENGTH_LONG);
        toast.setGravity(0, 0, 4);
        toast.show();
        Intent intent = new Intent(BattleActivity.this,MainActivity.class);
        intent.putExtra(EXTRA_MESSAGE,totalpoint);
        intent.putExtra("DRUG",drugStock);
        intent.putExtra("BALL",ballStock);
        startActivity(intent);
    }
}
